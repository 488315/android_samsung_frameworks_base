package android.util;

public class PerfLog {
    public static final int AMPSS_THRESHOLD = 524288;

    static final native void OLOG(int i, int i2, int i3, int i4, String str);

    static final native void OLOG(int i, int i2, String str);

    static final native void OLOG(int i, int i2, short s, String str);

    static final native void OLOG(int i, int i2, short s, short s2, String str);

    static final native void OLOG(String str);

    public static final void d(String message) {
        OLOG(message);
    }

    public static final void d(int logid, String message) {
        OLOG(0, logid, message);
    }

    public static final void d(int logid, short param1, String message) {
        OLOG(2, logid, param1, message);
    }

    public static final void d(int logid, short param1, short param2, String message) {
        OLOG(2, logid, param1, param2, message);
    }

    public static final void e(int logid, int param1, int param2, String message) {
        OLOG(2, logid, param1, param2, message);
    }
}
