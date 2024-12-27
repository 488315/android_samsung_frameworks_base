package com.android.server.enterprise.plm.common;

import android.os.Handler;
import android.os.Message;

public final class PlmMessage {
    public Object callback;
    public Object obj1;
    public Object obj2;
    public Object obj3;
    public Object obj4;

    public static Message obtain(Handler handler, int i, Object obj) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }

    public static Message obtain(Handler handler, int i, Object obj, Object obj2) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.obj2 = obj2;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }
}
