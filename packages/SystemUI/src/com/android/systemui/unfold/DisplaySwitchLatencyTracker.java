package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.unfold.data.repository.ScreenTimeoutPolicyRepository;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.foresight.KnoxForesight;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplaySwitchLatencyTracker implements CoreStartable {
    public static final long COOL_DOWN_DURATION;
    public static final Companion Companion = new Companion(null);
    public static final long SCREEN_EVENT_TIMEOUT;
    public final AnimationStatusRepository animationStatusRepository;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final DeviceStateManager deviceStateManager;
    public final DisplaySwitchLatencyLogger displaySwitchLatencyLogger;
    public final DisplaySwitchLatencyTracker$special$$inlined$filter$1 displaySwitchStarted;
    public boolean isCoolingDown;
    public final KeyguardInteractor keyguardInteractor;
    public final LatencyTracker latencyTracker;
    public final PowerInteractor powerInteractor;
    public final ScreenTimeoutPolicyRepository screenTimeoutPolicyRepository;
    public final Executor singleThreadBgExecutor;
    public final ChannelLimitedFlowMerge startOrEndEvent;
    public final SystemClock systemClock;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getCOOL_DOWN_DURATION-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m3111getCOOL_DOWN_DURATIONUwyO8pc$annotations() {
        }

        /* renamed from: getSCREEN_EVENT_TIMEOUT-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m3112getSCREEN_EVENT_TIMEOUTUwyO8pc$annotations() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        public final int screenWakelockStatus;
        public final int throttlingLevel;
        public final int toDensityDpi;
        public final int toFocusedAppUid;
        public final int toFoldableDeviceState;
        public final int toPipAppUid;
        public final int toState;
        public final Set toVisibleAppsUid;
        public final int trackingResult;
        public final int vskinTemperatureC;

        public DisplaySwitchLatencyEvent() {
            this(0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8388607, null);
        }

        public static DisplaySwitchLatencyEvent copy$default(DisplaySwitchLatencyEvent displaySwitchLatencyEvent, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            int i8 = (i7 & 1) != 0 ? displaySwitchLatencyEvent.latencyMs : i;
            int i9 = (i7 & 2) != 0 ? displaySwitchLatencyEvent.fromFoldableDeviceState : i2;
            int i10 = displaySwitchLatencyEvent.fromState;
            int i11 = displaySwitchLatencyEvent.fromFocusedAppUid;
            int i12 = displaySwitchLatencyEvent.fromPipAppUid;
            Set set = displaySwitchLatencyEvent.fromVisibleAppsUid;
            int i13 = displaySwitchLatencyEvent.fromDensityDpi;
            int i14 = (i7 & 128) != 0 ? displaySwitchLatencyEvent.toFoldableDeviceState : i3;
            int i15 = (i7 & 256) != 0 ? displaySwitchLatencyEvent.toState : i4;
            int i16 = displaySwitchLatencyEvent.toFocusedAppUid;
            int i17 = displaySwitchLatencyEvent.toPipAppUid;
            Set set2 = displaySwitchLatencyEvent.toVisibleAppsUid;
            int i18 = displaySwitchLatencyEvent.toDensityDpi;
            int i19 = displaySwitchLatencyEvent.notificationCount;
            int i20 = displaySwitchLatencyEvent.externalDisplayCount;
            int i21 = displaySwitchLatencyEvent.throttlingLevel;
            int i22 = displaySwitchLatencyEvent.vskinTemperatureC;
            int i23 = displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs;
            int i24 = displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs;
            int i25 = displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs;
            int i26 = displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs;
            int i27 = (i7 & 2097152) != 0 ? displaySwitchLatencyEvent.trackingResult : i5;
            int i28 = (i7 & 4194304) != 0 ? displaySwitchLatencyEvent.screenWakelockStatus : i6;
            displaySwitchLatencyEvent.getClass();
            return new DisplaySwitchLatencyEvent(i8, i9, i10, i11, i12, set, i13, i14, i15, i16, i17, set2, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27, i28);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplaySwitchLatencyEvent)) {
                return false;
            }
            DisplaySwitchLatencyEvent displaySwitchLatencyEvent = (DisplaySwitchLatencyEvent) obj;
            return this.latencyMs == displaySwitchLatencyEvent.latencyMs && this.fromFoldableDeviceState == displaySwitchLatencyEvent.fromFoldableDeviceState && this.fromState == displaySwitchLatencyEvent.fromState && this.fromFocusedAppUid == displaySwitchLatencyEvent.fromFocusedAppUid && this.fromPipAppUid == displaySwitchLatencyEvent.fromPipAppUid && Intrinsics.areEqual(this.fromVisibleAppsUid, displaySwitchLatencyEvent.fromVisibleAppsUid) && this.fromDensityDpi == displaySwitchLatencyEvent.fromDensityDpi && this.toFoldableDeviceState == displaySwitchLatencyEvent.toFoldableDeviceState && this.toState == displaySwitchLatencyEvent.toState && this.toFocusedAppUid == displaySwitchLatencyEvent.toFocusedAppUid && this.toPipAppUid == displaySwitchLatencyEvent.toPipAppUid && Intrinsics.areEqual(this.toVisibleAppsUid, displaySwitchLatencyEvent.toVisibleAppsUid) && this.toDensityDpi == displaySwitchLatencyEvent.toDensityDpi && this.notificationCount == displaySwitchLatencyEvent.notificationCount && this.externalDisplayCount == displaySwitchLatencyEvent.externalDisplayCount && this.throttlingLevel == displaySwitchLatencyEvent.throttlingLevel && this.vskinTemperatureC == displaySwitchLatencyEvent.vskinTemperatureC && this.hallSensorToFirstHingeAngleChangeMs == displaySwitchLatencyEvent.hallSensorToFirstHingeAngleChangeMs && this.hallSensorToDeviceStateChangeMs == displaySwitchLatencyEvent.hallSensorToDeviceStateChangeMs && this.onScreenTurningOnToOnDrawnMs == displaySwitchLatencyEvent.onScreenTurningOnToOnDrawnMs && this.onDrawnToOnScreenTurnedOnMs == displaySwitchLatencyEvent.onDrawnToOnScreenTurnedOnMs && this.trackingResult == displaySwitchLatencyEvent.trackingResult && this.screenWakelockStatus == displaySwitchLatencyEvent.screenWakelockStatus;
        }

        public final int hashCode() {
            return Integer.hashCode(this.screenWakelockStatus) + ReorderTile$$ExternalSyntheticOutline0.m(this.trackingResult, ReorderTile$$ExternalSyntheticOutline0.m(this.onDrawnToOnScreenTurnedOnMs, ReorderTile$$ExternalSyntheticOutline0.m(this.onScreenTurningOnToOnDrawnMs, ReorderTile$$ExternalSyntheticOutline0.m(this.hallSensorToDeviceStateChangeMs, ReorderTile$$ExternalSyntheticOutline0.m(this.hallSensorToFirstHingeAngleChangeMs, ReorderTile$$ExternalSyntheticOutline0.m(this.vskinTemperatureC, ReorderTile$$ExternalSyntheticOutline0.m(this.throttlingLevel, ReorderTile$$ExternalSyntheticOutline0.m(this.externalDisplayCount, ReorderTile$$ExternalSyntheticOutline0.m(this.notificationCount, ReorderTile$$ExternalSyntheticOutline0.m(this.toDensityDpi, (this.toVisibleAppsUid.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.toPipAppUid, ReorderTile$$ExternalSyntheticOutline0.m(this.toFocusedAppUid, ReorderTile$$ExternalSyntheticOutline0.m(this.toState, ReorderTile$$ExternalSyntheticOutline0.m(this.toFoldableDeviceState, ReorderTile$$ExternalSyntheticOutline0.m(this.fromDensityDpi, (this.fromVisibleAppsUid.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.fromPipAppUid, ReorderTile$$ExternalSyntheticOutline0.m(this.fromFocusedAppUid, ReorderTile$$ExternalSyntheticOutline0.m(this.fromState, ReorderTile$$ExternalSyntheticOutline0.m(this.fromFoldableDeviceState, Integer.hashCode(this.latencyMs) * 31, 31), 31), 31), 31)) * 31, 31), 31), 31), 31), 31)) * 31, 31), 31), 31), 31), 31), 31), 31), 31), 31), 31);
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
            sb.append(this.onDrawnToOnScreenTurnedOnMs);
            sb.append(", trackingResult=");
            sb.append(this.trackingResult);
            sb.append(", screenWakelockStatus=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.screenWakelockStatus, ")", sb);
        }

        public DisplaySwitchLatencyEvent(int i, int i2, int i3, int i4, int i5, Set set, int i6, int i7, int i8, int i9, int i10, Set set2, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21, int i22, DefaultConstructorMarker defaultConstructorMarker) {
            this((i22 & 1) != 0 ? -1 : i, (i22 & 2) != 0 ? 0 : i2, (i22 & 4) != 0 ? 0 : i3, (i22 & 8) != 0 ? -1 : i4, (i22 & 16) != 0 ? -1 : i5, (i22 & 32) != 0 ? EmptySet.INSTANCE : set, (i22 & 64) != 0 ? -1 : i6, (i22 & 128) != 0 ? 0 : i7, (i22 & 256) != 0 ? 0 : i8, (i22 & 512) != 0 ? -1 : i9, (i22 & 1024) != 0 ? -1 : i10, (i22 & 2048) != 0 ? EmptySet.INSTANCE : set2, (i22 & 4096) != 0 ? -1 : i11, (i22 & 8192) != 0 ? -1 : i12, (i22 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? -1 : i13, (i22 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? 0 : i14, (i22 & 65536) != 0 ? -1 : i15, (i22 & 131072) != 0 ? -1 : i16, (i22 & 262144) != 0 ? -1 : i17, (i22 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? -1 : i18, (i22 & 1048576) != 0 ? -1 : i19, (i22 & 2097152) != 0 ? 0 : i20, (i22 & 4194304) != 0 ? 0 : i21);
        }

        public DisplaySwitchLatencyEvent(int i, int i2, int i3, int i4, int i5, Set<Integer> set, int i6, int i7, int i8, int i9, int i10, Set<Integer> set2, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21) {
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
            this.trackingResult = i20;
            this.screenWakelockStatus = i21;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TrackingResult {
        public static final /* synthetic */ TrackingResult[] $VALUES;
        public static final TrackingResult CORRUPTED;
        public static final TrackingResult SUCCESS;
        public static final TrackingResult TIMED_OUT;

        static {
            TrackingResult trackingResult = new TrackingResult(KnoxForesight.SUCCESS, 0);
            SUCCESS = trackingResult;
            TrackingResult trackingResult2 = new TrackingResult("CORRUPTED", 1);
            CORRUPTED = trackingResult2;
            TrackingResult trackingResult3 = new TrackingResult("TIMED_OUT", 2);
            TIMED_OUT = trackingResult3;
            TrackingResult[] trackingResultArr = {trackingResult, trackingResult2, trackingResult3};
            $VALUES = trackingResultArr;
            EnumEntriesKt.enumEntries(trackingResultArr);
        }

        private TrackingResult(String str, int i) {
        }

        public static TrackingResult valueOf(String str) {
            return (TrackingResult) Enum.valueOf(TrackingResult.class, str);
        }

        public static TrackingResult[] values() {
            return (TrackingResult[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[TrackingResult.values().length];
            try {
                iArr2[TrackingResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TrackingResult.CORRUPTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[TrackingResult.TIMED_OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.SECONDS;
        SCREEN_EVENT_TIMEOUT = DurationKt.toDuration(15, durationUnit);
        COOL_DOWN_DURATION = DurationKt.toDuration(2, durationUnit);
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1, kotlinx.coroutines.flow.Flow] */
    public DisplaySwitchLatencyTracker(Context context, DeviceStateRepository deviceStateRepository, PowerInteractor powerInteractor, ScreenTimeoutPolicyRepository screenTimeoutPolicyRepository, UnfoldTransitionInteractor unfoldTransitionInteractor, AnimationStatusRepository animationStatusRepository, KeyguardInteractor keyguardInteractor, Executor executor, CoroutineScope coroutineScope, DisplaySwitchLatencyLogger displaySwitchLatencyLogger, SystemClock systemClock, DeviceStateManager deviceStateManager, LatencyTracker latencyTracker) {
        this.context = context;
        this.powerInteractor = powerInteractor;
        this.screenTimeoutPolicyRepository = screenTimeoutPolicyRepository;
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.animationStatusRepository = animationStatusRepository;
        this.keyguardInteractor = keyguardInteractor;
        this.singleThreadBgExecutor = executor;
        this.applicationScope = coroutineScope;
        this.displaySwitchLatencyLogger = displaySwitchLatencyLogger;
        this.systemClock = systemClock;
        this.deviceStateManager = deviceStateManager;
        this.latencyTracker = latencyTracker;
        this.backgroundDispatcher = ExecutorsKt.from(executor);
        final Flow pairwise = FlowKt.pairwise(((DeviceStateRepositoryImpl) deviceStateRepository).state);
        ?? r2 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
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
                        boolean r0 = r7 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.util.kotlin.WithPrev r7 = (com.android.systemui.util.kotlin.WithPrev) r7
                        java.lang.Object r2 = r7.getPreviousValue()
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r4 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.FOLDED
                        if (r2 == r4) goto L43
                        java.lang.Object r7 = r7.getNewValue()
                        if (r7 != r4) goto L4e
                    L43:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.displaySwitchStarted = r2;
        final Flow flow = unfoldTransitionInteractor.unfoldTransitionStatus;
        Flow flow2 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1$2$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1$2$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.unfold.data.repository.UnfoldTransitionStatus r6 = (com.android.systemui.unfold.data.repository.UnfoldTransitionStatus) r6
                        boolean r6 = r6 instanceof com.android.systemui.unfold.data.repository.UnfoldTransitionStatus.TransitionStarted
                        if (r6 == 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 drop = kotlinx.coroutines.flow.FlowKt.drop(powerInteractor.screenPowerState);
        Flow flow3 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2$2$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2$2$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.power.shared.model.ScreenPowerState r6 = (com.android.systemui.power.shared.model.ScreenPowerState) r6
                        com.android.systemui.power.shared.model.ScreenPowerState r2 = com.android.systemui.power.shared.model.ScreenPowerState.SCREEN_ON
                        if (r6 != r2) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 drop2 = kotlinx.coroutines.flow.FlowKt.drop(powerInteractor.detailedWakefulness);
        this.startOrEndEvent = kotlinx.coroutines.flow.FlowKt.merge(r2, kotlinx.coroutines.flow.FlowKt.merge(flow3, new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ DisplaySwitchLatencyTracker this$0;

                /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisplaySwitchLatencyTracker displaySwitchLatencyTracker) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = displaySwitchLatencyTracker;
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
                        boolean r0 = r7 instanceof com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3$2$1 r0 = (com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3$2$1 r0 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.power.shared.model.WakefulnessModel r7 = (com.android.systemui.power.shared.model.WakefulnessModel) r7
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker$Companion r2 = com.android.systemui.unfold.DisplaySwitchLatencyTracker.Companion
                        com.android.systemui.unfold.DisplaySwitchLatencyTracker r2 = r5.this$0
                        r2.getClass()
                        com.android.systemui.power.shared.model.WakefulnessState r7 = r7.internalWakefulnessState
                        com.android.systemui.power.shared.model.WakefulnessState r4 = com.android.systemui.power.shared.model.WakefulnessState.ASLEEP
                        if (r7 != r4) goto L53
                        boolean r7 = r2.isAodEnabled()
                        if (r7 != 0) goto L53
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, flow2));
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
    public static final java.lang.Object access$waitForDisplaySwitch(com.android.systemui.unfold.DisplaySwitchLatencyTracker r6, int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForDisplaySwitch(com.android.systemui.unfold.DisplaySwitchLatencyTracker, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForGoToSleepWithScreenOff(final com.android.systemui.unfold.DisplaySwitchLatencyTracker r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
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
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$8$$inlined$filter$1 r7 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$8$$inlined$filter$1     // Catch: java.lang.Throwable -> L78
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForGoToSleepWithScreenOff(com.android.systemui.unfold.DisplaySwitchLatencyTracker, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$waitForScreenTurnedOn(com.android.systemui.unfold.DisplaySwitchLatencyTracker r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
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
            com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$6$$inlined$filter$1 r6 = new com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$6$$inlined$filter$1     // Catch: java.lang.Throwable -> L7c
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.DisplaySwitchLatencyTracker.access$waitForScreenTurnedOn(com.android.systemui.unfold.DisplaySwitchLatencyTracker, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static int toStatsInt(DeviceStateRepository.DeviceState deviceState) {
        int i = WhenMappings.$EnumSwitchMapping$0[deviceState.ordinal()];
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    i2 = 4;
                    if (i != 4) {
                        return 0;
                    }
                }
            }
        }
        return i2;
    }

    public final boolean isAodEnabled() {
        return ((Boolean) this.keyguardInteractor.isAodAvailable.$$delegate_0.getValue()).booleanValue();
    }

    public final boolean isAsleepDueToFold() {
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.powerInteractor.detailedWakefulness.$$delegate_0.getValue();
        if (wakefulnessModel.isAsleep()) {
            return wakefulnessModel.lastSleepReason == WakeSleepReason.FOLD;
        }
        return false;
    }

    public final void logDisplaySwitchEvent(DisplaySwitchLatencyEvent displaySwitchLatencyEvent, DeviceStateRepository.DeviceState deviceState, long j, TrackingResult trackingResult) {
        int i;
        int i2 = 1;
        if (isAsleepDueToFold() && isAodEnabled()) {
            i = 1;
        } else {
            i = (!isAsleepDueToFold() || isAodEnabled()) ? 0 : 9;
        }
        if (Trace.isEnabled()) {
            Trace.instantForTrack(4096L, "DisplaySwitchLatency", "toFoldableDeviceState=" + deviceState + ", toState=" + i);
        }
        int statsInt = toStatsInt(deviceState);
        int i3 = (int) j;
        int i4 = WhenMappings.$EnumSwitchMapping$1[trackingResult.ordinal()];
        if (i4 != 1) {
            i2 = 2;
            if (i4 != 2) {
                i2 = 3;
                if (i4 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
            }
        }
        DisplaySwitchLatencyEvent copy$default = DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent, i3, 0, statsInt, i, i2, 0, 6291070);
        this.displaySwitchLatencyLogger.getClass();
        DisplaySwitchLatencyLogger.log(copy$default);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Utils.isDeviceFoldable(this.context.getResources(), this.deviceStateManager)) {
            BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new DisplaySwitchLatencyTracker$start$1(this, null), 2);
        }
    }
}
