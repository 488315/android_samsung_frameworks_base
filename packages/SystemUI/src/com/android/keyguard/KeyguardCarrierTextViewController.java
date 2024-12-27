package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.IndicatorBasicGardener;
import com.android.systemui.statusbar.phone.IndicatorGarden;
import com.android.systemui.statusbar.phone.IndicatorGardenContainer;
import com.android.systemui.statusbar.phone.IndicatorGardenInputProperties;
import com.android.systemui.statusbar.phone.IndicatorGardenModel;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter$onGardenOnLayout$1;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;

public final class KeyguardCarrierTextViewController extends ViewController implements SystemUIWidgetCallback, IndicatorGarden {
    public ViewGroup mBouncerStatusBarAreaRootView;
    public ViewGroup mBouncerStatusBarAreaView;
    public final CarrierTextController mCarrierTextController;
    public int mEssentialLeftWidth;
    public IndicatorGardenContainer mGardenLeftContainer;
    public IndicatorGardenContainer mGardenRightContainer;
    public final AnonymousClass2 mGardener;
    public final IndicatorGardenPresenter mIndicatorGardenPresenter;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;

    public static WindowInsets $r8$lambda$ALKzW_bxVWrgAYAViyzvOhBChas(KeyguardCarrierTextViewController keyguardCarrierTextViewController, WindowInsets windowInsets) {
        keyguardCarrierTextViewController.mIndicatorGardenPresenter.onGardenApplyWindowInsets(keyguardCarrierTextViewController);
        return ((CarrierText) ((KeyguardCarrierTextView) keyguardCarrierTextViewController.mView).findViewById(R.id.bouncer_carrier_text)).onApplyWindowInsets(windowInsets);
    }

    public KeyguardCarrierTextViewController(KeyguardCarrierTextView keyguardCarrierTextView, KeyguardUpdateMonitor keyguardUpdateMonitor, CarrierTextController carrierTextController, IndicatorGardenPresenter indicatorGardenPresenter) {
        super(keyguardCarrierTextView);
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardCarrierTextViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
                ((KeyguardCarrierTextView) ((ViewController) KeyguardCarrierTextViewController.this).mView).updateVisibility();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                ((KeyguardCarrierTextView) ((ViewController) KeyguardCarrierTextViewController.this).mView).updateVisibility();
            }
        };
        this.mGardener = new IndicatorBasicGardener(this, KeyguardCarrierTextViewController.class.getName()) { // from class: com.android.keyguard.KeyguardCarrierTextViewController.2
            @Override // com.android.systemui.statusbar.phone.IndicatorBasicGardener
            public final ViewGroup.MarginLayoutParams getCameraTopMarginContainerMarginLayoutParams() {
                return (ViewGroup.MarginLayoutParams) KeyguardCarrierTextViewController.this.getSidePaddingContainer().getLayoutParams();
            }
        };
        this.mEssentialLeftWidth = -1;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mCarrierTextController = carrierTextController;
        this.mIndicatorGardenPresenter = indicatorGardenPresenter;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getCenterContainer() {
        return null;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialLeftWidth() {
        if (this.mEssentialLeftWidth < 0) {
            this.mEssentialLeftWidth = getResources().getDimensionPixelSize(R.dimen.carrier_label_portrait_max_width);
        }
        return this.mEssentialLeftWidth;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final int getEssentialRightWidth() {
        return 0;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final WindowInsets getGardenWindowInsets() {
        return ((KeyguardCarrierTextView) this.mView).getRootWindowInsets();
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getHeightContainer() {
        if (this.mBouncerStatusBarAreaRootView == null) {
            this.mBouncerStatusBarAreaRootView = (ViewGroup) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_status_bar_area_root);
        }
        return this.mBouncerStatusBarAreaRootView;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getLeftContainer() {
        if (this.mGardenLeftContainer == null) {
            this.mGardenLeftContainer = (IndicatorGardenContainer) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_left_side_container);
        }
        return this.mGardenLeftContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final IndicatorGardenContainer getRightContainer() {
        if (this.mGardenRightContainer == null) {
            this.mGardenRightContainer = (IndicatorGardenContainer) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_right_side_container);
        }
        return this.mGardenRightContainer;
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final ViewGroup getSidePaddingContainer() {
        if (this.mBouncerStatusBarAreaView == null) {
            this.mBouncerStatusBarAreaView = (ViewGroup) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_status_bar_area);
        }
        return this.mBouncerStatusBarAreaView;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mCarrierTextController.init();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((CarrierText) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_carrier_text)).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.keyguard.KeyguardCarrierTextViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return KeyguardCarrierTextViewController.$r8$lambda$ALKzW_bxVWrgAYAViyzvOhBChas(KeyguardCarrierTextViewController.this, windowInsets);
            }
        });
        this.mIndicatorGardenPresenter.updateGardenWithNewModel(this);
        ((KeyguardCarrierTextView) this.mView).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardCarrierTextViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                KeyguardCarrierTextViewController keyguardCarrierTextViewController = KeyguardCarrierTextViewController.this;
                if (keyguardCarrierTextViewController.mKeyguardUpdateMonitor.isBouncerFullyShown()) {
                    IndicatorGardenPresenter indicatorGardenPresenter = keyguardCarrierTextViewController.mIndicatorGardenPresenter;
                    indicatorGardenPresenter.getClass();
                    indicatorGardenPresenter.mainHandler.post(new IndicatorGardenPresenter$onGardenOnLayout$1(indicatorGardenPresenter, keyguardCarrierTextViewController));
                }
            }
        });
        WallpaperUtils.registerSystemUIWidgetCallback(this, 512L);
        ((KeyguardCarrierTextView) this.mView).updateVisibility();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        WallpaperUtils.removeSystemUIWidgetCallback(this);
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGarden
    public final void updateGarden(IndicatorGardenModel indicatorGardenModel, IndicatorGardenInputProperties indicatorGardenInputProperties) {
        updateGarden(indicatorGardenModel, indicatorGardenInputProperties);
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        ((CarrierText) ((KeyguardCarrierTextView) this.mView).findViewById(R.id.bouncer_carrier_text)).setTextColor(getResources().getColor(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.color.whitebg_keyguard_text_color : R.color.origin_bouncer_carrier_text_color, null));
    }
}
