package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class WifiInteractorImpl$areNetworksAvailable$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ WifiInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiInteractorImpl$areNetworksAvailable$1(WifiInteractorImpl wifiInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = wifiInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiInteractorImpl$areNetworksAvailable$1 wifiInteractorImpl$areNetworksAvailable$1 = new WifiInteractorImpl$areNetworksAvailable$1(this.this$0, (Continuation) obj3);
        wifiInteractorImpl$areNetworksAvailable$1.L$0 = (WifiNetworkModel) obj;
        wifiInteractorImpl$areNetworksAvailable$1.L$1 = (List) obj2;
        return wifiInteractorImpl$areNetworksAvailable$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0047, code lost:
    
        if (r0 != null) goto L9;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r5.label
            if (r0 != 0) goto L4f
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel r6 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel) r6
            java.lang.Object r0 = r5.L$1
            java.util.List r0 = (java.util.List) r0
            boolean r1 = r0.isEmpty()
            r2 = 0
            if (r1 == 0) goto L19
            goto L4a
        L19:
            boolean r1 = r6 instanceof com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Active
            r3 = 1
            if (r1 != 0) goto L20
        L1e:
            r2 = r3
            goto L4a
        L20:
            com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl r5 = r5.this$0
            com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel$Active r6 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel.Active) r6
            r5.getClass()
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.Iterator r5 = r0.iterator()
        L2d:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L46
            java.lang.Object r0 = r5.next()
            r1 = r0
            com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiScanEntry r1 = (com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiScanEntry) r1
            java.lang.String r1 = r1.ssid
            java.lang.String r4 = r6.ssid
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r4)
            r1 = r1 ^ r3
            if (r1 == 0) goto L2d
            goto L47
        L46:
            r0 = 0
        L47:
            if (r0 == 0) goto L4a
            goto L1e
        L4a:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r2)
            return r5
        L4f:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl$areNetworksAvailable$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
