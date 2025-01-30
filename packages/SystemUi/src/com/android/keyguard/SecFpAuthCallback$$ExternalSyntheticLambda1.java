package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecFpAuthCallback$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecFpAuthCallback f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ CharSequence f$2;

    public /* synthetic */ SecFpAuthCallback$$ExternalSyntheticLambda1(SecFpAuthCallback secFpAuthCallback, int i, CharSequence charSequence, int i2) {
        this.$r8$classId = i2;
        this.f$0 = secFpAuthCallback;
        this.f$1 = i;
        this.f$2 = charSequence;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecFpAuthCallback secFpAuthCallback = this.f$0;
                ((Consumer) obj).accept(SecFpMsg.obtain(0, secFpAuthCallback.mCallbackSeq, this.f$1, this.f$2, null));
                break;
            default:
                SecFpAuthCallback secFpAuthCallback2 = this.f$0;
                ((Consumer) obj).accept(SecFpMsg.obtain(1, secFpAuthCallback2.mCallbackSeq, this.f$1, this.f$2, null));
                break;
        }
    }
}
