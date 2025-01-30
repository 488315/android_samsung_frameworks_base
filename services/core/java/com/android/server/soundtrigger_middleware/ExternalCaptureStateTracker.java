package com.android.server.soundtrigger_middleware;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/* loaded from: classes3.dex */
class ExternalCaptureStateTracker implements ICaptureStateNotifier {
  public static final String TAG = "CaptureStateTracker";
  public final List mListeners = new LinkedList();
  public boolean mCaptureActive = true;
  public final Semaphore mNeedToConnect = new Semaphore(1);

  private native void connect();

  public ExternalCaptureStateTracker() {
    new Thread(
            new Runnable() { // from class:
              // com.android.server.soundtrigger_middleware.ExternalCaptureStateTracker$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                ExternalCaptureStateTracker.this.run();
              }
            })
        .start();
  }

  @Override // com.android.server.soundtrigger_middleware.ICaptureStateNotifier
  public boolean registerListener(ICaptureStateNotifier.Listener listener) {
    boolean z;
    synchronized (this.mListeners) {
      this.mListeners.add(listener);
      z = this.mCaptureActive;
    }
    return z;
  }

  @Override // com.android.server.soundtrigger_middleware.ICaptureStateNotifier
  public void unregisterListener(ICaptureStateNotifier.Listener listener) {
    synchronized (this.mListeners) {
      this.mListeners.remove(listener);
    }
  }

  public final void run() {
    while (true) {
      this.mNeedToConnect.acquireUninterruptibly();
      connect();
    }
  }

  public final void setCaptureState(boolean z) {
    try {
      synchronized (this.mListeners) {
        this.mCaptureActive = z;
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
          ((ICaptureStateNotifier.Listener) it.next()).onCaptureStateChange(z);
        }
      }
    } catch (Exception e) {
      Log.e(TAG, "Exception caught while setting capture state", e);
    }
  }

  public final void binderDied() {
    Log.w(TAG, "Audio policy service died");
    this.mNeedToConnect.release();
  }
}
