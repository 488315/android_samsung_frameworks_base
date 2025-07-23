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
class NfcLedCoverTouchListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
    private static final int MSG_LISTEN_COVER_TOUCH_ACCEPT = 0;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT = 1;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_LEFT = 2;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_MID = 3;
    private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_RIGHT = 4;
    private ListenerDelegateHandler mHandler;
    private CoverManager.NfcLedCoverTouchListener mListener;

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
    public void onSystemCoverEvent(int i, Bundle bundle) throws RemoteException {
    }

    NfcLedCoverTouchListenerDelegate(CoverManager.NfcLedCoverTouchListener nfcLedCoverTouchListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = nfcLedCoverTouchListener;
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
    public void onCoverTouchAccept() throws RemoteException {
        this.mHandler.obtainMessage(0).sendToTarget();
    }

    @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
    public void onCoverTouchReject() throws RemoteException {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.NfcLedCoverTouchListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.NfcLedCoverTouchListener nfcLedCoverTouchListener) {
            super(looper);
            this.mListener = nfcLedCoverTouchListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mListener != null) {
                int i = message.what;
                if (i == 0) {
                    this.mListener.onCoverTouchAccept();
                } else {
                    if (i != 1) {
                        return;
                    }
                    this.mListener.onCoverTouchReject();
                }
            }
        }
    }
}
