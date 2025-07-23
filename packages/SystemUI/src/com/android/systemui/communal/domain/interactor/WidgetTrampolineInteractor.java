package com.android.systemui.communal.domain.interactor;

import android.app.DreamManager;
import com.android.systemui.common.usagestats.domain.UsageStatsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00bd, code lost:
    
        if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r5, r0) != r1) goto L13;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x00bd -> B:11:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForActivityStartByPolling(com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor r12, long r13, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            r12.getClass()
            boolean r0 = r15 instanceof com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartByPolling$1
            if (r0 == 0) goto L16
            r0 = r15
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartByPolling$1 r0 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartByPolling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartByPolling$1 r0 = new com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartByPolling$1
            r0.<init>(r12, r15)
        L1b:
            java.lang.Object r15 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L47
            if (r2 == r4) goto L3d
            if (r2 != r3) goto L35
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor r14 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor) r14
            kotlin.ResultKt.throwOnFailure(r15)
        L32:
            r7 = r12
            r12 = r14
            goto L4b
        L35:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L3d:
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor r14 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor) r14
            kotlin.ResultKt.throwOnFailure(r15)
            goto L7e
        L47:
            kotlin.ResultKt.throwOnFailure(r15)
            r7 = r13
        L4b:
            com.android.systemui.common.usagestats.domain.UsageStatsInteractor r13 = r12.usageStatsInteractor
            r0.L$0 = r12
            r0.J$0 = r7
            r0.label = r4
            com.android.systemui.util.time.SystemClock r14 = r13.systemClock
            long r9 = r14.currentTimeMillis()
            android.os.UserHandle r14 = android.os.UserHandle.CURRENT
            kotlin.collections.EmptyList r11 = kotlin.collections.EmptyList.INSTANCE
            boolean r15 = kotlin.jvm.internal.Intrinsics.areEqual(r14, r14)
            if (r15 == 0) goto L6b
            com.android.systemui.settings.UserTracker r14 = r13.userTracker
            com.android.systemui.settings.UserTrackerImpl r14 = (com.android.systemui.settings.UserTrackerImpl) r14
            android.os.UserHandle r14 = r14.getUserHandle()
        L6b:
            r6 = r14
            com.android.systemui.common.usagestats.data.model.UsageStatsQuery r5 = new com.android.systemui.common.usagestats.data.model.UsageStatsQuery
            r5.<init>(r6, r7, r9, r11)
            com.android.systemui.common.usagestats.data.repository.UsageStatsRepository r13 = r13.repository
            com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl r13 = (com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl) r13
            java.lang.Object r15 = r13.queryActivityEvents(r5, r0)
            if (r15 != r1) goto L7c
            goto Lbf
        L7c:
            r14 = r12
            r12 = r7
        L7e:
            java.util.List r15 = (java.util.List) r15
            java.lang.Iterable r15 = (java.lang.Iterable) r15
            boolean r2 = r15 instanceof java.util.Collection
            if (r2 == 0) goto L90
            r2 = r15
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L90
            goto La9
        L90:
            java.util.Iterator r15 = r15.iterator()
        L94:
            boolean r2 = r15.hasNext()
            if (r2 == 0) goto La9
            java.lang.Object r2 = r15.next()
            com.android.systemui.common.usagestats.shared.model.ActivityEventModel r2 = (com.android.systemui.common.usagestats.shared.model.ActivityEventModel) r2
            com.android.systemui.common.usagestats.shared.model.ActivityEventModel$Lifecycle r2 = r2.lifecycle
            com.android.systemui.common.usagestats.shared.model.ActivityEventModel$Lifecycle r5 = com.android.systemui.common.usagestats.shared.model.ActivityEventModel.Lifecycle.RESUMED
            if (r2 != r5) goto L94
            java.lang.Boolean r12 = java.lang.Boolean.TRUE
            return r12
        La9:
            kotlin.time.Duration$Companion r15 = kotlin.time.Duration.Companion
            r15 = 200(0xc8, float:2.8E-43)
            kotlin.time.DurationUnit r2 = kotlin.time.DurationUnit.MILLISECONDS
            long r5 = kotlin.time.DurationKt.toDuration(r15, r2)
            r0.L$0 = r14
            r0.J$0 = r12
            r0.label = r3
            java.lang.Object r15 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r5, r0)
            if (r15 != r1) goto L32
        Lbf:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor.access$waitForActivityStartByPolling(com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor, long, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForActivityStartAndDismissKeyguard(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1 r0 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1 r0 = new com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor r4 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r4.waitForActivityStartWhileOnHub(r0)
            if (r5 != r1) goto L41
            return r1
        L41:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L5d
            com.android.systemui.log.core.Logger r5 = r4.logger
            java.lang.String r0 = "Detected trampoline, requesting unlock"
            r1 = 0
            r2 = 2
            com.android.systemui.log.core.Logger.d$default(r5, r0, r1, r2, r1)
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$2 r5 = new com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartAndDismissKeyguard$2
            r5.<init>()
            r0 = 0
            com.android.systemui.plugins.ActivityStarter r4 = r4.activityStarter
            r4.dismissKeyguardThenExecute(r5, r1, r0)
        L5d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor.waitForActivityStartAndDismissKeyguard(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForActivityStartWhileOnHub(kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1 r0 = (com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1 r0 = new com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1
            r0.<init>(r8, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            return r9
        L27:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L2f:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.util.time.SystemClock r9 = r8.systemClock
            long r4 = r9.currentTimeMillis()
            kotlin.time.Duration$Companion r9 = kotlin.time.Duration.Companion     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            kotlin.time.DurationUnit r9 = kotlin.time.DurationUnit.SECONDS     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            long r6 = kotlin.time.DurationKt.toDuration(r3, r9)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2 r9 = new com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$2     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            r2 = 0
            r9.<init>(r8, r4, r2)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            r0.label = r3     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            java.lang.Object r8 = kotlinx.coroutines.TimeoutKt.m3451withTimeoutKLykuaI(r6, r9, r0)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L50
            if (r8 != r1) goto L4f
            return r1
        L4f:
            return r8
        L50:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.domain.interactor.WidgetTrampolineInteractor.waitForActivityStartWhileOnHub(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
