package android.telecom;

import android.p009os.Handler;

/* loaded from: classes3.dex */
class CallbackRecord<T> {
  private final T mCallback;
  private final Handler mHandler;

  public CallbackRecord(T callback, Handler handler) {
    this.mCallback = callback;
    this.mHandler = handler;
  }

  public T getCallback() {
    return this.mCallback;
  }

  public Handler getHandler() {
    return this.mHandler;
  }
}
