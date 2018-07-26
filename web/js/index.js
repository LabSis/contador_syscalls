(function () {
    $(document).ready(function () {
        // Variables.
        var ransomwares = [];

        // Eventos.
        $("#btn-cifrar").click(function () {
            $("#card-resultados").addClass("d-none");
            var ransomware = $("#slc-ransomware option:selected").val();
            var tamTotalACifrar = $("#slc-cantidad-datos-a-cifrar option:selected").val();
            var cantidadArchivos = $("#slc-cantidad-archivos option:selected").val();
            var dejarArchivosCreados = $("#chk-dejar-archivos-creados").is(":checked");
            $.ajax({
                url: "/GestionRansomware/ControladorRansomware",
                type: "post",
                data: {
                    id_ransomware: ransomware,
                    cantidad_datos_a_cifrar: tamTotalACifrar,
                    cantidad_archivos: cantidadArchivos,
                    dejar_archivos_creados: dejarArchivosCreados
                },
                success: function (r) {
                    var $tblSyscalls = $("#tbl-syscalls tbody");
                    $tblSyscalls.empty();
                    $("#card-resultados").removeClass("d-none");
                    var syscalls = r.syscalls;
                    for (var i = 0; i < syscalls.length; i++) {
                        var $trSyscall = $("<tr></tr>");
                        var $tdNombreSyscall = $("<td></td>");
                        var $tdCantidadSyscall = $("<td></td>");
                        var $tdCantidadYTamanio = $("<td></td>");
                        var $tdCantidadYCantidadTotal = $("<td></td>");

                        var syscall = syscalls[i];
                        $tdNombreSyscall.text(syscall.syscall);
                        $tdCantidadSyscall.text(syscall.cantidad);
                        $tdCantidadYTamanio.text(syscall.k);
                        $tdCantidadYCantidadTotal.text(syscall.q);

                        $trSyscall.append($tdNombreSyscall);
                        $trSyscall.append($tdCantidadSyscall);
                        $trSyscall.append($tdCantidadYTamanio);
                        $trSyscall.append($tdCantidadYCantidadTotal);

                        $tblSyscalls.append($trSyscall);
                    }

                }
            });
        });

        $(document).on("change", "#slc-ransomware", function () {
            var ransomware = $(this).find("option:selected").data("ransomware");
            $("#txa-descripcion").val(ransomware.descripcion);
        });

        // Funciones.
        

        // Setup.
        $(document).ajaxStart(function (args) {
            $(".cargando").show();
        });
        $(document).ajaxStop(function (args) {
            $(".cargando").hide();
        });

        $.ajax({
            url: "/GestionRansomware/ControladorRansomware",
            type: "get",
            success: function (r) {
                var $slcRansomware = $("#slc-ransomware");
                $slcRansomware.empty();
                for (var i = 0; i < r.length; i++) {
                    var ransomwareJson = r[i];
                    var $option = $("<option></option>");
                    $option.text(ransomwareJson.name);
                    $option.val(ransomwareJson.id);
                    var ransomware = new Ransomware(ransomwareJson.id, ransomwareJson.name, ransomwareJson.description);
                    $option.data("ransomware", ransomware);
                    $slcRansomware.append($option);
                    ransomwares.push(ransomware);
                    if (i === 0) {
                        $("#txa-descripcion").val(ransomware.descripcion);
                    }
                }
            }
        });
    });
})();