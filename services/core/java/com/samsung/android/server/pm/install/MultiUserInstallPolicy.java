package com.samsung.android.server.pm.install;

import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.Slog;
import com.android.internal.util.function.TriConsumer;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.component.ParsedActivity;
import com.android.server.pm.pkg.component.ParsedService;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.server.pm.MetaDataHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class MultiUserInstallPolicy {
    public final MetaDataHelper mMetaDataHelper;
    public final PackageSettingsDelegator mSettingsDelegator;
    public final UserManagerService mUserManager;

    public MultiUserInstallPolicy(PackageSettingsDelegator packageSettingsDelegator, MetaDataHelper metaDataHelper, UserManagerService userManagerService) {
        this.mSettingsDelegator = packageSettingsDelegator;
        this.mMetaDataHelper = metaDataHelper;
        this.mUserManager = userManagerService;
    }

    public void applyInstallPolicyLPw() {
        ArrayList arrayList = new ArrayList();
        int subUserIdsAndGuestUserId = getSubUserIdsAndGuestUserId(arrayList, -1);
        Iterator it = this.mSettingsDelegator.getPackagesLocked().values().iterator();
        while (it.hasNext()) {
            applyInstallPolicyPackageInternalLPw((PackageSetting) it.next(), arrayList, subUserIdsAndGuestUserId);
        }
    }

    public void applyInstallPolicyPackageAsUserLPw(String str, int i) {
        PackageSetting packageSetting = (PackageSetting) this.mSettingsDelegator.getPackagesLocked().get(str);
        if (packageSetting == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        applyInstallPolicyPackageInternalLPw(packageSetting, arrayList, getSubUserIdsAndGuestUserId(arrayList, i));
    }

    public void applyInstallPolicyPackageInternalLPw(PackageSetting packageSetting, List list, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        List<String> componentsHavingEnabledMetaDataLPr;
        if (packageSetting == null || list == null) {
            Slog.i("MultiUserInstallPolicy", "Subuser id is null");
            return;
        }
        Bundle appMetaData = this.mMetaDataHelper.getAppMetaData(packageSetting);
        boolean z4 = false;
        if (appMetaData != null) {
            z2 = MetaDataHelper.isMetaDataInBundle(appMetaData, "com.samsung.android.multiuser.install_only_owner");
            z3 = MetaDataHelper.isMetaDataInBundle(appMetaData, "com.samsung.android.multiuser.disable_for_guest");
            if (z2) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    Slog.i("MultiUserInstallPolicy", "Set package state as uninstalled: " + packageSetting.getName() + " for userId: " + num);
                    this.mSettingsDelegator.setInstalled(packageSetting, false, num.intValue());
                }
            }
            if (z3 && i > 0) {
                Slog.i("MultiUserInstallPolicy", "Set package state as uninstalled: " + packageSetting.getName() + " for userId: " + i);
                this.mSettingsDelegator.setInstalled(packageSetting, false, i);
            }
            z = MetaDataHelper.isMetaDataInBundle(appMetaData, "com.samsung.android.multiuser.disable_sub_user");
            if (z) {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    Integer num2 = (Integer) it2.next();
                    Slog.i("MultiUserInstallPolicy", "Set package state as disabled: " + packageSetting.getName() + " for userId: " + i);
                    this.mSettingsDelegator.setEnabled(packageSetting, 2, num2.intValue());
                }
            }
        } else {
            z = false;
            z2 = false;
            z3 = false;
        }
        if (!z2 && !z3 && !z) {
            z4 = true;
        }
        if (!z4 || (componentsHavingEnabledMetaDataLPr = getComponentsHavingEnabledMetaDataLPr(packageSetting, "com.samsung.android.multiuser.install_only_owner")) == null) {
            return;
        }
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            Integer num3 = (Integer) it3.next();
            for (String str : componentsHavingEnabledMetaDataLPr) {
                Slog.i("MultiUserInstallPolicy", "Set component state as disabled: " + str + " in pkg: " + packageSetting.getName() + " for userId: " + num3);
                this.mSettingsDelegator.addDisabledComponent(packageSetting, str, num3.intValue());
            }
        }
    }

    public final int getSubUserIdsAndGuestUserId(List list, int i) {
        List<UserInfo> users = getUsers(true);
        if (users == null) {
            return -1;
        }
        int i2 = -1;
        for (UserInfo userInfo : users) {
            int i3 = userInfo.id;
            if (i3 != 0) {
                if (i == -1) {
                    list.add(Integer.valueOf(i3));
                } else if (i3 == i) {
                    list.add(Integer.valueOf(i3));
                }
                if (userInfo.isGuest()) {
                    i2 = userInfo.id;
                }
            }
        }
        return i2;
    }

    public final List getComponentsHavingEnabledMetaDataLPr(PackageSetting packageSetting, String str) {
        ArrayList arrayList = new ArrayList();
        if (packageSetting == null) {
            Slog.i("MultiUserInstallPolicy", "ps object is null");
            return null;
        }
        AndroidPackageInternal pkg = packageSetting.getPkg();
        if (pkg == null) {
            Slog.i("MultiUserInstallPolicy", "Package " + packageSetting.getName() + " has not package object");
            return null;
        }
        for (ParsedActivity parsedActivity : pkg.getActivities()) {
            if (MetaDataHelper.isMetaDataInBundle(this.mMetaDataHelper.getComponentMetaData(parsedActivity), str) && !arrayList.contains(parsedActivity.getName())) {
                arrayList.add(parsedActivity.getName());
            }
        }
        for (ParsedService parsedService : pkg.getServices()) {
            if (MetaDataHelper.isMetaDataInBundle(this.mMetaDataHelper.getComponentMetaData(parsedService), str) && !arrayList.contains(parsedService.getName())) {
                arrayList.add(parsedService.getName());
            }
        }
        for (ParsedActivity parsedActivity2 : pkg.getReceivers()) {
            if (MetaDataHelper.isMetaDataInBundle(this.mMetaDataHelper.getComponentMetaData(parsedActivity2), str) && !arrayList.contains(parsedActivity2.getName())) {
                arrayList.add(parsedActivity2.getName());
            }
        }
        return arrayList;
    }

    public static String notAllowedReasonToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "INSTALL_NOT_ALLOWED_UNINSTALL_FOR_GUEST" : "INSTALL_NOT_ALLOWED_INSTALL_ONLY_OWNER" : "INSTALL_ALLOWD";
    }

    public static int checkIfInstallExistingAllowed(Bundle bundle, int i) {
        return (i == 0 || bundle == null || !MetaDataHelper.isMetaDataInBundle(bundle, "com.samsung.android.multiuser.install_only_owner")) ? 0 : 1;
    }

    public static int checkIfInstallAllowed(Bundle bundle, int i, Function function) {
        if (i == -1 || i == 0 || bundle == null) {
            return 0;
        }
        if (MetaDataHelper.isMetaDataInBundle(bundle, "com.samsung.android.multiuser.install_only_owner")) {
            return 1;
        }
        UserInfo userInfo = (UserInfo) function.apply(Integer.valueOf(i));
        return (userInfo != null && userInfo.isGuest() && MetaDataHelper.isMetaDataInBundle(bundle, "com.samsung.android.multiuser.disable_for_guest")) ? 2 : 0;
    }

    public final List getUsers(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List users = this.mUserManager.getUsers(z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return users;
        } catch (NullPointerException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static int findCurrentGuestUserId() {
        List guestUsers = UserManagerService.getInstance().getGuestUsers();
        if (guestUsers.size() == 0) {
            return -1;
        }
        return ((UserInfo) guestUsers.get(0)).id;
    }

    public class PackageSettingsDelegator {
        public final TriConsumer mAddDisabledComponent;
        public final Supplier mGetPackagesLocked;
        public final TriConsumer mSetEnabled;
        public final TriConsumer mSetInstalled;

        public PackageSettingsDelegator(Supplier supplier, TriConsumer triConsumer, TriConsumer triConsumer2, TriConsumer triConsumer3) {
            this.mGetPackagesLocked = supplier;
            this.mSetInstalled = triConsumer;
            this.mSetEnabled = triConsumer2;
            this.mAddDisabledComponent = triConsumer3;
        }

        public WatchedArrayMap getPackagesLocked() {
            return (WatchedArrayMap) this.mGetPackagesLocked.get();
        }

        public void setInstalled(PackageSetting packageSetting, boolean z, int i) {
            this.mSetInstalled.accept(packageSetting, Boolean.valueOf(z), Integer.valueOf(i));
        }

        public void setEnabled(PackageSetting packageSetting, int i, int i2) {
            this.mSetEnabled.accept(packageSetting, Integer.valueOf(i), Integer.valueOf(i2));
        }

        public void addDisabledComponent(PackageSetting packageSetting, String str, int i) {
            this.mAddDisabledComponent.accept(packageSetting, str, Integer.valueOf(i));
        }
    }
}
