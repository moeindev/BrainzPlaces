package ir.moeindeveloper.brainzplaces.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ir.moeindeveloper.brainzplaces.places.repository.PlaceRepository
import ir.moeindeveloper.brainzplaces.places.repository.PlaceRepositoryImpl
import ir.moeindeveloper.brainzplaces.places.service.PlaceService

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesPlaceRepository(service: PlaceService): PlaceRepository =
        PlaceRepositoryImpl(service)
}