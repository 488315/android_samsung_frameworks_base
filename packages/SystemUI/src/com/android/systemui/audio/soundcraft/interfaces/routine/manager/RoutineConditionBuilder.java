package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.extension.ConditionParamCreator;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class RoutineConditionBuilder {
    public static final RoutineConditionBuilder INSTANCE = new RoutineConditionBuilder();

    private RoutineConditionBuilder() {
    }

    public static HashMap buildConditions(Context context, String str, boolean z) throws PackageManager.NameNotFoundException {
        HashMap map = new HashMap();
        try {
            int packageUid = context.getPackageManager().getPackageUid(str, 1);
            if (packageUid != -1) {
                ConditionParamCreator conditionParamCreator = ConditionParamCreator.INSTANCE;
                String strValueOf = String.valueOf(packageUid);
                conditionParamCreator.getClass();
                ConditionParamCreator.putPlayingAudioCondition(map, str, strValueOf, z);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return map;
    }
}
