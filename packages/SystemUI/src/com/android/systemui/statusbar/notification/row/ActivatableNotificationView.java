package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import com.android.app.animation.Interpolators;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.FakeShadowView;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationHeadsUpCycling;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
    public boolean mIsHeadsUpAnimation;
    public boolean mIsHeadsUpCycling;
    public long mLastActionUpTime;
    public int mNormalColor;
    public final Set mOnDetachResetRoundness;
    public boolean mRefocusOnDismiss;
    public boolean mShadowHidden;
    public int mStartTint;
    public Point mTargetPoint;
    public int mTargetTint;
    public Gefingerpoken mTouchHandler;

    /* renamed from: -$$Nest$mgetCujType, reason: not valid java name */
    public static int m3065$$Nest$mgetCujType(ActivatableNotificationView activatableNotificationView, boolean z) {
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
        SeslRecoilDrawable seslRecoilDrawable;
        OngoingActivityDataHelper ongoingActivityDataHelper = OngoingActivityDataHelper.INSTANCE;
        String str = ((ExpandableNotificationRow) this).mEntry.mKey;
        ongoingActivityDataHelper.getClass();
        OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
        if (ongoingActivityDataByKey == null || (seslRecoilDrawable = this.mBackgroundNormal.mBackground) == null || seslRecoilDrawable.getNumberOfLayers() <= 1) {
            return;
        }
        boolean z2 = ongoingActivityDataByKey.mCustomExpandedCardView == null;
        CardStackViewUtils cardStackViewUtils = CardStackViewUtils.INSTANCE;
        Context context = ((FrameLayout) this).mContext;
        int i3 = ongoingActivityDataByKey.mChipBackground;
        Drawable drawable = seslRecoilDrawable.getDrawable(1);
        boolean z3 = z && z2;
        cardStackViewUtils.getClass();
        CardStackViewUtils.addGradientBackground(context, i, i2, i3, drawable, false, z3);
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
        SeslRecoilDrawable seslRecoilDrawable = (SeslRecoilDrawable) getResources().getDrawable(R.drawable.notification_material_bg);
        ((GradientDrawable) seslRecoilDrawable.getDrawable(0)).setColors(new int[]{color, color});
        this.mBackgroundNormal.setCustomBackground(seslRecoilDrawable);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        ((FrameLayout) this).mContext.getColor(R.color.notification_panel_theme_ripple_color);
        SeslRecoilDrawable seslRecoilDrawable2 = notificationBackgroundView.mBackground;
        notificationBackgroundView.mRippleColor = null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.Roundable
    public void applyRoundnessAndInvalidate() {
        float f = getRoundableState().topRoundness * getRoundableState().maxRadius;
        float f2 = getRoundableState().bottomRoundness * getRoundableState().maxRadius;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        float[] fArr = notificationBackgroundView.mCornerRadii;
        if (f != fArr[0] || f2 != fArr[4]) {
            notificationBackgroundView.mBottomIsRounded = f2 != 0.0f;
            fArr[0] = f;
            fArr[1] = f;
            fArr[2] = f;
            fArr[3] = f;
            fArr[4] = f2;
            fArr[5] = f2;
            fArr[6] = f2;
            fArr[7] = f2;
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
        return (!z || (i = this.mBgTint) == 0) ? this.mNormalColor : i;
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
        SeslRecoilDrawable seslRecoilDrawable = notificationBackgroundView.mBackground;
        if (seslRecoilDrawable != null) {
            seslRecoilDrawable.setState(drawableState);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(DumpUtilsKt.asIndenting(printWriter), strArr);
    }

    public final void enableAppearDrawing(boolean z) {
        if (z != this.mDrawingAppearAnimation) {
            this.mDrawingAppearAnimation = z;
            if (!z) {
                setContentAlpha$1(1.0f);
                this.mAppearAnimationFraction = -1.0f;
                this.mCustomOutline = false;
                applyRoundnessAndInvalidate();
            }
            invalidate();
        }
    }

    public abstract View getContentView();

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    public boolean hideBackground() {
        return false;
    }

    public final void initBackground() {
        NotificationEntry notificationEntry;
        NotificationEntry notificationEntry2;
        ExpandableNotificationRow expandableNotificationRow = this instanceof ExpandableNotificationRow ? (ExpandableNotificationRow) this : null;
        if (expandableNotificationRow != null && (notificationEntry2 = expandableNotificationRow.mEntry) != null && notificationEntry2.isOngoingActivity() && expandableNotificationRow.mEntry.isPromotedState()) {
            expandableNotificationRow.mViewState.hasGradient = false;
        }
        SeslRecoilDrawable seslRecoilDrawable = (SeslRecoilDrawable) getResources().getDrawable(R.drawable.notification_material_bg, null);
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        int notificationBgColor = notificationColorPicker.getNotificationBgColor();
        if (expandableNotificationRow != null && (notificationEntry = expandableNotificationRow.mEntry) != null && notificationEntry.mSbn != null && !NotificationColorPicker.isNeedToUpdated(expandableNotificationRow)) {
            notificationBgColor = notificationColorPicker.getNotificationDefaultBgColor();
        }
        if (expandableNotificationRow != null && NotificationColorPicker.isNeedToUpdated(expandableNotificationRow) && this.mDimmed) {
            notificationBgColor = ((FrameLayout) this).mContext.getColor(R.color.notification_material_background_on_lockscreen);
        }
        if ((this instanceof NotificationShelf) && this.mDimmed) {
            notificationBgColor = ((FrameLayout) this).mContext.getColor(R.color.notification_material_background_on_lockscreen);
        }
        seslRecoilDrawable.setDrawable(0, new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{notificationBgColor, notificationBgColor}));
        this.mBackgroundNormal.setCustomBackground(seslRecoilDrawable);
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
    public void performAddAnimation(long j, long j2, boolean z, boolean z2, Runnable runnable) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        this.mIsHeadsUpCycling = z2;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(true, z ? 0.0f : -1.0f, j, j2, null, null, null, ExpandableView.ClipSide.BOTTOM);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public long performRemoveAnimation(long j, long j2, float f, boolean z, boolean z2, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter, ExpandableView.ClipSide clipSide) {
        enableAppearDrawing(true);
        this.mIsHeadsUpAnimation = z;
        this.mIsHeadsUpCycling = z2;
        if (this.mDrawingAppearAnimation) {
            startAppearAnimation(false, f, j2, j, runnable, runnable2, animatorListenerAdapter, clipSide);
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

    public final void setContentAlpha$1(float f) {
        if (isHeadsUpAnimatingAway() || isPinned()) {
            setAlpha(f);
            return;
        }
        View contentView = getContentView();
        if (contentView.hasOverlappingRendering()) {
            contentView.setLayerType((f == 0.0f || f == 1.0f) ? 0 : 2, null);
        }
        contentView.setAlpha(f);
        if (f == 1.0f) {
            resetAllContentAlphas();
        }
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
        if (this instanceof NotificationShelf) {
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

    public final void startAppearAnimation(final boolean z, float f, long j, long j2, final Runnable runnable, final Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter, final ExpandableView.ClipSide clipSide) {
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
            this.mCurrentAppearInterpolator = Interpolators.FAST_OUT_SLOW_IN_REVERSE;
            f2 = 0.0f;
        }
        int i = NotificationHeadsUpCycling.$r8$clinit;
        if (this.mIsHeadsUpCycling) {
            this.mCurrentAppearInterpolator = Interpolators.LINEAR;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mAppearAnimationFraction, f2);
        this.mAppearAnimator = ofFloat;
        ofFloat.setInterpolator(!this.mIsHeadsUpCycling ? Interpolators.LINEAR : this.mCurrentAppearInterpolator);
        this.mAppearAnimator.setDuration((long) (Math.abs(this.mAppearAnimationFraction - f2) * j2));
        this.mAppearAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                ExpandableView.ClipSide clipSide2 = clipSide;
                int i2 = ActivatableNotificationView.$r8$clinit;
                activatableNotificationView.getClass();
                activatableNotificationView.mAppearAnimationFraction = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                activatableNotificationView.updateAppearAnimationAlpha();
                int i3 = NotificationHeadsUpCycling.$r8$clinit;
                activatableNotificationView.updateAppearRect(clipSide2);
                activatableNotificationView.invalidate();
            }
        });
        if (animatorListenerAdapter != null) {
            this.mAppearAnimator.addListener(animatorListenerAdapter);
        }
        updateAppearAnimationAlpha();
        updateAppearRect(clipSide);
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
                    int i2 = ActivatableNotificationView.$r8$clinit;
                    activatableNotificationView.enableAppearDrawing(false);
                }
                ActivatableNotificationView.this.onAppearAnimationFinished(z, !this.mRunWithoutInterruptions);
                if (this.mRunWithoutInterruptions) {
                    InteractionJankMonitor.getInstance().end(ActivatableNotificationView.m3065$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                } else {
                    InteractionJankMonitor.getInstance().cancel(ActivatableNotificationView.m3065$$Nest$mgetCujType(ActivatableNotificationView.this, z));
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                Runnable runnable3 = runnable;
                if (runnable3 != null) {
                    runnable3.run();
                }
                ActivatableNotificationView.this.onAppearAnimationStarted(z);
                this.mRunWithoutInterruptions = true;
                InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withView(ActivatableNotificationView.m3065$$Nest$mgetCujType(ActivatableNotificationView.this, z), ActivatableNotificationView.this));
            }
        });
        final ValueAnimator valueAnimator2 = this.mAppearAnimator;
        Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda2
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j3) {
                ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                ValueAnimator valueAnimator3 = valueAnimator2;
                boolean z2 = z;
                ValueAnimator valueAnimator4 = activatableNotificationView.mAppearAnimator;
                if (valueAnimator4 == valueAnimator3) {
                    valueAnimator4.start();
                } else {
                    activatableNotificationView.onAppearAnimationSkipped(z2);
                }
            }
        }, j);
    }

    public final void updateAppearAnimationAlpha() {
        if (isHeadsUpAnimatingAway() || isPinned()) {
            setContentAlpha$1(this.mCurrentAppearInterpolator.getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.3f, 1.0f) - 0.3f) / 0.7f));
        } else {
            setContentAlpha$1(this.mCurrentAppearInterpolator.getInterpolation((MathUtils.constrain(this.mAppearAnimationFraction, 0.55f, 1.0f) - 0.55f) / 0.45f));
        }
    }

    public final void updateAppearRect(ExpandableView.ClipSide clipSide) {
        if (isHeadsUpAnimatingAway() || isPinned()) {
            this.mAppearAnimationFraction = 1.0f;
        }
        float interpolation = !this.mIsHeadsUpCycling ? this.mCurrentAppearInterpolator.getInterpolation(this.mAppearAnimationFraction) : this.mAppearAnimationFraction;
        float f = (1.0f - interpolation) * this.mAnimationTranslationY;
        this.mAppearAnimationTranslation = f;
        float f2 = this.mActualHeight;
        float f3 = interpolation * f2;
        if (this.mTargetPoint == null) {
            if (clipSide == ExpandableView.ClipSide.TOP) {
                setOutlineRect(0.0f, f2 - f3, getWidth(), f2);
                return;
            } else {
                if (clipSide == ExpandableView.ClipSide.BOTTOM) {
                    setOutlineRect(0.0f, f, getWidth(), f3 + this.mAppearAnimationTranslation);
                    return;
                }
                return;
            }
        }
        int width = getWidth();
        float f4 = 1.0f - this.mAppearAnimationFraction;
        Point point = this.mTargetPoint;
        int i = point.x;
        float f5 = this.mAnimationTranslationY;
        setOutlineRect(i * f4, DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f5, point.y, f4, f5), width - ((width - i) * f4), f2 - ((r3 - r0) * f4));
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
        this.mNormalColor = ((NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class)).getNotificationBgColor();
        ((FrameLayout) this).mContext.getColor(R.color.notification_ripple_tinted_color);
        ((FrameLayout) this).mContext.getColor(R.color.notification_ripple_untinted_color);
        this.mBgTint = 0;
    }

    public final void updateCurrentBackgroundDimmedAlpha() {
        boolean z = this instanceof ExpandableNotificationRow;
        if (z && isInsignificant() && !this.mDimmed) {
            if (isGroupExpanded$1()) {
                updateInsignificantAlpha(1.0f);
                return;
            } else {
                updateInsignificantAlpha(0.0f);
                return;
            }
        }
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        if (z) {
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
        if (this.mDimmed) {
            return;
        }
        NotificationColorPicker notificationColorPicker = (NotificationColorPicker) Dependency.sDependency.getDependencyInner(NotificationColorPicker.class);
        notificationColorPicker.getClass();
        if (NotificationColorPicker.isNeedToUpdated((ExpandableNotificationRow) this)) {
            float interpolate = NotificationUtils.interpolate(notificationColorPicker.isDarkMode$1() ? 56 : 102, notificationColorPicker.mCustomedAlpha, f);
            NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
            int i = (int) interpolate;
            notificationBackgroundView.mDrawableAlpha = i;
            if (notificationBackgroundView.mExpandAnimationRunning) {
                return;
            }
            notificationBackgroundView.mBackground.setAlpha(i);
        }
    }

    public final void updateBackgroundTint(boolean z) {
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ((FrameLayout) this).mContext.getColor(R.color.notification_panel_theme_ripple_color);
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        SeslRecoilDrawable seslRecoilDrawable = notificationBackgroundView.mBackground;
        notificationBackgroundView.mRippleColor = null;
        int calculateBgColor = calculateBgColor(true, true);
        if (!z) {
            setBackgroundTintColor(calculateBgColor);
            return;
        }
        int i = this.mCurrentBackgroundTint;
        if (calculateBgColor != i) {
            this.mStartTint = i;
            this.mTargetTint = calculateBgColor;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mBackgroundColorAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.notification.row.ActivatableNotificationView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ActivatableNotificationView activatableNotificationView = ActivatableNotificationView.this;
                    activatableNotificationView.setBackgroundTintColor(NotificationUtils.interpolateColors(valueAnimator2.getAnimatedFraction(), activatableNotificationView.mStartTint, activatableNotificationView.mTargetTint));
                }
            });
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

    public void onAppearAnimationSkipped(boolean z) {
    }

    public void onAppearAnimationStarted(boolean z) {
    }

    public void resetAllContentAlphas() {
    }

    public void onAppearAnimationFinished(boolean z, boolean z2) {
    }
}
