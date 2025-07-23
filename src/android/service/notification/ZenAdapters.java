package android.service.notification;

import android.app.NotificationManager;
import android.service.notification.ZenPolicy;

/* loaded from: classes3.dex */
public class ZenAdapters {
    private static int notificationPolicyConversationSendersToZenPolicy(int i) {
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                i2 = 3;
                if (i != 3) {
                    return 0;
                }
            }
        }
        return i2;
    }

    public static int peopleTypeToPrioritySenders(int i, int i2) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            return i2;
        }
        return 2;
    }

    public static int prioritySendersToPeopleType(int i) {
        if (i != 0) {
            return i != 1 ? 3 : 2;
        }
        return 1;
    }

    public static int zenPolicyConversationSendersToNotificationPolicy(int i, int i2) {
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                i3 = 3;
                if (i != 3) {
                    return i2;
                }
            }
        }
        return i3;
    }

    public static ZenPolicy notificationPolicyToZenPolicy(NotificationManager.Policy policy) {
        ZenPolicy.Builder allowPriorityChannels = new ZenPolicy.Builder().allowAlarms(policy.allowAlarms()).allowCalls(policy.allowCalls() ? prioritySendersToPeopleType(policy.allowCallsFrom()) : 4).allowConversations(policy.allowConversations() ? notificationPolicyConversationSendersToZenPolicy(policy.allowConversationsFrom()) : 3).allowEvents(policy.allowEvents()).allowMedia(policy.allowMedia()).allowMessages(policy.allowMessages() ? prioritySendersToPeopleType(policy.allowMessagesFrom()) : 4).allowReminders(policy.allowReminders()).allowRepeatCallers(policy.allowRepeatCallers()).allowSystem(policy.allowSystem()).allowPriorityChannels(policy.allowPriorityChannels());
        if (policy.suppressedVisualEffects != -1) {
            allowPriorityChannels.showBadges(policy.showBadges()).showFullScreenIntent(policy.showFullScreenIntents()).showInAmbientDisplay(policy.showAmbient()).showInNotificationList(policy.showInNotificationList()).showLights(policy.showLights()).showPeeking(policy.showPeeking()).showStatusBarIcons(policy.showStatusBarIcons());
        }
        return allowPriorityChannels.build();
    }
}
