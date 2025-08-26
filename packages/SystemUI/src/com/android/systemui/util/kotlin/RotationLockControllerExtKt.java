package com.android.systemui.util.kotlin;

import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.kotlin.RotationLockControllerExtKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class RotationLockControllerExtKt {

    /* renamed from: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ RotationLockController $this_isRotationLockEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(RotationLockController rotationLockController, Continuation continuation) {
            super(2, continuation);
            this.$this_isRotationLockEnabled = rotationLockController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(RotationLockController rotationLockController, RotationLockController.RotationLockControllerCallback rotationLockControllerCallback) {
            rotationLockController.removeCallback(rotationLockControllerCallback);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_isRotationLockEnabled, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$1$rotationLockCallback$1
                    @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
                    public final void onRotationLockStateChanged(boolean z, boolean z2) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                    }
                };
                this.$this_isRotationLockEnabled.addCallback(rotationLockControllerCallback);
                final RotationLockController rotationLockController = this.$this_isRotationLockEnabled;
                Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return RotationLockControllerExtKt.AnonymousClass1.invokeSuspend$lambda$0(rotationLockController, rotationLockControllerCallback);
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.kotlin.RotationLockControllerExtKt$isRotationLockEnabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ RotationLockController $this_isRotationLockEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(RotationLockController rotationLockController, Continuation continuation) {
            super(2, continuation);
            this.$this_isRotationLockEnabled = rotationLockController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_isRotationLockEnabled, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Boolean boolValueOf = Boolean.valueOf(this.$this_isRotationLockEnabled.isRotationLocked());
                this.label = 1;
                if (flowCollector.emit(boolValueOf, this) == coroutineSingletons) {
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

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector flowCollector, Continuation continuation) {
            return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public static final Flow isRotationLockEnabled(RotationLockController rotationLockController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(rotationLockController, null), FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(rotationLockController, null)));
    }
}
