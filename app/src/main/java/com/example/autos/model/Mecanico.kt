package com.example.autos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mecanico(

    var id: String,
    val descripcion: String?,
    val celular: String?,
    val latitud: Double?,
    val longitud: Double?,
    val altura: Double?
) : Parcelable {
    constructor() :
            this("",
                "",
                "",
                0.0,
                0.0,
                0.0
            )
}