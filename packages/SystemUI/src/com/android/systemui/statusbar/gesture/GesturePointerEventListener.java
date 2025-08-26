package com.android.systemui.statusbar.gesture;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewRootImpl;
import android.widget.OverScroller;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.gesture.GesturePointerEventListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
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
    public AnonymousClass2 mFlingGestureDetector;
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$checkNull(Companion companion, String str, Object obj) {
            companion.getClass();
            if (obj == null) {
                throw new IllegalArgumentException(str.concat(" must not be null").toString());
            }
        }

        private Companion() {
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
            long jUptimeMillis = SystemClock.uptimeMillis();
            long j = GesturePointerEventListener.this.mLastFlingTime;
            if (j != 0 && jUptimeMillis > j + 5000) {
                this.mOverscroller.forceFinished(true);
            }
            this.mOverscroller.fling(0, 0, (int) f, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.mOverscroller.getDuration();
            GesturePointerEventListener gesturePointerEventListener = GesturePointerEventListener.this;
            gesturePointerEventListener.mLastFlingTime = jUptimeMillis;
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

    public GesturePointerEventListener(Context context, GesturePointerEventDetector gesturePointerEventDetector) throws Resources.NotFoundException {
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
            int dimensionPixelSize = resources.getDimensionPixelSize(17106393);
            rect.set(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            this.mSwipeDistanceThreshold = resources.getDimensionPixelSize(17106392);
            Display realDisplay = DisplayManagerGlobal.getInstance().getRealDisplay(context.getDisplayId());
            DisplayCutout cutout = realDisplay != null ? realDisplay.getCutout() : null;
            if (cutout != null) {
                this.mDisplayCutoutTouchableRegionSize = resources.getDimensionPixelSize(R.dimen.indeterminate_progress_alpha_23);
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
        int iFindIndex = findIndex(motionEvent.getPointerId(i));
        if (iFindIndex != -1) {
            this.mDownX[iFindIndex] = motionEvent.getX(i);
            this.mDownY[iFindIndex] = motionEvent.getY(i);
            this.mDownTime[iFindIndex] = motionEvent.getEventTime();
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

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.gesture.GesturePointerEventListener$start$2] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (ViewRootImpl.CLIENT_TRANSIENT) {
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.gesture.GesturePointerEventListener$$ExternalSyntheticLambda0
                /* JADX WARN: Removed duplicated region for block: B:104:0x014d  */
                /* JADX WARN: Removed duplicated region for block: B:81:0x00fc  */
                /* JADX WARN: Removed duplicated region for block: B:85:0x0103  */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object mo781invoke(Object obj) {
                    boolean z;
                    int iDetectSwipe;
                    GesturePointerEventListener.AnonymousClass2 anonymousClass2;
                    MotionEvent motionEvent = (MotionEvent) obj;
                    GesturePointerEventListener.Companion companion = GesturePointerEventListener.Companion;
                    GesturePointerEventListener gesturePointerEventListener = this.f$0;
                    gesturePointerEventListener.getClass();
                    if (motionEvent != null) {
                        if (motionEvent.isTouchEvent() && (anonymousClass2 = gesturePointerEventListener.mFlingGestureDetector) != null) {
                            anonymousClass2.onTouchEvent(motionEvent);
                        }
                        int actionMasked = motionEvent.getActionMasked();
                        if (actionMasked == 0) {
                            gesturePointerEventListener.mSwipeFireable = true;
                            gesturePointerEventListener.mDebugFireable = true;
                            gesturePointerEventListener.mDownPointers = 0;
                            gesturePointerEventListener.captureDown(motionEvent, 0);
                            if (gesturePointerEventListener.mMouseHoveringAtLeft) {
                                gesturePointerEventListener.mMouseHoveringAtLeft = false;
                            }
                            if (gesturePointerEventListener.mMouseHoveringAtTop) {
                                gesturePointerEventListener.mMouseHoveringAtTop = false;
                            }
                            if (gesturePointerEventListener.mMouseHoveringAtRight) {
                                gesturePointerEventListener.mMouseHoveringAtRight = false;
                            }
                            if (gesturePointerEventListener.mMouseHoveringAtBottom) {
                                gesturePointerEventListener.mMouseHoveringAtBottom = false;
                            }
                        } else if (actionMasked == 1) {
                            gesturePointerEventListener.mSwipeFireable = false;
                            gesturePointerEventListener.mDebugFireable = false;
                        } else if (actionMasked != 2) {
                            if (actionMasked != 3) {
                                if (actionMasked == 5) {
                                    gesturePointerEventListener.captureDown(motionEvent, motionEvent.getActionIndex());
                                    if (gesturePointerEventListener.mDebugFireable) {
                                        gesturePointerEventListener.mDebugFireable = motionEvent.getPointerCount() < 5;
                                    }
                                } else if (actionMasked == 7 && motionEvent.isFromSource(8194)) {
                                    float x = motionEvent.getX();
                                    float y = motionEvent.getY();
                                    boolean z2 = gesturePointerEventListener.mMouseHoveringAtLeft;
                                    if (!z2 && x == 0.0f) {
                                        gesturePointerEventListener.mMouseHoveringAtLeft = true;
                                    } else if (z2 && x > 0.0f) {
                                        gesturePointerEventListener.mMouseHoveringAtLeft = false;
                                    }
                                    boolean z3 = gesturePointerEventListener.mMouseHoveringAtTop;
                                    if (!z3 && y == 0.0f) {
                                        gesturePointerEventListener.mMouseHoveringAtTop = true;
                                    } else if (z3 && y > 0.0f) {
                                        gesturePointerEventListener.mMouseHoveringAtTop = false;
                                    }
                                    boolean z4 = gesturePointerEventListener.mMouseHoveringAtRight;
                                    if (!z4 && x >= -1) {
                                        gesturePointerEventListener.mMouseHoveringAtRight = true;
                                    } else if (z4 && x < -1) {
                                        gesturePointerEventListener.mMouseHoveringAtRight = false;
                                    }
                                    boolean z5 = gesturePointerEventListener.mMouseHoveringAtBottom;
                                    if (!z5 && y >= -1) {
                                        gesturePointerEventListener.mMouseHoveringAtBottom = true;
                                    } else if (z5 && y < -1) {
                                        gesturePointerEventListener.mMouseHoveringAtBottom = false;
                                    }
                                }
                            }
                        } else if (gesturePointerEventListener.mSwipeFireable) {
                            if (motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == 3.0f) {
                                float x2 = motionEvent.getX() - gesturePointerEventListener.mDownX[0];
                                float y2 = motionEvent.getY() - gesturePointerEventListener.mDownY[0];
                                if (Math.abs(x2) >= Math.abs(y2) ? Math.abs(x2) > gesturePointerEventListener.mSwipeDistanceThreshold : Math.abs(y2) > gesturePointerEventListener.mSwipeDistanceThreshold) {
                                    z = false;
                                }
                                gesturePointerEventListener.mSwipeFireable = z;
                                if (z) {
                                }
                            } else {
                                z = true;
                                gesturePointerEventListener.mSwipeFireable = z;
                                if (z) {
                                    int historySize = motionEvent.getHistorySize();
                                    int pointerCount = motionEvent.getPointerCount();
                                    int i = 0;
                                    loop0: while (true) {
                                        if (i >= pointerCount) {
                                            iDetectSwipe = 0;
                                            break;
                                        }
                                        int iFindIndex = gesturePointerEventListener.findIndex(motionEvent.getPointerId(i));
                                        if (iFindIndex != -1) {
                                            int i2 = 0;
                                            while (true) {
                                                if (i2 < historySize) {
                                                    iDetectSwipe = gesturePointerEventListener.detectSwipe(motionEvent.getHistoricalX(i, i2), motionEvent.getHistoricalY(i, i2), motionEvent.getHistoricalEventTime(i2), iFindIndex);
                                                    if (iDetectSwipe != 0) {
                                                        break loop0;
                                                    }
                                                    i2++;
                                                } else {
                                                    iDetectSwipe = gesturePointerEventListener.detectSwipe(motionEvent.getX(i), motionEvent.getY(i), motionEvent.getEventTime(), iFindIndex);
                                                    if (iDetectSwipe != 0) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        i++;
                                    }
                                    gesturePointerEventListener.mSwipeFireable = iDetectSwipe == 0;
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            GesturePointerEventDetector gesturePointerEventDetector = this.mGestureDetector;
            gesturePointerEventDetector.addOnGestureDetectedCallback("GesturePointerEventHandler", function1);
            gesturePointerEventDetector.startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            this.mFlingGestureDetector = new GestureDetector(this.mContext, new FlingGestureDetector(), this.mHandler) { // from class: com.android.systemui.statusbar.gesture.GesturePointerEventListener.start.2
            };
        }
    }
}
