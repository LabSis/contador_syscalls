(function () {
    $(document).ready(function () {
        // Variables.
        var ransomwares = [];

        // Eventos.
        $("#btn-cifrar").click(function () {
            var ransomware = $("#slc-ransomware option:selected").val();
            var tamTotalACifrar = $("#slc-cantidad-datos-a-cifrar option:selected").val();
            var cantidadArchivos = $("#slc-cantidad-archivos option:selected").val();
            $.ajax({
                url: "/GestionRansomware/ControladorRansomware",
                type: "post",
                data: {
                    ransomware: ransomware,
                    tam: tamTotalACifrar,
                    cantidadArchivos: cantidadArchivos
                }
            });
        });

        $(document).on("change", "#slc-ransomware", function () {
            var ransomware = $(this).find("option:selected").data("ransomware");
            $("#txa-descripcion").val(ransomware.descripcion);
        });

        // Funciones.
        function Ransomware(id, nombre, descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }

        // Setup.
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