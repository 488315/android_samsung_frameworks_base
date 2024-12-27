package com.samsung.android.allshare;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sec.android.allshare.iface.CVMessage;

/* loaded from: classes3.dex */
abstract class AllShareEventHandler extends Handler {
    private static final String TAG = "AllShareEventHandler";

    abstract void handleEventMessage(CVMessage cVMessage);

    protected AllShareEventHandler(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        bundle.setClassLoader(getClass().getClassLoader());
        CVMessage cvm = (CVMessage) bundle.getParcelable(CVMessage.EVT_MSG_KEY);
        handleEventMessage(cvm);
    }
}
