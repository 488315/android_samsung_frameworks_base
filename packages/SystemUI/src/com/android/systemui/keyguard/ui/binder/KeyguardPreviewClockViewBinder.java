package com.android.systemui.keyguard.ui.binder;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSectionKt;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewClockViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import com.android.systemui.plugins.clocks.ClockPreviewConfig;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.plugins.clocks.ThemeConfig;
import com.android.systemui.shared.clocks.ClockRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
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

/* loaded from: classes2.dex */
public final class KeyguardPreviewClockViewBinder {
    public static final KeyguardPreviewClockViewBinder INSTANCE = new KeyguardPreviewClockViewBinder();
    public static final int lockId = View.generateViewId();

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

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
        final /* synthetic */ ClockRegistry $clockRegistry;
        final /* synthetic */ ConstraintLayout $rootView;
        final /* synthetic */ Function3 $updateClockAppearance;
        final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
            final /* synthetic */ ClockRegistry $clockRegistry;
            final /* synthetic */ ConstraintLayout $rootView;
            final /* synthetic */ Function3 $updateClockAppearance;
            final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$1, reason: invalid class name and collision with other inner class name */
            final class C02921 extends SuspendLambda implements Function2 {
                final /* synthetic */ ClockPreviewConfig $clockPreviewConfig;
                final /* synthetic */ Ref$ObjectRef<ClockController> $lastClock;
                final /* synthetic */ ConstraintLayout $rootView;
                final /* synthetic */ Function3 $updateClockAppearance;
                final /* synthetic */ KeyguardPreviewClockViewModel $viewModel;
                int label;

