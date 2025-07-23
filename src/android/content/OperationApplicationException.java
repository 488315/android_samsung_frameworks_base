package android.content;

/* loaded from: classes.dex */
public class OperationApplicationException extends Exception {
    private final int mNumSuccessfulYieldPoints;

    public OperationApplicationException() {
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(String str) {
        super(str);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(String str, Throwable th) {
        super(str, th);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(Throwable th) {
        super(th);
        this.mNumSuccessfulYieldPoints = 0;
    }

    public OperationApplicationException(int i) {
        this.mNumSuccessfulYieldPoints = i;
    }

    public OperationApplicationException(String str, int i) {
        super(str);
        this.mNumSuccessfulYieldPoints = i;
    }

    public int getNumSuccessfulYieldPoints() {
        return this.mNumSuccessfulYieldPoints;
    }
}
