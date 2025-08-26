package com.android.systemui.smartspace.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.ui.viewmodel.SmartspaceViewModel;
import com.android.systemui.smartspace.ui.viewmodel.SmartspaceViewModel$special$$inlined$filter$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class SmartspaceViewBinder {
    public static final SmartspaceViewBinder INSTANCE = new SmartspaceViewBinder();

    /* renamed from: com.android.systemui.smartspace.ui.binder.SmartspaceViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $smartspaceView;
        final /* synthetic */ SmartspaceViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.smartspace.ui.binder.SmartspaceViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C04821 extends SuspendLambda implements Function2 {
            final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $smartspaceView;
            final /* synthetic */ SmartspaceViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.smartspace.ui.binder.SmartspaceViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C04831 extends SuspendLambda implements Function2 {
                final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceView $smartspaceView;
                final /* synthetic */ SmartspaceViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04831(SmartspaceViewModel smartspaceViewModel, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = smartspaceViewModel;
                    this.$smartspaceView = smartspaceView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C04831(this.$viewModel, this.$smartspaceView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04831) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        SmartspaceViewModel$special$$inlined$filter$1 smartspaceViewModel$special$$inlined$filter$1 = this.$viewModel.isAwake;
                        final BcSmartspaceDataPlugin.SmartspaceView smartspaceView = this.$smartspaceView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.smartspace.ui.binder.SmartspaceViewBinder.bind.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                smartspaceView.setScreenOn(((Boolean) obj2).booleanValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (smartspaceViewModel$special$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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
            public C04821(SmartspaceViewModel smartspaceViewModel, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = smartspaceViewModel;
                this.$smartspaceView = smartspaceView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04821 c04821 = new C04821(this.$viewModel, this.$smartspaceView, continuation);
                c04821.L$0 = obj;
                return c04821;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04821) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C04831(this.$viewModel, this.$smartspaceView, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SmartspaceViewModel smartspaceViewModel, BcSmartspaceDataPlugin.SmartspaceView smartspaceView, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = smartspaceViewModel;
            this.$smartspaceView = smartspaceView;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$smartspaceView, (Continuation) obj3);
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
                C04821 c04821 = new C04821(this.$viewModel, this.$smartspaceView, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04821, this) == coroutineSingletons) {
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

    private SmartspaceViewBinder() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void bind(BcSmartspaceDataPlugin.SmartspaceView smartspaceView, SmartspaceViewModel smartspaceViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached((View) smartspaceView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(smartspaceViewModel, smartspaceView, null));
    }
}
