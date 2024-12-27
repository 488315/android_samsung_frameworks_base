package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
