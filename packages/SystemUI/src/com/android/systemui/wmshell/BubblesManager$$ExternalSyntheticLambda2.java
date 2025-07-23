package com.android.systemui.wmshell;

import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda12;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda2(BubblesManager.AnonymousClass5 anonymousClass5, String str, BubbleController$$ExternalSyntheticLambda12 bubbleController$$ExternalSyntheticLambda12) {
        this.$r8$classId = 2;
        this.f$1 = anonymousClass5;
        this.f$0 = str;
        this.f$2 = bubbleController$$ExternalSyntheticLambda12;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_CLOSED, (String) this.f$0, (String) this.f$1, (String) this.f$2);
                break;
            case 1:
                BubblesManager.AnonymousClass5 anonymousClass5 = (BubblesManager.AnonymousClass5) this.f$0;
                Set set = (Set) this.f$1;
                BubbleController$$ExternalSyntheticLambda4 bubbleController$$ExternalSyntheticLambda4 = (BubbleController$$ExternalSyntheticLambda4) this.f$2;
                anonymousClass5.getClass();
                ArrayList arrayList = new ArrayList();
                BubblesManager bubblesManager = BubblesManager.this;
                for (NotificationEntry notificationEntry : ((NotifPipeline) bubblesManager.mCommonNotifCollection).getAllNotifs()) {
                    if (((NotificationLockscreenUserManagerImpl) bubblesManager.mNotifUserManager).isCurrentProfile(notificationEntry.mSbn.getUserId()) && set.contains(notificationEntry.mKey) && bubblesManager.shouldBubbleUp(notificationEntry) && notificationEntry.isBubble()) {
                        arrayList.add(bubblesManager.notifToBubbleEntry(notificationEntry));
                    }
                }
                bubbleController$$ExternalSyntheticLambda4.accept(arrayList);
                break;
            default:
                BubblesManager.AnonymousClass5 anonymousClass52 = (BubblesManager.AnonymousClass5) this.f$1;
                String str = (String) this.f$0;
                BubbleController$$ExternalSyntheticLambda12 bubbleController$$ExternalSyntheticLambda12 = (BubbleController$$ExternalSyntheticLambda12) this.f$2;
                BubblesManager bubblesManager2 = BubblesManager.this;
                NotificationEntry entry = ((NotifPipeline) bubblesManager2.mCommonNotifCollection).mNotifCollection.getEntry(str);
                bubbleController$$ExternalSyntheticLambda12.accept(entry == null ? null : bubblesManager2.notifToBubbleEntry(entry));
                break;
        }
    }

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda2(Object obj, Object obj2, int i, Object obj3) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }
}
