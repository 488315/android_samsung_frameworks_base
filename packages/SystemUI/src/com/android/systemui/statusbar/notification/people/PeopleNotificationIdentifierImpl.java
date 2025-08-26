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
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes3.dex */
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
        int iMax = 0;
        if (ranking.isConversation()) {
            i = 1;
            if (ranking.getConversationShortcutInfo() != null) {
                NotificationChannel channel = ranking.getChannel();
                i = (channel == null || !channel.isImportantConversation()) ? 2 : 3;
            }
        } else {
            i = 0;
        }
        if (i != 3) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            NotificationPersonExtractorPlugin notificationPersonExtractorPlugin = ((NotificationPersonExtractorPluginBoundary) this.personExtractor).plugin;
            int iMax2 = Math.max(i, (int) (notificationPersonExtractorPlugin != null ? notificationPersonExtractorPlugin.isPersonNotification(statusBarNotification) : 0));
            if (iMax2 != 3) {
                GroupMembershipManagerImpl groupMembershipManagerImpl = (GroupMembershipManagerImpl) this.groupManager;
                if (groupMembershipManagerImpl.isGroupSummary(notificationEntry) && (children = groupMembershipManagerImpl.getChildren(notificationEntry)) != null) {
                    TransformingSequence.AnonymousClass1 anonymousClass1 = new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(children), new Function1() { // from class: com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            return Integer.valueOf(this.f$0.getPeopleNotificationType((NotificationEntry) obj));
                        }
                    }).new AnonymousClass1();
                    while (anonymousClass1.iterator.hasNext() && (iMax = Math.max(iMax, ((Number) anonymousClass1.next()).intValue())) != 3) {
                    }
                }
                return Math.max(iMax2, iMax);
            }
        }
        return 3;
    }
}
