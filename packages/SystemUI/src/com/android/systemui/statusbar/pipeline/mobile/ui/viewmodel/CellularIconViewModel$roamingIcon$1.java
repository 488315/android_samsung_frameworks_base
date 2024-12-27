package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.BasicRune;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.IconLocation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class CellularIconViewModel$roamingIcon$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public CellularIconViewModel$roamingIcon$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        CellularIconViewModel$roamingIcon$1 cellularIconViewModel$roamingIcon$1 = new CellularIconViewModel$roamingIcon$1((Continuation) obj5);
        cellularIconViewModel$roamingIcon$1.Z$0 = booleanValue;
        cellularIconViewModel$roamingIcon$1.I$0 = intValue;
        cellularIconViewModel$roamingIcon$1.L$0 = (DisabledDataIconModel) obj3;
        cellularIconViewModel$roamingIcon$1.Z$1 = booleanValue2;
        return cellularIconViewModel$roamingIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Icon.Resource resource;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.I$0;
        DisabledDataIconModel disabledDataIconModel = (DisabledDataIconModel) this.L$0;
        boolean z2 = this.Z$1;
        if (z && (!BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL || !z2)) {
            resource = new Icon.Resource(i, null);
        } else {
            if (!disabledDataIconModel.iconLocation.equals(IconLocation.ROAMING_ICON)) {
                return null;
            }
            resource = new Icon.Resource(disabledDataIconModel.iconId, null);
        }
        return resource;
    }
}
