package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiRepositoryImpl$wifiActivity$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WifiRepositoryImpl$wifiActivity$1$$ExternalSyntheticLambda0(WifiRepositoryImpl wifiRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiRepositoryImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.wifiManager.unregisterTrafficStateCallback((WifiRepositoryImpl$wifiActivity$1$callback$1) this.f$1);
                break;
            default:
                this.f$0.wifiManager.unregisterScanResultsCallback((WifiRepositoryImpl$wifiScanResults$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
