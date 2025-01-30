package com.example.proyectofinal.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Parques

@Composable
fun PantallaInsertar(modifier: Modifier, onObjetoInsertar: () -> Unit) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

 /*   Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        TextField(
            value = especie.id.toString(),
            label = {
                Text(stringResource(R.string.id))
            },
            onValueChange = {},
            enabled = false
        )


        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            label = { Text(stringResource(R.string.nombre)) },
            onValueChange = { nombre = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = descripcion,
            label = { Text(stringResource(R.string.descripcion)) },
            onValueChange = { descripcion = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = tipo,
            label = { Text(stringResource(R.string.tipo)) },
            onValueChange = { tipo = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val especieActualizada = Especies(
                    id = especie.id,
                    nombre = nombre,
                    descripcion = descripcion,
                    tipo = tipo
                )
                onEspecieActualizada(especieActualizada)
            }) {
            Text(stringResource(R.string.actualizar))
        }

    }*/
}