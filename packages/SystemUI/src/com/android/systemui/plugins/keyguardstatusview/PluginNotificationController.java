package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.service.notification.StatusBarNotification;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginNotificationController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @SupportVersionChecker
    public interface Callback {
        @VersionCheck(version = 3013)
        void dismissOngoingActivityNotification(String str);

        @VersionCheck(version = 2010)
        void expandToNotifications();

        int getActiveNotificationSize();

        @VersionCheck(version = 1026)
        List<StatusBarNotification> getAllNotifications();

        String getEntryKey(int i);

        String getNotificationPackageName(int i);

        int getNotificationUid(int i);

        @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE)
        ArrayList<NowBarItem> getNowBarItemList();

        @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_SERVICE_NOT_ALLOWED_IN_THIS_LOCATION)
        View getNowBarRootView();

        @VersionCheck(version = VolteConstants.ErrorCode.CALL_FORBIDDEN)
        int getPluginLockDataGravity();

        @VersionCheck(version = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_GENERAL)
        int getPluginLockDataMarginTop();

        @VersionCheck(version = VolteConstants.ErrorCode.CALL_FORBIDDEN)
        int getPluginLockDataPaddingEnd();

        @VersionCheck(version = VolteConstants.ErrorCode.CALL_FORBIDDEN)
        int getPluginLockDataPaddingStart();

        @VersionCheck(version = VolteConstants.ErrorCode.CALL_FORBIDDEN)
        boolean isPluginLockDataAvailable();

        @VersionCheck(version = 1050)
        boolean isTransformAnimating();

        @VersionCheck(version = 1050)
        void onClick();

        @VersionCheck(version = 3017)
        void onExternalExpandNowbarCardAttached(NowBarItem nowBarItem, boolean z);

        @VersionCheck(version = 3014)
        void onTopNowBarItemChangedFromNowbar(String str);

        @VersionCheck(version = 1050)
        void onTouchEvent(int i);

        @VersionCheck(version = 1050)
        void setNotificationIconsOnlyContainer();

        @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE)
        void swapLastItemToFirst();
    }

    void addFaceWidgetMusicNotification(String str);

    @VersionCheck(version = 1050)
    void calculateAlphaByQsExpansion(float f, boolean z, int i, int i2);

    @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE_FORKED)
    void createFullScreenView(View view);

    @VersionCheck(version = 1050)
    View getIconContainer();

    @VersionCheck(version = 1050)
    PluginAODNotificationManager getNotificationManager();

    void init(Consumer<Void> consumer);

    boolean isFaceWidgetMusicNotification(String str);

    @VersionCheck(version = 1050)
    boolean isIconsOnlyInterceptTouchEvent(MotionEvent motionEvent);

    @VersionCheck(version = 1050)
    boolean isIconsOnlyTouchEvent(MotionEvent motionEvent);

    boolean isMusicFaceWidgetOn();

    @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE_FORKED)
    void onFullScreenViewClosed();

    @VersionCheck(version = 1018)
    void onMediaDataLoaded(String str, String str2, PluginFaceWidgetMediaData pluginFaceWidgetMediaData);

    @VersionCheck(version = 1018)
    void onMediaDataRemoved(String str);

    @VersionCheck(version = VolteConstants.ErrorCode.OTHER_SECONDARY_DEVICE_IN_USE)
    void onNowBarItemRemoved(NowBarItem nowBarItem);

    @VersionCheck(version = VolteConstants.ErrorCode.OTHER_SECONDARY_DEVICE_IN_USE)
    void onNowBarItemUpdated(NowBarItem nowBarItem);

    @VersionCheck(version = 3014)
    void onTopNowBarItemChanged(NowBarItem nowBarItem);

    void removeFaceWidgetMusicNotification(String str);

    @VersionCheck(version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE_FORKED)
    void setFullNowBarProgress(Float f);

    boolean updateFaceWidgetMusicNotificationKey();

    void updateFaceWidgetMusicNotificationPkg(String str);

    @VersionCheck(version = 1050)
    Point updateNotificationIconsOnlyPosition();
}
