package com.android.server.infra;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AbstractMasterSystemService extends SystemService {
    public boolean debug;
    public boolean mAllowInstantService;
    public final SparseBooleanArray mDisabledByUserRestriction;
    public final Object mLock;
    public final ServiceNameBaseResolver mServiceNameResolver;
    public final int mServicePackagePolicyFlags;
    public final SparseArray mServicesCacheList;
    public final String mTag;
    public UserManagerInternal mUm;
    public SparseArray mUpdatingPackageNames;
    public boolean verbose;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
            ContentResolver contentResolver =
                    AbstractMasterSystemService.this.getContext().getContentResolver();
            String serviceSettingsProperty =
                    AbstractMasterSystemService.this.getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                contentResolver.registerContentObserver(
                        Settings.Secure.getUriFor(serviceSettingsProperty), false, this, -1);
            }
            contentResolver.registerContentObserver(
                    Settings.Secure.getUriFor("user_setup_complete"), false, this, -1);
            AbstractMasterSystemService.this.registerForExtraSettingsChanges(contentResolver, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            AbstractMasterSystemService abstractMasterSystemService =
                    AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(
                        abstractMasterSystemService.mTag,
                        "onChange(): uri=" + uri + ", userId=" + i);
            }
            String lastPathSegment = uri.getLastPathSegment();
            if (lastPathSegment == null) {
                return;
            }
            if (!lastPathSegment.equals(
                            AbstractMasterSystemService.this.getServiceSettingsProperty())
                    && !lastPathSegment.equals("user_setup_complete")) {
                AbstractMasterSystemService.this.onSettingsChanged(i, lastPathSegment);
                return;
            }
            synchronized (AbstractMasterSystemService.this.mLock) {
                AbstractMasterSystemService.this.updateCachedServiceLocked(i);
            }
        }
    }

    public AbstractMasterSystemService(
            Context context,
            FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver,
            String str) {
        this(context, frameworkResourcesServiceNameResolver, str, 34);
    }

    public AbstractMasterSystemService(
            Context context,
            ServiceNameBaseResolver serviceNameBaseResolver,
            final String str,
            int i) {
        super(context);
        String simpleName = getClass().getSimpleName();
        this.mTag = simpleName;
        this.mLock = new Object();
        int i2 = 0;
        this.verbose = false;
        this.debug = false;
        this.mServicesCacheList = new SparseArray();
        i = (i & 7) == 0 ? i | 2 : i;
        this.mServicePackagePolicyFlags = (i & 112) == 0 ? i | 32 : i;
        this.mServiceNameResolver = serviceNameBaseResolver;
        if (serviceNameBaseResolver != null) {
            ServiceNameResolver$NameResolverListener serviceNameResolver$NameResolverListener =
                    new ServiceNameResolver$NameResolverListener() { // from class:
                                                                     // com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda0
                        @Override // com.android.server.infra.ServiceNameResolver$NameResolverListener
                        public final void onNameResolved(int i3, String str2, boolean z) {
                            AbstractMasterSystemService.this.onServiceNameChanged(i3, str2, z);
                        }
                    };
            synchronized (serviceNameBaseResolver.mLock) {
                serviceNameBaseResolver.mOnSetCallback = serviceNameResolver$NameResolverListener;
            }
        }
        if (str == null) {
            this.mDisabledByUserRestriction = null;
        } else {
            this.mDisabledByUserRestriction = new SparseBooleanArray();
            if (this.mUm == null) {
                if (this.verbose) {
                    Slog.v(simpleName, "lazy-loading UserManagerInternal");
                }
                this.mUm =
                        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            }
            UserManagerInternal userManagerInternal = this.mUm;
            List supportedUsers = getSupportedUsers();
            while (true) {
                ArrayList arrayList = (ArrayList) supportedUsers;
                if (i2 >= arrayList.size()) {
                    break;
                }
                int i3 = ((UserInfo) arrayList.get(i2)).id;
                boolean userRestriction = userManagerInternal.getUserRestriction(i3, str);
                if (userRestriction) {
                    HermesService$3$$ExternalSyntheticOutline0.m(
                            i3, "Disabling by restrictions user ", this.mTag);
                    this.mDisabledByUserRestriction.put(i3, userRestriction);
                }
                i2++;
            }
            userManagerInternal.addUserRestrictionsListener(
                    new UserManagerInternal
                            .UserRestrictionsListener() { // from class:
                                                          // com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda1
                        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
                        public final void onUserRestrictionsChanged(
                                int i4, Bundle bundle, Bundle bundle2) {
                            AbstractMasterSystemService abstractMasterSystemService =
                                    AbstractMasterSystemService.this;
                            String str2 = str;
                            abstractMasterSystemService.getClass();
                            boolean z = bundle.getBoolean(str2, false);
                            synchronized (abstractMasterSystemService.mLock) {
                                try {
                                    if (abstractMasterSystemService.mDisabledByUserRestriction.get(
                                                            i4)
                                                    == z
                                            && abstractMasterSystemService.debug) {
                                        Slog.d(
                                                abstractMasterSystemService.mTag,
                                                "Restriction did not change for user " + i4);
                                        return;
                                    }
                                    Slog.i(
                                            abstractMasterSystemService.mTag,
                                            "Updating for user " + i4 + ": disabled=" + z);
                                    abstractMasterSystemService.mDisabledByUserRestriction.put(
                                            i4, z);
                                    abstractMasterSystemService.getServiceForUserLocked(i4);
                                    abstractMasterSystemService.updateCachedServiceListLocked(
                                            i4, z);
                                } finally {
                                }
                            }
                        }
                    });
        }
        new PackageMonitor() { // from class: com.android.server.infra.AbstractMasterSystemService.1
            public final void handleActiveServiceRestartedLocked(int i4, String str2) {
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                int i5 = abstractMasterSystemService.mServicePackagePolicyFlags & 16;
                String str3 = abstractMasterSystemService.mTag;
                if (i5 == 0) {
                    if (abstractMasterSystemService.debug) {
                        Slog.d(
                                str3,
                                AccountManagerService$$ExternalSyntheticOutline0.m(
                                        i4,
                                        "Removing service for user ",
                                        " because package ",
                                        str2,
                                        " is being restarted"));
                    }
                    AbstractMasterSystemService.this.removeCachedServiceListLocked(i4);
                    AbstractMasterSystemService abstractMasterSystemService2 =
                            AbstractMasterSystemService.this;
                    if ((abstractMasterSystemService2.mServicePackagePolicyFlags & 64) != 0) {
                        if (abstractMasterSystemService2.debug) {
                            Slog.d(
                                    abstractMasterSystemService2.mTag,
                                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                                            i4, "Eagerly recreating service for user "));
                        }
                        AbstractMasterSystemService.this.updateCachedServiceLocked(i4);
                    }
                } else if (abstractMasterSystemService.debug) {
                    Slog.d(
                            str3,
                            AccountManagerService$$ExternalSyntheticOutline0.m(
                                    i4,
                                    "Holding service for user ",
                                    " while package ",
                                    str2,
                                    " is being restarted"));
                }
                AbstractMasterSystemService.this.onServicePackageRestartedLocked(i4);
            }

            public final void handlePackageUpdateLocked(String str2) {
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                int size = abstractMasterSystemService.mServicesCacheList.size();
                for (int i4 = 0; i4 < size; i4++) {
                    List list = (List) abstractMasterSystemService.mServicesCacheList.valueAt(i4);
                    for (int i5 = 0; i5 < list.size(); i5++) {
                        ((AbstractPerUserSystemService) list.get(i5))
                                .handlePackageUpdateLocked(str2);
                    }
                }
            }

            public final boolean onHandleForceStop(
                    Intent intent, String[] strArr, int i4, boolean z) {
                ComponentName serviceComponentName;
                synchronized (AbstractMasterSystemService.this.mLock) {
                    try {
                        AbstractPerUserSystemService peekServiceForUserLocked =
                                AbstractMasterSystemService.this.peekServiceForUserLocked(
                                        getChangingUserId());
                        String str2 = null;
                        if (peekServiceForUserLocked != null
                                && (serviceComponentName =
                                                peekServiceForUserLocked.getServiceComponentName())
                                        != null) {
                            str2 = serviceComponentName.getPackageName();
                        }
                        for (String str3 : strArr) {
                            if (!str3.equals(str2)) {
                                handlePackageUpdateLocked(str3);
                            } else {
                                if (!z) {
                                    return true;
                                }
                                String action = intent.getAction();
                                int changingUserId = getChangingUserId();
                                if ("android.intent.action.PACKAGE_RESTARTED".equals(action)) {
                                    handleActiveServiceRestartedLocked(changingUserId, str2);
                                } else {
                                    AbstractMasterSystemService.this.removeCachedServiceListLocked(
                                            changingUserId);
                                }
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onPackageDataCleared(String str2, int i4) {
                ComponentName serviceComponentName;
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                if (abstractMasterSystemService.verbose) {
                    Slog.v(abstractMasterSystemService.mTag, "onPackageDataCleared(): " + str2);
                }
                int changingUserId = getChangingUserId();
                AbstractMasterSystemService abstractMasterSystemService2 =
                        AbstractMasterSystemService.this;
                ServiceNameBaseResolver serviceNameBaseResolver2 =
                        abstractMasterSystemService2.mServiceNameResolver;
                if (serviceNameBaseResolver2 != null && serviceNameBaseResolver2.mIsMultiple) {
                    synchronized (abstractMasterSystemService2.mLock) {
                        AbstractMasterSystemService abstractMasterSystemService3 =
                                AbstractMasterSystemService.this;
                        if (abstractMasterSystemService3.verbose) {
                            Slog.v(
                                    abstractMasterSystemService3.mTag,
                                    "onServicePackageDataClearedMultiModeLocked("
                                            + changingUserId
                                            + ")");
                        }
                    }
                    return;
                }
                synchronized (abstractMasterSystemService2.mLock) {
                    try {
                        AbstractPerUserSystemService peekServiceForUserLocked =
                                AbstractMasterSystemService.this.peekServiceForUserLocked(
                                        changingUserId);
                        if (peekServiceForUserLocked != null
                                && (serviceComponentName =
                                                peekServiceForUserLocked.getServiceComponentName())
                                        != null
                                && str2.equals(serviceComponentName.getPackageName())) {
                            AbstractMasterSystemService abstractMasterSystemService4 =
                                    AbstractMasterSystemService.this;
                            if (abstractMasterSystemService4.verbose) {
                                Slog.v(
                                        abstractMasterSystemService4.mTag,
                                        "onServicePackageDataCleared(" + changingUserId + ")");
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onPackageModified(String str2) {
                ComponentName unflattenFromString;
                AbstractPerUserSystemService peekServiceForUserLocked;
                synchronized (AbstractMasterSystemService.this.mLock) {
                    try {
                        AbstractMasterSystemService abstractMasterSystemService =
                                AbstractMasterSystemService.this;
                        if (abstractMasterSystemService.verbose) {
                            Slog.v(
                                    abstractMasterSystemService.mTag,
                                    "onPackageModified(): " + str2);
                        }
                        if (AbstractMasterSystemService.this.mServiceNameResolver == null) {
                            return;
                        }
                        int changingUserId = getChangingUserId();
                        String[] defaultServiceNameList =
                                AbstractMasterSystemService.this.mServiceNameResolver
                                        .getDefaultServiceNameList(changingUserId);
                        if (defaultServiceNameList != null) {
                            for (String str3 : defaultServiceNameList) {
                                if (str3 != null
                                        && (unflattenFromString =
                                                        ComponentName.unflattenFromString(str3))
                                                != null
                                        && unflattenFromString.getPackageName().equals(str2)
                                        && (peekServiceForUserLocked =
                                                        AbstractMasterSystemService.this
                                                                .peekServiceForUserLocked(
                                                                        changingUserId))
                                                != null
                                        && peekServiceForUserLocked.getServiceComponentName()
                                                == null) {
                                    AbstractMasterSystemService abstractMasterSystemService2 =
                                            AbstractMasterSystemService.this;
                                    if (abstractMasterSystemService2.verbose) {
                                        Slog.v(abstractMasterSystemService2.mTag, "update cached");
                                    }
                                    AbstractMasterSystemService.this.updateCachedServiceLocked(
                                            changingUserId);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onPackageRemoved(String str2, int i4) {
                ComponentName serviceComponentName;
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                ServiceNameBaseResolver serviceNameBaseResolver2 =
                        abstractMasterSystemService.mServiceNameResolver;
                if (serviceNameBaseResolver2 != null && serviceNameBaseResolver2.mIsMultiple) {
                    int changingUserId = getChangingUserId();
                    synchronized (AbstractMasterSystemService.this.mLock) {
                        AbstractMasterSystemService.this.handlePackageRemovedMultiModeLocked(
                                changingUserId, str2);
                    }
                    return;
                }
                synchronized (abstractMasterSystemService.mLock) {
                    try {
                        int changingUserId2 = getChangingUserId();
                        AbstractPerUserSystemService peekServiceForUserLocked =
                                AbstractMasterSystemService.this.peekServiceForUserLocked(
                                        changingUserId2);
                        if (peekServiceForUserLocked != null
                                && (serviceComponentName =
                                                peekServiceForUserLocked.getServiceComponentName())
                                        != null
                                && str2.equals(serviceComponentName.getPackageName())) {
                            synchronized (AbstractMasterSystemService.this.mLock) {
                                AbstractMasterSystemService.this.removeCachedServiceListLocked(
                                        changingUserId2);
                            }
                            String serviceSettingsProperty =
                                    AbstractMasterSystemService.this.getServiceSettingsProperty();
                            if (serviceSettingsProperty != null) {
                                Settings.Secure.putStringForUser(
                                        AbstractMasterSystemService.this
                                                .getContext()
                                                .getContentResolver(),
                                        serviceSettingsProperty,
                                        null,
                                        changingUserId2);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onPackageUpdateFinished(String str2, int i4) {
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                if (abstractMasterSystemService.verbose) {
                    Slog.v(abstractMasterSystemService.mTag, "onPackageUpdateFinished(): " + str2);
                }
                int changingUserId = getChangingUserId();
                synchronized (AbstractMasterSystemService.this.mLock) {
                    try {
                        SparseArray sparseArray =
                                AbstractMasterSystemService.this.mUpdatingPackageNames;
                        if (str2.equals(
                                sparseArray == null
                                        ? null
                                        : (String) sparseArray.get(changingUserId))) {
                            SparseArray sparseArray2 =
                                    AbstractMasterSystemService.this.mUpdatingPackageNames;
                            if (sparseArray2 != null) {
                                sparseArray2.remove(changingUserId);
                                if (AbstractMasterSystemService.this.mUpdatingPackageNames.size()
                                        == 0) {
                                    AbstractMasterSystemService.this.mUpdatingPackageNames = null;
                                }
                            }
                            AbstractMasterSystemService.this.onServicePackageUpdatedLocked(
                                    changingUserId);
                        } else {
                            handlePackageUpdateLocked(str2);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            public final void onPackageUpdateStarted(String str2, int i4) {
                ComponentName serviceComponentName;
                AbstractMasterSystemService abstractMasterSystemService =
                        AbstractMasterSystemService.this;
                if (abstractMasterSystemService.verbose) {
                    Slog.v(abstractMasterSystemService.mTag, "onPackageUpdateStarted(): " + str2);
                }
                AbstractPerUserSystemService peekServiceForUserLocked =
                        AbstractMasterSystemService.this.peekServiceForUserLocked(
                                getChangingUserId());
                String str3 = null;
                if (peekServiceForUserLocked != null
                        && (serviceComponentName =
                                        peekServiceForUserLocked.getServiceComponentName())
                                != null) {
                    str3 = serviceComponentName.getPackageName();
                }
                if (str2.equals(str3)) {
                    int changingUserId = getChangingUserId();
                    synchronized (AbstractMasterSystemService.this.mLock) {
                        try {
                            AbstractMasterSystemService abstractMasterSystemService2 =
                                    AbstractMasterSystemService.this;
                            if (abstractMasterSystemService2.mUpdatingPackageNames == null) {
                                abstractMasterSystemService2.mUpdatingPackageNames =
                                        new SparseArray(
                                                AbstractMasterSystemService.this.mServicesCacheList
                                                        .size());
                            }
                            AbstractMasterSystemService.this.mUpdatingPackageNames.put(
                                    changingUserId, str2);
                            AbstractMasterSystemService.this.onServicePackageUpdatingLocked(
                                    changingUserId);
                            AbstractMasterSystemService abstractMasterSystemService3 =
                                    AbstractMasterSystemService.this;
                            if ((abstractMasterSystemService3.mServicePackagePolicyFlags & 1)
                                    == 0) {
                                if (abstractMasterSystemService3.debug) {
                                    Slog.d(
                                            abstractMasterSystemService3.mTag,
                                            "Removing service for user "
                                                    + changingUserId
                                                    + " because package "
                                                    + str3
                                                    + " is being updated");
                                }
                                AbstractMasterSystemService.this.removeCachedServiceListLocked(
                                        changingUserId);
                                AbstractMasterSystemService abstractMasterSystemService4 =
                                        AbstractMasterSystemService.this;
                                if ((abstractMasterSystemService4.mServicePackagePolicyFlags & 4)
                                        != 0) {
                                    if (abstractMasterSystemService4.debug) {
                                        Slog.d(
                                                abstractMasterSystemService4.mTag,
                                                "Eagerly recreating service for user "
                                                        + changingUserId);
                                    }
                                    AbstractMasterSystemService.this.getServiceForUserLocked(
                                            changingUserId);
                                }
                            } else if (abstractMasterSystemService3.debug) {
                                Slog.d(
                                        abstractMasterSystemService3.mTag,
                                        "Holding service for user "
                                                + changingUserId
                                                + " while package "
                                                + str3
                                                + " is being updated");
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }.register(getContext(), (Looper) null, UserHandle.ALL, true);
    }

    public void assertCalledByPackageOwner(String str) {
        Objects.requireNonNull(str);
        int callingUid = Binder.getCallingUid();
        String[] packagesForUid = getContext().getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            for (String str2 : packagesForUid) {
                if (str.equals(str2)) {
                    return;
                }
            }
        }
        throw new SecurityException(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        callingUid, "UID ", " does not own ", str));
    }

    public void dumpLocked(PrintWriter printWriter) {
        boolean z = this.debug;
        boolean z2 = this.verbose;
        try {
            this.verbose = true;
            this.debug = true;
            int size = this.mServicesCacheList.size();
            printWriter.print("");
            printWriter.print("Debug: ");
            printWriter.print(z);
            printWriter.print(" Verbose: ");
            printWriter.println(z2);
            printWriter.print("Package policy flags: ");
            printWriter.println(this.mServicePackagePolicyFlags);
            if (this.mUpdatingPackageNames != null) {
                printWriter.print("Packages being updated: ");
                printWriter.println(this.mUpdatingPackageNames);
            }
            dumpSupportedUsers(printWriter, "");
            if (this.mServiceNameResolver != null) {
                printWriter.print("");
                printWriter.print("Name resolver: ");
                this.mServiceNameResolver.dumpShort(printWriter);
                printWriter.println();
                List supportedUsers = getSupportedUsers();
                int i = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) supportedUsers;
                    if (i >= arrayList.size()) {
                        break;
                    }
                    int i2 = ((UserInfo) arrayList.get(i)).id;
                    printWriter.print("    ");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    this.mServiceNameResolver.dumpShort(i2, printWriter);
                    printWriter.println();
                    i++;
                }
            }
            printWriter.print("");
            printWriter.print("Users disabled by restriction: ");
            printWriter.println(this.mDisabledByUserRestriction);
            printWriter.print("");
            printWriter.print("Allow instant service: ");
            printWriter.println(this.mAllowInstantService);
            String serviceSettingsProperty = getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                printWriter.print("");
                printWriter.print("Settings property: ");
                printWriter.println(serviceSettingsProperty);
            }
            printWriter.print("");
            printWriter.print("Cached services: ");
            if (size == 0) {
                printWriter.println("none");
            } else {
                printWriter.println(size);
                for (int i3 = 0; i3 < size; i3++) {
                    printWriter.print("");
                    printWriter.print("Service at ");
                    printWriter.print(i3);
                    printWriter.println(": ");
                    List list = (List) this.mServicesCacheList.valueAt(i3);
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        AbstractPerUserSystemService abstractPerUserSystemService =
                                (AbstractPerUserSystemService) list.get(i4);
                        synchronized (abstractPerUserSystemService.mLock) {
                            abstractPerUserSystemService.dumpLocked(printWriter);
                        }
                    }
                    printWriter.println();
                }
            }
            this.debug = z;
            this.verbose = z2;
        } catch (Throwable th) {
            this.debug = z;
            this.verbose = z2;
            throw th;
        }
    }

    public void enforceCallingPermissionForManagement() {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    public int getMaximumTemporaryServiceDurationMs() {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    public final AbstractPerUserSystemService getServiceForUserLocked(int i) {
        List serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null || serviceListForUserLocked.size() == 0) {
            return null;
        }
        return (AbstractPerUserSystemService) serviceListForUserLocked.get(0);
    }

    public final List getServiceListForUserLocked(int i) {
        List list;
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        false,
                        null,
                        null);
        List list2 = (List) this.mServicesCacheList.get(handleIncomingUser);
        if (list2 != null && list2.size() != 0) {
            return list2;
        }
        boolean isDisabledLocked = isDisabledLocked(i);
        ServiceNameBaseResolver serviceNameBaseResolver = this.mServiceNameResolver;
        if (serviceNameBaseResolver == null || !serviceNameBaseResolver.mIsMultiple) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(newServiceLocked(handleIncomingUser, isDisabledLocked));
            list = arrayList;
        } else {
            list =
                    newServiceListLocked(
                            handleIncomingUser, serviceNameBaseResolver.getServiceNameList(i));
        }
        if (!isDisabledLocked) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                onServiceEnabledLocked(
                        (AbstractPerUserSystemService) list.get(i2), handleIncomingUser);
            }
        }
        this.mServicesCacheList.put(i, list);
        return list;
    }

    public String getServiceSettingsProperty() {
        return null;
    }

    public final List getSupportedUsers() {
        if (this.mUm == null) {
            if (this.verbose) {
                Slog.v(this.mTag, "lazy-loading UserManagerInternal");
            }
            this.mUm = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        UserInfo[] userInfos = this.mUm.getUserInfos();
        ArrayList arrayList = new ArrayList(userInfos.length);
        for (UserInfo userInfo : userInfos) {
            if (isUserSupported(new SystemService.TargetUser(userInfo))) {
                arrayList.add(userInfo);
            }
        }
        return arrayList;
    }

    public void handlePackageRemovedMultiModeLocked(int i, String str) {
        if (this.verbose) {
            Slog.v(
                    this.mTag,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "handlePackageRemovedMultiModeLocked(", ")"));
        }
    }

    public final boolean isBindInstantServiceAllowed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAllowInstantService;
        }
        return z;
    }

    public final boolean isDefaultServiceEnabled(int i) {
        boolean z;
        enforceCallingPermissionForManagement();
        if (this.mServiceNameResolver == null) {
            return false;
        }
        synchronized (this.mLock) {
            ServiceNameBaseResolver serviceNameBaseResolver = this.mServiceNameResolver;
            synchronized (serviceNameBaseResolver.mLock) {
                z = !serviceNameBaseResolver.mDefaultServicesDisabled.get(i);
            }
        }
        return z;
    }

    public boolean isDisabledLocked(int i) {
        SparseBooleanArray sparseBooleanArray = this.mDisabledByUserRestriction;
        return sparseBooleanArray != null && sparseBooleanArray.get(i);
    }

    public List newServiceListLocked(int i, String[] strArr) {
        throw new UnsupportedOperationException("newServiceListLocked not implemented. ");
    }

    public abstract AbstractPerUserSystemService newServiceLocked(int i, boolean z);

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            new SettingsObserver(BackgroundThread.getHandler());
        }
    }

    public void onServiceEnabledLocked(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {}

    public void onServiceNameChanged(int i, String str, boolean z) {
        synchronized (this.mLock) {
            updateCachedServiceListLocked(i, isDisabledLocked(i));
        }
    }

    public void onServicePackageRestartedLocked(int i) {
        if (this.verbose) {
            Slog.v(
                    this.mTag,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "onServicePackageRestarted(", ")"));
        }
    }

    public void onServicePackageUpdatedLocked(int i) {
        if (this.verbose) {
            Slog.v(
                    this.mTag,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "onServicePackageUpdated(", ")"));
        }
    }

    public void onServicePackageUpdatingLocked(int i) {
        if (this.verbose) {
            Slog.v(
                    this.mTag,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "onServicePackageUpdatingLocked(", ")"));
        }
    }

    public void onServiceRemoved(
            AbstractPerUserSystemService abstractPerUserSystemService, int i) {}

    public void onSettingsChanged(int i, String str) {}

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            removeCachedServiceListLocked(targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            updateCachedServiceLocked(targetUser.getUserIdentifier());
        }
    }

    public final AbstractPerUserSystemService peekServiceForUserLocked(int i) {
        List peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked == null || peekServiceListForUserLocked.size() == 0) {
            return null;
        }
        return (AbstractPerUserSystemService) peekServiceListForUserLocked.get(0);
    }

    public final List peekServiceListForUserLocked(int i) {
        return (List)
                this.mServicesCacheList.get(
                        ActivityManager.handleIncomingUser(
                                Binder.getCallingPid(),
                                Binder.getCallingUid(),
                                i,
                                false,
                                false,
                                null,
                                null));
    }

    public void registerForExtraSettingsChanges(
            ContentResolver contentResolver, ContentObserver contentObserver) {}

    public final void removeCachedServiceListLocked(int i) {
        List peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked != null) {
            this.mServicesCacheList.delete(i);
            for (int i2 = 0; i2 < peekServiceListForUserLocked.size(); i2++) {
                onServiceRemoved(
                        (AbstractPerUserSystemService) peekServiceListForUserLocked.get(i2), i);
            }
        }
    }

    public final void resetTemporaryService(int i) {
        Slog.i(this.mTag, "resetTemporaryService(): " + i);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            AbstractPerUserSystemService serviceForUserLocked = getServiceForUserLocked(i);
            if (serviceForUserLocked != null) {
                serviceForUserLocked.mMaster.mServiceNameResolver.resetTemporaryService(
                        serviceForUserLocked.mUserId);
            }
        }
    }

    public final void setAllowInstantService(boolean z) {
        Slog.i(this.mTag, "setAllowInstantService(): " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            this.mAllowInstantService = z;
        }
    }

    public final boolean setDefaultServiceEnabled(int i, boolean z) {
        Slog.i(this.mTag, "setDefaultServiceEnabled() for userId " + i + ": " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            try {
                ServiceNameBaseResolver serviceNameBaseResolver = this.mServiceNameResolver;
                if (serviceNameBaseResolver == null) {
                    return false;
                }
                if (serviceNameBaseResolver.setDefaultServiceEnabled(i, z)) {
                    AbstractPerUserSystemService peekServiceForUserLocked =
                            peekServiceForUserLocked(i);
                    if (peekServiceForUserLocked != null) {
                        peekServiceForUserLocked.removeSelfFromCache();
                    }
                    updateCachedServiceLocked(i);
                    return true;
                }
                if (this.verbose) {
                    Slog.v(this.mTag, "setDefaultServiceEnabled(" + i + "): already " + z);
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setTemporaryService(int i, String str, int i2) {
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i, "setTemporaryService(", ") to ", str, " for "),
                i2,
                "ms",
                this.mTag);
        if (this.mServiceNameResolver == null) {
            return;
        }
        enforceCallingPermissionForManagement();
        Objects.requireNonNull(str);
        int maximumTemporaryServiceDurationMs = getMaximumTemporaryServiceDurationMs();
        if (i2 > maximumTemporaryServiceDurationMs) {
            throw new IllegalArgumentException(
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            maximumTemporaryServiceDurationMs,
                            i2,
                            "Max duration is ",
                            " (called with ",
                            ")"));
        }
        synchronized (this.mLock) {
            try {
                AbstractPerUserSystemService peekServiceForUserLocked = peekServiceForUserLocked(i);
                if (peekServiceForUserLocked != null) {
                    peekServiceForUserLocked.removeSelfFromCache();
                }
                this.mServiceNameResolver.setTemporaryServices(i, i2, new String[] {str});
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateCachedServiceListLocked(int i, boolean z) {
        ServiceNameBaseResolver serviceNameBaseResolver = this.mServiceNameResolver;
        if (serviceNameBaseResolver != null && serviceNameBaseResolver.mIsMultiple) {
            int handleIncomingUser =
                    ActivityManager.handleIncomingUser(
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            false,
                            false,
                            null,
                            null);
            new ArrayList();
            synchronized (this.mLock) {
                removeCachedServiceListLocked(handleIncomingUser);
                getServiceListForUserLocked(i);
            }
            return;
        }
        List serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null) {
            return;
        }
        for (int i2 = 0; i2 < serviceListForUserLocked.size(); i2++) {
            AbstractPerUserSystemService abstractPerUserSystemService =
                    (AbstractPerUserSystemService) serviceListForUserLocked.get(i2);
            if (abstractPerUserSystemService != null) {
                synchronized (abstractPerUserSystemService.mLock) {
                    try {
                        abstractPerUserSystemService.updateLocked(z);
                        if (abstractPerUserSystemService.isEnabledLocked()) {
                            onServiceEnabledLocked(
                                    (AbstractPerUserSystemService) serviceListForUserLocked.get(i2),
                                    i);
                        } else {
                            removeCachedServiceListLocked(i);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void updateCachedServiceLocked(int i) {
        updateCachedServiceListLocked(i, isDisabledLocked(i));
    }
}
