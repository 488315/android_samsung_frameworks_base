package com.android.server.wm;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.InputConstants;
import android.view.GestureDetector;
import android.view.InputApplicationHandle;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputWindowHandle;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Letterbox {
    public static final Rect EMPTY_RECT = new Rect();
    public static final Point ZERO_POINT = new Point(0, 0);
    public final AppCompatLetterboxOverrides mAppCompatLetterboxOverrides;
    public final AppCompatReachabilityPolicy mAppCompatReachabilityPolicy;
    public final LetterboxSurface mBottom;
    public final LetterboxSurface mFullWindowSurface;
    public WindowState mInputWindow;
    public boolean mLastUseFullWindowSurface;
    public final LetterboxSurface mLeft;
    public final Supplier mParentSurfaceSupplier;
    public final LetterboxSurface mRight;
    public final Supplier mSurfaceControlFactory;
    public final LetterboxSurface[] mSurfaces;
    public final LetterboxSurface mTop;
    public final Supplier mTransactionFactory;
    public final Rect mOuter = new Rect();
    public final Rect mInner = new Rect();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public final WindowManagerService mWmService;

        public DoubleTapListener(WindowManagerService windowManagerService) {
            this.mWmService = windowManagerService;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (Letterbox.this.mOuter.isEmpty() || motionEvent.getAction() != 1) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    Letterbox.this.mAppCompatReachabilityPolicy.handleDoubleTap(
                            (int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                return super.onSingleTapUp(motionEvent);
            }
            AppCompatReachabilityPolicy appCompatReachabilityPolicy =
                    Letterbox.this.mAppCompatReachabilityPolicy;
            ActivityRecord activityRecord = appCompatReachabilityPolicy.mActivityRecord;
            activityRecord.mAtmService.mH.removeCallbacks(appCompatReachabilityPolicy.mOnSingleTap);
            activityRecord.mAtmService.mH.post(appCompatReachabilityPolicy.mOnSingleTap);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputInterceptor implements Runnable {
        public final InputChannel mClientChannel;
        public final Handler mHandler;
        public final TapEventReceiver mInputEventReceiver;
        public final IBinder mToken;
        public final InputWindowHandle mWindowHandle;
        public final WindowManagerService mWmService;

        public InputInterceptor(Letterbox letterbox, String str, WindowState windowState) {
            WindowManagerService windowManagerService = windowState.mWmService;
            this.mWmService = windowManagerService;
            Handler handler = UiThread.getHandler();
            this.mHandler = handler;
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
            Object obj = windowState.mActivityRecord;
            m.append(obj == null ? windowState : obj);
            String sb = m.toString();
            InputChannel createInputChannel =
                    windowManagerService.mInputManager.createInputChannel(sb);
            this.mClientChannel = createInputChannel;
            this.mInputEventReceiver =
                    new TapEventReceiver(
                            letterbox, createInputChannel, windowManagerService, handler);
            IBinder token = createInputChannel.getToken();
            this.mToken = token;
            InputWindowHandle inputWindowHandle =
                    new InputWindowHandle(
                            (InputApplicationHandle) null, windowState.getDisplayId());
            this.mWindowHandle = inputWindowHandle;
            inputWindowHandle.name = sb;
            inputWindowHandle.token = token;
            inputWindowHandle.layoutParamsType = 2022;
            inputWindowHandle.dispatchingTimeoutMillis =
                    InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            inputWindowHandle.ownerPid = WindowManagerService.MY_PID;
            inputWindowHandle.ownerUid = WindowManagerService.MY_UID;
            inputWindowHandle.scaleFactor = 1.0f;
            inputWindowHandle.inputConfig = 1028;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mInputEventReceiver.dispose();
            this.mClientChannel.dispose();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LetterboxSurface {
        public Color mColor;
        public boolean mHasWallpaperBackground;
        public InputInterceptor mInputInterceptor;
        public SurfaceControl mParentSurface;
        public SurfaceControl mSurface;
        public final String mType;
        public final Rect mSurfaceFrameRelative = new Rect();
        public final Rect mLayoutFrameGlobal = new Rect();
        public final Rect mLayoutFrameRelative = new Rect();

        public LetterboxSurface(String str) {
            this.mType = str;
        }

        public final void applySurfaceChanges(
                SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            InputInterceptor inputInterceptor;
            SemBlurInfo.ColorCurve letterboxBackgroundWallpaperBlurColorCurve;
            if (needsApplySurfaceChanges()) {
                this.mSurfaceFrameRelative.set(this.mLayoutFrameRelative);
                if (!this.mSurfaceFrameRelative.isEmpty()) {
                    Letterbox letterbox = Letterbox.this;
                    if (!letterbox.mAppCompatLetterboxOverrides.shouldHideLetterboxSurface(
                            letterbox.mInputWindow)) {
                        if (this.mSurface == null) {
                            SurfaceControl build =
                                    ((SurfaceControl.Builder)
                                                    letterbox.mSurfaceControlFactory.get())
                                            .setName("Letterbox - " + this.mType + "")
                                            .setFlags(4)
                                            .setColorLayer()
                                            .setCallsite("LetterboxSurface.createSurface")
                                            .build();
                            this.mSurface = build;
                            transaction
                                    .setLayer(build, -20000)
                                    .setColorSpaceAgnostic(this.mSurface, true);
                        }
                        this.mColor =
                                letterbox.mAppCompatLetterboxOverrides
                                        .getLetterboxBackgroundColor();
                        this.mParentSurface =
                                (SurfaceControl) letterbox.mParentSurfaceSupplier.get();
                        transaction.setColor(
                                this.mSurface,
                                new float[] {
                                    this.mColor.red(), this.mColor.green(), this.mColor.blue()
                                });
                        SurfaceControl surfaceControl = this.mSurface;
                        Rect rect = this.mSurfaceFrameRelative;
                        transaction.setPosition(surfaceControl, rect.left, rect.top);
                        transaction.setWindowCrop(
                                this.mSurface,
                                this.mSurfaceFrameRelative.width(),
                                this.mSurfaceFrameRelative.height());
                        transaction.reparent(this.mSurface, this.mParentSurface);
                        this.mHasWallpaperBackground =
                                letterbox.mAppCompatLetterboxOverrides
                                        .hasWallpaperBackgroundForLetterbox();
                        boolean z = CoreRune.MT_APP_COMPAT_CONFIGURATION;
                        if (z
                                && MultiTaskingAppCompatConfiguration.getConfig(
                                                letterbox
                                                        .mAppCompatLetterboxOverrides
                                                        .mActivityRecord)
                                        .hasCapturedLetterboxSurface()) {
                            transaction.setAlpha(
                                    this.mSurface, FullScreenMagnificationGestureHandler.MAX_SCALE);
                            transaction.setBackgroundBlurRadius(this.mSurface, 0);
                        } else if (this.mHasWallpaperBackground) {
                            transaction.setAlpha(
                                    this.mSurface,
                                    letterbox.mAppCompatLetterboxOverrides
                                            .getLetterboxWallpaperDarkScrimAlpha());
                            AppCompatLetterboxOverrides appCompatLetterboxOverrides =
                                    letterbox.mAppCompatLetterboxOverrides;
                            int letterboxWallpaperBlurRadiusPx =
                                    appCompatLetterboxOverrides.getLetterboxWallpaperBlurRadiusPx();
                            if (letterboxWallpaperBlurRadiusPx <= 0) {
                                transaction.setBackgroundBlurRadius(this.mSurface, 0);
                            } else {
                                if (z
                                        && (letterboxBackgroundWallpaperBlurColorCurve =
                                                        MultiTaskingAppCompatConfiguration
                                                                .getConfig(
                                                                        appCompatLetterboxOverrides
                                                                                .mActivityRecord)
                                                                .getLetterboxBackgroundWallpaperBlurColorCurve())
                                                != null) {
                                    transaction.setBackgroundBlurColorCurve(
                                            this.mSurface,
                                            letterboxBackgroundWallpaperBlurColorCurve);
                                }
                                transaction.setBackgroundBlurRadius(
                                        this.mSurface, letterboxWallpaperBlurRadiusPx);
                            }
                        } else {
                            transaction.setAlpha(this.mSurface, 1.0f);
                            transaction.setBackgroundBlurRadius(this.mSurface, 0);
                        }
                        transaction.show(this.mSurface);
                        if (this.mSurface != null
                                || (inputInterceptor = this.mInputInterceptor) == null) {}
                        Rect rect2 = this.mSurfaceFrameRelative;
                        inputInterceptor.getClass();
                        if (rect2.isEmpty()) {
                            inputInterceptor.mWindowHandle.token = null;
                        } else {
                            InputWindowHandle inputWindowHandle = inputInterceptor.mWindowHandle;
                            inputWindowHandle.token = inputInterceptor.mToken;
                            inputWindowHandle.touchableRegion.set(rect2);
                            inputInterceptor.mWindowHandle.touchableRegion.translate(
                                    -rect2.left, -rect2.top);
                        }
                        transaction2.setInputWindowInfo(
                                this.mSurface, this.mInputInterceptor.mWindowHandle);
                        return;
                    }
                }
                SurfaceControl surfaceControl2 = this.mSurface;
                if (surfaceControl2 != null) {
                    transaction.hide(surfaceControl2);
                }
                if (this.mSurface != null) {}
            }
        }

        public final void attachInput(WindowState windowState) {
            InputInterceptor inputInterceptor = this.mInputInterceptor;
            if (inputInterceptor != null) {
                inputInterceptor.mWmService.mInputManager.removeInputChannel(
                        inputInterceptor.mToken);
                inputInterceptor.mHandler.post(inputInterceptor);
            }
            this.mInputInterceptor =
                    new InputInterceptor(
                            Letterbox.this,
                            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Letterbox_"), this.mType, "_"),
                            windowState);
        }

        public final void layout(int i, int i2, int i3, int i4, Point point) {
            this.mLayoutFrameGlobal.set(i, i2, i3, i4);
            this.mLayoutFrameRelative.set(this.mLayoutFrameGlobal);
            this.mLayoutFrameRelative.offset(-point.x, -point.y);
        }

        public final boolean needsApplySurfaceChanges() {
            if (this.mSurfaceFrameRelative.equals(this.mLayoutFrameRelative)) {
                if (!this.mSurfaceFrameRelative.isEmpty()) {
                    Letterbox letterbox = Letterbox.this;
                    if (letterbox.mAppCompatLetterboxOverrides.hasWallpaperBackgroundForLetterbox()
                                    != this.mHasWallpaperBackground
                            || !letterbox
                                    .mAppCompatLetterboxOverrides
                                    .getLetterboxBackgroundColor()
                                    .equals(this.mColor)
                            || letterbox.mParentSurfaceSupplier.get() != this.mParentSurface) {}
                }
                return false;
            }
            return true;
        }

        public final void remove() {
            if (this.mSurface != null) {
                ((SurfaceControl.Transaction) Letterbox.this.mTransactionFactory.get())
                        .remove(this.mSurface)
                        .apply();
                this.mSurface = null;
            }
            InputInterceptor inputInterceptor = this.mInputInterceptor;
            if (inputInterceptor != null) {
                inputInterceptor.mWmService.mInputManager.removeInputChannel(
                        inputInterceptor.mToken);
                inputInterceptor.mHandler.post(inputInterceptor);
                this.mInputInterceptor = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TapEventReceiver extends InputEventReceiver {
        public final GestureDetector mDoubleTapDetector;

        public TapEventReceiver(
                Letterbox letterbox,
                InputChannel inputChannel,
                WindowManagerService windowManagerService,
                Handler handler) {
            super(inputChannel, handler.getLooper());
            this.mDoubleTapDetector =
                    new GestureDetector(
                            windowManagerService.mContext,
                            letterbox.new DoubleTapListener(windowManagerService),
                            handler);
        }

        public final void onInputEvent(InputEvent inputEvent) {
            finishInputEvent(
                    inputEvent, this.mDoubleTapDetector.onTouchEvent((MotionEvent) inputEvent));
        }
    }

    public Letterbox(
            AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1
                    appCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1,
            Supplier supplier,
            AppCompatReachabilityPolicy appCompatReachabilityPolicy,
            AppCompatLetterboxOverrides appCompatLetterboxOverrides,
            AppCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1
                    appCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda12) {
        LetterboxSurface letterboxSurface = new LetterboxSurface("top");
        this.mTop = letterboxSurface;
        LetterboxSurface letterboxSurface2 = new LetterboxSurface("left");
        this.mLeft = letterboxSurface2;
        LetterboxSurface letterboxSurface3 = new LetterboxSurface("bottom");
        this.mBottom = letterboxSurface3;
        LetterboxSurface letterboxSurface4 = new LetterboxSurface("right");
        this.mRight = letterboxSurface4;
        this.mFullWindowSurface = new LetterboxSurface("fullWindow");
        this.mSurfaces =
                new LetterboxSurface[] {
                    letterboxSurface2, letterboxSurface, letterboxSurface4, letterboxSurface3
                };
        this.mSurfaceControlFactory =
                appCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda1;
        this.mTransactionFactory = supplier;
        this.mAppCompatReachabilityPolicy = appCompatReachabilityPolicy;
        this.mAppCompatLetterboxOverrides = appCompatLetterboxOverrides;
        this.mParentSurfaceSupplier =
                appCompatLetterboxPolicy$LetterboxPolicyState$$ExternalSyntheticLambda12;
    }

    public final void layout(Rect rect, Rect rect2, Point point) {
        this.mOuter.set(rect);
        this.mInner.set(rect2);
        this.mTop.layout(rect.left, rect.top, rect.right, rect2.top, point);
        this.mLeft.layout(rect.left, rect.top, rect2.left, rect.bottom, point);
        this.mBottom.layout(rect.left, rect2.bottom, rect.right, rect.bottom, point);
        this.mRight.layout(rect2.right, rect.top, rect.right, rect.bottom, point);
        this.mFullWindowSurface.layout(rect.left, rect.top, rect.right, rect.bottom, point);
    }

    public final boolean useFullWindowSurface() {
        AppCompatLetterboxOverrides appCompatLetterboxOverrides = this.mAppCompatLetterboxOverrides;
        appCompatLetterboxOverrides.getClass();
        boolean z = CoreRune.MT_APP_COMPAT_CONFIGURATION;
        ActivityRecord activityRecord = appCompatLetterboxOverrides.mActivityRecord;
        if (z) {
            if (MultiTaskingAppCompatConfiguration.getConfig(activityRecord)
                                    .getLetterboxActivityCornersRadius()
                            != 0
                    && activityRecord.occludesParent(true)) {
                return true;
            }
        } else if (appCompatLetterboxOverrides
                                .mAppCompatConfiguration
                                .mLetterboxActivityCornersRadius
                        != 0
                && activityRecord.occludesParent(true)) {
            return true;
        }
        return appCompatLetterboxOverrides.hasWallpaperBackgroundForLetterbox();
    }
}
