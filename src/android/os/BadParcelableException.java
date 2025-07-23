package android.os;

import android.util.AndroidRuntimeException;

/* loaded from: classes3.dex */
public class BadParcelableException extends AndroidRuntimeException {
    public BadParcelableException(String str) {
        super(str);
    }

    public BadParcelableException(Exception exc) {
        super(exc);
    }

    public BadParcelableException(String str, Throwable th) {
        super(str, th);
    }
}
