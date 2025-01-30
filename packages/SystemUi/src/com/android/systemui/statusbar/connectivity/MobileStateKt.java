package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class MobileStateKt {
    public static final String access$minLog(ServiceState serviceState) {
        int state = serviceState.getState();
        boolean isEmergencyOnly = serviceState.isEmergencyOnly();
        boolean roaming = serviceState.getRoaming();
        String operatorAlphaShort = serviceState.getOperatorAlphaShort();
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("serviceState={state=", state, ",isEmergencyOnly=", isEmergencyOnly, ",roaming=");
        m76m.append(roaming);
        m76m.append(",operatorNameAlphaShort=");
        m76m.append(operatorAlphaShort);
        m76m.append("}");
        return m76m.toString();
    }

    public static final String access$minLog(SignalStrength signalStrength) {
        return "signalStrength={isGsm=" + signalStrength.isGsm() + ",level=" + signalStrength.getLevel() + "}";
    }
}
