package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.util.Assert;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleCoordinator implements Coordinator {
    public final Optional mBubblesManagerOptional;
    public final Optional mBubblesOptional;
    public final NotifCollection mNotifCollection;
    public NotifPipeline mNotifPipeline;
    public NotifCollection$$ExternalSyntheticLambda8 mOnEndDismissInterception;
    public final Set mInterceptedDismissalEntries = new HashSet();
    public final C27981 mNotifFilter = new NotifFilter("BubbleCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            boolean z = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH;
            BubbleCoordinator bubbleCoordinator = BubbleCoordinator.this;
            if (z && bubbleCoordinator.mBubblesOptional.isPresent()) {
                if (((BubbleController.BubblesImpl) ((Bubbles) bubbleCoordinator.mBubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                    SubscreenDeviceModelParent subscreenDeviceModelParent = ((SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class)).mDeviceModel;
                    if (subscreenDeviceModelParent.mIsFolded && notificationEntry.canBubble()) {
                        NotificationEntry notificationEntry2 = subscreenDeviceModelParent.mBubbleReplyEntry;
                        String str = notificationEntry.mKey;
                        if (notificationEntry2 != null && str.equals(notificationEntry2.mKey)) {
                            Log.d("S.S.N.", "shouldFilterOutBubble parent - mBubbleReplyEntry key :".concat(str));
                            subscreenDeviceModelParent.mBubbleReplyEntry = null;
                        } else if (((SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(str)) != null) {
                            Log.d("S.S.N.", "shouldFilterOutBubble parent - remove Bubble Item :" + str);
                            subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                            subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                            subscreenDeviceModelParent.removeMainHashItem(notificationEntry);
                        }
                    }
                }
            }
            if (bubbleCoordinator.mBubblesOptional.isPresent()) {
                if (((BubbleController.BubblesImpl) ((Bubbles) bubbleCoordinator.mBubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                    return true;
                }
            }
            return false;
        }
    };
    public final C27992 mDismissInterceptor = new C27992();
    public final C28003 mNotifCallback = new C28003();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator$2 */
    public final class C27992 {
        public C27992() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator$3 */
    public final class C28003 {
        public C28003() {
        }

        public final void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats) {
            BubbleCoordinator bubbleCoordinator = BubbleCoordinator.this;
            boolean contains = ((HashSet) bubbleCoordinator.mInterceptedDismissalEntries).contains(notificationEntry.mKey);
            String str = notificationEntry.mKey;
            if (!contains) {
                if (bubbleCoordinator.mNotifPipeline.getEntry(str) != null) {
                    bubbleCoordinator.mNotifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
                    return;
                }
                return;
            }
            ((HashSet) bubbleCoordinator.mInterceptedDismissalEntries).remove(str);
            NotifCollection notifCollection = bubbleCoordinator.mOnEndDismissInterception.f$0;
            notifCollection.getClass();
            Assert.isMainThread();
            if (notifCollection.mAttached) {
                notifCollection.checkForReentrantCall();
                List list = notificationEntry.mDismissInterceptors;
                C27992 c27992 = bubbleCoordinator.mDismissInterceptor;
                if (((ArrayList) list).remove(c27992)) {
                    if (((ArrayList) list).size() > 0) {
                        return;
                    }
                    notifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
                } else {
                    c27992.getClass();
                    IllegalStateException illegalStateException = new IllegalStateException(String.format("Cannot end dismiss interceptor for interceptor \"%s\" (%s)", "BubbleCoordinator", c27992));
                    notifCollection.mEulogizer.record(illegalStateException);
                    throw illegalStateException;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator$1] */
    public BubbleCoordinator(Optional<BubblesManager> optional, Optional<Bubbles> optional2, NotifCollection notifCollection, LockscreenNotificationManager lockscreenNotificationManager) {
        this.mBubblesManagerOptional = optional;
        this.mBubblesOptional = optional2;
        this.mNotifCollection = notifCollection;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        ArrayList arrayList = (ArrayList) notifCollection.mDismissInterceptors;
        C27992 c27992 = this.mDismissInterceptor;
        if (arrayList.contains(c27992)) {
            throw new IllegalArgumentException("Interceptor " + c27992 + " already added.");
        }
        arrayList.add(c27992);
        BubbleCoordinator.this.mOnEndDismissInterception = new NotifCollection$$ExternalSyntheticLambda8(notifCollection);
        this.mNotifPipeline.addPreGroupFilter(this.mNotifFilter);
        this.mBubblesManagerOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BubbleCoordinator bubbleCoordinator = BubbleCoordinator.this;
                bubbleCoordinator.getClass();
                ((ArrayList) ((BubblesManager) obj).mCallbacks).add(bubbleCoordinator.mNotifCallback);
            }
        });
    }
}
