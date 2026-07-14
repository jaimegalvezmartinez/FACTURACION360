/*
 * clientes.js
 * -----------
 * Rellena la tabla de clientes con datos que pide al backend por fetch y
 * gestiona la paginación (de 10 en 10).
 *
 * Arquitectura (por qué está organizado así):
 *  - UNA función de datos (cargarClientes) que pide una página al API.
 *  - Funciones de pintado (pintarFilas, pintarPaginacion) que SOLO tocan el DOM
 *    a partir de lo recibido. Separar "pedir" de "pintar" hace el código fácil
 *    de seguir y de depurar.
 *  - El único estado que guardamos entre clics es la página actual; el backend
 *    ya nos manda el resto de metadatos (total de páginas, si hay anterior...).
 *  - Usamos el <template> del HTML como molde: clonarlo es más rápido y seguro
 *    que construir <tr> concatenando cadenas.
 */

// Ruta relativa: al servir el HTML desde el propio Spring Boot, "/api/clientes"
// apunta al mismo origen y no hay problemas de CORS.
const API_CLIENTES = "/api/clientes";
const TAMANO_PAGINA = 10;

// Guardamos una sola vez las referencias del DOM que usamos repetidamente.
const cuerpoTabla = document.getElementById("tabla-clientes");
const plantillaFila = document.getElementById("fila-cliente-template");
const btnRecientes = document.getElementById("btn-recientes");
const btnAntiguos = document.getElementById("btn-antiguos");
const infoPagina = document.getElementById("info-pagina");

// Único estado que necesitamos mantener entre pulsaciones de botón.
let paginaActual = 0;

/**
 * Pide una página de clientes al backend y dispara el repintado.
 * @param {number} pagina índice de página (0 = los 10 más recientes)
 */
async function cargarClientes(pagina) {
    try {
        const respuesta = await fetch(
            `${API_CLIENTES}?pagina=${pagina}&tamano=${TAMANO_PAGINA}`
        );

        // fetch NO lanza error con códigos 4xx/5xx: hay que comprobarlo a mano.
        if (!respuesta.ok) {
            throw new Error(`El servidor respondió ${respuesta.status}`);
        }

        const datos = await respuesta.json();

        paginaActual = datos.paginaActual;
        pintarFilas(datos.contenido);
        pintarPaginacion(datos);
    } catch (error) {
        mostrarError(error);
    }
}

/**
 * Vacía el <tbody> y lo rellena clonando el <template> por cada cliente.
 * @param {Array<Object>} clientes DTOs {id, nombre, cif, email, telefono}
 */
function pintarFilas(clientes) {
    cuerpoTabla.replaceChildren(); // limpia la tabla sin usar innerHTML

    if (clientes.length === 0) {
        mostrarMensaje("No hay clientes que mostrar.");
        return;
    }

    for (const cliente of clientes) {
        // Clonamos el contenido del template (un <tr> completo con sus <td>).
        const fila = plantillaFila.content.cloneNode(true);

        // textContent (y no innerHTML): el navegador escapa el texto, de modo
        // que un nombre con < o & no puede romper la página ni inyectar HTML.
        fila.querySelector(".cliente-nombre").textContent = cliente.nombre;
        fila.querySelector(".cliente-cif").textContent = cliente.cif;
        fila.querySelector(".cliente-email").textContent = cliente.email;
        fila.querySelector(".cliente-telefono").textContent = cliente.telefono;

        // Guardamos el id en la fila por si los botones de acción lo necesitan.
        fila.querySelector("tr").dataset.clienteId = cliente.id;

        cuerpoTabla.appendChild(fila);
    }
}

/**
 * Ajusta los botones y el texto de estado según los metadatos de la página.
 * @param {Object} datos PaginaClientesDTO recibido del backend
 */
function pintarPaginacion(datos) {
    // "Más recientes" lleva a la página anterior (índice menor) y "Más antiguos"
    // a la siguiente. El backend nos dice si cada una existe.
    btnRecientes.disabled = !datos.hayAnterior;
    btnAntiguos.disabled = !datos.haySiguiente;

    const numeroHumano = datos.paginaActual + 1; // las personas cuentan desde 1
    infoPagina.textContent =
        `Página ${numeroHumano} de ${datos.totalPaginas} · ${datos.totalElementos} clientes`;
}

/** Pinta una fila que ocupa toda la tabla con un mensaje informativo. */
function mostrarMensaje(texto) {
    cuerpoTabla.replaceChildren();
    const fila = document.createElement("tr");
    const celda = document.createElement("td");
    celda.colSpan = 5; // la tabla tiene 5 columnas
    celda.className = "text-center text-muted py-4";
    celda.textContent = texto;
    fila.appendChild(celda);
    cuerpoTabla.appendChild(fila);
}

/** Muestra el error al usuario y lo deja en consola para depurar. */
function mostrarError(error) {
    console.error("No se pudieron cargar los clientes:", error);
    mostrarMensaje("No se pudieron cargar los clientes. Inténtalo de nuevo.");
    btnRecientes.disabled = true;
    btnAntiguos.disabled = true;
}

// --- Enlazado de eventos ---
btnRecientes.addEventListener("click", () => cargarClientes(paginaActual - 1));
btnAntiguos.addEventListener("click", () => cargarClientes(paginaActual + 1));

// Primera carga: los 10 clientes más recientes.
cargarClientes(0);
