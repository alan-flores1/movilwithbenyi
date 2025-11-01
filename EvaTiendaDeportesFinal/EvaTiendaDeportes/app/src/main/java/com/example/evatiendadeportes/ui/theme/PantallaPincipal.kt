package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaPrincipal(viewModel: ProductoViewModel, navController: NavController) {
    val usuario = viewModel.usuarioActual.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bienvenido, ${usuario?.nombreUsuario ?: ""} 👋")

            Spacer(Modifier.height(32.dp))

            Button(onClick = { navController.navigate("catalogo") }, modifier = Modifier.fillMaxWidth()) {
                Text("Catálogo de Productos")
            }

            Button(onClick = { navController.navigate("carrito") }, modifier = Modifier.fillMaxWidth()) {
                Text("Carrito de Compras")
            }

            Button(onClick = { navController.navigate("quienes_somos") }, modifier = Modifier.fillMaxWidth()) {
                Text("Quiénes Somos")
            }

            if (viewModel.esAdministrador()) {
                Button(onClick = { navController.navigate("admin") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Panel de Administración")
                }
            }

            Spacer(Modifier.height(32.dp))

            OutlinedButton(
                onClick = {
                    viewModel.cerrarSesion()
                    navController.navigate("inicio") { popUpTo("principal") { inclusive = true } }
                }
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}