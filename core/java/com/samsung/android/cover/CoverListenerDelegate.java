package com.samsung.android.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes5.dex */
class CoverListenerDelegate extends ICoverManagerCallback.Stub {
  private static final String TAG = "CoverManager";
  private ListenerDelegateHandler mHandler;
  private final CoverManager.StateListener mListener;

  CoverListenerDelegate(CoverManager.StateListener listener, Handler handler, Context context) {
    this.mListener = listener;
    Looper looper = handler == null ? context.getMainLooper() : handler.getLooper();
    this.mHandler = new ListenerDelegateHandler(looper, listener);
  }

  public CoverManager.StateListener getListener() {
    return this.mListener;
  }

  @Override // com.samsung.android.cover.ICoverManagerCallback
  public void coverCallback(CoverState state) throws RemoteException {
    Message msg = Message.obtain();
    msg.what = 0;
    msg.obj = state;
    this.mHandler.sendMessage(msg);
  }

  @Override // com.samsung.android.cover.ICoverManagerCallback
  public String getListenerInfo() throws RemoteException {
    return this.mListener.toString();
  }

  private static class ListenerDelegateHandler extends Handler {
    private final CoverManager.StateListener mListener;

    ListenerDelegateHandler(Looper looper, CoverManager.StateListener listener) {
      super(looper);
      this.mListener = listener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      if (this.mListener != null) {
        CoverState coverState = (CoverState) msg.obj;
        if (coverState != null) {
          this.mListener.onCoverStateChanged(coverState);
        } else {
          Log.m96e(CoverListenerDelegate.TAG, "coverState : null");
        }
      }
    }
  }
}
