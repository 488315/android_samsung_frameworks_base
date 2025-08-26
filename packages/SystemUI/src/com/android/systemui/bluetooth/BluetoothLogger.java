package com.android.systemui.bluetooth;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* loaded from: classes.dex */
public final class BluetoothLogger {
    public final LogBuffer logBuffer;

    public BluetoothLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logProfileConnectionStateChanged(int i, String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(8);
        LogBuffer logBuffer = this.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str2 = str2;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
    }
}
