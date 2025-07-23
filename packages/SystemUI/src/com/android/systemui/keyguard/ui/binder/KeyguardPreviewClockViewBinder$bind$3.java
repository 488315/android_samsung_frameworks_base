package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.shared.clocks.ClockRegistry;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardPreviewClockViewBinder$bind$3 extends SuspendLambda implements Function3 {
    final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
    final /* synthetic */ ClockRegistry $clockRegistry;
    final /* synthetic */ ConstraintLayout $rootView;
    final /* synthetic */ Function3 $updateClockAppearance;
    final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
        final /* synthetic */ ClockRegistry $clockRegistry;
        final /* synthetic */ ConstraintLayout $rootView;
        final /* synthetic */ Function3 $updateClockAppearance;
        final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$1, reason: invalid class name and collision with other inner class name */
        final class C01781 extends SuspendLambda implements Function2 {
            final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
            final /* synthetic */ Ref$ObjectRef<ClockController> $lastClock;
            final /* synthetic */ ConstraintLayout $rootView;
            final /* synthetic */ Function3 $updateClockAppearance;
            final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$1$3, reason: invalid class name */
            final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                public AnonymousClass3() {
                    super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return new Pair((ClockController) obj, (ClockSizeSetting) obj2);
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$1$4, reason: invalid class name */
            public final class AnonymousClass4 implements FlowCollector {
                public final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
                public final /* synthetic */ Ref$ObjectRef $lastClock;
                public final /* synthetic */ ConstraintLayout $rootView;
                public final /* synthetic */ Function3 $updateClockAppearance;
                public final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;

                public AnonymousClass4(Ref$ObjectRef<ClockController> ref$ObjectRef, Function3 function3, ClockPreviewConfig clockPreviewConfig, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, ConstraintLayout constraintLayout) {
                    this.$lastClock = ref$ObjectRef;
                    this.$updateClockAppearance = function3;
                    this.$clockPreviewConfig = clockPreviewConfig;
                    this.$viewModel = keyguardPreviewClockViewModel;
                    this.$rootView = constraintLayout;
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:12:0x00b5  */
                /* JADX WARN: Removed duplicated region for block: B:19:0x0108  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x0122 A[EDGE_INSN: B:27:0x0122->B:28:0x0122 BREAK  A[LOOP:1: B:17:0x00ff->B:25:0x011e], SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:31:0x013a  */
                /* JADX WARN: Removed duplicated region for block: B:43:0x016c  */
                /* JADX WARN: Removed duplicated region for block: B:46:0x0195  */
                /* JADX WARN: Removed duplicated region for block: B:49:0x01a3  */
                /* JADX WARN: Removed duplicated region for block: B:56:0x01b2  */
                /* JADX WARN: Removed duplicated region for block: B:58:0x01be A[ADDED_TO_REGION] */
                /* JADX WARN: Removed duplicated region for block: B:65:0x01b4  */
                /* JADX WARN: Removed duplicated region for block: B:67:0x0197  */
                /* JADX WARN: Removed duplicated region for block: B:70:0x0043  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
                /* JADX WARN: Type inference failed for: r1v3, types: [T, com.android.systemui.plugins.clocks.ClockController, java.lang.Object] */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(kotlin.Pair r18, kotlin.coroutines.Continuation r19) {
                    /*
                        Method dump skipped, instructions count: 498
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3.AnonymousClass1.C01781.AnonymousClass4.emit(kotlin.Pair, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01781(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, Ref$ObjectRef<ClockController> ref$ObjectRef, Function3 function3, ClockPreviewConfig clockPreviewConfig, ConstraintLayout constraintLayout, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardPreviewClockViewModel;
                this.$lastClock = ref$ObjectRef;
                this.$updateClockAppearance = function3;
                this.$clockPreviewConfig = clockPreviewConfig;
                this.$rootView = constraintLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01781(this.$viewModel, this.$lastClock, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01781) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    KeyguardPreviewClockViewModel keyguardPreviewClockViewModel = this.$viewModel;
                    FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardPreviewClockViewModel.previewClock, keyguardPreviewClockViewModel.selectedClockSize, AnonymousClass3.INSTANCE);
                    AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$lastClock, this.$updateClockAppearance, this.$clockPreviewConfig, this.$viewModel, this.$rootView);
                    this.label = 1;
                    if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(anonymousClass4, this) == coroutineSingletons) {
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
        public AnonymousClass1(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, Function3 function3, ClockPreviewConfig clockPreviewConfig, ConstraintLayout constraintLayout, ClockRegistry clockRegistry, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = keyguardPreviewClockViewModel;
            this.$updateClockAppearance = function3;
            this.$clockPreviewConfig = clockPreviewConfig;
            this.$rootView = constraintLayout;
            this.$clockRegistry = clockRegistry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, this.$clockRegistry, continuation);
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
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            StandaloneCoroutine launchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01781(this.$viewModel, ref$ObjectRef, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, null), 6);
            final ClockRegistry clockRegistry = this.$clockRegistry;
            launchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    Ref$ObjectRef ref$ObjectRef2 = Ref$ObjectRef.this;
                    ClockController clockController = (ClockController) ref$ObjectRef2.element;
                    if (clockController != null) {
                        ClockFaceController smallClock = clockController.getSmallClock();
                        ClockFaceEvents events = smallClock.getEvents();
                        ThemeConfig theme = smallClock.getTheme();
                        ClockRegistry clockRegistry2 = clockRegistry;
                        ClockSettings clockSettings = clockRegistry2.settings;
                        events.onThemeChanged(ThemeConfig.copy$default(theme, false, clockSettings != null ? clockSettings.getSeedColor() : null, 1, null));
                        ClockFaceController largeClock = clockController.getLargeClock();
                        ClockFaceEvents events2 = largeClock.getEvents();
                        ThemeConfig theme2 = largeClock.getTheme();
                        ClockSettings clockSettings2 = clockRegistry2.settings;
                        events2.onThemeChanged(ThemeConfig.copy$default(theme2, false, clockSettings2 != null ? clockSettings2.getSeedColor() : null, 1, null));
                    }
                    ref$ObjectRef2.element = null;
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewClockViewBinder$bind$3(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, Function3 function3, ClockPreviewConfig clockPreviewConfig, ConstraintLayout constraintLayout, ClockRegistry clockRegistry, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = keyguardPreviewClockViewModel;
        this.$updateClockAppearance = function3;
        this.$clockPreviewConfig = clockPreviewConfig;
        this.$rootView = constraintLayout;
        this.$clockRegistry = clockRegistry;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewClockViewBinder$bind$3 keyguardPreviewClockViewBinder$bind$3 = new KeyguardPreviewClockViewBinder$bind$3(this.$viewModel, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, this.$clockRegistry, (Continuation) obj3);
        keyguardPreviewClockViewBinder$bind$3.L$0 = (LifecycleOwner) obj;
        return keyguardPreviewClockViewBinder$bind$3.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, this.$clockRegistry, null);
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
