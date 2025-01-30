package com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.extension;

import com.samsung.android.sdk.routines.automationservice.data.ActionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.data.RoutineDetail;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoutineDetailActionExtractor {
    public static final RoutineDetailActionExtractor INSTANCE = new RoutineDetailActionExtractor();

    private RoutineDetailActionExtractor() {
    }

    public static String getActionValue(RoutineDetail routineDetail, String str, String str2) {
        Object obj;
        ParameterValues parameterValues;
        ParameterValues.ParameterValue parameterValue;
        Iterator it = routineDetail.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ActionStatus) obj).tag, str)) {
                break;
            }
        }
        ActionStatus actionStatus = (ActionStatus) obj;
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null || (parameterValue = (ParameterValues.ParameterValue) ((HashMap) parameterValues.parameterValueMap).get("v2IntentParam")) == null || parameterValue.getValue() == null) ? str2 : (String) parameterValue.getValue();
    }
}
