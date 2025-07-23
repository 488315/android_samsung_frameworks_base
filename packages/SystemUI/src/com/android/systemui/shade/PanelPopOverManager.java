package com.android.systemui.shade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.internal.graphics.drawable.BackgroundBlurDrawable;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.blur.SecQSBlurShadowView;
import com.android.systemui.blur.SecQSNewBlurView;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PanelPopOverManager {
    public static final Interpolator INTERPOLATOR;
    public final BarOrderInteractor barOrderInteractor;
    public View blurView;
    public int blurViewTopMargin;
    public NotificationPanelViewController$$ExternalSyntheticLambda18 collapseRunnable;
    public final Context context;
    public int currentPanelState;
    public View customizerView;
    public View detailView;
    public boolean isAnimating;
    public boolean isClosingByOutsideTouch;
    public boolean isPopOverAreaListenerAdded;
    public SecQSBlurShadowView largeShadowView;
    public int largeShadowViewDistanceFromBlur;
    public NotificationPanelView mView;
    public int navigationBarTop;
    public NotificationShelf notificationShelf;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public int overExpansionAmount;
    public final SecPanelSplitHelper panelSplitHelper;
    public NotificationPanelViewController panelViewController;
    public View qsPanelView;
    public final SecQSPanelResourcePicker secQSPanelResourcePicker;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final ShadeRepository shadeRepository;
    public ValueAnimator showDetailAnimator;
    public ValueAnimator showPanelAnimator;
    public SecQSBlurShadowView smallShadowView;
    public int smallShadowViewDistanceFromBlur;
    public final PanelPopOverManager$popOverInsetsListener$1 popOverInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.shade.PanelPopOverManager$popOverInsetsListener$1
        public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            PanelPopOverManager panelPopOverManager = PanelPopOverManager.this;
            NotificationPanelViewController notificationPanelViewController = panelPopOverManager.panelViewController;
            if (notificationPanelViewController != null && notificationPanelViewController.isExpanded()) {
                NotificationPanelViewController notificationPanelViewController2 = panelPopOverManager.panelViewController;
                if ((notificationPanelViewController2 != null ? notificationPanelViewController2.mExpandedHeight : 0.0f) > 0.0f) {
                    View view = panelPopOverManager.qsPanelView;
                    int width = view != null ? view.getWidth() : 0;
                    int popOverHeight = panelPopOverManager.getPopOverHeight();
                    panelPopOverManager.setTouchableArea(internalInsetsInfo, width, popOverHeight);
                    panelPopOverManager.setBlurArea(width, popOverHeight);
                    return;
                }
            }
            NotificationPanelViewController notificationPanelViewController3 = panelPopOverManager.panelViewController;
            boolean z = !(notificationPanelViewController3 != null && notificationPanelViewController3.isExpanded());
            NotificationPanelViewController notificationPanelViewController4 = panelPopOverManager.panelViewController;
            Log.d("PanelPopOverManager", "setPopoverArea: not expanded. " + z + " || " + ((notificationPanelViewController4 != null ? notificationPanelViewController4.mExpandedHeight : 0.0f) <= 0.0f) + " ");
            panelPopOverManager.setTouchableArea(internalInsetsInfo, 0, 0);
            panelPopOverManager.setBlurArea(0, 0);
            panelPopOverManager.removePopOverAreaListener();
        }
    };
    public final int[] blurViewLocation = {0, 0};
    public final int[] shelfLocation = {0, 0};
    public final int[] panelLocation = {0, 0};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        INTERPOLATOR = new PathInterpolator(0.37f, 0.3f, 0.14f, 1.34f);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.PanelPopOverManager$popOverInsetsListener$1] */
    public PanelPopOverManager(Context context, SecPanelSplitHelper secPanelSplitHelper, NotificationStackScrollLayoutController notificationStackScrollLayoutController, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, ShadeExpansionStateManager shadeExpansionStateManager, ShadeRepository shadeRepository, BarOrderInteractor barOrderInteractor, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.context = context;
        this.panelSplitHelper = secPanelSplitHelper;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.shadeRepository = shadeRepository;
        this.barOrderInteractor = barOrderInteractor;
        this.secQSPanelResourcePicker = secQSPanelResourcePicker;
    }

    public final boolean getNeedToPopOver() {
        return QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && this.secQsUiDisplayModeInteractor.isTablet();
    }

    public final int getPopOverHeight() {
        int height;
        int i;
        SecPanelSplitHelper.Companion.getClass();
        boolean z = SecPanelSplitHelper.isEnabled;
        int[] iArr = this.panelLocation;
        int[] iArr2 = this.shelfLocation;
        int[] iArr3 = this.blurViewLocation;
        if (!z) {
            View view = this.blurView;
            if (view != null) {
                view.getLocationOnScreen(iArr3);
            }
            NotificationShelf notificationShelf = this.notificationShelf;
            if (notificationShelf != null) {
                notificationShelf.getLocationOnScreen(iArr2);
            }
            View view2 = this.isAnimating ? this.qsPanelView : QsAnimatorState.isCustomizerShowing ? this.customizerView : QsAnimatorState.isDetailShowing ? this.detailView : this.qsPanelView;
            if (view2 != null) {
                view2.getLocationOnScreen(iArr);
            }
            NotificationShelf notificationShelf2 = this.notificationShelf;
            int height2 = notificationShelf2 != null ? notificationShelf2.getHeight() : 0;
            height = view2 != null ? view2.getHeight() : 0;
            int dimensionPixelSize = iArr2[1] + height2 + this.context.getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
            int i2 = iArr3[1];
            int i3 = dimensionPixelSize - i2;
            int i4 = (iArr[1] + height) - i2;
            if (view2 != this.qsPanelView) {
                return height;
            }
            ShadeRepository shadeRepository = this.shadeRepository;
            return (((double) ((Number) ((ShadeRepositoryImpl) shadeRepository).qsExpansion.$$delegate_0.getValue()).floatValue()) < 0.5d ? Float.valueOf((((Number) ((ShadeRepositoryImpl) shadeRepository).qsExpansion.$$delegate_0.getValue()).floatValue() * (i4 - i3) * 2) + i3) : Integer.valueOf(i4)).intValue();
        }
        SecPanelSplitHelper secPanelSplitHelper = this.panelSplitHelper;
        if (secPanelSplitHelper.enabled) {
            int i5 = secPanelSplitHelper.currentState;
            i = i5 != 2 ? i5 : secPanelSplitHelper.draggedFraction < 0.5f ? secPanelSplitHelper.stateOnDown : secPanelSplitHelper.stateToChange;
        } else {
            i = 3;
        }
        if (i == 0) {
            View view3 = this.blurView;
            if (view3 != null) {
                view3.getLocationOnScreen(iArr3);
            }
            View view4 = this.isAnimating ? this.qsPanelView : QsAnimatorState.isCustomizerShowing ? this.customizerView : QsAnimatorState.isDetailShowing ? this.detailView : this.qsPanelView;
            if (view4 != null) {
                view4.getLocationOnScreen(iArr);
            }
            height = view4 != null ? view4.getHeight() : 0;
            return view4 != this.qsPanelView ? height : ((iArr[1] + height) + this.overExpansionAmount) - iArr3[1];
        }
        if (i != 1) {
            return 0;
        }
        View view5 = this.blurView;
        if (view5 != null) {
            view5.getLocationOnScreen(iArr3);
        }
        NotificationShelf notificationShelf3 = this.notificationShelf;
        if (notificationShelf3 != null) {
            notificationShelf3.getLocationOnScreen(iArr2);
        }
        int dimensionPixelSize2 = this.context.getResources().getDimensionPixelSize(R.dimen.qs_icon_size);
        NotificationShelf notificationShelf4 = this.notificationShelf;
        return ((iArr2[1] + (notificationShelf4 != null ? notificationShelf4.getHeight() : 0)) + dimensionPixelSize2) - iArr3[1];
    }

    public final void removePopOverAreaListener() {
        ViewTreeObserver viewTreeObserver;
        if (this.isPopOverAreaListenerAdded) {
            Log.d("PanelPopOverManager", "removePopoverAreaListener");
            NotificationPanelView notificationPanelView = this.mView;
            if (notificationPanelView != null && (viewTreeObserver = notificationPanelView.getViewTreeObserver()) != null) {
                viewTreeObserver.removeOnComputeInternalInsetsListener(this.popOverInsetsListener);
            }
            this.isPopOverAreaListenerAdded = false;
            View view = this.blurView;
            if (view != null) {
                view.setVisibility(8);
            }
            SecQSBlurShadowView secQSBlurShadowView = this.largeShadowView;
            if (secQSBlurShadowView != null) {
                secQSBlurShadowView.setVisibility(8);
            }
            SecQSBlurShadowView secQSBlurShadowView2 = this.smallShadowView;
            if (secQSBlurShadowView2 != null) {
                secQSBlurShadowView2.setVisibility(8);
            }
            this.blurView = null;
            this.largeShadowView = null;
            this.smallShadowView = null;
            this.qsPanelView = null;
            this.customizerView = null;
            this.notificationShelf = null;
        }
    }

    public final void setBlurArea(int i, int i2) {
        SecQSNewBlurView secQSNewBlurView;
        SecQSNewBlurView secQSNewBlurView2;
        if (this.isAnimating) {
            return;
        }
        View view = this.blurView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (view != null ? view.getLayoutParams() : null);
        float qsFrameX = this.secQSPanelResourcePicker.getQsFrameX();
        View view2 = this.blurView;
        Integer valueOf = (view2 == null || (secQSNewBlurView2 = (SecQSNewBlurView) view2.findViewById(R.id.qs_new_blur)) == null) ? null : Integer.valueOf(secQSNewBlurView2.newPos[1]);
        if (layoutParams != null && layoutParams.width == i && layoutParams.height == i2 && layoutParams.topMargin == this.blurViewTopMargin) {
            View view3 = this.blurView;
            if (Intrinsics.areEqual(view3 != null ? Float.valueOf(view3.getTranslationX()) : null, qsFrameX)) {
                int i3 = this.blurViewLocation[1];
                if (valueOf != null && i3 == valueOf.intValue()) {
                    return;
                }
            }
        }
        setShadowArea(i, i2);
        if (layoutParams != null) {
            layoutParams.width = i;
        }
        if (layoutParams != null) {
            layoutParams.height = i2;
        }
        if (layoutParams != null) {
            layoutParams.topMargin = this.blurViewTopMargin;
        }
        View view4 = this.blurView;
        if (view4 != null) {
            view4.setLayoutParams(layoutParams);
        }
        View view5 = this.blurView;
        if (view5 != null) {
            view5.setTranslationX(qsFrameX);
        }
        View view6 = this.blurView;
        if (view6 != null && (secQSNewBlurView = (SecQSNewBlurView) view6.findViewById(R.id.qs_new_blur)) != null) {
            int i4 = this.navigationBarTop;
            if (secQSNewBlurView.getBackground() instanceof BackgroundBlurDrawable) {
                secQSNewBlurView.getLocationOnScreen(secQSNewBlurView.newPos);
                BackgroundBlurDrawable background = secQSNewBlurView.getBackground();
                int[] iArr = secQSNewBlurView.newPos;
                int i5 = iArr[0];
                background.setClipRect(i5, iArr[1], secQSNewBlurView.getWidth() + i5, i4);
            }
        }
        View view7 = this.blurView;
        if (view7 != null) {
            view7.setClipBounds(new Rect(0, 0, i, this.navigationBarTop - this.blurViewTopMargin));
        }
    }

    public final void setShadowArea(int i, int i2) {
        SecQSBlurShadowView secQSBlurShadowView = this.largeShadowView;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (secQSBlurShadowView != null ? secQSBlurShadowView.getLayoutParams() : null);
        SecQSBlurShadowView secQSBlurShadowView2 = this.smallShadowView;
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) (secQSBlurShadowView2 != null ? secQSBlurShadowView2.getLayoutParams() : null);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.secQSPanelResourcePicker;
        float qsFrameX = secQSPanelResourcePicker.getQsFrameX();
        Context context = this.context;
        SecQSPanelResourceNormalPicker targetPicker = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker();
        targetPicker.getClass();
        int screenWidth = DeviceState.getScreenWidth(context) - targetPicker.getPanelWidth(context);
        float f = screenWidth == 0 ? 0.0f : (targetPicker.qsTransitionX * (-4.0f)) / screenWidth;
        int i3 = this.largeShadowViewDistanceFromBlur;
        int i4 = (i3 * 2) + i;
        int i5 = this.smallShadowViewDistanceFromBlur;
        int i6 = (i5 * 2) + i;
        int i7 = this.blurViewTopMargin;
        int i8 = i7 - i3;
        int i9 = i7 - i5;
        SecQSBlurShadowView secQSBlurShadowView3 = this.largeShadowView;
        if (secQSBlurShadowView3 != null) {
            secQSBlurShadowView3.setClipBounds(new Rect(0, 0, i4, this.navigationBarTop - i8));
        }
        SecQSBlurShadowView secQSBlurShadowView4 = this.smallShadowView;
        if (secQSBlurShadowView4 != null) {
            secQSBlurShadowView4.setClipBounds(new Rect(0, 0, i6, this.navigationBarTop - i9));
        }
        if (layoutParams != null) {
            layoutParams.width = i4;
        }
        if (layoutParams != null) {
            layoutParams.height = (this.largeShadowViewDistanceFromBlur * 2) + i2;
        }
        if (layoutParams != null) {
            layoutParams.topMargin = i8;
        }
        if (layoutParams2 != null) {
            layoutParams2.width = i6;
        }
        if (layoutParams2 != null) {
            layoutParams2.height = (this.smallShadowViewDistanceFromBlur * 2) + i2;
        }
        if (layoutParams2 != null) {
            layoutParams2.topMargin = i9;
        }
        SecQSBlurShadowView secQSBlurShadowView5 = this.largeShadowView;
        if (secQSBlurShadowView5 != null) {
            secQSBlurShadowView5.setLayoutParams(layoutParams);
        }
        SecQSBlurShadowView secQSBlurShadowView6 = this.largeShadowView;
        if (secQSBlurShadowView6 != null) {
            secQSBlurShadowView6.setTranslationX(qsFrameX);
        }
        SecQSBlurShadowView secQSBlurShadowView7 = this.largeShadowView;
        if (secQSBlurShadowView7 != null) {
            secQSBlurShadowView7.paint.setShadowLayer(secQSBlurShadowView7.getAlpha() * 60.0f, f, 15.0f, this.context.getColor(R.color.qs_large_shadow_color));
        }
        SecQSBlurShadowView secQSBlurShadowView8 = this.largeShadowView;
        if (secQSBlurShadowView8 != null) {
            float f2 = this.largeShadowViewDistanceFromBlur;
            secQSBlurShadowView8.left = f2;
            secQSBlurShadowView8.top = f2;
        }
        if (secQSBlurShadowView8 != null) {
            secQSBlurShadowView8.width = i;
            secQSBlurShadowView8.height = i2;
        }
        SecQSBlurShadowView secQSBlurShadowView9 = this.smallShadowView;
        if (secQSBlurShadowView9 != null) {
            secQSBlurShadowView9.setLayoutParams(layoutParams2);
        }
        SecQSBlurShadowView secQSBlurShadowView10 = this.smallShadowView;
        if (secQSBlurShadowView10 != null) {
            secQSBlurShadowView10.setTranslationX(qsFrameX);
        }
        SecQSBlurShadowView secQSBlurShadowView11 = this.smallShadowView;
        if (secQSBlurShadowView11 != null) {
            secQSBlurShadowView11.paint.setShadowLayer(secQSBlurShadowView11.getAlpha() * 10.0f, 0.0f, 2.0f, this.context.getColor(R.color.qs_small_shadow_color));
        }
        SecQSBlurShadowView secQSBlurShadowView12 = this.smallShadowView;
        if (secQSBlurShadowView12 != null) {
            float f3 = this.smallShadowViewDistanceFromBlur;
            secQSBlurShadowView12.left = f3;
            secQSBlurShadowView12.top = f3;
        }
        if (secQSBlurShadowView12 != null) {
            secQSBlurShadowView12.width = i;
            secQSBlurShadowView12.height = i2;
        }
    }

    public final void setTouchableArea(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo, int i, int i2) {
        NotificationPanelViewController notificationPanelViewController = this.panelViewController;
        if (notificationPanelViewController == null || notificationPanelViewController.mBarState != 0) {
            return;
        }
        int[] iArr = this.blurViewLocation;
        int i3 = iArr[1] + i2;
        int i4 = iArr[0];
        Region region = new Region(i4, iArr[1], i + i4, i3);
        if (region.isEmpty()) {
            NotificationPanelView notificationPanelView = this.mView;
            if (notificationPanelView != null) {
                region.set(0, 0, notificationPanelView.getWidth(), notificationPanelView.getHeight());
            }
        } else {
            Rect rect = new Rect();
            int statusBarHeight = SystemBarUtils.getStatusBarHeight(this.context);
            NotificationPanelView notificationPanelView2 = this.mView;
            rect.set(0, 0, notificationPanelView2 != null ? notificationPanelView2.getWidth() : 0, statusBarHeight);
            region.union(rect);
        }
        NotificationPanelViewController notificationPanelViewController2 = this.panelViewController;
        if (notificationPanelViewController2 == null || !notificationPanelViewController2.isFlinging()) {
            internalInsetsInfo.setTouchableInsets(3);
            internalInsetsInfo.touchableRegion.set(region);
        } else {
            internalInsetsInfo.setTouchableInsets(0);
        }
        region.recycle();
    }

    public final void transitionBlurAnim(boolean z) {
        if (getNeedToPopOver()) {
            this.isAnimating = true;
            View view = this.blurView;
            int height = view != null ? view.getHeight() : 0;
            View view2 = this.blurView;
            final int width = view2 != null ? view2.getWidth() : 0;
            View view3 = QsAnimatorState.isCustomizerShowing ? this.customizerView : this.detailView;
            int height2 = view3 != null ? view3.getHeight() : 0;
            if (z) {
                ValueAnimator ofInt = ValueAnimator.ofInt(height, height2);
                ofInt.setDuration(480L);
                ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$1$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        PanelPopOverManager panelPopOverManager = PanelPopOverManager.this;
                        panelPopOverManager.showDetailAnimator = null;
                        panelPopOverManager.isAnimating = false;
                    }
                });
                ofInt.setInterpolator(INTERPOLATOR);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$1$2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        View view4 = PanelPopOverManager.this.blurView;
                        ViewGroup.LayoutParams layoutParams = view4 != null ? view4.getLayoutParams() : null;
                        if (layoutParams != null) {
                            layoutParams.height = intValue;
                        }
                        View view5 = PanelPopOverManager.this.blurView;
                        if (view5 != null) {
                            view5.setLayoutParams(layoutParams);
                        }
                        PanelPopOverManager.this.setShadowArea(width, intValue);
                    }
                });
                this.showDetailAnimator = ofInt;
                ValueAnimator valueAnimator = this.showPanelAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                }
                ValueAnimator valueAnimator2 = this.showDetailAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.start();
                    return;
                }
                return;
            }
            ValueAnimator ofInt2 = ValueAnimator.ofInt(height, getPopOverHeight());
            ofInt2.setDuration(300L);
            ofInt2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$2$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PanelPopOverManager panelPopOverManager = PanelPopOverManager.this;
                    panelPopOverManager.showPanelAnimator = null;
                    panelPopOverManager.isAnimating = false;
                }
            });
            ofInt2.setInterpolator(INTERPOLATOR);
            ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.shade.PanelPopOverManager$transitionBlurAnim$animator$2$2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                    int intValue = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                    View view4 = PanelPopOverManager.this.blurView;
                    ViewGroup.LayoutParams layoutParams = view4 != null ? view4.getLayoutParams() : null;
                    if (layoutParams != null) {
                        layoutParams.height = intValue;
                    }
                    View view5 = PanelPopOverManager.this.blurView;
                    if (view5 != null) {
                        view5.setLayoutParams(layoutParams);
                    }
                    PanelPopOverManager.this.setShadowArea(width, intValue);
                }
            });
            this.showPanelAnimator = ofInt2;
            ValueAnimator valueAnimator3 = this.showDetailAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.cancel();
            }
            ValueAnimator valueAnimator4 = this.showPanelAnimator;
            if (valueAnimator4 != null) {
                valueAnimator4.start();
            }
        }
    }
}
