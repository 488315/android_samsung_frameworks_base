package com.android.server.enterprise.plm.common;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public final class HandlerObserver {
    public final WeakReference handler;
    public final int what;

    public HandlerObserver(int i, Handler handler) {
        this.handler = new WeakReference(handler);
        this.what = i;
    }

    public final Handler getHandler() {
        return (Handler) this.handler.get();
    }

    public final void notifyMessage(Object obj, Object obj2, Object obj3) {
        Handler handler = getHandler();
        if (handler == null) {
            return;
        }
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.obj2 = obj2;
        plmMessage.obj3 = obj3;
        Message obtain = Message.obtain(handler);
        obtain.what = this.what;
        obtain.obj = plmMessage;
        handler.sendMessage(obtain);
    }
}
