package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetMediaData;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetNotificationControllerWrapper implements PluginNotificationController.Callback, FaceWidgetNotificationController {
    public Context mContext;
    public LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public C13851 mMediaDataListener;
    public PluginNotificationController mNotificationController;

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void expandToNotifications() {
        NotificationPanelViewController notificationPanelViewController;
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null || (notificationPanelViewController = lockscreenNotificationIconsOnlyController.mNPVController) == null) {
            return;
        }
        notificationPanelViewController.expandToNotifications();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getActiveNotificationSize() {
        return 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final List getAllNotifications() {
        return new ArrayList();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final String getEntryKey(int i) {
        return null;
    }

    public final PluginAODNotificationManager getNotificationManager() {
        PluginNotificationController pluginNotificationController = this.mNotificationController;
        if (pluginNotificationController != null) {
            return pluginNotificationController.getNotificationManager();
        }
        return null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final String getNotificationPackageName(int i) {
        return null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getNotificationUid(int i) {
        return 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataGravity() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return 17;
        }
        PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) lockscreenNotificationIconsOnlyController.mPluginLockData;
        if (pluginLockDataImpl.mData == null) {
            return -1;
        }
        return pluginLockDataImpl.isLandscape() ? pluginLockDataImpl.mData.getNotificationData().getIconOnlyData().getGravityLand().intValue() : pluginLockDataImpl.mData.getNotificationData().getIconOnlyData().getGravity().intValue();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataMarginTop() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            return ((PluginLockDataImpl) lockscreenNotificationIconsOnlyController.mPluginLockData).getTop(3);
        }
        return -1;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataPaddingEnd() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return 0;
        }
        PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) lockscreenNotificationIconsOnlyController.mPluginLockData;
        if (pluginLockDataImpl.mData == null) {
            return -1;
        }
        return pluginLockDataImpl.isLandscape() ? pluginLockDataImpl.mData.getNotificationData().getIconOnlyData().getPaddingEndLand().intValue() : pluginLockDataImpl.mData.getNotificationData().getIconOnlyData().getPaddingEnd().intValue();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataPaddingStart() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            return ((PluginLockDataImpl) lockscreenNotificationIconsOnlyController.mPluginLockData).getPaddingStart(3);
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final boolean isPluginLockDataAvailable() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            return ((PluginLockDataImpl) lockscreenNotificationIconsOnlyController.mPluginLockData).isAvailable();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final boolean isTransformAnimating() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            return lockscreenNotificationIconsOnlyController.mNotificationIconTransitionController.misTransformAnimating;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onClick() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = lockscreenNotificationIconsOnlyController.mStackScrollLayoutController;
        if (notificationStackScrollLayoutController == null) {
            return;
        }
        notificationStackScrollLayoutController.mProgressingShadeLockedFromNotiIcon = true;
        lockscreenNotificationIconsOnlyController.mShadeController.goToLockedShade(null, false);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onTouchEvent(int i) {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null || !lockscreenNotificationIconsOnlyController.mScreenOn) {
            return;
        }
        if (i == 1) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1005", "Tap icon only");
        } else {
            if (i != 2) {
                return;
            }
            lockscreenNotificationIconsOnlyController.mStackScrollLayoutController.mProgressingShadeLockedFromNotiIcon = true;
            lockscreenNotificationIconsOnlyController.mShadeController.goToLockedShade(null, false);
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "1005", "Swipe down icon only");
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void setNotificationIconsOnlyContainer() {
        this.mLockscreenNotificationIconsOnlyController.init();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper$1 */
    public final class C13851 implements MediaDataManager.Listener {
        public C13851() {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
            PluginFaceWidgetMediaData.PluginFaceWidgetMediaDeviceData pluginFaceWidgetMediaDeviceData;
            Context context;
            Drawable loadDrawable;
            Drawable mutate;
            Drawable.ConstantState constantState;
            Drawable.ConstantState constantState2;
            Drawable.ConstantState constantState3;
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = FaceWidgetNotificationControllerWrapper.this;
            if (faceWidgetNotificationControllerWrapper.mNotificationController != null) {
                StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("onMediaDataLoaded, ", str, ", ", str2, ", ");
                m87m.append(mediaData.toString());
                Log.d("FaceWidgetNotificationControllerWrapper", m87m.toString());
                PluginNotificationController pluginNotificationController = faceWidgetNotificationControllerWrapper.mNotificationController;
                ArrayList arrayList = new ArrayList();
                List<MediaAction> list = mediaData.actions;
                if (list != null && !list.isEmpty()) {
                    for (MediaAction mediaAction : list) {
                        Drawable drawable = mediaAction.icon;
                        arrayList.add(new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction((drawable == null || (constantState3 = drawable.mutate().getConstantState()) == null) ? null : constantState3.newDrawable(), mediaAction.action, mediaAction.contentDescription));
                    }
                }
                MediaDeviceData mediaDeviceData = mediaData.device;
                if (mediaDeviceData != null) {
                    Integer num = -1;
                    Drawable drawable2 = mediaDeviceData.icon;
                    Drawable newDrawable = (drawable2 == null || (constantState2 = drawable2.mutate().getConstantState()) == null) ? null : constantState2.newDrawable();
                    CharSequence charSequence = mediaDeviceData.name;
                    pluginFaceWidgetMediaDeviceData = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaDeviceData(mediaDeviceData.enabled, newDrawable, charSequence == null ? "" : charSequence.toString(), num != null ? num.intValue() : -1);
                } else {
                    pluginFaceWidgetMediaDeviceData = null;
                }
                Integer valueOf = Integer.valueOf(mediaData.userId);
                int intValue = valueOf == null ? 0 : valueOf.intValue();
                boolean z3 = mediaData.initialized;
                Integer num2 = 0;
                int intValue2 = num2 == null ? 0 : num2.intValue();
                Integer num3 = 0;
                int intValue3 = num3 != null ? num3.intValue() : 0;
                String str3 = mediaData.app;
                Icon icon = mediaData.appIcon;
                pluginNotificationController.onMediaDataLoaded(str, str2, new PluginFaceWidgetMediaData(intValue, z3, intValue2, intValue3, str3, (icon == null || (context = faceWidgetNotificationControllerWrapper.mContext) == null || (loadDrawable = icon.loadDrawable(context)) == null || (mutate = loadDrawable.mutate()) == null || (constantState = mutate.getConstantState()) == null) ? null : constantState.newDrawable(), mediaData.artist, mediaData.song, mediaData.artwork, arrayList, mediaData.actionsToShowInCompact, mediaData.packageName, mediaData.token, mediaData.clickIntent, pluginFaceWidgetMediaDeviceData, mediaData.active, mediaData.resumeAction, mediaData.resumption, mediaData.notificationKey, mediaData.hasCheckedForResume));
            }
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str) {
            AbstractC0000x2c234b15.m3m("onMediaDataRemoved, ", str, "FaceWidgetNotificationControllerWrapper");
            PluginNotificationController pluginNotificationController = FaceWidgetNotificationControllerWrapper.this.mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.onMediaDataRemoved(str);
            }
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }
    }
}
