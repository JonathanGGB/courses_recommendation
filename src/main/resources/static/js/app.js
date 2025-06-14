document.addEventListener("DOMContentLoaded", () => {
  loadUsers();

  document.getElementById("userForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("userName").value;

    const response = await fetch("/users/", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: name, courses: [] }),
    });

    if (response.ok) {
      alert("Usuario creado");
      document.getElementById("userName").value = "";
      loadUsers();
    }
  });
});

async function loadUsers() {
  const res = await fetch("/users/");
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
              <button class="btn btn-danger btn-sm" onclick="deleteUser(${
                user.id
              })">Eliminar</button>
            </td>
          </tr>
        `
          )
          .join("")}
      </tbody>
    </table>
  `;
}

async function deleteUser(id) {
  if (confirm("¿Seguro que deseas eliminar este usuario?")) {
    await fetch(`/users/${id}`, { method: "DELETE" });
    loadUsers();
  }
}
