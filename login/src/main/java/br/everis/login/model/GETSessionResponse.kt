package br.everis.login.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.niemietz.everis.beca.modularizacao.login.model.KeyboardItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GETSessionResponse (
    @SerializedName("sessionId")
    val sessionId: String,
    @SerializedName("keyboard")
    val keyboard: ArrayList<KeyboardItem>
): Parcelable