package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evatiendadeportes.Model.Categoria
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaAdmin(viewModel: ProductoViewModel) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf<Categoria?>(null) }

    val productos = viewModel.productos

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Panel de Administración", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
            DropdownMenuDemo(selected = categoria, onSelect = { categoria = it })

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.crearProducto(nombre, precio.toDoubleOrNull(), descripcion, categoria)
                    nombre = ""; precio = ""; descripcion = ""; categoria = null
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Producto")
            }

            viewModel.mensaje.value?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))
            Text("Lista de Productos", style = MaterialTheme.typography.titleLarge)

            LazyColumn {
                items(productos) { p ->
                    Card(modifier = Modifier.padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("${p.nombre} - $${p.precio}")
                            Text(p.descripcion)
                            Text("Categoría: ${p.categoria}")
                            Button(onClick = { viewModel.eliminarProducto(p.id) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuDemo(selected: Categoria?, onSelect: (Categoria) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) {
            Text(selected?.name ?: "Seleccionar categoría")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Categoria.values().forEach {
                DropdownMenuItem(text = { Text(it.name) }, onClick = {
                    onSelect(it)
                    expanded = false
                })
            }
        }
    }
}