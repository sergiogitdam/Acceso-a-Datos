package EjerciciosTerceraPractica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Clase que contiene los atributos y constructores del objeto "Usuario"
class Usuario {
    private int id;
    private String nombre;
    private String email;
    private int edad;

    // Constructor
    public Usuario(int id, String nombre, String email, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Email: " + email + ", Edad: " + edad;
    }
}
// Clase principal que contiene los métodos para interactuar con la BDD
public class Ejercicio2 {

    // Método de creación de tablas: permite crear tablas en la BDD recibiendo una conexión a dicha BDD cómo parámetro
    public static void crearTabla(Connection conn) throws SQLException {
        // Sentencia sql para crear la tabla
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "edad INT" +
                ")";

        // Pasar el comando a MySQL
        try(Statement stmt = conn.createStatement()) {
            // Ejecutar la sentencia
            stmt.executeUpdate(sql);
            System.out.println("Tabla creada correctamente");
        }
    }

    // Método de inserción de usuarios: permite insertar usaurios a una BDD recibiendo la conexión a ésta y los datos del usuario a insertar cómo parámetros
    // Devuelve el ID del usuario insertado
    public static int insertarUsuario(Connection conn, String nombre, String email, int edad) throws SQLException {
        // Sentencia SQL para insertar usuario
        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";

        // Pasar el comando a MySQL
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Establecer los valores de los parámetros
            pstmt.setString(1, nombre);
            pstmt.setString(2, email);
            pstmt.setInt(3, edad);

            // Ejecutar la sentencia
            int filasAfectadas = pstmt.executeUpdate();

            // Se devuelve el ID del usuario insertado
            if (filasAfectadas > 0) {
                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            // Por si fallase la inserción se devuelve -1, aunque el código nunca llegaría a devolverlo por la excepción, es para que no se queje Java
            return -1;
        }
    }

