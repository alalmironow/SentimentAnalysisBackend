package ru.mironow.sentiment_analysis.social_network

import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.mironow.sentiment_analysis.SocialData
import ru.mironow.sentiment_analysis.SocialDataSource
import ru.mironow.sentiment_analysis.SocialDataType
import ru.mironow.sentiment_analysis.data_service.SocialNetworkService
import java.sql.Timestamp
import java.util.stream.Collectors


@Service
class VKService: SocialNetworkService {
    @Value("\${social_networks.vk.access_token}") lateinit var accessToken: String
    @Value("\${social_networks.vk.secret_key}") lateinit var secretKey: String
    @Value("\${social_networks.vk.app_id}") var appId: Int = 0

    private lateinit var VK: VkApiClient
    private lateinit var SERVICE_ACTOR: ServiceActor

    override fun getData(q: String): List<SocialData> {
        initVK()
        return searchPosts(q)
    }

    private fun searchPosts(q: String): List<SocialData> {
        val response = VK.newsfeed().search(SERVICE_ACTOR)
                .count(200).q(q).execute()
        return response.items.stream().map { wallPostFull -> SocialData(
                        data = wallPostFull.text,
                        source = SocialDataSource.VK,
                        type = SocialDataType.POST,
                        date = Timestamp(wallPostFull.date.toLong()).toLocalDateTime())
        }.collect(Collectors.toList())
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
    }
}