package com.android.systemui.biometrics.ui.binder;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class UdfpsTouchOverlayBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
    final /* synthetic */ UdfpsTouchOverlay $view;
    final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
        final /* synthetic */ UdfpsTouchOverlay $view;
        final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00501 extends SuspendLambda implements Function2 {
            final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
            final /* synthetic */ UdfpsTouchOverlay $view;
            final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00501(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = udfpsTouchOverlayViewModel;
                this.$view = udfpsTouchOverlay;
                this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00501(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow shouldHandleTouches = this.$viewModel.getShouldHandleTouches();
                    final UdfpsTouchOverlay udfpsTouchOverlay = this.$view;
                    final UdfpsOverlayInteractor udfpsOverlayInteractor = this.$udfpsOverlayInteractor;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            StringBuilder sb = new StringBuilder("[");
                            UdfpsTouchOverlay udfpsTouchOverlay2 = UdfpsTouchOverlay.this;
                            sb.append(udfpsTouchOverlay2);
                            sb.append("]: update shouldHandleTouches=");
                            sb.append(booleanValue);
                            Log.d("UdfpsTouchOverlayBinder", sb.toString());
                            udfpsTouchOverlay2.setVisibility(booleanValue ^ true ? 4 : 0);
                            udfpsOverlayInteractor.setHandleTouches(booleanValue);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (shouldHandleTouches.collect(flowCollector, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = udfpsTouchOverlayViewModel;
            this.$view = udfpsTouchOverlay;
            this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Job launch$default = BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C00501(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, null), 3);
            final UdfpsTouchOverlay udfpsTouchOverlay = this.$view;
            final UdfpsOverlayInteractor udfpsOverlayInteractor = this.$udfpsOverlayInteractor;
            ((JobSupport) launch$default).invokeOnCompletion(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder.bind.1.1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    Log.d("UdfpsTouchOverlayBinder", "[" + UdfpsTouchOverlay.this + "-detached]: update shouldHandleTouches=false");
                    udfpsOverlayInteractor.setHandleTouches(false);
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsTouchOverlayBinder$bind$1(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = udfpsTouchOverlayViewModel;
        this.$view = udfpsTouchOverlay;
        this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UdfpsTouchOverlayBinder$bind$1 udfpsTouchOverlayBinder$bind$1 = new UdfpsTouchOverlayBinder$bind$1(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, (Continuation) obj3);
        udfpsTouchOverlayBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return udfpsTouchOverlayBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
