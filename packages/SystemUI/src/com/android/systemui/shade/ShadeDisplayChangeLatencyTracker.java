package com.android.systemui.shade;

import com.android.app.tracing.coroutines.TrackTracer;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.common.ui.data.repository.ConfigurationRepository;
import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import com.android.systemui.common.ui.view.ChoreographerUtils;
import com.android.systemui.scene.ui.view.WindowRootView;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeDisplayChangeLatencyTracker {
    public static final long TIMEOUT;
    public static final TrackTracer t;
    public final CoroutineScope bgScope;
    public final ChoreographerUtils choreographerUtils;
    public final LatencyTracker latencyTracker;
    public final ReadonlyStateFlow onMovedToDisplayFlow;
    public StandaloneCoroutine previousJob;
    public final WindowRootView shadeRootView;

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
        t = new TrackTracer("ShadeDisplayLatency", 0L, "shade", 2, null);
        Duration.Companion companion = Duration.Companion;
        TIMEOUT = DurationKt.toDuration(3, DurationUnit.SECONDS);
    }

    public ShadeDisplayChangeLatencyTracker(WindowRootView windowRootView, ConfigurationRepository configurationRepository, LatencyTracker latencyTracker, CoroutineScope coroutineScope, ChoreographerUtils choreographerUtils) {
        this.shadeRootView = windowRootView;
        this.latencyTracker = latencyTracker;
        this.bgScope = coroutineScope;
        this.choreographerUtils = choreographerUtils;
        this.onMovedToDisplayFlow = ((ConfigurationRepositoryImpl) configurationRepository).onMovedToDisplay;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:0|1|(2:3|(5:5|6|7|(1:(1:(5:11|12|13|14|15)(2:18|19))(2:20|21))(3:25|26|(2:28|24))|22))|35|6|7|(0)(0)|22) */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0068, code lost:
    
        if (r6.waitUntilNextDoFrameDone(r0) != r1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0035, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0073, code lost:
    
        if ((r8 instanceof java.util.concurrent.CancellationException) != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0075, code lost:
    
        r7 = com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(r7, "Shade move to ", " cancelled as a new move is being done before the previous one finished. Message: ", r8.getMessage());
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0084, code lost:
    
        android.util.Log.e("ShadeDisplayLatency", r7, r8);
        r6.latencyTracker.onActionCancel(29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0082, code lost:
    
        r7 = "Shade move cancelled.";
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$onShadeDisplayChangingAsync(com.android.systemui.shade.ShadeDisplayChangeLatencyTracker r6, int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 r0 = (com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1 r0 = new com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$onShadeDisplayChangingAsync$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 29
            if (r2 == 0) goto L49
            if (r2 == r4) goto L3f
            if (r2 != r3) goto L37
            int r7 = r0.I$0
            java.lang.Object r6 = r0.L$0
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker r6 = (com.android.systemui.shade.ShadeDisplayChangeLatencyTracker) r6
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Exception -> L35
            goto L6b
        L35:
            r8 = move-exception
            goto L71
        L37:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3f:
            int r7 = r0.I$0
            java.lang.Object r6 = r0.L$0
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker r6 = (com.android.systemui.shade.ShadeDisplayChangeLatencyTracker) r6
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Exception -> L35
            goto L5e
        L49:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.internal.util.LatencyTracker r8 = r6.latencyTracker     // Catch: java.lang.Exception -> L35
            r8.onActionStart(r5)     // Catch: java.lang.Exception -> L35
            r0.L$0 = r6     // Catch: java.lang.Exception -> L35
            r0.I$0 = r7     // Catch: java.lang.Exception -> L35
            r0.label = r4     // Catch: java.lang.Exception -> L35
            java.lang.Object r8 = r6.waitForOnMovedToDisplayDispatchedToView(r7, r0)     // Catch: java.lang.Exception -> L35
            if (r8 != r1) goto L5e
            goto L6a
        L5e:
            r0.L$0 = r6     // Catch: java.lang.Exception -> L35
            r0.I$0 = r7     // Catch: java.lang.Exception -> L35
            r0.label = r3     // Catch: java.lang.Exception -> L35
            java.lang.Object r8 = r6.waitUntilNextDoFrameDone(r0)     // Catch: java.lang.Exception -> L35
            if (r8 != r1) goto L6b
        L6a:
            return r1
        L6b:
            com.android.internal.util.LatencyTracker r8 = r6.latencyTracker     // Catch: java.lang.Exception -> L35
            r8.onActionEnd(r5)     // Catch: java.lang.Exception -> L35
            goto L8e
        L71:
            boolean r0 = r8 instanceof java.util.concurrent.CancellationException
            if (r0 == 0) goto L82
            java.lang.String r0 = r8.getMessage()
            java.lang.String r1 = "Shade move to "
            java.lang.String r2 = " cancelled as a new move is being done before the previous one finished. Message: "
            java.lang.String r7 = com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(r7, r1, r2, r0)
            goto L84
        L82:
            java.lang.String r7 = "Shade move cancelled."
        L84:
            java.lang.String r0 = "ShadeDisplayLatency"
            android.util.Log.e(r0, r7, r8)
            com.android.internal.util.LatencyTracker r6 = r6.latencyTracker
            r6.onActionCancel(r5)
        L8e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker.access$onShadeDisplayChangingAsync(com.android.systemui.shade.ShadeDisplayChangeLatencyTracker, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final synchronized void onShadeDisplayChanging(int i) {
        try {
            StandaloneCoroutine standaloneCoroutine = this.previousJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancelInternal(new CancellationException("New shade move in progress to " + i));
            }
            this.previousJob = BuildersKt.launch$default(this.bgScope, null, null, new ShadeDisplayChangeLatencyTracker$onShadeDisplayChanging$1(this, i, null), 3);
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00be, code lost:
    
        if (kotlinx.coroutines.TimeoutKt.m3451withTimeoutKLykuaI(r9, r14, r0) == r1) goto L39;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008e A[Catch: all -> 0x0047, TryCatch #0 {all -> 0x0047, blocks: (B:21:0x0043, B:22:0x0088, B:24:0x008e, B:25:0x00a1), top: B:20:0x0043 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitForOnMovedToDisplayDispatchedToView(int r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker.waitForOnMovedToDisplayDispatchedToView(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object waitUntilNextDoFrameDone(kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r10 = this;
            boolean r0 = r11 instanceof com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1 r0 = (com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1 r0 = new com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$1
            r0.<init>(r10, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            int r10 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L2f
            goto L6a
        L2f:
            r11 = move-exception
            goto L79
        L31:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L39:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.app.tracing.coroutines.TrackTracer r11 = com.android.systemui.shade.ShadeDisplayChangeLatencyTracker.t
            long r4 = r11.traceTag
            java.util.concurrent.ThreadLocalRandom r2 = java.util.concurrent.ThreadLocalRandom.current()
            int r2 = r2.nextInt()
            java.lang.String r6 = "waitUntilNextDoFrameDone"
            java.lang.String r11 = r11.trackName
            android.os.Trace.asyncTraceForTrackBegin(r4, r11, r6, r2)
            long r6 = com.android.systemui.shade.ShadeDisplayChangeLatencyTracker.TIMEOUT     // Catch: java.lang.Throwable -> L77
            com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1 r8 = new com.android.systemui.shade.ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1     // Catch: java.lang.Throwable -> L77
            r9 = 0
            r8.<init>(r10, r9)     // Catch: java.lang.Throwable -> L77
            r0.L$0 = r11     // Catch: java.lang.Throwable -> L77
            r0.J$0 = r4     // Catch: java.lang.Throwable -> L77
            r0.I$0 = r2     // Catch: java.lang.Throwable -> L77
            r0.label = r3     // Catch: java.lang.Throwable -> L77
            java.lang.Object r10 = kotlinx.coroutines.TimeoutKt.m3451withTimeoutKLykuaI(r6, r8, r0)     // Catch: java.lang.Throwable -> L77
            if (r10 != r1) goto L67
            return r1
        L67:
            r0 = r11
            r10 = r2
            r1 = r4
        L6a:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L2f
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r10)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L72:
            r0 = r11
            r11 = r10
            r10 = r2
            r1 = r4
            goto L79
        L77:
            r10 = move-exception
            goto L72
        L79:
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadeDisplayChangeLatencyTracker.waitUntilNextDoFrameDone(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
