package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.content.IntentFilter;
import android.os.PowerManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.shade.PulsingGestureListener;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardTouchHandlingInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isMenuVisible;
    public final StateFlowImpl _shouldOpenSettings;
    public final AccessibilityManagerWrapper accessibilityManager;
    public final Context context;
    public StandaloneCoroutine delayedHideMenuJob;
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final ReadonlyStateFlow isDoubleTapHandlingEnabled;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;
    public final ReadonlyStateFlow isMenuVisible;
    public final UiEventLogger logger;
    public final PowerManager powerManager;
    public final PulsingGestureListener pulsingGestureListener;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow shouldOpenSettings;
    public final SystemClock systemClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTouchHandlingInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardTouchHandlingInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            KeyguardTouchHandlingInteractor keyguardTouchHandlingInteractor = KeyguardTouchHandlingInteractor.this;
            int i = KeyguardTouchHandlingInteractor.$r8$clinit;
            keyguardTouchHandlingInteractor.hideMenu();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getDEFAULT_POPUP_AUTO_HIDE_TIMEOUT_MS$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LogEvents implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ LogEvents[] $VALUES;
        public static final LogEvents LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED;
        public static final LogEvents LOCK_SCREEN_LONG_PRESS_POPUP_SHOWN = null;
        private final int _id;

        static {
            LogEvents logEvents = new LogEvents("LOCK_SCREEN_LONG_PRESS_POPUP_SHOWN", 0, 1292);
            LogEvents logEvents2 = new LogEvents("LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED", 1, 1293);
            LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED = logEvents2;
            LogEvents[] logEventsArr = {logEvents, logEvents2};
            $VALUES = logEventsArr;
            EnumEntriesKt.enumEntries(logEventsArr);
        }

        private LogEvents(String str, int i, int i2) {
            this._id = i2;
        }

        public static LogEvents valueOf(String str) {
            return (LogEvents) Enum.valueOf(LogEvents.class, str);
        }

        public static LogEvents[] values() {
            return (LogEvents[]) $VALUES.clone();
        }

        public final int getId() {
            return this._id;
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardTouchHandlingInteractor(Context context, CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardRepository keyguardRepository, UiEventLogger uiEventLogger, FeatureFlags featureFlags, BroadcastDispatcher broadcastDispatcher, AccessibilityManagerWrapper accessibilityManagerWrapper, PulsingGestureListener pulsingGestureListener, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, PowerManager powerManager, SystemClock systemClock) {
        this.context = context;
        this.scope = coroutineScope;
        this.logger = uiEventLogger;
        this.accessibilityManager = accessibilityManagerWrapper;
        this.pulsingGestureListener = pulsingGestureListener;
        this.faceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.powerManager = powerManager;
        this.systemClock = systemClock;
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = context.getResources().getBoolean(R.bool.long_press_keyguard_customize_lockscreen_enabled) ? new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardTransitionInteractor.isFinishedIn$1(KeyguardState.LOCKSCREEN), ((KeyguardRepositoryImpl) keyguardRepository).isQuickSettingsVisible, new KeyguardTouchHandlingInteractor$isLongPressHandlingEnabled$1(null)) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, WhileSubscribed$default, bool);
        this.isLongPressHandlingEnabled = stateIn;
        this.isDoubleTapHandlingEnabled = FlowKt.stateIn(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this._isMenuVisible = StateFlowKt.MutableStateFlow(bool);
        this.isMenuVisible = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new KeyguardTouchHandlingInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._shouldOpenSettings = MutableStateFlow;
        this.shouldOpenSettings = FlowKt.asStateFlow(MutableStateFlow);
        if (context.getResources().getBoolean(R.bool.long_press_keyguard_customize_lockscreen_enabled)) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, 14), new AnonymousClass1(null)), coroutineScope);
        }
    }

    public final void hideMenu() {
        StandaloneCoroutine standaloneCoroutine = this.delayedHideMenuJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.delayedHideMenuJob = null;
        this._isMenuVisible.updateState(null, Boolean.FALSE);
    }

    public final void scheduleAutomaticMenuHiding() {
        StandaloneCoroutine standaloneCoroutine = this.delayedHideMenuJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.delayedHideMenuJob = null;
        this.delayedHideMenuJob = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new KeyguardTouchHandlingInteractor$scheduleAutomaticMenuHiding$1(this, null), 7);
    }
}
