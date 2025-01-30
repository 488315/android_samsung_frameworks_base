package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class QrCodeScannerKeyguardQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context context;
    public final QRCodeScannerController controller;
    public final Flow lockScreenState;
    public final String key = "qr_code_scanner";
    public final int pickerIconResourceId = R.drawable.ic_qr_code_scanner;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QrCodeScannerKeyguardQuickAffordanceConfig(Context context, QRCodeScannerController qRCodeScannerController) {
        this.context = context;
        this.controller = qRCodeScannerController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 = new QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        this.lockScreenState = ConflatedCallbackFlow.conflatedCallbackFlow(qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1);
    }

    public static final KeyguardQuickAffordanceConfig.LockScreenState access$state(QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig) {
        QRCodeScannerController qRCodeScannerController = qrCodeScannerKeyguardQuickAffordanceConfig.controller;
        return qRCodeScannerController.mQRCodeScannerEnabled && qRCodeScannerController.isAbleToOpenCameraApp() && qRCodeScannerController.mConfigEnableLockScreenButton ? new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.ic_qr_code_scanner, new ContentDescription.Resource(R.string.accessibility_qr_code_scanner_button)), null, 2, null) : KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        QRCodeScannerController qRCodeScannerController = this.controller;
        return !qRCodeScannerController.mConfigEnableLockScreenButton ? KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE : !qRCodeScannerController.isAbleToOpenCameraApp() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(this.context.getString(R.string.qr_scanner_quick_affordance_unavailable_explanation), null, null, 6, null) : new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity(this.controller.mIntent, true);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.qr_code_scanner_title);
    }
}
