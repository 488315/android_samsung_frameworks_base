package com.android.server.autofill.ui;

import com.android.server.autofill.ui.AutoFillUI;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AutoFillUI$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AutoFillUI f$0;
    public final /* synthetic */ AutoFillUI.AutoFillUiCallback f$1;

    public /* synthetic */ AutoFillUI$$ExternalSyntheticLambda1(AutoFillUI autoFillUI, AutoFillUI.AutoFillUiCallback autoFillUiCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = autoFillUI;
        this.f$1 = autoFillUiCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AutoFillUI autoFillUI = this.f$0;
                AutoFillUI.AutoFillUiCallback autoFillUiCallback = this.f$1;
                AutoFillUI.AutoFillUiCallback autoFillUiCallback2 = autoFillUI.mCallback;
                if (autoFillUiCallback2 != autoFillUiCallback) {
                    if (autoFillUiCallback2 != null) {
                        SaveUi saveUi = autoFillUI.mSaveUi;
                        if (saveUi == null ? false : saveUi.mDialog.isShowing()) {
                            autoFillUI.hideFillUiUiThread(autoFillUiCallback, true);
                        } else {
                            autoFillUI.hideAllUiThread(autoFillUI.mCallback);
                        }
                    }
                    autoFillUI.mCallback = autoFillUiCallback;
                    break;
                }
                break;
            case 1:
                this.f$0.hideFillDialogUiThread(this.f$1);
                break;
            case 2:
                this.f$0.hideAllUiThread(this.f$1);
                break;
            case 3:
                this.f$0.hideFillUiUiThread(this.f$1, true);
                break;
            default:
                AutoFillUI autoFillUI2 = this.f$0;
                AutoFillUI.AutoFillUiCallback autoFillUiCallback3 = this.f$1;
                if (autoFillUI2.mCallback == autoFillUiCallback3) {
                    autoFillUI2.hideAllUiThread(autoFillUiCallback3);
                    autoFillUI2.mCallback = null;
                    break;
                }
                break;
        }
    }
}
