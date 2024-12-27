package com.android.systemui.keyguard;

import java.util.function.LongConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardViewMediatorHelperImpl$endHandleMsgTime$1 implements LongConsumer {
    public final /* synthetic */ int $what;
    public final /* synthetic */ KeyguardViewMediatorHelperImpl this$0;

    public KeyguardViewMediatorHelperImpl$endHandleMsgTime$1(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, int i) {
        this.this$0 = keyguardViewMediatorHelperImpl;
        this.$what = i;
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        if (j >= 30) {
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.this$0;
            String str = "handleMessage " + this.$what + " / elapsed time: " + j + "ms";
            keyguardViewMediatorHelperImpl.getClass();
            KeyguardViewMediatorHelperImpl.logD(str);
        }
    }
}
