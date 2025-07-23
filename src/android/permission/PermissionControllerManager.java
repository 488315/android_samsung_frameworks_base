package android.permission;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.permission.IPermissionController;
import android.permission.PermissionControllerManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.RemoteStream;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import libcore.util.EmptyArray;

@SystemApi
/* loaded from: classes3.dex */
public final class PermissionControllerManager {
    private static final int CHUNK_SIZE = 4096;
    public static final int COUNT_ONLY_WHEN_GRANTED = 1;
    public static final int COUNT_WHEN_SYSTEM = 2;

    @SystemApi
    public static final int HIBERNATION_ELIGIBILITY_ELIGIBLE = 0;

    @SystemApi
    public static final int HIBERNATION_ELIGIBILITY_EXEMPT_BY_SYSTEM = 1;

    @SystemApi
    public static final int HIBERNATION_ELIGIBILITY_EXEMPT_BY_USER = 2;

    @SystemApi
    public static final int HIBERNATION_ELIGIBILITY_UNKNOWN = -1;
    public static final int REASON_INSTALLER_POLICY_VIOLATION = 2;
    public static final int REASON_MALWARE = 1;
    private static final String TAG = "PermissionControllerManager";
    private final Context mContext;
    private final Handler mHandler;
    private final ServiceConnector<IPermissionController> mRemoteService;
    private static final long REQUEST_TIMEOUT_MILLIS = Build.HW_TIMEOUT_MULTIPLIER * 60000;
    private static final long UNBIND_TIMEOUT_MILLIS = Build.HW_TIMEOUT_MULTIPLIER * JobInfo.MIN_BACKOFF_MILLIS;
    private static final Object sLock = new Object();
    private static ArrayMap<Pair<Integer, Thread>, ServiceConnector<IPermissionController>> sRemoteServices = new ArrayMap<>(1);

    @Retention(RetentionPolicy.SOURCE)
    public @interface CountPermissionAppsFlag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HibernationEligibilityFlag {
    }

    public interface OnCountPermissionAppsResultCallback {
        void onCountPermissionApps(int i);
    }

    public interface OnGetAppPermissionResultCallback {
        void onGetAppPermissions(List<RuntimePermissionPresentationInfo> list);
    }

    public interface OnPermissionUsageResultCallback {
        void onPermissionUsageResult(List<RuntimePermissionUsageInfo> list);
    }

