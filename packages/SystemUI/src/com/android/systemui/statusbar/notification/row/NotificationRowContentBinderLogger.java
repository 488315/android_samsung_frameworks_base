package com.android.systemui.statusbar.notification.row;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.ArrayList;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationRowContentBinderLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer buffer;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String flagToString(int i) {
            if (i == 0) {
                return PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE;
            }
            if (i == 511) {
                return "ALL";
            }
            ArrayList arrayList = new ArrayList();
            if ((i & 1) != 0) {
                arrayList.add("CONTRACTED");
            }
            if ((i & 2) != 0) {
                arrayList.add("EXPANDED");
            }
            if ((i & 4) != 0) {
                arrayList.add("HEADS_UP");
            }
            if ((i & 8) != 0) {
                arrayList.add("PUBLIC");
            }
            if ((i & 16) != 0) {
                arrayList.add("SINGLE_LINE");
            }
            if ((i & 32) != 0) {
                arrayList.add("GROUP_SUMMARY_HEADER");
            }
            if ((i & 64) != 0) {
                arrayList.add("LOW_PRIORITY_GROUP_SUMMARY_HEADER");
            }
            return CollectionsKt___CollectionsKt.joinToString$default(arrayList, "|", null, null, null, 62);
        }

        private Companion() {
        }
    }

    public NotificationRowContentBinderLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logAsyncTaskException(String str, String str2, Throwable th) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = ExceptionsKt__ExceptionsKt.stackTraceToString(th);
        logBuffer.commit(logMessageObtain);
    }

    public final void logAsyncTaskProgress(String str, String str2) {
        LogLevel logLevel = LogLevel.DEBUG;
        NotificationRowContentBinderLogger$$ExternalSyntheticLambda0 notificationRowContentBinderLogger$$ExternalSyntheticLambda0 = new NotificationRowContentBinderLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = this.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("NotificationRowContentBinder", logLevel, notificationRowContentBinderLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(logMessageObtain);
    }
}
