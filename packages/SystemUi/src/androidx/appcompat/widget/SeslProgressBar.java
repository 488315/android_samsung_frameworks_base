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
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslViewReflector;
import com.sec.ims.settings.ImsProfile;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@RemoteViews.RemoteView
/* loaded from: classes.dex */
public class SeslProgressBar extends View {
    public static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();
    public final C00901 VISUAL_PROGRESS;
    public AccessibilityEventSender mAccessibilityEventSender;
    public boolean mAggregatedIsVisible;
    public AlphaAnimation mAnimation;
    public boolean mAttached;
    public int mBehavior;
    public Locale mCachedLocale;
    public CircleAnimationCallback mCircleAnimationCallback;
    public int mCirclePadding;
    public Drawable mCurrentDrawable;
    public int mCurrentMode;
    public final float mDensity;
    public int mDuration;
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
    public int mMinHeight;
    public boolean mMinInitialized;
    public int mMinWidth;
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
    public final boolean mUseHorizontalProgress;
    public float mVisualProgress;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccessibilityEventSender implements Runnable {
        private AccessibilityEventSender() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SeslProgressBar.this.sendAccessibilityEvent(4);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CirCleProgressDrawable extends Drawable {
        public final C00911 VISUAL_CIRCLE_PROGRESS;
        public int mAlpha;
        public final RectF mArcRect;
        public int mColor;
        public ColorStateList mColorStateList;
        public final boolean mIsBackground;
        public final Paint mPaint;
        public int mProgress;
        public final ProgressState mState;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ProgressState extends Drawable.ConstantState {
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
            boolean onStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            return onStateChange;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CircleAnimationCallback extends Animatable2.AnimationCallback {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProgressTintInfo {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RefreshData {
        public static final Pools$SynchronizedPool sPool = new Pools$SynchronizedPool(24);
        public boolean animate;
        public boolean fromUser;

        /* renamed from: id */
        public int f1id;
        public int progress;

        private RefreshData() {
        }

        public static RefreshData obtain(int i, int i2, boolean z, boolean z2) {
            RefreshData refreshData = (RefreshData) sPool.acquire();
            if (refreshData == null) {
                refreshData = new RefreshData();
            }
            refreshData.f1id = i;
            refreshData.progress = i2;
            refreshData.fromUser = z;
            refreshData.animate = z2;
            return refreshData;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RefreshProgressRunnable implements Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (SeslProgressBar.this) {
                int size = SeslProgressBar.this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    RefreshData refreshData = (RefreshData) SeslProgressBar.this.mRefreshData.get(i);
                    SeslProgressBar.this.doRefreshProgress(refreshData.f1id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    RefreshData.sPool.release(refreshData);
                }
                SeslProgressBar.this.mRefreshData.clear();
                SeslProgressBar.this.mRefreshIsPosted = false;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
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
            Drawable mutate = drawable.mutate();
            this.mIndeterminateDrawable = mutate;
            if (progressTintInfo.mHasIndeterminateTint) {
                mutate.setTintList(progressTintInfo.mIndeterminateTintList);
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
        int i3 = this.mMax - this.mMin;
        float f = i3 > 0 ? (i2 - r1) / i3 : 0.0f;
        boolean z4 = i == 16908301;
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            int i4 = (int) (10000.0f * f);
            if (drawable instanceof LayerDrawable) {
                Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(i);
                if (findDrawableByLayerId != null && canResolveLayoutDirection()) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    findDrawableByLayerId.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
                }
                if (findDrawableByLayerId != null) {
                    drawable = findDrawableByLayerId;
                }
                drawable.setLevel(i4);
            } else if (drawable instanceof StateListDrawable) {
            } else {
                drawable.setLevel(i4);
            }
        } else {
            invalidate();
        }
        if (z4 && z3) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, f);
            ofFloat.setAutoCancel(true);
            ofFloat.setDuration(80L);
            ofFloat.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            ofFloat.start();
        } else {
            setVisualProgress(f, i);
        }
        if (z4 && z2) {
            onProgressRefresh(f, i2, z);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void drawTrack(Canvas canvas) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int save = canvas.save();
            if (this.mCurrentMode != 3 && this.mMirrorForRtl && ViewUtils.isLayoutRtl(this)) {
                canvas.translate(getWidth() - getPaddingRight(), getPaddingTop());
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(getPaddingLeft(), getPaddingTop());
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
                    ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
                } catch (Throwable th) {
                    this.mInDrawing = false;
                    throw th;
                }
            }
            drawable.draw(canvas);
            canvas.restoreToCount(save);
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
        Field declaredField = SeslBaseReflector.getDeclaredField(SeslViewReflector.mClass, "mPaddingLeft");
        if (declaredField != null) {
            Object obj = SeslBaseReflector.get(declaredField, this);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        return 0;
    }

    @Override // android.view.View
    public final int getPaddingRight() {
        Field declaredField = SeslBaseReflector.getDeclaredField(SeslViewReflector.mClass, "mPaddingRight");
        if (declaredField != null) {
            Object obj = SeslBaseReflector.get(declaredField, this);
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
        }
        return 0;
    }

    public synchronized int getProgress() {
        return this.mIndeterminate ? 0 : this.mProgress;
    }

    public final Drawable getTintTarget(int i, boolean z) {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            this.mProgressDrawable = drawable.mutate();
            r1 = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(i) : null;
            if (z && r1 == null) {
                return drawable;
            }
        }
        return r1;
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
        int paddingLeft = getPaddingLeft() + getScrollX();
        int paddingTop = getPaddingTop() + getScrollY();
        invalidate(bounds.left + paddingLeft, bounds.top + paddingTop, bounds.right + paddingLeft, bounds.bottom + paddingTop);
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
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation();
        }
        synchronized (this) {
            try {
                int size = this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    RefreshData refreshData = (RefreshData) this.mRefreshData.get(i);
                    doRefreshProgress(refreshData.f1id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    RefreshData.sPool.release(refreshData);
                }
                this.mRefreshData.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mAttached = true;
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation();
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
                    accessibilityNodeInfo.setStateDescription(string);
                    return;
                }
                string = "";
                accessibilityNodeInfo.setStateDescription(string);
                return;
            }
            int i = this.mProgress;
            Locale locale = getResources().getConfiguration().locale;
            if (!locale.equals(this.mCachedLocale) || this.mPercentFormat == null) {
                this.mCachedLocale = locale;
                this.mPercentFormat = NumberFormat.getPercentInstance(locale);
            }
            accessibilityNodeInfo.setStateDescription(this.mPercentFormat.format(getMax() - getMin() > 0.0f ? MathUtils.clamp((i - r5) / r2, 0.0f, 1.0f) : 0.0f));
        }
    }

    @Override // android.view.View
    public synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            i4 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
            i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
        } else {
            i3 = 0;
            i4 = 0;
        }
        updateDrawableState();
        int paddingLeft = getPaddingLeft() + getPaddingRight() + i4;
        int paddingTop = getPaddingTop() + getPaddingBottom() + i3;
        int resolveSizeAndState = View.resolveSizeAndState(paddingLeft, i, 0);
        int resolveSizeAndState2 = View.resolveSizeAndState(paddingTop, i2, 0);
        initCirCleStrokeWidth((resolveSizeAndState - getPaddingLeft()) - getPaddingRight());
        if (this.mUseHorizontalProgress && this.mIndeterminate) {
            seslSetIndeterminateProgressDrawable((resolveSizeAndState - getPaddingLeft()) - getPaddingRight());
        }
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    public void onProgressRefresh(float f, int i, boolean z) {
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
        int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
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
                    startAnimation();
                } else {
                    stopAnimation();
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

    public final synchronized void refreshProgress(int i, int i2, boolean z, boolean z2) {
        if (this.mUiThreadId == Thread.currentThread().getId()) {
            doRefreshProgress(i, i2, z, true, z2);
        } else {
            if (this.mRefreshProgressRunnable == null) {
                this.mRefreshProgressRunnable = new RefreshProgressRunnable();
            }
            this.mRefreshData.add(RefreshData.obtain(i, i2, z, z2));
            if (this.mAttached && !this.mRefreshIsPosted) {
                post(this.mRefreshProgressRunnable);
                this.mRefreshIsPosted = true;
            }
        }
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
        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && z != this.mIndeterminate) {
            this.mIndeterminate = z;
            if (z) {
                swapCurrentDrawable(this.mIndeterminateDrawable);
                startAnimation();
            } else {
                swapCurrentDrawable(this.mProgressDrawable);
                stopAnimation();
            }
        }
    }

    public final void setIndeterminateDrawable(Drawable drawable) {
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                if (this.mUseHorizontalProgress) {
                    stopAnimation();
                }
                this.mIndeterminateDrawable.setCallback(null);
                unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                drawable.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                if (this.mUseHorizontalProgress) {
                    startAnimation();
                }
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
        }
    }

