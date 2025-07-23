package com.android.keyguard;

import com.android.keyguard.StrongAuthPopup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class StrongAuthPopup$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StrongAuthPopup$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((StrongAuthPopup.AnonymousClass1) obj).this$0.dismiss();
                break;
            default:
                ((StrongAuthPopup.AnonymousClass2) obj).this$0.dismiss();
                break;
        }
    }
}
