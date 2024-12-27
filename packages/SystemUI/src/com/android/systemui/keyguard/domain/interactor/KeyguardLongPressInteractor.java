package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.content.IntentFilter;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardLongPressInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isMenuVisible;
    public final StateFlowImpl _shouldOpenSettings;
    public final AccessibilityManagerWrapper accessibilityManager;
    public final Context appContext;
    public Job delayedHideMenuJob;
    public final FeatureFlags featureFlags;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;
    public final ReadonlyStateFlow isMenuVisible;
    public final UiEventLogger logger;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow shouldOpenSettings;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardLongPressInteractor.this.new AnonymousClass1(continuation);
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
            KeyguardLongPressInteractor keyguardLongPressInteractor = KeyguardLongPressInteractor.this;
            int i = KeyguardLongPressInteractor.$r8$clinit;
            keyguardLongPressInteractor.hideMenu();
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getDEFAULT_POPUP_AUTO_HIDE_TIMEOUT_MS$annotations() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public KeyguardLongPressInteractor(Context context, CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardRepository keyguardRepository, UiEventLogger uiEventLogger, FeatureFlags featureFlags, BroadcastDispatcher broadcastDispatcher, AccessibilityManagerWrapper accessibilityManagerWrapper) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.appContext = context;
        this.scope = coroutineScope;
        this.logger = uiEventLogger;
        this.featureFlags = featureFlags;
        this.accessibilityManager = accessibilityManagerWrapper;
        if (isFeatureEnabled()) {
            final ReadonlySharedFlow readonlySharedFlow = keyguardTransitionInteractor.finishedKeyguardState;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L4a
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.keyguard.shared.model.KeyguardState r5 = (com.android.systemui.keyguard.shared.model.KeyguardState) r5
                            com.android.systemui.keyguard.shared.model.KeyguardState r6 = com.android.systemui.keyguard.shared.model.KeyguardState.LOCKSCREEN
                            if (r5 != r6) goto L3a
                            r5 = r3
                            goto L3b
                        L3a:
                            r5 = 0
                        L3b:
                            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L4a
                            return r1
                        L4a:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, ((KeyguardRepositoryImpl) keyguardRepository).isQuickSettingsVisible, new KeyguardLongPressInteractor$isLongPressHandlingEnabled$2(null));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, coroutineScope, WhileSubscribed$default, bool);
        this.isLongPressHandlingEnabled = stateIn;
        this._isMenuVisible = StateFlowKt.MutableStateFlow(bool);
        this.isMenuVisible = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new KeyguardLongPressInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._shouldOpenSettings = MutableStateFlow;
        this.shouldOpenSettings = FlowKt.asStateFlow(MutableStateFlow);
        if (isFeatureEnabled()) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, 0, null, 14), new AnonymousClass1(null)), coroutineScope);
        }
    }

    public final void hideMenu() {
        Job job = this.delayedHideMenuJob;
        if (job != null) {
            job.cancel(null);
        }
        this.delayedHideMenuJob = null;
        this._isMenuVisible.updateState(null, Boolean.FALSE);
    }

    public final boolean isFeatureEnabled() {
        return ((FeatureFlagsClassicRelease) this.featureFlags).isEnabled(Flags.LOCK_SCREEN_LONG_PRESS_ENABLED) && this.appContext.getResources().getBoolean(R.bool.long_press_keyguard_customize_lockscreen_enabled);
    }

    public final void scheduleAutomaticMenuHiding() {
        Job job = this.delayedHideMenuJob;
        if (job != null) {
            job.cancel(null);
        }
        this.delayedHideMenuJob = null;
        this.delayedHideMenuJob = BuildersKt.launch$default(this.scope, null, null, new KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1(this, null), 3);
    }
}
