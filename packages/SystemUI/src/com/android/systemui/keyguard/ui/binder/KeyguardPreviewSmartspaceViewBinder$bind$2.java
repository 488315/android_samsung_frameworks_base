package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardPreviewSmartspaceViewBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
    final /* synthetic */ View $smartspace;
    final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
        final /* synthetic */ View $smartspace;
        final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C01791 extends SuspendLambda implements Function2 {
            final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
            final /* synthetic */ View $smartspace;
            final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01791(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardPreviewSmartspaceViewModel;
                this.$clockPreviewConfig = clockPreviewConfig;
                this.$smartspace = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01791(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01791) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = keyguardPreviewSmartspaceViewModel.previewingClockSize;
                    final ClockPreviewConfig clockPreviewConfig = this.$clockPreviewConfig;
                    final View view = this.$smartspace;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder.bind.2.1.1.1

                        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1$1$1$WhenMappings */
                        public abstract /* synthetic */ class WhenMappings {
                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                            static {
                                int[] iArr = new int[ClockSizeSetting.values().length];
                                try {
                                    iArr[ClockSizeSetting.DYNAMIC.ordinal()] = 1;
                                } catch (NoSuchFieldError unused) {
                                }
                                try {
                                    iArr[ClockSizeSetting.SMALL.ordinal()] = 2;
                                } catch (NoSuchFieldError unused2) {
                                }
                                $EnumSwitchMapping$0 = iArr;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            int smallClockTopPadding;
                            int i2 = WhenMappings.$EnumSwitchMapping$0[((ClockSizeSetting) obj2).ordinal()];
                            ClockPreviewConfig clockPreviewConfig2 = clockPreviewConfig;
                            KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel2 = KeyguardPreviewSmartspaceViewModel.this;
                            if (i2 == 1) {
                                smallClockTopPadding = clockPreviewConfig2.getSmallClockTopPadding(((SystemBarUtilsProxyImpl) keyguardPreviewSmartspaceViewModel2.systemBarUtils).getStatusBarHeaderHeightKeyguard());
                            } else {
                                if (i2 != 2) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                smallClockTopPadding = clockPreviewConfig2.getContext().getResources().getDimensionPixelSize(R$dimen.small_clock_height) + clockPreviewConfig2.getSmallClockTopPadding(((SystemBarUtilsProxyImpl) keyguardPreviewSmartspaceViewModel2.systemBarUtils).getStatusBarHeaderHeightKeyguard());
                            }
                            KeyguardPreviewSmartspaceViewBinder keyguardPreviewSmartspaceViewBinder = KeyguardPreviewSmartspaceViewBinder.INSTANCE;
                            View view2 = view;
                            keyguardPreviewSmartspaceViewBinder.getClass();
                            view2.setPaddingRelative(view2.getPaddingStart(), smallClockTopPadding, view2.getPaddingEnd(), view2.getPaddingBottom());
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
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ View $smartspace;
            final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, View view, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardPreviewSmartspaceViewModel;
                this.$smartspace = view;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$smartspace, continuation);
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
                    KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1 keyguardPreviewSmartspaceViewModel$special$$inlined$map$1 = this.$viewModel.shouldHideSmartspace;
                    final View view = this.$smartspace;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder.bind.2.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            view.setVisibility(((Boolean) obj2).booleanValue() ? 4 : 0);
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (keyguardPreviewSmartspaceViewModel$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardPreviewSmartspaceViewModel;
            this.$clockPreviewConfig = clockPreviewConfig;
            this.$smartspace = view;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, continuation);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01791(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$smartspace, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewSmartspaceViewBinder$bind$2(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardPreviewSmartspaceViewModel;
        this.$clockPreviewConfig = clockPreviewConfig;
        this.$smartspace = view;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewSmartspaceViewBinder$bind$2 keyguardPreviewSmartspaceViewBinder$bind$2 = new KeyguardPreviewSmartspaceViewBinder$bind$2(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, (Continuation) obj3);
        keyguardPreviewSmartspaceViewBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return keyguardPreviewSmartspaceViewBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, null);
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
