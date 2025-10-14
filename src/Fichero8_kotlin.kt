import java.io.File


/*
Función explorarCarpeta(String ruta): lista contenido.
Función analizarElemento(String ruta): muestra si es archivo (con tamaño) o carpeta (con número de elementos).
Función convertirAURI(String ruta): convierte ruta a URI.
Practica: list(), isFile(), isDirectory(), toURI().
*/

fun main() {
    println("Escribe la ruta de la carpeta que quieras explorar")
    val ruta: String? = readLine() ?: "RUTA NULA"
    val ruta_carpeta: File = File(ruta)
    if (!ruta_carpeta.exists()) {
        println("Error, la ruta introducida no existe en el equipo")
    } else {
        explorarCarpeta(ruta_carpeta)
    }
}

fun explorarCarpeta(ruta_carpeta: File) {
    println("Explorar carpeta")
}

fun analizarElemento(ruta_carpeta: File) {

}

fun convertirAURI(ruta_carpeta: File) {

}