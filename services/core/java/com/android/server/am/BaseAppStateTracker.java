package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.media.session.MediaSessionManager;
import android.os.BatteryManagerInternal;
import android.os.BatteryStatsInternal;
import android.os.ServiceManager;
import android.permission.PermissionManager;
import android.util.proto.ProtoOutputStream;

import com.android.internal.app.IAppOpsService;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BaseAppStateTracker {
    public final AppRestrictionController mAppRestrictionController;
    public final AppRestrictionController.BgHandler mBgHandler;
    public final Context mContext;
    public final Object mLock;
    public final ArrayList mStateListeners = new ArrayList();
    public final Injector mInjector = new Injector();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public ActivityManagerInternal mActivityManagerInternal;
        public BaseAppStatePolicy mAppStatePolicy;
        public BatteryManagerInternal mBatteryManagerInternal;
        public BatteryStatsInternal mBatteryStatsInternal;
        public Context mContext;
        public IAppOpsService mIAppOpsService;
        public MediaSessionManager mMediaSessionManager;
        public PackageManager mPackageManager;
        public PackageManagerInternal mPackageManagerInternal;
        public PermissionManager mPermissionManager;
        public UserManagerInternal mUserManagerInternal;
    }

    public BaseAppStateTracker(Context context, AppRestrictionController appRestrictionController) {
        this.mContext = context;
        this.mAppRestrictionController = appRestrictionController;
        this.mBgHandler = appRestrictionController.mBgHandler;
        this.mLock = appRestrictionController.mLock;
    }

    public abstract void dump(PrintWriter printWriter, String str);

    public void dumpAsProto(int i, ProtoOutputStream protoOutputStream) {}

    public byte[] getTrackerInfoForStatsd(int i) {
        return null;
    }

    public abstract int getType();

    public final void notifyListenersOnStateChange(int i, int i2, long j, String str, boolean z) {
        synchronized (this.mLock) {
            try {
                int size = this.mStateListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((AppBatteryExemptionTracker) this.mStateListeners.get(i3))
                            .onStateChange(i, i2, j, str, z);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onBackgroundRestrictionChanged(int i, boolean z) {}

    public void onLockedBootCompleted() {}

    public void onSystemReady() {
        Injector injector = this.mInjector;
        injector.getClass();
        injector.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        injector.mBatteryManagerInternal =
                (BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class);
        injector.mBatteryStatsInternal =
                (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
        injector.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        injector.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        Context context = injector.mAppStatePolicy.mTracker.mContext;
        injector.mPackageManager = context.getPackageManager();
        injector.mMediaSessionManager =
                (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
        injector.mPermissionManager =
                (PermissionManager) context.getSystemService(PermissionManager.class);
        injector.mIAppOpsService =
                IAppOpsService.Stub.asInterface(ServiceManager.getService("appops"));
        injector.mContext = context;
        injector.mAppStatePolicy.onSystemReady();
    }

    public void onUidGone(int i) {}

    public void onUidProcStateChanged(int i, int i2) {}

    public void onUidRemoved(int i) {}

    public void onUserInteractionStarted(String str, int i) {}

    public void onUserRemoved(int i) {}

    public void onUserStarted(int i) {}

    public void onUserStopped(int i) {}
}
