package com.android.server.enterprise.plm.common;

import android.os.Handler;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class HandlerObserver {
    public final WeakReference handler;
    public final int what;

    public HandlerObserver(Handler handler, int i) {
        this.handler = new WeakReference(handler);
        this.what = i;
    }

    public Handler getHandler() {
        return (Handler) this.handler.get();
    }

    public void notifyMessage(Object obj) {
        Handler handler = getHandler();
        if (handler == null) {
            return;
        }
        handler.sendMessage(PlmMessage.obtain(handler, this.what, obj, null));
    }

    public void notifyMessage(Object obj, Object obj2) {
        Handler handler = getHandler();
        if (handler == null) {
            return;
        }
        handler.sendMessage(PlmMessage.obtain(handler, this.what, obj, obj2, null));
    }

    public void notifyMessage(Object obj, Object obj2, Object obj3) {
        Handler handler = getHandler();
        if (handler == null) {
            return;
        }
        handler.sendMessage(PlmMessage.obtain(handler, this.what, obj, obj2, obj3, null));
    }

    public void notifyMessage(Object obj, Object obj2, Object obj3, Object obj4) {
        Handler handler = getHandler();
        if (handler == null) {
            return;
        }
        handler.sendMessage(PlmMessage.obtain(handler, this.what, obj, obj2, obj3, obj4, null));
    }
}
