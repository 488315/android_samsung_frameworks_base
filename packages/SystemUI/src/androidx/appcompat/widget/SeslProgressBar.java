package androidx.appcompat.widget;

import android.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.math.MathUtils;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.reflect.view.SeslViewReflector;
import com.sec.ims.settings.ImsProfile;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.WeakHashMap;

@RemoteViews.RemoteView
/* loaded from: classes.dex */
public class SeslProgressBar extends View {
    public static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();
    public final AnonymousClass1 VISUAL_PROGRESS;
    public AccessibilityEventSender mAccessibilityEventSender;
    public boolean mAggregatedIsVisible;
    public AlphaAnimation mAnimation;
    public boolean mAttached;
    public final int mBehavior;
    public Locale mCachedLocale;
    public CircleAnimationCallback mCircleAnimationCallback;
    public int mCirclePadding;
    public Drawable mCurrentDrawable;
    public int mCurrentMode;
    public final float mDensity;
    public final int mDuration;
    public boolean mHasAnimation;
    public boolean mInDrawing;
    public boolean mIndeterminate;
    public Drawable mIndeterminateDrawable;
    public final Drawable mIndeterminateHorizontalLarge;
    public final Drawable mIndeterminateHorizontalMedium;
    public final Drawable mIndeterminateHorizontalSmall;
    public final Drawable mIndeterminateHorizontalXlarge;
    public final Drawable mIndeterminateHorizontalXsmall;
    public Interpolator mInterpolator;
    public int mMax;
    public int mMaxHeight;
    public boolean mMaxInitialized;
    public int mMaxWidth;
    public int mMin;
    public final int mMinHeight;
    public boolean mMinInitialized;
    public final int mMinWidth;
    public final boolean mMirrorForRtl;
    public final boolean mNoInvalidate;
    public boolean mOnlyIndeterminate;
    public NumberFormat mPercentFormat;
    public int mProgress;
    public Drawable mProgressDrawable;
    public ProgressTintInfo mProgressTintInfo;
    public final ArrayList mRefreshData;
    public boolean mRefreshIsPosted;
    public RefreshProgressRunnable mRefreshProgressRunnable;
    public int mRoundStrokeWidth;
    public int mSampleWidth;
    public int mSecondaryProgress;
    public boolean mShouldStartAnimationDrawable;
    public Transformation mTransformation;
    public final long mUiThreadId;
    public final boolean mUseCustomWidthForCircleMode;
    public final boolean mUseHorizontalProgress;
    public float mVisualProgress;

