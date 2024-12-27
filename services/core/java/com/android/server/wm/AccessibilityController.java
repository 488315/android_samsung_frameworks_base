package com.android.server.wm;

import android.R;
import android.accessibilityservice.AccessibilityTrace;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.BLASTBufferQueue;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.MagnificationSpec;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.TraceBuffer;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilityWindowManager;
import com.android.server.accessibility.magnification.FullScreenMagnificationController;
import com.android.server.accessibility.magnification.FullScreenMagnificationController$$ExternalSyntheticLambda4;
import com.android.server.accessibility.magnification.FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.AccessibilityController;
import com.android.server.wm.AccessibilityWindowsPopulator;
import com.android.server.wm.WindowManagerInternal;
import com.android.window.flags.Flags;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AccessibilityController {
    public final AccessibilityControllerInternalImpl mAccessibilityTracing;
    public final AccessibilityWindowsPopulator mAccessibilityWindowsPopulator;
    public final WindowManagerService mService;
    public static final Object STATIC_LOCK = new Object();
    public static final Rect EMPTY_RECT = new Rect();
    public static final float[] sTempFloats = new float[9];
    public final SparseArray mDisplayMagnifiers = new SparseArray();
    public final SparseArray mWindowsForAccessibilityObserver = new SparseArray();
    public final SparseArray mFocusedWindow = new SparseArray();
    public int mFocusedDisplay = -1;
    public final SparseBooleanArray mIsImeVisibleArray = new SparseBooleanArray();
    public boolean mAllObserversInitialized = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityControllerInternalImpl implements WindowManagerInternal.AccessibilityControllerInternal {
        public static AccessibilityControllerInternalImpl sInstance;
        public UiChangesForAccessibilityCallbacksDispatcher mCallbacksDispatcher;
        public volatile long mEnabledTracingFlags;
        public final Looper mLooper;
        public final AccessibilityTracing mTracing;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UiChangesForAccessibilityCallbacksDispatcher {
            public final AccessibilityControllerInternalImpl mAccessibilityTracing;
            public final WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks mCallbacks;
            public final Handler mHandler;

            public UiChangesForAccessibilityCallbacksDispatcher(AccessibilityControllerInternalImpl accessibilityControllerInternalImpl, Looper looper, WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks) {
                this.mAccessibilityTracing = accessibilityControllerInternalImpl;
                this.mCallbacks = uiChangesForAccessibilityCallbacks;
                this.mHandler = new Handler(looper);
            }
        }

        public AccessibilityControllerInternalImpl(WindowManagerService windowManagerService) {
            AccessibilityTracing accessibilityTracing;
            this.mLooper = windowManagerService.mH.getLooper();
            synchronized (AccessibilityController.STATIC_LOCK) {
                try {
                    if (AccessibilityTracing.sInstance == null) {
                        AccessibilityTracing.sInstance = new AccessibilityTracing(windowManagerService);
                    }
                    accessibilityTracing = AccessibilityTracing.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mTracing = accessibilityTracing;
            this.mEnabledTracingFlags = 0L;
        }

        public final boolean isTracingEnabled(long j) {
            return (j & this.mEnabledTracingFlags) != 0;
        }

        public final void logTrace(String str, long j) {
            logTrace(str, j, "");
        }

        public final void logTrace(String str, long j, String str2) {
            logTrace(str, j, str2, "".getBytes(), Binder.getCallingUid());
        }

        public final void logTrace(String str, long j, String str2, byte[] bArr, int i) {
            AccessibilityTracing accessibilityTracing = this.mTracing;
            HashSet hashSet = new HashSet(Arrays.asList("logTrace"));
            if (accessibilityTracing.mEnabled) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                hashSet.add("logState");
                accessibilityTracing.logState(str, j, str2, bArr, i, stackTrace, hashSet);
            }
        }

        public final void onRectangleOnScreenRequested(int i, Rect rect) {
            if (isTracingEnabled(2048L)) {
                Object obj = AccessibilityController.STATIC_LOCK;
                logTrace("AccessibilityController.onRectangleOnScreenRequested", 2048L, "rectangle={" + rect + "}");
            }
            UiChangesForAccessibilityCallbacksDispatcher uiChangesForAccessibilityCallbacksDispatcher = this.mCallbacksDispatcher;
            if (uiChangesForAccessibilityCallbacksDispatcher != null) {
                AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = uiChangesForAccessibilityCallbacksDispatcher.mAccessibilityTracing;
                if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                    accessibilityControllerInternalImpl.logTrace("WindowManager.onRectangleOnScreenRequested", 2048L, "rectangle={" + rect + "}");
                }
                final WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks = uiChangesForAccessibilityCallbacksDispatcher.mCallbacks;
                Objects.requireNonNull(uiChangesForAccessibilityCallbacks);
                uiChangesForAccessibilityCallbacksDispatcher.mHandler.sendMessage(PooledLambda.obtainMessage(new QuintConsumer() { // from class: com.android.server.wm.AccessibilityController$AccessibilityControllerInternalImpl$UiChangesForAccessibilityCallbacksDispatcher$$ExternalSyntheticLambda0
                    public final void accept(Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                        WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks.this.onRectangleOnScreenRequested(((Integer) obj2).intValue(), ((Integer) obj3).intValue(), ((Integer) obj4).intValue(), ((Integer) obj5).intValue(), ((Integer) obj6).intValue());
                    }
                }, Integer.valueOf(i), Integer.valueOf(rect.left), Integer.valueOf(rect.top), Integer.valueOf(rect.right), Integer.valueOf(rect.bottom)));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityTracing {
        public static AccessibilityTracing sInstance;
        public volatile boolean mEnabled;
        public final WindowManagerService mService;
        public final Object mLock = new Object();
        public final File mTraceFile = new File("/data/misc/a11ytrace/a11y_trace.winscope");
        public final TraceBuffer mBuffer = new TraceBuffer(12582912);
        public final DisplayMagnifier.MyHandler mHandler = new DisplayMagnifier.MyHandler(this, KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("AccessibilityTracing").getLooper(), 1);

        /* renamed from: -$$Nest$mtoStackTraceString, reason: not valid java name */
        public static String m1046$$Nest$mtoStackTraceString(AccessibilityTracing accessibilityTracing, StackTraceElement[] stackTraceElementArr, Set set) {
            accessibilityTracing.getClass();
            if (stackTraceElementArr == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            int i2 = -1;
            while (i < stackTraceElementArr.length) {
                Iterator it = set.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (stackTraceElementArr[i].toString().contains((String) it.next())) {
                        i2 = i;
                        break;
                    }
                }
                if (i2 >= 0) {
                    break;
                }
                i++;
            }
            if (i < stackTraceElementArr.length) {
                do {
                    i++;
                    if (i >= stackTraceElementArr.length) {
                        break;
                    }
                    Iterator it2 = set.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (stackTraceElementArr[i].toString().contains((String) it2.next())) {
                            i2 = i;
                            break;
                        }
                    }
                } while (i2 == i);
            }
            while (true) {
                i2++;
                if (i2 >= stackTraceElementArr.length) {
                    return sb.toString();
                }
                sb.append(stackTraceElementArr[i2].toString());
                sb.append("\n");
            }
        }

        /* renamed from: -$$Nest$mwriteTraceToFileInternal, reason: not valid java name */
        public static void m1047$$Nest$mwriteTraceToFileInternal(AccessibilityTracing accessibilityTracing) {
            accessibilityTracing.getClass();
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                protoOutputStream.write(1125281431553L, 4846245196254490945L);
                protoOutputStream.write(1125281431555L, TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - SystemClock.elapsedRealtimeNanos());
                accessibilityTracing.mBuffer.writeTraceToFile(accessibilityTracing.mTraceFile, protoOutputStream);
            } catch (IOException e) {
                Slog.e("AccessibilityTracing", "Unable to write buffer to file", e);
            }
        }

        public AccessibilityTracing(WindowManagerService windowManagerService) {
            this.mService = windowManagerService;
        }

        public final void log(String str, long j, String str2, byte[] bArr, int i, StackTraceElement[] stackTraceElementArr, long j2, String str3, String str4, Set set) {
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argl1 = j2;
            obtain.argl2 = j;
            obtain.arg1 = str;
            obtain.arg2 = str3;
            obtain.arg3 = str4;
            obtain.arg4 = set;
            obtain.arg5 = str2;
            obtain.arg6 = stackTraceElementArr;
            obtain.arg7 = bArr;
            this.mHandler.obtainMessage(1, i, 0, obtain).sendToTarget();
        }

        public final void logState(String str, long j, String str2, byte[] bArr, int i, StackTraceElement[] stackTraceElementArr, Set set) {
            if (this.mEnabled) {
                log(str, j, str2, bArr, i, stackTraceElementArr, SystemClock.elapsedRealtimeNanos(), Process.myPid() + ":" + Application.getProcessName(), Thread.currentThread().getId() + ":" + Thread.currentThread().getName(), set);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayMagnifier {
        public final AccessibilityControllerInternalImpl mAccessibilityTracing;
        public final WindowManagerInternal.MagnificationCallbacks mCallbacks;
        public final Path mCircularPath;
        public final Display mDisplay;
        public final DisplayContent mDisplayContent;
        public final Context mDisplayContext;
        public final MyHandler mHandler;
        public final long mLongAnimationDuration;
        public final MagnifiedViewport mMagnifiedViewport;
        public final Point mScreenSize;
        public final WindowManagerService mService;
        public final Matrix mTempMatrix;
        public final SparseArray mTempWindowStates;
        public final UserContextChangedNotifier mUserContextChangedNotifier;
        public final Rect mTempRect1 = new Rect();
        public final Rect mTempRect2 = new Rect();
        public final Region mTempRegion1 = new Region();
        public final Region mTempRegion2 = new Region();
        public final Region mTempRegion3 = new Region();
        public final Region mTempRegion4 = new Region();
        public boolean mIsFullscreenMagnificationActivated = false;
        public final Region mMagnificationRegion = new Region();
        public final Region mOldMagnificationRegion = new Region();
        public final MagnificationSpec mMagnificationSpec = new MagnificationSpec();
        public int mTempLayer = 0;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MagnifiedViewport {
            public final float mBorderWidth;
            public final int mDrawBorderInset;
            public boolean mFullRedrawNeeded;
            public final int mHalfBorderWidth;
            public final ViewportWindow mWindow;

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            public final class ViewportWindow implements Runnable {
                public int mAlpha;
                public final AnimationController mAnimationController;
                public final BLASTBufferQueue mBlastBufferQueue;
                public volatile boolean mInvalidated;
                public boolean mLastSurfaceShown;
                public int mPreviousAlpha;
                public boolean mShown;
                public final Surface mSurface;
                public final SurfaceControl mSurfaceControl;
                public final SurfaceControl.Transaction mTransaction;
                public final Region mBounds = new Region();
                public final Rect mDirtyRect = new Rect();
                public final Paint mPaint = new Paint();

                /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
                public final class AnimationController extends Handler {
                    public final ValueAnimator mShowHideFrameAnimator;

                    public AnimationController(Context context, Looper looper) {
                        super(looper);
                        ObjectAnimator ofInt = ObjectAnimator.ofInt(ViewportWindow.this, "alpha", 0, IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                        this.mShowHideFrameAnimator = ofInt;
                        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(2.5f);
                        long integer = context.getResources().getInteger(R.integer.config_longAnimTime);
                        ofInt.setInterpolator(decelerateInterpolator);
                        ofInt.setDuration(integer);
                    }

                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what != 1) {
                            return;
                        }
                        boolean z = message.arg1 == 1;
                        if (message.arg2 != 1) {
                            this.mShowHideFrameAnimator.cancel();
                            ViewportWindow viewportWindow = ViewportWindow.this;
                            if (z) {
                                viewportWindow.setAlpha(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                                return;
                            } else {
                                viewportWindow.setAlpha(0);
                                return;
                            }
                        }
                        if (this.mShowHideFrameAnimator.isRunning()) {
                            this.mShowHideFrameAnimator.reverse();
                        } else if (z) {
                            this.mShowHideFrameAnimator.start();
                        } else {
                            this.mShowHideFrameAnimator.reverse();
                        }
                    }
                }

                public ViewportWindow(Context context) {
                    SurfaceControl surfaceControl;
                    try {
                        surfaceControl = DisplayMagnifier.this.mDisplayContent.makeOverlay().setName("Magnification Overlay").setBLASTLayer().setFormat(-3).setCallsite("ViewportWindow").build();
                    } catch (Surface.OutOfResourcesException unused) {
                        surfaceControl = null;
                    }
                    this.mSurfaceControl = surfaceControl;
                    DisplayMagnifier displayMagnifier = DisplayMagnifier.this;
                    displayMagnifier.mDisplay.getRealSize(displayMagnifier.mScreenSize);
                    Point point = DisplayMagnifier.this.mScreenSize;
                    BLASTBufferQueue bLASTBufferQueue = new BLASTBufferQueue("Magnification Overlay", surfaceControl, point.x, point.y, 1);
                    this.mBlastBufferQueue = bLASTBufferQueue;
                    SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) DisplayMagnifier.this.mService.mTransactionFactory.get();
                    DisplayMagnifier.this.mService.mPolicy.getClass();
                    transaction.setLayer(surfaceControl, WindowManagerPolicy.getWindowLayerFromTypeLw(2027) * 10000).setPosition(surfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    InputMonitor.setTrustedOverlayInputInfo(surfaceControl, transaction, DisplayMagnifier.this.mDisplayContent.mDisplayId, "Magnification Overlay");
                    transaction.apply();
                    this.mTransaction = transaction;
                    this.mSurface = bLASTBufferQueue.createSurface();
                    this.mAnimationController = new AnimationController(context, DisplayMagnifier.this.mService.mH.getLooper());
                    TypedValue typedValue = new TypedValue();
                    context.getTheme().resolveAttribute(R.attr.colorActivatedHighlight, typedValue, true);
                    context.getColor(typedValue.resourceId);
                    int color = context.getColor(R.color.tertiary_text_holo_light);
                    this.mPaint.setStyle(Paint.Style.STROKE);
                    this.mPaint.setStrokeWidth(MagnifiedViewport.this.mBorderWidth);
                    this.mPaint.setColor(color);
                    this.mInvalidated = true;
                }

                public final void invalidate(Rect rect) {
                    if (rect != null) {
                        this.mDirtyRect.set(rect);
                    } else {
                        this.mDirtyRect.setEmpty();
                    }
                    this.mInvalidated = true;
                    DisplayMagnifier.this.mService.scheduleAnimationLocked();
                }

                /* JADX WARN: Removed duplicated region for block: B:27:0x0055 A[Catch: all -> 0x002f, TryCatch #1 {all -> 0x002f, blocks: (B:4:0x000c, B:6:0x0016, B:8:0x001e, B:9:0x0032, B:13:0x0038, B:15:0x003c, B:19:0x0043, B:21:0x004a, B:27:0x0055, B:29:0x0064, B:30:0x006b, B:31:0x007f), top: B:3:0x000c }] */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00aa  */
                /* JADX WARN: Removed duplicated region for block: B:48:0x00ac  */
                /* JADX WARN: Removed duplicated region for block: B:49:0x0085 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:56:0x007d  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 218
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow.run():void");
                }

                public final void setAlpha(int i) {
                    WindowManagerGlobalLock windowManagerGlobalLock = DisplayMagnifier.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (this.mAlpha == i) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return;
                            }
                            this.mAlpha = i;
                            invalidate(null);
                            WindowManagerService.resetPriorityAfterLockedSection();
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                }
            }

            public MagnifiedViewport() {
                float dimension = DisplayMagnifier.this.mDisplayContext.getResources().getDimension(R.dimen.accessibility_magnification_thumbnail_container_stroke_width);
                this.mBorderWidth = dimension;
                this.mHalfBorderWidth = (int) Math.ceil(dimension / 2.0f);
                this.mDrawBorderInset = ((int) dimension) / 2;
                this.mWindow = new ViewportWindow(DisplayMagnifier.this.mDisplayContext);
            }

            public final void setMagnifiedRegionBorderShown(boolean z, boolean z2) {
                ViewportWindow viewportWindow = this.mWindow;
                WindowManagerGlobalLock windowManagerGlobalLock = DisplayMagnifier.this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        if (viewportWindow.mShown == z) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            z = false;
                        } else {
                            viewportWindow.mShown = z;
                            viewportWindow.mAnimationController.obtainMessage(1, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                if (z) {
                    this.mFullRedrawNeeded = true;
                    DisplayMagnifier.this.mOldMagnificationRegion.set(0, 0, 0, 0);
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MyHandler extends Handler {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ Object this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public MyHandler(WindowsForAccessibilityObserver windowsForAccessibilityObserver, Looper looper) {
                super(looper, null, false);
                this.$r8$classId = 2;
                this.this$0 = windowsForAccessibilityObserver;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public /* synthetic */ MyHandler(Object obj, Looper looper, int i) {
                super(looper);
                this.$r8$classId = i;
                this.this$0 = obj;
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                switch (this.$r8$classId) {
                    case 0:
                        int i = message.what;
                        if (i == 1) {
                            Region region = (Region) ((SomeArgs) message.obj).arg1;
                            FullScreenMagnificationController.DisplayMagnification displayMagnification = (FullScreenMagnificationController.DisplayMagnification) ((DisplayMagnifier) this.this$0).mCallbacks;
                            displayMagnification.getClass();
                            FullScreenMagnificationController.this.mControllerCtx.mHandler.sendMessage(PooledLambda.obtainMessage(new FullScreenMagnificationController$$ExternalSyntheticLambda4(2), displayMagnification, Region.obtain(region)));
                            region.recycle();
                            return;
                        }
                        if (i == 3) {
                            ((FullScreenMagnificationController.DisplayMagnification) ((DisplayMagnifier) this.this$0).mCallbacks).onUserContextChanged();
                            return;
                        }
                        if (i == 4) {
                            ((FullScreenMagnificationController.DisplayMagnification) ((DisplayMagnifier) this.this$0).mCallbacks).onUserContextChanged();
                            return;
                        }
                        if (i == 5) {
                            WindowManagerGlobalLock windowManagerGlobalLock = ((DisplayMagnifier) this.this$0).mService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    if (((DisplayMagnifier) this.this$0).isFullscreenMagnificationActivated()) {
                                        if (!Flags.alwaysDrawMagnificationFullscreenBorder()) {
                                            ((DisplayMagnifier) this.this$0).mMagnifiedViewport.setMagnifiedRegionBorderShown(true, true);
                                        }
                                        ((DisplayMagnifier) this.this$0).mService.scheduleAnimationLocked();
                                    }
                                } finally {
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return;
                        }
                        if (i != 6) {
                            return;
                        }
                        boolean z = message.arg1 == 1;
                        FullScreenMagnificationController.DisplayMagnification displayMagnification2 = (FullScreenMagnificationController.DisplayMagnification) ((DisplayMagnifier) this.this$0).mCallbacks;
                        displayMagnification2.getClass();
                        FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2 fullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2 = new FullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2(1);
                        Integer valueOf = Integer.valueOf(displayMagnification2.mDisplayId);
                        Boolean valueOf2 = Boolean.valueOf(z);
                        FullScreenMagnificationController fullScreenMagnificationController = FullScreenMagnificationController.this;
                        fullScreenMagnificationController.mControllerCtx.mHandler.sendMessage(PooledLambda.obtainMessage(fullScreenMagnificationController$DisplayMagnification$$ExternalSyntheticLambda2, fullScreenMagnificationController, valueOf, valueOf2));
                        return;
                    case 1:
                        int i2 = message.what;
                        if (i2 != 1) {
                            if (i2 != 2) {
                                return;
                            }
                            synchronized (((AccessibilityTracing) this.this$0).mLock) {
                                AccessibilityTracing.m1047$$Nest$mwriteTraceToFileInternal((AccessibilityTracing) this.this$0);
                            }
                            return;
                        }
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        try {
                            ProtoOutputStream protoOutputStream = new ProtoOutputStream();
                            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                            long start = protoOutputStream.start(2246267895810L);
                            long j = someArgs.argl1;
                            long time = new Date().getTime() - ((SystemClock.elapsedRealtimeNanos() - j) / 1000000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
                            protoOutputStream.write(1125281431553L, j);
                            protoOutputStream.write(1138166333442L, simpleDateFormat.format(Long.valueOf(time)).toString());
                            Iterator it = AccessibilityTrace.getNamesOfLoggingTypes(someArgs.argl2).iterator();
                            while (it.hasNext()) {
                                protoOutputStream.write(2237677961219L, (String) it.next());
                            }
                            protoOutputStream.write(1138166333446L, (String) someArgs.arg1);
                            protoOutputStream.write(1138166333444L, (String) someArgs.arg2);
                            protoOutputStream.write(1138166333445L, (String) someArgs.arg3);
                            protoOutputStream.write(1138166333447L, packageManagerInternal.getNameForUid(message.arg1));
                            protoOutputStream.write(1138166333448L, (String) someArgs.arg5);
                            protoOutputStream.write(1138166333449L, AccessibilityTracing.m1046$$Nest$mtoStackTraceString((AccessibilityTracing) this.this$0, (StackTraceElement[]) someArgs.arg6, (Set) someArgs.arg4));
                            protoOutputStream.write(1146756268042L, (byte[]) someArgs.arg7);
                            long start2 = protoOutputStream.start(1146756268043L);
                            WindowManagerGlobalLock windowManagerGlobalLock2 = ((AccessibilityTracing) this.this$0).mService.mGlobalLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock2) {
                                try {
                                    ((AccessibilityTracing) this.this$0).mService.dumpDebugLocked(0, protoOutputStream);
                                } finally {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                            protoOutputStream.end(start2);
                            Pair appProfileStatsForDebugging = ((AccessibilityTracing) this.this$0).mService.mAmInternal.getAppProfileStatsForDebugging(j, 5);
                            protoOutputStream.write(1138166333452L, ((String) appProfileStatsForDebugging.first) + ((String) appProfileStatsForDebugging.second));
                            protoOutputStream.end(start);
                            synchronized (((AccessibilityTracing) this.this$0).mLock) {
                                ((AccessibilityTracing) this.this$0).mBuffer.add(protoOutputStream);
                            }
                            return;
                        } catch (Exception e) {
                            Slog.e("AccessibilityTracing", "Exception while tracing state", e);
                            return;
                        }
                    default:
                        if (message.what != 1) {
                            return;
                        }
                        ((WindowsForAccessibilityObserver) this.this$0).computeChangedWindows(false);
                        return;
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UserContextChangedNotifier {
            public final Handler mHandler;
            public boolean mHasDelayedNotificationForRecentsToFrontTransition;

            public UserContextChangedNotifier(MyHandler myHandler) {
                this.mHandler = myHandler;
            }

            public final void sendUserContextChangedNotification() {
                if (DisplayMagnifier.this.isFullscreenMagnificationActivated() && AccessibilityUtils.getVisiblityShortcutDialog()) {
                    AccessibilityUtils.setVisibilityShortcutDialog(false);
                } else {
                    this.mHasDelayedNotificationForRecentsToFrontTransition = false;
                    this.mHandler.sendEmptyMessage(3);
                }
            }
        }

        public DisplayMagnifier(WindowManagerService windowManagerService, DisplayContent displayContent, Display display, WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
            Point point = new Point();
            this.mScreenSize = point;
            this.mTempWindowStates = new SparseArray();
            new RectF();
            this.mTempMatrix = new Matrix();
            Context createDisplayContext = windowManagerService.mContext.createDisplayContext(display);
            this.mDisplayContext = createDisplayContext;
            this.mService = windowManagerService;
            this.mCallbacks = magnificationCallbacks;
            this.mDisplayContent = displayContent;
            this.mDisplay = display;
            MyHandler myHandler = new MyHandler(this, windowManagerService.mH.getLooper(), 0);
            this.mHandler = myHandler;
            this.mUserContextChangedNotifier = new UserContextChangedNotifier(myHandler);
            this.mMagnifiedViewport = Flags.alwaysDrawMagnificationFullscreenBorder() ? null : new MagnifiedViewport();
            AccessibilityControllerInternalImpl accessibilityControllerInternal = AccessibilityController.getAccessibilityControllerInternal(windowManagerService);
            this.mAccessibilityTracing = accessibilityControllerInternal;
            this.mLongAnimationDuration = createDisplayContext.getResources().getInteger(R.integer.config_longAnimTime);
            if (createDisplayContext.getResources().getConfiguration().isScreenRound()) {
                Path path = new Path();
                this.mCircularPath = path;
                getDisplaySizeLocked(point);
                float f = point.x / 2;
                path.addCircle(f, f, f, Path.Direction.CW);
            } else {
                this.mCircularPath = null;
            }
            if (accessibilityControllerInternal.isTracingEnabled(2048L)) {
                accessibilityControllerInternal.logTrace("WindowManager.DisplayMagnifier.constructor", 2048L, "windowManagerService={" + windowManagerService + "}; displayContent={" + displayContent + "}; display={" + display + "}; callbacks={" + magnificationCallbacks + "}");
            }
            recomputeBounds();
        }

        public final void getDisplaySizeLocked(Point point) {
            Rect bounds = this.mDisplayContent.getConfiguration().windowConfiguration.getBounds();
            point.set(bounds.width(), bounds.height());
        }

        public final boolean isFullscreenMagnificationActivated() {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl.logTrace("WindowManager.isFullscreenMagnificationActivated", 2048L);
            }
            return this.mIsFullscreenMagnificationActivated;
        }

        public final void notifyImeWindowVisibilityChanged(boolean z) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl.logTrace("WindowManager.notifyImeWindowVisibilityChanged", 2048L, "shown=" + z);
            }
            this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
        }

        public final void recomputeBounds() {
            Region region;
            boolean isEmpty;
            Region region2;
            getDisplaySizeLocked(this.mScreenSize);
            Point point = this.mScreenSize;
            int i = point.x;
            int i2 = point.y;
            this.mMagnificationRegion.set(0, 0, 0, 0);
            Region region3 = this.mTempRegion1;
            region3.set(0, 0, i, i2);
            Path path = this.mCircularPath;
            if (path != null) {
                region3.setPath(path, region3);
            }
            Region region4 = this.mTempRegion4;
            region4.set(0, 0, 0, 0);
            final SparseArray sparseArray = this.mTempWindowStates;
            sparseArray.clear();
            this.mTempLayer = 0;
            this.mDisplayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.AccessibilityController$DisplayMagnifier$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AccessibilityController.DisplayMagnifier displayMagnifier = AccessibilityController.DisplayMagnifier.this;
                    SparseArray sparseArray2 = sparseArray;
                    WindowState windowState = (WindowState) obj;
                    displayMagnifier.getClass();
                    if (windowState.isOnScreen() && windowState.isVisible() && windowState.mAttrs.alpha != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        int i3 = displayMagnifier.mTempLayer + 1;
                        displayMagnifier.mTempLayer = i3;
                        sparseArray2.put(i3, windowState);
                    }
                }
            }, false);
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.isDefaultDisplay && displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                region = new Region();
                this.mDisplayContent.getStableRect(this.mTempRect1);
                region.set(this.mTempRect1);
            } else {
                region = null;
            }
            Region region5 = region;
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                WindowState windowState = (WindowState) sparseArray.valueAt(size);
                WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                int i3 = layoutParams.type;
                if (i3 != 2027 && i3 != 2039) {
                    int i4 = layoutParams.privateFlags;
                    if ((2097152 & i4) == 0 && (i4 & 1048576) == 0) {
                        Matrix matrix = this.mTempMatrix;
                        float[] fArr = AccessibilityController.sTempFloats;
                        windowState.getTransformationMatrix(fArr, matrix);
                        Region region6 = this.mTempRegion3;
                        windowState.getTouchableRegion(region6);
                        Region region7 = this.mTempRegion2;
                        Rect rect = windowState.mWindowFrames.mFrame;
                        region6.translate(-rect.left, -rect.top);
                        matrix.getValues(fArr);
                        region6.scale(fArr[0]);
                        region6.translate((int) fArr[2], (int) fArr[5]);
                        region7.set(region6);
                        Region region8 = this.mTempRegion3;
                        region8.set(this.mMagnificationRegion);
                        Region.Op op = Region.Op.UNION;
                        region8.op(region4, op);
                        Region.Op op2 = Region.Op.DIFFERENCE;
                        region7.op(region8, op2);
                        if (windowState.shouldMagnify()) {
                            this.mMagnificationRegion.op(region7, op);
                            this.mMagnificationRegion.op(region3, Region.Op.INTERSECT);
                        } else {
                            region4.op(region7, op);
                            region3.op(region7, op2);
                        }
                        if (region5 != null && windowState.inSplitScreenWindowingMode()) {
                            region5.op(region7, op2);
                        }
                        Region region9 = this.mTempRegion3;
                        if (windowState.mAttrs.type != 2019) {
                            isEmpty = false;
                        } else {
                            windowState.getTouchableRegion(region9);
                            isEmpty = region9.isEmpty();
                        }
                        if (isEmpty) {
                            InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
                            Rect frame = controllableInsetProvider != null ? controllableInsetProvider.mSource.getFrame() : AccessibilityController.EMPTY_RECT;
                            region4.op(frame, op);
                            region3.op(frame, op2);
                        }
                        if (windowState.areAppWindowBoundsLetterboxed()) {
                            ActivityRecord activityRecord = windowState.mActivityRecord;
                            if (activityRecord == null) {
                                region2 = new Region();
                            } else {
                                Rect bounds = windowState.getBounds();
                                Rect letterboxInsets = activityRecord.getLetterboxInsets();
                                Rect copyOrNull = Rect.copyOrNull(bounds);
                                copyOrNull.inset(Insets.subtract(Insets.NONE, Insets.of(letterboxInsets)));
                                Region region10 = new Region();
                                region10.set(copyOrNull);
                                region10.op(bounds, op2);
                                region2 = region10;
                            }
                            region4.op(region2, op);
                            region3.op(region2, op2);
                        }
                        Region region11 = this.mTempRegion2;
                        region11.set(this.mMagnificationRegion);
                        region11.op(region4, op);
                        region11.op(0, 0, i, i2, Region.Op.INTERSECT);
                        if (region11.isRect()) {
                            Rect rect2 = this.mTempRect1;
                            region11.getBounds(rect2);
                            if (rect2.width() == i && rect2.height() == i2) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            if (region5 != null) {
                region5.op(region4, Region.Op.DIFFERENCE);
                this.mMagnificationRegion.op(region5, Region.Op.UNION);
            }
            sparseArray.clear();
            if (!Flags.alwaysDrawMagnificationFullscreenBorder()) {
                MagnifiedViewport magnifiedViewport = this.mMagnifiedViewport;
                Region region12 = DisplayMagnifier.this.mMagnificationRegion;
                int i5 = magnifiedViewport.mDrawBorderInset;
                region12.op(i5, i5, i - i5, i2 - i5, Region.Op.INTERSECT);
            }
            if (!this.mOldMagnificationRegion.equals(this.mMagnificationRegion)) {
                if (!Flags.alwaysDrawMagnificationFullscreenBorder()) {
                    MagnifiedViewport magnifiedViewport2 = this.mMagnifiedViewport;
                    MagnifiedViewport.ViewportWindow viewportWindow = magnifiedViewport2.mWindow;
                    Region region13 = DisplayMagnifier.this.mMagnificationRegion;
                    WindowManagerGlobalLock windowManagerGlobalLock = DisplayMagnifier.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            if (viewportWindow.mBounds.equals(region13)) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                            } else {
                                viewportWindow.mBounds.set(region13);
                                viewportWindow.invalidate(viewportWindow.mDirtyRect);
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    DisplayMagnifier displayMagnifier = DisplayMagnifier.this;
                    Rect rect3 = displayMagnifier.mTempRect1;
                    if (magnifiedViewport2.mFullRedrawNeeded) {
                        magnifiedViewport2.mFullRedrawNeeded = false;
                        int i6 = magnifiedViewport2.mDrawBorderInset;
                        rect3.set(i6, i6, i - i6, i2 - i6);
                        magnifiedViewport2.mWindow.invalidate(rect3);
                    } else {
                        Region region14 = displayMagnifier.mTempRegion3;
                        region14.set(displayMagnifier.mMagnificationRegion);
                        region14.op(DisplayMagnifier.this.mOldMagnificationRegion, Region.Op.XOR);
                        region14.getBounds(rect3);
                        magnifiedViewport2.mWindow.invalidate(rect3);
                    }
                }
                this.mOldMagnificationRegion.set(this.mMagnificationRegion);
                SomeArgs obtain = SomeArgs.obtain();
                obtain.arg1 = Region.obtain(this.mMagnificationRegion);
                this.mHandler.obtainMessage(1, obtain).sendToTarget();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WindowsForAccessibilityObserver {
        public final AccessibilityWindowsPopulator mA11yWindowsPopulator;
        public final AccessibilityControllerInternalImpl mAccessibilityTracing;
        public final WindowManagerInternal.WindowsForAccessibilityCallback mCallback;
        public final int mDisplayId;
        public final DisplayMagnifier.MyHandler mHandler;
        public boolean mInitialized;
        public final long mRecurringAccessibilityEventsIntervalMillis;
        public final WindowManagerService mService;

        public WindowsForAccessibilityObserver(WindowManagerService windowManagerService, int i, WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback, AccessibilityWindowsPopulator accessibilityWindowsPopulator) {
            new ArraySet();
            new Region();
            new Region();
            this.mService = windowManagerService;
            this.mCallback = windowsForAccessibilityCallback;
            this.mDisplayId = i;
            this.mHandler = new DisplayMagnifier.MyHandler(this, windowManagerService.mH.getLooper());
            this.mAccessibilityTracing = AccessibilityController.getAccessibilityControllerInternal(windowManagerService);
            this.mRecurringAccessibilityEventsIntervalMillis = ViewConfiguration.getSendRecurringAccessibilityEventsInterval();
            this.mA11yWindowsPopulator = accessibilityWindowsPopulator;
            computeChangedWindows(true);
        }

        public final void computeChangedWindows(boolean z) {
            WindowState windowState;
            if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
                this.mAccessibilityTracing.logTrace("WindowManager.computeChangedWindows", 1024L, "forceSend=" + z);
            }
            ArrayList arrayList = new ArrayList();
            Point point = new Point();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    WindowManagerService windowManagerService = this.mService;
                    RecentsAnimationController recentsAnimationController = windowManagerService.mRecentsAnimationController;
                    if (recentsAnimationController != null) {
                        ActivityRecord activityRecord = recentsAnimationController.mTargetActivityRecord;
                        windowState = activityRecord == null ? null : activityRecord.findMainWindow(true);
                    } else {
                        windowState = windowManagerService.mRoot.getTopFocusedDisplayContent().mCurrentFocus;
                    }
                    if (windowState == null) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    DisplayContent displayContent = this.mService.mRoot.getDisplayContent(this.mDisplayId);
                    if (displayContent == null) {
                        Slog.w("WindowManager", "display content is null, should be created later");
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    displayContent.mDisplay.getRealSize(point);
                    this.mA11yWindowsPopulator.populateVisibleWindowsOnScreenLocked(this.mDisplayId, arrayList);
                    com.android.server.accessibility.Flags.computeWindowChangesOnA11yV2();
                    int i = this.mService.mRoot.getTopFocusedDisplayContent().mDisplayId;
                    IBinder asBinder = windowState.mClient.asBinder();
                    WindowManagerService.resetPriorityAfterLockedSection();
                    com.android.server.accessibility.Flags.computeWindowChangesOnA11yV2();
                    AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = (AccessibilityWindowManager.DisplayWindowsObserver) this.mCallback;
                    synchronized (AccessibilityWindowManager.this.mLock) {
                        List createWindowInfoListLocked = displayWindowsObserver.createWindowInfoListLocked(point, arrayList);
                        synchronized (AccessibilityWindowManager.this.mLock) {
                            try {
                                com.android.server.accessibility.Flags.computeWindowChangesOnA11yV2();
                                if (displayWindowsObserver.shouldUpdateWindowsLocked(createWindowInfoListLocked, z)) {
                                    AccessibilityWindowManager accessibilityWindowManager = AccessibilityWindowManager.this;
                                    accessibilityWindowManager.mTopFocusedDisplayId = i;
                                    AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver2 = (AccessibilityWindowManager.DisplayWindowsObserver) accessibilityWindowManager.mDisplayWindowsObservers.get(i);
                                    if (displayWindowsObserver2 == null || !displayWindowsObserver2.mIsProxy) {
                                        AccessibilityWindowManager.this.mLastNonProxyTopFocusedDisplayId = i;
                                    }
                                    AccessibilityWindowManager.this.mTopFocusedWindowToken = asBinder;
                                    displayWindowsObserver.cacheWindows(createWindowInfoListLocked);
                                    displayWindowsObserver.updateWindowsLocked(((AccessibilityManagerService) AccessibilityWindowManager.this.mAccessibilityUserManager).mCurrentUserId, createWindowInfoListLocked);
                                    AccessibilityWindowManager.this.mLock.notifyAll();
                                }
                            } finally {
                            }
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((AccessibilityWindowsPopulator.AccessibilityWindow) it.next()).mWindowInfo.recycle();
                    }
                    this.mInitialized = true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        public final void performComputeChangedWindows(boolean z) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(1024L)) {
                accessibilityControllerInternalImpl.logTrace("WindowManager.performComputeChangedWindows", 1024L, "forceSend=" + z);
            }
            this.mHandler.removeMessages(1);
            computeChangedWindows(z);
        }

        public final void scheduleComputeChangedWindows() {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(1024L)) {
                accessibilityControllerInternalImpl.logTrace("WindowManager.scheduleComputeChangedWindows", 1024L);
            }
            DisplayMagnifier.MyHandler myHandler = this.mHandler;
            if (myHandler.hasMessages(1)) {
                return;
            }
            myHandler.sendEmptyMessageDelayed(1, this.mRecurringAccessibilityEventsIntervalMillis);
        }

        public final String toString() {
            return "WindowsForAccessibilityObserver{mDisplayId=" + this.mDisplayId + ", mInitialized=" + this.mInitialized + '}';
        }
    }

    public AccessibilityController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
        this.mAccessibilityTracing = getAccessibilityControllerInternal(windowManagerService);
        this.mAccessibilityWindowsPopulator = new AccessibilityWindowsPopulator(windowManagerService, this);
    }

    public static AccessibilityControllerInternalImpl getAccessibilityControllerInternal(WindowManagerService windowManagerService) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl;
        synchronized (STATIC_LOCK) {
            try {
                if (AccessibilityControllerInternalImpl.sInstance == null) {
                    AccessibilityControllerInternalImpl.sInstance = new AccessibilityControllerInternalImpl(windowManagerService);
                }
                accessibilityControllerInternalImpl = AccessibilityControllerInternalImpl.sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return accessibilityControllerInternalImpl;
    }

    public Surface forceShowMagnifierSurface(int i) {
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier == null) {
            return null;
        }
        DisplayMagnifier.MagnifiedViewport magnifiedViewport = displayMagnifier.mMagnifiedViewport;
        magnifiedViewport.mWindow.setAlpha(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        return magnifiedViewport.mWindow.mSurface;
    }

    public final void getMagnificationRegion(int i, Region region) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.getMagnificationRegion", 2048L, "displayId=" + i + "; outMagnificationRegion={" + region + "}");
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl2.logTrace("WindowManager.getMagnificationRegion", 2048L, "outMagnificationRegion={" + region + "}");
            }
            displayMagnifier.recomputeBounds();
            region.set(displayMagnifier.mMagnificationRegion);
        }
    }

    public final MagnificationSpec getMagnificationSpecForWindow(WindowState windowState) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.getMagnificationSpecForWindow", 2048L, "windowState={" + windowState + "}");
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(windowState.getDisplayId());
        if (displayMagnifier == null) {
            return null;
        }
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl2.logTrace("WindowManager.getMagnificationSpecForWindow", 2048L, "windowState={" + windowState + "}");
        }
        MagnificationSpec magnificationSpec = displayMagnifier.mMagnificationSpec;
        if (magnificationSpec == null || magnificationSpec.isNop() || windowState.shouldMagnify()) {
            return displayMagnifier.mMagnificationSpec;
        }
        return null;
    }

    public final boolean hasCallbacks() {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(3072L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.hasCallbacks", 3072L);
        }
        return this.mDisplayMagnifiers.size() > 0 || this.mWindowsForAccessibilityObserver.size() > 0;
    }

    public final void onFocusChanged(InputTarget inputTarget, InputTarget inputTarget2) {
        if (inputTarget != null) {
            this.mFocusedWindow.remove(inputTarget.getDisplayId());
            DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(inputTarget.getDisplayId());
            if (displayMagnifier != null && displayMagnifier.isFullscreenMagnificationActivated()) {
                DisplayMagnifier.UserContextChangedNotifier userContextChangedNotifier = displayMagnifier.mUserContextChangedNotifier;
                if (userContextChangedNotifier.mHasDelayedNotificationForRecentsToFrontTransition) {
                    userContextChangedNotifier.sendUserContextChangedNotification();
                }
            }
        }
        if (inputTarget2 != null) {
            this.mFocusedWindow.put(inputTarget2.getDisplayId(), inputTarget2.getWindowToken());
        }
    }

    public final void onSomeWindowResizedOrMovedWithCallingUid(int i, int... iArr) {
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace("AccessibilityController.onSomeWindowResizedOrMoved", 1024L, "displayIds={" + Arrays.toString(iArr) + "}", "".getBytes(), i);
        }
        for (int i2 : iArr) {
            WindowsForAccessibilityObserver windowsForAccessibilityObserver = (WindowsForAccessibilityObserver) this.mWindowsForAccessibilityObserver.get(i2);
            if (windowsForAccessibilityObserver != null) {
                windowsForAccessibilityObserver.scheduleComputeChangedWindows();
            }
        }
    }

    public final void onWMTransition(int i, int i2, int i3) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "displayId=", "; type=", "; flags=");
            m.append(i3);
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.onWMTransition", 2048L, m.toString());
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
                StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "displayId=", "; type=", "; flags=");
                m2.append(i3);
                accessibilityControllerInternalImpl2.logTrace("WindowManager.onWMTransition", 2048L, m2.toString());
            }
            if (displayMagnifier.isFullscreenMagnificationActivated()) {
                if (i2 == 1 || i2 == 2 || i2 == 3 || i2 == 4) {
                    DisplayMagnifier.UserContextChangedNotifier userContextChangedNotifier = displayMagnifier.mUserContextChangedNotifier;
                    userContextChangedNotifier.getClass();
                    if (Flags.delayNotificationToMagnificationWhenRecentsWindowToFrontTransition() && i2 == 3 && (i3 & 128) != 0) {
                        userContextChangedNotifier.mHasDelayedNotificationForRecentsToFrontTransition = true;
                    } else {
                        userContextChangedNotifier.sendUserContextChangedNotification();
                    }
                }
            }
        }
    }

    public final void onWindowTransition(WindowState windowState, int i) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(3072L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.onWindowTransition", 3072L, "windowState={" + windowState + "}; transition=" + i);
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(windowState.getDisplayId());
        if (displayMagnifier != null) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl2.logTrace("WindowManager.onWindowTransition", 2048L, "windowState={" + windowState + "}; transition=" + i);
            }
            if (displayMagnifier.isFullscreenMagnificationActivated() && windowState.shouldMagnify()) {
                DisplayMagnifier.UserContextChangedNotifier userContextChangedNotifier = displayMagnifier.mUserContextChangedNotifier;
                userContextChangedNotifier.getClass();
                if (i == 2 && windowState.isActivityTypeHomeOrRecents() && userContextChangedNotifier.mHasDelayedNotificationForRecentsToFrontTransition) {
                    userContextChangedNotifier.mHasDelayedNotificationForRecentsToFrontTransition = false;
                }
                int i2 = windowState.mAttrs.type;
                if (i == 1 || i == 3) {
                    if (i2 != 2 && i2 != 4 && i2 != 1005 && i2 != 2020 && i2 != 2024 && i2 != 2035 && i2 != 2038) {
                        switch (i2) {
                            case 1000:
                            case 1001:
                            case 1002:
                            case 1003:
                                break;
                            default:
                                switch (i2) {
                                    case 2001:
                                    case 2002:
                                    case 2003:
                                        break;
                                    default:
                                        switch (i2) {
                                        }
                                }
                        }
                    }
                    Rect rect = displayMagnifier.mTempRect2;
                    displayMagnifier.mMagnificationRegion.getBounds(rect);
                    MagnificationSpec magnificationSpec = displayMagnifier.mMagnificationSpec;
                    rect.offset((int) (-magnificationSpec.offsetX), (int) (-magnificationSpec.offsetY));
                    rect.scale(1.0f / displayMagnifier.mMagnificationSpec.scale);
                    Rect rect2 = displayMagnifier.mTempRect1;
                    windowState.getTouchableRegion(displayMagnifier.mTempRegion1);
                    displayMagnifier.mTempRegion1.getBounds(rect2);
                    if (rect.intersect(rect2)) {
                        return;
                    }
                    ((FullScreenMagnificationController.DisplayMagnification) displayMagnifier.mCallbacks).onRectangleOnScreenRequested(rect2.left, rect2.top, rect2.right, rect2.bottom);
                }
            }
        }
    }

    public final void performComputeChangedWindowsNot(int i, boolean z) {
        WindowsForAccessibilityObserver windowsForAccessibilityObserver;
        if (this.mAccessibilityTracing.isTracingEnabled(1024L)) {
            this.mAccessibilityTracing.logTrace("AccessibilityController.performComputeChangedWindowsNot", 1024L, AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i, "displayId=", "; forceSend=", z));
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowsForAccessibilityObserver = (WindowsForAccessibilityObserver) this.mWindowsForAccessibilityObserver.get(i);
                if (windowsForAccessibilityObserver == null) {
                    windowsForAccessibilityObserver = null;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.performComputeChangedWindows(z);
        }
    }

    public final void recomputeMagnifiedRegionAndDrawMagnifiedRegionBorderIfNeeded(int i) {
        if (this.mAccessibilityTracing.isTracingEnabled(2048L)) {
            this.mAccessibilityTracing.logTrace("AccessibilityController.recomputeMagnifiedRegionAndDrawMagnifiedRegionBorderIfNeeded", 2048L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "displayId="));
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            if (displayMagnifier.mAccessibilityTracing.isTracingEnabled(2048L)) {
                displayMagnifier.mAccessibilityTracing.logTrace("WindowManager.recomputeMagnifiedRegionAndDrawMagnifiedRegionBorderIfNeeded", 2048L);
            }
            displayMagnifier.recomputeBounds();
            if (Flags.alwaysDrawMagnificationFullscreenBorder()) {
                return;
            }
            DisplayMagnifier.MagnifiedViewport.ViewportWindow viewportWindow = displayMagnifier.mMagnifiedViewport.mWindow;
            if (viewportWindow.mInvalidated) {
                DisplayMagnifier.this.mService.mAnimationHandler.post(viewportWindow);
            }
        }
    }

    public final void setFullscreenMagnificationActivated(int i, boolean z) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.setFullscreenMagnificationActivated", 2048L, AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(i, "displayId=", "; activated=", z));
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl2.logTrace("WindowManager.setFullscreenMagnificationActivated", 2048L, "activated=" + z);
            }
            displayMagnifier.mIsFullscreenMagnificationActivated = z;
            if (Flags.alwaysDrawMagnificationFullscreenBorder()) {
                return;
            }
            DisplayMagnifier.MagnifiedViewport magnifiedViewport = displayMagnifier.mMagnifiedViewport;
            magnifiedViewport.setMagnifiedRegionBorderShown(z, true);
            DisplayMagnifier displayMagnifier2 = DisplayMagnifier.this;
            if (displayMagnifier2.mAccessibilityTracing.isTracingEnabled(2048L)) {
                displayMagnifier2.mAccessibilityTracing.logTrace("WindowManager.showMagnificationBoundsIfNeeded", 2048L);
            }
            displayMagnifier2.mHandler.obtainMessage(5).sendToTarget();
        }
    }

    public final boolean setMagnificationCallbacks(int i, WindowManagerInternal.MagnificationCallbacks magnificationCallbacks) {
        Display display;
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.setMagnificationCallbacks", 2048L, "displayId=" + i + "; callbacks={" + magnificationCallbacks + "}");
        }
        if (magnificationCallbacks != null) {
            if (this.mDisplayMagnifiers.get(i) != null) {
                throw new IllegalStateException("Magnification callbacks already set!");
            }
            WindowManagerService windowManagerService = this.mService;
            DisplayContent displayContent = windowManagerService.mRoot.getDisplayContent(i);
            if (displayContent == null || (display = displayContent.mDisplay) == null || display.getType() == 4) {
                return false;
            }
            DisplayMagnifier displayMagnifier = new DisplayMagnifier(windowManagerService, displayContent, display, magnificationCallbacks);
            displayMagnifier.notifyImeWindowVisibilityChanged(this.mIsImeVisibleArray.get(i, false));
            this.mDisplayMagnifiers.put(i, displayMagnifier);
            return true;
        }
        DisplayMagnifier displayMagnifier2 = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier2 == null) {
            throw new IllegalStateException("Magnification callbacks already cleared!");
        }
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl2 = displayMagnifier2.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl2.isTracingEnabled(2048L)) {
            accessibilityControllerInternalImpl2.logTrace("WindowManager.destroy", 2048L);
        }
        if (!Flags.alwaysDrawMagnificationFullscreenBorder()) {
            DisplayMagnifier.MagnifiedViewport.ViewportWindow viewportWindow = displayMagnifier2.mMagnifiedViewport.mWindow;
            viewportWindow.mBlastBufferQueue.destroy();
            DisplayMagnifier.this.mService.mAnimationHandler.post(viewportWindow);
        }
        this.mDisplayMagnifiers.remove(i);
        return true;
    }

    public final void setMagnificationSpec(int i, MagnificationSpec magnificationSpec) {
        if (this.mAccessibilityTracing.isTracingEnabled(3072L)) {
            this.mAccessibilityTracing.logTrace("AccessibilityController.setMagnificationSpec", 3072L, "displayId=" + i + "; spec={" + magnificationSpec + "}");
        }
        AccessibilityWindowsPopulator accessibilityWindowsPopulator = this.mAccessibilityWindowsPopulator;
        synchronized (accessibilityWindowsPopulator.mLock) {
            try {
                MagnificationSpec magnificationSpec2 = (MagnificationSpec) accessibilityWindowsPopulator.mCurrentMagnificationSpec.get(i);
                if (magnificationSpec2 == null) {
                    MagnificationSpec magnificationSpec3 = new MagnificationSpec();
                    magnificationSpec3.setTo(magnificationSpec);
                    accessibilityWindowsPopulator.mCurrentMagnificationSpec.put(i, magnificationSpec3);
                } else {
                    MagnificationSpec magnificationSpec4 = (MagnificationSpec) accessibilityWindowsPopulator.mPreviousMagnificationSpec.get(i);
                    if (magnificationSpec4 == null) {
                        magnificationSpec4 = new MagnificationSpec();
                        accessibilityWindowsPopulator.mPreviousMagnificationSpec.put(i, magnificationSpec4);
                    }
                    magnificationSpec4.setTo(magnificationSpec2);
                    magnificationSpec2.setTo(magnificationSpec);
                }
            } finally {
            }
        }
        DisplayMagnifier displayMagnifier = (DisplayMagnifier) this.mDisplayMagnifiers.get(i);
        if (displayMagnifier != null) {
            AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = displayMagnifier.mAccessibilityTracing;
            if (accessibilityControllerInternalImpl.isTracingEnabled(2048L)) {
                accessibilityControllerInternalImpl.logTrace("WindowManager.setMagnificationSpec", 2048L, "spec={" + magnificationSpec + "}");
            }
            if (magnificationSpec != null) {
                displayMagnifier.mMagnificationSpec.initialize(magnificationSpec.scale, magnificationSpec.offsetX, magnificationSpec.offsetY);
            } else {
                displayMagnifier.mMagnificationSpec.clear();
            }
            if (!Flags.alwaysDrawMagnificationFullscreenBorder()) {
                DisplayMagnifier.MagnifiedViewport magnifiedViewport = displayMagnifier.mMagnifiedViewport;
                DisplayMagnifier displayMagnifier2 = DisplayMagnifier.this;
                if (!displayMagnifier2.mHandler.hasMessages(5)) {
                    magnifiedViewport.setMagnifiedRegionBorderShown(displayMagnifier2.isFullscreenMagnificationActivated(), true);
                }
            }
            displayMagnifier.recomputeBounds();
            int displayId = displayMagnifier.mDisplay.getDisplayId();
            WindowManagerService windowManagerService = displayMagnifier.mService;
            windowManagerService.applyMagnificationSpecLocked(displayId, magnificationSpec);
            windowManagerService.scheduleAnimationLocked();
        }
        WindowsForAccessibilityObserver windowsForAccessibilityObserver = (WindowsForAccessibilityObserver) this.mWindowsForAccessibilityObserver.get(i);
        if (windowsForAccessibilityObserver != null) {
            windowsForAccessibilityObserver.scheduleComputeChangedWindows();
        }
    }

    public final void setWindowsForAccessibilityCallback(int i, WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback) {
        AccessibilityControllerInternalImpl accessibilityControllerInternalImpl = this.mAccessibilityTracing;
        if (accessibilityControllerInternalImpl.isTracingEnabled(1024L)) {
            accessibilityControllerInternalImpl.logTrace("AccessibilityController.setWindowsForAccessibilityCallback", 1024L, "displayId=" + i + "; callback={" + windowsForAccessibilityCallback + "}");
        }
        AccessibilityWindowsPopulator accessibilityWindowsPopulator = this.mAccessibilityWindowsPopulator;
        if (windowsForAccessibilityCallback == null) {
            if (((WindowsForAccessibilityObserver) this.mWindowsForAccessibilityObserver.get(i)) == null) {
                String str = "Windows for accessibility callback of display " + i + " already cleared!";
                Slog.e("AccessibilityController", str);
                if (Build.IS_DEBUGGABLE) {
                    throw new IllegalStateException(str);
                }
            }
            this.mWindowsForAccessibilityObserver.remove(i);
            if (this.mWindowsForAccessibilityObserver.size() <= 0) {
                accessibilityWindowsPopulator.setWindowsNotification(false);
                return;
            }
            return;
        }
        if (((WindowsForAccessibilityObserver) this.mWindowsForAccessibilityObserver.get(i)) != null) {
            String str2 = "Windows for accessibility callback of display " + i + " already set!";
            Slog.e("AccessibilityController", str2);
            if (Build.IS_DEBUGGABLE) {
                throw new IllegalStateException(str2);
            }
            this.mWindowsForAccessibilityObserver.remove(i);
        }
        accessibilityWindowsPopulator.setWindowsNotification(true);
        WindowsForAccessibilityObserver windowsForAccessibilityObserver = new WindowsForAccessibilityObserver(this.mService, i, windowsForAccessibilityCallback, accessibilityWindowsPopulator);
        this.mWindowsForAccessibilityObserver.put(i, windowsForAccessibilityObserver);
        this.mAllObserversInitialized &= windowsForAccessibilityObserver.mInitialized;
    }
}
