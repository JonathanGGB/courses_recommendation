const API = "/users";

document.addEventListener("DOMContentLoaded", () => {
  loadUsers();
  loadCourses();

  document.getElementById("userForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("userId").value;
    const name = document.getElementById("userName").value;

    const method = id ? "PUT" : "POST";
    const url = id ? `${API}/${id}` : `${API}/`;

    const response = await fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: name, courses: [] }),
    });

    if (response.ok) {
      alert("Guardado exitosamente");
      document.getElementById("userForm").reset();
      loadUsers();
    }
  });
});

async function loadUsers() {
  const res = await fetch(`${API}/`);
  const users = await res.json();
  const userList = document.getElementById("userList");

  userList.innerHTML = `
        <table class="table table-bordered">
          <thead><tr><th>ID</th><th>Nombre</th><th>Cursos</th><th>Acciones</th></tr></thead>
          <tbody>
            ${users
              .map(
                (user) => `
              <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.courses.map((c) => c.title).join(", ")}</td>
                <td>
                  <button class="btn btn-warning btn-sm" onclick="editUser(${
                    user.id
                  }, '${user.name}')">Editar</button>
                  <button class="btn btn-danger btn-sm" onclick="deleteUser(${
                    user.id
                  })">Eliminar</button>
                  <select onchange="updateCourse(${
                    user.id
                  }, this.value, true)" class="form-select form-select-sm mt-1">
                    <option value="">+ Curso</option>
                    ${window.courseOptions}
                  </select>
                </td>
              </tr>
            `
              )
              .join("")}
          </tbody>
        </table>
      `;
}

async function loadCourses() {
  const res = await fetch("/courses/");
  const courses = await res.json();
  window.courseOptions = courses
    .map((c) => `<option value="${c.id}">${c.title}</option>`)
    .join("");
}

function editUser(id, name) {
  document.getElementById("userId").value = id;
  document.getElementById("userName").value = name;
}

async function deleteUser(id) {
  if (confirm("¿Eliminar este usuario?")) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    loadUsers();
  }
}

async function updateCourse(userId, courseId, add) {
  if (!courseId) return;
  const url = `${API}/${userId}/courses/${courseId}`;
  await fetch(url, { method: add ? "POST" : "DELETE" });
  loadUsers();
}
