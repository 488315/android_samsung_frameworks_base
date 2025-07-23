package com.samsung.android.sdk.routines.v3.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class HandlerProvider {
    public final HashMap b = new HashMap();
    public final ConcurrentHashMap c = new ConcurrentHashMap();

    public final Object getWithTimeout(String str) {
        Object obj = this.b.get(str);
        if (obj == null) {
            obj = null;
        }
        if (obj != null) {
            return obj;
        }
        android.util.Log.i("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("getWithTimeout: tag=", str, ", wait 3000 ms until initialized..."));
        Object computeIfAbsent = this.c.computeIfAbsent(str, new HandlerProvider$$ExternalSyntheticLambda0());
        synchronized (computeIfAbsent) {
            try {
                try {
                    computeIfAbsent.wait(3000L);
                } catch (InterruptedException e) {
                    android.util.Log.e("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), "waitWithTimeout: tag=" + str + ", InterruptedException", e);
                }
            } catch (IllegalMonitorStateException e2) {
                android.util.Log.e("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), "waitWithTimeout: tag=" + str + ", IllegalMonitorStateException", e2);
            }
        }
        android.util.Log.i("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("getWithTimeout: tag=", str, ", notified or timeout"));
        Object obj2 = this.b.get(str);
        if (obj2 != null) {
            return obj2;
        }
        return null;
    }

    public final void set(Object obj, String str) {
        this.b.put(str, obj);
        Object obj2 = this.c.get(str);
        if (obj2 != null) {
            android.util.Log.i("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), ContentInViewNode$Request$$ExternalSyntheticOutline0.m("notify: tag=", str, ", notifyAll"));
            synchronized (obj2) {
                try {
                    obj2.notifyAll();
                } catch (IllegalMonitorStateException e) {
                    android.util.Log.e("Routine@Sdk[3.1.9]: ".concat("HandlerProvider"), "notify: tag=" + str + ", IllegalMonitorStateException", e);
                }
            }
        }
    }
}
