package com.samsung.accessory.manager.authentication;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class LocalAuthenticator {
    public static String convertMsg(int i) {
        switch (i) {
            case 1:
                return "MSG_INITIALIZE";
            case 2:
                return "MSG_AUTH_START";
            case 3:
                return "MSG_AUTH_RESPONSE";
            case 4:
                return "MSG_AUTH_STOP";
            case 5:
                return "MSG_AUTH_TIMEOUT";
            case 6:
                return "MSG_AUTH_FORCE_UNVERIFY";
            case 7:
                return "MSG_SET_AUTH_HALL";
            default:
                return null;
        }
    }

    public abstract void dump(PrintWriter printWriter);

    public abstract void systemReady();
}
