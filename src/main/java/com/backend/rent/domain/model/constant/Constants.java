package com.backend.rent.domain.model.constant;

import com.backend.rent.infraestructure.adapter.exception.PropertyException;
import org.springframework.http.HttpStatus;

public class Constants {
    Constants() throws PropertyException {
        throw new PropertyException(HttpStatus.INTERNAL_SERVER_ERROR, "Esta es una clase de utilidad no se puede instanciar");
    }

    public static final String BOGOTA = "Bogota";
    public static final String MEDELLIN = "Medellin";
    public static final String CALI = "Cali";
    public static final String CARTAGENA = "Cartagena";
    public static final Long MINPRICEBOGOTAANDCALI = 2000000L;
    public static final Long MINPRICE = 0L;
    public static final int MINDAYTODELETE = 30;



}
