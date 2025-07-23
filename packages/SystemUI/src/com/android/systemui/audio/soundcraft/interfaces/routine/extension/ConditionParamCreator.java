package com.android.systemui.audio.soundcraft.interfaces.routine.extension;

import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import java.util.HashMap;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ConditionParamCreator {
    public static final ConditionParamCreator INSTANCE = new ConditionParamCreator();

    private ConditionParamCreator() {
    }

    public static void putPlayingAudioCondition(HashMap hashMap, String str, String str2, boolean z) {
        MetaInfo.Companion.getClass();
        MetaInfo metaInfo = new MetaInfo("com.android.systemui", "playing_audio", null);
        ParameterValues.Companion.getClass();
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.put("playing_audio_app_uid", str2);
        parameterValues.put("playing_audio_app_package_name", str);
        ((HashMap) parameterValues.parameterValueMap).put("is_buds_action", new ParameterValues.ParameterValue(z));
        Unit unit = Unit.INSTANCE;
        hashMap.put(metaInfo, parameterValues);
    }
}
