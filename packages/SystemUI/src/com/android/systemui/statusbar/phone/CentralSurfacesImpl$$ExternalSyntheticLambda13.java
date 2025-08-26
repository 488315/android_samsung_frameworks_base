package com.android.systemui.statusbar.phone;

import android.R;
import android.app.RemoteInput;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* loaded from: classes3.dex */
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
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                CentralSurfacesImpl centralSurfacesImpl = this.f$0;
                ExpandableNotificationRow expandableNotificationRow = this.f$1;
                String str = this.f$2;
                if (centralSurfacesImpl.mStatusBarStateController.getState() == 1) {
                    centralSurfacesImpl.mLockscreenShadeTransitionController.goToLockedShade(null, true);
                } else if (!centralSurfacesImpl.mShadeController.isExpandedVisible()) {
                    centralSurfacesImpl.mCommandQueueCallbacks.animateExpandNotificationsPanel();
                }
                expandableNotificationRow.setUserExpanded(true, true);
                if (expandableNotificationRow.isChildInGroup()) {
                    int i2 = NotificationBundleUi.$r8$clinit;
                    ((GroupExpansionManagerImpl) expandableNotificationRow.mGroupExpansionManager).setGroupExpanded(expandableNotificationRow.getEntryLegacy(), true);
                }
                expandableNotificationRow.notifyHeightChanged(false);
                NotificationStackScrollLayout notificationStackScrollLayout = centralSurfacesImpl.mStackScroller;
                if (notificationStackScrollLayout.mForcedScroll != expandableNotificationRow) {
                    int i3 = SceneContainerFlag.$r8$clinit;
                    notificationStackScrollLayout.mForcedScroll = expandableNotificationRow;
                    notificationStackScrollLayout.updateForcedScroll();
                }
                centralSurfacesImpl.mMainHandler.postDelayed(new CentralSurfacesImpl$$ExternalSyntheticLambda13(centralSurfacesImpl, expandableNotificationRow, str, i), 500L);
                break;
            default:
                CentralSurfacesImpl centralSurfacesImpl2 = this.f$0;
                ExpandableNotificationRow expandableNotificationRow2 = this.f$1;
                String str2 = this.f$2;
                UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                centralSurfacesImpl2.getClass();
                ViewGroup viewGroup = (ViewGroup) expandableNotificationRow2.mPrivateLayout.mExpandedChild.findViewById(R.id.animation);
                int childCount = viewGroup.getChildCount();
                for (int i4 = 0; i4 < childCount; i4++) {
                    View childAt = viewGroup.getChildAt(i4);
                    Object tag = childAt.getTag(R.id.status);
                    RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                    if (remoteInputArr != null) {
                        RemoteInput remoteInput = null;
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
