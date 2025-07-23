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
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SystemStatusAnimationSchedulerImpl implements SystemStatusAnimationScheduler, DesktopManager.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _animationState;
    public final ReadonlyStateFlow animationState;
    public final SystemEventChipAnimationController chipAnimationController;
    public final SystemEventCoordinator coordinator;
    public final CoroutineScope coroutineScope;
    public StatusEvent currentlyDisplayedEvent;
    public StandaloneCoroutine currentlyRunningAnimationJob;
    public final DesktopManager desktopManager;
    public StandaloneCoroutine eventCancellationJob;
    public boolean hasPersistentDot;
    public final HeaderBatteryStatusChipController headerBatteryChipController;
    public final Set listeners;
    public final SystemStatusAnimationSchedulerLogger logger;
    public final StateFlowImpl scheduledEvent = StateFlowKt.MutableStateFlow(null);
    public boolean showingDotWhileChipAnim;
    public boolean statusBarHidden;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;
    public final SystemClock systemClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$1$1, reason: invalid class name and collision with other inner class name */
        final class C03091 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            public C03091(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                C03091 c03091 = new C03091((Continuation) obj3);
                c03091.L$0 = (SystemEventAnimationState) obj;
                c03091.L$1 = (StatusEvent) obj2;
                return c03091.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return new Pair((SystemEventAnimationState) this.L$0, (StatusEvent) this.L$1);
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
                Flow debounce = FlowKt.debounce(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(systemStatusAnimationSchedulerImpl._animationState, systemStatusAnimationSchedulerImpl.scheduledEvent, new C03091(null)), 500L);
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.1.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        SystemEventAnimationState systemEventAnimationState = (SystemEventAnimationState) pair.component1();
                        StatusEvent statusEvent = (StatusEvent) pair.component2();
                        if (systemEventAnimationState == SystemEventAnimationState.AnimationQueued && statusEvent != null) {
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
                                    stateFlowImpl.getClass();
                                    Object value = stateFlowImpl.getValue();
                                    value.getClass();
                                    Function1 viewCreator = ((StatusEvent) value).getViewCreator();
                                    HeaderBatteryStatusChipController headerBatteryStatusChipController = systemStatusAnimationSchedulerImpl3.headerBatteryChipController;
                                    BackgroundAnimatableView backgroundAnimatableView = (BackgroundAnimatableView) viewCreator.mo779invoke(headerBatteryStatusChipController.context);
                                    FrameLayout frameLayout = headerBatteryStatusChipController.batteryChipContainer;
                                    backgroundAnimatableView.getClass();
                                    View view = (View) backgroundAnimatableView;
                                    frameLayout.addView(view, new FrameLayout.LayoutParams(-2, -2));
                                    view.setVisibility(8);
                                    headerBatteryStatusChipController.currentAnimatedView = backgroundAnimatableView;
                                }
                                systemStatusAnimationSchedulerImpl3.currentlyRunningAnimationJob = CoroutineTracingKt.launchTraced$default(systemStatusAnimationSchedulerImpl3.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$startAnimationLifecycle$1(systemStatusAnimationSchedulerImpl3, statusEvent, null), 7);
                            } else {
                                systemStatusAnimationSchedulerImpl3._animationState.setValue(SystemEventAnimationState.ShowingPersistentDot);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl._animationState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SystemEventAnimationState systemEventAnimationState = (SystemEventAnimationState) obj2;
                        SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = SystemStatusAnimationSchedulerImpl.this.logger;
                        if (systemStatusAnimationSchedulerLogger != null) {
                            LogLevel logLevel = LogLevel.DEBUG;
                            SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 = new SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0(2);
                            LogBuffer logBuffer = systemStatusAnimationSchedulerLogger.logBuffer;
                            LogMessage obtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) obtain).str1 = systemEventAnimationState.name();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                ReadonlyStateFlow readonlyStateFlow = ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) SystemStatusAnimationSchedulerImpl.this.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode;
                final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl = SystemStatusAnimationSchedulerImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SystemStatusAnimationSchedulerImpl.this.statusBarHidden = ((Boolean) obj2).booleanValue();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SystemStatusAnimationSchedulerImpl(SystemEventCoordinator systemEventCoordinator, SystemEventChipAnimationController systemEventChipAnimationController, StatusBarWindowControllerStore statusBarWindowControllerStore, DumpManager dumpManager, SystemClock systemClock, CoroutineScope coroutineScope, SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger, StatusBarModeRepositoryStore statusBarModeRepositoryStore, DesktopManager desktopManager, HeaderBatteryStatusChipController headerBatteryStatusChipController) {
        this.coordinator = systemEventCoordinator;
        this.chipAnimationController = systemEventChipAnimationController;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.systemClock = systemClock;
        this.coroutineScope = coroutineScope;
        this.logger = systemStatusAnimationSchedulerLogger;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.desktopManager = desktopManager;
        this.headerBatteryChipController = headerBatteryStatusChipController;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(SystemEventAnimationState.Idle);
        this._animationState = MutableStateFlow;
        this.animationState = FlowKt.asStateFlow(MutableStateFlow);
        this.listeners = new LinkedHashSet();
        systemEventCoordinator.scheduler = this;
        dumpManager.registerCriticalDumpable("SystemStatusAnimationSchedulerImpl", this);
        desktopManager.registerCallback(this);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 7);
    }

    public static final void access$runChipDisappearAnimation(final SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl) {
        SpringAnimatorSet notifyTransitionToPersistentDot;
        systemStatusAnimationSchedulerImpl.getClass();
        Assert.isMainThread();
        boolean z = systemStatusAnimationSchedulerImpl.statusBarHidden;
        ArrayList arrayList = new ArrayList();
        boolean z2 = systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent instanceof BatteryEvent;
        Iterator it = systemStatusAnimationSchedulerImpl.listeners.iterator();
        while (it.hasNext()) {
            SpringAnimatorSet onSystemEventAnimationFinish = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z, z2);
            if (onSystemEventAnimationFinish != null) {
                arrayList.add(onSystemEventAnimationFinish);
            }
        }
        arrayList.add(systemStatusAnimationSchedulerImpl.chipAnimationController.onSystemEventAnimationFinish(systemStatusAnimationSchedulerImpl.hasPersistentDot, z, z2));
        if (systemStatusAnimationSchedulerImpl.hasPersistentDot && (notifyTransitionToPersistentDot = systemStatusAnimationSchedulerImpl.notifyTransitionToPersistentDot(systemStatusAnimationSchedulerImpl.currentlyDisplayedEvent)) != null) {
            arrayList.add(notifyTransitionToPersistentDot);
        }
        systemStatusAnimationSchedulerImpl.headerBatteryChipController.onSystemEventAnimationFinish(z, z, z2);
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(arrayList);
        systemStatusAnimationSchedulerImpl._animationState.setValue(SystemEventAnimationState.AnimatingOut);
        springAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.events.SystemStatusAnimationSchedulerImpl$runChipDisappearAnimation$1
            @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SystemEventAnimationState systemEventAnimationState;
                SystemStatusAnimationSchedulerImpl systemStatusAnimationSchedulerImpl2 = SystemStatusAnimationSchedulerImpl.this;
                StateFlowImpl stateFlowImpl = systemStatusAnimationSchedulerImpl2._animationState;
                if (systemStatusAnimationSchedulerImpl2.scheduledEvent.getValue() != null) {
                    if ((systemStatusAnimationSchedulerImpl2.scheduledEvent.getValue() instanceof BatteryEvent) && systemStatusAnimationSchedulerImpl2.hasPersistentDot) {
                        systemStatusAnimationSchedulerImpl2.showingDotWhileChipAnim = true;
                    }
                    systemEventAnimationState = SystemEventAnimationState.AnimationQueued;
                } else if (systemStatusAnimationSchedulerImpl2.hasPersistentDot) {
                    systemEventAnimationState = SystemEventAnimationState.ShowingPersistentDot;
                } else if (systemStatusAnimationSchedulerImpl2.showingDotWhileChipAnim) {
                    systemStatusAnimationSchedulerImpl2.hasPersistentDot = true;
                    systemStatusAnimationSchedulerImpl2.showingDotWhileChipAnim = false;
                    systemEventAnimationState = SystemEventAnimationState.ShowingPersistentDot;
                } else {
                    systemEventAnimationState = SystemEventAnimationState.Idle;
                }
                stateFlowImpl.setValue(systemEventAnimationState);
                ((StatusBarWindowControllerImpl) ((StatusBarWindowController) systemStatusAnimationSchedulerImpl2.statusBarWindowControllerStore.getDefaultDisplay())).setForceStatusBarVisible(false);
            }
        });
        springAnimatorSet.start();
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

    public final SpringAnimatorSet collectStartAnimations(boolean z) {
        ArrayList arrayList = new ArrayList();
        boolean z2 = this.currentlyDisplayedEvent instanceof BatteryEvent;
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            SpringAnimatorSet onSystemEventAnimationBegin = ((SystemStatusAnimationCallback) it.next()).onSystemEventAnimationBegin(z, z2);
            if (onSystemEventAnimationBegin != null) {
                arrayList.add(onSystemEventAnimationBegin);
            }
        }
        arrayList.add(this.chipAnimationController.onSystemEventAnimationBegin(z, z2));
        this.headerBatteryChipController.onSystemEventAnimationBegin(z, z2);
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(arrayList);
        return springAnimatorSet;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Scheduled event: ", this.scheduledEvent.getValue(), printWriter);
        printWriter.println("Currently displayed event: " + this.currentlyDisplayedEvent);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "Has persistent privacy dot: ", this.hasPersistentDot);
        printWriter.println("Animation state: " + this._animationState.getValue());
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
            LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Hide persistent dot callback invoked");
        }
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((SystemStatusAnimationCallback) it.next()).onHidePersistentDot(z);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        return animatorSet;
    }

    public final SpringAnimatorSet notifyTransitionToPersistentDot(StatusEvent statusEvent) {
        SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = this.logger;
        if (systemStatusAnimationSchedulerLogger != null) {
            LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Transition to persistent dot callback invoked");
        }
        Set set = this.listeners;
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (true) {
            String str = null;
            if (!it.hasNext()) {
                break;
            }
            SystemStatusAnimationCallback systemStatusAnimationCallback = (SystemStatusAnimationCallback) it.next();
            if (statusEvent != null) {
                str = statusEvent.getContentDescription();
            }
            systemStatusAnimationCallback.onSystemStatusAnimationTransitionToPersistentDot(str);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        SpringAnimatorSet springAnimatorSet = new SpringAnimatorSet();
        springAnimatorSet.playTogether(arrayList);
        return springAnimatorSet;
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
        StandaloneCoroutine standaloneCoroutine;
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
            StateFlowImpl stateFlowImpl2 = this._animationState;
            ReadonlyStateFlow readonlyStateFlow = this.animationState;
            SystemStatusAnimationSchedulerLogger systemStatusAnimationSchedulerLogger = this.logger;
            if (priority > priority2) {
                int priority3 = statusEvent.getPriority();
                StatusEvent statusEvent3 = this.currentlyDisplayedEvent;
                if (priority3 > (statusEvent3 != null ? statusEvent3.getPriority() : -1) && (statusEvent.getShowAnimation() || !this.hasPersistentDot)) {
                    if (systemStatusAnimationSchedulerLogger != null) {
                        LogLevel logLevel = LogLevel.DEBUG;
                        SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 = new SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0(0);
                        LogBuffer logBuffer = systemStatusAnimationSchedulerLogger.logBuffer;
                        LogMessage obtain = logBuffer.obtain("SystemStatusAnimationSchedulerLog", logLevel, systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                        logMessageImpl.str1 = statusEvent.getClass().getSimpleName();
                        logMessageImpl.int1 = statusEvent.getPriority();
                        logMessageImpl.bool1 = statusEvent.getForceVisible();
                        logMessageImpl.bool2 = statusEvent.getShowAnimation();
                        logBuffer.commit(obtain);
                    }
                    if (statusEvent.getShowAnimation() && (readonlyStateFlow.$$delegate_0.getValue() == SystemEventAnimationState.ShowingPersistentDot || this.showingDotWhileChipAnim)) {
                        if (statusEvent instanceof PrivacyEvent) {
                            if (systemStatusAnimationSchedulerLogger != null) {
                                LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, FakeFeatures$$ExternalSyntheticOutline0.m("Hide dot for next chip animation. state=", ((SystemEventAnimationState) readonlyStateFlow.$$delegate_0.getValue()).name(), ", showingDotWhileChipAnim=", this.showingDotWhileChipAnim));
                            }
                            removePersistentDot(false);
                        } else if (statusEvent instanceof BatteryEvent) {
                            if (systemStatusAnimationSchedulerLogger != null) {
                                LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Show battery chip and privacy dot");
                            }
                            this.showingDotWhileChipAnim = true;
                            stateFlowImpl2.setValue(SystemEventAnimationState.Idle);
                        }
                    }
                    stateFlowImpl.updateState(null, statusEvent);
                    if (this.currentlyDisplayedEvent != null && ((standaloneCoroutine = this.eventCancellationJob) == null || !standaloneCoroutine.isActive())) {
                        this.eventCancellationJob = CoroutineTracingKt.launchTraced$default(this.coroutineScope, null, null, new SystemStatusAnimationSchedulerImpl$cancelCurrentlyDisplayedEvent$1(this, null), 7);
                        return;
                    } else {
                        if (stateFlowImpl2.getValue() == SystemEventAnimationState.Idle) {
                            stateFlowImpl2.setValue(SystemEventAnimationState.AnimationQueued);
                            return;
                        }
                        return;
                    }
                }
            }
            StatusEvent statusEvent4 = this.currentlyDisplayedEvent;
            if (statusEvent4 != null && statusEvent4.shouldUpdateFromEvent(statusEvent)) {
                if (systemStatusAnimationSchedulerLogger != null) {
                    systemStatusAnimationSchedulerLogger.logUpdateEvent(statusEvent, (SystemEventAnimationState) stateFlowImpl2.getValue());
                }
                if (readonlyStateFlow.$$delegate_0.getValue() == SystemEventAnimationState.AnimatingIn) {
                    if (systemStatusAnimationSchedulerLogger != null) {
                        LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "skip updating since animation is already started");
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
                if (readonlyStateFlow.$$delegate_0.getValue() != SystemEventAnimationState.AnimationQueued || stateFlowImpl.getValue() == null) {
                    return;
                }
                if (systemStatusAnimationSchedulerLogger != null) {
                    LogBuffer.log$default(systemStatusAnimationSchedulerLogger.logBuffer, "SystemStatusAnimationSchedulerLog", LogLevel.DEBUG, "Reset chip animation since privacy items are updated");
                }
                stateFlowImpl.getClass();
                Object value = stateFlowImpl.getValue();
                value.getClass();
                this.chipAnimationController.prepareChipAnimation(((StatusEvent) value).getViewCreator(), stateFlowImpl.getValue() instanceof BatteryEvent);
                return;
            }
            StatusEvent statusEvent6 = (StatusEvent) stateFlowImpl.getValue();
            if (statusEvent6 != null && statusEvent6.shouldUpdateFromEvent(statusEvent)) {
                if (systemStatusAnimationSchedulerLogger != null) {
                    systemStatusAnimationSchedulerLogger.logUpdateEvent(statusEvent, (SystemEventAnimationState) stateFlowImpl2.getValue());
                }
                StatusEvent statusEvent7 = (StatusEvent) stateFlowImpl.getValue();
                if (statusEvent7 != null) {
                    statusEvent7.updateFromEvent(statusEvent);
                    return;
                }
                return;
            }
            if (systemStatusAnimationSchedulerLogger != null) {
                LogLevel logLevel2 = LogLevel.DEBUG;
                SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda02 = new SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0(3);
                LogBuffer logBuffer2 = systemStatusAnimationSchedulerLogger.logBuffer;
                LogMessage obtain2 = logBuffer2.obtain("SystemStatusAnimationSchedulerLog", logLevel2, systemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda02, null);
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
            StateFlowImpl stateFlowImpl2 = this._animationState;
            if (stateFlowImpl2.getValue() == SystemEventAnimationState.ShowingPersistentDot) {
                notifyHidePersistentDot(z);
                if (stateFlowImpl.getValue() != null) {
                    stateFlowImpl2.setValue(SystemEventAnimationState.AnimationQueued);
                } else {
                    stateFlowImpl2.setValue(SystemEventAnimationState.Idle);
                }
            } else if (stateFlowImpl2.getValue() == SystemEventAnimationState.AnimatingOut) {
                notifyHidePersistentDot(z);
            } else if (this.showingDotWhileChipAnim) {
                notifyHidePersistentDot(z);
            }
            this.showingDotWhileChipAnim = false;
        }
    }
}
