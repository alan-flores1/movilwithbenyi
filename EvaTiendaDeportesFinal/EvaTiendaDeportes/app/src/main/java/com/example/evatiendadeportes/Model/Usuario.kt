package com.example.evatiendadeportes.Model


// Rol que puede tener un usuario
// Rol del usuario: Administrador o Cliente
enum class Rol { ADMINISTRADOR, CLIENTE }

// Representación de usuario
data class Usuario(
    val nombreUsuario: String,
    val contrasena: String,
    val rol: Rol = Rol.CLIENTE
) val rol: Rol = Rol.CLIENTE