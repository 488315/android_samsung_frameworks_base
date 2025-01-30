package com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel;

import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel$activityIcon$1", m277f = "WifiViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiViewModel$activityIcon$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    public WifiViewModel$activityIcon$1(Continuation<? super WifiViewModel$activityIcon$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        WifiViewModel$activityIcon$1 wifiViewModel$activityIcon$1 = new WifiViewModel$activityIcon$1((Continuation) obj4);
        wifiViewModel$activityIcon$1.L$0 = (DataActivityModel) obj;
        wifiViewModel$activityIcon$1.L$1 = (SignalIcon$IconGroup) obj2;
        wifiViewModel$activityIcon$1.L$2 = (WifiNetworkModel) obj3;
        return wifiViewModel$activityIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int[] iArr;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataActivityModel dataActivityModel = (DataActivityModel) this.L$0;
        SignalIcon$IconGroup signalIcon$IconGroup = (SignalIcon$IconGroup) this.L$1;
        WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) this.L$2;
        if (wifiNetworkModel instanceof WifiNetworkModel.Active) {
            WifiNetworkModel.Active active = (WifiNetworkModel.Active) wifiNetworkModel;
            if (((new Integer(active.receivedInetCondition).equals(new Integer(-1)) && active.isValidated) || new Integer(active.receivedInetCondition).equals(new Integer(1))) && signalIcon$IconGroup != null && (iArr = signalIcon$IconGroup.activityIcons) != null) {
                boolean z = dataActivityModel.hasActivityIn;
                boolean z2 = dataActivityModel.hasActivityOut;
                return new Icon.Resource((z && z2) ? iArr[3] : z ? iArr[1] : z2 ? iArr[2] : iArr[0], null);
            }
        }
        return null;
    }
}
