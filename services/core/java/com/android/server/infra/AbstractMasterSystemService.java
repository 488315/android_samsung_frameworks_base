package com.android.server.infra;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
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
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.infra.ServiceNameResolver;
import com.android.server.pm.UserManagerInternal;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public abstract class AbstractMasterSystemService extends SystemService {
    public boolean debug;
    public boolean mAllowInstantService;
    public final SparseBooleanArray mDisabledByUserRestriction;
    public final Object mLock;
    public final ServiceNameResolver mServiceNameResolver;
    public final int mServicePackagePolicyFlags;
    public final SparseArray mServicesCacheList;
    public final String mTag;
    public UserManagerInternal mUm;
    public SparseArray mUpdatingPackageNames;
    public boolean verbose;

    public interface Visitor {
        void visit(Object obj);
    }

    public String getServiceSettingsProperty() {
        return null;
    }

    public abstract AbstractPerUserSystemService newServiceLocked(int i, boolean z);

    public void onServiceEnabledLocked(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
    }

    public void onServiceRemoved(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
    }

    public void onSettingsChanged(int i, String str) {
    }

    public void registerForExtraSettingsChanges(ContentResolver contentResolver, ContentObserver contentObserver) {
    }

    public AbstractMasterSystemService(Context context, ServiceNameResolver serviceNameResolver, String str) {
        this(context, serviceNameResolver, str, 34);
    }

    public AbstractMasterSystemService(Context context, ServiceNameResolver serviceNameResolver, final String str, int i) {
        super(context);
        this.mTag = getClass().getSimpleName();
        this.mLock = new Object();
        this.verbose = false;
        this.debug = false;
        this.mServicesCacheList = new SparseArray();
        i = (i & 7) == 0 ? i | 2 : i;
        this.mServicePackagePolicyFlags = (i & 112) == 0 ? i | 32 : i;
        this.mServiceNameResolver = serviceNameResolver;
        if (serviceNameResolver != null) {
            serviceNameResolver.setOnTemporaryServiceNameChangedCallback(new ServiceNameResolver.NameResolverListener() { // from class: com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda0
                @Override // com.android.server.infra.ServiceNameResolver.NameResolverListener
                public final void onNameResolved(int i2, String str2, boolean z) {
                    AbstractMasterSystemService.this.onServiceNameChanged(i2, str2, z);
                }
            });
        }
        if (str == null) {
            this.mDisabledByUserRestriction = null;
        } else {
            this.mDisabledByUserRestriction = new SparseBooleanArray();
            UserManagerInternal userManagerInternal = getUserManagerInternal();
            List supportedUsers = getSupportedUsers();
            for (int i2 = 0; i2 < supportedUsers.size(); i2++) {
                int i3 = ((UserInfo) supportedUsers.get(i2)).id;
                boolean userRestriction = userManagerInternal.getUserRestriction(i3, str);
                if (userRestriction) {
                    Slog.i(this.mTag, "Disabling by restrictions user " + i3);
                    this.mDisabledByUserRestriction.put(i3, userRestriction);
                }
            }
            userManagerInternal.addUserRestrictionsListener(new UserManagerInternal.UserRestrictionsListener() { // from class: com.android.server.infra.AbstractMasterSystemService$$ExternalSyntheticLambda1
                @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
                public final void onUserRestrictionsChanged(int i4, Bundle bundle, Bundle bundle2) {
                    AbstractMasterSystemService.this.lambda$new$0(str, i4, bundle, bundle2);
                }
            });
        }
        startTrackingPackageChanges();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str, int i, Bundle bundle, Bundle bundle2) {
        boolean z = bundle.getBoolean(str, false);
        synchronized (this.mLock) {
            if (this.mDisabledByUserRestriction.get(i) == z && this.debug) {
                Slog.d(this.mTag, "Restriction did not change for user " + i);
                return;
            }
            Slog.i(this.mTag, "Updating for user " + i + ": disabled=" + z);
            this.mDisabledByUserRestriction.put(i, z);
            updateCachedServiceLocked(i, z);
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            new SettingsObserver(BackgroundThread.getHandler());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            updateCachedServiceLocked(targetUser.getUserIdentifier());
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            removeCachedServiceListLocked(targetUser.getUserIdentifier());
        }
    }

    public final boolean getAllowInstantService() {
        boolean z;
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            z = this.mAllowInstantService;
        }
        return z;
    }

    public final boolean isBindInstantServiceAllowed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mAllowInstantService;
        }
        return z;
    }

    public final void setAllowInstantService(boolean z) {
        Slog.i(this.mTag, "setAllowInstantService(): " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            this.mAllowInstantService = z;
        }
    }

    public final void setTemporaryService(int i, String str, int i2) {
        Slog.i(this.mTag, "setTemporaryService(" + i + ") to " + str + " for " + i2 + "ms");
        if (this.mServiceNameResolver == null) {
            return;
        }
        enforceCallingPermissionForManagement();
        Objects.requireNonNull(str);
        int maximumTemporaryServiceDurationMs = getMaximumTemporaryServiceDurationMs();
        if (i2 > maximumTemporaryServiceDurationMs) {
            throw new IllegalArgumentException("Max duration is " + maximumTemporaryServiceDurationMs + " (called with " + i2 + ")");
        }
        synchronized (this.mLock) {
            AbstractPerUserSystemService peekServiceForUserLocked = peekServiceForUserLocked(i);
            if (peekServiceForUserLocked != null) {
                peekServiceForUserLocked.removeSelfFromCache();
            }
            this.mServiceNameResolver.setTemporaryService(i, str, i2);
        }
    }

    public final void setTemporaryServices(int i, String[] strArr, int i2) {
        Slog.i(this.mTag, "setTemporaryService(" + i + ") to " + Arrays.toString(strArr) + " for " + i2 + "ms");
        if (this.mServiceNameResolver == null) {
            return;
        }
        enforceCallingPermissionForManagement();
        Objects.requireNonNull(strArr);
        int maximumTemporaryServiceDurationMs = getMaximumTemporaryServiceDurationMs();
        if (i2 > maximumTemporaryServiceDurationMs) {
            throw new IllegalArgumentException("Max duration is " + maximumTemporaryServiceDurationMs + " (called with " + i2 + ")");
        }
        synchronized (this.mLock) {
            AbstractPerUserSystemService peekServiceForUserLocked = peekServiceForUserLocked(i);
            if (peekServiceForUserLocked != null) {
                peekServiceForUserLocked.removeSelfFromCache();
            }
            this.mServiceNameResolver.setTemporaryServices(i, strArr, i2);
        }
    }

    public final boolean setDefaultServiceEnabled(int i, boolean z) {
        Slog.i(this.mTag, "setDefaultServiceEnabled() for userId " + i + ": " + z);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            ServiceNameResolver serviceNameResolver = this.mServiceNameResolver;
            if (serviceNameResolver == null) {
                return false;
            }
            if (!serviceNameResolver.setDefaultServiceEnabled(i, z)) {
                if (this.verbose) {
                    Slog.v(this.mTag, "setDefaultServiceEnabled(" + i + "): already " + z);
                }
                return false;
            }
            AbstractPerUserSystemService peekServiceForUserLocked = peekServiceForUserLocked(i);
            if (peekServiceForUserLocked != null) {
                peekServiceForUserLocked.removeSelfFromCache();
            }
            updateCachedServiceLocked(i);
            return true;
        }
    }

    public final boolean isDefaultServiceEnabled(int i) {
        boolean isDefaultServiceEnabled;
        enforceCallingPermissionForManagement();
        if (this.mServiceNameResolver == null) {
            return false;
        }
        synchronized (this.mLock) {
            isDefaultServiceEnabled = this.mServiceNameResolver.isDefaultServiceEnabled(i);
        }
        return isDefaultServiceEnabled;
    }

    public int getMaximumTemporaryServiceDurationMs() {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    public final void resetTemporaryService(int i) {
        Slog.i(this.mTag, "resetTemporaryService(): " + i);
        enforceCallingPermissionForManagement();
        synchronized (this.mLock) {
            AbstractPerUserSystemService serviceForUserLocked = getServiceForUserLocked(i);
            if (serviceForUserLocked != null) {
                serviceForUserLocked.resetTemporaryServiceLocked();
            }
        }
    }

    public void enforceCallingPermissionForManagement() {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    public List newServiceListLocked(int i, boolean z, String[] strArr) {
        throw new UnsupportedOperationException("newServiceListLocked not implemented. ");
    }

    public AbstractPerUserSystemService getServiceForUserLocked(int i) {
        List serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null || serviceListForUserLocked.size() == 0) {
            return null;
        }
        return (AbstractPerUserSystemService) serviceListForUserLocked.get(0);
    }

    public List getServiceListForUserLocked(int i) {
        List arrayList;
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, null, null);
        List list = (List) this.mServicesCacheList.get(handleIncomingUser);
        if (list != null && list.size() != 0) {
            return list;
        }
        boolean isDisabledLocked = isDisabledLocked(i);
        ServiceNameResolver serviceNameResolver = this.mServiceNameResolver;
        if (serviceNameResolver != null && serviceNameResolver.isConfiguredInMultipleMode()) {
            arrayList = newServiceListLocked(handleIncomingUser, isDisabledLocked, this.mServiceNameResolver.getServiceNameList(i));
        } else {
            arrayList = new ArrayList();
            arrayList.add(newServiceLocked(handleIncomingUser, isDisabledLocked));
        }
        if (!isDisabledLocked) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                onServiceEnabledLocked((AbstractPerUserSystemService) arrayList.get(i2), handleIncomingUser);
            }
        }
        this.mServicesCacheList.put(i, arrayList);
        return arrayList;
    }

    public AbstractPerUserSystemService peekServiceForUserLocked(int i) {
        List peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked == null || peekServiceListForUserLocked.size() == 0) {
            return null;
        }
        return (AbstractPerUserSystemService) peekServiceListForUserLocked.get(0);
    }

    public List peekServiceListForUserLocked(int i) {
        return (List) this.mServicesCacheList.get(ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, null, null));
    }

    public void updateCachedServiceLocked(int i) {
        updateCachedServiceListLocked(i, isDisabledLocked(i));
    }

    public boolean isDisabledLocked(int i) {
        SparseBooleanArray sparseBooleanArray = this.mDisabledByUserRestriction;
        return sparseBooleanArray != null && sparseBooleanArray.get(i);
    }

    public AbstractPerUserSystemService updateCachedServiceLocked(int i, boolean z) {
        AbstractPerUserSystemService serviceForUserLocked = getServiceForUserLocked(i);
        updateCachedServiceListLocked(i, z);
        return serviceForUserLocked;
    }

    public List updateCachedServiceListLocked(int i, boolean z) {
        ServiceNameResolver serviceNameResolver = this.mServiceNameResolver;
        if (serviceNameResolver != null && serviceNameResolver.isConfiguredInMultipleMode()) {
            return updateCachedServiceListMultiModeLocked(i, z);
        }
        List serviceListForUserLocked = getServiceListForUserLocked(i);
        if (serviceListForUserLocked == null) {
            return null;
        }
        for (int i2 = 0; i2 < serviceListForUserLocked.size(); i2++) {
            AbstractPerUserSystemService abstractPerUserSystemService = (AbstractPerUserSystemService) serviceListForUserLocked.get(i2);
            if (abstractPerUserSystemService != null) {
                synchronized (abstractPerUserSystemService.mLock) {
                    abstractPerUserSystemService.updateLocked(z);
                    if (!abstractPerUserSystemService.isEnabledLocked()) {
                        removeCachedServiceListLocked(i);
                    } else {
                        onServiceEnabledLocked((AbstractPerUserSystemService) serviceListForUserLocked.get(i2), i);
                    }
                }
            }
        }
        return serviceListForUserLocked;
    }

    public final List updateCachedServiceListMultiModeLocked(int i, boolean z) {
        List serviceListForUserLocked;
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, null, null);
        new ArrayList();
        synchronized (this.mLock) {
            removeCachedServiceListLocked(handleIncomingUser);
            serviceListForUserLocked = getServiceListForUserLocked(i);
        }
        return serviceListForUserLocked;
    }

    public final List removeCachedServiceListLocked(int i) {
        List peekServiceListForUserLocked = peekServiceListForUserLocked(i);
        if (peekServiceListForUserLocked != null) {
            this.mServicesCacheList.delete(i);
            for (int i2 = 0; i2 < peekServiceListForUserLocked.size(); i2++) {
                onServiceRemoved((AbstractPerUserSystemService) peekServiceListForUserLocked.get(i2), i);
            }
        }
        return peekServiceListForUserLocked;
    }

    public void onServicePackageUpdatingLocked(int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "onServicePackageUpdatingLocked(" + i + ")");
        }
    }

    public void onServicePackageUpdatedLocked(int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "onServicePackageUpdated(" + i + ")");
        }
    }

    public void onServicePackageDataClearedLocked(int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "onServicePackageDataCleared(" + i + ")");
        }
    }

    public void onServicePackageRestartedLocked(int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "onServicePackageRestarted(" + i + ")");
        }
    }

    public void onServiceNameChanged(int i, String str, boolean z) {
        synchronized (this.mLock) {
            updateCachedServiceListLocked(i, isDisabledLocked(i));
        }
    }

    public void visitServicesLocked(Visitor visitor) {
        int size = this.mServicesCacheList.size();
        for (int i = 0; i < size; i++) {
            List list = (List) this.mServicesCacheList.valueAt(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                visitor.visit((AbstractPerUserSystemService) list.get(i2));
            }
        }
    }

    public void clearCacheLocked() {
        this.mServicesCacheList.clear();
    }

    public UserManagerInternal getUserManagerInternal() {
        if (this.mUm == null) {
            if (this.verbose) {
                Slog.v(this.mTag, "lazy-loading UserManagerInternal");
            }
            this.mUm = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        }
        return this.mUm;
    }

    public List getSupportedUsers() {
        UserInfo[] userInfos = getUserManagerInternal().getUserInfos();
        ArrayList arrayList = new ArrayList(userInfos.length);
        for (UserInfo userInfo : userInfos) {
            if (isUserSupported(new SystemService.TargetUser(userInfo))) {
                arrayList.add(userInfo);
            }
        }
        return arrayList;
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
        throw new SecurityException("UID " + callingUid + " does not own " + str);
    }

    public void dumpLocked(String str, PrintWriter printWriter) {
        boolean z = this.debug;
        boolean z2 = this.verbose;
        try {
            this.verbose = true;
            this.debug = true;
            int size = this.mServicesCacheList.size();
            printWriter.print(str);
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
            dumpSupportedUsers(printWriter, str);
            if (this.mServiceNameResolver != null) {
                printWriter.print(str);
                printWriter.print("Name resolver: ");
                this.mServiceNameResolver.dumpShort(printWriter);
                printWriter.println();
                List supportedUsers = getSupportedUsers();
                for (int i = 0; i < supportedUsers.size(); i++) {
                    int i2 = ((UserInfo) supportedUsers.get(i)).id;
                    printWriter.print("    ");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    this.mServiceNameResolver.dumpShort(printWriter, i2);
                    printWriter.println();
                }
            }
            printWriter.print(str);
            printWriter.print("Users disabled by restriction: ");
            printWriter.println(this.mDisabledByUserRestriction);
            printWriter.print(str);
            printWriter.print("Allow instant service: ");
            printWriter.println(this.mAllowInstantService);
            String serviceSettingsProperty = getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                printWriter.print(str);
                printWriter.print("Settings property: ");
                printWriter.println(serviceSettingsProperty);
            }
            printWriter.print(str);
            printWriter.print("Cached services: ");
            if (size == 0) {
                printWriter.println("none");
            } else {
                printWriter.println(size);
                for (int i3 = 0; i3 < size; i3++) {
                    printWriter.print(str);
                    printWriter.print("Service at ");
                    printWriter.print(i3);
                    printWriter.println(": ");
                    List list = (List) this.mServicesCacheList.valueAt(i3);
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        AbstractPerUserSystemService abstractPerUserSystemService = (AbstractPerUserSystemService) list.get(i4);
                        synchronized (abstractPerUserSystemService.mLock) {
                            abstractPerUserSystemService.dumpLocked("    ", printWriter);
                        }
                    }
                    printWriter.println();
                }
            }
        } finally {
            this.debug = z;
            this.verbose = z2;
        }
    }

    /* renamed from: com.android.server.infra.AbstractMasterSystemService$1 */
    public class C16531 extends PackageMonitor {
        public C16531() {
        }

        public void onPackageUpdateStarted(String str, int i) {
            AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(abstractMasterSystemService.mTag, "onPackageUpdateStarted(): " + str);
            }
            String activeServicePackageNameLocked = getActiveServicePackageNameLocked();
            if (str.equals(activeServicePackageNameLocked)) {
                int changingUserId = getChangingUserId();
                synchronized (AbstractMasterSystemService.this.mLock) {
                    if (AbstractMasterSystemService.this.mUpdatingPackageNames == null) {
                        AbstractMasterSystemService.this.mUpdatingPackageNames = new SparseArray(AbstractMasterSystemService.this.mServicesCacheList.size());
                    }
                    AbstractMasterSystemService.this.mUpdatingPackageNames.put(changingUserId, str);
                    AbstractMasterSystemService.this.onServicePackageUpdatingLocked(changingUserId);
                    if ((AbstractMasterSystemService.this.mServicePackagePolicyFlags & 1) != 0) {
                        AbstractMasterSystemService abstractMasterSystemService2 = AbstractMasterSystemService.this;
                        if (abstractMasterSystemService2.debug) {
                            Slog.d(abstractMasterSystemService2.mTag, "Holding service for user " + changingUserId + " while package " + activeServicePackageNameLocked + " is being updated");
                        }
                    } else {
                        AbstractMasterSystemService abstractMasterSystemService3 = AbstractMasterSystemService.this;
                        if (abstractMasterSystemService3.debug) {
                            Slog.d(abstractMasterSystemService3.mTag, "Removing service for user " + changingUserId + " because package " + activeServicePackageNameLocked + " is being updated");
                        }
                        AbstractMasterSystemService.this.removeCachedServiceListLocked(changingUserId);
                        if ((AbstractMasterSystemService.this.mServicePackagePolicyFlags & 4) != 0) {
                            AbstractMasterSystemService abstractMasterSystemService4 = AbstractMasterSystemService.this;
                            if (abstractMasterSystemService4.debug) {
                                Slog.d(abstractMasterSystemService4.mTag, "Eagerly recreating service for user " + changingUserId);
                            }
                            AbstractMasterSystemService.this.getServiceForUserLocked(changingUserId);
                        }
                    }
                }
            }
        }

        public void onPackageUpdateFinished(String str, int i) {
            AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(abstractMasterSystemService.mTag, "onPackageUpdateFinished(): " + str);
            }
            int changingUserId = getChangingUserId();
            synchronized (AbstractMasterSystemService.this.mLock) {
                if (str.equals(AbstractMasterSystemService.this.mUpdatingPackageNames == null ? null : (String) AbstractMasterSystemService.this.mUpdatingPackageNames.get(changingUserId))) {
                    if (AbstractMasterSystemService.this.mUpdatingPackageNames != null) {
                        AbstractMasterSystemService.this.mUpdatingPackageNames.remove(changingUserId);
                        if (AbstractMasterSystemService.this.mUpdatingPackageNames.size() == 0) {
                            AbstractMasterSystemService.this.mUpdatingPackageNames = null;
                        }
                    }
                    AbstractMasterSystemService.this.onServicePackageUpdatedLocked(changingUserId);
                } else {
                    handlePackageUpdateLocked(str);
                }
            }
        }

        public void onPackageRemoved(String str, int i) {
            ComponentName serviceComponentName;
            ServiceNameResolver serviceNameResolver = AbstractMasterSystemService.this.mServiceNameResolver;
            if (serviceNameResolver != null && serviceNameResolver.isConfiguredInMultipleMode()) {
                int changingUserId = getChangingUserId();
                synchronized (AbstractMasterSystemService.this.mLock) {
                    AbstractMasterSystemService.this.handlePackageRemovedMultiModeLocked(str, changingUserId);
                }
                return;
            }
            synchronized (AbstractMasterSystemService.this.mLock) {
                int changingUserId2 = getChangingUserId();
                AbstractPerUserSystemService peekServiceForUserLocked = AbstractMasterSystemService.this.peekServiceForUserLocked(changingUserId2);
                if (peekServiceForUserLocked != null && (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) != null && str.equals(serviceComponentName.getPackageName())) {
                    handleActiveServiceRemoved(changingUserId2);
                }
            }
        }

        public boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
            synchronized (AbstractMasterSystemService.this.mLock) {
                String activeServicePackageNameLocked = getActiveServicePackageNameLocked();
                for (String str : strArr) {
                    if (str.equals(activeServicePackageNameLocked)) {
                        if (!z) {
                            return true;
                        }
                        String action = intent.getAction();
                        int changingUserId = getChangingUserId();
                        if ("android.intent.action.PACKAGE_RESTARTED".equals(action)) {
                            handleActiveServiceRestartedLocked(activeServicePackageNameLocked, changingUserId);
                        } else {
                            AbstractMasterSystemService.this.removeCachedServiceListLocked(changingUserId);
                        }
                    } else {
                        handlePackageUpdateLocked(str);
                    }
                }
                return false;
            }
        }

        public void onPackageDataCleared(String str, int i) {
            ComponentName serviceComponentName;
            AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(abstractMasterSystemService.mTag, "onPackageDataCleared(): " + str);
            }
            int changingUserId = getChangingUserId();
            ServiceNameResolver serviceNameResolver = AbstractMasterSystemService.this.mServiceNameResolver;
            if (serviceNameResolver != null && serviceNameResolver.isConfiguredInMultipleMode()) {
                synchronized (AbstractMasterSystemService.this.mLock) {
                    AbstractMasterSystemService.this.onServicePackageDataClearedMultiModeLocked(str, changingUserId);
                }
                return;
            }
            synchronized (AbstractMasterSystemService.this.mLock) {
                AbstractPerUserSystemService peekServiceForUserLocked = AbstractMasterSystemService.this.peekServiceForUserLocked(changingUserId);
                if (peekServiceForUserLocked != null && (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) != null && str.equals(serviceComponentName.getPackageName())) {
                    AbstractMasterSystemService.this.onServicePackageDataClearedLocked(changingUserId);
                }
            }
        }

        public final void handleActiveServiceRemoved(int i) {
            synchronized (AbstractMasterSystemService.this.mLock) {
                AbstractMasterSystemService.this.removeCachedServiceListLocked(i);
            }
            String serviceSettingsProperty = AbstractMasterSystemService.this.getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                Settings.Secure.putStringForUser(AbstractMasterSystemService.this.getContext().getContentResolver(), serviceSettingsProperty, null, i);
            }
        }

        public final void handleActiveServiceRestartedLocked(String str, int i) {
            if ((AbstractMasterSystemService.this.mServicePackagePolicyFlags & 16) != 0) {
                AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
                if (abstractMasterSystemService.debug) {
                    Slog.d(abstractMasterSystemService.mTag, "Holding service for user " + i + " while package " + str + " is being restarted");
                }
            } else {
                AbstractMasterSystemService abstractMasterSystemService2 = AbstractMasterSystemService.this;
                if (abstractMasterSystemService2.debug) {
                    Slog.d(abstractMasterSystemService2.mTag, "Removing service for user " + i + " because package " + str + " is being restarted");
                }
                AbstractMasterSystemService.this.removeCachedServiceListLocked(i);
                if ((AbstractMasterSystemService.this.mServicePackagePolicyFlags & 64) != 0) {
                    AbstractMasterSystemService abstractMasterSystemService3 = AbstractMasterSystemService.this;
                    if (abstractMasterSystemService3.debug) {
                        Slog.d(abstractMasterSystemService3.mTag, "Eagerly recreating service for user " + i);
                    }
                    AbstractMasterSystemService.this.updateCachedServiceLocked(i);
                }
            }
            AbstractMasterSystemService.this.onServicePackageRestartedLocked(i);
        }

        public void onPackageModified(String str) {
            synchronized (AbstractMasterSystemService.this.mLock) {
                AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
                if (abstractMasterSystemService.verbose) {
                    Slog.v(abstractMasterSystemService.mTag, "onPackageModified(): " + str);
                }
                if (AbstractMasterSystemService.this.mServiceNameResolver == null) {
                    return;
                }
                int changingUserId = getChangingUserId();
                String[] defaultServiceNameList = AbstractMasterSystemService.this.mServiceNameResolver.getDefaultServiceNameList(changingUserId);
                if (defaultServiceNameList != null) {
                    for (String str2 : defaultServiceNameList) {
                        peekAndUpdateCachedServiceLocked(str, changingUserId, str2);
                    }
                }
            }
        }

        public final void peekAndUpdateCachedServiceLocked(String str, int i, String str2) {
            ComponentName unflattenFromString;
            AbstractPerUserSystemService peekServiceForUserLocked;
            if (str2 == null || (unflattenFromString = ComponentName.unflattenFromString(str2)) == null || !unflattenFromString.getPackageName().equals(str) || (peekServiceForUserLocked = AbstractMasterSystemService.this.peekServiceForUserLocked(i)) == null || peekServiceForUserLocked.getServiceComponentName() != null) {
                return;
            }
            AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(abstractMasterSystemService.mTag, "update cached");
            }
            AbstractMasterSystemService.this.updateCachedServiceLocked(i);
        }

        public final String getActiveServicePackageNameLocked() {
            ComponentName serviceComponentName;
            AbstractPerUserSystemService peekServiceForUserLocked = AbstractMasterSystemService.this.peekServiceForUserLocked(getChangingUserId());
            if (peekServiceForUserLocked == null || (serviceComponentName = peekServiceForUserLocked.getServiceComponentName()) == null) {
                return null;
            }
            return serviceComponentName.getPackageName();
        }

        public final void handlePackageUpdateLocked(final String str) {
            AbstractMasterSystemService.this.visitServicesLocked(new Visitor() { // from class: com.android.server.infra.AbstractMasterSystemService$1$$ExternalSyntheticLambda0
                @Override // com.android.server.infra.AbstractMasterSystemService.Visitor
                public final void visit(Object obj) {
                    ((AbstractPerUserSystemService) obj).handlePackageUpdateLocked(str);
                }
            });
        }
    }

    public final void startTrackingPackageChanges() {
        new C16531().register(getContext(), (Looper) null, UserHandle.ALL, true);
    }

    public void onServicePackageDataClearedMultiModeLocked(String str, int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "onServicePackageDataClearedMultiModeLocked(" + i + ")");
        }
    }

    public void handlePackageRemovedMultiModeLocked(String str, int i) {
        if (this.verbose) {
            Slog.v(this.mTag, "handlePackageRemovedMultiModeLocked(" + i + ")");
        }
    }

    public void removeServiceFromCache(AbstractPerUserSystemService abstractPerUserSystemService, int i) {
        if (this.mServicesCacheList.get(i) != null) {
            ((List) this.mServicesCacheList.get(i)).remove(abstractPerUserSystemService);
        }
    }

    public void removeServiceFromMultiModeSettings(String str, int i) {
        ServiceNameResolver serviceNameResolver;
        if (getServiceSettingsProperty() == null || (serviceNameResolver = this.mServiceNameResolver) == null || !serviceNameResolver.isConfiguredInMultipleMode()) {
            if (this.verbose) {
                Slog.v(this.mTag, "removeServiceFromSettings not implemented  for single backend implementation");
                return;
            }
            return;
        }
        String[] serviceNameList = this.mServiceNameResolver.getServiceNameList(i);
        ArrayList arrayList = new ArrayList();
        for (String str2 : serviceNameList) {
            if (!str2.equals(str)) {
                arrayList.add(str2);
            }
        }
        this.mServiceNameResolver.setServiceNameList(arrayList, i);
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
            ContentResolver contentResolver = AbstractMasterSystemService.this.getContext().getContentResolver();
            String serviceSettingsProperty = AbstractMasterSystemService.this.getServiceSettingsProperty();
            if (serviceSettingsProperty != null) {
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(serviceSettingsProperty), false, this, -1);
            }
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("user_setup_complete"), false, this, -1);
            AbstractMasterSystemService.this.registerForExtraSettingsChanges(contentResolver, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            AbstractMasterSystemService abstractMasterSystemService = AbstractMasterSystemService.this;
            if (abstractMasterSystemService.verbose) {
                Slog.v(abstractMasterSystemService.mTag, "onChange(): uri=" + uri + ", userId=" + i);
            }
            String lastPathSegment = uri.getLastPathSegment();
            if (lastPathSegment == null) {
                return;
            }
            if (lastPathSegment.equals(AbstractMasterSystemService.this.getServiceSettingsProperty()) || lastPathSegment.equals("user_setup_complete")) {
                synchronized (AbstractMasterSystemService.this.mLock) {
                    AbstractMasterSystemService.this.updateCachedServiceLocked(i);
                }
                return;
            }
            AbstractMasterSystemService.this.onSettingsChanged(i, lastPathSegment);
        }
    }
}
