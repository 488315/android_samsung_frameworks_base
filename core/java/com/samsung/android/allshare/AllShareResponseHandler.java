package com.samsung.android.allshare;

import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import com.sec.android.allshare.iface.CVMessage;

/* loaded from: classes5.dex */
abstract class AllShareResponseHandler extends Handler {
    private static final String TAG = "AllShareResponseHandler";

    public abstract void handleResponseMessage(CVMessage cVMessage);

    protected AllShareResponseHandler(Looper looper) {
        super(looper);
    }

    @Override // android.p009os.Handler
    public void handleMessage(Message msg) {
        Bundle bundle = msg.getData();
        bundle.setClassLoader(getClass().getClassLoader());
        CVMessage cvm = (CVMessage) bundle.getParcelable(CVMessage.RES_MSG_KEY);
        handleResponseMessage(cvm);
    }
}
