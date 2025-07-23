package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum NotificationLite {
    COMPLETE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class DisposableNotification implements Serializable {
        private static final long serialVersionUID = -7482590109178395495L;
        final Disposable upstream;

        public DisposableNotification(Disposable disposable) {
            this.upstream = disposable;
        }

        public final String toString() {
            return "NotificationLite.Disposable[" + this.upstream + "]";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ErrorNotification implements Serializable {
        private static final long serialVersionUID = -8759979445933046293L;
        final Throwable e;

        public ErrorNotification(Throwable th) {
            this.e = th;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ErrorNotification)) {
                return false;
            }
            Throwable th = this.e;
            Throwable th2 = ((ErrorNotification) obj).e;
            int i = ObjectHelper.$r8$clinit;
            if (th != th2) {
                return th != null && th.equals(th2);
            }
            return true;
        }

        public final int hashCode() {
            return this.e.hashCode();
        }

        public final String toString() {
            return "NotificationLite.Error[" + this.e + "]";
        }
    }

    public static Object error(Throwable th) {
        return new ErrorNotification(th);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return "NotificationLite.Complete";
    }
}
