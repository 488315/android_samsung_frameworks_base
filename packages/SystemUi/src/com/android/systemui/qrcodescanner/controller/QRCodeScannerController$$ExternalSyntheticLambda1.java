package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QRCodeScannerController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QRCodeScannerController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((QRCodeScannerController) this.f$0).updateQRCodeScannerActivityDetails();
                break;
            case 1:
                ((QRCodeScannerController) this.f$0).updateQRCodeScannerPreferenceDetails(true);
                break;
            default:
                ((QRCodeScannerController.C20231) this.f$0).this$0.updateQRCodeScannerPreferenceDetails(false);
                break;
        }
    }
}