                /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$1$3, reason: invalid class name and collision with other inner class name */
                final /* synthetic */ class C02933 extends AdaptedFunctionReference implements Function3 {
                    public static final C02933 INSTANCE = new C02933();

                    public C02933() {
                        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        return new Pair((ClockController) obj, (ClockSizeSetting) obj2);
                    }
                }

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
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                    /* JADX WARN: Type inference failed for: r1v3, types: [T, com.android.systemui.plugins.clocks.ClockController, java.lang.Object] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Pair pair, Continuation continuation) {
                        KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1 keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1;
                        ClockSizeSetting clockSizeSetting;
                        ClockController clockController;
                        ConstraintLayout constraintLayout;
                        int i;
                        AnonymousClass4 anonymousClass4 = this;
                        if (continuation instanceof KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1) {
                            keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1 = (KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1) continuation;
                            int i2 = keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.label;
                            if ((i2 & Integer.MIN_VALUE) != 0) {
                                keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.label = i2 - Integer.MIN_VALUE;
                            } else {
                                keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1 = new KeyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1(anonymousClass4, continuation);
                            }
                        }
                        Object obj = keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i3 = keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.label;
                        int i4 = 0;
                        if (i3 == 0) {
                            ResultKt.throwOnFailure(obj);
                            ?? r1 = (ClockController) pair.component1();
                            clockSizeSetting = (ClockSizeSetting) pair.component2();
                            Ref$ObjectRef ref$ObjectRef = anonymousClass4.$lastClock;
                            ClockController clockController2 = (ClockController) ref$ObjectRef.element;
                            if (clockController2 != null) {
                                ArrayList arrayList = (ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) clockController2.getSmallClock().getLayout().getViews(), (Collection) clockController2.getLargeClock().getLayout().getViews());
                                int size = arrayList.size();
                                int i5 = 0;
                                while (i5 < size) {
                                    Object obj2 = arrayList.get(i5);
                                    i5++;
                                    anonymousClass4.$rootView.removeView((View) obj2);
                                }
                            }
                            ref$ObjectRef.element = r1;
                            Resources resources = anonymousClass4.$clockPreviewConfig.getContext().getResources();
                            keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$0 = anonymousClass4;
                            keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$1 = r1;
                            keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$2 = clockSizeSetting;
                            keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.label = 1;
                            if (anonymousClass4.$updateClockAppearance.invoke(r1, resources, keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            clockController = r1;
                        } else {
                            if (i3 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ClockSizeSetting clockSizeSetting2 = (ClockSizeSetting) keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$2;
                            clockController = (ClockController) keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$1;
                            AnonymousClass4 anonymousClass42 = (AnonymousClass4) keyguardPreviewClockViewBinder$bind$3$1$1$4$emit$1.L$0;
                            ResultKt.throwOnFailure(obj);
                            clockSizeSetting = clockSizeSetting2;
                            anonymousClass4 = anonymousClass42;
                        }
                        if (anonymousClass4.$viewModel.shouldHighlightSelectedAffordance) {
                            ArrayList arrayList2 = (ArrayList) CollectionsKt___CollectionsKt.plus((Iterable) clockController.getSmallClock().getLayout().getViews(), (Collection) clockController.getLargeClock().getLayout().getViews());
                            int size2 = arrayList2.size();
                            int i6 = 0;
                            while (i6 < size2) {
                                Object obj3 = arrayList2.get(i6);
                                i6++;
                                ((View) obj3).setAlpha(0.3f);
                            }
                        }
                        Iterator<T> it = clockController.getLargeClock().getLayout().getViews().iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            constraintLayout = anonymousClass4.$rootView;
                            if (!zHasNext) {
                                break;
                            }
                            View view = (View) it.next();
                            ViewParent parent = view.getParent();
                            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                            if (viewGroup != null) {
                                viewGroup.removeView(view);
                            }
                            constraintLayout.addView(view);
                        }
                        for (View view2 : clockController.getSmallClock().getLayout().getViews()) {
                            ViewParent parent2 = view2.getParent();
                            ViewGroup viewGroup2 = parent2 instanceof ViewGroup ? (ViewGroup) parent2 : null;
                            if (viewGroup2 != null) {
                                viewGroup2.removeView(view2);
                            }
                            constraintLayout.addView(view2);
                        }
                        KeyguardPreviewClockViewBinder.INSTANCE.getClass();
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(constraintLayout);
                        int i7 = KeyguardPreviewClockViewBinder.lockId;
                        View viewById = constraintLayout.getViewById(i7);
                        ClockPreviewConfig clockPreviewConfigCopy$default = anonymousClass4.$clockPreviewConfig;
                        if (viewById != null) {
                            clockPreviewConfigCopy$default = ClockPreviewConfig.copy$default(clockPreviewConfigCopy$default, null, false, false, Integer.valueOf(i7), null, 23, null);
                        }
                        clockController.getLargeClock().getLayout().applyPreviewConstraints(clockPreviewConfigCopy$default, constraintSet);
                        clockController.getSmallClock().getLayout().applyPreviewConstraints(clockPreviewConfigCopy$default, constraintSet);
                        int i8 = clockSizeSetting == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockSizeSetting.ordinal()];
                        if (i8 == -1) {
                            i = 4;
                        } else if (i8 != 1) {
                            if (i8 != 2) {
                                throw new NoWhenBranchMatchedException();
                            }
                            i = 4;
                        } else {
                            i = 0;
                        }
                        int i9 = clockSizeSetting == null ? -1 : WhenMappings.$EnumSwitchMapping$0[clockSizeSetting.ordinal()];
                        if (i9 == -1 || i9 == 1) {
                            i4 = 4;
                        } else if (i9 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        ClockSectionKt.setVisibility(constraintSet, clockController.getLargeClock().getLayout().getViews(), i);
                        ClockSectionKt.setVisibility(constraintSet, clockController.getSmallClock().getLayout().getViews(), i4);
                        constraintSet.applyTo(constraintLayout);
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02921(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, Ref$ObjectRef<ClockController> ref$ObjectRef, Function3 function3, ClockPreviewConfig clockPreviewConfig, ConstraintLayout constraintLayout, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardPreviewClockViewModel;
                    this.$lastClock = ref$ObjectRef;
                    this.$updateClockAppearance = function3;
                    this.$clockPreviewConfig = clockPreviewConfig;
                    this.$rootView = constraintLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02921(this.$viewModel, this.$lastClock, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02921) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        KeyguardPreviewClockViewModel keyguardPreviewClockViewModel = this.$viewModel;
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardPreviewClockViewModel.previewClock, keyguardPreviewClockViewModel.selectedClockSize, C02933.INSTANCE);
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
                StandaloneCoroutine standaloneCoroutineLaunchTraced$default = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02921(this.$viewModel, ref$ObjectRef, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, null), 6);
                final ClockRegistry clockRegistry = this.$clockRegistry;
                standaloneCoroutineLaunchTraced$default.invokeOnCompletion(new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardPreviewClockViewBinder$bind$3$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
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
        public AnonymousClass3(KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, Function3 function3, ClockPreviewConfig clockPreviewConfig, ConstraintLayout constraintLayout, ClockRegistry clockRegistry, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardPreviewClockViewModel;
            this.$updateClockAppearance = function3;
            this.$clockPreviewConfig = clockPreviewConfig;
            this.$rootView = constraintLayout;
            this.$clockRegistry = clockRegistry;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$viewModel, this.$updateClockAppearance, this.$clockPreviewConfig, this.$rootView, this.$clockRegistry, (Continuation) obj3);
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

    private KeyguardPreviewClockViewBinder() {
    }

    public static final void bind(KeyguardRootView keyguardRootView, KeyguardPreviewClockViewModel keyguardPreviewClockViewModel, ClockRegistry clockRegistry, Function3 function3, ClockPreviewConfig clockPreviewConfig) {
        RepeatWhenAttachedKt.repeatWhenAttached(keyguardRootView, EmptyCoroutineContext.INSTANCE, new AnonymousClass3(keyguardPreviewClockViewModel, function3, clockPreviewConfig, keyguardRootView, clockRegistry, null));
    }
}
