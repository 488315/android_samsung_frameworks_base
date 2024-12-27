package com.android.systemui.statusbar.pipeline.shared;

import android.net.Network;
import android.net.NetworkCapabilities;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function1;

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

    public final void logOnDefaultCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        LoggerHelper.INSTANCE.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LoggerHelper$logOnCapabilitiesChanged$2 loggerHelper$logOnCapabilitiesChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.LoggerHelper$logOnCapabilitiesChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str = logMessage.getBool1() ? SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT : "";
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "on", str, "CapabilitiesChanged: net=", " capabilities=");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, loggerHelper$logOnCapabilitiesChanged$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = true;
        logMessageImpl.int1 = network.getNetId();
        logMessageImpl.str1 = networkCapabilities.toString();
        logBuffer.commit(obtain);
    }

    public final void logOnDefaultLost(Network network) {
        LoggerHelper.INSTANCE.getClass();
        LogLevel logLevel = LogLevel.INFO;
        LoggerHelper$logOnLost$2 loggerHelper$logOnLost$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.LoggerHelper$logOnLost$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "on", logMessage.getBool1() ? SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT : "", "Lost: net=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, loggerHelper$logOnLost$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = network.getNetId();
        logMessageImpl.bool1 = true;
        logBuffer.commit(obtain);
    }

    public final void logTuningChanged(String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        ConnectivityInputLogger$logTuningChanged$2 connectivityInputLogger$logTuningChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger$logTuningChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTuningChanged: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, connectivityInputLogger$logTuningChanged$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logVcnSubscriptionId(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        ConnectivityInputLogger$logVcnSubscriptionId$2 connectivityInputLogger$logVcnSubscriptionId$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger$logVcnSubscriptionId$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "vcnSubId changed: ");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("ConnectivityInputLogger", logLevel, connectivityInputLogger$logVcnSubscriptionId$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }
}
