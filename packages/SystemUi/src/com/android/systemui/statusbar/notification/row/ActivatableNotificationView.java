package com.android.systemui.statusbar.notification.row;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.statusbar.notification.FakeShadowView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ActivatableNotificationView extends ExpandableOutlineView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mAnimationTranslationY;
    public float mAppearAnimationFraction;
    public float mAppearAnimationTranslation;
    public ValueAnimator mAppearAnimator;
    public ValueAnimator mBackgroundColorAnimator;
    public NotificationBackgroundView mBackgroundNormal;
    public int mBgTint;
    public Interpolator mCurrentAppearInterpolator;
    public int mCurrentBackgroundTint;
    public boolean mDimmed;
    public boolean mDismissed;
    public boolean mDrawingAppearAnimation;
    public FakeShadowView mFakeShadow;
    public boolean mIsBelowSpeedBump;
    public boolean mIsHeadsUpAnimation;
    public long mLastActionUpTime;
    public int mNormalColor;
    public int mNormalRippleColor;
    public final Set mOnDetachResetRoundness;
    public float mOverrideAmount;
    public int mOverrideTint;
    public boolean mRefocusOnDismiss;
    public boolean mShadowHidden;
    public final Interpolator mSlowOutFastInInterpolator;
    public int mStartTint;
    public Point mTargetPoint;
    public int mTargetTint;
    public int mTintedRippleColor;
    public Gefingerpoken mTouchHandler;

    /* renamed from: -$$Nest$mgetCujType, reason: not valid java name */
    public static int m1710$$Nest$mgetCujType(ActivatableNotificationView activatableNotificationView, boolean z) {
        return activatableNotificationView.mIsHeadsUpAnimation ? z ? 12 : 13 : z ? 14 : 15;
    }

    public ActivatableNotificationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnDetachResetRoundness = new HashSet();
        this.mBgTint = 0;
        this.mAppearAnimationFraction = -1.0f;
        this.mSlowOutFastInInterpolator = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        setClipChildren(false);
        setClipToPadding(false);
        updateColors();
    }

    private void setContentAlpha(float f) {
        View contentView = getContentView();
        if (contentView.hasOverlappingRendering()) {
            contentView.setLayerType((f == 0.0f || f == 1.0f) ? 0 : 2, null);
        }
        contentView.setAlpha(f);
        if (f == 1.0f) {
            resetAllContentAlphas();
        }
    }

    private void updateColors() {
        this.mNormalColor = Utils.getColorAttrDefaultColor(R.^attr-private.colorSurface, ((FrameLayout) this).mContext, 0);
        this.mTintedRippleColor = ((FrameLayout) this).mContext.getColor(com.android.systemui.R.color.notification_ripple_tinted_color);
        this.mNormalRippleColor = ((FrameLayout) this).mContext.getColor(com.android.systemui.R.color.notification_ripple_untinted_color);
        this.mBgTint = 0;
        this.mOverrideTint = 0;
        this.mOverrideAmount = 0.0f;
    }

    public final void applyHeadsUpBackground(boolean z) {
        int color = getResources().getColor(com.android.systemui.R.color.heads_up_notification_background_color, null);
        if (!z) {
            if (DeviceState.isOpenTheme(((FrameLayout) this).mContext)) {
                int color2 = ((FrameLayout) this).mContext.getResources().getColor(com.android.systemui.R.color.open_theme_notification_bg_color, null);
                color = Color.argb(255, Color.red(color2), Color.green(color2), Color.blue(color2));
            }
            if (((FrameLayout) this).mContext.getResources().getBoolean(com.android.systemui.R.bool.theme_designer_quick_panel_turned_on)) {
                int color3 = ((FrameLayout) this).mContext.getResources().getColor(com.android.systemui.R.color.qp_notification_background_color, null);
                color = Color.argb(255, Color.red(color3), Color.green(color3), Color.blue(color3));
            }
        }
        RippleDrawable rippleDrawable = (RippleDrawable) getResources().getDrawable(com.android.systemui.R.drawable.notification_material_bg, null);
        ((GradientDrawable) rippleDrawable.getDrawable(0)).setColors(new int[]{color, color});
        this.mBackgroundNormal.setCustomBackground(rippleDrawable);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int i = this.mBgTint != 0 ? this.mTintedRippleColor : this.mNormalRippleColor;
        Drawable drawable = notificationBackgroundView.mBackground;
        if (!(drawable instanceof RippleDrawable)) {
            notificationBackgroundView.mRippleColor = null;
        } else {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
            notificationBackgroundView.mRippleColor = Integer.valueOf(i);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.Roundable
    public void applyRoundnessAndInvalidate() {
        float topCornerRadius = getTopCornerRadius();
        float bottomCornerRadius = getBottomCornerRadius();
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        float[] fArr = notificationBackgroundView.mCornerRadii;
        if (topCornerRadius != fArr[0] || bottomCornerRadius != fArr[4]) {
            notificationBackgroundView.mBottomIsRounded = bottomCornerRadius != 0.0f;
            fArr[0] = topCornerRadius;
            fArr[1] = topCornerRadius;
            fArr[2] = topCornerRadius;
            fArr[3] = topCornerRadius;
            fArr[4] = bottomCornerRadius;
            fArr[5] = bottomCornerRadius;
            fArr[6] = bottomCornerRadius;
            fArr[7] = bottomCornerRadius;
            if (!notificationBackgroundView.mDontModifyCorners) {
                Drawable drawable = notificationBackgroundView.mBackground;
                if (drawable instanceof LayerDrawable) {
                    ((GradientDrawable) ((LayerDrawable) drawable).getDrawable(0)).setCornerRadii(notificationBackgroundView.mCornerRadii);
                }
            }
        }
        super.applyRoundnessAndInvalidate();
    }

    public final int calculateBgColor(boolean z, boolean z2) {
        int i;
        return (!z2 || this.mOverrideTint == 0) ? (!z || (i = this.mBgTint) == 0) ? this.mNormalColor : i : NotificationUtils.interpolateColors(this.mOverrideAmount, calculateBgColor(z, false), this.mOverrideTint);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public boolean childNeedsClipping(View view) {
        return (view instanceof NotificationBackgroundView) && isClippingNeeded();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        if (this.mDrawingAppearAnimation) {
            canvas.save();
            canvas.translate(0.0f, this.mAppearAnimationTranslation);
        }
        super.dispatchDraw(canvas);
        if (this.mDrawingAppearAnimation) {
            canvas.restore();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int[] drawableState = getDrawableState();
        Drawable drawable = notificationBackgroundView.mBackground;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        if (!notificationBackgroundView.mIsPressedAllowed) {
            drawableState = ArrayUtils.removeInt(drawableState, R.attr.state_pressed);
        }
        notificationBackgroundView.mBackground.setState(drawableState);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(DumpUtilsKt.asIndenting(printWriter), strArr);
    }

    public final void enableAppearDrawing(boolean z) {
        if (z != this.mDrawingAppearAnimation) {
            this.mDrawingAppearAnimation = z;
            if (!z) {
                setContentAlpha(1.0f);
                this.mAppearAnimationFraction = -1.0f;
                this.mCustomOutline = false;
                applyRoundnessAndInvalidate();
            }
            invalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final float getBottomCornerRadius() {
        float f = this.mAppearAnimationFraction;
        return MathUtils.lerp(0.0f, super.getBottomCornerRadius(), f >= 0.0f ? ((PathInterpolator) this.mCurrentAppearInterpolator).getInterpolation(f) : 1.0f);
    }

    public abstract View getContentView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final float getTopCornerRadius() {
        float f = this.mAppearAnimationFraction;
        return MathUtils.lerp(0.0f, super.getTopCornerRadius(), f >= 0.0f ? ((PathInterpolator) this.mCurrentAppearInterpolator).getInterpolation(f) : 1.0f);
    }

    public boolean hideBackground() {
        return false;
    }

    public final void initBackground() {
        NotificationEntry notificationEntry;
        RippleDrawable rippleDrawable = (RippleDrawable) getResources().getDrawable(com.android.systemui.R.drawable.notification_material_bg, null);
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
        int notificationBgColor$1 = notificationColorPicker.getNotificationBgColor$1();
        ExpandableNotificationRow expandableNotificationRow = this instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) this : null;
        if (expandableNotificationRow != null && (notificationEntry = expandableNotificationRow.mEntry) != null && notificationEntry.mSbn != null && !NotificationColorPicker.isNeedToUpdated(expandableNotificationRow)) {
            notificationBgColor$1 = notificationColorPicker.getNotificationDefaultBgColor();
        }
        rippleDrawable.setDrawable(0, new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{notificationBgColor$1, notificationBgColor$1}));
        this.mBackgroundNormal.setCustomBackground(rippleDrawable);
        updateCurrentBackgroundDimmedAlpha();
    }

    public boolean isHeadsUp() {
        return false;
    }

    public boolean isShowingPublic() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (((HashSet) this.mOnDetachResetRoundness).isEmpty()) {
            return;
        }
        Iterator it = ((HashSet) this.mOnDetachResetRoundness).iterator();
        while (it.hasNext()) {
            requestRoundnessReset((SourceType) it.next());
        }
        ((HashSet) this.mOnDetachResetRoundness).clear();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBackgroundNormal = (NotificationBackgroundView) findViewById(com.android.systemui.R.id.backgroundNormal);
        FakeShadowView fakeShadowView = (FakeShadowView) findViewById(com.android.systemui.R.id.fake_shadow);
        this.mFakeShadow = fakeShadowView;
        this.mShadowHidden = fakeShadowView.getVisibility() != 0;
        initBackground();
        updateBackgroundTint();
        if (0.7f != this.mOutlineAlpha) {
            this.mOutlineAlpha = 0.7f;
            applyRoundnessAndInvalidate();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.mTouchHandler;
        if (gefingerpoken == null || !gefingerpoken.onInterceptTouchEvent(motionEvent)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setPivotX(getWidth() / 2);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void performAddAnimation(long j, long j2, boolean z) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(true, z ? 0.0f : -1.0f, j, j2, null, null);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(false, f, j2, j, runnable, animatorListenerAdapter);
            return 0L;
        }
        if (runnable == null) {
            return 0L;
        }
        runnable.run();
        return 0L;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeight(int i, boolean z) {
        super.setActualHeight(i, z);
        setPivotY(i / 2);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        if (notificationBackgroundView.mExpandAnimationRunning) {
            return;
        }
        notificationBackgroundView.mActualHeight = i;
        notificationBackgroundView.invalidate();
    }

    public void setBackgroundTintColor(int i) {
        if (i != this.mCurrentBackgroundTint) {
            this.mCurrentBackgroundTint = i;
            if (i == this.mNormalColor) {
                i = 0;
            }
            NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
            if (i != 0) {
                notificationBackgroundView.mBackground.setColorFilter(i, PorterDuff.Mode.SRC);
            } else {
                notificationBackgroundView.mBackground.clearColorFilter();
            }
            notificationBackgroundView.mTintColor = i;
            notificationBackgroundView.invalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setBelowSpeedBump(boolean z) {
        if (z != this.mIsBelowSpeedBump) {
            this.mIsBelowSpeedBump = z;
            updateBackgroundTint();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipBottomAmount(int i) {
        super.setClipBottomAmount(i);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        notificationBackgroundView.mClipBottomAmount = i;
        notificationBackgroundView.invalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipTopAmount(int i) {
        super.setClipTopAmount(i);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        notificationBackgroundView.mClipTopAmount = i;
        notificationBackgroundView.invalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setDimmed(boolean z, boolean z2) {
        if (this.mDimmed == z) {
            return;
        }
        this.mDimmed = z;
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
        if (this instanceof ExpandableNotificationRow) {
            notificationColorPicker.updateAllTextViewColors((ExpandableNotificationRow) this, z);
        }
        updateCurrentBackgroundDimmedAlpha();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView
    public void setDistanceToTopRoundness(float f) {
        super.setDistanceToTopRoundness(f);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        if (f != notificationBackgroundView.mDistanceToTopRoundness) {
            notificationBackgroundView.mDistanceToTopRoundness = f;
            notificationBackgroundView.invalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setFakeShadowIntensity(int i, float f, float f2, int i2) {
        boolean z = this.mShadowHidden;
        boolean z2 = f == 0.0f;
        this.mShadowHidden = z2;
        if (z2 && z) {
            return;
        }
        FakeShadowView fakeShadowView = this.mFakeShadow;
        float translationZ = (getTranslationZ() + 0.1f) * f;
        if (translationZ == 0.0f) {
            fakeShadowView.mFakeShadow.setVisibility(4);
            return;
        }
        fakeShadowView.mFakeShadow.setVisibility(0);
        fakeShadowView.mFakeShadow.setTranslationZ(Math.max(fakeShadowView.mShadowMinHeight, translationZ));
        fakeShadowView.mFakeShadow.setTranslationX(i2);
        fakeShadowView.mFakeShadow.setTranslationY(i - r4.getHeight());
        if (f2 != fakeShadowView.mOutlineAlpha) {
            fakeShadowView.mOutlineAlpha = f2;
            fakeShadowView.mFakeShadow.invalidateOutline();
        }
    }

    public final void setOverrideTintColor(float f, int i) {
        this.mOverrideTint = i;
        this.mOverrideAmount = f;
        setBackgroundTintColor(calculateBgColor(true, true));
    }

    public final void startAppearAnimation(final boolean z, float f, long j, long j2, final Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        this.mAnimationTranslationY = f * this.mActualHeight;
        ValueAnimator valueAnimator = this.mAppearAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mAppearAnimator = null;
        }
        float f2 = 1.0f;
        if (this.mAppearAnimationFraction == -1.0f) {
            if (z) {
                this.mAppearAnimationFraction = 0.0f;
                this.mAppearAnimationTranslation = this.mAnimationTranslationY;
            } else {
                this.mAppearAnimationFraction = 1.0f;
                this.mAppearAnimationTranslation = 0.0f;
            }
        }
        if (z) {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN;
        } else {
            this.mCurrentAppearInterpolator = this.mSlowOutFastInInterpolator;
            f2 = 0.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mAppearAnimationFraction, f2);
        this.mAppearAnimator = ofFloat;
        ofFloat.setInterpolator(Interpolators.LINEAR);
        this.mAppearAnimator.setDuration((long) (Math.abs(this.mAppearAnimationFraction - f2) * j2));
        this.mAppearAnimator.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(this, 1));
        if (animatorListenerAdapter != null) {
            this.mAppearAnimator.addListener(animatorListenerAdapter);
        }
        updateAppearAnimationAlpha();
        updateAppearRect();
        this.mAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView.2
            public boolean mWasCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mWasCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Log.d("ActivatableNotificationView", "startAppearAnim end" + this);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                if (this.mWasCancelled) {
                    InteractionJankMonitor.getInstance().cancel(ActivatableNotificationView.m1710$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                    return;
                }
                ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                int i = ActivatableNotificationView.$r8$clinit;
                activatableNotificationView.enableAppearDrawing(false);
                ActivatableNotificationView.this.onAppearAnimationFinished(z);
                InteractionJankMonitor.getInstance().end(ActivatableNotificationView.m1710$$Nest$mgetCujType(ActivatableNotificationView.this, z));
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                this.mWasCancelled = false;
                InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withView(ActivatableNotificationView.m1710$$Nest$mgetCujType(ActivatableNotificationView.this, z), ActivatableNotificationView.this));
            }
        });
        final ValueAnimator valueAnimator2 = this.mAppearAnimator;
        Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j3) {
                ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                ValueAnimator valueAnimator3 = valueAnimator2;
                ValueAnimator valueAnimator4 = activatableNotificationView.mAppearAnimator;
                if (valueAnimator4 == valueAnimator3) {
                    valueAnimator4.start();
                }
            }
        }, j);
    }

    public final void updateAppearAnimationAlpha() {
        setContentAlpha(((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.4f, 1.0f) - 0.4f) / 0.6f));
    }

    public final void updateAppearRect() {
        float interpolation = this.mCurrentAppearInterpolator.getInterpolation(this.mAppearAnimationFraction);
        float f = (1.0f - interpolation) * this.mAnimationTranslationY;
        this.mAppearAnimationTranslation = f;
        float f2 = this.mActualHeight;
        float f3 = interpolation * f2;
        if (this.mTargetPoint == null) {
            setOutlineRect(0.0f, f, getWidth(), f3 + this.mAppearAnimationTranslation);
            return;
        }
        int width = getWidth();
        float f4 = 1.0f - this.mAppearAnimationFraction;
        Point point = this.mTargetPoint;
        int i = point.x;
        float f5 = this.mAnimationTranslationY;
        setOutlineRect(i * f4, DependencyGraph$$ExternalSyntheticOutline0.m20m(f5, point.y, f4, f5), width - ((width - i) * f4), f2 - ((r3 - r2) * f4));
    }

    public final void updateBackground() {
        this.mBackgroundNormal.setVisibility(!hideBackground() || ((isPinned() && isSummaryWithChildren()) || (isSummaryWithChildren() && isShowingPublic())) ? 0 : 4);
    }

    public void updateBackgroundColors() {
        updateColors();
        initBackground();
        updateBackgroundTint();
    }

    public void updateBackgroundTint() {
        updateBackgroundTint(false);
    }

    public final void updateCurrentBackgroundDimmedAlpha() {
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.get(NotificationColorPicker.class);
        if (this instanceof ExpandableNotificationRow) {
            notificationColorPicker.getClass();
            if (!NotificationColorPicker.isNeedToUpdated((ExpandableNotificationRow) this)) {
                return;
            }
        }
        float lockNoticardOpacity = (((SettingsHelper) Dependency.get(SettingsHelper.class)).getLockNoticardOpacity() * 255) / 100;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int i = this.mDimmed ? (int) lockNoticardOpacity : notificationColorPicker.mCustomedAlpha;
        notificationBackgroundView.mDrawableAlpha = i;
        if (notificationBackgroundView.mExpandAnimationRunning) {
            return;
        }
        notificationBackgroundView.mBackground.setAlpha(i);
    }

    public final void updateBackgroundTint(boolean z) {
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        int i = this.mBgTint != 0 ? this.mTintedRippleColor : this.mNormalRippleColor;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        Drawable drawable = notificationBackgroundView.mBackground;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
            notificationBackgroundView.mRippleColor = Integer.valueOf(i);
        } else {
            notificationBackgroundView.mRippleColor = null;
        }
        int calculateBgColor = calculateBgColor(true, true);
        if (!z) {
            setBackgroundTintColor(calculateBgColor);
            return;
        }
        int i2 = this.mCurrentBackgroundTint;
        if (calculateBgColor != i2) {
            this.mStartTint = i2;
            this.mTargetTint = calculateBgColor;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mBackgroundColorAnimator = ofFloat;
            ofFloat.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(this, 0));
            this.mBackgroundColorAnimator.setDuration(360L);
            this.mBackgroundColorAnimator.setInterpolator(Interpolators.LINEAR);
            this.mBackgroundColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ActivatableNotificationView.this.mBackgroundColorAnimator = null;
                }
            });
            this.mBackgroundColorAnimator.start();
        }
    }

    public void onAppearAnimationFinished(boolean z) {
    }

    public void onTap() {
    }

    public void resetAllContentAlphas() {
    }
}
