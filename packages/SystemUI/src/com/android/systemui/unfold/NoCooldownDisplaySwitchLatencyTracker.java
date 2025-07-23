package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.time.SystemClock;
import java.time.Duration;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NoCooldownDisplaySwitchLatencyTracker implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long SCREEN_EVENT_TIMEOUT;
    public final AnimationStatusRepository animationStatusRepository;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final DeviceStateRepository deviceStateRepository;
    public final DisplaySwitchLatencyLogger displaySwitchLatencyLogger;
    public final KeyguardInteractor keyguardInteractor;
    public final PowerInteractor powerInteractor;
    public final Executor singleThreadBgExecutor;
    public final SystemClock systemClock;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceStateRepository.DeviceState.values().length];
            try {
                iArr[DeviceStateRepository.DeviceState.FOLDED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.HALF_FOLDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.UNFOLDED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        SCREEN_EVENT_TIMEOUT = Duration.ofMillis(15000L).toMillis();
    }

    public NoCooldownDisplaySwitchLatencyTracker(Context context, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor, AnimationStatusRepository animationStatusRepository, KeyguardInteractor keyguardInteractor, Executor executor, CoroutineScope coroutineScope, DisplaySwitchLatencyLogger displaySwitchLatencyLogger, SystemClock systemClock, DeviceStateManager deviceStateManager) {
        this.context = context;
        this.deviceStateRepository = deviceStateRepository;
        this.powerInteractor = powerInteractor;
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.animationStatusRepository = animationStatusRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.singleThreadBgExecutor = executor;
        this.applicationScope = coroutineScope;
        this.displaySwitchLatencyLogger = displaySwitchLatencyLogger;
        this.systemClock = systemClock;
        this.deviceStateManager = deviceStateManager;
        this.backgroundDispatcher = ExecutorsKt.from(executor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ef, code lost:
    
        if (com.android.systemui.util.kotlin.SuspendKt.race(r6, r0) != r1) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0074, code lost:
    
        if (r8 == r1) goto L52;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0088 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForDisplaySwitch(com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker r6, int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker.access$waitForDisplaySwitch(com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForGoToSleepWithScreenOff(final com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8.getClass()
            boolean r0 = r9 instanceof com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1
            if (r0 == 0) goto L16
            r0 = r9
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 r0 = (com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 r0 = new com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1
            r0.<init>(r8, r9)
        L1b:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            int r8 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L32
            goto L70
        L32:
            r9 = move-exception
            goto L7e
        L34:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3c:
            kotlin.ResultKt.throwOnFailure(r9)
            int r9 = com.android.app.tracing.TraceUtils.$r8$clinit
            java.util.concurrent.ThreadLocalRandom r9 = java.util.concurrent.ThreadLocalRandom.current()
            int r9 = r9.nextInt()
            r4 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r2 = "DisplaySwitchLatency"
            java.lang.String r6 = "waitForGoToSleepWithScreenOff()"
            android.os.Trace.asyncTraceForTrackBegin(r4, r2, r6, r9)
            com.android.systemui.power.domain.interactor.PowerInteractor r6 = r8.powerInteractor     // Catch: java.lang.Throwable -> L78
            kotlinx.coroutines.flow.ReadonlyStateFlow r6 = r6.detailedWakefulness     // Catch: java.lang.Throwable -> L78
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1 r7 = new com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1     // Catch: java.lang.Throwable -> L78
            r7.<init>()     // Catch: java.lang.Throwable -> L78
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L78
            r0.J$0 = r4     // Catch: java.lang.Throwable -> L78
            r0.I$0 = r9     // Catch: java.lang.Throwable -> L78
            r0.label = r3     // Catch: java.lang.Throwable -> L78
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.first(r7, r0)     // Catch: java.lang.Throwable -> L78
            if (r8 != r1) goto L6b
            return r1
        L6b:
            r0 = r9
            r9 = r8
            r8 = r0
            r0 = r2
            r1 = r4
        L70:
            com.android.systemui.power.shared.model.WakefulnessModel r9 = (com.android.systemui.power.shared.model.WakefulnessModel) r9     // Catch: java.lang.Throwable -> L32
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r8)
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L78:
            r8 = move-exception
            r0 = r9
            r9 = r8
            r8 = r0
            r0 = r2
            r1 = r4
        L7e:
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker.access$waitForGoToSleepWithScreenOff(com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForScreenTurnedOn(com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7.getClass()
            boolean r0 = r8 instanceof com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 r0 = (com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 r0 = new com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$1
            r0.<init>(r7, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            int r7 = r0.I$0
            long r1 = r0.J$0
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L32
            goto L74
        L32:
            r8 = move-exception
            goto L82
        L34:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3c:
            kotlin.ResultKt.throwOnFailure(r8)
            int r8 = com.android.app.tracing.TraceUtils.$r8$clinit
            java.util.concurrent.ThreadLocalRandom r8 = java.util.concurrent.ThreadLocalRandom.current()
            int r8 = r8.nextInt()
            r4 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r2 = "DisplaySwitchLatency"
            java.lang.String r6 = "waitForScreenTurnedOn()"
            android.os.Trace.asyncTraceForTrackBegin(r4, r2, r6, r8)
            com.android.systemui.power.domain.interactor.PowerInteractor r7 = r7.powerInteractor     // Catch: java.lang.Throwable -> L7c
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r7.screenPowerState     // Catch: java.lang.Throwable -> L7c
            kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 r7 = kotlinx.coroutines.flow.FlowKt.drop(r7)     // Catch: java.lang.Throwable -> L7c
            com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1 r6 = new com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1     // Catch: java.lang.Throwable -> L7c
            r6.<init>()     // Catch: java.lang.Throwable -> L7c
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L7c
            r0.J$0 = r4     // Catch: java.lang.Throwable -> L7c
            r0.I$0 = r8     // Catch: java.lang.Throwable -> L7c
            r0.label = r3     // Catch: java.lang.Throwable -> L7c
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r6, r0)     // Catch: java.lang.Throwable -> L7c
            if (r7 != r1) goto L6f
            return r1
        L6f:
            r0 = r8
            r8 = r7
            r7 = r0
            r0 = r2
            r1 = r4
        L74:
            com.android.systemui.power.shared.model.ScreenPowerState r8 = (com.android.systemui.power.shared.model.ScreenPowerState) r8     // Catch: java.lang.Throwable -> L32
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r7)
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L7c:
            r7 = move-exception
            r0 = r8
            r8 = r7
            r7 = r0
            r0 = r2
            r1 = r4
        L82:
            android.os.Trace.asyncTraceForTrackEnd(r1, r0, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker.access$waitForScreenTurnedOn(com.android.systemui.unfold.NoCooldownDisplaySwitchLatencyTracker, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final boolean isAodEnabled$1() {
        return ((Boolean) this.keyguardInteractor.isAodAvailable.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isAsleepDueToFold$1() {
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue();
        if (wakefulnessModel.isAsleep()) {
            return wakefulnessModel.lastSleepReason == WakeSleepReason.FOLD;
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Utils.isDeviceFoldable(this.context.getResources(), this.deviceStateManager)) {
            CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new NoCooldownDisplaySwitchLatencyTracker$start$1(this, null), 5);
        }
    }
}