    public synchronized void setMax(int i) {
        int i2;
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
    }

    public synchronized void setMin(int i) {
        int i2;
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
                drawable.setLayoutDirection(ViewCompat.Api17Impl.getLayoutDirection(this));
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
        Drawable findDrawableByLayerId;
        if (this.mIndeterminate) {
            return false;
        }
        int clamp = MathUtils.clamp(i, this.mMin, this.mMax);
        if (clamp == this.mProgress) {
            return false;
        }
        this.mProgress = clamp;
        if (this.mCurrentMode == 7) {
            Drawable drawable = this.mProgressDrawable;
            if ((drawable instanceof LayerDrawable) && (findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(R.id.progress)) != null && (findDrawableByLayerId instanceof CirCleProgressDrawable)) {
                CirCleProgressDrawable cirCleProgressDrawable = (CirCleProgressDrawable) findDrawableByLayerId;
                if (z2) {
                    ObjectAnimator ofInt = ObjectAnimator.ofInt(cirCleProgressDrawable, cirCleProgressDrawable.VISUAL_CIRCLE_PROGRESS, clamp);
                    ofInt.setAutoCancel(true);
                    ofInt.setDuration(80L);
                    ofInt.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
                    ofInt.start();
                } else {
                    cirCleProgressDrawable.mProgress = clamp;
                    SeslProgressBar.this.invalidate();
                }
            }
        }
        refreshProgress(R.id.progress, this.mProgress, z, z2);
        return true;
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
        Drawable drawable = this.mCurrentDrawable;
        if ((drawable instanceof LayerDrawable) && (drawable = ((LayerDrawable) drawable).findDrawableByLayerId(i)) == null) {
            drawable = this.mCurrentDrawable;
        }
        if (drawable != null) {
            drawable.setLevel((int) (10000.0f * f));
        } else {
            invalidate();
        }
        onVisualProgressChanged(f, i);
    }

