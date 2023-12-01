package com.example.petsocial.di

import androidx.core.view.MenuHost
import androidx.fragment.app.FragmentActivity
import com.example.petsocial.CustomFragmentFactory
import com.example.petsocial.feature.login.LoginRepositoryImpl
import com.example.petsocial.feature.registiration.RegistirationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object Module {
    @Provides
    @Singleton
    fun provideMenuHost(activity: FragmentActivity): MenuHost {
        return activity
    }

    @Provides
    @Singleton
    fun provideCustomFragmentFactory( ): CustomFragmentFactory {
        return CustomFragmentFactory()
    }
    @Provides
    @Singleton
    fun provideSignUpRepository(): RegistirationRepositoryImpl {
        return RegistirationRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideSignInRepository(): LoginRepositoryImpl {
        return LoginRepositoryImpl()
    }


}