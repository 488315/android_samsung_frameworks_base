package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import com.android.systemui.BasicRune;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.model.IconLocation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$roamingIcon$1", m277f = "MobileIconViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CellularIconViewModel$roamingIcon$1 extends SuspendLambda implements Function6 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public CellularIconViewModel$roamingIcon$1(Continuation<? super CellularIconViewModel$roamingIcon$1> continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        boolean booleanValue2 = ((Boolean) obj4).booleanValue();
        int intValue2 = ((Number) obj5).intValue();
        CellularIconViewModel$roamingIcon$1 cellularIconViewModel$roamingIcon$1 = new CellularIconViewModel$roamingIcon$1((Continuation) obj6);
        cellularIconViewModel$roamingIcon$1.Z$0 = booleanValue;
        cellularIconViewModel$roamingIcon$1.I$0 = intValue;
        cellularIconViewModel$roamingIcon$1.L$0 = (DisabledDataIconModel) obj3;
        cellularIconViewModel$roamingIcon$1.Z$1 = booleanValue2;
        cellularIconViewModel$roamingIcon$1.I$1 = intValue2;
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
        int i2 = this.I$1;
        if (z) {
            boolean z3 = true;
            if (BasicRune.STATUS_NETWORK_SIGNAL_LIMITED_WHILE_OTHER_SLOT_CALL && z2) {
                z3 = false;
            }
            if (z3) {
                resource = new Icon.Resource(i, null);
                return resource;
            }
        }
        if (i2 != 0) {
            return new Icon.Resource(i2, null);
        }
        if (!disabledDataIconModel.iconLocation.equals(IconLocation.ROAMING_ICON)) {
            return null;
        }
        resource = new Icon.Resource(disabledDataIconModel.iconId, null);
        return resource;
    }
}
