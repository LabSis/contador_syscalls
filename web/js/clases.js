function Ransomware(id, nombre, descripcion) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
}

function Prueba(ransomware, cantidadDatos, cantidadArchivos, detectorHabilitado,
        deteccionPositiva, tiempoEjecucion, fecha, resultado) {
    this.ransomware = ransomware;
    this.cantidadDatos = cantidadDatos;
    this.cantidadArchivos = cantidadArchivos;
    this.detectorHabilitado = detectorHabilitado;
    this.deteccionPositiva = deteccionPositiva;
    this.tiempoEjecucion = tiempoEjecucion;
    this.fecha = fecha;
    this.resultado = resultado;
}

function Syscall(syscall, cantidad, k, q){
    this.syscall = syscall;
    this.cantidad = cantidad;
    this.k = k;
    this.q = q;
}

function Procesamiento(user, system){
    this.user = user;
    this.system = system;
}

function Disco(iowait, idle){
    this.iowait = iowait;
    this.idle = idle;
}

function Memoria(memused, porcentajeMemused){
    this.memused = memused;
    this.porcentajeMemused = porcentajeMemused;
}

function Resultado(syscalls, procesamiento, disco, memoria){
    /* Array de Syscall */
    this.syscalls = syscalls;
    this.procesamiento = procesamiento;
    this.disco = disco;
    this.memoria = memoria;
}