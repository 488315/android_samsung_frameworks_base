package com.android.systemui.statusbar.pipeline.wifi.domain.interactor;

import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class WifiInteractorImpl$wifiNetwork$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public WifiInteractorImpl$wifiNetwork$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj2).intValue();
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        WifiInteractorImpl$wifiNetwork$1 wifiInteractorImpl$wifiNetwork$1 = new WifiInteractorImpl$wifiNetwork$1((Continuation) obj4);
        wifiInteractorImpl$wifiNetwork$1.L$0 = (WifiNetworkModel) obj;
        wifiInteractorImpl$wifiNetwork$1.I$0 = intValue;
        wifiInteractorImpl$wifiNetwork$1.Z$0 = booleanValue;
        return wifiInteractorImpl$wifiNetwork$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$0;
        int i = this.I$0;
        boolean z = this.Z$0;
        if (wifiNetworkModel instanceof WifiNetworkModel.Active) {
            WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
            if (active.receivedInetCondition != i) {
                active.receivedInetCondition = i;
            }
            if (z) {
                active.isValidated = z;
            }
        }
        return wifiNetworkModel;
    }
}
