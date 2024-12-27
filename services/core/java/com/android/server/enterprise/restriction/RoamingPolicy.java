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
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RoamingPolicy extends IRoamingPolicy.Stub implements EnterpriseServiceCallback {
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public EnterpriseDumpHelper mEnterpriseDumpHelper;
    public String mPolicyState = "";
    public final AnonymousClass1 mReceiver;
    public final AnonymousClass1 mSimFactoryIntentReceiver;
    public final TelephonyManager mTelMgr;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.content.BroadcastReceiver, com.android.server.enterprise.restriction.RoamingPolicy$1] */
    public RoamingPolicy(Context context) {
        final int i = 0;
        ?? r0 = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.restriction.RoamingPolicy.1
            public final /* synthetic */ RoamingPolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        if ("android.intent.action.SERVICE_STATE".equals(intent.getAction())) {
                            RoamingPolicy roamingPolicy = this.this$0;
                            boolean isNetworkRoaming = roamingPolicy.mTelMgr.isNetworkRoaming();
                            if (roamingPolicy.mTelMgr.getPhoneCount() > 1) {
                                String semGetTelephonyProperty = TelephonyManager.semGetTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false");
                                isNetworkRoaming = !TextUtils.isEmpty(semGetTelephonyProperty) && semGetTelephonyProperty.equals("true");
                            }
                            if (!isNetworkRoaming) {
                                Log.d("RoamingPolicy", "Leaving Roaming");
                                roamingPolicy.restoreUserAutoSyncSetting();
                                break;
                            } else {
                                Log.d("RoamingPolicy", "Entering Roaming");
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", "nonRoamingAutoSyncSetting");
                                String str = ContentResolver.getMasterSyncAutomatically() ? "1" : "0";
                                ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("value", str);
                                Log.d("RoamingPolicy", "storeUserAutoSyncSetting : storing user sync setting as ".concat(str));
                                roamingPolicy.mEdmStorageProvider.put("generic", m, contentValues);
                                RoamingPolicy.setMasterSyncAutomatically(roamingPolicy.isRoamingSyncEnabled(null));
                                break;
                            }
                        }
                        break;
                    default:
                        if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                            RoamingPolicy roamingPolicy2 = this.this$0;
                            roamingPolicy2.setRoamingDataAllowedSystemUI(intExtra, roamingPolicy2.isRoamingDataEnabled(null));
                            break;
                        }
                        break;
                }
            }
        };
        this.mSimFactoryIntentReceiver = r0;
        this.mEDM = null;
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.restriction.RoamingPolicy.1
            public final /* synthetic */ RoamingPolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        if ("android.intent.action.SERVICE_STATE".equals(intent.getAction())) {
                            RoamingPolicy roamingPolicy = this.this$0;
                            boolean isNetworkRoaming = roamingPolicy.mTelMgr.isNetworkRoaming();
                            if (roamingPolicy.mTelMgr.getPhoneCount() > 1) {
                                String semGetTelephonyProperty = TelephonyManager.semGetTelephonyProperty(SubscriptionManager.getPhoneId(SubscriptionManager.getDefaultDataSubscriptionId()), "gsm.operator.isroaming", "false");
                                isNetworkRoaming = !TextUtils.isEmpty(semGetTelephonyProperty) && semGetTelephonyProperty.equals("true");
                            }
                            if (!isNetworkRoaming) {
                                Log.d("RoamingPolicy", "Leaving Roaming");
                                roamingPolicy.restoreUserAutoSyncSetting();
                                break;
                            } else {
                                Log.d("RoamingPolicy", "Entering Roaming");
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("name", "nonRoamingAutoSyncSetting");
                                String str = ContentResolver.getMasterSyncAutomatically() ? "1" : "0";
                                ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("value", str);
                                Log.d("RoamingPolicy", "storeUserAutoSyncSetting : storing user sync setting as ".concat(str));
                                roamingPolicy.mEdmStorageProvider.put("generic", m, contentValues);
                                RoamingPolicy.setMasterSyncAutomatically(roamingPolicy.isRoamingSyncEnabled(null));
                                break;
                            }
                        }
                        break;
                    default:
                        if ("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL".equals(intent.getAction())) {
                            int intExtra = intent.getIntExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", 0);
                            RoamingPolicy roamingPolicy2 = this.this$0;
                            roamingPolicy2.setRoamingDataAllowedSystemUI(intExtra, roamingPolicy2.isRoamingDataEnabled(null));
                            break;
                        }
                        break;
                }
            }
        };
        this.mContext = context;
        this.mTelMgr = (TelephonyManager) context.getSystemService("phone");
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        if (!isRoamingSyncEnabled(null)) {
            Log.d("RoamingPolicy", "registering roaming listener");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SERVICE_STATE");
            this.mContext.registerReceiver(r0, intentFilter);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL");
        context.registerReceiver(broadcastReceiver, intentFilter2, 2);
    }

    public static void setMasterSyncAutomatically(boolean z) {
        for (int i : UserManagerService.getInstance().getUserIds()) {
            ContentResolver.setMasterSyncAutomaticallyAsUser(z, i);
            Log.d("RoamingPolicy", "setMasterSyncAutomatically : userid = " + i);
        }
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

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump Roaming Policy");
        } else {
            if (this.mPolicyState.isEmpty()) {
                return;
            }
            printWriter.print(this.mPolicyState);
        }
    }

    public final ContextInfo enforceOwnerOnlyAndRoamingPermission(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_ROAMING")));
    }

    public final boolean isRoamingDataEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "ROAMING", "roamingDataEnabled").iterator();
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
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isDataRoamingEnabled : ", "RoamingPolicy", z);
        return z;
    }

    public final boolean isRoamingPushEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "ROAMING", "roamingPushEnabled").iterator();
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
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isRoamingPushEnabled : ", "RoamingPolicy", z);
        return z;
    }

    public final boolean isRoamingSyncEnabled(ContextInfo contextInfo) {
        boolean z;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "ROAMING", "roamingSyncEnabled").iterator();
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
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("isRoamingSyncEnabled : ", "RoamingPolicy", z);
        return z;
    }

    public final boolean isRoamingVoiceCallsEnabled(ContextInfo contextInfo) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "ROAMING", "roamingVoiceCallsEnabled").iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (!booleanValue) {
                return booleanValue;
            }
        }
        return true;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        Log.d("RoamingPolicy", "onAdminRemoved : " + i);
        int callingOrCurrentUserId = Utils.getCallingOrCurrentUserId(new ContextInfo(i, 0));
        if (isRoamingSyncEnabled(null)) {
            Log.d("RoamingPolicy", "Roaming Sync is not being applied, so de-register listener and restore sync setting");
            deregisterRoamingListener();
            restoreUserAutoSyncSetting();
        }
        if (callingOrCurrentUserId == ActivityManager.getCurrentUser()) {
            setRoamingDataAllowedSystemUI(callingOrCurrentUserId, isRoamingDataEnabled(null));
        }
        updatePolicyState();
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
    }

    public final void restoreUserAutoSyncSetting() {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("name", "nonRoamingAutoSyncSetting");
        String string = this.mEdmStorageProvider.getString(m, "generic", "value");
        DualAppManagerService$$ExternalSyntheticOutline0.m("restoreUserAutoSyncSetting : stored user sync setting is ", string, "RoamingPolicy");
        if (string != null) {
            setMasterSyncAutomatically("1".equals(string));
            this.mEdmStorageProvider.delete("generic", m);
        }
    }

    public final boolean setRoamingData(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRoamingPermission = enforceOwnerOnlyAndRoamingPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("ROAMING", enforceOwnerOnlyAndRoamingPermission.mCallerUid, z, 0, "roamingDataEnabled");
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

    public final boolean setRoamingPush(ContextInfo contextInfo, boolean z) {
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("ROAMING", enforceOwnerOnlyAndRoamingPermission(contextInfo).mCallerUid, z, 0, "roamingPushEnabled");
        Log.d("RoamingPolicy", "setRoamingPush : " + z);
        updatePolicyState();
        return putBoolean;
    }

    public final boolean setRoamingSync(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceOwnerOnlyAndRoamingPermission = enforceOwnerOnlyAndRoamingPermission(contextInfo);
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("ROAMING", enforceOwnerOnlyAndRoamingPermission.mCallerUid, z, 0, "roamingSyncEnabled");
        Log.d("RoamingPolicy", "setRoamingSync : " + z);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (isRoamingSyncEnabled(enforceOwnerOnlyAndRoamingPermission)) {
                deregisterRoamingListener();
                restoreUserAutoSyncSetting();
            } else {
                Log.d("RoamingPolicy", "registering roaming listener");
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SERVICE_STATE");
                this.mContext.registerReceiver(this.mSimFactoryIntentReceiver, intentFilter);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            updatePolicyState();
            return putBoolean;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setRoamingVoiceCalls(ContextInfo contextInfo, boolean z) {
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("ROAMING", enforceOwnerOnlyAndRoamingPermission(contextInfo).mCallerUid, z, 0, "roamingVoiceCallsEnabled");
        updatePolicyState();
        return putBoolean;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        updatePolicyState();
    }

    public final void updatePolicyState() {
        if (this.mEnterpriseDumpHelper == null) {
            this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
        }
        String readColumns = this.mEnterpriseDumpHelper.readColumns("ROAMING", new String[]{"adminUid", "roamingSyncEnabled", "roamingPushEnabled", "roamingDataEnabled", "roamingVoiceCallsEnabled"}, null, null);
        if (readColumns == null) {
            readColumns = "";
        }
        this.mPolicyState = readColumns;
    }
}
