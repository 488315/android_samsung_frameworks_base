package com.android.systemui.keyguard.ui.binder;

import android.content.res.ColorStateList;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.statusbar.KeyguardIndicationController;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardIndicationAreaBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
    final /* synthetic */ KeyguardIndicationController $indicationController;
    final /* synthetic */ TextView $indicationText;
    final /* synthetic */ TextView $indicationTextBottom;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        final /* synthetic */ KeyguardIndicationController $indicationController;
        final /* synthetic */ TextView $indicationText;
        final /* synthetic */ TextView $indicationTextBottom;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01671 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01671(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardIndicationAreaViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01671(this.$viewModel, this.$view, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01671) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.indicationAreaTranslationX;
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            viewGroup.setTranslationX(((Number) obj2).floatValue());
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$2, reason: invalid class name and collision with other inner class name */
            final class C01692 extends SuspendLambda implements Function3 {
                /* synthetic */ int I$0;
                /* synthetic */ boolean Z$0;
                int label;

                public C01692(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    int intValue = ((Number) obj2).intValue();
                    C01692 c01692 = new C01692((Continuation) obj3);
                    c01692.Z$0 = booleanValue;
                    c01692.I$0 = intValue;
                    return c01692.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    int i = this.I$0;
                    if (!z) {
                        i = 0;
                    }
                    return new Integer(i);
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardIndicationAreaViewModel;
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$configurationBasedDimensions, this.$view, continuation);
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
                    Flow flow = this.$viewModel.isIndicationAreaPadded;
                    final MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow, new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L46
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$ConfigurationBasedDimensions r5 = (com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.ConfigurationBasedDimensions) r5
                                    int r5 = r5.indicationAreaPaddingPx
                                    java.lang.Integer r6 = new java.lang.Integer
                                    r6.<init>(r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r6, r0)
                                    if (r4 != r1) goto L46
                                    return r1
                                L46:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new C01692(null));
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.2.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int intValue = ((Number) obj2).intValue();
                            viewGroup.setPadding(intValue, 0, intValue, 0);
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(MutableStateFlow mutableStateFlow, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, Continuation continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$viewModel = keyguardIndicationAreaViewModel;
                this.$view = viewGroup;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$configurationBasedDimensions, this.$viewModel, this.$view, continuation);
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
                    final MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= Integer.MIN_VALUE;
                                    return AnonymousClass2.this.emit(null, this);
                                }
                            }

                            public AnonymousClass2(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L46
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$ConfigurationBasedDimensions r5 = (com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.ConfigurationBasedDimensions) r5
                                    int r5 = r5.defaultBurnInPreventionYOffsetPx
                                    java.lang.Integer r6 = new java.lang.Integer
                                    r6.<init>(r5)
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r6, r0)
                                    if (r4 != r1) goto L46
                                    return r1
                                L46:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    }, new KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$flatMapLatest$1(null, this.$viewModel));
                    final ViewGroup viewGroup = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.3.3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            viewGroup.setTranslationY(((Number) obj2).floatValue());
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ TextView $indicationText;
            final /* synthetic */ TextView $indicationTextBottom;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(MutableStateFlow mutableStateFlow, TextView textView, TextView textView2, Continuation continuation) {
                super(2, continuation);
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$indicationText = textView;
                this.$indicationTextBottom = textView2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, continuation);
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
                    MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    final TextView textView = this.$indicationText;
                    final TextView textView2 = this.$indicationTextBottom;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardIndicationAreaBinder.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardIndicationAreaBinder.ConfigurationBasedDimensions) obj2;
                            textView.setTextSize(0, configurationBasedDimensions.indicationTextSizePx);
                            textView2.setTextSize(0, configurationBasedDimensions.indicationTextSizePx);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (mutableStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
            final /* synthetic */ KeyguardIndicationController $indicationController;
            final /* synthetic */ ViewGroup $view;
            final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, KeyguardIndicationController keyguardIndicationController, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardIndicationAreaViewModel;
                this.$configurationBasedDimensions = mutableStateFlow;
                this.$view = viewGroup;
                this.$indicationController = keyguardIndicationController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.$viewModel, this.$configurationBasedDimensions, this.$view, this.$indicationController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = this.$viewModel.configurationChange;
                    final MutableStateFlow mutableStateFlow = this.$configurationBasedDimensions;
                    final ViewGroup viewGroup = this.$view;
                    final KeyguardIndicationController keyguardIndicationController = this.$indicationController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.5.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardIndicationAreaBinder keyguardIndicationAreaBinder = KeyguardIndicationAreaBinder.INSTANCE;
                            ViewGroup viewGroup2 = viewGroup;
                            keyguardIndicationAreaBinder.getClass();
                            MutableStateFlow.this.setValue(KeyguardIndicationAreaBinder.loadFromResources(viewGroup2));
                            KeyguardIndicationController keyguardIndicationController2 = keyguardIndicationController;
                            keyguardIndicationController2.mInitialTextColorState = ColorStateList.valueOf(Utils.getColorAttrDefaultColor(keyguardIndicationController2.mContext, R.attr.wallpaperTextColor, 0));
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardIndicationController $indicationController;
            final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, KeyguardIndicationController keyguardIndicationController, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardIndicationAreaViewModel;
                this.$indicationController = keyguardIndicationController;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.$viewModel, this.$indicationController, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.visible;
                    final KeyguardIndicationController keyguardIndicationController = this.$indicationController;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.6.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KeyguardIndicationController.this.setVisible(((Boolean) obj2).booleanValue());
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, MutableStateFlow mutableStateFlow, TextView textView, TextView textView2, KeyguardIndicationController keyguardIndicationController, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardIndicationAreaViewModel;
            this.$view = viewGroup;
            this.$configurationBasedDimensions = mutableStateFlow;
            this.$indicationText = textView;
            this.$indicationTextBottom = textView2;
            this.$indicationController = keyguardIndicationController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$indicationController, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01671(this.$viewModel, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$configurationBasedDimensions, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$configurationBasedDimensions, this.$viewModel, this.$view, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$configurationBasedDimensions, this.$view, this.$indicationController, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$indicationController, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardIndicationAreaBinder$bind$2(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, MutableStateFlow mutableStateFlow, TextView textView, TextView textView2, KeyguardIndicationController keyguardIndicationController, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardIndicationAreaViewModel;
        this.$view = viewGroup;
        this.$configurationBasedDimensions = mutableStateFlow;
        this.$indicationText = textView;
        this.$indicationTextBottom = textView2;
        this.$indicationController = keyguardIndicationController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardIndicationAreaBinder$bind$2 keyguardIndicationAreaBinder$bind$2 = new KeyguardIndicationAreaBinder$bind$2(this.$viewModel, this.$view, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$indicationController, (Continuation) obj3);
        keyguardIndicationAreaBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardIndicationAreaBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$indicationController, null);
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
