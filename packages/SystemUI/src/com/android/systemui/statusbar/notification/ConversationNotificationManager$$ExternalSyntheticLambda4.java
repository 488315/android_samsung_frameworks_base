package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ConversationNotificationManager$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ConversationNotificationManager f$0;

    public /* synthetic */ ConversationNotificationManager$$ExternalSyntheticLambda4(ConversationNotificationManager conversationNotificationManager, int i) {
        this.$r8$classId = i;
        this.f$0 = conversationNotificationManager;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ExpandableNotificationRow expandableNotificationRow;
        ConversationNotificationManager conversationNotificationManager = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = ConversationNotificationManager.$r8$clinit;
                String str = (String) ((Map.Entry) obj).getKey();
                NotificationEntry entry = ((NotifPipeline) conversationNotificationManager.notifCollection).mNotifCollection.getEntry(str);
                if (entry == null || (expandableNotificationRow = entry.row) == null || !expandableNotificationRow.isExpanded(false)) {
                    return null;
                }
                return new Pair(str, entry);
            default:
                return ((NotifPipeline) conversationNotificationManager.notifCollection).mNotifCollection.getEntry((String) obj);
        }
    }
}
