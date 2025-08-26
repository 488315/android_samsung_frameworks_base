package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IActivityManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.customize.viewcontroller.QSLayoutEditViewController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.notification.shared.CallType;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class OngoingCallController implements CallbackController, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public CallNotificationInfo callNotificationInfo;
    public View chipView;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DumpManager dumpManager;
    public final IActivityManager iActivityManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isFullscreen;
    public final LogBuffer logger;
    public final Executor mainExecutor;
    public final OngoingCallRepository ongoingCallRepository;
    public View parent;
    public final OngoingCallControllerExt samsungExt;
    public final CoroutineScope scope;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public final StatusBarWindowControllerStore statusBarWindowControllerStore;
    public final SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler;
    public final SystemClock systemClock;
    public final List mListeners = new ArrayList();
    public final CallAppUidObserver uidObserver = new CallAppUidObserver();
    public final OngoingCallController$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            OngoingCallController ongoingCallController = this.this$0;
            if (ongoingCallController.hasOngoingCall()) {
                ongoingCallController.updateCallChipScale(ongoingCallController.indicatorScaleGardener.getLatestScaleModel(ongoingCallController.context).ratio);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            onDensityOrFontScaleChanged();
        }
    };

    public final class CallAppUidObserver extends UidObserver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Integer callAppUid;
        public boolean isCallAppVisible;
        public boolean isRegistered;

        public CallAppUidObserver() {
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            Integer num = this.callAppUid;
            if (num == null || i != num.intValue()) {
                return;
            }
            boolean z = this.isCallAppVisible;
            OngoingCallController ongoingCallController = OngoingCallController.this;
            int i4 = OngoingCallController.$r8$clinit;
            ongoingCallController.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i5 = StatusBarChipsModernization.$r8$clinit;
            boolean z2 = i2 <= 2;
            this.isCallAppVisible = z2;
            if (z != z2) {
                LogBuffer logBuffer = OngoingCallController.this.logger;
                LogMessage logMessageObtain = logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(6), null);
                ((LogMessageImpl) logMessageObtain).bool1 = this.isCallAppVisible;
                logBuffer.commit(logMessageObtain);
                final OngoingCallController ongoingCallController2 = OngoingCallController.this;
                ongoingCallController2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallAppUidObserver$onUidStateChanged$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        OngoingCallController ongoingCallController3 = ongoingCallController2;
                        int i6 = OngoingCallController.$r8$clinit;
                        ongoingCallController3.sendStateChangeEvent();
                    }
                });
            }
        }
    }

    /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OngoingCallController.this.new AnonymousClass1(continuation);
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
                final OngoingCallController ongoingCallController = OngoingCallController.this;
                Flow flow = ongoingCallController.activeNotificationsInteractor.ongoingCallNotification;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj2;
                        int i2 = OngoingCallController.$r8$clinit;
                        OngoingCallController ongoingCallController2 = ongoingCallController;
                        ongoingCallController2.getClass();
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i3 = StatusBarChipsModernization.$r8$clinit;
                        LogBuffer logBuffer = ongoingCallController2.logger;
                        if (activeNotificationModel == null) {
                            logBuffer.commit(logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(2), null));
                            ongoingCallController2.removeChipInfo();
                        } else {
                            CallType callType = CallType.Ongoing;
                            CallType callType2 = activeNotificationModel.callType;
                            if (callType2 != callType) {
                                LogMessage logMessageObtain = logBuffer.obtain("OngoingCall", LogLevel.ERROR, new OngoingCallController$$ExternalSyntheticLambda2(3), null);
                                ((LogMessageImpl) logMessageObtain).str1 = callType2.name();
                                logBuffer.commit(logMessageObtain);
                                ongoingCallController2.removeChipInfo();
                            } else {
                                LogMessage logMessageObtain2 = logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(4), null);
                                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                                logMessageImpl.str1 = activeNotificationModel.key;
                                logMessageImpl.long1 = activeNotificationModel.whenTime;
                                logMessageImpl.str1 = callType2.name();
                                logMessageImpl.bool1 = activeNotificationModel.statusBarChipIconView != null;
                                logBuffer.commit(logMessageObtain2);
                                PendingIntent pendingIntent = activeNotificationModel.contentIntent;
                                InstanceId instanceId = activeNotificationModel.instanceId;
                                ActiveNotificationsInteractor.Companion.getClass();
                                boolean zIsOngoingCallNotification = ActiveNotificationsInteractor.Companion.isOngoingCallNotification(activeNotificationModel);
                                CallNotificationInfo callNotificationInfo = ongoingCallController2.callNotificationInfo;
                                boolean z = callNotificationInfo != null ? callNotificationInfo.statusBarSwipedAway : false;
                                CallNotificationInfo callNotificationInfo2 = new CallNotificationInfo(activeNotificationModel.key, activeNotificationModel.whenTime, activeNotificationModel.statusBarChipIconView, pendingIntent, activeNotificationModel.uid, activeNotificationModel.appName, instanceId, activeNotificationModel.promotedContent, zIsOngoingCallNotification, z, activeNotificationModel.callChipColor, activeNotificationModel.extraVisibleFlag);
                                if (!callNotificationInfo2.equals(ongoingCallController2.callNotificationInfo)) {
                                    ongoingCallController2.callNotificationInfo = callNotificationInfo2;
                                    ongoingCallController2.updateChip();
                                    if (ongoingCallController2.callNotificationInfo == null) {
                                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_CALL_CHIP_GENERATED);
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return OngoingCallController.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlow = ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) OngoingCallController.this.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode;
                final OngoingCallController ongoingCallController = OngoingCallController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController.start.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        OngoingCallController ongoingCallController2 = ongoingCallController;
                        ongoingCallController2.isFullscreen = zBooleanValue;
                        ongoingCallController2.updateGestureListening();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1] */
    public OngoingCallController(OngoingCallControllerExt ongoingCallControllerExt, CoroutineScope coroutineScope, Context context, OngoingCallRepository ongoingCallRepository, ActiveNotificationsInteractor activeNotificationsInteractor, Executor executor, IActivityManager iActivityManager, DumpManager dumpManager, StatusBarWindowControllerStore statusBarWindowControllerStore, SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler, StatusBarModeRepositoryStore statusBarModeRepositoryStore, LogBuffer logBuffer, KeyguardCallChipController keyguardCallChipController, SystemClock systemClock, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        this.samsungExt = ongoingCallControllerExt;
        this.scope = coroutineScope;
        this.context = context;
        this.ongoingCallRepository = ongoingCallRepository;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.mainExecutor = executor;
        this.iActivityManager = iActivityManager;
        this.dumpManager = dumpManager;
        this.statusBarWindowControllerStore = statusBarWindowControllerStore;
        this.swipeStatusBarAwayGestureHandler = swipeStatusBarAwayGestureHandler;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.logger = logBuffer;
        this.systemClock = systemClock;
        this.configurationController = configurationController;
        this.indicatorScaleGardener = indicatorScaleGardener;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Active call notification: " + this.callNotificationInfo);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "Call app visible: ", this.uidObserver.isCallAppVisible);
    }

    public final boolean hasOngoingCall() {
        int i;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = StatusBarChipsModernization.$r8$clinit;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo == null || !callNotificationInfo.isOngoing) {
            return false;
        }
        return !this.uidObserver.isCallAppVisible || ((i = callNotificationInfo.extraVisibleFlag) != 0 && i == 1);
    }

    public final void hideTimeViewByOngoingChip(boolean z) {
        OngoingCallControllerExt ongoingCallControllerExt = this.samsungExt;
        if (ongoingCallControllerExt.isShowingOAChip != z) {
            ongoingCallControllerExt.isShowingOAChip = z;
            OngoingCallChronometer ongoingCallChronometer = ongoingCallControllerExt.timeView;
            if (ongoingCallChronometer == null || ongoingCallChronometer.isShowingOAChip == z) {
                return;
            }
            ongoingCallChronometer.isShowingOAChip = z;
            ongoingCallChronometer.requestLayout();
        }
    }

    public final void removeChipInfo() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        this.callNotificationInfo = null;
        this.swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCall");
        sendStateChangeEvent();
        CallAppUidObserver callAppUidObserver = this.uidObserver;
        callAppUidObserver.callAppUid = null;
        callAppUidObserver.isRegistered = false;
        OngoingCallController ongoingCallController = OngoingCallController.this;
        ongoingCallController.iActivityManager.unregisterUidObserver(ongoingCallController.uidObserver);
    }

    public final void sendStateChangeEvent() {
        OngoingCallModel inCall;
        CallNotificationInfo callNotificationInfo;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        int i2 = 0;
        if (!hasOngoingCall() || (callNotificationInfo = this.callNotificationInfo) == null) {
            inCall = OngoingCallModel.NoCall.INSTANCE;
        } else {
            LogLevel logLevel = LogLevel.DEBUG;
            OngoingCallController$$ExternalSyntheticLambda2 ongoingCallController$$ExternalSyntheticLambda2 = new OngoingCallController$$ExternalSyntheticLambda2(1);
            LogBuffer logBuffer = this.logger;
            LogMessage logMessageObtain = logBuffer.obtain("OngoingCall", logLevel, ongoingCallController$$ExternalSyntheticLambda2, null);
            ((LogMessageImpl) logMessageObtain).bool1 = callNotificationInfo.notificationIconView != null;
            logBuffer.commit(logMessageObtain);
            inCall = new OngoingCallModel.InCall(callNotificationInfo.callStartTime, callNotificationInfo.notificationIconView, callNotificationInfo.intent, callNotificationInfo.key, callNotificationInfo.appName, callNotificationInfo.promotedContent, false, callNotificationInfo.instanceId);
        }
        OngoingCallRepository ongoingCallRepository = this.ongoingCallRepository;
        ongoingCallRepository.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        OngoingCallRepository$$ExternalSyntheticLambda0 ongoingCallRepository$$ExternalSyntheticLambda0 = new OngoingCallRepository$$ExternalSyntheticLambda0();
        LogBuffer logBuffer2 = ongoingCallRepository.logger;
        LogMessage logMessageObtain2 = logBuffer2.obtain("OngoingCall", logLevel2, ongoingCallRepository$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain2).str1 = Reflection.getOrCreateKotlinClass(inCall.getClass()).getSimpleName();
        logBuffer2.commit(logMessageObtain2);
        ongoingCallRepository._ongoingCallState.updateState(null, inCall);
        ArrayList arrayList = (ArrayList) this.mListeners;
        int size = arrayList.size();
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((OngoingCallListener) obj).onOngoingCallStateChanged();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.dumpManager.registerDumpable(this);
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, anonymousClass1, 7);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 7);
    }

    public final Function0 tearDownChipView() {
        return new OngoingCallController$$ExternalSyntheticLambda0(this, 0);
    }

    public final void updateCallChipScale(float f) {
        OngoingCallChronometer ongoingCallChronometer;
        View viewFindViewById;
        View viewFindViewById2;
        View view = this.chipView;
        if (this.callNotificationInfo == null) {
            return;
        }
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMarginStart(MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(view, R.dimen.samsung_ongoing_call_chip_margin_start) * f));
            marginLayoutParams.setMarginEnd(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_margin_end) * f));
            view.setLayoutParams(marginLayoutParams);
        }
        if (view != null && (viewFindViewById2 = view.findViewById(R.id.ongoing_call_chip_background)) != null) {
            viewFindViewById2.getLayoutParams().height = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById2, R.dimen.samsung_ongoing_call_chip_height) * f);
            int iRoundToInt = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById2, R.dimen.samsung_ongoing_call_chip_side_padding) * f);
            viewFindViewById2.setPaddingRelative(iRoundToInt, 0, iRoundToInt, 0);
        }
        if (view != null && (viewFindViewById = view.findViewById(R.id.ongoing_call_chip_icon)) != null) {
            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById, R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(QSLayoutEditViewController$$ExternalSyntheticOutline0.m(viewFindViewById, R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            viewFindViewById.setLayoutParams(layoutParams);
        }
        if (view == null || (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) == null) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) ongoingCallChronometer.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_padding) * f));
        ongoingCallChronometer.setTextSize(0, ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_text_size) * f);
    }

    public final void updateChip() throws Resources.NotFoundException {
        final View viewFindViewById;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo == null) {
            return;
        }
        updateCallChipScale(this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio);
        View view = this.chipView;
        OngoingCallChronometer ongoingCallChronometer = view != null ? (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time) : null;
        if (view == null || ongoingCallChronometer == null) {
            this.callNotificationInfo = null;
            LogLevel logLevel = LogLevel.WARNING;
            OngoingCallController$$ExternalSyntheticLambda2 ongoingCallController$$ExternalSyntheticLambda2 = new OngoingCallController$$ExternalSyntheticLambda2(0);
            LogBuffer logBuffer = this.logger;
            logBuffer.commit(logBuffer.obtain("OngoingCall", logLevel, ongoingCallController$$ExternalSyntheticLambda2, null));
            return;
        }
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        callNotificationInfo2.getClass();
        Drawable drawable = this.context.getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
        int i2 = callNotificationInfo2.callChipColor;
        drawable.setColorFilter(new BlendModeColorFilter(i2, i2 != -1 ? BlendMode.SRC : BlendMode.DST));
        View viewFindViewById2 = view.findViewById(R.id.ongoing_call_chip_background);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setBackground(drawable);
        }
        final OngoingCallControllerExt ongoingCallControllerExt = this.samsungExt;
        OngoingCallChronometer ongoingCallChronometer2 = ongoingCallControllerExt.timeView;
        if (ongoingCallChronometer2 != null) {
            ongoingCallChronometer2.setImportantForAccessibility(2);
            long j = callNotificationInfo.callStartTime;
            if (j > 0) {
                if (!ongoingCallChronometer2.isRunningTimer) {
                    ongoingCallChronometer2.isRunningTimer = true;
                    ongoingCallChronometer2.requestLayout();
                }
                SystemClock systemClock = ongoingCallControllerExt.systemClock;
                ongoingCallChronometer2.setBase(systemClock.elapsedRealtime() + (j - systemClock.currentTimeMillis()));
                ongoingCallChronometer2.start();
            } else {
                if (ongoingCallChronometer2.isRunningTimer) {
                    ongoingCallChronometer2.isRunningTimer = false;
                    ongoingCallChronometer2.requestLayout();
                }
                ongoingCallChronometer2.stop();
            }
        }
        View view2 = this.chipView;
        View view3 = this.parent;
        CallNotificationInfo callNotificationInfo3 = this.callNotificationInfo;
        final PendingIntent pendingIntent = callNotificationInfo3 != null ? callNotificationInfo3.intent : null;
        if (view2 != null && pendingIntent != null && (viewFindViewById = view2.findViewById(R.id.ongoing_call_chip_background)) != null) {
            TouchInterceptFrameLayout touchInterceptFrameLayout = view2 instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) view2 : null;
            if (touchInterceptFrameLayout != null) {
                touchInterceptFrameLayout.customClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerExt$updateCallChipClickListener$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view4) {
                        OngoingCallControllerExt.access$handleClickCallChip(ongoingCallControllerExt, pendingIntent, (OngoingCallBackgroundContainer) viewFindViewById);
                    }
                };
                touchInterceptFrameLayout.touchForwardView = view3;
            } else {
                view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerExt$updateCallChipClickListener$1$2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view4) {
                        OngoingCallControllerExt.access$handleClickCallChip(ongoingCallControllerExt, pendingIntent, (OngoingCallBackgroundContainer) viewFindViewById);
                    }
                });
            }
        }
        CallAppUidObserver callAppUidObserver = this.uidObserver;
        Integer num = callAppUidObserver.callAppUid;
        int i3 = callNotificationInfo.uid;
        if (num == null || num.intValue() != i3) {
            callAppUidObserver.callAppUid = Integer.valueOf(i3);
            try {
                OngoingCallController ongoingCallController = OngoingCallController.this;
                callAppUidObserver.isCallAppVisible = ongoingCallController.iActivityManager.getUidProcessState(i3, ongoingCallController.context.getOpPackageName()) <= 2;
                LogBuffer logBuffer2 = OngoingCallController.this.logger;
                LogMessage logMessageObtain = logBuffer2.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(7), null);
                ((LogMessageImpl) logMessageObtain).bool1 = callAppUidObserver.isCallAppVisible;
                logBuffer2.commit(logMessageObtain);
                if (!callAppUidObserver.isRegistered) {
                    OngoingCallController ongoingCallController2 = OngoingCallController.this;
                    ongoingCallController2.iActivityManager.registerUidObserver(ongoingCallController2.uidObserver, 1, -1, ongoingCallController2.context.getOpPackageName());
                    callAppUidObserver.isRegistered = true;
                }
            } catch (SecurityException e) {
                LogBuffer logBuffer3 = OngoingCallController.this.logger;
                logBuffer3.commit(logBuffer3.obtain("OngoingCall", LogLevel.ERROR, new OngoingCallController$$ExternalSyntheticLambda2(8), e));
            }
        }
        updateGestureListening();
        sendStateChangeEvent();
    }

    public final void updateGestureListening() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = this.swipeStatusBarAwayGestureHandler;
        if (callNotificationInfo == null || callNotificationInfo.statusBarSwipedAway || !this.isFullscreen) {
            swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCall");
        } else {
            swipeStatusBarAwayGestureHandler.addOnGestureDetectedCallback("OngoingCall", new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int i2 = OngoingCallController.$r8$clinit;
                    OngoingCallController ongoingCallController = this.f$0;
                    ongoingCallController.getClass();
                    RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                    int i3 = StatusBarChipsModernization.$r8$clinit;
                    LogLevel logLevel = LogLevel.DEBUG;
                    OngoingCallController$$ExternalSyntheticLambda2 ongoingCallController$$ExternalSyntheticLambda2 = new OngoingCallController$$ExternalSyntheticLambda2(5);
                    LogBuffer logBuffer = ongoingCallController.logger;
                    OngoingCallController.CallNotificationInfo callNotificationInfo2 = null;
                    logBuffer.commit(logBuffer.obtain("OngoingCall", logLevel, ongoingCallController$$ExternalSyntheticLambda2, null));
                    OngoingCallController.CallNotificationInfo callNotificationInfo3 = ongoingCallController.callNotificationInfo;
                    if (callNotificationInfo3 != null) {
                        callNotificationInfo2 = new OngoingCallController.CallNotificationInfo(callNotificationInfo3.key, callNotificationInfo3.callStartTime, callNotificationInfo3.notificationIconView, callNotificationInfo3.intent, callNotificationInfo3.uid, callNotificationInfo3.appName, callNotificationInfo3.instanceId, callNotificationInfo3.promotedContent, callNotificationInfo3.isOngoing, true, callNotificationInfo3.callChipColor, callNotificationInfo3.extraVisibleFlag);
                    }
                    ongoingCallController.callNotificationInfo = callNotificationInfo2;
                    ongoingCallController.swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCall");
                    return Unit.INSTANCE;
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(OngoingCallListener ongoingCallListener) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        synchronized (this.mListeners) {
            try {
                if (!((ArrayList) this.mListeners).contains(ongoingCallListener)) {
                    ((ArrayList) this.mListeners).add(ongoingCallListener);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(OngoingCallListener ongoingCallListener) {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        synchronized (this.mListeners) {
            ((ArrayList) this.mListeners).remove(ongoingCallListener);
        }
    }

    public final class CallNotificationInfo {
        public final String appName;
        public final int callChipColor;
        public final long callStartTime;
        public final int extraVisibleFlag;
        public final InstanceId instanceId;
        public final PendingIntent intent;
        public final boolean isOngoing;
        public final String key;
        public final StatusBarIconView notificationIconView;
        public final PromotedNotificationContentModels promotedContent;
        public final boolean statusBarSwipedAway;
        public final int uid;

        public CallNotificationInfo(String str, long j, StatusBarIconView statusBarIconView, PendingIntent pendingIntent, int i, String str2, InstanceId instanceId, PromotedNotificationContentModels promotedNotificationContentModels, boolean z, boolean z2, int i2, int i3) {
            this.key = str;
            this.callStartTime = j;
            this.notificationIconView = statusBarIconView;
            this.intent = pendingIntent;
            this.uid = i;
            this.appName = str2;
            this.instanceId = instanceId;
            this.promotedContent = promotedNotificationContentModels;
            this.isOngoing = z;
            this.statusBarSwipedAway = z2;
            this.callChipColor = i2;
            this.extraVisibleFlag = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CallNotificationInfo)) {
                return false;
            }
            CallNotificationInfo callNotificationInfo = (CallNotificationInfo) obj;
            return Intrinsics.areEqual(this.key, callNotificationInfo.key) && this.callStartTime == callNotificationInfo.callStartTime && Intrinsics.areEqual(this.notificationIconView, callNotificationInfo.notificationIconView) && Intrinsics.areEqual(this.intent, callNotificationInfo.intent) && this.uid == callNotificationInfo.uid && Intrinsics.areEqual(this.appName, callNotificationInfo.appName) && Intrinsics.areEqual(this.instanceId, callNotificationInfo.instanceId) && Intrinsics.areEqual(this.promotedContent, callNotificationInfo.promotedContent) && this.isOngoing == callNotificationInfo.isOngoing && this.statusBarSwipedAway == callNotificationInfo.statusBarSwipedAway && this.callChipColor == callNotificationInfo.callChipColor && this.extraVisibleFlag == callNotificationInfo.extraVisibleFlag;
        }

        public final int hashCode() {
            int iM = MoveResult$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.callStartTime);
            StatusBarIconView statusBarIconView = this.notificationIconView;
            int iHashCode = (iM + (statusBarIconView == null ? 0 : statusBarIconView.hashCode())) * 31;
            PendingIntent pendingIntent = this.intent;
            int iM2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.uid, (iHashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31, 31), 31, this.appName);
            InstanceId instanceId = this.instanceId;
            int iHashCode2 = (iM2 + (instanceId == null ? 0 : instanceId.hashCode())) * 31;
            PromotedNotificationContentModels promotedNotificationContentModels = this.promotedContent;
            return Integer.hashCode(this.extraVisibleFlag) + ReorderTile$$ExternalSyntheticOutline0.m(this.callChipColor, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((iHashCode2 + (promotedNotificationContentModels != null ? promotedNotificationContentModels.hashCode() : 0)) * 31, 31, this.isOngoing), 31, this.statusBarSwipedAway), 31);
        }

        public final String toString() {
            PendingIntent pendingIntent = this.intent;
            InstanceId instanceId = this.instanceId;
            StringBuilder sb = new StringBuilder("CallNotificationInfo(key=");
            sb.append(this.key);
            sb.append(", callStartTime=");
            sb.append(this.callStartTime);
            sb.append(", notificationIconView=");
            sb.append(this.notificationIconView);
            sb.append(", intent=");
            sb.append(pendingIntent);
            sb.append(", uid=");
            sb.append(this.uid);
            sb.append(", appName=");
            sb.append(this.appName);
            sb.append(", instanceId=");
            sb.append(instanceId);
            sb.append(", promotedContent=");
            sb.append(this.promotedContent);
            sb.append(", isOngoing=");
            sb.append(this.isOngoing);
            sb.append(", statusBarSwipedAway=");
            sb.append(this.statusBarSwipedAway);
            sb.append(", callChipColor=");
            sb.append(this.callChipColor);
            sb.append(", extraVisibleFlag=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.extraVisibleFlag, ")", sb);
        }

        public /* synthetic */ CallNotificationInfo(String str, long j, StatusBarIconView statusBarIconView, PendingIntent pendingIntent, int i, String str2, InstanceId instanceId, PromotedNotificationContentModels promotedNotificationContentModels, boolean z, boolean z2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, j, statusBarIconView, pendingIntent, i, str2, instanceId, promotedNotificationContentModels, z, z2, (i4 & 1024) != 0 ? -1 : i2, i3);
        }
    }
}
