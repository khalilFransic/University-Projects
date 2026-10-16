public class Functions {
    String kind ;
    String dataType;
    String parameters;
    String scope;

    Functions(String kind, String dataType, String parameters, String scope) {
        this.kind = kind;
        this.dataType = dataType;
        this.parameters = parameters;
        this.scope = scope;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
