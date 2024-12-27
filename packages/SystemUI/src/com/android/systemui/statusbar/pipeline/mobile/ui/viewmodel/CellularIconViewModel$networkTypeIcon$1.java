package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.DisabledDataIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.IconLocation;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CellularIconViewModel$networkTypeIcon$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public CellularIconViewModel$networkTypeIcon$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        CellularIconViewModel$networkTypeIcon$1 cellularIconViewModel$networkTypeIcon$1 = new CellularIconViewModel$networkTypeIcon$1((Continuation) obj4);
        cellularIconViewModel$networkTypeIcon$1.L$0 = (NetworkTypeIconModel) obj;
        cellularIconViewModel$networkTypeIcon$1.Z$0 = booleanValue;
        cellularIconViewModel$networkTypeIcon$1.L$1 = (DisabledDataIconModel) obj3;
        return cellularIconViewModel$networkTypeIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) this.L$0;
        boolean z = this.Z$0;
        DisabledDataIconModel disabledDataIconModel = (DisabledDataIconModel) this.L$1;
        Icon.Resource resource = networkTypeIconModel.getIconId() != 0 ? new Icon.Resource(networkTypeIconModel.getIconId(), networkTypeIconModel.getContentDescription() != 0 ? new ContentDescription.Resource(networkTypeIconModel.getContentDescription()) : null) : null;
        boolean equals = disabledDataIconModel.iconLocation.equals(IconLocation.DATA_ICON);
        int i = disabledDataIconModel.iconId;
        boolean z2 = equals && i != 0;
        Icon.Resource resource2 = new Icon.Resource(i, null);
        if (z) {
            return resource;
        }
        if (z2) {
            return resource2;
        }
        return null;
    }
}
