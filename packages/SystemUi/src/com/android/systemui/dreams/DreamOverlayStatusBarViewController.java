package com.android.systemui.dreams;

import android.app.AlarmManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.text.format.DateFormat;
import com.android.systemui.R;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.touch.TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.DateFormatUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayStatusBarViewController extends ViewController {
    public final AlarmManager mAlarmManager;
    public final ConnectivityManager mConnectivityManager;
    public final DateFormatUtil mDateFormatUtil;
    public final Optional mDreamOverlayNotificationCountProvider;
    public final C12782 mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mEntryAnimationsFinished;
    public final List mExtraStatusBarItems;
    public boolean mIsAttached;
    public final Executor mMainExecutor;
    public final C12771 mNetworkCallback;
    public final NetworkRequest mNetworkRequest;
    public final DreamOverlayStatusBarViewController$$ExternalSyntheticLambda2 mNextAlarmCallback;
    public final NextAlarmController mNextAlarmController;
    public final DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 mNotificationCountCallback;
    public final Resources mResources;
    public final DreamOverlayStatusBarViewController$$ExternalSyntheticLambda1 mSensorCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public final DreamOverlayStatusBarItemsProvider mStatusBarItemsProvider;
    public final DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4 mStatusBarItemsProviderCallback;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
    public final UserTracker mUserTracker;
    public final C12793 mZenModeCallback;
    public final ZenModeController mZenModeController;

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$3] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$1] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$2] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda2] */
    public DreamOverlayStatusBarViewController(DreamOverlayStatusBarView dreamOverlayStatusBarView, Resources resources, Executor executor, ConnectivityManager connectivityManager, TouchInsetManager.TouchInsetSession touchInsetSession, AlarmManager alarmManager, NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil, IndividualSensorPrivacyController individualSensorPrivacyController, Optional<DreamOverlayNotificationCountProvider> optional, ZenModeController zenModeController, StatusBarWindowStateController statusBarWindowStateController, DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, DreamOverlayStateController dreamOverlayStateController, UserTracker userTracker) {
        super(dreamOverlayStatusBarView);
        this.mExtraStatusBarItems = new ArrayList();
        this.mEntryAnimationsFinished = false;
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addTransportType(1).build();
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.2
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = DreamOverlayStatusBarViewController.this;
                dreamOverlayStatusBarViewController.mEntryAnimationsFinished = dreamOverlayStatusBarViewController.mDreamOverlayStateController.containsState(4);
                dreamOverlayStatusBarViewController.updateVisibility();
                dreamOverlayStatusBarViewController.showIcon(7, R.string.assistant_attention_content_description, dreamOverlayStatusBarViewController.mDreamOverlayStateController.containsState(16));
            }
        };
        this.mSensorCallback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
            public final void onSensorBlockedChanged(int i, boolean z) {
                DreamOverlayStatusBarViewController.this.updateMicCameraBlockedStatusIcon();
            }
        };
        this.mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                DreamOverlayStatusBarViewController.this.updateAlarmStatusIcon();
            }
        };
        this.mZenModeCallback = new ZenModeController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.3
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = DreamOverlayStatusBarViewController.this;
                dreamOverlayStatusBarViewController.showIcon(6, R.string.priority_mode_dream_overlay_content_description, ((ZenModeControllerImpl) dreamOverlayStatusBarViewController.mZenModeController).mZenMode != 0);
            }
        };
        this.mNotificationCountCallback = new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3(this);
        this.mStatusBarItemsProviderCallback = new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4(this);
        this.mResources = resources;
        this.mMainExecutor = executor;
        this.mConnectivityManager = connectivityManager;
        this.mTouchInsetSession = touchInsetSession;
        this.mAlarmManager = alarmManager;
        this.mNextAlarmController = nextAlarmController;
        this.mDateFormatUtil = dateFormatUtil;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mDreamOverlayNotificationCountProvider = optional;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mStatusBarItemsProvider = dreamOverlayStatusBarItemsProvider;
        this.mZenModeController = zenModeController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mUserTracker = userTracker;
        ((HashSet) statusBarWindowStateController.listeners).add(new StatusBarWindowStateListener() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                final DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = DreamOverlayStatusBarViewController.this;
                if (dreamOverlayStatusBarViewController.mIsAttached && dreamOverlayStatusBarViewController.mEntryAnimationsFinished) {
                    dreamOverlayStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            DreamOverlayStatusBarViewController.this.updateVisibility();
                        }
                    });
                }
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mIsAttached = true;
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback);
        updateWifiUnavailableStatusIcon();
        ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this.mNextAlarmCallback);
        updateAlarmStatusIcon();
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this.mSensorCallback);
        updateMicCameraBlockedStatusIcon();
        ZenModeController zenModeController = this.mZenModeController;
        ((ZenModeControllerImpl) zenModeController).addCallback(this.mZenModeCallback);
        showIcon(6, R.string.priority_mode_dream_overlay_content_description, ((ZenModeControllerImpl) zenModeController).mZenMode != 0);
        this.mDreamOverlayNotificationCountProvider.ifPresent(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0(this, 0));
        DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.mStatusBarItemsProvider;
        dreamOverlayStatusBarItemsProvider.getClass();
        dreamOverlayStatusBarItemsProvider.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(dreamOverlayStatusBarItemsProvider, this.mStatusBarItemsProviderCallback, 1));
        this.mDreamOverlayStateController.addCallback((DreamOverlayStateController.Callback) this.mDreamOverlayStateCallback);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ZenModeControllerImpl) this.mZenModeController).removeCallback(this.mZenModeCallback);
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).removeCallback(this.mSensorCallback);
        ((NextAlarmControllerImpl) this.mNextAlarmController).removeCallback(this.mNextAlarmCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.mDreamOverlayNotificationCountProvider.ifPresent(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0(this, 1));
        DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.mStatusBarItemsProvider;
        dreamOverlayStatusBarItemsProvider.getClass();
        dreamOverlayStatusBarItemsProvider.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(dreamOverlayStatusBarItemsProvider, this.mStatusBarItemsProviderCallback, 0));
        ((DreamOverlayStatusBarView) this.mView).mExtraSystemStatusViewGroup.removeAllViews();
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        dreamOverlayStateController.getClass();
        dreamOverlayStateController.modifyState(1, 32);
        dreamOverlayStateController.removeCallback((DreamOverlayStateController.Callback) this.mDreamOverlayStateCallback);
        TouchInsetManager.TouchInsetSession touchInsetSession = this.mTouchInsetSession;
        touchInsetSession.getClass();
        touchInsetSession.mExecutor.execute(new TouchInsetManager$TouchInsetSession$$ExternalSyntheticLambda1(touchInsetSession, 1));
        this.mIsAttached = false;
    }

    public final void showIcon(int i, int i2, boolean z) {
        this.mMainExecutor.execute(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6(this, i, z, this.mResources.getString(i2)));
    }

    public final void updateAlarmStatusIcon() {
        String str;
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(((UserTrackerImpl) this.mUserTracker).getUserId());
        boolean z = nextAlarmClock != null && nextAlarmClock.getTriggerTime() > 0;
        if (z) {
            DateFormatUtil dateFormatUtil = this.mDateFormatUtil;
            str = this.mResources.getString(R.string.accessibility_quick_settings_alarm, DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), DateFormat.is24HourFormat(dateFormatUtil.mContext, ((UserTrackerImpl) dateFormatUtil.mUserTracker).getUserId()) ? "EHm" : "Ehma"), nextAlarmClock.getTriggerTime()).toString());
        } else {
            str = null;
        }
        this.mMainExecutor.execute(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6(this, 2, z, str));
    }

    public final void updateMicCameraBlockedStatusIcon() {
        IndividualSensorPrivacyController individualSensorPrivacyController = this.mSensorPrivacyController;
        boolean isSensorBlocked = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(1);
        boolean isSensorBlocked2 = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(2);
        showIcon(3, R.string.camera_blocked_dream_overlay_content_description, !isSensorBlocked && isSensorBlocked2);
        showIcon(4, R.string.microphone_blocked_dream_overlay_content_description, isSensorBlocked && !isSensorBlocked2);
        showIcon(5, R.string.camera_and_microphone_blocked_dream_overlay_content_description, isSensorBlocked && isSensorBlocked2);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0028 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVisibility() {
        boolean z;
        int i;
        int visibility = ((DreamOverlayStatusBarView) this.mView).getVisibility();
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        if (!dreamOverlayStateController.containsState(2)) {
            if (!(this.mStatusBarWindowStateController.windowState == 0)) {
                z = true;
                i = !z ? 0 : 4;
                if (visibility != i) {
                    return;
                }
                ((DreamOverlayStatusBarView) this.mView).setVisibility(i);
                dreamOverlayStateController.modifyState(i == 0 ? 2 : 1, 32);
                return;
            }
        }
        z = false;
        if (!z) {
        }
        if (visibility != i) {
        }
    }

    public final void updateWifiUnavailableStatusIcon() {
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        showIcon(1, R.string.wifi_unavailable_dream_overlay_content_description, !(networkCapabilities != null && networkCapabilities.hasTransport(1)));
    }
}
