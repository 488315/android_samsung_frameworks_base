package com.android.systemui.ambient.statusbar.ui;

import android.app.AlarmManager;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController$$ExternalSyntheticLambda3;
import com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider;
import com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractor;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.time.DateFormatUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class AmbientStatusBarViewController extends ViewController {
    public final AlarmManager mAlarmManager;
    public final CommunalSceneInteractor mCommunalSceneInteractor;
    public boolean mCommunalVisible;
    public final DateFormatUtil mDateFormatUtil;
    public final Optional mDreamOverlayNotificationCountProvider;
    public final DreamOverlayStateController.Callback mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mEntryAnimationsFinished;
    public final List mExtraStatusBarItems;
    public boolean mIsAttached;
    public final DreamLogger mLogger;
    public final Executor mMainExecutor;
    public final AmbientStatusBarViewController$$ExternalSyntheticLambda1 mNextAlarmCallback;
    public final NextAlarmController mNextAlarmController;
    public final AmbientStatusBarViewController$$ExternalSyntheticLambda2 mNotificationCountCallback;
    public final Resources mResources;
    public final AmbientStatusBarViewController$$ExternalSyntheticLambda0 mSensorCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public final DreamOverlayStatusBarItemsProvider mStatusBarItemsProvider;
    public final AmbientStatusBarViewController$$ExternalSyntheticLambda3 mStatusBarItemsProviderCallback;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final UserTracker mUserTracker;
    public final WifiInteractor mWifiInteractor;
    public final AnonymousClass2 mZenModeCallback;
    public final ZenModeController mZenModeController;

    public static void $r8$lambda$K0wXz652B8Jyh9bMy8eqfTnVhUo(AmbientStatusBarViewController ambientStatusBarViewController, List list) {
        ((ArrayList) ambientStatusBarViewController.mExtraStatusBarItems).clear();
        ((ArrayList) ambientStatusBarViewController.mExtraStatusBarItems).addAll(list);
        final AmbientStatusBarView ambientStatusBarView = (AmbientStatusBarView) ambientStatusBarViewController.mView;
        List list2 = (List) list.stream().map(new AmbientStatusBarViewController$$ExternalSyntheticLambda12()).collect(Collectors.toList());
        ambientStatusBarView.mExtraSystemStatusViewGroup.removeAllViews();
        list2.forEach(new Consumer() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AmbientStatusBarView.this.mExtraSystemStatusViewGroup.addView((View) obj);
            }
        });
    }

    public static void $r8$lambda$UoohdCnOV3aj3xxsZILgQEVVriI(AmbientStatusBarViewController ambientStatusBarViewController, boolean z, int i, String str) {
        String str2;
        if (ambientStatusBarViewController.mIsAttached) {
            int i2 = AmbientStatusBarView.$r8$clinit;
            switch (i) {
                case 0:
                    str2 = "notifications";
                    break;
                case 1:
                    str2 = "wifi_unavailable";
                    break;
                case 2:
                    str2 = "alarm_set";
                    break;
                case 3:
                    str2 = "camera_disabled";
                    break;
                case 4:
                    str2 = "mic_disabled";
                    break;
                case 5:
                    str2 = "mic_camera_disabled";
                    break;
                case 6:
                    str2 = "priority_mode_on";
                    break;
                case 7:
                    str2 = "assistant_attention_active";
                    break;
                default:
                    str2 = i + "(unknown)";
                    break;
            }
            ambientStatusBarViewController.mLogger.logShowOrHideStatusBarItem(str2, z);
            AmbientStatusBarView ambientStatusBarView = (AmbientStatusBarView) ambientStatusBarViewController.mView;
            View view = (View) ((HashMap) ambientStatusBarView.mStatusIcons).get(Integer.valueOf(i));
            if (view == null) {
                return;
            }
            if (z && str != null) {
                view.setContentDescription(str);
            }
            int i3 = 8;
            view.setVisibility(z ? 0 : 8);
            ViewGroup viewGroup = ambientStatusBarView.mSystemStatusViewGroup;
            int i4 = 0;
            while (true) {
                if (i4 < ambientStatusBarView.mSystemStatusViewGroup.getChildCount()) {
                    if (ambientStatusBarView.mSystemStatusViewGroup.getChildAt(i4).getVisibility() == 0) {
                        i3 = 0;
                    } else {
                        i4++;
                    }
                }
            }
            viewGroup.setVisibility(i3);
        }
    }

    public AmbientStatusBarViewController(AmbientStatusBarView ambientStatusBarView, Resources resources, Executor executor, AlarmManager alarmManager, NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil, IndividualSensorPrivacyController individualSensorPrivacyController, Optional<DreamOverlayNotificationCountProvider> optional, ZenModeController zenModeController, StatusBarWindowStateController statusBarWindowStateController, DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, DreamOverlayStateController dreamOverlayStateController, UserTracker userTracker, WifiInteractor wifiInteractor, CommunalSceneInteractor communalSceneInteractor, LogBuffer logBuffer) {
        super(ambientStatusBarView);
        this.mExtraStatusBarItems = new ArrayList();
        this.mEntryAnimationsFinished = false;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController.1
            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public final void onStateChanged() {
                AmbientStatusBarViewController ambientStatusBarViewController = AmbientStatusBarViewController.this;
                ambientStatusBarViewController.mEntryAnimationsFinished = ambientStatusBarViewController.mDreamOverlayStateController.containsState(4);
                ambientStatusBarViewController.updateVisibility$3();
                ambientStatusBarViewController.showIcon(7, R.string.assistant_attention_content_description, ambientStatusBarViewController.mDreamOverlayStateController.containsState(16));
            }
        };
        this.mSensorCallback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
            public final void onSensorBlockedChanged(int i, boolean z) {
                AmbientStatusBarViewController.this.updateMicCameraBlockedStatusIcon();
            }
        };
        this.mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                AmbientStatusBarViewController.this.updateAlarmStatusIcon();
            }
        };
        this.mZenModeCallback = new ZenModeController.Callback() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController.2
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                AmbientStatusBarViewController ambientStatusBarViewController = AmbientStatusBarViewController.this;
                ambientStatusBarViewController.showIcon(6, R.string.priority_mode_dream_overlay_content_description, ((ZenModeControllerImpl) ambientStatusBarViewController.mZenModeController).mZenMode != 0);
            }
        };
        this.mNotificationCountCallback = new AmbientStatusBarViewController$$ExternalSyntheticLambda2(this);
        this.mStatusBarItemsProviderCallback = new AmbientStatusBarViewController$$ExternalSyntheticLambda3(this);
        this.mResources = resources;
        this.mMainExecutor = executor;
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
        this.mWifiInteractor = wifiInteractor;
        this.mCommunalSceneInteractor = communalSceneInteractor;
        this.mLogger = new DreamLogger(logBuffer, "DreamStatusBarCtrl");
        statusBarWindowStateController.addListener(new StatusBarWindowStateListener() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.statusbar.window.StatusBarWindowStateListener
            public final void onStatusBarWindowStateChanged(int i) {
                final AmbientStatusBarViewController ambientStatusBarViewController = AmbientStatusBarViewController.this;
                if (ambientStatusBarViewController.mIsAttached && ambientStatusBarViewController.mEntryAnimationsFinished) {
                    ambientStatusBarViewController.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            AmbientStatusBarViewController.this.updateVisibility$3();
                        }
                    });
                }
            }
        });
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mIsAttached = true;
        JavaAdapterKt.collectFlow(this.mView, ((WifiInteractorImpl) this.mWifiInteractor).wifiNetwork, new AmbientStatusBarViewController$$ExternalSyntheticLambda5(this, 1));
        JavaAdapterKt.collectFlow(this.mView, this.mCommunalSceneInteractor.isCommunalVisible, new AmbientStatusBarViewController$$ExternalSyntheticLambda5(this, 2));
        ((NextAlarmControllerImpl) this.mNextAlarmController).addCallback(this.mNextAlarmCallback);
        updateAlarmStatusIcon();
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this.mSensorCallback);
        updateMicCameraBlockedStatusIcon();
        ((ZenModeControllerImpl) this.mZenModeController).addCallback(this.mZenModeCallback);
        showIcon(6, R.string.priority_mode_dream_overlay_content_description, ((ZenModeControllerImpl) this.mZenModeController).mZenMode != 0);
        this.mDreamOverlayNotificationCountProvider.ifPresent(new AmbientStatusBarViewController$$ExternalSyntheticLambda5(this, 3));
        DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.mStatusBarItemsProvider;
        dreamOverlayStatusBarItemsProvider.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(dreamOverlayStatusBarItemsProvider, this.mStatusBarItemsProviderCallback, 0));
        this.mDreamOverlayStateController.addCallback(this.mDreamOverlayStateCallback);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ZenModeControllerImpl) this.mZenModeController).removeCallback(this.mZenModeCallback);
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).removeCallback(this.mSensorCallback);
        ((NextAlarmControllerImpl) this.mNextAlarmController).removeCallback(this.mNextAlarmCallback);
        this.mDreamOverlayNotificationCountProvider.ifPresent(new AmbientStatusBarViewController$$ExternalSyntheticLambda5(this, 0));
        DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider = this.mStatusBarItemsProvider;
        dreamOverlayStatusBarItemsProvider.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(dreamOverlayStatusBarItemsProvider, this.mStatusBarItemsProviderCallback, 1));
        ((AmbientStatusBarView) this.mView).mExtraSystemStatusViewGroup.removeAllViews();
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        dreamOverlayStateController.setDreamOverlayStatusBarVisible(false);
        dreamOverlayStateController.mExecutor.execute(new DreamOverlayStateController$$ExternalSyntheticLambda3(dreamOverlayStateController, this.mDreamOverlayStateCallback, 0));
        this.mIsAttached = false;
    }

    public final void setFadeAmount(float f, boolean z) {
        updateVisibility$3();
        if (((AmbientStatusBarView) this.mView).getVisibility() != 0) {
            return;
        }
        if (z) {
            CrossFadeHelper.fadeOut((View) this.mView, 1.0f - f, false);
        } else {
            CrossFadeHelper.fadeIn((View) this.mView, f, false);
        }
    }

    public final void setTranslationY(float f) {
        ((AmbientStatusBarView) this.mView).setTranslationY(f);
    }

    public final void showIcon(int i, int i2, boolean z) {
        this.mMainExecutor.execute(new AmbientStatusBarViewController$$ExternalSyntheticLambda9(this, z, i, this.mResources.getString(i2)));
    }

    public final void updateAlarmStatusIcon() {
        String str;
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(((UserTrackerImpl) this.mUserTracker).getUserId());
        boolean z = nextAlarmClock != null && nextAlarmClock.getTriggerTime() > 0;
        if (z) {
            str = this.mResources.getString(R.string.accessibility_quick_settings_alarm, DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mDateFormatUtil.is24HourFormat() ? "EHm" : "Ehma"), nextAlarmClock.getTriggerTime()).toString());
        } else {
            str = null;
        }
        this.mMainExecutor.execute(new AmbientStatusBarViewController$$ExternalSyntheticLambda9(this, z, 2, str));
    }

    public final void updateMicCameraBlockedStatusIcon() {
        IndividualSensorPrivacyController individualSensorPrivacyController = this.mSensorPrivacyController;
        boolean isSensorBlocked = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(1);
        boolean isSensorBlocked2 = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(2);
        showIcon(3, R.string.camera_blocked_dream_overlay_content_description, !isSensorBlocked && isSensorBlocked2);
        showIcon(4, R.string.microphone_blocked_dream_overlay_content_description, isSensorBlocked && !isSensorBlocked2);
        showIcon(5, R.string.camera_and_microphone_blocked_dream_overlay_content_description, isSensorBlocked && isSensorBlocked2);
    }

    public final void updateVisibility$3() {
        int visibility = ((AmbientStatusBarView) this.mView).getVisibility();
        DreamOverlayStateController dreamOverlayStateController = this.mDreamOverlayStateController;
        int i = ((dreamOverlayStateController.containsState(2) || this.mStatusBarWindowStateController.windowState == 0) && !this.mCommunalVisible) ? 4 : 0;
        if (visibility == i) {
            return;
        }
        ((AmbientStatusBarView) this.mView).setVisibility(i);
        dreamOverlayStateController.setDreamOverlayStatusBarVisible(i == 0);
    }

    public void updateWifiUnavailableStatusIcon(boolean z) {
        showIcon(1, R.string.wifi_unavailable_dream_overlay_content_description, !z);
    }
}
