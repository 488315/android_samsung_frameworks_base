package android.os;

/* loaded from: classes3.dex */
class BadTypeParcelableException extends BadParcelableException {
    BadTypeParcelableException(String msg) {
        super(msg);
    }

    BadTypeParcelableException(Exception cause) {
        super(cause);
    }

    BadTypeParcelableException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
