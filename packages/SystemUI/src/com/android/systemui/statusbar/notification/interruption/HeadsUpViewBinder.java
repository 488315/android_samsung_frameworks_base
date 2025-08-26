package com.android.systemui.statusbar.notification.interruption;

import android.util.ArrayMap;
import androidx.core.os.CancellationSignal;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import java.util.Map;

/* loaded from: classes3.dex */
public class HeadsUpViewBinder {
    public final HeadsUpViewBinderLogger mLogger;
    public final Map mOngoingBindCallbacks = new ArrayMap();
    public final RowContentBindStage mStage;

    public interface HeadsUpBindCallback {
        void onHeadsUpBindFinished(NotificationEntry notificationEntry, boolean z);
    }

    public HeadsUpViewBinder(RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        this.mStage = rowContentBindStage;
        this.mLogger = headsUpViewBinderLogger;
    }

    public final void abortBindCallback(NotificationEntry notificationEntry) {
        CancellationSignal cancellationSignal = (CancellationSignal) ((ArrayMap) this.mOngoingBindCallbacks).remove(notificationEntry);
        if (cancellationSignal != null) {
            HeadsUpViewBinderLogger headsUpViewBinderLogger = this.mLogger;
            headsUpViewBinderLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 headsUpViewBinderLogger$$ExternalSyntheticLambda0 = new HeadsUpViewBinderLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = headsUpViewBinderLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("HeadsUpViewBinder", logLevel, headsUpViewBinderLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = NotificationUtilsKt.getLogKey(notificationEntry);
            logBuffer.commit(logMessageObtain);
            cancellationSignal.cancel();
        }
    }
}
