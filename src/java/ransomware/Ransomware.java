package ransomware;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.TreeMap;

public abstract class Ransomware {
    
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    private transient TreeMap<String, String> parameters;

    public Ransomware(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parameters = new TreeMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TreeMap<String, String> getParametros() {
        return parameters;
    }

    public void setParameters(TreeMap<String, String> parametros) {
        this.parameters = parametros;
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    /**
     * 
     * @param directorioVictima directorio víctima. Se asume que el directorio existe y está sanitizado.
     * @return 
     * @throws Exception 
     */
    public abstract Process encrypt(String directorioVictima) throws Exception;

    public abstract Process decrypt(String directorioVictima) throws Exception;
}
