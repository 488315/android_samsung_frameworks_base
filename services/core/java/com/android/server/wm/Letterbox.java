package com.android.server.wm;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
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
import com.android.server.UiThread;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class Letterbox {
    public static final Rect EMPTY_RECT = new Rect();
    public static final Point ZERO_POINT = new Point(0, 0);
    public final Supplier mAreCornersRounded;
    public Supplier mBlurColorCurveSupplier;
    public final Supplier mBlurRadiusSupplier;
    public final LetterboxSurface mBottom;
    public Supplier mCanHaveLetterboxSurfaceSupplier;
    public final Supplier mColorSupplier;
    public final Supplier mDarkScrimAlphaSupplier;
    public final IntConsumer mDoubleTapCallbackX;
    public final IntConsumer mDoubleTapCallbackY;
    public final LetterboxSurface mFullWindowSurface;
    public final Supplier mHasWallpaperBackgroundSupplier;
    public WindowState mInputWindow;
    public final LetterboxSurface mLeft;
    public final Supplier mParentSurfaceSupplier;
    public final LetterboxSurface mRight;
    public IntConsumer mSingleTapCallback;
    public final Supplier mSurfaceControlFactory;
    public final LetterboxSurface[] mSurfaces;
    public final LetterboxSurface mTop;
    public final Supplier mTransactionFactory;
    public boolean mUseFullWindowSurface;
    public final Rect mOuter = new Rect();
    public final Rect mInner = new Rect();

    public Letterbox(Supplier supplier, Supplier supplier2, Supplier supplier3, Supplier supplier4, Supplier supplier5, Supplier supplier6, Supplier supplier7, IntConsumer intConsumer, IntConsumer intConsumer2, Supplier supplier8) {
        LetterboxSurface letterboxSurface = new LetterboxSurface("top");
        this.mTop = letterboxSurface;
        LetterboxSurface letterboxSurface2 = new LetterboxSurface("left");
        this.mLeft = letterboxSurface2;
        LetterboxSurface letterboxSurface3 = new LetterboxSurface("bottom");
        this.mBottom = letterboxSurface3;
        LetterboxSurface letterboxSurface4 = new LetterboxSurface("right");
        this.mRight = letterboxSurface4;
        this.mFullWindowSurface = new LetterboxSurface("fullWindow");
        this.mSurfaces = new LetterboxSurface[]{letterboxSurface2, letterboxSurface, letterboxSurface4, letterboxSurface3};
        this.mSurfaceControlFactory = supplier;
        this.mTransactionFactory = supplier2;
        this.mAreCornersRounded = supplier3;
        this.mColorSupplier = supplier4;
        this.mHasWallpaperBackgroundSupplier = supplier5;
        this.mBlurRadiusSupplier = supplier6;
        this.mDarkScrimAlphaSupplier = supplier7;
        this.mDoubleTapCallbackX = intConsumer;
        this.mDoubleTapCallbackY = intConsumer2;
        this.mParentSurfaceSupplier = supplier8;
        this.mUseFullWindowSurface = useFullWindowSurface();
    }

    public void layout(Rect rect, Rect rect2, Point point) {
        this.mOuter.set(rect);
        this.mInner.set(rect2);
        this.mTop.layout(rect.left, rect.top, rect.right, rect2.top, point);
        this.mLeft.layout(rect.left, rect.top, rect2.left, rect.bottom, point);
        this.mBottom.layout(rect.left, rect2.bottom, rect.right, rect.bottom, point);
        this.mRight.layout(rect2.right, rect.top, rect.right, rect.bottom, point);
        this.mFullWindowSurface.layout(rect.left, rect.top, rect.right, rect.bottom, point);
    }

    public Rect getInsets() {
        return new Rect(this.mLeft.getWidth(), this.mTop.getHeight(), this.mRight.getWidth(), this.mBottom.getHeight());
    }

    public Rect getInnerFrame() {
        return this.mInner;
    }

    public Rect getOuterFrame() {
        return this.mOuter;
    }

    public boolean notIntersectsOrFullyContains(Rect rect) {
        int i = 0;
        int i2 = 0;
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            Rect rect2 = letterboxSurface.mLayoutFrameGlobal;
            if (rect2.isEmpty()) {
                i++;
            } else if (!Rect.intersects(rect2, rect)) {
                i2++;
            } else if (rect2.contains(rect)) {
                return true;
            }
        }
        return i + i2 == this.mSurfaces.length;
    }

    public void hide() {
        Rect rect = EMPTY_RECT;
        layout(rect, rect, ZERO_POINT);
    }

    public void destroy() {
        this.mOuter.setEmpty();
        this.mInner.setEmpty();
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            letterboxSurface.remove();
        }
        this.mFullWindowSurface.remove();
    }

    public boolean needsApplySurfaceChanges() {
        if (isUseFullWindowSurfaceChanged()) {
            return true;
        }
        if (useFullWindowSurface()) {
            return this.mFullWindowSurface.needsApplySurfaceChanges();
        }
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            if (letterboxSurface.needsApplySurfaceChanges()) {
                return true;
            }
        }
        return false;
    }

    public void applySurfaceChanges(SurfaceControl.Transaction transaction) {
        int i = 0;
        if (isUseFullWindowSurfaceChanged()) {
            boolean z = !this.mUseFullWindowSurface;
            this.mUseFullWindowSurface = z;
            if (z) {
                applyLetterboxSurfaceChanges(transaction, this.mFullWindowSurface);
                LetterboxSurface[] letterboxSurfaceArr = this.mSurfaces;
                int length = letterboxSurfaceArr.length;
                while (i < length) {
                    removeLetterboxSurface(transaction, letterboxSurfaceArr[i]);
                    i++;
                }
                return;
            }
            LetterboxSurface[] letterboxSurfaceArr2 = this.mSurfaces;
            int length2 = letterboxSurfaceArr2.length;
            while (i < length2) {
                applyLetterboxSurfaceChanges(transaction, letterboxSurfaceArr2[i]);
                i++;
            }
            removeLetterboxSurface(transaction, this.mFullWindowSurface);
            return;
        }
        if (useFullWindowSurface()) {
            this.mFullWindowSurface.applySurfaceChanges(transaction);
            LetterboxSurface[] letterboxSurfaceArr3 = this.mSurfaces;
            int length3 = letterboxSurfaceArr3.length;
            while (i < length3) {
                letterboxSurfaceArr3[i].remove();
                i++;
            }
            return;
        }
        LetterboxSurface[] letterboxSurfaceArr4 = this.mSurfaces;
        int length4 = letterboxSurfaceArr4.length;
        while (i < length4) {
            letterboxSurfaceArr4[i].applySurfaceChanges(transaction);
            i++;
        }
        this.mFullWindowSurface.remove();
    }

    public void attachInput(WindowState windowState) {
        this.mInputWindow = windowState;
        if (useFullWindowSurface()) {
            this.mFullWindowSurface.attachInput(windowState);
            return;
        }
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            letterboxSurface.attachInput(windowState);
        }
    }

    public void onMovedToDisplay(int i) {
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            if (letterboxSurface.mInputInterceptor != null) {
                letterboxSurface.mInputInterceptor.mWindowHandle.displayId = i;
            }
        }
        if (this.mFullWindowSurface.mInputInterceptor != null) {
            this.mFullWindowSurface.mInputInterceptor.mWindowHandle.displayId = i;
        }
    }

    public final boolean useFullWindowSurface() {
        return ((Boolean) this.mAreCornersRounded.get()).booleanValue() || ((Boolean) this.mHasWallpaperBackgroundSupplier.get()).booleanValue();
    }

    public final class TapEventReceiver extends InputEventReceiver {
        public final GestureDetector mDoubleTapDetector;
        public final DoubleTapListener mDoubleTapListener;

        public TapEventReceiver(InputChannel inputChannel, Context context) {
            super(inputChannel, UiThread.getHandler().getLooper());
            DoubleTapListener doubleTapListener = new DoubleTapListener();
            this.mDoubleTapListener = doubleTapListener;
            this.mDoubleTapDetector = new GestureDetector(context, doubleTapListener, UiThread.getHandler());
        }

        public void onInputEvent(InputEvent inputEvent) {
            finishInputEvent(inputEvent, this.mDoubleTapDetector.onTouchEvent((MotionEvent) inputEvent));
        }
    }

    public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public DoubleTapListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 1) {
                return false;
            }
            Letterbox.this.mDoubleTapCallbackX.accept((int) motionEvent.getRawX());
            Letterbox.this.mDoubleTapCallbackY.accept((int) motionEvent.getRawY());
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            IntConsumer intConsumer;
            if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE && (intConsumer = Letterbox.this.mSingleTapCallback) != null) {
                intConsumer.accept(0);
                return true;
            }
            return super.onSingleTapUp(motionEvent);
        }
    }

    public final class InputInterceptor {
        public final InputChannel mClientChannel;
        public final InputEventReceiver mInputEventReceiver;
        public final IBinder mToken;
        public final InputWindowHandle mWindowHandle;
        public final WindowManagerService mWmService;

        public InputInterceptor(String str, WindowState windowState) {
            WindowManagerService windowManagerService = windowState.mWmService;
            this.mWmService = windowManagerService;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            Object obj = windowState.mActivityRecord;
            sb.append(obj == null ? windowState : obj);
            String sb2 = sb.toString();
            InputChannel createInputChannel = windowManagerService.mInputManager.createInputChannel(sb2);
            this.mClientChannel = createInputChannel;
            this.mInputEventReceiver = Letterbox.this.new TapEventReceiver(createInputChannel, windowManagerService.mContext);
            IBinder token = createInputChannel.getToken();
            this.mToken = token;
            InputWindowHandle inputWindowHandle = new InputWindowHandle((InputApplicationHandle) null, windowState.getDisplayId());
            this.mWindowHandle = inputWindowHandle;
            inputWindowHandle.name = sb2;
            inputWindowHandle.token = token;
            inputWindowHandle.layoutParamsType = 2022;
            inputWindowHandle.dispatchingTimeoutMillis = InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            inputWindowHandle.ownerPid = WindowManagerService.MY_PID;
            inputWindowHandle.ownerUid = WindowManagerService.MY_UID;
            inputWindowHandle.scaleFactor = 1.0f;
            inputWindowHandle.inputConfig = 1028;
        }

        public void updateTouchableRegion(Rect rect) {
            if (rect.isEmpty()) {
                this.mWindowHandle.token = null;
                return;
            }
            InputWindowHandle inputWindowHandle = this.mWindowHandle;
            inputWindowHandle.token = this.mToken;
            inputWindowHandle.touchableRegion.set(rect);
            this.mWindowHandle.touchableRegion.translate(-rect.left, -rect.top);
        }

        public void dispose() {
            this.mWmService.mInputManager.removeInputChannel(this.mToken);
            this.mInputEventReceiver.dispose();
            this.mClientChannel.dispose();
        }
    }

    public class LetterboxSurface {
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

        public void layout(int i, int i2, int i3, int i4, Point point) {
            this.mLayoutFrameGlobal.set(i, i2, i3, i4);
            this.mLayoutFrameRelative.set(this.mLayoutFrameGlobal);
            this.mLayoutFrameRelative.offset(-point.x, -point.y);
        }

        public final void createSurface(SurfaceControl.Transaction transaction) {
            SurfaceControl build = ((SurfaceControl.Builder) Letterbox.this.mSurfaceControlFactory.get()).setName("Letterbox - " + this.mType).setFlags(4).setColorLayer().setCallsite("LetterboxSurface.createSurface").build();
            this.mSurface = build;
            transaction.setLayer(build, -20000).setColorSpaceAgnostic(this.mSurface, true);
        }

        public void attachInput(WindowState windowState) {
            InputInterceptor inputInterceptor = this.mInputInterceptor;
            if (inputInterceptor != null) {
                inputInterceptor.dispose();
            }
            this.mInputInterceptor = Letterbox.this.new InputInterceptor("Letterbox_" + this.mType + "_", windowState);
        }

        public void remove() {
            if (this.mSurface != null) {
                ((SurfaceControl.Transaction) Letterbox.this.mTransactionFactory.get()).remove(this.mSurface).apply();
                this.mSurface = null;
            }
            InputInterceptor inputInterceptor = this.mInputInterceptor;
            if (inputInterceptor != null) {
                inputInterceptor.dispose();
                this.mInputInterceptor = null;
            }
        }

        public int getWidth() {
            return Math.max(0, this.mLayoutFrameGlobal.width());
        }

        public int getHeight() {
            return Math.max(0, this.mLayoutFrameGlobal.height());
        }

        public void applySurfaceChanges(SurfaceControl.Transaction transaction) {
            InputInterceptor inputInterceptor;
            if (needsApplySurfaceChanges()) {
                this.mSurfaceFrameRelative.set(this.mLayoutFrameRelative);
                if (!this.mSurfaceFrameRelative.isEmpty() && (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE || !CoreRune.FW_CUSTOM_LETTERBOX || Letterbox.this.canHaveLetterboxSurface())) {
                    if (this.mSurface == null) {
                        createSurface(transaction);
                    }
                    this.mColor = (Color) Letterbox.this.mColorSupplier.get();
                    this.mParentSurface = (SurfaceControl) Letterbox.this.mParentSurfaceSupplier.get();
                    transaction.setColor(this.mSurface, getRgbColorArray());
                    SurfaceControl surfaceControl = this.mSurface;
                    Rect rect = this.mSurfaceFrameRelative;
                    transaction.setPosition(surfaceControl, rect.left, rect.top);
                    transaction.setWindowCrop(this.mSurface, this.mSurfaceFrameRelative.width(), this.mSurfaceFrameRelative.height());
                    transaction.reparent(this.mSurface, this.mParentSurface);
                    this.mHasWallpaperBackground = ((Boolean) Letterbox.this.mHasWallpaperBackgroundSupplier.get()).booleanValue();
                    updateAlphaAndBlur(transaction);
                    transaction.show(this.mSurface);
                } else {
                    SurfaceControl surfaceControl2 = this.mSurface;
                    if (surfaceControl2 != null) {
                        transaction.hide(surfaceControl2);
                    }
                }
                if (this.mSurface == null || (inputInterceptor = this.mInputInterceptor) == null) {
                    return;
                }
                inputInterceptor.updateTouchableRegion(this.mSurfaceFrameRelative);
                transaction.setInputWindowInfo(this.mSurface, this.mInputInterceptor.mWindowHandle);
            }
        }

        public final void updateAlphaAndBlur(SurfaceControl.Transaction transaction) {
            Supplier supplier;
            SemBlurInfo.ColorCurve colorCurve;
            if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_FOR_BOUNDS_COMPAT_UI_EXPERIENCE && !Letterbox.this.canHaveLetterboxSurface()) {
                transaction.setAlpha(this.mSurface, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                transaction.setBackgroundBlurRadius(this.mSurface, 0);
                return;
            }
            if (!this.mHasWallpaperBackground) {
                transaction.setAlpha(this.mSurface, 1.0f);
                transaction.setBackgroundBlurRadius(this.mSurface, 0);
                return;
            }
            transaction.setAlpha(this.mSurface, ((Float) Letterbox.this.mDarkScrimAlphaSupplier.get()).floatValue());
            if (((Integer) Letterbox.this.mBlurRadiusSupplier.get()).intValue() <= 0) {
                transaction.setBackgroundBlurRadius(this.mSurface, 0);
                return;
            }
            if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX && (supplier = Letterbox.this.mBlurColorCurveSupplier) != null && (colorCurve = (SemBlurInfo.ColorCurve) supplier.get()) != null) {
                transaction.setBackgroundBlurColorCurve(this.mSurface, colorCurve);
            }
            transaction.setBackgroundBlurRadius(this.mSurface, ((Integer) Letterbox.this.mBlurRadiusSupplier.get()).intValue());
        }

        public final float[] getRgbColorArray() {
            return new float[]{this.mColor.red(), this.mColor.green(), this.mColor.blue()};
        }

        public boolean needsApplySurfaceChanges() {
            return (this.mSurfaceFrameRelative.equals(this.mLayoutFrameRelative) && (this.mSurfaceFrameRelative.isEmpty() || (((Boolean) Letterbox.this.mHasWallpaperBackgroundSupplier.get()).booleanValue() == this.mHasWallpaperBackground && ((Color) Letterbox.this.mColorSupplier.get()).equals(this.mColor) && Letterbox.this.mParentSurfaceSupplier.get() == this.mParentSurface))) ? false : true;
        }
    }

    public final boolean isUseFullWindowSurfaceChanged() {
        return this.mUseFullWindowSurface != useFullWindowSurface();
    }

    public final void applyLetterboxSurfaceChanges(SurfaceControl.Transaction transaction, LetterboxSurface letterboxSurface) {
        if (letterboxSurface.mInputInterceptor == null) {
            letterboxSurface.attachInput(this.mInputWindow);
            letterboxSurface.mSurfaceFrameRelative.setEmpty();
        }
        letterboxSurface.applySurfaceChanges(transaction);
    }

    public final void removeLetterboxSurface(SurfaceControl.Transaction transaction, LetterboxSurface letterboxSurface) {
        if (letterboxSurface.mSurface != null && letterboxSurface.mSurface.isValid()) {
            letterboxSurface.mSurfaceFrameRelative.setEmpty();
            transaction.hide(letterboxSurface.mSurface);
            transaction.remove(letterboxSurface.mSurface);
            letterboxSurface.mSurface = null;
        }
        letterboxSurface.remove();
    }

    public int getDirection(int i, int i2, int i3, int i4) {
        if (i > 0 && !this.mLeft.mSurfaceFrameRelative.isEmpty() && i == this.mLeft.getWidth()) {
            return 1;
        }
        if (i2 > 0 && !this.mRight.mSurfaceFrameRelative.isEmpty() && i2 == this.mRight.getWidth()) {
            return 2;
        }
        if (i3 <= 0 || this.mTop.mSurfaceFrameRelative.isEmpty() || i3 != this.mTop.getHeight()) {
            return (i4 <= 0 || this.mBottom.mSurfaceFrameRelative.isEmpty() || i4 != this.mBottom.getHeight()) ? 0 : 8;
        }
        return 4;
    }

    public final boolean canHaveLetterboxSurface() {
        Supplier supplier = this.mCanHaveLetterboxSurfaceSupplier;
        return supplier == null || ((Boolean) supplier.get()).booleanValue();
    }

    public boolean reparentToWallpaperParentImmediately(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (!useFullWindowSurface() || this.mFullWindowSurface.mSurface == null || this.mFullWindowSurface.mSurfaceFrameRelative.isEmpty()) {
            return false;
        }
        SurfaceControl surfaceControl2 = this.mFullWindowSurface.mSurface;
        transaction.reparent(surfaceControl2, surfaceControl);
        transaction.setPosition(surfaceControl2, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        transaction.setLayer(surfaceControl2, 1);
        transaction.apply(true);
        transaction.setLayer(surfaceControl2, -20000);
        return true;
    }

    public void clearSurfaceFrame() {
        if (useFullWindowSurface()) {
            this.mFullWindowSurface.mSurfaceFrameRelative.setEmpty();
            return;
        }
        for (LetterboxSurface letterboxSurface : this.mSurfaces) {
            letterboxSurface.mSurfaceFrameRelative.setEmpty();
        }
    }
}
