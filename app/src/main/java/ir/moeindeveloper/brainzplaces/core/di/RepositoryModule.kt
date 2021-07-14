package ir.moeindeveloper.brainzplaces.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    /**
     * Provide repositories with [ViewModelScoped] annotation!
     */
}