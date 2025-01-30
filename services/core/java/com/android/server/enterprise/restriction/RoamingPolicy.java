package com.android.server.enterprise.restriction;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapterlayer.SystemUIAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.Utils;
import com.android.server.pm.UserManagerService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.IRoamingPolicy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class RoamingPolicy extends IRoamingPolicy.Stub implements EnterpriseServiceCallback {
    public Context mContext;
    public EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final BroadcastReceiver mReceiver;
    public TelephonyManager mTelMgr;
    public String mPolicyState = "";
    public final BroadcastReceiver mSimFactoryIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.RoamingPolicy.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.SERVICE_STATE".equals(intent.getAction())) {
                RoamingPolicy.this.blockSyncIfRoaming();
            }
        }
    };
    public EnterpriseDeviceManager mEDM = null;

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onPreAdminRemoval(int i) {
    }

    public RoamingPolicy(Context context) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.enterprise.restriction.RoamingPolicy.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                    RoamingPolicy.this.updateSystemUIMonitor(intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0));
                }
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mContext = context;
        this.mTelMgr = (TelephonyManager) context.getSystemService("phone");
        this.mEdmStorageProvider = new EdmStorageProvider(this.mContext);
        if (!isRoamingSyncEnabled(null)) {
            registerRoamingListener();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    public final void registerRoamingListener() {
        Log.d("RoamingPolicy", "registering roaming listener");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        this.mContext.registerReceiver(this.mSimFactoryIntentReceiver, intentFilter);
    }

    public final void deregisterRoamingListener() {
        Log.d("RoamingPolicy", "unregistering roaming listener");
        try {
            this.mContext.unregisterReceiver(this.mSimFactoryIntentReceiver);
        } catch (IllegalArgumentException e) {
            Log.d("RoamingPolicy", "Exception thrown!");
            e.printStackTrace();
        }
    }

    public final void storeUserAutoSyncSetting() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "nonRoamingAutoSyncSetting");
        String str = ContentResolver.getMasterSyncAutomatically() ? "1" : "0";
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("value", str);
        Log.d("RoamingPolicy", "storeUserAutoSyncSetting : storing user sync setting as " + str);
        this.mEdmStorageProvider.put("generic", contentValues2, contentValues);
    }

    public final void restoreUserAutoSyncSetting() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "nonRoamingAutoSyncSetting");
        String string = this.mEdmStorageProvider.getString("generic", "value", contentValues);
        Log.d("RoamingPolicy", "restoreUserAutoSyncSetting : stored user sync setting is " + string);
        if (string != null) {
            setMasterSyncAutomatically("1".equals(string));
            this.mEdmStorageProvider.delete("generic", contentValues);
        }
    }

    public final void setMasterSyncAutomatically(boolean z) {
        for (int i : UserManagerService.getInstance().getUserIds()) {
            ContentResolver.setMasterSyncAutomaticallyAsUser(z, i);
            Log.d("RoamingPolicy", "setMasterSyncAutomatically : userid = " + i);
        }
    }

    public final void blockSyncIfRoaming() {
        boolean isNetworkRoaming = this.mTelMgr.isNetworkRoaming();
        if (this.mTelMgr.getPhoneCount() > 1) {
            String semGetTelephonyProperty = TelephonyManager.semGetTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false");
            isNetworkRoaming = !TextUtils.isEmpty(semGetTelephonyProperty) && semGetTelephonyProperty.equals("true");
        }
        if (isNetworkRoaming) {
            Log.d("RoamingPolicy", "Entering Roaming");
            storeUserAutoSyncSetting();
            setMasterSyncAutomatically(isRoamingSyncEnabled(null));
        } else {
            Log.d("RoamingPolicy", "Leaving Roaming");
            restoreUserAutoSyncSetting();
        }
    }

    public final EnterpriseDeviceManager getEDM() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final ContextInfo enforceOwnerOnlyAndRoamingPermission(ContextInfo contextInfo) {
        return getEDM().enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ROAMING")));
    }

    public boolean setRoamingSync(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRoamingPermission = enforceOwnerOnlyAndRoamingPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRoamingPermission.mCallerUid, "ROAMING", "roamingSyncEnabled", z);
        Log.d("RoamingPolicy", "setRoamingSync : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!isRoamingSyncEnabled(enforceOwnerOnlyAndRoamingPermission)) {
                registerRoamingListener();
            } else {
                deregisterRoamingListener();
                restoreUserAutoSyncSetting();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            updatePolicyState();
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean isRoamingSyncEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("ROAMING", "roamingSyncEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        Log.d("RoamingPolicy", "isRoamingSyncEnabled : " + z);
        return z;
    }

    public boolean setRoamingPush(ContextInfo contextInfo, boolean z) {
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRoamingPermission(contextInfo).mCallerUid, "ROAMING", "roamingPushEnabled", z);
        Log.d("RoamingPolicy", "setRoamingPush : " + z);
        updatePolicyState();
        return putBoolean;
    }

    public boolean isRoamingPushEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("ROAMING", "roamingPushEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        Log.d("RoamingPolicy", "isRoamingPushEnabled : " + z);
        return z;
    }

    public boolean setRoamingData(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRoamingPermission = enforceOwnerOnlyAndRoamingPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRoamingPermission.mCallerUid, "ROAMING", "roamingDataEnabled", z);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(enforceOwnerOnlyAndRoamingPermission);
        if (!isRoamingDataEnabled(enforceOwnerOnlyAndRoamingPermission)) {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.ROAMING_SETROAMINGDATA_INTERNAL");
            intent.setPackage("com.samsung.android.app.telephonyui");
            intent.putExtra("roamingData", false);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_ROAMING");
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        Log.d("RoamingPolicy", "setDataRoaming : " + isRoamingDataEnabled(enforceOwnerOnlyAndRoamingPermission));
        setRoamingDataAllowedSystemUI(callingOrCurrentUserId, isRoamingDataEnabled(enforceOwnerOnlyAndRoamingPermission));
        updatePolicyState();
        return putBoolean;
    }

    public boolean isRoamingDataEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanList("ROAMING", "roamingDataEnabled").iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            z = ((Boolean) it.next()).booleanValue();
            if (!z) {
                break;
            }
        }
        Log.d("RoamingPolicy", "isDataRoamingEnabled : " + z);
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void onAdminRemoved(int i) {
        Log.d("RoamingPolicy", "onAdminRemoved : " + i);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (isRoamingSyncEnabled(null)) {
            Log.d("RoamingPolicy", "Roaming Sync is not being applied, so de-register listener and restore sync setting");
            deregisterRoamingListener();
            restoreUserAutoSyncSetting();
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            updateSystemUIMonitor(callingOrCurrentUserId);
        }
        updatePolicyState();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public void systemReady() {
        updatePolicyState();
    }

    public boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) {
        boolean putBoolean = this.mEdmStorageProvider.putBoolean(enforceOwnerOnlyAndRoamingPermission(contextInfo).mCallerUid, "ROAMING", "roamingVoiceCallsEnabled", z);
        updatePolicyState();
        return putBoolean;
    }

    public boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanList("ROAMING", "roamingVoiceCallsEnabled").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    public final void updateSystemUIMonitor(int i) {
        setRoamingDataAllowedSystemUI(i, isRoamingDataEnabled(null));
    }

    public final void setRoamingDataAllowedSystemUI(int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                SystemUIAdapter.getInstance(this.mContext).setRoamingDataAllowedAsUser(i, z);
            } catch (Exception e) {
                Log.e("RoamingPolicy", "setRoamingDataAllowedSystemUI() failed. ", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updatePolicyState() {
        this.mPolicyState = getEnterpriseDumpHelper().readTable("ROAMING", new String[]{"adminUid", "roamingSyncEnabled", "roamingPushEnabled", "roamingDataEnabled", "roamingVoiceCallsEnabled"});
    }

    public final EnterpriseDumpHelper getEnterpriseDumpHelper() {
        if (this.mEnterpriseDumpHelper == null) {
            this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
        }
        return this.mEnterpriseDumpHelper;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Roaming Policy");
        } else {
            if (this.mPolicyState.isEmpty()) {
                return;
            }
            printWriter.print(this.mPolicyState);
        }
    }
}
