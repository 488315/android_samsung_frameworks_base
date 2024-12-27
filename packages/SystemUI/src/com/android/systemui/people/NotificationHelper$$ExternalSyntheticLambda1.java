package com.android.systemui.people;

import android.app.Notification;
import java.util.function.Function;

public final /* synthetic */ class NotificationHelper$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Long.valueOf(((Notification.MessagingStyle.Message) obj).getTimestamp());
    }
}
