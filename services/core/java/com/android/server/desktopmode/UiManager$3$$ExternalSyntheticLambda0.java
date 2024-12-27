package com.android.server.desktopmode;

import com.android.server.desktopmode.UiManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UiManager$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager.InternalUiCallback f$0;

    public /* synthetic */ UiManager$3$$ExternalSyntheticLambda0(UiManager.InternalUiCallback internalUiCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = internalUiCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UiManager.InternalUiCallback internalUiCallback = this.f$0;
        switch (i) {
            case 0:
                internalUiCallback.onClickButtonPositive();
                break;
            case 1:
                internalUiCallback.getClass();
                break;
            case 2:
                internalUiCallback.onShow();
                break;
            default:
                internalUiCallback.onDismiss();
                break;
        }
    }
}
