package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.internal.util.NotificationLite;

/* loaded from: classes4.dex */
public class AppendOnlyLinkedArrayList {
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

    public final boolean accept(Observer observer) {
        int i;
        Object obj;
        Object[] objArr = this.head;
        while (true) {
            int i2 = 0;
            if (objArr == null) {
                return false;
            }
            while (true) {
                i = this.capacity;
                if (i2 >= i || (obj = objArr[i2]) == null) {
                    break;
                }
                if (obj == NotificationLite.COMPLETE) {
                    observer.onComplete();
                    return true;
                }
                if (obj instanceof NotificationLite.ErrorNotification) {
                    observer.onError(((NotificationLite.ErrorNotification) obj).e);
                    return true;
                }
                if (obj instanceof NotificationLite.DisposableNotification) {
                    observer.onSubscribe(((NotificationLite.DisposableNotification) obj).upstream);
                } else {
                    observer.onNext(obj);
                }
                i2++;
            }
            objArr = objArr[i];
        }
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
