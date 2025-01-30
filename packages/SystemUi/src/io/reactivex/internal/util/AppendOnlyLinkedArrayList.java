package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.internal.util.NotificationLite;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AppendOnlyLinkedArrayList {
    public final int capacity;
    public final Object[] head;
    public int offset;
    public Object[] tail;

    public AppendOnlyLinkedArrayList(int i) {
        this.capacity = i;
        Object[] objArr = new Object[i + 1];
        this.head = objArr;
        this.tail = objArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x003b, code lost:
    
        r0 = r0[r3];
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038 A[LOOP:1: B:5:0x0006->B:14:0x0038, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0037 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean accept(Observer observer) {
        Object obj;
        boolean z;
        Object[] objArr = this.head;
        while (objArr != null) {
            int i = 0;
            while (true) {
                int i2 = this.capacity;
                if (i < i2 && (obj = objArr[i]) != null) {
                    if (obj == NotificationLite.COMPLETE) {
                        observer.onComplete();
                    } else if (obj instanceof NotificationLite.ErrorNotification) {
                        observer.onError(((NotificationLite.ErrorNotification) obj).f656e);
                    } else {
                        if (obj instanceof NotificationLite.DisposableNotification) {
                            observer.onSubscribe(((NotificationLite.DisposableNotification) obj).upstream);
                        } else {
                            observer.onNext(obj);
                        }
                        z = false;
                        if (!z) {
                            return true;
                        }
                        i++;
                    }
                    z = true;
                    if (!z) {
                    }
                }
            }
        }
        return false;
    }

    public final void add(Object obj) {
        int i = this.offset;
        int i2 = this.capacity;
        if (i == i2) {
            Object[] objArr = new Object[i2 + 1];
            this.tail[i2] = objArr;
            this.tail = objArr;
            i = 0;
        }
        this.tail[i] = obj;
        this.offset = i + 1;
    }
}
