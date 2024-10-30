async function registrarUsuarios() {
    let datos = {};

    // Obteniendo los valores de los campos del formulario
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.telefono = document.getElementById('txtTelefono').value;
    datos.password = document.getElementById('txtPassword').value;

    console.log("Teléfono:", datos.telefono);

    let repeatPassword = document.getElementById('txtRepetirPassword').value;

    // Validación de la contraseña
    if (repeatPassword != datos.password) {
        alert('Las contraseñas no coinciden');
        return;
    }

    // Haciendo la solicitud POST para registrar el usuario
    const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos) // Enviando los datos en formato JSON
    });

    // Manejar la respuesta de la API
    if (request.ok) {
        alert('Usuario registrado con éxito');
        window.location.href = 'login.html'; // Redirigir después del registro exitoso
    } else {
        alert('Error al registrar el usuario');
    }
	
}
