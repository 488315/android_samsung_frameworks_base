package com.android.dream.lowlight;

import android.util.Log;
import java.util.concurrent.CancellationException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LowLightDreamManager$setAmbientLightMode$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $shouldEnterLowLight;
    int label;
    final /* synthetic */ LowLightDreamManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LowLightDreamManager$setAmbientLightMode$1(LowLightDreamManager lowLightDreamManager, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lowLightDreamManager;
        this.$shouldEnterLowLight = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LowLightDreamManager$setAmbientLightMode$1(this.this$0, this.$shouldEnterLowLight, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LowLightDreamManager$setAmbientLightMode$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LowLightDreamManager lowLightDreamManager = this.this$0;
                LowLightTransitionCoordinator lowLightTransitionCoordinator = lowLightDreamManager.lowLightTransitionCoordinator;
                long j = lowLightDreamManager.mLowLightTransitionTimeout;
                boolean z = this.$shouldEnterLowLight;
                this.label = 1;
                lowLightTransitionCoordinator.getClass();
                Object m3451withTimeoutKLykuaI = TimeoutKt.m3451withTimeoutKLykuaI(j, new LowLightTransitionCoordinator$waitForLowLightTransitionAnimationKLykuaI$$inlined$suspendCoroutineWithTimeoutKLykuaI$1(null, z, lowLightTransitionCoordinator), this);
                if (m3451withTimeoutKLykuaI != obj2) {
                    m3451withTimeoutKLykuaI = Unit.INSTANCE;
                }
                if (m3451withTimeoutKLykuaI == obj2) {
                    return obj2;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (TimeoutCancellationException e) {
            Log.e("LowLightDreamManager", "timed out while waiting for low light animation", e);
        } catch (CancellationException unused) {
            Log.w("LowLightDreamManager", "low light transition animation cancelled");
        }
        LowLightDreamManager lowLightDreamManager2 = this.this$0;
        lowLightDreamManager2.dreamManager.setSystemDreamComponent(this.$shouldEnterLowLight ? lowLightDreamManager2.lowLightDreamComponent : null);
        return Unit.INSTANCE;
    }
}
