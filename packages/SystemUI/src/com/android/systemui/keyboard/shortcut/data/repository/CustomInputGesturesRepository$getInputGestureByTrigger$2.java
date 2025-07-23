package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputGestureData;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomInputGesturesRepository$getInputGestureByTrigger$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ InputGestureData.Trigger $trigger;
    int label;
    final /* synthetic */ CustomInputGesturesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomInputGesturesRepository$getInputGestureByTrigger$2(CustomInputGesturesRepository customInputGesturesRepository, InputGestureData.Trigger trigger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customInputGesturesRepository;
        this.$trigger = trigger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomInputGesturesRepository$getInputGestureByTrigger$2(this.this$0, this.$trigger, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomInputGesturesRepository$getInputGestureByTrigger$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CustomInputGesturesRepository customInputGesturesRepository = this.this$0;
        int i = CustomInputGesturesRepository.$r8$clinit;
        return customInputGesturesRepository.getInputManager().getInputGesture(this.$trigger);
    }
}
