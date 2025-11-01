package com.example.evatiendadeportes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evatiendadeportes.ui.*
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = ProductoViewModel()

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(
                    navController = navController,
                    startDestination = "inicio_sesion"
                ) {
                    composable("inicio_sesion") {
                        PantallaInicioSesion(viewModel, navController)
                    }
                    composable("registro") {
                        PantallaRegistro(viewModel, navController)
                    }
                    composable("principal") {
                        PantallaPrincipal(viewModel, navController)
                    }
                    composable("catalogo") {
                        PantallaCatalogo(viewModel)
                    }
                    composable("carrito") {
                        PantallaCarrito(viewModel)
                    }
                    composable("quienes_somos") {
                        PantallaQuienesSomos()
                    }
                    composable("admin") {
                        PantallaAdmin(viewModel)
                    }
                }
            }
        }
    }
}