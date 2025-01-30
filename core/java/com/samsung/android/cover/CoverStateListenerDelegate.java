package com.samsung.android.cover;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

/* loaded from: classes5.dex */
class CoverStateListenerDelegate extends ICoverStateListenerCallback.Stub {
  private static final int MSG_LISTEN_COVER_ATTACH_STATE_CHANGE = 1;
  private static final int MSG_LISTEN_COVER_SWITCH_STATE_CHANGE = 0;
  public static final int TYPE_COVER_STATE_LISTENER = 2;
  private ListenerDelegateHandler mHandler;
  private final CoverManager.CoverStateListener mListener;

  CoverStateListenerDelegate(
      CoverManager.CoverStateListener listener, Handler handler, Context context) {
    this.mListener = listener;
    Looper looper = handler == null ? context.getMainLooper() : handler.getLooper();
    this.mHandler = new ListenerDelegateHandler(looper, listener);
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

    ListenerDelegateHandler(Looper looper, CoverManager.CoverStateListener listener) {
      super(looper);
      this.mListener = listener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      if (this.mListener != null) {
        switch (msg.what) {
          case 0:
            this.mListener.onCoverSwitchStateChanged(msg.arg1 == 1);
            break;
          case 1:
            this.mListener.onCoverAttachStateChanged(msg.arg1 == 1);
            break;
        }
      }
    }
  }
}
