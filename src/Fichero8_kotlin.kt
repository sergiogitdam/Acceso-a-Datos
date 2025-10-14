import java.io.File
import java.net.URI


/*
Función explorarCarpeta(String ruta): lista contenido.
Función analizarElemento(String ruta): muestra si es archivo (con tamaño) o carpeta (con número de elementos).
Función convertirAURI(String ruta): convierte ruta a URI.
Practica: list(), isFile(), isDirectory(), toURI().
*/

fun main() {
    print("Escribe la ruta de la carpeta que quieras explorar: ")
    val ruta: String? = readLine() ?: "RUTA NULA"
    val ruta_carpeta: File = File(ruta)
    if (!ruta_carpeta.exists()) {
        println("Error, la ruta introducida no existe en el equipo")
    } else {
        explorarCarpeta(ruta_carpeta)
        convertirAURI(ruta_carpeta)
    }
}

fun explorarCarpeta(ruta_carpeta: File) {
    println("Explorando carpeta $ruta_carpeta")
    ruta_carpeta.list().forEach { elemento_carpeta ->
        var file_elemento_carpeta: File = File(ruta_carpeta, elemento_carpeta)
       analizarElemento(file_elemento_carpeta)
    }
}

fun analizarElemento(file_elemento_carpeta: File) {
    if (file_elemento_carpeta.isFile) {
        val size_file_bytes: Long = file_elemento_carpeta.length()
        println("Archivo: $file_elemento_carpeta || tamaño: $size_file_bytes bytes")
    } else if (file_elemento_carpeta.isDirectory) {
        val size_array_numbers = file_elemento_carpeta.list().size
        println("Carpeta: $file_elemento_carpeta || número de elementos: $size_array_numbers")
    }
}

fun convertirAURI(ruta_carpeta: File) {
    println("Intentanto convertir la ruta '" + ruta_carpeta + "' a URI")
    try {
        val uri: URI = ruta_carpeta.toURI()
        println("Ruta convertida a URI correctamente: " + uri.toString())
    } catch (e: Exception) {
        // Mensaje de error en caso de fallo de conversión
        System.err.println("Error al intentar convertir la ruta a URI: " + e.message)
    }
}