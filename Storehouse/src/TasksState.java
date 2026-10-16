public enum TasksState {
    UNDERIMP("underimp"),
    COMPLET("complet"),
    CANCEL("cancel");

    private final String value;


    TasksState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
