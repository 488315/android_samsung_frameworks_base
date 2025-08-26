package com.android.settingslib.notification.modes;

import android.content.Context;
import android.service.notification.ZenModeConfig;
import com.google.common.base.Platform;

/* loaded from: classes.dex */
public final class ZenModeDescriptions {
    public final Context mContext;

    public ZenModeDescriptions(Context context) {
        this.mContext = context;
    }

    public final String getTriggerDescription(ZenMode zenMode) {
        if (zenMode.isManualDnd() && zenMode.isActive()) {
            long jTryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenMode.mRule.getConditionId());
            if (jTryParseCountdownConditionId > 0) {
                return this.mContext.getString(17043832, ZenModeConfig.getFormattedTime(this.mContext, jTryParseCountdownConditionId, ZenModeConfig.isToday(jTryParseCountdownConditionId), this.mContext.getUserId()));
            }
        }
        String triggerDescription = zenMode.mRule.getTriggerDescription();
        int i = Platform.$r8$clinit;
        if (triggerDescription == null || triggerDescription.isEmpty()) {
            return null;
        }
        return triggerDescription;
    }
}
