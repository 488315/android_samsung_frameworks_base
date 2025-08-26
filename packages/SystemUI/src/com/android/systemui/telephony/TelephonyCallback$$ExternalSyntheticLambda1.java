package com.android.systemui.telephony;

import android.telephony.TelephonyCallback;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class TelephonyCallback$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ TelephonyCallback$$ExternalSyntheticLambda1(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                int i3 = TelephonyCallback.$r8$clinit;
                ((TelephonyCallback.CallStateListener) obj).onCallStateChanged(i2);
                break;
            default:
                int i4 = TelephonyCallback.$r8$clinit;
                ((TelephonyCallback.ActiveDataSubscriptionIdListener) obj).onActiveDataSubscriptionIdChanged(i2);
                break;
        }
    }
}
