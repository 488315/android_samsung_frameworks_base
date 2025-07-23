package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.CoreStartable;
import com.android.systemui.activity.data.repository.ActivityManagerRepository;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallInteractor implements CoreStartable {
    public static final String TAG;
    public final StateFlowImpl _isChipSwipedAway;
    public final ActivityManagerRepository activityManagerRepository;
    public final ReadonlyStateFlow isChipSwipedAway;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isGestureListeningEnabled;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isStatusBarRequiredForOngoingCall;
    public final Logger logger;
    public final ReadonlyStateFlow ongoingCallState;
    public final CoroutineScope scope;
    public final StatusBarModeRepositoryStore statusBarModeRepositoryStore;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;
    public final SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler;

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
        TAG = "OngoingCall";
    }

    public OngoingCallInteractor(CoroutineScope coroutineScope, ActivityManagerRepository activityManagerRepository, StatusBarModeRepositoryStore statusBarModeRepositoryStore, StatusBarWindowControllerStore statusBarWindowControllerStore, SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler, ActiveNotificationsInteractor activeNotificationsInteractor, LogBuffer logBuffer) {
        this.scope = coroutineScope;
        this.activityManagerRepository = activityManagerRepository;
        this.statusBarModeRepositoryStore = statusBarModeRepositoryStore;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.swipeStatusBarAwayGestureHandler = swipeStatusBarAwayGestureHandler;
        this.logger = new Logger(logBuffer, TAG);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isChipSwipedAway = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.isChipSwipedAway = asStateFlow;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(FlowKt.transformLatest(activeNotificationsInteractor.ongoingCallNotification, new OngoingCallInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), OngoingCallModel.NoCall.INSTANCE);
        this.ongoingCallState = stateIn;
        this.isStatusBarRequiredForOngoingCall = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, asStateFlow, new OngoingCallInteractor$isStatusBarRequiredForOngoingCall$1(this, null));
        this.isGestureListeningEnabled = FlowKt.combine(stateIn, ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) statusBarModeRepositoryStore.getDefaultDisplay())).isInFullscreenMode, asStateFlow, new OngoingCallInteractor$isGestureListeningEnabled$1(this, null));
    }

    public final void onStatusBarSwiped() {
        Logger.d$default(this.logger, "Status bar chip swiped away", null, 2, null);
        this._isChipSwipedAway.updateState(null, Boolean.TRUE);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final ReadonlyStateFlow readonlyStateFlow = this.ongoingCallState;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1$2$1 r0 = (com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1$2$1 r0 = new com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        boolean r6 = r5 instanceof com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel.NoCall
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new OngoingCallInteractor$start$1(this, null));
        CoroutineScope coroutineScope = this.scope;
        FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.isStatusBarRequiredForOngoingCall, new OngoingCallInteractor$start$2(this, null)), coroutineScope);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.isGestureListeningEnabled, new OngoingCallInteractor$start$3(this, null)), coroutineScope);
    }

    public static /* synthetic */ void isGestureListeningEnabled$annotations() {
    }

    public static /* synthetic */ void isStatusBarRequiredForOngoingCall$annotations() {
    }
}
