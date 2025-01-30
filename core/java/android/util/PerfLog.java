package android.util;

/* loaded from: classes4.dex */
public class PerfLog {
    public static final int AMPSS_THRESHOLD = 524288;

    static final native void OLOG(int i, int i2, int i3, int i4, String str);

    static final native void OLOG(int i, int i2, String str);

    static final native void OLOG(int i, int i2, short s, String str);

    static final native void OLOG(int i, int i2, short s, short s2, String str);

    static final native void OLOG(String str);

    /* renamed from: d */
    public static final void m110d(String message) {
        OLOG(message);
    }

    /* renamed from: d */
    public static final void m107d(int logid, String message) {
        OLOG(0, logid, message);
    }

    /* renamed from: d */
    public static final void m108d(int logid, short param1, String message) {
        OLOG(2, logid, param1, message);
    }

    /* renamed from: d */
    public static final void m109d(int logid, short param1, short param2, String message) {
        OLOG(2, logid, param1, param2, message);
    }

    /* renamed from: e */
    public static final void m111e(int logid, int param1, int param2, String message) {
        OLOG(2, logid, param1, param2, message);
    }
}
