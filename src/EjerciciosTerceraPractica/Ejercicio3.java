package EjerciciosTerceraPractica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Ejercicio3 {
    // Método que crea una configuración por defecto si no se importa ninguna
    public static Properties crearConfiguracionPordDefecto() {
        // Creación de las propiedades por defecto
        Properties propiedadesPorDefecto = new Properties();

        // Configuración de la base de datos
        propiedadesPorDefecto.setProperty("db.host", "localhost");
        propiedadesPorDefecto.setProperty("db.port", "3306");
        propiedadesPorDefecto.setProperty("db.user", "administrador");
        propiedadesPorDefecto.setProperty("db.password", "contraseña");
        propiedadesPorDefecto.setProperty("db.name", "Base_de_datos_inventada"); // Cómo es una simulación me invento una BBDD que no existe

        // Configuración de aplicación
        propiedadesPorDefecto.setProperty("app.titulo", "Aplicación de ejemplo");
        propiedadesPorDefecto.setProperty("app.version", "1.0.0");
        propiedadesPorDefecto.setProperty("app.debug", "false");
        propiedadesPorDefecto.setProperty("app.idioma", "es");

        // Configuración de interfaz
        propiedadesPorDefecto.setProperty("ui.tema", "oscuro");
        propiedadesPorDefecto.setProperty("ui.tamano_fuente", "11");

        return propiedadesPorDefecto;
    }

    // Método que carga la configuración desde un archivo y devuelve las propiedades de éste
    public static Properties cargarConfiguracion(String archivo) throws IOException {
        Properties propiedades = new Properties();
        try(FileInputStream fis = new FileInputStream(archivo)) {
            propiedades.load(fis);
        } catch (FileNotFoundException e) {
            System.err.println("Error, archivo con la configuración no encontrado " + e.getMessage());
            System.out.println("Creando configuración por defecto...");
            // Se crea una configuración por defecto si no se ha encontrado ninguna en el equipo
            propiedades = crearConfiguracionPordDefecto();
        }
        return propiedades;
    }

    // Método que devuelve el valor de una propiedad con valor alfanumérico en base a la clave que ha recibido
    public static String getString(Properties propiedades, String clave, String valorDefecto) {
        return propiedades.getProperty(clave, valorDefecto);
    }

    // Método que devuelve el valor de una propiedad con valor numérico en base a la clave que ha recibido
    public static int getInt(Properties propiedades, String clave, int valorDefecto) {
        String valorString = propiedades.getProperty(clave);
        if (valorString == null || valorString.trim().isEmpty()) {
            System.err.println("Error, clave " + clave + " no encontrada o sin valor. Devolviendo valor por defecto...");
            return valorDefecto;
        }

        try {
            return Integer.parseInt(valorString.trim());
        } catch (NumberFormatException e) {
            System.err.println("Error: El valor de la propiedad " + clave + "[" + valorString + "] no es un número entero. Devolviendo valor por defecto");
            return valorDefecto;
        }
    }

    // Método que devuelve el valor de una propiedad con valor booleano en base a la clave que ha recibido
    public static boolean getBoolean(Properties props, String clave, boolean valorDefecto) {
        String valorString = props.getProperty(clave);
        if (valorString == null || valorString.trim().isEmpty()) {
            System.err.println("Error, clave " + clave + " no encontrada o sin valor. Devolviendo valor por defecto...");
            return valorDefecto;
        }

        // Se devuelve true si el valor del "String" es "true"
        return Boolean.parseBoolean(valorString.trim());
    }

    // Método que guarda la configuración en un archivo y un mensaje de comentario
    public static void guardarConfiguracion(Properties props, String archivo, String comentario) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(archivo)) {
            props.store(fos, comentario);
            System.out.println("Configuración guardada en el archivo: " + archivo);
        }
    }

    // Método que devuelve las propiedades de una configuración pasada cómo parámetro
    public static void mostrarConfiguracion(Properties propiedades) {
        propiedades.list(System.out);
    }

    public static void main(String[] args) {
        String archivoConfiguracion = "app.properties";
        String comentarioConfiguracion = "Configuración de la aplicación";
        Properties propiedades = new Properties();

        try {
            propiedades = cargarConfiguracion(archivoConfiguracion);

            String dbHost = getString(propiedades, "db.host", "localhost");
            int dbPort = getInt(propiedades, "db.port", 3306);
            boolean debug = getBoolean(propiedades, "app.debug", false);

            System.out.println("---Valores leídos por defecto---");
            System.out.println("DB Host: " + dbHost);
            System.out.println("DB Port: " + dbPort);
            System.out.println("Debug Mode: " + debug);

            System.out.println("---Configuración Actual Completa---");
            mostrarConfiguracion(propiedades);

            propiedades.setProperty("app.idioma", "es");
            propiedades.setProperty("ui.tema", "oscuro");
            propiedades.setProperty("db.port", "3307");

            guardarConfiguracion(propiedades, archivoConfiguracion, comentarioConfiguracion);

        } catch (IOException e) {
            System.err.println("Error en el sistema de configuración: " + e.getMessage());
        }
    }
}
