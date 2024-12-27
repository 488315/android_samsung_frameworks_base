package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class QRCodeScannerController$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        QRCodeScannerController.Callback callback = (QRCodeScannerController.Callback) obj;
        switch (this.$r8$classId) {
            case 0:
                callback.onQRCodeScannerActivityChanged();
                break;
            default:
                callback.onQRCodeScannerPreferenceChanged();
                break;
        }
    }
}
