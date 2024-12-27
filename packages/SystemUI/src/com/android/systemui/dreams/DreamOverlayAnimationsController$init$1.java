package com.android.systemui.dreams;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.ui.viewmodel.DreamViewModel$special$$inlined$filter$1;
import java.util.Iterator;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

final class DreamOverlayAnimationsController$init$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DreamOverlayAnimationsController this$0;

    /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ DreamOverlayAnimationsController this$0;

        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00821 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            public C00821(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = dreamOverlayAnimationsController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00821(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00821) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    ChannelFlowTransformLatest channelFlowTransformLatest = dreamOverlayAnimationsController.dreamViewModel.dreamOverlayTranslationY;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final float floatValue = ((Number) obj2).floatValue();
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(3, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.1.1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    DreamOverlayAnimationsController.access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController.this, floatValue, ((Number) obj3).intValue());
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (channelFlowTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            public AnonymousClass2(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
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
                    Flow flow = dreamOverlayAnimationsController.dreamViewModel.dreamOverlayTranslationX;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final float floatValue = ((Number) obj2).floatValue();
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(3, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.2.1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    int intValue = ((Number) obj3).intValue();
                                    DreamOverlayAnimationsController dreamOverlayAnimationsController3 = DreamOverlayAnimationsController.this;
                                    float f = floatValue;
                                    Iterator it = dreamOverlayAnimationsController3.mComplicationHostViewController.getViewsAtPosition(intValue).iterator();
                                    while (it.hasNext()) {
                                        ((View) it.next()).setTranslationX(f);
                                    }
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            public AnonymousClass3(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
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
                    Flow flow = dreamOverlayAnimationsController.dreamViewModel.dreamOverlayAlpha;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            final float floatValue = ((Number) obj2).floatValue();
                            final DreamOverlayAnimationsController dreamOverlayAnimationsController2 = DreamOverlayAnimationsController.this;
                            ComplicationLayoutParams.iteratePositions(3, new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.3.1.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj3) {
                                    DreamOverlayAnimationsController.access$setElementsAlphaAtPosition(DreamOverlayAnimationsController.this, floatValue, ((Number) obj3).intValue(), true);
                                }
                            });
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DreamOverlayAnimationsController this$0;

            public AnonymousClass4(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
                super(2, continuation);
                this.this$0 = dreamOverlayAnimationsController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final DreamOverlayAnimationsController dreamOverlayAnimationsController = this.this$0;
                    DreamViewModel$special$$inlined$filter$1 dreamViewModel$special$$inlined$filter$1 = dreamOverlayAnimationsController.dreamViewModel.transitionEnded;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController.init.1.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DreamOverlayStateController dreamOverlayStateController = DreamOverlayAnimationsController.this.mOverlayStateController;
                            dreamOverlayStateController.getClass();
                            dreamOverlayStateController.modifyState(1, 8);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (dreamViewModel$special$$inlined$filter$1.collect(flowCollector, this) == coroutineSingletons) {
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

        public AnonymousClass1(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = dreamOverlayAnimationsController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
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
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C00821(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 3);
            return Unit.INSTANCE;
        }
    }

    public DreamOverlayAnimationsController$init$1(DreamOverlayAnimationsController dreamOverlayAnimationsController, Continuation continuation) {
        super(3, continuation);
        this.this$0 = dreamOverlayAnimationsController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DreamOverlayAnimationsController$init$1 dreamOverlayAnimationsController$init$1 = new DreamOverlayAnimationsController$init$1(this.this$0, (Continuation) obj3);
        dreamOverlayAnimationsController$init$1.L$0 = (LifecycleOwner) obj;
        return dreamOverlayAnimationsController$init$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
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
