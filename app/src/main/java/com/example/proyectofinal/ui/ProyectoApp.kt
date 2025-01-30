package com.example.proyectofinal.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.datos.HamburgerMenu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.proyectofinal.ui.pantallas.PantallaActualizar
import com.example.proyectofinal.ui.pantallas.PantallaInicioEspecie
import com.example.proyectofinal.ui.pantallas.PantallaInicioParque
import com.example.proyectofinal.ui.pantallas.PantallaInsertar


enum class Pantallas(@StringRes val titulo: Int) {
    Parques(titulo = R.string.parques),
    Especies(titulo = R.string.especies),
    Actualizar(titulo = R.string.actualizar),
    Insertar(titulo = R.string.insertar)
}

val menu = arrayOf(
    HamburgerMenu(Icons.Filled.Place, Pantallas.Parques.titulo, Pantallas.Parques.name),
    HamburgerMenu(Icons.Filled.Pets, Pantallas.Especies.titulo, Pantallas.Especies.name),

    )

@Composable
fun ProyectoApp(
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Parques.name
    )

    val uiStateParque = viewModel.appUIstateParque
    val uiStateEspecie = viewModel.appUIstateEspecie

    var pantallaElegida by remember { mutableStateOf(Pantallas.Especies.name) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    menu = menu,
                    pantallaActual = pantallaActual
                ) { ruta ->
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navController.navigate(ruta)
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    pantallaActual = pantallaActual,
                    drawerState = drawerState
                )
            }, floatingActionButton = {
                if (pantallaActual.titulo == R.string.parques || pantallaActual.titulo == R.string.especies) {
                    FloatingActionButton(
                        onClick = { navController.navigate(route = Pantallas.Insertar.name) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.insertar),


                            )
                    }

                }
            }

        ) { innerPadding ->


            NavHost(
                navController = navController,
                startDestination = Pantallas.Parques.name,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(route = Pantallas.Parques.name) {
                    PantallaInicioParque(
                        appUIState = uiStateParque,
                        onObtenerParques = { viewModel.obtenerParques() },
                        modifier = Modifier
                            .fillMaxSize(),
                        onPulsarActualizar = {
                            viewModel.actualizarObjetoPulsado(it)
                            pantallaElegida = Pantallas.Parques.name
                            navController.navigate(Pantallas.Actualizar.name)
                        }
                    )
                }
                composable(route = Pantallas.Especies.name) {
                    PantallaInicioEspecie(
                        appUIState = uiStateEspecie,
                        onObtenerEspecie = { viewModel.obtenerEspecies() },
                        modifier = Modifier
                            .fillMaxSize(),
                        onPulsarActualizar = {
                            viewModel.actualizarObjetoPulsado(it)
                            pantallaElegida= Pantallas.Especies.name
                            navController.navigate(Pantallas.Actualizar.name)
                        }
                    )

                }
                composable(route = Pantallas.Actualizar.name) {
                    PantallaActualizar(
                        modifier = Modifier
                            .fillMaxSize(),
                        objeto = viewModel.objetoPulsado,
                        onObjetoActualizado = {
                            viewModel.actualizarObjeto(it)
                            navController.popBackStack(pantallaElegida, inclusive = false)
                        }
                    )
                }
                composable(route = Pantallas.Insertar.name) {
                    PantallaInsertar(
                        modifier = Modifier
                            .fillMaxSize(),
                        onObjetoInsertar = {}
                    )
                }


            }
        }
    }
}

@Composable
private fun DrawerContent(
    menu: Array<HamburgerMenu>,
    pantallaActual: Pantallas,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menu.forEach {
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = it.titulo)) },
                icon = { Icon(imageVector = it.icono, contentDescription = null) },
                selected = it.titulo == pantallaActual.titulo,
                onClick = {
                    onMenuClick(it.ruta)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    drawerState: DrawerState?,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (drawerState != null) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(id = R.string.atras)
                    )
                }
            }
        },
        modifier = modifier
    )
}