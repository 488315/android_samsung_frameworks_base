package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingCardController;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingActivityController$createCardController$1 implements OngoingCardController.OnStateEventListener {
    public final /* synthetic */ OngoingActivityController this$0;

    public OngoingActivityController$createCardController$1(OngoingActivityController ongoingActivityController) {
        this.this$0 = ongoingActivityController;
    }

    public final void onAllowStateChanged(boolean z) {
        final OngoingActivityController ongoingActivityController = this.this$0;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("onAllowStateChanged : delayedEntry size = ", ongoingActivityController.delayedEntry.size(), " : ", z, "{OngoingActivityController}");
        if (ongoingActivityController.isUpdateNotAllowed != z) {
            ongoingActivityController.isUpdateNotAllowed = z;
            if (z) {
                return;
            }
            ongoingActivityController.delayedEntry.forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$createCardController$1$onAllowStateChanged$1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    String str = (String) obj;
                    Pair pair = (Pair) obj2;
                    if (((Boolean) pair.getSecond()).booleanValue()) {
                        OngoingActivityController.access$updateOngoingActivityData(OngoingActivityController.this, (NotificationEntry) pair.getFirst());
                        Log.d("{OngoingActivityController}", "update delayedEntry  = ".concat(str));
                    } else {
                        OngoingActivityController.access$removeOngoingActivityData(OngoingActivityController.this, (NotificationEntry) pair.getFirst());
                        Log.d("{OngoingActivityController}", "remove delayedEntry  = ".concat(str));
                    }
                }
            });
            ongoingActivityController.delayedEntry.clear();
        }
    }

    public final void onClosed() {
        Log.d("{OngoingActivityController}", "onClosed() Card!");
        final OngoingActivityController ongoingActivityController = this.this$0;
        if (ongoingActivityController.delayedEntry.size() != 0) {
            ongoingActivityController.delayedEntry.forEach(new BiConsumer() { // from class: com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController$createCardController$1$onClosed$1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    String str = (String) obj;
                    Pair pair = (Pair) obj2;
                    if (((Boolean) pair.getSecond()).booleanValue()) {
                        OngoingActivityController.access$updateOngoingActivityData(OngoingActivityController.this, (NotificationEntry) pair.getFirst());
                        Log.d("{OngoingActivityController}", " onClosed: update delayedEntry  = ".concat(str));
                    } else {
                        OngoingActivityController.access$removeOngoingActivityData(OngoingActivityController.this, (NotificationEntry) pair.getFirst());
                        Log.d("{OngoingActivityController}", " onClosed: remove delayedEntry  = ".concat(str));
                    }
                }
            });
            ongoingActivityController.delayedEntry.clear();
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        LinkedList<OngoingActivityData> linkedList = new LinkedList();
        linkedList.addAll(OngoingActivityDataHelper.hiddenOngoingActivityDataList);
        boolean z = false;
        for (OngoingActivityData ongoingActivityData : linkedList) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            if (!OngoingActivityDataHelper.shouldHide(ongoingActivityController.userManager, ongoingActivityData)) {
                OngoingActivityDataHelper.hiddenOngoingActivityDataList.remove(ongoingActivityData);
                String str = ongoingActivityData.mNotiID;
                Iterator it = OngoingActivityDataHelper.mOngoingActivityLists.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((OngoingActivityData) it.next()).mNotiID.equals(str)) {
                            break;
                        }
                    } else {
                        OngoingActivityDataHelper.mOngoingActivityLists.addFirst(ongoingActivityData);
                        z = true;
                        break;
                    }
                }
            }
        }
        if (z) {
            OngoingActivityDataHelper.notifyUpdateObservers();
            OngoingActivityDataHelper.updateTopIndex();
        }
        OngoingChipAdapter ongoingChipAdapter = ongoingActivityController.mOngoingChipAdapter;
        if (ongoingChipAdapter != null) {
            ongoingChipAdapter.notifyDataSetChanged();
        }
        ongoingActivityController.updateParentViewVisibility(false);
        ongoingActivityController.startMarqueeAnimation();
        ongoingActivityController.mOngoingCardController = null;
        ongoingActivityController.isUpdateNotAllowed = false;
    }
}
