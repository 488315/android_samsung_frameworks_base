package com.android.keyguard.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public BiometricMessageDeferralLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }

    public final void logFrameIgnored(int i) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricMessageDeferralLogger$logFrameIgnored$2 biometricMessageDeferralLogger$logFrameIgnored$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logFrameIgnored$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "frameIgnored acquiredInfo=");
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, logLevel, biometricMessageDeferralLogger$logFrameIgnored$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
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
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(int1, int2, "frameProcessed acquiredInfo=", " totalFrames=", " messageToShowOnTimeout=");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, logLevel, biometricMessageDeferralLogger$logFrameProcessed$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void logUpdateMessage(int i, String str) {
        LogLevel logLevel = LogLevel.DEBUG;
        BiometricMessageDeferralLogger$logUpdateMessage$2 biometricMessageDeferralLogger$logUpdateMessage$2 = new Function1() { // from class: com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "updateMessage acquiredInfo=", " helpString=", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain(this.tag, logLevel, biometricMessageDeferralLogger$logUpdateMessage$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }
}
