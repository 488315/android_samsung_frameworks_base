package com.android.server.enterprise.scpm;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Pair;

import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.localservice.CloudConfigurationManagerInternal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public final class CloudConfigurationManagerService extends CloudConfigurationManagerInternal {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public Bundle mScpmBundle;
    public String mScpmToken = null;
    public int mRetryNumber = 3;
    public final IPackageManager mPMS =
            IPackageManager.Stub.asInterface(ServiceManager.getService("package"));

    /* renamed from: com.android.server.enterprise.scpm.CloudConfigurationManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.samsung.intent.action.LAZY_BOOT_COMPLETE")) {
                CloudConfigurationManagerService.m522$$Nest$mtryRegister(
                        CloudConfigurationManagerService.this);
                return;
            }
            if (intent.getAction().equals(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA)) {
                KnoxsdkFileLog.d(
                        "CloudConfigurationManagerService",
                        "receive ACTION_SCPM_POLICY_CLEAR_DATA intent");
                new Handler().postDelayed(new AnonymousClass2(1, this), 60000L);
            } else if (intent.getAction()
                    .equals(KnoxCustomManagerService.ACTION_SCPM_MAM_CONFIGURATION_BROADCAST)) {
                KnoxsdkFileLog.d(
                        "CloudConfigurationManagerService",
                        "receive intent: knox-sdk-mam-configuration");
                CloudConfigurationManagerService.m523$$Nest$mupdateConfiguration(
                        CloudConfigurationManagerService.this,
                        KnoxCustomManagerService.SCPM_MAM_CONFIGURATION,
                        null,
                        null);
            } else if (intent.getAction()
                    .equals("com.samsung.android.scpm.policy.UPDATE.knox-remotecontrol")) {
                KnoxsdkFileLog.d(
                        "CloudConfigurationManagerService", "receive intent: knox-remotecontrol");
                CloudConfigurationManagerService.m523$$Nest$mupdateConfiguration(
                        CloudConfigurationManagerService.this,
                        "knox-remotecontrol",
                        "RESTRICTION",
                        "allowRemoteControlAllowList");
            }
        }
    }

    /* renamed from: com.android.server.enterprise.scpm.CloudConfigurationManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    CloudConfigurationManagerService.m522$$Nest$mtryRegister(
                            (CloudConfigurationManagerService) this.this$0);
                    break;
                default:
                    CloudConfigurationManagerService cloudConfigurationManagerService =
                            CloudConfigurationManagerService.this;
                    cloudConfigurationManagerService.mScpmToken = null;
                    CloudConfigurationManagerService.m522$$Nest$mtryRegister(
                            cloudConfigurationManagerService);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mtryRegister, reason: not valid java name */
    public static void m522$$Nest$mtryRegister(
            CloudConfigurationManagerService cloudConfigurationManagerService) {
        cloudConfigurationManagerService.getClass();
        try {
            if (cloudConfigurationManagerService.registerScpm()) {
                Log.d("CloudConfigurationManagerService", "tryRegister - register success");
                return;
            }
        } catch (Throwable th) {
            Log.e("CloudConfigurationManagerService", "Failed to tryRegister " + th.getMessage());
        }
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("tryRegister - register fail, retryNumber is "),
                cloudConfigurationManagerService.mRetryNumber,
                "CloudConfigurationManagerService");
        int i = cloudConfigurationManagerService.mRetryNumber;
        cloudConfigurationManagerService.mRetryNumber = i - 1;
        if (i > 0) {
            new Handler()
                    .postDelayed(new AnonymousClass2(0, cloudConfigurationManagerService), 30000L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: -$$Nest$mupdateConfiguration, reason: not valid java name */
    public static void m523$$Nest$mupdateConfiguration(
            CloudConfigurationManagerService cloudConfigurationManagerService,
            String str,
            String str2,
            String str3) {
        ParcelFileDescriptor parcelFileDescriptor;
        cloudConfigurationManagerService.getClass();
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        bufferedReader = null;
        r10 = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        bufferedReader = null;
        bufferedReader = null;
        try {
            try {
                try {
                    parcelFileDescriptor =
                            cloudConfigurationManagerService
                                    .mContext
                                    .getContentResolver()
                                    .openFileDescriptor(
                                            Uri.parse(
                                                    "content://com.samsung.android.scpm.policy/"
                                                            + cloudConfigurationManagerService
                                                                    .mScpmToken
                                                            + "/"
                                                            + str),
                                            "r");
                    try {
                        if (parcelFileDescriptor == null) {
                            new Bundle();
                            Bundle call =
                                    cloudConfigurationManagerService
                                            .mContext
                                            .getContentResolver()
                                            .call(
                                                    Uri.parse(
                                                            "content://com.samsung.android.scpm.policy/"),
                                                    "getLastError",
                                                    "android",
                                                    (Bundle) null);
                            Pair create =
                                    Pair.create(
                                            Integer.valueOf(
                                                    call.getInt(
                                                            KnoxCustomManagerService
                                                                    .SPCM_KEY_RESULT_CODE,
                                                            -1)),
                                            call.getString(
                                                    KnoxCustomManagerService
                                                            .SPCM_KEY_RESULT_MESSAGE,
                                                    ""));
                            KnoxsdkFileLog.e(
                                    "CloudConfigurationManagerService",
                                    "It can't get the configuration data : "
                                            + create.first
                                            + ","
                                            + ((String) create.second));
                            if (parcelFileDescriptor == null) {
                                return;
                            }
                        } else {
                            BufferedReader bufferedReader3 =
                                    new BufferedReader(
                                            new InputStreamReader(
                                                    new FileInputStream(
                                                            parcelFileDescriptor
                                                                    .getFileDescriptor()),
                                                    "UTF-8"));
                            try {
                                if (str.equals(KnoxCustomManagerService.SCPM_MAM_CONFIGURATION)) {
                                    while (true) {
                                        String readLine = bufferedReader3.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        String[] split = readLine.split(",");
                                        List asList = Arrays.asList(split[1].split(";"));
                                        List asList2 = Arrays.asList(split[2].split(";"));
                                        String str4 = split[0];
                                        byte[] serializeObject = Utils.serializeObject(asList);
                                        byte[] serializeObject2 = Utils.serializeObject(asList2);
                                        KnoxsdkFileLog.d(
                                                "CloudConfigurationManagerService",
                                                "updateConfiguration: " + str4);
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("signature", serializeObject);
                                        contentValues.put("permission", serializeObject2);
                                        ContentValues contentValues2 = new ContentValues();
                                        contentValues2.put("pkgName", str4);
                                        int count =
                                                cloudConfigurationManagerService.mEdmStorageProvider
                                                        .getCount(
                                                                "ENT_APP_MGMT_RT", contentValues2);
                                        if (count > 0) {
                                            EdmStorageProvider edmStorageProvider =
                                                    cloudConfigurationManagerService
                                                            .mEdmStorageProvider;
                                            edmStorageProvider.putValues(
                                                    "ENT_APP_MGMT_RT",
                                                    contentValues,
                                                    contentValues2);
                                            bufferedReader2 = edmStorageProvider;
                                        } else {
                                            contentValues.put("pkgName", str4);
                                            cloudConfigurationManagerService.mEdmStorageProvider
                                                    .putValuesNoUpdate(
                                                            "ENT_APP_MGMT_RT", contentValues);
                                            bufferedReader2 = count;
                                        }
                                        try {
                                            if (cloudConfigurationManagerService
                                                            .mContext
                                                            .getPackageManager()
                                                            .getPackageInfo(str4, 0)
                                                    != null) {
                                                cloudConfigurationManagerService
                                                        .setLicensePermissionsForMDM(str4);
                                            }
                                        } catch (PackageManager.NameNotFoundException unused) {
                                        }
                                    }
                                } else {
                                    while (true) {
                                        String readLine2 = bufferedReader3.readLine();
                                        if (readLine2 == null) {
                                            break;
                                        } else {
                                            sb.append(readLine2);
                                        }
                                    }
                                    KnoxsdkFileLog.d(
                                            "CloudConfigurationManagerService",
                                            "updateConfiguration: " + sb.toString());
                                    cloudConfigurationManagerService.mEdmStorageProvider.putString(
                                            1000, 0, str2, str3, sb.toString());
                                }
                                bufferedReader3.close();
                                bufferedReader = bufferedReader2;
                            } catch (Exception e) {
                                e = e;
                                bufferedReader = bufferedReader3;
                                KnoxsdkFileLog.e(
                                        "CloudConfigurationManagerService",
                                        "Unknown exception : " + e.getMessage());
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (parcelFileDescriptor == null) {
                                    return;
                                }
                                parcelFileDescriptor.close();
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader3;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (Exception unused2) {
                                        Log.e(
                                                "CloudConfigurationManagerService",
                                                "Resource isn't closed");
                                        throw th;
                                    }
                                }
                                if (parcelFileDescriptor != null) {
                                    parcelFileDescriptor.close();
                                }
                                throw th;
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    parcelFileDescriptor = null;
                } catch (Throwable th2) {
                    th = th2;
                    parcelFileDescriptor = null;
                }
                parcelFileDescriptor.close();
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception unused3) {
            Log.e("CloudConfigurationManagerService", "Resource isn't closed");
        }
    }

    public CloudConfigurationManagerService(Context context) {
        this.mEdmStorageProvider = null;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_MAM_CONFIGURATION_BROADCAST);
        intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.knox-remotecontrol");
        context.registerReceiver(new AnonymousClass1(), intentFilter, 2);
    }

    public final boolean registerScpm() {
        if (this.mScpmToken != null) {
            Log.d("CloudConfigurationManagerService", "token is not null");
            return true;
        }
        if (this.mContext
                        .getPackageManager()
                        .resolveContentProvider(KnoxCustomManagerService.SPCM_PROVIDER_AUTHORITY, 0)
                == null) {
            KnoxsdkFileLog.d("CloudConfigurationManagerService", "scpm v2 is not available");
            return false;
        }
        try {
            Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/");
            Bundle bundle = new Bundle();
            bundle.putString("packageName", "android");
            bundle.putString("appId", KnoxCustomManagerService.SPCM_KNOX_CUSTOM_APP_ID);
            bundle.putString("version", KnoxCustomManagerService.SPCM_APP_VERSION);
            bundle.putString("receiverPackageName", "android");
            Bundle call =
                    this.mContext
                            .getContentResolver()
                            .call(parse, "register", this.mContext.getPackageName(), bundle);
            this.mScpmBundle = call;
            if (call != null) {
                boolean z = call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 1) == 1;
                this.mScpmToken =
                        this.mScpmBundle.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
                KnoxsdkFileLog.d(
                        "CloudConfigurationManagerService",
                        "trying to register package: android version:38 status: "
                                .concat(z ? "registered" : "failed"));
                if (!z) {
                    Bundle bundle2 = this.mScpmBundle;
                    Pair create =
                            Pair.create(
                                    Integer.valueOf(
                                            bundle2.getInt(
                                                    KnoxCustomManagerService.SPCM_KEY_RESULT_CODE,
                                                    -1)),
                                    bundle2.getString(
                                            KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE, ""));
                    KnoxsdkFileLog.d(
                            "CloudConfigurationManagerService",
                            "register fail rCode:" + create.first + "," + ((String) create.second));
                }
            }
        } catch (Exception e) {
            KnoxsdkFileLog.e(
                    "CloudConfigurationManagerService",
                    "cannot register package : " + e.getMessage());
        }
        return this.mScpmToken != null;
    }

    public final void setLicensePermissionsForMDM(String str) {
        try {
            KnoxsdkFileLog.d(
                    "CloudConfigurationManagerService",
                    "setLicensePermissionsForMDM = [" + str + "]");
            this.mPMS.setLicensePermissionsForMDM(str);
        } catch (RemoteException unused) {
            KnoxsdkFileLog.e(
                    "CloudConfigurationManagerService",
                    "Error while grating license permissions for MDM");
        }
    }
}
