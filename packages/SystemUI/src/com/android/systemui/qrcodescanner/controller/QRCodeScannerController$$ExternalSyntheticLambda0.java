package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                ((QRCodeScannerController) obj).updateQRCodeScannerPreferenceDetails(true);
                break;
            case 1:
                ((QRCodeScannerController) obj).updateQRCodeScannerActivityDetails();
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
