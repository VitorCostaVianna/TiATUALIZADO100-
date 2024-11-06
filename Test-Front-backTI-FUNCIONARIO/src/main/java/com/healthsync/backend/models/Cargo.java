package com.healthsync.backend.models;

public enum Cargo {
    GERENTE , RECEPCIONISTA , MEDICO;

    public static Cargo parse(String cargo){
        cargo = cargo.toUpperCase();
        switch (cargo){
            case "GERENTE":
                return GERENTE;
            case "RECEPCIONISTA":
                return RECEPCIONISTA;
            case "MEDICO":
                return MEDICO;
            default:
                return null;
        }

    }
}
