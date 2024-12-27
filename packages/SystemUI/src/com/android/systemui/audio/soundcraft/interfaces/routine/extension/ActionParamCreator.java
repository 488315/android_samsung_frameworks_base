package com.android.systemui.audio.soundcraft.interfaces.routine.extension;

import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.android.systemui.audio.soundcraft.interfaces.routine.action.wearable.BudsActionType;
import com.android.systemui.audio.soundcraft.interfaces.wearable.BudsPluginInfo;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import java.util.HashMap;
import kotlin.Unit;

public final class ActionParamCreator {
    public static final ActionParamCreator INSTANCE = new ActionParamCreator();

    private ActionParamCreator() {
    }

    public static void putActionValue(HashMap hashMap, PhoneActionType phoneActionType, String str) {
        MetaInfo.Companion companion = MetaInfo.Companion;
        String actionTag = phoneActionType.getActionTag();
        companion.getClass();
        MetaInfo metaInfo = new MetaInfo("com.sec.android.app.soundalive", actionTag, null);
        ParameterValues.Companion.getClass();
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.put(phoneActionType.getParamTag(), str);
        Unit unit = Unit.INSTANCE;
        hashMap.put(metaInfo, parameterValues);
    }

    public static void putActionValue(HashMap hashMap, String str, BudsActionType budsActionType, String str2) {
        BudsPluginInfo.Companion.getClass();
        String findProjectName = BudsPluginInfo.Companion.findProjectName(str);
        if (findProjectName != null) {
            MetaInfo.Companion companion = MetaInfo.Companion;
            String tag = budsActionType.getTag(findProjectName);
            companion.getClass();
            MetaInfo metaInfo = new MetaInfo(str, tag, null);
            ParameterValues.Companion.getClass();
            ParameterValues parameterValues = new ParameterValues();
            parameterValues.put("v2IntentParam", str2);
            Unit unit = Unit.INSTANCE;
        }
    }
}
