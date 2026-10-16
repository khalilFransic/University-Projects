public enum State {
    ACTIVE("active"),
    STOP("stop"),
    MANITENANCE("maintenance");

    private final String value;


    State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
