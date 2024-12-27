package com.android.server.autofill.ui;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AutoFillUI$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ AutoFillUI f$0;
    public final /* synthetic */ PendingUi f$1;
    public final /* synthetic */ AutoFillUI.AutoFillUiCallback f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ AutoFillUI$$ExternalSyntheticLambda3(
            AutoFillUI autoFillUI,
            PendingUi pendingUi,
            AutoFillUI.AutoFillUiCallback autoFillUiCallback,
            boolean z) {
        this.f$0 = autoFillUI;
        this.f$1 = pendingUi;
        this.f$2 = autoFillUiCallback;
        this.f$3 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AutoFillUI autoFillUI = this.f$0;
        PendingUi pendingUi = this.f$1;
        AutoFillUI.AutoFillUiCallback autoFillUiCallback = this.f$2;
        boolean z = this.f$3;
        autoFillUI.hideFillUiUiThread(autoFillUiCallback, z);
        autoFillUI.hideFillDialogUiThread(autoFillUiCallback);
        autoFillUI.destroySaveUiUiThread(pendingUi, z);
    }
}
