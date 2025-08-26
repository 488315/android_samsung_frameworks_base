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
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.internal.R;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes5.dex */
public final class BackgroundBlurDrawable extends Drawable {
    private static final boolean DEBUG;
    private static final String TAG = "BackgroundBlurDrawable";
    private final Aggregator mAggregator;
    private float mAlpha;
    private int mBlurRadius;
    private int mClipRectBottom;
    private int mClipRectLeft;
    private int mClipRectRight;
    private int mClipRectTop;
    private SemBlurInfo.ColorCurve mColorCurve;
    private float mCornerRadiusBL;
    private float mCornerRadiusBR;
    private float mCornerRadiusTL;
    private float mCornerRadiusTR;
    private final Handler mHandler;
    private final Paint mPaint;
    public final RenderNode.PositionUpdateListener mPositionUpdateListener;
    private final Rect mRect;
    private final Path mRectPath;
    private final RenderNode mRenderNode;
    private float mScaleX;
    private float mScaleY;
    private final float[] mTmpRadii;
    private boolean mVisible;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0013  */
    static {
        boolean z;
        if (!Log.isLoggable("BackgroundBlurDrawable", 3)) {
            z = SystemProperties.getInt("viewroot.debug.blur", 0) != 0;
        }
        DEBUG = z;
    }

