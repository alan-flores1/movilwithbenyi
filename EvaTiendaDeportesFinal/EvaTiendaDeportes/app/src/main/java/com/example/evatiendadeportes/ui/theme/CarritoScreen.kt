package com.example.evatiendadeportes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evatiendadeportes.viewmodel.ProductoViewModel

@Composable
fun PantallaCarrito(viewModel: ProductoViewModel) {
    val carrito = viewModel.carrito
    val total by remember { derivedStateOf { viewModel.totalCarrito() } }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Carrito de Compras", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))

            if (carrito.isEmpty()) {
                Text("Tu carrito está vacío.")
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(carrito) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text("Precio: $${item.producto.precio}")
                                Spacer(Modifier.height(4.dp))
                                Text("Subtotal: $${item.subtotal}")

                                Spacer(Modifier.height(8.dp))
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row {
                                        Button(
                                            onClick = { viewModel.cambiarCantidad(item.producto.id, -1) },
                                            modifier = Modifier.size(36.dp)
                                        ) { Text("-") }
                                        Spacer(Modifier.width(8.dp))
                                        Text("${item.cantidad}")
                                        Spacer(Modifier.width(8.dp))
                                        Button(
                                            onClick = { viewModel.cambiarCantidad(item.producto.id, 1) },
                                            modifier = Modifier.size(36.dp)
                                        ) { Text("+") }
                                    }
                                    Button(
                                        onClick = { viewModel.eliminarDelCarrito(item.producto.id) }
                                    ) {
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.vaciarCarrito() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vaciar Carrito")
                }
            }
        }
    }
}