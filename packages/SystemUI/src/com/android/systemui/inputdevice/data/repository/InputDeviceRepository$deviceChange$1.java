package com.android.systemui.inputdevice.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.inputdevice.data.repository.InputDeviceRepository;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class InputDeviceRepository$deviceChange$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ InputDeviceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InputDeviceRepository$deviceChange$1(InputDeviceRepository inputDeviceRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = inputDeviceRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        InputDeviceRepository$deviceChange$1 inputDeviceRepository$deviceChange$1 = new InputDeviceRepository$deviceChange$1(this.this$0, continuation);
        inputDeviceRepository$deviceChange$1.L$0 = obj;
        return inputDeviceRepository$deviceChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((InputDeviceRepository$deviceChange$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, java.util.Set] */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.hardware.input.InputManager$InputDeviceListener, com.android.systemui.inputdevice.data.repository.InputDeviceRepository$deviceChange$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = ArraysKt___ArraysKt.toSet(this.this$0.inputManager.getInputDeviceIds());
            final InputDeviceRepository inputDeviceRepository = this.this$0;
            final ?? r3 = new InputManager.InputDeviceListener() { // from class: com.android.systemui.inputdevice.data.repository.InputDeviceRepository$deviceChange$1$listener$1
                /* JADX WARN: Type inference failed for: r1v2, types: [T, java.util.Set] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceAdded(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    ref$ObjectRef2.element = SetsKt___SetsKt.plus((Set) ref$ObjectRef2.element, Integer.valueOf(i2));
                    InputDeviceRepository inputDeviceRepository2 = inputDeviceRepository;
                    ProducerScope producerScope2 = producerScope;
                    Pair pair = new Pair(ref$ObjectRef.element, new InputDeviceRepository.DeviceAdded(i2));
                    int i3 = InputDeviceRepository.$r8$clinit;
                    inputDeviceRepository2.getClass();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope2, pair, "InputDeviceRepository");
                }

                /* JADX WARN: Type inference failed for: r1v2, types: [T, java.util.Set] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceRemoved(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    ref$ObjectRef2.element = SetsKt___SetsKt.minus((Set) ref$ObjectRef2.element, Integer.valueOf(i2));
                    InputDeviceRepository inputDeviceRepository2 = inputDeviceRepository;
                    ProducerScope producerScope2 = producerScope;
                    Pair pair = new Pair(ref$ObjectRef.element, new InputDeviceRepository.DeviceRemoved(i2));
                    int i3 = InputDeviceRepository.$r8$clinit;
                    inputDeviceRepository2.getClass();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope2, pair, "InputDeviceRepository");
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceChanged(int i2) {
                }
            };
            InputDeviceRepository inputDeviceRepository2 = this.this$0;
            Pair pair = new Pair(ref$ObjectRef.element, InputDeviceRepository.FreshStart.INSTANCE);
            inputDeviceRepository2.getClass();
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, pair, "InputDeviceRepository");
            InputDeviceRepository inputDeviceRepository3 = this.this$0;
            inputDeviceRepository3.inputManager.registerInputDeviceListener(r3, inputDeviceRepository3.backgroundHandler);
            final InputDeviceRepository inputDeviceRepository4 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.inputdevice.data.repository.InputDeviceRepository$deviceChange$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    InputDeviceRepository.this.inputManager.unregisterInputDeviceListener(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
