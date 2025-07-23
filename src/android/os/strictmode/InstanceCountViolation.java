package android.os.strictmode;

/* loaded from: classes3.dex */
public class InstanceCountViolation extends Violation {
    private static final StackTraceElement[] FAKE_STACK = {new StackTraceElement("android.os.StrictMode", "setClassInstanceLimit", "StrictMode.java", 1)};
    private final long mInstances;

    public InstanceCountViolation(Class cls, long j, int i) {
        super(cls.toString() + "; instances=" + j + "; limit=" + i);
        setStackTrace(FAKE_STACK);
        this.mInstances = j;
    }

    public long getNumberOfInstances() {
        return this.mInstances;
    }
}
