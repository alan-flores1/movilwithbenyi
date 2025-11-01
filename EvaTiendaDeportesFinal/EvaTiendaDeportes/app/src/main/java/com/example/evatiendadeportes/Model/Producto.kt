package com.example.evatiendadeportes.Model

// Clase de datos que representa un producto en la tienda
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val categoria: Categoria
)