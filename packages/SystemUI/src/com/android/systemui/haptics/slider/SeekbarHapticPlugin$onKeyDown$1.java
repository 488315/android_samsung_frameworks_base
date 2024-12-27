package com.android.systemui.haptics.slider;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SeekbarHapticPlugin$onKeyDown$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SeekbarHapticPlugin this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekbarHapticPlugin$onKeyDown$1(SeekbarHapticPlugin seekbarHapticPlugin, Continuation continuation) {
        super(2, continuation);
        this.this$0 = seekbarHapticPlugin;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SeekbarHapticPlugin$onKeyDown$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeekbarHapticPlugin$onKeyDown$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(60L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        SeekbarHapticPlugin seekbarHapticPlugin = this.this$0;
        int i2 = SeekbarHapticPlugin.$r8$clinit;
        if (seekbarHapticPlugin.isTracking()) {
            seekbarHapticPlugin.sliderEventProducer.onStopTracking(false);
        }
        return Unit.INSTANCE;
    }
}
