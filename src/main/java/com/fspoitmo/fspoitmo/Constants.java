package com.fspoitmo.fspoitmo;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    private Constants() {}

    @Value("${application.constants.serviceName}")
    public static String serviceName = "ifspo";


    public static String schedSyncDateConst = "IFSPO";
}
