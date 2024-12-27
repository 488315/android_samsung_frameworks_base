package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda1;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleEntry;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

@CoordinatorScope
public class BubbleCoordinator implements Coordinator {
    private static final String TAG = "BubbleCoordinator";
    private final Optional<BubblesManager> mBubblesManagerOptional;
    private final Optional<Bubbles> mBubblesOptional;
    private final NotifCollection mNotifCollection;
    private NotifPipeline mNotifPipeline;
    private NotifDismissInterceptor.OnEndDismissInterception mOnEndDismissInterception;
    private final Set<String> mInterceptedDismissalEntries = new HashSet();
    private final NotifFilter mNotifFilter = new NotifFilter(TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (BubbleCoordinator.this.mBubblesOptional.isPresent()) {
                if (((BubbleController.BubblesImpl) ((Bubbles) BubbleCoordinator.this.mBubblesOptional.get())).isBubbleNotificationSuppressedFromShade(notificationEntry.mKey, notificationEntry.mSbn.getGroupKey())) {
                    return true;
                }
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

        /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3] */
        /* JADX WARN: Type inference failed for: r7v1, types: [com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda12] */
        /* JADX WARN: Type inference failed for: r8v0, types: [com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda11] */
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifDismissInterceptor
        public boolean shouldInterceptDismissal(final NotificationEntry notificationEntry) {
            final ArrayList arrayList;
            boolean booleanValue;
            if (BubbleCoordinator.this.mBubblesManagerOptional.isPresent()) {
                final BubblesManager bubblesManager = (BubblesManager) BubbleCoordinator.this.mBubblesManagerOptional.get();
                bubblesManager.getClass();
                if (notificationEntry == null) {
                    booleanValue = false;
                } else {
                    final List attachedNotifChildren = notificationEntry.getAttachedNotifChildren();
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
                    final BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    final ?? r7 = new IntConsumer() { // from class: com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda3
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            BubblesManager bubblesManager2 = BubblesManager.this;
                            List list = attachedNotifChildren;
                            NotificationEntry notificationEntry2 = notificationEntry;
                            if (i2 < 0) {
                                Iterator it = ((ArrayList) bubblesManager2.mCallbacks).iterator();
                                while (it.hasNext()) {
                                    ((BubblesManager.NotifCallback) it.next()).removeNotification(notificationEntry2, new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) bubblesManager2.mVisibilityProvider).obtain(notificationEntry2)), 12);
                                }
                            } else {
                                Iterator it2 = ((ArrayList) bubblesManager2.mCallbacks).iterator();
                                while (it2.hasNext()) {
                                    ((BubblesManager.NotifCallback) it2.next()).removeNotification((NotificationEntry) list.get(i2), new DismissedByUserStats(4, 1, ((NotificationVisibilityProviderImpl) bubblesManager2.mVisibilityProvider).obtain((NotificationEntry) list.get(i2))), 12);
                                }
                            }
                        }
                    };
                    final Executor executor = bubblesManager.mSysuiMainExecutor;
                    final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    bubblesImpl.getClass();
                    final ?? r8 = new IntConsumer() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda11
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            executor.execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda19((BubblesManager$$ExternalSyntheticLambda3) r7, i2, 0));
                        }
                    };
                    ShellExecutor shellExecutor = BubbleController.this.mMainExecutor;
                    final ?? r72 = new Supplier() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda12
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                            BubbleEntry bubbleEntry = notifToBubbleEntry;
                            List list = arrayList;
                            IntConsumer intConsumer = r8;
                            BubbleController bubbleController = BubbleController.this;
                            boolean isSummaryOfBubbles = bubbleController.isSummaryOfBubbles(bubbleEntry);
                            boolean z = true;
                            BubbleData bubbleData = bubbleController.mBubbleData;
                            if (isSummaryOfBubbles) {
                                if (list != null) {
                                    for (int i2 = 0; i2 < list.size(); i2++) {
                                        BubbleEntry bubbleEntry2 = (BubbleEntry) list.get(i2);
                                        if (bubbleData.hasAnyBubbleWithKey(bubbleEntry2.mSbn.getKey())) {
                                            Bubble anyBubbleWithkey = bubbleData.getAnyBubbleWithkey(bubbleEntry2.mSbn.getKey());
                                            if (anyBubbleWithkey != null) {
                                                anyBubbleWithkey.setSuppressNotification(true);
                                                anyBubbleWithkey.setShowDot(false);
                                            }
                                        } else {
                                            intConsumer.accept(i2);
                                        }
                                    }
                                }
                                intConsumer.accept(-1);
                                String groupKey = bubbleEntry.mSbn.getGroupKey();
                                bubbleData.mSuppressedGroupKeys.put(groupKey, bubbleEntry.mSbn.getKey());
                                BubbleData.Update update = bubbleData.mStateChange;
                                update.suppressedSummaryChanged = true;
                                update.suppressedSummaryGroup = groupKey;
                                bubbleData.dispatchPendingChanges();
                            } else {
                                Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(bubbleEntry.mSbn.getKey());
                                if (bubbleInStackWithKey == null || !bubbleEntry.isBubble()) {
                                    bubbleInStackWithKey = bubbleData.getOverflowBubbleWithKey(bubbleEntry.mSbn.getKey());
                                }
                                if (bubbleInStackWithKey != null) {
                                    bubbleInStackWithKey.setSuppressNotification(true);
                                    bubbleInStackWithKey.setShowDot(false);
                                    BubblesManager.AnonymousClass5 anonymousClass5 = bubbleController.mSysuiProxy;
                                    anonymousClass5.val$sysuiMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda1(anonymousClass5, "BubbleController.handleDismissalInterception", 1));
                                    return Boolean.valueOf(z);
                                }
                            }
                            z = false;
                            return Boolean.valueOf(z);
                        }
                    };
                    shellExecutor.getClass();
                    final Object[] objArr = (Object[]) Array.newInstance((Class<?>) Boolean.class, 1);
                    final CountDownLatch countDownLatch = new CountDownLatch(1);
                    ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Object[] objArr2 = objArr;
                            Supplier supplier = r72;
                            CountDownLatch countDownLatch2 = countDownLatch;
                            objArr2[0] = supplier.get();
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
            NotifCollection notifCollection = ((NotifCollection$$ExternalSyntheticLambda4) onEndDismissInterception).f$0;
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
}
