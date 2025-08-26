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

/* loaded from: classes5.dex */
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

    public void onBeginPackageChanges() {
    }

    public void onFinishPackageChanges() {
    }

    public boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
        return false;
    }

    public void onPackageAdded(String str, int i) {
    }

    public void onPackageAddedWithExtras(String str, int i, Bundle bundle) {
    }

    public void onPackageAppeared(String str, int i) {
    }

    public void onPackageAppearedWithExtras(String str, Bundle bundle) {
    }

    public void onPackageChangedWithExtras(String str, Bundle bundle) {
    }

    public void onPackageDataCleared(String str, int i) {
    }

    public void onPackageDisappeared(String str, int i) {
    }

    public void onPackageDisappearedWithExtras(String str, Bundle bundle) {
    }

    public void onPackageModified(String str) {
    }

    public void onPackageModifiedWithExtras(String str, Bundle bundle) {
    }

    public void onPackageRemoved(String str, int i) {
    }

    public void onPackageRemovedAllUsers(String str, int i) {
    }

    public void onPackageRemovedAllUsersWithExtras(String str, int i, Bundle bundle) {
    }

    public void onPackageRemovedWithExtras(String str, int i, Bundle bundle) {
    }

    public void onPackageStateChanged(String str, int i) {
    }

    public void onPackageUnstopped(String str, int i, Bundle bundle) {
    }

    public void onPackageUpdateFinished(String str, int i) {
    }

    public void onPackageUpdateFinishedWithExtras(String str, int i, Bundle bundle) {
    }

    public void onPackageUpdateStarted(String str, int i) {
    }

    public void onPackageUpdateStartedWithExtras(String str, int i, Bundle bundle) {
    }

    public void onPackagesAvailable(String[] strArr) {
    }

    public void onPackagesSuspended(String[] strArr) {
    }

    public void onPackagesUnavailable(String[] strArr) {
    }

    public void onPackagesUnsuspended(String[] strArr) {
    }

    public void onSomePackagesChanged() {
    }

    public void onUidRemoved(int i) {
    }

    public PackageMonitor() {
        this(!Flags.packageRestartQueryDisabledByDefault());
    }

    public PackageMonitor(boolean z) {
        this.mChangeUserId = -10000;
        this.mTempArray = new String[1];
        this.mSupportsPackageRestartQuery = z;
    }

    private IntentFilter getPackageFilter() {
        boolean zIsCore = UserHandle.isCore(Process.myUid());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_QUERY_PACKAGE_RESTART);
        intentFilter.addDataScheme("package");
        if (zIsCore) {
            intentFilter.setPriority(1000);
        }
        return intentFilter;
    }

    public void register(Context context, Looper looper, boolean z) {
        register(context, looper, null, z);
    }

    public void register(Context context, Looper looper, UserHandle userHandle, boolean z) {
        register(context, userHandle, looper == null ? BackgroundThread.getHandler() : new Handler(looper));
    }

    public void register(Context context, UserHandle userHandle, Handler handler) {
        PackageMonitor packageMonitor;
        UserHandle userHandle2;
        PackageManager packageManager;
        if (this.mRegisteredContext != null) {
            throw new IllegalStateException("Already registered");
        }
        this.mRegisteredContext = context;
        this.mRegisteredHandler = (Handler) Objects.requireNonNull(handler);
        if (this.mSupportsPackageRestartQuery) {
            IntentFilter packageFilter = getPackageFilter();
            if (userHandle != null) {
                packageMonitor = this;
                userHandle2 = userHandle;
                context.registerReceiverAsUser(packageMonitor, userHandle2, packageFilter, null, this.mRegisteredHandler);
            } else {
                packageMonitor = this;
                userHandle2 = userHandle;
                context.registerReceiver(packageMonitor, packageFilter, null, packageMonitor.mRegisteredHandler);
            }
        } else {
            packageMonitor = this;
            userHandle2 = userHandle;
        }
        if (packageMonitor.mPackageMonitorCallback != null || (packageManager = packageMonitor.mRegisteredContext.getPackageManager()) == null) {
            return;
        }
        packageMonitor.mExecutor = new HandlerExecutor(packageMonitor.mRegisteredHandler);
        packageMonitor.mPackageMonitorCallback = new PackageMonitorCallback(packageMonitor);
        packageManager.registerPackageMonitorCallback(packageMonitor.mPackageMonitorCallback, userHandle2 != null ? userHandle2.getIdentifier() : packageMonitor.mRegisteredContext.getUserId());
    }

    public Handler getRegisteredHandler() {
        return this.mRegisteredHandler;
    }

    public void unregister() {
        PackageMonitorCallback packageMonitorCallback;
        Context context = this.mRegisteredContext;
        if (context == null) {
            throw new IllegalStateException("Not registered");
        }
        if (this.mSupportsPackageRestartQuery) {
            context.unregisterReceiver(this);
        }
        PackageManager packageManager = this.mRegisteredContext.getPackageManager();
        if (packageManager != null && (packageMonitorCallback = this.mPackageMonitorCallback) != null) {
            packageManager.unregisterPackageMonitorCallback(packageMonitorCallback);
        }
        this.mPackageMonitorCallback = null;
        this.mRegisteredContext = null;
        this.mExecutor = null;
    }

    public boolean onPackageChanged(String str, int i, String[] strArr) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z, Bundle bundle) {
        return onHandleForceStop(intent, strArr, i, z);
    }

    public boolean didSomePackagesChange() {
        return this.mSomePackagesChanged;
    }

    public int isPackageAppearing(String str) {
        String[] strArr = this.mAppearingPackages;
        if (strArr == null) {
            return 0;
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            if (str.equals(this.mAppearingPackages[length])) {
                return this.mChangeType;
            }
        }
        return 0;
    }

    public boolean anyPackagesAppearing() {
        return this.mAppearingPackages != null;
    }

    public int isPackageDisappearing(String str) {
        String[] strArr = this.mDisappearingPackages;
        if (strArr == null) {
            return 0;
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            if (str.equals(this.mDisappearingPackages[length])) {
                return this.mChangeType;
            }
        }
        return 0;
    }

    public boolean anyPackagesDisappearing() {
        return this.mDisappearingPackages != null;
    }

    public boolean isReplacing() {
        return this.mChangeType == 1;
    }

    public boolean isPackageModified(String str) {
        String[] strArr = this.mModifiedPackages;
        if (strArr == null) {
            return false;
        }
        for (int length = strArr.length - 1; length >= 0; length--) {
            if (str.equals(this.mModifiedPackages[length])) {
                return true;
            }
        }
        return false;
    }

    public boolean isComponentModified(String str) {
        String[] strArr;
        if (str != null && (strArr = this.mModifiedComponents) != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                if (str.equals(this.mModifiedComponents[length])) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getChangingUserId() {
        return this.mChangeUserId;
    }

    String getPackageName(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            return data.getSchemeSpecificPart();
        }
        return null;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        lambda$postHandlePackageEvent$0(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postHandlePackageEvent(final Intent intent) {
        Executor executor = this.mExecutor;
        if (executor != null) {
            executor.execute(new Runnable() { // from class: com.android.internal.content.PackageMonitor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$postHandlePackageEvent$0(intent);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x024c  */
    /* renamed from: doHandlePackageEvent, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void lambda$postHandlePackageEvent$0(Intent intent) {
        PackageMonitor packageMonitor;
        int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
        this.mChangeUserId = intExtra;
        if (intExtra == -10000) {
            Slog.w(TAG, "Intent broadcast does not contain user handle: " + intent);
            return;
        }
        onBeginPackageChanges();
        this.mAppearingPackages = null;
        this.mDisappearingPackages = null;
        int i = 0;
        this.mSomePackagesChanged = false;
        this.mModifiedComponents = null;
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String packageName = getPackageName(intent);
            int intExtra2 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            this.mSomePackagesChanged = true;
            if (packageName != null) {
                String[] strArr = this.mTempArray;
                this.mAppearingPackages = strArr;
                strArr[0] = packageName;
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    this.mModifiedPackages = this.mTempArray;
                    this.mChangeType = 1;
                    onPackageUpdateFinished(packageName, intExtra2);
                    onPackageUpdateFinishedWithExtras(packageName, intExtra2, intent.getExtras());
                    onPackageModified(packageName);
                    onPackageModifiedWithExtras(packageName, intent.getExtras());
                } else {
                    this.mChangeType = 3;
                    onPackageAdded(packageName, intExtra2);
                    onPackageAddedWithExtras(packageName, intExtra2, intent.getExtras());
                }
                onPackageAppearedWithExtras(packageName, intent.getExtras());
                onPackageAppeared(packageName, this.mChangeType);
            }
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            String packageName2 = getPackageName(intent);
            int intExtra3 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            if (packageName2 != null) {
                String[] strArr2 = this.mTempArray;
                this.mDisappearingPackages = strArr2;
                strArr2[0] = packageName2;
                if (intent.getBooleanExtra(Intent.EXTRA_REPLACING, false)) {
                    this.mChangeType = 1;
                    onPackageUpdateStarted(packageName2, intExtra3);
                    onPackageUpdateStartedWithExtras(packageName2, intExtra3, intent.getExtras());
                    if (intent.getBooleanExtra(Intent.EXTRA_ARCHIVAL, false)) {
                        onPackageModified(packageName2);
                        onPackageModifiedWithExtras(packageName2, intent.getExtras());
                    }
                } else {
                    this.mChangeType = 3;
                    this.mSomePackagesChanged = true;
                    onPackageRemoved(packageName2, intExtra3);
                    onPackageRemovedWithExtras(packageName2, intExtra3, intent.getExtras());
                    if (intent.getBooleanExtra(Intent.EXTRA_REMOVED_FOR_ALL_USERS, false)) {
                        onPackageRemovedAllUsers(packageName2, intExtra3);
                        onPackageRemovedAllUsersWithExtras(packageName2, intExtra3, intent.getExtras());
                    }
                }
                onPackageDisappearedWithExtras(packageName2, intent.getExtras());
                onPackageDisappeared(packageName2, this.mChangeType);
            }
        } else if (Intent.ACTION_PACKAGE_CHANGED.equals(action)) {
            String packageName3 = getPackageName(intent);
            int intExtra4 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            String[] stringArrayExtra = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_COMPONENT_NAME_LIST);
            this.mModifiedComponents = stringArrayExtra;
            if (packageName3 != null) {
                String[] strArr3 = this.mTempArray;
                this.mModifiedPackages = strArr3;
                strArr3[0] = packageName3;
                this.mChangeType = 3;
                if (onPackageChanged(packageName3, intExtra4, stringArrayExtra)) {
                    this.mSomePackagesChanged = true;
                }
                onPackageChangedWithExtras(packageName3, intent.getExtras());
                onPackageModified(packageName3);
                onPackageModifiedWithExtras(packageName3, intent.getExtras());
            }
        } else if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
            String packageName4 = getPackageName(intent);
            int intExtra5 = intent.getIntExtra(Intent.EXTRA_UID, 0);
            if (packageName4 != null) {
                onPackageDataCleared(packageName4, intExtra5);
            }
        } else {
            if (Intent.ACTION_QUERY_PACKAGE_RESTART.equals(action)) {
                String[] stringArrayExtra2 = intent.getStringArrayExtra(Intent.EXTRA_PACKAGES);
                this.mDisappearingPackages = stringArrayExtra2;
                this.mChangeType = 2;
                boolean zOnHandleForceStop = onHandleForceStop(intent, stringArrayExtra2, intent.getIntExtra(Intent.EXTRA_UID, 0), false, intent.getExtras());
                packageMonitor = this;
                if (zOnHandleForceStop) {
                    packageMonitor.setResultCode(-1);
                }
            } else {
                packageMonitor = this;
                if (Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
                    String[] strArr4 = {packageMonitor.getPackageName(intent)};
                    packageMonitor.mDisappearingPackages = strArr4;
                    packageMonitor.mChangeType = 2;
                    packageMonitor.onHandleForceStop(intent, strArr4, intent.getIntExtra(Intent.EXTRA_UID, 0), true, intent.getExtras());
                } else if (Intent.ACTION_UID_REMOVED.equals(action)) {
                    packageMonitor.onUidRemoved(intent.getIntExtra(Intent.EXTRA_UID, 0));
                } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action)) {
                    String[] stringArrayExtra3 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                    packageMonitor.mAppearingPackages = stringArrayExtra3;
                    packageMonitor.mChangeType = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false) ? 1 : 2;
                    packageMonitor.mSomePackagesChanged = true;
                    if (stringArrayExtra3 != null) {
                        packageMonitor.onPackagesAvailable(stringArrayExtra3);
                        while (i < stringArrayExtra3.length) {
                            packageMonitor.onPackageAppeared(stringArrayExtra3[i], packageMonitor.mChangeType);
                            i++;
                        }
                    }
                } else if (Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
                    String[] stringArrayExtra4 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                    packageMonitor.mDisappearingPackages = stringArrayExtra4;
                    packageMonitor.mChangeType = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false) ? 1 : 2;
                    packageMonitor.mSomePackagesChanged = true;
                    if (stringArrayExtra4 != null) {
                        packageMonitor.onPackagesUnavailable(stringArrayExtra4);
                        while (i < stringArrayExtra4.length) {
                            packageMonitor.onPackageDisappeared(stringArrayExtra4[i], packageMonitor.mChangeType);
                            i++;
                        }
                    }
                } else if (Intent.ACTION_PACKAGES_SUSPENDED.equals(action)) {
                    String[] stringArrayExtra5 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                    packageMonitor.mSomePackagesChanged = true;
                    packageMonitor.onPackagesSuspended(stringArrayExtra5);
                } else if (Intent.ACTION_PACKAGES_UNSUSPENDED.equals(action)) {
                    String[] stringArrayExtra6 = intent.getStringArrayExtra(Intent.EXTRA_CHANGED_PACKAGE_LIST);
                    packageMonitor.mSomePackagesChanged = true;
                    packageMonitor.onPackagesUnsuspended(stringArrayExtra6);
                } else if (Intent.ACTION_PACKAGE_UNSTOPPED.equals(action)) {
                    String packageName5 = packageMonitor.getPackageName(intent);
                    packageMonitor.mAppearingPackages = new String[]{packageName5};
                    packageMonitor.mChangeType = 2;
                    packageMonitor.onPackageUnstopped(packageName5, intent.getIntExtra(Intent.EXTRA_UID, 0), intent.getExtras());
                }
            }
            if (packageMonitor.mSomePackagesChanged) {
                packageMonitor.onSomePackagesChanged();
            }
            packageMonitor.onFinishPackageChanges();
            packageMonitor.mChangeUserId = -10000;
        }
        packageMonitor = this;
        if (packageMonitor.mSomePackagesChanged) {
        }
        packageMonitor.onFinishPackageChanges();
        packageMonitor.mChangeUserId = -10000;
    }

    private static final class PackageMonitorCallback extends IRemoteCallback.Stub {
        private final WeakReference<PackageMonitor> mMonitorWeakReference;

        PackageMonitorCallback(PackageMonitor packageMonitor) {
            this.mMonitorWeakReference = new WeakReference<>(packageMonitor);
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(Bundle bundle) throws RemoteException {
            onHandlePackageMonitorCallback(bundle);
        }

        private void onHandlePackageMonitorCallback(Bundle bundle) {
            Intent intent = (Intent) bundle.getParcelable(PackageManager.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT, Intent.class);
            if (intent == null) {
                Log.w(PackageMonitor.TAG, "No intent is set for PackageMonitorCallback");
                return;
            }
            PackageMonitor packageMonitor = this.mMonitorWeakReference.get();
            if (packageMonitor != null) {
                packageMonitor.postHandlePackageEvent(intent);
            }
        }
    }
}
