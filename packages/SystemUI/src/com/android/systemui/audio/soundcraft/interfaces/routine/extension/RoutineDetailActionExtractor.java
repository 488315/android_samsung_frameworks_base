package com.android.systemui.audio.soundcraft.interfaces.routine.extension;

import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.samsung.android.sdk.routines.automationservice.data.ActionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.data.RoutineDetail;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class RoutineDetailActionExtractor {
    public static final RoutineDetailActionExtractor INSTANCE = new RoutineDetailActionExtractor();

    private RoutineDetailActionExtractor() {
    }

    public static String getBudsActionValue(RoutineDetail routineDetail, String str, String str2) {
        Object obj;
        ParameterValues parameterValues;
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
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null) ? str2 : parameterValues.getString("v2IntentParam", str2);
    }

    public static String getPhoneActionValue(RoutineDetail routineDetail, PhoneActionType phoneActionType, String str) {
        Object obj;
        ParameterValues parameterValues;
        Iterator it = routineDetail.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ActionStatus) obj).tag, phoneActionType.getActionTag())) {
                break;
            }
        }
        ActionStatus actionStatus = (ActionStatus) obj;
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null) ? str : parameterValues.getString(phoneActionType.getParamTag(), str);
    }
}
