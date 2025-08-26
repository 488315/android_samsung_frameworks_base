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
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OngoingCallInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((OngoingCallModel.NoCall) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            OngoingCallInteractor.this._isChipSwipedAway.updateState(null, Boolean.FALSE);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = OngoingCallInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            OngoingCallInteractor ongoingCallInteractor = OngoingCallInteractor.this;
            ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) ongoingCallInteractor.statusBarModeRepositoryStore.getDefaultDisplay()))._ongoingProcessRequiresStatusBarVisible.updateState(null, Boolean.valueOf(z));
            StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) ((StatusBarWindowController) ongoingCallInteractor.statusBarWindowControllerStore.getDefaultDisplay());
            StatusBarWindowControllerImpl.State state = statusBarWindowControllerImpl.mCurrentState;
            state.mOngoingProcessRequiresStatusBarVisible = z;
            statusBarWindowControllerImpl.apply(state);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = OngoingCallInteractor.this.new AnonymousClass3(continuation);
            anonymousClass3.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            final OngoingCallInteractor ongoingCallInteractor = OngoingCallInteractor.this;
            SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = ongoingCallInteractor.swipeStatusBarAwayGestureHandler;
            String str = OngoingCallInteractor.TAG;
            if (z) {
                swipeStatusBarAwayGestureHandler.addOnGestureDetectedCallback(str, new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        String str2 = OngoingCallInteractor.TAG;
                        ongoingCallInteractor.onStatusBarSwiped();
                        return Unit.INSTANCE;
                    }
                });
            } else {
                swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback(str);
            }
            return Unit.INSTANCE;
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isChipSwipedAway = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.isChipSwipedAway = readonlyStateFlowAsStateFlow;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(FlowKt.transformLatest(activeNotificationsInteractor.ongoingCallNotification, new OngoingCallInteractor$special$$inlined$flatMapLatest$1(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), OngoingCallModel.NoCall.INSTANCE);
        this.ongoingCallState = readonlyStateFlowStateIn;
        this.isStatusBarRequiredForOngoingCall = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, readonlyStateFlowAsStateFlow, new OngoingCallInteractor$isStatusBarRequiredForOngoingCall$1(this, null));
        this.isGestureListeningEnabled = FlowKt.combine(readonlyStateFlowStateIn, ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) statusBarModeRepositoryStore.getDefaultDisplay())).isInFullscreenMode, readonlyStateFlowAsStateFlow, new OngoingCallInteractor$isGestureListeningEnabled$1(this, null));
    }

    public final void onStatusBarSwiped() {
        Logger.d$default(this.logger, "Status bar chip swiped away", null, 2, null);
        this._isChipSwipedAway.updateState(null, Boolean.TRUE);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final ReadonlyStateFlow readonlyStateFlow = this.ongoingCallState;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$start$$inlined$filterIsInstance$1

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
                        if (obj instanceof OngoingCallModel.NoCall) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass1(null));
        CoroutineScope coroutineScope = this.scope;
        FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.isStatusBarRequiredForOngoingCall, new AnonymousClass2(null)), coroutineScope);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.isGestureListeningEnabled, new AnonymousClass3(null)), coroutineScope);
    }

    public static /* synthetic */ void isGestureListeningEnabled$annotations() {
    }

    public static /* synthetic */ void isStatusBarRequiredForOngoingCall$annotations() {
    }
}
