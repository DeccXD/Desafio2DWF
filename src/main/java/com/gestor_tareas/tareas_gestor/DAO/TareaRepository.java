package com.gestor_tareas.tareas_gestor.DAO;

import com.gestor_tareas.tareas_gestor.modelo.Tarea;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TareaRepository {

    private final JdbcTemplate jdbcTemplate;

    public TareaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Listar todas las tareas
    public List<Tarea> obtenerTodas() {
        String sql = "SELECT * FROM tareas";
        return jdbcTemplate.query(sql, mapRowToTarea());
    }

    public int actualizarEstado(int id, boolean completada) {
        String sql = "UPDATE tareas SET completada = ? WHERE id = ?";
        return jdbcTemplate.update(sql, completada, id);
    }
    public void eliminarPorId(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    // Guardar tarea y devolver la tarea con ID generado
    public Tarea guardar(Tarea tarea) {
        String sql = "INSERT INTO tareas (nombre, descripcion, completada) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tarea.getNombre());
            ps.setString(2, tarea.getDescripcion());
            ps.setBoolean(3, tarea.isCompletada());
            return ps;
        }, keyHolder);

        // Obtener el ID generado
        Number key = keyHolder.getKey();
        if (key != null) {
            tarea.setId(key.intValue());
        }

        return tarea;
    }

    // Mapper
    private RowMapper<Tarea> mapRowToTarea() {
        return (rs, rowNum) -> new Tarea(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getBoolean("completada")
        );
    }
}
