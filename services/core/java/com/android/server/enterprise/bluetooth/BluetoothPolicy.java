package com.android.server.enterprise.bluetooth;

import android.R;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.BlackWhiteListPolicy;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.SettingNotFoundException;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.location.gnss.hal.GnssNative;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.ControlInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.bluetooth.BluetoothControlInfo;
import com.samsung.android.knox.bluetooth.IBluetoothPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public class BluetoothPolicy extends IBluetoothPolicy.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public BluetoothDevicePolicy mDevicePolicy;
    public EnterpriseDeviceManager mEDM;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public BlockingQueue mLogQueue;
    public BluetoothProfilePolicy mProfilePolicy;
    public final BroadcastReceiver mReceiver;
    public boolean mRestart;
    public boolean mCacheIsBluetoothLogEnabled = false;
    public boolean isCacheUpdated = false;
    public StoreLogThread mThread = null;
    public Map mProfileMap = new HashMap();

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    public BluetoothPolicy(Context context) {
        this.mLogQueue = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.bluetooth.BluetoothPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                BluetoothDevice bluetoothDevice;
                BluetoothClass bluetoothClass;
                String action = intent.getAction();
                Log.d("BluetoothPolicyService", "onReceive " + action);
                if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 10 && BluetoothPolicy.this.mRestart) {
                        Log.d("BluetoothPolicyService", "***** Restarting Bluetooth *****");
                        BluetoothPolicy.this.mRestart = false;
                        if (BluetoothAdapter.getDefaultAdapter() != null) {
                            BluetoothAdapter.getDefaultAdapter().enable();
                            return;
                        }
                        return;
                    }
                    return;
                }
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(action)) {
                    BluetoothPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                    return;
                }
                if (!"android.bluetooth.device.action.ACL_CONNECTED".equals(action) || BluetoothPolicy.this.isDesktopConnectivityEnabled(false) || (bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")) == null || (bluetoothClass = bluetoothDevice.getBluetoothClass()) == null || bluetoothClass.getMajorDeviceClass() != 256) {
                    return;
                }
                Log.d("BluetoothPolicyService", "Unpair this bluetooth computer(ACL CONNECTED) : " + bluetoothDevice.getAddress());
                bluetoothDevice.removeBond();
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mRestart = false;
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
        this.mLogQueue = new LinkedBlockingQueue();
        this.mProfilePolicy = new BluetoothProfilePolicy(this.mContext, "BLUETOOTH_PROFILE_BWLIST");
        this.mDevicePolicy = new BluetoothDevicePolicy(this.mContext, "BLUETOOTH_DEVICE_BWLIST");
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
        initProfileMap();
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public boolean isBluetoothEnabled(ContextInfo contextInfo) {
        return isBluetoothEnabled(false);
    }

    public boolean isBluetoothEnabledWithMsg(boolean z) {
        return isBluetoothEnabled(z);
    }

    public boolean isBluetoothEnabled(boolean z) {
        boolean z2;
        if (isBluetoothRestrictedByConstrainedState()) {
            z2 = false;
        } else {
            ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "bluetoothEnabled");
            if (booleanList != null) {
                Iterator it = booleanList.iterator();
                while (it.hasNext()) {
                    boolean booleanValue = ((Boolean) it.next()).booleanValue();
                    if (!booleanValue) {
                        z2 = booleanValue;
                        break;
                    }
                }
            }
            z2 = true;
        }
        Log.d("BluetoothPolicyService", "isBluetoothEnabled : " + z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_deviceConfiguratorPackageName);
        }
        return z2;
    }

    public boolean setBluetooth(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
                boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "bluetoothEnabled", z);
                Log.w("BluetoothPolicyService", "setBluetooth : " + z);
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (z && isBluetoothEnabled(enforceOwnerOnlyAndBluetoothPermission)) {
                    defaultAdapter.enable();
                } else if (defaultAdapter.getState() == 12) {
                    defaultAdapter.disable();
                } else if (defaultAdapter.getState() == 11) {
                    delayedBTOff();
                }
                if (putBoolean) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed bluetooth enabled to %s", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z2 = putBoolean;
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            if (z2) {
                setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(enforceOwnerOnlyAndBluetoothPermission));
            }
            return z2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean allowBluetooth(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        if (!isBLEAllowed(enforceOwnerOnlyAndBluetoothPermission) && getAdminUidForBLE() == enforceOwnerOnlyAndBluetoothPermission.mCallerUid) {
            Log.d("BluetoothPolicyService", "failed to allowBluetooth due to BLE policy");
            return false;
        }
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter.getState() == 12) {
                    defaultAdapter.disable();
                } else if (defaultAdapter.getState() == 11) {
                    delayedBTOff();
                }
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "bluetoothEnabled", z);
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed allow bluetooth to %s", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
            setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(enforceOwnerOnlyAndBluetoothPermission));
        }
        return putBoolean;
    }

    public final boolean allowBluetoothForBLE(ContextInfo contextInfo, boolean z) {
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z) {
            try {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter.getState() == 12) {
                    defaultAdapter.disable();
                } else if (defaultAdapter.getState() == 11) {
                    delayedBTOff();
                }
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
                return false;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "BLUETOOTH", "bluetoothEnabled", z);
        if (putBoolean) {
            AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format("Admin %d has changed allow bluetooth to %s", Integer.valueOf(contextInfo.mCallerUid), Boolean.valueOf(z)), UserHandle.getUserId(contextInfo.mCallerUid));
            setBluetoothAllowedSystemUI(callingOrCurrentUserId, isBluetoothEnabled(contextInfo));
        }
        return putBoolean;
    }

    public final void delayedBTOff() {
        new Thread() { // from class: com.android.server.enterprise.bluetooth.BluetoothPolicy.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                final ConditionVariable conditionVariable = new ConditionVariable();
                IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothPolicy.this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.enterprise.bluetooth.BluetoothPolicy.2.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        if (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE) == 12) {
                            conditionVariable.open();
                        }
                    }
                }, intentFilter);
                conditionVariable.block(10000L);
                if (defaultAdapter != null) {
                    defaultAdapter.disable();
                }
            }
        }.start();
    }

    public final ContextInfo enforceBluetoothPermission(ContextInfo contextInfo) {
        return getEDM().enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BLUETOOTH")));
    }

    public final ContextInfo enforceOwnerOnlyAndBluetoothPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_BLUETOOTH")));
    }

    public final ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION")));
    }

    public boolean setAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setAllowBluetoothDataTransfer = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        restartBluetooth();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "allowDataTransfer", z);
    }

    public boolean getAllowBluetoothDataTransfer(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("BluetoothPolicyService", "getAllowBluetoothDataTransfer - showMsg: " + z);
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "allowDataTransfer");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z2 = ((Boolean) it.next()).booleanValue();
                if (!z2) {
                    break;
                }
            }
        }
        z2 = true;
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_defaultSmartspaceService);
        }
        return z2;
    }

    public boolean setPairingState(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setPairingState = " + z);
        return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid, "BLUETOOTH", "pairingEnabled", z);
    }

    public boolean isPairingEnabled(ContextInfo contextInfo) {
        return isPairingEnabled(false);
    }

    public boolean isPairingEnabled(boolean z) {
        boolean z2;
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "pairingEnabled");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z2 = ((Boolean) it.next()).booleanValue();
                if (!z2) {
                    break;
                }
            }
        }
        z2 = true;
        Log.d("BluetoothPolicyService", "isPairingEnabled " + z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_defaultWearableSensingService);
        }
        return z2;
    }

    public boolean allowOutgoingCalls(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "allowOutgoingCalls = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "allowOutgoingCalls", z);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (z2 && defaultAdapter != null && defaultAdapter.isEnabled()) {
                this.mRestart = true;
                defaultAdapter.disable();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean isOutgoingCallsAllowed(ContextInfo contextInfo) {
        return isOutgoingCallsAllowed(false);
    }

    public boolean isOutgoingCallsAllowed(boolean z) {
        boolean z2;
        Log.d("BluetoothPolicyService", "isPairingEnabled ");
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "allowOutgoingCalls");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z2 = ((Boolean) it.next()).booleanValue();
                if (!z2) {
                    break;
                }
            }
        }
        z2 = true;
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_defaultWearableSensingConsentComponent);
        }
        return z2;
    }

    public boolean setLimitedDiscoverableState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "limitedDiscoverableEnabled", z);
            Log.w("BluetoothPolicyService", "setLimitedDiscoverableState : " + z);
            if (putBoolean) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format(z ? "Admin %d has enabled bluetooth limited discoverable state." : "Admin %d has disabled bluetooth limited discoverable state.", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
            }
            return putBoolean;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isLimitedDiscoverableEnabled(ContextInfo contextInfo) {
        boolean z;
        Log.d("BluetoothPolicyService", "isLimitedDiscoverableEnabled ");
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "limitedDiscoverableEnabled");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z = ((Boolean) it.next()).booleanValue();
                if (!z) {
                    break;
                }
            }
        }
        z = true;
        Log.d("BluetoothPolicyService", "isLimitedDiscoverableEnabled ret:" + z);
        return z;
    }

    public boolean setProfileState(ContextInfo contextInfo, boolean z, int i) {
        int i2;
        Log.d("BluetoothPolicyService", "setProfileState = " + i + " state " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        int i3 = enforceOwnerOnlyAndBluetoothPermission.mCallerUid;
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndBluetoothPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            try {
            } catch (InvalidParameterException unused) {
                Log.w("BluetoothPolicyService", "setProfileState() failed: INVALID PARAMETER INPUT");
            } catch (Exception e) {
                Log.w("BluetoothPolicyService", e.toString());
            }
            if (i <= 0) {
                throw new InvalidParameterException();
            }
            try {
                i2 = this.mEdmStorageProvider.getInt(i3, "BLUETOOTH", "profileSettings");
            } catch (SettingNotFoundException unused2) {
                i2 = GnssNative.GNSS_AIDING_TYPE_ALL;
            }
            z2 = this.mEdmStorageProvider.putInt(i3, "BLUETOOTH", "profileSettings", true == z ? i2 | i : i2 & (~i));
            if (z2) {
                auditLogMessage(i3, i, z, callingOrCurrentUserId);
            }
            restartBluetooth();
            return z2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void auditLogMessage(int i, int i2, boolean z, int i3) {
        String str = i2 != 1 ? i2 != 2 ? i2 != 4 ? i2 != 8 ? i2 != 16 ? i2 != 32 ? i2 != 64 ? i2 != 128 ? i2 != 256 ? i2 != 512 ? "" : "BPP" : "SAP" : "SPP" : "FTP" : "DUN" : "AVRCP" : "A2DP" : "PBAP" : "HFP" : "HSP";
        int myPid = Process.myPid();
        String str2 = z ? "Admin %d has allowed %s bluetooth profile." : "Admin %d has blocked %s bluetooth profile.";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(i);
        boolean isEmpty = str.isEmpty();
        Object obj = str;
        if (isEmpty) {
            obj = Integer.valueOf(i2);
        }
        objArr[1] = obj;
        AuditLog.logAsUser(5, 1, true, myPid, "BluetoothPolicy", String.format(str2, objArr), i3);
    }

    public boolean isProfileEnabled(ContextInfo contextInfo, int i) {
        return isProfileEnabled(Utils.getCallingOrUserUid(contextInfo), i);
    }

    public final boolean isProfileEnabled(int i, int i2) {
        try {
            if (i2 <= 0) {
                throw new InvalidParameterException();
            }
            ArrayList intList = this.mEdmStorageProvider.getIntList("BLUETOOTH", "profileSettings");
            if (intList.isEmpty()) {
                return true;
            }
            Iterator it = intList.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                if (num.intValue() >= 0 && i2 != (num.intValue() & i2)) {
                    return false;
                }
            }
            return true;
        } catch (InvalidParameterException unused) {
            Log.w("BluetoothPolicyService", "isProfileEnabled() failed: INVALID PARAMETER INPUT");
            return true;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public final void showProfileBlockedToast(int i) {
        int i2;
        if (i == 1) {
            i2 = R.string.config_defaultWallpaperEffectsGenerationService;
        } else if (i == 2) {
            i2 = R.string.config_defaultTrustAgent;
        } else if (i == 4) {
            i2 = R.string.config_defaultWellbeingPackage;
        } else if (i == 8) {
            i2 = R.string.config_defaultProfcollectReportUploaderApp;
        } else if (i == 16) {
            i2 = R.string.config_defaultRotationResolverService;
        } else if (i == 32) {
            i2 = R.string.config_defaultTextClassifierPackage;
        } else if (i == 64) {
            i2 = R.string.config_defaultTranslationService;
        } else if (i == 128) {
            i2 = R.string.config_deviceSpecificDevicePolicyManagerService;
        } else if (i == 256) {
            i2 = R.string.config_devicePolicyManagementUpdater;
        } else if (i != 512) {
            return;
        } else {
            i2 = R.string.config_defaultSearchSelectorPackageName;
        }
        RestrictionToastManager.show(i2);
    }

    public boolean isProfileEnabledInternal(int i, boolean z) {
        boolean z2 = true;
        try {
        } catch (InvalidParameterException unused) {
            Log.w("BluetoothPolicyService", "isProfileEnabledInternal() failed: INVALID PARAMETER INPUT");
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        if (i <= 0) {
            throw new InvalidParameterException();
        }
        if (!isProfileEnabled(Binder.getCallingUid(), i)) {
            if (z) {
                showProfileBlockedToast(i);
            }
            return false;
        }
        List list = (List) this.mProfileMap.get(Integer.valueOf(i));
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            if (!isBluetoothUUIDAllowed((String) list.get(i2))) {
                z2 = false;
                break;
            }
            i2++;
        }
        if (z && !z2) {
            showProfileBlockedToast(i);
        }
        return z2;
    }

    public boolean setDiscoverableState(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "discoverableModeEnabled", z);
            Log.w("BluetoothPolicyService", "setDiscoverableState : " + z);
            if (putBoolean) {
                AuditLog.logAsUser(5, 1, true, Process.myPid(), "BluetoothPolicy", String.format(z ? "Admin %d has enabled Bluetooth discoverable state." : "Admin %d has disabled Bluetooth discoverable state.", Integer.valueOf(enforceOwnerOnlyAndBluetoothPermission.mCallerUid)), UserHandle.getUserId(enforceOwnerOnlyAndBluetoothPermission.mCallerUid));
            }
            return putBoolean;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isDiscoverableEnabled(ContextInfo contextInfo) {
        boolean z;
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "discoverableModeEnabled");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z = ((Boolean) it.next()).booleanValue();
                if (!z) {
                    break;
                }
            }
        }
        z = true;
        Log.d("BluetoothPolicyService", "isDiscoverableEnabled : " + z);
        return z;
    }

    public boolean setDesktopConnectivityState(ContextInfo contextInfo, boolean z) {
        boolean z2;
        Log.d("BluetoothPolicyService", "setDesktopConnectivityState = " + z);
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            z2 = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", "desktopConnectivityEnabled", z);
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!z && z2 && defaultAdapter != null && defaultAdapter.isEnabled()) {
                disableDesktopConnectivity();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            z2 = false;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public final void disableDesktopConnectivity() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getBondedDevices()) {
                BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
                if (bluetoothClass != null && bluetoothClass.getMajorDeviceClass() == 256) {
                    if (bluetoothDevice.isConnected()) {
                        Log.d("BluetoothPolicyService", "Unpair this bluetooth computer(connected) : " + bluetoothDevice.getAddress());
                        bluetoothDevice.removeBond();
                    } else {
                        Log.d("BluetoothPolicyService", "Keep this bluetooth computer(not connected) : " + bluetoothDevice.getAddress());
                    }
                }
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public boolean isDesktopConnectivityEnabled(ContextInfo contextInfo) {
        return isDesktopConnectivityEnabled(false);
    }

    public boolean isDesktopConnectivityEnabled(boolean z) {
        boolean z2;
        ArrayList booleanList = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "desktopConnectivityEnabled");
        if (booleanList != null) {
            Iterator it = booleanList.iterator();
            while (it.hasNext()) {
                z2 = ((Boolean) it.next()).booleanValue();
                if (!z2) {
                    break;
                }
            }
        }
        z2 = true;
        Log.d("BluetoothPolicyService", "isDesktopConnectivityEnabled : " + z2);
        if (z && !z2) {
            RestrictionToastManager.show(R.string.config_defaultSupervisionProfileOwnerComponent);
        }
        return z2;
    }

    public boolean setBluetoothLogEnabled(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "setBluetoothLogEnabled(" + z + ")");
        ContextInfo enforceOwnerOnlyAndBluetoothPermission = enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        ContentValues contentValues = new ContentValues();
        contentValues.put("bluetoothLogEnabled", String.valueOf(z));
        boolean putValues = this.mEdmStorageProvider.putValues(enforceOwnerOnlyAndBluetoothPermission.mCallerUid, "BLUETOOTH", contentValues);
        if (!z && !getBluetoothLogEnabled(enforceOwnerOnlyAndBluetoothPermission, true)) {
            Log.d("BluetoothPolicyService", "setBluetoothLogEnabled - Clean log");
            this.mEdmStorageProvider.remove("BluetoothLogTable");
            this.mLogQueue.clear();
        }
        Log.d("BluetoothPolicyService", "setBluetoothLogEnabled - return = " + putValues);
        if (putValues) {
            this.isCacheUpdated = false;
        }
        return putValues;
    }

    public boolean isBluetoothLogEnabled(ContextInfo contextInfo) {
        if (!this.isCacheUpdated) {
            this.mCacheIsBluetoothLogEnabled = getBluetoothLogEnabled(contextInfo, true);
            this.isCacheUpdated = true;
        }
        return this.mCacheIsBluetoothLogEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0089, code lost:
    
        if (r8 == null) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean getBluetoothLogEnabled(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "getBluetoothLogEnabled(" + z + ")");
        int i = !z ? enforceBluetoothPermission(contextInfo).mCallerUid : -1;
        String[] strArr = {"bluetoothLogEnabled"};
        Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - uid: " + i);
        boolean z2 = false;
        if (i > 0) {
            Cursor cursor = null;
            try {
                try {
                    cursor = this.mEdmStorageProvider.getCursorByAdmin("BLUETOOTH", i, strArr);
                    if (cursor != null && cursor.getCount() == 1 && cursor.moveToFirst()) {
                        if (cursor.getString(0).equals("true")) {
                            z2 = true;
                        }
                    }
                } catch (SQLException e) {
                    Log.e("BluetoothPolicyService", "Exception occurred accessing Enterprise db " + e.getMessage());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            List valuesList = this.mEdmStorageProvider.getValuesList("BLUETOOTH", strArr);
            if (valuesList != null) {
                Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - cvList: " + valuesList);
                Iterator it = valuesList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ContentValues contentValues = (ContentValues) it.next();
                    Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - cv: " + contentValues);
                    if ("true".equals(contentValues.getAsString("bluetoothLogEnabled"))) {
                        z2 = true;
                        break;
                    }
                }
            }
        }
        Log.d("BluetoothPolicyService", "getBluetoothLogEnabled - ret: " + z2);
        return z2;
    }

    public List getBluetoothLog(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getBluetoothLog()");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        ArrayList arrayList = new ArrayList();
        ArrayList<ContentValues> dataByFields = this.mEdmStorageProvider.getDataByFields("BluetoothLogTable", null, null, new String[]{"time", "log"});
        if (dataByFields == null) {
            Log.d("BluetoothPolicyService", "getBluetoothLog - cvList is null");
            return null;
        }
        Log.d("BluetoothPolicyService", "getBluetoothLog() - cvList: " + dataByFields);
        for (ContentValues contentValues : dataByFields) {
            String asString = contentValues.getAsString("time");
            if (asString != null) {
                String concat = asString.concat(XmlUtils.STRING_ARRAY_SEPARATOR);
                String asString2 = contentValues.getAsString("log");
                if (asString2 != null) {
                    concat = concat.concat(asString2);
                }
                arrayList.add(concat);
                Log.d("BluetoothPolicyService", "getBluetoothLog() - report: " + concat);
            }
        }
        Log.d("BluetoothPolicyService", "getBluetoothLog() - reportList: " + arrayList);
        return arrayList;
    }

    public boolean bluetoothLog(ContextInfo contextInfo, String str, String str2) {
        if (!isBluetoothLogEnabled(contextInfo)) {
            return false;
        }
        Log.d("BluetoothPolicyService", "on server bluetoothLoglogConnectionChanged [" + str + "] " + str2);
        return this.mLogQueue.offer("[" + str + "]\n" + str2);
    }

    public class StoreLogThread extends Thread {
        public StoreLogThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    String str = (String) BluetoothPolicy.this.mLogQueue.take();
                    if (str != null) {
                        long timeInMillis = Calendar.getInstance().getTimeInMillis();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("time", String.valueOf(timeInMillis));
                        contentValues.put("log", str);
                        Log.d("BluetoothPolicyService", "StoreLogThread - cv: " + contentValues);
                        if (!BluetoothPolicy.this.mEdmStorageProvider.putValuesNoUpdate("BluetoothLogTable", contentValues)) {
                            Log.d("BluetoothPolicyService", "StoreLogThread - Failed on inserting log");
                        }
                    }
                } catch (InterruptedException unused) {
                    Log.d("BluetoothPolicyService", "StoreLogThread - InterruptedException on take");
                }
            }
        }
    }

    public boolean addBluetoothUUIDsToBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addProfilesToBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToBlackList = this.mProfilePolicy.addObjectsToBlackList(i, list);
        if (addObjectsToBlackList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToBlackList;
    }

    public boolean removeBluetoothUUIDsFromBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeProfilesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromBlackList = this.mProfilePolicy.removeObjectsFromBlackList(i, list);
        if (removeObjectsFromBlackList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromBlackList;
    }

    public boolean clearBluetoothUUIDsFromBlackList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearProfilesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromBlackList = this.mProfilePolicy.clearObjectsFromBlackList(i);
        if (clearObjectsFromBlackList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromBlackList;
    }

    public List getAllBluetoothUUIDsBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllProfilesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mProfilePolicy.getAllObjectsFromBlackListForAllAdmins());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public List getEffectiveBluetoothUUIDsBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothUUIDsBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveBlackList = this.mProfilePolicy.getEffectiveBlackList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveBlackList;
    }

    public boolean addBluetoothUUIDsToWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addProfilesToWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToWhiteList = this.mProfilePolicy.addObjectsToWhiteList(i, list);
        if (addObjectsToWhiteList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToWhiteList;
    }

    public boolean removeBluetoothUUIDsFromWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeProfilesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromWhiteList = this.mProfilePolicy.removeObjectsFromWhiteList(i, list);
        if (removeObjectsFromWhiteList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromWhiteList;
    }

    public boolean clearBluetoothUUIDsFromWhiteList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearProfilesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromWhiteList = this.mProfilePolicy.clearObjectsFromWhiteList(i);
        if (clearObjectsFromWhiteList && this.mProfilePolicy.isPolicyActive(i)) {
            restartBluetooth();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromWhiteList;
    }

    public List getAllBluetoothUUIDsWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllProfilesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mProfilePolicy.getAllObjectsFromWhiteListForAllAdmins());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public List getEffectiveBluetoothUUIDsWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothUUIDsWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveWhiteList = this.mProfilePolicy.getEffectiveWhiteList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveWhiteList;
    }

    public boolean activateBluetoothUUIDRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "activateBluetoothUUIDRestriction");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            boolean z3 = z != this.mProfilePolicy.isPolicyActive(i);
            z2 = this.mEdmStorageProvider.putBoolean(i, "BLUETOOTH", "profilePolicyEnabled", z);
            if (z2 && z3) {
                this.mProfilePolicy.reload();
                restartBluetooth();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean addBluetoothDevicesToBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addDevicesToBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToBlackList = this.mDevicePolicy.addObjectsToBlackList(i, list);
        if (addObjectsToBlackList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToBlackList;
    }

    public boolean removeBluetoothDevicesFromBlackList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeDevicesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromBlackList = this.mDevicePolicy.removeObjectsFromBlackList(i, list);
        if (removeObjectsFromBlackList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromBlackList;
    }

    public boolean clearBluetoothDevicesFromBlackList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearDevicesFromBlackList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromBlackList = this.mDevicePolicy.clearObjectsFromBlackList(i);
        if (clearObjectsFromBlackList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromBlackList;
    }

    public List getAllBluetoothDevicesBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllDevicesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mDevicePolicy.getAllObjectsFromBlackListForAllAdmins());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public List getEffectiveBluetoothDevicesBlackLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothDevicesBlackLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveBlackList = this.mDevicePolicy.getEffectiveBlackList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveBlackList;
    }

    public boolean addBluetoothDevicesToWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "addDevicesToWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean addObjectsToWhiteList = this.mDevicePolicy.addObjectsToWhiteList(i, list);
        if (addObjectsToWhiteList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return addObjectsToWhiteList;
    }

    public boolean removeBluetoothDevicesFromWhiteList(ContextInfo contextInfo, List list) {
        Log.d("BluetoothPolicyService", "removeDevicesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        if (list == null || list.isEmpty()) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean removeObjectsFromWhiteList = this.mDevicePolicy.removeObjectsFromWhiteList(i, list);
        if (removeObjectsFromWhiteList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return removeObjectsFromWhiteList;
    }

    public boolean clearBluetoothDevicesFromWhiteList(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "clearDevicesFromWhiteList");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean clearObjectsFromWhiteList = this.mDevicePolicy.clearObjectsFromWhiteList(i);
        if (clearObjectsFromWhiteList && this.mDevicePolicy.isPolicyActive(i)) {
            applyDevicePolicy();
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return clearObjectsFromWhiteList;
    }

    public List translateList(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ControlInfo controlInfo = (ControlInfo) it.next();
                List list2 = controlInfo.entries;
                if (list2 != null && !list2.isEmpty()) {
                    BluetoothControlInfo bluetoothControlInfo = new BluetoothControlInfo();
                    ArrayList arrayList2 = new ArrayList();
                    bluetoothControlInfo.entries = arrayList2;
                    bluetoothControlInfo.adminPackageName = controlInfo.adminPackageName;
                    arrayList2.addAll(controlInfo.entries);
                    arrayList.add(bluetoothControlInfo);
                }
            }
        }
        return arrayList;
    }

    public List getAllBluetoothDevicesWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getAllDevicesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List translateList = translateList(this.mDevicePolicy.getAllObjectsFromWhiteListForAllAdmins());
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return translateList;
    }

    public List getEffectiveBluetoothDevicesWhiteLists(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "getEffectiveBluetoothDevicesWhiteLists");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        List effectiveWhiteList = this.mDevicePolicy.getEffectiveWhiteList();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return effectiveWhiteList;
    }

    public boolean activateBluetoothDeviceRestriction(ContextInfo contextInfo, boolean z) {
        Log.d("BluetoothPolicyService", "activateBluetoothDeviceRestriction");
        int i = enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean z2 = false;
        try {
            boolean z3 = z != this.mDevicePolicy.isPolicyActive(i);
            z2 = this.mEdmStorageProvider.putBoolean(i, "BLUETOOTH", "devicePolicyEnabled", z);
            if (z2 && z3) {
                this.mDevicePolicy.reload();
                applyDevicePolicy();
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z2;
    }

    public boolean isBluetoothUUIDRestrictionActive(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "isBluetoothUUIDRestrictionActive");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isPolicyActive = this.mProfilePolicy.isPolicyActive(contextInfo.mCallerUid);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isPolicyActive;
    }

    public boolean isBluetoothDeviceRestrictionActive(ContextInfo contextInfo) {
        Log.d("BluetoothPolicyService", "isBluetoothDeviceRestrictionActive");
        enforceOwnerOnlyAndBluetoothPermission(contextInfo);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isPolicyActive = this.mDevicePolicy.isPolicyActive(contextInfo.mCallerUid);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isPolicyActive;
    }

    public boolean isBluetoothUUIDAllowed(ContextInfo contextInfo, String str) {
        return isBluetoothUUIDAllowed(str);
    }

    public boolean isBluetoothUUIDAllowed(String str) {
        return this.mProfilePolicy.isObjectAllowed(str);
    }

    public int getProfileFromUUID(String str) {
        for (Integer num : this.mProfileMap.keySet()) {
            List list = (List) this.mProfileMap.get(num);
            for (int i = 0; i < list.size(); i++) {
                if (((String) list.get(i)).equalsIgnoreCase(str)) {
                    return num.intValue();
                }
            }
        }
        return -1;
    }

    public boolean isBluetoothUUIDAllowedInternal(String str) {
        int profileFromUUID = getProfileFromUUID(str);
        if (isBluetoothUUIDAllowed(str)) {
            return profileFromUUID == -1 || isProfileEnabled(Binder.getCallingUid(), profileFromUUID);
        }
        showProfileBlockedToast(profileFromUUID);
        return false;
    }

    public boolean isBluetoothDeviceAllowed(ContextInfo contextInfo, String str) {
        return isBluetoothDeviceAllowed(str, false);
    }

    public boolean isBluetoothDeviceAllowed(String str, boolean z) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        boolean isObjectAllowed = this.mDevicePolicy.isObjectAllowed(str);
        if (z && !isObjectAllowed) {
            RestrictionToastManager.show(R.string.config_defaultSystemCaptionsManagerService);
        }
        return isObjectAllowed;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        Log.d("BluetoothPolicyService", "onAdminRemoved - uid: " + i);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        this.mProfilePolicy.reload();
        this.mDevicePolicy.reload();
        this.isCacheUpdated = false;
        if (!getBluetoothLogEnabled(null, true)) {
            Log.d("BluetoothPolicyService", "onAdminRemoved - Clean log");
            this.mEdmStorageProvider.remove("BluetoothLogTable");
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        this.mProfilePolicy.reload();
        this.mDevicePolicy.reload();
        StoreLogThread storeLogThread = new StoreLogThread();
        this.mThread = storeLogThread;
        storeLogThread.start();
    }

    public class BluetoothProfilePolicy extends BlackWhiteListPolicy {
        public BluetoothProfilePolicy(Context context, String str) {
            super(context, str);
        }

        @Override // com.android.server.enterprise.BlackWhiteListPolicy
        public boolean isPolicyActive(int i) {
            try {
                return BluetoothPolicy.this.mEdmStorageProvider.getBoolean(i, "BLUETOOTH", "profilePolicyEnabled");
            } catch (Exception e) {
                Log.d("BluetoothPolicyService", e.toString());
                return false;
            }
        }
    }

    public final void applyDevicePolicy() {
        applyBlackList(this.mDevicePolicy.getEffectiveBlackList());
    }

    public final void restartBluetooth() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            return;
        }
        this.mRestart = true;
        defaultAdapter.disable();
    }

    public void applyBlackList(List list) {
        try {
            for (BluetoothDevice bluetoothDevice : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
                String address = bluetoothDevice.getAddress();
                if (list.contains(address) || list.contains("*")) {
                    Log.d("BluetoothPolicyService", "device.removeBond() : " + address);
                    bluetoothDevice.removeBond();
                }
            }
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
        }
    }

    public final void initProfileMap() {
        this.mProfileMap.put(1, new ArrayList());
        this.mProfileMap.put(2, new ArrayList());
        this.mProfileMap.put(16, new ArrayList());
        this.mProfileMap.put(4, new ArrayList());
        this.mProfileMap.put(8, new ArrayList());
        this.mProfileMap.put(32, new ArrayList());
        this.mProfileMap.put(64, new ArrayList());
        this.mProfileMap.put(128, new ArrayList());
        this.mProfileMap.put(256, new ArrayList());
        this.mProfileMap.put(512, new ArrayList());
        ((List) this.mProfileMap.get(1)).add("00001108-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(1)).add("00001112-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(2)).add("0000111E-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(2)).add("0000111F-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(16)).add("0000110E-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(16)).add("0000110C-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(4)).add("0000112f-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(4)).add("00001130-0000-1000-8000-00805f9b34fb");
        ((List) this.mProfileMap.get(8)).add("0000110A-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(8)).add("0000110B-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(8)).add("0000110D-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(32)).add("00001103-0000-1000-8000-00805f9b34fb");
        ((List) this.mProfileMap.get(64)).add("00001106-0000-1000-8000-00805f9b34fb");
        ((List) this.mProfileMap.get(128)).add("00001101-0000-1000-8000-00805f9b34fb");
        ((List) this.mProfileMap.get(256)).add("0000112D-0000-1000-8000-00805F9B34FB");
        ((List) this.mProfileMap.get(512)).add("00001122-0000-1000-8000-00805f9b34fb");
    }

    public class BluetoothDevicePolicy extends BlackWhiteListPolicy {
        public BluetoothDevicePolicy(Context context, String str) {
            super(context, str);
        }

        @Override // com.android.server.enterprise.BlackWhiteListPolicy
        public boolean isPolicyActive(int i) {
            try {
                return BluetoothPolicy.this.mEdmStorageProvider.getBoolean(i, "BLUETOOTH", "devicePolicyEnabled");
            } catch (Exception e) {
                Log.d("BluetoothPolicyService", e.toString());
                return false;
            }
        }
    }

    public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
        try {
            return this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndBluetoothPermission(contextInfo).mCallerUid, "BLUETOOTH", "allowCallerID", z);
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return false;
        }
    }

    public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
        try {
            Iterator it = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "allowCallerID").iterator();
            while (it.hasNext()) {
                boolean booleanValue = ((Boolean) it.next()).booleanValue();
                if (!booleanValue) {
                    return booleanValue;
                }
            }
            return true;
        } catch (Exception e) {
            Log.w("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public boolean allowBLE(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndAdvancedRestrictionPermission = enforceOwnerOnlyAndAdvancedRestrictionPermission(contextInfo);
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!z) {
            setBluetoothRestore(enforceOwnerOnlyAndAdvancedRestrictionPermission, isBluetoothEnabledforAdmin(enforceOwnerOnlyAndAdvancedRestrictionPermission));
        }
        if (isBluetoothRestore(enforceOwnerOnlyAndAdvancedRestrictionPermission)) {
            allowBluetoothForBLE(enforceOwnerOnlyAndAdvancedRestrictionPermission, z);
        }
        if (allowBluetoothForBLE(enforceOwnerOnlyAndAdvancedRestrictionPermission, z)) {
            boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndAdvancedRestrictionPermission.mCallerUid, "BLUETOOTH", "allowBLE", z);
            if (!z) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (defaultAdapter != null) {
                    try {
                        defaultAdapter.semShutdown();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                Settings.Global.putInt(contentResolver, "ble_scan_always_enabled", 0);
            }
            return putBoolean;
        }
        Log.d("BluetoothPolicyService", "allowBLE was failed");
        return false;
    }

    public boolean isBLEAllowed(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("BLUETOOTH", "allowBLE").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public int getAdminUidForBLE() {
        int adminByField = this.mEdmStorageProvider.getAdminByField("BLUETOOTH", "allowBLE", Integer.toString(0));
        Log.d("BluetoothPolicyService", "getAdminUidForBLE - " + adminByField);
        return adminByField;
    }

    public final boolean setBluetoothRestore(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean(contextInfo.mCallerUid, "BLUETOOTH", "allowBluetoothRestore", z);
    }

    public final boolean isBluetoothRestore(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getBoolean(contextInfo.mCallerUid, "BLUETOOTH", "allowBluetoothRestore");
        } catch (Exception e) {
            Log.d("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public final boolean isBluetoothEnabledforAdmin(ContextInfo contextInfo) {
        try {
            return this.mEdmStorageProvider.getBoolean(contextInfo.mCallerUid, "BLUETOOTH", "bluetoothEnabled");
        } catch (Exception e) {
            Log.d("BluetoothPolicyService", e.toString());
            return true;
        }
    }

    public final boolean isBluetoothRestrictedByConstrainedState() {
        if (getEDM() != null) {
            return getEDM().isRestrictedByConstrainedState(8);
        }
        return false;
    }

    public final void updateSystemUIMonitor(int i) {
        setBluetoothAllowedSystemUI(i, isBluetoothEnabled((ContextInfo) null) && !isBluetoothRestrictedByConstrainedState());
    }

    public final void setBluetoothAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setBluetoothAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("BluetoothPolicyService", "setBluetoothAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump SecurityPolicy");
        } else {
            this.mEnterpriseDumpHelper.dumpTable(printWriter, "BLUETOOTH", new String[]{"adminUid", "allowCallerID", "allowDataTransfer", "allowOutgoingCalls", "desktopConnectivityEnabled", "devicePolicyEnabled", "discoverableModeEnabled", "bluetoothEnabled", "limitedDiscoverableEnabled", "bluetoothLogEnabled", "pairingEnabled", "profileSettings", "profilePolicyEnabled"});
        }
    }
}
