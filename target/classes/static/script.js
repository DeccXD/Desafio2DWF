document.addEventListener("DOMContentLoaded", () => {
    const lista = document.getElementById("listaTareas");
    const form = document.getElementById("formTarea");

    const cargarTareas = async () => {
        const res = await fetch("/tareas");
        const tareas = await res.json();

        lista.innerHTML = "";

        tareas.forEach(tarea => {
            const li = document.createElement("li");

            const estado = tarea.completada ? "✅" : "❌";
            const estadoClase = tarea.completada ? "tarea-completada" : "tarea-pendiente";

            // Estructura principal de la tarea
            li.innerHTML = `
                <div class="tarea-info">
                    <strong>${tarea.nombre}</strong>
                    <span>${tarea.descripcion}</span>
                </div>
                <div class="tarea-acciones">
                    <span class="${estadoClase}">${estado}</span>
                </div>
            `;

            const acciones = li.querySelector(".tarea-acciones");

            // Botón para marcar como completada
            if (!tarea.completada) {
                const btnCompletar = document.createElement("button");
                btnCompletar.textContent = "✔ Completar";
                btnCompletar.classList.add("btn-completar");
                btnCompletar.addEventListener("click", async () => {
                    await fetch(`/tareas/${tarea.id}/completar`, {
                        method: "PATCH"
                    });
                    cargarTareas();
                });
                acciones.appendChild(btnCompletar);
            }

            // Botón para eliminar
            const btnEliminar = document.createElement("button");
            btnEliminar.textContent = "🗑 Eliminar";
            btnEliminar.classList.add("btn-eliminar");
            btnEliminar.addEventListener("click", async () => {
                if (confirm("¿Estás seguro de eliminar esta tarea?")) {
                    await fetch(`/tareas/${tarea.id}`, {
                        method: "DELETE"
                    });
                    cargarTareas();
                }
            });
            acciones.appendChild(btnEliminar);

            lista.appendChild(li);
        });
    };

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nuevaTarea = {
            nombre: document.getElementById("nombre").value,
            descripcion: document.getElementById("descripcion").value
        };

        await fetch("/tareas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(nuevaTarea)
        });

        form.reset();
        cargarTareas();
    });

    cargarTareas();
});
