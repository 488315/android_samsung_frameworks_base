package com.samsung.android.wifitrackerlib;

import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class SemWifiUtils {
    public static final /* synthetic */ int $r8$clinit = 0;

    public class Mutable {
        public Object value;

        public Mutable(Object obj) {
            this.value = obj;
        }
    }

    static {
        Pattern.compile("^$|^[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*(\\.[a-zA-Z0-9]+(\\-[a-zA-Z0-9]+)*)*$");
        Pattern.compile("^$|^[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*(,[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*(\\.[a-zA-Z0-9*]+(\\-[a-zA-Z0-9*]+)*)*)*$");
    }

    public static int calculateSignalLevel(int i) {
        if (i <= -89) {
            return 0;
        }
        if (i > -89 && i <= -83) {
            return 1;
        }
        if (i <= -83 || i > -75) {
            return (i <= -75 || i > -64) ? 4 : 3;
        }
        return 2;
    }
}
