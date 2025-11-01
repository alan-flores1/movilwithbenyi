package com.example.evatiendadeportes.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.evatiendadeportes.Model.*

data class ItemCarrito(val producto: Producto, var cantidad: Int = 1) {
    val subtotal: Double get() = producto.precio * cantidad
}

class ProductoViewModel : ViewModel() {

    // ----------------- Estados -----------------
    private val _productos = mutableStateListOf<Producto>()
    val productos: List<Producto> get() = _productos

    val carrito = mutableStateListOf<ItemCarrito>()

    private val usuarios = mutableListOf<Usuario>()

    var usuarioActual = mutableStateOf<Usuario?>(null)
    var mensaje = mutableStateOf<String?>(null)

    private var proximoIdProducto = 1

    init {
        // Usuarios base
        usuarios.add(Usuario("admin", "admin123", Rol.ADMINISTRADOR))
        usuarios.add(Usuario("cliente", "cliente123", Rol.CLIENTE))

        // Productos iniciales
        cargarProductosIniciales()
    }

    // ----------------- Autenticación -----------------

    fun iniciarSesion(nombreUsuario: String, contrasena: String): Boolean {
        val usuario = usuarios.find { it.nombreUsuario == nombreUsuario && it.contrasena == contrasena }
        return if (usuario != null) {
            usuarioActual.value = usuario
            mensaje.value = null
            true
        } else {
            mensaje.value = "Usuario o contraseña incorrectos"
            false
        }
    }

    fun registrarUsuario(nombreUsuario: String, contrasena: String, rol: Rol = Rol.CLIENTE): Boolean {
        if (nombreUsuario.isBlank() || contrasena.isBlank()) {
            mensaje.value = "Nombre de usuario y contraseña son obligatorios"
            return false
        }
        if (usuarios.any { it.nombreUsuario.equals(nombreUsuario, ignoreCase = true) }) {
            mensaje.value = "El usuario ya existe"
            return false
        }
        usuarios.add(Usuario(nombreUsuario.trim(), contrasena, rol))
        mensaje.value = "Usuario registrado correctamente"
        return true
    }

    fun cerrarSesion() {
        usuarioActual.value = null
        carrito.clear()
        mensaje.value = "Sesión cerrada"
    }

    fun esAdministrador(): Boolean = usuarioActual.value?.rol == Rol.ADMINISTRADOR

    // ----------------- Productos -----------------

    private fun cargarProductosIniciales() {
        if (_productos.isNotEmpty()) return

        agregarProductoInterno("Ramp Master Skate", 89990.0, "Skate deck profesional 8.0 pulgadas", Categoria.SKATE)
        agregarProductoInterno("Street Cruiser Skate", 75500.0, "Skate urbano resistente al desgaste", Categoria.SKATE)
        agregarProductoInterno("Turbo Roller", 120000.0, "Patines con ruedas de PU", Categoria.ROLLER)
        agregarProductoInterno("Speed Roller", 99000.0, "Patín inline cómodo", Categoria.ROLLER)
        agregarProductoInterno("BMX Freestyle X", 45000.0, "Bicicleta BMX para trucos", Categoria.BMX)
        agregarProductoInterno("BMX Street Pro", 52000.0, "Cuadro reforzado para saltos", Categoria.BMX)
    }

    private fun agregarProductoInterno(nombre: String, precio: Double, descripcion: String, categoria: Categoria) {
        _productos.add(Producto(proximoIdProducto++, nombre, precio, descripcion, categoria))
    }

    fun crearProducto(nombre: String, precio: Double?, descripcion: String, categoria: Categoria?): Boolean {
        if (!esAdministrador()) {
            mensaje.value = "Solo el administrador puede crear productos"
            return false
        }
        if (nombre.isBlank() || descripcion.isBlank() || precio == null || precio <= 0.0 || categoria == null) {
            mensaje.value = "Datos inválidos al crear producto"
            return false
        }
        _productos.add(Producto(proximoIdProducto++, nombre.trim(), precio, descripcion.trim(), categoria))
        mensaje.value = "Producto creado correctamente"
        return true
    }

    fun modificarProducto(id: Int, nombre: String, precio: Double?, descripcion: String, categoria: Categoria?): Boolean {
        if (!esAdministrador()) {
            mensaje.value = "Solo el administrador puede modificar productos"
            return false
        }

        val indice = _productos.indexOfFirst { it.id == id }
        if (indice == -1) {
            mensaje.value = "Producto no encontrado"
            return false
        }

        if (nombre.isBlank() || descripcion.isBlank() || precio == null || precio <= 0.0 || categoria == null) {
            mensaje.value = "Datos inválidos"
            return false
        }

        _productos[indice] = Producto(id, nombre.trim(), precio, descripcion.trim(), categoria)
        mensaje.value = "Producto modificado correctamente"
        return true
    }

    fun eliminarProducto(id: Int): Boolean {
        if (!esAdministrador()) {
            mensaje.value = "Solo el administrador puede eliminar productos"
            return false
        }

        val eliminado = _productos.removeIf { it.id == id }
        mensaje.value = if (eliminado) "Producto eliminado" else "Producto no encontrado"
        return eliminado
    }

    fun productosPorCategoria(categoria: Categoria): List<Producto> =
        _productos.filter { it.categoria == categoria }

    fun buscarProductoPorId(id: Int): Producto? =
        _productos.firstOrNull { it.id == id }

    // ----------------- Carrito -----------------

    fun agregarAlCarrito(idProducto: Int, cantidad: Int = 1) {
        val producto = buscarProductoPorId(idProducto) ?: return
        val existente = carrito.firstOrNull { it.producto.id == idProducto }

        if (existente != null) {
            existente.cantidad += cantidad
            actualizarCarrito(existente)
        } else {
            carrito.add(ItemCarrito(producto, cantidad.coerceAtLeast(1)))
        }
    }

    fun eliminarDelCarrito(idProducto: Int) {
        carrito.removeIf { it.producto.id == idProducto }
    }

    fun cambiarCantidad(idProducto: Int, delta: Int) {
        val item = carrito.firstOrNull { it.producto.id == idProducto } ?: return
        val nuevaCantidad = item.cantidad + delta

        if (nuevaCantidad <= 0) {
            eliminarDelCarrito(idProducto)
        } else {
            item.cantidad = nuevaCantidad
            actualizarCarrito(item)
        }
    }

    private fun actualizarCarrito(item: ItemCarrito) {
        val index = carrito.indexOf(item)
        if (index != -1) carrito[index] = item.copy()
    }

    fun totalCarrito(): Double = carrito.sumOf { it.subtotal }

    fun vaciarCarrito() {
        carrito.clear()
    }

    fun carritoVacio(): Boolean = carrito.isEmpty()
}