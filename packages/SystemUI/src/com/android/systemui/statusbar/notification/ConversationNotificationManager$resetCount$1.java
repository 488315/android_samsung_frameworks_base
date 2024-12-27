package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
