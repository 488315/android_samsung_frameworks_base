package com.samsung.android.motionphoto.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes6.dex */
public class MPClientEventHandler extends Handler {
    private static final String TAG = "MPClientEventHandler";
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private int mToken;

    public interface OnErrorListener {
        void onError(int i, int i2, int i3, Object obj);
    }

    public interface OnInfoListener {
        void onInfo(int i, int i2, int i3, Object obj);
    }

    public MPClientEventHandler(Looper looper) {
        super(looper);
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        String str = TAG;
        Log.d(str, String.format("handleMessage: what=%d, arg1=%d, arg2=%d", Integer.valueOf(message.what), Integer.valueOf(message.arg1), Integer.valueOf(message.arg2)));
        Log.d(str, "infolistener: " + this.mOnInfoListener);
        Log.d(str, "errorlistener: " + this.mOnErrorListener);
        int i = message.what;
        if (i == 3001) {
            OnInfoListener onInfoListener = this.mOnInfoListener;
            if (onInfoListener != null) {
                onInfoListener.onInfo(message.arg1, message.arg2, this.mToken, message.obj);
                return;
            }
            return;
        }
        if (i == 3002) {
            OnErrorListener onErrorListener = this.mOnErrorListener;
            if (onErrorListener != null) {
                onErrorListener.onError(message.arg1, message.arg2, this.mToken, message.obj);
                return;
            }
            return;
        }
        Log.e(str, "Unknown message type" + message.what);
    }

    public void setToken(int i) {
        this.mToken = i;
    }
}
