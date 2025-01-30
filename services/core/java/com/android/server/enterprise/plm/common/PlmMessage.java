package com.android.server.enterprise.plm.common;

import android.os.Handler;
import android.os.Message;

/* loaded from: classes2.dex */
public class PlmMessage {
    public Object callback;
    public Object obj1;
    public Object obj2;
    public Object obj3;
    public Object obj4;

    public static Message obtain(Handler handler, int i, Object obj, Object obj2) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.callback = obj2;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }

    public static Message obtain(Handler handler, int i, Object obj, Object obj2, Object obj3) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.obj2 = obj2;
        plmMessage.callback = obj3;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }

    public static Message obtain(Handler handler, int i, Object obj, Object obj2, Object obj3, Object obj4) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.obj2 = obj2;
        plmMessage.obj3 = obj3;
        plmMessage.callback = obj4;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }

    public static Message obtain(Handler handler, int i, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        PlmMessage plmMessage = new PlmMessage();
        plmMessage.obj1 = obj;
        plmMessage.obj2 = obj2;
        plmMessage.obj3 = obj3;
        plmMessage.obj4 = obj4;
        plmMessage.callback = obj5;
        Message obtain = Message.obtain(handler);
        obtain.what = i;
        obtain.obj = plmMessage;
        return obtain;
    }
}
