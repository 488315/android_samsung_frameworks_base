package com.android.systemui.statusbar.pipeline.mobile.data;

import android.content.Intent;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import com.android.settingslib.Utils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes3.dex */
public final class MobileInputLogger {
    public final LogBuffer buffer;

    public MobileInputLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logOnDataConnectionStateChanged(int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(26);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i3;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int2 = i;
        logMessageImpl.str1 = String.valueOf(i2);
        logBuffer.commit(logMessageObtain);
    }

    public final void logOnDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = telephonyDisplayInfo.toString();
        logMessageImpl.bool1 = telephonyDisplayInfo.isRoaming();
        logBuffer.commit(logMessageObtain);
    }

    public final void logOnServiceStateChanged(int i, ServiceState serviceState) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(21);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = Utils.isInService(serviceState);
        logMessageImpl.bool2 = serviceState.isEmergencyOnly();
        logMessageImpl.bool3 = serviceState.getRoaming();
        logMessageImpl.bool4 = serviceState.isUsingNonTerrestrialNetwork();
        logMessageImpl.str1 = serviceState.getOperatorAlphaShort();
        logMessageImpl.int2 = serviceState.getVoiceNetworkType();
        logBuffer.commit(logMessageObtain);
    }

    public final void logOnSignalStrengthsChanged(SignalStrength signalStrength, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(27);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = signalStrength.toString();
        logMessageImpl.int2 = signalStrength.getVendorLevel();
        logBuffer.commit(logMessageObtain);
    }

    public final void logServiceProvidersUpdatedBroadcast(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.SPN");
        String stringExtra2 = intent.getStringExtra("android.telephony.extra.DATA_SPN");
        boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
        String stringExtra3 = intent.getStringExtra("android.telephony.extra.PLMN");
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = booleanExtra;
        logMessageImpl.str1 = stringExtra;
        logMessageImpl.str2 = stringExtra2;
        logMessageImpl.bool2 = booleanExtra2;
        logMessageImpl.str3 = stringExtra3;
        logBuffer.commit(logMessageObtain);
    }

    public final void logSimSettingChanged(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(13);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
    }
}
