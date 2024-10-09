package jp.speakbuddy.edisonandroidexercise.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.data.repository.FactRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFactRepository(factRepositoryImpl: FactRepositoryImpl) : FactRepository
}