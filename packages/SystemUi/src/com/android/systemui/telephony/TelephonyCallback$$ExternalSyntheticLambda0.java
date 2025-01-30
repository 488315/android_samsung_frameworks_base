package com.android.systemui.telephony;

import android.telephony.TelephonyCallback;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TelephonyCallback$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ TelephonyCallback$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((TelephonyCallback.ActiveDataSubscriptionIdListener) obj).onActiveDataSubscriptionIdChanged(this.f$0);
                break;
            default:
                ((TelephonyCallback.CallStateListener) obj).onCallStateChanged(this.f$0);
                break;
        }
    }
}
