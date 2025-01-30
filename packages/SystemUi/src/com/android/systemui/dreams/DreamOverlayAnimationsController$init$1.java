package com.android.systemui.dreams;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.DreamOverlayAnimationsController;
import com.android.systemui.keyguard.p009ui.viewmodel.C1749x4d7f0271;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1", m277f = "DreamOverlayAnimationsController.kt", m278l = {98}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class DreamOverlayAnimationsController$init$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View $view;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DreamOverlayAnimationsController this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1", m277f = "DreamOverlayAnimationsController.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1 */
    final class C12691 extends SuspendLambda implements Function2 {
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ DreamOverlayAnimationsController this$0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1", m277f = "DreamOverlayAnimationsController.kt", m278l = {105}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(MutableStateFlow mutableStateFlow, DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.this$0 = dreamOverlayAnimationsController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$configurationBasedDimensions, this.this$0, continuation);
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
                    ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(this.$configurationBasedDimensions, new C1265xc4797fdb(null, this.this$0));
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.2
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final float floatValue = ((Number) obj2).floatValue();
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(3, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.2.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    DreamOverlayAnimationsController.access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController.this, floatValue, ((Number) obj3).intValue());
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (transformLatest.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$2", m277f = "DreamOverlayAnimationsController.kt", m278l = {117}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = dreamOverlayAnimationsController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.this$0, continuation);
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
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = dreamOverlayAnimationsController.transitionViewModel.dreamOverlayAlpha;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final float floatValue = ((Number) obj2).floatValue();
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(3, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    DreamOverlayAnimationsController.access$setElementsAlphaAtPosition(DreamOverlayAnimationsController.this, floatValue, ((Number) obj3).intValue(), true);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$3", m277f = "DreamOverlayAnimationsController.kt", m278l = {132}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = dreamOverlayAnimationsController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    C1749x4d7f0271 c1749x4d7f0271 = dreamOverlayAnimationsController.transitionViewModel.transitionEnded;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DreamOverlayStateController dreamOverlayStateController = DreamOverlayAnimationsController.this.mOverlayStateController;
                            dreamOverlayStateController.getClass();
                            dreamOverlayStateController.modifyState(1, 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (c1749x4d7f0271.collect(flowCollector, this) == coroutineSingletons) {
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
        public C12691(MutableStateFlow mutableStateFlow, DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation<? super C12691> continuation) {
            super(2, continuation);
            this.$configurationBasedDimensions = mutableStateFlow;
            this.this$0 = dreamOverlayAnimationsController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C12691 c12691 = new C12691(this.$configurationBasedDimensions, this.this$0, continuation);
            c12691.L$0 = obj;
            return c12691;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12691) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$configurationBasedDimensions, this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamOverlayAnimationsController$init$1(DreamOverlayAnimationsController dreamOverlayAnimationsController, View view, Continuation<? super DreamOverlayAnimationsController$init$1> continuation) {
        super(3, continuation);
        this.this$0 = dreamOverlayAnimationsController;
        this.$view = view;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DreamOverlayAnimationsController$init$1 dreamOverlayAnimationsController$init$1 = new DreamOverlayAnimationsController$init$1(this.this$0, this.$view, (Continuation) obj3);
        dreamOverlayAnimationsController$init$1.L$0 = (LifecycleOwner) obj;
        return dreamOverlayAnimationsController$init$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ConfigurationController.ConfigurationListener configurationListener;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
            View view = this.$view;
            int i2 = DreamOverlayAnimationsController.$r8$clinit;
            dreamOverlayAnimationsController.getClass();
            final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new DreamOverlayAnimationsController.ConfigurationBasedDimensions(view.getResources().getDimensionPixelSize(R.dimen.dream_overlay_exit_y_offset)));
            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = this.this$0;
            final View view2 = this.$view;
            ConfigurationController.ConfigurationListener configurationListener2 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$configCallback$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onDensityOrFontScaleChanged() {
                    int i3 = DreamOverlayAnimationsController.$r8$clinit;
                    dreamOverlayAnimationsController2.getClass();
                    ((StateFlowImpl) MutableStateFlow.this).setValue(new DreamOverlayAnimationsController.ConfigurationBasedDimensions(view2.getResources().getDimensionPixelSize(R.dimen.dream_overlay_exit_y_offset)));
                }
            };
            ((ConfigurationControllerImpl) this.this$0.configController).addCallback(configurationListener2);
            Lifecycle.State state = Lifecycle.State.CREATED;
            C12691 c12691 = new C12691(MutableStateFlow, this.this$0, null);
            this.L$0 = configurationListener2;
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c12691, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            configurationListener = configurationListener2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            configurationListener = (DreamOverlayAnimationsController$init$1$configCallback$1) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ((ConfigurationControllerImpl) this.this$0.configController).removeCallback(configurationListener);
        return Unit.INSTANCE;
    }
}
