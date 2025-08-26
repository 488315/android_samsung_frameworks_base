package com.android.systemui.unfold;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Trace;
import com.android.app.tracing.TraceUtils;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import com.android.systemui.unfold.DisplaySwitchLatencyTracker;
import com.android.systemui.unfold.data.repository.ScreenTimeoutPolicyRepository;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl;
import com.android.systemui.unfold.data.repository.UnfoldTransitionStatus;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$waitForTransitionStart$$inlined$filter$1;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import com.android.systemui.util.kotlin.WithPrev;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.foresight.KnoxForesight;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* renamed from: getCOOL_DOWN_DURATION-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m3128getCOOL_DOWN_DURATIONUwyO8pc$annotations() {
        }

        /* renamed from: getSCREEN_EVENT_TIMEOUT-UwyO8pc$annotations, reason: not valid java name */
        public static /* synthetic */ void m3129getSCREEN_EVENT_TIMEOUTUwyO8pc$annotations() {
        }
    }

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

    /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C06071 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            Object L$1;
            int label;
            final /* synthetic */ DisplaySwitchLatencyTracker this$0;

            /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$start$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ DisplaySwitchLatencyEvent $event;
                final /* synthetic */ DeviceStateRepository.DeviceState $newState;
                final /* synthetic */ DeviceStateRepository.DeviceState $previousState;
                int I$0;
                long J$0;
                long J$1;
                Object L$0;
                Object L$1;
                int label;
                final /* synthetic */ DisplaySwitchLatencyTracker this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, DeviceStateRepository.DeviceState deviceState, DisplaySwitchLatencyEvent displaySwitchLatencyEvent, DeviceStateRepository.DeviceState deviceState2, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = displaySwitchLatencyTracker;
                    this.$previousState = deviceState;
                    this.$event = displaySwitchLatencyEvent;
                    this.$newState = deviceState2;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.this$0, this.$previousState, this.$event, this.$newState, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws Throwable {
                    long jCurrentTimeMillis;
                    Throwable th;
                    int i;
                    long j;
                    String str;
                    SystemClock systemClock;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = this.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
                        SystemClock systemClock2 = displaySwitchLatencyTracker.systemClock;
                        DeviceStateRepository.DeviceState deviceState = this.$newState;
                        jCurrentTimeMillis = systemClock2.currentTimeMillis();
                        int i3 = TraceUtils.$r8$clinit;
                        int iNextInt = ThreadLocalRandom.current().nextInt();
                        Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "displaySwitch", iNextInt);
                        try {
                            int statsInt = DisplaySwitchLatencyTracker.toStatsInt(deviceState);
                            this.L$0 = systemClock2;
                            this.L$1 = "DisplaySwitchLatency";
                            this.J$0 = jCurrentTimeMillis;
                            this.J$1 = 4096L;
                            this.I$0 = iNextInt;
                            this.label = 1;
                            if (DisplaySwitchLatencyTracker.access$waitForDisplaySwitch(displaySwitchLatencyTracker, statsInt, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            systemClock = systemClock2;
                            i = iNextInt;
                            j = 4096;
                            str = "DisplaySwitchLatency";
                        } catch (Throwable th2) {
                            th = th2;
                            i = iNextInt;
                            j = 4096;
                            str = "DisplaySwitchLatency";
                            Trace.asyncTraceForTrackEnd(j, str, i);
                            throw th;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        i = this.I$0;
                        j = this.J$1;
                        jCurrentTimeMillis = this.J$0;
                        str = (String) this.L$1;
                        systemClock = (SystemClock) this.L$0;
                        try {
                            ResultKt.throwOnFailure(obj);
                        } catch (Throwable th3) {
                            th = th3;
                            Trace.asyncTraceForTrackEnd(j, str, i);
                            throw th;
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    Trace.asyncTraceForTrackEnd(j, str, i);
                    long jCurrentTimeMillis2 = systemClock.currentTimeMillis() - jCurrentTimeMillis;
                    if (this.$previousState == DeviceStateRepository.DeviceState.FOLDED) {
                        this.this$0.latencyTracker.onActionEnd(13);
                    }
                    DisplaySwitchLatencyTracker displaySwitchLatencyTracker2 = this.this$0;
                    DisplaySwitchLatencyEvent displaySwitchLatencyEvent = this.$event;
                    DeviceStateRepository.DeviceState deviceState2 = this.$newState;
                    Companion companion = DisplaySwitchLatencyTracker.Companion;
                    displaySwitchLatencyTracker2.logDisplaySwitchEvent(displaySwitchLatencyEvent, deviceState2, jCurrentTimeMillis2, TrackingResult.SUCCESS);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06071(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, Continuation continuation) {
                super(2, continuation);
                this.this$0 = displaySwitchLatencyTracker;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06071 c06071 = new C06071(this.this$0, continuation);
                c06071.L$0 = obj;
                return c06071;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06071) create((WithPrev) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:34:0x010a  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x011f  */
            /* JADX WARN: Removed duplicated region for block: B:41:0x0136  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                DisplaySwitchLatencyEvent displaySwitchLatencyEvent;
                DeviceStateRepository.DeviceState deviceState;
                DisplaySwitchLatencyTracker displaySwitchLatencyTracker;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    WithPrev withPrev = (WithPrev) this.L$0;
                    DeviceStateRepository.DeviceState deviceState2 = (DeviceStateRepository.DeviceState) withPrev.component1();
                    DeviceStateRepository.DeviceState deviceState3 = (DeviceStateRepository.DeviceState) withPrev.component2();
                    DisplaySwitchLatencyTracker displaySwitchLatencyTracker2 = this.this$0;
                    if (displaySwitchLatencyTracker2.isCoolingDown) {
                        return Unit.INSTANCE;
                    }
                    if (deviceState2 == DeviceStateRepository.DeviceState.FOLDED) {
                        displaySwitchLatencyTracker2.latencyTracker.onActionStart(13);
                        if (Trace.isEnabled()) {
                            Trace.instantForTrack(4096L, "DisplaySwitchLatency", "unfold latency tracking started");
                        }
                    }
                    DisplaySwitchLatencyTracker displaySwitchLatencyTracker3 = this.this$0;
                    DisplaySwitchLatencyEvent displaySwitchLatencyEvent2 = new DisplaySwitchLatencyEvent(0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8388607, null);
                    this.this$0.getClass();
                    int statsInt = DisplaySwitchLatencyTracker.toStatsInt(deviceState2);
                    displaySwitchLatencyTracker3.getClass();
                    if (Trace.isEnabled()) {
                        Trace.instantForTrack(4096L, "DisplaySwitchLatency", "fromFoldableDeviceState=" + statsInt);
                    }
                    DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default = DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent2, 0, statsInt, 0, 0, 0, ((Boolean) displaySwitchLatencyTracker3.screenTimeoutPolicyRepository.screenTimeoutActive.$$delegate_0.getValue()).booleanValue() ? 1 : 2, 4194301);
                    try {
                        DisplaySwitchLatencyTracker.Companion.getClass();
                        long j = DisplaySwitchLatencyTracker.SCREEN_EVENT_TIMEOUT;
                        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, deviceState2, displaySwitchLatencyEventCopy$default, deviceState3, null);
                        this.L$0 = deviceState3;
                        this.L$1 = displaySwitchLatencyEventCopy$default;
                        this.label = 1;
                        if (TimeoutKt.m3471withTimeoutKLykuaI(j, anonymousClass2, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } catch (TimeoutCancellationException unused) {
                        displaySwitchLatencyEvent = displaySwitchLatencyEventCopy$default;
                        deviceState = deviceState3;
                        if (Trace.isEnabled()) {
                            Trace.instantForTrack(4096L, "DisplaySwitchLatency", "tracking timed out");
                        }
                        this.this$0.latencyTracker.onActionCancel(13);
                        DisplaySwitchLatencyTracker displaySwitchLatencyTracker4 = this.this$0;
                        DisplaySwitchLatencyTracker.Companion.getClass();
                        displaySwitchLatencyTracker4.logDisplaySwitchEvent(displaySwitchLatencyEvent, deviceState, Duration.m3457getInWholeMillisecondsimpl(DisplaySwitchLatencyTracker.SCREEN_EVENT_TIMEOUT), TrackingResult.TIMED_OUT);
                        return Unit.INSTANCE;
                    } catch (CancellationException unused2) {
                        displaySwitchLatencyEvent = displaySwitchLatencyEventCopy$default;
                        if (Trace.isEnabled()) {
                            Trace.instantForTrack(4096L, "DisplaySwitchLatency", "new state interrupted, entering cool down");
                        }
                        this.this$0.latencyTracker.onActionCancel(13);
                        displaySwitchLatencyTracker = this.this$0;
                        if (!displaySwitchLatencyTracker.isCoolingDown) {
                            displaySwitchLatencyTracker.isCoolingDown = true;
                            BuildersKt.launch$default(displaySwitchLatencyTracker.applicationScope, displaySwitchLatencyTracker.backgroundDispatcher, null, new DisplaySwitchLatencyTracker$startCoolDown$1(displaySwitchLatencyTracker, displaySwitchLatencyEvent, null), 2);
                        }
                        return Unit.INSTANCE;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    displaySwitchLatencyEvent = (DisplaySwitchLatencyEvent) this.L$1;
                    deviceState = (DeviceStateRepository.DeviceState) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                    } catch (TimeoutCancellationException unused3) {
                        if (Trace.isEnabled()) {
                        }
                        this.this$0.latencyTracker.onActionCancel(13);
                        DisplaySwitchLatencyTracker displaySwitchLatencyTracker42 = this.this$0;
                        DisplaySwitchLatencyTracker.Companion.getClass();
                        displaySwitchLatencyTracker42.logDisplaySwitchEvent(displaySwitchLatencyEvent, deviceState, Duration.m3457getInWholeMillisecondsimpl(DisplaySwitchLatencyTracker.SCREEN_EVENT_TIMEOUT), TrackingResult.TIMED_OUT);
                        return Unit.INSTANCE;
                    } catch (CancellationException unused4) {
                        if (Trace.isEnabled()) {
                        }
                        this.this$0.latencyTracker.onActionCancel(13);
                        displaySwitchLatencyTracker = this.this$0;
                        if (!displaySwitchLatencyTracker.isCoolingDown) {
                        }
                        return Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisplaySwitchLatencyTracker.this.new AnonymousClass1(continuation);
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
                DisplaySwitchLatencyTracker displaySwitchLatencyTracker = DisplaySwitchLatencyTracker.this;
                DisplaySwitchLatencyTracker$special$$inlined$filter$1 displaySwitchLatencyTracker$special$$inlined$filter$1 = displaySwitchLatencyTracker.displaySwitchStarted;
                C06071 c06071 = new C06071(displaySwitchLatencyTracker, null);
                this.label = 1;
                if (FlowKt.collectLatest(displaySwitchLatencyTracker$special$$inlined$filter$1, c06071, this) == coroutineSingletons) {
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
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(((DeviceStateRepositoryImpl) deviceStateRepository).state);
        ?? r2 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$special$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        WithPrev withPrev = (WithPrev) obj;
                        Object previousValue = withPrev.getPreviousValue();
                        DeviceStateRepository.DeviceState deviceState = DeviceStateRepository.DeviceState.FOLDED;
                        if (previousValue == deviceState || withPrev.getNewValue() == deviceState) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.displaySwitchStarted = r2;
        final Flow flow = unfoldTransitionInteractor.unfoldTransitionStatus;
        Flow flow2 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((UnfoldTransitionStatus) obj) instanceof UnfoldTransitionStatus.TransitionStarted) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop = FlowKt.drop(powerInteractor.screenPowerState);
        Flow flow3 = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((ScreenPowerState) obj) == ScreenPowerState.SCREEN_ON) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop2 = FlowKt.drop(powerInteractor.detailedWakefulness);
        this.startOrEndEvent = FlowKt.merge(r2, FlowKt.merge(flow3, new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$anyEndEventFlow$$inlined$filter$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        DisplaySwitchLatencyTracker.Companion companion = DisplaySwitchLatencyTracker.Companion;
                        DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
                        displaySwitchLatencyTracker.getClass();
                        if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.ASLEEP && !displaySwitchLatencyTracker.isAodEnabled()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, flow2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        if (r8 == r1) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ef, code lost:
    
        if (com.android.systemui.util.kotlin.SuspendKt.race(r6, r0) != r1) goto L53;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForDisplaySwitch(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, int i, ContinuationImpl continuationImpl) throws Throwable {
        DisplaySwitchLatencyTracker$waitForDisplaySwitch$1 displaySwitchLatencyTracker$waitForDisplaySwitch$1;
        boolean z;
        Throwable th;
        int i2;
        String str;
        long j;
        displaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof DisplaySwitchLatencyTracker$waitForDisplaySwitch$1) {
            displaySwitchLatencyTracker$waitForDisplaySwitch$1 = (DisplaySwitchLatencyTracker$waitForDisplaySwitch$1) continuationImpl;
            int i3 = displaySwitchLatencyTracker$waitForDisplaySwitch$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.label = i3 - Integer.MIN_VALUE;
            } else {
                displaySwitchLatencyTracker$waitForDisplaySwitch$1 = new DisplaySwitchLatencyTracker$waitForDisplaySwitch$1(displaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object objFirst = displaySwitchLatencyTracker$waitForDisplaySwitch$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = displaySwitchLatencyTracker$waitForDisplaySwitch$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objFirst);
            if (((UnfoldTransitionRepositoryImpl) displaySwitchLatencyTracker.unfoldTransitionInteractor.repository).unfoldProgressProvider.isPresent()) {
                Flow flowAreAnimationsEnabled = displaySwitchLatencyTracker.animationStatusRepository.areAnimationsEnabled();
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = displaySwitchLatencyTracker;
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0 = i;
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 1;
                objFirst = FlowKt.first(flowAreAnimationsEnabled, displaySwitchLatencyTracker$waitForDisplaySwitch$1);
            }
            displaySwitchLatencyTracker.getClass();
            if (i == 1 && z) {
                int i5 = TraceUtils.$r8$clinit;
                int iNextInt = ThreadLocalRandom.current().nextInt();
                Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForTransitionStart()", iNextInt);
                try {
                    UnfoldTransitionInteractor unfoldTransitionInteractor = displaySwitchLatencyTracker.unfoldTransitionInteractor;
                    displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = "DisplaySwitchLatency";
                    displaySwitchLatencyTracker$waitForDisplaySwitch$1.J$0 = 4096L;
                    displaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0 = iNextInt;
                    displaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 2;
                    Object objFirst2 = FlowKt.first(new UnfoldTransitionInteractor$waitForTransitionStart$$inlined$filter$1(((UnfoldTransitionRepositoryImpl) unfoldTransitionInteractor.repository).getTransitionStatus()), displaySwitchLatencyTracker$waitForDisplaySwitch$1);
                    if (objFirst2 != coroutineSingletons) {
                        objFirst2 = Unit.INSTANCE;
                    }
                    if (objFirst2 != coroutineSingletons) {
                        i2 = iNextInt;
                        str = "DisplaySwitchLatency";
                        j = 4096;
                        Unit unit = Unit.INSTANCE;
                        Trace.asyncTraceForTrackEnd(j, str, i2);
                        return Unit.INSTANCE;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    i2 = iNextInt;
                    str = "DisplaySwitchLatency";
                    j = 4096;
                    Trace.asyncTraceForTrackEnd(j, str, i2);
                    throw th;
                }
            } else {
                Function1[] function1Arr = {new DisplaySwitchLatencyTracker$waitForDisplaySwitch$3(displaySwitchLatencyTracker, null), new DisplaySwitchLatencyTracker$waitForDisplaySwitch$4(displaySwitchLatencyTracker, null)};
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = null;
                displaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 3;
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 != 2) {
                if (i4 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objFirst);
                return Unit.INSTANCE;
            }
            i2 = displaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0;
            j = displaySwitchLatencyTracker$waitForDisplaySwitch$1.J$0;
            str = (String) displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0;
            try {
                ResultKt.throwOnFailure(objFirst);
                Unit unit2 = Unit.INSTANCE;
                Trace.asyncTraceForTrackEnd(j, str, i2);
                return Unit.INSTANCE;
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i2);
                throw th;
            }
        }
        i = displaySwitchLatencyTracker$waitForDisplaySwitch$1.I$0;
        displaySwitchLatencyTracker = (DisplaySwitchLatencyTracker) displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0;
        ResultKt.throwOnFailure(objFirst);
        z = ((Boolean) objFirst).booleanValue();
        displaySwitchLatencyTracker.getClass();
        if (i == 1) {
        }
        Function1[] function1Arr2 = {new DisplaySwitchLatencyTracker$waitForDisplaySwitch$3(displaySwitchLatencyTracker, null), new DisplaySwitchLatencyTracker$waitForDisplaySwitch$4(displaySwitchLatencyTracker, null)};
        displaySwitchLatencyTracker$waitForDisplaySwitch$1.L$0 = null;
        displaySwitchLatencyTracker$waitForDisplaySwitch$1.label = 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForGoToSleepWithScreenOff(final DisplaySwitchLatencyTracker displaySwitchLatencyTracker, ContinuationImpl continuationImpl) throws Throwable {
        DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1;
        Throwable th;
        int i;
        String str;
        long j;
        displaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) {
            displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 = (DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1) continuationImpl;
            int i2 = displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label = i2 - Integer.MIN_VALUE;
            } else {
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1 = new DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1(displaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object obj = displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int i4 = TraceUtils.$r8$clinit;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForGoToSleepWithScreenOff()", iNextInt);
            try {
                final ReadonlyStateFlow readonlyStateFlow = displaySwitchLatencyTracker.powerInteractor.detailedWakefulness;
                Flow flow = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$8$$inlined$filter$1

                    /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$8$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ DisplaySwitchLatencyTracker this$0;

                        /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$lambda$8$$inlined$filter$1$2$1, reason: invalid class name */
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                DisplaySwitchLatencyTracker.Companion companion = DisplaySwitchLatencyTracker.Companion;
                                DisplaySwitchLatencyTracker displaySwitchLatencyTracker = this.this$0;
                                displaySwitchLatencyTracker.getClass();
                                if (((WakefulnessModel) obj).internalWakefulnessState == WakefulnessState.ASLEEP && !displaySwitchLatencyTracker.isAodEnabled()) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, displaySwitchLatencyTracker), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.L$0 = "DisplaySwitchLatency";
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.J$0 = 4096L;
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.I$0 = iNextInt;
                displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.label = 1;
                Object objFirst = FlowKt.first(flow, displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objFirst;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
            } catch (Throwable th2) {
                th = th2;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.I$0;
            j = displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.J$0;
            str = (String) displaySwitchLatencyTracker$waitForGoToSleepWithScreenOff$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$waitForScreenTurnedOn(DisplaySwitchLatencyTracker displaySwitchLatencyTracker, ContinuationImpl continuationImpl) throws Throwable {
        DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1 displaySwitchLatencyTracker$waitForScreenTurnedOn$1;
        Throwable th;
        int i;
        String str;
        long j;
        displaySwitchLatencyTracker.getClass();
        if (continuationImpl instanceof DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) {
            displaySwitchLatencyTracker$waitForScreenTurnedOn$1 = (DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1) continuationImpl;
            int i2 = displaySwitchLatencyTracker$waitForScreenTurnedOn$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1.label = i2 - Integer.MIN_VALUE;
            } else {
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1 = new DisplaySwitchLatencyTracker$waitForScreenTurnedOn$1(displaySwitchLatencyTracker, continuationImpl);
            }
        }
        Object obj = displaySwitchLatencyTracker$waitForScreenTurnedOn$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = displaySwitchLatencyTracker$waitForScreenTurnedOn$1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            int i4 = TraceUtils.$r8$clinit;
            int iNextInt = ThreadLocalRandom.current().nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DisplaySwitchLatency", "waitForScreenTurnedOn()", iNextInt);
            try {
                final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop = FlowKt.drop(displaySwitchLatencyTracker.powerInteractor.screenPowerState);
                Flow flow = new Flow() { // from class: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$6$$inlined$filter$1

                    /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$6$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.unfold.DisplaySwitchLatencyTracker$waitForScreenTurnedOn$lambda$6$$inlined$filter$1$2$1, reason: invalid class name */
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (((ScreenPowerState) obj) == ScreenPowerState.SCREEN_ON) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flowKt__LimitKt$drop$$inlined$unsafeFlow$1Drop.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1.L$0 = "DisplaySwitchLatency";
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1.J$0 = 4096L;
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1.I$0 = iNextInt;
                displaySwitchLatencyTracker$waitForScreenTurnedOn$1.label = 1;
                Object objFirst = FlowKt.first(flow, displaySwitchLatencyTracker$waitForScreenTurnedOn$1);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objFirst;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
            } catch (Throwable th2) {
                th = th2;
                i = iNextInt;
                str = "DisplaySwitchLatency";
                j = 4096;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = displaySwitchLatencyTracker$waitForScreenTurnedOn$1.I$0;
            j = displaySwitchLatencyTracker$waitForScreenTurnedOn$1.J$0;
            str = (String) displaySwitchLatencyTracker$waitForScreenTurnedOn$1.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                Trace.asyncTraceForTrackEnd(j, str, i);
                throw th;
            }
        }
        Trace.asyncTraceForTrackEnd(j, str, i);
        return Unit.INSTANCE;
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
        DisplaySwitchLatencyEvent displaySwitchLatencyEventCopy$default = DisplaySwitchLatencyEvent.copy$default(displaySwitchLatencyEvent, i3, 0, statsInt, i, i2, 0, 6291070);
        this.displaySwitchLatencyLogger.getClass();
        DisplaySwitchLatencyLogger.log(displaySwitchLatencyEventCopy$default);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (Utils.isDeviceFoldable(this.context.getResources(), this.deviceStateManager)) {
            BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new AnonymousClass1(null), 2);
        }
    }
}
