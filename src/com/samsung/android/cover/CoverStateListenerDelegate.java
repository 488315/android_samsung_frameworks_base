package com.samsung.android.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.ICoverStateListenerCallback;

/* loaded from: classes6.dex */
class CoverStateListenerDelegate extends ICoverStateListenerCallback.Stub {
    private static final int MSG_LISTEN_COVER_ATTACH_STATE_CHANGE = 1;
    private static final int MSG_LISTEN_COVER_SWITCH_STATE_CHANGE = 0;
    public static final int TYPE_COVER_STATE_LISTENER = 2;
    private ListenerDelegateHandler mHandler;
    private final CoverManager.CoverStateListener mListener;

    CoverStateListenerDelegate(CoverManager.CoverStateListener coverStateListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = coverStateListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, coverStateListener);
    }

    public CoverManager.CoverStateListener getListener() {
        return this.mListener;
    }

    @Override // com.samsung.android.cover.ICoverStateListenerCallback
    public void onCoverSwitchStateChanged(boolean z) throws RemoteException {
        Message.obtain(this.mHandler, 0, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.samsung.android.cover.ICoverStateListenerCallback
    public void onCoverAttachStateChanged(boolean z) throws RemoteException {
        Message.obtain(this.mHandler, 1, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.samsung.android.cover.ICoverStateListenerCallback
    public String getListenerInfo() throws RemoteException {
        return this.mListener.toString();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.CoverStateListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.CoverStateListener coverStateListener) {
            super(looper);
            this.mListener = coverStateListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mListener != null) {
                int i = message.what;
                if (i == 0) {
                    this.mListener.onCoverSwitchStateChanged(message.arg1 == 1);
                } else {
                    if (i != 1) {
                        return;
                    }
                    this.mListener.onCoverAttachStateChanged(message.arg1 == 1);
                }
            }
        }
    }
}
