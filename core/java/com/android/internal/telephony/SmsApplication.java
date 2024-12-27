package com.android.internal.telephony;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Process;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Telephony;
import android.sec.enterprise.content.SecContentProviderURI;
import android.telecom.Logging.Session;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.internal.telephony.vzwavslibrary.VZWAVSLibrary;
import com.google.android.mms.ContentType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes5.dex */
public final class SmsApplication {
    public static final String ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL";
    private static final String CARRIER_TAG = "Carrier";
    private static final String CODE_UNKNOWN = "NONE";
    private static final String COREAPPS_PACKAGE_NAME = "com.samsung.android.coreapps";
    private static final String COUNTRYISO_OPENBUYER_CONFIG_XML = "/system/etc/countryISO_openBuyer_config.xml";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_MULTIUSER = false;
    private static final String DEFAULT_MSGAPP_SYSTEMPROPERTY = "persist.ril.config.defaultmsgapp";
    public static final String DEFAULT_MSG_APP_INFO_LOGGING = "android.provider.action.DEFAULT_MSG_APP_INFO_LOGGING";
    private static final String DEFAULT_MSG_CONFIG_XML = "/system/etc/default_msg_config.xml";
    private static final String DOCOMO_MESSAGES = "com.nttdocomo.android.msg";
    private static final String GOOGLE_MESSAGE_PACKAGE = "com.google.android.apps.messaging";
    private static final String KDDI_MESSAGES = "com.kddi.android.cmail";
    static final String LOG_TAG = "SmsApplication";
    public static final String MMS_SERVICE_PACKAGE_NAME = "com.android.mms.service";
    private static final String NEW_SEC_SMS_PACKAGE_NAME = "com.samsung.android.messaging";
    private static final String NSRI_PACKAGE_NAME = "com.tion.securitysms";
    private static final String OPEN_TAG = "Open";
    public static final String PERMISSION_MONITOR_DEFAULT_SMS_PACKAGE = "android.permission.MONITOR_DEFAULT_SMS_PACKAGE";
    public static final String PHONE_PACKAGE_NAME = "com.android.phone";
    private static final String SCHEME_MMS = "mms";
    private static final String SCHEME_MMSTO = "mmsto";
    private static final String SCHEME_SMS = "sms";
    private static final String SCHEME_SMSTO = "smsto";
    private static final String SEC_SMS_PACKAGE_NAME = "com.android.mms";
    private static final String SM_TAG = "SM";
    private static final String SOFTBANK_MESSAGES = "jp.softbank.mb.mail";
    public static final String TELEPHONY_PROVIDER_PACKAGE_NAME = "com.android.providers.telephony";
    private static final String[] DEFAULT_APP_EXCLUSIVE_APPOPS = {AppOpsManager.OPSTR_READ_SMS, AppOpsManager.OPSTR_WRITE_SMS, AppOpsManager.OPSTR_RECEIVE_SMS, AppOpsManager.OPSTR_RECEIVE_WAP_PUSH, AppOpsManager.OPSTR_SEND_SMS, AppOpsManager.OPSTR_READ_CELL_BROADCASTS};
    private static SmsPackageMonitor sSmsPackageMonitor = null;
    private static SmsRoleListener sSmsRoleListener = null;
    private static DefaultMessageAppConfig sDefaultMessageAppConfig = null;
    private static String[] sPackageNamePattern = null;
    private static PendingIntent mPendingDeliveryIntent = null;
    public static final String SALES_CODE = SystemProperties.get("ro.csc.sales_code", "NONE");
    private static StringBuffer mLogStb = new StringBuffer();
    private static SemDMACdata sDMACdata = new SemDMACdata();

    public static class SmsApplicationData {
        private String mApplicationName;
        private String mMmsReceiverClass;
        public String mPackageName;
        private String mProviderChangedReceiverClass;
        private String mRespondViaMessageClass;
        private String mSendToClass;
        private String mSimFullReceiverClass;
        private String mSmsAppChangedReceiverClass;
        private String mSmsReceiverClass;
        private int mUid;

        public boolean isComplete() {
            return (this.mSmsReceiverClass == null || this.mMmsReceiverClass == null || this.mRespondViaMessageClass == null || this.mSendToClass == null) ? false : true;
        }

        public SmsApplicationData(String packageName, int uid) {
            this.mPackageName = packageName;
            this.mUid = uid;
        }

