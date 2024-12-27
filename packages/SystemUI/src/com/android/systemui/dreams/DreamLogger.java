package com.android.systemui.dreams;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DreamLogger extends Logger {
    public DreamLogger(MessageBuffer messageBuffer, String str) {
        super(messageBuffer, str);
    }

    public final void logShowOrHideStatusBarItem(String str, boolean z) {
        DreamLogger$logShowOrHideStatusBarItem$1 dreamLogger$logShowOrHideStatusBarItem$1 = new Function1() { // from class: com.android.systemui.dreams.DreamLogger$logShowOrHideStatusBarItem$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return (logMessage.getBool1() ? "Showing" : "Hiding") + " dream status bar item: " + logMessage.getInt1();
            }
        };
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, dreamLogger$logShowOrHideStatusBarItem$1, null);
        obtain.setBool1(z);
        obtain.setStr1(str);
        getBuffer().commit(obtain);
    }
}
