package com.android.systemui.keyguard.ui.binder;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder;
import com.android.systemui.keyguard.ui.view.KeyguardIndicationArea;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardIndicationAreaViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.util.kotlin.DisposableHandles;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class KeyguardIndicationAreaBinder {
    public static final KeyguardIndicationAreaBinder INSTANCE = new KeyguardIndicationAreaBinder();

    public final class ConfigurationBasedDimensions {
        public final int defaultBurnInPreventionYOffsetPx;
        public final int indicationAreaPaddingPx;
        public final int indicationTextSizePx;

        public ConfigurationBasedDimensions(int i, int i2, int i3) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaPaddingPx = i2;
            this.indicationTextSizePx = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaPaddingPx == configurationBasedDimensions.indicationAreaPaddingPx && this.indicationTextSizePx == configurationBasedDimensions.indicationTextSizePx;
        }

        public final int hashCode() {
            return Integer.hashCode(this.indicationTextSizePx) + ReorderTile$$ExternalSyntheticOutline0.m(this.indicationAreaPaddingPx, Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=");
            sb.append(this.defaultBurnInPreventionYOffsetPx);
            sb.append(", indicationAreaPaddingPx=");
            sb.append(this.indicationAreaPaddingPx);
            sb.append(", indicationTextSizePx=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.indicationTextSizePx, ")", sb);
        }
    }

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
        final /* synthetic */ KeyguardIndicationController $indicationController;
        final /* synthetic */ TextView $indicationText;
        final /* synthetic */ TextView $indicationTextBottom;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02791 extends SuspendLambda implements Function2 {
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02791(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardIndicationAreaViewModel;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02791(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02791) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C02812 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $configurationBasedDimensions;
                final /* synthetic */ ViewGroup $view;
                final /* synthetic */ KeyguardIndicationAreaViewModel $viewModel;
                int label;

                /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$2$2, reason: invalid class name and collision with other inner class name */
                final class C02822 extends SuspendLambda implements Function3 {
                    /* synthetic */ int I$0;
                    /* synthetic */ boolean Z$0;
                    int label;

                    public C02822(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        int iIntValue = ((Number) obj2).intValue();
                        C02822 c02822 = new C02822((Continuation) obj3);
                        c02822.Z$0 = zBooleanValue;
                        c02822.I$0 = iIntValue;
                        return c02822.invokeSuspend(Unit.INSTANCE);
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
                public C02812(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, MutableStateFlow mutableStateFlow, ViewGroup viewGroup, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardIndicationAreaViewModel;
                    this.$configurationBasedDimensions = mutableStateFlow;
                    this.$view = viewGroup;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02812(this.$viewModel, this.$configurationBasedDimensions, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02812) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i = anonymousClass1.label;
                                        if ((i & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i2 = anonymousClass1.label;
                                    if (i2 == 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        Integer num = new Integer(((KeyguardIndicationAreaBinder.ConfigurationBasedDimensions) obj).indicationAreaPaddingPx);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i2 != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = mutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, new C02822(null));
                        final ViewGroup viewGroup = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.2.1.2.3
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int iIntValue = ((Number) obj2).intValue();
                                viewGroup.setPadding(iIntValue, 0, iIntValue, 0);
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
                        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(new Flow() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder$bind$2$1$3$invokeSuspend$$inlined$map$1

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

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i = anonymousClass1.label;
                                        if ((i & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i2 = anonymousClass1.label;
                                    if (i2 == 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        Integer num = new Integer(((KeyguardIndicationAreaBinder.ConfigurationBasedDimensions) obj).defaultBurnInPreventionYOffsetPx);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i2 != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = mutableStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                        if (channelFlowTransformLatestTransformLatest.collect(flowCollector, this) == coroutineSingletons) {
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
                                ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj2;
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
                                mutableStateFlow.setValue(KeyguardIndicationAreaBinder.loadFromResources(viewGroup2));
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
                                keyguardIndicationController.setVisible(((Boolean) obj2).booleanValue());
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02791(this.$viewModel, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02812(this.$viewModel, this.$configurationBasedDimensions, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$configurationBasedDimensions, this.$viewModel, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$configurationBasedDimensions, this.$view, this.$indicationController, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$indicationController, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, ViewGroup viewGroup, MutableStateFlow mutableStateFlow, TextView textView, TextView textView2, KeyguardIndicationController keyguardIndicationController, Continuation continuation) {
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
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$view, this.$configurationBasedDimensions, this.$indicationText, this.$indicationTextBottom, this.$indicationController, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
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

    private KeyguardIndicationAreaBinder() {
    }

    public static final DisposableHandles bind(KeyguardIndicationArea keyguardIndicationArea, KeyguardIndicationAreaViewModel keyguardIndicationAreaViewModel, final KeyguardIndicationController keyguardIndicationController) {
        DisposableHandles disposableHandles = new DisposableHandles();
        final ViewGroup viewGroup = keyguardIndicationController.mIndicationArea;
        keyguardIndicationController.setIndicationArea(keyguardIndicationArea);
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardIndicationAreaBinder.bind.1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                ViewGroup viewGroup2 = viewGroup;
                if (viewGroup2 != null) {
                    keyguardIndicationController.setIndicationArea(viewGroup2);
                }
            }
        });
        TextView textView = (TextView) keyguardIndicationArea.requireViewById(R.id.keyguard_indication_text);
        TextView textView2 = (TextView) keyguardIndicationArea.requireViewById(R.id.keyguard_indication_text_bottom);
        keyguardIndicationArea.setClipChildren(false);
        keyguardIndicationArea.setClipToPadding(false);
        INSTANCE.getClass();
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(keyguardIndicationAreaViewModel, keyguardIndicationArea, StateFlowKt.MutableStateFlow(loadFromResources(keyguardIndicationArea)), textView, textView2, keyguardIndicationController, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(keyguardIndicationArea, EmptyCoroutineContext.INSTANCE, anonymousClass2));
        return disposableHandles;
    }

    public static ConfigurationBasedDimensions loadFromResources(View view) {
        return new ConfigurationBasedDimensions(view.getResources().getDimensionPixelOffset(R.dimen.default_burn_in_prevention_offset), view.getResources().getDimensionPixelOffset(R.dimen.keyguard_indication_area_padding), view.getResources().getDimensionPixelSize(17106417));
    }
}
