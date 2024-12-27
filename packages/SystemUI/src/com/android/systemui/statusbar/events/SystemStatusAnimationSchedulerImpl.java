package com.android.systemui.statusbar.events;

import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.DeviceConfig;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemStatusAnimationSchedulerImpl implements SystemStatusAnimationScheduler, DesktopManager.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SystemEventChipAnimationController chipAnimationController;
    public final SystemEventCoordinator coordinator;
    public final CoroutineScope coroutineScope;
    public StatusEvent currentlyDisplayedEvent;
    public Job currentlyRunningAnimationJob;
    public final DesktopManager desktopManager;
    public Job eventCancellationJob;
    public boolean hasPersistentDot;
    public final HeaderBatteryStatusChipController headerBatteryChipController;
    public final SystemStatusAnimationSchedulerLogger logger;
    public boolean showingDotWhileChipAnim;
    public boolean statusBarHidden;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public final StatusBarWindowController statusBarWindowController;
    public final StatusBarWindowStateController statusBarWindowStateController;
    public final SystemClock systemClock;
    public final StateFlowImpl scheduledEvent = StateFlowKt.MutableStateFlow(null);
    public final StateFlowImpl animationState = StateFlowKt.MutableStateFlow(0);
    public final Set listeners = new LinkedHashSet();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1$1, reason: invalid class name and collision with other inner class name */
        final class C01861 extends SuspendLambda implements Function3 {
            /* synthetic */ int I$0;
            /* synthetic */ Object L$0;
            int label;

            public C01861(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int intValue = ((Number) obj).intValue();
                C01861 c01861 = new C01861((Continuation) obj3);
                c01861.I$0 = intValue;
                c01861.L$0 = (StatusEvent) obj2;
                return c01861.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                int i = this.I$0;
                return new Pair(new Integer(i), (StatusEvent) this.L$0);
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SystemStatusAnimationSchedulerImpl.this.new AnonymousClass1(continuation);
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
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                Flow debounce = FlowKt.debounce(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(systemStatusAnimationSchedulerImpl.animationState, systemStatusAnimationSchedulerImpl.scheduledEvent, new C01861(null)), 500L);
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.1.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        int intValue = ((Number) pair.component1()).intValue();
                        StatusEvent statusEvent = (StatusEvent) pair.component2();
                        if (intValue == 1 && statusEvent != null) {
                            int i2 = SystemStatusAnimationSchedulerImpl.$r8$clinit;
                            SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl3 = SystemStatusAnimationSchedulerImpl.this;
                            systemStatusAnimationSchedulerImpl3.getClass();
                            Assert.isMainThread();
                            systemStatusAnimationSchedulerImpl3.hasPersistentDot = statusEvent.getForceVisible();
                            boolean showAnimation = statusEvent.getShowAnimation();
                            StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl3.scheduledEvent;
                            if (showAnimation || !statusEvent.getForceVisible()) {
                                systemStatusAnimationSchedulerImpl3.currentlyDisplayedEvent = statusEvent;
                                systemStatusAnimationSchedulerImpl3.chipAnimationController.prepareChipAnimation(statusEvent.getViewCreator(), stateFlowImpl.getValue() instanceof BatteryEvent);
                                if (stateFlowImpl.getValue() instanceof BatteryEvent) {
                                    Intrinsics.checkNotNull(stateFlowImpl);
                                    Object value = stateFlowImpl.getValue();
                                    Intrinsics.checkNotNull(value);
                                    Function1 viewCreator = ((StatusEvent) value).getViewCreator();
                                    HeaderBatteryStatusChipController headerBatteryStatusChipController = systemStatusAnimationSchedulerImpl3.headerBatteryChipController;
                                    BackgroundAnimatableView backgroundAnimatableView = (BackgroundAnimatableView) viewCreator.invoke(headerBatteryStatusChipController.context);
                                    FrameLayout frameLayout = headerBatteryStatusChipController.batteryChipContainer;
                                    backgroundAnimatableView.getClass();
                                    View view = (View) backgroundAnimatableView;
                                    frameLayout.addView(view, new FrameLayout.LayoutParams(-2, -2));
                                    view.setVisibility(8);
                                    headerBatteryStatusChipController.currentAnimatedView = backgroundAnimatableView;
                                }
                                systemStatusAnimationSchedulerImpl3.currentlyRunningAnimationJob = BuildersKt.launch$default(systemStatusAnimationSchedulerImpl3.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(systemStatusAnimationSchedulerImpl3, statusEvent, null), 3);
                            } else {
                                systemStatusAnimationSchedulerImpl3.animationState.updateState(null, 5);
                                systemStatusAnimationSchedulerImpl3.notifyTransitionToPersistentDot(statusEvent);
                            }
                            stateFlowImpl.setValue(null);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (debounce.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SystemStatusAnimationSchedulerImpl.this.new AnonymousClass2(continuation);
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
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl.animationState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        int intValue = ((Number) obj2).intValue();
                        final SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = SystemStatusAnimationSchedulerImpl.this.logger;
                        if (systemStatusAnimationSchedulerLogger != null) {
                            LogLevel logLevel = LogLevel.DEBUG;
                            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerLogger$logAnimationStateUpdate$2
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj3) {
                                    SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger2 = SystemStatusAnimationSchedulerLogger.this;
                                    int int1 = ((LogMessage) obj3).getInt1();
                                    systemStatusAnimationSchedulerLogger2.getClass();
                                    return "AnimationState update: ".concat(SystemStatusAnimationSchedulerLogger.name(int1));
                                }
                            };
                            LogBuffer logBuffer = systemStatusAnimationSchedulerLogger.logBuffer;
                            LogMessage obtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, function1, null);
                            ((LogMessageImpl) obtain).int1 = intValue;
                            logBuffer.commit(obtain);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SystemStatusAnimationSchedulerImpl.this.new AnonymousClass3(continuation);
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
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                ReadonlyStateFlow readonlyStateFlow = ((StatusBarModeRepositoryImpl) systemStatusAnimationSchedulerImpl.statusBarModeRepository).defaultDisplay.isInFullscreenMode;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                        systemStatusAnimationSchedulerImpl2.statusBarHidden = booleanValue || systemStatusAnimationSchedulerImpl2.statusBarWindowStateController.windowState != 0;
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SystemStatusAnimationSchedulerImpl(SystemEventCoordinator systemEventCoordinator, SystemEventChipAnimationController systemEventChipAnimationController, StatusBarWindowController statusBarWindowController, DumpManager dumpManager, SystemClock systemClock, CoroutineScope coroutineScope, SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger, StatusBarModeRepositoryStore statusBarModeRepositoryStore, DesktopManager desktopManager, StatusBarWindowStateController statusBarWindowStateController, HeaderBatteryStatusChipController headerBatteryStatusChipController) {
        this.coordinator = systemEventCoordinator;
        this.chipAnimationController = systemEventChipAnimationController;
        this.statusBarWindowController = statusBarWindowController;
        this.systemClock = systemClock;
        this.coroutineScope = coroutineScope;
        this.logger = systemStatusAnimationSchedulerLogger;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.desktopManager = desktopManager;
        this.statusBarWindowStateController = statusBarWindowStateController;
        this.headerBatteryChipController = headerBatteryStatusChipController;
        systemEventCoordinator.scheduler = this;
        dumpManager.registerCriticalDumpable("SystemStatusAnimationSchedulerImpl", this);
        desktopManager.registerCallback(this);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(null), 3);
    }

    public static final void access$runChipDisappearAnimation(final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl) {
        AnimatorSet notifyTransitionToPersistentDot;
        systemStatusAnimationSchedulerImpl.getClass();
        Assert.isMainThread();
        boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
        ArrayList arrayList = new ArrayList();
        boolean z2 = systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent;
        Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
        while (it.hasNext()) {
            Animator onSystemEventAnimationFinish = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z, z2);
            if (onSystemEventAnimationFinish != null) {
                arrayList.add(onSystemEventAnimationFinish);
            }
        }
        arrayList.add(systemStatusAnimationSchedulerImpl.chipAnimationController.onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z, z2));
        if (systemStatusAnimationSchedulerImpl.hasPersistentDot && (notifyTransitionToPersistentDot = systemStatusAnimationSchedulerImpl.notifyTransitionToPersistentDot(systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent)) != null) {
            arrayList.add(notifyTransitionToPersistentDot);
        }
        systemStatusAnimationSchedulerImpl.headerBatteryChipController.onSystemEventAnimationFinish(z, z, z2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        systemStatusAnimationSchedulerImpl.animationState.updateState(null, 4);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipDisappearAnimation$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                int i;
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl2.animationState;
                if (systemStatusAnimationSchedulerImpl2.scheduledEvent.getValue() != null) {
                    i = 1;
                    if ((systemStatusAnimationSchedulerImpl2.scheduledEvent.getValue() instanceof BatteryEvent) && systemStatusAnimationSchedulerImpl2.hasPersistentDot) {
                        systemStatusAnimationSchedulerImpl2.showingDotWhileChipAnim = true;
                    }
                } else {
                    i = 5;
                    if (!systemStatusAnimationSchedulerImpl2.hasPersistentDot && !systemStatusAnimationSchedulerImpl2.showingDotWhileChipAnim) {
                        i = 0;
                    }
                }
                stateFlowImpl.updateState(null, Integer.valueOf(i));
                systemStatusAnimationSchedulerImpl2.statusBarWindowController.setForceStatusBarVisible(false);
            }
        });
        animatorSet.start();
        systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent = null;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        SystemStatusAnimationCallback systemStatusAnimationCallback = (SystemStatusAnimationCallback) obj;
        Assert.isMainThread();
        if (this.listeners.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            systemEventCoordinator.privacyController.addCallback(systemEventCoordinator.privacyStateListener);
        }
        this.listeners.add(systemStatusAnimationCallback);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Scheduled event: ", this.scheduledEvent.getValue(), printWriter);
        printWriter.println("Currently displayed event: " + this.currentlyDisplayedEvent);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("Has persistent privacy dot: ", this.hasPersistentDot, printWriter);
        printWriter.println("Animation state: " + this.animationState.getValue());
        printWriter.println("Listeners:");
        if (this.listeners.isEmpty()) {
            printWriter.println("(none)");
            return;
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            printWriter.println("  " + ((SystemStatusAnimationCallback) it.next()));
        }
    }

    public final AnimatorSet notifyHidePersistentDot(boolean z) {
        Assert.isMainThread();
        SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = this.logger;
        if (systemStatusAnimationSchedulerLogger != null) {
            systemStatusAnimationSchedulerLogger.logBuffer.log("SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Hide persistent dot callback invoked", null);
        }
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((SystemStatusAnimationCallback) it.next()).onHidePersistentDot(z);
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    public final AnimatorSet notifyTransitionToPersistentDot(StatusEvent statusEvent) {
        SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = this.logger;
        if (systemStatusAnimationSchedulerLogger != null) {
            systemStatusAnimationSchedulerLogger.logBuffer.log("SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Transition to persistent dot callback invoked", null);
        }
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((SystemStatusAnimationCallback) it.next()).onSystemStatusAnimationTransitionToPersistentDot(statusEvent != null ? statusEvent.getContentDescription() : null);
        }
        if (!(!arrayList.isEmpty())) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    @Override // com.android.systemui.util.DesktopManager.Callback
    public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        if (semDesktopModeState == null) {
            return;
        }
        int state = semDesktopModeState.getState();
        int enabled = semDesktopModeState.getEnabled();
        int displayType = semDesktopModeState.getDisplayType();
        if (state == 50 && displayType == 101) {
            if (enabled == 4) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$onDesktopModeStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                        int i = SystemStatusAnimationSchedulerImpl.$r8$clinit;
                        systemStatusAnimationSchedulerImpl.notifyHidePersistentDot(true);
                    }
                });
            } else if (this.hasPersistentDot) {
                notifyTransitionToPersistentDot(this.currentlyDisplayedEvent);
            }
        }
    }

    public final void onStatusEvent(StatusEvent statusEvent) {
        Job job;
        Assert.isMainThread();
        if ((this.systemClock.uptimeMillis() - Process.getStartUptimeMillis() >= 5000 || statusEvent.getForceVisible()) && DeviceConfig.getBoolean("privacy", "enable_immersive_indicator", true)) {
            if (this.desktopManager.isStandalone()) {
                if (statusEvent.getForceVisible()) {
                    this.hasPersistentDot = true;
                    return;
                }
                return;
            }
            int priority = statusEvent.getPriority();
            StateFlowImpl stateFlowImpl = this.scheduledEvent;
            StatusEvent statusEvent2 = (StatusEvent) stateFlowImpl.getValue();
            int priority2 = statusEvent2 != null ? statusEvent2.getPriority() : -1;
            StateFlowImpl stateFlowImpl2 = this.animationState;
            SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = this.logger;
            if (priority > priority2) {
                int priority3 = statusEvent.getPriority();
                StatusEvent statusEvent3 = this.currentlyDisplayedEvent;
                if (priority3 > (statusEvent3 != null ? statusEvent3.getPriority() : -1) && (statusEvent.getShowAnimation() || !this.hasPersistentDot)) {
                    if (systemStatusAnimationSchedulerLogger != null) {
                        LogLevel logLevel = LogLevel.DEBUG;
                        SystemStatusAnimationSchedulerLogger$logScheduleEvent$2 systemStatusAnimationSchedulerLogger$logScheduleEvent$2 = new Function1() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerLogger$logScheduleEvent$2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                LogMessage logMessage = (LogMessage) obj;
                                String str1 = logMessage.getStr1();
                                boolean bool1 = logMessage.getBool1();
                                int int1 = logMessage.getInt1();
                                boolean bool2 = logMessage.getBool2();
                                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Scheduling event: ", str1, "(forceVisible=", ", priority=", bool1);
                                m.append(int1);
                                m.append(", showAnimation=");
                                m.append(bool2);
                                m.append(")");
                                return m.toString();
                            }
                        };
                        LogBuffer logBuffer = systemStatusAnimationSchedulerLogger.logBuffer;
                        LogMessage obtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, systemStatusAnimationSchedulerLogger$logScheduleEvent$2, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = statusEvent.getClass().getSimpleName();
                        logMessageImpl.int1 = statusEvent.getPriority();
                        logMessageImpl.bool1 = statusEvent.getForceVisible();
                        logMessageImpl.bool2 = statusEvent.getShowAnimation();
                        logBuffer.commit(obtain);
                    }
                    if (statusEvent.getShowAnimation() && ((Number) stateFlowImpl2.getValue()).intValue() == 5) {
                        if (statusEvent instanceof PrivacyEvent) {
                            removePersistentDot(false);
                        } else {
                            if (systemStatusAnimationSchedulerLogger != null) {
                                systemStatusAnimationSchedulerLogger.logBuffer.log("SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Show battery chip and privacy dot", null);
                            }
                            this.showingDotWhileChipAnim = true;
                            stateFlowImpl2.updateState(null, 0);
                        }
                    }
                    stateFlowImpl.updateState(null, statusEvent);
                    if (this.currentlyDisplayedEvent != null && ((job = this.eventCancellationJob) == null || !job.isActive())) {
                        this.eventCancellationJob = BuildersKt.launch$default(this.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1(this, null), 3);
                        return;
                    } else {
                        if (((Number) stateFlowImpl2.getValue()).intValue() == 0) {
                            stateFlowImpl2.updateState(null, 1);
                            return;
                        }
                        return;
                    }
                }
            }
            StatusEvent statusEvent4 = this.currentlyDisplayedEvent;
            if (statusEvent4 != null && statusEvent4.shouldUpdateFromEvent(statusEvent)) {
                if (systemStatusAnimationSchedulerLogger != null) {
                    systemStatusAnimationSchedulerLogger.logUpdateEvent(statusEvent, ((Number) stateFlowImpl2.getValue()).intValue());
                }
                if (((Number) stateFlowImpl2.getValue()).intValue() == 2) {
                    if (systemStatusAnimationSchedulerLogger != null) {
                        systemStatusAnimationSchedulerLogger.logBuffer.log("SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "skip updating since animation is already started", null);
                        return;
                    }
                    return;
                }
                StatusEvent statusEvent5 = this.currentlyDisplayedEvent;
                if (statusEvent5 != null) {
                    statusEvent5.updateFromEvent(statusEvent);
                }
                if (statusEvent.getForceVisible()) {
                    this.hasPersistentDot = true;
                }
                if (((Number) stateFlowImpl2.getValue()).intValue() == 1) {
                    if (systemStatusAnimationSchedulerLogger != null) {
                        systemStatusAnimationSchedulerLogger.logBuffer.log("SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Reset chip animation since privacy items are updated", null);
                    }
                    Intrinsics.checkNotNull(stateFlowImpl);
                    Object value = stateFlowImpl.getValue();
                    Intrinsics.checkNotNull(value);
                    this.chipAnimationController.prepareChipAnimation(((StatusEvent) value).getViewCreator(), stateFlowImpl.getValue() instanceof BatteryEvent);
                    return;
                }
                return;
            }
            StatusEvent statusEvent6 = (StatusEvent) stateFlowImpl.getValue();
            if (statusEvent6 != null && statusEvent6.shouldUpdateFromEvent(statusEvent)) {
                if (systemStatusAnimationSchedulerLogger != null) {
                    systemStatusAnimationSchedulerLogger.logUpdateEvent(statusEvent, ((Number) stateFlowImpl2.getValue()).intValue());
                }
                StatusEvent statusEvent7 = (StatusEvent) stateFlowImpl.getValue();
                if (statusEvent7 != null) {
                    statusEvent7.updateFromEvent(statusEvent);
                    return;
                }
                return;
            }
            int priority4 = statusEvent.getPriority();
            StatusEvent statusEvent8 = (StatusEvent) stateFlowImpl.getValue();
            if (priority4 > (statusEvent8 != null ? statusEvent8.getPriority() : -1)) {
                stateFlowImpl.updateState(null, statusEvent);
                return;
            }
            if (systemStatusAnimationSchedulerLogger != null) {
                LogLevel logLevel2 = LogLevel.DEBUG;
                SystemStatusAnimationSchedulerLogger$logIgnoreEvent$2 systemStatusAnimationSchedulerLogger$logIgnoreEvent$2 = new Function1() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerLogger$logIgnoreEvent$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        String str1 = logMessage.getStr1();
                        boolean bool1 = logMessage.getBool1();
                        int int1 = logMessage.getInt1();
                        boolean bool2 = logMessage.getBool2();
                        StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Ignore event: ", str1, "(forceVisible=", ", priority=", bool1);
                        m.append(int1);
                        m.append(", showAnimation=");
                        m.append(bool2);
                        m.append(")");
                        return m.toString();
                    }
                };
                LogBuffer logBuffer2 = systemStatusAnimationSchedulerLogger.logBuffer;
                LogMessage obtain2 = logBuffer2.obtain("SystemStatusAnimationSchedulerLog", logLevel2, systemStatusAnimationSchedulerLogger$logIgnoreEvent$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = statusEvent.getClass().getSimpleName();
                logMessageImpl2.int1 = statusEvent.getPriority();
                logMessageImpl2.bool1 = statusEvent.getForceVisible();
                logMessageImpl2.bool2 = statusEvent.getShowAnimation();
                logBuffer2.commit(obtain2);
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        Assert.isMainThread();
        this.listeners.remove((SystemStatusAnimationCallback) obj);
        if (this.listeners.isEmpty()) {
            SystemEventCoordinator systemEventCoordinator = this.coordinator;
            systemEventCoordinator.privacyController.removeCallback(systemEventCoordinator.privacyStateListener);
        }
    }

    public final void removePersistentDot(boolean z) {
        Assert.isMainThread();
        StateFlowImpl stateFlowImpl = this.scheduledEvent;
        StatusEvent statusEvent = (StatusEvent) stateFlowImpl.getValue();
        if (statusEvent != null) {
            statusEvent.setForceVisible();
        }
        if (this.hasPersistentDot || this.showingDotWhileChipAnim) {
            this.hasPersistentDot = false;
            StateFlowImpl stateFlowImpl2 = this.animationState;
            if (((Number) stateFlowImpl2.getValue()).intValue() == 5) {
                notifyHidePersistentDot(z);
                if (stateFlowImpl.getValue() != null) {
                    stateFlowImpl2.updateState(null, 1);
                } else {
                    stateFlowImpl2.updateState(null, 0);
                }
            } else if (((Number) stateFlowImpl2.getValue()).intValue() == 4) {
                notifyHidePersistentDot(z);
            } else if (((Number) stateFlowImpl2.getValue()).intValue() == 3 && this.showingDotWhileChipAnim) {
                notifyHidePersistentDot(z);
            }
            this.showingDotWhileChipAnim = false;
        }
    }
}
