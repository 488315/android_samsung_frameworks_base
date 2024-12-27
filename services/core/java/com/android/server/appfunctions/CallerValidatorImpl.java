package com.android.server.appfunctions;

import android.app.admin.DevicePolicyManager;
import android.app.appfunctions.AppFunctionStaticMetadataHelper;
import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.GetByDocumentIdRequest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.appfunctions.agentpolicy.AppFunctionAgentPolicyManager;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CallerValidatorImpl implements CallerValidator {
    public final Context mContext;

    public CallerValidatorImpl(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
    }

    public final UserHandle handleIncomingUser(int i, int i2, UserHandle userHandle, String str) {
        if (UserHandle.getUserHandleForUid(i2).equals(userHandle)) {
            return userHandle;
        }
        if (userHandle.getIdentifier() < 0) {
            throw new IllegalArgumentException("Call does not support special user " + userHandle);
        }
        if (this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0) {
            try {
                this.mContext.createPackageContextAsUser(str, 0, userHandle);
                return userHandle;
            } catch (PackageManager.NameNotFoundException unused) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Package: ", str, " haven't installed for user ");
                m.append(userHandle.getIdentifier());
                throw new SecurityException(m.toString());
            }
        }
        throw new SecurityException("Permission denied while calling from uid " + i2 + " with " + userHandle + "; Requires permission: android.permission.INTERACT_ACROSS_USERS_FULL");
    }

    @Override // com.android.server.appfunctions.CallerValidator
    public final boolean isUserOrganizationManaged(UserHandle userHandle) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        Objects.requireNonNull(devicePolicyManager);
        if (devicePolicyManager.isDeviceManaged()) {
            return true;
        }
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        Objects.requireNonNull(userManager);
        return userManager.isManagedProfile(userHandle.getIdentifier());
    }

    @Override // com.android.server.appfunctions.CallerValidator
    public final String validateCallingPackage(String str) {
        int i;
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                i = this.mContext.createContextAsUser(UserHandle.getUserHandleForUid(callingUid), 0).getPackageManager().getPackageUid(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                i = -1;
            }
            if (i == callingUid) {
                return str;
            }
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Specified calling package [", str, "] does not match the calling uid "));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.appfunctions.CallerValidator
    public final AndroidFuture verifyCallerCanExecuteAppFunction(int i, int i2, UserHandle userHandle, String str, String str2, String str3, AppFunctionAgentPolicyManager appFunctionAgentPolicyManager) {
        boolean z;
        boolean z2;
        if (str.equals(str2)) {
            return AndroidFuture.completedFuture(Boolean.TRUE);
        }
        if (this.mContext.checkPermission("android.permission.EXECUTE_APP_FUNCTIONS_TRUSTED", i2, i) != 0) {
            boolean isSystemAppOrPrivilegedApp = appFunctionAgentPolicyManager.isSystemAppOrPrivilegedApp(str);
            synchronized (appFunctionAgentPolicyManager.mLock) {
                z = true;
                if (isSystemAppOrPrivilegedApp) {
                    try {
                        if (((ArrayList) appFunctionAgentPolicyManager.appliedAgentPolicyInfo.trustedAgentList).contains(str)) {
                            z2 = true;
                            Slog.d("AppFunctionAgentPolicyManager", "packageName : " + str + " / isTrustedAgentPackage : " + z2);
                        }
                    } finally {
                    }
                }
                z2 = false;
                Slog.d("AppFunctionAgentPolicyManager", "packageName : " + str + " / isTrustedAgentPackage : " + z2);
            }
            if (!z2) {
                if (this.mContext.checkPermission("android.permission.EXECUTE_APP_FUNCTIONS", i2, i) != 0) {
                    boolean isSystemAppOrPrivilegedApp2 = appFunctionAgentPolicyManager.isSystemAppOrPrivilegedApp(str);
                    synchronized (appFunctionAgentPolicyManager.mLock) {
                        if (isSystemAppOrPrivilegedApp2) {
                            try {
                                if (((ArrayList) appFunctionAgentPolicyManager.appliedAgentPolicyInfo.normalAgentList).contains(str)) {
                                    Slog.d("AppFunctionAgentPolicyManager", "packageName : " + str + " / isAgentPackage : " + z);
                                }
                            } finally {
                            }
                        }
                        z = false;
                        Slog.d("AppFunctionAgentPolicyManager", "packageName : " + str + " / isAgentPackage : " + z);
                    }
                    if (!z) {
                        return AndroidFuture.completedFuture(Boolean.FALSE);
                    }
                }
                AppSearchManager appSearchManager = (AppSearchManager) this.mContext.createContextAsUser(userHandle, 0).getSystemService(AppSearchManager.class);
                Objects.requireNonNull(appSearchManager);
                final FutureAppSearchSessionImpl futureAppSearchSessionImpl = new FutureAppSearchSessionImpl(appSearchManager, AppFunctionExecutors.THREAD_POOL_EXECUTOR, new AppSearchManager.SearchContext.Builder("apps-db").build());
                final String documentIdForAppFunction = AppFunctionStaticMetadataHelper.getDocumentIdForAppFunction(str2, str3);
                GetByDocumentIdRequest build = new GetByDocumentIdRequest.Builder("app_functions").addIds(documentIdForAppFunction).build();
                Objects.requireNonNull(build);
                return futureAppSearchSessionImpl.getSessionAsync().thenCompose(new FutureAppSearchSessionImpl$$ExternalSyntheticLambda0(futureAppSearchSessionImpl, build, 4)).thenApply(new Function() { // from class: com.android.server.appfunctions.CallerValidatorImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String str4 = documentIdForAppFunction;
                        AppSearchBatchResult appSearchBatchResult = (AppSearchBatchResult) obj;
                        if (appSearchBatchResult.isSuccess()) {
                            return (GenericDocument) appSearchBatchResult.getSuccesses().get(str4);
                        }
                        AppSearchResult appSearchResult = (AppSearchResult) appSearchBatchResult.getFailures().get(str4);
                        int resultCode = appSearchResult.getResultCode();
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Unable to retrieve document with id: ", str4, " due to ");
                        m.append(appSearchResult.getErrorMessage());
                        throw new AppSearchException(resultCode, m.toString());
                    }
                }).thenApply(new CallerValidatorImpl$$ExternalSyntheticLambda1()).whenComplete(new BiConsumer() { // from class: com.android.server.appfunctions.CallerValidatorImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((FutureAppSearchSessionImpl) futureAppSearchSessionImpl).close();
                    }
                });
            }
        }
        return AndroidFuture.completedFuture(Boolean.TRUE);
    }

    @Override // com.android.server.appfunctions.CallerValidator
    public final UserHandle verifyTargetUserHandle(String str, UserHandle userHandle) {
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return handleIncomingUser(callingPid, callingUid, userHandle, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
