package com.android.systemui.audio.soundcraft.interfaces.routine.extension;

import com.android.systemui.audio.soundcraft.interfaces.routine.action.phone.PhoneActionType;
import com.samsung.android.sdk.routines.automationservice.data.ActionStatus;
import com.samsung.android.sdk.routines.automationservice.data.ParameterValues;
import com.samsung.android.sdk.routines.automationservice.data.RoutineDetail;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class RoutineDetailActionExtractor {
    public static final RoutineDetailActionExtractor INSTANCE = new RoutineDetailActionExtractor();

    private RoutineDetailActionExtractor() {
    }

    public static String getBudsActionValue(RoutineDetail routineDetail, String str, String str2) {
        Object next;
        ParameterValues parameterValues;
        String string;
        Iterator it = routineDetail.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((ActionStatus) next).tag, str)) {
                break;
            }
        }
        ActionStatus actionStatus = (ActionStatus) next;
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null || (string = parameterValues.getString("v2IntentParam", str2)) == null) ? str2 : string;
    }

    public static String getPhoneActionValue(RoutineDetail routineDetail, PhoneActionType phoneActionType, String str) {
        Object next;
        ParameterValues parameterValues;
        String string;
        Iterator it = routineDetail.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((ActionStatus) next).tag, phoneActionType.getActionTag())) {
                break;
            }
        }
        ActionStatus actionStatus = (ActionStatus) next;
        return (actionStatus == null || (parameterValues = actionStatus.parameterValues) == null || (string = parameterValues.getString(phoneActionType.getParamTag(), str)) == null) ? str : string;
    }
}
