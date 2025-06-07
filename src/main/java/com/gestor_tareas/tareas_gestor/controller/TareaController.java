package com.gestor_tareas.tareas_gestor.controller;

import com.gestor_tareas.tareas_gestor.DAO.TareaRepository;
import com.gestor_tareas.tareas_gestor.modelo.Tarea;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaRepository tareaRepository;

    public TareaController(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    // GET /tareas → Lista todas las tareas
    @GetMapping
    public List<Tarea> listarTareas() {
        return tareaRepository.obtenerTodas();
    }

    // POST /tareas → Crear nueva tarea
    @PostMapping
    public Tarea agregarTarea(@RequestBody Tarea tarea) {
        return tareaRepository.guardar(tarea);
    }

    @PatchMapping("/{id}/completar")
    public void marcarComoCompletada(@PathVariable int id) {
        tareaRepository.actualizarEstado(id, true);
    }

    @DeleteMapping("/{id}")
    public void eliminarTarea(@PathVariable int id) {
        tareaRepository.eliminarPorId(id);
    }

}
