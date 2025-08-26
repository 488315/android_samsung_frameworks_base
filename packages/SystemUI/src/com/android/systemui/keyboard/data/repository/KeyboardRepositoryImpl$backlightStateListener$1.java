package com.android.systemui.keyboard.data.repository;

import android.hardware.input.InputManager;
import android.hardware.input.KeyboardBacklightState;
import com.android.systemui.common.coroutine.ChannelExt;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class KeyboardRepositoryImpl$backlightStateListener$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyboardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardRepositoryImpl$backlightStateListener$1(KeyboardRepositoryImpl keyboardRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyboardRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyboardRepositoryImpl$backlightStateListener$1 keyboardRepositoryImpl$backlightStateListener$1 = new KeyboardRepositoryImpl$backlightStateListener$1(this.this$0, continuation);
        keyboardRepositoryImpl$backlightStateListener$1.L$0 = obj;
        return keyboardRepositoryImpl$backlightStateListener$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyboardRepositoryImpl$backlightStateListener$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.hardware.input.InputManager$KeyboardBacklightListener, com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyboardRepositoryImpl keyboardRepositoryImpl = this.this$0;
            final ?? r1 = new InputManager.KeyboardBacklightListener() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1$listener$1
                public final void onKeyboardBacklightChanged(int i2, KeyboardBacklightState keyboardBacklightState, boolean z) {
                    if (z) {
                        KeyboardRepositoryImpl keyboardRepositoryImpl2 = keyboardRepositoryImpl;
                        ProducerScope producerScope2 = producerScope;
                        int i3 = KeyboardRepositoryImpl.$r8$clinit;
                        keyboardRepositoryImpl2.getClass();
                        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope2, keyboardBacklightState, "KeyboardRepositoryImpl");
                    }
                }
            };
            this.this$0.inputManager.registerKeyboardBacklightListener(new Executor() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            }, r1);
            final KeyboardRepositoryImpl keyboardRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl$backlightStateListener$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    keyboardRepositoryImpl2.inputManager.unregisterKeyboardBacklightListener(r1);
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
