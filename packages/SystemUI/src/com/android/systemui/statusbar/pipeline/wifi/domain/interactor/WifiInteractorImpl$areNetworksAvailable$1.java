package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiScanEntry;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:9:0x001e  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Object next;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$0;
        List list = (List) this.L$1;
        boolean z = false;
        if (!list.isEmpty()) {
            if (wifiNetworkModel instanceof WifiNetworkModel.Active) {
                WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
                this.this$0.getClass();
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (!Intrinsics.areEqual(((WifiScanEntry) next).ssid, active.ssid)) {
                        break;
                    }
                }
                if (next != null) {
                }
            } else {
                z = true;
            }
        }
        return Boolean.valueOf(z);
    }
}
