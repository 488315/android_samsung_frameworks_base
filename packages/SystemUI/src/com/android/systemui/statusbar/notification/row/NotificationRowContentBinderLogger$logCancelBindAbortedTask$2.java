package com.android.systemui.statusbar.notification.row;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NotificationRowContentBinderLogger$logCancelBindAbortedTask$2 extends Lambda implements Function1 {
    public static final NotificationRowContentBinderLogger$logCancelBindAbortedTask$2 INSTANCE = new NotificationRowContentBinderLogger$logCancelBindAbortedTask$2();

    public NotificationRowContentBinderLogger$logCancelBindAbortedTask$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborted task to cancel binding ", ((LogMessage) obj).getStr1());
    }
}
