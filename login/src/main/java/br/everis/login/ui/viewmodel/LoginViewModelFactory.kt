package br.everis.login.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LoginViewModelFactory(
    private val context: Context,
    private val repository: br.everis.login.repository.LoginRepository,
    private val checkInternetConnection: Boolean = true
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(br.everis.login.ui.viewmodel.LoginViewModel::class.java) ->
                    br.everis.login.ui.viewmodel.LoginViewModel(
                        context,
                        repository,
                        checkInternetConnection
                    )

                else ->
                    throw IllegalArgumentException("Unkown ViewModel class: ${modelClass.name}")
            }
        } as T
}