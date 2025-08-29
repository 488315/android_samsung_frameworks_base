package com.android.systemui.brightness.data.repository;

import android.hardware.display.DisplayManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class ScreenBrightnessDisplayManagerRepository$brightnessInfo$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScreenBrightnessDisplayManagerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenBrightnessDisplayManagerRepository$brightnessInfo$1(ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenBrightnessDisplayManagerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScreenBrightnessDisplayManagerRepository$brightnessInfo$1 screenBrightnessDisplayManagerRepository$brightnessInfo$1 = new ScreenBrightnessDisplayManagerRepository$brightnessInfo$1(this.this$0, continuation);
        screenBrightnessDisplayManagerRepository$brightnessInfo$1.L$0 = obj;
        return screenBrightnessDisplayManagerRepository$brightnessInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenBrightnessDisplayManagerRepository$brightnessInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.hardware.display.DisplayManager$DisplayListener, com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfo$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository = this.this$0;
            final ?? r4 = new DisplayManager.DisplayListener() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfo$1$listener$1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayChanged(int i2) {
                    if (i2 == screenBrightnessDisplayManagerRepository.displayId) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Unit.INSTANCE);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayAdded(int i2) {
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public final void onDisplayRemoved(int i2) {
                }
            };
            this.this$0.displayManager.registerDisplayListener(r4, null, 0L, 1L);
            final ScreenBrightnessDisplayManagerRepository screenBrightnessDisplayManagerRepository2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.brightness.data.repository.ScreenBrightnessDisplayManagerRepository$brightnessInfo$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    screenBrightnessDisplayManagerRepository2.displayManager.unregisterDisplayListener(r4);
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
