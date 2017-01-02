package com.mapapp.bos.mapapp.gson;

/**
 * Created by Bos on 2017-01-02.
 */

public class Result {

    private static final String OKAY_STATUS = "OK";

    private String status;

    public String getStatus( ) {
        return this.status;
    }

    public boolean isOkay( ) {
        return OKAY_STATUS.equals( this.getStatus( ) );
    }

}