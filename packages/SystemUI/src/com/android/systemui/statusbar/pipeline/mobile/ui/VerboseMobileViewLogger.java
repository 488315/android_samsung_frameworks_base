package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.domain.model.SignalIconModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VerboseMobileViewLogger {
    public final LogBuffer buffer;

    public VerboseMobileViewLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logBinderReceivedNetworkTypeIcon(View view, int i, Icon.Resource resource) {
        LogLevel logLevel = LogLevel.VERBOSE;
        VerboseMobileViewLogger$logBinderReceivedNetworkTypeIcon$2 verboseMobileViewLogger$logBinderReceivedNetworkTypeIcon$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger$logBinderReceivedNetworkTypeIcon$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String m = logMessage.getBool1() ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt2(), "resId=") : "null";
                StringBuilder m2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Binder[subId=", ", viewId=", str1, "] received new network type icon: ");
                m2.append(m);
                return m2.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$logBinderReceivedNetworkTypeIcon$2, null);
        MobileViewLogger.Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(view);
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = resource != null;
        logMessageImpl.int2 = resource != null ? resource.res : -1;
        logBuffer.commit(obtain);
    }

    public final void logBinderReceivedSignalIcon(View view, int i, SignalIconModel signalIconModel) {
        LogLevel logLevel = LogLevel.VERBOSE;
        VerboseMobileViewLogger$logBinderReceivedSignalIcon$2 verboseMobileViewLogger$logBinderReceivedSignalIcon$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger$logBinderReceivedSignalIcon$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Binder[subId=", ", viewId=", str1, "] received new signal icon: level=");
                m.append(int2);
                m.append(" showExclamation=");
                m.append(bool1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$logBinderReceivedSignalIcon$2, null);
        MobileViewLogger.Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(view);
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = signalIconModel.getLevel();
        logMessageImpl.bool1 = signalIconModel instanceof SignalIconModel.Cellular ? ((SignalIconModel.Cellular) signalIconModel).showExclamationMark : false;
        logBuffer.commit(obtain);
    }

    public final void logBinderReceivedVisibility(View view, int i, boolean z) {
        LogLevel logLevel = LogLevel.VERBOSE;
        VerboseMobileViewLogger$logBinderReceivedVisibility$2 verboseMobileViewLogger$logBinderReceivedVisibility$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.VerboseMobileViewLogger$logBinderReceivedVisibility$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Binder[subId=", ", viewId=", str1, "] received visibility: ");
                m.append(bool1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$logBinderReceivedVisibility$2, null);
        MobileViewLogger.Companion.getClass();
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = MobileViewLogger.Companion.getIdForLogging(view);
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }
}
