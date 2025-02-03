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
import androidx.compose.ui.unit.max
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.modelos.Parques

@Composable
fun PantallaActualizar(modifier: Modifier, objeto: Any?, onObjetoActualizado: (Any) -> Unit) {

    when (objeto) {
        is Parques -> parque(parque = objeto, onParqueActualizado = onObjetoActualizado)
        is Especies -> especies(especie = objeto, onEspecieActualizada = onObjetoActualizado)
        is Favoritos -> favoritos(favorito = objeto, onFavoritoActualizado = onObjetoActualizado)
    }
}

@Composable
private fun especies(especie: Especies, onEspecieActualizada: (Any) -> Unit) {

    var errorNombre by remember { mutableStateOf(false) }
    var errorDescripcion by remember { mutableStateOf(false) }
    var errorTipo by remember { mutableStateOf(false) }
    var marcarError by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(especie.nombre) }
    var descripcion by remember { mutableStateOf(especie.descripcion) }
    var tipo by remember { mutableStateOf(especie.tipo) }

    Column(
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
            marcarError = true
        } else {
            marcarError = false
        }
        Button(
            onClick = {
                val especieActualizada = Especies(
                    id = especie.id,
                    nombre = nombre,
                    descripcion = descripcion,
                    tipo = tipo
                )
                onEspecieActualizada(especieActualizada)
            },
            enabled = !marcarError
        ) {
            Text(
                stringResource(R.string.actualizar),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

@Composable
private fun parque(parque: Parques, onParqueActualizado: (Any) -> Unit) {

    var errorNombre by remember { mutableStateOf(false) }
    var errorExtension by remember { mutableStateOf(false) }
    var marcarError by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(parque.nombre) }
    var extensionText by remember { mutableStateOf(parque.extension.toString()) }
    var extension by remember { mutableStateOf(parque.extension) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            value = parque.id.toString(),
            label = { Text(stringResource(R.string.id)) },
            onValueChange = {},
            enabled = false
        )

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
            marcarError = true
        } else {
            marcarError = false
        }
        Button(
            onClick = {
                val parqueActualizado = Parques(
                    id = parque.id,
                    nombre = nombre,
                    extension = extension,
                )
                onParqueActualizado(parqueActualizado)
            },

            enabled = !marcarError
        ) {
            Text(
                stringResource(R.string.actualizar),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

@Composable
private fun favoritos(favorito: Favoritos, onFavoritoActualizado: (Any) -> Unit) {

    var errorApodo by remember { mutableStateOf(false) }
    var apodo by remember { mutableStateOf(favorito.apodo) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        TextField(
            value = favorito.id.toString(),
            label = {
                Text(stringResource(R.string.id))
            },
            onValueChange = {},
            enabled = false
        )


        Spacer(Modifier.height(16.dp))

        TextField(
            value = favorito.nombreEspecie,
            label = {
                Text(stringResource(R.string.nombre))
            },
            onValueChange = {},
            enabled = false
        )


        Spacer(Modifier.height(16.dp))

        TextField(
            value = apodo,
            label = { Text(stringResource(R.string.apodo)) },
            onValueChange = {
                apodo = it
                if (it.isBlank()) {
                    errorApodo = true
                } else {
                    errorApodo = false
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = errorApodo
        )


        Spacer(Modifier.height(16.dp))


        Button(
            onClick = {
                val favoritoActualizado = Favoritos(
                    id = favorito.id,
                    nombreEspecie = favorito.nombreEspecie,
                    apodo = apodo
                )
                onFavoritoActualizado(favoritoActualizado)
            },
            enabled = !errorApodo
        ) {
            Text(
                stringResource(R.string.actualizar),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}