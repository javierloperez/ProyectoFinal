package com.example.proyectofinal.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.ui.AppUIstateEspecie

@Composable
fun PantallaInicioEspecie(
    modifier: Modifier,
    appUIState: AppUIstateEspecie,
    onPulsarActualizar: (Especies) -> Unit,
    onObtenerEspecie: () -> Unit,
    onEliminarEspecie: (Int) -> Unit,
    onFavoritoPulsado: (Favoritos) -> Unit,
    onEliminarFavorito: (Favoritos) -> Unit,
) {

    when (appUIState) {


        is AppUIstateEspecie.ObtenerExitoEspecies -> {
            PantallaEspecies(
                listaEspecies = appUIState.especies,
                onPulsarActualizar = onPulsarActualizar,
                onEliminarEspecie = onEliminarEspecie,
                onFavoritoPulsado = onFavoritoPulsado,
                listaFavoritos = appUIState.listaFavoritos,
                onEliminarFavorito = onEliminarFavorito,
            )
        }

        is AppUIstateEspecie.Error -> PantallaError(modifier)
        is AppUIstateEspecie.Cargando -> PantallaCargando(modifier)
        is AppUIstateEspecie.CrearExito -> onObtenerEspecie()
        is AppUIstateEspecie.ActualizarExito -> onObtenerEspecie()
        is AppUIstateEspecie.EliminarExito -> onObtenerEspecie()
        else -> {
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaEspecies(
    listaEspecies: List<Especies>,
    onPulsarActualizar: (Especies) -> Unit,
    onEliminarEspecie: (Int) -> Unit,
    onFavoritoPulsado: (Favoritos) -> Unit,
    onEliminarFavorito: (Favoritos) -> Unit,
    listaFavoritos: List<Favoritos>
) {

    var idEspecie by remember { mutableStateOf(0) }
    var eliminarDialogo by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        items(listaEspecies) { especie ->
            val marcadoFavorito = listaFavoritos.any{it.id == especie.id}
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onPulsarActualizar(especie) },
                        onLongClick = {
                            idEspecie = especie.id
                            eliminarDialogo = true
                        }
                    )
                    .border(width = 2.dp, color = Color.Black)
                    .aspectRatio(3.6f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.nombre) + ": " + especie.nombre)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.descripcion) + ": " + especie.descripcion)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.tipo) + ": " + especie.tipo)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp, end = 50.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    val favorito = listaFavoritos.find { it.id == especie.id }
                    IconButton(onClick = {
                        if (marcadoFavorito) {
                            if (favorito != null) {
                                onEliminarFavorito(favorito)
                            }
                        } else {
                            onFavoritoPulsado(Favoritos(especie.id, especie.nombre, ""))
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.estrella),
                            tint =
                            if (marcadoFavorito) {
                                Color.Yellow
                            } else {
                                Color.Gray
                            },
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }
                }

            }

        }
    }
    if (eliminarDialogo) {
        AlertDialog(
            onDismissRequest = {
                eliminarDialogo = false
            },
            title = { Text(text = stringResource(R.string.tituloEliminar)) },
            dismissButton = {
                TextButton(
                    onClick = {
                        eliminarDialogo = false
                    }) {
                    Text(text = stringResource(R.string.cancelar))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onEliminarEspecie(idEspecie)
                }) {
                    Text(text = stringResource(R.string.aceptar))
                }
            },
        )
    }
}

