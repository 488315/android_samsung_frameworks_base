package com.android.systemui.unfold;

import android.content.Context;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DisplaySwitchLatencyTracker implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long SCREEN_EVENT_TIMEOUT;
    public final AnimationStatusRepository animationStatusRepository;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DeviceStateRepository deviceStateRepository;
    public final DisplaySwitchLatencyLogger displaySwitchLatencyLogger;
    public final KeyguardInteractor keyguardInteractor;
    public final PowerInteractor powerInteractor;
    public final Executor singleThreadBgExecutor;
    public final SystemClock systemClock;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DisplaySwitchLatencyEvent {
        public final int externalDisplayCount;
        public final int fromDensityDpi;
        public final int fromFocusedAppUid;
        public final int fromFoldableDeviceState;
        public final int fromPipAppUid;
        public final int fromState;
        public final Set fromVisibleAppsUid;
        public final int hallSensorToDeviceStateChangeMs;
        public final int hallSensorToFirstHingeAngleChangeMs;
        public final int latencyMs;
        public final int notificationCount;
        public final int onDrawnToOnScreenTurnedOnMs;
        public final int onScreenTurningOnToOnDrawnMs;
        public final int throttlingLevel;
        public final int toDensityDpi;
        public final int toFocusedAppUid;
        public final int toFoldableDeviceState;
        public final int toPipAppUid;
        public final int toState;
        public final Set toVisibleAppsUid;
        public final int vskinTemperatureC;

        public DisplaySwitchLatencyEvent() {
            this(0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2097151, null);
        }

        public static DisplaySwitchLatencyEvent copy$default(DisplaySwitchLatencyEvent displaySwitchLatencyEvent, int i, int i2, int i3, int i4, int i5) {
            int i6 = (i5 & 1) != 0 ? displaySwitchLatencyEvent.latencyMs : i;
            int i7 = (i5 & 2) != 0 ? displaySwitchLatencyEvent.fromFoldableDeviceState : i2;
            int i8 = displaySwitchLatencyEvent.fromState;
            int i9 = displaySwitchLatencyEvent.fromFocusedAppUid;
            int i10 = displaySwitchLatencyEvent.fromPipAppUid;
            Set set = displaySwitchLatencyEvent.fromVisibleAppsUid;
            int i11 = displaySwitchLatencyEvent.fromDensityDpi;
            int i12 = (i5 & 128) != 0 ? displaySwitchLatencyEvent.toFoldableDeviceState : i3;
            int i13 = (i5 & 256) != 0 ? displaySwitchLatencyEvent.toState : i4;
            int i14 = displaySwitchLatencyEvent.toFocusedAppUid;
            int i15 = displaySwitchLatencyEvent.toPipAppUid;
            Set set2 = displaySwitchLatencyEvent.toVisibleAppsUid;
            int i16 = displaySwitchLatencyEvent.toDensityDpi;
            int i17 = displaySwitchLatencyEvent.notificationCount;
            int i18 = displaySwitchLatencyEvent.externalDisplayCount;
            int i19 = displaySwitchLatencyEvent.throttlingLevel;
            int i20 = displaySwitchLatencyEvent.vskinTemperatureC;
            int i21 = displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs;
            int i22 = displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs;
            int i23 = displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs;
            int i24 = displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs;
            displaySwitchLatencyEvent.getClass();
            return new DisplaySwitchLatencyEvent(i6, i7, i8, i9, i10, set, i11, i12, i13, i14, i15, set2, i16, i17, i18, i19, i20, i21, i22, i23, i24);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplaySwitchLatencyEvent)) {
                return false;
            }
            DisplaySwitchLatencyEvent displaySwitchLatencyEvent = (DisplaySwitchLatencyEvent) obj;
            return this.latencyMs == displaySwitchLatencyEvent.latencyMs && this.fromFoldableDeviceState == displaySwitchLatencyEvent.fromFoldableDeviceState && this.fromState == displaySwitchLatencyEvent.fromState && this.fromFocusedAppUid == displaySwitchLatencyEvent.fromFocusedAppUid && this.fromPipAppUid == displaySwitchLatencyEvent.fromPipAppUid && Intrinsics.areEqual(this.fromVisibleAppsUid, displaySwitchLatencyEvent.fromVisibleAppsUid) && this.fromDensityDpi == displaySwitchLatencyEvent.fromDensityDpi && this.toFoldableDeviceState == displaySwitchLatencyEvent.toFoldableDeviceState && this.toState == displaySwitchLatencyEvent.toState && this.toFocusedAppUid == displaySwitchLatencyEvent.toFocusedAppUid && this.toPipAppUid == displaySwitchLatencyEvent.toPipAppUid && Intrinsics.areEqual(this.toVisibleAppsUid, displaySwitchLatencyEvent.toVisibleAppsUid) && this.toDensityDpi == displaySwitchLatencyEvent.toDensityDpi && this.notificationCount == displaySwitchLatencyEvent.notificationCount && this.externalDisplayCount == displaySwitchLatencyEvent.externalDisplayCount && this.throttlingLevel == displaySwitchLatencyEvent.throttlingLevel && this.vskinTemperatureC == displaySwitchLatencyEvent.vskinTemperatureC && this.hallSensorToFirstHingeAngleChangeMs == displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs && this.hallSensorToDeviceStateChangeMs == displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs && this.onScreenTurningOnToOnDrawnMs == displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs && this.onDrawnToOnScreenTurnedOnMs == displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs;
        }

        public final int hashCode() {
            return Integer.hashCode(this.onDrawnToOnScreenTurnedOnMs) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.onScreenTurningOnToOnDrawnMs, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.hallSensorToDeviceStateChangeMs, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.hallSensorToFirstHingeAngleChangeMs, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.vskinTemperatureC, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.throttlingLevel, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.externalDisplayCount, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.notificationCount, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.toDensityDpi, (this.toVisibleAppsUid.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.toPipAppUid, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.toFocusedAppUid, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.toState, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.toFoldableDeviceState, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.fromDensityDpi, (this.fromVisibleAppsUid.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.fromPipAppUid, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.fromFocusedAppUid, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.fromState, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.fromFoldableDeviceState, Integer.hashCode(this.latencyMs) * 31, 31), 31), 31), 31)) * 31, 31), 31), 31), 31), 31)) * 31, 31), 31), 31), 31), 31), 31), 31), 31);
        }

        public final String toString() {
            Set set = this.fromVisibleAppsUid;
            Set set2 = this.toVisibleAppsUid;
            StringBuilder sb = new StringBuilder("DisplaySwitchLatencyEvent(latencyMs=");
            sb.append(this.latencyMs);
            sb.append(", fromFoldableDeviceState=");
            sb.append(this.fromFoldableDeviceState);
            sb.append(", fromState=");
            sb.append(this.fromState);
            sb.append(", fromFocusedAppUid=");
            sb.append(this.fromFocusedAppUid);
            sb.append(", fromPipAppUid=");
            sb.append(this.fromPipAppUid);
            sb.append(", fromVisibleAppsUid=");
            sb.append(set);
            sb.append(", fromDensityDpi=");
            sb.append(this.fromDensityDpi);
            sb.append(", toFoldableDeviceState=");
            sb.append(this.toFoldableDeviceState);
            sb.append(", toState=");
            sb.append(this.toState);
            sb.append(", toFocusedAppUid=");
            sb.append(this.toFocusedAppUid);
            sb.append(", toPipAppUid=");
            sb.append(this.toPipAppUid);
            sb.append(", toVisibleAppsUid=");
            sb.append(set2);
            sb.append(", toDensityDpi=");
            sb.append(this.toDensityDpi);
            sb.append(", notificationCount=");
            sb.append(this.notificationCount);
            sb.append(", externalDisplayCount=");
            sb.append(this.externalDisplayCount);
            sb.append(", throttlingLevel=");
            sb.append(this.throttlingLevel);
            sb.append(", vskinTemperatureC=");
            sb.append(this.vskinTemperatureC);
            sb.append(", hallSensorToFirstHingeAngleChangeMs=");
            sb.append(this.hallSensorToFirstHingeAngleChangeMs);
            sb.append(", hallSensorToDeviceStateChangeMs=");
            sb.append(this.hallSensorToDeviceStateChangeMs);
            sb.append(", onScreenTurningOnToOnDrawnMs=");
            sb.append(this.onScreenTurningOnToOnDrawnMs);
            sb.append(", onDrawnToOnScreenTurnedOnMs=");
            return Anchor$$ExternalSyntheticOutline0.m(this.onDrawnToOnScreenTurnedOnMs, ")", sb);
        }

        public DisplaySwitchLatencyEvent(int i, int i2, int i3, int i4, int i5, Set set, int i6, int i7, int i8, int i9, int i10, Set set2, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, DefaultConstructorMarker defaultConstructorMarker) {
            this((i20 & 1) != 0 ? -1 : i, (i20 & 2) != 0 ? 0 : i2, (i20 & 4) != 0 ? 0 : i3, (i20 & 8) != 0 ? -1 : i4, (i20 & 16) != 0 ? -1 : i5, (i20 & 32) != 0 ? EmptySet.INSTANCE : set, (i20 & 64) != 0 ? -1 : i6, (i20 & 128) != 0 ? 0 : i7, (i20 & 256) != 0 ? 0 : i8, (i20 & 512) != 0 ? -1 : i9, (i20 & 1024) != 0 ? -1 : i10, (i20 & 2048) != 0 ? EmptySet.INSTANCE : set2, (i20 & 4096) != 0 ? -1 : i11, (i20 & 8192) != 0 ? -1 : i12, (i20 & 16384) != 0 ? -1 : i13, (i20 & 32768) != 0 ? 0 : i14, (i20 & 65536) != 0 ? -1 : i15, (i20 & 131072) != 0 ? -1 : i16, (i20 & 262144) != 0 ? -1 : i17, (i20 & 524288) != 0 ? -1 : i18, (i20 & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 ? -1 : i19);
        }

        public DisplaySwitchLatencyEvent(int i, int i2, int i3, int i4, int i5, Set<Integer> set, int i6, int i7, int i8, int i9, int i10, Set<Integer> set2, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19) {
            this.latencyMs = i;
            this.fromFoldableDeviceState = i2;
            this.fromState = i3;
            this.fromFocusedAppUid = i4;
            this.fromPipAppUid = i5;
            this.fromVisibleAppsUid = set;
            this.fromDensityDpi = i6;
            this.toFoldableDeviceState = i7;
            this.toState = i8;
            this.toFocusedAppUid = i9;
            this.toPipAppUid = i10;
            this.toVisibleAppsUid = set2;
            this.toDensityDpi = i11;
            this.notificationCount = i12;
            this.externalDisplayCount = i13;
            this.throttlingLevel = i14;
            this.vskinTemperatureC = i15;
            this.hallSensorToFirstHingeAngleChangeMs = i16;
            this.hallSensorToDeviceStateChangeMs = i17;
            this.onScreenTurningOnToOnDrawnMs = i18;
            this.onDrawnToOnScreenTurnedOnMs = i19;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public DisplaySwitchLatencyTracker(Context context, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, UnfoldTransitionInteractor unfoldTransitionInteractor, AnimationStatusRepository animationStatusRepository, KeyguardInteractor keyguardInteractor, Executor executor, CoroutineScope coroutineScope, DisplaySwitchLatencyLogger displaySwitchLatencyLogger, SystemClock systemClock) {
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
        this.backgroundDispatcher = ExecutorsKt.from(executor);
    }

    public static final int access$toStatsInt(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, DeviceStateRepository.DeviceState deviceState) {
        displaySwitchLatencyTracker.getClass();
        int i = WhenMappings.$EnumSwitchMapping$0[deviceState.ordinal()];
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i != 3) {
            return i != 4 ? 0 : 4;
        }
        return 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForDisplaySwitch(com.android.systemui.unfold.DisplaySwitchLatencyTracker r7, int r8, kotlin.coroutines.Continuation r9) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForDisplaySwitch(com.android.systemui.unfold.DisplaySwitchLatencyTracker, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForGoToSleepWithScreenOff(final com.android.systemui.unfold.DisplaySwitchLatencyTracker r8, kotlin.coroutines.Continuation r9) {
        /*
            r8.getClass()
            boolean r0 = r9 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1
            if (r0 == 0) goto L16
            r0 = r9
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1
            r0.<init>(r8, r9)
        L1b:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3e
            if (r2 != r3) goto L36
            int r8 = r0.I$0
            java.lang.Object r1 = r0.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L34
            goto L6d
        L34:
            r9 = move-exception
            goto L7a
        L36:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L3e:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.concurrent.ThreadLocalRandom r9 = java.util.concurrent.ThreadLocalRandom.current()
            int r9 = r9.nextInt()
            java.lang.String r2 = "DisplaySwitchLatency"
            java.lang.String r4 = "waitForGoToSleepWithScreenOff()"
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r9, r2, r4)
            com.android.systemui.power.domain.interactor.PowerInteractor r5 = r8.powerInteractor     // Catch: java.lang.Throwable -> L75
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.detailedWakefulness     // Catch: java.lang.Throwable -> L75
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1 r6 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$4$$inlined$filter$1     // Catch: java.lang.Throwable -> L75
            r6.<init>()     // Catch: java.lang.Throwable -> L75
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L75
            r0.L$1 = r4     // Catch: java.lang.Throwable -> L75
            r0.I$0 = r9     // Catch: java.lang.Throwable -> L75
            r0.label = r3     // Catch: java.lang.Throwable -> L75
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.first(r6, r0)     // Catch: java.lang.Throwable -> L75
            if (r8 != r1) goto L69
            goto L74
        L69:
            r0 = r2
            r7 = r9
            r9 = r8
            r8 = r7
        L6d:
            com.android.systemui.power.shared.model.WakefulnessModel r9 = (com.android.systemui.power.shared.model.WakefulnessModel) r9     // Catch: java.lang.Throwable -> L34
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r8, r0)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L74:
            return r1
        L75:
            r8 = move-exception
            r0 = r2
            r7 = r9
            r9 = r8
            r8 = r7
        L7a:
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r8, r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForGoToSleepWithScreenOff(com.android.systemui.unfold.DisplaySwitchLatencyTracker, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForScreenTurnedOn(com.android.systemui.unfold.DisplaySwitchLatencyTracker r7, kotlin.coroutines.Continuation r8) {
        /*
            r7.getClass()
            boolean r0 = r8 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1
            r0.<init>(r7, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3e
            if (r2 != r3) goto L36
            int r7 = r0.I$0
            java.lang.Object r1 = r0.L$1
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: java.lang.Throwable -> L34
            goto L6d
        L34:
            r8 = move-exception
            goto L7a
        L36:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3e:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.concurrent.ThreadLocalRandom r8 = java.util.concurrent.ThreadLocalRandom.current()
            int r8 = r8.nextInt()
            java.lang.String r2 = "DisplaySwitchLatency"
            java.lang.String r4 = "waitForScreenTurnedOn()"
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r8, r2, r4)
            com.android.systemui.power.domain.interactor.PowerInteractor r7 = r7.powerInteractor     // Catch: java.lang.Throwable -> L75
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r7.screenPowerState     // Catch: java.lang.Throwable -> L75
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1 r5 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$2$$inlined$filter$1     // Catch: java.lang.Throwable -> L75
            r5.<init>()     // Catch: java.lang.Throwable -> L75
            r0.L$0 = r2     // Catch: java.lang.Throwable -> L75
            r0.L$1 = r4     // Catch: java.lang.Throwable -> L75
            r0.I$0 = r8     // Catch: java.lang.Throwable -> L75
            r0.label = r3     // Catch: java.lang.Throwable -> L75
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r5, r0)     // Catch: java.lang.Throwable -> L75
            if (r7 != r1) goto L69
            goto L74
        L69:
            r0 = r2
            r6 = r8
            r8 = r7
            r7 = r6
        L6d:
            com.android.systemui.power.shared.model.ScreenPowerState r8 = (com.android.systemui.power.shared.model.ScreenPowerState) r8     // Catch: java.lang.Throwable -> L34
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r7, r0)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L74:
            return r1
        L75:
            r7 = move-exception
            r0 = r2
            r6 = r8
            r8 = r7
            r7 = r6
        L7a:
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r7, r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForScreenTurnedOn(com.android.systemui.unfold.DisplaySwitchLatencyTracker, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final boolean isAsleepDueToFold() {
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue();
        if (wakefulnessModel.isAsleep()) {
            if (wakefulnessModel.lastSleepReason == WakeSleepReason.FOLD) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Utils.isDeviceFoldable(this.context)) {
            BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new DisplaySwitchLatencyTracker$start$1(this, null), 2);
        }
    }
}
