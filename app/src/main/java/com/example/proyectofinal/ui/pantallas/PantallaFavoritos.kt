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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.ui.AppUIstateEspecie



@Composable
fun PantallaInicioFavoritos(
    onPulsarActualizar: (Favoritos) -> Unit,
    appUiState: AppUIstateEspecie,
    onObtenerFavoritos: () -> Unit,
    ) {

    when(appUiState){
        is AppUIstateEspecie.ObtenerExitoEspecies -> {
            PantallaFavoritos(
                lista = appUiState.listaFavoritos,
                onPulsarActualizar = onPulsarActualizar
            )
        }
        is AppUIstateEspecie.ActualizarExito -> onObtenerFavoritos()
        else ->{

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaFavoritos(
    lista: List<Favoritos>,
    onPulsarActualizar: (Favoritos) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        items(lista) { favorito ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onPulsarActualizar(favorito) },
                    )
                    .border(width = 2.dp, color = Color.Black)
                    .aspectRatio(4f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.nombre) + ": " + favorito.nombreEspecie)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.apodo) + ": " + favorito.apodo)
                }
            }
        }
    }
}