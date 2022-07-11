package com.anonlatte.natgeo.di

import com.anonlatte.natgeo.data.repository.ArticleMapper
import com.anonlatte.natgeo.data.repository.MainRepository
import com.anonlatte.natgeo.data.repository.MainRepositoryImpl
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractor
import com.anonlatte.natgeo.ui.home.interactor.HomeInteractorImpl
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModel
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { ArticleMapper() }
    single<MainRepository> { MainRepositoryImpl(get(), get()) }
    single<HomeInteractor> { HomeInteractorImpl(get()) }
    viewModel { HomeViewModelImpl(get()) } bind HomeViewModel::class
}