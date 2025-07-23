package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class SecFaceAuthCallback$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ CharSequence f$1;

    public /* synthetic */ SecFaceAuthCallback$$ExternalSyntheticLambda1(int i, int i2, CharSequence charSequence) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = charSequence;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                CharSequence charSequence = this.f$1;
                int i2 = SecFaceAuthCallback.$r8$clinit;
                ((Consumer) obj).accept(SecFaceMsg.obtain(0, i, charSequence, null));
                break;
            default:
                int i3 = this.f$0;
                CharSequence charSequence2 = this.f$1;
                int i4 = SecFaceAuthCallback.$r8$clinit;
                ((Consumer) obj).accept(SecFaceMsg.obtain(1, i3, charSequence2, null));
                break;
        }
    }
}