    /* renamed from: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1, reason: invalid class name */
    class AnonymousClass1 implements RenderNode.PositionUpdateListener {
        AnonymousClass1() {
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(final long j, final int i, final int i2, final int i3, final int i4) {
            BackgroundBlurDrawable.this.mRect.set(i, i2, i3, i4);
            if (BackgroundBlurDrawable.DEBUG) {
                Log.i(BackgroundBlurDrawable.TAG, "positionChanged fn=" + j + " drawable=" + BackgroundBlurDrawable.this + ", left : " + i + ", top : " + i2 + ", right : " + i3 + ", bottom : " + i4);
                Drawable.Callback callback = BackgroundBlurDrawable.this.getCallback();
                if (callback instanceof View) {
                    View view = (View) callback;
                    ViewRootImpl viewRootImpl = view.getViewRootImpl();
                    String str = BackgroundBlurDrawable.TAG;
                    StringBuilder sb = new StringBuilder("positionChanged attached View=");
                    sb.append(view);
                    sb.append(", viewRoot=");
                    String tag = viewRootImpl;
                    if (viewRootImpl != null) {
                        tag = viewRootImpl.getTag();
                    }
                    sb.append((Object) tag);
                    Log.i(str, sb.toString());
                } else {
                    Log.i(BackgroundBlurDrawable.TAG, "positionChanged attached callback=" + callback);
                }
            }
            BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(j, new Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$positionChanged$1(j, i, i2, i3, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionChanged$1(final long j, int i, int i2, int i3, int i4) {
            if (BackgroundBlurDrawable.DEBUG) {
                Log.i(BackgroundBlurDrawable.TAG, "positionChanged$run fn=" + j + " dr=BackgroundBlurDrawable@" + BackgroundBlurDrawable.this.hashCode() + " rect=" + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mHandler.post(new Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$positionChanged$0(j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionChanged$0(long j) {
            if (BackgroundBlurDrawable.DEBUG) {
                Log.i(BackgroundBlurDrawable.TAG, "positionChanged$run#2 fn=" + j + " dr=BackgroundBlurDrawable@" + BackgroundBlurDrawable.this.hashCode() + " rect=" + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.invalidateSelf();
            BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(BackgroundBlurDrawable.this);
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(final long j) {
            final boolean z = BackgroundBlurDrawable.DEBUG;
            BackgroundBlurDrawable.this.mRect.setEmpty();
            if (BackgroundBlurDrawable.DEBUG || z) {
                Log.i(BackgroundBlurDrawable.TAG, "positionLost fn=" + j + " dr=BackgroundBlurDrawable@" + BackgroundBlurDrawable.this.hashCode() + " rect=" + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(j, new Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$positionLost$3(z, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionLost$3(final boolean z, final long j) {
            if (BackgroundBlurDrawable.DEBUG || z) {
                Log.i(BackgroundBlurDrawable.TAG, "positionLost$run fn=" + j + " dr=BackgroundBlurDrawable@" + BackgroundBlurDrawable.this.hashCode() + " rect=" + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.mHandler.post(new Runnable() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$positionLost$2(z, j);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$positionLost$2(boolean z, long j) {
            if (BackgroundBlurDrawable.DEBUG || z) {
                Log.i(BackgroundBlurDrawable.TAG, "positionLost$run#2 fn=" + j + " dr=BackgroundBlurDrawable@" + BackgroundBlurDrawable.this.hashCode() + " rect=" + BackgroundBlurDrawable.this.mRect);
            }
            BackgroundBlurDrawable.this.invalidateSelf();
            BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(BackgroundBlurDrawable.this);
        }
    }

    public boolean isShowDebug() {
        return DEBUG;
    }

    private BackgroundBlurDrawable(Aggregator aggregator, boolean z) {
        this.mColorCurve = null;
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mRectPath = new Path();
        this.mTmpRadii = new float[8];
        this.mVisible = true;
        this.mAlpha = 1.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mRect = new Rect();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mPositionUpdateListener = anonymousClass1;
        this.mAggregator = aggregator;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setColor(0);
        paint.setAntiAlias(true);
        RenderNode renderNode = new RenderNode("BackgroundBlurDrawable");
        this.mRenderNode = renderNode;
        renderNode.addPositionUpdateListener(anonymousClass1);
        this.mHandler = new Handler(Looper.myLooper());
    }

    private BackgroundBlurDrawable(Aggregator aggregator) {
        this.mColorCurve = null;
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mRectPath = new Path();
        this.mTmpRadii = new float[8];
        this.mVisible = true;
        this.mAlpha = 1.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mRect = new Rect();
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mPositionUpdateListener = anonymousClass1;
        this.mAggregator = aggregator;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        paint.setColor(0);
        paint.setAntiAlias(true);
        RenderNode renderNode = new RenderNode("BackgroundBlurDrawable");
        this.mRenderNode = renderNode;
        renderNode.addPositionUpdateListener(anonymousClass1);
        this.mHandler = new Handler(Looper.myLooper());
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mRectPath.isEmpty() || !isVisible() || getAlpha() == 0) {
            if (DEBUG) {
                Log.i(TAG, "draw: BackgroundBlurDrawable@" + hashCode() + " bounds=" + getBounds() + ", mRectPath.isEmpty()=" + this.mRectPath.isEmpty() + " isVisible()=" + isVisible() + " getAlpha()=" + getAlpha() + " mAlpha=" + this.mAlpha + " return");
                return;
            }
            return;
        }
        if (canvas.isHardwareAccelerated()) {
            canvas.drawPath(this.mRectPath, this.mPaint);
            if (DEBUG) {
                Log.i(TAG, "draw: BackgroundBlurDrawable@" + hashCode() + " bounds=" + getBounds() + ", drawRenderNode w=" + this.mRenderNode.getWidth() + " h=" + this.mRenderNode.getHeight());
            }
            canvas.drawRenderNode(this.mRenderNode);
            return;
        }
        Log.i(TAG, "BackgroundBlur is not supported on S/W canvas!!!!");
    }

    public void setColor(int i) {
        this.mPaint.setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            this.mVisible = z;
            if (z) {
                if (getAlpha() != 0 && this.mRectPath.isEmpty()) {
                    if (DEBUG) {
                        Log.i(TAG, "setVisible: mRectPath is empty, need to call updatePath");
                    }
                    updatePath();
                }
                Drawable.Callback callback = getCallback();
                if (callback instanceof View) {
                    ((View) callback).postInvalidate();
                }
            }
            this.mAggregator.onBlurDrawableUpdated(this);
        }
        return visible;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        float f = i / 255.0f;
        if (this.mAlpha != f) {
            this.mAlpha = f;
            if (getAlpha() != 0 && this.mVisible && this.mRectPath.isEmpty()) {
                if (DEBUG) {
                    Log.i(TAG, "setAlpha: mRectPath is empty, need to call updatePath");
                }
                updatePath();
            }
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setBlurRadius(int i) {
        if (this.mBlurRadius != i) {
            this.mBlurRadius = i;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setBlurColorCurve(SemBlurInfo.ColorCurve colorCurve) {
        SemBlurInfo.ColorCurve colorCurve2 = this.mColorCurve;
        if (colorCurve2 == null || !colorCurve2.equals(colorCurve)) {
            this.mColorCurve = colorCurve;
            invalidateSelf();
            this.mAggregator.onBlurDrawableUpdated(this);
        }
    }

    public void setClipRect(int i, int i2, int i3, int i4) {
        this.mClipRectLeft = i;
        this.mClipRectTop = i2;
        this.mClipRectRight = i3;
        this.mClipRectBottom = i4;
        invalidateSelf();
        this.mAggregator.onBlurDrawableUpdated(this);
    }

    public void setCornerRadius(float f) {
        setCornerRadius(f, f, f, f);
    }

    public void setCornerRadius(float f, float f2, float f3, float f4) {
        if (this.mCornerRadiusTL == f && this.mCornerRadiusTR == f2 && this.mCornerRadiusBL == f3 && this.mCornerRadiusBR == f4) {
            return;
        }
        this.mCornerRadiusTL = f;
        this.mCornerRadiusTR = f2;
        this.mCornerRadiusBL = f3;
        this.mCornerRadiusBR = f4;
        updatePath();
        invalidateSelf();
        this.mAggregator.onBlurDrawableUpdated(this);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        if (DEBUG) {
            Log.i(TAG, "setBounds: BackgroundBlurDrawable@" + hashCode() + " setPosition(" + i + ", " + i2 + ", " + i3 + ", " + i4 + ") Callers=" + Debug.getCallers(10));
        }
        this.mRenderNode.setPosition(i, i2, i3, i4);
        updatePath();
    }

    private void updatePath() {
        float[] fArr = this.mTmpRadii;
        float f = this.mCornerRadiusTL;
        fArr[1] = f;
        fArr[0] = f;
        float f2 = this.mCornerRadiusTR;
        fArr[3] = f2;
        fArr[2] = f2;
        float f3 = this.mCornerRadiusBR;
        fArr[5] = f3;
        fArr[4] = f3;
        float f4 = this.mCornerRadiusBL;
        fArr[7] = f4;
        fArr[6] = f4;
        this.mRectPath.reset();
        if (getAlpha() == 0 || !isVisible()) {
            return;
        }
        Rect bounds = getBounds();
        this.mRectPath.addRoundRect(bounds.left, bounds.top, bounds.right, bounds.bottom, this.mTmpRadii, Path.Direction.CW);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new IllegalArgumentException("not implemented");
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("BackgroundBlurDrawable{@");
        sb.append(hashCode());
        sb.append(" blurRadius=");
        sb.append(this.mBlurRadius);
        sb.append(", corners={");
        sb.append(this.mCornerRadiusTL);
        sb.append(",");
        sb.append(this.mCornerRadiusTR);
        sb.append(",");
        sb.append(this.mCornerRadiusBL);
        sb.append(",");
        sb.append(this.mCornerRadiusBR);
        sb.append("}, alpha=");
        sb.append(this.mAlpha);
        sb.append(", visible=");
        sb.append(this.mVisible);
        sb.append(", rect=");
        sb.append(this.mRect);
        sb.append(", clipRect={");
        sb.append(this.mClipRectLeft);
        sb.append(",");
        sb.append(this.mClipRectTop);
        sb.append(",");
        sb.append(this.mClipRectRight);
        sb.append(",");
        sb.append(this.mClipRectBottom);
        sb.append("}");
        if (this.mColorCurve != null) {
            str = ", blurColorCurve=" + this.mColorCurve;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("}");
        return sb.toString();
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

        public Aggregator(ViewRootImpl viewRootImpl) {
            setViewRoot(viewRootImpl);
        }

        public void setViewRoot(ViewRootImpl viewRootImpl) {
            this.mViewRoot = viewRootImpl;
        }

        public BackgroundBlurDrawable createBackgroundBlurDrawable(Context context) {
            BackgroundBlurDrawable backgroundBlurDrawable = new BackgroundBlurDrawable(this);
            backgroundBlurDrawable.setBlurRadius(context.getResources().getDimensionPixelSize(R.dimen.default_background_blur_radius));
            return backgroundBlurDrawable;
        }

        public BackgroundBlurDrawable createBackgroundBlurDrawable(Context context, boolean z) {
            BackgroundBlurDrawable backgroundBlurDrawable = new BackgroundBlurDrawable(this, z);
            backgroundBlurDrawable.setBlurRadius(context.getResources().getDimensionPixelSize(R.dimen.default_background_blur_radius));
            return backgroundBlurDrawable;
        }

        void onBlurDrawableUpdated(BackgroundBlurDrawable backgroundBlurDrawable) {
            ViewRootImpl viewRootImpl;
            boolean z = false;
            boolean z2 = backgroundBlurDrawable.mAlpha != 0.0f && backgroundBlurDrawable.mBlurRadius > 0 && backgroundBlurDrawable.mVisible;
            boolean zContains = this.mDrawables.contains(backgroundBlurDrawable);
            if (backgroundBlurDrawable.isShowDebug() && ((z2 && !zContains) || (!z2 && zContains))) {
                z = true;
            }
            if (BackgroundBlurDrawable.DEBUG || z) {
                Log.i(BackgroundBlurDrawable.TAG, "onBlurDrawableUpdated BackgroundBlurDrawable@" + backgroundBlurDrawable.hashCode() + " rect=" + backgroundBlurDrawable.mRect + " bounds=" + backgroundBlurDrawable.getBounds() + ", renderNode w=" + backgroundBlurDrawable.mRenderNode.getWidth() + " h=" + backgroundBlurDrawable.mRenderNode.getHeight() + ", shouldBeDrawn=" + z2 + ", isDrawn=" + z2 + ", size=" + this.mDrawables.size() + ", visible=" + backgroundBlurDrawable.mVisible + ", Callers=" + Debug.getCallers(5));
            }
            if (z2) {
                this.mHasUiUpdates = true;
                if (!zContains) {
                    this.mDrawables.add(backgroundBlurDrawable);
                    if (z) {
                        Log.i(BackgroundBlurDrawable.TAG, "Add BackgroundBlurDrawable@" + backgroundBlurDrawable.hashCode());
                    } else if (BackgroundBlurDrawable.DEBUG) {
                        Log.d(BackgroundBlurDrawable.TAG, "Add " + backgroundBlurDrawable);
                    }
                } else if (z) {
                    Log.i(BackgroundBlurDrawable.TAG, "Update BackgroundBlurDrawable@" + backgroundBlurDrawable.hashCode());
                } else if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, "Update " + backgroundBlurDrawable);
                }
            } else if (!z2 && zContains) {
                this.mHasUiUpdates = true;
                this.mDrawables.remove(backgroundBlurDrawable);
                if (z) {
                    Log.i(BackgroundBlurDrawable.TAG, "Remove BackgroundBlurDrawable@" + backgroundBlurDrawable.hashCode());
                } else if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, "Remove " + backgroundBlurDrawable);
                }
            }
            if (this.mOnPreDrawListener != null || (viewRootImpl = this.mViewRoot) == null || viewRootImpl.getView() == null || !hasRegions()) {
                return;
            }
            if (z) {
                Log.i(BackgroundBlurDrawable.TAG, "registerPreDrawListener");
            }
            registerPreDrawListener(z);
        }

        private void registerPreDrawListener(final boolean z) {
            this.mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public final boolean onPreDraw() {
                    return this.f$0.lambda$registerPreDrawListener$1(z);
                }
            };
            this.mViewRoot.getView().getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$registerPreDrawListener$1(boolean z) {
            final boolean zHasUpdates = hasUpdates();
            if (zHasUpdates || hasRegions()) {
                final BlurRegion[] blurRegionsCopyForRT = getBlurRegionsCopyForRT();
                this.mViewRoot.registerRtFrameCallback(new HardwareRenderer.FrameDrawingCallback() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda1
                    @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                    public final void onFrameDraw(long j) {
                        this.f$0.lambda$registerPreDrawListener$0(blurRegionsCopyForRT, zHasUpdates, j);
                    }
                });
            }
            if (hasRegions() || this.mViewRoot.getView() == null) {
                return true;
            }
            this.mViewRoot.getView().getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
            this.mOnPreDrawListener = null;
            if (!z) {
                return true;
            }
            Log.i(BackgroundBlurDrawable.TAG, "removeOnPreDrawListener");
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$registerPreDrawListener$0(BlurRegion[] blurRegionArr, boolean z, long j) {
            synchronized (this.mRtLock) {
                this.mLastFrameNumber = j;
                this.mLastFrameBlurRegions = blurRegionArr;
                handleDispatchBlurTransactionLocked(j, blurRegionArr, z);
            }
        }

        void onRenderNodePositionChanged(long j, Runnable runnable) {
            synchronized (this.mRtLock) {
                ArraySet<Runnable> arraySet = this.mFrameRtUpdates.get(j);
                if (arraySet == null) {
                    arraySet = new ArraySet<>();
                    this.mFrameRtUpdates.put(j, arraySet);
                }
                arraySet.add(runnable);
                if (this.mLastFrameNumber == j) {
                    handleDispatchBlurTransactionLocked(j, this.mLastFrameBlurRegions, true);
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
                Iterator<BackgroundBlurDrawable> it = this.mDrawables.iterator();
                for (int i = 0; i < this.mDrawables.size(); i++) {
                    this.mTmpBlurRegionsForFrame[i] = new BlurRegion(it.next());
                }
                if (CoreRune.FW_STACKED_BLUR_SUPPORTED) {
                    Arrays.sort(this.mTmpBlurRegionsForFrame, new Comparator() { // from class: com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return BackgroundBlurDrawable.Aggregator.lambda$getBlurRegionsCopyForRT$2((BackgroundBlurDrawable.BlurRegion) obj, (BackgroundBlurDrawable.BlurRegion) obj2);
                        }
                    });
                    if (BackgroundBlurDrawable.DEBUG) {
                        for (int i2 = 0; i2 < this.mDrawables.size(); i2++) {
                            Log.d(BackgroundBlurDrawable.TAG, "getBlurRegionsCopyForRT_" + i2 + ", " + this.mTmpBlurRegionsForFrame[i2]);
                        }
                    }
                }
                this.mHasUiUpdates = false;
            }
            return this.mTmpBlurRegionsForFrame;
        }

        static /* synthetic */ int lambda$getBlurRegionsCopyForRT$2(BlurRegion blurRegion, BlurRegion blurRegion2) {
            return blurRegion.mTranslationZ - blurRegion2.mTranslationZ;
        }

        public float[][] getBlurRegionsForFrameLocked(long j, BlurRegion[] blurRegionArr, boolean z) {
            if (!z && (this.mFrameRtUpdates.size() == 0 || this.mFrameRtUpdates.keyAt(0) > j)) {
                return null;
            }
            while (this.mFrameRtUpdates.size() != 0 && this.mFrameRtUpdates.keyAt(0) <= j) {
                ArraySet<Runnable> arraySetValueAt = this.mFrameRtUpdates.valueAt(0);
                this.mFrameRtUpdates.removeAt(0);
                for (int i = 0; i < arraySetValueAt.size(); i++) {
                    arraySetValueAt.valueAt(i).run();
                }
            }
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d(BackgroundBlurDrawable.TAG, "Dispatching " + blurRegionArr.length + " blur regions:");
            }
            int length = blurRegionArr.length;
            float[][] fArr = new float[length][];
            for (int i2 = 0; i2 < length; i2++) {
                fArr[i2] = blurRegionArr[i2].toFloatArray();
                if (BackgroundBlurDrawable.DEBUG) {
                    Log.d(BackgroundBlurDrawable.TAG, blurRegionArr[i2].toString());
                }
            }
            return fArr;
        }

        private void handleDispatchBlurTransactionLocked(long j, BlurRegion[] blurRegionArr, boolean z) {
            ViewRootImpl viewRootImpl;
            float[][] blurRegionsForFrameLocked = getBlurRegionsForFrameLocked(j, blurRegionArr, z);
            if (blurRegionsForFrameLocked == null || (viewRootImpl = this.mViewRoot) == null) {
                return;
            }
            viewRootImpl.dispatchBlurRegions(blurRegionsForFrameLocked, j);
        }
    }

    public static final class BlurRegion {
        private static final int COLOR_CURVE_ITEM_SIZE = 20;
        private static final int DEFAULT_ITEM_SIZE = 14;
        public final float alpha;
        public final int blurRadius;
        public final int clipRectBottom;
        public final int clipRectLeft;
        public final int clipRectRight;
        public final int clipRectTop;
        public final SemBlurInfo.ColorCurve colorCurve;
        public final float cornerRadiusBL;
        public final float cornerRadiusBR;
        public final float cornerRadiusTL;
        public final float cornerRadiusTR;
        private int mTranslationZ;
        public final Rect rect;
        private boolean showDebug;

        BlurRegion(BackgroundBlurDrawable backgroundBlurDrawable) {
            float scaleX;
            float translationZ;
            this.showDebug = false;
            this.mTranslationZ = 0;
            this.alpha = backgroundBlurDrawable.mAlpha;
            this.blurRadius = backgroundBlurDrawable.mBlurRadius;
            Drawable.Callback callback = backgroundBlurDrawable.getCallback();
            if (callback instanceof View) {
                View view = (View) callback;
                translationZ = view.getTranslationZ();
                scaleX = view.getScaleX();
                Object parent = view.getParent();
                while (parent != null && (parent instanceof View)) {
                    View view2 = (View) parent;
                    scaleX *= view2.getScaleX();
                    translationZ += view2.getTranslationZ();
                    parent = view2.getParent();
                }
            } else {
                scaleX = 1.0f;
                translationZ = 0.0f;
            }
            if (BackgroundBlurDrawable.DEBUG) {
                Log.d("BackgroundBlurDrawable", "scale : " + scaleX + ",translationZ : " + translationZ + ", " + backgroundBlurDrawable);
            }
            this.mTranslationZ = (int) translationZ;
            this.cornerRadiusTL = backgroundBlurDrawable.mCornerRadiusTL * scaleX;
            this.cornerRadiusTR = backgroundBlurDrawable.mCornerRadiusTR * scaleX;
            this.cornerRadiusBL = backgroundBlurDrawable.mCornerRadiusBL * scaleX;
            this.cornerRadiusBR = backgroundBlurDrawable.mCornerRadiusBR * scaleX;
            this.clipRectLeft = backgroundBlurDrawable.mClipRectLeft;
            this.clipRectTop = backgroundBlurDrawable.mClipRectTop;
            this.clipRectRight = backgroundBlurDrawable.mClipRectRight;
            this.clipRectBottom = backgroundBlurDrawable.mClipRectBottom;
            this.rect = backgroundBlurDrawable.mRect;
            this.colorCurve = backgroundBlurDrawable.mColorCurve;
            boolean zIsShowDebug = backgroundBlurDrawable.isShowDebug();
            this.showDebug = zIsShowDebug;
            if (zIsShowDebug) {
                Log.i(BackgroundBlurDrawable.TAG, "BlurRegion@" + hashCode() + " drawable=" + backgroundBlurDrawable);
            }
        }

        float[] toFloatArray() {
            SemBlurInfo.ColorCurve colorCurve;
            int i = this.colorCurve == null ? 14 : 20;
            float[] fArr = new float[i];
            fArr[0] = this.blurRadius;
            fArr[1] = this.alpha;
            fArr[2] = this.rect.left;
            fArr[3] = this.rect.top;
            fArr[4] = this.rect.right;
            fArr[5] = this.rect.bottom;
            fArr[6] = this.cornerRadiusTL;
            fArr[7] = this.cornerRadiusTR;
            fArr[8] = this.cornerRadiusBL;
            fArr[9] = this.cornerRadiusBR;
            fArr[10] = this.clipRectLeft;
            fArr[11] = this.clipRectTop;
            fArr[12] = this.clipRectRight;
            fArr[13] = this.clipRectBottom;
            if (i == 20 && (colorCurve = this.colorCurve) != null) {
                fArr[14] = colorCurve.mMinX;
                fArr[15] = this.colorCurve.mMinY;
                fArr[16] = this.colorCurve.mMaxX;
                fArr[17] = this.colorCurve.mMaxY;
                fArr[18] = this.colorCurve.mCurveBias;
                fArr[19] = this.colorCurve.mSaturation;
            }
            if (this.showDebug) {
                Log.i(BackgroundBlurDrawable.TAG, "toFloatArray: BlurRegion@" + hashCode() + " rect=" + this.rect);
            }
            return fArr;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder("BlurRegion{@");
            sb.append(hashCode());
            sb.append(" blurRadius=");
            sb.append(this.blurRadius);
            sb.append(", corners={");
            sb.append(this.cornerRadiusTL);
            sb.append(",");
            sb.append(this.cornerRadiusTR);
            sb.append(",");
            sb.append(this.cornerRadiusBL);
            sb.append(",");
            sb.append(this.cornerRadiusBR);
            sb.append("}, alpha=");
            sb.append(this.alpha);
            sb.append(", rect=");
            sb.append(this.rect);
            sb.append(", clipRect={");
            sb.append(this.clipRectLeft);
            sb.append(",");
            sb.append(this.clipRectTop);
            sb.append(",");
            sb.append(this.clipRectRight);
            sb.append(",");
            sb.append(this.clipRectBottom);
            sb.append("}, mTranslationZ=");
            sb.append(this.mTranslationZ);
            if (this.colorCurve != null) {
                str = ", blurColorCurve=" + this.colorCurve;
            } else {
                str = "";
            }
            sb.append(str);
            sb.append("}");
            return sb.toString();
        }
    }
}
