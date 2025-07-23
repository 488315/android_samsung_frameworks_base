package android.content.pm;

/* loaded from: classes.dex */
public class SigningInfoException extends Exception {
    private final int mCode;

    public SigningInfoException(int i, String str, Throwable th) {
        super(str, th);
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }
}
