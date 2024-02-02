package com.example.petsocial.di

import android.content.Context
import androidx.core.view.MenuHost
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.petsocial.R
import com.example.petsocial.feature.login.LoginRepositoryImpl
import com.example.petsocial.feature.post.PostRepositoryImpl
import com.example.petsocial.feature.profile.ProfileRepositoryImpl
import com.example.petsocial.feature.registiration.RegistirationRepositoryImpl
import com.example.petsocial.room.UserDatabase
import com.example.petsocial.room.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, UserDatabase::class.java, "users")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun injectDao(database: UserDatabase) = database.userInfoDao()

    @Provides
    @Singleton
    fun provideMenuHost(activity: FragmentActivity): MenuHost {
        return activity
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(): RegistirationRepositoryImpl {
        return RegistirationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSignInRepository(userInfoDao: UserInfoDao): LoginRepositoryImpl {
        return LoginRepositoryImpl(userInfoDao)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(userInfoDao: UserInfoDao) : ProfileRepositoryImpl {
        return ProfileRepositoryImpl(userInfoDao)
    }
    @Provides
    @Singleton
    fun providePostRepository() : PostRepositoryImpl {
        return PostRepositoryImpl()
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.logo)
                .error(R.drawable.logo)
        )

}