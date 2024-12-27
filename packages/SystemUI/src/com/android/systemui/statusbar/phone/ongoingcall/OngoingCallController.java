package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.IActivityManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.TouchInterceptFrameLayout;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class OngoingCallController implements CallbackController, Dumpable, CoreStartable {
    public final ActivityStarter activityStarter;
    public CallNotificationInfo callNotificationInfo;
    public View chipView;
    public final ConfigurationController configurationController;
    public final Context context;
    public final DumpManager dumpManager;
    public final IActivityManager iActivityManager;
    public final IndicatorScaleGardener indicatorScaleGardener;
    public boolean isFullscreen;
    public final KeyguardCallChipController keyguardCallChipController;
    public final OngoingCallLogger logger;
    public final Executor mainExecutor;
    public final CommonNotifCollection notifCollection;
    public final OngoingCallRepository ongoingCallRepository;
    public View parent;
    public final CoroutineScope scope;
    public final StatusBarModeRepositoryStore statusBarModeRepository;
    public final StatusBarWindowController statusBarWindowController;
    public final SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler;
    public final SystemClock systemClock;
    public final List mListeners = new ArrayList();
    public final CallAppUidObserver uidObserver = new CallAppUidObserver();
    public final OngoingCallController$notifListener$1 notifListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$notifListener$1
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryAdded(NotificationEntry notificationEntry) {
            onEntryUpdated(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
            String key = notificationEntry.mSbn.getKey();
            OngoingCallController ongoingCallController = OngoingCallController.this;
            OngoingCallController.CallNotificationInfo callNotificationInfo = ongoingCallController.callNotificationInfo;
            if (Intrinsics.areEqual(key, callNotificationInfo != null ? callNotificationInfo.key : null)) {
                OngoingCallController.access$removeChip(ongoingCallController);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0026, code lost:
        
            if (r0 != false) goto L17;
         */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry r14) {
            /*
                r13 = this;
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController r13 = com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController.this
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r0 = r13.callNotificationInfo
                r1 = 0
                if (r0 != 0) goto L28
                boolean r0 = com.android.systemui.statusbar.phone.ongoingcall.OngoingCallControllerKt.DEBUG
                boolean r0 = com.android.systemui.NotiRune.NOTI_ONGOING_GEMINI_DEMO
                if (r0 == 0) goto L1a
                com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper r0 = com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.INSTANCE
                r0.getClass()
                boolean r0 = com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper.isExceptionalOngoingActivity(r14)
                if (r0 == 0) goto L1a
                r0 = r1
                goto L26
            L1a:
                android.service.notification.StatusBarNotification r0 = r14.mSbn
                android.app.Notification r0 = r0.getNotification()
                java.lang.Class<android.app.Notification$CallStyle> r2 = android.app.Notification.CallStyle.class
                boolean r0 = r0.isStyle(r2)
            L26:
                if (r0 != 0) goto L3c
            L28:
                android.service.notification.StatusBarNotification r0 = r14.mSbn
                java.lang.String r0 = r0.getKey()
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r2 = r13.callNotificationInfo
                if (r2 == 0) goto L35
                java.lang.String r2 = r2.key
                goto L36
            L35:
                r2 = 0
            L36:
                boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
                if (r0 == 0) goto Lc5
            L3c:
                android.service.notification.StatusBarNotification r0 = r14.mSbn
                android.app.Notification r0 = r0.getNotification()
                android.os.Bundle r0 = r0.extras
                java.lang.String r2 = "android.callChipVisible"
                int r12 = r0.getInt(r2, r1)
                if (r12 == 0) goto L53
                java.lang.String r0 = "Set extra call chip visible="
                java.lang.String r2 = "OngoingCallController"
                androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m(r12, r0, r2)
            L53:
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r0 = new com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo
                android.service.notification.StatusBarNotification r2 = r14.mSbn
                java.lang.String r4 = r2.getKey()
                android.service.notification.StatusBarNotification r2 = r14.mSbn
                android.app.Notification r2 = r2.getNotification()
                long r5 = r2.getWhen()
                android.service.notification.StatusBarNotification r2 = r14.mSbn
                android.app.Notification r2 = r2.getNotification()
                android.app.PendingIntent r7 = r2.contentIntent
                android.service.notification.StatusBarNotification r2 = r14.mSbn
                int r8 = r2.getUid()
                android.service.notification.StatusBarNotification r2 = r14.mSbn
                android.app.Notification r2 = r2.getNotification()
                android.os.Bundle r2 = r2.extras
                java.lang.String r3 = "android.callType"
                r9 = -1
                int r2 = r2.getInt(r3, r9)
                r3 = 2
                if (r2 != r3) goto L87
                r2 = 1
                goto L88
            L87:
                r2 = r1
            L88:
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r3 = r13.callNotificationInfo
                if (r3 == 0) goto L8e
                boolean r1 = r3.statusBarSwipedAway
            L8e:
                r10 = r1
                android.service.notification.StatusBarNotification r14 = r14.mSbn
                android.app.Notification r14 = r14.getNotification()
                android.os.Bundle r14 = r14.extras
                java.lang.String r1 = "android.callChipBg"
                int r11 = r14.getInt(r1, r9)
                r3 = r0
                r9 = r2
                r3.<init>(r4, r5, r7, r8, r9, r10, r11, r12)
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r14 = r13.callNotificationInfo
                boolean r14 = r0.equals(r14)
                if (r14 == 0) goto Lab
                return
            Lab:
                r13.callNotificationInfo = r0
                boolean r14 = r0.isOngoing
                if (r14 == 0) goto Lb5
                r13.updateChip()
                goto Lb8
            Lb5:
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController.access$removeChip(r13)
            Lb8:
                com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallNotificationInfo r13 = r13.callNotificationInfo
                if (r13 != 0) goto Lc5
                java.lang.String r13 = com.android.systemui.util.SystemUIAnalytics.getCurrentScreenID()
                java.lang.String r14 = "QPNE0105"
                com.android.systemui.util.SystemUIAnalytics.sendEventLog(r13, r14)
            Lc5:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$notifListener$1.onEntryUpdated(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
        }
    };
    public final OngoingCallController$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            OngoingCallController ongoingCallController = OngoingCallController.this;
            if (ongoingCallController.hasOngoingCall()) {
                float f = ongoingCallController.indicatorScaleGardener.getLatestScaleModel(ongoingCallController.context).ratio;
                ongoingCallController.scaleCallChip(ongoingCallController.chipView, f, false);
                ongoingCallController.scaleCallChip(ongoingCallController.keyguardCallChipController.chipView, f, true);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            onDensityOrFontScaleChanged();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CallAppUidObserver extends UidObserver {
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
            OngoingCallController.this.getClass();
            boolean z2 = i2 <= 2;
            this.isCallAppVisible = z2;
            if (z != z2) {
                final OngoingCallController ongoingCallController = OngoingCallController.this;
                ongoingCallController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$CallAppUidObserver$onUidStateChanged$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OngoingCallController.this.sendStateChangeEvent();
                    }
                });
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$notifListener$1] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1] */
    public OngoingCallController(CoroutineScope coroutineScope, Context context, OngoingCallRepository ongoingCallRepository, CommonNotifCollection commonNotifCollection, SystemClock systemClock, ActivityStarter activityStarter, Executor executor, IActivityManager iActivityManager, OngoingCallLogger ongoingCallLogger, DumpManager dumpManager, StatusBarWindowController statusBarWindowController, SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler, StatusBarModeRepositoryStore statusBarModeRepositoryStore, KeyguardCallChipController keyguardCallChipController, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        this.scope = coroutineScope;
        this.context = context;
        this.ongoingCallRepository = ongoingCallRepository;
        this.notifCollection = commonNotifCollection;
        this.systemClock = systemClock;
        this.activityStarter = activityStarter;
        this.mainExecutor = executor;
        this.iActivityManager = iActivityManager;
        this.logger = ongoingCallLogger;
        this.dumpManager = dumpManager;
        this.statusBarWindowController = statusBarWindowController;
        this.swipeStatusBarAwayGestureHandler = swipeStatusBarAwayGestureHandler;
        this.statusBarModeRepository = statusBarModeRepositoryStore;
        this.keyguardCallChipController = keyguardCallChipController;
        this.configurationController = configurationController;
        this.indicatorScaleGardener = indicatorScaleGardener;
    }

    public static final void access$handleClick(OngoingCallController ongoingCallController, PendingIntent pendingIntent, View view) {
        ongoingCallController.logger.logger.log(OngoingCallLogger.OngoingCallEvents.ONGOING_CALL_CLICKED);
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_CALL_CHIP_OPEN);
        ongoingCallController.activityStarter.postStartActivityDismissingKeyguard(pendingIntent, ActivityTransitionAnimator.Controller.Companion.fromView$default(ActivityTransitionAnimator.Controller.Companion, view, 34, 28));
    }

    public static final void access$removeChip(OngoingCallController ongoingCallController) {
        ongoingCallController.callNotificationInfo = null;
        Flags.statusBarScreenSharingChips();
        ongoingCallController.tearDownChipView();
        ongoingCallController.swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCallController");
        ongoingCallController.sendStateChangeEvent();
        CallAppUidObserver callAppUidObserver = ongoingCallController.uidObserver;
        callAppUidObserver.callAppUid = null;
        callAppUidObserver.isRegistered = false;
        OngoingCallController ongoingCallController2 = OngoingCallController.this;
        ongoingCallController2.iActivityManager.unregisterUidObserver(ongoingCallController2.uidObserver);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Active call notification: " + this.callNotificationInfo);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("Call app visible: ", this.uidObserver.isCallAppVisible, printWriter);
    }

    public final boolean hasOngoingCall() {
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo != null && callNotificationInfo.isOngoing) {
            if (!this.uidObserver.isCallAppVisible) {
                return true;
            }
            if ((callNotificationInfo == null || callNotificationInfo.extraVisibleFlag != 0) && callNotificationInfo != null && callNotificationInfo.extraVisibleFlag == 1) {
                return true;
            }
        }
        return false;
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

    public final void scaleCallChip(View view, float f, boolean z) {
        OngoingCallChronometer ongoingCallChronometer;
        View findViewById;
        View findViewById2;
        if (this.callNotificationInfo == null) {
            return;
        }
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMarginStart(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(z ? R.dimen.samsung_ongoing_call_chip_keyguard_margin_start : R.dimen.samsung_ongoing_call_chip_margin_start) * f));
            marginLayoutParams.setMarginEnd(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_margin_end) * f));
            view.setLayoutParams(marginLayoutParams);
        }
        if (view != null && (findViewById2 = view.findViewById(R.id.ongoing_call_chip_background)) != null) {
            findViewById2.getLayoutParams().height = MathKt__MathJVMKt.roundToInt(findViewById2.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_height) * f);
            int roundToInt = MathKt__MathJVMKt.roundToInt(findViewById2.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_side_padding) * f);
            findViewById2.setPaddingRelative(roundToInt, 0, roundToInt, 0);
        }
        if (view != null && (findViewById = view.findViewById(R.id.ongoing_call_chip_icon)) != null) {
            ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(findViewById.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(findViewById.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            findViewById.setLayoutParams(layoutParams);
        }
        if (view == null || (ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) == null) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) ongoingCallChronometer.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_padding) * f));
        ongoingCallChronometer.setTextSize(0, ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_text_size) * f);
    }

    public final void sendStateChangeEvent() {
        OngoingCallModel ongoingCallModel;
        if (hasOngoingCall()) {
            CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
            if (callNotificationInfo == null) {
                ongoingCallModel = OngoingCallModel.NoCall.INSTANCE;
            } else {
                ongoingCallModel = new OngoingCallModel.InCall(callNotificationInfo.callStartTime, callNotificationInfo.intent);
            }
        } else {
            ongoingCallModel = OngoingCallModel.NoCall.INSTANCE;
        }
        this.ongoingCallRepository._ongoingCallState.updateState(null, ongoingCallModel);
        Iterator it = ((ArrayList) this.mListeners).iterator();
        while (it.hasNext()) {
            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
        }
    }

    public final void setChipView(View view) {
        CallNotificationInfo callNotificationInfo;
        tearDownChipView();
        this.chipView = view;
        OngoingCallBackgroundContainer ongoingCallBackgroundContainer = (OngoingCallBackgroundContainer) view.findViewById(R.id.ongoing_call_chip_background);
        if (ongoingCallBackgroundContainer != null) {
            ongoingCallBackgroundContainer.maxHeightFetcher = new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$setChipView$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Integer.valueOf(OngoingCallController.this.statusBarWindowController.mBarHeight);
                }
            };
        }
        if (hasOngoingCall() || ((callNotificationInfo = this.callNotificationInfo) != null && callNotificationInfo.isOngoing)) {
            updateChip();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.dumpManager.registerDumpable(this);
        ((NotifPipeline) this.notifCollection).addCollectionListener(this.notifListener);
        ((ConfigurationControllerImpl) this.configurationController).addCallback(this.configurationListener);
        BuildersKt.launch$default(this.scope, null, null, new OngoingCallController$start$1(this, null), 3);
    }

    public final Function0 tearDownChipView() {
        return new Function0() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$tearDownChipView$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                OngoingCallChronometer ongoingCallChronometer;
                OngoingCallChronometer ongoingCallChronometer2;
                View view = OngoingCallController.this.chipView;
                if (view != null && (ongoingCallChronometer2 = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) != null) {
                    ongoingCallChronometer2.stop();
                }
                View view2 = OngoingCallController.this.keyguardCallChipController.chipView;
                if (view2 != null && (ongoingCallChronometer = (OngoingCallChronometer) view2.findViewById(R.id.ongoing_call_chip_time)) != null) {
                    ongoingCallChronometer.stop();
                }
                return Unit.INSTANCE;
            }
        };
    }

    public final void updateChip() {
        OngoingCallChronometer ongoingCallChronometer;
        OngoingCallChronometer ongoingCallChronometer2;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo == null) {
            return;
        }
        float f = this.indicatorScaleGardener.getLatestScaleModel(this.context).ratio;
        scaleCallChip(this.chipView, f, false);
        KeyguardCallChipController keyguardCallChipController = this.keyguardCallChipController;
        scaleCallChip(keyguardCallChipController.chipView, f, true);
        View view = this.chipView;
        OngoingCallChronometer ongoingCallChronometer3 = view != null ? (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time) : null;
        if (view == null || ongoingCallChronometer3 == null) {
            this.callNotificationInfo = null;
            if (OngoingCallControllerKt.DEBUG) {
                Log.w("OngoingCallController", "Ongoing call chip view could not be found; Not displaying chip in status bar");
                return;
            }
            return;
        }
        CallNotificationInfo callNotificationInfo2 = this.callNotificationInfo;
        Intrinsics.checkNotNull(callNotificationInfo2);
        Drawable drawable = this.context.getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
        int i = callNotificationInfo2.callChipColor;
        drawable.setColorFilter(new BlendModeColorFilter(i, i != -1 ? BlendMode.SRC : BlendMode.DST));
        View findViewById = view.findViewById(R.id.ongoing_call_chip_background);
        if (findViewById != null) {
            findViewById.setBackground(drawable);
        }
        View view2 = keyguardCallChipController.chipView;
        if (view2 != null) {
            Drawable drawable2 = view2.getContext().getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
            drawable2.setColorFilter(new BlendModeColorFilter(i, i != -1 ? BlendMode.SRC : BlendMode.DST));
            View findViewById2 = view2.findViewById(R.id.ongoing_call_chip_background);
            if (findViewById2 != null) {
                findViewById2.setBackground(drawable2);
            }
        }
        Flags.statusBarScreenSharingChips();
        long j = callNotificationInfo.callStartTime;
        if (j > 0) {
            if (!ongoingCallChronometer3.isRunningTimer) {
                ongoingCallChronometer3.isRunningTimer = true;
                ongoingCallChronometer3.requestLayout();
            }
            boolean z = ongoingCallChronometer3.getContext().getResources().getConfiguration().orientation == 2;
            if (ongoingCallChronometer3.isLandscape != z) {
                ongoingCallChronometer3.isLandscape = z;
                ongoingCallChronometer3.requestLayout();
            }
            SystemClock systemClock = this.systemClock;
            ongoingCallChronometer3.setBase(systemClock.elapsedRealtime() + (j - systemClock.currentTimeMillis()));
            ongoingCallChronometer3.start();
            long base = ongoingCallChronometer3.getBase();
            View view3 = keyguardCallChipController.chipView;
            if (view3 != null && (ongoingCallChronometer2 = (OngoingCallChronometer) view3.findViewById(R.id.ongoing_call_chip_time)) != null) {
                if (!ongoingCallChronometer2.isRunningTimer) {
                    ongoingCallChronometer2.isRunningTimer = true;
                    ongoingCallChronometer2.requestLayout();
                }
                ongoingCallChronometer2.setBase(base);
                ongoingCallChronometer2.start();
            }
        } else {
            if (ongoingCallChronometer3.isRunningTimer) {
                ongoingCallChronometer3.isRunningTimer = false;
                ongoingCallChronometer3.requestLayout();
            }
            ongoingCallChronometer3.stop();
            View view4 = keyguardCallChipController.chipView;
            if (view4 != null && (ongoingCallChronometer = (OngoingCallChronometer) view4.findViewById(R.id.ongoing_call_chip_time)) != null) {
                if (ongoingCallChronometer.isRunningTimer) {
                    ongoingCallChronometer.isRunningTimer = false;
                    ongoingCallChronometer.requestLayout();
                }
                ongoingCallChronometer.stop();
            }
        }
        updateChipClickListener();
        CallAppUidObserver callAppUidObserver = this.uidObserver;
        Integer num = callAppUidObserver.callAppUid;
        int i2 = callNotificationInfo.uid;
        if (num == null || num.intValue() != i2) {
            callAppUidObserver.callAppUid = Integer.valueOf(i2);
            try {
                OngoingCallController ongoingCallController = OngoingCallController.this;
                callAppUidObserver.isCallAppVisible = ongoingCallController.iActivityManager.getUidProcessState(i2, ongoingCallController.context.getOpPackageName()) <= 2;
                if (!callAppUidObserver.isRegistered) {
                    OngoingCallController ongoingCallController2 = OngoingCallController.this;
                    ongoingCallController2.iActivityManager.registerUidObserver(ongoingCallController2.uidObserver, 1, -1, ongoingCallController2.context.getOpPackageName());
                    callAppUidObserver.isRegistered = true;
                }
            } catch (SecurityException e) {
                Log.e("OngoingCallController", "Security exception when trying to set up uid observer: " + e);
            }
        }
        updateGestureListening();
        sendStateChangeEvent();
    }

    public final void updateChipClickListener() {
        Flags.statusBarScreenSharingChips();
        if (this.callNotificationInfo == null) {
            return;
        }
        View view = this.chipView;
        final View findViewById = view != null ? view.findViewById(R.id.ongoing_call_chip_background) : null;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        final PendingIntent pendingIntent = callNotificationInfo != null ? callNotificationInfo.intent : null;
        if (view == null || findViewById == null || pendingIntent == null) {
            return;
        }
        TouchInterceptFrameLayout touchInterceptFrameLayout = view instanceof TouchInterceptFrameLayout ? (TouchInterceptFrameLayout) view : null;
        if (touchInterceptFrameLayout == null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateChipClickListener$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OngoingCallController.access$handleClick(OngoingCallController.this, pendingIntent, findViewById);
                }
            });
        } else {
            touchInterceptFrameLayout.customClickListener = new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateChipClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    OngoingCallController.access$handleClick(OngoingCallController.this, pendingIntent, findViewById);
                }
            };
            touchInterceptFrameLayout.touchForwardView = this.parent;
        }
    }

    public final void updateGestureListening() {
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = this.swipeStatusBarAwayGestureHandler;
        if (callNotificationInfo == null || ((callNotificationInfo != null && callNotificationInfo.statusBarSwipedAway) || !this.isFullscreen)) {
            swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCallController");
        } else {
            swipeStatusBarAwayGestureHandler.addOnGestureDetectedCallback(new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateGestureListening$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    OngoingCallController.CallNotificationInfo callNotificationInfo2;
                    OngoingCallController ongoingCallController = OngoingCallController.this;
                    ongoingCallController.getClass();
                    if (OngoingCallControllerKt.DEBUG) {
                        Log.d("OngoingCallController", "Swipe away gesture detected");
                    }
                    OngoingCallController.CallNotificationInfo callNotificationInfo3 = ongoingCallController.callNotificationInfo;
                    if (callNotificationInfo3 != null) {
                        callNotificationInfo2 = new OngoingCallController.CallNotificationInfo(callNotificationInfo3.key, callNotificationInfo3.callStartTime, callNotificationInfo3.intent, callNotificationInfo3.uid, callNotificationInfo3.isOngoing, true, callNotificationInfo3.callChipColor, callNotificationInfo3.extraVisibleFlag);
                    } else {
                        callNotificationInfo2 = null;
                    }
                    ongoingCallController.callNotificationInfo = callNotificationInfo2;
                    ongoingCallController.swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback("OngoingCallController");
                    return Unit.INSTANCE;
                }
            }, "OngoingCallController");
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(OngoingCallListener ongoingCallListener) {
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
        synchronized (this.mListeners) {
            ((ArrayList) this.mListeners).remove(ongoingCallListener);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CallNotificationInfo {
        public final int callChipColor;
        public final long callStartTime;
        public final int extraVisibleFlag;
        public final PendingIntent intent;
        public final boolean isOngoing;
        public final String key;
        public final boolean statusBarSwipedAway;
        public final int uid;

        public CallNotificationInfo(String str, long j, PendingIntent pendingIntent, int i, boolean z, boolean z2, int i2, int i3) {
            this.key = str;
            this.callStartTime = j;
            this.intent = pendingIntent;
            this.uid = i;
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
            return Intrinsics.areEqual(this.key, callNotificationInfo.key) && this.callStartTime == callNotificationInfo.callStartTime && Intrinsics.areEqual(this.intent, callNotificationInfo.intent) && this.uid == callNotificationInfo.uid && this.isOngoing == callNotificationInfo.isOngoing && this.statusBarSwipedAway == callNotificationInfo.statusBarSwipedAway && this.callChipColor == callNotificationInfo.callChipColor && this.extraVisibleFlag == callNotificationInfo.extraVisibleFlag;
        }

        public final int hashCode() {
            int m = Scale$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.callStartTime);
            PendingIntent pendingIntent = this.intent;
            return Integer.hashCode(this.extraVisibleFlag) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.callChipColor, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.uid, (m + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31, 31), 31, this.isOngoing), 31, this.statusBarSwipedAway), 31);
        }

        public final String toString() {
            return "CallNotificationInfo(key=" + this.key + ", callStartTime=" + this.callStartTime + ", intent=" + this.intent + ", uid=" + this.uid + ", isOngoing=" + this.isOngoing + ", statusBarSwipedAway=" + this.statusBarSwipedAway + ", callChipColor=" + this.callChipColor + ", extraVisibleFlag=" + this.extraVisibleFlag + ")";
        }

        public /* synthetic */ CallNotificationInfo(String str, long j, PendingIntent pendingIntent, int i, boolean z, boolean z2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, j, pendingIntent, i, z, z2, (i4 & 64) != 0 ? -1 : i2, i3);
        }
    }
}
