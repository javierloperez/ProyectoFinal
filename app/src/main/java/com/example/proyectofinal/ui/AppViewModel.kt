package com.example.proyectofinal.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlin.math.log

sealed interface AppUIstateParque {
    data class ObtenerExitoParques(val parques: List<Parques>) : AppUIstateParque
    data class ObtenerExitoParque(val parques: Parques) : AppUIstateParque
    data class ObtenerExitoFavoritos(val favoritos: List<Favoritos>) : AppUIstateParque
    data class ObtenerExitoFavorito(val favoritos: Favoritos) : AppUIstateParque


    object CrearExito : AppUIstateParque
    object Cargando : AppUIstateParque
    object Error : AppUIstateParque
    object ActualizarExito : AppUIstateParque
    object EliminarExito : AppUIstateParque

}

sealed interface AppUIstateEspecie {
    data class ObtenerExitoEspecies(val especies: List<Especies>) : AppUIstateEspecie
    data class ObtenerExitoEspecie(val especies: Especies) : AppUIstateEspecie
    data class ObtenerExitoFavoritos(val favoritos: List<Favoritos>) : AppUIstateEspecie
    data class ObtenerExitoFavorito(val favoritos: Favoritos) : AppUIstateEspecie


    object CrearExito : AppUIstateEspecie
    object Cargando : AppUIstateEspecie
    object Error : AppUIstateEspecie
    object ActualizarExito : AppUIstateEspecie
    object EliminarExito : AppUIstateEspecie

}

class AppViewModel(
    private val baseLocalRepositorio: BaseLocalRepositorio,
    private val jsonRepositorio: JsonRepositorio
) : ViewModel() {

    var appUIstateParque: AppUIstateParque by mutableStateOf(AppUIstateParque.Cargando)
        private set

    var appUIstateEspecie: AppUIstateEspecie by mutableStateOf(AppUIstateEspecie.Cargando)
        private set

    var objetoPulsado: Any? by mutableStateOf(null)
        private set



    fun <T> actualizarObjetoPulsado(objeto:T){
        objetoPulsado = objeto
    }
    init {
        obtenerParques()
        obtenerEspecies()
    }

    fun actualizarObjeto(objeto: Any){

        viewModelScope.launch {
            try {
                when(objeto){
                    is Parques ->{
                        jsonRepositorio.actualizarParque(objeto.id, objeto)
                        appUIstateParque = AppUIstateParque.ActualizarExito
                    }
                    is Especies -> {
                        jsonRepositorio.actualizarEspecie(objeto.id,objeto)
                        appUIstateEspecie = AppUIstateEspecie.ActualizarExito
                    }
                }

            }catch (e:Exception){
                AppUIstateParque.Error
            }
        }
    }


    fun obtenerParques() {
        viewModelScope.launch {
            appUIstateParque = try {
                val lista = jsonRepositorio.obtenerParques()
                AppUIstateParque.ObtenerExitoParques(lista)
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun obtenerParque(id:Int){
        viewModelScope.launch {
            appUIstateParque = try {
                val parque = jsonRepositorio.obtenerParque(id)
                AppUIstateParque.ObtenerExitoParque(parque)
            }catch (e:Exception){
                AppUIstateParque.Error
            }
        }
    }
    fun insertarParque(parques: Parques){
        viewModelScope.launch {
            try {
                jsonRepositorio.insertarParque(parques)
                AppUIstateParque.CrearExito
            }catch (e:Exception){
                AppUIstateParque.Error
            }
        }
    }




    fun eliminarParque(id: Int){
        viewModelScope.launch {
            try {
                jsonRepositorio.eliminarParque(id)
                AppUIstateParque.EliminarExito
            }catch (e:Exception){
                AppUIstateParque.Error
            }
        }
    }


    fun obtenerEspecies() {
        viewModelScope.launch {
            appUIstateEspecie = try {
                val lista = jsonRepositorio.obtenerEspecies()
                AppUIstateEspecie.ObtenerExitoEspecies(lista)
            } catch (e: Exception) {
                AppUIstateEspecie.Error
            }
        }
    }

    fun obtenerEspecie(id:Int){
        viewModelScope.launch {
            appUIstateEspecie = try {
                val especie = jsonRepositorio.obtenerEspecie(id)
                AppUIstateEspecie.ObtenerExitoEspecie(especie)
            }catch (e:Exception){
                AppUIstateEspecie.Error
            }
        }
    }

    fun insertarEspecie(especies: Especies){
        viewModelScope.launch {
            try {
                jsonRepositorio.insertarEspecie(especies)
                AppUIstateEspecie.CrearExito
            }catch (e:Exception){
                AppUIstateEspecie.Error
            }
        }
    }


    fun eliminarEspecie(id: Int){
        viewModelScope.launch {
            try {
                jsonRepositorio.eliminarEspecie(id)
                AppUIstateEspecie.EliminarExito
            }catch (e:Exception){
                AppUIstateEspecie.Error
            }
        }
    }

    fun obtenerFavoritos() {
        viewModelScope.launch {
            appUIstateParque = try {
                val lista = baseLocalRepositorio.obtenerFavoritos()
                AppUIstateParque.ObtenerExitoFavoritos(lista)
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun obtenerFavorito(id: Int) {
        viewModelScope.launch {
            appUIstateParque = try {
                val favorito = baseLocalRepositorio.obtenerFavorito(id)
                AppUIstateParque.ObtenerExitoFavorito(favorito)
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun actualizarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstateParque = try {
                baseLocalRepositorio.actualizarFavorito(favoritos)
                AppUIstateParque.ActualizarExito
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun insertarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstateParque = try {
                baseLocalRepositorio.insertarFavorito(favoritos)
                AppUIstateParque.CrearExito

            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun eliminarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstateParque = try {
                baseLocalRepositorio.eliminarFavorito(favoritos)
                AppUIstateParque.EliminarExito
            } catch (e: Exception) {
                AppUIstateParque.Error
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