package com.skb.btvdomainlib.di

import com.skb.btvdomainlib.network.api.RetrofitBuilder
import com.skb.btvdomainlib.network.boot.BootService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideBootService(isStaging: Boolean): BootService {
        val baseUrl = BootService.Companion.getBaseUrl(provideIsStaging())
        val trustedHosts = BootService.Companion.getTrustedHosts(provideIsStaging())
        return RetrofitBuilder.createService(BootService::class.java, baseUrl, trustedHosts)
    }
}