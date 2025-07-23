package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
