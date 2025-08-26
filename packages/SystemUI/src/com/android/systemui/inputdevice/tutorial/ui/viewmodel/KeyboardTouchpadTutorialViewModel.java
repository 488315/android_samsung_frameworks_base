package com.android.systemui.inputdevice.tutorial.ui.viewmodel;

import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.domain.interactor.ConnectionState;
import com.android.systemui.inputdevice.tutorial.domain.interactor.KeyboardTouchpadConnectionInteractor;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.touchpad.tutorial.domain.interactor.TouchpadGesturesInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Collections;
import java.util.Optional;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyboardTouchpadTutorialViewModel extends ViewModel implements DefaultLifecycleObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long AUTO_PROCEED_DELAY;
    public final StateFlowImpl _closeActivity;
    public final StateFlowImpl _screen;
    public final StateFlowImpl closeActivity;
    public ConnectionState connectionState;
    public final Optional gesturesInteractor;
    public final boolean hasTouchpadTutorialScreens;
    public final KeyboardTouchpadConnectionInteractor keyboardTouchpadConnectionInteractor;
    public final InputDeviceTutorialLogger logger;
    public final KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1 screen;
    public final ScreenSequence screenSequence;
    public final ArrayDeque screensBackStack;

    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadTutorialViewModel.this.new AnonymousClass1(continuation);
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
                final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel = KeyboardTouchpadTutorialViewModel.this;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = keyboardTouchpadTutorialViewModel.keyboardTouchpadConnectionInteractor.connectionState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel2 = keyboardTouchpadTutorialViewModel;
                        InputDeviceTutorialLogger inputDeviceTutorialLogger = keyboardTouchpadTutorialViewModel2.logger;
                        ConnectionState connectionState = keyboardTouchpadTutorialViewModel2.connectionState;
                        inputDeviceTutorialLogger.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(0);
                        LogLevel logLevel = LogLevel.INFO;
                        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.bool1 = connectionState.touchpadConnected;
                        logMessageImpl.bool2 = connectionState.keyboardConnected;
                        logBuffer.commit(logMessageObtain);
                        keyboardTouchpadTutorialViewModel2.connectionState = (ConnectionState) obj2;
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

    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            public AnonymousClass1(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
                anonymousClass1.L$0 = (Pair) obj;
                anonymousClass1.L$1 = (Screen) obj2;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                return new Pair(pair.getSecond(), (Screen) this.L$1);
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadTutorialViewModel.this.new AnonymousClass2(continuation);
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
                FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(new Pair(null, null), KeyboardTouchpadTutorialViewModel.this.screen, new AnonymousClass1(null));
                final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel = KeyboardTouchpadTutorialViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.2.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        Screen screen = (Screen) pair.component1();
                        Screen screen2 = (Screen) pair.component2();
                        if (screen2 != null) {
                            int i2 = KeyboardTouchpadTutorialViewModel.$r8$clinit;
                            keyboardTouchpadTutorialViewModel.setupDeviceState(screen, screen2);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadTutorialViewModel.this.new AnonymousClass3(continuation);
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
                final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel = KeyboardTouchpadTutorialViewModel.this;
                final StateFlowImpl stateFlowImpl = keyboardTouchpadTutorialViewModel._screen;
                Flow flow = new Flow() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1

                    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ KeyboardTouchpadTutorialViewModel this$0;

                        /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
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

                        public AnonymousClass2(FlowCollector flowCollector, KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = keyboardTouchpadTutorialViewModel;
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
                                int i3 = KeyboardTouchpadTutorialViewModel.$r8$clinit;
                                KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel = this.this$0;
                                keyboardTouchpadTutorialViewModel.getClass();
                                if (((Screen) obj).getRequiredHardware() == RequiredHardware.TOUCHPAD && !keyboardTouchpadTutorialViewModel.hasTouchpadTutorialScreens) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                        Object objCollect = stateFlowImpl.collect(new AnonymousClass2(flowCollector, keyboardTouchpadTutorialViewModel), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel2 = KeyboardTouchpadTutorialViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel3 = keyboardTouchpadTutorialViewModel2;
                        ConstantStringsLoggerImpl constantStringsLoggerImpl = keyboardTouchpadTutorialViewModel3.logger.$$delegate_0;
                        constantStringsLoggerImpl.getClass();
                        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.ERROR, "Touchpad is connected but touchpad module is missing, something went wrong");
                        keyboardTouchpadTutorialViewModel3._closeActivity.updateState(null, Boolean.TRUE);
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

    public final class AllSupportedScreens implements ScreenSequence {
        public static final AllSupportedScreens INSTANCE = new AllSupportedScreens();

        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Screen.values().length];
                try {
                    iArr[Screen.BACK_GESTURE.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Screen.HOME_GESTURE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Screen.ACTION_KEY.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private AllSupportedScreens() {
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.ScreenSequence
        public final Screen nextScreen(Screen screen) {
            int i = WhenMappings.$EnumSwitchMapping$0[screen.ordinal()];
            if (i == 1) {
                return Screen.HOME_GESTURE;
            }
            if (i == 2) {
                return Screen.ACTION_KEY;
            }
            if (i == 3) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Factory extends AbstractSavedStateViewModelFactory {
        public final Optional gesturesInteractor;
        public final boolean hasTouchpadTutorialScreens;
        public final KeyboardTouchpadConnectionInteractor keyboardTouchpadConnected;
        public final InputDeviceTutorialLogger logger;

        public interface ViewModelFactoryAssistedProvider {
            Factory create(boolean z);
        }

        public Factory(Optional<TouchpadGesturesInteractor> optional, KeyboardTouchpadConnectionInteractor keyboardTouchpadConnectionInteractor, InputDeviceTutorialLogger inputDeviceTutorialLogger, boolean z) {
            this.gesturesInteractor = optional;
            this.keyboardTouchpadConnected = keyboardTouchpadConnectionInteractor;
            this.logger = inputDeviceTutorialLogger;
            this.hasTouchpadTutorialScreens = z;
        }

        @Override // androidx.lifecycle.AbstractSavedStateViewModelFactory
        public final ViewModel create(SavedStateHandle savedStateHandle) {
            return new KeyboardTouchpadTutorialViewModel(this.gesturesInteractor, this.keyboardTouchpadConnected, this.hasTouchpadTutorialScreens, this.logger, savedStateHandle);
        }
    }

    public interface ScreenSequence {
        Screen nextScreen(Screen screen);
    }

    public final class SingleScreenOnly implements ScreenSequence {
        public static final SingleScreenOnly INSTANCE = new SingleScreenOnly();

        private SingleScreenOnly() {
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.ScreenSequence
        public final Screen nextScreen(Screen screen) {
            return null;
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RequiredHardware.values().length];
            try {
                iArr[RequiredHardware.TOUCHPAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RequiredHardware.KEYBOARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1, reason: invalid class name and case insensitive filesystem */
    final class C08751 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08751(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyboardTouchpadTutorialViewModel.this.onAutoProceed(this);
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        AUTO_PROCEED_DELAY = DurationKt.toDuration(3, DurationUnit.SECONDS);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0057  */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KeyboardTouchpadTutorialViewModel(Optional<TouchpadGesturesInteractor> optional, KeyboardTouchpadConnectionInteractor keyboardTouchpadConnectionInteractor, boolean z, InputDeviceTutorialLogger inputDeviceTutorialLogger, SavedStateHandle savedStateHandle) {
        Screen screen;
        this.gesturesInteractor = optional;
        this.keyboardTouchpadConnectionInteractor = keyboardTouchpadConnectionInteractor;
        this.hasTouchpadTutorialScreens = z;
        this.logger = inputDeviceTutorialLogger;
        String str = (String) savedStateHandle.get("tutorial_scope");
        if (str != null) {
            switch (str.hashCode()) {
                case -819522316:
                    if (!str.equals("touchpad")) {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl = inputDeviceTutorialLogger.$$delegate_0;
                        constantStringsLoggerImpl.getClass();
                        LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.WARNING, "Intent didn't specify tutorial scope, starting with default");
                        screen = Screen.BACK_GESTURE;
                        break;
                    } else {
                        screen = Screen.BACK_GESTURE;
                        break;
                    }
                case 96673:
                    if (!str.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL)) {
                    }
                    break;
                case 503739367:
                    if (str.equals("keyboard")) {
                        screen = Screen.ACTION_KEY;
                        break;
                    }
                    break;
                case 1475125586:
                    if (!str.equals("touchpad_back")) {
                    }
                    break;
                case 1475318090:
                    if (str.equals("touchpad_home")) {
                        screen = Screen.HOME_GESTURE;
                        break;
                    }
                    break;
            }
        }
        final StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(screen);
        this._screen = stateFlowImplMutableStateFlow;
        this.screen = new Flow() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1

            /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KeyboardTouchpadTutorialViewModel this$0;

                /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = keyboardTouchpadTutorialViewModel;
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
                        int i3 = KeyboardTouchpadTutorialViewModel.$r8$clinit;
                        KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel = this.this$0;
                        keyboardTouchpadTutorialViewModel.getClass();
                        if (((Screen) obj).getRequiredHardware() != RequiredHardware.TOUCHPAD || keyboardTouchpadTutorialViewModel.hasTouchpadTutorialScreens) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = stateFlowImplMutableStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._closeActivity = stateFlowImplMutableStateFlow2;
        this.closeActivity = stateFlowImplMutableStateFlow2;
        String str2 = (String) savedStateHandle.get("tutorial_scope");
        this.screenSequence = (Intrinsics.areEqual(str2, "touchpad_home") || Intrinsics.areEqual(str2, "touchpad_back")) ? SingleScreenOnly.INSTANCE : AllSupportedScreens.INSTANCE;
        this.screensBackStack = new ArrayDeque(Collections.singletonList(stateFlowImplMutableStateFlow.getValue()));
        this.connectionState = new ConnectionState(false, false);
        CoroutineTracingKt.launchTraced$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 7);
        CoroutineTracingKt.launchTraced$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 7);
        CoroutineTracingKt.launchTraced$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass3(null), 7);
    }

    public final void clearDeviceStateForScreen(Screen screen) {
        int i = WhenMappings.$EnumSwitchMapping$0[screen.getRequiredHardware().ordinal()];
        if (i == 1) {
            ((TouchpadGesturesInteractor) this.gesturesInteractor.get()).enableGestures();
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onAutoProceed(Continuation continuation) {
        C08751 c08751;
        if (continuation instanceof C08751) {
            c08751 = (C08751) continuation;
            int i = c08751.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08751.label = i - Integer.MIN_VALUE;
            } else {
                c08751 = new C08751(continuation);
            }
        }
        Object obj = c08751.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08751.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            c08751.L$0 = this;
            c08751.label = 1;
            if (DelayKt.m3469delayVtjQ1oo(AUTO_PROCEED_DELAY, c08751) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (KeyboardTouchpadTutorialViewModel) c08751.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.progressToNextScreen();
        return Unit.INSTANCE;
    }

    public final void onBack() {
        ArrayDeque arrayDeque = this.screensBackStack;
        if (arrayDeque.size <= 1) {
            this._closeActivity.updateState(null, Boolean.TRUE);
            return;
        }
        arrayDeque.removeLast();
        Screen screen = (Screen) arrayDeque.last();
        InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(6);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = screen.toString();
        logBuffer.commit(logMessageObtain);
        this._screen.setValue(arrayDeque.last());
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        clearDeviceStateForScreen((Screen) this._screen.getValue());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStart$1() {
        setupDeviceState(null, (Screen) this._screen.getValue());
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStop$1() {
        clearDeviceStateForScreen((Screen) this._screen.getValue());
    }

    public final void progressToNextScreen() {
        InputDeviceTutorialLogger inputDeviceTutorialLogger;
        boolean z;
        StateFlowImpl stateFlowImpl = this._screen;
        Screen screen = (Screen) stateFlowImpl.getValue();
        ScreenSequence screenSequence = this.screenSequence;
        Screen screenNextScreen = screenSequence.nextScreen(screen);
        while (true) {
            inputDeviceTutorialLogger = this.logger;
            if (screenNextScreen == null) {
                break;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[screenNextScreen.getRequiredHardware().ordinal()];
            if (i == 1) {
                z = this.connectionState.touchpadConnected;
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                z = this.connectionState.keyboardConnected;
            }
            if (z) {
                break;
            }
            inputDeviceTutorialLogger.getClass();
            LogLevel logLevel = LogLevel.WARNING;
            InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = screenNextScreen.toString();
            logBuffer.commit(logMessageObtain);
            screenNextScreen = screenSequence.nextScreen(screenNextScreen);
        }
        if (screenNextScreen == null) {
            ConstantStringsLoggerImpl constantStringsLoggerImpl = inputDeviceTutorialLogger.$$delegate_0;
            constantStringsLoggerImpl.getClass();
            LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, "Final screen reached, closing tutorial");
            this._closeActivity.updateState(null, Boolean.TRUE);
            return;
        }
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda02 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(8);
        LogLevel logLevel2 = LogLevel.INFO;
        LogBuffer logBuffer2 = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("InputDeviceTutorial", logLevel2, inputDeviceTutorialLogger$$ExternalSyntheticLambda02, null);
        ((LogMessageImpl) logMessageObtain2).str1 = screenNextScreen.toString();
        logBuffer2.commit(logMessageObtain2);
        stateFlowImpl.updateState(null, screenNextScreen);
        this.screensBackStack.addLast(screenNextScreen);
    }

    public final void setupDeviceState(Screen screen, Screen screen2) {
        String string;
        InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(2);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        if (screen == null || (string = screen.toString()) == null) {
            string = "NO_SCREEN";
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = string;
        logMessageImpl.str2 = screen2.toString();
        logBuffer.commit(logMessageObtain);
        if ((screen != null ? screen.getRequiredHardware() : null) == screen2.getRequiredHardware()) {
            return;
        }
        if (screen != null) {
            clearDeviceStateForScreen(screen);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[screen2.getRequiredHardware().ordinal()];
        if (i == 1) {
            ((TouchpadGesturesInteractor) this.gesturesInteractor.get()).disableGestures();
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
    }
}
