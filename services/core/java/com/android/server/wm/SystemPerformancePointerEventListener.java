package com.android.server.wm;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.samsung.android.os.SemDvfsManager;

/* loaded from: classes3.dex */
public class SystemPerformancePointerEventListener
    implements WindowManagerPolicyConstants.PointerEventListener {
  public final String TAG;
  public final Context mContext;
  public GestureDetector mGestureDetector;
  public final Handler mHandler;
  public long mLastAcquireTime;
  public Runnable mScrollAcquireRunnable;
  public SemDvfsManager mScrollDvfsManager;
  public Runnable mScrollReleaseRunnable;

  public SystemPerformancePointerEventListener(Context context) {
    String simpleName = SystemPerformancePointerEventListener.class.getSimpleName();
    this.TAG = simpleName;
    this.mContext = context;
    Handler handler = new Handler(context.getMainLooper());
    this.mHandler = handler;
    SemDvfsManager createInstance =
        SemDvfsManager.createInstance(context, "WM_SCROLL_DETECTED", 14);
    this.mScrollDvfsManager = createInstance;
    if (createInstance != null) {
      int[] supportedFrequency = createInstance.getSupportedFrequency();
      if (supportedFrequency != null && supportedFrequency.length > 0) {
        this.mScrollDvfsManager.setDvfsValue(supportedFrequency[0]);
        this.mLastAcquireTime = 0L;
        this.mScrollReleaseRunnable =
            new Runnable() { // from class:
                             // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                SystemPerformancePointerEventListener.this.lambda$new$0();
              }
            };
        this.mScrollAcquireRunnable =
            new Runnable() { // from class:
                             // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                SystemPerformancePointerEventListener.this.lambda$new$1();
              }
            };
      } else {
        Slog.e(simpleName, "SemDvfsManager Not Initialized...");
        this.mScrollDvfsManager = null;
      }
    }
    handler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.SystemPerformancePointerEventListener$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            SystemPerformancePointerEventListener.this.lambda$new$2();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    SemDvfsManager semDvfsManager = this.mScrollDvfsManager;
    if (semDvfsManager != null) {
      semDvfsManager.release();
      this.mLastAcquireTime = 0L;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1() {
    SemDvfsManager semDvfsManager = this.mScrollDvfsManager;
    if (semDvfsManager != null) {
      semDvfsManager.acquire();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$2() {
    this.mGestureDetector =
        new GestureDetector(
            this.mContext,
            new PerformaneGestureDetector(),
            this
                .mHandler) { // from class:
                             // com.android.server.wm.SystemPerformancePointerEventListener.1
        };
  }

  public void onPointerEvent(MotionEvent motionEvent) {
    Runnable runnable;
    if (this.mGestureDetector != null && motionEvent.isTouchEvent()) {
      this.mGestureDetector.onTouchEvent(motionEvent);
    }
    int actionMasked = motionEvent.getActionMasked();
    if ((actionMasked != 1 && actionMasked != 3)
        || this.mScrollDvfsManager == null
        || (runnable = this.mScrollReleaseRunnable) == null) {
      return;
    }
    this.mHandler.removeCallbacks(runnable);
    this.mScrollDvfsManager.release();
    this.mLastAcquireTime = 0L;
  }

  public final class PerformaneGestureDetector extends GestureDetector.SimpleOnGestureListener {
    public PerformaneGestureDetector() {}

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
      long uptimeMillis = SystemClock.uptimeMillis();
      if (SystemPerformancePointerEventListener.this.mContext.getDisplay().getRefreshRate() < 90.0f
          || SystemPerformancePointerEventListener.this.mScrollDvfsManager == null
          || uptimeMillis - SystemPerformancePointerEventListener.this.mLastAcquireTime < 500
          || SystemPerformancePointerEventListener.this.mScrollReleaseRunnable == null
          || SystemPerformancePointerEventListener.this.mScrollAcquireRunnable == null) {
        return true;
      }
      SystemPerformancePointerEventListener.this.mHandler.removeCallbacks(
          SystemPerformancePointerEventListener.this.mScrollReleaseRunnable);
      SystemPerformancePointerEventListener.this.mHandler.post(
          SystemPerformancePointerEventListener.this.mScrollAcquireRunnable);
      SystemPerformancePointerEventListener.this.mLastAcquireTime = uptimeMillis;
      SystemPerformancePointerEventListener.this.mHandler.postDelayed(
          SystemPerformancePointerEventListener.this.mScrollReleaseRunnable, 1000L);
      return true;
    }
  }
}
