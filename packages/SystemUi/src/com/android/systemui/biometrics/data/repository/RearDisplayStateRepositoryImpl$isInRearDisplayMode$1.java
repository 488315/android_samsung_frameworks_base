package com.android.systemui.biometrics.data.repository;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.common.coroutine.ChannelExt;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.data.repository.RearDisplayStateRepositoryImpl$isInRearDisplayMode$1", m277f = "RearDisplayStateRepository.kt", m278l = {74}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class RearDisplayStateRepositoryImpl$isInRearDisplayMode$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ DeviceStateManager $deviceStateManager;
    final /* synthetic */ Executor $mainExecutor;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RearDisplayStateRepositoryImpl$isInRearDisplayMode$1(DeviceStateManager deviceStateManager, Executor executor, Context context, Continuation<? super RearDisplayStateRepositoryImpl$isInRearDisplayMode$1> continuation) {
        super(2, continuation);
        this.$deviceStateManager = deviceStateManager;
        this.$mainExecutor = executor;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RearDisplayStateRepositoryImpl$isInRearDisplayMode$1 rearDisplayStateRepositoryImpl$isInRearDisplayMode$1 = new RearDisplayStateRepositoryImpl$isInRearDisplayMode$1(this.$deviceStateManager, this.$mainExecutor, this.$context, continuation);
        rearDisplayStateRepositoryImpl$isInRearDisplayMode$1.L$0 = obj;
        return rearDisplayStateRepositoryImpl$isInRearDisplayMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RearDisplayStateRepositoryImpl$isInRearDisplayMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Function1 function1 = new Function1() { // from class: com.android.systemui.biometrics.data.repository.RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$sendRearDisplayStateUpdate$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, Boolean.valueOf(booleanValue), "RearDisplayStateRepositoryImpl", "Error sending rear display state update to " + booleanValue);
                    return Unit.INSTANCE;
                }
            };
            final Context context = this.$context;
            final DeviceStateManager.DeviceStateCallback deviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.biometrics.data.repository.RearDisplayStateRepositoryImpl$isInRearDisplayMode$1$callback$1
                public final void onStateChanged(int i2) {
                    function1.invoke(Boolean.valueOf(ArrayUtils.contains(context.getResources().getIntArray(17236277), i2)));
                }
            };
            function1.invoke(Boolean.FALSE);
            this.$deviceStateManager.registerCallback(this.$mainExecutor, deviceStateCallback);
            final DeviceStateManager deviceStateManager = this.$deviceStateManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.data.repository.RearDisplayStateRepositoryImpl$isInRearDisplayMode$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    deviceStateManager.unregisterCallback(deviceStateCallback);
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
