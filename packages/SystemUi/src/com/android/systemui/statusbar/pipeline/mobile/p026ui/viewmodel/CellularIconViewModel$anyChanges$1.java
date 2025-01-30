package com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel;

import android.telephony.TelephonyDisplayInfo;
import com.android.systemui.statusbar.pipeline.mobile.data.model.MobileServiceState;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.NetworkTypeIconModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.CellularIconViewModel$anyChanges$1", m277f = "MobileIconViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class CellularIconViewModel$anyChanges$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ CellularIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CellularIconViewModel$anyChanges$1(CellularIconViewModel cellularIconViewModel, Continuation<? super CellularIconViewModel$anyChanges$1> continuation) {
        super(5, continuation);
        this.this$0 = cellularIconViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        CellularIconViewModel$anyChanges$1 cellularIconViewModel$anyChanges$1 = new CellularIconViewModel$anyChanges$1(this.this$0, (Continuation) obj5);
        cellularIconViewModel$anyChanges$1.Z$0 = booleanValue;
        cellularIconViewModel$anyChanges$1.Z$1 = booleanValue2;
        cellularIconViewModel$anyChanges$1.L$0 = (NetworkTypeIconModel) obj3;
        cellularIconViewModel$anyChanges$1.L$1 = (MobileServiceState) obj4;
        return cellularIconViewModel$anyChanges$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        NetworkTypeIconModel networkTypeIconModel = (NetworkTypeIconModel) this.L$0;
        MobileServiceState mobileServiceState = (MobileServiceState) this.L$1;
        MobileSimpleLogger mobileSimpleLogger = this.this$0.simpleLogger;
        mobileSimpleLogger.connected = z;
        mobileSimpleLogger.dataConnected = z2;
        mobileSimpleLogger.dataType = networkTypeIconModel;
        mobileSimpleLogger.networkType = mobileServiceState.telephonyDisplayInfo.getNetworkType();
        TelephonyDisplayInfo telephonyDisplayInfo = mobileServiceState.telephonyDisplayInfo;
        mobileSimpleLogger.overrideNetworkType = telephonyDisplayInfo.getOverrideNetworkType();
        mobileSimpleLogger.fivegavailable = telephonyDisplayInfo.is5gAvailable();
        mobileSimpleLogger.simCard = mobileServiceState.simCardInfo.simType.toString();
        return this.this$0.simpleLogger;
    }
}
