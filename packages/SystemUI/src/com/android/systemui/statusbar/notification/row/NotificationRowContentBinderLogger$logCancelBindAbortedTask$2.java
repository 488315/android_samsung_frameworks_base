package com.android.systemui.statusbar.notification.row;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

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
