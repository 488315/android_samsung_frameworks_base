package com.android.systemui.qrcodescanner.controller;

import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QRCodeScannerController$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((QRCodeScannerController.Callback) obj).onQRCodeScannerActivityChanged();
                break;
            default:
                ((QRCodeScannerController.Callback) obj).onQRCodeScannerPreferenceChanged();
                break;
        }
    }
}
