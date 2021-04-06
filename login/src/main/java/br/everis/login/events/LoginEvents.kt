package br.everis.login.events

sealed class LoginEvents {
    object StartLoading: LoginEvents()
    object NoInternet: LoginEvents()
    object NoSession : LoginEvents()
}