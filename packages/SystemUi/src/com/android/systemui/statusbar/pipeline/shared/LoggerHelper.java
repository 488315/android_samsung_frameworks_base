package com.android.systemui.statusbar.pipeline.shared;

import android.net.Network;
import android.net.NetworkCapabilities;
import com.android.systemui.AbstractC0950x8906c950;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LoggerHelper {
    public static final LoggerHelper INSTANCE = new LoggerHelper();

    private LoggerHelper() {
    }

    public static void logOnCapabilitiesChanged(LogBuffer logBuffer, String str, Network network, NetworkCapabilities networkCapabilities, boolean z) {
        LogMessage obtain = logBuffer.obtain(str, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.LoggerHelper$logOnCapabilitiesChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str2 = logMessage.getBool1() ? "Default" : "";
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                StringBuilder m92m = AbstractC0950x8906c950.m92m("on", str2, "CapabilitiesChanged: net=", int1, " capabilities=");
                m92m.append(str1);
                return m92m.toString();
            }
        }, null);
        obtain.setBool1(z);
        obtain.setInt1(network.getNetId());
        obtain.setStr1(networkCapabilities.toString());
        logBuffer.commit(obtain);
    }

    public static void logOnLost(LogBuffer logBuffer, String str, Network network, boolean z) {
        LogMessage obtain = logBuffer.obtain(str, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.pipeline.shared.LoggerHelper$logOnLost$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "on" + (logMessage.getBool1() ? "Default" : "") + "Lost: net=" + logMessage.getInt1();
            }
        }, null);
        obtain.setInt1(network.getNetId());
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}
