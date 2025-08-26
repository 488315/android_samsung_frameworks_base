package android.os.strictmode;

/* loaded from: classes3.dex */
public abstract class Violation extends Throwable {
    private int mHashCode;
    private boolean mHashCodeValid;

    Violation(String str) {
        super(str);
    }

    public int hashCode() {
        synchronized (this) {
            if (this.mHashCodeValid) {
                return this.mHashCode;
            }
            String message = getMessage();
            Throwable cause = getCause();
            int iHashCode = ((((message != null ? message.hashCode() : getClass().hashCode()) * 37) + calcStackTraceHashCode(getStackTrace())) * 37) + (cause != null ? cause.toString().hashCode() : 0);
            this.mHashCodeValid = true;
            this.mHashCode = iHashCode;
            return iHashCode;
        }
    }

    @Override // java.lang.Throwable
    public synchronized Throwable initCause(Throwable th) {
        this.mHashCodeValid = false;
        return super.initCause(th);
    }

    @Override // java.lang.Throwable
    public void setStackTrace(StackTraceElement[] stackTraceElementArr) {
        super.setStackTrace(stackTraceElementArr);
        synchronized (this) {
            this.mHashCodeValid = false;
        }
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        this.mHashCodeValid = false;
        return super.fillInStackTrace();
    }

    private static int calcStackTraceHashCode(StackTraceElement[] stackTraceElementArr) {
        int iHashCode = 17;
        if (stackTraceElementArr != null) {
            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                if (stackTraceElement != null) {
                    iHashCode = (iHashCode * 37) + stackTraceElement.hashCode();
                }
            }
        }
        return iHashCode;
    }
}
