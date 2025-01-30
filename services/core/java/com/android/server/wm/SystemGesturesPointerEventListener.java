package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import android.widget.OverScroller;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class SystemGesturesPointerEventListener
    implements WindowManagerPolicyConstants.PointerEventListener {
  public final Callbacks mCallbacks;
  public final Context mContext;
  public boolean mDebugFireable;
  public DisplayContent mDisplayContent;
  public int mDisplayCutoutTouchableRegionSize;
  public int mDownPointers;
  public GestureDetector mGestureDetector;
  public final Handler mHandler;
  public long mLastFlingTime;
  public boolean mMouseHoveringAtBottom;
  public boolean mMouseHoveringAtLeft;
  public boolean mMouseHoveringAtRight;
  public boolean mMouseHoveringAtTop;
  public MultiWindowEdgeDetector mMultiWindowEdgeDetector;
  public int mSwipeDistanceThreshold;
  public boolean mSwipeFireable;
  public int screenHeight;
  public int screenWidth;
  public final Rect mSwipeStartThreshold = new Rect();
  public final int[] mDownPointerId = new int[32];
  public final float[] mDownX = new float[32];
  public final float[] mDownY = new float[32];
  public final long[] mDownTime = new long[32];

  public interface Callbacks {
    void onDebug();

    void onDown();

    void onFling(int i);

    void onMouseHoverAtBottom();

    void onMouseHoverAtLeft();

    void onMouseHoverAtRight();

    void onMouseHoverAtTop();

    void onMouseLeaveFromBottom();

    void onMouseLeaveFromLeft();

    void onMouseLeaveFromRight();

    void onMouseLeaveFromTop();

    void onSwipeFromBottom();

    void onSwipeFromLeft();

    void onSwipeFromRight();

    void onSwipeFromTop();

    void onUpOrCancel();
  }

  public SystemGesturesPointerEventListener(Context context, Handler handler, Callbacks callbacks) {
    this.mContext = (Context) checkNull("context", context);
    this.mHandler = handler;
    this.mCallbacks = (Callbacks) checkNull("callbacks", callbacks);
    onConfigurationChanged();
  }

  public void onDisplayInfoChanged(DisplayInfo displayInfo) {
    this.screenWidth = displayInfo.logicalWidth;
    this.screenHeight = displayInfo.logicalHeight;
    onConfigurationChanged();
  }

  public void onConfigurationChanged() {
    MultiWindowEdgeDetector multiWindowEdgeDetector = this.mMultiWindowEdgeDetector;
    if (multiWindowEdgeDetector != null) {
      multiWindowEdgeDetector.onConfigurationChanged();
    }
    Resources resources = this.mContext.getResources();
    int dimensionPixelSize = resources.getDimensionPixelSize(17106189);
    this.mSwipeStartThreshold.set(
        dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    this.mSwipeDistanceThreshold = dimensionPixelSize;
    DisplayCutout cutout = DisplayManagerGlobal.getInstance().getRealDisplay(0).getCutout();
    if (cutout != null) {
      this.mDisplayCutoutTouchableRegionSize =
          resources.getDimensionPixelSize(R.dimen.docked_stack_minimize_thickness);
      Rect[] boundingRectsAll = cutout.getBoundingRectsAll();
      Rect rect = boundingRectsAll[0];
      if (rect != null) {
        Rect rect2 = this.mSwipeStartThreshold;
        rect2.left = Math.max(rect2.left, rect.width() + this.mDisplayCutoutTouchableRegionSize);
      }
      Rect rect3 = boundingRectsAll[1];
      if (rect3 != null) {
        Rect rect4 = this.mSwipeStartThreshold;
        rect4.top = Math.max(rect4.top, rect3.height() + this.mDisplayCutoutTouchableRegionSize);
      }
      Rect rect5 = boundingRectsAll[2];
      if (rect5 != null) {
        Rect rect6 = this.mSwipeStartThreshold;
        rect6.right = Math.max(rect6.right, rect5.width() + this.mDisplayCutoutTouchableRegionSize);
      }
      Rect rect7 = boundingRectsAll[3];
      if (rect7 != null) {
        Rect rect8 = this.mSwipeStartThreshold;
        rect8.bottom =
            Math.max(rect8.bottom, rect7.height() + this.mDisplayCutoutTouchableRegionSize);
      }
    }
  }

  public static Object checkNull(String str, Object obj) {
    if (obj != null) {
      return obj;
    }
    throw new IllegalArgumentException(str + " must not be null");
  }

  public void systemReady() {
    this.mHandler.post(
        new Runnable() { // from class:
                         // com.android.server.wm.SystemGesturesPointerEventListener$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            SystemGesturesPointerEventListener.this.lambda$systemReady$0();
          }
        });
    if (this.mContext.getDisplayId() == 0) {
      this.mMultiWindowEdgeDetector = new MultiWindowEdgeDetector(this.mContext, "SystemGesture");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$systemReady$0() {
    int displayId = this.mContext.getDisplayId();
    if (DisplayManagerGlobal.getInstance().getDisplayInfo(displayId) == null) {
      Slog.w("SystemGestures", "Cannot create GestureDetector, display removed:" + displayId);
      return;
    }
    this.mGestureDetector =
        new GestureDetector(
            this.mContext,
            new FlingGestureDetector(),
            this
                .mHandler) { // from class:
                             // com.android.server.wm.SystemGesturesPointerEventListener.1
        };
  }

  public void onPointerEvent(MotionEvent motionEvent) {
    if (this.mGestureDetector != null && motionEvent.isTouchEvent()) {
      this.mGestureDetector.onTouchEvent(motionEvent);
    }
    int actionMasked = motionEvent.getActionMasked();
    if (actionMasked == 0) {
      this.mSwipeFireable = true;
      this.mDebugFireable = true;
      this.mDownPointers = 0;
      captureDown(motionEvent, 0);
      if (this.mMouseHoveringAtLeft) {
        this.mMouseHoveringAtLeft = false;
        this.mCallbacks.onMouseLeaveFromLeft();
      }
      if (this.mMouseHoveringAtTop) {
        this.mMouseHoveringAtTop = false;
        this.mCallbacks.onMouseLeaveFromTop();
      }
      if (this.mMouseHoveringAtRight) {
        this.mMouseHoveringAtRight = false;
        this.mCallbacks.onMouseLeaveFromRight();
      }
      if (this.mMouseHoveringAtBottom) {
        this.mMouseHoveringAtBottom = false;
        this.mCallbacks.onMouseLeaveFromBottom();
      }
      this.mCallbacks.onDown();
      return;
    }
    if (actionMasked != 1) {
      if (actionMasked == 2) {
        if (this.mSwipeFireable) {
          int detectSwipe = detectSwipe(motionEvent);
          this.mSwipeFireable = detectSwipe == 0;
          if (detectSwipe == 1) {
            this.mCallbacks.onSwipeFromTop();
            return;
          }
          if (detectSwipe == 2) {
            this.mCallbacks.onSwipeFromBottom();
            return;
          }
          if (detectSwipe == 3) {
            this.mCallbacks.onSwipeFromRight();
            return;
          } else if (detectSwipe == 4) {
            this.mCallbacks.onSwipeFromLeft();
            return;
          } else {
            if (this.mDisplayContent.isDexMode()) {
              detectSystemGestureForDex(motionEvent);
              return;
            }
            return;
          }
        }
        return;
      }
      if (actionMasked != 3) {
        if (actionMasked == 5) {
          captureDown(motionEvent, motionEvent.getActionIndex());
          if (this.mDebugFireable) {
            boolean z = motionEvent.getPointerCount() < 5;
            this.mDebugFireable = z;
            if (z) {
              return;
            }
            this.mCallbacks.onDebug();
            return;
          }
          return;
        }
        if (actionMasked != 7) {
          return;
        }
        if (this.mDisplayContent.isDexMode() && motionEvent.isFromSource(2)) {
          detectSystemGestureForDex(motionEvent);
          return;
        }
        if (motionEvent.isFromSource(8194)) {
          float x = motionEvent.getX();
          float y = motionEvent.getY();
          boolean z2 = this.mMouseHoveringAtLeft;
          if (!z2 && x == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mCallbacks.onMouseHoverAtLeft();
            this.mMouseHoveringAtLeft = true;
          } else if (z2 && x > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mCallbacks.onMouseLeaveFromLeft();
            this.mMouseHoveringAtLeft = false;
          }
          boolean z3 = this.mMouseHoveringAtTop;
          if (!z3 && y == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mCallbacks.onMouseHoverAtTop();
            this.mMouseHoveringAtTop = true;
          } else if (z3 && y > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mCallbacks.onMouseLeaveFromTop();
            this.mMouseHoveringAtTop = false;
          }
          boolean z4 = this.mMouseHoveringAtRight;
          if (!z4 && x >= this.screenWidth - 1) {
            this.mCallbacks.onMouseHoverAtRight();
            this.mMouseHoveringAtRight = true;
          } else if (z4 && x < this.screenWidth - 1) {
            this.mCallbacks.onMouseLeaveFromRight();
            this.mMouseHoveringAtRight = false;
          }
          boolean z5 = this.mMouseHoveringAtBottom;
          if (!z5 && y >= this.screenHeight - 1) {
            this.mCallbacks.onMouseHoverAtBottom();
            this.mMouseHoveringAtBottom = true;
            return;
          } else {
            if (!z5 || y >= this.screenHeight - 1) {
              return;
            }
            this.mCallbacks.onMouseLeaveFromBottom();
            this.mMouseHoveringAtBottom = false;
            return;
          }
        }
        return;
      }
    }
    this.mSwipeFireable = false;
    this.mDebugFireable = false;
    this.mCallbacks.onUpOrCancel();
  }

  public final void captureDown(MotionEvent motionEvent, int i) {
    int findIndex = findIndex(motionEvent.getPointerId(i));
    if (findIndex != -1) {
      this.mDownX[findIndex] = motionEvent.getX(i);
      this.mDownY[findIndex] = motionEvent.getY(i);
      this.mDownTime[findIndex] = motionEvent.getEventTime();
    }
  }

  public boolean currentGestureStartedInRegion(Region region) {
    return region.contains((int) this.mDownX[0], (int) this.mDownY[0]);
  }

  public final int findIndex(int i) {
    int i2 = 0;
    while (true) {
      int i3 = this.mDownPointers;
      if (i2 >= i3) {
        if (i3 == 32 || i == -1) {
          return -1;
        }
        int[] iArr = this.mDownPointerId;
        int i4 = i3 + 1;
        this.mDownPointers = i4;
        iArr[i3] = i;
        return i4 - 1;
      }
      if (this.mDownPointerId[i2] == i) {
        return i2;
      }
      i2++;
    }
  }

  public final int detectSwipe(MotionEvent motionEvent) {
    int historySize = motionEvent.getHistorySize();
    int pointerCount = motionEvent.getPointerCount();
    for (int i = 0; i < pointerCount; i++) {
      int findIndex = findIndex(motionEvent.getPointerId(i));
      if (findIndex != -1) {
        for (int i2 = 0; i2 < historySize; i2++) {
          int detectSwipe =
              detectSwipe(
                  findIndex,
                  motionEvent.getHistoricalEventTime(i2),
                  motionEvent.getHistoricalX(i, i2),
                  motionEvent.getHistoricalY(i, i2));
          if (detectSwipe != 0) {
            return detectSwipe;
          }
        }
        int detectSwipe2 =
            detectSwipe(
                findIndex, motionEvent.getEventTime(), motionEvent.getX(i), motionEvent.getY(i));
        if (detectSwipe2 != 0) {
          return detectSwipe2;
        }
      }
    }
    return 0;
  }

  public final int detectSwipe(int i, long j, float f, float f2) {
    float f3 = this.mDownX[i];
    float f4 = this.mDownY[i];
    long j2 = j - this.mDownTime[i];
    Rect rect = this.mSwipeStartThreshold;
    if (f4 <= rect.top && f2 > this.mSwipeDistanceThreshold + f4 && j2 < 500) {
      return 1;
    }
    if (f4 >= this.screenHeight - rect.bottom
        && f2 < f4 - this.mSwipeDistanceThreshold
        && j2 < 500) {
      return 2;
    }
    if (f3 < this.screenWidth - rect.right || f >= f3 - this.mSwipeDistanceThreshold || j2 >= 500) {
      return (f3 > ((float) rect.left)
              || f <= f3 + ((float) this.mSwipeDistanceThreshold)
              || j2 >= 500)
          ? 0
          : 4;
    }
    return 3;
  }

  public void setDisplayContent(DisplayContent displayContent) {
    this.mDisplayContent = displayContent;
  }

  public final void detectSystemGestureForDex(MotionEvent motionEvent) {
    if (!this.mMouseHoveringAtTop
        && motionEvent.getY() == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      this.mCallbacks.onMouseHoverAtTop();
      this.mMouseHoveringAtTop = true;
      return;
    }
    if (this.mMouseHoveringAtTop
        && motionEvent.getY() > DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        && motionEvent.getY() < this.screenHeight - 22) {
      this.mCallbacks.onMouseLeaveFromTop();
      this.mMouseHoveringAtTop = false;
    } else if (!this.mMouseHoveringAtBottom && motionEvent.getY() >= this.screenHeight - 22) {
      this.mCallbacks.onMouseHoverAtBottom();
      this.mMouseHoveringAtBottom = true;
    } else {
      if (!this.mMouseHoveringAtBottom || motionEvent.getY() >= this.screenHeight - 22) {
        return;
      }
      this.mCallbacks.onMouseLeaveFromBottom();
      this.mMouseHoveringAtBottom = false;
    }
  }

  public void dump(PrintWriter printWriter, String str) {
    String str2 = str + "  ";
    printWriter.println(str + "SystemGestures" + XmlUtils.STRING_ARRAY_SEPARATOR);
    printWriter.print(str2);
    printWriter.print("mDisplayCutoutTouchableRegionSize=");
    printWriter.println(this.mDisplayCutoutTouchableRegionSize);
    printWriter.print(str2);
    printWriter.print("mSwipeStartThreshold=");
    printWriter.println(this.mSwipeStartThreshold);
    printWriter.print(str2);
    printWriter.print("mSwipeDistanceThreshold=");
    printWriter.println(this.mSwipeDistanceThreshold);
  }

  public final class FlingGestureDetector extends GestureDetector.SimpleOnGestureListener {
    public OverScroller mOverscroller;

    public FlingGestureDetector() {
      this.mOverscroller = new OverScroller(SystemGesturesPointerEventListener.this.mContext);
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
      if (!this.mOverscroller.isFinished()) {
        this.mOverscroller.forceFinished(true);
      }
      return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener,
              // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
      this.mOverscroller.computeScrollOffset();
      long uptimeMillis = SystemClock.uptimeMillis();
      if (SystemGesturesPointerEventListener.this.mLastFlingTime != 0
          && uptimeMillis > SystemGesturesPointerEventListener.this.mLastFlingTime + 5000) {
        this.mOverscroller.forceFinished(true);
      }
      this.mOverscroller.fling(
          0,
          0,
          (int) f,
          (int) f2,
          Integer.MIN_VALUE,
          Integer.MAX_VALUE,
          Integer.MIN_VALUE,
          Integer.MAX_VALUE);
      int duration = this.mOverscroller.getDuration();
      if (duration > 5000) {
        duration = 5000;
      }
      SystemGesturesPointerEventListener.this.mLastFlingTime = uptimeMillis;
      this.mOverscroller.abortAnimation();
      SystemGesturesPointerEventListener.this.mCallbacks.onFling(duration);
      return true;
    }
  }
}
