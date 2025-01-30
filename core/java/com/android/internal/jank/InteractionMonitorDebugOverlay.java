package com.android.internal.jank;

import android.app.ActivityThread;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.p009os.Handler;
import android.p009os.Trace;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.WindowCallbacks;

/* loaded from: classes4.dex */
class InteractionMonitorDebugOverlay implements WindowCallbacks {
  private static final int REASON_STILL_RUNNING = -1000;
  private static final String TRACK_NAME = "InteractionJankMonitor";
  private final int mBgColor;
  private final Paint.FontMetrics mDebugFontMetrics;
  private final Paint mDebugPaint;
  private final Object mLock;
  private final String mPackageName;
  private final double mYOffset;
  private final SparseIntArray mRunningCujs = new SparseIntArray();
  private Handler mHandler = null;
  private FrameTracker.ViewRootWrapper mViewRoot = null;

  InteractionMonitorDebugOverlay(Object lock, int bgColor, double yOffset) {
    this.mLock = lock;
    this.mBgColor = bgColor;
    this.mYOffset = yOffset;
    Paint paint = new Paint();
    this.mDebugPaint = paint;
    paint.setAntiAlias(false);
    this.mDebugFontMetrics = new Paint.FontMetrics();
    Context context = ActivityThread.currentApplication();
    this.mPackageName = context.getPackageName();
  }

  void dispose() {
    Handler handler;
    if (this.mViewRoot != null && (handler = this.mHandler) != null) {
      handler.runWithScissors(
          new Runnable() { // from class:
                           // com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              InteractionMonitorDebugOverlay.this.lambda$dispose$0();
            }
          },
          500L);
      forceRedraw();
    }
    this.mHandler = null;
    this.mViewRoot = null;
    Trace.asyncTraceForTrackEnd(4096L, TRACK_NAME, 0);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$dispose$0() {
    this.mViewRoot.removeWindowCallbacks(this);
  }

  private boolean attachViewRootIfNeeded(FrameTracker tracker) {
    final FrameTracker.ViewRootWrapper viewRoot = tracker.getViewRoot();
    if (this.mViewRoot != null || viewRoot == null) {
      return false;
    }
    Trace.asyncTraceForTrackBegin(4096L, TRACK_NAME, "DEBUG_OVERLAY_DRAW", 0);
    Handler handler = tracker.getHandler();
    this.mHandler = handler;
    this.mViewRoot = viewRoot;
    handler.runWithScissors(
        new Runnable() { // from class:
                         // com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            InteractionMonitorDebugOverlay.this.lambda$attachViewRootIfNeeded$1(viewRoot);
          }
        },
        500L);
    forceRedraw();
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$attachViewRootIfNeeded$1(
      FrameTracker.ViewRootWrapper viewRoot) {
    viewRoot.addWindowCallbacks(this);
  }

  private float getWidthOfLongestCujName(int cujFontSize) {
    this.mDebugPaint.setTextSize(cujFontSize);
    float maxLength = 0.0f;
    for (int i = 0; i < this.mRunningCujs.size(); i++) {
      String cujName = InteractionJankMonitor.getNameOfCuj(this.mRunningCujs.keyAt(i));
      float textLength = this.mDebugPaint.measureText(cujName);
      if (textLength > maxLength) {
        maxLength = textLength;
      }
    }
    return maxLength;
  }

  private float getTextHeight(int textSize) {
    this.mDebugPaint.setTextSize(textSize);
    this.mDebugPaint.getFontMetrics(this.mDebugFontMetrics);
    return this.mDebugFontMetrics.descent - this.mDebugFontMetrics.ascent;
  }

  private int dipToPx(int dip) {
    FrameTracker.ViewRootWrapper viewRootWrapper = this.mViewRoot;
    if (viewRootWrapper != null) {
      return viewRootWrapper.dipToPx(dip);
    }
    return dip;
  }

