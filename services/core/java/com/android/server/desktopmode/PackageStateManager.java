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
import android.util.IndentingPrintWriter;
import com.android.internal.content.PackageMonitor;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class PackageStateManager extends PackageMonitor {
    public static final String TAG = "[DMS]" + PackageStateManager.class.getSimpleName();
    public final Context mContext;
    public final EnterpriseDeviceManager mEnterpriseDeviceManager;
    public final Object mLock;
    public final IPackageManager mPackageManager;
    public final Map mPackageState;
    public final IStateManager mStateManager;

    public PackageStateManager(Context context, IStateManager iStateManager, ServiceThread serviceThread, IPackageManager iPackageManager) {
        Object obj = new Object();
        this.mLock = obj;
        ArrayMap arrayMap = new ArrayMap();
        this.mPackageState = arrayMap;
        this.mContext = context;
        this.mStateManager = iStateManager;
        iStateManager.registerListener(new StateListener());
        this.mPackageManager = iPackageManager;
        this.mEnterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
        synchronized (obj) {
            Boolean bool = Boolean.FALSE;
            arrayMap.put("com.sec.android.app.desktoplauncher", bool);
            arrayMap.put("com.sec.android.desktopmode.uiservice", bool);
        }
        register(new Handler(serviceThread.getLooper()));
    }

    public final boolean isPackageAvailable(String str) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (this.mStateManager.getState().isEmergencyModeEnabled()) {
            return false;
        }
        try {
            ApplicationInfo applicationInfoAsUser = this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, this.mStateManager.getState().getCurrentUserId());
            if (applicationInfoAsUser != null) {
                boolean z = applicationInfoAsUser.enabled;
                return (!z || (enterpriseDeviceManager = this.mEnterpriseDeviceManager) == null) ? z : !enterpriseDeviceManager.getApplicationPolicy().isApplicationStartDisabledAsUser(str, r0);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.m12w(TAG, "isPackageAvailable failed: unknown package " + str);
        }
        return false;
    }

    public final void updatePackageState() {
        ArrayMap arrayMap;
        synchronized (this.mLock) {
            Iterator it = this.mPackageState.keySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (updatePackageState((String) it.next())) {
                    z = true;
                }
            }
            arrayMap = z ? new ArrayMap((ArrayMap) this.mPackageState) : null;
        }
        if (arrayMap != null) {
            this.mStateManager.setPackageState(arrayMap);
        }
    }

    public final boolean updatePackageState(String str) {
        synchronized (this.mLock) {
            boolean isPackageAvailable = isPackageAvailable(str);
            if (isPackageAvailable == ((Boolean) this.mPackageState.get(str)).booleanValue()) {
                return false;
            }
            this.mPackageState.put(str, Boolean.valueOf(isPackageAvailable));
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "updatePackageState(), packageName=" + str + ", available=" + isPackageAvailable);
            }
            return true;
        }
    }

    public final void setSettingsComponent(State state) {
        int i = state.getDesktopModeState().enabled;
        setComponentFromList(state, R.array.config_default_vm_number, (i == 3 || i == 4) ? 1 : 0);
    }

    public final void setComponentFromList(State state, int i, int i2) {
        for (String str : this.mContext.getResources().getStringArray(i)) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
            if (unflattenFromString != null) {
                try {
                    if (this.mPackageManager.isPackageAvailable(unflattenFromString.getPackageName(), state.getCurrentUserId())) {
                        this.mPackageManager.setComponentEnabledSetting(unflattenFromString, i2, 1, state.getCurrentUserId(), "");
                    }
                } catch (RemoteException | IllegalArgumentException e) {
                    Log.m9e(TAG, "Failed to enable/disable components", e);
                }
            }
        }
    }

    public final void setComponent(String str, boolean z) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "setComponent(component= " + str + ", enabled=" + z + ")");
        }
        try {
            this.mPackageManager.setComponentEnabledSetting(ComponentName.createRelative("com.sec.android.desktopmode.uiservice", str), z ? 1 : 2, 1, this.mStateManager.getState().getCurrentUserId(), "");
        } catch (RemoteException | IllegalArgumentException unused) {
            Log.m12w(TAG, "Failed to setComponent");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void register(Handler handler) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        synchronized (this.mLock) {
            Iterator it = this.mPackageState.keySet().iterator();
            while (it.hasNext()) {
                intentFilter.addDataSchemeSpecificPart((String) it.next(), 0);
            }
        }
        this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, handler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL");
        this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter2, null, handler);
    }

    public void onReceive(Context context, Intent intent) {
        if ("com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL".equals(intent.getAction())) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "onReceive(), action=com.samsung.android.knox.intent.action.ADD_PACKAGE_PREVENT_START_BLACKLIST_CHANGED_INTERNAL");
            }
            onSomePackagesChanged();
            return;
        }
        super.onReceive(context, intent);
    }

    public void onSomePackagesChanged() {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "onSomePackagesChanged()");
        }
        updatePackageState();
    }

    public void onPackageUpdateFinished(String str, int i) {
        if ("com.sec.android.app.desktoplauncher".equals(str)) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "onPackageUpdateFinished(), packageName=" + str);
            }
            setSettingsComponent(this.mStateManager.getState());
            this.mStateManager.notifyLauncherPackageReplaced(false);
        }
    }

    public void onPackageDataCleared(String str, int i) {
        if ("com.sec.android.app.desktoplauncher".equals(str)) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "onPackageDataCleared(), packageName=" + str);
            }
            setSettingsComponent(this.mStateManager.getState());
            this.mStateManager.notifyLauncherPackageReplaced(true);
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + PackageStateManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            indentingPrintWriter.println("mPackageState=" + this.mPackageState);
        }
        indentingPrintWriter.decreaseIndent();
    }

    public class StateListener extends StateManager.StateListener {
        public StateListener() {
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onEmergencyModeChanged(State state) {
            PackageStateManager.this.updatePackageState();
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onUserChanged(State state) {
            PackageStateManager.this.updatePackageState();
            PackageStateManager.this.setComponent("com.sec.android.desktopmode.uiservice.DesktopModeTile", true);
            PackageStateManager.this.setComponent("com.sec.android.desktopmode.uiservice.DesktopDisplayDesktopModeTile", true);
            PackageStateManager.this.setComponent("com.sec.android.desktopmode.uiservice.activity.touchpad.TouchpadActivity", false);
            PackageStateManager.this.setSettingsComponent(state);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onSetDesktopModeInternal(boolean z) {
            PackageStateManager packageStateManager = PackageStateManager.this;
            packageStateManager.setSettingsComponent(packageStateManager.mStateManager.getState());
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public void onDualModeSetDesktopModeInternal(boolean z) {
            PackageStateManager packageStateManager = PackageStateManager.this;
            packageStateManager.setSettingsComponent(packageStateManager.mStateManager.getState());
            PackageStateManager.this.setComponent("com.sec.android.desktopmode.uiservice.activity.touchpad.TouchpadActivity", z);
        }
    }
}
