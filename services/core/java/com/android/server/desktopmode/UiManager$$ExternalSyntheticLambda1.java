package com.android.server.desktopmode;


public final /* synthetic */ class UiManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ UiManager.InternalUiCallback f$3;

    public /* synthetic */ UiManager$$ExternalSyntheticLambda1(
            UiManager uiManager,
            int i,
            int i2,
            UiManager.InternalUiCallback internalUiCallback,
            int i3) {
        this.$r8$classId = i3;
        this.f$0 = uiManager;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = internalUiCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.handleShowOverlay(this.f$1, this.f$2, this.f$3);
                break;
            case 1:
                this.f$0.handleStartActivity(this.f$1, this.f$2, this.f$3);
                break;
            case 2:
                this.f$0.handleShowOverlay(this.f$1, this.f$2, this.f$3);
                break;
            case 3:
                this.f$0.handleShowDialog(this.f$1, this.f$2, this.f$3);
                break;
            case 4:
                this.f$0.handleShowDialog(this.f$1, this.f$2, this.f$3);
                break;
            case 5:
                this.f$0.handleShowDialog(this.f$1, this.f$2, this.f$3);
                break;
            default:
                this.f$0.handleStartActivity(this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
