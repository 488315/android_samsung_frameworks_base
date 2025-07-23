package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.util.Assert;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda11;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda12;
import com.android.wm.shell.bubbles.BubbleEntry;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.ShellExecutor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@CoordinatorScope
/* loaded from: classes3.dex */
public class BubbleCoordinator implements Coordinator {
    private static final String TAG = "BubbleCoordinator";
    private final Optional<BubblesManager> mBubblesManagerOptional;
    private final Optional<Bubbles> mBubblesOptional;
    private final NotifCollection mNotifCollection;
    private NotifPipeline mNotifPipeline;
    private NotifDismissInterceptor.OnEndDismissInterception mOnEndDismissInterception;
    private Runnable mUpdateInsignificantGroupRunnable;
    private final Set<String> mInterceptedDismissalEntries = new HashSet();
    private final NotifFilter mNotifFilter = new NotifFilter(TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (BubbleCoordinator.this.mBubblesOptional.isPresent()) {
                return ((BubbleController.BubblesImpl) ((Bubbles) BubbleCoordinator.this.mBubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey());
            }
            return false;
        }
    };
    private final NotifDismissInterceptor mDismissInterceptor = new NotifDismissInterceptor() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator.2
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor
        public void cancelDismissInterception(NotificationEntry notificationEntry) {
            BubbleCoordinator.this.mInterceptedDismissalEntries.remove(notificationEntry.mKey);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor
        public String getName() {
            return BubbleCoordinator.TAG;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor
        public void setCallback(NotifDismissInterceptor.OnEndDismissInterception onEndDismissInterception) {
            BubbleCoordinator.this.mOnEndDismissInterception = onEndDismissInterception;
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor
        public boolean shouldInterceptDismissal(NotificationEntry notificationEntry) {
            ArrayList arrayList;
            boolean booleanValue;
            if (BubbleCoordinator.this.mBubblesManagerOptional.isPresent()) {
                BubblesManager bubblesManager = (BubblesManager) BubbleCoordinator.this.mBubblesManagerOptional.get();
                bubblesManager.getClass();
                if (notificationEntry == null) {
                    booleanValue = false;
                } else {
                    List attachedNotifChildren = notificationEntry.getAttachedNotifChildren();
                    Object obj = null;
                    if (attachedNotifChildren != null) {
                        arrayList = new ArrayList();
                        int i = 0;
                        while (true) {
                            ArrayList arrayList2 = (ArrayList) attachedNotifChildren;
                            if (i >= arrayList2.size()) {
                                break;
                            }
                            arrayList.add(bubblesManager.notifToBubbleEntry((NotificationEntry) arrayList2.get(i)));
                            i++;
                        }
                    } else {
                        arrayList = null;
                    }
                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    BubblesManager$$ExternalSyntheticLambda3 bubblesManager$$ExternalSyntheticLambda3 = new BubblesManager$$ExternalSyntheticLambda3(bubblesManager, attachedNotifChildren, notificationEntry);
                    Executor executor = bubblesManager.mSysuiMainExecutor;
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    bubblesImpl.getClass();
                    BubbleController$BubblesImpl$$ExternalSyntheticLambda11 bubbleController$BubblesImpl$$ExternalSyntheticLambda11 = new BubbleController$BubblesImpl$$ExternalSyntheticLambda11(executor, bubblesManager$$ExternalSyntheticLambda3);
                    ShellExecutor shellExecutor = BubbleController.this.mMainExecutor;
                    final BubbleController$BubblesImpl$$ExternalSyntheticLambda12 bubbleController$BubblesImpl$$ExternalSyntheticLambda12 = new BubbleController$BubblesImpl$$ExternalSyntheticLambda12(bubblesImpl, notifToBubbleEntry, arrayList, bubbleController$BubblesImpl$$ExternalSyntheticLambda11);
                    shellExecutor.getClass();
                    final Object[] objArr = (Object[]) Array.newInstance((Class<?>) Boolean.class, 1);
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Object[] objArr2 = objArr;
                            BubbleController$BubblesImpl$$ExternalSyntheticLambda12 bubbleController$BubblesImpl$$ExternalSyntheticLambda122 = bubbleController$BubblesImpl$$ExternalSyntheticLambda12;
                            CountDownLatch countDownLatch2 = countDownLatch;
                            objArr2[0] = bubbleController$BubblesImpl$$ExternalSyntheticLambda122.get();
                            countDownLatch2.countDown();
                        }
                    });
                    try {
                        countDownLatch.await();
                        obj = objArr[0];
                    } catch (InterruptedException unused) {
                    }
                    booleanValue = ((Boolean) obj).booleanValue();
                }
                if (booleanValue) {
                    BubbleCoordinator.this.mInterceptedDismissalEntries.add(notificationEntry.mKey);
                    return true;
                }
            }
            BubbleCoordinator.this.mInterceptedDismissalEntries.remove(notificationEntry.mKey);
            return false;
        }
    };
    private final BubblesManager.NotifCallback mNotifCallback = new BubblesManager.NotifCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator.3
        @Override // com.android.systemui.wmshell.BubblesManager.NotifCallback
        public void invalidateNotifications(String str) {
            BubbleCoordinator.this.mNotifFilter.invalidateList(str);
            if (BubbleCoordinator.this.mUpdateInsignificantGroupRunnable != null) {
                BubbleCoordinator.this.mUpdateInsignificantGroupRunnable.run();
            }
        }

        @Override // com.android.systemui.wmshell.BubblesManager.NotifCallback
        public void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i) {
            if (!BubbleCoordinator.this.isInterceptingDismissal(notificationEntry)) {
                NotifPipeline notifPipeline = BubbleCoordinator.this.mNotifPipeline;
                if (notifPipeline.mNotifCollection.getEntry(notificationEntry.mKey) != null) {
                    BubbleCoordinator.this.mNotifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
                    return;
                }
                return;
            }
            BubbleCoordinator.this.mInterceptedDismissalEntries.remove(notificationEntry.mKey);
            NotifDismissInterceptor.OnEndDismissInterception onEndDismissInterception = BubbleCoordinator.this.mOnEndDismissInterception;
            NotifDismissInterceptor notifDismissInterceptor = BubbleCoordinator.this.mDismissInterceptor;
            NotifCollection$$ExternalSyntheticLambda4 notifCollection$$ExternalSyntheticLambda4 = (NotifCollection$$ExternalSyntheticLambda4) onEndDismissInterception;
            notifCollection$$ExternalSyntheticLambda4.getClass();
            int i2 = NotifCollection.$r8$clinit;
            NotifCollection notifCollection = notifCollection$$ExternalSyntheticLambda4.f$0;
            notifCollection.getClass();
            Assert.isMainThread();
            if (notifCollection.mAttached) {
                notifCollection.checkForReentrantCall();
                if (!((ArrayList) notificationEntry.mDismissInterceptors).remove(notifDismissInterceptor)) {
                    IllegalStateException illegalStateException = new IllegalStateException(String.format("Cannot end dismiss interceptor for interceptor \"%s\" (%s)", notifDismissInterceptor.getName(), notifDismissInterceptor));
                    notifCollection.mEulogizer.record(illegalStateException);
                    throw illegalStateException;
                }
                if (((ArrayList) notificationEntry.mDismissInterceptors).size() > 0) {
                    return;
                }
                notifCollection.dismissNotification(notificationEntry, dismissedByUserStats);
            }
        }
    };

    public BubbleCoordinator(Optional<BubblesManager> optional, Optional<Bubbles> optional2, NotifCollection notifCollection) {
        this.mBubblesManagerOptional = optional;
        this.mBubblesOptional = optional2;
        this.mNotifCollection = notifCollection;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInterceptingDismissal(NotificationEntry notificationEntry) {
        return this.mInterceptedDismissalEntries.contains(notificationEntry.mKey);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$attach$0(BubblesManager bubblesManager) {
        ((ArrayList) bubblesManager.mCallbacks).add(this.mNotifCallback);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        NotifDismissInterceptor notifDismissInterceptor = this.mDismissInterceptor;
        NotifCollection notifCollection = notifPipeline.mNotifCollection;
        notifCollection.getClass();
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        if (((ArrayList) notifCollection.mDismissInterceptors).contains(notifDismissInterceptor)) {
            throw new IllegalArgumentException("Interceptor " + notifDismissInterceptor + " already added.");
        }
        ((ArrayList) notifCollection.mDismissInterceptors).add(notifDismissInterceptor);
        notifDismissInterceptor.setCallback(new NotifCollection$$ExternalSyntheticLambda4(notifCollection));
        this.mNotifPipeline.addPreGroupFilter(this.mNotifFilter);
        this.mBubblesManagerOptional.ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BubbleCoordinator.this.lambda$attach$0((BubblesManager) obj);
            }
        });
    }

    public boolean isBubbleNotificationSuppressed(NotificationEntry notificationEntry) {
        if (notificationEntry.mRanking.canBubble() && this.mBubblesOptional.isPresent()) {
            return ((BubbleController.BubblesImpl) this.mBubblesOptional.get()).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey());
        }
        return false;
    }

    public void setUpdateInsignificantGroupRunnable(Runnable runnable) {
        this.mUpdateInsignificantGroupRunnable = runnable;
    }
}
