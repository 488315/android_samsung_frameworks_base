package com.android.systemui.statusbar.chips.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class OngoingActivityChipsViewModel$isScreenReasonablyLarge$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OngoingActivityChipsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingActivityChipsViewModel$isScreenReasonablyLarge$2(OngoingActivityChipsViewModel ongoingActivityChipsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ongoingActivityChipsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OngoingActivityChipsViewModel$isScreenReasonablyLarge$2 ongoingActivityChipsViewModel$isScreenReasonablyLarge$2 = new OngoingActivityChipsViewModel$isScreenReasonablyLarge$2(this.this$0, continuation);
        ongoingActivityChipsViewModel$isScreenReasonablyLarge$2.Z$0 = ((Boolean) obj).booleanValue();
        return ongoingActivityChipsViewModel$isScreenReasonablyLarge$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((OngoingActivityChipsViewModel$isScreenReasonablyLarge$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        LogBuffer logBuffer = this.this$0.logger;
        LogMessage logMessageObtain = logBuffer.obtain(OngoingActivityChipsViewModel.TAG, LogLevel.DEBUG, new OngoingActivityChipsViewModel$incomingChipBundle$1$$ExternalSyntheticLambda0(2), null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