  private void forceRedraw() {
    Handler handler;
    if (this.mViewRoot != null && (handler = this.mHandler) != null) {
      handler.runWithScissors(
          new Runnable() { // from class:
                           // com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              InteractionMonitorDebugOverlay.this.lambda$forceRedraw$2();
            }
          },
          500L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$forceRedraw$2() {
    this.mViewRoot.requestInvalidateRootRenderNode();
    this.mViewRoot.getView().invalidate();
  }

  void onTrackerRemoved(int removedCuj, int reason, SparseArray<FrameTracker> runningTrackers) {
    synchronized (this.mLock) {
      this.mRunningCujs.put(removedCuj, reason);
      if (this.mRunningCujs.indexOfValue(-1000) < 0) {
        this.mRunningCujs.clear();
        dispose();
      } else {
        boolean needsNewViewRoot = true;
        if (this.mViewRoot != null) {
          int i = 0;
          while (true) {
            if (i >= runningTrackers.size()) {
              break;
            }
            if (!this.mViewRoot.equals(runningTrackers.valueAt(i).getViewRoot())) {
              i++;
            } else {
              needsNewViewRoot = false;
              break;
            }
          }
        }
        if (needsNewViewRoot) {
          dispose();
          for (int i2 = 0;
              i2 < runningTrackers.size() && !attachViewRootIfNeeded(runningTrackers.valueAt(i2));
              i2++) {}
        } else {
          forceRedraw();
        }
      }
    }
  }

  void onTrackerAdded(int addedCuj, FrameTracker tracker) {
    synchronized (this.mLock) {
      this.mRunningCujs.put(addedCuj, -1000);
      attachViewRootIfNeeded(tracker);
      forceRedraw();
    }
  }

  @Override // android.view.WindowCallbacks
  public void onWindowSizeIsChanging(
      Rect newBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {}

  @Override // android.view.WindowCallbacks
  public void onWindowDragResizeStart(
      Rect initialBounds, boolean fullscreen, Rect systemInsets, Rect stableInsets) {}

  @Override // android.view.WindowCallbacks
  public void onWindowDragResizeEnd() {}

  @Override // android.view.WindowCallbacks
  public boolean onContentDrawn(int offsetX, int offsetY, int sizeX, int sizeY) {
    return false;
  }

  @Override // android.view.WindowCallbacks
  public void onRequestDraw(boolean reportNextDraw) {}

  @Override // android.view.WindowCallbacks
  public void onPostDraw(RecordingCanvas canvas) {
    int padding = dipToPx(5);
    int h = canvas.getHeight();
    int w = canvas.getWidth();
    int dy = (int) (h * this.mYOffset);
    int packageNameFontSize = dipToPx(12);
    int cujFontSize = dipToPx(18);
    float cujNameTextHeight = getTextHeight(cujFontSize);
    float packageNameTextHeight = getTextHeight(packageNameFontSize);
    float maxLength = getWidthOfLongestCujName(cujFontSize);
    int dx = (int) ((w - maxLength) / 2.0f);
    canvas.translate(dx, dy);
    this.mDebugPaint.setColor(this.mBgColor);
    canvas.drawRect(
        (-padding) * 2,
        -padding,
        (padding * 2) + maxLength,
        (this.mRunningCujs.size() * cujNameTextHeight) + (padding * 2) + packageNameTextHeight,
        this.mDebugPaint);
    this.mDebugPaint.setTextSize(packageNameFontSize);
    int i = -16777216;
    this.mDebugPaint.setColor(-16777216);
    this.mDebugPaint.setStrikeThruText(false);
    canvas.translate(0.0f, packageNameTextHeight);
    canvas.drawText("package:" + this.mPackageName, 0.0f, 0.0f, this.mDebugPaint);
    this.mDebugPaint.setTextSize(cujFontSize);
    int i2 = 0;
    while (i2 < this.mRunningCujs.size()) {
      int status = this.mRunningCujs.valueAt(i2);
      if (status == -1000) {
        this.mDebugPaint.setColor(i);
        this.mDebugPaint.setStrikeThruText(false);
      } else if (status == 0) {
        this.mDebugPaint.setColor(Color.GRAY);
        this.mDebugPaint.setStrikeThruText(false);
      } else {
        this.mDebugPaint.setColor(-65536);
        this.mDebugPaint.setStrikeThruText(true);
      }
      String cujName = InteractionJankMonitor.getNameOfCuj(this.mRunningCujs.keyAt(i2));
      canvas.translate(0.0f, cujNameTextHeight);
      canvas.drawText(cujName, 0.0f, 0.0f, this.mDebugPaint);
      i2++;
      i = -16777216;
    }
  }
}
