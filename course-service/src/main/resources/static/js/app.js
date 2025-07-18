const API = "/users";

document.addEventListener("DOMContentLoaded", () => {
  loadCourses();
  loadUsers();

  document.getElementById("userForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = document.getElementById("userId").value;
    const name = document.getElementById("userName").value;
    const selectedCourses = Array.from(document.getElementById("userCourses").selectedOptions)
      .map(option => ({ id: option.value }));

    const method = id ? "PUT" : "POST";
    const url = id ? `${API}/${id}` : `${API}/`;

    const response = await fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, courses: selectedCourses })
    });

    if (response.ok) {
      showAlert("Usuario guardado exitosamente", "success");
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
          .map(user => `
            <tr>
              <td>${user.id}</td>
              <td>${user.name}</td>
              <td>${user.courses.map(c => c.title).join(", ")}</td>
              <td>
                <button class="btn btn-warning btn-sm" onclick="editUser(${user.id})">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteUser(${user.id})">Eliminar</button>
              </td>
            </tr>
          `).join("")}
      </tbody>
    </table>
  `;
}

let allCourses = [];

async function loadCourses() {
  const res = await fetch("/courses/");
  allCourses = await res.json();

  const courseSelect = document.getElementById("userCourses");
  courseSelect.innerHTML = allCourses.map(c => `<option value="${c.id}">${c.title}</option>`).join("");
}

async function editUser(id) {
  const res = await fetch(`${API}/${id}`);
  const user = await res.json();

  document.getElementById("userId").value = user.id;
  document.getElementById("userName").value = user.name;

  const courseSelect = document.getElementById("userCourses");
  Array.from(courseSelect.options).forEach(option => {
    option.selected = user.courses.some(c => c.id == option.value);
  });
}

async function deleteUser(id) {
  if (confirm("¿Eliminar este usuario?")) {
    await fetch(`${API}/${id}`, { method: "DELETE" });
    showAlert("Usuario eliminado correctamente", "warning");
    loadUsers();
  }
}

function showAlert(message, type = "success") {
  const alertContainer = document.getElementById("alertContainer");
  const wrapper = document.createElement("div");
  wrapper.innerHTML = `
    <div class="alert alert-${type} alert-dismissible fade show" role="alert">
      ${message}
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  `;
  alertContainer.appendChild(wrapper);
}