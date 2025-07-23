package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IActivityManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.content.Context;
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
import com.android.systemui.qs.SecQSDetailController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
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
import com.android.systemui.util.time.SystemClock;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            OngoingCallController ongoingCallController = OngoingCallController.this;
            if (ongoingCallController.hasOngoingCall()) {
                ongoingCallController.updateCallChipScale(ongoingCallController.indicatorScaleGardener.getLatestScaleModel(ongoingCallController.context).ratio);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            onDensityOrFontScaleChanged();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                LogMessage obtain = logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(6), null);
                ((LogMessageImpl) obtain).bool1 = this.isCallAppVisible;
                logBuffer.commit(obtain);
                final OngoingCallController ongoingCallController2 = OngoingCallController.this;
                ongoingCallController2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallAppUidObserver$onUidStateChanged$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        OngoingCallController ongoingCallController3 = OngoingCallController.this;
                        int i6 = OngoingCallController.$r8$clinit;
                        ongoingCallController3.sendStateChangeEvent();
                    }
                });
            }
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
        OngoingCallChronometer ongoingCallChronometer;
        boolean z2;
        View view = this.chipView;
        if (view == null || (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) == null || ongoingCallChronometer.isEnoughTimerWidth == (!z)) {
            return;
        }
        ongoingCallChronometer.isEnoughTimerWidth = z2;
        ongoingCallChronometer.requestLayout();
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
        OngoingCallModel ongoingCallModel;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarChipsModernization.$r8$clinit;
        int i2 = 0;
        if (hasOngoingCall()) {
            CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
            if (callNotificationInfo == null) {
                ongoingCallModel = OngoingCallModel.NoCall.INSTANCE;
            } else {
                LogLevel logLevel = LogLevel.DEBUG;
                OngoingCallController$$ExternalSyntheticLambda2 ongoingCallController$$ExternalSyntheticLambda2 = new OngoingCallController$$ExternalSyntheticLambda2(1);
                LogBuffer logBuffer = this.logger;
                LogMessage obtain = logBuffer.obtain("OngoingCall", logLevel, ongoingCallController$$ExternalSyntheticLambda2, null);
                ((LogMessageImpl) obtain).bool1 = callNotificationInfo.notificationIconView != null;
                logBuffer.commit(obtain);
                ongoingCallModel = new OngoingCallModel.InCall(callNotificationInfo.callStartTime, callNotificationInfo.notificationIconView, callNotificationInfo.intent, callNotificationInfo.key, callNotificationInfo.appName, callNotificationInfo.promotedContent, false, callNotificationInfo.instanceId);
            }
        } else {
            ongoingCallModel = OngoingCallModel.NoCall.INSTANCE;
        }
        OngoingCallRepository ongoingCallRepository = this.ongoingCallRepository;
        ongoingCallRepository.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        OngoingCallRepository$$ExternalSyntheticLambda0 ongoingCallRepository$$ExternalSyntheticLambda0 = new OngoingCallRepository$$ExternalSyntheticLambda0();
        LogBuffer logBuffer2 = ongoingCallRepository.logger;
        LogMessage obtain2 = logBuffer2.obtain("OngoingCall", logLevel2, ongoingCallRepository$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain2).str1 = Reflection.getOrCreateKotlinClass(ongoingCallModel.getClass()).getSimpleName();
        logBuffer2.commit(obtain2);
        ongoingCallRepository._ongoingCallState.updateState(null, ongoingCallModel);
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
        OngoingCallController$start$1 ongoingCallController$start$1 = new OngoingCallController$start$1(this, null);
        CoroutineScope coroutineScope = this.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, ongoingCallController$start$1, 7);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new OngoingCallController$start$2(this, null), 7);
    }

    public final Function0 tearDownChipView() {
        return new OngoingCallController$$ExternalSyntheticLambda0(this, 0);
    }

    public final void updateCallChipScale(float f) {
        OngoingCallChronometer ongoingCallChronometer;
        View findViewById;
        View findViewById2;
        View view = this.chipView;
        if (this.callNotificationInfo == null) {
            return;
        }
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMarginStart(MathKt__MathJVMKt.roundToInt(SecQSDetailController$$ExternalSyntheticOutline0.m(view, R.dimen.samsung_ongoing_call_chip_margin_start) * f));
            marginLayoutParams.setMarginEnd(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_margin_end) * f));
            view.setLayoutParams(marginLayoutParams);
        }
        if (view != null && (findViewById2 = view.findViewById(R.id.ongoing_call_chip_background)) != null) {
            findViewById2.getLayoutParams().height = MathKt__MathJVMKt.roundToInt(SecQSDetailController$$ExternalSyntheticOutline0.m(findViewById2, R.dimen.samsung_ongoing_call_chip_height) * f);
            int roundToInt = MathKt__MathJVMKt.roundToInt(SecQSDetailController$$ExternalSyntheticOutline0.m(findViewById2, R.dimen.samsung_ongoing_call_chip_side_padding) * f);
            findViewById2.setPaddingRelative(roundToInt, 0, roundToInt, 0);
        }
        if (view != null && (findViewById = view.findViewById(R.id.ongoing_call_chip_icon)) != null) {
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(SecQSDetailController$$ExternalSyntheticOutline0.m(findViewById, R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(SecQSDetailController$$ExternalSyntheticOutline0.m(findViewById, R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            findViewById.setLayoutParams(layoutParams);
        }
        if (view == null || (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) == null) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) ongoingCallChronometer.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_padding) * f));
        ongoingCallChronometer.setTextSize(0, ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_text_size) * f);
    }

    public final void updateChip() {
        final View findViewById;
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
        ongoingCallChronometer.setImportantForAccessibility(2);
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        callNotificationInfo2.getClass();
        Drawable drawable = this.context.getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
        int i2 = callNotificationInfo2.callChipColor;
        drawable.setColorFilter(new BlendModeColorFilter(i2, i2 != -1 ? BlendMode.SRC : BlendMode.DST));
        View findViewById2 = view.findViewById(R.id.ongoing_call_chip_background);
        if (findViewById2 != null) {
            findViewById2.setBackground(drawable);
        }
        long j = callNotificationInfo.callStartTime;
        if (j > 0) {
            if (!ongoingCallChronometer.isRunningTimer) {
                ongoingCallChronometer.isRunningTimer = true;
                ongoingCallChronometer.requestLayout();
            }
            boolean z = ongoingCallChronometer.getContext().getResources().getConfiguration().orientation == 2;
            if (ongoingCallChronometer.isLandscape != z) {
                ongoingCallChronometer.isLandscape = z;
                ongoingCallChronometer.requestLayout();
            }
            boolean z2 = ongoingCallChronometer.getContext().getResources().getConfiguration().semDisplayDeviceType == 0;
            if (ongoingCallChronometer.isMainDisplay != z2) {
                ongoingCallChronometer.isMainDisplay = z2;
                ongoingCallChronometer.requestLayout();
            }
            SystemClock systemClock = this.systemClock;
            ongoingCallChronometer.setBase(systemClock.elapsedRealtime() + (j - systemClock.currentTimeMillis()));
            ongoingCallChronometer.start();
        } else {
            if (ongoingCallChronometer.isRunningTimer) {
                ongoingCallChronometer.isRunningTimer = false;
                ongoingCallChronometer.requestLayout();
            }
            ongoingCallChronometer.stop();
        }
        View view2 = this.chipView;
        View view3 = this.parent;
        CallNotificationInfo callNotificationInfo3 = this.callNotificationInfo;
        final PendingIntent pendingIntent = callNotificationInfo3 != null ? callNotificationInfo3.intent : null;
        final OngoingCallControllerExt ongoingCallControllerExt = this.samsungExt;
        ongoingCallControllerExt.getClass();
        if (view2 != null && pendingIntent != null && (findViewById = view2.findViewById(R.id.ongoing_call_chip_background)) != null) {
            TouchInterceptFrameLayout touchInterceptFrameLayout = view2 instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) view2 : null;
            if (touchInterceptFrameLayout != null) {
                touchInterceptFrameLayout.customClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerExt$updateCallChipClickListener$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view4) {
                        OngoingCallControllerExt.access$handleClickCallChip(OngoingCallControllerExt.this, pendingIntent, (OngoingCallBackgroundContainer) findViewById);
                    }
                };
                touchInterceptFrameLayout.touchForwardView = view3;
            } else {
                view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerExt$updateCallChipClickListener$1$2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view4) {
                        OngoingCallControllerExt.access$handleClickCallChip(OngoingCallControllerExt.this, pendingIntent, (OngoingCallBackgroundContainer) findViewById);
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
                LogMessage obtain = logBuffer2.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(7), null);
                ((LogMessageImpl) obtain).bool1 = callAppUidObserver.isCallAppVisible;
                logBuffer2.commit(obtain);
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
                public final Object mo779invoke(Object obj) {
                    int i2 = OngoingCallController.$r8$clinit;
                    OngoingCallController ongoingCallController = OngoingCallController.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int m = MoveResult$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.callStartTime);
            StatusBarIconView statusBarIconView = this.notificationIconView;
            int hashCode = (m + (statusBarIconView == null ? 0 : statusBarIconView.hashCode())) * 31;
            PendingIntent pendingIntent = this.intent;
            int m2 = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.uid, (hashCode + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31, 31), 31, this.appName);
            InstanceId instanceId = this.instanceId;
            int hashCode2 = (m2 + (instanceId == null ? 0 : instanceId.hashCode())) * 31;
            PromotedNotificationContentModels promotedNotificationContentModels = this.promotedContent;
            return Integer.hashCode(this.extraVisibleFlag) + ReorderTile$$ExternalSyntheticOutline0.m(this.callChipColor, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (promotedNotificationContentModels != null ? promotedNotificationContentModels.hashCode() : 0)) * 31, 31, this.isOngoing), 31, this.statusBarSwipedAway), 31);
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
