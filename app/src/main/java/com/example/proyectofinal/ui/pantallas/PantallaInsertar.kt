package com.example.proyectofinal.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
fun PantallaInsertar(modifier: Modifier, onObjetoInsertar: (Any) -> Unit, tipo: Int) {

    when (tipo) {
        R.string.parques -> InsertarParque(onParqueInsertado = onObjetoInsertar)
        R.string.especies -> InsertarEspecie(onEspecieInsertada = onObjetoInsertar)
    }
}

@Composable
private fun InsertarEspecie(onEspecieInsertada: (Any) -> Unit) {

    var errorNombre by remember { mutableStateOf(false) }
    var deshabilitar by remember { mutableStateOf(true) }
    var errorDescripcion by remember { mutableStateOf(false) }
    var errorTipo by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(Modifier.height(16.dp))

        TextField(
            value = nombre,
            label = { Text(stringResource(R.string.nombre)) },
            onValueChange = {
                nombre = it
                if (it.isBlank()) {
                    errorNombre = true
                } else {
                    errorNombre = false
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = errorNombre

        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = descripcion,
            label = { Text(stringResource(R.string.descripcion)) },
            onValueChange = {
                descripcion = it
                if (it.isBlank()) {
                    errorDescripcion = true
                } else {
                    errorDescripcion = false
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = errorDescripcion
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = tipo,
            label = { Text(stringResource(R.string.tipo)) },
            onValueChange = {
                tipo = it
                if (it.isBlank()) {
                    errorTipo = true
                } else {
                    errorTipo = false
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = errorTipo

        )

        Spacer(Modifier.height(16.dp))
        if (errorNombre || errorDescripcion || errorTipo) {
            deshabilitar = true
        } else {
            deshabilitar = false
        }
        Button(
            onClick = {
                val especieNueva = Especies(
                    nombre = nombre,
                    descripcion = descripcion,
                    tipo = tipo
                )
                onEspecieInsertada(especieNueva)

            },
            enabled = !deshabilitar
        ) {

            Text(
                stringResource(R.string.insertar),
                style = MaterialTheme.typography.bodyLarge            )
        }

    }
}

@Composable
private fun InsertarParque(onParqueInsertado: (Any) -> Unit) {

    var errorNombre by remember { mutableStateOf(false) }
    var deshabilitar by remember { mutableStateOf(true) }
    var errorExtension by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var extensionText by remember { mutableStateOf("") }
    var extension by remember { mutableStateOf(0.0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        TextField(
            value = nombre,
            label = { Text(stringResource(R.string.nombre)) },
            onValueChange = {
                nombre = it
                if (it.isBlank()) {
                    errorNombre = true
                } else {
                    errorNombre = false
                }
            },

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = errorNombre,
            modifier = Modifier
                .width(280.dp)
        )

        Spacer(Modifier.height(16.dp))

        TextField(
            value = extensionText,
            label = { Text(stringResource(R.string.extension)) },
            onValueChange = {
                extensionText = it
                extension = extensionText.toDoubleOrNull() ?: 0.0
                if (it.isBlank()) {
                    errorExtension = true
                } else {
                    errorExtension = false
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorExtension
        )
        Spacer(Modifier.height(16.dp))

        if (errorNombre || errorExtension) {
            deshabilitar = true
        } else {
            deshabilitar = false
        }
        Button(
            onClick = {
                val parqueNUevo = Parques(
                    nombre = nombre,
                    extension = extension,
                )
                onParqueInsertado(parqueNUevo)
            },
            enabled = !deshabilitar
        ) {
            Text(
                stringResource(R.string.insertar),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}