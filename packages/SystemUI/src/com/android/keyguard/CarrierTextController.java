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
import java.io.PrintWriter;

public final class CarrierTextController extends ViewController {
    public final AnonymousClass1 mCarrierTextCallback;
    public final CarrierTextManager mCarrierTextManager;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mLastScaleEvent;

    public CarrierTextController(CarrierText carrierText, CarrierTextManager.Builder builder, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        super(carrierText);
        this.mCarrierTextCallback = new CarrierTextManager.CarrierTextCallback() { // from class: com.android.keyguard.CarrierTextController.1
            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void finishedWakingUp() {
                CarrierTextController carrierTextController = CarrierTextController.this;
                ((CarrierText) ((ViewController) carrierTextController).mView).setVisibility(0);
                ((CarrierText) ((ViewController) carrierTextController).mView).setSelected(true);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void startedGoingToSleep() {
                CarrierTextController carrierTextController = CarrierTextController.this;
                ((CarrierText) ((ViewController) carrierTextController).mView).setVisibility(4);
                ((CarrierText) ((ViewController) carrierTextController).mView).setSelected(false);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public final void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
                boolean z = LsRune.SECURITY_BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY;
                CarrierTextController carrierTextController = CarrierTextController.this;
                if (z && !carrierTextCallbackInfo.anySimReady && !carrierTextCallbackInfo.airplaneMode && !"Shade".equals(carrierTextCallbackInfo.location)) {
                    Log.d("CarrierTextController", "BLOCK_CARRIER_TEXT_WHEN_SIM_NOT_READY");
                    ((CarrierText) ((ViewController) carrierTextController).mView).setText("");
                    return;
                }
                CharSequence charSequence = carrierTextCallbackInfo.carrierText;
                if (charSequence == null || !charSequence.toString().contains("&")) {
                    ((CarrierText) ((ViewController) carrierTextController).mView).setTextDirection(5);
                } else {
                    ((CarrierText) ((ViewController) carrierTextController).mView).setTextDirection(6);
                }
                ((CarrierText) ((ViewController) carrierTextController).mView).setText(carrierTextCallbackInfo.carrierText);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.CarrierTextController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                CarrierTextController carrierTextController = CarrierTextController.this;
                float f = carrierTextController.mIndicatorScaleGardener.getLatestScaleModel(carrierTextController.getContext()).ratio;
                float dimensionPixelSize = carrierTextController.getResources().getDimensionPixelSize(R.dimen.status_bar_clock_size) * f;
                ((CarrierText) ((ViewController) carrierTextController).mView).setTextSize(0, dimensionPixelSize);
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

    public final void dump(PrintWriter printWriter) {
        CarrierTextController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "CarrierTextController:", "    last: "), this.mLastScaleEvent, printWriter);
        CarrierText carrierText = (CarrierText) this.mView;
        carrierText.getClass();
        printWriter.println("CarrierText:");
        StringBuilder sb = new StringBuilder("    textSize: ");
        sb.append(carrierText.mFontSize);
        sb.append(" from: ");
        CarrierTextController$$ExternalSyntheticOutline0.m(sb, carrierText.mSetTextSizeCaller, printWriter);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        ((CarrierText) this.mView).setSelected(this.mKeyguardUpdateMonitor.mDeviceInteractive);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, this.mCarrierTextCallback));
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        CarrierTextManager carrierTextManager = this.mCarrierTextManager;
        carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, null));
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }
}
