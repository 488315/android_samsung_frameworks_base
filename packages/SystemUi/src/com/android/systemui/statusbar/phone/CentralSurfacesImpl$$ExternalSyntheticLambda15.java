package com.android.systemui.statusbar.phone;

import android.R;
import android.app.RemoteInput;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda15 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CentralSurfacesImpl f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda15(CentralSurfacesImpl centralSurfacesImpl, ExpandableNotificationRow expandableNotificationRow, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = centralSurfacesImpl;
        this.f$1 = expandableNotificationRow;
        this.f$2 = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00b5 A[LOOP:0: B:22:0x007b->B:39:0x00b5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ab A[SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = this.f$1;
                String str = this.f$2;
                if (((StatusBarStateControllerImpl) centralSurfacesImpl.mStatusBarStateController).mState == 1) {
                    centralSurfacesImpl.mLockscreenShadeTransitionController.goToLockedShade(null, true);
                } else if (!((ShadeControllerImpl) centralSurfacesImpl.mShadeController).mExpandedVisible) {
                    centralSurfacesImpl.mCommandQueueCallbacks.animateExpandNotificationsPanel();
                }
                NotificationStackScrollLayout notificationStackScrollLayout = centralSurfacesImpl.mStackScroller;
                expandableNotificationRow.setUserExpanded(true, true);
                if (expandableNotificationRow.isChildInGroup()) {
                    ((GroupExpansionManagerImpl) expandableNotificationRow.mGroupExpansionManager).setGroupExpanded(expandableNotificationRow.mEntry, true);
                }
                expandableNotificationRow.notifyHeightChanged(false);
                if (notificationStackScrollLayout.mForcedScroll != expandableNotificationRow) {
                    notificationStackScrollLayout.mForcedScroll = expandableNotificationRow;
                    if (notificationStackScrollLayout.mAnimatedInsets) {
                        notificationStackScrollLayout.updateForcedScroll();
                    } else {
                        notificationStackScrollLayout.scrollTo(expandableNotificationRow);
                    }
                }
                centralSurfacesImpl.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda15(centralSurfacesImpl, expandableNotificationRow, str, i), 500L);
                break;
            default:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow2 = this.f$1;
                String str2 = this.f$2;
                centralSurfacesImpl2.getClass();
                ViewGroup viewGroup = (ViewGroup) expandableNotificationRow2.mPrivateLayout.mExpandedChild.findViewById(R.id.actions_container);
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = viewGroup.getChildAt(i2);
                    Object tag = childAt.getTag(R.id.showCustom);
                    RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                    if (remoteInputArr != null) {
                        RemoteInput remoteInput = null;
                        for (RemoteInput remoteInput2 : remoteInputArr) {
                            if (remoteInput2.getAllowFreeFormInput()) {
                                remoteInput = remoteInput2;
                            }
                        }
                        if (remoteInput != null) {
                            z = true;
                            if (!z) {
                                if (str2 != null) {
                                    expandableNotificationRow2.mEntry.remoteInputText = str2;
                                }
                                childAt.performClick();
                                break;
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
                break;
        }
    }
}
