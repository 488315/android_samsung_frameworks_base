package com.android.systemui.education.domain.interactor;

import android.os.SystemProperties;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.ContextualEducationMetricsLogger;
import com.android.systemui.inputdevice.data.repository.UserInputDeviceRepository;
import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.time.Clock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyboardTouchpadEduInteractor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long initialDelayDuration;
    public static final long minIntervalBetweenEdu;
    public static final long usageSessionDuration;
    public final StateFlowImpl _educationTriggered;
    public final CoroutineScope backgroundScope;
    public final Clock clock;
    public final ContextualEducationInteractor contextualEducationInteractor;
    public final ReadonlyStateFlow educationTriggered;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 gestureModelMap;
    public final LauncherProxyService launcherProxyService;
    public final ContextualEducationMetricsLogger metricsLogger;
    public final Flow statsUpdateRequests;
    public final TutorialSchedulerRepository tutorialRepository;
    public final UserInputDeviceRepository userInputDeviceRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: access$getDurationForConfig-hgUFU34, reason: not valid java name */
        public static final long m2563access$getDurationForConfighgUFU34(Companion companion, String str, long j) {
            Duration.Companion companion2 = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.SECONDS;
            return DurationKt.toDuration(SystemProperties.getLong(str, Duration.m3445toLongimpl(j, durationUnit)), durationUnit);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StatsUpdateRequest {
        public final GestureType gestureType;
        public final boolean isTrackpadGesture;

        public StatsUpdateRequest(boolean z, GestureType gestureType) {
            this.isTrackpadGesture = z;
            this.gestureType = gestureType;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StatsUpdateRequest)) {
                return false;
            }
            StatsUpdateRequest statsUpdateRequest = (StatsUpdateRequest) obj;
            return this.isTrackpadGesture == statsUpdateRequest.isTrackpadGesture && this.gestureType == statsUpdateRequest.gestureType;
        }

        public final int hashCode() {
            return this.gestureType.hashCode() + (Boolean.hashCode(this.isTrackpadGesture) * 31);
        }

        public final String toString() {
            return "StatsUpdateRequest(isTrackpadGesture=" + this.isTrackpadGesture + ", gestureType=" + this.gestureType + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DeviceType.values().length];
            try {
                iArr[DeviceType.KEYBOARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceType.TOUCHPAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[GestureType.values().length];
            try {
                iArr2[GestureType.ALL_APPS.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        Companion companion = new Companion(null);
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.DAYS;
        usageSessionDuration = Companion.m2563access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.usage_session_sec", DurationKt.toDuration(3, durationUnit));
        minIntervalBetweenEdu = Companion.m2563access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.edu_interval_sec", DurationKt.toDuration(7, durationUnit));
        initialDelayDuration = Companion.m2563access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.initial_delay_sec", DurationKt.toDuration(7, durationUnit));
    }

    public KeyboardTouchpadEduInteractor(CoroutineScope coroutineScope, ContextualEducationInteractor contextualEducationInteractor, UserInputDeviceRepository userInputDeviceRepository, TutorialSchedulerRepository tutorialSchedulerRepository, LauncherProxyService launcherProxyService, ContextualEducationMetricsLogger contextualEducationMetricsLogger, Clock clock) {
        this.backgroundScope = coroutineScope;
        this.contextualEducationInteractor = contextualEducationInteractor;
        this.userInputDeviceRepository = userInputDeviceRepository;
        this.tutorialRepository = tutorialSchedulerRepository;
        this.launcherProxyService = launcherProxyService;
        this.metricsLogger = contextualEducationMetricsLogger;
        this.clock = clock;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._educationTriggered = MutableStateFlow;
        this.educationTriggered = FlowKt.asStateFlow(MutableStateFlow);
        this.statsUpdateRequests = FlowConflatedKt.conflatedCallbackFlow(new KeyboardTouchpadEduInteractor$statsUpdateRequests$1(this, null));
        this.gestureModelMap = FlowKt.combine(contextualEducationInteractor.backGestureModelFlow, contextualEducationInteractor.homeGestureModelFlow, contextualEducationInteractor.overviewGestureModelFlow, contextualEducationInteractor.allAppsGestureModelFlow, new KeyboardTouchpadEduInteractor$gestureModelMap$1(null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00e0, code lost:
    
        if (r9 == r1) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b7, code lost:
    
        if (r11 == r1) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0088, code lost:
    
        if (r2 == r1) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$incrementSignalCount(com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor r9, com.android.systemui.contextualeducation.GestureType r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.access$incrementSignalCount(com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor, com.android.systemui.contextualeducation.GestureType, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
    
        if (r7 == r1) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0052, code lost:
    
        if (r7 == r1) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object hasInitialDelayElapsed(com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1 r0 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1 r0 = new com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor r5 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L69
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r6 = (com.android.systemui.inputdevice.tutorial.data.repository.DeviceType) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor r5 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L55
        L43:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository r7 = r5.tutorialRepository
            java.lang.Object r7 = r7.getScheduledTutorialLaunchTime(r6, r0)
            if (r7 != r1) goto L55
            goto L68
        L55:
            java.time.Instant r7 = (java.time.Instant) r7
            if (r7 != 0) goto L70
            com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository r7 = r5.tutorialRepository
            r0.L$0 = r5
            r2 = 0
            r0.L$1 = r2
            r0.label = r3
            java.lang.Object r7 = r7.getNotifiedTime(r6, r0)
            if (r7 != r1) goto L69
        L68:
            return r1
        L69:
            java.time.Instant r7 = (java.time.Instant) r7
            if (r7 != 0) goto L70
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            return r5
        L70:
            java.time.Clock r5 = r5.clock
            java.time.Instant r5 = r5.instant()
            kotlin.time.DurationUnit r6 = kotlin.time.DurationUnit.SECONDS
            long r0 = com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.initialDelayDuration
            long r0 = kotlin.time.Duration.m3445toLongimpl(r0, r6)
            java.time.Instant r6 = r7.plusSeconds(r0)
            boolean r5 = r5.isAfter(r6)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.hasInitialDelayElapsed(com.android.systemui.inputdevice.tutorial.data.repository.DeviceType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isMinIntervalForToastEduElapsed(com.android.systemui.contextualeducation.GestureType r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.isMinIntervalForToastEduElapsed(com.android.systemui.contextualeducation.GestureType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:
    
        if (r7 == r1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        if (r7 == r1) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isTargetDeviceConnected(com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1 r0 = (com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1 r0 = new com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L36
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L52
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L68
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            int[] r7 = com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.WhenMappings.$EnumSwitchMapping$0
            int r6 = r6.ordinal()
            r6 = r7[r6]
            com.android.systemui.inputdevice.data.repository.UserInputDeviceRepository r5 = r5.userInputDeviceRepository
            if (r6 == r4) goto L5d
            if (r6 != r3) goto L57
            kotlinx.coroutines.flow.Flow r5 = r5.isAnyTouchpadConnectedForUser
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r5, r0)
            if (r7 != r1) goto L52
            goto L67
        L52:
            com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus r7 = (com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus) r7
            boolean r5 = r7.isConnected
            goto L6c
        L57:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        L5d:
            kotlinx.coroutines.flow.Flow r5 = r5.isAnyKeyboardConnectedForUser
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r5, r0)
            if (r7 != r1) goto L68
        L67:
            return r1
        L68:
            com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus r7 = (com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus) r7
            boolean r5 = r7.isConnected
        L6c:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.isTargetDeviceConnected(com.android.systemui.inputdevice.tutorial.data.repository.DeviceType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        KeyboardTouchpadEduInteractor$start$1 keyboardTouchpadEduInteractor$start$1 = new KeyboardTouchpadEduInteractor$start$1(this, null);
        CoroutineScope coroutineScope = this.backgroundScope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, keyboardTouchpadEduInteractor$start$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyboardTouchpadEduInteractor$start$2(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyboardTouchpadEduInteractor$start$3(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyboardTouchpadEduInteractor$start$4(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyboardTouchpadEduInteractor$start$5(this, null), 7);
    }
}
