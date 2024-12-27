package android.service.notification;

import android.app.Flags;
import android.app.NotificationManager;

public class ZenAdapters {
    public static ZenPolicy notificationPolicyToZenPolicy(NotificationManager.Policy policy) {
        int i;
        int i2;
        ZenPolicy.Builder allowAlarms = new ZenPolicy.Builder().allowAlarms(policy.allowAlarms());
        if (policy.allowCalls()) {
            i = prioritySendersToPeopleType(policy.allowCallsFrom());
        } else {
            i = 4;
        }
        ZenPolicy.Builder allowCalls = allowAlarms.allowCalls(i);
        if (policy.allowConversations()) {
            i2 = notificationPolicyConversationSendersToZenPolicy(policy.allowConversationsFrom());
        } else {
            i2 = 3;
        }
        ZenPolicy.Builder zenPolicyBuilder =
                allowCalls
                        .allowConversations(i2)
                        .allowEvents(policy.allowEvents())
                        .allowMedia(policy.allowMedia())
                        .allowMessages(
                                policy.allowMessages()
                                        ? prioritySendersToPeopleType(policy.allowMessagesFrom())
                                        : 4)
                        .allowReminders(policy.allowReminders())
                        .allowRepeatCallers(policy.allowRepeatCallers())
                        .allowSystem(policy.allowSystem());
        if (policy.suppressedVisualEffects != -1) {
            zenPolicyBuilder
                    .showBadges(policy.showBadges())
                    .showFullScreenIntent(policy.showFullScreenIntents())
                    .showInAmbientDisplay(policy.showAmbient())
                    .showInNotificationList(policy.showInNotificationList())
                    .showLights(policy.showLights())
                    .showPeeking(policy.showPeeking())
                    .showStatusBarIcons(policy.showStatusBarIcons());
        }
        if (Flags.modesApi()) {
            zenPolicyBuilder.allowPriorityChannels(policy.allowPriorityChannels());
        }
        return zenPolicyBuilder.build();
    }

    public static int peopleTypeToPrioritySenders(int zpPeopleType, int defaultResult) {
        switch (zpPeopleType) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return defaultResult;
        }
    }

    public static int prioritySendersToPeopleType(int npPrioritySenders) {
        switch (npPrioritySenders) {
            case 0:
                return 1;
            case 1:
                return 2;
            default:
                return 3;
        }
    }

    public static int zenPolicyConversationSendersToNotificationPolicy(
            int zpConversationSenders, int defaultResult) {
        switch (zpConversationSenders) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return defaultResult;
        }
    }

    private static int notificationPolicyConversationSendersToZenPolicy(
            int npPriorityConversationSenders) {
        switch (npPriorityConversationSenders) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }
}
