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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SlimIndicatorViewMediatorImpl implements SlimIndicatorViewMediator, SlimIndicatorManager {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SlimIndicatorPluginMediator mPluginMediator;
    public final SettingsHelper mSettingsHelper;
    public UserSwitchListener mUserSwitchListener;
    public boolean mIsAddedTunable = false;
    public final SlimIndicatorCarrierCrew mCarrierCrew = new SlimIndicatorCarrierCrew();
    public final HashMap mSubscriberList = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsListener implements SettingsHelper.OnChangedCallback {
        public SettingsListener() {
            Uri[] uriArr = {Settings.Secure.getUriFor("icon_blacklist")};
            SettingsHelper settingsHelper = SlimIndicatorViewMediatorImpl.this.mSettingsHelper;
            if (settingsHelper != null) {
                settingsHelper.registerCallback(this, uriArr);
            }
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = SlimIndicatorViewMediatorImpl.this;
            SettingsHelper settingsHelper = slimIndicatorViewMediatorImpl.mSettingsHelper;
            if (settingsHelper != null) {
                slimIndicatorViewMediatorImpl.postUpdateAll(settingsHelper.getIconBlacklist());
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserSwitchListener extends KeyguardUpdateMonitorCallback {
        public /* synthetic */ UserSwitchListener(SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl, int i) {
            this();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = SlimIndicatorViewMediatorImpl.this;
            SettingsHelper settingsHelper = slimIndicatorViewMediatorImpl.mSettingsHelper;
            if (settingsHelper != null) {
                slimIndicatorViewMediatorImpl.postUpdateAll(settingsHelper.getIconBlacklist());
            }
        }

        private UserSwitchListener() {
        }
    }

    public SlimIndicatorViewMediatorImpl(Context context, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPluginMediator = new SlimIndicatorPluginMediator(context, this);
        new SettingsListener();
        this.mUserSwitchListener = new UserSwitchListener(this, 0);
    }

    public final String getSubscriberTicketList() {
        HashMap hashMap = this.mSubscriberList;
        if (hashMap == null) {
            return "nothing";
        }
        StringBuilder sb = new StringBuilder("SubList:");
        Iterator it = hashMap.keySet().iterator();
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

    public final boolean isLeftClockPosition() {
        String iconBlacklist;
        if (!this.mPluginMediator.mIsSPluginConnected || (iconBlacklist = this.mSettingsHelper.getIconBlacklist()) == null || iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_LEFT_CLOCK_POSITION)) {
            return true;
        }
        return (iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_MIDDLE_CLOCK_POSITION) || iconBlacklist.contains(SPluginSlimIndicatorModel.DB_KEY_RIGHT_CLOCK_POSITION)) ? false : true;
    }

    public final void notifyNewsToSubscribers() {
        HashMap hashMap = this.mSubscriberList;
        if (hashMap == null) {
            return;
        }
        for (String str : hashMap.keySet()) {
            if (!TextUtils.isEmpty(str)) {
                SlimIndicatorViewSubscriber slimIndicatorViewSubscriber = (SlimIndicatorViewSubscriber) hashMap.get(str);
                if (slimIndicatorViewSubscriber != null) {
                    slimIndicatorViewSubscriber.updateQuickStarStyle();
                } else {
                    hashMap.remove(str);
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
        ((Handler) Dependency.get(Dependency.MAIN_HANDLER)).post(new Runnable() { // from class: com.android.systemui.slimindicator.SlimIndicatorViewMediatorImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SlimIndicatorViewMediatorImpl slimIndicatorViewMediatorImpl = SlimIndicatorViewMediatorImpl.this;
                String str2 = str;
                slimIndicatorViewMediatorImpl.getClass();
                Log.d("[QuickStar]SlimIndicatorViewMediator", "postUpdateAll() [newValue]" + str2 + " [SettingsHelper]" + slimIndicatorViewMediatorImpl.mSettingsHelper.getIconBlacklist());
                SlimIndicatorCarrierCrew slimIndicatorCarrierCrew = slimIndicatorViewMediatorImpl.mCarrierCrew;
                slimIndicatorCarrierCrew.getClass();
                if (str2 != null) {
                    boolean contains = str2.contains(SPluginSlimIndicatorModel.DB_KEY_LOCK_CARRIER);
                    boolean contains2 = str2.contains(SPluginSlimIndicatorModel.DB_KEY_HOME_CARRIER);
                    boolean contains3 = str2.contains(SPluginSlimIndicatorModel.DB_KEY_PANEL_CARRIER);
                    slimIndicatorCarrierCrew.mIsLockCarrierDisabled = contains ? 1 : -1;
                    slimIndicatorCarrierCrew.mIsHomeCarrierDisabled = contains2 ? 1 : -1;
                    slimIndicatorCarrierCrew.mIsPanelCarrierDisabled = contains3 ? 1 : -1;
                }
                slimIndicatorViewMediatorImpl.notifyNewsToSubscribers();
            }
        });
    }

    public final void registerSubscriber(String str, SlimIndicatorViewSubscriber slimIndicatorViewSubscriber) {
        HashMap hashMap = this.mSubscriberList;
        if (hashMap == null || slimIndicatorViewSubscriber == null) {
            return;
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("registerSubscriber(", str, ") to ");
        m4m.append(getSubscriberTicketList());
        Log.d("[QuickStar]SlimIndicatorViewMediator", m4m.toString());
        hashMap.put(str, slimIndicatorViewSubscriber);
        slimIndicatorViewSubscriber.updateQuickStarStyle();
    }

    public final void unregisterSubscriber(String str) {
        HashMap hashMap = this.mSubscriberList;
        if (hashMap == null || str == null) {
            return;
        }
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("unregisterSubscriber(", str, ") From ");
        m4m.append(getSubscriberTicketList());
        Log.d("[QuickStar]SlimIndicatorViewMediator", m4m.toString());
        hashMap.remove(str);
    }
}
