package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@CoordinatorScope
public final class SubscreenNotificationListCoordinator implements Coordinator {
    public static final int $stable = 8;
    private final SubscreenNotificationListCoordinator$bubbleFilter$1 bubbleFilter;
    private final Optional<Bubbles> bubblesOptional;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final SubscreenNotificationListCoordinator$pluginLockListener$1 pluginLockListener;
    private final PluginLockMediator pluginLockMediator;
    private int pluginLockMode;
    private final SysuiStatusBarStateController statusBarStateController;
    private final SubscreenNotificationController subscreenController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.pluginlock.listener.PluginLockListener$State, com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$pluginLockListener$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$bubbleFilter$1] */
    public SubscreenNotificationListCoordinator(SubscreenNotificationController subscreenNotificationController, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, PluginLockMediator pluginLockMediator, Optional<Bubbles> optional) {
        this.subscreenController = subscreenNotificationController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.pluginLockMediator = pluginLockMediator;
        this.bubblesOptional = optional;
        ?? r1 = new PluginLockListener.State() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$pluginLockListener$1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public void onViewModeChanged(int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onViewModeChanged mode: ", "SubscreenNotificationListCoordinator");
                SubscreenNotificationListCoordinator.this.pluginLockMode = i;
            }
        };
        this.pluginLockListener = r1;
        pluginLockMediator.registerStateCallback(r1);
        this.bubbleFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$bubbleFilter$1
            {
                super("SubscreenNotificationListCoordinator");
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                Optional optional2;
                Optional optional3;
                SubscreenNotificationController subscreenNotificationController2;
                optional2 = SubscreenNotificationListCoordinator.this.bubblesOptional;
                if (!optional2.isPresent()) {
                    return false;
                }
                optional3 = SubscreenNotificationListCoordinator.this.bubblesOptional;
                Bubbles bubbles = (Bubbles) optional3.get();
                String groupKey = notificationEntry.mSbn.getGroupKey();
                String str = notificationEntry.mKey;
                if (!((BubbleController.BubblesImpl) bubbles).isBubbleNotificationSuppressedFromShade(str, groupKey)) {
                    return false;
                }
                subscreenNotificationController2 = SubscreenNotificationListCoordinator.this.subscreenController;
                SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController2.mDeviceModel;
                if (subscreenDeviceModelParent == null || !subscreenDeviceModelParent.mIsFolded || !notificationEntry.mRanking.canBubble()) {
                    return false;
                }
                NotificationEntry notificationEntry2 = subscreenDeviceModelParent.mBubbleReplyEntry;
                if (notificationEntry2 != null && str.equals(notificationEntry2.mKey)) {
                    Log.d("S.S.N.", "shouldFilterOutBubble parent - mBubbleReplyEntry key :".concat(str));
                    subscreenDeviceModelParent.mBubbleReplyEntry = null;
                    return false;
                }
                if (((SubscreenDeviceModelParent.MainListHashMapItem) subscreenDeviceModelParent.mMainListArrayHashMap.get(str)) == null) {
                    return false;
                }
                Log.d("S.S.N.", "shouldFilterOutBubble parent - remove Bubble Item :" + str);
                subscreenDeviceModelParent.notifyListAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.notifyGroupAdapterItemRemoved(notificationEntry);
                subscreenDeviceModelParent.removeMainHashItem(notificationEntry);
                return false;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x037e, code lost:
    
        if (r3 != false) goto L199;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:339:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x051e A[LOOP:13: B:362:0x0518->B:364:0x051e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:368:0x052e  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x05b2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAfterRenderList(java.util.List<? extends com.android.systemui.statusbar.notification.collection.ListEntry> r21, com.android.systemui.statusbar.notification.collection.render.NotifStackController r22) {
        /*
            Method dump skipped, instructions count: 2080
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator.onAfterRenderList(java.util.List, com.android.systemui.statusbar.notification.collection.render.NotifStackController):void");
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.bubbleFilter);
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SubscreenNotificationListCoordinator$attach$1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List<? extends ListEntry> list, NotifStackController notifStackController) {
                SubscreenNotificationListCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
    }
}
