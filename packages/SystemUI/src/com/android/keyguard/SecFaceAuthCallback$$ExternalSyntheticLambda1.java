package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                ((Consumer) obj).accept(SecFaceMsg.obtain(0, this.f$0, this.f$1, null));
                break;
            default:
                ((Consumer) obj).accept(SecFaceMsg.obtain(1, this.f$0, this.f$1, null));
                break;
        }
    }
}
