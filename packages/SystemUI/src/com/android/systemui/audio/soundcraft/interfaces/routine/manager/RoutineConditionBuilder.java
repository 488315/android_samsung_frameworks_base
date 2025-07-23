package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.systemui.audio.soundcraft.interfaces.routine.extension.ConditionParamCreator;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RoutineConditionBuilder {
    public static final RoutineConditionBuilder INSTANCE = new RoutineConditionBuilder();

    private RoutineConditionBuilder() {
    }

    public static HashMap buildConditions(Context context, String str, boolean z) {
        HashMap hashMap = new HashMap();
        try {
            int packageUid = context.getPackageManager().getPackageUid(str, 1);
            if (packageUid != -1) {
                ConditionParamCreator conditionParamCreator = ConditionParamCreator.INSTANCE;
                String valueOf = String.valueOf(packageUid);
                conditionParamCreator.getClass();
                ConditionParamCreator.putPlayingAudioCondition(hashMap, str, valueOf, z);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return hashMap;
    }
}
