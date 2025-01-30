package com.samsung.android.sdk.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.samsung.android.cover.ICoverStateListenerCallback;
import com.samsung.android.sdk.cover.ScoverManager;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CoverStateListenerDelegate extends ICoverStateListenerCallback.Stub {
    public final ListenerDelegateHandler mHandler;
    public final ScoverManager.CoverStateListener mListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListenerDelegateHandler extends Handler {
        public final WeakReference mListenerRef;

        public ListenerDelegateHandler(Looper looper, ScoverManager.CoverStateListener coverStateListener) {
            super(looper);
            this.mListenerRef = new WeakReference(coverStateListener);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ScoverManager.CoverStateListener coverStateListener = (ScoverManager.CoverStateListener) this.mListenerRef.get();
            if (coverStateListener != null) {
                int i = message.what;
                if (i == 0) {
                    coverStateListener.onCoverSwitchStateChanged(message.arg1 == 1);
                } else {
                    if (i != 1) {
                        return;
                    }
                    coverStateListener.onCoverAttachStateChanged(message.arg1 == 1);
                }
            }
        }
    }

    public CoverStateListenerDelegate(ScoverManager.CoverStateListener coverStateListener, Handler handler, Context context) {
        this.mListener = coverStateListener;
        this.mHandler = new ListenerDelegateHandler(handler == null ? context.getMainLooper() : handler.getLooper(), coverStateListener);
    }

    public final String getListenerInfo() {
        return this.mListener.toString();
    }

    public final void onCoverAttachStateChanged(boolean z) {
        Message.obtain(this.mHandler, 1, z ? 1 : 0, 0).sendToTarget();
    }

    public final void onCoverSwitchStateChanged(boolean z) {
        Message.obtain(this.mHandler, 0, z ? 1 : 0, 0).sendToTarget();
    }
}
