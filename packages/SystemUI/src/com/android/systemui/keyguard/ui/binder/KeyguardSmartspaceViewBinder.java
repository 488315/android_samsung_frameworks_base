package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class KeyguardSmartspaceViewBinder {
    public static final KeyguardSmartspaceViewBinder INSTANCE = new KeyguardSmartspaceViewBinder();

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
        final /* synthetic */ KeyguardClockViewModel $clockViewModel;
        final /* synthetic */ ConstraintLayout $keyguardRootView;
        final /* synthetic */ KeyguardRootViewModel $keyguardRootViewModel;
        final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C03231 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
            final /* synthetic */ KeyguardClockViewModel $clockViewModel;
            final /* synthetic */ ConstraintLayout $keyguardRootView;
            final /* synthetic */ KeyguardRootViewModel $keyguardRootViewModel;
            final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C03241 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
                final /* synthetic */ KeyguardClockViewModel $clockViewModel;
                final /* synthetic */ ConstraintLayout $keyguardRootView;
                final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
                int label;

                /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder$bind$1$1$1$3, reason: invalid class name */
                final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                    public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                    public AnonymousClass3() {
                        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        Boolean bool = (Boolean) obj;
                        bool.booleanValue();
                        Boolean bool2 = (Boolean) obj2;
                        bool2.booleanValue();
                        return new Pair(bool, bool2);
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C03241(KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockViewModel keyguardClockViewModel, ConstraintLayout constraintLayout, KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                    this.$clockViewModel = keyguardClockViewModel;
                    this.$keyguardRootView = constraintLayout;
                    this.$blueprintInteractor = keyguardBlueprintInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C03241(this.$smartspaceViewModel, this.$clockViewModel, this.$keyguardRootView, this.$blueprintInteractor, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C03241) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.$smartspaceViewModel.isWeatherVisible, this.$clockViewModel.hasCustomWeatherDataDisplay, AnonymousClass3.INSTANCE);
                        final ConstraintLayout constraintLayout = this.$keyguardRootView;
                        final KeyguardClockViewModel keyguardClockViewModel = this.$clockViewModel;
                        final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.$smartspaceViewModel;
                        final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder.bind.1.1.1.4
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                KeyguardSmartspaceViewBinder.INSTANCE.getClass();
                                KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                                boolean zBooleanValue = ((Boolean) keyguardClockViewModel2.hasCustomWeatherDataDisplay.$$delegate_0.getValue()).booleanValue();
                                ConstraintLayout constraintLayout2 = constraintLayout;
                                KeyguardSmartspaceViewModel keyguardSmartspaceViewModel2 = keyguardSmartspaceViewModel;
                                if (zBooleanValue) {
                                    Layer layer = (Layer) constraintLayout2.requireViewById(R.id.burn_in_layer);
                                    if (keyguardSmartspaceViewModel2.isSmartspaceEnabled && keyguardSmartspaceViewModel2.isDateWeatherDecoupled) {
                                        layer.removeView(constraintLayout2.requireViewById(R.id.date_smartspace_view));
                                    }
                                } else {
                                    Layer layer2 = (Layer) constraintLayout2.requireViewById(R.id.burn_in_layer);
                                    if (keyguardSmartspaceViewModel2.isSmartspaceEnabled && keyguardSmartspaceViewModel2.isDateWeatherDecoupled) {
                                        layer2.addView(constraintLayout2.requireViewById(R.id.date_smartspace_view));
                                    }
                                }
                                AodBurnInLayer aodBurnInLayer = keyguardClockViewModel2.burnInLayer;
                                if (aodBurnInLayer != null) {
                                    aodBurnInLayer.updatePostLayout(constraintLayout2);
                                }
                                keyguardBlueprintInteractor.refreshBlueprint(new IntraBlueprintTransition.Config(IntraBlueprintTransition.Type.SmartspaceVisibility, false, false, null, 8, null));
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder$bind$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardBlueprintInteractor $blueprintInteractor;
                final /* synthetic */ KeyguardClockViewModel $clockViewModel;
                final /* synthetic */ ConstraintLayout $keyguardRootView;
                final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor, Continuation continuation) {
                    super(2, continuation);
                    this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                    this.$keyguardRootView = constraintLayout;
                    this.$clockViewModel = keyguardClockViewModel;
                    this.$blueprintInteractor = keyguardBlueprintInteractor;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$smartspaceViewModel, this.$keyguardRootView, this.$clockViewModel, this.$blueprintInteractor, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ReadonlyStateFlow readonlyStateFlow = this.$smartspaceViewModel.bcSmartspaceVisibility;
                        final ConstraintLayout constraintLayout = this.$keyguardRootView;
                        final KeyguardClockViewModel keyguardClockViewModel = this.$clockViewModel;
                        final KeyguardBlueprintInteractor keyguardBlueprintInteractor = this.$blueprintInteractor;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardSmartspaceViewBinder.bind.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                ((Number) obj2).intValue();
                                KeyguardSmartspaceViewBinder.INSTANCE.getClass();
                                ConstraintLayout constraintLayout2 = constraintLayout;
                                Layer layer = (Layer) constraintLayout2.requireViewById(R.id.burn_in_layer);
                                View viewRequireViewById = constraintLayout2.requireViewById(R.id.bc_smartspace_view);
                                if (viewRequireViewById.getVisibility() == 0) {
                                    layer.addView(viewRequireViewById);
                                } else {
                                    layer.removeView(viewRequireViewById);
                                }
                                AodBurnInLayer aodBurnInLayer = keyguardClockViewModel.burnInLayer;
                                if (aodBurnInLayer != null) {
                                    aodBurnInLayer.updatePostLayout(constraintLayout2);
                                }
                                keyguardBlueprintInteractor.refreshBlueprint(new IntraBlueprintTransition.Config(IntraBlueprintTransition.Type.SmartspaceVisibility, false, false, null, 8, null));
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
            public C03231(ConstraintLayout constraintLayout, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
                super(2, continuation);
                this.$keyguardRootView = constraintLayout;
                this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                this.$clockViewModel = keyguardClockViewModel;
                this.$blueprintInteractor = keyguardBlueprintInteractor;
                this.$keyguardRootViewModel = keyguardRootViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03231 c03231 = new C03231(this.$keyguardRootView, this.$smartspaceViewModel, this.$clockViewModel, this.$blueprintInteractor, this.$keyguardRootViewModel, continuation);
                c03231.L$0 = obj;
                return c03231;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03231) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03241(this.$smartspaceViewModel, this.$clockViewModel, this.$keyguardRootView, this.$blueprintInteractor, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$smartspaceViewModel, this.$keyguardRootView, this.$clockViewModel, this.$blueprintInteractor, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ConstraintLayout constraintLayout, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, Continuation continuation) {
            super(3, continuation);
            this.$keyguardRootView = constraintLayout;
            this.$smartspaceViewModel = keyguardSmartspaceViewModel;
            this.$clockViewModel = keyguardClockViewModel;
            this.$blueprintInteractor = keyguardBlueprintInteractor;
            this.$keyguardRootViewModel = keyguardRootViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$keyguardRootView, this.$smartspaceViewModel, this.$clockViewModel, this.$blueprintInteractor, this.$keyguardRootViewModel, (Continuation) obj3);
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
                C03231 c03231 = new C03231(this.$keyguardRootView, this.$smartspaceViewModel, this.$clockViewModel, this.$blueprintInteractor, this.$keyguardRootViewModel, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c03231, this) == coroutineSingletons) {
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

    private KeyguardSmartspaceViewBinder() {
    }

    public static final RepeatWhenAttachedKt.C09181 bind(ConstraintLayout constraintLayout, KeyguardRootViewModel keyguardRootViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, KeyguardBlueprintInteractor keyguardBlueprintInteractor) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(constraintLayout, keyguardSmartspaceViewModel, keyguardClockViewModel, keyguardBlueprintInteractor, keyguardRootViewModel, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, EmptyCoroutineContext.INSTANCE, anonymousClass1);
    }
}
