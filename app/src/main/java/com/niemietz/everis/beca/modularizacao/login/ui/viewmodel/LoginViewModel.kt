package com.niemietz.everis.beca.modularizacao.login.ui.viewmodel

import android.content.Context
import android.provider.Settings.Secure
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.niemietz.everis.beca.core.InternetChecker.isConnected2Internet
import com.niemietz.everis.beca.core.Session
import br.everis.login.events.LoginEvents
import br.everis.login.events.LoginInteractor
import com.niemietz.everis.beca.modularizacao.login.states.LoginStates
import com.niemietz.everis.beca.modularizacao.login.repository.LoginRepository
import br.everis.login.model.AuthenticateRequest
import br.everis.login.model.AuthenticateResponseContent
import br.everis.login.model.GETSessionRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val context: Context,
    private var repository: LoginRepository,
    private val checkInternetConnection: Boolean = true
): ViewModel() {
    private val job: Job = Job()
    private val currentJob: CoroutineContext =
        Dispatchers.Main + job
    val events = MutableLiveData<br.everis.login.events.LoginEvents>()
    val states = MutableLiveData<LoginStates>()

    fun interact(interaction: br.everis.login.events.LoginInteractor) {
        when (interaction) {
            is br.everis.login.events.LoginInteractor.GetSession -> getSession()
            is br.everis.login.events.LoginInteractor.Authenticate -> authenticate(interaction.password)
        }
    }

    private fun getSession() {
        if (isConnected2Internet(context)) {
            events.value = br.everis.login.events.LoginEvents.StartLoading

            CoroutineScope(currentJob).launch {
                try {
                    val deviceId = Secure.getString(
                        context.contentResolver,
                        Secure.ANDROID_ID
                    )

                    val response = repository.getSession(
                        br.everis.login.model.GETSessionRequest(
                            deviceId
                        )
                    )

                    setCoreSession(response.sessionId)

                    states.value = LoginStates.GetSessionResult(
                        response.keyboard
                    )
                } catch (ex: Exception) {
                    states.value = LoginStates.GetSessionError(ex)
                }
            }
        } else {
            events.value = br.everis.login.events.LoginEvents.NoInternet
        }
    }

    private fun authenticate(password: String) {
        if (isConnected2Internet(context)) {
            events.value = br.everis.login.events.LoginEvents.StartLoading

            CoroutineScope(currentJob).launch {
                try {
                    Session.id?.let {
                        val sessionId = it

                        val response = repository.authenticate(
                            br.everis.login.model.AuthenticateRequest(
                                sessionId,
                                password
                            )
                        )

                        setCoreUserContent(response.content)

                        states.value = LoginStates.AuthenticateResult(
                            response.result
                        )
                    } ?: run {
                        events.value = br.everis.login.events.LoginEvents.NoSession
                    }
                } catch (ex: Exception) {
                    states.value = LoginStates.GetSessionError(ex)
                }
            }
        } else {
            events.value = br.everis.login.events.LoginEvents.NoInternet
        }
    }

    private fun setCoreSession(sessionId: String) {
        Session.id = sessionId
    }

    private fun setCoreUserContent(userContent: br.everis.login.model.AuthenticateResponseContent) {
        Session.content = JSONObject(Gson().toJson(userContent))
    }
}