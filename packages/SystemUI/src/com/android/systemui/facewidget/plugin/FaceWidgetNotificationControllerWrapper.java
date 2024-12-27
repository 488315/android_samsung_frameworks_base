package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarCallBack;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetMediaData;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.iconsOnly.LockscreenNotificationIconsOnlyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper$nowbarWatcher$1;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

public final class FaceWidgetNotificationControllerWrapper implements PluginNotificationController.Callback, FaceWidgetNotificationController {
    public final ArrayList mBriefCallbacks = new ArrayList();
    public Context mContext;
    public LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public AnonymousClass1 mMediaDataListener;
    public final NotifCollection mNotifCollection;
    public PluginNotificationController mNotificationController;
    public OngoingActivityDataHelper$nowbarWatcher$1 mNowbarWatcher;
    public Lazy mPanelViewControllerLazy;

    public FaceWidgetNotificationControllerWrapper(NotifCollection notifCollection) {
        this.mNotifCollection = notifCollection;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void dismissOngoingActivityNotification(String str) {
        this.mNotifCollection.dismissOngoingActivityNotification(str);
    }

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
    public final ArrayList getNowBarItemList() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        LinkedList<OngoingActivityData> linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
        Log.i(OngoingActivityDataHelper.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(linkedList.size(), "getNowBarItemList size = "));
        ArrayList arrayList = new ArrayList();
        for (OngoingActivityData ongoingActivityData : linkedList) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            arrayList.add(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityData));
        }
        return arrayList;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final View getNowBarRootView() {
        View view = ((NotificationPanelViewController) this.mPanelViewControllerLazy.get()).mNowBarContainer;
        if (view != null) {
            return view;
        }
        Log.e("NotificationPanelView", "provideFaceWidgetNowBarContainer nowbarRootView is null");
        return null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataGravity() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return 17;
        }
        LockStarValues lockStarValues = lockscreenNotificationIconsOnlyController.mPluginLockStarManager.getLockStarValues();
        return lockStarValues != null ? lockStarValues.getNotificationIconGravity() : lockscreenNotificationIconsOnlyController.mPluginLockData.getGravity(3);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataMarginTop() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return -1;
        }
        LockStarValues lockStarValues = lockscreenNotificationIconsOnlyController.mPluginLockStarManager.getLockStarValues();
        return lockStarValues != null ? lockStarValues.getNotificationTopMargin() : lockscreenNotificationIconsOnlyController.mPluginLockData.getTop(3);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataPaddingEnd() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return 0;
        }
        LockStarValues lockStarValues = lockscreenNotificationIconsOnlyController.mPluginLockStarManager.getLockStarValues();
        return lockStarValues != null ? lockStarValues.getNotificationIconPaddingEnd() : lockscreenNotificationIconsOnlyController.mPluginLockData.getPaddingEnd(3);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final int getPluginLockDataPaddingStart() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null) {
            return 0;
        }
        LockStarValues lockStarValues = lockscreenNotificationIconsOnlyController.mPluginLockStarManager.getLockStarValues();
        return lockStarValues != null ? lockStarValues.getNotificationIconPaddingStart() : lockscreenNotificationIconsOnlyController.mPluginLockData.getPaddingStart(3);
    }

    public final void initPlugin(PluginNotificationController pluginNotificationController, Context context) {
        this.mNotificationController = pluginNotificationController;
        if (pluginNotificationController != null) {
            pluginNotificationController.init(null);
        }
        this.mContext = context;
        if (pluginNotificationController != null) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            ((ArrayList) OngoingActivityDataHelper.nowbarObservers).add(this);
            this.mNowbarWatcher = OngoingActivityDataHelper.nowbarWatcher;
            return;
        }
        OngoingActivityDataHelper.INSTANCE.getClass();
        ((ArrayList) OngoingActivityDataHelper.nowbarObservers).remove(this);
        OngoingActivityDataHelper$nowbarWatcher$1 ongoingActivityDataHelper$nowbarWatcher$1 = this.mNowbarWatcher;
        if (ongoingActivityDataHelper$nowbarWatcher$1 == null || !ongoingActivityDataHelper$nowbarWatcher$1.equals(OngoingActivityDataHelper.nowbarWatcher)) {
            return;
        }
        this.mNowbarWatcher = null;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final boolean isPluginLockDataAvailable() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            return lockscreenNotificationIconsOnlyController.mPluginLockStarManager.isLockStarEnabled();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final boolean isTransformAnimating() {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController != null) {
            lockscreenNotificationIconsOnlyController.getClass();
        }
        return false;
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onClick() {
        this.mLockscreenNotificationIconsOnlyController.mShadeController.goToLockedShade(null, false);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onExternalExpandNowbarCardAttached(NowBarItem nowBarItem, boolean z) {
        Log.i("FaceWidgetNotificationControllerWrapper", "onExternalExpandNowbarCardAttached targetItem = " + nowBarItem + " attached + " + z);
        if (nowBarItem == null || nowBarItem.getNowBarViewStyle() != 7) {
            return;
        }
        synchronized (this.mBriefCallbacks) {
            for (int i = 0; i < this.mBriefCallbacks.size(); i++) {
                try {
                    BriefNowBarCallBack briefNowBarCallBack = (BriefNowBarCallBack) this.mBriefCallbacks.get(i);
                    if (briefNowBarCallBack != null) {
                        briefNowBarCallBack.onFullScreenShowingChanged(z);
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onTopNowBarItemChangedFromNowbar(String str) {
        StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("onTopNowBarItemChangedFromNowbar sbnId = ", str, " NowbarWatcher + ");
        m.append(this.mNowbarWatcher);
        Log.i("FaceWidgetNotificationControllerWrapper", m.toString());
        if (this.mNowbarWatcher != null) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            LinkedList linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
            if (linkedList.size() > 1) {
                Collections.rotate(linkedList, -1);
                if (!Intrinsics.areEqual(((OngoingActivityData) linkedList.get(0)).mNotiID, str)) {
                    Log.e(OngoingActivityDataHelper.TAG, MotionLayout$$ExternalSyntheticOutline0.m("rotateItemToFirst fail. req id:", str, ", fixed top mNodID:", ((OngoingActivityData) linkedList.get(0)).mNotiID, " "));
                }
                OngoingActivityDataHelper.notifyUpdateObservers();
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onTouchEvent(int i) {
        LockscreenNotificationIconsOnlyController lockscreenNotificationIconsOnlyController = this.mLockscreenNotificationIconsOnlyController;
        if (lockscreenNotificationIconsOnlyController == null || !lockscreenNotificationIconsOnlyController.mScreenOn) {
            return;
        }
        if (i == 1) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, SystemUIAnalytics.DID_TAP_ICON_ONLY);
        } else {
            if (i != 2) {
                return;
            }
            lockscreenNotificationIconsOnlyController.mShadeController.goToLockedShade(null, false);
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, SystemUIAnalytics.DID_DRAG_ICON_ONLY);
        }
    }

    public final void removeItem(NowBarItem nowBarItem) {
        try {
            PluginNotificationController pluginNotificationController = this.mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.onNowBarItemRemoved(nowBarItem);
            }
        } catch (Exception e) {
            Log.e("FaceWidgetNotificationControllerWrapper", "removeItem " + nowBarItem + " / " + e);
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void setNotificationIconsOnlyContainer() {
        FaceWidgetNotificationController faceWidgetNotificationController = this.mLockscreenNotificationIconsOnlyController.mFaceWidgetNotificationController;
        if (faceWidgetNotificationController != null) {
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) faceWidgetNotificationController;
            if (faceWidgetNotificationControllerWrapper.getNotificationManager() != null) {
                faceWidgetNotificationControllerWrapper.getNotificationManager().setTagId(R.id.tag_fresh_drawable, R.id.tag_shows_conversation);
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void swapLastItemToFirst() {
        OngoingActivityDataHelper.INSTANCE.getClass();
        LinkedList linkedList = OngoingActivityDataHelper.mOngoingActivityLists;
        if (linkedList.size() > 1) {
            Collections.rotate(linkedList, -1);
        }
    }

    public final void updateItem(NowBarItem nowBarItem) {
        try {
            PluginNotificationController pluginNotificationController = this.mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.onNowBarItemUpdated(nowBarItem);
            }
        } catch (Exception e) {
            Log.e("FaceWidgetNotificationControllerWrapper", "updateItem " + nowBarItem + " / " + e);
        }
    }

    /* renamed from: com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper$1, reason: invalid class name */
    public final class AnonymousClass1 implements MediaDataManager.Listener {
        public AnonymousClass1() {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
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
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onMediaDataLoaded, ", str, ", ", str2, ", ");
                m.append(mediaData.toString());
                Log.d("FaceWidgetNotificationControllerWrapper", m.toString());
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
                    Drawable drawable2 = mediaDeviceData.icon;
                    Drawable newDrawable = (drawable2 == null || (constantState2 = drawable2.mutate().getConstantState()) == null) ? null : constantState2.newDrawable();
                    CharSequence charSequence = mediaDeviceData.name;
                    pluginFaceWidgetMediaDeviceData = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaDeviceData(mediaDeviceData.enabled, newDrawable, charSequence == null ? "" : charSequence.toString(), -1);
                } else {
                    pluginFaceWidgetMediaDeviceData = null;
                }
                Icon icon = mediaData.appIcon;
                pluginNotificationController.onMediaDataLoaded(str, str2, new PluginFaceWidgetMediaData(mediaData.userId, mediaData.initialized, 0, 0, mediaData.app, (icon == null || (context = faceWidgetNotificationControllerWrapper.mContext) == null || (loadDrawable = icon.loadDrawable(context)) == null || (mutate = loadDrawable.mutate()) == null || (constantState = mutate.getConstantState()) == null) ? null : constantState.newDrawable(), mediaData.artist, mediaData.song, mediaData.artwork, arrayList, mediaData.actionsToShowInCompact, mediaData.packageName, mediaData.token, mediaData.clickIntent, pluginFaceWidgetMediaDeviceData, mediaData.active, mediaData.resumeAction, mediaData.resumption, mediaData.notificationKey, mediaData.hasCheckedForResume, Boolean.TRUE.equals(mediaData.isPlaying)));
            }
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str, boolean z) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onMediaDataRemoved, ", str, "FaceWidgetNotificationControllerWrapper");
            PluginNotificationController pluginNotificationController = FaceWidgetNotificationControllerWrapper.this.mNotificationController;
            if (pluginNotificationController != null) {
                pluginNotificationController.onMediaDataRemoved(str);
            }
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }
    }
}
