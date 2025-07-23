package com.android.systemui.statusbar.pipeline.shared;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConnectivityInputLogger {
    public final LogBuffer buffer;

    public ConnectivityInputLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDefaultConnectionsChanged(DefaultConnectionModel defaultConnectionModel) {
        LogLevel logLevel = LogLevel.DEBUG;
        ConnectivityInputLogger$logDefaultConnectionsChanged$2 connectivityInputLogger$logDefaultConnectionsChanged$2 = new ConnectivityInputLogger$logDefaultConnectionsChanged$2(defaultConnectionModel);
        LogBuffer logBuffer = this.buffer;
        LogMessageImpl logMessageImpl = (LogMessageImpl) logBuffer.obtain("ConnectivityInputLogger", logLevel, connectivityInputLogger$logDefaultConnectionsChanged$2, null);
        logMessageImpl.setBool1(defaultConnectionModel.wifi.isDefault);
        logMessageImpl.setBool2(defaultConnectionModel.mobile.isDefault);
        logMessageImpl.setBool3(defaultConnectionModel.carrierMerged.isDefault);
        logMessageImpl.setBool4(defaultConnectionModel.ethernet.isDefault);
        logMessageImpl.setBool5(defaultConnectionModel.btTether.isDefault);
        logMessageImpl.setInt1(defaultConnectionModel.isValidated ? 1 : 0);
        logBuffer.commit(logMessageImpl);
    }
}