    public final void startAnimation() {
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

    public final void stopAnimation() {
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

    public void updateDrawableBounds(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int paddingLeft = i - (getPaddingLeft() + getPaddingRight());
        int paddingBottom = i2 - (getPaddingBottom() + getPaddingTop());
        Drawable drawable = this.mIndeterminateDrawable;
        if (drawable != null) {
            if (this.mOnlyIndeterminate && !(drawable instanceof AnimationDrawable)) {
                float intrinsicWidth = drawable.getIntrinsicWidth() / this.mIndeterminateDrawable.getIntrinsicHeight();
                float f = paddingLeft;
                float f2 = paddingBottom;
                float f3 = f / f2;
                if (Math.abs(intrinsicWidth - f3) < 1.0E-7d) {
                    if (f3 > intrinsicWidth) {
                        int i6 = (int) (f2 * intrinsicWidth);
                        int i7 = (paddingLeft - i6) / 2;
                        i5 = i7;
                        i3 = i6 + i7;
                        i4 = 0;
                    } else {
                        int i8 = (int) ((1.0f / intrinsicWidth) * f);
                        int i9 = (paddingBottom - i8) / 2;
                        int i10 = i8 + i9;
                        i3 = paddingLeft;
                        i5 = 0;
                        i4 = i9;
                        paddingBottom = i10;
                    }
                    if (this.mMirrorForRtl || !ViewUtils.isLayoutRtl(this)) {
                        paddingLeft = i3;
                    } else {
                        int i11 = paddingLeft - i3;
                        paddingLeft -= i5;
                        i5 = i11;
                    }
                    this.mIndeterminateDrawable.setBounds(i5, i4, paddingLeft, paddingBottom);
                }
            }
            i3 = paddingLeft;
            i4 = 0;
            i5 = 0;
            if (this.mMirrorForRtl) {
            }
            paddingLeft = i3;
            this.mIndeterminateDrawable.setBounds(i5, i4, paddingLeft, paddingBottom);
        }
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, paddingLeft, paddingBottom);
        }
    }

