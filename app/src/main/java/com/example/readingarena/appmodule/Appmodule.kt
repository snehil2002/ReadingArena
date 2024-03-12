package com.example.readingarena.appmodule

import com.example.readingarena.api.Bookapi
import com.example.readingarena.repo.Bookrepo
import com.example.readingarena.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Appmodule {

    @Provides
    @Singleton
    fun provideapi():Bookapi{
        return Retrofit.Builder().baseUrl(Constants.baseurl).
        addConverterFactory(GsonConverterFactory.create()).
        build().create(Bookapi::class.java)
    }
    @Provides
    @Singleton
    fun providefirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }



}