    public class AccessibilityEventSender implements Runnable {
        private AccessibilityEventSender() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SeslProgressBar.this.sendAccessibilityEvent(4);
        }
    }

    public class CirCleProgressDrawable extends Drawable {
        public final AnonymousClass1 VISUAL_CIRCLE_PROGRESS;
        public int mAlpha;
        public final RectF mArcRect;
        public int mColor;
        public ColorStateList mColorStateList;
        public final boolean mIsBackground;
        public final Paint mPaint;
        public int mProgress;
        public final ProgressState mState;

        public class ProgressState extends Drawable.ConstantState {
            private ProgressState() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public final int getChangingConfigurations() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public final Drawable newDrawable() {
                return CirCleProgressDrawable.this;
            }
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.appcompat.widget.SeslProgressBar$CirCleProgressDrawable$1] */
        public CirCleProgressDrawable(boolean z, ColorStateList colorStateList) {
            Paint paint = new Paint();
            this.mPaint = paint;
            this.mAlpha = 255;
            this.mArcRect = new RectF();
            this.mState = new ProgressState();
            this.VISUAL_CIRCLE_PROGRESS = new IntProperty("visual_progress") { // from class: androidx.appcompat.widget.SeslProgressBar.CirCleProgressDrawable.1
                @Override // android.util.Property
                public final Integer get(Object obj) {
                    return Integer.valueOf(((CirCleProgressDrawable) obj).mProgress);
                }

                @Override // android.util.IntProperty
                public final void setValue(Object obj, int i) {
                    ((CirCleProgressDrawable) obj).mProgress = i;
                    CirCleProgressDrawable.this.invalidateSelf();
                }
            };
            this.mIsBackground = z;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = colorStateList;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            paint.setColor(defaultColor);
            this.mProgress = 0;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(SeslProgressBar.this.mRoundStrokeWidth);
            int alpha = this.mPaint.getAlpha();
            Paint paint = this.mPaint;
            int i = this.mAlpha;
            paint.setAlpha(((i + (i >>> 7)) * alpha) >>> 8);
            this.mPaint.setAntiAlias(true);
            RectF rectF = this.mArcRect;
            SeslProgressBar seslProgressBar = SeslProgressBar.this;
            int i2 = seslProgressBar.mRoundStrokeWidth;
            int i3 = seslProgressBar.mCirclePadding;
            float f = (i2 / 2.0f) + i3;
            float f2 = (i2 / 2.0f) + i3;
            float width = seslProgressBar.getWidth();
            float f3 = (width - (r7.mRoundStrokeWidth / 2.0f)) - r7.mCirclePadding;
            float width2 = SeslProgressBar.this.getWidth();
            SeslProgressBar seslProgressBar2 = SeslProgressBar.this;
            rectF.set(f, f2, f3, (width2 - (seslProgressBar2.mRoundStrokeWidth / 2.0f)) - seslProgressBar2.mCirclePadding);
            SeslProgressBar seslProgressBar3 = SeslProgressBar.this;
            int i4 = seslProgressBar3.mMax - seslProgressBar3.mMin;
            float f4 = i4 > 0 ? (this.mProgress - r2) / i4 : 0.0f;
            canvas.save();
            if (this.mIsBackground) {
                canvas.drawArc(this.mArcRect, 270.0f, 360.0f, false, this.mPaint);
            } else {
                canvas.drawArc(this.mArcRect, 270.0f, f4 * 360.0f, false, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public final Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            Paint paint = this.mPaint;
            if (paint.getXfermode() != null) {
                return -3;
            }
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                return -2;
            }
            return alpha == 255 ? -1 : -3;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public final boolean onStateChange(int[] iArr) {
            boolean zOnStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            return zOnStateChange;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            this.mAlpha = i;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public final void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            if (colorStateList != null) {
                this.mColorStateList = colorStateList;
                int defaultColor = colorStateList.getDefaultColor();
                this.mColor = defaultColor;
                this.mPaint.setColor(defaultColor);
                invalidateSelf();
            }
        }
    }

    public class CircleAnimationCallback extends Animatable2.AnimationCallback {
        public final Handler mHandler = new Handler(Looper.getMainLooper());
        public final WeakReference mProgressBar;

        public CircleAnimationCallback(SeslProgressBar seslProgressBar) {
            this.mProgressBar = new WeakReference(seslProgressBar);
        }

        @Override // android.graphics.drawable.Animatable2.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            this.mHandler.post(new Runnable() { // from class: androidx.appcompat.widget.SeslProgressBar.CircleAnimationCallback.1
                @Override // java.lang.Runnable
                public final void run() {
                    SeslProgressBar seslProgressBar = (SeslProgressBar) CircleAnimationCallback.this.mProgressBar.get();
                    if (seslProgressBar == null) {
                        return;
                    }
                    ((AnimatedVectorDrawable) seslProgressBar.mIndeterminateDrawable).start();
                }
            });
        }
    }

    public class ProgressTintInfo {
        public boolean mHasIndeterminateTint;
        public boolean mHasIndeterminateTintMode;
        public boolean mHasProgressBackgroundTint;
        public boolean mHasProgressBackgroundTintMode;
        public boolean mHasProgressTint;
        public boolean mHasProgressTintMode;
        public boolean mHasSecondaryProgressTint;
        public boolean mHasSecondaryProgressTintMode;
        public ColorStateList mIndeterminateTintList;
        public PorterDuff.Mode mIndeterminateTintMode;
        public ColorStateList mProgressBackgroundTintList;
        public PorterDuff.Mode mProgressBackgroundTintMode;
        public ColorStateList mProgressTintList;
        public PorterDuff.Mode mProgressTintMode;
        public ColorStateList mSecondaryProgressTintList;
        public PorterDuff.Mode mSecondaryProgressTintMode;

        private ProgressTintInfo() {
        }
    }

    public class RefreshData {
        public static final Pools$SynchronizedPool sPool = new Pools$SynchronizedPool(24);
        public boolean animate;
        public boolean fromUser;
        public int id;
        public int progress;

        private RefreshData() {
        }

        public static RefreshData obtain(int i, int i2, boolean z, boolean z2) {
            RefreshData refreshData = (RefreshData) sPool.acquire();
            if (refreshData == null) {
                refreshData = new RefreshData();
            }
            refreshData.id = i;
            refreshData.progress = i2;
            refreshData.fromUser = z;
            refreshData.animate = z2;
            return refreshData;
        }
    }

    public class RefreshProgressRunnable implements Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (SeslProgressBar.this) {
                try {
                    int size = SeslProgressBar.this.mRefreshData.size();
                    for (int i = 0; i < size; i++) {
                        RefreshData refreshData = (RefreshData) SeslProgressBar.this.mRefreshData.get(i);
                        SeslProgressBar.this.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                        RefreshData.sPool.release(refreshData);
                    }
                    SeslProgressBar.this.mRefreshData.clear();
                    SeslProgressBar.this.mRefreshIsPosted = false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.appcompat.widget.SeslProgressBar.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int progress;
        public int secondaryProgress;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.secondaryProgress);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.secondaryProgress = parcel.readInt();
        }
    }

    public SeslProgressBar(Context context) {
        this(context, null);
    }

    public static boolean needsTileify(Drawable drawable) {
        if (!(drawable instanceof LayerDrawable)) {
            if (!(drawable instanceof StateListDrawable)) {
                return drawable instanceof BitmapDrawable;
            }
            return false;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            if (needsTileify(layerDrawable.getDrawable(i))) {
                return true;
            }
        }
        return false;
    }

    public final void applyIndeterminateTint() {
        ProgressTintInfo progressTintInfo;
        Drawable drawable = this.mIndeterminateDrawable;
        if (drawable == null || (progressTintInfo = this.mProgressTintInfo) == null) {
            return;
        }
        if (progressTintInfo.mHasIndeterminateTint || progressTintInfo.mHasIndeterminateTintMode) {
            Drawable drawableMutate = drawable.mutate();
            this.mIndeterminateDrawable = drawableMutate;
            if (progressTintInfo.mHasIndeterminateTint) {
                drawableMutate.setTintList(progressTintInfo.mIndeterminateTintList);
            }
            if (progressTintInfo.mHasIndeterminateTintMode) {
                this.mIndeterminateDrawable.setTintMode(progressTintInfo.mIndeterminateTintMode);
            }
            if (this.mIndeterminateDrawable.isStateful()) {
                this.mIndeterminateDrawable.setState(getDrawableState());
            }
        }
    }

    public final void applyPrimaryProgressTint() {
        Drawable tintTarget;
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if ((progressTintInfo.mHasProgressTint || progressTintInfo.mHasProgressTintMode) && (tintTarget = getTintTarget(R.id.progress, true)) != null) {
            ProgressTintInfo progressTintInfo2 = this.mProgressTintInfo;
            if (progressTintInfo2.mHasProgressTint) {
                tintTarget.setTintList(progressTintInfo2.mProgressTintList);
            }
            ProgressTintInfo progressTintInfo3 = this.mProgressTintInfo;
            if (progressTintInfo3.mHasProgressTintMode) {
                tintTarget.setTintMode(progressTintInfo3.mProgressTintMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    public final void applyProgressBackgroundTint() {
        Drawable tintTarget;
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if ((progressTintInfo.mHasProgressBackgroundTint || progressTintInfo.mHasProgressBackgroundTintMode) && (tintTarget = getTintTarget(R.id.background, false)) != null) {
            ProgressTintInfo progressTintInfo2 = this.mProgressTintInfo;
            if (progressTintInfo2.mHasProgressBackgroundTint) {
                tintTarget.setTintList(progressTintInfo2.mProgressBackgroundTintList);
            }
            ProgressTintInfo progressTintInfo3 = this.mProgressTintInfo;
            if (progressTintInfo3.mHasProgressBackgroundTintMode) {
                tintTarget.setTintMode(progressTintInfo3.mProgressBackgroundTintMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    public final void applyProgressTints() {
        Drawable tintTarget;
        if (this.mProgressDrawable == null || this.mProgressTintInfo == null) {
            return;
        }
        applyPrimaryProgressTint();
        applyProgressBackgroundTint();
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if ((progressTintInfo.mHasSecondaryProgressTint || progressTintInfo.mHasSecondaryProgressTintMode) && (tintTarget = getTintTarget(R.id.secondaryProgress, false)) != null) {
            ProgressTintInfo progressTintInfo2 = this.mProgressTintInfo;
            if (progressTintInfo2.mHasSecondaryProgressTint) {
                tintTarget.setTintList(progressTintInfo2.mSecondaryProgressTintList);
            }
            ProgressTintInfo progressTintInfo3 = this.mProgressTintInfo;
            if (progressTintInfo3.mHasSecondaryProgressTintMode) {
                tintTarget.setTintMode(progressTintInfo3.mSecondaryProgressTintMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    public final synchronized void doRefreshProgress(int i, int i2, boolean z, boolean z2, boolean z3) {
        try {
            int i3 = this.mMax;
            int i4 = this.mMin;
            int i5 = i3 - i4;
            float f = i5 > 0 ? (i2 - i4) / i5 : 0.0f;
            float f2 = i5 > 0 ? (this.mVisualProgress - i4) / i5 : 0.0f;
            boolean z4 = i == 16908301;
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                int i6 = (int) (10000.0f * f);
                if (drawable instanceof LayerDrawable) {
                    Drawable drawableFindDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(i);
                    if (drawableFindDrawableByLayerId != null && canResolveLayoutDirection()) {
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        drawableFindDrawableByLayerId.setLayoutDirection(getLayoutDirection());
                    }
                    if (drawableFindDrawableByLayerId != null) {
                        drawable = drawableFindDrawableByLayerId;
                    }
                    drawable.setLevel(i6);
                } else if (drawable instanceof StateListDrawable) {
                } else {
                    drawable.setLevel(i6);
                }
            } else {
                invalidate();
            }
            if (z4 && z3) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, f2, f);
                objectAnimatorOfFloat.setAutoCancel(true);
                objectAnimatorOfFloat.setDuration(80L);
                objectAnimatorOfFloat.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
                objectAnimatorOfFloat.start();
            } else {
                setVisualProgress(f, i);
            }
            if (z4 && z2) {
                onProgressRefresh(f, z, i2);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawTrack(Canvas canvas) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int iSave = canvas.save();
            if (this.mCurrentMode != 3 && this.mMirrorForRtl && getLayoutDirection() == 1) {
                canvas.translate(getWidth() - SeslViewReflector.getField_mPaddingRight(this), getPaddingTop());
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(SeslViewReflector.getField_mPaddingLeft(this), getPaddingTop());
            }
            long drawingTime = getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(drawingTime, this.mTransformation);
                float alpha = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    drawable.setLevel((int) (alpha * 10000.0f));
                    this.mInDrawing = false;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    postInvalidateOnAnimation();
                } catch (Throwable th) {
                    this.mInDrawing = false;
                    throw th;
                }
            }
            drawable.draw(canvas);
            canvas.restoreToCount(iSave);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override // android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return ProgressBar.class.getName();
    }

    public synchronized int getMax() {
        return this.mMax;
    }

    public synchronized int getMin() {
        return this.mMin;
    }

    @Override // android.view.View
    public final int getPaddingLeft() {
        return SeslViewReflector.getField_mPaddingLeft(this);
    }

    @Override // android.view.View
    public final int getPaddingRight() {
        return SeslViewReflector.getField_mPaddingRight(this);
    }

    public synchronized int getProgress() {
        return this.mIndeterminate ? 0 : this.mProgress;
    }

    public final Drawable getTintTarget(int i, boolean z) {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            this.mProgressDrawable = drawable.mutate();
            drawableFindDrawableByLayerId = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(i) : null;
            if (z && drawableFindDrawableByLayerId == null) {
                return drawable;
            }
        }
        return drawableFindDrawableByLayerId;
    }

    public final void initCirCleStrokeWidth(int i) {
        if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_small) == i) {
            this.mRoundStrokeWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_small_width);
            this.mCirclePadding = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_small_padding);
            return;
        }
        if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_small_title) == i) {
            this.mRoundStrokeWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_small_title_width);
            this.mCirclePadding = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_small_title_padding);
        } else if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_large) == i) {
            this.mRoundStrokeWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_large_width);
            this.mCirclePadding = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_large_padding);
        } else if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_xlarge) == i) {
            this.mRoundStrokeWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_xlarge_width);
            this.mCirclePadding = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_xlarge_padding);
        } else {
            this.mRoundStrokeWidth = (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_small_width) * i) / getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_small);
            this.mCirclePadding = (getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_small_padding) * i) / getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_size_small);
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        if (this.mInDrawing) {
            return;
        }
        if (!verifyDrawable(drawable)) {
            super.invalidateDrawable(drawable);
            return;
        }
        Rect bounds = drawable.getBounds();
        int field_mPaddingLeft = SeslViewReflector.getField_mPaddingLeft(this) + getScrollX();
        int paddingTop = getPaddingTop() + getScrollY();
        invalidate(bounds.left + field_mPaddingLeft, bounds.top + paddingTop, bounds.right + field_mPaddingLeft, bounds.bottom + paddingTop);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public final void onAttachedToWindow() throws Throwable {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation$1();
        }
        synchronized (this) {
            try {
                try {
                    int size = this.mRefreshData.size();
                    int i = 0;
                    while (i < size) {
                        RefreshData refreshData = (RefreshData) this.mRefreshData.get(i);
                        SeslProgressBar seslProgressBar = this;
                        seslProgressBar.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                        RefreshData.sPool.release(refreshData);
                        i++;
                        this = seslProgressBar;
                    }
                    SeslProgressBar seslProgressBar2 = this;
                    seslProgressBar2.mRefreshData.clear();
                    seslProgressBar2.mAttached = true;
                } catch (Throwable th) {
                    th = th;
                    Throwable th2 = th;
                    throw th2;
                }
            } catch (Throwable th3) {
                th = th3;
                SeslProgressBar seslProgressBar3 = this;
                Throwable th22 = th;
                throw th22;
            }
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation$1();
        } else {
            this.mCircleAnimationCallback = null;
        }
        RefreshProgressRunnable refreshProgressRunnable = this.mRefreshProgressRunnable;
        if (refreshProgressRunnable != null) {
            removeCallbacks(refreshProgressRunnable);
            this.mRefreshIsPosted = false;
        }
        AccessibilityEventSender accessibilityEventSender = this.mAccessibilityEventSender;
        if (accessibilityEventSender != null) {
            removeCallbacks(accessibilityEventSender);
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setItemCount(this.mMax - this.mMin);
        accessibilityEvent.setCurrentItemIndex(this.mProgress);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        boolean z2;
        String string;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        synchronized (this) {
            z = this.mIndeterminate;
        }
        if (!z) {
            accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(0, getMin(), getMax(), getProgress()));
        }
        if (getStateDescription() == null) {
            synchronized (this) {
                z2 = this.mIndeterminate;
            }
            if (z2) {
                Context context = getContext();
                int identifier = context.getResources().getIdentifier("in_progress", "string", "android");
                if (identifier > 0) {
                    try {
                        string = context.getResources().getString(identifier);
                    } catch (Resources.NotFoundException unused) {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    string = "";
                }
                accessibilityNodeInfo.setStateDescription(string);
                return;
            }
            int progress = getProgress();
            Locale locale = getResources().getConfiguration().locale;
            if (!locale.equals(this.mCachedLocale) || this.mPercentFormat == null) {
                this.mCachedLocale = locale;
                this.mPercentFormat = NumberFormat.getPercentInstance(locale);
            }
            accessibilityNodeInfo.setStateDescription(this.mPercentFormat.format(getMax() - getMin() > 0.0f ? MathUtils.clamp((progress - r5) / r2, 0.0f, 1.0f) : 0.0f));
        }
    }

    @Override // android.view.View
    public synchronized void onMeasure(int i, int i2) {
        int iMax;
        int iMax2;
        try {
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                iMax2 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
                iMax = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
            } else {
                iMax = 0;
                iMax2 = 0;
            }
            updateDrawableState();
            int field_mPaddingLeft = SeslViewReflector.getField_mPaddingLeft(this) + SeslViewReflector.getField_mPaddingRight(this) + iMax2;
            int paddingTop = getPaddingTop() + getPaddingBottom() + iMax;
            int iResolveSizeAndState = View.resolveSizeAndState(field_mPaddingLeft, i, 0);
            int iResolveSizeAndState2 = View.resolveSizeAndState(paddingTop, i2, 0);
            if (!this.mUseCustomWidthForCircleMode) {
                initCirCleStrokeWidth((iResolveSizeAndState - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this));
            }
            if (this.mUseHorizontalProgress && this.mIndeterminate) {
                seslSetIndeterminateProgressDrawable((iResolveSizeAndState - SeslViewReflector.getField_mPaddingLeft(this)) - SeslViewReflector.getField_mPaddingRight(this));
            }
            setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public void onProgressRefresh(float f, boolean z, int i) {
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
            AccessibilityEventSender accessibilityEventSender = this.mAccessibilityEventSender;
            if (accessibilityEventSender == null) {
                this.mAccessibilityEventSender = new AccessibilityEventSender();
            } else {
                removeCallbacks(accessibilityEventSender);
            }
            postDelayed(this.mAccessibilityEventSender, 200L);
        }
        int i2 = this.mSecondaryProgress;
        if (i2 <= this.mProgress || z) {
            return;
        }
        refreshProgress(R.id.secondaryProgress, i2, false, false);
    }

    public void onResolveDrawables(int i) {
        Drawable drawable = this.mCurrentDrawable;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = getLayoutDirection();
        if (drawable != null) {
            drawable.setLayoutDirection(layoutDirection);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(layoutDirection);
        }
        Drawable drawable3 = this.mProgressDrawable;
        if (drawable3 != null) {
            drawable3.setLayoutDirection(layoutDirection);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress);
        setSecondaryProgress(savedState.secondaryProgress);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        savedState.secondaryProgress = this.mSecondaryProgress;
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        updateDrawableBounds(i, i2);
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z != this.mAggregatedIsVisible) {
            this.mAggregatedIsVisible = z;
            if (this.mIndeterminate) {
                if (z) {
                    startAnimation$1();
                } else {
                    stopAnimation$1();
                }
            }
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                drawable.setVisible(z, false);
            }
        }
    }

    @Override // android.view.View
    public final void postInvalidate() {
        if (this.mNoInvalidate) {
            return;
        }
        super.postInvalidate();
    }

    public final synchronized void refreshProgress(int i, int i2, boolean z, boolean z2) throws Throwable {
        SeslProgressBar seslProgressBar;
        try {
            try {
                if (this.mUiThreadId == Thread.currentThread().getId()) {
                    seslProgressBar = this;
                    seslProgressBar.doRefreshProgress(i, i2, z, true, z2);
                } else {
                    seslProgressBar = this;
                    if (seslProgressBar.mRefreshProgressRunnable == null) {
                        seslProgressBar.mRefreshProgressRunnable = new RefreshProgressRunnable();
                    }
                    seslProgressBar.mRefreshData.add(RefreshData.obtain(i, i2, z, z2));
                    if (seslProgressBar.mAttached && !seslProgressBar.mRefreshIsPosted) {
                        seslProgressBar.post(seslProgressBar.mRefreshProgressRunnable);
                        seslProgressBar.mRefreshIsPosted = true;
                    }
                }
                return;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        throw th;
    }

    public final void seslSetIndeterminateProgressDrawable(int i) {
        if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_indeterminate_xsmall) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalXsmall);
            return;
        }
        if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_indeterminate_small) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalSmall);
            return;
        }
        if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_indeterminate_medium) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalMedium);
        } else if (getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_bar_indeterminate_large) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalLarge);
        } else {
            setIndeterminateDrawable(this.mIndeterminateHorizontalXlarge);
        }
    }

    public final synchronized void setIndeterminate(boolean z) {
        try {
            if (!this.mOnlyIndeterminate || !this.mIndeterminate) {
                if (z != this.mIndeterminate) {
                    this.mIndeterminate = z;
                    if (z) {
                        swapCurrentDrawable(this.mIndeterminateDrawable);
                        startAnimation$1();
                    } else {
                        swapCurrentDrawable(this.mProgressDrawable);
                        stopAnimation$1();
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void setIndeterminateDrawable(Drawable drawable) {
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                if (this.mUseHorizontalProgress) {
                    stopAnimation$1();
                }
                this.mIndeterminateDrawable.setCallback(null);
                unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                if (this.mUseHorizontalProgress) {
                    startAnimation$1();
                }
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
        }
    }

    public synchronized void setMax(int i) {
        int i2;
        try {
            boolean z = this.mMinInitialized;
            if (z && i < (i2 = this.mMin)) {
                i = i2;
            }
            this.mMaxInitialized = true;
            if (!z || i == this.mMax) {
                this.mMax = i;
            } else {
                this.mMax = i;
                postInvalidate();
                if (this.mProgress > i) {
                    this.mProgress = i;
                }
                refreshProgress(R.id.progress, this.mProgress, false, false);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setMin(int i) {
        int i2;
        try {
            boolean z = this.mMaxInitialized;
            if (z && i > (i2 = this.mMax)) {
                i = i2;
            }
            this.mMinInitialized = true;
            if (!z || i == this.mMin) {
                this.mMin = i;
            } else {
                this.mMin = i;
                postInvalidate();
                if (this.mProgress < i) {
                    this.mProgress = i;
                }
                refreshProgress(R.id.progress, this.mProgress, false, false);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void setProgress(int i) {
        setProgressInternal(i, false, false);
    }

    public final void setProgressBackgroundTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressBackgroundTintList = colorStateList;
        progressTintInfo.mHasProgressBackgroundTint = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                if (this.mCurrentMode == 3) {
                    int minimumWidth = drawable.getMinimumWidth();
                    if (this.mMaxWidth < minimumWidth) {
                        this.mMaxWidth = minimumWidth;
                        requestLayout();
                    }
                } else {
                    int minimumHeight = drawable.getMinimumHeight();
                    if (this.mMaxHeight < minimumHeight) {
                        this.mMaxHeight = minimumHeight;
                        requestLayout();
                    }
                }
                applyProgressTints();
            }
            if (!this.mIndeterminate) {
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(R.id.progress, this.mProgress, false, false, false);
            doRefreshProgress(R.id.secondaryProgress, this.mSecondaryProgress, false, false, false);
            if (getImportantForAccessibility() == 0) {
                setImportantForAccessibility(1);
            }
        }
    }

    public synchronized boolean setProgressInternal(int i, boolean z, boolean z2) {
        Drawable drawableFindDrawableByLayerId;
        try {
            if (this.mIndeterminate) {
                return false;
            }
            int iClamp = MathUtils.clamp(i, this.mMin, this.mMax);
            int i2 = this.mProgress;
            if (iClamp == i2) {
                return false;
            }
            this.mVisualProgress = i2;
            this.mProgress = iClamp;
            if (this.mCurrentMode == 7) {
                Drawable drawable = this.mProgressDrawable;
                if ((drawable instanceof LayerDrawable) && (drawableFindDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(R.id.progress)) != null && (drawableFindDrawableByLayerId instanceof CirCleProgressDrawable)) {
                    CirCleProgressDrawable cirCleProgressDrawable = (CirCleProgressDrawable) drawableFindDrawableByLayerId;
                    if (z2) {
                        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(cirCleProgressDrawable, cirCleProgressDrawable.VISUAL_CIRCLE_PROGRESS, iClamp);
                        objectAnimatorOfInt.setAutoCancel(true);
                        objectAnimatorOfInt.setDuration(80L);
                        objectAnimatorOfInt.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
                        objectAnimatorOfInt.start();
                    } else {
                        cirCleProgressDrawable.mProgress = iClamp;
                        SeslProgressBar.this.invalidate();
                    }
                }
            }
            refreshProgress(R.id.progress, this.mProgress, z, z2);
            return true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public void setProgressTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        progressTintInfo.mProgressTintList = colorStateList;
        progressTintInfo.mHasProgressTint = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public synchronized void setSecondaryProgress(int i) {
        if (this.mIndeterminate) {
            return;
        }
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.mMax;
        if (i > i3) {
            i = i3;
        }
        if (i != this.mSecondaryProgress) {
            this.mSecondaryProgress = i;
            refreshProgress(R.id.secondaryProgress, i, false, false);
        }
    }

    public final void setVisualProgress(float f, int i) {
        this.mVisualProgress = f;
        Drawable drawableFindDrawableByLayerId = this.mCurrentDrawable;
        if ((drawableFindDrawableByLayerId instanceof LayerDrawable) && (drawableFindDrawableByLayerId = ((LayerDrawable) drawableFindDrawableByLayerId).findDrawableByLayerId(i)) == null) {
            drawableFindDrawableByLayerId = this.mCurrentDrawable;
        }
        if (drawableFindDrawableByLayerId != null) {
            drawableFindDrawableByLayerId.setLevel((int) (10000.0f * f));
        } else {
            invalidate();
        }
        onVisualProgressChanged(f, i);
    }

    public final void startAnimation$1() {
        if (getVisibility() == 0) {
            Drawable drawable = this.mIndeterminateDrawable;
            if (drawable instanceof Animatable) {
                this.mShouldStartAnimationDrawable = true;
                this.mHasAnimation = false;
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).registerAnimationCallback(this.mCircleAnimationCallback);
                }
            } else {
                this.mHasAnimation = true;
                if (this.mInterpolator == null) {
                    this.mInterpolator = new LinearInterpolator();
                }
                Transformation transformation = this.mTransformation;
                if (transformation == null) {
                    this.mTransformation = new Transformation();
                } else {
                    transformation.clear();
                }
                AlphaAnimation alphaAnimation = this.mAnimation;
                if (alphaAnimation == null) {
                    this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
                } else {
                    alphaAnimation.reset();
                }
                this.mAnimation.setRepeatMode(this.mBehavior);
                this.mAnimation.setRepeatCount(-1);
                this.mAnimation.setDuration(this.mDuration);
                this.mAnimation.setInterpolator(this.mInterpolator);
                this.mAnimation.setStartTime(-1L);
            }
            postInvalidate();
        }
    }

    public final void stopAnimation$1() {
        this.mHasAnimation = false;
        Object obj = this.mIndeterminateDrawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).stop();
            Drawable drawable = this.mIndeterminateDrawable;
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(this.mCircleAnimationCallback);
            }
            this.mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    public final void swapCurrentDrawable(Drawable drawable) {
        Drawable drawable2 = this.mCurrentDrawable;
        this.mCurrentDrawable = drawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setVisible(false, false);
            }
            Drawable drawable3 = this.mCurrentDrawable;
            if (drawable3 != null) {
                drawable3.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v1, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v4, types: [android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable] */
    public final Drawable tileify(Drawable drawable, boolean z) {
        if (!(drawable instanceof LayerDrawable)) {
            if (drawable instanceof StateListDrawable) {
                return new StateListDrawable();
            }
            if (drawable instanceof BitmapDrawable) {
                drawable = (BitmapDrawable) drawable.getConstantState().newDrawable(getResources());
                drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
                if (this.mSampleWidth <= 0) {
                    this.mSampleWidth = drawable.getIntrinsicWidth();
                }
                if (z) {
                    return new ClipDrawable(drawable, 3, 1);
                }
            }
            return drawable;
        }
        LayerDrawable layerDrawable = (LayerDrawable) drawable;
        int numberOfLayers = layerDrawable.getNumberOfLayers();
        Drawable[] drawableArr = new Drawable[numberOfLayers];
        for (int i = 0; i < numberOfLayers; i++) {
            int id = layerDrawable.getId(i);
            drawableArr[i] = tileify(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
        }
        LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
        for (int i2 = 0; i2 < numberOfLayers; i2++) {
            layerDrawable2.setId(i2, layerDrawable.getId(i2));
            layerDrawable2.setLayerGravity(i2, layerDrawable.getLayerGravity(i2));
            layerDrawable2.setLayerWidth(i2, layerDrawable.getLayerWidth(i2));
            layerDrawable2.setLayerHeight(i2, layerDrawable.getLayerHeight(i2));
            layerDrawable2.setLayerInsetLeft(i2, layerDrawable.getLayerInsetLeft(i2));
            layerDrawable2.setLayerInsetRight(i2, layerDrawable.getLayerInsetRight(i2));
            layerDrawable2.setLayerInsetTop(i2, layerDrawable.getLayerInsetTop(i2));
            layerDrawable2.setLayerInsetBottom(i2, layerDrawable.getLayerInsetBottom(i2));
            layerDrawable2.setLayerInsetStart(i2, layerDrawable.getLayerInsetStart(i2));
            layerDrawable2.setLayerInsetEnd(i2, layerDrawable.getLayerInsetEnd(i2));
        }
        return layerDrawable2;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateDrawableBounds(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int field_mPaddingLeft = i - (SeslViewReflector.getField_mPaddingLeft(this) + SeslViewReflector.getField_mPaddingRight(this));
        int paddingBottom = i2 - (getPaddingBottom() + getPaddingTop());
        Drawable drawable = this.mIndeterminateDrawable;
        if (drawable != null) {
            if (!this.mOnlyIndeterminate || (drawable instanceof AnimationDrawable)) {
                i3 = field_mPaddingLeft;
                i4 = 0;
                i5 = 0;
                if (this.mMirrorForRtl || getLayoutDirection() != 1) {
                    field_mPaddingLeft = i3;
                } else {
                    int i6 = field_mPaddingLeft - i3;
                    field_mPaddingLeft -= i5;
                    i5 = i6;
                }
                this.mIndeterminateDrawable.setBounds(i5, i4, field_mPaddingLeft, paddingBottom);
            } else {
                float intrinsicWidth = drawable.getIntrinsicWidth() / this.mIndeterminateDrawable.getIntrinsicHeight();
                float f = field_mPaddingLeft;
                float f2 = paddingBottom;
                float f3 = f / f2;
                if (Math.abs(intrinsicWidth - f3) < 1.0E-7d) {
                    if (f3 > intrinsicWidth) {
                        int i7 = (int) (f2 * intrinsicWidth);
                        int i8 = (field_mPaddingLeft - i7) / 2;
                        i5 = i8;
                        i3 = i7 + i8;
                        i4 = 0;
                    } else {
                        int i9 = (int) ((1.0f / intrinsicWidth) * f);
                        int i10 = (paddingBottom - i9) / 2;
                        int i11 = i9 + i10;
                        i4 = i10;
                        paddingBottom = i11;
                        i3 = field_mPaddingLeft;
                        i5 = 0;
                    }
                }
                if (this.mMirrorForRtl) {
                    field_mPaddingLeft = i3;
                    this.mIndeterminateDrawable.setBounds(i5, i4, field_mPaddingLeft, paddingBottom);
                }
            }
        }
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, field_mPaddingLeft, paddingBottom);
        }
    }

    public final void updateDrawableState() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mProgressDrawable;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mProgressDrawable || drawable == this.mIndeterminateDrawable || super.verifyDrawable(drawable);
    }

    public SeslProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.progressBarStyle);
    }

    public SeslProgressBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.appcompat.widget.SeslProgressBar$1] */
    public SeslProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentMode = 0;
        this.mUseCustomWidthForCircleMode = false;
        this.mUseHorizontalProgress = false;
        this.mSampleWidth = 0;
        this.mMirrorForRtl = false;
        this.mRefreshData = new ArrayList();
        this.VISUAL_PROGRESS = new FloatProperty(this, "visual_progress") { // from class: androidx.appcompat.widget.SeslProgressBar.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((SeslProgressBar) obj).mVisualProgress);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                SeslProgressBar seslProgressBar = (SeslProgressBar) obj;
                DecelerateInterpolator decelerateInterpolator = SeslProgressBar.PROGRESS_ANIM_INTERPOLATOR;
                seslProgressBar.setVisualProgress(f, R.id.progress);
                seslProgressBar.mVisualProgress = f;
            }
        };
        this.mUiThreadId = Thread.currentThread().getId();
        this.mMin = 0;
        this.mMax = 100;
        this.mProgress = 0;
        this.mSecondaryProgress = 0;
        this.mIndeterminate = false;
        this.mOnlyIndeterminate = false;
        this.mDuration = ImsProfile.DEFAULT_DEREG_TIMEOUT;
        this.mBehavior = 1;
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
        int[] iArr = R$styleable.ProgressBar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        try {
            saveAttributeDataForStyleable(context, iArr, attributeSet, typedArrayObtainStyledAttributes, i, i2);
            this.mNoInvalidate = true;
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(8);
            if (drawable != null) {
                if (needsTileify(drawable)) {
                    setProgressDrawable(tileify(drawable, false));
                } else {
                    setProgressDrawable(drawable);
                }
            }
            this.mDuration = typedArrayObtainStyledAttributes.getInt(9, this.mDuration);
            this.mMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(11, this.mMinWidth);
            this.mMaxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, this.mMaxWidth);
            this.mMinHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(12, this.mMinHeight);
            this.mMaxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, this.mMaxHeight);
            this.mBehavior = typedArrayObtainStyledAttributes.getInt(10, this.mBehavior);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(13, R.anim.linear_interpolator);
            if (resourceId > 0) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context, resourceId);
            }
            setMin(typedArrayObtainStyledAttributes.getInt(26, this.mMin));
            setMax(typedArrayObtainStyledAttributes.getInt(2, this.mMax));
            setProgress(typedArrayObtainStyledAttributes.getInt(3, this.mProgress));
            setSecondaryProgress(typedArrayObtainStyledAttributes.getInt(4, this.mSecondaryProgress));
            Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(7);
            if (drawable2 != null) {
                if (needsTileify(drawable2)) {
                    if (drawable2 instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) drawable2;
                        int numberOfFrames = animationDrawable.getNumberOfFrames();
                        AnimationDrawable animationDrawable2 = new AnimationDrawable();
                        animationDrawable2.setOneShot(animationDrawable.isOneShot());
                        for (int i3 = 0; i3 < numberOfFrames; i3++) {
                            Drawable drawableTileify = tileify(animationDrawable.getFrame(i3), true);
                            drawableTileify.setLevel(10000);
                            animationDrawable2.addFrame(drawableTileify, animationDrawable.getDuration(i3));
                        }
                        animationDrawable2.setLevel(10000);
                        drawable2 = animationDrawable2;
                    }
                    setIndeterminateDrawable(drawable2);
                } else {
                    setIndeterminateDrawable(drawable2);
                }
            }
            boolean z = typedArrayObtainStyledAttributes.getBoolean(6, this.mOnlyIndeterminate);
            this.mOnlyIndeterminate = z;
            this.mNoInvalidate = false;
            setIndeterminate(z || typedArrayObtainStyledAttributes.getBoolean(5, this.mIndeterminate));
            this.mMirrorForRtl = typedArrayObtainStyledAttributes.getBoolean(15, this.mMirrorForRtl);
            if (typedArrayObtainStyledAttributes.hasValue(17)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(17, -1), null);
                this.mProgressTintInfo.mHasProgressTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(16)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressTintList = typedArrayObtainStyledAttributes.getColorStateList(16);
                this.mProgressTintInfo.mHasProgressTint = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(19)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressBackgroundTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(19, -1), null);
                this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(18)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressBackgroundTintList = typedArrayObtainStyledAttributes.getColorStateList(18);
                this.mProgressTintInfo.mHasProgressBackgroundTint = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(21)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mSecondaryProgressTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(21, -1), null);
                this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(20)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mSecondaryProgressTintList = typedArrayObtainStyledAttributes.getColorStateList(20);
                this.mProgressTintInfo.mHasSecondaryProgressTint = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(23)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mIndeterminateTintMode = DrawableUtils.parseTintMode(typedArrayObtainStyledAttributes.getInt(23, -1), null);
                this.mProgressTintInfo.mHasIndeterminateTintMode = true;
            }
            if (typedArrayObtainStyledAttributes.hasValue(22)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mIndeterminateTintList = typedArrayObtainStyledAttributes.getColorStateList(22);
                this.mProgressTintInfo.mHasIndeterminateTint = true;
            }
            boolean z2 = typedArrayObtainStyledAttributes.getBoolean(29, this.mUseCustomWidthForCircleMode);
            this.mUseCustomWidthForCircleMode = z2;
            if (z2) {
                this.mRoundStrokeWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(28, getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_progress_circle_size_small_width));
                this.mCirclePadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(27, getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.sesl_progress_circle_size_small_padding));
            }
            this.mUseHorizontalProgress = typedArrayObtainStyledAttributes.getBoolean(30, this.mUseHorizontalProgress);
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 2132017465);
            this.mIndeterminateHorizontalXsmall = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_xsmall_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalSmall = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_small_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalMedium = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_medium_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalLarge = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_large_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalXlarge = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_xlarge_transition, contextThemeWrapper.getTheme());
            typedArrayObtainStyledAttributes.recycle();
            applyProgressTints();
            applyIndeterminateTint();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (getImportantForAccessibility() == 0) {
                setImportantForAccessibility(1);
            }
            this.mDensity = context.getResources().getDisplayMetrics().density;
            this.mCircleAnimationCallback = new CircleAnimationCallback(this);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void onVisualProgressChanged(float f, int i) {
    }
}
