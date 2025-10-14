/*
Función organizarBiblioteca(): crea carpeta de categoría y archivo catalogo.txt.
Función verificarLibro(): verifica si existe un libro; si no, pregunta si se crea.
Practica: exists(), mkdir(), createNewFile(), funciones separadas.
 */

import java.io.File

fun main() {
    val CARPETA_BIBLIOTECA: File = File("C:\\Users\\AlumnoAfternoon\\Documents\\Pruebas-Kotlin\\Biblioteca")
    if (!CARPETA_BIBLIOTECA.isDirectory && !CARPETA_BIBLIOTECA.exists()) {
        println("Error, la ruta  no existe")
    } else {
        println("***ORGANIZADOR DE BIBLIOTECA***")
        organizarBiblioteca(CARPETA_BIBLIOTECA)
    }
}

//Función organizarBiblioteca(): crea carpeta de categoría y archivo catalogo.txt.
fun organizarBiblioteca(CARPETA_BIBLIOTECA: File) {
    print("Escribe la categoría que quieras añadir a la biblioteca: ")
    val categoria: String? = readLine() ?: "CATEGORIA EJEMPLO"
    val ruta_categoria: File = File(CARPETA_BIBLIOTECA, categoria)
    val ruta_catalogo: File = File(ruta_categoria, "catalogo.txt")
    if (!ruta_categoria.exists()) {
        if (ruta_categoria.mkdirs()) {
            println("Carpeta $categoria creada: ${ruta_categoria.absolutePath}")
        }
        else {
            println("Error al crear la carpeta $categoria")
        }
    } else {
        println("La categoría ya existía: ${ruta_categoria.absolutePath}")
    }
    if (!ruta_catalogo.exists()) {
        if (ruta_catalogo.createNewFile()) {
            println("Archivo catálogo.txt creado: ${ruta_catalogo.absolutePath}")
        }
        else {
            println("Error al crear catalogo.txt")
        }
    } else {
        println("El archivo catálogo.txt ya esxistía: ${ruta_catalogo.absolutePath}")
    }
    verificarLibro(ruta_categoria)
}
//Función verificarLibro(): verifica si existe un libro; si no, pregunta si se crea.
fun verificarLibro(ruta_categoria: File) {
    print("Escribe el nombre del libro que quieres agregar con la extensión incluida: ")
    val libro: String? = readLine() ?: "LIBRO EJEMPLO"
    val ruta_libro: File = File(ruta_categoria, libro)
    if (!ruta_libro.exists()) {
        do {
            print("Libro inexistente en esta categoría, desea añadirlo(si/no)?: ")
            val op: String? = readLine() ?: "nulo"
            if (op.equals("si")) {
                if (ruta_libro.createNewFile()) {
                    println("Archivo $libro creado: ${ruta_libro.absolutePath}")
                }
                else {
                    println("Error al crear catalogo.txt")
                }
            } else if (op.equals("no")) {
                println("Entendido, no se añadirá el libro: $libro")
            } else {
                println("Por favor, responde si o no")
            }
        } while (!op.equals("no") && !op.equals("si"))
    } else {
        println("El archivo $libro ya esxistía: ${ruta_libro.absolutePath}")
    }
}
