package com.imams.simpleform.domain.di

import com.imams.simpleform.data.repository.ProvinceDataRepository
import com.imams.simpleform.data.repository.RegistrationDataRepository
import com.imams.simpleform.domain.*
import com.imams.simpleform.domain.implementation.AddressFormUseCaseImpl
import com.imams.simpleform.domain.implementation.FormPersonalInfoUseCaseImpl
import com.imams.simpleform.domain.implementation.ReviewUseCaseImpl
import com.imams.simpleform.domain.usecase.AddressFormUseCase
import com.imams.simpleform.domain.usecase.FormPersonalInfoUseCase
import com.imams.simpleform.domain.usecase.ReviewRegistrationUseCase
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

    @Singleton
    @Provides
    fun provideUseCaseReviewRegistration(repository: RegistrationDataRepository): ReviewRegistrationUseCase {
        return ReviewUseCaseImpl(repository)
    }
}