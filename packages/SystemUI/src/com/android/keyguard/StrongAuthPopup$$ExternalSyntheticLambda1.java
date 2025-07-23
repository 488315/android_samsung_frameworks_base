package com.android.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                int i2 = StrongAuthPopup.$r8$clinit;
                strongAuthPopup.show();
                break;
        }
    }
}
