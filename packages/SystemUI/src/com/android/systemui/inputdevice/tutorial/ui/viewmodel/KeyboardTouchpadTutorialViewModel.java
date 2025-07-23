package com.android.systemui.inputdevice.tutorial.ui.viewmodel;

import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
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
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel2 = KeyboardTouchpadTutorialViewModel.this;
                        InputDeviceTutorialLogger inputDeviceTutorialLogger = keyboardTouchpadTutorialViewModel2.logger;
                        ConnectionState connectionState = keyboardTouchpadTutorialViewModel2.connectionState;
                        inputDeviceTutorialLogger.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(0);
                        LogLevel logLevel = LogLevel.INFO;
                        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
                        LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.bool1 = connectionState.touchpadConnected;
                        logMessageImpl.bool2 = connectionState.keyboardConnected;
                        logBuffer.commit(obtain);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            KeyboardTouchpadTutorialViewModel.this.setupDeviceState(screen, screen2);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L54
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                r7 = r6
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen r7 = (com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen) r7
                                int r2 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.$r8$clinit
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel r2 = r5.this$0
                                r2.getClass()
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.RequiredHardware r7 = r7.getRequiredHardware()
                                com.android.systemui.inputdevice.tutorial.ui.viewmodel.RequiredHardware r4 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.RequiredHardware.TOUCHPAD
                                if (r7 != r4) goto L54
                                boolean r7 = r2.hasTouchpadTutorialScreens
                                if (r7 == 0) goto L49
                                goto L54
                            L49:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r6, r0)
                                if (r5 != r1) goto L54
                                return r1
                            L54:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3$invokeSuspend$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, keyboardTouchpadTutorialViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel2 = KeyboardTouchpadTutorialViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel3 = KeyboardTouchpadTutorialViewModel.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AllSupportedScreens implements ScreenSequence {
        public static final AllSupportedScreens INSTANCE = new AllSupportedScreens();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory extends AbstractSavedStateViewModelFactory {
        public final Optional gesturesInteractor;
        public final boolean hasTouchpadTutorialScreens;
        public final KeyboardTouchpadConnectionInteractor keyboardTouchpadConnected;
        public final InputDeviceTutorialLogger logger;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ScreenSequence {
        Screen nextScreen(Screen screen);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SingleScreenOnly implements ScreenSequence {
        public static final SingleScreenOnly INSTANCE = new SingleScreenOnly();

        private SingleScreenOnly() {
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.ScreenSequence
        public final Screen nextScreen(Screen screen) {
            return null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        AUTO_PROCEED_DELAY = DurationKt.toDuration(3, DurationUnit.SECONDS);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
    
        if (r5.equals("touchpad_back") == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0054, code lost:
    
        r5 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen.BACK_GESTURE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0047, code lost:
    
        if (r5.equals(com.android.systemui.util.SystemUIAnalytics.QPNE_VID_COVER_ALL) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0051, code lost:
    
        if (r5.equals("touchpad") == false) goto L25;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public KeyboardTouchpadTutorialViewModel(java.util.Optional<com.android.systemui.touchpad.tutorial.domain.interactor.TouchpadGesturesInteractor> r4, com.android.systemui.inputdevice.tutorial.domain.interactor.KeyboardTouchpadConnectionInteractor r5, boolean r6, com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger r7, androidx.lifecycle.SavedStateHandle r8) {
        /*
            r3 = this;
            r3.<init>()
            r3.gesturesInteractor = r4
            r3.keyboardTouchpadConnectionInteractor = r5
            r3.hasTouchpadTutorialScreens = r6
            r3.logger = r7
            java.lang.String r4 = "tutorial_scope"
            java.lang.Object r5 = r8.get(r4)
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r6 = "touchpad_back"
            java.lang.String r0 = "touchpad_home"
            if (r5 == 0) goto L57
            int r1 = r5.hashCode()
            switch(r1) {
                case -819522316: goto L4a;
                case 96673: goto L41;
                case 503739367: goto L35;
                case 1475125586: goto L2e;
                case 1475318090: goto L24;
                default: goto L23;
            }
        L23:
            goto L57
        L24:
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L2b
            goto L57
        L2b:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen r5 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen.HOME_GESTURE
            goto L69
        L2e:
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L54
            goto L57
        L35:
            java.lang.String r1 = "keyboard"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L3e
            goto L57
        L3e:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen r5 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen.ACTION_KEY
            goto L69
        L41:
            java.lang.String r1 = "all"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L54
            goto L57
        L4a:
            java.lang.String r1 = "touchpad"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L54
            goto L57
        L54:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen r5 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen.BACK_GESTURE
            goto L69
        L57:
            com.android.systemui.log.ConstantStringsLoggerImpl r5 = r7.$$delegate_0
            r5.getClass()
            com.android.systemui.log.core.LogLevel r7 = com.android.systemui.log.core.LogLevel.WARNING
            java.lang.String r1 = r5.tag
            com.android.systemui.log.LogBuffer r5 = r5.buffer
            java.lang.String r2 = "Intent didn't specify tutorial scope, starting with default"
            com.android.systemui.log.LogBuffer.log$default(r5, r1, r7, r2)
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen r5 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen.BACK_GESTURE
        L69:
            kotlinx.coroutines.flow.StateFlowImpl r5 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r5)
            r3._screen = r5
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1 r7 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$special$$inlined$filter$1
            r7.<init>()
            r3.screen = r7
            java.lang.Boolean r7 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.StateFlowImpl r7 = kotlinx.coroutines.flow.StateFlowKt.MutableStateFlow(r7)
            r3._closeActivity = r7
            r3.closeActivity = r7
            java.lang.Object r4 = r8.get(r4)
            java.lang.String r4 = (java.lang.String) r4
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r0)
            if (r7 != 0) goto L96
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r6)
            if (r4 == 0) goto L93
            goto L96
        L93:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$AllSupportedScreens r4 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.AllSupportedScreens.INSTANCE
            goto L98
        L96:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$SingleScreenOnly r4 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.SingleScreenOnly.INSTANCE
        L98:
            r3.screenSequence = r4
            kotlin.collections.ArrayDeque r4 = new kotlin.collections.ArrayDeque
            java.lang.Object r5 = r5.getValue()
            java.util.List r5 = java.util.Collections.singletonList(r5)
            java.util.Collection r5 = (java.util.Collection) r5
            r4.<init>(r5)
            r3.screensBackStack = r4
            com.android.systemui.inputdevice.tutorial.domain.interactor.ConnectionState r4 = new com.android.systemui.inputdevice.tutorial.domain.interactor.ConnectionState
            r5 = 0
            r4.<init>(r5, r5)
            r3.connectionState = r4
            androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope r4 = androidx.lifecycle.ViewModelKt.getViewModelScope(r3)
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$1 r5 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$1
            r6 = 0
            r5.<init>(r6)
            r7 = 7
            com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r4, r6, r6, r5, r7)
            androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope r4 = androidx.lifecycle.ViewModelKt.getViewModelScope(r3)
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$2 r5 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$2
            r5.<init>(r6)
            com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r4, r6, r6, r5, r7)
            androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope r4 = androidx.lifecycle.ViewModelKt.getViewModelScope(r3)
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3 r5 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$3
            r5.<init>(r6)
            com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r4, r6, r6, r5, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.<init>(java.util.Optional, com.android.systemui.inputdevice.tutorial.domain.interactor.KeyboardTouchpadConnectionInteractor, boolean, com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger, androidx.lifecycle.SavedStateHandle):void");
    }

    public final void clearDeviceStateForScreen(Screen screen) {
        int i = WhenMappings.$EnumSwitchMapping$0[screen.getRequiredHardware().ordinal()];
        if (i == 1) {
            ((TouchpadGesturesInteractor) this.gesturesInteractor.get()).enableGestures();
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onAutoProceed(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1 r0 = (com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1 r0 = new com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel$onAutoProceed$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel r4 = (com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            long r2 = com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.AUTO_PROCEED_DELAY
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r2, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            r4.progressToNextScreen()
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel.onAutoProceed(kotlin.coroutines.Continuation):java.lang.Object");
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
        LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = screen.toString();
        logBuffer.commit(obtain);
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
        Screen nextScreen = screenSequence.nextScreen(screen);
        while (true) {
            inputDeviceTutorialLogger = this.logger;
            if (nextScreen == null) {
                break;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[nextScreen.getRequiredHardware().ordinal()];
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
            LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = nextScreen.toString();
            logBuffer.commit(obtain);
            nextScreen = screenSequence.nextScreen(nextScreen);
        }
        if (nextScreen == null) {
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
        LogMessage obtain2 = logBuffer2.obtain("InputDeviceTutorial", logLevel2, inputDeviceTutorialLogger$$ExternalSyntheticLambda02, null);
        ((LogMessageImpl) obtain2).str1 = nextScreen.toString();
        logBuffer2.commit(obtain2);
        stateFlowImpl.updateState(null, nextScreen);
        this.screensBackStack.addLast(nextScreen);
    }

    public final void setupDeviceState(Screen screen, Screen screen2) {
        String str;
        InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(2);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        if (screen == null || (str = screen.toString()) == null) {
            str = "NO_SCREEN";
        }
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = screen2.toString();
        logBuffer.commit(obtain);
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
