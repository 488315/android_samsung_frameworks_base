package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.View;
import com.android.keyguard.AbstractC0662xaf167275;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.model.SignalIconModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                String m0m = logMessage.getBool1() ? AbstractC0000x2c234b15.m0m("resId=", logMessage.getInt2()) : "null";
                StringBuilder m61m = AbstractC0662xaf167275.m61m("Binder[subId=", int1, ", viewId=", str1, "] received new network type icon: ");
                m61m.append(m0m);
                return m61m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$logBinderReceivedNetworkTypeIcon$2, null);
        MobileViewLogger.Companion.getClass();
        obtain.setStr1(MobileViewLogger.Companion.getIdForLogging(view));
        obtain.setInt1(i);
        obtain.setBool1(resource != null);
        obtain.setInt2(resource != null ? resource.res : -1);
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
                StringBuilder m61m = AbstractC0662xaf167275.m61m("Binder[subId=", int1, ", viewId=", str1, "] received new signal icon: level=");
                m61m.append(int2);
                m61m.append(" showExclamation=");
                m61m.append(bool1);
                return m61m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("VerboseMobileViewLogger", logLevel, verboseMobileViewLogger$logBinderReceivedSignalIcon$2, null);
        MobileViewLogger.Companion.getClass();
        obtain.setStr1(MobileViewLogger.Companion.getIdForLogging(view));
        obtain.setInt1(i);
        obtain.setInt2(signalIconModel.getLevel());
        obtain.setBool1(signalIconModel instanceof SignalIconModel.Cellular ? ((SignalIconModel.Cellular) signalIconModel).showExclamationMark : false);
        logBuffer.commit(obtain);
    }
}
