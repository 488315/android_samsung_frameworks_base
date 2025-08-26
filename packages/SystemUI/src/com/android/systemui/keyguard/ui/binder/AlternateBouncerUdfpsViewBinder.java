package com.android.systemui.keyguard.ui.binder;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
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
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class AlternateBouncerUdfpsViewBinder {

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C02321 extends SuspendLambda implements Function2 {
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02331 extends SuspendLambda implements Function2 {
                final /* synthetic */ DeviceEntryIconView $view;
                final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02331(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = alternateBouncerUdfpsIconViewModel;
                    this.$view = deviceEntryIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02331(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02331) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        final AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel = this.$viewModel;
                        ChannelFlowTransformLatest channelFlowTransformLatest = alternateBouncerUdfpsIconViewModel.accessibilityDelegateHint;
                        final DeviceEntryIconView deviceEntryIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                DeviceEntryIconView.AccessibilityHintType accessibilityHintType = (DeviceEntryIconView.AccessibilityHintType) obj2;
                                DeviceEntryIconView deviceEntryIconView2 = deviceEntryIconView;
                                deviceEntryIconView2.accessibilityHintType = accessibilityHintType;
                                if (accessibilityHintType != DeviceEntryIconView.AccessibilityHintType.NONE) {
                                    final AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel2 = alternateBouncerUdfpsIconViewModel;
                                    deviceEntryIconView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.1.1.1
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            alternateBouncerUdfpsIconViewModel2.statusBarKeyguardViewManager.showPrimaryBouncer("AlternateBouncerUdfpsIconViewModel#onTapped", true);
                                        }
                                    });
                                } else {
                                    deviceEntryIconView2.setOnClickListener(null);
                                }
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ DeviceEntryIconView $view;
                final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = alternateBouncerUdfpsIconViewModel;
                    this.$view = deviceEntryIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$viewModel, this.$view, continuation);
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
                        AlternateBouncerUdfpsIconViewModel$special$$inlined$map$1 alternateBouncerUdfpsIconViewModel$special$$inlined$map$1 = this.$viewModel.alpha;
                        final DeviceEntryIconView deviceEntryIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                deviceEntryIconView.setAlpha(((Number) obj2).floatValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (alternateBouncerUdfpsIconViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
            public C02321(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Continuation continuation) {
                super(2, continuation);
                this.$view = deviceEntryIconView;
                this.$viewModel = alternateBouncerUdfpsIconViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02321 c02321 = new C02321(this.$view, this.$viewModel, continuation);
                c02321.L$0 = obj;
                return c02321;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02321) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                this.$view.setAlpha(0.0f);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02331(this.$viewModel, this.$view, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, Continuation continuation) {
            super(3, continuation);
            this.$view = deviceEntryIconView;
            this.$viewModel = alternateBouncerUdfpsIconViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, (Continuation) obj3);
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
                Lifecycle.State state = Lifecycle.State.STARTED;
                C02321 c02321 = new C02321(this.$view, this.$viewModel, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02321, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ ImageView $fgIconView;
        final /* synthetic */ DeviceEntryIconView $view;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $fgIconView;
            final /* synthetic */ DeviceEntryIconView $view;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerUdfpsIconViewModel;
                this.$fgIconView = imageView;
                this.$view = deviceEntryIconView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$fgIconView, this.$view, continuation);
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
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = this.$viewModel.fgViewModel;
                    final ImageView imageView = this.$fgIconView;
                    final DeviceEntryIconView deviceEntryIconView = this.$view;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            DeviceEntryForegroundViewModel.ForegroundIconViewModel foregroundIconViewModel = (DeviceEntryForegroundViewModel.ForegroundIconViewModel) obj2;
                            ImageView imageView2 = imageView;
                            DeviceEntryIconView.IconType iconType = foregroundIconViewModel.type;
                            deviceEntryIconView.getClass();
                            imageView2.setImageState(DeviceEntryIconView.getIconState(iconType, foregroundIconViewModel.useAodVariant), false);
                            imageView.setImageTintList(ColorStateList.valueOf(foregroundIconViewModel.tint));
                            ImageView imageView3 = imageView;
                            int i2 = foregroundIconViewModel.padding;
                            imageView3.setPadding(i2, i2, i2, i2);
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, DeviceEntryIconView deviceEntryIconView, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = alternateBouncerUdfpsIconViewModel;
            this.$fgIconView = imageView;
            this.$view = deviceEntryIconView;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$fgIconView, this.$view, (Continuation) obj3);
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
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$fgIconView, this.$view, null);
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

    /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ ImageView $bgView;
        final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ImageView $bgView;
            final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$3$1$1, reason: invalid class name and collision with other inner class name */
            final class C02381 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $bgView;
                final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02381(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = alternateBouncerUdfpsIconViewModel;
                    this.$bgView = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02381(this.$viewModel, this.$bgView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02381) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ChannelFlowTransformLatest channelFlowTransformLatest = this.$viewModel.bgColor;
                        final ImageView imageView = this.$bgView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.3.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                imageView.setImageTintList(ColorStateList.valueOf(((Number) obj2).intValue()));
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder$bind$3$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ ImageView $bgView;
                final /* synthetic */ AlternateBouncerUdfpsIconViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = alternateBouncerUdfpsIconViewModel;
                    this.$bgView = imageView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$viewModel, this.$bgView, continuation);
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
                        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = this.$viewModel.bgAlpha;
                        final ImageView imageView = this.$bgView;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.AlternateBouncerUdfpsViewBinder.bind.3.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                imageView.setAlpha(((Number) obj2).floatValue());
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(flowCollector, this) == coroutineSingletons) {
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
            public AnonymousClass1(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = alternateBouncerUdfpsIconViewModel;
                this.$bgView = imageView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$bgView, continuation);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02381(this.$viewModel, this.$bgView, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$bgView, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel, ImageView imageView, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = alternateBouncerUdfpsIconViewModel;
            this.$bgView = imageView;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$viewModel, this.$bgView, (Continuation) obj3);
            anonymousClass3.L$0 = (LifecycleOwner) obj;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$bgView, null);
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

    static {
        new AlternateBouncerUdfpsViewBinder();
    }

    private AlternateBouncerUdfpsViewBinder() {
    }

    public static final void bind(DeviceEntryIconView deviceEntryIconView, AlternateBouncerUdfpsIconViewModel alternateBouncerUdfpsIconViewModel) {
        ImageView imageView = deviceEntryIconView.iconView;
        ImageView imageView2 = deviceEntryIconView.bgView;
        RepeatWhenAttachedKt.repeatWhenAttached(deviceEntryIconView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(deviceEntryIconView, alternateBouncerUdfpsIconViewModel, null));
        RepeatWhenAttachedKt.repeatWhenAttached(imageView, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(alternateBouncerUdfpsIconViewModel, imageView, deviceEntryIconView, null));
        imageView2.setVisibility(0);
        RepeatWhenAttachedKt.repeatWhenAttached(imageView2, EmptyCoroutineContext.INSTANCE, new AnonymousClass3(alternateBouncerUdfpsIconViewModel, imageView2, null));
    }
}
