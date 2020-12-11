package com.cql.es;

public enum ESEnum {
    EQ("eq"),
    LIKE("like"),
    GT("gt"),
    LT("lt"),
    ASC("asc"),
    DESC("desc");

    private String type;

    private ESEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
