package com.gestor_tareas.tareas_gestor.modelo;

public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean completada;


    public Tarea() {}


    public Tarea(int id, String nombre, String descripcion, boolean completada) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    public Tarea(int id, String nombre, String descripcion, boolean completada, String fechaCreacion, String prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

}
