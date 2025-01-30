package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.content.IntentFilter;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardLongPressInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isMenuVisible;
    public final StateFlowImpl _shouldOpenSettings;
    public final AccessibilityManagerWrapper accessibilityManager;
    public final Context appContext;
    public StandaloneCoroutine delayedHideMenuJob;
    public final FeatureFlags featureFlags;
    public final ReadonlyStateFlow isLongPressHandlingEnabled;
    public final ReadonlyStateFlow isMenuVisible;
    public final UiEventLogger logger;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow shouldOpenSettings;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$1", m277f = "KeyguardLongPressInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$1 */
    public static final class C16991 extends SuspendLambda implements Function2 {
        int label;

        public C16991(Continuation<? super C16991> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardLongPressInteractor.this.new C16991(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C16991) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum LogEvents implements UiEventLogger.UiEventEnum {
        /* JADX INFO: Fake field, exist only in values array */
        LOCK_SCREEN_LONG_PRESS_POPUP_SHOWN(1292),
        LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED(1293);

        private final int _id;

        LogEvents(int i) {
            this._id = i;
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
            final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.finishedKeyguardState;
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2 */
                public final class C16982 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$special$$inlined$map$1$2", m277f = "KeyguardLongPressInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C16982.this.emit(null, this);
                        }
                    }

                    public C16982(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    Boolean valueOf = Boolean.valueOf(((KeyguardState) obj) == KeyguardState.LOCKSCREEN);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new C16982(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, ((KeyguardRepositoryImpl) keyguardRepository).isQuickSettingsVisible, new KeyguardLongPressInteractor$isLongPressHandlingEnabled$2(null));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, coroutineScope, WhileSubscribed$default, bool);
        this.isLongPressHandlingEnabled = stateIn;
        this._isMenuVisible = StateFlowKt.MutableStateFlow(bool);
        this.isMenuVisible = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new KeyguardLongPressInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), bool);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._shouldOpenSettings = MutableStateFlow;
        this.shouldOpenSettings = FlowKt.asStateFlow(MutableStateFlow);
        if (isFeatureEnabled()) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), null, 0, null, 14), new C16991(null)), coroutineScope);
        }
    }

    public final void hideMenu() {
        StandaloneCoroutine standaloneCoroutine = this.delayedHideMenuJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.delayedHideMenuJob = null;
        this._isMenuVisible.setValue(Boolean.FALSE);
    }

    public final boolean isFeatureEnabled() {
        ReleasedFlag releasedFlag = Flags.LOCK_SCREEN_LONG_PRESS_ENABLED;
        FeatureFlags featureFlags = this.featureFlags;
        if (((FeatureFlagsRelease) featureFlags).isEnabled(releasedFlag)) {
            if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.REVAMPED_WALLPAPER_UI) && this.appContext.getResources().getBoolean(R.bool.long_press_keyguard_customize_lockscreen_enabled)) {
                return true;
            }
        }
        return false;
    }

    public final void onMenuTouchGestureEnded(boolean z) {
        if (z) {
            hideMenu();
            this.logger.log(LogEvents.LOCK_SCREEN_LONG_PRESS_POPUP_CLICKED);
            this._shouldOpenSettings.setValue(Boolean.TRUE);
        } else {
            StandaloneCoroutine standaloneCoroutine = this.delayedHideMenuJob;
            if (standaloneCoroutine != null) {
                standaloneCoroutine.cancel(null);
            }
            this.delayedHideMenuJob = null;
            this.delayedHideMenuJob = BuildersKt.launch$default(this.scope, null, null, new KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1(this, null), 3);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getDEFAULT_POPUP_AUTO_HIDE_TIMEOUT_MS$annotations() {
        }
    }
}
