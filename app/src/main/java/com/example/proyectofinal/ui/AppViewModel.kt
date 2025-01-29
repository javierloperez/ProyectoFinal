package com.example.proyectofinal.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.proyectofinal.Aplicacion
import com.example.proyectofinal.datos.BaseLocalRepositorio
import com.example.proyectofinal.datos.JsonRepositorio
import com.example.proyectofinal.modelos.Especies
import com.example.proyectofinal.modelos.Favoritos
import com.example.proyectofinal.modelos.Parques
import kotlinx.coroutines.launch

sealed interface AppUIstate {
    data class ObtenerExitoParques(val parques: List<Parques>) : AppUIstate
    data class ObtenerExitoParque(val parques: Parques) : AppUIstate
    data class ObtenerExitoEspecies(val especies: List<Especies>) : AppUIstate
    data class ObtenerExitoEspecie(val especies: Especies) : AppUIstate
    data class ObtenerExitoFavoritos(val favoritos: List<Favoritos>) : AppUIstate
    data class ObtenerExitoFavorito(val favoritos: Favoritos) : AppUIstate


    object CrearExito : AppUIstate
    object Cargando : AppUIstate
    object Error : AppUIstate
    object ActualizarExito : AppUIstate
    object EliminarExito : AppUIstate

}

class AppViewModel(
    private val baseLocalRepositorio: BaseLocalRepositorio,
    private val jsonRepositorio: JsonRepositorio
) : ViewModel() {

    var appUIstate: AppUIstate by mutableStateOf(AppUIstate.Cargando)
        private set

    init {
        obtenerParques()
    }

    fun obtenerParques() {
        viewModelScope.launch {
            appUIstate = try {
                val lista = jsonRepositorio.obtenerParques()
                AppUIstate.ObtenerExitoParques(lista)
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun obtenerParque(id:Int){
        viewModelScope.launch {
            appUIstate = try {
                val parque = jsonRepositorio.obtenerParque(id)
                AppUIstate.ObtenerExitoParque(parque)
            }catch (e:Exception){
                AppUIstate.Error
            }
        }
    }
    fun obtenerEspecies() {
        viewModelScope.launch {
            appUIstate = try {
                val lista = jsonRepositorio.obtenerEspecies()
                AppUIstate.ObtenerExitoEspecies(lista)
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun obtenerFavoritos() {
        viewModelScope.launch {
            appUIstate = try {
                val lista = baseLocalRepositorio.obtenerFavoritos()
                AppUIstate.ObtenerExitoFavoritos(lista)
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun obtenerFavorito(id: Int) {
        viewModelScope.launch {
            appUIstate = try {
                val favorito = baseLocalRepositorio.obtenerFavorito(id)
                AppUIstate.ObtenerExitoFavorito(favorito)
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun actualizarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstate = try {
                baseLocalRepositorio.actualizarFavorito(favoritos)
                AppUIstate.ActualizarExito
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun insertarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstate = try {
                baseLocalRepositorio.insertarFavorito(favoritos)
                AppUIstate.CrearExito

            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    fun eliminarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstate = try {
                baseLocalRepositorio.eliminarFavorito(favoritos)
                AppUIstate.EliminarExito
            } catch (e: Exception) {
                AppUIstate.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                val jsonRepositorio = aplicacion.contenedor.jsonRepositorio
                val baseLocalRepositorio = aplicacion.contenedor.baseLocalRepositorio
                AppViewModel(
                    jsonRepositorio = jsonRepositorio,
                    baseLocalRepositorio = baseLocalRepositorio
                )
            }
        }
    }
}