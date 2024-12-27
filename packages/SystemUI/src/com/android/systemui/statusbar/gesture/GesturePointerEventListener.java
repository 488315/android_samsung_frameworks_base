package com.android.systemui.statusbar.gesture;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewRootImpl;
import android.widget.OverScroller;
import com.android.systemui.CoreStartable;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class GesturePointerEventListener implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public final Context mContext;
    public boolean mDebugFireable;
    public final int mDisplayCutoutTouchableRegionSize;
    public final int[] mDownPointerId;
    public int mDownPointers;
    public final long[] mDownTime;
    public final float[] mDownX;
    public final float[] mDownY;
    public GesturePointerEventListener$start$2 mFlingGestureDetector;
    public final GesturePointerEventDetector mGestureDetector;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public long mLastFlingTime;
    public boolean mMouseHoveringAtBottom;
    public boolean mMouseHoveringAtLeft;
    public boolean mMouseHoveringAtRight;
    public boolean mMouseHoveringAtTop;
    public final int mSwipeDistanceThreshold;
    public boolean mSwipeFireable;
    public final Rect mSwipeStartThreshold;

    public final class Companion {
        private Companion() {
        }

        public static final void access$checkNull(Companion companion, String str, Object obj) {
            companion.getClass();
            if (obj == null) {
                throw new IllegalArgumentException(str.concat(" must not be null").toString());
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final class FlingGestureDetector extends GestureDetector.SimpleOnGestureListener {
        public final OverScroller mOverscroller;

        public FlingGestureDetector() {
            this.mOverscroller = new OverScroller(GesturePointerEventListener.this.mContext);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            this.mOverscroller.computeScrollOffset();
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = GesturePointerEventListener.this.mLastFlingTime;
            if (j != 0 && uptimeMillis > j + 5000) {
                this.mOverscroller.forceFinished(true);
            }
            this.mOverscroller.fling(0, 0, (int) f, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.mOverscroller.getDuration();
            GesturePointerEventListener gesturePointerEventListener = GesturePointerEventListener.this;
            gesturePointerEventListener.mLastFlingTime = uptimeMillis;
            gesturePointerEventListener.getClass();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!this.mOverscroller.isFinished()) {
                this.mOverscroller.forceFinished(true);
            }
            return true;
        }
    }

    public GesturePointerEventListener(Context context, GesturePointerEventDetector gesturePointerEventDetector) {
        Rect rect = new Rect();
        this.mSwipeStartThreshold = rect;
        this.mDownPointerId = new int[32];
        this.mDownX = new float[32];
        this.mDownY = new float[32];
        this.mDownTime = new long[32];
        Companion companion = Companion;
        Companion.access$checkNull(companion, "context", context);
        this.mContext = context;
        Companion.access$checkNull(companion, "gesture detector", gesturePointerEventDetector);
        this.mGestureDetector = gesturePointerEventDetector;
        if (ViewRootImpl.CLIENT_TRANSIENT) {
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(17106300);
            rect.set(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            this.mSwipeDistanceThreshold = resources.getDimensionPixelSize(17106299);
            DisplayCutout cutout = DisplayManagerGlobal.getInstance().getRealDisplay(context.getDisplayId()).getCutout();
            if (cutout != null) {
                this.mDisplayCutoutTouchableRegionSize = resources.getDimensionPixelSize(R.dimen.harmful_app_name_padding_right);
                Rect[] boundingRectsAll = cutout.getBoundingRectsAll();
                Rect rect2 = boundingRectsAll[0];
                if (rect2 != null) {
                    rect.left = Math.max(rect.left, rect2.width() + this.mDisplayCutoutTouchableRegionSize);
                }
                Rect rect3 = boundingRectsAll[1];
                if (rect3 != null) {
                    rect.top = Math.max(rect.top, rect3.height() + this.mDisplayCutoutTouchableRegionSize);
                }
                Rect rect4 = boundingRectsAll[2];
                if (rect4 != null) {
                    rect.right = Math.max(rect.right, rect4.width() + this.mDisplayCutoutTouchableRegionSize);
                }
                Rect rect5 = boundingRectsAll[3];
                if (rect5 != null) {
                    rect.bottom = Math.max(rect.bottom, rect5.height() + this.mDisplayCutoutTouchableRegionSize);
                }
            }
        }
    }

    public final void captureDown(MotionEvent motionEvent, int i) {
        int findIndex = findIndex(motionEvent.getPointerId(i));
        if (findIndex != -1) {
            this.mDownX[findIndex] = motionEvent.getX(i);
            this.mDownY[findIndex] = motionEvent.getY(i);
            this.mDownTime[findIndex] = motionEvent.getEventTime();
        }
    }

    public final int detectSwipe(float f, float f2, long j, int i) {
        float f3 = this.mDownX[i];
        float f4 = this.mDownY[i];
        long j2 = j - this.mDownTime[i];
        Rect rect = this.mSwipeStartThreshold;
        if (f4 <= rect.top && f2 > this.mSwipeDistanceThreshold + f4 && j2 < 500) {
            return 1;
        }
        if (f4 >= 0 - rect.bottom && f2 < f4 - this.mSwipeDistanceThreshold && j2 < 500) {
            return 2;
        }
        if (f3 < 0 - rect.right || f >= f3 - this.mSwipeDistanceThreshold || j2 >= 500) {
            return (f3 > ((float) rect.left) || f <= f3 + ((float) this.mSwipeDistanceThreshold) || j2 >= 500) ? 0 : 4;
        }
        return 3;
    }

    public final int findIndex(int i) {
        int i2 = this.mDownPointers;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mDownPointerId;
            if (i3 >= i2) {
                int i4 = this.mDownPointers;
                if (i4 == 32 || i == -1) {
                    return -1;
                }
                this.mDownPointers = i4 + 1;
                iArr[i4] = i;
                return i4;
            }
            if (iArr[i3] == i) {
                return i3;
            }
            i3++;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (ViewRootImpl.CLIENT_TRANSIENT) {
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.gesture.GesturePointerEventListener$start$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r15) {
                    /*
                        Method dump skipped, instructions count: 374
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.gesture.GesturePointerEventListener$start$1.invoke(java.lang.Object):java.lang.Object");
                }
            };
            GesturePointerEventDetector gesturePointerEventDetector = this.mGestureDetector;
            gesturePointerEventDetector.addOnGestureDetectedCallback(function1, "GesturePointerEventHandler");
            gesturePointerEventDetector.startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            final Context context = this.mContext;
            final FlingGestureDetector flingGestureDetector = new FlingGestureDetector();
            final Handler handler = this.mHandler;
            this.mFlingGestureDetector = new GestureDetector(context, flingGestureDetector, handler) { // from class: com.android.systemui.statusbar.gesture.GesturePointerEventListener$start$2
            };
        }
    }
}
