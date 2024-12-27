package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.DesktopManager;
import java.util.ArrayList;

public final class StatusBarSignalPolicy implements SignalCallback, SecurityController.SecurityControllerCallback, TunerService.Tunable {
    public static final boolean DEBUG = Log.isLoggable("StatusBarSignalPolicy", 3);
    public int mAirplaneResId;
    public final Context mContext;
    public final DesktopManager mDesktopManager;
    public final AnonymousClass1 mDesktopStatusBarIconUpdateCallback;
    public boolean mHideAirplane;
    public boolean mHideEthernet;
    public boolean mHideMobile;
    public final StatusBarIconController mIconController;
    public boolean mInitialized;
    public final NetworkController mNetworkController;
    public final SecurityController mSecurityController;
    public final String mSlotAirplane;
    public final String mSlotEthernet;
    public final String mSlotMobile;
    public final String mSlotVpn;
    public final TunerService mTunerService;
    public final Handler mHandler = Handler.getMain();
    public boolean mIsAirplaneMode = false;

    public interface DesktopCallback {
        void updateDesktopStatusBarIcons();
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.statusbar.phone.StatusBarSignalPolicy$1] */
    public StatusBarSignalPolicy(Context context, StatusBarIconController statusBarIconController, CarrierConfigTracker carrierConfigTracker, NetworkController networkController, SecurityController securityController, TunerService tunerService, DesktopManager desktopManager, CoverScreenIconController coverScreenIconController) {
        new ArrayList();
        this.mDesktopStatusBarIconUpdateCallback = new DesktopCallback() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy.1
            @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
            public final void updateDesktopStatusBarIcons() {
                StatusBarSignalPolicy statusBarSignalPolicy = StatusBarSignalPolicy.this;
                statusBarSignalPolicy.mDesktopManager.setAirplaneMode(statusBarSignalPolicy.mIsAirplaneMode, statusBarSignalPolicy.mAirplaneResId);
            }
        };
        this.mContext = context;
        this.mIconController = statusBarIconController;
        this.mNetworkController = networkController;
        this.mSecurityController = securityController;
        this.mTunerService = tunerService;
        this.mSlotAirplane = context.getString(17043119);
        this.mSlotMobile = context.getString(17043145);
        this.mSlotEthernet = context.getString(17043134);
        this.mSlotVpn = context.getString(17043165);
        context.getString(17043150);
        context.getString(17043125);
        context.getResources().getBoolean(R.bool.config_showActivity);
        this.mDesktopManager = desktopManager;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public final void onStateChanged() {
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0
            /* JADX WARN: Code restructure failed: missing block: B:28:0x00bd, code lost:
            
                if (r0 == false) goto L51;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x00b8, code lost:
            
                if (r0.checkPermission("android.permission.POST_NOTIFICATIONS", "com.samsung.android.fast") == 0) goto L47;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 319
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarSignalPolicy$$ExternalSyntheticLambda0.run():void");
            }
        });
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            boolean contains = iconHideList.contains(this.mSlotAirplane);
            boolean contains2 = iconHideList.contains(this.mSlotMobile);
            boolean contains3 = iconHideList.contains(this.mSlotEthernet);
            if (contains == this.mHideAirplane && contains2 == this.mHideMobile && contains3 == this.mHideEthernet) {
                return;
            }
            this.mHideAirplane = contains;
            this.mHideMobile = contains2;
            this.mHideEthernet = contains3;
            NetworkController networkController = this.mNetworkController;
            ((NetworkControllerImpl) networkController).removeCallback(this);
            ((NetworkControllerImpl) networkController).addCallback(this);
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setEthernetIndicators(IconState iconState) {
        int i = iconState.icon;
        String str = this.mSlotEthernet;
        StatusBarIconController statusBarIconController = this.mIconController;
        if (i <= 0) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        } else {
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(iconState.contentDescription, str, i);
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        }
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalCallback
    public final void setIsAirplaneMode(IconState iconState) {
        if (DEBUG) {
            Log.d("StatusBarSignalPolicy", "setIsAirplaneMode: icon = ".concat(iconState == null ? "" : iconState.toString()));
        }
        boolean z = iconState.visible && !this.mHideAirplane;
        this.mIsAirplaneMode = z;
        String str = this.mSlotAirplane;
        StatusBarIconController statusBarIconController = this.mIconController;
        int i = iconState.icon;
        if (!z || i <= 0) {
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, false);
        } else {
            i = R.drawable.samsung_stat_sys_airplane_mode;
            ((StatusBarIconControllerImpl) statusBarIconController).setIcon(iconState.contentDescription, str, R.drawable.samsung_stat_sys_airplane_mode);
            ((StatusBarIconControllerImpl) statusBarIconController).setIconVisibility(str, true);
        }
        this.mAirplaneResId = i;
        this.mDesktopManager.setAirplaneMode(this.mIsAirplaneMode, i);
    }
}
