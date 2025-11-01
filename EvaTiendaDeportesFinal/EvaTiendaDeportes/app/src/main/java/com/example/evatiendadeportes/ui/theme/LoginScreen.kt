package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaLogin(viewModel: ProductoViewModel, navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val ok = viewModel.iniciarSesion(usuario, contrasena)
                    if (ok) {
                        if (viewModel.esAdministrador()) {
                            navController.navigate("admin") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            navController.navigate("principal") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            viewModel.mensaje.value?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}