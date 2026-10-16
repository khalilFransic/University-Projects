public class Variables {
    String kind;
    String type;
    String scope;
    int line;

    public Variables(String kind, String type, String scope, int line) {
        this.kind = kind;
        this.type = type;
        this.scope = scope;
        this.line = line;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setLine(int line) {
        this.line = line;
    }
}
