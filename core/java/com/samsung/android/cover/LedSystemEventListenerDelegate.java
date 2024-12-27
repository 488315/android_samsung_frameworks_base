package com.samsung.android.cover;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

class LedSystemEventListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_SYSTEM_COVER_EVENT = 0;
    private ListenerDelegateHandler mHandler;
    private CoverManager.LedSystemEventListener mListener;

    LedSystemEventListenerDelegate(
            CoverManager.LedSystemEventListener listener, Handler handler, Context context) {
        this.mListener = listener;
        Looper looper = handler == null ? context.getMainLooper() : handler.getLooper();
        this.mHandler = new ListenerDelegateHandler(looper, this.mListener);
    }

    public Object getListener() {
        return this.mListener;
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchAccept() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchReject() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapLeft() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapMid() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTapRight() throws RemoteException {}

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onSystemCoverEvent(int event, Bundle args) throws RemoteException {
        Message msg = this.mHandler.obtainMessage(0);
        msg.arg1 = event;
        msg.obj = args;
        msg.sendToTarget();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.LedSystemEventListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.LedSystemEventListener listener) {
            super(looper);
            this.mListener = listener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (this.mListener != null) {
                switch (msg.what) {
                    case 0:
                        Bundle args = (Bundle) msg.obj;
                        this.mListener.onSystemCoverEvent(msg.arg1, args);
                        break;
                }
            }
        }
    }
}
