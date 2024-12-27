package com.android.systemui.keyboard.data.repository;

import android.hardware.input.InputManager;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyboardRepositoryImpl$keyboardsChange$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyboardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardRepositoryImpl$keyboardsChange$1(KeyboardRepositoryImpl keyboardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardRepositoryImpl$keyboardsChange$1 keyboardRepositoryImpl$keyboardsChange$1 = new KeyboardRepositoryImpl$keyboardsChange$1(this.this$0, continuation);
        keyboardRepositoryImpl$keyboardsChange$1.L$0 = obj;
        return keyboardRepositoryImpl$keyboardsChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardRepositoryImpl$keyboardsChange$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [T, java.util.Set] */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.hardware.input.InputManager$InputDeviceListener, com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ref$ObjectRef.element = ArraysKt___ArraysKt.toSet(this.this$0.inputManager.getInputDeviceIds());
            final KeyboardRepositoryImpl keyboardRepositoryImpl = this.this$0;
            final ?? r3 = new InputManager.InputDeviceListener() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1$listener$1
                /* JADX WARN: Type inference failed for: r1v2, types: [T, java.util.Set] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceAdded(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    ref$ObjectRef2.element = SetsKt___SetsKt.plus((Set) ref$ObjectRef2.element, Integer.valueOf(i2));
                    KeyboardRepositoryImpl.access$sendWithLogging(keyboardRepositoryImpl, producerScope, new Pair(ref$ObjectRef.element, new KeyboardRepositoryImpl.DeviceAdded(i2)));
                }

                /* JADX WARN: Type inference failed for: r4v2, types: [T, java.util.Set] */
                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceRemoved(int i2) {
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    ref$ObjectRef2.element = SetsKt___SetsKt.minus((Set) ref$ObjectRef2.element, Integer.valueOf(i2));
                    KeyboardRepositoryImpl.access$sendWithLogging(keyboardRepositoryImpl, producerScope, new Pair(ref$ObjectRef.element, KeyboardRepositoryImpl.DeviceRemoved.INSTANCE));
                }

                @Override // android.hardware.input.InputManager.InputDeviceListener
                public final void onInputDeviceChanged(int i2) {
                }
            };
            KeyboardRepositoryImpl.access$sendWithLogging(this.this$0, producerScope, new Pair(ref$ObjectRef.element, KeyboardRepositoryImpl.FreshStart.INSTANCE));
            this.this$0.inputManager.registerInputDeviceListener(r3, null);
            final KeyboardRepositoryImpl keyboardRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$keyboardsChange$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyboardRepositoryImpl.this.inputManager.unregisterInputDeviceListener(r3);
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
