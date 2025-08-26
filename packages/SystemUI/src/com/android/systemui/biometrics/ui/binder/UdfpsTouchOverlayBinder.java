package com.android.systemui.biometrics.ui.binder;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.biometrics.ui.view.UdfpsTouchOverlay;
import com.android.systemui.biometrics.ui.viewmodel.UdfpsTouchOverlayViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
public final class UdfpsTouchOverlayBinder {

    /* renamed from: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
        final /* synthetic */ UdfpsTouchOverlay $view;
        final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C00981 extends SuspendLambda implements Function2 {
            final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
            final /* synthetic */ UdfpsTouchOverlay $view;
            final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C00991 extends SuspendLambda implements Function2 {
                final /* synthetic */ UdfpsOverlayInteractor $udfpsOverlayInteractor;
                final /* synthetic */ UdfpsTouchOverlay $view;
                final /* synthetic */ UdfpsTouchOverlayViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00991(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = udfpsTouchOverlayViewModel;
                    this.$view = udfpsTouchOverlay;
                    this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00991(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00991) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                StringBuilder sb = new StringBuilder("[");
                                UdfpsTouchOverlay udfpsTouchOverlay2 = udfpsTouchOverlay;
                                sb.append(udfpsTouchOverlay2);
                                sb.append("]: update shouldHandleTouches=");
                                sb.append(zBooleanValue);
                                Log.d("UdfpsTouchOverlayBinder", sb.toString());
                                udfpsTouchOverlay2.setVisibility(!zBooleanValue ? 4 : 0);
                                udfpsOverlayInteractor.setHandleTouches(zBooleanValue);
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
            public C00981(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = udfpsTouchOverlayViewModel;
                this.$view = udfpsTouchOverlay;
                this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00981 c00981 = new C00981(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, continuation);
                c00981.L$0 = obj;
                return c00981;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00981) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C00991(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, null), 7);
                final UdfpsTouchOverlay udfpsTouchOverlay = this.$view;
                final UdfpsOverlayInteractor udfpsOverlayInteractor = this.$udfpsOverlayInteractor;
                standaloneCoroutineLaunchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.biometrics.ui.binder.UdfpsTouchOverlayBinder$bind$1$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Log.d("UdfpsTouchOverlayBinder", "[" + udfpsTouchOverlay + "-detached]: update shouldHandleTouches=false");
                        udfpsOverlayInteractor.setHandleTouches(false);
                        return Unit.INSTANCE;
                    }
                });
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsTouchOverlay udfpsTouchOverlay, UdfpsOverlayInteractor udfpsOverlayInteractor, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = udfpsTouchOverlayViewModel;
            this.$view = udfpsTouchOverlay;
            this.$udfpsOverlayInteractor = udfpsOverlayInteractor;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00981 c00981 = new C00981(this.$viewModel, this.$view, this.$udfpsOverlayInteractor, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c00981, this) == coroutineSingletons) {
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

    static {
        new UdfpsTouchOverlayBinder();
    }

    private UdfpsTouchOverlayBinder() {
    }

    public static final void bind(UdfpsTouchOverlay udfpsTouchOverlay, UdfpsTouchOverlayViewModel udfpsTouchOverlayViewModel, UdfpsOverlayInteractor udfpsOverlayInteractor) {
        RepeatWhenAttachedKt.repeatWhenAttached(udfpsTouchOverlay, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(udfpsTouchOverlayViewModel, udfpsTouchOverlay, udfpsOverlayInteractor, null));
    }
}
