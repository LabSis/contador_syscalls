$(document).ready(function () {
    //Variables
    var pruebas = [];
    var $tblPruebas = $("#tbl-pruebas");
    var $tblSyscalls = $("#tbl-syscalls");
    var $modalResultado = $("#modal-resultado");
    
    //Eventos
    $(document).on("click", ".btn-ver-prueba", function(){
        var prueba = $(this).data("prueba");
        console.log(prueba);
        renderizarResultado(prueba);
        $modalResultado.modal();
    });
    
    
    //Funciones
    function obtenerPruebas(){
        $.ajax({
           url: "/GestionRansomware/ControladorResultado",
           type: "GET",
           dataType: "json",
           success: function(pruebasJson){
               var pruebas = [];
               for(var i=0; i < pruebasJson.length; i++){
                   var pruebaJson = pruebasJson[i];
                   var prueba = new Prueba();
                   prueba.cantidadArchivos = pruebaJson.cantidadArchivos;
                   prueba.cantidadDatos = pruebaJson.cantidadDatos;
                   prueba.detectorHabilitado = pruebaJson.detectorHabilitado;
                   prueba.deteccionPositiva = pruebaJson.deteccionPositiva;
                   prueba.fecha = pruebaJson.fechaHora;
                   prueba.tiempoEjecucion = pruebaJson.tiempoEjecucion;
                   
                   var ransomware = new Ransomware(pruebaJson.ransomware.id, pruebaJson.ransomware.name, pruebaJson.ransomware.description);
                   prueba.ransomware = ransomware;
                   
                   var disco = new Disco(pruebaJson.resultado.disco.iowait, pruebaJson.resultado.disco.idle);
                   var memoria = new Memoria(pruebaJson.resultado.memoria.memused, pruebaJson.resultado.memoria.porcentajeMemused);
                   var procesamiento = new Procesamiento(pruebaJson.resultado.procesamiento.user, pruebaJson.resultado.procesamiento.system);
                   var syscalls = [];
                   for(var j=0; j < pruebaJson.resultado.syscalls.length; j++){
                       var syscall = new Syscall();
                       syscall.syscall = pruebaJson.resultado.syscalls[j].syscall;
                       syscall.cantidad = pruebaJson.resultado.syscalls[j].cantidad;
                       syscalls.push(syscall);
                   }
                   
                   var resultado = new Resultado(syscalls, procesamiento, disco, memoria);
                   prueba.resultado = resultado;
                   
                   pruebas.push(prueba);
               }
                renderizarPruebas(pruebas);
           }
        });
    }
    
    function renderizarPruebas(pruebas){
        if(pruebas.length > 0){
            for(var i=0; i < pruebas.length; i++){
                var prueba = pruebas[i];
                
                var $tr = $("<tr></tr>");
                $tr.append($("<td>" + prueba.fecha +"</td>"));
                $tr.append($("<td>" + prueba.ransomware.nombre +"</td>"));
                $tr.append($("<td>" + prueba.cantidadDatos +"</td>"));
                $tr.append($("<td>" + prueba.cantidadArchivos +"</td>"));
                $tr.append($("<td>" + prueba.tiempoEjecucion +"</td>"));
                
                var $tdBtn = $("<td></td>");
                var $btnVer = $("<button>Ver</button>");                
                $btnVer.addClass("btn btn-sm btn-secondary btn-ver-prueba");
                $btnVer.data("prueba", prueba);
                $tdBtn.append($btnVer);
                $tr.append($tdBtn);
                
                
                $tblPruebas.find("tbody").append($tr);
            }
        }else{
            
        }
    }
    
    function renderizarResultado(prueba){
        var syscalls = prueba.resultado.syscalls;
        $tblSyscalls.find("tbody").empty();
        for(var i=0; i < syscalls.length; i++){
            var $tr = $("<tr></tr>");
            $tr.append($("<td>" +syscalls[i].syscall + "</td>"));
            $tr.append($("<td>" +syscalls[i].cantidad + "</td>"));
            
            $tblSyscalls.find("tbody").append($tr);
        }
    }
    //Setup
    obtenerPruebas();
});