package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileConnectionsRepositoryKairosImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$$ExternalSyntheticLambda0(MobileConnectionsRepositoryKairosImpl mobileConnectionsRepositoryKairosImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileConnectionsRepositoryKairosImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.keyguardUpdateMonitor.removeCallback((MobileConnectionsRepositoryKairosImpl$isAnySimSecure$1$1$callback$1) this.f$1);
                break;
            case 1:
                this.f$0.subscriptionManager.removeOnSubscriptionsChangedListener((MobileConnectionsRepositoryKairosImpl$mobileSubscriptionsChangeEvent$1$1$callback$1) this.f$1);
                break;
            default:
                this.f$0.telephonyManager.unregisterTelephonyCallback((MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
