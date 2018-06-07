(function() {
    $(document).ready(function() {
        $("#btn-cifrar").click(function() {
            var ransomware = $("#slc-ransomware option:selected").val();
            var tamTotalACifrar = $("#slc-cantidad-datos-a-cifrar option:selected").val();
            var cantidadArchivos = $("#slc-cantidad-archivos option:selected").val();
            $.ajax({
                url: "cifrar.jsp",
                data: {
                    ransomware: ransomware,
                    tam: tamTotalACifrar,
                    cantidadArchivos: cantidadArchivos
                }
            });
        });
    });
})();