package com.android.systemui.statusbar.policy;

import android.animation.ValueAnimator;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.MirrorController;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class BrightnessMirrorController implements MirrorController {
    public FrameLayout mBrightnessMirror;
    public int mBrightnessMirrorBackgroundPadding;
    public final NotificationShadeDepthController mDepthController;
    public final ShadeViewController mNotificationPanel;
    public final SecBrightnessMirrorController mSecBrightnessMirrorController;
    public final NotificationShadeWindowView mStatusBarWindow;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final Consumer mVisibilityCallback;
    public final ArraySet mBrightnessMirrorListeners = new ArraySet();
    public final int[] mInt2Cache = new int[2];
    public final int mLastBrightnessSliderWidth = -1;
    public BrightnessSliderController mToggleSliderController = setMirrorLayout();

    public BrightnessMirrorController(NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, NotificationShadeDepthController notificationShadeDepthController, BrightnessSliderController.Factory factory, Consumer<Boolean> consumer) {
        this.mStatusBarWindow = notificationShadeWindowView;
        this.mToggleSliderFactory = factory;
        this.mBrightnessMirror = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.sec_brightness_mirror_container, (ViewGroup) notificationShadeWindowView, false);
        this.mNotificationPanel = shadeViewController;
        this.mDepthController = notificationShadeDepthController;
        shadeViewController.setAlphaChangeAnimationEndAction(new BrightnessMirrorController$$ExternalSyntheticLambda0(this));
        this.mVisibilityCallback = consumer;
        SecBrightnessMirrorController secBrightnessMirrorController = new SecBrightnessMirrorController();
        this.mSecBrightnessMirrorController = secBrightnessMirrorController;
        secBrightnessMirrorController.reinflate(this.mBrightnessMirror, this.mToggleSliderController);
        updateResources$1();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        BrightnessBar.AnonymousClass2 anonymousClass2 = (BrightnessBar.AnonymousClass2) obj;
        Objects.requireNonNull(anonymousClass2);
        this.mBrightnessMirrorListeners.add(anonymousClass2);
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void hideMirror() {
        SecBrightnessSliderController secBrightnessSliderController;
        SecBrightnessSliderController secBrightnessSliderController2;
        SecQpBlurController secQpBlurController;
        this.mVisibilityCallback.accept(Boolean.FALSE);
        this.mNotificationPanel.setAlpha(255, true);
        NotificationShadeDepthController.DepthAnimation depthAnimation = this.mDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != 0) {
            depthAnimation.pendingRadius = 0;
            depthAnimation.springAnimation.animateToFinalPosition(0);
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            FrameLayout frameLayout = secBrightnessMirrorController.brightnessMirror;
            if (frameLayout != null) {
                frameLayout.setVisibility(4);
            }
            QuickPanelBlur quickPanelBlur = secBrightnessMirrorController.quickPanelBlur;
            if (quickPanelBlur != null && (secQpBlurController = (SecQpBlurController) quickPanelBlur.blurController$delegate.getValue()) != null) {
                secQpBlurController.setBrightnessMirrorVisible(false);
            }
            BrightnessSliderController brightnessSliderController = secBrightnessMirrorController.toggleSliderController;
            if (brightnessSliderController != null && (secBrightnessSliderController2 = brightnessSliderController.mSecBrightnessSliderController) != null) {
                QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = secBrightnessMirrorController.quickTileBrightnessMirrorDummyView;
                secBrightnessSliderController2.isExpanded = quickTileBrightnessMirrorDummyView != null ? quickTileBrightnessMirrorDummyView.expanded : false;
            }
            if (brightnessSliderController == null || (secBrightnessSliderController = brightnessSliderController.mSecBrightnessSliderController) == null) {
                return;
            }
            ValueAnimator valueAnimator = secBrightnessSliderController.thumbAnimator;
            secBrightnessSliderController.isThumbShowing = false;
            valueAnimator.reverse();
        }
    }

    public final void reinflate$1() {
        FrameLayout frameLayout = this.mBrightnessMirror;
        NotificationShadeWindowView notificationShadeWindowView = this.mStatusBarWindow;
        int iIndexOfChild = notificationShadeWindowView.indexOfChild(frameLayout);
        notificationShadeWindowView.removeView(this.mBrightnessMirror);
        this.mBrightnessMirror = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.sec_brightness_mirror_container, (ViewGroup) notificationShadeWindowView, false);
        this.mToggleSliderController = setMirrorLayout();
        notificationShadeWindowView.addView(this.mBrightnessMirror, iIndexOfChild);
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            secBrightnessMirrorController.reinflate(this.mBrightnessMirror, this.mToggleSliderController);
        }
        updateResources$1();
        for (int i = 0; i < this.mBrightnessMirrorListeners.size(); i++) {
            BrightnessBar.AnonymousClass2 anonymousClass2 = (BrightnessBar.AnonymousClass2) this.mBrightnessMirrorListeners.valueAt(i);
            anonymousClass2.getClass();
            int i2 = BrightnessBar.$r8$clinit;
            BrightnessBar.this.updateBrightnessMirror();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mBrightnessMirrorListeners.remove((BrightnessBar.AnonymousClass2) obj);
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void setLocationAndSize(View view) {
        int[] iArr = this.mInt2Cache;
        view.getLocationInWindow(iArr);
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            secBrightnessMirrorController.setLocationAndSize(view);
            return;
        }
        int i = iArr[0];
        int i2 = iArr[1] - this.mBrightnessMirrorBackgroundPadding;
        this.mBrightnessMirror.setTranslationX(0.0f);
        this.mBrightnessMirror.setTranslationY(0.0f);
        this.mBrightnessMirror.getLocationInWindow(iArr);
        int i3 = iArr[0];
        int i4 = iArr[1];
        this.mBrightnessMirror.setTranslationX((i - r3) - i3);
        this.mBrightnessMirror.setTranslationY(i2 - i4);
        int measuredWidth = (this.mBrightnessMirrorBackgroundPadding * 2) + view.getMeasuredWidth();
        if (measuredWidth != this.mLastBrightnessSliderWidth) {
            ViewGroup.LayoutParams layoutParams = this.mBrightnessMirror.getLayoutParams();
            layoutParams.width = measuredWidth;
            this.mBrightnessMirror.setLayoutParams(layoutParams);
        }
    }

    public final BrightnessSliderController setMirrorLayout() {
        BrightnessSliderController brightnessSliderControllerCreate = ((BrightnessSliderController.BrightnessSliderControllerFactory) this.mToggleSliderFactory).create(this.mBrightnessMirror.getContext(), this.mBrightnessMirror);
        brightnessSliderControllerCreate.init();
        this.mBrightnessMirror.addView(brightnessSliderControllerCreate.getRootView(), -1, -2);
        return brightnessSliderControllerCreate;
    }

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void showMirror() {
        this.mBrightnessMirror.setVisibility(0);
        this.mVisibilityCallback.accept(Boolean.TRUE);
        this.mNotificationPanel.setAlpha(0, true);
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        int iBlurRadiusOfRatio = (int) notificationShadeDepthController.blurUtils.blurRadiusOfRatio(1.0f);
        NotificationShadeDepthController.DepthAnimation depthAnimation = notificationShadeDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != iBlurRadiusOfRatio) {
            depthAnimation.pendingRadius = iBlurRadiusOfRatio;
            depthAnimation.springAnimation.animateToFinalPosition(iBlurRadiusOfRatio);
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            secBrightnessMirrorController.showMirror();
        }
    }

    public final void updateResources$1() {
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            secBrightnessMirrorController.updateLayout();
            return;
        }
        int dimensionPixelSize = this.mBrightnessMirror.getResources().getDimensionPixelSize(R.dimen.rounded_slider_background_padding);
        this.mBrightnessMirrorBackgroundPadding = dimensionPixelSize;
        this.mBrightnessMirror.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }
}
