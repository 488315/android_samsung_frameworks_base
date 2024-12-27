package com.android.internal.graphics.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.HardwareRenderer;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;

import com.android.internal.R;

import com.samsung.android.rune.CoreRune;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;

public final class BackgroundBlurDrawable extends Drawable {
    private static final boolean DEBUG;
    private static final String TAG = BackgroundBlurDrawable.class.getSimpleName();
    private final Aggregator mAggregator;
    private float mAlpha;
    private int mBlurRadius;
    private SemBlurInfo.ColorCurve mColorCurve;
    private float mCornerRadiusBL;
    private float mCornerRadiusBR;
    private float mCornerRadiusTL;
    private float mCornerRadiusTR;
    private final Handler mHandler;
    private boolean mIsTargetView;
    private final Paint mPaint;
    public final RenderNode.PositionUpdateListener mPositionUpdateListener;
    private final Rect mRect;
    private final Path mRectPath;
    private final RenderNode mRenderNode;
    private float mScaleX;
    private float mScaleY;
    private boolean mShowDebug;
    private final float[] mTmpRadii;
    private boolean mVisible;

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0019, code lost:

       if (android.os.SystemProperties.getInt("viewroot.debug.blur", 0) != 0) goto L6;
    */
    static {
        /*
            java.lang.Class<com.android.internal.graphics.drawable.BackgroundBlurDrawable> r0 = com.android.internal.graphics.drawable.BackgroundBlurDrawable.class
            java.lang.String r0 = r0.getSimpleName()
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG = r0
            java.lang.String r0 = com.android.internal.graphics.drawable.BackgroundBlurDrawable.TAG
            r1 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r1)
            if (r0 != 0) goto L1b
            java.lang.String r0 = "viewroot.debug.blur"
            r1 = 0
            int r0 = android.os.SystemProperties.getInt(r0, r1)
            if (r0 == 0) goto L1c
        L1b:
            r1 = 1
        L1c:
            com.android.internal.graphics.drawable.BackgroundBlurDrawable.DEBUG = r1
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.graphics.drawable.BackgroundBlurDrawable.<clinit>():void");
    }

    /* renamed from: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1, reason: invalid class name */
    class AnonymousClass1 implements RenderNode.PositionUpdateListener {
        AnonymousClass1() {}

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(
                final long frameNumber,
                final int left,
                final int top,
                final int right,
                final int bottom) {
            final boolean showDebug = BackgroundBlurDrawable.this.mShowDebug;
            BackgroundBlurDrawable.this.mRect.set(left, top, right, bottom);
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionChanged fn="
                                + frameNumber
                                + " drawable="
                                + BackgroundBlurDrawable.this
                                + ", left : "
                                + left
                                + ", top : "
                                + top
                                + ", right : "
                                + right
                                + ", bottom : "
                                + bottom);
                Drawable.Callback callback = BackgroundBlurDrawable.this.getCallback();
                if (callback instanceof View) {
                    View attachedView = (View) callback;
                    ViewRootImpl viewRoot = attachedView.getViewRootImpl();
                    Log.i(
                            BackgroundBlurDrawable.TAG,
                            "positionChanged attached View="
                                    + attachedView
                                    + ", viewRoot="
                                    + ((Object) (viewRoot != null ? viewRoot.getTag() : viewRoot)));
                } else {
                    Log.i(
                            BackgroundBlurDrawable.TAG,
                            "positionChanged attached callback=" + callback);
                }
            }
            BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(
                    frameNumber,
                    new Runnable() { // from class:
                                     // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionChanged$1(
                                    showDebug, frameNumber, left, top, right, bottom);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionChanged$1(
                final boolean showDebug,
                final long frameNumber,
                int left,
                int top,
                int right,
                int bottom) {
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionChanged$run fn="
                                + frameNumber
                                + " dr=BackgroundBlurDrawable@"
                                + BackgroundBlurDrawable.this.hashCode()
                                + " rect="
                                + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionChanged$0(
                                    showDebug, frameNumber);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionChanged$0(boolean showDebug, long frameNumber) {
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionChanged$run#2 fn="
                                + frameNumber
                                + " dr=BackgroundBlurDrawable@"
                                + BackgroundBlurDrawable.this.hashCode()
                                + " rect="
                                + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.invalidateSelf();
            BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(
                    BackgroundBlurDrawable.this);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(final long frameNumber) {
            final boolean showDebug = BackgroundBlurDrawable.this.mShowDebug;
            BackgroundBlurDrawable.this.mRect.setEmpty();
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionLost fn="
                                + frameNumber
                                + " dr=BackgroundBlurDrawable@"
                                + BackgroundBlurDrawable.this.hashCode()
                                + " rect="
                                + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(
                    frameNumber,
                    new Runnable() { // from class:
                                     // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionLost$3(
                                    showDebug, frameNumber);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionLost$3(
                final boolean showDebug, final long frameNumber) {
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionLost$run fn="
                                + frameNumber
                                + " dr=BackgroundBlurDrawable@"
                                + BackgroundBlurDrawable.this.hashCode()
                                + " rect="
                                + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackgroundBlurDrawable.AnonymousClass1.this.lambda$positionLost$2(
                                    showDebug, frameNumber);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionLost$2(boolean showDebug, long frameNumber) {
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "positionLost$run#2 fn="
                                + frameNumber
                                + " dr=BackgroundBlurDrawable@"
                                + BackgroundBlurDrawable.this.hashCode()
                                + " rect="
                                + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.invalidateSelf();
            BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(
                    BackgroundBlurDrawable.this);
        }
    }

    public boolean isShowDebug() {
        return this.mShowDebug;
    }

    private boolean showDebugByTargetView() {
        Drawable.Callback callback = getCallback();
        if (!(callback instanceof View) || !((View) callback).isBlurDebug()) {
            return false;
        }
        return true;
    }

    private BackgroundBlurDrawable(Aggregator aggregator, boolean showDebug) {
        this.mColorCurve = null;
        this.mShowDebug = false;
        this.mIsTargetView = false;
        this.mPaint = new Paint();
        this.mRectPath = new Path();
        this.mTmpRadii = new float[8];
        this.mVisible = true;
        this.mAlpha = 1.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mRect = new Rect();
        this.mPositionUpdateListener = new AnonymousClass1();
        this.mAggregator = aggregator;
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mPaint.setColor(0);
        this.mPaint.setAntiAlias(true);
        this.mRenderNode = new RenderNode("BackgroundBlurDrawable");
        this.mShowDebug = showDebug;
        this.mIsTargetView = showDebug;
        this.mRenderNode.addPositionUpdateListener(this.mPositionUpdateListener);
        this.mHandler = new Handler(Looper.myLooper());
    }

    private BackgroundBlurDrawable(Aggregator aggregator) {
        this.mColorCurve = null;
        this.mShowDebug = false;
        this.mIsTargetView = false;
        this.mPaint = new Paint();
        this.mRectPath = new Path();
        this.mTmpRadii = new float[8];
        this.mVisible = true;
        this.mAlpha = 1.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mRect = new Rect();
        this.mPositionUpdateListener = new AnonymousClass1();
        this.mAggregator = aggregator;
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.mPaint.setColor(0);
        this.mPaint.setAntiAlias(true);
        this.mRenderNode = new RenderNode("BackgroundBlurDrawable");
        this.mRenderNode.addPositionUpdateListener(this.mPositionUpdateListener);
        this.mHandler = new Handler(Looper.myLooper());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mRectPath.isEmpty() || !isVisible() || getAlpha() == 0) {
            if (this.mShowDebug) {
                Log.i(
                        TAG,
                        "draw: BackgroundBlurDrawable@"
                                + hashCode()
                                + " bounds="
                                + getBounds()
                                + ", mRectPath.isEmpty()="
                                + this.mRectPath.isEmpty()
                                + " isVisible()="
                                + isVisible()
                                + " getAlpha()="
                                + getAlpha()
                                + " mAlpha="
                                + this.mAlpha
                                + " return");
            }
        } else {
            if (canvas.isHardwareAccelerated()) {
                canvas.drawPath(this.mRectPath, this.mPaint);
                if (this.mShowDebug) {
                    Log.i(
                            TAG,
                            "draw: BackgroundBlurDrawable@"
                                    + hashCode()
                                    + " bounds="
                                    + getBounds()
                                    + ", drawRenderNode w="
                                    + this.mRenderNode.getWidth()
                                    + " h="
                                    + this.mRenderNode.getHeight());
                }
                canvas.drawRenderNode(this.mRenderNode);
                return;
            }
            Log.i(TAG, "BackgroundBlur is not supported on S/W canvas!!!!");
        }
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        if (this.mShowDebug) {
            Log.i(
                    TAG,
                    "setVisible: visible="
                            + visible
                            + " BackgroundBlurDrawable@"
                            + hashCode()
                            + " Callers="
                            + Debug.getCallers(10));
        }
        if (changed) {
            this.mVisible = visible;
            if (this.mIsTargetView
                    && this.mVisible
                    && getAlpha() != 0
                    && this.mRectPath.isEmpty()) {
                Log.i(TAG, "setVisible: mRectPath is empty, need to call updatePath");
                updatePath();
            }
            this.mAggregator.onBlurDrawableUpdated(this);
        }
        return changed;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        if (this.mAlpha != alpha / 255.0f) {
            this.mAlpha = alpha / 255.0f;
            if (this.mIsTargetView
                    && getAlpha() != 0
                    && this.mVisible
                    && this.mRectPath.isEmpty()) {
                if (this.mShowDebug) {
                    Log.i(
                            TAG,
                            "setAlpha: mRectPath is empty, need to call updatePath mAlpha="
                                    + this.mAlpha
                                    + " BackgroundBlurDrawable@"
                                    + hashCode()
                                    + " Callers="
                                    + Debug.getCallers(10));
                }
                updatePath();
            }
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setBlurRadius(int blurRadius) {
        if (this.mBlurRadius != blurRadius) {
            this.mBlurRadius = blurRadius;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setBlurColorCurve(SemBlurInfo.ColorCurve colorCurve) {
        if (this.mColorCurve == null || !this.mColorCurve.equals(colorCurve)) {
            this.mColorCurve = colorCurve;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setCornerRadius(float cornerRadius) {
        setCornerRadius(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
    }

    public void setCornerRadius(
            float cornerRadiusTL,
            float cornerRadiusTR,
            float cornerRadiusBL,
            float cornerRadiusBR) {
        if (this.mCornerRadiusTL != cornerRadiusTL
                || this.mCornerRadiusTR != cornerRadiusTR
                || this.mCornerRadiusBL != cornerRadiusBL
                || this.mCornerRadiusBR != cornerRadiusBR) {
            this.mCornerRadiusTL = cornerRadiusTL;
            this.mCornerRadiusTR = cornerRadiusTR;
            this.mCornerRadiusBL = cornerRadiusBL;
            this.mCornerRadiusBR = cornerRadiusBR;
            updatePath();
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        if (this.mShowDebug) {
            Log.i(
                    TAG,
                    "setBounds: BackgroundBlurDrawable@"
                            + hashCode()
                            + " setPosition("
                            + left
                            + ", "
                            + top
                            + ", "
                            + right
                            + ", "
                            + bottom
                            + NavigationBarInflaterView.KEY_CODE_END
                            + " Callers="
                            + Debug.getCallers(10));
        }
        this.mRenderNode.setPosition(left, top, right, bottom);
        updatePath();
    }

    private void updatePath() {
        float[] fArr = this.mTmpRadii;
        float[] fArr2 = this.mTmpRadii;
        float f = this.mCornerRadiusTL;
        fArr2[1] = f;
        fArr[0] = f;
        float[] fArr3 = this.mTmpRadii;
        float[] fArr4 = this.mTmpRadii;
        float f2 = this.mCornerRadiusTR;
        fArr4[3] = f2;
        fArr3[2] = f2;
        float[] fArr5 = this.mTmpRadii;
        float[] fArr6 = this.mTmpRadii;
        float f3 = this.mCornerRadiusBL;
        fArr6[5] = f3;
        fArr5[4] = f3;
        float[] fArr7 = this.mTmpRadii;
        float[] fArr8 = this.mTmpRadii;
        float f4 = this.mCornerRadiusBR;
        fArr8[7] = f4;
        fArr7[6] = f4;
        this.mRectPath.reset();
        if (getAlpha() == 0 || !isVisible()) {
            return;
        }
        Rect bounds = getBounds();
        this.mRectPath.addRoundRect(
                bounds.left,
                bounds.top,
                bounds.right,
                bounds.bottom,
                this.mTmpRadii,
                Path.Direction.CW);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new IllegalArgumentException("not implemented");
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public String toString() {
        return "BackgroundBlurDrawable{@"
                + hashCode()
                + " blurRadius="
                + this.mBlurRadius
                + ", corners={"
                + this.mCornerRadiusTL
                + ","
                + this.mCornerRadiusTR
                + ","
                + this.mCornerRadiusBL
                + ","
                + this.mCornerRadiusBR
                + "}, alpha="
                + this.mAlpha
                + ", visible="
                + this.mVisible
                + ", rect="
                + this.mRect
                + (this.mColorCurve != null ? ", blurColorCurve=" + this.mColorCurve : "")
                + "}";
    }

    public static final class Aggregator {
        private boolean mHasUiUpdates;
        private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
        private ViewRootImpl mViewRoot;
        private final Object mRtLock = new Object();
        private final LinkedHashSet<BackgroundBlurDrawable> mDrawables = new LinkedHashSet<>();
        private final LongSparseArray<ArraySet<Runnable>> mFrameRtUpdates = new LongSparseArray<>();
        private long mLastFrameNumber = 0;
        private BlurRegion[] mLastFrameBlurRegions = null;
        private BlurRegion[] mTmpBlurRegionsForFrame = new BlurRegion[0];

        public Aggregator(ViewRootImpl viewRoot) {
            setViewRoot(viewRoot);
        }

        public void setViewRoot(ViewRootImpl viewRoot) {
            this.mViewRoot = viewRoot;
        }

        public BackgroundBlurDrawable createBackgroundBlurDrawable(Context context) {
            BackgroundBlurDrawable drawable = new BackgroundBlurDrawable(this);
            drawable.setBlurRadius(
                    context.getResources()
                            .getDimensionPixelSize(R.dimen.default_background_blur_radius));
            return drawable;
        }

        public BackgroundBlurDrawable createBackgroundBlurDrawable(
                Context context, boolean showDebug) {
            BackgroundBlurDrawable drawable = new BackgroundBlurDrawable(this, showDebug);
            drawable.setBlurRadius(
                    context.getResources()
                            .getDimensionPixelSize(R.dimen.default_background_blur_radius));
            return drawable;
        }

        void onBlurDrawableUpdated(BackgroundBlurDrawable drawable) {
            boolean showDebug = false;
            boolean shouldBeDrawn =
                    drawable.mAlpha != 0.0f && drawable.mBlurRadius > 0 && drawable.mVisible;
            boolean isDrawn = this.mDrawables.contains(drawable);
            if (drawable.isShowDebug()
                    && ((shouldBeDrawn && !isDrawn) || (!shouldBeDrawn && isDrawn))) {
                showDebug = true;
            }
            if (BackgroundBlurDrawable.DEBUG || showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "onBlurDrawableUpdated BackgroundBlurDrawable@"
                                + drawable.hashCode()
                                + " rect="
                                + drawable.mRect
                                + " bounds="
                                + drawable.getBounds()
                                + ", renderNode w="
                                + drawable.mRenderNode.getWidth()
                                + " h="
                                + drawable.mRenderNode.getHeight()
                                + ", shouldBeDrawn="
                                + shouldBeDrawn
                                + ", isDrawn="
                                + shouldBeDrawn
                                + ", size="
                                + this.mDrawables.size()
                                + ", visible="
                                + drawable.mVisible
                                + ", Callers="
                                + Debug.getCallers(5));
            }
            if (shouldBeDrawn) {
                this.mHasUiUpdates = true;
                if (!isDrawn) {
                    this.mDrawables.add(drawable);
                    if (showDebug) {
                        Log.i(
                                BackgroundBlurDrawable.TAG,
                                "Add BackgroundBlurDrawable@" + drawable.hashCode());
                    } else if (BackgroundBlurDrawable.DEBUG) {
                        Log.d(BackgroundBlurDrawable.TAG, "Add " + drawable);
                    }
                } else if (showDebug) {
                    Log.i(
                            BackgroundBlurDrawable.TAG,
                            "Update BackgroundBlurDrawable@" + drawable.hashCode());
                } else if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, "Update " + drawable);
                }
            } else if (!shouldBeDrawn && isDrawn) {
                this.mHasUiUpdates = true;
                this.mDrawables.remove(drawable);
                if (showDebug) {
                    Log.i(
                            BackgroundBlurDrawable.TAG,
                            "Remove BackgroundBlurDrawable@" + drawable.hashCode());
                } else if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, "Remove " + drawable);
                }
            }
            if (this.mOnPreDrawListener == null
                    && this.mViewRoot != null
                    && this.mViewRoot.getView() != null
                    && hasRegions()) {
                if (showDebug) {
                    Log.i(BackgroundBlurDrawable.TAG, "registerPreDrawListener");
                }
                registerPreDrawListener(showDebug);
            }
        }

        private void registerPreDrawListener(final boolean showDebug) {
            this.mOnPreDrawListener =
                    new ViewTreeObserver
                            .OnPreDrawListener() { // from class:
                                                   // com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda2
                        @Override // android.view.ViewTreeObserver.OnPreDrawListener
                        public final boolean onPreDraw() {
                            boolean lambda$registerPreDrawListener$1;
                            lambda$registerPreDrawListener$1 =
                                    BackgroundBlurDrawable.Aggregator.this
                                            .lambda$registerPreDrawListener$1(showDebug);
                            return lambda$registerPreDrawListener$1;
                        }
                    };
            this.mViewRoot
                    .getView()
                    .getViewTreeObserver()
                    .addOnPreDrawListener(this.mOnPreDrawListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$registerPreDrawListener$1(boolean debug) {
            final boolean hasUiUpdates = hasUpdates();
            if (hasUiUpdates || hasRegions()) {
                final BlurRegion[] blurRegionsForNextFrame = getBlurRegionsCopyForRT();
                this.mViewRoot.registerRtFrameCallback(
                        new HardwareRenderer
                                .FrameDrawingCallback() { // from class:
                                                          // com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda1
                            @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                            public final void onFrameDraw(long j) {
                                BackgroundBlurDrawable.Aggregator.this
                                        .lambda$registerPreDrawListener$0(
                                                blurRegionsForNextFrame, hasUiUpdates, j);
                            }
                        });
            }
            if (!hasRegions() && this.mViewRoot.getView() != null) {
                this.mViewRoot
                        .getView()
                        .getViewTreeObserver()
                        .removeOnPreDrawListener(this.mOnPreDrawListener);
                this.mOnPreDrawListener = null;
                if (debug) {
                    Log.i(BackgroundBlurDrawable.TAG, "removeOnPreDrawListener");
                    return true;
                }
                return true;
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerPreDrawListener$0(
                BlurRegion[] blurRegionsForNextFrame, boolean hasUiUpdates, long frame) {
            synchronized (this.mRtLock) {
                this.mLastFrameNumber = frame;
                this.mLastFrameBlurRegions = blurRegionsForNextFrame;
                handleDispatchBlurTransactionLocked(frame, blurRegionsForNextFrame, hasUiUpdates);
            }
        }

        void onRenderNodePositionChanged(long frameNumber, Runnable update) {
            synchronized (this.mRtLock) {
                ArraySet<Runnable> frameRtUpdates = this.mFrameRtUpdates.get(frameNumber);
                if (frameRtUpdates == null) {
                    frameRtUpdates = new ArraySet<>();
                    this.mFrameRtUpdates.put(frameNumber, frameRtUpdates);
                }
                frameRtUpdates.add(update);
                if (this.mLastFrameNumber == frameNumber) {
                    handleDispatchBlurTransactionLocked(
                            frameNumber, this.mLastFrameBlurRegions, true);
                }
            }
        }

        public boolean hasUpdates() {
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d(BackgroundBlurDrawable.TAG, "hasUpdates " + this.mHasUiUpdates);
            }
            return this.mHasUiUpdates;
        }

        public boolean hasRegions() {
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d(BackgroundBlurDrawable.TAG, "hasRegions " + this.mDrawables.size());
            }
            return this.mDrawables.size() > 0;
        }

        public BlurRegion[] getBlurRegionsCopyForRT() {
            if (this.mHasUiUpdates) {
                this.mTmpBlurRegionsForFrame = new BlurRegion[this.mDrawables.size()];
                Iterator itr = this.mDrawables.iterator();
                for (int i = 0; i < this.mDrawables.size(); i++) {
                    this.mTmpBlurRegionsForFrame[i] = new BlurRegion(itr.next());
                }
                if (CoreRune.FW_STACKED_BLUR_SUPPORTED) {
                    Arrays.sort(
                            this.mTmpBlurRegionsForFrame,
                            new Comparator() { // from class:
                                               // com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda0
                                @Override // java.util.Comparator
                                public final int compare(Object obj, Object obj2) {
                                    return BackgroundBlurDrawable.Aggregator
                                            .lambda$getBlurRegionsCopyForRT$2(
                                                    (BackgroundBlurDrawable.BlurRegion) obj,
                                                    (BackgroundBlurDrawable.BlurRegion) obj2);
                                }
                            });
                    if (BackgroundBlurDrawable.DEBUG) {
                        for (int i2 = 0; i2 < this.mDrawables.size(); i2++) {
                            Log.d(
                                    BackgroundBlurDrawable.TAG,
                                    "getBlurRegionsCopyForRT_"
                                            + i2
                                            + ", "
                                            + this.mTmpBlurRegionsForFrame[i2]);
                        }
                    }
                }
                this.mHasUiUpdates = false;
            }
            return this.mTmpBlurRegionsForFrame;
        }

        static /* synthetic */ int lambda$getBlurRegionsCopyForRT$2(
                BlurRegion br0, BlurRegion br1) {
            return br0.mTranslationZ - br1.mTranslationZ;
        }

        public float[][] getBlurRegionsForFrameLocked(
                long frameNumber, BlurRegion[] blurRegionsForFrame, boolean forceUpdate) {
            if (!forceUpdate
                    && (this.mFrameRtUpdates.size() == 0
                            || this.mFrameRtUpdates.keyAt(0) > frameNumber)) {
                return null;
            }
            while (this.mFrameRtUpdates.size() != 0
                    && this.mFrameRtUpdates.keyAt(0) <= frameNumber) {
                ArraySet<Runnable> frameUpdates = this.mFrameRtUpdates.valueAt(0);
                this.mFrameRtUpdates.removeAt(0);
                for (int i = 0; i < frameUpdates.size(); i++) {
                    frameUpdates.valueAt(i).run();
                }
            }
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d(
                        BackgroundBlurDrawable.TAG,
                        "Dispatching " + blurRegionsForFrame.length + " blur regions:");
            }
            float[][] blurRegionsArray = new float[blurRegionsForFrame.length][];
            for (int i2 = 0; i2 < blurRegionsArray.length; i2++) {
                blurRegionsArray[i2] = blurRegionsForFrame[i2].toFloatArray();
                if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, blurRegionsForFrame[i2].toString());
                }
            }
            return blurRegionsArray;
        }

        private void handleDispatchBlurTransactionLocked(
                long frameNumber, BlurRegion[] blurRegions, boolean forceUpdate) {
            float[][] blurRegionsArray =
                    getBlurRegionsForFrameLocked(frameNumber, blurRegions, forceUpdate);
            if (blurRegionsArray != null && this.mViewRoot != null) {
                this.mViewRoot.dispatchBlurRegions(blurRegionsArray, frameNumber);
            }
        }
    }

    public static final class BlurRegion {
        private static final int COLOR_CURVE_ITEM_SIZE = 16;
        private static final int DEFAULT_ITEM_SIZE = 10;
        public final float alpha;
        public final int blurRadius;
        public final SemBlurInfo.ColorCurve colorCurve;
        public final float cornerRadiusBL;
        public final float cornerRadiusBR;
        public final float cornerRadiusTL;
        public final float cornerRadiusTR;
        private int mTranslationZ;
        public final Rect rect;
        private boolean showDebug;

        BlurRegion(BackgroundBlurDrawable drawable) {
            this.showDebug = false;
            this.mTranslationZ = 0;
            this.alpha = drawable.mAlpha;
            this.blurRadius = drawable.mBlurRadius;
            float scaleValue = 1.0f;
            float translationZ = 0.0f;
            Drawable.Callback callback = drawable.getCallback();
            if (callback instanceof View) {
                View view = (View) callback;
                translationZ = view.getTranslationZ();
                scaleValue = view.getScaleX();
                ViewParent p = view.getParent();
                while (p != null && (p instanceof View)) {
                    View v = (View) p;
                    scaleValue *= v.getScaleX();
                    translationZ += v.getTranslationZ();
                    p = v.getParent();
                }
            }
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d(
                        "BackgroundBlurDrawable",
                        "scale : "
                                + scaleValue
                                + ",translationZ : "
                                + translationZ
                                + ", "
                                + drawable);
            }
            this.mTranslationZ = (int) translationZ;
            this.cornerRadiusTL = drawable.mCornerRadiusTL * scaleValue;
            this.cornerRadiusTR = drawable.mCornerRadiusTR * scaleValue;
            this.cornerRadiusBL = drawable.mCornerRadiusBL * scaleValue;
            this.cornerRadiusBR = drawable.mCornerRadiusBR * scaleValue;
            this.rect = drawable.mRect;
            this.colorCurve = drawable.mColorCurve;
            this.showDebug = drawable.isShowDebug();
            if (this.showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "BlurRegion@" + hashCode() + " drawable=" + drawable);
            }
        }

        float[] toFloatArray() {
            int size = this.colorCurve == null ? 10 : 16;
            float[] floatArray = new float[size];
            floatArray[0] = this.blurRadius;
            floatArray[1] = this.alpha;
            floatArray[2] = this.rect.left;
            floatArray[3] = this.rect.top;
            floatArray[4] = this.rect.right;
            floatArray[5] = this.rect.bottom;
            floatArray[6] = this.cornerRadiusTL;
            floatArray[7] = this.cornerRadiusTR;
            floatArray[8] = this.cornerRadiusBL;
            floatArray[9] = this.cornerRadiusBR;
            if (size == 16 && this.colorCurve != null) {
                floatArray[10] = this.colorCurve.mMinX;
                floatArray[11] = this.colorCurve.mMinY;
                floatArray[12] = this.colorCurve.mMaxX;
                floatArray[13] = this.colorCurve.mMaxY;
                floatArray[14] = this.colorCurve.mCurveBias;
                floatArray[15] = this.colorCurve.mSaturation;
            }
            if (this.showDebug) {
                Log.i(
                        BackgroundBlurDrawable.TAG,
                        "toFloatArray: BlurRegion@" + hashCode() + " rect=" + this.rect);
            }
            return floatArray;
        }

        public String toString() {
            return "BlurRegion{@"
                    + hashCode()
                    + " blurRadius="
                    + this.blurRadius
                    + ", corners={"
                    + this.cornerRadiusTL
                    + ","
                    + this.cornerRadiusTR
                    + ","
                    + this.cornerRadiusBL
                    + ","
                    + this.cornerRadiusBR
                    + "}, alpha="
                    + this.alpha
                    + ", rect="
                    + this.rect
                    + ", mTranslationZ="
                    + this.mTranslationZ
                    + (this.colorCurve != null ? ", blurColorCurve=" + this.colorCurve : "")
                    + "}";
        }
    }
}
