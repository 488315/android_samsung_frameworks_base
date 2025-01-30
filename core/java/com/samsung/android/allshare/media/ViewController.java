package com.samsung.android.allshare.media;

import com.samsung.android.allshare.ERROR;

/* loaded from: classes5.dex */
public abstract class ViewController {

  public interface IEventListener {
    void onDisconnected(ViewController viewController, ERROR error);
  }

  public interface IResponseListener {
    void onConnectResponseReceived(ViewController viewController, ERROR error);

    void onDisconnectResponseReceived(ViewController viewController, ERROR error);
  }

  public abstract void connect();

  public abstract void disconnect();

  public abstract int getViewHeight();

  public abstract int getViewWidth();

  public abstract boolean isConnected();

  public abstract void move(int i, int i2, boolean z);

  public abstract void setEventListener(IEventListener iEventListener);

  public abstract void setResponseListener(IResponseListener iResponseListener);

  public abstract void setViewAngle(int i);

  public abstract void zoom(int i, int i2, int i3, int i4);

  public abstract void zoom(int i, int i2, int i3, int i4, int i5, int i6);

  protected ViewController() {}
}
