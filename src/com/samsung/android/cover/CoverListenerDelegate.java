package com.samsung.android.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.ICoverManagerCallback;

/* loaded from: classes6.dex */
class CoverListenerDelegate extends ICoverManagerCallback.Stub {
    private static final String TAG = "CoverManager";
    private ListenerDelegateHandler mHandler;
    private final CoverManager.StateListener mListener;

    CoverListenerDelegate(CoverManager.StateListener stateListener, Handler handler, Context context) {
        Looper looper;
        this.mListener = stateListener;
        if (handler == null) {
            looper = context.getMainLooper();
        } else {
            looper = handler.getLooper();
        }
        this.mHandler = new ListenerDelegateHandler(looper, stateListener);
    }

    public CoverManager.StateListener getListener() {
        return this.mListener;
    }

    @Override // com.samsung.android.cover.ICoverManagerCallback
    public void coverCallback(CoverState coverState) throws RemoteException {
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = coverState;
        this.mHandler.sendMessage(obtain);
    }

    @Override // com.samsung.android.cover.ICoverManagerCallback
    public String getListenerInfo() throws RemoteException {
        return this.mListener.toString();
    }

    private static class ListenerDelegateHandler extends Handler {
        private final CoverManager.StateListener mListener;

        ListenerDelegateHandler(Looper looper, CoverManager.StateListener stateListener) {
            super(looper);
            this.mListener = stateListener;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mListener != null) {
                CoverState coverState = (CoverState) message.obj;
                if (coverState != null) {
                    this.mListener.onCoverStateChanged(coverState);
                } else {
                    Log.e(CoverListenerDelegate.TAG, "coverState : null");
                }
            }
        }
    }
}
