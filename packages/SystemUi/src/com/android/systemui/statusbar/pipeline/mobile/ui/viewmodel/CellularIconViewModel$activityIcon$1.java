package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$activityIcon$1", m277f = "MobileIconViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CellularIconViewModel$activityIcon$1 extends SuspendLambda implements Function5 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;

    public CellularIconViewModel$activityIcon$1(Continuation<? super CellularIconViewModel$activityIcon$1> continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        int intValue = ((Number) obj4).intValue();
        CellularIconViewModel$activityIcon$1 cellularIconViewModel$activityIcon$1 = new CellularIconViewModel$activityIcon$1((Continuation) obj5);
        cellularIconViewModel$activityIcon$1.L$0 = (DataActivityModel) obj;
        cellularIconViewModel$activityIcon$1.L$1 = (NetworkTypeIconModel) obj2;
        cellularIconViewModel$activityIcon$1.Z$0 = booleanValue;
        cellularIconViewModel$activityIcon$1.I$0 = intValue;
        return cellularIconViewModel$activityIcon$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DataActivityModel dataActivityModel = (DataActivityModel) this.L$0;
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) this.L$1;
        boolean z = this.Z$0;
        int i = this.I$0;
        if (z && dataActivityModel != null && networkTypeIconModel.getActivityIcons() != null) {
            boolean z2 = dataActivityModel.hasActivityOut;
            if (dataActivityModel.hasActivityIn) {
                if (z2) {
                    int[] activityIcons = networkTypeIconModel.getActivityIcons();
                    Intrinsics.checkNotNull(activityIcons);
                    i = activityIcons[3];
                } else {
                    int[] activityIcons2 = networkTypeIconModel.getActivityIcons();
                    Intrinsics.checkNotNull(activityIcons2);
                    i = activityIcons2[1];
                }
            } else if (z2) {
                int[] activityIcons3 = networkTypeIconModel.getActivityIcons();
                Intrinsics.checkNotNull(activityIcons3);
                i = activityIcons3[2];
            } else {
                int[] activityIcons4 = networkTypeIconModel.getActivityIcons();
                Intrinsics.checkNotNull(activityIcons4);
                i = activityIcons4[0];
            }
        } else if (i == 0) {
            i = 0;
        }
        return new Icon.Resource(i, null);
    }
}
