package com.android.systemui.audio.soundcraft.interfaces.routine.extension;

import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.samsung.android.sdk.routines.automationservice.data.ActionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.data.RoutineDetail;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RoutineDetailActionExtractor {
    public static final RoutineDetailActionExtractor INSTANCE = new RoutineDetailActionExtractor();

    private RoutineDetailActionExtractor() {
    }

    public static String getBudsActionValue(RoutineDetail routineDetail, String str, String str2) {
        Object obj;
        ParameterValues parameterValues;
        String string;
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
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null || (string = parameterValues.getString("v2IntentParam", str2)) == null) ? str2 : string;
    }

    public static String getPhoneActionValue(RoutineDetail routineDetail, PhoneActionType phoneActionType, String str) {
        Object obj;
        ParameterValues parameterValues;
        String string;
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
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null || (string = parameterValues.getString(phoneActionType.getParamTag(), str)) == null) ? str : string;
    }
}
