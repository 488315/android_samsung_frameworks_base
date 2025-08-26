package com.android.systemui.util.kotlin;

import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.ReduceBrightColorsControllerImpl;
import com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt;
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
public final class ReduceBrightColorsControllerExtKt {

    /* renamed from: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ReduceBrightColorsController $this_isEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ReduceBrightColorsController reduceBrightColorsController, Continuation continuation) {
            super(2, continuation);
            this.$this_isEnabled = reduceBrightColorsController;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(ReduceBrightColorsController reduceBrightColorsController, ReduceBrightColorsControllerExtKt$isEnabled$1$callback$1 reduceBrightColorsControllerExtKt$isEnabled$1$callback$1) {
            ((ReduceBrightColorsControllerImpl) reduceBrightColorsController).removeCallback(reduceBrightColorsControllerExtKt$isEnabled$1$callback$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_isEnabled, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1$callback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final ?? r1 = new ReduceBrightColorsController.Listener() { // from class: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1$callback$1
                    @Override // com.android.systemui.qs.ReduceBrightColorsController.Listener
                    public void onActivated(boolean z) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Boolean.valueOf(z));
                    }
                };
                ((ReduceBrightColorsControllerImpl) this.$this_isEnabled).addCallback(r1);
                final ReduceBrightColorsController reduceBrightColorsController = this.$this_isEnabled;
                Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return ReduceBrightColorsControllerExtKt.AnonymousClass1.invokeSuspend$lambda$0(reduceBrightColorsController, r1);
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

    /* renamed from: com.android.systemui.util.kotlin.ReduceBrightColorsControllerExtKt$isEnabled$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ ReduceBrightColorsController $this_isEnabled;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ReduceBrightColorsController reduceBrightColorsController, Continuation continuation) {
            super(2, continuation);
            this.$this_isEnabled = reduceBrightColorsController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$this_isEnabled, continuation);
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
                Boolean boolValueOf = Boolean.valueOf(((ReduceBrightColorsControllerImpl) this.$this_isEnabled).mManager.isReduceBrightColorsActivated());
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

    public static final Flow isEnabled(ReduceBrightColorsController reduceBrightColorsController) {
        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(reduceBrightColorsController, null), FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(reduceBrightColorsController, null)));
    }
}
