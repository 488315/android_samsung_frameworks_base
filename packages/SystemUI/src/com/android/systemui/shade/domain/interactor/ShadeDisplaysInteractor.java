package com.android.systemui.shade.domain.interactor;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.window.WindowContext;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.shade.ShadeDisplayChangeLatencyTracker;
import com.android.systemui.shade.data.repository.MutableShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.display.ShadeExpansionIntent;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.row.NotificationRebindingTracker;
import com.android.systemui.statusbar.notification.stack.NotificationStackRebindingHider;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationForwarder;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeDisplaysInteractor implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public static final long TIMEOUT;
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final CoroutineScope bgScope;
    public final ConfigurationForwarder configForwarder;
    public final ConfigurationRepository configurationRepository;
    public final StateFlowImpl displayId;
    public final LogBuffer logBuffer;
    public final CoroutineContext mainThreadContext;
    public final NotificationRebindingTracker notificationRebindingTracker;
    public final NotificationStackRebindingHider notificationStackRebindingHider;
    public final WindowContext shadeContext;
    public final ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker;
    public final ShadeExpandedStateInteractor shadeExpandedInteractor;
    public final ShadeExpansionIntent shadeExpansionIntent;
    public final MutableShadeDisplaysRepository shadePositionRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        TIMEOUT = DurationKt.toDuration(1, DurationUnit.SECONDS);
    }

    public ShadeDisplaysInteractor(MutableShadeDisplaysRepository mutableShadeDisplaysRepository, WindowContext windowContext, ConfigurationRepository configurationRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, ShadeExpandedStateInteractor shadeExpandedStateInteractor, ShadeExpansionIntent shadeExpansionIntent, ActiveNotificationsInteractor activeNotificationsInteractor, NotificationRebindingTracker notificationRebindingTracker, NotificationStackRebindingHider notificationStackRebindingHider, ConfigurationForwarder configurationForwarder, LogBuffer logBuffer) {
        this.shadePositionRepository = mutableShadeDisplaysRepository;
        this.shadeContext = windowContext;
        this.configurationRepository = configurationRepository;
        this.bgScope = coroutineScope;
        this.mainThreadContext = coroutineContext;
        this.shadeDisplayChangeLatencyTracker = shadeDisplayChangeLatencyTracker;
        this.shadeExpandedInteractor = shadeExpandedStateInteractor;
        this.shadeExpansionIntent = shadeExpansionIntent;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.notificationRebindingTracker = notificationRebindingTracker;
        this.notificationStackRebindingHider = notificationStackRebindingHider;
        this.configForwarder = configurationForwarder;
        this.logBuffer = logBuffer;
        this.displayId = ((ShadeDisplaysRepositoryImpl) mutableShadeDisplaysRepository).displayId;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0102, code lost:
    
        if (r10.waitForNotificationsRebinding(r0) == r1) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00f7, code lost:
    
        if (r11 == r1) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008a, code lost:
    
        if (r13.collapse() == r1) goto L50;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /* JADX WARN: Type inference failed for: r12v15, types: [kotlin.jvm.functions.Function0] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$collapseAndExpandShadeIfNeeded(com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r10, int r11, com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0 r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.access$collapseAndExpandShadeIfNeeded(com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor, int, com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$moveShadeWindowTo$6$1$$ExternalSyntheticLambda0, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$moveShadeWindowTo(com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r11, int r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.access$moveShadeWindowTo(com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void errorLog(String str) {
        LogBuffer.log$default(this.logBuffer, "ShadeDisplaysInteractor", LogLevel.ERROR, str);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (!ShadeWindowGoesAround.FLAG.isTrue()) {
            refactorFlagUtils.getClass();
            RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.shade_window_goes_around to be enabled.");
        }
        this.shadeContext.registerComponentCallbacks(new ComponentCallbacks() { // from class: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$listenForWindowContextConfigChanges$1
            @Override // android.content.ComponentCallbacks
            public final void onConfigurationChanged(Configuration configuration) {
                ((ConfigurationControllerImpl) ShadeDisplaysInteractor.this.configForwarder).onConfigurationChanged(configuration);
            }

            @Override // android.content.ComponentCallbacks
            public final void onLowMemory() {
            }
        });
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new ShadeDisplaysInteractor$start$1(this, null), 6);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007f A[Catch: all -> 0x0087, TRY_LEAVE, TryCatch #2 {all -> 0x0087, blocks: (B:14:0x007b, B:16:0x007f), top: B:13:0x007b }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForNotificationsRebinding(kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$1
            r0.<init>(r10, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L40
            if (r2 != r3) goto L38
            int r10 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r3 = r0.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r0 = r0.L$0
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor r0 = (com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor) r0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L36
            r4 = r1
            r2 = r10
            r10 = r0
            goto L7b
        L36:
            r11 = move-exception
            goto L96
        L38:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L40:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.shade.ShadeTraceLogger r11 = com.android.systemui.shade.ShadeTraceLogger.INSTANCE
            r11.getClass()
            com.android.app.tracing.coroutines.TrackTracer r11 = com.android.systemui.shade.ShadeTraceLogger.t
            long r4 = r11.traceTag
            java.util.concurrent.ThreadLocalRandom r2 = java.util.concurrent.ThreadLocalRandom.current()
            int r2 = r2.nextInt()
            java.lang.String r6 = "waiting for notifications rebinding to finish"
            java.lang.String r11 = r11.trackName
            android.os.Trace.asyncTraceForTrackBegin(r4, r11, r6, r2)
            long r6 = com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.TIMEOUT     // Catch: java.lang.Throwable -> L94
            com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$2$1 r8 = new com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor$waitForNotificationsRebinding$2$1     // Catch: java.lang.Throwable -> L94
            r9 = 0
            r8.<init>(r10, r9)     // Catch: java.lang.Throwable -> L94
            r0.L$0 = r10     // Catch: java.lang.Throwable -> L94
            r0.L$1 = r11     // Catch: java.lang.Throwable -> L94
            r0.J$0 = r4     // Catch: java.lang.Throwable -> L94
            r0.I$0 = r2     // Catch: java.lang.Throwable -> L94
            r0.label = r3     // Catch: java.lang.Throwable -> L94
            long r6 = kotlinx.coroutines.DelayKt.m3450toDelayMillisLRDsOJo(r6)     // Catch: java.lang.Throwable -> L94
            java.lang.Object r0 = kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(r6, r8, r0)     // Catch: java.lang.Throwable -> L94
            if (r0 != r1) goto L79
            return r1
        L79:
            r3 = r11
            r11 = r0
        L7b:
            java.lang.Integer r11 = (java.lang.Integer) r11     // Catch: java.lang.Throwable -> L87
            if (r11 != 0) goto L8b
            java.lang.String r11 = "Timed out while waiting for inflations to finish"
            r10.errorLog(r11)     // Catch: java.lang.Throwable -> L87
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L87
            goto L8b
        L87:
            r11 = move-exception
        L88:
            r10 = r2
            r1 = r4
            goto L96
        L8b:
            android.os.Trace.asyncTraceForTrackEnd(r4, r3, r2)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L91:
            r3 = r11
            r11 = r10
            goto L88
        L94:
            r10 = move-exception
            goto L91
        L96:
            android.os.Trace.asyncTraceForTrackEnd(r1, r3, r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeDisplaysInteractor.waitForNotificationsRebinding(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
