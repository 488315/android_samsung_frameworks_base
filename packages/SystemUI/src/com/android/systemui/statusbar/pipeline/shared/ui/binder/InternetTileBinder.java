package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.qs.tiles.InternetTileNewImpl;
import com.android.systemui.statusbar.pipeline.shared.ui.model.InternetTileModel;
import java.util.function.Consumer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class InternetTileBinder {
    public static final InternetTileBinder INSTANCE = new InternetTileBinder();

    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.InternetTileBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<InternetTileModel> $consumer;
        final /* synthetic */ Lifecycle $lifecycle;
        final /* synthetic */ StateFlow $tileModelFlow;
        int label;

        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.binder.InternetTileBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C05761 extends SuspendLambda implements Function2 {
            final /* synthetic */ Consumer<InternetTileModel> $consumer;
            final /* synthetic */ StateFlow $tileModelFlow;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05761(StateFlow stateFlow, Consumer<InternetTileModel> consumer, Continuation continuation) {
                super(2, continuation);
                this.$tileModelFlow = stateFlow;
                this.$consumer = consumer;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C05761(this.$tileModelFlow, this.$consumer, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05761) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    StateFlow stateFlow = this.$tileModelFlow;
                    final Consumer<InternetTileModel> consumer = this.$consumer;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.binder.InternetTileBinder.bind.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            consumer.accept((InternetTileModel) obj2);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Lifecycle lifecycle, StateFlow stateFlow, Consumer<InternetTileModel> consumer, Continuation continuation) {
            super(2, continuation);
            this.$lifecycle = lifecycle;
            this.$tileModelFlow = stateFlow;
            this.$consumer = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$lifecycle, this.$tileModelFlow, this.$consumer, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Lifecycle lifecycle = this.$lifecycle;
                Lifecycle.State state = Lifecycle.State.RESUMED;
                C05761 c05761 = new C05761(this.$tileModelFlow, this.$consumer, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycle, state, c05761, this) == coroutineSingletons) {
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

    private InternetTileBinder() {
    }

    public static void bind(LifecycleRegistry lifecycleRegistry, ReadonlyStateFlow readonlyStateFlow, InternetTileNewImpl.AnonymousClass1 anonymousClass1) {
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleRegistry), null, null, new AnonymousClass1(lifecycleRegistry, readonlyStateFlow, anonymousClass1, null), 7);
    }
}
