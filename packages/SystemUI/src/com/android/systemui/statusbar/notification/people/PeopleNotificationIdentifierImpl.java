package com.android.systemui.statusbar.notification.people;

import android.app.NotificationChannel;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.plugins.NotificationPersonExtractorPlugin;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PeopleNotificationIdentifierImpl implements PeopleNotificationIdentifier {
    public final GroupMembershipManager groupManager;
    public final NotificationPersonExtractor personExtractor;

    public PeopleNotificationIdentifierImpl(NotificationPersonExtractor notificationPersonExtractor, GroupMembershipManager groupMembershipManager) {
        this.personExtractor = notificationPersonExtractor;
        this.groupManager = groupMembershipManager;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    /* JADX WARN: Type inference failed for: r0v8 */
    public final int getPeopleNotificationType(NotificationEntry notificationEntry) {
        int i;
        List children;
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        int i2 = 0;
        if (ranking.isConversation()) {
            i = 1;
            if (ranking.getConversationShortcutInfo() != null) {
                NotificationChannel channel = ranking.getChannel();
                i = (channel == null || !channel.isImportantConversation()) ? 2 : 3;
            }
        } else {
            i = 0;
        }
        if (i == 3) {
            return 3;
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        NotificationPersonExtractorPlugin notificationPersonExtractorPlugin = ((NotificationPersonExtractorPluginBoundary) this.personExtractor).plugin;
        int max = Math.max(i, (int) (notificationPersonExtractorPlugin != null ? notificationPersonExtractorPlugin.isPersonNotification(statusBarNotification) : 0));
        if (max == 3) {
            return 3;
        }
        GroupMembershipManagerImpl groupMembershipManagerImpl = (GroupMembershipManagerImpl) this.groupManager;
        if (groupMembershipManagerImpl.isGroupSummary(notificationEntry) && (children = groupMembershipManagerImpl.getChildren(notificationEntry)) != null) {
            TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(children), new Function1() { // from class: com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$getPeopleTypeOfSummary$childTypes$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    NotificationEntry notificationEntry2 = (NotificationEntry) obj;
                    PeopleNotificationIdentifierImpl peopleNotificationIdentifierImpl = PeopleNotificationIdentifierImpl.this;
                    Intrinsics.checkNotNull(notificationEntry2);
                    return Integer.valueOf(peopleNotificationIdentifierImpl.getPeopleNotificationType(notificationEntry2));
                }
            }));
            while (transformingSequence$iterator$1.hasNext() && (i2 = Math.max(i2, ((Number) transformingSequence$iterator$1.next()).intValue())) != 3) {
            }
        }
        return Math.max(max, i2);
    }
}
