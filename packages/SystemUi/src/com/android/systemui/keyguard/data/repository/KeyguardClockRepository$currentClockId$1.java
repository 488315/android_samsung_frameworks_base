package com.android.systemui.keyguard.data.repository;

import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardClockRepository$currentClockId$1", m277f = "KeyguardClockRepository.kt", m278l = {70}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardClockRepository$currentClockId$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardClockRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardClockRepository$currentClockId$1(KeyguardClockRepository keyguardClockRepository, Continuation<? super KeyguardClockRepository$currentClockId$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardClockRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardClockRepository$currentClockId$1 keyguardClockRepository$currentClockId$1 = new KeyguardClockRepository$currentClockId$1(this.this$0, continuation);
        keyguardClockRepository$currentClockId$1.L$0 = obj;
        return keyguardClockRepository$currentClockId$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardClockRepository$currentClockId$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardClockRepository$currentClockId$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardClockRepository keyguardClockRepository = this.this$0;
            final ?? r1 = new ClockRegistry.ClockChangeListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$currentClockId$1$listener$1
                @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
                public final void onCurrentClockChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(keyguardClockRepository.clockRegistry.getCurrentClockId());
                }

                @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
                public final void onAvailableClocksChanged() {
                }
            };
            ClockRegistry clockRegistry = this.this$0.clockRegistry;
            clockRegistry.getClass();
            Assert.isMainThread();
            ((ArrayList) clockRegistry.clockChangeListeners).add(r1);
            ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(this.this$0.clockRegistry.getCurrentClockId());
            final KeyguardClockRepository keyguardClockRepository2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardClockRepository$currentClockId$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ClockRegistry clockRegistry2 = KeyguardClockRepository.this.clockRegistry;
                    KeyguardClockRepository$currentClockId$1$listener$1 keyguardClockRepository$currentClockId$1$listener$1 = r1;
                    clockRegistry2.getClass();
                    Assert.isMainThread();
                    ((ArrayList) clockRegistry2.clockChangeListeners).remove(keyguardClockRepository$currentClockId$1$listener$1);
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
