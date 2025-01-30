package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecFaceAuthCallback$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ CharSequence f$1;

    public /* synthetic */ SecFaceAuthCallback$$ExternalSyntheticLambda0(int i, int i2, CharSequence charSequence) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = charSequence;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Consumer) obj).accept(SecFaceMsg.obtain(0, this.f$0, this.f$1, null));
                break;
            default:
                ((Consumer) obj).accept(SecFaceMsg.obtain(1, this.f$0, this.f$1, null));
                break;
        }
    }
}
