package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputGestureData;
import android.hardware.input.InputSettings;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class CustomInputGesturesRepository$customInputGestures$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomInputGesturesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomInputGesturesRepository$customInputGestures$1(CustomInputGesturesRepository customInputGesturesRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customInputGesturesRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomInputGesturesRepository$customInputGestures$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomInputGesturesRepository$customInputGestures$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List customInputGestures;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CustomInputGesturesRepository customInputGesturesRepository = this.this$0;
        customInputGesturesRepository.getClass();
        if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
            customInputGestures = customInputGesturesRepository.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
            customInputGestures.getClass();
        } else {
            customInputGestures = EmptyList.INSTANCE;
        }
        customInputGesturesRepository._customInputGesture.setValue(customInputGestures);
        return Unit.INSTANCE;
    }
}
