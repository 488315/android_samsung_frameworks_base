package com.android.systemui.plugins.keyguardstatusview;

import android.util.Pair;
import android.view.View;
import com.android.systemui.plugins.annotations.SupportVersionChecker;
import com.android.systemui.plugins.annotations.VersionCheck;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@SupportVersionChecker
/* loaded from: classes2.dex */
public interface PluginClockProvider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ClockCallback {
        void onAODClockStyleChanged();

        void onClockColorChanged();

        void onClockFontChanged();

        void onClockPackageChanged();

        @VersionCheck(version = 2009)
        void onClockPositionChanged(boolean z);

        void onClockScaleChanged();

        void onClockStyleChanged(boolean z);

        void onClockVisibilityChanged();
    }

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_SUCCESSFUL)
    void forceRefresh(View view);

    @VersionCheck(version = 2011)
    int getClockDateColor();

    @VersionCheck(version = 1044)
    String getClockFontPath();

    @VersionCheck(version = 1044)
    int getClockFontType();

    @VersionCheck(defInt = -1, version = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423)
    int getClockGravity();

    @VersionCheck(defInt = 2, version = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS)
    int getClockGroup(int i);

    @VersionCheck(defFloat = 1.0f, version = 1038)
    float getClockScale();

    @VersionCheck(defInt = 4112, version = 1038)
    int getClockScalePivot(int i);

    @VersionCheck(version = 1002)
    View getClockView(int i);

    @VersionCheck(version = 1044)
    int getClockVisibility();

    @VersionCheck(version = 1042)
    int getConsideredClockType();

    @VersionCheck(version = 1033)
    int getConsideredThemeClockType();

    @VersionCheck(defInt = 2, version = 1002)
    int getDefaultClockType();

    @VersionCheck(version = VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423)
    Pair<Integer, Integer> getKeyguardClockHorizontalPadding();

    @VersionCheck(defInt = 2, version = 1002)
    int getLockClockType();

    @VersionCheck(version = 1002)
    boolean isStartAlignClock(int i);

    @VersionCheck(version = 1002)
    void registerClockChangedCallback(ClockCallback clockCallback);

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_CHANGE_PWD_SUCCESSFUL)
    void setAdaptiveColors(View view, int[] iArr);

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS)
    boolean setClockType(int i);

    @VersionCheck(version = 1031)
    void setColorThemeAdaptiveColor(View view, int i, boolean z);

    @VersionCheck(version = 1031)
    void setFontColorType(View view, int i);

    @VersionCheck(version = EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS)
    void setPreDefineOrCustomColor(View view);

    @VersionCheck(version = 1002)
    void unregisterClockChangedCallback(ClockCallback clockCallback);
}
