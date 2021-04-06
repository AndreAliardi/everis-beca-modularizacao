package br.everis.login.states

sealed class LoginStates {
    data class GetSessionResult(val keyboard: ArrayList<br.everis.login.model.KeyboardItem>): LoginStates()
    data class GetSessionError(val exception: Exception): LoginStates()

    data class AuthenticateResult(val result: Boolean): LoginStates()
    data class AuthenticateError(val exception: Exception): LoginStates()
}