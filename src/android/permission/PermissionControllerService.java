package android.permission;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.permission.IPermissionController;
import android.permission.PermissionControllerService;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class PermissionControllerService extends Service {
    private static final long CAMERA_MIC_INDICATORS_NOT_PRESENT = 162547999;
    private static final String LOG_TAG = "PermissionControllerService";
    public static final String SERVICE_INTERFACE = "android.permission.PermissionControllerService";

    public abstract void onCountPermissionApps(List<String> list, int i, IntConsumer intConsumer);

    public abstract void onGetAppPermissions(String str, Consumer<List<RuntimePermissionPresentationInfo>> consumer);

    public abstract void onGetPermissionUsages(boolean z, long j, Consumer<List<RuntimePermissionUsageInfo>> consumer);

    public abstract void onGetRuntimePermissionsBackup(UserHandle userHandle, OutputStream outputStream, Runnable runnable);

    public abstract void onGrantOrUpgradeDefaultRuntimePermissions(Runnable runnable);

    @Deprecated
    public void onRestoreDelayedRuntimePermissionsBackup(String str, UserHandle userHandle, Consumer<Boolean> consumer) {
    }

    @Deprecated
    public void onRestoreRuntimePermissionsBackup(UserHandle userHandle, InputStream inputStream, Runnable runnable) {
    }

    public abstract void onRevokeRuntimePermission(String str, String str2, Runnable runnable);

    public abstract void onRevokeRuntimePermissions(Map<String, List<String>> map, boolean z, int i, String str, Consumer<Map<String, List<String>>> consumer);

    @Deprecated
    public abstract void onSetRuntimePermissionGrantStateByDeviceAdmin(String str, String str2, String str3, int i, Consumer<Boolean> consumer);

    public void onStageAndApplyRuntimePermissionsBackup(UserHandle userHandle, InputStream inputStream, Runnable runnable) {
        onRestoreRuntimePermissionsBackup(userHandle, inputStream, runnable);
    }

    public void onApplyStagedRuntimePermissionBackup(String str, UserHandle userHandle, Consumer<Boolean> consumer) {
        onRestoreDelayedRuntimePermissionsBackup(str, userHandle, consumer);
    }

    public void onUpdateUserSensitivePermissionFlags(int i, Executor executor, Runnable runnable) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    public void onUpdateUserSensitivePermissionFlags(int i, Runnable runnable) {
        onUpdateUserSensitivePermissionFlags(i, getMainExecutor(), runnable);
    }

    public void onSetRuntimePermissionGrantStateByDeviceAdmin(String str, AdminPermissionControlParams adminPermissionControlParams, Consumer<Boolean> consumer) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    @Deprecated
    public void onOneTimePermissionSessionTimeout(String str) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    public void onOneTimePermissionSessionTimeout(String str, int i) {
        onOneTimePermissionSessionTimeout(str);
    }

    public void onGetPlatformPermissionsForGroup(String str, Consumer<List<String>> consumer) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    public void onGetGroupOfPlatformPermission(String str, Consumer<String> consumer) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    @Deprecated
    public void onRevokeSelfPermissionsOnKill(String str, List<String> list, Runnable runnable) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    public void onRevokeSelfPermissionsOnKill(String str, List<String> list, int i, Runnable runnable) {
        onRevokeSelfPermissionsOnKill(str, list, runnable);
    }

    @SystemApi
    @Deprecated
    public String getPrivilegesDescriptionStringForProfile(String str) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    @SystemApi
    public void onGetUnusedAppCount(IntConsumer intConsumer) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    @SystemApi
    public void onGetHibernationEligibility(String str, IntConsumer intConsumer) {
        throw new AbstractMethodError("Must be overridden in implementing class");
    }

    /* renamed from: android.permission.PermissionControllerService$1, reason: invalid class name */
    class AnonymousClass1 extends IPermissionController.Stub {
        AnonymousClass1() {
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermissions(Bundle bundle, boolean z, int i, String str, final AndroidFuture androidFuture) {
            Preconditions.checkNotNull(bundle, "bundleizedRequest");
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(androidFuture);
            ArrayMap arrayMap = new ArrayMap();
            for (String str2 : bundle.keySet()) {
                Preconditions.checkNotNull(str2);
                ArrayList<String> stringArrayList = bundle.getStringArrayList(str2);
                Preconditions.checkCollectionElementsNotNull(stringArrayList, "permissions");
                arrayMap.put(str2, stringArrayList);
            }
            enforceSomePermissionsGrantedToCaller(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            try {
                Preconditions.checkArgument(getCallingUid() == PermissionControllerService.this.getPackageManager().getPackageInfo(str, 0).applicationInfo.uid);
                PermissionControllerService.this.onRevokeRuntimePermissions(arrayMap, z, i, str, new Consumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PermissionControllerService.AnonymousClass1.lambda$revokeRuntimePermissions$1(androidFuture, (Map) obj);
                    }
                });
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        static /* synthetic */ void lambda$revokeRuntimePermissions$1(AndroidFuture androidFuture, Map map) {
            CollectionUtils.forEach(map, new BiConsumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda7
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PermissionControllerService.AnonymousClass1.lambda$revokeRuntimePermissions$0((String) obj, (List) obj2);
                }
            });
            androidFuture.complete(map);
        }

        static /* synthetic */ void lambda$revokeRuntimePermissions$0(String str, List list) {
            Preconditions.checkNotNull(str);
            Preconditions.checkCollectionElementsNotNull(list, "permissions");
        }

        private void enforceSomePermissionsGrantedToCaller(String... strArr) {
            for (String str : strArr) {
                if (PermissionControllerService.this.checkCallingPermission(str) == 0) {
                    return;
                }
            }
            throw new SecurityException("At lest one of the following permissions is required: " + Arrays.toString(strArr));
        }

        @Override // android.permission.IPermissionController
        public void getRuntimePermissionBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            Preconditions.checkNotNull(userHandle);
            Preconditions.checkNotNull(parcelFileDescriptor);
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GET_RUNTIME_PERMISSIONS);
            try {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                try {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    PermissionControllerService.this.onGetRuntimePermissionsBackup(userHandle, autoCloseOutputStream, new PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
                    countDownLatch.await();
                    autoCloseOutputStream.close();
                } finally {
                }
            } catch (IOException e) {
                Log.e(PermissionControllerService.LOG_TAG, "Could not open pipe to write backup to", e);
            } catch (InterruptedException e2) {
                Log.e(PermissionControllerService.LOG_TAG, "getRuntimePermissionBackup timed out", e2);
            }
        }

        @Override // android.permission.IPermissionController
        public void stageAndApplyRuntimePermissionsBackup(UserHandle userHandle, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
            Preconditions.checkNotNull(userHandle);
            Preconditions.checkNotNull(parcelFileDescriptor);
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GRANT_RUNTIME_PERMISSIONS, Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
            try {
                ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
                try {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    PermissionControllerService.this.onStageAndApplyRuntimePermissionsBackup(userHandle, autoCloseInputStream, new PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
                    countDownLatch.await();
                    autoCloseInputStream.close();
                } catch (Throwable th) {
                    try {
                        autoCloseInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException e) {
                Log.e(PermissionControllerService.LOG_TAG, "Could not open pipe to read backup from", e);
            } catch (InterruptedException e2) {
                Log.e(PermissionControllerService.LOG_TAG, "restoreRuntimePermissionBackup timed out", e2);
            }
        }

        @Override // android.permission.IPermissionController
        public void applyStagedRuntimePermissionBackup(String str, UserHandle userHandle, AndroidFuture androidFuture) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(userHandle);
            Preconditions.checkNotNull(androidFuture);
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GRANT_RUNTIME_PERMISSIONS, Manifest.permission.RESTORE_RUNTIME_PERMISSIONS);
            PermissionControllerService permissionControllerService = PermissionControllerService.this;
            Objects.requireNonNull(androidFuture);
            permissionControllerService.onApplyStagedRuntimePermissionBackup(str, userHandle, new PermissionControllerService$1$$ExternalSyntheticLambda6(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void getAppPermissions(String str, AndroidFuture androidFuture) {
            Preconditions.checkNotNull(str, "packageName");
            Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GET_RUNTIME_PERMISSIONS);
            PermissionControllerService permissionControllerService = PermissionControllerService.this;
            Objects.requireNonNull(androidFuture);
            permissionControllerService.onGetAppPermissions(str, new PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermission(String str, String str2) throws InterruptedException {
            Preconditions.checkNotNull(str, "packageName");
            Preconditions.checkNotNull(str2, "permissionName");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            PermissionControllerService.this.onRevokeRuntimePermission(str, str2, new PermissionControllerService$1$$ExternalSyntheticLambda0(countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                Log.e(PermissionControllerService.LOG_TAG, "revokeRuntimePermission timed out", e);
            }
        }

        @Override // android.permission.IPermissionController
        public void countPermissionApps(List<String> list, int i, AndroidFuture androidFuture) {
            Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
            Preconditions.checkFlagsArgument(i, 3);
            Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GET_RUNTIME_PERMISSIONS);
            PermissionControllerService permissionControllerService = PermissionControllerService.this;
            Objects.requireNonNull(androidFuture);
            permissionControllerService.onCountPermissionApps(list, i, new PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void getPermissionUsages(boolean z, long j, AndroidFuture androidFuture) {
            Preconditions.checkArgumentNonnegative(j);
            Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GET_RUNTIME_PERMISSIONS);
            PermissionControllerService permissionControllerService = PermissionControllerService.this;
            Objects.requireNonNull(androidFuture);
            permissionControllerService.onGetPermissionUsages(z, j, new PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void setRuntimePermissionGrantStateByDeviceAdminFromParams(String str, AdminPermissionControlParams adminPermissionControlParams, AndroidFuture androidFuture) {
            Preconditions.checkStringNotEmpty(str);
            if (adminPermissionControlParams.getGrantState() == 1) {
                enforceSomePermissionsGrantedToCaller(Manifest.permission.GRANT_RUNTIME_PERMISSIONS);
            }
            if (adminPermissionControlParams.getGrantState() == 2) {
                enforceSomePermissionsGrantedToCaller(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            }
            enforceSomePermissionsGrantedToCaller(Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            Preconditions.checkNotNull(androidFuture);
            PermissionControllerService permissionControllerService = PermissionControllerService.this;
            Objects.requireNonNull(androidFuture);
            permissionControllerService.onSetRuntimePermissionGrantStateByDeviceAdmin(str, adminPermissionControlParams, new PermissionControllerService$1$$ExternalSyntheticLambda6(androidFuture));
        }

        @Override // android.permission.IPermissionController
        public void grantOrUpgradeDefaultRuntimePermissions(final AndroidFuture androidFuture) {
            Preconditions.checkNotNull(androidFuture, "callback");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            Log.i(PermissionControllerService.LOG_TAG, "Calling onGrantOrUpgradeDefaultRuntimePermissions");
            PermissionControllerService.this.onGrantOrUpgradeDefaultRuntimePermissions(new Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    androidFuture.complete(true);
                }
            });
        }

        @Override // android.permission.IPermissionController
        public void updateUserSensitiveForApp(int i, final AndroidFuture androidFuture) {
            Preconditions.checkNotNull(androidFuture, "callback cannot be null");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.ADJUST_RUNTIME_PERMISSIONS_POLICY);
            try {
                PermissionControllerService.this.onUpdateUserSensitivePermissionFlags(i, new Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        androidFuture.complete(null);
                    }
                });
            } catch (Exception e) {
                androidFuture.completeExceptionally(e);
            }
        }

        @Override // android.permission.IPermissionController
        public void notifyOneTimePermissionSessionTimeout(String str, int i) {
            enforceSomePermissionsGrantedToCaller(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
            PermissionControllerService.this.onOneTimePermissionSessionTimeout((String) Preconditions.checkNotNull(str, "packageName cannot be null"), i);
        }

        @Override // android.os.Binder
        protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Preconditions.checkNotNull(fileDescriptor, "fd");
            Preconditions.checkNotNull(printWriter, "writer");
            enforceSomePermissionsGrantedToCaller(Manifest.permission.GET_RUNTIME_PERMISSIONS);
            PermissionControllerService.this.dump(fileDescriptor, printWriter, strArr);
        }

        @Override // android.permission.IPermissionController
        public void getPrivilegesDescriptionStringForProfile(String str, AndroidFuture<String> androidFuture) {
            try {
                Preconditions.checkStringNotEmpty(str);
                Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(Manifest.permission.MANAGE_COMPANION_DEVICES);
                androidFuture.complete(PermissionControllerService.this.getPrivilegesDescriptionStringForProfile(str));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getPlatformPermissionsForGroup(String str, AndroidFuture<List<String>> androidFuture) {
            try {
                Objects.requireNonNull(str);
                Objects.requireNonNull(androidFuture);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetPlatformPermissionsForGroup(str, new PermissionControllerService$1$$ExternalSyntheticLambda1(androidFuture));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getGroupOfPlatformPermission(String str, final AndroidFuture<String> androidFuture) {
            try {
                Objects.requireNonNull(str);
                Objects.requireNonNull(androidFuture);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetGroupOfPlatformPermission(str, new Consumer() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        androidFuture.complete((String) obj);
                    }
                });
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getUnusedAppCount(AndroidFuture androidFuture) {
            try {
                Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(Manifest.permission.MANAGE_APP_HIBERNATION);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetUnusedAppCount(new PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void getHibernationEligibility(String str, AndroidFuture androidFuture) {
            try {
                Objects.requireNonNull(androidFuture);
                enforceSomePermissionsGrantedToCaller(Manifest.permission.MANAGE_APP_HIBERNATION);
                PermissionControllerService permissionControllerService = PermissionControllerService.this;
                Objects.requireNonNull(androidFuture);
                permissionControllerService.onGetHibernationEligibility(str, new PermissionControllerService$1$$ExternalSyntheticLambda4(androidFuture));
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }

        @Override // android.permission.IPermissionController
        public void revokeSelfPermissionsOnKill(String str, List<String> list, int i, final AndroidFuture androidFuture) {
            try {
                Objects.requireNonNull(androidFuture);
                if (PermissionControllerService.this.getPackageManager().getPackageUid(str, PackageManager.PackageInfoFlags.of(0L)) != Binder.getCallingUid()) {
                    enforceSomePermissionsGrantedToCaller(Manifest.permission.REVOKE_RUNTIME_PERMISSIONS);
                }
                PermissionControllerService.this.onRevokeSelfPermissionsOnKill(str, list, i, new Runnable() { // from class: android.permission.PermissionControllerService$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        androidFuture.complete(null);
                    }
                });
            } catch (Throwable th) {
                androidFuture.completeExceptionally(th);
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new AnonymousClass1();
    }
}
