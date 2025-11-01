package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evatiendadeportes.Model.Categoria
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaCatalogo(viewModel: ProductoViewModel) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Catálogo de Productos", style = MaterialTheme.typography.headlineSmall)

            Categoria.values().forEach { categoria ->
                Text(categoria.name, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                val productos = viewModel.productosPorCategoria(categoria)
                LazyColumn {
                    items(productos) { producto ->
                        Card(modifier = Modifier.padding(8.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(producto.descripcion)
                                Text("Precio: $${producto.precio}")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { viewModel.agregarAlCarrito(producto.id) }) {
                                    Text("Agregar al carrito")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}