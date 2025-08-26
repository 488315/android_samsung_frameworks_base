package com.samsung.android.cover;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.INfcLedCoverTouchListenerCallback;

/* loaded from: classes6.dex */
class LedSystemEventListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_SYSTEM_COVER_EVENT = 0;
    private ListenerDelegateHandler mHandler;
    private CoverManager.LedSystemEventListener mListener;

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapLeft() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapMid() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapRight() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchAccept() throws RemoteException {
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchReject() throws RemoteException {
    }

    LedSystemEventListenerDelegate(CoverManager.LedSystemEventListener ledSystemEventListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = ledSystemEventListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public Object getListener() {
        return this.mListener;
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
        Message messageObtainMessage = this.mHandler.obtainMessage(0);
        messageObtainMessage.arg1 = i;
        messageObtainMessage.obj = bundle;
        messageObtainMessage.sendToTarget();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.LedSystemEventListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.LedSystemEventListener ledSystemEventListener) {
            super(looper);
            this.mListener = ledSystemEventListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mListener == null || message.what != 0) {
                return;
            }
            this.mListener.onSystemCoverEvent(message.arg1, (Bundle) message.obj);
        }
    }
}
