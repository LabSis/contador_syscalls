package programas;

import com.google.gson.annotations.SerializedName;

public abstract class Ejecutable {

    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    public Ejecutable(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public abstract int getId();

    public abstract Process ejecutar() throws Exception;
}
