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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.ui.AppUIstateEspecie

@Composable
fun PantallaInicioEspecie(modifier: Modifier, appUIState: AppUIstateEspecie, onPulsarActualizar: (Especies) -> Unit, onObtenerEspecie: () -> Unit) {

    when(appUIState){
        is AppUIstateEspecie.ObtenerExitoEspecies -> {
            PantallaEspecies(
                listaEspecies = appUIState.especies,
                onPulsarActualizar = onPulsarActualizar
            )
        }
        is AppUIstateEspecie.Error -> PantallaError(modifier)
        is AppUIstateEspecie.Cargando -> PantallaCargando(modifier)
        is AppUIstateEspecie.CrearExito -> onObtenerEspecie()
        is AppUIstateEspecie.ActualizarExito -> onObtenerEspecie()
        else ->{
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PantallaEspecies(listaEspecies: List<Especies>, onPulsarActualizar:(Especies) -> Unit) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),

        ) {
        items(listaEspecies){ especie ->

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = {onPulsarActualizar(especie)}
                    )
                    .border(width = 2.dp, color = Color.Black)
                    .aspectRatio(3.6f)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(stringResource(R.string.nombre)+": " +especie.nombre)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.descripcion)+": "+especie.descripcion)
                    Spacer(Modifier.height(5.dp))
                    Text(stringResource(R.string.tipo)+": "+especie.tipo)
                }

            }

        }
    }
}

