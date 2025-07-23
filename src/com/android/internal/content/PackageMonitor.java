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
        boolean isCore = UserHandle.isCore(Process.myUid());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_QUERY_PACKAGE_RESTART);
        intentFilter.addDataScheme("package");
        if (isCore) {
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
                    PackageMonitor.this.lambda$postHandlePackageEvent$0(intent);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x024c  */
    /* renamed from: doHandlePackageEvent, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void lambda$postHandlePackageEvent$0(android.content.Intent r14) {
        /*
            Method dump skipped, instructions count: 597
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.content.PackageMonitor.lambda$postHandlePackageEvent$0(android.content.Intent):void");
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
