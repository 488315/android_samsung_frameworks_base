package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.CarrierTextManager;
import com.android.systemui.BasicRune;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CarrierTextController extends ViewController {
    public final C06331 mCarrierTextCallback;
    public final CarrierTextManager mCarrierTextManager;
    public final ConfigurationController mConfigurationController;
    public final C06342 mConfigurationListener;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mLastScaleEvent;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.keyguard.CarrierTextController$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.CarrierTextController$2] */
    public CarrierTextController(CarrierText carrierText, CarrierTextManager.Builder builder, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        super(carrierText);
        this.mCarrierTextCallback = new CarrierTextManager.CarrierTextCallback() { // from class: com.android.keyguard.CarrierTextController.1
            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void finishedWakingUp() {
                ((CarrierText) CarrierTextController.this.mView).setSelected(true);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void startedGoingToSleep() {
                ((CarrierText) CarrierTextController.this.mView).setSelected(false);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
                boolean z = LsRune.SECURITY_BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY;
                CarrierTextController carrierTextController = CarrierTextController.this;
                if (z && !carrierTextCallbackInfo.anySimReady && !carrierTextCallbackInfo.airplaneMode && !"Shade".equals(carrierTextCallbackInfo.location)) {
                    Log.d("CarrierTextController", "BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY");
                    ((CarrierText) carrierTextController.mView).setText("");
                    return;
                }
                CharSequence charSequence = carrierTextCallbackInfo.carrierText;
                if (charSequence == null || !charSequence.toString().contains("&")) {
                    ((CarrierText) carrierTextController.mView).setTextDirection(5);
                } else {
                    ((CarrierText) carrierTextController.mView).setTextDirection(6);
                }
                ((CarrierText) carrierTextController.mView).setText(carrierTextCallbackInfo.carrierText);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.CarrierTextController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                CarrierTextController carrierTextController = CarrierTextController.this;
                float f = carrierTextController.mIndicatorScaleGardener.getLatestScaleModel(carrierTextController.getContext()).ratio;
                float dimensionPixelSize = carrierTextController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * f;
                ((CarrierText) carrierTextController.mView).setTextSize(0, dimensionPixelSize);
                carrierTextController.mLastScaleEvent = "ratio=" + f + " font size=" + dimensionPixelSize;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDisplayDeviceTypeChanged() {
                if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    onDensityOrFontScaleChanged();
                }
            }
        };
        CarrierText carrierText2 = (CarrierText) this.mView;
        builder.mShowAirplaneMode = carrierText2.mShowAirplaneMode;
        builder.mShowMissingSim = carrierText2.mShowMissingSim;
        builder.mDebugLocation = carrierText2.mDebugLocation;
        this.mCarrierTextManager = builder.build();
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mIndicatorScaleGardener = indicatorScaleGardener;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        ((CarrierText) this.mView).setSelected(this.mKeyguardUpdateMonitor.mDeviceInteractive);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        carrierTextManager.getClass();
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, this.mCarrierTextCallback));
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        carrierTextManager.getClass();
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, (CarrierTextManager.CarrierTextCallback) null));
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }
}
