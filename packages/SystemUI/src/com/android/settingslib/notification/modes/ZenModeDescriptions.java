package com.android.settingslib.notification.modes;

import android.content.Context;
import android.service.notification.ZenModeConfig;
import com.google.common.base.Platform;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ZenModeDescriptions {
    public final Context mContext;

    public ZenModeDescriptions(Context context) {
        this.mContext = context;
    }

    public final String getTriggerDescription(ZenMode zenMode) {
        if (zenMode.isManualDnd() && zenMode.isActive()) {
            long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(zenMode.mRule.getConditionId());
            if (tryParseCountdownConditionId > 0) {
                return this.mContext.getString(17043828, ZenModeConfig.getFormattedTime(this.mContext, tryParseCountdownConditionId, ZenModeConfig.isToday(tryParseCountdownConditionId), this.mContext.getUserId()));
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
