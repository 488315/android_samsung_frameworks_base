package com.android.systemui.keyguard;

import android.view.WindowManagerGlobal;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class ResourceTrimmer$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ResourceTrimmer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResourceTrimmer$start$1(ResourceTrimmer resourceTrimmer, Continuation continuation) {
        super(2, continuation);
        this.this$0 = resourceTrimmer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ResourceTrimmer$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ResourceTrimmer$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flags.sceneContainer();
            Flow transition = this.this$0.keyguardTransitionInteractor.transition((Edge) Edge.Companion.create$default(Edge.Companion, null, KeyguardState.GONE, 1));
            final ResourceTrimmer resourceTrimmer = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ResourceTrimmer$start$1.3
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    if (((TransitionStep) obj2).transitionState == TransitionState.FINISHED) {
                        int i2 = ResourceTrimmer.$r8$clinit;
                        ResourceTrimmer resourceTrimmer2 = ResourceTrimmer.this;
                        resourceTrimmer2.getClass();
                        android.util.Log.d("ResourceTrimmer", "Sending TRIM_MEMORY_UI_HIDDEN.");
                        resourceTrimmer2.globalWindowManager.getClass();
                        WindowManagerGlobal.getInstance().trimMemory(20);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 2;
            if (transition.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1 && i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
