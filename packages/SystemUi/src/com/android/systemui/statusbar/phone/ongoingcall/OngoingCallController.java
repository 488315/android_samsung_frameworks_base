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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OngoingCallController implements CallbackController, Dumpable {
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
    public final OngoingCallFlags ongoingCallFlags;
    public final StatusBarStateController statusBarStateController;
    public final Optional statusBarWindowController;
    public final Optional swipeStatusBarAwayGestureHandler;
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

        /* JADX WARN: Code restructure failed: missing block: B:4:0x0014, code lost:
        
            if (r14.mSbn.getNotification().isStyle(android.app.Notification.CallStyle.class) == false) goto L6;
         */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onEntryUpdated(NotificationEntry notificationEntry) {
            OngoingCallController ongoingCallController = OngoingCallController.this;
            if (ongoingCallController.callNotificationInfo == null) {
                boolean z = OngoingCallControllerKt.DEBUG;
            }
            String key = notificationEntry.mSbn.getKey();
            OngoingCallController.CallNotificationInfo callNotificationInfo = ongoingCallController.callNotificationInfo;
            if (!Intrinsics.areEqual(key, callNotificationInfo != null ? callNotificationInfo.key : null)) {
                return;
            }
            int i = notificationEntry.mSbn.getNotification().extras.getInt("android.callChipVisible", 0);
            if (i != 0) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Set extra call chip visible=", i, "OngoingCallController");
            }
            String key2 = notificationEntry.mSbn.getKey();
            long j = notificationEntry.mSbn.getNotification().when;
            PendingIntent pendingIntent = notificationEntry.mSbn.getNotification().contentIntent;
            int uid = notificationEntry.mSbn.getUid();
            boolean z2 = notificationEntry.mSbn.getNotification().extras.getInt("android.callType", -1) == 2;
            OngoingCallController.CallNotificationInfo callNotificationInfo2 = ongoingCallController.callNotificationInfo;
            OngoingCallController.CallNotificationInfo callNotificationInfo3 = new OngoingCallController.CallNotificationInfo(key2, j, pendingIntent, uid, z2, callNotificationInfo2 != null ? callNotificationInfo2.statusBarSwipedAway : false, notificationEntry.mSbn.getNotification().extras.getInt("android.callChipBg", -1), i);
            if (ongoingCallController.callNotificationInfo == null) {
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0105");
            }
            if (Intrinsics.areEqual(callNotificationInfo3, ongoingCallController.callNotificationInfo)) {
                return;
            }
            ongoingCallController.callNotificationInfo = callNotificationInfo3;
            if (callNotificationInfo3.isOngoing) {
                ongoingCallController.updateChip();
            } else {
                OngoingCallController.access$removeChip(ongoingCallController);
            }
        }
    };
    public final OngoingCallController$configurationListener$1 configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDensityOrFontScaleChanged() {
            OngoingCallController ongoingCallController = OngoingCallController.this;
            OngoingCallController.CallNotificationInfo callNotificationInfo = ongoingCallController.callNotificationInfo;
            if (callNotificationInfo != null && callNotificationInfo.isOngoing) {
                float f = ongoingCallController.indicatorScaleGardener.getLatestScaleModel(ongoingCallController.context).ratio;
                OngoingCallController.scaleCallChip(ongoingCallController.chipView, f, false);
                OngoingCallController.scaleCallChip(ongoingCallController.keyguardCallChipController.chipView, f, true);
            }
        }

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onDisplayDeviceTypeChanged() {
            onDensityOrFontScaleChanged();
        }
    };
    public final OngoingCallController$statusBarStateListener$1 statusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$statusBarStateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onFullscreenStateChanged(boolean z) {
            OngoingCallController ongoingCallController = OngoingCallController.this;
            ongoingCallController.isFullscreen = z;
            ongoingCallController.updateChipClickListener();
            ongoingCallController.updateGestureListening();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                        Iterator it = ((ArrayList) OngoingCallController.this.mListeners).iterator();
                        while (it.hasNext()) {
                            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$notifListener$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$configurationListener$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$statusBarStateListener$1] */
    public OngoingCallController(Context context, CommonNotifCollection commonNotifCollection, OngoingCallFlags ongoingCallFlags, SystemClock systemClock, ActivityStarter activityStarter, Executor executor, IActivityManager iActivityManager, OngoingCallLogger ongoingCallLogger, DumpManager dumpManager, Optional<StatusBarWindowController> optional, Optional<SwipeStatusBarAwayGestureHandler> optional2, StatusBarStateController statusBarStateController, KeyguardCallChipController keyguardCallChipController, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener) {
        this.context = context;
        this.notifCollection = commonNotifCollection;
        this.ongoingCallFlags = ongoingCallFlags;
        this.systemClock = systemClock;
        this.activityStarter = activityStarter;
        this.mainExecutor = executor;
        this.iActivityManager = iActivityManager;
        this.logger = ongoingCallLogger;
        this.dumpManager = dumpManager;
        this.statusBarWindowController = optional;
        this.swipeStatusBarAwayGestureHandler = optional2;
        this.statusBarStateController = statusBarStateController;
        this.keyguardCallChipController = keyguardCallChipController;
        this.configurationController = configurationController;
        this.indicatorScaleGardener = indicatorScaleGardener;
    }

    public static final void access$removeChip(OngoingCallController ongoingCallController) {
        ongoingCallController.callNotificationInfo = null;
        ongoingCallController.tearDownChipView();
        ongoingCallController.swipeStatusBarAwayGestureHandler.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$removeChip$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SwipeStatusBarAwayGestureHandler) obj).removeOnGestureDetectedCallback("OngoingCallController");
            }
        });
        Iterator it = ((ArrayList) ongoingCallController.mListeners).iterator();
        while (it.hasNext()) {
            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
        }
        CallAppUidObserver callAppUidObserver = ongoingCallController.uidObserver;
        callAppUidObserver.callAppUid = null;
        callAppUidObserver.isRegistered = false;
        OngoingCallController ongoingCallController2 = OngoingCallController.this;
        ongoingCallController2.iActivityManager.unregisterUidObserver(ongoingCallController2.uidObserver);
    }

    public static void scaleCallChip(View view, float f, boolean z) {
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMarginStart(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(z ? R.dimen.samsung_ongoing_call_chip_keyguard_margin_start : R.dimen.samsung_ongoing_call_chip_margin_start) * f));
            marginLayoutParams.setMarginEnd(MathKt__MathJVMKt.roundToInt(view.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_margin_end) * f));
            view.setLayoutParams(marginLayoutParams);
            View findViewById = view.findViewById(R.id.ongoing_call_chip_background);
            findViewById.getLayoutParams().height = MathKt__MathJVMKt.roundToInt(findViewById.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_height) * f);
            int roundToInt = MathKt__MathJVMKt.roundToInt(findViewById.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_side_padding) * f);
            findViewById.setPaddingRelative(roundToInt, 0, roundToInt, 0);
            View findViewById2 = view.findViewById(R.id.ongoing_call_chip_icon);
            ViewGroup.LayoutParams layoutParams = findViewById2.getLayoutParams();
            layoutParams.width = MathKt__MathJVMKt.roundToInt(findViewById2.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            layoutParams.height = MathKt__MathJVMKt.roundToInt(findViewById2.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_size) * f);
            findViewById2.setLayoutParams(layoutParams);
            OngoingCallChronometer ongoingCallChronometer = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time);
            ((ViewGroup.MarginLayoutParams) ongoingCallChronometer.getLayoutParams()).setMarginStart(MathKt__MathJVMKt.roundToInt(ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_icon_padding) * f));
            ongoingCallChronometer.setTextSize(0, ongoingCallChronometer.getContext().getResources().getDimensionPixelSize(R.dimen.samsung_ongoing_call_chip_text_size) * f);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Active call notification: " + this.callNotificationInfo);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("Call app visible: ", this.uidObserver.isCallAppVisible, printWriter);
    }

    public final boolean hasOngoingCall() {
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo != null && callNotificationInfo.isOngoing) {
            if (!this.uidObserver.isCallAppVisible) {
                return true;
            }
            if (!(callNotificationInfo != null && callNotificationInfo.extraVisibleFlag == 0)) {
                if (callNotificationInfo != null && callNotificationInfo.extraVisibleFlag == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001f, code lost:
    
        if (r2.isOngoing == true) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setChipView(View view) {
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
                    return Integer.valueOf(((StatusBarWindowController) OngoingCallController.this.statusBarWindowController.get()).mBarHeight);
                }
            };
        }
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        boolean z = callNotificationInfo != null;
        if (z || hasOngoingCall()) {
            updateChip();
        }
    }

    public final void tearDownChipView() {
        OngoingCallChronometer ongoingCallChronometer;
        OngoingCallChronometer ongoingCallChronometer2;
        View view = this.chipView;
        if (view != null && (ongoingCallChronometer2 = (OngoingCallChronometer) view.findViewById(R.id.ongoing_call_chip_time)) != null) {
            ongoingCallChronometer2.stop();
        }
        View view2 = this.keyguardCallChipController.chipView;
        if (view2 == null || (ongoingCallChronometer = (OngoingCallChronometer) view2.findViewById(R.id.ongoing_call_chip_time)) == null) {
            return;
        }
        ongoingCallChronometer.stop();
    }

    public final void updateChip() {
        OngoingCallChronometer ongoingCallChronometer;
        OngoingCallChronometer ongoingCallChronometer2;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        if (callNotificationInfo == null) {
            return;
        }
        IndicatorScaleGardener indicatorScaleGardener = this.indicatorScaleGardener;
        Context context = this.context;
        float f = indicatorScaleGardener.getLatestScaleModel(context).ratio;
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
        Drawable drawable = context.getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
        int i = callNotificationInfo2.callChipColor;
        drawable.setColorFilter(new BlendModeColorFilter(i, i != -1 ? BlendMode.SRC : BlendMode.DST));
        view.findViewById(R.id.ongoing_call_chip_background).setBackground(drawable);
        View view2 = keyguardCallChipController.chipView;
        if (view2 != null) {
            Drawable drawable2 = view2.getContext().getResources().getDrawable(R.drawable.samsung_ongoing_call_chip_bg, null);
            drawable2.setColorFilter(new BlendModeColorFilter(i, i != -1 ? BlendMode.SRC : BlendMode.DST));
            view2.findViewById(R.id.ongoing_call_chip_background).setBackground(drawable2);
        }
        long j = callNotificationInfo.callStartTime;
        if (j > 0) {
            ongoingCallChronometer3.shouldHideText = false;
            ongoingCallChronometer3.requestLayout();
            SystemClockImpl systemClockImpl = (SystemClockImpl) this.systemClock;
            systemClockImpl.getClass();
            long currentTimeMillis = j - System.currentTimeMillis();
            systemClockImpl.getClass();
            ongoingCallChronometer3.setBase(android.os.SystemClock.elapsedRealtime() + currentTimeMillis);
            ongoingCallChronometer3.start();
            long base = ongoingCallChronometer3.getBase();
            View view3 = keyguardCallChipController.chipView;
            if (view3 != null && (ongoingCallChronometer2 = (OngoingCallChronometer) view3.findViewById(R.id.ongoing_call_chip_time)) != null) {
                ongoingCallChronometer2.shouldHideText = false;
                ongoingCallChronometer2.requestLayout();
                ongoingCallChronometer2.setBase(base);
                ongoingCallChronometer2.start();
            }
        } else {
            ongoingCallChronometer3.shouldHideText = true;
            ongoingCallChronometer3.requestLayout();
            ongoingCallChronometer3.stop();
            View view4 = keyguardCallChipController.chipView;
            if (view4 != null && (ongoingCallChronometer = (OngoingCallChronometer) view4.findViewById(R.id.ongoing_call_chip_time)) != null) {
                ongoingCallChronometer.shouldHideText = true;
                ongoingCallChronometer.requestLayout();
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
        Iterator it = ((ArrayList) this.mListeners).iterator();
        while (it.hasNext()) {
            ((OngoingCallListener) it.next()).onOngoingCallStateChanged();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        if (((com.android.systemui.flags.FeatureFlagsRelease) r0.featureFlags).isEnabled(com.android.systemui.flags.Flags.ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP) != false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateChipClickListener() {
        if (this.callNotificationInfo == null) {
            return;
        }
        if (this.isFullscreen) {
            OngoingCallFlags ongoingCallFlags = this.ongoingCallFlags;
            ongoingCallFlags.getClass();
            Flags flags = Flags.INSTANCE;
            flags.getClass();
            ReleasedFlag releasedFlag = Flags.ONGOING_CALL_STATUS_BAR_CHIP;
            FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) ongoingCallFlags.featureFlags;
            boolean z = true;
            if (featureFlagsRelease.isEnabled(releasedFlag) && featureFlagsRelease.isEnabled(Flags.ONGOING_CALL_IN_IMMERSIVE)) {
                flags.getClass();
            }
            z = false;
            if (!z) {
                View view = this.chipView;
                if (view != null) {
                    view.setOnClickListener(null);
                    return;
                }
                return;
            }
        }
        View view2 = this.chipView;
        final View findViewById = view2 != null ? view2.findViewById(R.id.ongoing_call_chip_background) : null;
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        final PendingIntent pendingIntent = callNotificationInfo != null ? callNotificationInfo.intent : null;
        if (view2 == null || findViewById == null || pendingIntent == null) {
            return;
        }
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateChipClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                OngoingCallController.this.logger.logger.log(OngoingCallLogger.OngoingCallEvents.ONGOING_CALL_CLICKED);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0106");
                ActivityStarter activityStarter = OngoingCallController.this.activityStarter;
                PendingIntent pendingIntent2 = pendingIntent;
                ActivityLaunchAnimator.Controller.Companion companion = ActivityLaunchAnimator.Controller.Companion;
                View view4 = findViewById;
                companion.getClass();
                activityStarter.postStartActivityDismissingKeyguard(pendingIntent2, ActivityLaunchAnimator.Controller.Companion.fromView(view4, 34));
            }
        });
    }

    public final void updateGestureListening() {
        CallNotificationInfo callNotificationInfo = this.callNotificationInfo;
        Optional optional = this.swipeStatusBarAwayGestureHandler;
        if (callNotificationInfo != null) {
            boolean z = false;
            if (callNotificationInfo != null && callNotificationInfo.statusBarSwipedAway) {
                z = true;
            }
            if (!z && this.isFullscreen) {
                optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateGestureListening$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = (SwipeStatusBarAwayGestureHandler) obj;
                        final OngoingCallController ongoingCallController = OngoingCallController.this;
                        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateGestureListening$2.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj2) {
                                OngoingCallController ongoingCallController2 = OngoingCallController.this;
                                ongoingCallController2.getClass();
                                if (OngoingCallControllerKt.DEBUG) {
                                    Log.d("OngoingCallController", "Swipe away gesture detected");
                                }
                                OngoingCallController.CallNotificationInfo callNotificationInfo2 = ongoingCallController2.callNotificationInfo;
                                ongoingCallController2.callNotificationInfo = callNotificationInfo2 != null ? new OngoingCallController.CallNotificationInfo(callNotificationInfo2.key, callNotificationInfo2.callStartTime, callNotificationInfo2.intent, callNotificationInfo2.uid, callNotificationInfo2.isOngoing, true, callNotificationInfo2.callChipColor, callNotificationInfo2.extraVisibleFlag) : null;
                                ongoingCallController2.swipeStatusBarAwayGestureHandler.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$onSwipeAwayGestureDetected$2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj3) {
                                        ((SwipeStatusBarAwayGestureHandler) obj3).removeOnGestureDetectedCallback("OngoingCallController");
                                    }
                                });
                                return Unit.INSTANCE;
                            }
                        };
                        Map map = swipeStatusBarAwayGestureHandler.callbacks;
                        boolean isEmpty = map.isEmpty();
                        map.put("OngoingCallController", function1);
                        if (isEmpty) {
                            swipeStatusBarAwayGestureHandler.mo200x8843713a();
                        }
                    }
                });
                return;
            }
        }
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$updateGestureListening$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((SwipeStatusBarAwayGestureHandler) obj).removeOnGestureDetectedCallback("OngoingCallController");
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(OngoingCallListener ongoingCallListener) {
        synchronized (this.mListeners) {
            if (!((ArrayList) this.mListeners).contains(ongoingCallListener)) {
                ((ArrayList) this.mListeners).add(ongoingCallListener);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(OngoingCallListener ongoingCallListener) {
        synchronized (this.mListeners) {
            ((ArrayList) this.mListeners).remove(ongoingCallListener);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m51m = TraceMetadata$$ExternalSyntheticOutline0.m51m(this.callStartTime, this.key.hashCode() * 31, 31);
            PendingIntent pendingIntent = this.intent;
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.uid, (m51m + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31, 31);
            boolean z = this.isOngoing;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (m42m + i) * 31;
            boolean z2 = this.statusBarSwipedAway;
            return Integer.hashCode(this.extraVisibleFlag) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.callChipColor, (i2 + (z2 ? 1 : z2 ? 1 : 0)) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CallNotificationInfo(key=");
            sb.append(this.key);
            sb.append(", callStartTime=");
            sb.append(this.callStartTime);
            sb.append(", intent=");
            sb.append(this.intent);
            sb.append(", uid=");
            sb.append(this.uid);
            sb.append(", isOngoing=");
            sb.append(this.isOngoing);
            sb.append(", statusBarSwipedAway=");
            sb.append(this.statusBarSwipedAway);
            sb.append(", callChipColor=");
            sb.append(this.callChipColor);
            sb.append(", extraVisibleFlag=");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.extraVisibleFlag, ")");
        }

        public /* synthetic */ CallNotificationInfo(String str, long j, PendingIntent pendingIntent, int i, boolean z, boolean z2, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, j, pendingIntent, i, z, z2, (i4 & 64) != 0 ? -1 : i2, i3);
        }
    }
}
