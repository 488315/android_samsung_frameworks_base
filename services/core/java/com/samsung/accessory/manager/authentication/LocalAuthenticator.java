package com.samsung.accessory.manager.authentication;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class LocalAuthenticator {
    public String convertMsg(int i) {
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

    public abstract void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract void systemReady();
}
