package com.android.server.enterprise.plm.common;

import android.os.Handler;
import android.os.Message;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
