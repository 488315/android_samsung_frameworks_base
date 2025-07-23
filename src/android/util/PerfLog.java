package android.util;

/* loaded from: classes4.dex */
public class PerfLog {
    public static final int AMPSS_THRESHOLD = 524288;

    static final native void OLOG(int i, int i2, int i3, int i4, String str);

    static final native void OLOG(int i, int i2, String str);

    static final native void OLOG(int i, int i2, short s, String str);

    static final native void OLOG(int i, int i2, short s, short s2, String str);

    static final native void OLOG(String str);

    public static final void d(String str) {
        OLOG(str);
    }

    public static final void d(int i, String str) {
        OLOG(0, i, str);
    }

    public static final void d(int i, short s, String str) {
        OLOG(2, i, s, str);
    }

    public static final void d(int i, short s, short s2, String str) {
        OLOG(2, i, s, s2, str);
    }

    public static final void e(int i, int i2, int i3, String str) {
        OLOG(2, i, i2, i3, str);
    }
}
