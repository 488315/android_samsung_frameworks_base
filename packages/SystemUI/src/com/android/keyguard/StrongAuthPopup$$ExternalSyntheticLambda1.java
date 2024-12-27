package com.android.keyguard;

public final /* synthetic */ class StrongAuthPopup$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StrongAuthPopup f$0;

    public /* synthetic */ StrongAuthPopup$$ExternalSyntheticLambda1(StrongAuthPopup strongAuthPopup, int i) {
        this.$r8$classId = i;
        this.f$0 = strongAuthPopup;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StrongAuthPopup strongAuthPopup = this.f$0;
        switch (i) {
            case 0:
                strongAuthPopup.updatePopup();
                break;
            default:
                strongAuthPopup.show();
                break;
        }
    }
}
