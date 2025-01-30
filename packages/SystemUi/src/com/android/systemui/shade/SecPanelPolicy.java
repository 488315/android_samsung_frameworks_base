package com.android.systemui.shade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.UserHandle;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.shade.SecPanelPolicy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelPolicy {
    public final CentralSurfaces mCentralSurfaces;
    public final CommandQueue mCommandQueue;
    public final CommonNotifCollection mCommonNotifCollection;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public final Handler mMainHandler;
    public final SecPanelConfigurationBellTower mPanelConfigurationBellTower;
    public final SecPanelLogger mPanelLogger;
    public final SecHideInformationMirroringController mSecHideInformationMirroringController;
    public final SecPanelDeviceProvisionedListener mSecPanelDeviceProvisionedListener;
    public final SecPanelSmartMirroringManager mSecPanelSmartMirroringManager;
    public final SysuiStatusBarStateController mSysuiStatusBarStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PhoneStateManager extends BroadcastReceiver {
        public final Handler mMainHandler;
        public final NotificationShadeWindowController mNotificationShadeWindowController;
        public String mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;

        public PhoneStateManager(BroadcastDispatcher broadcastDispatcher, NotificationShadeWindowController notificationShadeWindowController, Handler handler) {
            this.mNotificationShadeWindowController = notificationShadeWindowController;
            this.mMainHandler = handler;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PHONE_STATE");
            broadcastDispatcher.registerReceiver(intentFilter, this);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.intent.action.PHONE_STATE".equals(intent.getAction())) {
                this.mPhoneState = intent.getStringExtra("state");
                this.mMainHandler.post(new SecPanelPolicy$PhoneStateManager$$ExternalSyntheticLambda0(this, 0));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SecPanelDeviceProvisionedListener implements DeviceProvisionedController.DeviceProvisionedListener {
        public final CentralSurfaces mCentralSurfaces;
        public final Handler mMainHandler;

        public SecPanelDeviceProvisionedListener(CentralSurfaces centralSurfaces, Handler handler) {
            this.mCentralSurfaces = centralSurfaces;
            this.mMainHandler = handler;
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onDeviceProvisionedChanged() {
            CentralSurfaces centralSurfaces = this.mCentralSurfaces;
            Objects.requireNonNull(centralSurfaces);
            this.mMainHandler.post(new SecPanelPolicy$PhoneStateManager$$ExternalSyntheticLambda0(centralSurfaces, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SecPanelSmartMirroringManager extends BroadcastReceiver {
        public final BroadcastDispatcher mBroadcastDispatcher;
        public final Handler mMainHandler;
        public final QSTileHost mQSTileHost;

        public SecPanelSmartMirroringManager(QSTileHost qSTileHost, BroadcastDispatcher broadcastDispatcher, Handler handler) {
            this.mQSTileHost = qSTileHost;
            this.mBroadcastDispatcher = broadcastDispatcher;
            this.mMainHandler = handler;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("SecPanelPolicy", "onReceive DisplayManager.SEM_ACTION_SET_SCREEN_RATIO_VALUE");
            this.mMainHandler.postDelayed(new SecPanelPolicy$PhoneStateManager$$ExternalSyntheticLambda0(this, 2), 10L);
        }
    }

    public SecPanelPolicy(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, QSTileHost qSTileHost, final SecPanelTouchProximityHelper secPanelTouchProximityHelper, SecHideInformationMirroringController secHideInformationMirroringController, BootAnimationFinishedCache bootAnimationFinishedCache, CommonNotifCollection commonNotifCollection, SecPanelConfigurationBellTower secPanelConfigurationBellTower, SecPanelExpansionStateNotifier secPanelExpansionStateNotifier, final DeviceProvisionedController deviceProvisionedController, HeadsUpManagerPhone headsUpManagerPhone, CentralSurfaces centralSurfaces, StatusBarWindowController statusBarWindowController, SecPanelLogger secPanelLogger, BroadcastDispatcher broadcastDispatcher, ShadeController shadeController, CommandQueue commandQueue, NotificationShadeWindowController notificationShadeWindowController) {
        Handler handler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);
        this.mMainHandler = handler;
        this.mSysuiStatusBarStateController = sysuiStatusBarStateController;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        new PhoneStateManager(broadcastDispatcher, notificationShadeWindowController, handler);
        this.mCommonNotifCollection = commonNotifCollection;
        this.mPanelConfigurationBellTower = secPanelConfigurationBellTower;
        this.mSecPanelDeviceProvisionedListener = new SecPanelDeviceProvisionedListener(centralSurfaces, handler);
        this.mSecPanelSmartMirroringManager = new SecPanelSmartMirroringManager(qSTileHost, broadcastDispatcher, handler);
        this.mSecHideInformationMirroringController = secHideInformationMirroringController;
        this.mCentralSurfaces = centralSurfaces;
        this.mPanelLogger = secPanelLogger;
        this.mCommandQueue = commandQueue;
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.shade.SecPanelPolicy$$ExternalSyntheticLambda0
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                final SecPanelPolicy secPanelPolicy = SecPanelPolicy.this;
                secPanelPolicy.getClass();
                final SecPanelTouchProximityHelper secPanelTouchProximityHelper2 = secPanelTouchProximityHelper;
                final DeviceProvisionedController deviceProvisionedController2 = deviceProvisionedController;
                secPanelPolicy.mMainHandler.post(new Runnable() { // from class: com.android.systemui.shade.SecPanelPolicy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SecPanelPolicy secPanelPolicy2 = SecPanelPolicy.this;
                        SecPanelTouchProximityHelper secPanelTouchProximityHelper3 = secPanelTouchProximityHelper2;
                        DeviceProvisionedController deviceProvisionedController3 = deviceProvisionedController2;
                        secPanelPolicy2.getClass();
                        if (secPanelTouchProximityHelper3.mIsSupportProximity < 0) {
                            secPanelTouchProximityHelper3.mIsSupportProximity = ((SensorManager) secPanelTouchProximityHelper3.mContext.getSystemService("sensor")).getDefaultSensor(8) != null ? 1 : 0;
                        }
                        boolean z = secPanelTouchProximityHelper3.mIsSupportProximity > 0;
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("init() isSupportProximity:", z, "SecPanelTouchProximityHelper");
                        if (z) {
                            secPanelTouchProximityHelper3.mBroadcastDispatcher.registerReceiver(secPanelTouchProximityHelper3, new IntentFilter("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY"), null, UserHandle.ALL);
                        }
                        secPanelPolicy2.mSecHideInformationMirroringController.init();
                        SecPanelPolicy.SecPanelDeviceProvisionedListener secPanelDeviceProvisionedListener = secPanelPolicy2.mSecPanelDeviceProvisionedListener;
                        ((DeviceProvisionedControllerImpl) deviceProvisionedController3).addCallback(secPanelDeviceProvisionedListener);
                        secPanelDeviceProvisionedListener.onDeviceProvisionedChanged();
                        SecPanelPolicy.SecPanelSmartMirroringManager secPanelSmartMirroringManager = secPanelPolicy2.mSecPanelSmartMirroringManager;
                        secPanelSmartMirroringManager.getClass();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE");
                        secPanelSmartMirroringManager.mBroadcastDispatcher.registerReceiver(intentFilter, secPanelSmartMirroringManager);
                    }
                });
            }
        });
    }
}
