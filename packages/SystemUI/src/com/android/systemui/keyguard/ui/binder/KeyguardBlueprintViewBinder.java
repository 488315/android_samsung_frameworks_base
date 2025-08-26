package com.android.systemui.keyguard.ui.binder;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.DisplayRotationKt;
import com.android.systemui.customization.R$id;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockLogger;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class KeyguardBlueprintViewBinder {
    public static final KeyguardBlueprintViewBinder INSTANCE = new KeyguardBlueprintViewBinder();
    public static final String TAG = "KeyguardBlueprintViewBinder";

    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ KeyguardClockViewModel $clockViewModel;
        final /* synthetic */ ConstraintLayout $constraintLayout;
        final /* synthetic */ LogBuffer $log;
        final /* synthetic */ Logger $logger;
        final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
        final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C02651 extends SuspendLambda implements Function2 {
            final /* synthetic */ KeyguardClockViewModel $clockViewModel;
            final /* synthetic */ ConstraintLayout $constraintLayout;
            final /* synthetic */ LogBuffer $log;
            final /* synthetic */ Logger $logger;
            final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
            final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02661 extends SuspendLambda implements Function2 {
                final /* synthetic */ ConstraintLayout $constraintLayout;
                final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02661(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBlueprintViewModel;
                    this.$constraintLayout = constraintLayout;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02661(this.$viewModel, this.$constraintLayout, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02661) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow flowPairwise = FlowKt.pairwise(this.$viewModel.displayRotation, null);
                        final ConstraintLayout constraintLayout = this.$constraintLayout;
                        final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$viewModel;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder.bind.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                WithPrev withPrev = (WithPrev) obj2;
                                DisplayRotation displayRotation = (DisplayRotation) withPrev.component1();
                                DisplayRotation displayRotation2 = (DisplayRotation) withPrev.component2();
                                if (displayRotation != null && Math.abs(DisplayRotationKt.toRotation(displayRotation) - DisplayRotationKt.toRotation(displayRotation2)) == 2) {
                                    ConstraintSet constraintSet = new ConstraintSet();
                                    ConstraintLayout constraintLayout2 = constraintLayout;
                                    constraintSet.clone(constraintLayout2);
                                    ConstraintSet.Layout layout = new ConstraintSet.Layout();
                                    for (int i2 : constraintSet.getKnownIds()) {
                                        constraintSet.getConstraint(i2).layout.copyFrom(layout);
                                    }
                                    ((KeyguardBlueprint) keyguardBlueprintViewModel.blueprint.getValue()).applyConstraints(constraintSet);
                                    constraintSet.applyTo(constraintLayout2);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowPairwise.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardClockViewModel $clockViewModel;
                final /* synthetic */ ConstraintLayout $constraintLayout;
                final /* synthetic */ LogBuffer $log;
                final /* synthetic */ Logger $logger;
                final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
                final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer, ConstraintLayout constraintLayout, Logger logger, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBlueprintViewModel;
                    this.$clockViewModel = keyguardClockViewModel;
                    this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                    this.$log = logBuffer;
                    this.$constraintLayout = constraintLayout;
                    this.$logger = logger;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$constraintLayout, this.$logger, continuation);
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
                        Flow flowPairwise = FlowKt.pairwise(this.$viewModel.blueprint, null);
                        final KeyguardClockViewModel keyguardClockViewModel = this.$clockViewModel;
                        final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.$smartspaceViewModel;
                        final LogBuffer logBuffer = this.$log;
                        final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$viewModel;
                        final ConstraintLayout constraintLayout = this.$constraintLayout;
                        final Logger logger = this.$logger;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder.bind.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                WithPrev withPrev = (WithPrev) obj2;
                                final KeyguardBlueprint keyguardBlueprint = (KeyguardBlueprint) withPrev.component1();
                                final KeyguardBlueprint keyguardBlueprint2 = (KeyguardBlueprint) withPrev.component2();
                                IntraBlueprintTransition.Config.Companion.getClass();
                                final IntraBlueprintTransition.Config config = IntraBlueprintTransition.Config.DEFAULT;
                                IntraBlueprintTransition intraBlueprintTransition = new IntraBlueprintTransition(config, keyguardClockViewModel, keyguardSmartspaceViewModel, logBuffer);
                                final ConstraintLayout constraintLayout2 = constraintLayout;
                                final Logger logger2 = logger;
                                final KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                                keyguardBlueprintViewModel.runTransition(constraintLayout2, intraBlueprintTransition, config, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$2$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        List sections;
                                        ConstraintLayout constraintLayout3;
                                        List list = config.rebuildSections;
                                        KeyguardBlueprint keyguardBlueprint3 = keyguardBlueprint2;
                                        keyguardBlueprint3.getClass();
                                        List list2 = list;
                                        Iterator it = list2.iterator();
                                        while (it.hasNext()) {
                                            ((KeyguardSection) it.next()).onRebuildBegin();
                                        }
                                        KeyguardBlueprint keyguardBlueprint4 = keyguardBlueprint;
                                        if (keyguardBlueprint4 == null || (sections = keyguardBlueprint4.getSections()) == null) {
                                            sections = EmptyList.INSTANCE;
                                        }
                                        List list3 = sections;
                                        Set setSubtract = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.intersect(keyguardBlueprint3.getSections(), list3), list2);
                                        Iterator it2 = CollectionsKt___CollectionsKt.subtract(list3, setSubtract).iterator();
                                        while (true) {
                                            boolean zHasNext = it2.hasNext();
                                            constraintLayout3 = constraintLayout2;
                                            if (!zHasNext) {
                                                break;
                                            }
                                            ((KeyguardSection) it2.next()).removeViews(constraintLayout3);
                                        }
                                        for (KeyguardSection keyguardSection : CollectionsKt___CollectionsKt.subtract(keyguardBlueprint3.getSections(), setSubtract)) {
                                            keyguardSection.addViews(constraintLayout3);
                                            keyguardSection.bindData(constraintLayout3);
                                        }
                                        Iterator it3 = list2.iterator();
                                        while (it3.hasNext()) {
                                            ((KeyguardSection) it3.next()).onRebuildEnd();
                                        }
                                        ConstraintSet constraintSet = new ConstraintSet();
                                        constraintSet.clone(constraintLayout3);
                                        ConstraintSet.Layout layout = new ConstraintSet.Layout();
                                        for (int i2 : constraintSet.getKnownIds()) {
                                            constraintSet.getConstraint(i2).layout.copyFrom(layout);
                                        }
                                        keyguardBlueprint3.applyConstraints(constraintSet);
                                        KeyguardBlueprintViewBinder.access$logConstraintSet(KeyguardBlueprintViewBinder.INSTANCE, logger2, constraintSet, keyguardClockViewModel2);
                                        constraintSet.applyTo(constraintLayout3);
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowPairwise.collect(flowCollector, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ KeyguardClockViewModel $clockViewModel;
                final /* synthetic */ ConstraintLayout $constraintLayout;
                final /* synthetic */ Logger $logger;
                final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
                final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, Logger logger, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = keyguardBlueprintViewModel;
                    this.$constraintLayout = constraintLayout;
                    this.$clockViewModel = keyguardClockViewModel;
                    this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                    this.$logger = logger;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$logger, continuation);
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
                        final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.$viewModel;
                        SharedFlowImpl sharedFlowImpl = keyguardBlueprintViewModel.refreshTransition;
                        final ConstraintLayout constraintLayout = this.$constraintLayout;
                        final KeyguardClockViewModel keyguardClockViewModel = this.$clockViewModel;
                        final KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.$smartspaceViewModel;
                        final Logger logger = this.$logger;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder.bind.1.1.3.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                final IntraBlueprintTransition.Config config = (IntraBlueprintTransition.Config) obj2;
                                KeyguardBlueprintViewModel keyguardBlueprintViewModel2 = keyguardBlueprintViewModel;
                                final KeyguardBlueprint keyguardBlueprint = (KeyguardBlueprint) keyguardBlueprintViewModel2.blueprint.getValue();
                                final ConstraintLayout constraintLayout2 = constraintLayout;
                                final Logger logger2 = logger;
                                final KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                                keyguardBlueprintViewModel2.runTransition(constraintLayout2, new IntraBlueprintTransition(keyguardBlueprintViewModel2.keyguardTransitionInteractor.getCurrentState() == KeyguardState.OFF ? IntraBlueprintTransition.Config.copy$default(config, IntraBlueprintTransition.Type.Init) : config, keyguardClockViewModel2, keyguardSmartspaceViewModel, keyguardBlueprintViewModel2.blueprintLog), config, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$3$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        List list = config.rebuildSections;
                                        KeyguardBlueprint keyguardBlueprint2 = keyguardBlueprint;
                                        keyguardBlueprint2.getClass();
                                        boolean zIsEmpty = list.isEmpty();
                                        ConstraintLayout constraintLayout3 = constraintLayout2;
                                        if (!zIsEmpty) {
                                            List<KeyguardSection> list2 = list;
                                            Iterator it = list2.iterator();
                                            while (it.hasNext()) {
                                                ((KeyguardSection) it.next()).onRebuildBegin();
                                            }
                                            Iterator it2 = list2.iterator();
                                            while (it2.hasNext()) {
                                                ((KeyguardSection) it2.next()).removeViews(constraintLayout3);
                                            }
                                            for (KeyguardSection keyguardSection : list2) {
                                                keyguardSection.addViews(constraintLayout3);
                                                keyguardSection.bindData(constraintLayout3);
                                            }
                                            Iterator it3 = list2.iterator();
                                            while (it3.hasNext()) {
                                                ((KeyguardSection) it3.next()).onRebuildEnd();
                                            }
                                        }
                                        ConstraintSet constraintSet = new ConstraintSet();
                                        constraintSet.clone(constraintLayout3);
                                        keyguardBlueprint2.applyConstraints(constraintSet);
                                        KeyguardBlueprintViewBinder.access$logConstraintSet(KeyguardBlueprintViewBinder.INSTANCE, logger2, constraintSet, keyguardClockViewModel2);
                                        constraintSet.applyTo(constraintLayout3);
                                        return Unit.INSTANCE;
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        sharedFlowImpl.getClass();
                        if (SharedFlowImpl.collect$suspendImpl(sharedFlowImpl, flowCollector, this) == coroutineSingletons) {
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
            public C02651(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer, Logger logger, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBlueprintViewModel;
                this.$constraintLayout = constraintLayout;
                this.$clockViewModel = keyguardClockViewModel;
                this.$smartspaceViewModel = keyguardSmartspaceViewModel;
                this.$log = logBuffer;
                this.$logger = logger;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02651 c02651 = new C02651(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, continuation);
                c02651.L$0 = obj;
                return c02651;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02651) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                KeyguardBlueprintViewBinder keyguardBlueprintViewBinder = KeyguardBlueprintViewBinder.INSTANCE;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02661(this.$viewModel, this.$constraintLayout, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$constraintLayout, this.$logger, null), 6);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$logger, null), 6);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer, Logger logger, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = keyguardBlueprintViewModel;
            this.$constraintLayout = constraintLayout;
            this.$clockViewModel = keyguardClockViewModel;
            this.$smartspaceViewModel = keyguardSmartspaceViewModel;
            this.$log = logBuffer;
            this.$logger = logger;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, (Continuation) obj3);
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
                C02651 c02651 = new C02651(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02651, this) == coroutineSingletons) {
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

    private KeyguardBlueprintViewBinder() {
    }

    public static final void access$logConstraintSet(KeyguardBlueprintViewBinder keyguardBlueprintViewBinder, Logger logger, ConstraintSet constraintSet, KeyguardClockViewModel keyguardClockViewModel) {
        keyguardBlueprintViewBinder.getClass();
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController == null) {
            return;
        }
        final int i = 0;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        sbM.append(str2);
                        return sbM.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        };
        LogLevel logLevel = LogLevel.INFO;
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), logLevel, function1, null);
        int i2 = R$id.lockscreen_clock_view;
        logMessageObtain.setInt1(constraintSet.getVisibility(i2));
        logMessageObtain.setStr1(String.valueOf(constraintSet.getConstraint(i2).propertySet.alpha));
        logMessageObtain.setStr2(String.valueOf(constraintSet.getConstraint(i2).transform.scaleX));
        logger.getBuffer().commit(logMessageObtain);
        final int i3 = 1;
        LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), logLevel, new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i3) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        sbM.append(str2);
                        return sbM.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        }, null);
        int id = clockController.getLargeClock().getLayout().getViews().get(0).getId();
        logMessageObtain2.setInt1(constraintSet.getVisibility(id));
        logMessageObtain2.setStr1(String.valueOf(constraintSet.getConstraint(id).propertySet.alpha));
        logMessageObtain2.setStr2(String.valueOf(constraintSet.getConstraint(id).transform.scaleX));
        logMessageObtain2.setStr3(String.valueOf(constraintSet.getConstraint(id).transform.transformPivotX));
        logger.getBuffer().commit(logMessageObtain2);
        final int i4 = 2;
        LogMessage logMessageObtain3 = logger.getBuffer().obtain(logger.getTag(), logLevel, new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i4) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        sbM.append(str2);
                        return sbM.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        }, null);
        logMessageObtain3.setInt1(constraintSet.getVisibility(R.id.date_smartspace_view));
        logMessageObtain3.setStr1(String.valueOf(constraintSet.getConstraint(R.id.date_smartspace_view).propertySet.alpha));
        logger.getBuffer().commit(logMessageObtain3);
    }

    public static final void bind(ConstraintLayout constraintLayout, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer) {
        RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(keyguardBlueprintViewModel, constraintLayout, keyguardClockViewModel, keyguardSmartspaceViewModel, logBuffer, new Logger(logBuffer, TAG), null));
    }
}
