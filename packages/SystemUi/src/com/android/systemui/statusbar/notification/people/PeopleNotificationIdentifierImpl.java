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
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            if (ranking.getConversationShortcutInfo() != null) {
                NotificationChannel channel = ranking.getChannel();
                i = ((channel == null || !channel.isImportantConversation()) ? 0 : 1) != 0 ? 3 : 2;
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
                    return Integer.valueOf(PeopleNotificationIdentifierImpl.this.getPeopleNotificationType((NotificationEntry) obj));
                }
            }));
            while (transformingSequence$iterator$1.hasNext() && (i2 = Math.max(i2, ((Number) transformingSequence$iterator$1.next()).intValue())) != 3) {
            }
        }
        return Math.max(max, i2);
    }
}
