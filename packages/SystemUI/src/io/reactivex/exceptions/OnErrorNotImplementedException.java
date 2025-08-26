package io.reactivex.exceptions;

import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public final class OnErrorNotImplementedException extends RuntimeException {
    private static final long serialVersionUID = -6298857009889503852L;

    public OnErrorNotImplementedException(String str, Throwable th) {
        super(str, th == null ? new NullPointerException() : th);
    }

    public OnErrorNotImplementedException(Throwable th) {
        this(RowInflaterTask$$ExternalSyntheticOutline0.m("The exception was not handled due to missing onError handler in the subscribe() method call. Further reading: https://github.com/ReactiveX/RxJava/wiki/Error-Handling | ", th), th);
    }
}
