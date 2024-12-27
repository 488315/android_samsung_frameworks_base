package com.android.systemui.statusbar.policy;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.qs.bar.BrightnessBar;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.MirrorController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessMirrorController implements MirrorController {
    public FrameLayout mBrightnessMirror;
    public int mBrightnessMirrorBackgroundPadding;
    public final NotificationShadeDepthController mDepthController;
    public final ShadeViewController mNotificationPanel;
    public final SecBrightnessMirrorController mSecBrightnessMirrorController;
    public final NotificationShadeWindowView mStatusBarWindow;
    public BrightnessSliderController mToggleSliderController;
    public final BrightnessSliderController.Factory mToggleSliderFactory;
    public final Consumer mVisibilityCallback;
    public final ArraySet mBrightnessMirrorListeners = new ArraySet();
    public final int[] mInt2Cache = new int[2];
    public final int mLastBrightnessSliderWidth = -1;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0] */
    public BrightnessMirrorController(NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, NotificationShadeDepthController notificationShadeDepthController, BrightnessSliderController.Factory factory, Consumer<Boolean> consumer) {
        this.mStatusBarWindow = notificationShadeWindowView;
        this.mToggleSliderFactory = factory;
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.sec_brightness_mirror_container, (ViewGroup) notificationShadeWindowView, false);
        this.mBrightnessMirror = frameLayout;
        BrightnessSliderController create = factory.create(frameLayout.getContext(), this.mBrightnessMirror);
        create.init();
        this.mBrightnessMirror.addView(create.getRootView(), -1, -2);
        this.mToggleSliderController = create;
        this.mNotificationPanel = shadeViewController;
        this.mDepthController = notificationShadeDepthController;
        shadeViewController.setAlphaChangeAnimationEndAction(new Runnable() { // from class: com.android.systemui.statusbar.policy.BrightnessMirrorController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessMirrorController.this.mBrightnessMirror.setVisibility(4);
            }
        });
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
        this.mVisibilityCallback.accept(Boolean.FALSE);
        this.mNotificationPanel.setAlpha(255, true);
        NotificationShadeDepthController.DepthAnimation depthAnimation = this.mDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != 0) {
            depthAnimation.pendingRadius = 0;
            depthAnimation.springAnimation.animateToFinalPosition(0);
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            secBrightnessMirrorController.hideMirror();
        }
    }

    public final void reinflate$1() {
        FrameLayout frameLayout = this.mBrightnessMirror;
        NotificationShadeWindowView notificationShadeWindowView = this.mStatusBarWindow;
        int indexOfChild = notificationShadeWindowView.indexOfChild(frameLayout);
        notificationShadeWindowView.removeView(this.mBrightnessMirror);
        FrameLayout frameLayout2 = (FrameLayout) LayoutInflater.from(notificationShadeWindowView.getContext()).inflate(R.layout.sec_brightness_mirror_container, (ViewGroup) notificationShadeWindowView, false);
        this.mBrightnessMirror = frameLayout2;
        BrightnessSliderController create = this.mToggleSliderFactory.create(frameLayout2.getContext(), this.mBrightnessMirror);
        create.init();
        this.mBrightnessMirror.addView(create.getRootView(), -1, -2);
        this.mToggleSliderController = create;
        notificationShadeWindowView.addView(this.mBrightnessMirror, indexOfChild);
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

    @Override // com.android.systemui.settings.brightness.MirrorController
    public final void showMirror() {
        this.mBrightnessMirror.setVisibility(0);
        this.mVisibilityCallback.accept(Boolean.TRUE);
        this.mNotificationPanel.setAlpha(0, true);
        NotificationShadeDepthController notificationShadeDepthController = this.mDepthController;
        int blurRadiusOfRatio = (int) notificationShadeDepthController.blurUtils.blurRadiusOfRatio(1.0f);
        NotificationShadeDepthController.DepthAnimation depthAnimation = notificationShadeDepthController.brightnessMirrorSpring;
        if (depthAnimation.pendingRadius != blurRadiusOfRatio) {
            depthAnimation.pendingRadius = blurRadiusOfRatio;
            depthAnimation.springAnimation.animateToFinalPosition(blurRadiusOfRatio);
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
