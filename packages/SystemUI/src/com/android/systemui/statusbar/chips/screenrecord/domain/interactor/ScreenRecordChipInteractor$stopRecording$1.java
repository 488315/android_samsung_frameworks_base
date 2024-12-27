package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import com.android.systemui.screenrecord.data.repository.ScreenRecordRepository;
import com.android.systemui.screenrecord.data.repository.ScreenRecordRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ScreenRecordChipInteractor$stopRecording$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenRecordChipInteractor this$0;

    public ScreenRecordChipInteractor$stopRecording$1(ScreenRecordChipInteractor screenRecordChipInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenRecordChipInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenRecordChipInteractor$stopRecording$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenRecordChipInteractor$stopRecording$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ScreenRecordRepository screenRecordRepository = this.this$0.screenRecordRepository;
            this.label = 1;
            if (((ScreenRecordRepositoryImpl) screenRecordRepository).stopRecording(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
