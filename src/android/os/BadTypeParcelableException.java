package android.os;

/* loaded from: classes3.dex */
class BadTypeParcelableException extends BadParcelableException {
    BadTypeParcelableException(String str) {
        super(str);
    }

    BadTypeParcelableException(Exception exc) {
        super(exc);
    }

    BadTypeParcelableException(String str, Throwable th) {
        super(str, th);
    }
}
