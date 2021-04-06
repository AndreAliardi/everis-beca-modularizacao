package com.niemietz.everis.beca.modularizacao.login.repository

import br.everis.login.interfaces.LoginAPI
import com.niemietz.everis.beca.modularizacao.login.model.AuthenticateRequest
import com.niemietz.everis.beca.modularizacao.login.model.GETSessionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(private val api: br.everis.login.interfaces.LoginAPI) {
    suspend fun getSession(request: GETSessionRequest) =
        withContext(Dispatchers.IO) {
            api.getSession(request)
        }

    suspend fun authenticate(request: AuthenticateRequest) =
        withContext(Dispatchers.IO) {
            api.authenticate(request)
        }
}