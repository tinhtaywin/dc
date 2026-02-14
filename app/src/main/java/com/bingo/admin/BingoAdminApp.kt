package com.bingo.admin

import android.app.Application
import com.bingo.admin.data.remote.ApiService
import com.bingo.admin.di.NetworkModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BingoAdminApp : Application() {
    
    // Provide ApiService using the NetworkModule
    val apiService: ApiService by lazy {
        NetworkModule.provideApiService(
            NetworkModule.provideRetrofit(
                NetworkModule.provideHttpClient(),
                NetworkModule.provideConverterFactory()
            )
        )
    }
    
    override fun onCreate() {
        super.onCreate()
    }
}
