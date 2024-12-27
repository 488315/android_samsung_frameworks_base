package com.android.systemui.statusbar.notification.collection.render;

import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShadeViewDifferLogger {
    public final LogBuffer buffer;

    public ShadeViewDifferLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logDetachingChild(String str, String str2, String str3, boolean z, boolean z2) {
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeViewDifferLogger$logDetachingChild$2 shadeViewDifferLogger$logDetachingChild$2 = new Function1() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger$logDetachingChild$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Detach ", str1, " isTransfer=", " isParentRemoved=", bool1);
                m.append(bool2);
                m.append(" oldParent=");
                m.append(str22);
                m.append(" newParent=");
                m.append(str32);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logDetachingChild$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = str3;
        logBuffer.commit(obtain);
    }
}
