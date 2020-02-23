package ru.mironow.sentiment_analysis.social_network

import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.DateSocialData
import ru.mironow.sentiment_analysis.SocialData
import ru.mironow.sentiment_analysis.SocialDataSource
import ru.mironow.sentiment_analysis.SocialDataType
import ru.mironow.sentiment_analysis.data_service.SocialNetworkService
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.stream.Collectors


@Service
class VKService: SocialNetworkService {
    @Value("\${social_networks.vk.access_token}") lateinit var accessToken: String
    @Value("\${social_networks.vk.secret_key}") lateinit var secretKey: String
    @Value("\${social_networks.vk.app_id}") var appId: Int = 0

    private lateinit var VK: VkApiClient
    private lateinit var SERVICE_ACTOR: ServiceActor

    override fun getData(q: String): List<DateSocialData> {
        initVK()

        val now = LocalDateTime.now()
        var time = now
        val lastTime = time.minusMonths(12)
        val data = mutableListOf<DateSocialData>()

        while (time >= lastTime) {
            val items = if (time == now) {
                getDataLimit(q, COUNT_POST_FIRST_DAY)
            } else {
                getDataLimit(q, COUNT_POST_IN_MONTH, time, time.plusMonths(1L))
            }

            val dateSocialData = DateSocialData(date = time.toLocalDate(), data = items)
            data.add(dateSocialData)
            time = time.minusMonths(1L)
        }

        return data
    }

    private fun getDataLimit(
            q: String,
            limitPost: Int,
            startTime: LocalDateTime? = null,
            endTime: LocalDateTime? = null
    ): List<SocialData> {
        val data = mutableListOf<SocialData>()
        var postCounter = 0
        var index = 0

        var request = VK.newsfeed().search(SERVICE_ACTOR)
                .q(q)

        startTime?.let {
            request = request.startTime( (Timestamp.valueOf(startTime).time/1000).toInt() )
        }
        endTime?.let {
            request = request.endTime( (Timestamp.valueOf(endTime).time/1000).toInt() )
        }

        while (postCounter <= limitPost) {
            val countInRequest = if ((limitPost - postCounter) > LIMIT_POSTS_ON_REQUEST) {
                LIMIT_POSTS_ON_REQUEST
            } else {
                limitPost - postCounter
            }
            val posts = request.startFrom(index.toString())
                    .count(countInRequest)
                    .execute().items
                    .stream().map { wallPostFull ->
                        if (wallPostFull.comments.count > 0) {
                            val comments = searchCommentsForPost(wallPostFull.id, wallPostFull.ownerId)
                            data.addAll(comments)
                        }
                        SocialData(
                                data = wallPostFull.text,
                                source = SocialDataSource.VK,
                                type = SocialDataType.POST
                        )
                    }.collect(Collectors.toList())
            index += 1
            data.addAll(posts)
            postCounter += posts.size
            if (posts.size < LIMIT_POSTS_ON_REQUEST) {
                break
            }
        }
        return data
    }

    private fun searchCommentsForPost(postId: Int, ownerId: Int): List<SocialData> {
        try {
            return VK.wall().getComments(SERVICE_ACTOR, postId)
                    .ownerId(ownerId).execute()
                    .items.stream().map { comment ->
                SocialData(
                        data = comment.text,
                        source = SocialDataSource.VK,
                        type = SocialDataType.COMMENT)
            }.collect(Collectors.toList())
        } catch (e: Exception) {
            return emptyList()
        }
    }

    private fun initVK() {
        if (!(::VK.isInitialized && ::SERVICE_ACTOR.isInitialized)) {
            val transportClient: TransportClient = HttpTransportClient.getInstance()
            VK = VkApiClient(transportClient)
            SERVICE_ACTOR = ServiceActor(appId, secretKey, accessToken)
        }
    }

    companion object {
        private const val COUNT_POST_IN_MONTH = 50//200
        private const val COUNT_POST_FIRST_DAY = 50//1000
        private const val LIMIT_POSTS_ON_REQUEST = 200
    }
}