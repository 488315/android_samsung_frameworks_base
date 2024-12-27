package com.android.systemui.statusbar.phone;

import android.R;
import android.app.RemoteInput;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda13(CentralSurfacesImpl centralSurfacesImpl, ExpandableNotificationRow expandableNotificationRow, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
        this.f$1 = expandableNotificationRow;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = this.f$1;
                String str = this.f$2;
                centralSurfacesImpl.animateExpandNotificationsPanel(null, false);
                expandableNotificationRow.setUserExpanded(true, true);
                if (expandableNotificationRow.isChildInGroup()) {
                    ((GroupExpansionManagerImpl) expandableNotificationRow.mGroupExpansionManager).setGroupExpanded(expandableNotificationRow.mEntry, true);
                }
                expandableNotificationRow.notifyHeightChanged(false);
                NotificationStackScrollLayout notificationStackScrollLayout = centralSurfacesImpl.mStackScroller;
                if (notificationStackScrollLayout.mForcedScroll != expandableNotificationRow) {
                    notificationStackScrollLayout.mForcedScroll = expandableNotificationRow;
                    notificationStackScrollLayout.updateForcedScroll();
                }
                centralSurfacesImpl.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl, expandableNotificationRow, str, 1), 500L);
                break;
            default:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow2 = this.f$1;
                String str2 = this.f$2;
                centralSurfacesImpl2.getClass();
                ViewGroup viewGroup = (ViewGroup) expandableNotificationRow2.mPrivateLayout.mExpandedChild.findViewById(R.id.actions_container_layout);
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = viewGroup.getChildAt(i);
                    Object tag = childAt.getTag(R.id.simple);
                    RemoteInput remoteInput = null;
                    RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                    if (remoteInputArr != null) {
                        for (RemoteInput remoteInput2 : remoteInputArr) {
                            if (remoteInput2.getAllowFreeFormInput()) {
                                remoteInput = remoteInput2;
                            }
                        }
                        if (remoteInput != null) {
                            if (str2 != null) {
                                expandableNotificationRow2.mEntry.remoteInputText = str2;
                            }
                            childAt.performClick();
                            break;
                        }
                    }
                }
                break;
        }
    }
}
