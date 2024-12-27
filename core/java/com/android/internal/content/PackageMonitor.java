package com.android.internal.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.BackgroundThread;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;

public abstract class PackageMonitor extends BroadcastReceiver {
    public static final int PACKAGE_PERMANENT_CHANGE = 3;
    public static final int PACKAGE_TEMPORARY_CHANGE = 2;
    public static final int PACKAGE_UNCHANGED = 0;
    public static final int PACKAGE_UPDATING = 1;
    static final String TAG = "PackageMonitor";
    String[] mAppearingPackages;
    int mChangeType;
    int mChangeUserId;
    String[] mDisappearingPackages;
    private Executor mExecutor;
    String[] mModifiedComponents;
    String[] mModifiedPackages;
    PackageMonitorCallback mPackageMonitorCallback;
    Context mRegisteredContext;
    Handler mRegisteredHandler;
    boolean mSomePackagesChanged;
    final boolean mSupportsPackageRestartQuery;
    String[] mTempArray;

    public PackageMonitor() {
        this(!Flags.packageRestartQueryDisabledByDefault());
    }

    public PackageMonitor(boolean supportsPackageRestartQuery) {
        this.mChangeUserId = -10000;
        this.mTempArray = new String[1];
        this.mSupportsPackageRestartQuery = supportsPackageRestartQuery;
    }

