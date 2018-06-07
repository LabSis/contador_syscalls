package datos;

import java.util.TreeMap;

public abstract class Ransomware {

    private String name;
    private String description;
    private TreeMap<String, String> parameters;

    public Ransomware(String name, String description) {
        this.name = name;
        this.description = description;
        this.parameters = new TreeMap<>();
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

    public abstract void encrypt(String victimDir) throws Exception;

    public abstract void decrypt(String victimDir) throws Exception;
}
