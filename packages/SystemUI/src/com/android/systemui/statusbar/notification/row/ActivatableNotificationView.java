package com.android.systemui.statusbar.notification.row;

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
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.FakeShadowView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationHeadsUpCycling;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;
import com.android.systemui.statusbar.phone.ongoingactivity.CardStackview.CardStackViewUtils;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import noticolorpicker.NotificationColorPicker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public int mStartTint;
    public Point mTargetPoint;
    public int mTargetTint;
    public int mTintedRippleColor;
    public Gefingerpoken mTouchHandler;

    /* renamed from: -$$Nest$mgetCujType, reason: not valid java name */
    public static int m2220$$Nest$mgetCujType(ActivatableNotificationView activatableNotificationView, boolean z) {
        return activatableNotificationView.mIsHeadsUpAnimation ? z ? 12 : 13 : z ? 14 : 15;
    }

    public ActivatableNotificationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnDetachResetRoundness = new HashSet();
        this.mBgTint = 0;
        this.mAppearAnimationFraction = -1.0f;
        setClipChildren(false);
        setClipToPadding(false);
        updateColors$1();
    }

    public final void applyGradientBackground(int i, int i2, boolean z) {
        Drawable drawable;
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = ((ExpandableNotificationRow) this).mEntry.mKey;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey == null || (drawable = this.mBackgroundNormal.mBackground) == null || !(drawable instanceof RippleDrawable)) {
            return;
        }
        RippleDrawable rippleDrawable = (RippleDrawable) drawable;
        if (rippleDrawable.getNumberOfLayers() > 1) {
            CardStackViewUtils cardStackViewUtils = CardStackViewUtils.INSTANCE;
            Context context = ((FrameLayout) this).mContext;
            Drawable drawable2 = rippleDrawable.getDrawable(1);
            int i3 = ongoingActivityDataByKey.mChipBackground;
            cardStackViewUtils.getClass();
            CardStackViewUtils.addGradientBackground(context, i, i2, i3, drawable2, false, z);
        }
    }

    public final void applyHeadsUpBackground(boolean z) {
        int color = getResources().getColor(R.color.heads_up_notification_background_color);
        if (!z) {
            if (DeviceState.isOpenTheme(((FrameLayout) this).mContext)) {
                int color2 = ((FrameLayout) this).mContext.getResources().getColor(R.color.open_theme_notification_bg_color);
                color = Color.argb(255, Color.red(color2), Color.green(color2), Color.blue(color2));
            }
            if (((FrameLayout) this).mContext.getResources().getBoolean(R.bool.theme_designer_quick_panel_turned_on)) {
                int color3 = ((FrameLayout) this).mContext.getResources().getColor(R.color.qp_notification_background_color);
                color = Color.argb(255, Color.red(color3), Color.green(color3), Color.blue(color3));
            }
        }
        RippleDrawable rippleDrawable = (RippleDrawable) getResources().getDrawable(R.drawable.notification_material_bg);
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
        int i = NotificationsImprovedHunAnimation.$r8$clinit;
        float topCornerRadius = super.getTopCornerRadius();
        float bottomCornerRadius = super.getBottomCornerRadius();
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
            notificationBackgroundView.updateBackgroundRadii();
        }
        super.applyRoundnessAndInvalidate();
    }

    public final int calculateBgColor(boolean z, boolean z2) {
        int i;
        if (this instanceof ExpandableNotificationRow) {
            ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getClass();
            if (!NotificationColorPicker.isNeedToUpdated((ExpandableNotificationRow) this)) {
                return this.mBgTint;
            }
        }
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
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        String simpleName = getClass().getSimpleName();
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, simpleName, dispatchTouchEvent);
        return dispatchTouchEvent;
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
                View contentView = getContentView();
                if (contentView.hasOverlappingRendering()) {
                    contentView.setLayerType(0, null);
                }
                contentView.setAlpha(1.0f);
                resetAllContentAlphas();
                this.mAppearAnimationFraction = -1.0f;
                this.mCustomOutline = false;
                applyRoundnessAndInvalidate();
            }
            invalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final float getBottomCornerRadius() {
        int i = NotificationsImprovedHunAnimation.$r8$clinit;
        return super.getBottomCornerRadius();
    }

    public abstract View getContentView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public final float getTopCornerRadius() {
        int i = NotificationsImprovedHunAnimation.$r8$clinit;
        return super.getTopCornerRadius();
    }

    public boolean hideBackground() {
        return false;
    }

    public final void initBackground() {
        NotificationEntry notificationEntry;
        NotificationEntry notificationEntry2;
        ExpandableNotificationRow expandableNotificationRow = this instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) this : null;
        if (expandableNotificationRow != null && (notificationEntry2 = expandableNotificationRow.mEntry) != null && notificationEntry2.isOngoingAcitivty() && expandableNotificationRow.mEntry.isPromotedState()) {
            expandableNotificationRow.mViewState.hasGradient = false;
        }
        RippleDrawable rippleDrawable = (RippleDrawable) getResources().getDrawable(R.drawable.notification_material_bg, null);
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        int notificationBgColor$1 = notificationColorPicker.getNotificationBgColor$1();
        if (expandableNotificationRow != null && (notificationEntry = expandableNotificationRow.mEntry) != null && notificationEntry.mSbn != null && !NotificationColorPicker.isNeedToUpdated(expandableNotificationRow)) {
            notificationBgColor$1 = notificationColorPicker.getNotificationDefaultBgColor();
        }
        if (expandableNotificationRow != null && NotificationColorPicker.isNeedToUpdated(expandableNotificationRow) && this.mDimmed) {
            notificationBgColor$1 = ((FrameLayout) this).mContext.getColor(R.color.notification_material_background_on_lockscreen);
        }
        if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && (this instanceof NotificationShelf) && this.mDimmed) {
            notificationBgColor$1 = ((FrameLayout) this).mContext.getColor(R.color.notification_material_background_on_lockscreen);
        }
        rippleDrawable.setDrawable(0, new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{notificationBgColor$1, notificationBgColor$1}));
        this.mBackgroundNormal.setCustomBackground(rippleDrawable);
        updateCurrentBackgroundDimmedAlpha();
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
        this.mBackgroundNormal = (NotificationBackgroundView) findViewById(R.id.backgroundNormal);
        FakeShadowView fakeShadowView = (FakeShadowView) findViewById(R.id.fake_shadow);
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
    public final void performAddAnimation(long j, boolean z, long j2) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(z ? 0.0f : -1.0f, j, j2, null, ExpandableView.ClipSide.BOTTOM, null, null, true);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(float f, long j, long j2, AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide, Runnable runnable, Runnable runnable2, boolean z) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(f, j2, j, animatorListenerAdapter, clipSide, runnable, runnable2, false);
            return 0L;
        }
        if (runnable != null) {
            runnable.run();
        }
        if (runnable2 == null) {
            return 0L;
        }
        runnable2.run();
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
        int i = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
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
    public final void setDimmed(boolean z) {
        if (this.mDimmed == z) {
            return;
        }
        this.mDimmed = z;
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        if (this instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this;
            notificationColorPicker.updateAllTextViewColors(expandableNotificationRow, z);
            if (NotificationColorPicker.isNeedToUpdated(expandableNotificationRow)) {
                updateBackgroundColors();
            }
        }
        if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && (this instanceof NotificationShelf)) {
            updateBackgroundColors();
        }
        updateCurrentBackgroundDimmedAlpha();
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

    public final void startAppearAnimation(float f, long j, long j2, AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide, final Runnable runnable, final Runnable runnable2, final boolean z) {
        float f2;
        int i = 0;
        this.mAnimationTranslationY = this.mActualHeight * f;
        ValueAnimator valueAnimator = this.mAppearAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mAppearAnimator = null;
        }
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
            f2 = 1.0f;
        } else {
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
            f2 = 0.0f;
        }
        int i2 = NotificationHeadsUpCycling.$r8$clinit;
        Flags.notificationAvalancheThrottleHun();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mAppearAnimationFraction, f2);
        this.mAppearAnimator = ofFloat;
        int i3 = NotificationsImprovedHunAnimation.$r8$clinit;
        ofFloat.setInterpolator(this.mCurrentAppearInterpolator);
        this.mAppearAnimator.setDuration((long) (Math.abs(this.mAppearAnimationFraction - f2) * j2));
        this.mAppearAnimator.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(this, clipSide));
        if (animatorListenerAdapter != null) {
            this.mAppearAnimator.addListener(animatorListenerAdapter);
        }
        float interpolation = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.7f, 1.0f) - 0.7f) / 0.3f);
        View contentView = getContentView();
        if (contentView.hasOverlappingRendering()) {
            if (interpolation != 0.0f && interpolation != 1.0f) {
                i = 2;
            }
            contentView.setLayerType(i, null);
        }
        contentView.setAlpha(interpolation);
        if (interpolation == 1.0f) {
            resetAllContentAlphas();
        }
        Flags.notificationAvalancheThrottleHun();
        ExpandableView.ClipSide clipSide2 = ExpandableView.ClipSide.BOTTOM;
        float f3 = this.mAppearAnimationFraction;
        float f4 = (1.0f - f3) * this.mAnimationTranslationY;
        this.mAppearAnimationTranslation = f4;
        float f5 = this.mActualHeight;
        float f6 = f3 * f5;
        if (this.mTargetPoint != null) {
            int width = getWidth();
            float f7 = 1.0f - this.mAppearAnimationFraction;
            Point point = this.mTargetPoint;
            int i4 = point.x;
            float f8 = this.mAnimationTranslationY;
            setOutlineRect(i4 * f7, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f8, point.y, f7, f8), width - ((width - i4) * f7), f5 - ((r5 - r3) * f7));
        } else if (clipSide2 == ExpandableView.ClipSide.TOP) {
            setOutlineRect(0.0f, f5 - f6, getWidth(), f5);
        } else {
            setOutlineRect(0.0f, f4, getWidth(), f6 + this.mAppearAnimationTranslation);
        }
        this.mAppearAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView.2
            public boolean mRunWithoutInterruptions;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mRunWithoutInterruptions = false;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Log.d("ActivatableNotificationView", "startAppearAnim end" + this);
                Runnable runnable3 = runnable2;
                if (runnable3 != null) {
                    runnable3.run();
                }
                if (this.mRunWithoutInterruptions) {
                    ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                    int i5 = ActivatableNotificationView.$r8$clinit;
                    activatableNotificationView.enableAppearDrawing(false);
                }
                ActivatableNotificationView.this.onAppearAnimationFinished(z);
                if (this.mRunWithoutInterruptions) {
                    InteractionJankMonitor.getInstance().end(ActivatableNotificationView.m2220$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                } else {
                    InteractionJankMonitor.getInstance().cancel(ActivatableNotificationView.m2220$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
                this.mRunWithoutInterruptions = true;
                InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withView(ActivatableNotificationView.m2220$$Nest$mgetCujType(ActivatableNotificationView.this, z), ActivatableNotificationView.this));
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

    public final void updateBackground() {
        this.mBackgroundNormal.setVisibility(!hideBackground() || (isPinned() && isSummaryWithChildren()) ? 0 : 4);
    }

    public void updateBackgroundColors() {
        updateColors$1();
        initBackground();
        updateBackgroundTint();
    }

    public void updateBackgroundTint() {
        updateBackgroundTint(false);
    }

    public final void updateColors$1() {
        this.mNormalColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getNotificationBgColor$1();
        this.mTintedRippleColor = ((FrameLayout) this).mContext.getColor(R.color.notification_ripple_tinted_color);
        this.mNormalRippleColor = ((FrameLayout) this).mContext.getColor(R.color.notification_ripple_untinted_color);
        this.mBgTint = 0;
        this.mOverrideTint = 0;
        this.mOverrideAmount = 0.0f;
    }

    public final void updateCurrentBackgroundDimmedAlpha() {
        if (NotiRune.NOTI_INSIGNIFICANT && (this instanceof ExpandableNotificationRow) && isInsignificant()) {
            if (isGroupExpanded()) {
                updateInsignificantAlpha(1.0f);
                return;
            } else {
                updateInsignificantAlpha(0.0f);
                return;
            }
        }
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        if (this instanceof ExpandableNotificationRow) {
            notificationColorPicker.getClass();
            if (!NotificationColorPicker.isNeedToUpdated((ExpandableNotificationRow) this)) {
                return;
            }
        }
        float lockNoticardOpacity = (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getLockNoticardOpacity() * 255) / 100;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int i = this.mDimmed ? (int) lockNoticardOpacity : notificationColorPicker.mCustomedAlpha;
        notificationBackgroundView.mDrawableAlpha = i;
        if (notificationBackgroundView.mExpandAnimationRunning) {
            return;
        }
        notificationBackgroundView.mBackground.setAlpha(i);
    }

    public final void updateInsignificantAlpha(float f) {
        float interpolate = NotificationUtils.interpolate((((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 56 : 102, r0.mCustomedAlpha, f);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        int i = (int) interpolate;
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
            ofFloat.addUpdateListener(new ActivatableNotificationView$$ExternalSyntheticLambda0(this));
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

    public void resetAllContentAlphas() {
    }

    public void onAppearAnimationFinished(boolean z) {
    }
}
