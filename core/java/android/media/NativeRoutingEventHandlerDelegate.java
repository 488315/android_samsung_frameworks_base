package android.media;

import android.p009os.Handler;

/* loaded from: classes2.dex */
class NativeRoutingEventHandlerDelegate {
  private AudioRouting mAudioRouting;
  private Handler mHandler;
  private AudioRouting.OnRoutingChangedListener mOnRoutingChangedListener;

  NativeRoutingEventHandlerDelegate(
      AudioRouting audioRouting, AudioRouting.OnRoutingChangedListener listener, Handler handler) {
    this.mAudioRouting = audioRouting;
    this.mOnRoutingChangedListener = listener;
    this.mHandler = handler;
  }

  void notifyClient() {
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.post(
          new Runnable() { // from class: android.media.NativeRoutingEventHandlerDelegate.1
            @Override // java.lang.Runnable
            public void run() {
              if (NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener != null) {
                NativeRoutingEventHandlerDelegate.this.mOnRoutingChangedListener.onRoutingChanged(
                    NativeRoutingEventHandlerDelegate.this.mAudioRouting);
              }
            }
          });
    }
  }
}
