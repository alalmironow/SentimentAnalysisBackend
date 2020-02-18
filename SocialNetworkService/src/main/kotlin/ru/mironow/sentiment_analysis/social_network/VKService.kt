package ru.mironow.sentiment_analysis.social_network

import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.MonthSocialData
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

    override fun getRelevantData(q: String): List<SocialData> {
        initVK()
        val data = mutableListOf<SocialData>()
        var index = 0

        while(data.size <= MAX_COUNT_POSTS) {
            val response = VK.newsfeed().search(SERVICE_ACTOR)
                    .count(LIMIT_POSTS_ON_REQUEST)
                    .startFrom(index.toString())
                    .q(q).execute()
            index += LIMIT_POSTS_ON_REQUEST
            val items = response.items.stream().map { wallPostFull ->
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
            data.addAll(items)
            if (items.size < LIMIT_POSTS_ON_REQUEST) {
                break
            }
        }

        return data
    }

    override fun getYearData(q: String): List<MonthSocialData> {
        initVK()
        var date = LocalDateTime.now()
        var dateMinusYear = date.minusYears(1L)
        val data = mutableListOf<MonthSocialData>()
        while(date > dateMinusYear) {
            val dateMinusMonth = date.minusMonths(1L)
            val dateTime = (Timestamp.valueOf(date).time/1000).toInt()
            val dateMinusMonthTime = (Timestamp.valueOf(dateMinusMonth).time/1000).toInt()
            val response = VK.newsfeed().search(SERVICE_ACTOR)
                    .count(200)
                    .startTime(dateMinusMonthTime)
                    .endTime(dateTime)
                    .q(q).execute()
            val dataItems = mutableListOf<SocialData>()
            val items = response.items.stream().map { wallPostFull ->
                if (wallPostFull.comments.count > 0) {
                    val comments = searchCommentsForPost(wallPostFull.id, wallPostFull.ownerId)
                    dataItems.addAll(comments)
                }
                SocialData(
                    data = wallPostFull.text,
                    source = SocialDataSource.VK,
                    type = SocialDataType.POST)
            }.collect(Collectors.toList())
            dataItems.addAll(items)
            data.add(
                    MonthSocialData(date.toLocalDate(), dataItems)
            )
            date = dateMinusMonth
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
        private const val MAX_COUNT_POSTS = 1000
        private const val LIMIT_POSTS_ON_REQUEST = 200
    }
}