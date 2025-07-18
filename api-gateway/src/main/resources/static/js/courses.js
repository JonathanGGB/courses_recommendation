const COURSE_API = "/courses";

document.addEventListener("DOMContentLoaded", () => {
  loadCourses();

  document.getElementById("courseForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("courseId").value;
    const title = document.getElementById("courseTitle").value.trim();
    const topic = document.getElementById("courseTopic").value.trim();

    if (!title || !topic) {
      showAlert("Todos los campos son obligatorios.", "danger");
      return;
    }

    const method = id ? "PUT" : "POST";
    const url = id ? `${COURSE_API}/${id}` : `${COURSE_API}/`;

    const response = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ title, topic })
    });

    if (response.ok) {
      showAlert("Curso guardado correctamente.", "success");
      document.getElementById("courseForm").reset();
      loadCourses();
    } else {
      showAlert("Error al guardar el curso.", "danger");
    }
  });
});

async function loadCourses() {
  const res = await fetch(`${COURSE_API}/`);
  const courses = await res.json();
  const list = document.getElementById("courseList");

  list.innerHTML = `
    <table class="table table-striped">
      <thead>
        <tr><th>ID</th><th>Título</th><th>Tema</th><th>Acciones</th></tr>
      </thead>
      <tbody>
        ${courses
          .map(c => `
            <tr>
              <td>${c.id}</td>
              <td>${c.title}</td>
              <td>${c.topic}</td>
              <td>
                <button class="btn btn-sm btn-warning" onclick="editCourse(${c.id}, '${c.title}', '${c.topic}')">Editar</button>
                <button class="btn btn-sm btn-danger" onclick="deleteCourse(${c.id})">Eliminar</button>
              </td>
            </tr>
          `).join("")}
      </tbody>
    </table>
  `;
}

function editCourse(id, title, topic) {
  document.getElementById("courseId").value = id;
  document.getElementById("courseTitle").value = title;
  document.getElementById("courseTopic").value = topic;
}

async function deleteCourse(id) {
  if (confirm("¿Eliminar este curso?")) {
    const res = await fetch(`${COURSE_API}/${id}`, { method: "DELETE" });
    if (res.ok) {
      showAlert("Curso eliminado correctamente.", "warning");
      loadCourses();
    } else {
      showAlert("Error al eliminar el curso.", "danger");
    }
  }
}

function showAlert(message, type = "info") {
  const box = document.getElementById("alertBox");
  box.innerHTML = `
    <div class="alert alert-${type} alert-dismissible fade show" role="alert">
      ${message}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>`;
}