    public final void updateDrawableState() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mProgressDrawable;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
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

    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.appcompat.widget.SeslProgressBar$1] */
    public SeslProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCurrentMode = 0;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        try {
            saveAttributeDataForStyleable(context, iArr, attributeSet, obtainStyledAttributes, i, i2);
            this.mNoInvalidate = true;
            Drawable drawable = obtainStyledAttributes.getDrawable(8);
            if (drawable != null) {
                if (needsTileify(drawable)) {
                    setProgressDrawable(tileify(drawable, false));
                } else {
                    setProgressDrawable(drawable);
                }
            }
            this.mDuration = obtainStyledAttributes.getInt(9, this.mDuration);
            this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(11, this.mMinWidth);
            this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(0, this.mMaxWidth);
            this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(12, this.mMinHeight);
            this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(1, this.mMaxHeight);
            this.mBehavior = obtainStyledAttributes.getInt(10, this.mBehavior);
            int resourceId = obtainStyledAttributes.getResourceId(13, R.anim.linear_interpolator);
            if (resourceId > 0) {
                this.mInterpolator = AnimationUtils.loadInterpolator(context, resourceId);
            }
            setMin(obtainStyledAttributes.getInt(26, this.mMin));
            setMax(obtainStyledAttributes.getInt(2, this.mMax));
            setProgress(obtainStyledAttributes.getInt(3, this.mProgress));
            setSecondaryProgress(obtainStyledAttributes.getInt(4, this.mSecondaryProgress));
            Drawable drawable2 = obtainStyledAttributes.getDrawable(7);
            if (drawable2 != null) {
                if (needsTileify(drawable2)) {
                    if (drawable2 instanceof AnimationDrawable) {
                        AnimationDrawable animationDrawable = (AnimationDrawable) drawable2;
                        int numberOfFrames = animationDrawable.getNumberOfFrames();
                        AnimationDrawable animationDrawable2 = new AnimationDrawable();
                        animationDrawable2.setOneShot(animationDrawable.isOneShot());
                        for (int i3 = 0; i3 < numberOfFrames; i3++) {
                            Drawable tileify = tileify(animationDrawable.getFrame(i3), true);
                            tileify.setLevel(10000);
                            animationDrawable2.addFrame(tileify, animationDrawable.getDuration(i3));
                        }
                        animationDrawable2.setLevel(10000);
                        drawable2 = animationDrawable2;
                    }
                    setIndeterminateDrawable(drawable2);
                } else {
                    setIndeterminateDrawable(drawable2);
                }
            }
            boolean z = obtainStyledAttributes.getBoolean(6, this.mOnlyIndeterminate);
            this.mOnlyIndeterminate = z;
            this.mNoInvalidate = false;
            setIndeterminate(z || obtainStyledAttributes.getBoolean(5, this.mIndeterminate));
            this.mMirrorForRtl = obtainStyledAttributes.getBoolean(15, this.mMirrorForRtl);
            if (obtainStyledAttributes.hasValue(17)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(17, -1), null);
                this.mProgressTintInfo.mHasProgressTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(16)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressTintList = obtainStyledAttributes.getColorStateList(16);
                this.mProgressTintInfo.mHasProgressTint = true;
            }
            if (obtainStyledAttributes.hasValue(19)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressBackgroundTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(19, -1), null);
                this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(18)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mProgressBackgroundTintList = obtainStyledAttributes.getColorStateList(18);
                this.mProgressTintInfo.mHasProgressBackgroundTint = true;
            }
            if (obtainStyledAttributes.hasValue(21)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mSecondaryProgressTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(21, -1), null);
                this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(20)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mSecondaryProgressTintList = obtainStyledAttributes.getColorStateList(20);
                this.mProgressTintInfo.mHasSecondaryProgressTint = true;
            }
            if (obtainStyledAttributes.hasValue(23)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mIndeterminateTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(23, -1), null);
                this.mProgressTintInfo.mHasIndeterminateTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(22)) {
                if (this.mProgressTintInfo == null) {
                    this.mProgressTintInfo = new ProgressTintInfo();
                }
                this.mProgressTintInfo.mIndeterminateTintList = obtainStyledAttributes.getColorStateList(22);
                this.mProgressTintInfo.mHasIndeterminateTint = true;
            }
            this.mUseHorizontalProgress = obtainStyledAttributes.getBoolean(27, this.mUseHorizontalProgress);
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 2132017387);
            this.mIndeterminateHorizontalXsmall = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_xsmall_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalSmall = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_small_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalMedium = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_medium_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalLarge = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_large_transition, contextThemeWrapper.getTheme());
            this.mIndeterminateHorizontalXlarge = getResources().getDrawable(com.android.systemui.R.drawable.sesl_progress_bar_indeterminate_xlarge_transition, contextThemeWrapper.getTheme());
            obtainStyledAttributes.recycle();
            applyProgressTints();
            applyIndeterminateTint();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
                ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
            }
            this.mDensity = context.getResources().getDisplayMetrics().density;
            this.mCircleAnimationCallback = new CircleAnimationCallback(this);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void onVisualProgressChanged(float f, int i) {
    }
}
