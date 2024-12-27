package com.android.systemui.statusbar.notification;

import android.app.ActivityManager;
import android.util.Log;
import java.util.ArrayList;

public final /* synthetic */ class SubscreenNotificationListAdapter$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SubscreenNotificationListAdapter$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((SubscreenNotificationListAdapter) obj).mFooterViewHolder.mClearAllLayout.setVisibility(8);
                break;
            case 1:
                ((SubscreenNotificationListAdapter) obj).mFooterViewHolder.mClearAllLayout.setVisibility(0);
                break;
            default:
                final SubscreenNotificationInfoManager subscreenNotificationInfoManager = SubscreenNotificationListAdapter.this.mNotificationInfoManager;
                subscreenNotificationInfoManager.getClass();
                if (SubscreenNotificationInfoManager.checkRemoveNotification()) {
                    Log.d("SubscreenNotificationInfoManager", "clearAllSubscreenNotificaitonInfo()");
                    ArrayList arrayList = new ArrayList();
                    int subscreenNotificationInfoListSize = SubscreenNotificationInfoManager.getSubscreenNotificationInfoListSize();
                    for (int i2 = 0; i2 < subscreenNotificationInfoListSize; i2++) {
                        SubscreenNotificationInfo subscreenNotificationInfo = (SubscreenNotificationInfo) SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.get(i2);
                        if (SubscreenNotificationInfoManager.canViewBeCleared(subscreenNotificationInfo.mRow)) {
                            arrayList.add(subscreenNotificationInfo);
                            SubscreenNotificationInfoManager.setEntryDismissState(subscreenNotificationInfo.mRow.mEntry);
                        }
                    }
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        SubscreenNotificationInfoManager.mSubscreenNotificationInfoList.remove(arrayList.get(i3));
                    }
                    SubscreenNotificationListAdapter subscreenNotificationListAdapter = subscreenNotificationInfoManager.mNotificationListAdapter;
                    subscreenNotificationListAdapter.notifyDataSetChanged();
                    for (int size = subscreenNotificationInfoManager.mRecyclerViewItemHolderArray.size() - 1; size >= 0; size--) {
                        if (SubscreenNotificationInfoManager.canViewBeCleared(((SubscreenParentItemViewHolder) subscreenNotificationInfoManager.mRecyclerViewItemHolderArray.get(size)).mInfo.mRow)) {
                            subscreenNotificationInfoManager.mRecyclerViewItemHolderArray.remove(size);
                        }
                    }
                    subscreenNotificationInfoManager.mUiHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfoManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SubscreenNotificationInfoManager subscreenNotificationInfoManager2 = SubscreenNotificationInfoManager.this;
                            subscreenNotificationInfoManager2.getClass();
                            subscreenNotificationInfoManager2.mNotifCollection.dismissAllNotifications(ActivityManager.getCurrentUser(), false);
                        }
                    });
                    subscreenNotificationListAdapter.mSubRoomNotification.notifyClockSubRoomRequest();
                    break;
                }
                break;
        }
    }
}
