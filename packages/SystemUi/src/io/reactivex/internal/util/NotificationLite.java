package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public enum NotificationLite {
    COMPLETE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DisposableNotification implements Serializable {
        private static final long serialVersionUID = -7482590109178395495L;
        final Disposable upstream;

        public DisposableNotification(Disposable disposable) {
            this.upstream = disposable;
        }

        public final String toString() {
            return "NotificationLite.Disposable[" + this.upstream + "]";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class ErrorNotification implements Serializable {
        private static final long serialVersionUID = -8759979445933046293L;

        /* renamed from: e */
        final Throwable f656e;

        public ErrorNotification(Throwable th) {
            this.f656e = th;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ErrorNotification)) {
                return false;
            }
            Throwable th = this.f656e;
            Throwable th2 = ((ErrorNotification) obj).f656e;
            int i = ObjectHelper.$r8$clinit;
            return th == th2 || (th != null && th.equals(th2));
        }

        public final int hashCode() {
            return this.f656e.hashCode();
        }

        public final String toString() {
            return "NotificationLite.Error[" + this.f656e + "]";
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
