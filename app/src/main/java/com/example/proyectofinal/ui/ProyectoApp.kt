package com.example.proyectofinal.ui

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.proyectofinal.ui.pantallas.PantallaActualizar
import com.example.proyectofinal.ui.pantallas.PantallaInicioEspecie
import com.example.proyectofinal.ui.pantallas.PantallaInicioFavoritos
import com.example.proyectofinal.ui.pantallas.PantallaInicioParque
import com.example.proyectofinal.ui.pantallas.PantallaInsertar


enum class Pantallas(@StringRes val titulo: Int) {
    Parques(titulo = R.string.parques),
    Especies(titulo = R.string.especies),
    Actualizar(titulo = R.string.actualizar),
    Insertar(titulo = R.string.insertar),
    Favoritos(titulo = R.string.favoritos)
}

val menu = arrayOf(
    HamburgerMenu(Icons.Filled.Place, Pantallas.Parques.titulo, Pantallas.Parques.name),
    HamburgerMenu(Icons.Filled.Pets, Pantallas.Especies.titulo, Pantallas.Especies.name),

    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectoApp(
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {

    val pilaRetroceso by navController.currentBackStackEntryAsState()
    val App = LocalContext.current as? Activity
    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Parques.name
    )

    val uiStateParque = viewModel.appUIstateParque
    val uiStateEspecie = viewModel.appUIstateEspecie

    var pantallaElegida by remember { mutableStateOf(Pantallas.Parques) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

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
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(360.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Button(
                    onClick = { App?.finish() },
                    modifier = Modifier
                        .padding(bottom = 150.dp)
                        .width(150.dp)
                ) {
                    Text(
                        text = stringResource(R.string.salir),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        },

        ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    pantallaActual = pantallaActual,
                    drawerState = drawerState,
                    navController = navController,
                    scrollBehavior = scrollBehavior

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
                    pantallaElegida = Pantallas.Parques
                    PantallaInicioParque(
                        appUIState = uiStateParque,
                        onObtenerParques = { viewModel.obtenerParques() },
                        modifier = Modifier
                            .fillMaxSize(),
                        onEliminarParque = { viewModel.eliminarParque(it) },
                        onPulsarActualizar = {
                            viewModel.actualizarObjetoPulsado(it)
                            navController.navigate(Pantallas.Actualizar.name)
                        }
                    )
                }
                composable(route = Pantallas.Especies.name) {
                    pantallaElegida = Pantallas.Especies
                    PantallaInicioEspecie(
                        appUIState = uiStateEspecie,
                        onObtenerEspecie = { viewModel.obtenerEspecies() },
                        modifier = Modifier
                            .fillMaxSize(),
                        onEliminarEspecie = { viewModel.eliminarEspecie(it) },
                        onFavoritoPulsado = { viewModel.insertarFavorito(it) },
                        onEliminarFavorito = { viewModel.eliminarFavorito(it) },
                        onPulsarActualizar = {
                            viewModel.actualizarObjetoPulsado(it)
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
                            navController.popBackStack(pantallaElegida.name, inclusive = false)
                        }
                    )
                }
                composable(route = Pantallas.Insertar.name) {
                    PantallaInsertar(
                        tipo = pantallaElegida.titulo,
                        modifier = Modifier
                            .fillMaxSize(),
                        onObjetoInsertar = {
                            viewModel.insertarObjeto(it)
                            navController.popBackStack(pantallaElegida.name, inclusive = false)
                        }
                    )
                }
                composable(route = Pantallas.Favoritos.name) {
                    pantallaElegida = Pantallas.Favoritos
                    PantallaInicioFavoritos(
                        onObtenerFavoritos = { viewModel.obtenerEspecies() },
                        appUiState = uiStateEspecie,
                        onPulsarActualizar = {
                            viewModel.actualizarObjetoPulsado(it)
                            navController.navigate(Pantallas.Actualizar.name)
                        }
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
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var mostrarMenu by remember { mutableStateOf(false) }
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
        actions = {
            if (pantallaActual == Pantallas.Parques || pantallaActual == Pantallas.Especies) {
                IconButton(onClick = { mostrarMenu = true }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = stringResource(R.string.abrirMenu)
                    )
                }
                DropdownMenu(
                    expanded = mostrarMenu,
                    onDismissRequest = { mostrarMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.actualizarFavs)) },
                        onClick = {
                            mostrarMenu = false
                            navController.navigate(Pantallas.Favoritos.name)
                        }
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}