    public static abstract class OnRevokeRuntimePermissionsCallback {
        public abstract void onRevokeRuntimePermissions(Map<String, List<String>> map);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public PermissionControllerManager(Context context, Handler handler) {
        PermissionControllerManager permissionControllerManager;
        final Handler handler2;
        synchronized (sLock) {
            Pair<Integer, Thread> pair = new Pair<>(Integer.valueOf(context.getUserId()), handler.getLooper().getThread());
            ServiceConnector<IPermissionController> serviceConnector = sRemoteServices.get(pair);
            if (serviceConnector == null) {
                Intent intent = new Intent(PermissionControllerService.SERVICE_INTERFACE);
                String permissionControllerPackageName = context.getPackageManager().getPermissionControllerPackageName();
                intent.setPackage(permissionControllerPackageName);
                ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
                if (resolveService == null) {
                    String str = "No PermissionController package (" + permissionControllerPackageName + ") for user " + context.getUserId();
                    Log.wtf(TAG, str);
                    throw new IllegalStateException(str);
                }
                permissionControllerManager = this;
                handler2 = handler;
                ServiceConnector.Impl<IPermissionController> impl = new ServiceConnector.Impl<IPermissionController>(ActivityThread.currentApplication(), new Intent(PermissionControllerService.SERVICE_INTERFACE).setComponent(resolveService.getComponentInfo().getComponentName()), 0, context.getUserId(), new Function() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda31
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return IPermissionController.Stub.asInterface((IBinder) obj);
                    }
                }) { // from class: android.permission.PermissionControllerManager.1
                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected Handler getJobHandler() {
                        return handler2;
                    }

                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected long getRequestTimeoutMs() {
                        return PermissionControllerManager.REQUEST_TIMEOUT_MILLIS;
                    }

                    @Override // com.android.internal.infra.ServiceConnector.Impl
                    protected long getAutoDisconnectTimeoutMs() {
                        return PermissionControllerManager.UNBIND_TIMEOUT_MILLIS;
                    }
                };
                sRemoteServices.put(pair, impl);
                serviceConnector = impl;
            } else {
                permissionControllerManager = this;
                handler2 = handler;
            }
            permissionControllerManager.mRemoteService = serviceConnector;
        }
        permissionControllerManager.mContext = context;
        permissionControllerManager.mHandler = handler2;
    }

    private void enforceSomePermissionsGrantedToSelf(String... strArr) {
        for (String str : strArr) {
            if (this.mContext.checkSelfPermission(str) == 0) {
                return;
            }
        }
        throw new SecurityException("At lest one of the following permissions is required: " + Arrays.toString(strArr));
    }

    public void revokeRuntimePermissions(final Map<String, List<String>> map, final boolean z, final int i, Executor executor, final OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onRevokeRuntimePermissionsCallback);
        Preconditions.checkNotNull(map);
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            Preconditions.checkNotNull(entry.getKey());
            Preconditions.checkCollectionElementsNotNull(entry.getValue(), "permissions");
        }
        enforceSomePermissionsGrantedToSelf(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda14
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                CompletableFuture lambda$revokeRuntimePermissions$0;
                lambda$revokeRuntimePermissions$0 = PermissionControllerManager.this.lambda$revokeRuntimePermissions$0(map, z, i, (IPermissionController) obj);
                return lambda$revokeRuntimePermissions$0;
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$revokeRuntimePermissions$1(PermissionControllerManager.OnRevokeRuntimePermissionsCallback.this, (Map) obj, (Throwable) obj2);
            }
        }, executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletableFuture lambda$revokeRuntimePermissions$0(Map map, boolean z, int i, IPermissionController iPermissionController) throws Exception {
        Bundle bundle = new Bundle();
        for (Map.Entry entry : map.entrySet()) {
            bundle.putStringArrayList((String) entry.getKey(), new ArrayList<>((Collection) entry.getValue()));
        }
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.revokeRuntimePermissions(bundle, z, i, this.mContext.getPackageName(), androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$revokeRuntimePermissions$1(OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback, Map map, Throwable th) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                Log.e(TAG, "Failure when revoking runtime permissions " + map, th);
                onRevokeRuntimePermissionsCallback.onRevokeRuntimePermissions(Collections.EMPTY_MAP);
            } else {
                onRevokeRuntimePermissionsCallback.onRevokeRuntimePermissions(map);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setRuntimePermissionGrantStateByDeviceAdmin(final String str, final AdminPermissionControlParams adminPermissionControlParams, Executor executor, final Consumer<Boolean> consumer) {
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        Objects.requireNonNull(adminPermissionControlParams, "Admin control params must not be null.");
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda16
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$setRuntimePermissionGrantStateByDeviceAdmin$2(str, adminPermissionControlParams, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$setRuntimePermissionGrantStateByDeviceAdmin$3(str, consumer, (Boolean) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$setRuntimePermissionGrantStateByDeviceAdmin$2(String str, AdminPermissionControlParams adminPermissionControlParams, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.setRuntimePermissionGrantStateByDeviceAdminFromParams(str, adminPermissionControlParams, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$setRuntimePermissionGrantStateByDeviceAdmin$3(String str, Consumer consumer, Boolean bool, Throwable th) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                Log.e(TAG, "Error setting permissions state for device admin " + str, th);
                consumer.accept(false);
            } else {
                consumer.accept(Boolean.valueOf(Boolean.TRUE.equals(bool)));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getRuntimePermissionBackup(final UserHandle userHandle, Executor executor, final Consumer<byte[]> consumer) {
        Preconditions.checkNotNull(userHandle);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(consumer);
        enforceSomePermissionsGrantedToSelf(Manifest.permission.GET_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda20
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                CompletableFuture receiveBytes;
                receiveBytes = RemoteStream.receiveBytes(new FunctionalUtils.ThrowingConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda36
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingConsumer
                    public final void acceptOrThrow(Object obj2) {
                        IPermissionController.this.getRuntimePermissionBackup(r2, (ParcelFileDescriptor) obj2);
                    }
                });
                return receiveBytes;
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getRuntimePermissionBackup$6(consumer, (byte[]) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ void lambda$getRuntimePermissionBackup$6(Consumer consumer, byte[] bArr, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error getting permission backup", th);
            consumer.accept(EmptyArray.BYTE);
        } else {
            consumer.accept(bArr);
        }
    }

    public void stageAndApplyRuntimePermissionsBackup(final byte[] bArr, final UserHandle userHandle) {
        Preconditions.checkNotNull(bArr);
        Preconditions.checkNotNull(userHandle);
        enforceSomePermissionsGrantedToSelf(Manifest.permission.GRANT_RUNTIME_PERMISSIONS, Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda12
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                CompletableFuture sendBytes;
                sendBytes = RemoteStream.sendBytes((FunctionalUtils.ThrowingConsumer<ParcelFileDescriptor>) new FunctionalUtils.ThrowingConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda28
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingConsumer
                    public final void acceptOrThrow(Object obj2) {
                        IPermissionController.this.stageAndApplyRuntimePermissionsBackup(r2, (ParcelFileDescriptor) obj2);
                    }
                }, bArr);
                return sendBytes;
            }
        }).whenComplete((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$stageAndApplyRuntimePermissionsBackup$9((Void) obj, (Throwable) obj2);
            }
        });
    }

    static /* synthetic */ void lambda$stageAndApplyRuntimePermissionsBackup$9(Void r1, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error sending permission backup", th);
        }
    }

    public void applyStagedRuntimePermissionBackup(final String str, final UserHandle userHandle, Executor executor, final Consumer<Boolean> consumer) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(userHandle);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(consumer);
        enforceSomePermissionsGrantedToSelf(Manifest.permission.GRANT_RUNTIME_PERMISSIONS, Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda8
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$applyStagedRuntimePermissionBackup$10(str, userHandle, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$applyStagedRuntimePermissionBackup$11(str, consumer, (Boolean) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$applyStagedRuntimePermissionBackup$10(String str, UserHandle userHandle, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.applyStagedRuntimePermissionBackup(str, userHandle, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$applyStagedRuntimePermissionBackup$11(String str, Consumer consumer, Boolean bool, Throwable th) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                Log.e(TAG, "Error restoring delayed permissions for " + str, th);
                consumer.accept(true);
            } else {
                consumer.accept(Boolean.valueOf(Boolean.TRUE.equals(bool)));
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(final FileDescriptor fileDescriptor, final String[] strArr) {
        try {
            this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda24
                @Override // com.android.internal.infra.ServiceConnector.Job
                public final Object run(Object obj) {
                    CompletableFuture runAsync;
                    runAsync = AndroidFuture.runAsync(FunctionalUtils.uncheckExceptions(new FunctionalUtils.ThrowingRunnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda30
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            IPermissionController.this.asBinder().dump(r2, r3);
                        }
                    }), BackgroundThread.getExecutor());
                    return runAsync;
                }
            }).get(REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Could not get dump", e);
        }
    }

    public void getAppPermissions(final String str, final OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, final Handler handler) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(onGetAppPermissionResultCallback);
        if (handler == null) {
            handler = this.mHandler;
        }
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda37
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getAppPermissions$14(str, (IPermissionController) obj);
            }
        }).whenComplete((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda38
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Handler.this.post(new Runnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        PermissionControllerManager.lambda$getAppPermissions$15(r1, r2, r3);
                    }
                });
            }
        });
    }

    static /* synthetic */ CompletableFuture lambda$getAppPermissions$14(String str, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.getAppPermissions(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getAppPermissions$15(Throwable th, OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, List list) {
        if (th != null) {
            Log.e(TAG, "Error getting app permission", th);
            onGetAppPermissionResultCallback.onGetAppPermissions(Collections.EMPTY_LIST);
        } else {
            onGetAppPermissionResultCallback.onGetAppPermissions(CollectionUtils.emptyIfNull(list));
        }
    }

    public void revokeRuntimePermission(final String str, final String str2) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        this.mRemoteService.run(new ServiceConnector.VoidJob() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda27
            @Override // com.android.internal.infra.ServiceConnector.VoidJob
            public final void runNoResult(Object obj) {
                ((IPermissionController) obj).revokeRuntimePermission(str, str2);
            }
        });
    }

    public void countPermissionApps(final List<String> list, final int i, final OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, final Handler handler) {
        Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
        Preconditions.checkFlagsArgument(i, 3);
        Preconditions.checkNotNull(onCountPermissionAppsResultCallback);
        if (handler == null) {
            handler = this.mHandler;
        }
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda6
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$countPermissionApps$18(list, i, (IPermissionController) obj);
            }
        }).whenComplete((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Handler.this.post(new Runnable() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        PermissionControllerManager.lambda$countPermissionApps$19(r1, r2, r3);
                    }
                });
            }
        });
    }

    static /* synthetic */ CompletableFuture lambda$countPermissionApps$18(List list, int i, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.countPermissionApps(list, i, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$countPermissionApps$19(Throwable th, OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, Integer num) {
        if (th != null) {
            Log.e(TAG, "Error counting permission apps", th);
            onCountPermissionAppsResultCallback.onCountPermissionApps(0);
        } else {
            onCountPermissionAppsResultCallback.onCountPermissionApps(num.intValue());
        }
    }

    public void getPermissionUsages(final boolean z, final long j, Executor executor, final OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
        Preconditions.checkArgumentNonnegative(j);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onPermissionUsageResultCallback);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda10
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getPermissionUsages$21(z, j, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getPermissionUsages$22(PermissionControllerManager.OnPermissionUsageResultCallback.this, (List) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getPermissionUsages$21(boolean z, long j, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.getPermissionUsages(z, j, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPermissionUsages$22(OnPermissionUsageResultCallback onPermissionUsageResultCallback, List list, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error getting permission usages", th);
            onPermissionUsageResultCallback.onPermissionUsageResult(Collections.EMPTY_LIST);
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                onPermissionUsageResultCallback.onPermissionUsageResult(CollectionUtils.emptyIfNull(list));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void grantOrUpgradeDefaultRuntimePermissions(Executor executor, final Consumer<Boolean> consumer) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda34
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$grantOrUpgradeDefaultRuntimePermissions$23((IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda35
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$grantOrUpgradeDefaultRuntimePermissions$24(consumer, (Boolean) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$grantOrUpgradeDefaultRuntimePermissions$23(IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        Log.i(TAG, "Calling grantOrUpgradeDefaultRuntimePermissions");
        iPermissionController.grantOrUpgradeDefaultRuntimePermissions(androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$grantOrUpgradeDefaultRuntimePermissions$24(Consumer consumer, Boolean bool, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error granting or upgrading runtime permissions", th);
            consumer.accept(false);
        } else {
            consumer.accept(Boolean.valueOf(Boolean.TRUE.equals(bool)));
        }
    }

    @Deprecated
    public void getPrivilegesDescriptionStringForProfile(final String str, Executor executor, final Consumer<CharSequence> consumer) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda2
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getPrivilegesDescriptionStringForProfile$25(str, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getPrivilegesDescriptionStringForProfile$26(consumer, (String) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getPrivilegesDescriptionStringForProfile$25(String str, IPermissionController iPermissionController) throws Exception {
        AndroidFuture<String> androidFuture = new AndroidFuture<>();
        iPermissionController.getPrivilegesDescriptionStringForProfile(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPrivilegesDescriptionStringForProfile$26(Consumer consumer, String str, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error from getPrivilegesDescriptionStringForProfile", th);
            consumer.accept(null);
        } else {
            consumer.accept(str);
        }
    }

    public void updateUserSensitive() {
        updateUserSensitiveForApp(-1);
    }

    public void updateUserSensitiveForApp(final int i) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda0
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$updateUserSensitiveForApp$27(i, (IPermissionController) obj);
            }
        }).whenComplete((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$updateUserSensitiveForApp$28(i, (Void) obj, (Throwable) obj2);
            }
        });
    }

    static /* synthetic */ CompletableFuture lambda$updateUserSensitiveForApp$27(int i, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.updateUserSensitiveForApp(i, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$updateUserSensitiveForApp$28(int i, Void r3, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error updating user_sensitive flags for uid " + i, th);
        }
    }

    public void notifyOneTimePermissionSessionTimeout(final String str, final int i) {
        this.mRemoteService.run(new ServiceConnector.VoidJob() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda39
            @Override // com.android.internal.infra.ServiceConnector.VoidJob
            public final void runNoResult(Object obj) {
                ((IPermissionController) obj).notifyOneTimePermissionSessionTimeout(str, i);
            }
        });
    }

    public void getPlatformPermissionsForGroup(final String str, Executor executor, final Consumer<List<String>> consumer) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda18
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getPlatformPermissionsForGroup$30(str, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getPlatformPermissionsForGroup$31(str, consumer, (List) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getPlatformPermissionsForGroup$30(String str, IPermissionController iPermissionController) throws Exception {
        AndroidFuture<List<String>> androidFuture = new AndroidFuture<>();
        iPermissionController.getPlatformPermissionsForGroup(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getPlatformPermissionsForGroup$31(String str, Consumer consumer, List list, Throwable th) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                Log.e(TAG, "Failed to get permissions of " + str, th);
                consumer.accept(new ArrayList());
            } else {
                consumer.accept(list);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getGroupOfPlatformPermission(final String str, Executor executor, final Consumer<String> consumer) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda4
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getGroupOfPlatformPermission$32(str, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getGroupOfPlatformPermission$33(str, consumer, (String) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getGroupOfPlatformPermission$32(String str, IPermissionController iPermissionController) throws Exception {
        AndroidFuture<String> androidFuture = new AndroidFuture<>();
        iPermissionController.getGroupOfPlatformPermission(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getGroupOfPlatformPermission$33(String str, Consumer consumer, String str2, Throwable th) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (th != null) {
                Log.e(TAG, "Failed to get group of " + str, th);
                consumer.accept(null);
            } else {
                consumer.accept(str2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getUnusedAppCount(Executor executor, final IntConsumer intConsumer) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(intConsumer);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda32
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getUnusedAppCount$34((IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda33
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getUnusedAppCount$35(intConsumer, (Integer) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getUnusedAppCount$34(IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.getUnusedAppCount(androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getUnusedAppCount$35(IntConsumer intConsumer, Integer num, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error getting unused app count", th);
            intConsumer.accept(0);
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                intConsumer.accept(num.intValue());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void getHibernationEligibility(final String str, Executor executor, final IntConsumer intConsumer) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(intConsumer);
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda25
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                return PermissionControllerManager.lambda$getHibernationEligibility$36(str, (IPermissionController) obj);
            }
        }).whenCompleteAsync((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda26
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.lambda$getHibernationEligibility$37(intConsumer, (Integer) obj, (Throwable) obj2);
            }
        }, executor);
    }

    static /* synthetic */ CompletableFuture lambda$getHibernationEligibility$36(String str, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.getHibernationEligibility(str, androidFuture);
        return androidFuture;
    }

    static /* synthetic */ void lambda$getHibernationEligibility$37(IntConsumer intConsumer, Integer num, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Error getting hibernation eligibility", th);
            intConsumer.accept(-1);
        } else {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                intConsumer.accept(num.intValue());
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void revokeSelfPermissionsOnKill(final String str, final List<String> list) {
        this.mRemoteService.postAsync(new ServiceConnector.Job() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda22
            @Override // com.android.internal.infra.ServiceConnector.Job
            public final Object run(Object obj) {
                CompletableFuture lambda$revokeSelfPermissionsOnKill$38;
                lambda$revokeSelfPermissionsOnKill$38 = PermissionControllerManager.this.lambda$revokeSelfPermissionsOnKill$38(str, list, (IPermissionController) obj);
                return lambda$revokeSelfPermissionsOnKill$38;
            }
        }).whenComplete((BiConsumer<? super R, ? super Throwable>) new BiConsumer() { // from class: android.permission.PermissionControllerManager$$ExternalSyntheticLambda23
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PermissionControllerManager.this.lambda$revokeSelfPermissionsOnKill$39(list, str, (Void) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletableFuture lambda$revokeSelfPermissionsOnKill$38(String str, List list, IPermissionController iPermissionController) throws Exception {
        AndroidFuture androidFuture = new AndroidFuture();
        iPermissionController.revokeSelfPermissionsOnKill(str, list, this.mContext.getDeviceId(), androidFuture);
        return androidFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeSelfPermissionsOnKill$39(List list, String str, Void r5, Throwable th) {
        if (th != null) {
            Log.e(TAG, "Failed to self revoke " + String.join(",", list) + " for package " + str + ", and device " + this.mContext.getDeviceId(), th);
        }
    }
}
