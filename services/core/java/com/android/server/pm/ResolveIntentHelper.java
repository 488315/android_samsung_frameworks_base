package com.android.server.pm;

import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.internal.app.ResolverActivity;
import com.android.internal.util.ArrayUtils;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.verify.domain.DomainVerificationManagerInternal;
import com.android.server.pm.verify.domain.DomainVerificationService;

import com.samsung.android.knox.custom.ProKioskManager;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public final class ResolveIntentHelper {
    public final Context mContext;
    public final DomainVerificationManagerInternal mDomainVerificationManager;
    public final Supplier mInstantAppInstallerActivitySupplier;
    public final PlatformCompat mPlatformCompat;
    public final PreferredActivityHelper mPreferredActivityHelper;
    public final Supplier mResolveInfoSupplier;
    public final UserManagerService mUserManager;
    public final UserNeedsBadgingCache mUserNeedsBadging;

    public ResolveIntentHelper(
            Context context,
            PreferredActivityHelper preferredActivityHelper,
            PlatformCompat platformCompat,
            UserManagerService userManagerService,
            DomainVerificationManagerInternal domainVerificationManagerInternal,
            UserNeedsBadgingCache userNeedsBadgingCache,
            PackageManagerService$$ExternalSyntheticLambda45
                    packageManagerService$$ExternalSyntheticLambda45,
            PackageManagerService$$ExternalSyntheticLambda45
                    packageManagerService$$ExternalSyntheticLambda452) {
        this.mContext = context;
        this.mPreferredActivityHelper = preferredActivityHelper;
        this.mPlatformCompat = platformCompat;
        this.mUserManager = userManagerService;
        this.mDomainVerificationManager = domainVerificationManagerInternal;
        this.mUserNeedsBadging = userNeedsBadgingCache;
        this.mResolveInfoSupplier = packageManagerService$$ExternalSyntheticLambda45;
        this.mInstantAppInstallerActivitySupplier =
                packageManagerService$$ExternalSyntheticLambda452;
    }

    public final void applyPostContentProviderResolutionFilter(
            Computer computer, List list, String str, int i, int i2) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ResolveInfo resolveInfo = (ResolveInfo) list.get(size);
            if (str != null
                    || computer.shouldFilterApplication(
                            computer.getPackageStateInternal(
                                    0, resolveInfo.providerInfo.packageName),
                            i2,
                            i)) {
                boolean isInstantApp = resolveInfo.providerInfo.applicationInfo.isInstantApp();
                if (isInstantApp && str.equals(resolveInfo.providerInfo.packageName)) {
                    ProviderInfo providerInfo = resolveInfo.providerInfo;
                    String str2 = providerInfo.splitName;
                    if (str2 != null
                            && !ArrayUtils.contains(
                                    providerInfo.applicationInfo.splitNames, str2)) {
                        if (this.mInstantAppInstallerActivitySupplier.get() == null) {
                            if (PackageManagerService.DEBUG_INSTANT) {
                                Slog.v(
                                        "PackageManager",
                                        "No installer - not adding it to the ResolveInfo list");
                            }
                            list.remove(size);
                        } else {
                            if (PackageManagerService.DEBUG_INSTANT) {
                                Slog.v(
                                        "PackageManager",
                                        "Adding ephemeral installer to the ResolveInfo list");
                            }
                            ResolveInfo resolveInfo2 =
                                    new ResolveInfo(computer.getInstantAppInstallerInfo());
                            ProviderInfo providerInfo2 = resolveInfo.providerInfo;
                            resolveInfo2.auxiliaryInfo =
                                    new AuxiliaryResolveInfo(
                                            (ComponentName) null,
                                            providerInfo2.packageName,
                                            providerInfo2.applicationInfo.longVersionCode,
                                            providerInfo2.splitName);
                            resolveInfo2.filter = new IntentFilter();
                            resolveInfo2.resolvePackageName =
                                    resolveInfo.getComponentInfo().packageName;
                            list.set(size, resolveInfo2);
                        }
                    }
                } else if (isInstantApp || (resolveInfo.providerInfo.flags & 1048576) == 0) {
                    list.remove(size);
                }
            }
        }
    }

    public final ResolveInfo chooseBestActivity(
            Computer computer,
            Intent intent,
            String str,
            long j,
            long j2,
            List list,
            int i,
            boolean z) {
        int i2;
        PackageSetting packageStateInternal;
        String homeActivity;
        if (list == null) {
            return null;
        }
        int size = list.size();
        ProKioskManager proKioskManager = ProKioskManager.getInstance();
        if (proKioskManager == null
                || !intent.hasCategory("android.intent.category.HOME")
                || intent.getComponent() != null
                || size <= 0
                || !proKioskManager.getProKioskState()
                || (homeActivity = proKioskManager.getHomeActivity()) == null) {
            i2 = size;
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                if (homeActivity.startsWith(resolveInfo.activityInfo.packageName)) {
                    return resolveInfo;
                }
            }
            i2 = size;
            ResolveInfo findPreferredActivityNotLocked =
                    this.mPreferredActivityHelper.findPreferredActivityNotLocked(
                            intent, str, j, list, true, false, (intent.getFlags() & 8) != 0, i, z);
            if (findPreferredActivityNotLocked != null) {
                return findPreferredActivityNotLocked;
            }
        }
        if (i2 == 1) {
            return (ResolveInfo) list.get(0);
        }
        if (i2 <= 1) {
            return null;
        }
        boolean z2 = (intent.getFlags() & 8) != 0;
        ResolveInfo resolveInfo2 = (ResolveInfo) list.get(0);
        ResolveInfo resolveInfo3 = (ResolveInfo) list.get(1);
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append(resolveInfo2.activityInfo.name);
            sb.append("=");
            sb.append(resolveInfo2.priority);
            sb.append(" vs ");
            sb.append(resolveInfo3.activityInfo.name);
            sb.append("=");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, resolveInfo3.priority, "PackageManager");
        }
        if (resolveInfo2.priority != resolveInfo3.priority
                || resolveInfo2.preferredOrder != resolveInfo3.preferredOrder
                || resolveInfo2.isDefault != resolveInfo3.isDefault) {
            return (ResolveInfo) list.get(0);
        }
        ResolveInfo findPreferredActivityNotLocked2 =
                this.mPreferredActivityHelper.findPreferredActivityNotLocked(
                        intent, str, j, list, true, false, z2, i, z);
        if (findPreferredActivityNotLocked2 != null) {
            return findPreferredActivityNotLocked2;
        }
        int i3 = 0;
        int i4 = 0;
        while (i4 < i2) {
            ResolveInfo resolveInfo4 = (ResolveInfo) list.get(i4);
            if (resolveInfo4.handleAllWebDataURI) {
                i3++;
            }
            int i5 = i3;
            if (resolveInfo4.activityInfo.applicationInfo.isInstantApp()
                    && (packageStateInternal =
                                    computer.getPackageStateInternal(
                                            resolveInfo4.activityInfo.packageName))
                            != null) {
                boolean z3 = PackageManagerServiceUtils.DEBUG;
                if (((DomainVerificationService) this.mDomainVerificationManager)
                                .approvalLevelForDomain(packageStateInternal, intent, j, i)
                        > 0) {
                    return resolveInfo4;
                }
            }
            i4++;
            i3 = i5;
        }
        if ((j2 & 2) != 0) {
            return null;
        }
        ResolveInfo resolveInfo5 = new ResolveInfo((ResolveInfo) this.mResolveInfoSupplier.get());
        resolveInfo5.handleAllWebDataURI = i3 == i2;
        ActivityInfo activityInfo = new ActivityInfo(resolveInfo5.activityInfo);
        resolveInfo5.activityInfo = activityInfo;
        activityInfo.labelRes = ResolverActivity.getLabelRes(intent.getAction());
        if (resolveInfo5.userHandle == null) {
            resolveInfo5.userHandle = UserHandle.of(i);
        }
        String str2 = intent.getPackage();
        if (!TextUtils.isEmpty(str2) && !ArrayUtils.isEmpty(list)) {
            int size2 = list.size();
            int i6 = 0;
            while (true) {
                if (i6 < size2) {
                    ResolveInfo resolveInfo6 = (ResolveInfo) list.get(i6);
                    ActivityInfo activityInfo2 =
                            resolveInfo6 != null ? resolveInfo6.activityInfo : null;
                    if (activityInfo2 == null || !str2.equals(activityInfo2.packageName)) {
                        break;
                    }
                    i6++;
                } else {
                    ApplicationInfo applicationInfo =
                            ((ResolveInfo) list.get(0)).activityInfo.applicationInfo;
                    resolveInfo5.resolvePackageName = str2;
                    if (this.mUserNeedsBadging.get(i)) {
                        resolveInfo5.noResourceId = true;
                    } else {
                        resolveInfo5.icon = applicationInfo.icon;
                    }
                    resolveInfo5.iconResourceId = applicationInfo.icon;
                    resolveInfo5.labelRes = applicationInfo.labelRes;
                }
            }
        }
        resolveInfo5.activityInfo.applicationInfo =
                new ApplicationInfo(resolveInfo5.activityInfo.applicationInfo);
        if (i != 0) {
            ApplicationInfo applicationInfo2 = resolveInfo5.activityInfo.applicationInfo;
            applicationInfo2.uid = UserHandle.getUid(i, UserHandle.getAppId(applicationInfo2.uid));
        }
        ActivityInfo activityInfo3 = resolveInfo5.activityInfo;
        if (activityInfo3.metaData == null) {
            activityInfo3.metaData = new Bundle();
        }
        resolveInfo5.activityInfo.metaData.putBoolean("android.dock_home", true);
        return resolveInfo5;
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0152, code lost:

       if (r0 != null) goto L80;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List queryIntentReceiversInternal(
            com.android.server.pm.Computer r21,
            android.content.Intent r22,
            java.lang.String r23,
            long r24,
            int r26,
            int r27,
            int r28,
            boolean r29) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ResolveIntentHelper.queryIntentReceiversInternal(com.android.server.pm.Computer,"
                    + " android.content.Intent, java.lang.String, long, int, int, int,"
                    + " boolean):java.util.List");
    }

    public final ResolveInfo resolveIntentInternal(
            Computer computer,
            Intent intent,
            String str,
            long j,
            long j2,
            int i,
            boolean z,
            int i2,
            int i3) {
        List list;
        ResolveInfo resolveInfo;
        try {
            Trace.traceBegin(262144L, "resolveIntent");
            if (!this.mUserManager.mLocalService.exists(i)) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            long updateFlagsForResolve =
                    computer.updateFlagsForResolve(
                            i,
                            i2,
                            j,
                            z,
                            computer.isImplicitImageCaptureIntentAndNotSetByDpc(intent, str, j, i));
            computer.enforceCrossUserPermission("resolve intent", callingUid, i, false, false);
            List list2 = PersonaServiceHelper.ALLOWED_BLUETOOTH_TARGET;
            if (Binder.getCallingUid() == 1027) {
                Log.d(
                        "PackageManager",
                        "Resolving for NFC "
                                + intent
                                + " flag "
                                + updateFlagsForResolve
                                + " user "
                                + i);
                if ((131072 & updateFlagsForResolve) != 0) {
                    Log.d("PackageManager", "Get preferred activity for NFC of user " + i);
                    PreferredActivityHelper preferredActivityHelper = this.mPreferredActivityHelper;
                    try {
                        list =
                                ActivityThread.getPackageManager()
                                        .queryIntentActivities(
                                                intent, str, updateFlagsForResolve, i)
                                        .getList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        list = null;
                    }
                    if (list != null && !list.isEmpty()) {
                        resolveInfo =
                                preferredActivityHelper.findPreferredActivityNotLocked(
                                        intent,
                                        str,
                                        updateFlagsForResolve,
                                        list,
                                        true,
                                        false,
                                        true,
                                        i,
                                        UserHandle.getAppId(Binder.getCallingUid()) >= 10000);
                        return resolveInfo;
                    }
                    resolveInfo = null;
                    return resolveInfo;
                }
            }
            Trace.traceBegin(262144L, "queryIntentActivities");
            List queryIntentActivitiesInternal =
                    computer.queryIntentActivitiesInternal(
                            intent, str, updateFlagsForResolve, i2, i3, i, z, true);
            Trace.traceEnd(262144L);
            if (z) {
                SaferIntentUtils.IntentArgs intentArgs =
                        new SaferIntentUtils.IntentArgs(false, i2, i3, intent, str, true);
                intentArgs.platformCompat = this.mPlatformCompat;
                SaferIntentUtils.filterNonExportedComponents(
                        intentArgs, queryIntentActivitiesInternal);
            }
            if (UserHandle.getAppId(i2) >= 10000 && !z) {
                r16 = true;
            }
            ResolveInfo chooseBestActivity =
                    chooseBestActivity(
                            computer,
                            intent,
                            str,
                            updateFlagsForResolve,
                            j2,
                            queryIntentActivitiesInternal,
                            i,
                            r16);
            if ((j2 & 1) == 0
                    || chooseBestActivity == null
                    || !chooseBestActivity.handleAllWebDataURI) {
                return chooseBestActivity;
            }
            Trace.traceEnd(262144L);
            return null;
        } finally {
            Trace.traceEnd(262144L);
        }
    }

    public final ResolveInfo resolveServiceInternal(
            Computer computer,
            Intent intent,
            String str,
            long j,
            int i,
            int i2,
            int i3,
            boolean z) {
        List queryIntentServicesInternal;
        if (this.mUserManager.mLocalService.exists(i)
                && (queryIntentServicesInternal =
                                computer.queryIntentServicesInternal(
                                        intent,
                                        str,
                                        computer.updateFlagsForResolve(i, i2, j, false, false),
                                        i,
                                        i2,
                                        i3,
                                        z))
                        != null
                && queryIntentServicesInternal.size() >= 1) {
            return (ResolveInfo) queryIntentServicesInternal.get(0);
        }
        return null;
    }
}
