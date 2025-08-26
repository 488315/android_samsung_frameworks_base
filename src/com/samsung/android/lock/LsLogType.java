package com.samsung.android.lock;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.samsung.android.lock.LsLogType, still in use, count: 1, list:
  (r0v0 com.samsung.android.lock.LsLogType) from 0x0073: FILLED_NEW_ARRAY 
  (r0v0 com.samsung.android.lock.LsLogType)
  (r1v1 com.samsung.android.lock.LsLogType)
  (r2v2 com.samsung.android.lock.LsLogType)
  (r3v3 com.samsung.android.lock.LsLogType)
  (r4v4 com.samsung.android.lock.LsLogType)
  (r5v5 com.samsung.android.lock.LsLogType)
  (r6v5 com.samsung.android.lock.LsLogType)
 A[WRAPPED] (LINE:19) elemType: com.samsung.android.lock.LsLogType
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* loaded from: classes6.dex */
public final class LsLogType {
    SUMMARY(LsConstants.TAG_SUMMARY, 515, 0),
    ENROLL(LsConstants.TAG_ENROLL, 259, 0),
    VERIFY(LsConstants.TAG_VERIFY, 259, 30),
    KEYERR(LsConstants.TAG_KEYERR, 259, 30),
    RESTORE(LsConstants.TAG_RESTORE, 259, 30),
    EVENTS(LsConstants.TAG_EVENTS, 257, 10),
    UNKNOWN(LsConstants.TAG_UNKNOWN, 259, 0);

    public static final LsLogType[] LIST = {new LsLogType(LsConstants.TAG_SUMMARY, 515, 0), new LsLogType(LsConstants.TAG_ENROLL, 259, 0), new LsLogType(LsConstants.TAG_VERIFY, 259, 30), new LsLogType(LsConstants.TAG_KEYERR, 259, 30), new LsLogType(LsConstants.TAG_RESTORE, 259, 30), new LsLogType(LsConstants.TAG_EVENTS, 257, 10), new LsLogType(LsConstants.TAG_UNKNOWN, 259, 0)};
    private final String mErrorCode;
    private final long mMaxSize;
    private final int mProperty;

    public static LsLogType valueOf(String str) {
        return (LsLogType) Enum.valueOf(LsLogType.class, str);
    }

    public static LsLogType[] values() {
        return (LsLogType[]) $VALUES.clone();
    }

    static {
    }

    private LsLogType(String str, int i, int i2) {
        this.mErrorCode = str;
        this.mProperty = i;
        this.mMaxSize = i2 * 1024;
    }

    String getErrorCode() {
        return this.mErrorCode;
    }

    boolean containsProperty(int i) {
        return (this.mProperty & i) != 0;
    }

    long getMaxSize() {
        return this.mMaxSize;
    }
}
