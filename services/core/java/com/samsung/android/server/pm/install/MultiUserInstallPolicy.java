package com.samsung.android.server.pm.install;

import android.content.pm.ApplicationInfo;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.Slog;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda8;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda55;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda68;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.PackageInfoUtils;

import com.samsung.android.server.pm.MetaDataHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiUserInstallPolicy {
    public final MetaDataHelper mMetaDataHelper;
    public final PackageSettingsDelegator mSettingsDelegator;
    public final UserManagerService mUserManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageSettingsDelegator {
        public final TriConsumer mAddDisabledComponent;
        public final Supplier mGetPackagesLocked;
        public final TriConsumer mSetEnabled;
        public final TriConsumer mSetInstalled;

        public PackageSettingsDelegator(
                PackageManagerService$$ExternalSyntheticLambda55
                        packageManagerService$$ExternalSyntheticLambda55,
                PackageManagerService$$ExternalSyntheticLambda68
                        packageManagerService$$ExternalSyntheticLambda68,
                PackageManagerService$$ExternalSyntheticLambda68
                        packageManagerService$$ExternalSyntheticLambda682,
                PackageManagerService$$ExternalSyntheticLambda68
                        packageManagerService$$ExternalSyntheticLambda683) {
            this.mGetPackagesLocked = packageManagerService$$ExternalSyntheticLambda55;
            this.mSetInstalled = packageManagerService$$ExternalSyntheticLambda68;
            this.mSetEnabled = packageManagerService$$ExternalSyntheticLambda682;
            this.mAddDisabledComponent = packageManagerService$$ExternalSyntheticLambda683;
        }
    }

    public MultiUserInstallPolicy(
            PackageSettingsDelegator packageSettingsDelegator,
            MetaDataHelper metaDataHelper,
            UserManagerService userManagerService) {
        this.mSettingsDelegator = packageSettingsDelegator;
        this.mMetaDataHelper = metaDataHelper;
        this.mUserManager = userManagerService;
    }

    public static int checkIfInstallAllowed(
            Bundle bundle,
            int i,
            InstallPackageHelper$$ExternalSyntheticLambda8
                    installPackageHelper$$ExternalSyntheticLambda8) {
        if (i == -1 || i == 0 || bundle == null) {
            return 0;
        }
        if (MetaDataHelper.isMetaDataInBundle(
                bundle, "com.samsung.android.multiuser.install_only_owner")) {
            return 1;
        }
        UserInfo userInfo =
                (UserInfo) installPackageHelper$$ExternalSyntheticLambda8.apply(Integer.valueOf(i));
        return (userInfo != null
                        && userInfo.isGuest()
                        && MetaDataHelper.isMetaDataInBundle(
                                bundle, "com.samsung.android.multiuser.disable_for_guest"))
                ? 2
                : 0;
    }

    public final void applyInstallPolicyPackageInternalLPw(
            PackageSetting packageSetting, List list, int i) {
        ApplicationInfo generateApplicationInfo;
        boolean z;
        boolean z2;
        boolean z3;
        if (packageSetting == null) {
            Slog.i("MultiUserInstallPolicy", "Subuser id is null");
            return;
        }
        this.mMetaDataHelper.getClass();
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        ArrayList<String> arrayList = null;
        Bundle bundle =
                (androidPackageInternal == null
                                || (generateApplicationInfo =
                                                PackageInfoUtils.generateApplicationInfo(
                                                        androidPackageInternal,
                                                        128L,
                                                        packageSetting.readUserState(-1),
                                                        -1,
                                                        packageSetting))
                                        == null)
                        ? null
                        : generateApplicationInfo.metaData;
        PackageSettingsDelegator packageSettingsDelegator = this.mSettingsDelegator;
        if (bundle != null) {
            z =
                    MetaDataHelper.isMetaDataInBundle(
                            bundle, "com.samsung.android.multiuser.install_only_owner");
            z3 =
                    MetaDataHelper.isMetaDataInBundle(
                            bundle, "com.samsung.android.multiuser.disable_for_guest");
            if (z) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    Slog.i(
                            "MultiUserInstallPolicy",
                            "Set package state as uninstalled: "
                                    + packageSetting.mName
                                    + " for userId: "
                                    + num);
                    num.getClass();
                    packageSettingsDelegator.mSetInstalled.accept(
                            packageSetting, Boolean.FALSE, num);
                }
            }
            if (z3 && i > 0) {
                Slog.i(
                        "MultiUserInstallPolicy",
                        "Set package state as uninstalled: "
                                + packageSetting.mName
                                + " for userId: "
                                + i);
                packageSettingsDelegator.mSetInstalled.accept(
                        packageSetting, Boolean.FALSE, Integer.valueOf(i));
            }
            z2 =
                    MetaDataHelper.isMetaDataInBundle(
                            bundle, "com.samsung.android.multiuser.disable_sub_user");
            if (z2) {
                Iterator it2 = ((ArrayList) list).iterator();
                while (it2.hasNext()) {
                    Integer num2 = (Integer) it2.next();
                    Slog.i(
                            "MultiUserInstallPolicy",
                            "Set package state as disabled: "
                                    + packageSetting.mName
                                    + " for userId: "
                                    + i);
                    num2.getClass();
                    packageSettingsDelegator.mSetEnabled.accept(packageSetting, 2, num2);
                }
            }
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        if (z || z3 || z2) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        AndroidPackageInternal androidPackageInternal2 = packageSetting.pkg;
        if (androidPackageInternal2 == null) {
            BootReceiver$$ExternalSyntheticOutline0.m59m(
                    new StringBuilder("Package "),
                    packageSetting.mName,
                    " has not package object",
                    "MultiUserInstallPolicy");
        } else {
            for (ParsedActivity parsedActivity : androidPackageInternal2.getActivities()) {
                if (MetaDataHelper.isMetaDataInBundle(
                                parsedActivity.getMetaData(),
                                "com.samsung.android.multiuser.install_only_owner")
                        && !arrayList2.contains(parsedActivity.getName())) {
                    arrayList2.add(parsedActivity.getName());
                }
            }
            for (ParsedService parsedService : androidPackageInternal2.getServices()) {
                if (MetaDataHelper.isMetaDataInBundle(
                                parsedService.getMetaData(),
                                "com.samsung.android.multiuser.install_only_owner")
                        && !arrayList2.contains(parsedService.getName())) {
                    arrayList2.add(parsedService.getName());
                }
            }
            for (ParsedActivity parsedActivity2 : androidPackageInternal2.getReceivers()) {
                if (MetaDataHelper.isMetaDataInBundle(
                                parsedActivity2.getMetaData(),
                                "com.samsung.android.multiuser.install_only_owner")
                        && !arrayList2.contains(parsedActivity2.getName())) {
                    arrayList2.add(parsedActivity2.getName());
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            Iterator it3 = ((ArrayList) list).iterator();
            while (it3.hasNext()) {
                Integer num3 = (Integer) it3.next();
                for (String str : arrayList) {
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Set component state as disabled: ", str, " in pkg: ");
                    m.append(packageSetting.mName);
                    m.append(" for userId: ");
                    m.append(num3);
                    Slog.i("MultiUserInstallPolicy", m.toString());
                    num3.getClass();
                    packageSettingsDelegator.mAddDisabledComponent.accept(
                            packageSetting, str, num3);
                }
            }
        }
    }

    public final int getSubUserIdsAndGuestUserId(List list) {
        List<UserInfo> list2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            list2 = this.mUserManager.getUsers(true, true, true);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (NullPointerException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            list2 = null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        int i = -1;
        if (list2 == null) {
            return -1;
        }
        for (UserInfo userInfo : list2) {
            int i2 = userInfo.id;
            if (i2 != 0) {
                ((ArrayList) list).add(Integer.valueOf(i2));
                if (userInfo.isGuest()) {
                    i = userInfo.id;
                }
            }
        }
        return i;
    }
}
