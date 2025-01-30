package com.samsung.android.cover;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;

/* loaded from: classes5.dex */
class NfcLedCoverTouchListenerDelegate extends INfcLedCoverTouchListenerCallback.Stub {
  private static final int MSG_LISTEN_COVER_TOUCH_ACCEPT = 0;
  private static final int MSG_LISTEN_COVER_TOUCH_REJECT = 1;
  private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_LEFT = 2;
  private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_MID = 3;
  private static final int MSG_LISTEN_COVER_TOUCH_REJECT_TAP_RIGHT = 4;
  private ListenerDelegateHandler mHandler;
  private CoverManager.NfcLedCoverTouchListener mListener;

  NfcLedCoverTouchListenerDelegate(
      CoverManager.NfcLedCoverTouchListener listener, Handler handler, Context context) {
    this.mListener = listener;
    Looper looper = handler == null ? context.getMainLooper() : handler.getLooper();
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

  @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
  public void onCoverTapLeft() throws RemoteException {}

  @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
  public void onCoverTapMid() throws RemoteException {}

  @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
  public void onCoverTapRight() throws RemoteException {}

  @Override // com.samsung.android.cover.INfcLedCoverTouchListenerCallback
  public void onSystemCoverEvent(int event, Bundle args) throws RemoteException {}

  private static class ListenerDelegateHandler extends Handler {
    private final CoverManager.NfcLedCoverTouchListener mListener;

    ListenerDelegateHandler(Looper looper, CoverManager.NfcLedCoverTouchListener listener) {
      super(looper);
      this.mListener = listener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      if (this.mListener != null) {
        switch (msg.what) {
          case 0:
            this.mListener.onCoverTouchAccept();
            break;
          case 1:
            this.mListener.onCoverTouchReject();
            break;
        }
      }
    }
  }
}
