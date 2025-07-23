package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.LsRune;
import com.android.systemui.biometrics.shared.model.DisplayRotation;
import com.android.systemui.biometrics.shared.model.DisplayRotationKt;
import com.android.systemui.keyguard.shared.model.KeyguardBlueprint;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
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
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardBlueprintViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KeyguardClockViewModel $clockViewModel;
    final /* synthetic */ ConstraintLayout $constraintLayout;
    final /* synthetic */ LogBuffer $log;
    final /* synthetic */ Logger $logger;
    final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
    final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KeyguardClockViewModel $clockViewModel;
        final /* synthetic */ ConstraintLayout $constraintLayout;
        final /* synthetic */ LogBuffer $log;
        final /* synthetic */ Logger $logger;
        final /* synthetic */ KeyguardSmartspaceViewModel $smartspaceViewModel;
        final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C01561 extends SuspendLambda implements Function2 {
            final /* synthetic */ ConstraintLayout $constraintLayout;
            final /* synthetic */ KeyguardBlueprintViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01561(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = keyguardBlueprintViewModel;
                this.$constraintLayout = constraintLayout;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C01561(this.$viewModel, this.$constraintLayout, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C01561) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow pairwise = FlowKt.pairwise(this.$viewModel.displayRotation, null);
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
                                ConstraintLayout constraintLayout2 = ConstraintLayout.this;
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
                    if (pairwise.collect(flowCollector, this) == coroutineSingletons) {
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
                    Flow pairwise = FlowKt.pairwise(this.$viewModel.blueprint, null);
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
                            IntraBlueprintTransition intraBlueprintTransition = new IntraBlueprintTransition(config, KeyguardClockViewModel.this, keyguardSmartspaceViewModel, logBuffer);
                            final ConstraintLayout constraintLayout2 = constraintLayout;
                            final Logger logger2 = logger;
                            final KeyguardClockViewModel keyguardClockViewModel2 = KeyguardClockViewModel.this;
                            keyguardBlueprintViewModel.runTransition(constraintLayout2, intraBlueprintTransition, config, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$2$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    List list;
                                    ConstraintLayout constraintLayout3;
                                    List list2 = config.rebuildSections;
                                    KeyguardBlueprint keyguardBlueprint3 = KeyguardBlueprint.this;
                                    keyguardBlueprint3.getClass();
                                    List list3 = list2;
                                    Iterator it = list3.iterator();
                                    while (it.hasNext()) {
                                        ((KeyguardSection) it.next()).onRebuildBegin();
                                    }
                                    KeyguardBlueprint keyguardBlueprint4 = keyguardBlueprint;
                                    if (keyguardBlueprint4 == null || (list = keyguardBlueprint4.getSections()) == null) {
                                        list = EmptyList.INSTANCE;
                                    }
                                    List list4 = list;
                                    Set subtract = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.intersect(keyguardBlueprint3.getSections(), list4), list3);
                                    Iterator it2 = CollectionsKt___CollectionsKt.subtract(list4, subtract).iterator();
                                    while (true) {
                                        boolean hasNext = it2.hasNext();
                                        constraintLayout3 = constraintLayout2;
                                        if (!hasNext) {
                                            break;
                                        }
                                        ((KeyguardSection) it2.next()).removeViews(constraintLayout3);
                                    }
                                    for (KeyguardSection keyguardSection : CollectionsKt___CollectionsKt.subtract(keyguardBlueprint3.getSections(), subtract)) {
                                        keyguardSection.addViews(constraintLayout3);
                                        keyguardSection.bindData(constraintLayout3);
                                    }
                                    Iterator it3 = list3.iterator();
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
                    if (pairwise.collect(flowCollector, this) == coroutineSingletons) {
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
                            KeyguardBlueprintViewModel keyguardBlueprintViewModel2 = KeyguardBlueprintViewModel.this;
                            final KeyguardBlueprint keyguardBlueprint = (KeyguardBlueprint) keyguardBlueprintViewModel2.blueprint.getValue();
                            final ConstraintLayout constraintLayout2 = constraintLayout;
                            final Logger logger2 = logger;
                            final KeyguardClockViewModel keyguardClockViewModel2 = keyguardClockViewModel;
                            keyguardBlueprintViewModel2.runTransition(constraintLayout2, new IntraBlueprintTransition(keyguardBlueprintViewModel2.keyguardTransitionInteractor.getCurrentState() == KeyguardState.OFF ? IntraBlueprintTransition.Config.copy$default(config, IntraBlueprintTransition.Type.Init) : config, keyguardClockViewModel2, keyguardSmartspaceViewModel, keyguardBlueprintViewModel2.blueprintLog), config, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$bind$1$1$3$1$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    List list = config.rebuildSections;
                                    KeyguardBlueprint keyguardBlueprint2 = KeyguardBlueprint.this;
                                    keyguardBlueprint2.getClass();
                                    boolean isEmpty = list.isEmpty();
                                    ConstraintLayout constraintLayout3 = constraintLayout2;
                                    if (!isEmpty) {
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
        public AnonymousClass1(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer, Logger logger, Continuation continuation) {
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, continuation);
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
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            KeyguardBlueprintViewBinder keyguardBlueprintViewBinder = KeyguardBlueprintViewBinder.INSTANCE;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C01561(this.$viewModel, this.$constraintLayout, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$constraintLayout, this.$logger, null), 6);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$logger, null), 6);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBlueprintViewBinder$bind$1(KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer, Logger logger, Continuation continuation) {
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
        KeyguardBlueprintViewBinder$bind$1 keyguardBlueprintViewBinder$bind$1 = new KeyguardBlueprintViewBinder$bind$1(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, (Continuation) obj3);
        keyguardBlueprintViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return keyguardBlueprintViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$constraintLayout, this.$clockViewModel, this.$smartspaceViewModel, this.$log, this.$logger, null);
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
