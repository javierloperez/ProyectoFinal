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

sealed interface AppUIstateParque {
    data class ObtenerExitoParques(val parques: List<Parques>) : AppUIstateParque



    object CrearExito : AppUIstateParque
    object Cargando : AppUIstateParque
    object Error : AppUIstateParque
    object ActualizarExito : AppUIstateParque
    object EliminarExito : AppUIstateParque

}

sealed interface AppUIstateEspecie {


    data class ObtenerExitoEspecies(
        val especies: List<Especies>,
        val listaFavoritos: List<Favoritos>
    ) : AppUIstateEspecie


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


    fun <T> actualizarObjetoPulsado(objeto: T) {
        objetoPulsado = objeto
    }

    init {
        obtenerParques()
        obtenerEspecies()
    }

    fun actualizarObjeto(objeto: Any) {

        viewModelScope.launch {
            try {
                when (objeto) {
                    is Parques -> {
                        jsonRepositorio.actualizarParque(objeto.id, objeto)
                        appUIstateParque = AppUIstateParque.ActualizarExito
                    }

                    is Especies -> {
                        jsonRepositorio.actualizarEspecie(objeto.id, objeto)
                        appUIstateEspecie = AppUIstateEspecie.ActualizarExito
                    }
                    is Favoritos ->{
                        baseLocalRepositorio.actualizarFavorito(objeto)
                        appUIstateEspecie = AppUIstateEspecie.ActualizarExito
                    }
                }

            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun insertarObjeto(objeto: Any) {

        viewModelScope.launch {
            try {
                when (objeto) {
                    is Parques -> {
                        jsonRepositorio.insertarParque(objeto)
                        appUIstateParque = AppUIstateParque.CrearExito
                    }

                    is Especies -> {
                        jsonRepositorio.insertarEspecie(objeto)
                        appUIstateEspecie = AppUIstateEspecie.CrearExito
                    }


                }

            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }


    fun obtenerParques() {
        viewModelScope.launch {
            appUIstateParque = AppUIstateParque.Cargando
            appUIstateParque = try {
                val lista = jsonRepositorio.obtenerParques()
                AppUIstateParque.ObtenerExitoParques(lista)
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }

    fun eliminarParque(id: Int) {
        viewModelScope.launch {
            appUIstateParque = try {
                jsonRepositorio.eliminarParque(id)
                AppUIstateParque.EliminarExito
            } catch (e: Exception) {
                AppUIstateParque.Error
            }
        }
    }


    fun obtenerEspecies() {
        viewModelScope.launch {
            appUIstateEspecie = AppUIstateEspecie.Cargando
            try {
                val lista = jsonRepositorio.obtenerEspecies()

                obtenerFavoritos { listaFavoritos ->
                    appUIstateEspecie = AppUIstateEspecie.ObtenerExitoEspecies(
                        especies = lista,
                        listaFavoritos = listaFavoritos
                    )
                }
            } catch (e: Exception) {
                AppUIstateEspecie.Error
            }
        }
    }

    fun eliminarEspecie(id: Int) {
        viewModelScope.launch {
            appUIstateEspecie = try {
                jsonRepositorio.eliminarEspecie(id)
                AppUIstateEspecie.EliminarExito
            } catch (e: Exception) {
                AppUIstateEspecie.Error
            }
        }
    }

    fun obtenerFavoritos(onListaObtenida: (List<Favoritos>) -> Unit) {
        viewModelScope.launch {
            try {
                val lista = baseLocalRepositorio.obtenerFavoritos()
                onListaObtenida(lista)
            } catch (e: Exception) {
                AppUIstateEspecie.Error
            }
        }
    }

    fun insertarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstateEspecie = try {
                baseLocalRepositorio.insertarFavorito(favoritos)
                AppUIstateEspecie.CrearExito
            } catch (e: Exception) {
                AppUIstateEspecie.Error
            }
        }
    }

    fun eliminarFavorito(favoritos: Favoritos) {
        viewModelScope.launch {
            appUIstateEspecie = try {
                baseLocalRepositorio.eliminarFavorito(favoritos)
                AppUIstateEspecie.EliminarExito
            } catch (e: Exception) {
                AppUIstateEspecie.Error
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