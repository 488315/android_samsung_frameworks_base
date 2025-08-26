package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileConnectionsRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, MobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1 mobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1) {
        this.$r8$classId = 2;
        this.f$0 = mobileConnectionsRepositoryImpl;
        this.f$1 = mobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.keyguardUpdateMonitor.removeCallback((MobileConnectionsRepositoryImpl$isAnySimSecure$1$callback$1) this.f$1);
                break;
            case 1:
                this.f$0.telephonyManager.unregisterTelephonyCallback((MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1) this.f$1);
                break;
            case 2:
                this.f$0.telephonyManager.unregisterTelephonyCallback((MobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1) this.f$1);
                break;
            default:
                this.f$0.subscriptionManager.removeOnSubscriptionsChangedListener((MobileConnectionsRepositoryImpl$mobileSubscriptionsChangeEvent$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ MobileConnectionsRepositoryImpl$isAnySimSecure$1$$ExternalSyntheticLambda0(MobileConnectionsRepositoryImpl mobileConnectionsRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileConnectionsRepositoryImpl;
        this.f$1 = obj;
    }
}
