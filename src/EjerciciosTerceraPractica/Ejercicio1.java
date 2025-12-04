package EjerciciosTerceraPractica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio1 {
    // Clase producto
    static class Producto {
        private int id;
        private String nombre;
        private double precio;
        private int stock;

        // Constructor, getters y setters
        public Producto(int id, String nombre, double precio, int stock) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public int getStock() {
            return stock;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        // Método "toString()" para mostrar los detalles del producto
        @Override
        public String toString() {
            return "ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio + ", Stock: " + stock;
        }
    }

    // --- Métodos para trabajar sobre productos
    // Método que recibe la ruta a un archivo y un producto, escribe la información del producto en el archivo al que apunta la ruta
    public static void escribirProducto(String rutaIntroducida, Producto producto) throws IOException {
        File archivo = new File(rutaIntroducida);
        // Comprobamos si el rutaIntroducida existe en el equipo
        if(archivo.isFile() && archivo.exists()) {
            System.out.println("El ruta introducida existe en el equipo, escribiendo sobre éste...");
            // Se crea el canal para poder escribir sobre el archivo
            try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo, false))) {
                // Se utilizan los "getters()" de "Producto" para sacar los datos del producto y poder escribirlos en el archivo
                dos.writeInt(producto.getId());
                dos.writeUTF(producto.getNombre());
                dos.writeDouble(producto.getPrecio());
                dos.writeInt(producto.getStock());
            }
            // Aquí no hace falta el catch porque ya lo hemos declarado en la función
        } else {
            System.err.println("Error, la ruta introducida no existe o la ruta no apunta a un archivo: " + archivo.getAbsolutePath());
        }
    }

    // Método que recibe la ruta a un archivo, lee el contenido de éste guardándolo en una lista y posteriormente la devuelve
    public static List<Producto> leerProductos(String rutaIntroducida) throws IOException {
        File archivo = new File(rutaIntroducida);
        List<Producto> listaProductos = new ArrayList<>();
        // Comprobamos si el rutaIntroducida existe en el equipo
        if(archivo.isFile() && archivo.exists()) {
            System.out.println("El ruta introducida existe en el equipo, leyendo sobre éste...");
            // Se crea el canal para poder leer el archivo
            try(DataInputStream dis = new DataInputStream(new FileInputStream(archivo))) {
                // Creamos un bucle infinito hasta que no haya nada más que leer, el "try" se encargará de salir del bucle cuándo esto ocurra
                while(true) {
                    // Guardamos los datos del producto en variables
                    int id = dis.readInt();
                    String nombre = dis.readUTF();
                    double precio = dis.readDouble();
                    int stock = dis.readInt();

                    // Creamos el objeto gracias al constructor de "Producto()" y las variables que acabamos de crear
                    Producto producto = new Producto(id, nombre, precio, stock);
                    // Añadimos el producto a la lista
                    listaProductos.add(producto);
                }
            } catch(EOFException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            System.err.println("Error, la ruta introducida no existe o la ruta no apunta a un archivo: " + archivo.getAbsolutePath());
        }

        return listaProductos;
    }

    // Método que agrega un producto al final de un archivo, recibe ambos cómo parámetros
    public static void agregarProducto(String ruta, Producto producto) throws IOException {
        File archivo = new File(ruta);
        // Comprobamos si el rutaIntroducida existe en el equipo
        if(archivo.isFile() && archivo.exists()) {
            System.out.println("El ruta introducida existe en el equipo, agregando producto...");
            // Se crea el canal para poder escribir sobre el archivo
            try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(archivo, false))) {
                // Se utilizan los "getters()" de "Producto" para sacar los datos del producto y poder escribirlos en el archivo
                dos.writeInt(producto.getId());
                dos.writeUTF(producto.getNombre());
                dos.writeDouble(producto.getPrecio());
                dos.writeInt(producto.getStock());
            }
            // Aquí no hace falta el catch porque ya lo hemos declarado en la función

            // Imprimir por consola el producto agregado
            System.out.println("Producto agregado: " + producto.toString());
        } else {
            System.err.println("Error, la ruta introducida no existe o la ruta no apunta a un archivo");
        }
    }

    // --- Método principal, aquí se comprobará el funcionamiento de los métodos
    public static void main(String[] args) {
        String archivo = "inventarioDeProductos.dat";
        try {
            Producto p1 = new Producto(1, "Laptop", 999.99, 10);
            Producto p2 = new Producto(2, "Mouse", 19.99, 50);
            escribirProducto(archivo, p1);

            agregarProducto(archivo, p2);

            System.out.println("\n--- Contenido del archivo ---"); // Separador visual
            List<Producto> productos = leerProductos(archivo);

            for (Producto producto : productos) {
                System.out.println(producto);
            }
        } catch(IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
