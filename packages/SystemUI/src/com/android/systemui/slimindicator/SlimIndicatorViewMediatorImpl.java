package com.android.systemui.slimindicator;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.slimindicator.SPluginSlimIndicatorModel;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SlimIndicatorViewMediatorImpl implements SlimIndicatorViewMediator, SlimIndicatorManager {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SlimIndicatorPluginMediator mPluginMediator;
    private SettingsHelper mSettingsHelper;
    public UserSwitchListener mUserSwitchListener;
    public boolean mIsAddedTunable = false;
    public final SlimIndicatorCarrierCrew mCarrierCrew = new SlimIndicatorCarrierCrew();
    public final HashMap mSubscriberList = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SettingsListener implements SettingsHelper.OnChangedCallback {
        public SettingsListener() {
            Uri[] uriArr = {Settings.Secure.getUriFor("icon_blacklist")};
            if (SlimIndicatorViewMediatorImpl.this.mSettingsHelper != null) {
                SlimIndicatorViewMediatorImpl.this.mSettingsHelper.registerCallback(this, uriArr);
            }
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = SlimIndicatorViewMediatorImpl.this;
            if (slimIndicatorViewMediatorImpl.mSettingsHelper != null) {
                slimIndicatorViewMediatorImpl.postUpdateAll(slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist());
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class UserSwitchListener extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ UserSwitchListener(SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = SlimIndicatorViewMediatorImpl.this;
            if (slimIndicatorViewMediatorImpl.mSettingsHelper != null) {
                slimIndicatorViewMediatorImpl.postUpdateAll(slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist());
            }
        }

        private UserSwitchListener() {
        }
    }

    public static void $r8$lambda$lqyFPHTxnS2sH5Go2gjJjY1dJSs(SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl, String str) {
        slimIndicatorViewMediatorImpl.getClass();
        Log.d("[QuickStar]SlimIndicatorViewMediator", "postUpdateAll() [newValue]" + str + " [SettingsHelper]" + slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist());
        SlimIndicatorCarrierCrew slimIndicatorCarrierCrew = slimIndicatorViewMediatorImpl.mCarrierCrew;
        slimIndicatorCarrierCrew.getClass();
        if (str != null) {
            boolean contains = str.contains(SPluginSlimIndicatorModel.DB_KEY_LOCK_CARRIER);
            boolean contains2 = str.contains(SPluginSlimIndicatorModel.DB_KEY_HOME_CARRIER);
            boolean contains3 = str.contains(SPluginSlimIndicatorModel.DB_KEY_PANEL_CARRIER);
            slimIndicatorCarrierCrew.mIsLockCarrierDisabled = contains ? 1 : -1;
            slimIndicatorCarrierCrew.mIsHomeCarrierDisabled = contains2 ? 1 : -1;
            slimIndicatorCarrierCrew.mIsPanelCarrierDisabled = contains3 ? 1 : -1;
        }
        slimIndicatorViewMediatorImpl.notifyNewsToSubscribers();
    }

    public SlimIndicatorViewMediatorImpl(Context context, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPluginMediator = new SlimIndicatorPluginMediator(context, this);
        new SettingsListener();
        this.mUserSwitchListener = new UserSwitchListener(this, 0);
    }

    public final String getSubscriberTicketList() {
        if (this.mSubscriberList == null) {
            return "nothing";
        }
        StringBuilder sb = new StringBuilder("SubList:");
        Iterator it = this.mSubscriberList.keySet().iterator();
        while (it.hasNext()) {
            sb.append(((String) it.next()) + ", ");
        }
        return sb.toString();
    }

    public final boolean isBlocked(String str) {
        if (!this.mPluginMediator.mIsSPluginConnected) {
            return false;
        }
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return (TextUtils.isEmpty(str) || iconBlacklist == null || !iconBlacklist.contains(str)) ? false : true;
    }

    public final boolean isHiddenBatteryIcon() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_BATTERY_ICON);
    }

    public final boolean isHiddenLockScreenMum() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_LOCK_MUM);
    }

    public final boolean isLeftClockPosition() {
        String iconBlacklist;
        if (!this.mPluginMediator.mIsSPluginConnected || (iconBlacklist = this.mSettingsHelper.getIconBlacklist()) == null || iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_LEFT_CLOCK_POSITION)) {
            return true;
        }
        return (iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_MIDDLE_CLOCK_POSITION) || iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_RIGHT_CLOCK_POSITION)) ? false : true;
    }

    public final boolean isMiddleClockPosition() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_MIDDLE_CLOCK_POSITION);
    }

    public final boolean isRightClockPosition() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_RIGHT_CLOCK_POSITION);
    }

    public final void notifyNewsToSubscribers() {
        HashMap hashMap = this.mSubscriberList;
        if (hashMap == null) {
            return;
        }
        for (String str : hashMap.keySet()) {
            if (!TextUtils.isEmpty(str)) {
                SlimIndicatorViewSubscriber slimIndicatorViewSubscriber = (SlimIndicatorViewSubscriber) this.mSubscriberList.get(str);
                if (slimIndicatorViewSubscriber != null) {
                    slimIndicatorViewSubscriber.updateQuickStarStyle();
                } else {
                    this.mSubscriberList.remove(str);
                }
            }
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            postUpdateAll(str2);
        }
    }

    public final void postUpdateAll(final String str) {
        ((Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SlimIndicatorViewMediatorImpl.$r8$lambda$lqyFPHTxnS2sH5Go2gjJjY1dJSs(SlimIndicatorViewMediatorImpl.this, str);
            }
        });
    }

    public final void registerSubscriber(String str, SlimIndicatorViewSubscriber slimIndicatorViewSubscriber) {
        if (this.mSubscriberList == null || slimIndicatorViewSubscriber == null) {
            return;
        }
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("registerSubscriber(", str, ") to ");
        m.append(getSubscriberTicketList());
        Log.d("[QuickStar]SlimIndicatorViewMediator", m.toString());
        this.mSubscriberList.put(str, slimIndicatorViewSubscriber);
        slimIndicatorViewSubscriber.updateQuickStarStyle();
    }

    public final boolean shouldShowSecondsClock() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_SECONDS);
    }

    public final boolean showAmPmClock() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_AMPM);
    }

    public final boolean showDateClock() {
        String iconBlacklist = this.mSettingsHelper.getIconBlacklist();
        return this.mPluginMediator.mIsSPluginConnected && iconBlacklist != null && iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_SHOW_DATE);
    }

    public final void unregisterSubscriber(String str) {
        if (this.mSubscriberList == null || str == null) {
            return;
        }
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("unregisterSubscriber(", str, ") From ");
        m.append(getSubscriberTicketList());
        Log.d("[QuickStar]SlimIndicatorViewMediator", m.toString());
        this.mSubscriberList.remove(str);
    }
}
