package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import java.util.function.BiFunction;

public final class ConversationNotificationManager$resetCount$1 implements BiFunction {
    public static final ConversationNotificationManager$resetCount$1 INSTANCE = new ConversationNotificationManager$resetCount$1();

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
        if (conversationState != null) {
            return new ConversationNotificationManager.ConversationState(0, conversationState.f98notification);
        }
        return null;
    }
}
