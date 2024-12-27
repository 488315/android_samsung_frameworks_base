package com.android.systemui.media.taptotransfer.common;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaTttLoggerUtils {
    public static final MediaTttLoggerUtils INSTANCE = new MediaTttLoggerUtils();

    private MediaTttLoggerUtils() {
    }

    public static void logPackageNotFound(LogBuffer logBuffer, String str, String str2) {
        LogMessage obtain = logBuffer.obtain(str, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils$logPackageNotFound$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Package ", ((LogMessage) obj).getStr1(), " could not be found");
            }
        }, null);
        ((LogMessageImpl) obtain).str1 = str2;
        logBuffer.commit(obtain);
    }

    public static void logStateChange(LogBuffer logBuffer, String str, String str2, String str3, String str4) {
        LogMessage obtain = logBuffer.obtain(str, LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils$logStateChange$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("State changed to ", str1, " for ID=", str22, " package=");
                m.append(str32);
                return m.toString();
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str3;
        logMessageImpl.str3 = str4;
        logBuffer.commit(obtain);
    }

    public static void logStateChangeError(LogBuffer logBuffer, String str, int i) {
        LogMessage obtain = logBuffer.obtain(str, LogLevel.ERROR, new Function1() { // from class: com.android.systemui.media.taptotransfer.common.MediaTttLoggerUtils$logStateChangeError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Cannot display state=", "; aborting");
            }
        }, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }
}
