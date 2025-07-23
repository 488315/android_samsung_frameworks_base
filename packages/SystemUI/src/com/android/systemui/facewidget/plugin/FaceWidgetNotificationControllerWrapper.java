package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.aibrief.control.BriefNowBarCallBack;
import com.android.systemui.facewidget.FaceWidgetNotificationController;
import com.android.systemui.media.controls.domain.pipeline.MediaActionsKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.keyguardstatusview.NowBarItem;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetMediaData;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetNotificationControllerWrapper implements PluginNotificationController.Callback, FaceWidgetNotificationController {
    public final ActivityStarter mActivityStarter;
    public final ArrayList mBriefCallbacks = new ArrayList();
    public Context mContext;
    public LockscreenNotificationIconsOnlyController mLockscreenNotificationIconsOnlyController;
    public AnonymousClass1 mMediaDataListener;
    public final NotifCollection mNotifCollection;
    public PluginNotificationController mNotificationController;
    public OngoingActivityDataHelper$nowbarWatcher$1 mNowbarWatcher;
    public Lazy mPanelViewControllerLazy;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.facewidget.plugin.FaceWidgetNotificationControllerWrapper$1, reason: invalid class name */
    public class AnonymousClass1 implements MediaDataManager.Listener {
        public AnonymousClass1() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v1, types: [java.util.List] */
        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z) {
            boolean z2;
            PluginFaceWidgetMediaData.PluginFaceWidgetMediaDeviceData pluginFaceWidgetMediaDeviceData;
            Context context;
            Drawable loadDrawable;
            Drawable mutate;
            Drawable.ConstantState constantState;
            FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = FaceWidgetNotificationControllerWrapper.this;
            if (faceWidgetNotificationControllerWrapper.mNotificationController != null) {
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("onMediaDataLoaded, ", str, ", ", str2, ", ");
                m.append(mediaData.toString());
                Log.d("FaceWidgetNotificationControllerWrapper", m.toString());
                PluginNotificationController pluginNotificationController = faceWidgetNotificationControllerWrapper.mNotificationController;
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                MediaButton mediaButton = mediaData.semanticActions;
                if (mediaButton != null) {
                    MediaAction mediaAction = mediaButton.custom0;
                    if (mediaAction != null) {
                        arrayList.add(new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction.icon), mediaAction.action, mediaAction.contentDescription));
                    }
                    MediaAction mediaAction2 = mediaButton.prevOrCustom;
                    if (mediaAction2 != null) {
                        PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction pluginFaceWidgetMediaAction = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction2.icon), mediaAction2.action, mediaAction2.contentDescription);
                        arrayList.add(pluginFaceWidgetMediaAction);
                        arrayList2.add(Integer.valueOf(arrayList.indexOf(pluginFaceWidgetMediaAction)));
                    }
                    MediaAction mediaAction3 = mediaButton.playOrPause;
                    if (mediaAction3 != null) {
                        PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction pluginFaceWidgetMediaAction2 = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction3.icon), mediaAction3.action, mediaAction3.contentDescription);
                        arrayList.add(pluginFaceWidgetMediaAction2);
                        arrayList2.add(Integer.valueOf(arrayList.indexOf(pluginFaceWidgetMediaAction2)));
                    }
                    MediaAction mediaAction4 = mediaButton.nextOrCustom;
                    if (mediaAction4 != null) {
                        PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction pluginFaceWidgetMediaAction3 = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction4.icon), mediaAction4.action, mediaAction4.contentDescription);
                        arrayList.add(pluginFaceWidgetMediaAction3);
                        arrayList2.add(Integer.valueOf(arrayList.indexOf(pluginFaceWidgetMediaAction3)));
                    }
                    MediaAction mediaAction5 = mediaButton.custom1;
                    if (mediaAction5 != null) {
                        arrayList.add(new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction5.icon), mediaAction5.action, mediaAction5.contentDescription));
                    }
                    Log.d("FaceWidgetNotificationControllerWrapper", "getPluginFaceWidgetMediaData, actionsToShowInCompact = " + arrayList2);
                    z2 = true;
                } else {
                    ArrayList arrayList3 = (ArrayList) MediaActionsKt.getNotificationActions(mediaData.actions, faceWidgetNotificationControllerWrapper.mActivityStarter);
                    if (!arrayList3.isEmpty()) {
                        int size = arrayList3.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList3.get(i);
                            i++;
                            MediaAction mediaAction6 = (MediaAction) obj;
                            arrayList.add(new PluginFaceWidgetMediaData.PluginFaceWidgetMediaAction(FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaAction6.icon), mediaAction6.action, mediaAction6.contentDescription));
                        }
                    }
                    z2 = false;
                }
                Drawable drawable = null;
                MediaDeviceData mediaDeviceData = mediaData.device;
                if (mediaDeviceData != null) {
                    Integer num = mediaDeviceData.customMediaDeviceData.deviceType;
                    Drawable cloneDrawable = FaceWidgetNotificationControllerWrapper.cloneDrawable(mediaDeviceData.icon);
                    CharSequence charSequence = mediaDeviceData.name;
                    pluginFaceWidgetMediaDeviceData = new PluginFaceWidgetMediaData.PluginFaceWidgetMediaDeviceData(mediaDeviceData.enabled, cloneDrawable, charSequence == null ? "" : charSequence.toString(), num == null ? -1 : num.intValue());
                } else {
                    pluginFaceWidgetMediaDeviceData = null;
                }
                Icon icon = mediaData.appIcon;
                if (icon != null && (context = faceWidgetNotificationControllerWrapper.mContext) != null && (loadDrawable = icon.loadDrawable(context)) != null && (mutate = loadDrawable.mutate()) != null && (constantState = mutate.getConstantState()) != null) {
                    drawable = constantState.newDrawable();
                }
                Drawable drawable2 = drawable;
                CharSequence charSequence2 = mediaData.artist;
                CharSequence charSequence3 = mediaData.song;
                Icon icon2 = mediaData.artwork;
                ArrayList arrayList4 = arrayList2;
                if (!z2) {
                    arrayList4 = mediaData.actionsToShowInCompact;
                }
                pluginNotificationController.onMediaDataLoaded(str, str2, new PluginFaceWidgetMediaData(mediaData.userId, mediaData.initialized, 0, 0, mediaData.app, drawable2, charSequence2, charSequence3, icon2, arrayList, arrayList4, mediaData.packageName, mediaData.token, mediaData.clickIntent, pluginFaceWidgetMediaDeviceData, mediaData.active, mediaData.resumeAction, mediaData.resumption, mediaData.notificationKey, mediaData.hasCheckedForResume, Boolean.TRUE.equals(mediaData.isPlaying), mediaData.playbackLocation));
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
    }

    public FaceWidgetNotificationControllerWrapper(NotifCollection notifCollection, ActivityStarter activityStarter) {
        this.mNotifCollection = notifCollection;
        this.mActivityStarter = activityStarter;
    }

    public static Drawable cloneDrawable(Drawable drawable) {
        Drawable.ConstantState constantState;
        if (drawable == null || (constantState = drawable.mutate().getConstantState()) == null) {
            return null;
        }
        return constantState.newDrawable();
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void dismissOngoingActivityNotification(String str) {
        this.mNotifCollection.dismissOngoingActivityNotification(str);
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void expandToNotifications() {
        if (this.mLockscreenNotificationIconsOnlyController != null) {
            ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).countOpenNotificationPanelFromStatusbarOnLockscreen();
            NotificationPanelViewController notificationPanelViewController = this.mLockscreenNotificationIconsOnlyController.mNPVController;
            if (notificationPanelViewController != null) {
                notificationPanelViewController.expandToNotifications();
            }
        }
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
        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
        Log.i(OngoingActivityDataHelper.TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(copyOnWriteArrayList.size(), "getNowBarItemList size = "));
        ArrayList arrayList = new ArrayList();
        Iterator it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            OngoingActivityData ongoingActivityData = (OngoingActivityData) it.next();
            ongoingActivityData.getClass();
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

    public final View getViewFromNowBar(View view, Bundle bundle) {
        try {
            PluginNotificationController pluginNotificationController = this.mNotificationController;
            if (pluginNotificationController != null) {
                return pluginNotificationController.getViewFromNowBar(view, bundle);
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getViewFromNowBar e = ", e, "FaceWidgetNotificationControllerWrapper");
        }
        return view;
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.systemui.plugins.keyguardstatusview.PluginNotificationController.Callback
    public final void onTopNowBarItemChangedFromNowbar(String str) {
        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onTopNowBarItemChangedFromNowbar sbnId = ", str, " NowbarWatcher + ");
        m.append(this.mNowbarWatcher);
        Log.i("FaceWidgetNotificationControllerWrapper", m.toString());
        if (this.mNowbarWatcher != null) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
            if (copyOnWriteArrayList.size() > 1) {
                OngoingActivityData mediaData = str.equals("MEDIA_NOWBAR") ? OngoingActivityDataHelper.getMediaData() : OngoingActivityDataHelper.getOngoingActivityDataByKey(str);
                if (mediaData != null) {
                    int indexOf = copyOnWriteArrayList.indexOf(mediaData);
                    StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" RECEIVED ID - ", str, " and our current top is ", ((OngoingActivityData) copyOnWriteArrayList.get(0)).mNotiID, " and index ");
                    m2.append(indexOf);
                    String sb = m2.toString();
                    String str2 = OngoingActivityDataHelper.TAG;
                    Log.d(str2, sb);
                    int i = 0;
                    while (indexOf != -1 && i < indexOf) {
                        Collections.rotate(OngoingActivityDataHelper.mOngoingActivityLists, -1);
                        i++;
                    }
                    RecyclerView$$ExternalSyntheticOutline0.m(i, str2, KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(indexOf, "found index - ", " and after rotation top is ", ((OngoingActivityData) OngoingActivityDataHelper.mOngoingActivityLists.get(0)).mNotiID, " and count "));
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
        CopyOnWriteArrayList copyOnWriteArrayList = OngoingActivityDataHelper.mOngoingActivityLists;
        if (copyOnWriteArrayList.size() > 1) {
            Collections.rotate(copyOnWriteArrayList, -1);
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
}
