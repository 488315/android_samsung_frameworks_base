package com.android.server.desktopmode;


public final /* synthetic */ class UiManager$3$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager.InternalUiCallback f$0;

    public /* synthetic */ UiManager$3$$ExternalSyntheticLambda0(
            UiManager.InternalUiCallback internalUiCallback, int i) {
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
