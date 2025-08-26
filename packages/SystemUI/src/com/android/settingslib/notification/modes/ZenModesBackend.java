package com.android.settingslib.notification.modes;

import android.app.AutomaticZenRule;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ZenModesBackend {
    public static ZenModesBackend sInstance;
    public final Context mContext;
    public final NotificationManager mNotificationManager;

    public ZenModesBackend(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(NotificationManager.class);
    }

    public static void setInstance(ZenModesBackend zenModesBackend) {
        sInstance = zenModesBackend;
    }

    public final List getModes() {
        Map<String, AutomaticZenRule> automaticZenRules = this.mNotificationManager.getAutomaticZenRules();
        ZenModeConfig zenModeConfig = this.mNotificationManager.getZenModeConfig();
        ArrayList arrayList = new ArrayList();
        ZenModeConfig.ZenRule zenRule = zenModeConfig.manualRule;
        arrayList.add(ZenMode.manualDndMode(new AutomaticZenRule.Builder(this.mContext.getString(R.string.zen_mode_do_not_disturb_name), zenRule.conditionId).setPackage("android").setType(0).setZenPolicy(zenRule.zenPolicy).setDeviceEffects(zenRule.zenDeviceEffects).setManualInvocationAllowed(true).setConfigurationActivity((ComponentName) null).setInterruptionFilter(zenModeConfig.isManualActive() ? NotificationManager.zenModeToInterruptionFilter(zenRule.zenMode) : 2).build(), zenModeConfig.isManualActive()));
        for (Map.Entry<String, AutomaticZenRule> entry : automaticZenRules.entrySet()) {
            String key = entry.getKey();
            ZenModeConfig.ZenRule zenRule2 = (ZenModeConfig.ZenRule) zenModeConfig.automaticRules.get(key);
            if (zenRule2 != null) {
                arrayList.add(new ZenMode(key, entry.getValue(), zenRule2));
            } else {
                Log.w("ZenModeBackend", "Found AZR " + entry.getValue() + " but no corresponding entry in ZenModeConfig (" + zenModeConfig + "). Skipping");
            }
        }
        arrayList.sort(ZenMode.PRIORITIZING_COMPARATOR);
        return arrayList;
    }
}
