package com.android.keyguard.logging;

import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public BiometricMessageDeferralLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }

    public final void logFrameProcessed(int i, int i2, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricMessageDeferralLogger$logFrameProcessed$2 biometricMessageDeferralLogger$logFrameProcessed$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logFrameProcessed$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("frameProcessed acquiredInfo=", int1, " totalFrames=", int2, " messageToShowOnTimeout=");
                m45m.append(str1);
                return m45m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, logLevel, biometricMessageDeferralLogger$logFrameProcessed$2, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logUpdateMessage(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricMessageDeferralLogger$logUpdateMessage$2 biometricMessageDeferralLogger$logUpdateMessage$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "updateMessage acquiredInfo=" + logMessage.getInt1() + " helpString=" + logMessage.getStr1();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, logLevel, biometricMessageDeferralLogger$logUpdateMessage$2, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }
}
