package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evatiendadeportes.Model.Rol
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaRegistro(
    viewModel: ProductoViewModel,
    navController: NavController
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registro de nuevo usuario", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
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

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val exito = viewModel.registrarUsuario(usuario, contrasena, Rol.CLIENTE)
                    if (exito) {
                        viewModel.mensaje.value = "Usuario registrado correctamente"
                        navController.navigate("inicio_sesion")
                    } else {
                        mensajeError = viewModel.mensaje.value ?: "Error al registrar"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }

            if (mensajeError.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                Text(mensajeError, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("login") }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}