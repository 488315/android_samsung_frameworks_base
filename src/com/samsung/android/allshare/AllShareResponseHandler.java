package com.samsung.android.allshare;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.android.allshare.iface.CVMessage;

/* loaded from: classes6.dex */
abstract class AllShareResponseHandler extends Handler {
    private static final String TAG = "AllShareResponseHandler";

    public abstract void handleResponseMessage(CVMessage cVMessage);

    protected AllShareResponseHandler(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        Bundle data = message.getData();
        data.setClassLoader(getClass().getClassLoader());
        handleResponseMessage((CVMessage) data.getParcelable(CVMessage.RES_MSG_KEY));
    }
}
