package com.skb.btvdomainlib.di

import android.content.Context
import com.skb.btvdomainlib.network.api.RetrofitBuilder
import com.skb.btvdomainlib.network.boot.BootService
import com.skb.btvdomainlib.network.carbon.CarbonService
import com.skb.mytvlibrary.utils.BootUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Net work module
 *
 * @constructor Create empty Net work module
 */
@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    @Provides
    @Singleton
    fun provideIsStaging(): Boolean {
        return true // TODO:   staging state 를 가져 와야함
    }

    @Provides
    @Singleton
    fun provideBootService(): BootService {
        val baseUrl = BootService.Companion.getBaseUrl(provideIsStaging())
        val trustedHosts = BootService.Companion.getTrustedHosts(provideIsStaging())
        return RetrofitBuilder.createService(BootService::class.java, baseUrl, trustedHosts)
    }

    @Provides
    @Singleton
    fun provideCarbonService(@ApplicationContext context: Context): CarbonService {
        val trustedHosts = BootService.Companion.getTrustedHosts(provideIsStaging())
        return RetrofitBuilder.createService(
            CarbonService::class.java,
            getBaseUrl(context, serverType = ServerType.ATV) ?: CarbonService.Companion.baseUrl,
            trustedHosts
        )
            .apply { }
    }

    sealed class ServerType {
        object ATV : ServerType()

        fun get(): String {
            return when (this) {
                is ATV -> {
                    "atv"
                }
            }
        }
    }

    @Singleton
    fun getBaseUrl(context: Context, serverType: ServerType): String? {
        var serverList = BootUtil(context).loadServerList()
        fun find(server: String): String? {
            return serverList?.find { it.id == server }?.address
        }
        return when (serverType) {
            ServerType.ATV -> {
                find(ServerType.ATV.get())
            }
        }
    }
}