package com.android.systemui.plugins.keyguardstatusview;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.android.systemui.plugins.keyguardstatusview.PluginNotificationController;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginKeyguardStatusView.ACTION, version = 0)
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginKeyguardStatusView extends Plugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_FACE_WIDGET";
    public static final int MAJOR_VERSION = 2;
    public static final int MINOR_VERSION = 14;
    public static final int VERSION = 2014;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @SupportVersionChecker
    public interface Callback {
        @VersionCheck(version = 1028)
        void applyBlur(float f);

        @VersionCheck(version = 1030)
        void applyBlur(int i);

        boolean canBeSkipOnWakeAndUnlock();

        View getAODClockView(boolean z);

        Point getAODZigzagPosition();

        int[] getAdaptiveColorResult();

        @VersionCheck(version = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F)
        PluginFaceWidgetColorSchemeController getColorSchemeController();

        PluginDisplayLifeCycle getDisplayLifeCycle();

        int getFloatingShortcutRotation();

        String getHomeCityTimeZoneDeviceProvisionedFromPrefs();

        int getInDisplayFingerprintHeight();

        int getInDisplayFingerprintImageSize();

        PluginKeyguardStatusCallback getKeyguardStatusCallback();

        PluginKeyguardUpdateMonitor getKeyguardUpdateMonitor();

        PluginKnoxStateMonitor getKnoxStateMonitor();

        @Deprecated
        PluginLockPatternUtils getLockPatternUtils();

        @VersionCheck(version = 2007)
        boolean getMediaPlayerLastExpandedFromPrefs();

        int getNavigationBarHeight();

        PluginNotificationController.Callback getNotificationControllerCallback();

        int getNotificationPanelViewHeight();

        PluginFaceWidgetLockManager getPluginLockManager();

        int getSystemUIPluginVersion();

        PluginSystemUIWallpaperUtils getWallpaperUtils();

        boolean hasAdaptiveColorResult();

        boolean isBlurSupported();

        boolean isCMASSupported();

        @VersionCheck(version = 1028)
        boolean isCapturedBlurSupported();

        @VersionCheck(version = 1040)
        boolean isEditMode();

        boolean isInDisplayFingerprintSupported();

        boolean isLockScreenDisabled();

        boolean isMultiSimSupported();

        @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_FAILED)
        boolean isNoLockIcon();

        boolean isOpenThemeSupported();

        boolean isPresidentialCMASSupported();

        boolean isSubDisplay();

        boolean isUIBiometricsSupported();

        boolean isWhiteKeyguardWallpaper(String str);

        boolean isWiFiOnlyDevice();

        void onClockPageTransitionEnded();

        void putHomeCityTimeZoneDeviceProvisionedToPrefs(String str);

        void putHomeCityTimeZoneSetToPrefs(String str);

        @VersionCheck(version = 2007)
        void putMediaPlayerLastExpandedToPrefs(boolean z);

        @VersionCheck(version = 1036)
        void removeMediaData(List<String> list);

        @VersionCheck(version = 1027)
        void sendEventCDLog(String str, String str2, String str3, String str4, String str5, String str6);

        @VersionCheck(version = 1027)
        void sendEventCDLog(String str, String str2, Map<String, String> map);

        void sendEventLog(String str, String str2);

        void sendEventLog(String str, String str2, String str3);

        @VersionCheck(version = 2012)
        boolean shouldControlScreenOff();

        boolean shouldEnableKeyguardScreenRotation();

        @VersionCheck(version = 1025)
        void updateAnimateScreenOff();

        @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED)
        void updateFaceWidgetArea();

        @VersionCheck(version = 1032)
        void updateNIOShortcutFingerPrintVisibility(boolean z);
    }

    @VersionCheck(version = 1020)
    View attachFaceWidgetContainer(ViewGroup viewGroup, View view);

    @VersionCheck(version = 1039)
    View attachFaceWidgetContainer(ViewGroup viewGroup, View view, int i);

    @VersionCheck(version = 1035)
    void dismissFaceWidgetDashBoard();

    void dozeTimeTick();

    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @VersionCheck(version = 1025)
    PluginAODStateProvider getAODStateProvider();

    PluginClockProvider getClockProvider();

    @VersionCheck(version = 1000)
    View getContainerView();

    @VersionCheck(version = 1020)
    List<View> getContentsContainers();

    int getCurrentClockType();

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS)
    int getMinTopMargin(int i, boolean z);

    PluginNotificationController getNotificationController();

    PluginSecKeyguardClockPositionAlgorithm getPositionAlgorithm();

    PluginKeyguardSidePadding getSecKeyguardSidePadding();

    boolean isFaceWidgetFullScreenShowing();

    @VersionCheck(version = 1034)
    boolean isInContentBounds(float f, float f2);

    @VersionCheck(version = 1032)
    boolean isMediaOutputRemoteviewsVisible();

    @VersionCheck(version = 1040)
    void onCancelEditMode();

    @VersionCheck(version = 1029)
    void onClassLoaderDiscarded();

    void onDensityOrFontScaleChanged();

    void onFinishedGoingToSleep();

    void onFinishedWakingUp();

    void onFolderStateChanged(boolean z);

    @VersionCheck(version = 1045)
    void onKeyguardVisibilityHelperChanged(int i);

    @VersionCheck(version = 2008)
    void onMediaPlayerLastExpandedPrefChanged(boolean z);

    void onStartedGoingToSleep();

    void onStartedWakingUp();

    @VersionCheck(version = 1040)
    void onStartingEditModeAnimation(boolean z);

    void onUpdateLockscreenHiddenItems();

    void setDarkAmount(float f);

    void setDozing(boolean z);

    void setExpandState(boolean z, int i);

    @VersionCheck(version = 1032)
    void setMediaOutputVisibility(boolean z);

    void setPluginFaceWidgetCallback(Callback callback);

    void setTouchEnabled(boolean z);
}
