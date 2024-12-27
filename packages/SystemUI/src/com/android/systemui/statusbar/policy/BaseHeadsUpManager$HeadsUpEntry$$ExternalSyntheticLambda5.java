package com.android.systemui.statusbar.policy;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseHeadsUpManager.HeadsUpEntry f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda5(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = headsUpEntry;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry = this.f$0;
                String str = (String) this.f$1;
                Runnable runnable = headsUpEntry.mCancelRemoveRunnable;
                boolean z = runnable != null;
                if (z) {
                    runnable.run();
                    headsUpEntry.mCancelRemoveRunnable = null;
                }
                if (z) {
                    HeadsUpManagerLogger headsUpManagerLogger = BaseHeadsUpManager.this.mLogger;
                    NotificationEntry notificationEntry = headsUpEntry.mEntry;
                    headsUpManagerLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    HeadsUpManagerLogger$logAutoRemoveCanceled$2 headsUpManagerLogger$logAutoRemoveCanceled$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.HeadsUpManagerLogger$logAutoRemoveCanceled$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return FontProvider$$ExternalSyntheticOutline0.m("cancel auto remove of ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
                        }
                    };
                    LogBuffer logBuffer = headsUpManagerLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("HeadsUpManager", logLevel, headsUpManagerLogger$logAutoRemoveCanceled$2, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                    logMessageImpl.str1 = NotificationUtilsKt.getLogKey(notificationEntry);
                    if (str == null) {
                        str = "unknown";
                    }
                    logMessageImpl.str2 = str;
                    logBuffer.commit(obtain);
                    break;
                }
                break;
            default:
                BaseHeadsUpManager.this.removeEntry(((NotificationEntry) this.f$1).mKey, "createRemoveRunnable");
                break;
        }
    }
}
