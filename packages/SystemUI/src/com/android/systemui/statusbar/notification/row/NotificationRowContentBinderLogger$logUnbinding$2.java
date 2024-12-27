package com.android.systemui.statusbar.notification.row;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinderLogger;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationRowContentBinderLogger$logUnbinding$2 extends Lambda implements Function1 {
    public static final NotificationRowContentBinderLogger$logUnbinding$2 INSTANCE = new NotificationRowContentBinderLogger$logUnbinding$2();

    public NotificationRowContentBinderLogger$logUnbinding$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        NotificationRowContentBinderLogger.Companion companion = NotificationRowContentBinderLogger.Companion;
        int int1 = logMessage.getInt1();
        companion.getClass();
        return FontProvider$$ExternalSyntheticOutline0.m("unbinding views ", NotificationRowContentBinderLogger.Companion.flagToString(int1), " for ", logMessage.getStr1());
    }
}
