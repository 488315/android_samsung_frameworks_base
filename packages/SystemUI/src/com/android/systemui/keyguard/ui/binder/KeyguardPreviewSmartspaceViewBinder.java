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
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.NoWhenBranchMatchedException;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class KeyguardPreviewSmartspaceViewBinder {
    public static final KeyguardPreviewSmartspaceViewBinder INSTANCE = new KeyguardPreviewSmartspaceViewBinder();

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
        final /* synthetic */ View $smartspace;
        final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
            final /* synthetic */ View $smartspace;
            final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02941 extends SuspendLambda implements Function2 {
                final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
                final /* synthetic */ View $smartspace;
                final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02941(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardPreviewSmartspaceViewModel;
                    this.$clockPreviewConfig = clockPreviewConfig;
                    this.$smartspace = view;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02941(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02941) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel2 = keyguardPreviewSmartspaceViewModel;
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewSmartspaceViewBinder$bind$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C02962 extends SuspendLambda implements Function2 {
                final /* synthetic */ View $smartspace;
                final /* synthetic */ KeyguardPreviewSmartspaceViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02962(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, View view, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardPreviewSmartspaceViewModel;
                    this.$smartspace = view;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02962(this.$viewModel, this.$smartspace, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02962) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02941(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02962(this.$viewModel, this.$smartspace, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardPreviewSmartspaceViewModel;
            this.$clockPreviewConfig = clockPreviewConfig;
            this.$smartspace = view;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$clockPreviewConfig, this.$smartspace, (Continuation) obj3);
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

    private KeyguardPreviewSmartspaceViewBinder() {
    }

    public static final void bind(KeyguardPreviewSmartspaceViewModel keyguardPreviewSmartspaceViewModel, ClockPreviewConfig clockPreviewConfig, View view) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(keyguardPreviewSmartspaceViewModel, clockPreviewConfig, view, null));
    }
}
