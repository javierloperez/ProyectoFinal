package com.example.proyectofinal.ui

import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.example.proyectofinal.R


enum class Pantallas(@StringRes val titulo:Int){
    Login(titulo = R.string.login),
    Parques(titulo = R.string.parques),
    Especies(titulo = R.string.especies),
    Actualizar(titulo = R.string.actualizar),
}