package com.android.server.knox;

import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import java.util.Set;

/* loaded from: classes2.dex */
public interface IKnoxAnalyticsContainer {
    int getActivePasswordQuality(int i);

    ComponentName getAdminComponentForLegacy(int i);

    int getCallerIdToShow(int i);

    String getDeviceOwnerPackage();

    String getDisabledPrintServices(int i);

    String getInstallerPackageName(String str);

    int getKnoxFingerprintPlus(int i);

    int getKnoxScreenTimeOut(int i);

    int getLocationProvidersAllowed(int i);

    int getLockScreenAllowPrivateNotification(int i);

    PackageInfo getPackageInfo(String str, int i);

    Bundle getSeparatedAppsConfig();

    long getSeparatedAppsContainerId();

    int getSeparatedAppsUserId();

    Set getVisibleApps(int i);

    boolean hasUserRestriction(String str, int i);

    boolean isDoEnabled(int i);

    int isIMPackage(String str, int i);

    boolean isKnoxId(int i);

    boolean isLegacyClId(int i);

    boolean isOrganizationOwned();

    boolean isPremiumLicenseActivated(int i);

    boolean isSecureFolderId(int i);

    boolean isSeparatedAppsIndepdentApp(PackageInfo packageInfo);

    void sendAnalyticsLog(KnoxAnalyticsData knoxAnalyticsData);
}
