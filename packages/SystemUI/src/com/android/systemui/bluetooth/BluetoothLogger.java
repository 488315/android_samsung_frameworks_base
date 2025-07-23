package com.android.systemui.bluetooth;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str2 = str2;
        logMessageImpl.int1 = i;
        logBuffer.commit(obtain);
    }
}
