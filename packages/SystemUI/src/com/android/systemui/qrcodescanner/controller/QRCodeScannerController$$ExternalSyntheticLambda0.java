package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QRCodeScannerController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QRCodeScannerController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((QRCodeScannerController) obj).updateQRCodeScannerActivityDetails();
                break;
            case 1:
                ((QRCodeScannerController) obj).updateQRCodeScannerPreferenceDetails(true);
                break;
            case 2:
                ((QRCodeScannerController) obj).updateQRCodeScannerActivityDetails();
                break;
            default:
                ((QRCodeScannerController.AnonymousClass1) obj).this$0.updateQRCodeScannerPreferenceDetails(false);
                break;
        }
    }
}
