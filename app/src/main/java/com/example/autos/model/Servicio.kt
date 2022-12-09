package com.example.autos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Servicio(

    var id: String,
    val fecha: String,
    val descripcion: String?,
    val kilometraje: String?
) : Parcelable {
    constructor() :
            this("",
                "",
                "",
                ""
            )
            }