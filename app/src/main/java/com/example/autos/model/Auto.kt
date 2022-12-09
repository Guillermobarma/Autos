package com.example.autos.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Auto(

    var id: String,
    val nombre: String,
    val marca: String,
    val rutaimagen: String?
) : Parcelable {
    constructor() :
            this("",
                "",
                "",
                ""
            )
}