    private IntentFilter getPackageFilter() {
        boolean isCore = UserHandle.isCore(Process.myUid());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_QUERY_PACKAGE_RESTART);
        filter.addDataScheme("package");
        if (isCore) {
            filter.setPriority(1000);
        }
        return filter;
    }

    public void register(Context context, Looper thread, boolean externalStorage) {
        register(context, thread, null, externalStorage);
    }

    public void register(Context context, Looper thread, UserHandle user, boolean externalStorage) {
        register(
                context,
                user,
                thread == null ? BackgroundThread.getHandler() : new Handler(thread));
    }

    public void register(Context context, UserHandle user, Handler handler) {
        PackageManager pm;
        if (this.mRegisteredContext != null) {
            throw new IllegalStateException("Already registered");
        }
        this.mRegisteredContext = context;
        this.mRegisteredHandler = (Handler) Objects.requireNonNull(handler);
        if (this.mSupportsPackageRestartQuery) {
            IntentFilter filter = getPackageFilter();
            if (user != null) {
                context.registerReceiverAsUser(this, user, filter, null, this.mRegisteredHandler);
            } else {
                context.registerReceiver(this, filter, null, this.mRegisteredHandler);
            }
        }
        if (this.mPackageMonitorCallback == null
                && (pm = this.mRegisteredContext.getPackageManager()) != null) {
            this.mExecutor = new HandlerExecutor(this.mRegisteredHandler);
            this.mPackageMonitorCallback = new PackageMonitorCallback(this);
            int userId = user != null ? user.getIdentifier() : this.mRegisteredContext.getUserId();
            pm.registerPackageMonitorCallback(this.mPackageMonitorCallback, userId);
        }
    }

    public Handler getRegisteredHandler() {
        return this.mRegisteredHandler;
    }

    public void unregister() {
        if (this.mRegisteredContext == null) {
            throw new IllegalStateException("Not registered");
        }
        if (this.mSupportsPackageRestartQuery) {
            this.mRegisteredContext.unregisterReceiver(this);
        }
        PackageManager pm = this.mRegisteredContext.getPackageManager();
        if (pm != null && this.mPackageMonitorCallback != null) {
            pm.unregisterPackageMonitorCallback(this.mPackageMonitorCallback);
        }
        this.mPackageMonitorCallback = null;
        this.mRegisteredContext = null;
        this.mExecutor = null;
    }

    public void onBeginPackageChanges() {}

    public void onPackageAdded(String packageName, int uid) {}

    public void onPackageAddedWithExtras(String packageName, int uid, Bundle extras) {}

    public void onPackageRemoved(String packageName, int uid) {}

    public void onPackageRemovedWithExtras(String packageName, int uid, Bundle extras) {}

    public void onPackageRemovedAllUsers(String packageName, int uid) {}

    public void onPackageRemovedAllUsersWithExtras(String packageName, int uid, Bundle extras) {}

    public void onPackageUpdateStarted(String packageName, int uid) {}

    public void onPackageUpdateStartedWithExtras(String packageName, int uid, Bundle extras) {}

    public void onPackageUpdateFinished(String packageName, int uid) {}

    public void onPackageUpdateFinishedWithExtras(String packageName, int uid, Bundle extras) {}

    public boolean onPackageChanged(String packageName, int uid, String[] components) {
        if (components != null) {
            for (String name : components) {
                if (packageName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void onPackageChangedWithExtras(String packageName, Bundle extras) {}

    public boolean onHandleForceStop(
            Intent intent, String[] packages, int uid, boolean doit, Bundle extras) {
        return onHandleForceStop(intent, packages, uid, doit);
    }

    public boolean onHandleForceStop(Intent intent, String[] packages, int uid, boolean doit) {
        return false;
    }

    public void onUidRemoved(int uid) {}

    public void onPackagesAvailable(String[] packages) {}

    public void onPackagesUnavailable(String[] packages) {}

    public void onPackagesSuspended(String[] packages) {}

    public void onPackagesUnsuspended(String[] packages) {}

    public void onPackageDisappeared(String packageName, int reason) {}

    public void onPackageDisappearedWithExtras(String packageName, Bundle extras) {}

    public void onPackageAppeared(String packageName, int reason) {}

    public void onPackageAppearedWithExtras(String packageName, Bundle extras) {}

    public void onPackageModified(String packageName) {}

    public void onPackageModifiedWithExtras(String packageName, Bundle extras) {}

    public void onPackageUnstopped(String packageName, int uid, Bundle extras) {}

    public boolean didSomePackagesChange() {
        return this.mSomePackagesChanged;
    }

    public int isPackageAppearing(String packageName) {
        if (this.mAppearingPackages != null) {
            for (int i = this.mAppearingPackages.length - 1; i >= 0; i--) {
                if (packageName.equals(this.mAppearingPackages[i])) {
                    return this.mChangeType;
                }
            }
            return 0;
        }
        return 0;
    }

    public boolean anyPackagesAppearing() {
        return this.mAppearingPackages != null;
    }

    public int isPackageDisappearing(String packageName) {
        if (this.mDisappearingPackages != null) {
            for (int i = this.mDisappearingPackages.length - 1; i >= 0; i--) {
                if (packageName.equals(this.mDisappearingPackages[i])) {
                    return this.mChangeType;
                }
            }
            return 0;
        }
        return 0;
    }

    public boolean anyPackagesDisappearing() {
        return this.mDisappearingPackages != null;
    }

    public boolean isReplacing() {
        return this.mChangeType == 1;
    }

    public boolean isPackageModified(String packageName) {
        if (this.mModifiedPackages != null) {
            for (int i = this.mModifiedPackages.length - 1; i >= 0; i--) {
                if (packageName.equals(this.mModifiedPackages[i])) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean isComponentModified(String className) {
        if (className == null || this.mModifiedComponents == null) {
            return false;
        }
        for (int i = this.mModifiedComponents.length - 1; i >= 0; i--) {
            if (className.equals(this.mModifiedComponents[i])) {
                return true;
            }
        }
        return false;
    }

    public void onSomePackagesChanged() {}

    public void onFinishPackageChanges() {}

    public void onPackageDataCleared(String packageName, int uid) {}

    public void onPackageStateChanged(String packageName, int uid) {}

    public int getChangingUserId() {
        return this.mChangeUserId;
    }

    String getPackageName(Intent intent) {
        Uri uri = intent.getData();
        if (uri == null) {
            return null;
        }
        String pkg = uri.getSchemeSpecificPart();
        return pkg;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        lambda$postHandlePackageEvent$0(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postHandlePackageEvent(final Intent intent) {
        if (this.mExecutor != null) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.internal.content.PackageMonitor$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PackageMonitor.this.lambda$postHandlePackageEvent$0(intent);
                        }
                    });
        }
    }

    /* renamed from: doHandlePackageEvent, reason: merged with bridge method [inline-methods] */
    public final void lambda$postHandlePackageEvent$0(Intent intent) {
        this.mChangeUserId = intent.getIntExtra("android.intent.extra.user_handle", -10000);
        if (this.mChangeUserId == -10000) {
            Slog.w(TAG, "Intent broadcast does not contain user handle: " + intent);
            return;
        }
        onBeginPackageChanges();
        this.mAppearingPackages = null;
        this.mDisappearingPackages = null;
        this.mSomePackagesChanged = false;
        this.mModifiedComponents = null;
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String pkg = getPackageName(intent);
            int uid = intent.getIntExtra(Intent.EXTRA_UID, 0);
            this.mSomePackagesChanged = true;
            if (pkg != null) {
                this.mAppearingPackages = this.mTempArray;
                this.mTempArray[0] = pkg;
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    this.mModifiedPackages = this.mTempArray;
                    this.mChangeType = 1;
                    onPackageUpdateFinished(pkg, uid);
                    onPackageUpdateFinishedWithExtras(pkg, uid, intent.getExtras());
                    onPackageModified(pkg);
                    onPackageModifiedWithExtras(pkg, intent.getExtras());
                } else {
                    this.mChangeType = 3;
                    onPackageAdded(pkg, uid);
                    onPackageAddedWithExtras(pkg, uid, intent.getExtras());
                }
                onPackageAppearedWithExtras(pkg, intent.getExtras());
                onPackageAppeared(pkg, this.mChangeType);
            }
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            String pkg2 = getPackageName(intent);
            int uid2 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            if (pkg2 != null) {
                this.mDisappearingPackages = this.mTempArray;
                this.mTempArray[0] = pkg2;
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    this.mChangeType = 1;
                    onPackageUpdateStarted(pkg2, uid2);
                    onPackageUpdateStartedWithExtras(pkg2, uid2, intent.getExtras());
                    if (intent.getBooleanExtra(Intent.EXTRA_ARCHIVAL, false)) {
                        onPackageModified(pkg2);
                        onPackageModifiedWithExtras(pkg2, intent.getExtras());
                    }
                } else {
                    this.mChangeType = 3;
                    this.mSomePackagesChanged = true;
                    onPackageRemoved(pkg2, uid2);
                    onPackageRemovedWithExtras(pkg2, uid2, intent.getExtras());
                    if (intent.getBooleanExtra(Intent.EXTRA_REMOVED_FOR_ALL_USERS, false)) {
                        onPackageRemovedAllUsers(pkg2, uid2);
                        onPackageRemovedAllUsersWithExtras(pkg2, uid2, intent.getExtras());
                    }
                }
                onPackageDisappearedWithExtras(pkg2, intent.getExtras());
                onPackageDisappeared(pkg2, this.mChangeType);
            }
        } else if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            String pkg3 = getPackageName(intent);
            int uid3 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            this.mModifiedComponents =
                    intent.getStringArrayExtra(Intent.EXTRA_CHANGED_COMPONENT_NAME_LIST);
            if (pkg3 != null) {
                this.mModifiedPackages = this.mTempArray;
                this.mTempArray[0] = pkg3;
                this.mChangeType = 3;
                if (onPackageChanged(pkg3, uid3, this.mModifiedComponents)) {
                    this.mSomePackagesChanged = true;
                }
                onPackageChangedWithExtras(pkg3, intent.getExtras());
                onPackageModified(pkg3);
                onPackageModifiedWithExtras(pkg3, intent.getExtras());
            }
        } else if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
            String pkg4 = getPackageName(intent);
            int uid4 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            if (pkg4 != null) {
                onPackageDataCleared(pkg4, uid4);
            }
        } else if (Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
            this.mDisappearingPackages = intent.getStringArrayExtra(Intent.EXTRA_PACKAGES);
            this.mChangeType = 2;
            boolean canRestart =
                    onHandleForceStop(
                            intent,
                            this.mDisappearingPackages,
                            intent.getIntExtra(Intent.EXTRA_UID, 0),
                            false,
                            intent.getExtras());
            if (canRestart) {
                setResultCode(-1);
            }
        } else if (Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
            this.mDisappearingPackages = new String[] {getPackageName(intent)};
            this.mChangeType = 2;
            onHandleForceStop(
                    intent,
                    this.mDisappearingPackages,
                    intent.getIntExtra(Intent.EXTRA_UID, 0),
                    true,
                    intent.getExtras());
        } else if (Intent.ACTION_UID_REMOVED.equals(action)) {
            onUidRemoved(intent.getIntExtra(Intent.EXTRA_UID, 0));
        } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
            String[] pkgList = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            this.mAppearingPackages = pkgList;
            this.mChangeType = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false) ? 1 : 2;
            this.mSomePackagesChanged = true;
            if (pkgList != null) {
                onPackagesAvailable(pkgList);
                for (String str : pkgList) {
                    onPackageAppeared(str, this.mChangeType);
                }
            }
        } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
            String[] pkgList2 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            this.mDisappearingPackages = pkgList2;
            this.mChangeType = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false) ? 1 : 2;
            this.mSomePackagesChanged = true;
            if (pkgList2 != null) {
                onPackagesUnavailable(pkgList2);
                for (String str2 : pkgList2) {
                    onPackageDisappeared(str2, this.mChangeType);
                }
            }
        } else if (Intent.ACTION_PACKAGES_SUSPENDED.equals(action)) {
            String[] pkgList3 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            this.mSomePackagesChanged = true;
            onPackagesSuspended(pkgList3);
        } else if (Intent.ACTION_PACKAGES_UNSUSPENDED.equals(action)) {
            String[] pkgList4 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
            this.mSomePackagesChanged = true;
            onPackagesUnsuspended(pkgList4);
        } else if (Intent.ACTION_PACKAGE_UNSTOPPED.equals(action)) {
            String pkgName = getPackageName(intent);
            this.mAppearingPackages = new String[] {pkgName};
            this.mChangeType = 2;
            onPackageUnstopped(
                    pkgName, intent.getIntExtra(Intent.EXTRA_UID, 0), intent.getExtras());
        }
        if (this.mSomePackagesChanged) {
            onSomePackagesChanged();
        }
        onFinishPackageChanges();
        this.mChangeUserId = -10000;
    }

    private static final class PackageMonitorCallback extends IRemoteCallback.Stub {
        private final WeakReference<PackageMonitor> mMonitorWeakReference;

        PackageMonitorCallback(PackageMonitor monitor) {
            this.mMonitorWeakReference = new WeakReference<>(monitor);
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(Bundle data) throws RemoteException {
            onHandlePackageMonitorCallback(data);
        }

        private void onHandlePackageMonitorCallback(Bundle bundle) {
            Intent intent =
                    (Intent)
                            bundle.getParcelable(
                                    PackageManager.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT,
                                    Intent.class);
            if (intent == null) {
                Log.w(PackageMonitor.TAG, "No intent is set for PackageMonitorCallback");
                return;
            }
            PackageMonitor monitor = this.mMonitorWeakReference.get();
            if (monitor != null) {
                monitor.postHandlePackageEvent(intent);
            }
        }
    }
}
