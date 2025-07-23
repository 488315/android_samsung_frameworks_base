package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CastToOtherDeviceChipViewModel$internalChip$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CastToOtherDeviceChipViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastToOtherDeviceChipViewModel$internalChip$1(CastToOtherDeviceChipViewModel castToOtherDeviceChipViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = castToOtherDeviceChipViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CastToOtherDeviceChipViewModel$internalChip$1 castToOtherDeviceChipViewModel$internalChip$1 = new CastToOtherDeviceChipViewModel$internalChip$1(this.this$0, (Continuation) obj3);
        castToOtherDeviceChipViewModel$internalChip$1.L$0 = (OngoingActivityChipModel) obj;
        castToOtherDeviceChipViewModel$internalChip$1.L$1 = (OngoingActivityChipModel) obj2;
        return castToOtherDeviceChipViewModel$internalChip$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingActivityChipModel ongoingActivityChipModel = (OngoingActivityChipModel) this.L$0;
        OngoingActivityChipModel ongoingActivityChipModel2 = (OngoingActivityChipModel) this.L$1;
        LogBuffer logBuffer = this.this$0.logger;
        LogMessage obtain = logBuffer.obtain(CastToOtherDeviceChipViewModel.TAG, LogLevel.INFO, new CastToOtherDeviceChipViewModel$$ExternalSyntheticLambda0(2), null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = ongoingActivityChipModel.getLogName();
        logMessageImpl.str2 = ongoingActivityChipModel2.getLogName();
        logBuffer.commit(obtain);
        return ongoingActivityChipModel instanceof OngoingActivityChipModel.Active ? ongoingActivityChipModel : ongoingActivityChipModel2;
    }
}
