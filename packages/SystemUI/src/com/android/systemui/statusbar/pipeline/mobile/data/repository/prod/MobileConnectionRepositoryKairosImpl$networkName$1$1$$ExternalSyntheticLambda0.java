package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.net.ConnectivityManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileConnectionRepositoryKairosImpl$networkName$1$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((MobileConnectionRepositoryKairosImpl) this.f$0).context.unregisterReceiver((MobileConnectionRepositoryKairosImpl$networkName$1$1$receiver$1) this.f$1);
                break;
            case 1:
                ((MobileConnectionRepositoryKairosImpl) this.f$0).telephonyManager.unregisterTelephonyCallback((MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1) this.f$1);
                break;
            default:
                ((ConnectivityManager) this.f$0).unregisterNetworkCallback((MobileConnectionRepositoryKairosImpl$hasPrioritizedNetworkCapabilities$1$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
