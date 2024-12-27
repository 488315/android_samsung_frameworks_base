package com.android.server.desktopmode;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;
import com.android.internal.content.PackageMonitor;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PackageStateManager extends PackageMonitor {
    public final Context mContext;
    public final EnterpriseDeviceManager mEnterpriseDeviceManager;
    public final Object mLock;
    public final IPackageManager mPackageManager;
    public final Map mPackageState;
    public final IStateManager mStateManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StateListener extends StateManager.StateListener {
        public StateListener() {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onDualModeSetDesktopModeInternal(boolean z) {
            PackageStateManager packageStateManager = PackageStateManager.this;
            packageStateManager.setSettingsComponent(((StateManager) packageStateManager.mStateManager).getState());
            PackageStateManager.m417$$Nest$msetComponent(packageStateManager, "com.sec.android.desktopmode.uiservice.activity.touchpad.TouchpadActivity", z);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onEmergencyModeChanged() {
            PackageStateManager.this.updatePackageState();
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onSetDesktopModeInternal(boolean z) {
            PackageStateManager packageStateManager = PackageStateManager.this;
            packageStateManager.setSettingsComponent(((StateManager) packageStateManager.mStateManager).getState());
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onUserChanged(StateManager.InternalState internalState) {
            PackageStateManager packageStateManager = PackageStateManager.this;
            packageStateManager.updatePackageState();
            PackageStateManager.m417$$Nest$msetComponent(packageStateManager, "com.sec.android.desktopmode.uiservice.DesktopModeTile", true);
            PackageStateManager.m417$$Nest$msetComponent(packageStateManager, "com.sec.android.desktopmode.uiservice.DesktopDisplayDesktopModeTile", true);
            PackageStateManager.m417$$Nest$msetComponent(packageStateManager, "com.sec.android.desktopmode.uiservice.activity.touchpad.TouchpadActivity", false);
            packageStateManager.setSettingsComponent(internalState);
        }
    }

    /* renamed from: -$$Nest$msetComponent, reason: not valid java name */
    public static void m417$$Nest$msetComponent(PackageStateManager packageStateManager, String str, boolean z) {
        packageStateManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]PackageStateManager", "setComponent(component= " + str + ", enabled=" + z + ")");
        }
        try {
            packageStateManager.mPackageManager.setComponentEnabledSetting(ComponentName.createRelative("com.sec.android.desktopmode.uiservice", str), z ? 1 : 2, 1, ((StateManager) packageStateManager.mStateManager).getState().mCurrentUserId, "");
        } catch (RemoteException | IllegalArgumentException unused) {
            Log.w("[DMS]PackageStateManager", "Failed to setComponent");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PackageStateManager(Context context, IStateManager iStateManager, ServiceThread serviceThread, IPackageManager iPackageManager) {
        Object obj = new Object();
        this.mLock = obj;
        ArrayMap arrayMap = new ArrayMap();
        this.mPackageState = arrayMap;
        this.mContext = context;
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(new StateListener());
        this.mPackageManager = iPackageManager;
        this.mEnterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
        synchronized (obj) {
            Boolean bool = Boolean.FALSE;
            arrayMap.put("com.sec.android.app.desktoplauncher", bool);
            arrayMap.put("com.sec.android.desktopmode.uiservice", bool);
        }
        Handler handler = new Handler(serviceThread.getLooper());
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.QUERY_PACKAGE_RESTART", "android.intent.action.PACKAGE_RESTARTED");
        m.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        m.addDataScheme("package");
        synchronized (obj) {
            try {
                Iterator it = arrayMap.keySet().iterator();
                while (it.hasNext()) {
                    m.addDataSchemeSpecificPart((String) it.next(), 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Context context2 = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        context2.registerReceiverAsUser(this, userHandle, m, null, handler, 2);
        this.mContext.registerReceiverAsUser(this, userHandle, BatteryService$$ExternalSyntheticOutline0.m("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL"), null, handler, 2);
    }

    public final boolean isPackageAvailable(String str) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (((StateManager) this.mStateManager).getState().mEmergencyModeEnabled) {
            return false;
        }
        try {
            ApplicationInfo applicationInfoAsUser = this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, ((StateManager) this.mStateManager).getState().mCurrentUserId);
            if (applicationInfoAsUser != null) {
                boolean z = applicationInfoAsUser.enabled;
                return (!z || (enterpriseDeviceManager = this.mEnterpriseDeviceManager) == null) ? z : !enterpriseDeviceManager.getApplicationPolicy().isApplicationStartDisabledAsUser(str, r0);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("[DMS]PackageStateManager", "isPackageAvailable failed: unknown package " + str);
        }
        return false;
    }

    public final void onPackageDataCleared(String str, int i) {
        if ("com.sec.android.app.desktoplauncher".equals(str)) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]PackageStateManager", "onPackageDataCleared(), packageName=" + str);
            }
            setSettingsComponent(((StateManager) this.mStateManager).getState());
            ((StateManager) this.mStateManager).notifyLauncherPackageReplaced(true);
        }
    }

    public final void onPackageUpdateFinished(String str, int i) {
        if ("com.sec.android.app.desktoplauncher".equals(str)) {
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]PackageStateManager", "onPackageUpdateFinished(), packageName=" + str);
            }
            setSettingsComponent(((StateManager) this.mStateManager).getState());
            ((StateManager) this.mStateManager).notifyLauncherPackageReplaced(false);
        }
    }

    public final void onReceive(Context context, Intent intent) {
        if (!"com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL".equals(intent.getAction())) {
            super.onReceive(context, intent);
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]PackageStateManager", "onReceive(), action=com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL");
        }
        onSomePackagesChanged();
    }

    public final void onSomePackagesChanged() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]PackageStateManager", "onSomePackagesChanged()");
        }
        updatePackageState();
    }

    public final void setSettingsComponent(StateManager.InternalState internalState) {
        int i = internalState.mDesktopModeState.enabled;
        int i2 = (i == 3 || i == 4) ? 1 : 0;
        for (String str : this.mContext.getResources().getStringArray(R.array.config_default_vm_number)) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString != null) {
                try {
                    if (this.mPackageManager.isPackageAvailable(unflattenFromString.getPackageName(), internalState.mCurrentUserId)) {
                        this.mPackageManager.setComponentEnabledSetting(unflattenFromString, i2, 1, internalState.mCurrentUserId, "");
                    }
                } catch (RemoteException | IllegalArgumentException e) {
                    Log.e("[DMS]PackageStateManager", "Failed to enable/disable components", e);
                }
            }
        }
    }

    public final void updatePackageState() {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            try {
                Iterator it = ((ArrayMap) this.mPackageState).keySet().iterator();
                boolean z = false;
                while (it.hasNext()) {
                    if (updatePackageState((String) it.next())) {
                        z = true;
                    }
                }
                arrayMap = z ? new ArrayMap((ArrayMap) this.mPackageState) : null;
            } finally {
            }
        }
        if (arrayMap != null) {
            StateManager stateManager = (StateManager) this.mStateManager;
            stateManager.getClass();
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]StateManager", "setPackageState(packageState=" + arrayMap + ")");
            }
            synchronized (stateManager.mLock) {
                stateManager.mInternalState.mPackageState = arrayMap;
                stateManager.mHandler.post(new StateManager$$ExternalSyntheticLambda0(stateManager, stateManager.copyInternalStateLocked(stateManager.mInternalState), 9));
            }
        }
    }

    public final boolean updatePackageState(String str) {
        synchronized (this.mLock) {
            try {
                boolean isPackageAvailable = isPackageAvailable(str);
                if (isPackageAvailable == ((Boolean) ((ArrayMap) this.mPackageState).get(str)).booleanValue()) {
                    return false;
                }
                ((ArrayMap) this.mPackageState).put(str, Boolean.valueOf(isPackageAvailable));
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]PackageStateManager", "updatePackageState(), packageName=" + str + ", available=" + isPackageAvailable);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
