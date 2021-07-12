package ir.moeindeveloper.brainzplaces.core.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Providing a meaningful user agent based on the documents
 *
 * https://musicbrainz.org/doc/MusicBrainz_API/Rate_Limiting#Provide_meaningful_User-Agent_strings
 */
class AgentInterceptor: Interceptor {

    companion object {
        private const val UserAgent: String = "User-Agent"
        private const val UserAgentParameter: String = "BrainzPLaces/v1.0.0 (moeinabdi77@gmail.com)"
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestWithAgent = request.newBuilder()
            .addHeader(UserAgent, UserAgentParameter)
            .build()

        return chain.proceed(requestWithAgent)
    }
}