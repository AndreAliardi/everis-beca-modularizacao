package br.everis.login.repository

import br.everis.login.interfaces.LoginAPI
import br.everis.login.model.AuthenticateRequest
import br.everis.login.model.GETSessionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val api: br.everis.login.interfaces.LoginAPI) {
    suspend fun getSession(request: br.everis.login.model.GETSessionRequest) =
        withContext(Dispatchers.IO) {
            api.getSession(request)
        }

    suspend fun authenticate(request: br.everis.login.model.AuthenticateRequest) =
        withContext(Dispatchers.IO) {
            api.authenticate(request)
        }
}