    // Método para buscar usuarios por nombre: recibe una conexión a la BDD y un nombre para posteriormente buscarlo en la BDD y mostrar su info
    public static List<Usuario> buscarPorNombre(Connection conn, String nombre) throws SQLException {
        //Crear una lista de usuarios dónde meteremos los usuarios que tengan el nombre introducido
        List<Usuario> listaUsuarios = new ArrayList<>();
        // Sentencia SQL para buscar usuarios por nombre
        String sql = "SELECT id, nombre, email, edad FROM usuarios WHERE nombre LIKE ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Establecer el valor del parámetro (el nombre del usuario)
            pstmt.setString(1, "%" + nombre + "%");

            // Se ejecuta la consulta y se obtiene su resultado
            try(ResultSet rs = pstmt.executeQuery()) {
                // Bucle para recorrer los resultados
                while(rs.next()) {
                    // Guardar los resultados de la fila actual
                    int id = rs.getInt("id");
                    String nombreUsuario = rs.getString("nombre");
                    String email = rs.getString("email");
                    int edad = rs.getInt("edad");

                    // Crear un usuario con los datos obtenidos y añadirlo a la lista de usuarios
                    Usuario usuario = new Usuario(id, nombreUsuario, email, edad);
                    listaUsuarios.add(usuario);
                }
            }
        }
        System.out.println("Usuarios encontrados con el nombre " + nombre + ": " + listaUsuarios.size());
        return listaUsuarios;
    }

    // Método para actualizar el mail de un usuario: recibe la conexión a la BDD, el identificador del usuario y el nuevo mail de éste
    public static boolean actualizarMail(Connection conn, int id, String nuevoMail) throws SQLException {
        // Sentencia SQL para actualizar el mail de un usuario
        String sql = "UPDATE usuarios SET email = ? WHERE id = ?";

        // Ejecutar a sentencia SQL
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Establecer los valores de los parámetros
            pstmt.setString(1, nuevoMail);
            pstmt.setInt(2, id);

            // Ejecutar la sentencia
            int filasAfectadas = pstmt.executeUpdate();

            // Si el número de filas afectadas es mayor que 0 es que se ha actualizado correctamente y se devuelve true, si no es así se devuelve false
            if (filasAfectadas > 0) {
                System.out.println("Mail actualizado correctamente");
                return true;
            } else {
                System.err.println("No se ha podido actualizar el mail, ID no encontrado");
                return false;
            }
        }
    }

    // Método para eliminar usuario: recibe la conexión a la BDD y el identificador del usuario a eliminar
    public static boolean eliminarUsuario(Connection conn, int id) throws SQLException {
        // Sentencia SQL para eliminar un usuario
        String sql = "DELETE FROM usuarios WHERE id = ?";

        // Ejecutar la sentencia SQL
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Establecer el valor del parámetro
            pstmt.setInt(1, id);

            // Ejecutar la sentencia
            int filasAfectadas = pstmt.executeUpdate();

            // Si el número de filas afectadas es mayor que 0 es que se ha eliminado correctamente y se devuelve true, si no es así se devuelve false
            if (filasAfectadas > 0) {
                System.out.println("Usuario eliminado correctamente");
                return true;
            } else {
                System.err.println("Error al eliminar el usuario, ID no encontrado");
                return false;
            }
        }
    }
    // Método principal: aquí se creará la conexión de la BDD y se probarán los métodos para interactuar con la BDD
    public static void main(String[] args) {
        // Parámetros de conexión a la BD de MySQL
        String url = "jdbc:mysql://localhost:3306/AccesoADatosT3E2";
        String usuario = "root";
        String password = "mysqlmysql";

        // Probar con dos usuarios (valores por defecto para que Java no se queje)
        int idUsuario1 = -1;
        int idUsuario2 = -1;

        // Conexión a la BDD
        try(Connection conn = DriverManager.getConnection(url, usuario, password)) {
            System.out.println("Conexión establecida con MySQL");
            crearTabla(conn); // Crear tabla vacía

            // Insertar usuarios
            System.out.println("--- Insertando usuarios ---");
            idUsuario1 = insertarUsuario(conn, "Jose Josete", "josejoete@gmail.com", 50);
            idUsuario2 = insertarUsuario(conn, "Pepe pepito", "pepepepito@gmail.com", 20);

            // Buscar usuarios
            System.out.println("--- Buscando usuarios ---");
            System.out.println("Buscando al usuario 'Miguel'");
            List<Usuario> listaMigueles = buscarPorNombre(conn, "Miguel");
            for (Usuario juan: listaMigueles) {
                System.out.println(juan);
            }
            System.out.println("Buscando al usuario 'Pepito");
            List<Usuario> listaPepes= buscarPorNombre(conn, "Pepe");
            for (Usuario pepito: listaPepes) {
                System.out.println(pepito);
            }

            // Actualizar email usuario
            System.out.println("--- Actualizando mail de usuarios ---");
            // Utilizo la lista de "Pepes" para sacar un usuario
            if(!listaPepes.isEmpty()) {
                Usuario pepito = listaPepes.get(0);
                actualizarMail(conn, pepito.getId(), "pepitopepenuevomail@gmail.com");
                pepito.setEmail("pepitopepenuevomail@gmail.com");
                // Mostrar nuevo mail de Pepe
                System.out.println("Nuevo mail de Pepe: " + pepito.getEmail());
            } else {
                System.err.println("Error, no se encontró un usuario en la lista");
            }

            // Eliminar usaurios
            eliminarUsuario(conn, idUsuario1);
            List<Usuario> listaJosetes = buscarPorNombre(conn, "Jose");
            if(listaJosetes.isEmpty()) {
                System.out.println("Usuario con ID " + idUsuario1 + " eliminado correctamente");
            } else {
                System.err.println("Error al eliminar el usuario con ID " + idUsuario1);
            }
        } catch(SQLException e) {
            System.err.println("Error de conexión SQL: " + e.getMessage());
        }
    }
}
