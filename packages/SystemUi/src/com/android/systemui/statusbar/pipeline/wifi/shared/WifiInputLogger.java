package com.android.systemui.statusbar.pipeline.wifi.shared;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiInputLogger {
    public final LogBuffer buffer;

    public WifiInputLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logActivity(String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        WifiInputLogger$logActivity$2 wifiInputLogger$logActivity$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger$logActivity$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Activity: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("WifiInputLog", logLevel, wifiInputLogger$logActivity$2, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logHideDuringMobileSwitching(boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        WifiInputLogger$logHideDuringMobileSwitching$2 wifiInputLogger$logHideDuringMobileSwitching$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger$logHideDuringMobileSwitching$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("hideDuringSwitching: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("WifiInputLog", logLevel, wifiInputLogger$logHideDuringMobileSwitching$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logIntent() {
        LogLevel logLevel = LogLevel.DEBUG;
        WifiInputLogger$logIntent$2 wifiInputLogger$logIntent$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger$logIntent$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Intent received: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("WifiInputLog", logLevel, wifiInputLogger$logIntent$2, null);
        obtain.setStr1("WIFI_STATE_CHANGED_ACTION");
        logBuffer.commit(obtain);
    }

    public final void logReceivedInetCondition(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        WifiInputLogger$logReceivedInetCondition$2 wifiInputLogger$logReceivedInetCondition$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger$logReceivedInetCondition$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0000x2c234b15.m0m("ReceivedInetCondition: ", ((LogMessage) obj).getInt1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("WifiInputLog", logLevel, wifiInputLogger$logReceivedInetCondition$2, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logTestReported(boolean z) {
        LogLevel logLevel = LogLevel.DEBUG;
        WifiInputLogger$logTestReported$2 wifiInputLogger$logTestReported$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger$logTestReported$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("testReported: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("WifiInputLog", logLevel, wifiInputLogger$logTestReported$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }
}
