package com.android.systemui.qs;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.permission.PermissionManager;
import android.safetycenter.SafetyCenterManager;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.shade.ShadeHeaderController$chipVisibilityListener$1;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeaderPrivacyIconsController {
    public final ActivityStarter activityStarter;
    public final HeaderPrivacyIconsController$attachStateChangeListener$1 attachStateChangeListener;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final String cameraSlot;
    public ShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final HeaderPrivacyIconsController$desktopCallback$1 desktopCallback = new DesktopManager.Callback() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$desktopCallback$1
        @Override // com.android.systemui.util.DesktopManager.Callback
        public final void onPrivacyItemStateRequested() {
            if (HeaderPrivacyIconsController.this.privacyChip.getVisibility() == 0) {
                ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).notifyPrivacyItemsChanged(true);
            } else {
                ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).notifyPrivacyItemsChanged(false);
            }
        }
    };
    public final DeviceProvisionedController deviceProvisionedController;
    public final StatusIconContainer iconContainer;
    public boolean listening;
    public boolean locationIndicatorsEnabled;
    public final String locationSlot;
    public boolean micCameraIndicatorsEnabled;
    public final String micSlot;
    public final HeaderPrivacyIconsController$panelEventReceiver$1 panelEventReceiver;
    public final PrivacyItemController.Callback picCallback;
    public final OngoingPrivacyChip privacyChip;
    public boolean privacyChipLogged;
    public final PrivacyDialogController privacyDialogController;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public final SafetyCenterManager safetyCenterManager;
    public final HeaderPrivacyIconsController$safetyCenterReceiver$1 safetyCenterReceiver;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.qs.HeaderPrivacyIconsController$attachStateChangeListener$1] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.qs.HeaderPrivacyIconsController$desktopCallback$1] */
    /* JADX WARN: Type inference failed for: r3v9, types: [android.content.BroadcastReceiver, com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.qs.HeaderPrivacyIconsController$panelEventReceiver$1] */
    public HeaderPrivacyIconsController(PrivacyItemController privacyItemController, UiEventLogger uiEventLogger, OngoingPrivacyChip ongoingPrivacyChip, PrivacyDialogController privacyDialogController, PrivacyLogger privacyLogger, StatusIconContainer statusIconContainer, PermissionManager permissionManager, Executor executor, Executor executor2, ActivityStarter activityStarter, AppOpsController appOpsController, BroadcastDispatcher broadcastDispatcher, SafetyCenterManager safetyCenterManager, DeviceProvisionedController deviceProvisionedController) {
        this.privacyItemController = privacyItemController;
        this.uiEventLogger = uiEventLogger;
        this.privacyChip = ongoingPrivacyChip;
        this.privacyDialogController = privacyDialogController;
        this.privacyLogger = privacyLogger;
        this.iconContainer = statusIconContainer;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.activityStarter = activityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.safetyCenterManager = safetyCenterManager;
        this.deviceProvisionedController = deviceProvisionedController;
        this.cameraSlot = ongoingPrivacyChip.getResources().getString(17042912);
        this.micSlot = ongoingPrivacyChip.getResources().getString(17042929);
        this.locationSlot = ongoingPrivacyChip.getResources().getString(17042927);
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.safetyCenterManager.isSafetyCenterEnabled();
                headerPrivacyIconsController.getClass();
            }
        };
        this.safetyCenterReceiver = r3;
        this.panelEventReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$panelEventReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Dialog dialog;
                if (!"com.samsung.systemui.statusbar.COLLAPSED".equals(intent.getAction()) || (dialog = HeaderPrivacyIconsController.this.privacyDialogController.dialog) == null) {
                    return;
                }
                dialog.dismiss();
            }
        };
        ?? r10 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$attachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                BroadcastDispatcher.registerReceiver$default(headerPrivacyIconsController.broadcastDispatcher, headerPrivacyIconsController.safetyCenterReceiver, new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), HeaderPrivacyIconsController.this.backgroundExecutor, null, 0, null, 56);
                HeaderPrivacyIconsController headerPrivacyIconsController2 = HeaderPrivacyIconsController.this;
                BroadcastDispatcher.registerReceiver$default(headerPrivacyIconsController2.broadcastDispatcher, headerPrivacyIconsController2.panelEventReceiver, new IntentFilter("com.samsung.systemui.statusbar.COLLAPSED"), HeaderPrivacyIconsController.this.backgroundExecutor, null, 0, null, 56);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.broadcastDispatcher.unregisterReceiver(headerPrivacyIconsController.safetyCenterReceiver);
                HeaderPrivacyIconsController headerPrivacyIconsController2 = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController2.broadcastDispatcher.unregisterReceiver(headerPrivacyIconsController2.panelEventReceiver);
            }
        };
        this.attachStateChangeListener = r10;
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController.1
            @Override // java.lang.Runnable
            public final void run() {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.safetyCenterManager.isSafetyCenterEnabled();
                headerPrivacyIconsController.getClass();
            }
        });
        if (ongoingPrivacyChip.isAttachedToWindow()) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r3, new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), executor, null, 0, null, 56);
        }
        ongoingPrivacyChip.addOnAttachStateChangeListener(r10);
        this.picCallback = new PrivacyItemController.Callback() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$picCallback$1
            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagLocationChanged(boolean z) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                if (headerPrivacyIconsController.locationIndicatorsEnabled != z) {
                    headerPrivacyIconsController.locationIndicatorsEnabled = z;
                    headerPrivacyIconsController.updatePrivacyIconSlots();
                    headerPrivacyIconsController.setChipVisibility(!headerPrivacyIconsController.privacyChip.privacyList.isEmpty());
                }
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public final void onFlagMicCameraChanged(boolean z) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                if (headerPrivacyIconsController.micCameraIndicatorsEnabled != z) {
                    headerPrivacyIconsController.micCameraIndicatorsEnabled = z;
                    headerPrivacyIconsController.updatePrivacyIconSlots();
                    headerPrivacyIconsController.setChipVisibility(!headerPrivacyIconsController.privacyChip.privacyList.isEmpty());
                }
            }

            @Override // com.android.systemui.privacy.PrivacyItemController.Callback
            public final void onPrivacyItemsChanged(List list) {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.privacyChip.setPrivacyList(list);
                headerPrivacyIconsController.setChipVisibility(!list.isEmpty());
                Dialog dialog = headerPrivacyIconsController.privacyDialogController.dialog;
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setChipVisibility(boolean z) {
        OngoingPrivacyChip ongoingPrivacyChip;
        ShadeHeaderController$chipVisibilityListener$1 shadeHeaderController$chipVisibilityListener$1;
        PrivacyLogger privacyLogger = this.privacyLogger;
        if (z) {
            if (this.micCameraIndicatorsEnabled || this.locationIndicatorsEnabled) {
                privacyLogger.logChipVisible(true);
                if (!this.privacyChipLogged && this.listening) {
                    this.privacyChipLogged = true;
                    this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_VIEW);
                }
                ongoingPrivacyChip = this.privacyChip;
                if ((ongoingPrivacyChip.getVisibility() == 0) != z) {
                    ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).notifyPrivacyItemsChanged(z);
                }
                ongoingPrivacyChip.setVisibility(z ? 0 : 8);
                shadeHeaderController$chipVisibilityListener$1 = this.chipVisibilityListener;
                if (shadeHeaderController$chipVisibilityListener$1 == null) {
                    shadeHeaderController$chipVisibilityListener$1.onChipVisibilityRefreshed(z);
                    return;
                }
                return;
            }
        }
        privacyLogger.logChipVisible(false);
        ongoingPrivacyChip = this.privacyChip;
        if ((ongoingPrivacyChip.getVisibility() == 0) != z) {
        }
        ongoingPrivacyChip.setVisibility(z ? 0 : 8);
        shadeHeaderController$chipVisibilityListener$1 = this.chipVisibilityListener;
        if (shadeHeaderController$chipVisibilityListener$1 == null) {
        }
    }

    public final void updatePrivacyIconSlots() {
        boolean z = this.micCameraIndicatorsEnabled;
        boolean z2 = z || this.locationIndicatorsEnabled;
        String str = this.locationSlot;
        String str2 = this.micSlot;
        String str3 = this.cameraSlot;
        StatusIconContainer statusIconContainer = this.iconContainer;
        if (!z2) {
            statusIconContainer.removeIgnoredSlot(str3);
            statusIconContainer.removeIgnoredSlot(str2);
            statusIconContainer.removeIgnoredSlot(str);
            return;
        }
        if (z) {
            statusIconContainer.addIgnoredSlot(str3);
            statusIconContainer.addIgnoredSlot(str2);
        } else {
            statusIconContainer.removeIgnoredSlot(str3);
            statusIconContainer.removeIgnoredSlot(str2);
        }
        if (this.locationIndicatorsEnabled) {
            statusIconContainer.addIgnoredSlot(str);
        } else {
            statusIconContainer.removeIgnoredSlot(str);
        }
    }
}
