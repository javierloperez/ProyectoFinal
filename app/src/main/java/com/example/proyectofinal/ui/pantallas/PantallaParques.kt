package com.example.proyectofinal.ui.pantallas

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Parques
import com.example.proyectofinal.ui.AppUIstate

@Composable
fun PantallaInicio(modifier: Modifier, appUIState: AppUIstate,onPulsarActualizar: (Parques) -> Unit, onObtenerParques: () -> Unit) {

    when(appUIState){
        is AppUIstate.ObtenerExitoParques -> {
            PantallaParques(
                listaParques = appUIState.parques,
                onPulsarActualizar = onPulsarActualizar
            )
        }
        is AppUIstate.Error -> PantallaError(modifier)
        is AppUIstate.Cargando -> PantallaCargando(modifier)
        is AppUIstate.CrearExito -> onObtenerParques()
        else ->{

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun PantallaParques(listaParques: List<Parques>, onPulsarActualizar:(Parques) -> Unit) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),

    ) {

        items(listaParques){ parque ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = {onPulsarActualizar(parque)}
                    )
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(parque.nombre)
                    Spacer(Modifier.height(5.dp))
                    Text(parque.extension.toString())
                }

            }

        }
    }
}


@Composable
fun PantallaCargando(modifier: Modifier) {

    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(R.drawable.cargando),
        contentDescription = "Cargando"
    )
}

@Composable
fun PantallaError(modifier: Modifier) {
    Image(
        modifier = modifier.fillMaxSize(),
        painter = painterResource(R.drawable.error),
        contentDescription = "Error"
    )
}