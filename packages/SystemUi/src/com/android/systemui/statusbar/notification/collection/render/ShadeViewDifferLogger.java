package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                return "Detach " + logMessage.getStr1() + " isTransfer=" + logMessage.getBool1() + " isParentRemoved=" + logMessage.getBool2() + " oldParent=" + logMessage.getStr2() + " newParent=" + logMessage.getStr3();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("NotifViewManager", logLevel, shadeViewDifferLogger$logDetachingChild$2, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setStr2(str2);
        obtain.setStr3(str3);
        logBuffer.commit(obtain);
    }
}
