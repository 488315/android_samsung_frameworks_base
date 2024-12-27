package com.android.server.restrictions;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IRestrictionsManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IUserManager;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.internal.util.ArrayUtils;
import com.android.server.SystemService;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RestrictionsManagerService extends SystemService {
    public final RestrictionsManagerImpl mRestrictionsManagerImpl;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RestrictionsManagerImpl extends IRestrictionsManager.Stub {
        public final Context mContext;
        public final IDevicePolicyManager mDpm;
        public final DevicePolicyManagerInternal mDpmInternal;
        public final IUserManager mUm;

        public RestrictionsManagerImpl(RestrictionsManagerService restrictionsManagerService, Context context) {
            this.mContext = context;
            this.mUm = restrictionsManagerService.getBinderService("user");
            this.mDpm = restrictionsManagerService.getBinderService("device_policy");
            this.mDpmInternal = (DevicePolicyManagerInternal) restrictionsManagerService.getLocalService(DevicePolicyManagerInternal.class);
        }

        public static void enforceCallerMatchesPackage(int i, String str, String str2) {
            try {
                String[] packagesForUid = AppGlobals.getPackageManager().getPackagesForUid(i);
                if (packagesForUid != null && !ArrayUtils.contains(packagesForUid, str)) {
                    throw new SecurityException(str2 + i);
                }
            } catch (RemoteException unused) {
            }
        }

        public final Intent createLocalApprovalIntent() {
            ActivityInfo activityInfo;
            int callingUserId = UserHandle.getCallingUserId();
            if (this.mDpm == null) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(callingUserId);
                if (restrictionsProvider == null) {
                    throw new IllegalStateException("Cannot request permission without a restrictions provider registered");
                }
                String packageName = restrictionsProvider.getPackageName();
                Intent intent = new Intent("android.content.action.REQUEST_LOCAL_APPROVAL");
                intent.setPackage(packageName);
                ResolveInfo resolveIntent = AppGlobals.getPackageManager().resolveIntent(intent, (String) null, 0L, callingUserId);
                if (resolveIntent == null || (activityInfo = resolveIntent.activityInfo) == null || !activityInfo.exported) {
                    return null;
                }
                ActivityInfo activityInfo2 = resolveIntent.activityInfo;
                intent.setComponent(new ComponentName(activityInfo2.packageName, activityInfo2.name));
                return intent;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Bundle getApplicationRestrictions(String str) {
            return this.mUm.getApplicationRestrictions(str);
        }

        public final List getApplicationRestrictionsPerAdminForUser(int i, String str) {
            DevicePolicyManagerInternal devicePolicyManagerInternal = this.mDpmInternal;
            return devicePolicyManagerInternal != null ? devicePolicyManagerInternal.getApplicationRestrictionsPerAdminForUser(str, i) : new ArrayList();
        }

        public final boolean hasRestrictionsProvider() {
            int callingUserId = UserHandle.getCallingUserId();
            if (this.mDpm == null) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return this.mDpm.getRestrictionsProvider(callingUserId) != null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyPermissionResponse(String str, PersistableBundle persistableBundle) {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            if (this.mDpm != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(userId);
                    if (restrictionsProvider == null) {
                        throw new SecurityException("No restrictions provider registered for user");
                    }
                    enforceCallerMatchesPackage(callingUid, restrictionsProvider.getPackageName(), "Restrictions provider does not match caller ");
                    Intent intent = new Intent("android.content.action.PERMISSION_RESPONSE_RECEIVED");
                    intent.setPackage(str);
                    intent.putExtra("android.content.extra.RESPONSE_BUNDLE", persistableBundle);
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(userId));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void requestPermission(String str, String str2, String str3, PersistableBundle persistableBundle) {
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(callingUid);
            if (this.mDpm != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ComponentName restrictionsProvider = this.mDpm.getRestrictionsProvider(userId);
                    if (restrictionsProvider == null) {
                        throw new IllegalStateException("Cannot request permission without a restrictions provider registered");
                    }
                    enforceCallerMatchesPackage(callingUid, str, "Package name does not match caller ");
                    Intent intent = new Intent("android.content.action.REQUEST_PERMISSION");
                    intent.setComponent(restrictionsProvider);
                    intent.putExtra("android.content.extra.PACKAGE_NAME", str);
                    intent.putExtra("android.content.extra.REQUEST_TYPE", str2);
                    intent.putExtra("android.content.extra.REQUEST_ID", str3);
                    intent.putExtra("android.content.extra.REQUEST_BUNDLE", persistableBundle);
                    this.mContext.sendBroadcastAsUser(intent, new UserHandle(userId));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public RestrictionsManagerService(Context context) {
        super(context);
        this.mRestrictionsManagerImpl = new RestrictionsManagerImpl(this, context);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("restrictions", this.mRestrictionsManagerImpl);
    }
}
