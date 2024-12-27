package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class KeyguardInteractor$onCameraLaunchDetected$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardInteractor this$0;

    public KeyguardInteractor$onCameraLaunchDetected$1(KeyguardInteractor keyguardInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardInteractor$onCameraLaunchDetected$1 keyguardInteractor$onCameraLaunchDetected$1 = new KeyguardInteractor$onCameraLaunchDetected$1(this.this$0, continuation);
        keyguardInteractor$onCameraLaunchDetected$1.L$0 = obj;
        return keyguardInteractor$onCameraLaunchDetected$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardInteractor$onCameraLaunchDetected$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardInteractor keyguardInteractor = this.this$0;
            final ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$onCameraLaunchDetected$1$callback$1
                @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                public final void onCameraLaunchGestureDetected(int i2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    keyguardInteractor.getClass();
                    CameraLaunchSourceModel cameraLaunchSourceIntToModel = KeyguardInteractor.cameraLaunchSourceIntToModel(i2);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(ProducerScope.this, cameraLaunchSourceIntToModel, "KeyguardInteractor", "updated onCameraLaunchGestureDetected");
                }
            };
            this.this$0.commandQueue.addCallback((CommandQueue.Callbacks) r1);
            final KeyguardInteractor keyguardInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$onCameraLaunchDetected$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardInteractor.this.commandQueue.removeCallback((CommandQueue.Callbacks) r1);
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
