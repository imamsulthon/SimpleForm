package com.imams.simpleform.domain.di

import com.imams.simpleform.data.repository.ProvinceDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.domain.AddressFormUseCase
import com.imams.simpleform.domain.AddressFormUseCaseImpl
import com.imams.simpleform.domain.FormPersonalInfoUseCase
import com.imams.simpleform.domain.FormPersonalInfoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideUseCaseFormPersonalInfo(repository: RegistrationDataRepository): FormPersonalInfoUseCase {
        return FormPersonalInfoUseCaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideUseCaseFormAddressInfo(
        registrationRepo: RegistrationDataRepository,
        provinceDataRepo: ProvinceDataRepository,
    ): AddressFormUseCase {
        return AddressFormUseCaseImpl(registrationRepo, provinceDataRepo)
    }

}