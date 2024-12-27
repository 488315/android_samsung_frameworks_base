package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.content.Context;
import com.android.systemui.audio.soundcraft.interfaces.routine.extension.ConditionParamCreator;
import com.samsung.android.sdk.routines.automationservice.data.MetaInfo;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import java.util.HashMap;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class RoutineConditionBuilder {
    public static final RoutineConditionBuilder INSTANCE = new RoutineConditionBuilder();

    private RoutineConditionBuilder() {
    }

    public static HashMap buildConditions(Context context, String str, boolean z) {
        HashMap hashMap = new HashMap();
        ConditionParamCreator conditionParamCreator = ConditionParamCreator.INSTANCE;
        String valueOf = String.valueOf(context.getPackageManager().getPackageUid(str, 1));
        conditionParamCreator.getClass();
        MetaInfo.Companion.getClass();
        MetaInfo metaInfo = new MetaInfo("com.android.systemui", "playing_audio", null);
        ParameterValues.Companion.getClass();
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.put("playing_audio_app_uid", valueOf);
        parameterValues.put("playing_audio_app_package_name", str);
        ((HashMap) parameterValues.parameterValueMap).put("is_buds_action", new ParameterValues.ParameterValue(z));
        Unit unit = Unit.INSTANCE;
        hashMap.put(metaInfo, parameterValues);
        return hashMap;
    }
}
