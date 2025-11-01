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
fun PantallaInicioSesion(
    viewModel: ProductoViewModel,
    navController: NavController
) {
    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val mensaje = viewModel.mensaje.value
    val usuarioActual = viewModel.usuarioActual.value

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Inicio de sesión",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val exito = viewModel.iniciarSesion(nombreUsuario, contrasena)
                    if (exito) {
                        val rol = usuarioActual?.rol
                        if (rol == com.example.evatiendadeportes.Model.Rol.ADMINISTRADOR) {
                            navController.navigate("admin") {
                                popUpTo("inicio_sesion") { inclusive = true }
                            }
                        } else {
                            navController.navigate("principal") {
                                popUpTo("inicio_sesion") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            // Mostrar mensaje de error si existe
            if (!mensaje.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = { navController.navigate("registro") }
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}