        public String getApplicationName(Context context) {
            if (this.mApplicationName == null) {
                PackageManager pm = context.getPackageManager();
                try {
                    ApplicationInfo appInfo = pm.getApplicationInfoAsUser(this.mPackageName, 0, UserHandle.getUserHandleForUid(this.mUid));
                    if (appInfo != null) {
                        CharSequence label = pm.getApplicationLabel(appInfo);
                        this.mApplicationName = label != null ? label.toString() : null;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    return null;
                }
            }
            return this.mApplicationName;
        }

        public String toString() {
            return " mPackageName: " + this.mPackageName + " mSmsReceiverClass: " + this.mSmsReceiverClass + " mMmsReceiverClass: " + this.mMmsReceiverClass + " mRespondViaMessageClass: " + this.mRespondViaMessageClass + " mSendToClass: " + this.mSendToClass + " mSmsAppChangedClass: " + this.mSmsAppChangedReceiverClass + " mProviderChangedReceiverClass: " + this.mProviderChangedReceiverClass + " mSimFullReceiverClass: " + this.mSimFullReceiverClass + " mUid: " + this.mUid;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getIncomingUserId() {
        int contextUserId = UserHandle.myUserId();
        int callingUid = Binder.getCallingUid();
        if (UserHandle.getAppId(callingUid) < 10000) {
            return contextUserId;
        }
        return UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    }

    private static UserHandle getIncomingUserHandle() {
        return UserHandle.of(getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollection(Context context) {
        return getApplicationCollectionAsUser(context, getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollectionAsUser(Context context, int userId) {
        long token = Binder.clearCallingIdentity();
        try {
            return getApplicationCollectionInternal(context, userId);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private static Collection<SmsApplicationData> getApplicationCollectionInternal(Context context, int userId) {
        String packageName;
        SmsApplicationData smsApplicationData;
        Intent intent;
        SmsApplicationData smsApplicationData2;
        SmsApplicationData smsApplicationData3;
        SmsApplicationData smsApplicationData4;
        SmsApplicationData smsApplicationData5;
        PackageManager packageManager = context.getPackageManager();
        UserHandle userHandle = UserHandle.of(userId);
        List<ResolveInfo> smsReceivers = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.SMS_DELIVER_ACTION), 786432, userHandle);
        HashMap<String, SmsApplicationData> receivers = new HashMap<>();
        for (ResolveInfo resolveInfo : smsReceivers) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo != null && Manifest.permission.BROADCAST_SMS.equals(activityInfo.permission)) {
                String packageName2 = activityInfo.packageName;
                if (!receivers.containsKey(packageName2)) {
                    try {
                        SmsApplicationData smsApplicationData6 = new SmsApplicationData(packageName2, activityInfo.applicationInfo.uid);
                        smsApplicationData6.mSmsReceiverClass = activityInfo.name;
                        receivers.put(packageName2, smsApplicationData6);
                    } catch (Exception e) {
                        Rlog.e(LOG_TAG, "Error getting applicationName");
                    }
                }
            }
        }
        Intent intent2 = new Intent(Telephony.Sms.Intents.WAP_PUSH_DELIVER_ACTION);
        intent2.setDataAndType(null, ContentType.MMS_MESSAGE);
        List<ResolveInfo> mmsReceivers = packageManager.queryBroadcastReceiversAsUser(intent2, 786432, userHandle);
        for (ResolveInfo resolveInfo2 : mmsReceivers) {
            ActivityInfo activityInfo2 = resolveInfo2.activityInfo;
            if (activityInfo2 != null && Manifest.permission.BROADCAST_WAP_PUSH.equals(activityInfo2.permission) && (smsApplicationData5 = receivers.get(activityInfo2.packageName)) != null) {
                smsApplicationData5.mMmsReceiverClass = activityInfo2.name;
            }
        }
        List<ResolveInfo> respondServices = packageManager.queryIntentServicesAsUser(new Intent(TelephonyManager.ACTION_RESPOND_VIA_MESSAGE, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, UserHandle.of(userId));
        for (ResolveInfo resolveInfo3 : respondServices) {
            ServiceInfo serviceInfo = resolveInfo3.serviceInfo;
            if (serviceInfo != null && Manifest.permission.SEND_RESPOND_VIA_MESSAGE.equals(serviceInfo.permission)) {
                SmsApplicationData smsApplicationData7 = receivers.get(serviceInfo.packageName);
                if (smsApplicationData7 != null) {
                    smsApplicationData7.mRespondViaMessageClass = serviceInfo.name;
                }
            }
        }
        List<ResolveInfo> sendToActivities = packageManager.queryIntentActivitiesAsUser(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, userHandle);
        for (ResolveInfo resolveInfo4 : sendToActivities) {
            ActivityInfo activityInfo3 = resolveInfo4.activityInfo;
            if (activityInfo3 != null && (smsApplicationData4 = receivers.get(activityInfo3.packageName)) != null) {
                smsApplicationData4.mSendToClass = activityInfo3.name;
            }
        }
        List<ResolveInfo> smsAppChangedReceivers = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED), 786432, userHandle);
        for (ResolveInfo resolveInfo5 : smsAppChangedReceivers) {
            ActivityInfo activityInfo4 = resolveInfo5.activityInfo;
            if (activityInfo4 != null && (smsApplicationData3 = receivers.get(activityInfo4.packageName)) != null) {
                smsApplicationData3.mSmsAppChangedReceiverClass = activityInfo4.name;
            }
        }
        List<ResolveInfo> providerChangedReceivers = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_EXTERNAL_PROVIDER_CHANGE), 786432, userHandle);
        for (ResolveInfo resolveInfo6 : providerChangedReceivers) {
            ActivityInfo activityInfo5 = resolveInfo6.activityInfo;
            if (activityInfo5 != null && (smsApplicationData2 = receivers.get(activityInfo5.packageName)) != null) {
                smsApplicationData2.mProviderChangedReceiverClass = activityInfo5.name;
            }
        }
        Intent intent3 = new Intent(Telephony.Sms.Intents.SIM_FULL_ACTION);
        List<ResolveInfo> simFullReceivers = packageManager.queryBroadcastReceiversAsUser(intent3, 786432, userHandle);
        for (ResolveInfo resolveInfo7 : simFullReceivers) {
            ActivityInfo activityInfo6 = resolveInfo7.activityInfo;
            if (activityInfo6 != null) {
                SmsApplicationData smsApplicationData8 = receivers.get(activityInfo6.packageName);
                if (smsApplicationData8 == null) {
                    intent = intent3;
                } else {
                    intent = intent3;
                    smsApplicationData8.mSimFullReceiverClass = activityInfo6.name;
                }
                intent3 = intent;
            }
        }
        for (ResolveInfo resolveInfo8 : smsReceivers) {
            ActivityInfo activityInfo7 = resolveInfo8.activityInfo;
            if (activityInfo7 != null && (smsApplicationData = receivers.get((packageName = activityInfo7.packageName))) != null && !smsApplicationData.isComplete()) {
                receivers.remove(packageName);
            }
        }
        return receivers.values();
    }

    public static SmsApplicationData getApplicationForPackage(Collection<SmsApplicationData> applications, String packageName) {
        if (packageName == null) {
            return null;
        }
        for (SmsApplicationData application : applications) {
            if (application.mPackageName.contentEquals(packageName)) {
                return application;
            }
        }
        return null;
    }

    private static SmsApplicationData getApplication(Context context, boolean updateIfNeeded, int userId) {
        String defaultPackage;
        if (context == null) {
            Rlog.e(LOG_TAG, "getApplication: context is null!");
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        RoleManager roleManager = (RoleManager) context.getSystemService(Context.ROLE_SERVICE);
        if (!tm.isSmsCapable() && (roleManager == null || !roleManager.isRoleAvailable("android.app.role.SMS"))) {
            if ((!"ATT".equals(SALES_CODE) && !getEnableSecSms(context)) || !updateIfNeeded) {
                return null;
            }
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            PackageManager packageManager = context.getPackageManager();
            String bluetoothPackageName = context.getResources().getString(17039427);
            assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, "com.android.phone", true);
            assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, bluetoothPackageName, true);
            assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, MMS_SERVICE_PACKAGE_NAME, true);
            assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, TELEPHONY_PROVIDER_PACKAGE_NAME, true);
            assignWriteSmsPermissionToSystemUid(appOps, 1001);
            assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, NEW_SEC_SMS_PACKAGE_NAME, true);
            Rlog.d(LOG_TAG, "Assign writesms permission to special system apps for specific tablets");
            return null;
        }
        Collection<SmsApplicationData> applications = getApplicationCollectionInternal(context, userId);
        String defaultApplication = getDefaultSmsPackage(context, userId);
        SmsApplicationData applicationData = null;
        if (defaultApplication != null) {
            applicationData = getApplicationForPackage(applications, defaultApplication);
        }
        if (updateIfNeeded && applicationData == null) {
            Resources r = context.getResources();
            String configedSmsPackageName = SemSystemProperties.get(DEFAULT_MSGAPP_SYSTEMPROPERTY, SM_TAG);
            if (TextUtils.isEmpty(configedSmsPackageName)) {
                Log.i(LOG_TAG, "there is no configedPackage, getApplication from default_sms_application");
                defaultPackage = r.getString(R.string.default_sms_application);
            } else if (configedSmsPackageName.equals("AM")) {
                Log.i(LOG_TAG, "AM is configedPackage, getApplication from configedSmsPackageName");
                defaultPackage = GOOGLE_MESSAGE_PACKAGE;
            } else {
                Log.i(LOG_TAG, "SM is configedPackage, getApplication from default_sms_application");
                defaultPackage = r.getString(R.string.default_sms_application);
            }
            applicationData = getApplicationForPackage(applications, defaultPackage);
            if (applicationData == null && applications.size() != 0) {
                int i = 0;
                while (true) {
                    if (i >= applications.size()) {
                        break;
                    }
                    if (!NEW_SEC_SMS_PACKAGE_NAME.equals(((SmsApplicationData) applications.toArray()[i]).mPackageName)) {
                        i++;
                    } else {
                        applicationData = (SmsApplicationData) applications.toArray()[i];
                        break;
                    }
                }
                if (applicationData == null) {
                    applicationData = (SmsApplicationData) applications.toArray()[0];
                }
            }
            if (applicationData != null) {
                setDefaultApplicationInternal(applicationData.mPackageName, context, userId);
            }
        }
        if (applicationData != null) {
            if (updateIfNeeded || applicationData.mUid == Process.myUid()) {
                boolean appOpsFixed = tryFixExclusiveSmsAppops(context, applicationData, updateIfNeeded);
                if (!appOpsFixed) {
                    applicationData = null;
                }
            }
            if (applicationData != null && updateIfNeeded) {
                defaultSmsAppChanged(context);
            }
        }
        return applicationData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getDefaultSmsPackage(Context context, int userId) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        if (roleManager == null) {
            return "";
        }
        return roleManager.getSmsRoleHolder(userId);
    }

    private static void defaultSmsAppChanged(Context context) {
        PackageManager packageManager = context.getPackageManager();
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        String bluetoothPackageName = context.getResources().getString(17039427);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, "com.android.phone", true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, bluetoothPackageName, false);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, MMS_SERVICE_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, TELEPHONY_PROVIDER_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOps, CellBroadcastUtils.getDefaultCellBroadcastReceiverPackageName(context), false);
        for (String opStr : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            int mode = appOps.unsafeCheckOp(opStr, 1001, "com.android.phone");
            if (mode != 0) {
                appOps.setUidMode(opStr, 1001, 0);
            }
        }
    }

    private static boolean tryFixExclusiveSmsAppops(Context context, SmsApplicationData applicationData, boolean updateIfNeeded) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        for (String opStr : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            int mode = appOps.unsafeCheckOp(opStr, applicationData.mUid, applicationData.mPackageName);
            if (mode != 0) {
                Log.e(LOG_TAG, applicationData.mPackageName + " lost " + opStr + ": " + (updateIfNeeded ? " (fixing)" : " (no permission to fix)"));
                if (!updateIfNeeded) {
                    return false;
                }
                appOps.setUidMode(opStr, applicationData.mUid, 0);
            }
        }
        return true;
    }

    public static void setDefaultApplication(String packageName, Context context) {
        setDefaultApplicationAsUser(packageName, context, getIncomingUserId());
    }

    public static void setDefaultApplicationAsUser(String packageName, Context context, int userId) {
        if (context == null) {
            Rlog.e(LOG_TAG, "context in DefaultApplication is null");
            return;
        }
        int ret = -1;
        int userHandle = UserHandle.getUserId(Binder.getCallingUid());
        Uri uri = Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy");
        String[] selectionArgs = {packageName, Integer.toString(userHandle)};
        Cursor cr = context.getContentResolver().query(uri, null, SecContentProviderURI.APPLICATIONPOLICY_DEFAULTSMSAPP_METHOD, selectionArgs, null);
        if (cr != null) {
            try {
                cr.moveToFirst();
                if (cr.getString(cr.getColumnIndex(SecContentProviderURI.APPLICATIONPOLICY_DEFAULTSMSAPP_METHOD)).equals("true")) {
                    ret = 1;
                } else {
                    ret = 0;
                }
            } catch (Exception e) {
            } catch (Throwable th) {
                cr.close();
                throw th;
            }
            cr.close();
        }
        if (ret == 0) {
            Rlog.e(LOG_TAG, "Block setDefaultApplication by admin");
            return;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        RoleManager roleManager = (RoleManager) context.getSystemService(Context.ROLE_SERVICE);
        if (!tm.isSmsCapable() && (roleManager == null || !roleManager.isRoleAvailable("android.app.role.SMS"))) {
            return;
        }
        long token = Binder.clearCallingIdentity();
        try {
            setDefaultApplicationInternal(packageName, context, userId);
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    private static void setDefaultApplicationInternal(String packageName, Context context, int userId) {
        UserHandle userHandle = UserHandle.of(userId);
        String oldPackageName = getDefaultSmsPackage(context, userId);
        if (packageName != null && oldPackageName != null && packageName.equals(oldPackageName)) {
            return;
        }
        PackageManager packageManager = context.createContextAsUser(userHandle, 0).getPackageManager();
        Collection<SmsApplicationData> applications = getApplicationCollectionInternal(context, userId);
        if (oldPackageName != null) {
            getApplicationForPackage(applications, oldPackageName);
        }
        SmsApplicationData applicationData = getApplicationForPackage(applications, packageName);
        if (applicationData != null) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (oldPackageName != null) {
                try {
                    int uid = packageManager.getPackageInfo(oldPackageName, 0).applicationInfo.uid;
                    setExclusiveAppops(oldPackageName, appOps, uid, 3);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.w(LOG_TAG, "Old SMS package not found: " + oldPackageName);
                }
            }
            Rlog.e(LOG_TAG, "update the default app to : " + applicationData.mPackageName + " oldPackageName: " + oldPackageName);
            sendBroadcast_SMS_BIG_DATA_INFO(context, oldPackageName, applicationData.mPackageName, null);
            final CompletableFuture<Void> future = new CompletableFuture<>();
            Consumer<Boolean> callback = new Consumer() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SmsApplication.lambda$setDefaultApplicationInternal$0(future, (Boolean) obj);
                }
            };
            ((RoleManager) context.getSystemService(RoleManager.class)).addRoleHolderAsUser("android.app.role.SMS", applicationData.mPackageName, 0, UserHandle.of(userId), AsyncTask.THREAD_POOL_EXECUTOR, callback);
            try {
                future.get(5L, TimeUnit.SECONDS);
                defaultSmsAppChanged(context);
            } catch (InterruptedException | ExecutionException | TimeoutException e2) {
                Log.e(LOG_TAG, "Exception while adding sms role holder " + applicationData, e2);
            }
        }
    }

    static /* synthetic */ void lambda$setDefaultApplicationInternal$0(CompletableFuture future, Boolean successful) {
        if (successful.booleanValue()) {
            future.complete(null);
        } else {
            future.completeExceptionally(new RuntimeException());
        }
    }

    public static void sendBroadcast_SMS_BIG_DATA_INFO(Context context, String oldPackageName, String newPackageName, SemDMACdata dmacData) {
        try {
            Intent sdacIntent = new Intent("com.samsung.intent.action.SMS_BIG_DATA_INFO");
            sdacIntent.putExtra("feature", "sdac");
            sdacIntent.putExtra("nsda", newPackageName);
            if (!TextUtils.isEmpty(oldPackageName)) {
                sdacIntent.putExtra("osda", oldPackageName);
            }
            if (dmacData != null) {
                sdacIntent.putExtra("dmac", dmacData);
            }
            context.sendBroadcast(sdacIntent);
        } catch (IllegalStateException e) {
            Log.w(LOG_TAG, "IllegalStateException : intent should be broadcast after boot completed");
        } catch (SecurityException e2) {
            Log.w(LOG_TAG, "Permission Denial: com.samsung.intent.action.SMS_BIG_DATA_INFO");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void broadcastSmsAppChange(Context context, UserHandle userHandle, String oldPackage, String newPackage) {
        Collection<SmsApplicationData> apps = getApplicationCollection(context);
        broadcastSmsAppChange(context, userHandle, getApplicationForPackage(apps, oldPackage), getApplicationForPackage(apps, newPackage));
    }

    private static void broadcastSmsAppChange(Context context, UserHandle userHandle, SmsApplicationData oldAppData, SmsApplicationData applicationData) {
        if (oldAppData != null && oldAppData.mSmsAppChangedReceiverClass != null) {
            Intent oldAppIntent = new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            ComponentName component = new ComponentName(oldAppData.mPackageName, oldAppData.mSmsAppChangedReceiverClass);
            oldAppIntent.setComponent(component);
            oldAppIntent.putExtra(Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, false);
            context.sendBroadcastAsUser(oldAppIntent, userHandle);
        }
        if (applicationData != null && applicationData.mSmsAppChangedReceiverClass != null) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            ComponentName component2 = new ComponentName(applicationData.mPackageName, applicationData.mSmsAppChangedReceiverClass);
            intent.setComponent(component2);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, true);
            context.sendBroadcastAsUser(intent, userHandle);
        }
        context.sendBroadcastAsUser(new Intent(ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL), userHandle, "android.permission.MONITOR_DEFAULT_SMS_PACKAGE");
    }

    private static void assignExclusiveSmsPermissionsToSystemApp(Context context, PackageManager packageManager, AppOpsManager appOps, String packageName, boolean sigatureMatch) {
        if (packageName == null) {
            return;
        }
        if (sigatureMatch) {
            int result = packageManager.checkSignatures(context.getPackageName(), packageName);
            if (result != 0) {
                Log.e(LOG_TAG, packageName + " does not have system signature");
                return;
            }
        }
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            int mode = appOps.unsafeCheckOp(AppOpsManager.OPSTR_WRITE_SMS, info.applicationInfo.uid, packageName);
            if (mode != 0) {
                Log.w(LOG_TAG, packageName + " does not have OP_WRITE_SMS:  (fixing)");
                setExclusiveAppops(packageName, appOps, info.applicationInfo.uid, 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "Package not found: " + packageName);
        }
    }

    private static void setExclusiveAppops(String pkg, AppOpsManager appOpsManager, int uid, int mode) {
        for (String opStr : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            appOpsManager.setUidMode(opStr, uid, mode);
        }
    }

    private static final class SmsPackageMonitor extends PackageChangeReceiver {
        private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 30, 60, TimeUnit.MINUTES, new LinkedBlockingQueue());
        final Context mContext;

        public SmsPackageMonitor(Context context) {
            this.mContext = context;
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageDisappeared() {
            onPackageChanged();
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageAppeared() {
            onPackageChanged();
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageModified(String packageName) {
            onPackageChanged();
        }

        private void onPackageChanged() {
            int userId;
            try {
                userId = getSendingUser().getIdentifier();
            } catch (NullPointerException e) {
                userId = UserHandle.SYSTEM.getIdentifier();
            }
            Context userContext = this.mContext;
            if (userId != UserHandle.SYSTEM.getIdentifier()) {
                try {
                    userContext = this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, UserHandle.of(userId));
                } catch (PackageManager.NameNotFoundException e2) {
                }
            }
            final Context threadContext = userContext;
            threadPool.execute(new Runnable() { // from class: com.android.internal.telephony.SmsApplication.SmsPackageMonitor.1
                @Override // java.lang.Runnable
                public void run() {
                    Rlog.d(SmsApplication.LOG_TAG, "onPackageChanged: run");
                    PackageManager packageManager = threadContext.getPackageManager();
                    ComponentName componentName = SmsApplication.getDefaultSendToApplication(threadContext, true);
                    if (componentName != null) {
                        SmsApplication.configurePreferredActivity(packageManager, componentName);
                    }
                    Rlog.d(SmsApplication.LOG_TAG, "onPackageChanged: end");
                }
            });
        }
    }

    private static final class SmsRoleListener implements OnRoleHoldersChangedListener {
        private final Context mContext;
        private final RoleManager mRoleManager;
        private final SparseArray<String> mSmsPackageNames = new SparseArray<>();

        public SmsRoleListener(Context context) {
            this.mContext = context;
            this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
            List<UserHandle> users = ((UserManager) context.getSystemService(UserManager.class)).getUserHandles(true);
            int usersSize = users.size();
            for (int i = 0; i < usersSize; i++) {
                UserHandle user = users.get(i);
                this.mSmsPackageNames.put(user.getIdentifier(), getSmsPackageName(user));
            }
            this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), this, UserHandle.ALL);
        }

        public void onRoleHoldersChanged(String roleName, UserHandle user) {
            if (!Objects.equals(roleName, "android.app.role.SMS")) {
                return;
            }
            int userId = user.getIdentifier();
            String newSmsPackageName = getSmsPackageName(user);
            SmsApplication.broadcastSmsAppChange(this.mContext, user, this.mSmsPackageNames.get(userId), newSmsPackageName);
            this.mSmsPackageNames.put(userId, newSmsPackageName);
        }

        private String getSmsPackageName(UserHandle user) {
            List<String> roleHolders = this.mRoleManager.getRoleHoldersAsUser("android.app.role.SMS", user);
            if (roleHolders.isEmpty()) {
                return null;
            }
            return roleHolders.get(0);
        }
    }

    public static void initSmsPackageMonitor(Context context) {
        sSmsPackageMonitor = new SmsPackageMonitor(context);
        sSmsPackageMonitor.register(context, context.getMainLooper(), UserHandle.ALL);
        sSmsRoleListener = new SmsRoleListener(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void configurePreferredActivity(PackageManager packageManager, ComponentName componentName) {
        replacePreferredActivity(packageManager, componentName, "sms");
        replacePreferredActivity(packageManager, componentName, SCHEME_SMSTO);
        replacePreferredActivity(packageManager, componentName, "mms");
        replacePreferredActivity(packageManager, componentName, SCHEME_MMSTO);
    }

    private static void replacePreferredActivity(PackageManager packageManager, ComponentName componentName, String scheme) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(scheme, "", null));
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, Sensor.SEM_TYPE_HALLIC);
        List<ComponentName> components = (List) resolveInfoList.stream().map(new Function() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SmsApplication.lambda$replacePreferredActivity$1((ResolveInfo) obj);
            }
        }).collect(Collectors.toList());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SENDTO);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addDataScheme(scheme);
        packageManager.replacePreferredActivity(intentFilter, 2129920, components, componentName);
    }

    static /* synthetic */ ComponentName lambda$replacePreferredActivity$1(ResolveInfo info) {
        return new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
    }

    public static SmsApplicationData getSmsApplicationData(String packageName, Context context) {
        Collection<SmsApplicationData> applications = getApplicationCollection(context);
        return getApplicationForPackage(applications, packageName);
    }

    public static ComponentName getDefaultSmsApplication(Context context, boolean updateIfNeeded) {
        return getDefaultSmsApplicationAsUser(context, updateIfNeeded, getIncomingUserHandle());
    }

    public static ComponentName getDefaultSmsApplicationAsUser(Context context, boolean updateIfNeeded, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userHandle.getIdentifier());
            if (smsApplicationData != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSmsReceiverClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static ComponentName getDefaultMmsApplication(Context context, boolean updateIfNeeded) {
        return getDefaultMmsApplicationAsUser(context, updateIfNeeded, getIncomingUserHandle());
    }

    public static ComponentName getDefaultMmsApplicationAsUser(Context context, boolean updateIfNeeded, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userHandle.getIdentifier());
            if (smsApplicationData != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mMmsReceiverClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static ComponentName getDefaultRespondViaMessageApplication(Context context, boolean updateIfNeeded) {
        return getDefaultRespondViaMessageApplicationAsUser(context, updateIfNeeded, getIncomingUserHandle());
    }

    public static ComponentName getDefaultRespondViaMessageApplicationAsUser(Context context, boolean updateIfNeeded, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userHandle.getIdentifier());
            if (smsApplicationData != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mRespondViaMessageClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static ComponentName getDefaultSendToApplication(Context context, boolean updateIfNeeded) {
        int userId = getIncomingUserId();
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userId);
            if (smsApplicationData != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSendToClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplication(Context context, boolean updateIfNeeded) {
        return getDefaultExternalTelephonyProviderChangedApplicationAsUser(context, updateIfNeeded, getIncomingUserHandle());
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplicationAsUser(Context context, boolean updateIfNeeded, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userHandle.getIdentifier());
            if (smsApplicationData != null && smsApplicationData.mProviderChangedReceiverClass != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mProviderChangedReceiverClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static ComponentName getDefaultSimFullApplication(Context context, boolean updateIfNeeded) {
        return getDefaultSimFullApplicationAsUser(context, updateIfNeeded, getIncomingUserHandle());
    }

    public static ComponentName getDefaultSimFullApplicationAsUser(Context context, boolean updateIfNeeded, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long token = Binder.clearCallingIdentity();
        ComponentName component = null;
        try {
            SmsApplicationData smsApplicationData = getApplication(context, updateIfNeeded, userHandle.getIdentifier());
            if (smsApplicationData != null && smsApplicationData.mSimFullReceiverClass != null) {
                component = new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSimFullReceiverClass);
            }
            return component;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }

    public static boolean shouldWriteMessageForPackage(String packageName, Context context) {
        return !shouldWriteMessageForPackageAsUser(packageName, context, getIncomingUserHandle());
    }

    public static boolean shouldWriteMessageForPackageAsUser(String packageName, Context context, UserHandle userHandle) {
        return !isDefaultSmsApplicationAsUser(context, packageName, userHandle);
    }

    public static boolean isDefaultSmsApplication(Context context, String packageName) {
        return isDefaultSmsApplicationAsUser(context, packageName, getIncomingUserHandle());
    }

    public static boolean isDefaultSmsApplicationAsUser(Context context, String packageName, UserHandle userHandle) {
        boolean z;
        if (packageName == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        ComponentName component = getDefaultSmsApplicationAsUser(context, false, userHandle);
        String defaultSmsPackage = null;
        if (component != null) {
            defaultSmsPackage = component.getPackageName();
        }
        String bluetoothPackageName = context.getResources().getString(17039427);
        if ((defaultSmsPackage != null && defaultSmsPackage.equals(packageName)) || bluetoothPackageName.equals(packageName) || packageName.equals(NSRI_PACKAGE_NAME)) {
            return true;
        }
        if (!getEnableSecSms(context) && packageName.equals(COREAPPS_PACKAGE_NAME)) {
            Rlog.d(LOG_TAG, "shouldWriteMessageForPackage is true for none SECSMS app model.");
            return false;
        }
        boolean isCallingIdItsOn = true;
        if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableItsOn")) {
            if (Binder.getCallingUid() == 4002) {
                z = false;
            } else {
                z = true;
            }
            isCallingIdItsOn = z;
        }
        if ((defaultSmsPackage == null || !defaultSmsPackage.equals(packageName)) && !isShouldNotWriteMessage(context, packageName) && isCallingIdItsOn) {
            return false;
        }
        return true;
    }

    public static boolean isDefaultMmsApplication(Context context, String packageName) {
        return isDefaultMmsApplicationAsUser(context, packageName, getIncomingUserHandle());
    }

    public static boolean isDefaultMmsApplicationAsUser(Context context, String packageName, UserHandle userHandle) {
        String defaultMmsPackage;
        if (packageName == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        ComponentName component = getDefaultMmsApplicationAsUser(context, false, userHandle);
        if (component == null || (defaultMmsPackage = component.getPackageName()) == null) {
            return false;
        }
        String bluetoothPackageName = context.getResources().getString(17039427);
        if (!defaultMmsPackage.equals(packageName) && !bluetoothPackageName.equals(packageName)) {
            return false;
        }
        return true;
    }

    private static void assignWriteSmsPermissionToSystemUid(AppOpsManager appOps, int uid) {
        appOps.setUidMode(15, uid, 0);
    }

    private static String getDefaultSmsApplicationPackageName(Context context) {
        ComponentName component = getDefaultSmsApplication(context, false);
        if (component != null) {
            return component.getPackageName();
        }
        return null;
    }

    public static boolean isShouldNotWriteMessage(Context context, String packageName) {
        String countryisoCode = SemSystemProperties.get("ro.csc.countryiso_code");
        String defaultSmsPackage = getDefaultSmsApplicationPackageName(context);
        boolean isAMpreload = false;
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(GOOGLE_MESSAGE_PACKAGE, 0);
            if ((appInfo.flags & 1) != 0) {
                Rlog.i(LOG_TAG, "AM is preloaded");
                isAMpreload = true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            isAMpreload = false;
        }
        if (defaultSmsPackage != null && !defaultSmsPackage.equals(NEW_SEC_SMS_PACKAGE_NAME) && isAMpreload && NEW_SEC_SMS_PACKAGE_NAME.equals(packageName) && !"KR".equalsIgnoreCase(countryisoCode) && !"KOREA".equalsIgnoreCase(countryisoCode)) {
            return false;
        }
        if (sPackageNamePattern == null) {
            sPackageNamePattern = context.getResources().getStringArray(R.array.shouldNotWriteMessage);
        }
        for (String name : sPackageNamePattern) {
            if (packageName.equals(name)) {
                Rlog.d(LOG_TAG, packageName + " is matched");
                return true;
            }
        }
        if (!isVzwAuthorizedApp(context, packageName)) {
            Rlog.d(LOG_TAG, "No PackageName Pattern : " + packageName);
            return false;
        }
        return true;
    }

    private static boolean isVzwAuthorizedApp(Context context, String packageName) {
        return VZWAVSLibrary.isPackageAuthorized(context, packageName, "VZWSMS");
    }

    public static void setPendingDeliveryIntent(PendingIntent deliveryIntent) {
        mPendingDeliveryIntent = deliveryIntent;
    }

    public static void initPendingDeliveryIntent() {
        mPendingDeliveryIntent = null;
    }

    public static PendingIntent getPendingDeliveryIntent() {
        return mPendingDeliveryIntent;
    }

    public static boolean getEnableSecSms(Context context) {
        PackageManager packageManager = context.getPackageManager();
        boolean isSecSms = false;
        try {
            packageManager.getPackageInfo("com.android.mms".trim(), 128);
            isSecSms = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        try {
            packageManager.getPackageInfo(NEW_SEC_SMS_PACKAGE_NAME.trim(), 128);
            return true;
        } catch (PackageManager.NameNotFoundException e2) {
            return isSecSms;
        }
    }

    private static void defaultMessageAppConfigInfoDump(Context context, String defaultMsgAppConfigInfo) {
        Intent intent = new Intent(DEFAULT_MSG_APP_INFO_LOGGING);
        intent.putExtra("defaultMsgAppConfigInfo", defaultMsgAppConfigInfo);
        intent.setPackage("com.android.phone");
        context.sendBroadcast(intent);
    }

    public static void setDefaultMessageAppConfig(Context context) {
        if (TelephonyFeatures.IS_WIFI_ONLY) {
            Log.i(LOG_TAG, "wifi-only tablet does not support default message app.");
            mLogStb.append("wifi-only tablet, skip default message app setting.");
            defaultMessageAppConfigInfoDump(context, mLogStb.toString());
        } else {
            if (sDefaultMessageAppConfig == null) {
                sDefaultMessageAppConfig = new DefaultMessageAppConfig(context);
            }
            sDefaultMessageAppConfig.setDefaultMsgApp();
            defaultMessageAppConfigInfoDump(context, mLogStb.toString());
        }
    }

    private static final class DefaultMessageAppConfig {
        final Context mContext;

        public DefaultMessageAppConfig(Context context) {
            this.mContext = context;
        }

        private boolean isTssDevice() {
            return SemSystemProperties.getBoolean("mdc.singlesku", false);
        }

        private boolean isOperatorFixed() {
            boolean isSupportTrueSingleSKU = isTssDevice();
            boolean isTSSActivated = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            Log.i(SmsApplication.LOG_TAG, "isOperatorFixed()- isSupportTrueSingleSKU : " + isSupportTrueSingleSKU + " isTSSActivated : " + isTSSActivated);
            if (isSupportTrueSingleSKU) {
                return isTSSActivated;
            }
            return true;
        }

        private String getActiveOperatorIdByCountryiso(String countryIso) {
            String activeOperatorId = "NONE";
            try {
                FileInputStream stream = new FileInputStream(new File(SmsApplication.COUNTRYISO_OPENBUYER_CONFIG_XML));
                boolean bFind = false;
                try {
                    try {
                        XmlPullParser xpp = getParser(stream);
                        if (xpp == null) {
                            Log.e(SmsApplication.LOG_TAG, "XmlPullParser is null");
                        } else {
                            for (int eventType = xpp.getEventType(); eventType != 1; eventType = xpp.next()) {
                                switch (eventType) {
                                    case 2:
                                        if (countryIso.equals(xpp.getName())) {
                                            bFind = true;
                                            break;
                                        }
                                        break;
                                    case 4:
                                        if (bFind) {
                                            activeOperatorId = xpp.getText().trim();
                                            bFind = false;
                                            break;
                                        }
                                        break;
                                }
                                if ("NONE".equals(activeOperatorId)) {
                                }
                            }
                        }
                        Log.d(SmsApplication.LOG_TAG, "xml parsing result- activeOperatorId: " + activeOperatorId);
                    } catch (IOException | XmlPullParserException exp) {
                        Log.e(SmsApplication.LOG_TAG, "Error while parsing", exp);
                    }
                } catch (Throwable th) {
                }
                closeFileInputStream(stream);
                return activeOperatorId;
            } catch (FileNotFoundException e) {
                Log.e(SmsApplication.LOG_TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
                return "NONE";
            }
        }

        private boolean isWifiSkipCarrier() {
            String carrierId = SemSystemProperties.get("ro.boot.carrierid", null);
            String[] wifiSkipCarrier = {"XSG", "MID", "ILO", "XFA", "AFR", "M10", "M06", "M05"};
            List<String> wifiSkipCarrierList = Arrays.asList(wifiSkipCarrier);
            if (!TextUtils.isEmpty(carrierId) && wifiSkipCarrierList.contains(carrierId)) {
                Log.d(SmsApplication.LOG_TAG, "isWifiSkipCarrier return true");
                return true;
            }
            return false;
        }

        private String getActiveOperatorId() {
            boolean isSupportTrueSingleSKU = isTssDevice();
            boolean isTSSActivated = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            String activeOperatorId = "NONE";
            if (isSupportTrueSingleSKU) {
                if (isTSSActivated) {
                    activeOperatorId = SemSystemProperties.get("ro.boot.activatedid", "NONE");
                    if ("EUX".equals(activeOperatorId) || "EUY".equals(activeOperatorId)) {
                        String countryiso = SemSystemProperties.get("ro.csc.countryiso_code", "NONE");
                        Log.i(SmsApplication.LOG_TAG, "countryiso : " + countryiso);
                        if ("NONE".equals(countryiso)) {
                            activeOperatorId = "NONE";
                        } else {
                            activeOperatorId = getActiveOperatorIdByCountryiso(countryiso);
                        }
                    }
                } else if (isWifiSkipCarrier()) {
                    activeOperatorId = SystemProperties.get("ro.csc.sales_code", "NONE");
                }
            } else {
                activeOperatorId = SystemProperties.get("ro.csc.sales_code", "NONE");
            }
            SmsApplication.mLogStb.append(" isSupportTrueSingleSKU : ").append(isSupportTrueSingleSKU).append(", isTSSActivated : ").append(isTSSActivated).append(", isWifiSkipCarrier : ").append(isWifiSkipCarrier()).append(", activeOperatorId : ").append(activeOperatorId);
            setDMACdataTssInfo(isSupportTrueSingleSKU, isTSSActivated, activeOperatorId);
            return activeOperatorId;
        }

        void setDMACdataTssInfo(boolean isSupportTrueSingleSKU, boolean isTSSActivated, String activeOperatorId) {
            if (!isSupportTrueSingleSKU) {
                SmsApplication.sDMACdata.setTssActivated("NotSupported");
            } else if (isTSSActivated) {
                SmsApplication.sDMACdata.setTssActivated("Activated");
            } else {
                SmsApplication.sDMACdata.setTssActivated("Deactivated");
            }
            SmsApplication.sDMACdata.setCarrierActivatedId(activeOperatorId);
        }

        void setDMACdataConfigInfo(boolean isUnlockPhone, String mccmnc) {
            if (isUnlockPhone) {
                SmsApplication.sDMACdata.setIsUnLockedPhone("True");
            } else {
                SmsApplication.sDMACdata.setIsUnLockedPhone("False");
            }
            SmsApplication.sDMACdata.setMccmnc(mccmnc);
        }

        private int findLoadedSimSlot() {
            TelephonyManager tm = (TelephonyManager) this.mContext.getSystemService("phone");
            int phoneCount = tm != null ? tm.getPhoneCount() : 0;
            for (int i = 0; i < phoneCount; i++) {
                int state = TelephonyManager.getSimStateForSlotIndex(i);
                if (state == 10) {
                    int simSlot = i;
                    return simSlot;
                }
            }
            return -1;
        }

        private static XmlPullParser getParser(FileInputStream stream) {
            if (stream == null) {
                Log.d(SmsApplication.LOG_TAG, "no file");
                return null;
            }
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stream, null);
                return xpp;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                closeFileInputStream(stream);
                return null;
            }
        }

        private static void closeFileInputStream(FileInputStream stream) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean isAMInstalled() {
            PackageManager pm = this.mContext.getPackageManager();
            try {
                pm.getApplicationInfo(SmsApplication.GOOGLE_MESSAGE_PACKAGE, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        private boolean isSMInstalled() {
            PackageManager pm = this.mContext.getPackageManager();
            try {
                pm.getApplicationInfo(SmsApplication.NEW_SEC_SMS_PACKAGE_NAME, 0);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        private boolean isPackageEnabled(Context context, String packageName) {
            try {
                int enable = context.getPackageManager().getApplicationEnabledSetting(packageName);
                if (2 == enable || 3 == enable) {
                    return false;
                }
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        private boolean hasPackage(Context c, String pkg) {
            PackageManager pm = c.getPackageManager();
            try {
                pm.getApplicationInfo(pkg, 128);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.d(SmsApplication.LOG_TAG, "Package not found : " + pkg);
                return false;
            }
        }

        private String getMessagePackageName(Context context) {
            String packageName = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME");
            if (!hasPackage(context, packageName)) {
                return "com.android.mms";
            }
            return packageName;
        }

        private String getSalesCode() {
            String sales_code = "";
            try {
                sales_code = SystemProperties.get("ro.csc.sales_code", "NONE");
                if (TextUtils.isEmpty(sales_code)) {
                    String sales_code2 = SystemProperties.get("ril.sales_code", "NONE");
                    return sales_code2;
                }
                return sales_code;
            } catch (Exception e) {
                Log.d(SmsApplication.LOG_TAG, "readSalesCode failed");
                return sales_code;
            }
        }

        public void setDefaultMsgApp() {
            String activeOperatorId = getActiveOperatorId();
            Log.i(SmsApplication.LOG_TAG, "setDefaultMsgAppFromConfig");
            SmsApplication.mLogStb.append("setDefaultMsgApp Config Info =");
            int userId = SmsApplication.getIncomingUserId();
            String oldPackageName = SmsApplication.getDefaultSmsPackage(this.mContext, userId);
            boolean carrierApp = false;
            if ("SBM".equals(activeOperatorId) || "DCM".equals(activeOperatorId) || "KDI".equals(activeOperatorId)) {
                carrierApp = true;
            }
            if (!isAMInstalled()) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG);
                SmsApplication.sDMACdata.setPreInstalledMsgAppError("NoAM");
            } else if (!carrierApp && !isSMInstalled()) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, "AM");
                SmsApplication.sDMACdata.setPreInstalledMsgAppError("NoSM");
                Log.i(SmsApplication.LOG_TAG, "SM is not installed ");
            } else if ("NONE".equals(activeOperatorId) || carrierApp) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG);
            } else {
                boolean mChangeToAM = setDefaultMsgApp_File(activeOperatorId);
                SmsApplication.mLogStb.append(", mChangeToAM : ").append(mChangeToAM);
                if (mChangeToAM) {
                    SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, "AM");
                    SmsApplication.setDefaultApplication(SmsApplication.GOOGLE_MESSAGE_PACKAGE, this.mContext);
                } else {
                    SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG);
                }
            }
            if (SmsApplication.SM_TAG.equals(SemSystemProperties.get(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG))) {
                setSMorOperatorMessageApp();
            }
            SmsApplication.mLogStb.append(", SemSystemProperties - persist.ril.config.defaultmsgapp : ").append(SemSystemProperties.get(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG));
            Log.i(SmsApplication.LOG_TAG, "Default Msg app is " + SemSystemProperties.get(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY));
            Log.i(SmsApplication.LOG_TAG, "Default Msg app parameter: " + SmsApplication.sDMACdata.toString());
            SmsApplication.sendBroadcast_SMS_BIG_DATA_INFO(this.mContext, oldPackageName, SemSystemProperties.get(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY), SmsApplication.sDMACdata);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0050, code lost:
        
            if (r0.equals("DCM") != false) goto L22;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void setSMorOperatorMessageApp() {
            /*
                r8 = this;
                java.lang.String r0 = r8.getSalesCode()
                android.content.Context r1 = r8.mContext
                java.lang.String r2 = "phone"
                java.lang.Object r1 = r1.getSystemService(r2)
                android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L1b
                boolean r4 = r1.isSmsCapable()
                if (r4 == 0) goto L1b
                r4 = r2
                goto L1c
            L1b:
                r4 = r3
            L1c:
                if (r4 == 0) goto Laf
                com.samsung.android.feature.SemCscFeature r5 = com.samsung.android.feature.SemCscFeature.getInstance()
                java.lang.String r6 = "CscFeature_Setting_ConfigDefSmsApp"
                java.lang.String r5 = r5.getString(r6)
                android.content.Context r6 = r8.mContext
                java.lang.String r6 = r8.getMessagePackageName(r6)
                int r7 = r0.hashCode()
                switch(r7) {
                    case 67502: goto L4a;
                    case 74256: goto L40;
                    case 81886: goto L36;
                    default: goto L35;
                }
            L35:
                goto L53
            L36:
                java.lang.String r2 = "SBM"
                boolean r2 = r0.equals(r2)
                if (r2 == 0) goto L35
                r2 = r3
                goto L54
            L40:
                java.lang.String r2 = "KDI"
                boolean r2 = r0.equals(r2)
                if (r2 == 0) goto L35
                r2 = 2
                goto L54
            L4a:
                java.lang.String r3 = "DCM"
                boolean r3 = r0.equals(r3)
                if (r3 == 0) goto L35
                goto L54
            L53:
                r2 = -1
            L54:
                switch(r2) {
                    case 0: goto L7b;
                    case 1: goto L65;
                    case 2: goto L58;
                    default: goto L57;
                }
            L57:
                goto L89
            L58:
                android.content.Context r2 = r8.mContext
                java.lang.String r3 = "com.kddi.android.cmail"
                boolean r2 = r8.isPackageEnabled(r2, r3)
                if (r2 == 0) goto L89
                java.lang.String r6 = "com.kddi.android.cmail"
                goto L89
            L65:
                android.content.Context r2 = r8.mContext
                java.lang.String r3 = "com.nttdocomo.android.msg"
                boolean r2 = r8.isPackageEnabled(r2, r3)
                if (r2 == 0) goto L89
                java.lang.String r2 = "samsung"
                boolean r2 = r5.contains(r2)
                if (r2 != 0) goto L89
                java.lang.String r6 = "com.nttdocomo.android.msg"
                goto L89
            L7b:
                android.content.Context r2 = r8.mContext
                java.lang.String r3 = "jp.softbank.mb.mail"
                boolean r2 = r8.isPackageEnabled(r2, r3)
                if (r2 == 0) goto L89
                java.lang.String r6 = "jp.softbank.mb.mail"
            L89:
                android.content.Context r2 = r8.mContext
                boolean r2 = r8.isPackageEnabled(r2, r6)
                if (r2 == 0) goto Laf
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r3 = "setDefaultApplication messageAppName : "
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.StringBuilder r2 = r2.append(r6)
                java.lang.String r2 = r2.toString()
                java.lang.String r3 = "SmsApplication"
                android.util.Log.i(r3, r2)
                android.content.Context r2 = r8.mContext
                com.android.internal.telephony.SmsApplication.setDefaultApplication(r6, r2)
            Laf:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsApplication.DefaultMessageAppConfig.setSMorOperatorMessageApp():void");
        }

        private String updateChangeByOs(String changedValueByOs, String activeOperatorId, boolean isOpen) {
            if (TextUtils.isEmpty(changedValueByOs)) {
                return "";
            }
            String[] changeOsSplit = changedValueByOs.split("\\|");
            for (String split : changeOsSplit) {
                if (isOpen) {
                    if (split.contains(activeOperatorId)) {
                        String retVal = split.replace(activeOperatorId + Session.SESSION_SEPARATION_CHAR_CHILD, "");
                        return retVal;
                    }
                } else if (split.contains("SM_")) {
                    String retVal2 = split.replace("SM_", "");
                    return retVal2;
                }
            }
            return "";
        }

        private boolean isNeedToModifyFirstApi() {
            String productModel = SemSystemProperties.get("ro.product.model", "Unknown");
            String[] model = {"SM-A155F", "SM-A155M", "SM-G556B", "SM-A156E", "SM-A156B", "SM-A156M", "SM-A1560", "SM-A256B", "SM-A256E", "SM-A2560", "SM-A256U", "SM-A256U1", "SM-A256N", "SM-X306B", "SM-X300", "SM-X306N", "SM-X308U", "SM-X308B"};
            List<String> modelList = Arrays.asList(model);
            if (modelList.contains(productModel)) {
                Log.d(SmsApplication.LOG_TAG, "isNeedToModifyFirstApi return true : " + productModel);
                return true;
            }
            return false;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x02c8, code lost:
        
            if (r5.equals("111111") != false) goto L99;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean setDefaultMsgApp_File(java.lang.String r28) {
            /*
                Method dump skipped, instructions count: 1050
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsApplication.DefaultMessageAppConfig.setDefaultMsgApp_File(java.lang.String):boolean");
        }
    }
}
