package com.android.systemui.communal.domain.interactor;

import android.app.ActivityManager;
import android.app.DreamManager;
import android.os.UserHandle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.common.usagestats.data.model.UsageStatsQuery;
import com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl;
import com.android.systemui.common.usagestats.domain.UsageStatsInteractor;
import com.android.systemui.common.usagestats.shared.model.ActivityEventModel;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.kotlin.SuspendKt;
import com.android.systemui.util.time.SystemClock;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
public final class WidgetTrampolineInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final CoroutineScope bgScope;
    public final DreamManager dreamManager;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final Logger logger;
    public final SystemClock systemClock;
    public final TaskStackChangeListeners taskStackChangeListeners;
    public final UsageStatsInteractor usageStatsInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return WidgetTrampolineInteractor.this.waitForActivityStartAndDismissKeyguard(this);
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1, reason: invalid class name and case insensitive filesystem */
    final class C08371 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08371(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            WidgetTrampolineInteractor widgetTrampolineInteractor = WidgetTrampolineInteractor.this;
            int i = WidgetTrampolineInteractor.$r8$clinit;
            return widgetTrampolineInteractor.waitForActivityStartWhileOnHub(this);
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2, reason: invalid class name and case insensitive filesystem */
    final class C08382 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $startTime;
        int label;

        /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function1 {
            int label;
            final /* synthetic */ WidgetTrampolineInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(WidgetTrampolineInteractor widgetTrampolineInteractor, Continuation continuation) {
                super(1, continuation);
                this.this$0 = widgetTrampolineInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return ((AnonymousClass1) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForNewForegroundTask$2$listener$1, com.android.systemui.shared.system.TaskStackChangeListener] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final WidgetTrampolineInteractor widgetTrampolineInteractor = this.this$0;
                    this.label = 1;
                    int i2 = WidgetTrampolineInteractor.$r8$clinit;
                    widgetTrampolineInteractor.getClass();
                    final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                    cancellableContinuationImpl.initCancellability();
                    final ?? r3 = new TaskStackChangeListener() { // from class: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForNewForegroundTask$2$listener$1
                        @Override // com.android.systemui.shared.system.TaskStackChangeListener
                        public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
                            CancellableContinuation cancellableContinuation = cancellableContinuationImpl;
                            if (cancellableContinuation.isCompleted()) {
                                return;
                            }
                            cancellableContinuation.resume(Unit.INSTANCE, (Function1) null);
                        }
                    };
                    widgetTrampolineInteractor.taskStackChangeListeners.registerTaskStackListener(r3);
                    cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForNewForegroundTask$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            widgetTrampolineInteractor.taskStackChangeListeners.unregisterTaskStackListener(r3);
                            return Unit.INSTANCE;
                        }
                    });
                    Object result = cancellableContinuationImpl.getResult();
                    if (result != coroutineSingletons) {
                        result = Unit.INSTANCE;
                    }
                    if (result == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Boolean.TRUE;
            }
        }

        /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2$2, reason: invalid class name and collision with other inner class name */
        final class C01662 extends SuspendLambda implements Function1 {
            final /* synthetic */ long $startTime;
            int label;
            final /* synthetic */ WidgetTrampolineInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01662(WidgetTrampolineInteractor widgetTrampolineInteractor, long j, Continuation continuation) {
                super(1, continuation);
                this.this$0 = widgetTrampolineInteractor;
                this.$startTime = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Continuation continuation) {
                return new C01662(this.this$0, this.$startTime, continuation);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return ((C01662) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                ResultKt.throwOnFailure(obj);
                WidgetTrampolineInteractor widgetTrampolineInteractor = this.this$0;
                long j = this.$startTime;
                this.label = 1;
                Object objAccess$waitForActivityStartByPolling = WidgetTrampolineInteractor.access$waitForActivityStartByPolling(widgetTrampolineInteractor, j, this);
                return objAccess$waitForActivityStartByPolling == coroutineSingletons ? coroutineSingletons : objAccess$waitForActivityStartByPolling;
            }
        }

        /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function1 {
            int label;
            final /* synthetic */ WidgetTrampolineInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(WidgetTrampolineInteractor widgetTrampolineInteractor, Continuation continuation) {
                super(1, continuation);
                this.this$0 = widgetTrampolineInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return ((AnonymousClass3) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    WidgetTrampolineInteractor widgetTrampolineInteractor = this.this$0;
                    this.label = 1;
                    int i2 = WidgetTrampolineInteractor.$r8$clinit;
                    widgetTrampolineInteractor.getClass();
                    SceneKey sceneKey = Scenes.Communal;
                    Object objCollect = new FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(widgetTrampolineInteractor.keyguardTransitionInteractor.isFinishedIn(KeyguardState.GLANCEABLE_HUB), new WidgetTrampolineInteractor$waitForTransitionAwayFromHub$2(null)).collect(new FlowCollector() { // from class: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForTransitionAwayFromHub$3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            ((Boolean) obj2).booleanValue();
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Boolean.FALSE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08382(long j, Continuation continuation) {
            super(2, continuation);
            this.$startTime = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WidgetTrampolineInteractor.this.new C08382(this.$startTime, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08382) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            Function1[] function1Arr = {new AnonymousClass1(WidgetTrampolineInteractor.this, null), new C01662(WidgetTrampolineInteractor.this, this.$startTime, null), new AnonymousClass3(WidgetTrampolineInteractor.this, null)};
            this.label = 1;
            Object objRace = SuspendKt.race(function1Arr, this);
            return objRace == coroutineSingletons ? coroutineSingletons : objRace;
        }
    }

    static {
        new Companion(null);
    }

    public WidgetTrampolineInteractor(ActivityStarter activityStarter, SystemClock systemClock, KeyguardTransitionInteractor keyguardTransitionInteractor, TaskStackChangeListeners taskStackChangeListeners, UsageStatsInteractor usageStatsInteractor, DreamManager dreamManager, CoroutineScope coroutineScope, LogBuffer logBuffer) {
        this.activityStarter = activityStarter;
        this.systemClock = systemClock;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.taskStackChangeListeners = taskStackChangeListeners;
        this.usageStatsInteractor = usageStatsInteractor;
        this.dreamManager = dreamManager;
        this.bgScope = coroutineScope;
        this.logger = new Logger(logBuffer, "WidgetTrampolineInteractor");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00bd, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3469delayVtjQ1oo(r5, r0) != r1) goto L13;
     */
    /* JADX WARN: Path cross not found for [B:27:0x0086, B:30:0x0090], limit reached: 41 */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00bd -> B:13:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForActivityStartByPolling(WidgetTrampolineInteractor widgetTrampolineInteractor, long j, ContinuationImpl continuationImpl) {
        WidgetTrampolineInteractor$waitForActivityStartByPolling$1 widgetTrampolineInteractor$waitForActivityStartByPolling$1;
        long j2;
        WidgetTrampolineInteractor widgetTrampolineInteractor2;
        long j3;
        List list;
        Iterator it;
        UserHandle userHandle;
        widgetTrampolineInteractor.getClass();
        if (continuationImpl instanceof WidgetTrampolineInteractor$waitForActivityStartByPolling$1) {
            widgetTrampolineInteractor$waitForActivityStartByPolling$1 = (WidgetTrampolineInteractor$waitForActivityStartByPolling$1) continuationImpl;
            int i = widgetTrampolineInteractor$waitForActivityStartByPolling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                widgetTrampolineInteractor$waitForActivityStartByPolling$1.label = i - Integer.MIN_VALUE;
            } else {
                widgetTrampolineInteractor$waitForActivityStartByPolling$1 = new WidgetTrampolineInteractor$waitForActivityStartByPolling$1(widgetTrampolineInteractor, continuationImpl);
            }
        }
        Object objQueryActivityEvents = widgetTrampolineInteractor$waitForActivityStartByPolling$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = widgetTrampolineInteractor$waitForActivityStartByPolling$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objQueryActivityEvents);
            j2 = j;
            UsageStatsInteractor usageStatsInteractor = widgetTrampolineInteractor.usageStatsInteractor;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0 = widgetTrampolineInteractor;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0 = j2;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.label = 1;
            long jCurrentTimeMillis = usageStatsInteractor.systemClock.currentTimeMillis();
            userHandle = UserHandle.CURRENT;
            EmptyList emptyList = EmptyList.INSTANCE;
            if (Intrinsics.areEqual(userHandle, userHandle)) {
            }
            objQueryActivityEvents = ((UsageStatsRepositoryImpl) usageStatsInteractor.repository).queryActivityEvents(new UsageStatsQuery(userHandle, j2, jCurrentTimeMillis, emptyList), widgetTrampolineInteractor$waitForActivityStartByPolling$1);
            if (objQueryActivityEvents != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j3 = widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0;
            widgetTrampolineInteractor2 = (WidgetTrampolineInteractor) widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0;
            ResultKt.throwOnFailure(objQueryActivityEvents);
            j2 = j3;
            widgetTrampolineInteractor = widgetTrampolineInteractor2;
            UsageStatsInteractor usageStatsInteractor2 = widgetTrampolineInteractor.usageStatsInteractor;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0 = widgetTrampolineInteractor;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0 = j2;
            widgetTrampolineInteractor$waitForActivityStartByPolling$1.label = 1;
            long jCurrentTimeMillis2 = usageStatsInteractor2.systemClock.currentTimeMillis();
            userHandle = UserHandle.CURRENT;
            EmptyList emptyList2 = EmptyList.INSTANCE;
            if (Intrinsics.areEqual(userHandle, userHandle)) {
                userHandle = ((UserTrackerImpl) usageStatsInteractor2.userTracker).getUserHandle();
            }
            objQueryActivityEvents = ((UsageStatsRepositoryImpl) usageStatsInteractor2.repository).queryActivityEvents(new UsageStatsQuery(userHandle, j2, jCurrentTimeMillis2, emptyList2), widgetTrampolineInteractor$waitForActivityStartByPolling$1);
            if (objQueryActivityEvents != coroutineSingletons) {
                widgetTrampolineInteractor2 = widgetTrampolineInteractor;
                j3 = j2;
                list = (List) objQueryActivityEvents;
                if ((list instanceof Collection) || !list.isEmpty()) {
                    it = list.iterator();
                    while (it.hasNext()) {
                        if (((ActivityEventModel) it.next()).lifecycle == ActivityEventModel.Lifecycle.RESUMED) {
                            return Boolean.TRUE;
                        }
                    }
                }
                Duration.Companion companion = Duration.Companion;
                long duration = DurationKt.toDuration(200, DurationUnit.MILLISECONDS);
                widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0 = widgetTrampolineInteractor2;
                widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0 = j3;
                widgetTrampolineInteractor$waitForActivityStartByPolling$1.label = 2;
            }
            return coroutineSingletons;
        }
        j3 = widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0;
        widgetTrampolineInteractor2 = (WidgetTrampolineInteractor) widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0;
        ResultKt.throwOnFailure(objQueryActivityEvents);
        list = (List) objQueryActivityEvents;
        if (list instanceof Collection) {
        }
        it = list.iterator();
        while (it.hasNext()) {
        }
        Duration.Companion companion2 = Duration.Companion;
        long duration2 = DurationKt.toDuration(200, DurationUnit.MILLISECONDS);
        widgetTrampolineInteractor$waitForActivityStartByPolling$1.L$0 = widgetTrampolineInteractor2;
        widgetTrampolineInteractor$waitForActivityStartByPolling$1.J$0 = j3;
        widgetTrampolineInteractor$waitForActivityStartByPolling$1.label = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitForActivityStartAndDismissKeyguard(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWaitForActivityStartWhileOnHub = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWaitForActivityStartWhileOnHub);
            anonymousClass1.L$0 = this;
            anonymousClass1.label = 1;
            objWaitForActivityStartWhileOnHub = waitForActivityStartWhileOnHub(anonymousClass1);
            if (objWaitForActivityStartWhileOnHub == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (WidgetTrampolineInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objWaitForActivityStartWhileOnHub);
        }
        if (((Boolean) objWaitForActivityStartWhileOnHub).booleanValue()) {
            Logger.d$default(this.logger, "Detected trampoline, requesting unlock", null, 2, null);
            this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor.waitForActivityStartAndDismissKeyguard.2

                /* renamed from: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$2$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    int label;
                    final /* synthetic */ WidgetTrampolineInteractor this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(WidgetTrampolineInteractor widgetTrampolineInteractor, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = widgetTrampolineInteractor;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.this$0, continuation);
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
                        this.this$0.dreamManager.stopDream();
                        return Unit.INSTANCE;
                    }
                }

                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    WidgetTrampolineInteractor widgetTrampolineInteractor = WidgetTrampolineInteractor.this;
                    CoroutineTracingKt.launchTraced$default(widgetTrampolineInteractor.bgScope, null, null, new AnonymousClass1(widgetTrampolineInteractor, null), 7);
                    return false;
                }
            }, null, false);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object waitForActivityStartWhileOnHub(ContinuationImpl continuationImpl) {
        C08371 c08371;
        if (continuationImpl instanceof C08371) {
            c08371 = (C08371) continuationImpl;
            int i = c08371.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08371.label = i - Integer.MIN_VALUE;
            } else {
                c08371 = new C08371(continuationImpl);
            }
        }
        Object obj = c08371.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08371.label;
        try {
            if (i2 != 0) {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return obj;
            }
            ResultKt.throwOnFailure(obj);
            long jCurrentTimeMillis = this.systemClock.currentTimeMillis();
            Duration.Companion companion = Duration.Companion;
            long duration = DurationKt.toDuration(1, DurationUnit.SECONDS);
            C08382 c08382 = new C08382(jCurrentTimeMillis, null);
            c08371.label = 1;
            Object objM3471withTimeoutKLykuaI = TimeoutKt.m3471withTimeoutKLykuaI(duration, c08382, c08371);
            return objM3471withTimeoutKLykuaI == coroutineSingletons ? coroutineSingletons : objM3471withTimeoutKLykuaI;
        } catch (TimeoutCancellationException unused) {
            return Boolean.FALSE;
        }
    }
}
