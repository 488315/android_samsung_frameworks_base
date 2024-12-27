package com.android.systemui.log.echo;

import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.core.LogLevel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LogcatEchoTrackerProd implements LogcatEchoTracker {
    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isBufferLoggable(LogLevel logLevel, String str) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }

    @Override // com.android.systemui.log.LogcatEchoTracker
    public final boolean isTagLoggable(LogLevel logLevel, String str) {
        return logLevel.compareTo(LogLevel.WARNING) >= 0;
    }
}
