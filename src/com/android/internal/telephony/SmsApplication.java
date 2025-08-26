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
import com.samsung.android.lock.LsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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

/* loaded from: classes4.dex */
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

        public SmsApplicationData(String str, int i) {
            this.mPackageName = str;
            this.mUid = i;
        }

        public String getApplicationName(Context context) {
            if (this.mApplicationName == null) {
                PackageManager packageManager = context.getPackageManager();
                try {
                    ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(this.mPackageName, 0, UserHandle.getUserHandleForUid(this.mUid));
                    if (applicationInfoAsUser != null) {
                        CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfoAsUser);
                        this.mApplicationName = applicationLabel != null ? applicationLabel.toString() : null;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
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
        int iMyUserId = UserHandle.myUserId();
        int callingUid = Binder.getCallingUid();
        return UserHandle.getAppId(callingUid) < 10000 ? iMyUserId : UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    }

    private static UserHandle getIncomingUserHandle() {
        return UserHandle.of(getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollection(Context context) {
        return getApplicationCollectionAsUser(context, getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollectionAsUser(Context context, int i) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getApplicationCollectionInternal(context, i);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private static Collection<SmsApplicationData> getApplicationCollectionInternal(Context context, int i) {
        SmsApplicationData smsApplicationData;
        SmsApplicationData smsApplicationData2;
        SmsApplicationData smsApplicationData3;
        SmsApplicationData smsApplicationData4;
        SmsApplicationData smsApplicationData5;
        SmsApplicationData smsApplicationData6;
        PackageManager packageManager = context.getPackageManager();
        UserHandle userHandleOf = UserHandle.of(i);
        List<ResolveInfo> listQueryBroadcastReceiversAsUser = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.SMS_DELIVER_ACTION), 786432, userHandleOf);
        HashMap map = new HashMap();
        Iterator<ResolveInfo> it = listQueryBroadcastReceiversAsUser.iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && Manifest.permission.BROADCAST_SMS.equals(activityInfo.permission)) {
                String str = activityInfo.packageName;
                if (!map.containsKey(str)) {
                    try {
                        SmsApplicationData smsApplicationData7 = new SmsApplicationData(str, activityInfo.applicationInfo.uid);
                        smsApplicationData7.mSmsReceiverClass = activityInfo.name;
                        map.put(str, smsApplicationData7);
                    } catch (Exception unused) {
                        Rlog.e(LOG_TAG, "Error getting applicationName");
                    }
                }
            }
        }
        Intent intent = new Intent(Telephony.Sms.Intents.WAP_PUSH_DELIVER_ACTION);
        intent.setDataAndType(null, ContentType.MMS_MESSAGE);
        Iterator<ResolveInfo> it2 = packageManager.queryBroadcastReceiversAsUser(intent, 786432, userHandleOf).iterator();
        while (it2.hasNext()) {
            ActivityInfo activityInfo2 = it2.next().activityInfo;
            if (activityInfo2 != null && Manifest.permission.BROADCAST_WAP_PUSH.equals(activityInfo2.permission) && (smsApplicationData6 = (SmsApplicationData) map.get(activityInfo2.packageName)) != null) {
                smsApplicationData6.mMmsReceiverClass = activityInfo2.name;
            }
        }
        Iterator<ResolveInfo> it3 = packageManager.queryIntentServicesAsUser(new Intent(TelephonyManager.ACTION_RESPOND_VIA_MESSAGE, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, UserHandle.of(i)).iterator();
        while (it3.hasNext()) {
            ServiceInfo serviceInfo = it3.next().serviceInfo;
            if (serviceInfo != null && Manifest.permission.SEND_RESPOND_VIA_MESSAGE.equals(serviceInfo.permission) && (smsApplicationData5 = (SmsApplicationData) map.get(serviceInfo.packageName)) != null) {
                smsApplicationData5.mRespondViaMessageClass = serviceInfo.name;
            }
        }
        Iterator<ResolveInfo> it4 = packageManager.queryIntentActivitiesAsUser(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, userHandleOf).iterator();
        while (it4.hasNext()) {
            ActivityInfo activityInfo3 = it4.next().activityInfo;
            if (activityInfo3 != null && (smsApplicationData4 = (SmsApplicationData) map.get(activityInfo3.packageName)) != null) {
                smsApplicationData4.mSendToClass = activityInfo3.name;
            }
        }
        Iterator<ResolveInfo> it5 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED), 786432, userHandleOf).iterator();
        while (it5.hasNext()) {
            ActivityInfo activityInfo4 = it5.next().activityInfo;
            if (activityInfo4 != null && (smsApplicationData3 = (SmsApplicationData) map.get(activityInfo4.packageName)) != null) {
                smsApplicationData3.mSmsAppChangedReceiverClass = activityInfo4.name;
            }
        }
        Iterator<ResolveInfo> it6 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_EXTERNAL_PROVIDER_CHANGE), 786432, userHandleOf).iterator();
        while (it6.hasNext()) {
            ActivityInfo activityInfo5 = it6.next().activityInfo;
            if (activityInfo5 != null && (smsApplicationData2 = (SmsApplicationData) map.get(activityInfo5.packageName)) != null) {
                smsApplicationData2.mProviderChangedReceiverClass = activityInfo5.name;
            }
        }
        Iterator<ResolveInfo> it7 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.SIM_FULL_ACTION), 786432, userHandleOf).iterator();
        while (it7.hasNext()) {
            ActivityInfo activityInfo6 = it7.next().activityInfo;
            if (activityInfo6 != null && (smsApplicationData = (SmsApplicationData) map.get(activityInfo6.packageName)) != null) {
                smsApplicationData.mSimFullReceiverClass = activityInfo6.name;
            }
        }
        Iterator<ResolveInfo> it8 = listQueryBroadcastReceiversAsUser.iterator();
        while (it8.hasNext()) {
            ActivityInfo activityInfo7 = it8.next().activityInfo;
            if (activityInfo7 != null) {
                String str2 = activityInfo7.packageName;
                SmsApplicationData smsApplicationData8 = (SmsApplicationData) map.get(str2);
                if (smsApplicationData8 != null && !smsApplicationData8.isComplete()) {
                    map.remove(str2);
                }
            }
        }
        return map.values();
    }

    public static SmsApplicationData getApplicationForPackage(Collection<SmsApplicationData> collection, String str) {
        if (str == null) {
            return null;
        }
        for (SmsApplicationData smsApplicationData : collection) {
            if (smsApplicationData.mPackageName.contentEquals(str)) {
                return smsApplicationData;
            }
        }
        return null;
    }

    private static SmsApplicationData getApplication(Context context, boolean z, int i) throws ExecutionException, InterruptedException, Resources.NotFoundException, TimeoutException {
        String string;
        if (context == null) {
            Rlog.e(LOG_TAG, "getApplication: context is null!");
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        RoleManager roleManager = (RoleManager) context.getSystemService(Context.ROLE_SERVICE);
        if (!telephonyManager.isSmsCapable() && (roleManager == null || !roleManager.isRoleAvailable("android.app.role.SMS"))) {
            if (("ATT".equals(SALES_CODE) || getEnableSecSms(context)) && z) {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                PackageManager packageManager = context.getPackageManager();
                String string2 = context.getResources().getString(17039427);
                assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, "com.android.phone", true);
                assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, string2, true);
                assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, MMS_SERVICE_PACKAGE_NAME, true);
                assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, TELEPHONY_PROVIDER_PACKAGE_NAME, true);
                assignWriteSmsPermissionToSystemUid(appOpsManager, 1001);
                assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, NEW_SEC_SMS_PACKAGE_NAME, true);
                Rlog.d(LOG_TAG, "Assign writesms permission to special system apps for specific tablets");
            }
            return null;
        }
        Collection<SmsApplicationData> applicationCollectionInternal = getApplicationCollectionInternal(context, i);
        String defaultSmsPackage = getDefaultSmsPackage(context, i);
        SmsApplicationData applicationForPackage = defaultSmsPackage != null ? getApplicationForPackage(applicationCollectionInternal, defaultSmsPackage) : null;
        if (z && applicationForPackage == null) {
            Resources resources = context.getResources();
            String str = SemSystemProperties.get(DEFAULT_MSGAPP_SYSTEMPROPERTY, SM_TAG);
            if (TextUtils.isEmpty(str)) {
                Log.i(LOG_TAG, "there is no configedPackage, getApplication from default_sms_application");
                string = resources.getString(R.string.default_sms_application);
            } else if (str.equals("AM")) {
                Log.i(LOG_TAG, "AM is configedPackage, getApplication from configedSmsPackageName");
                string = GOOGLE_MESSAGE_PACKAGE;
            } else {
                Log.i(LOG_TAG, "SM is configedPackage, getApplication from default_sms_application");
                string = resources.getString(R.string.default_sms_application);
            }
            SmsApplicationData applicationForPackage2 = getApplicationForPackage(applicationCollectionInternal, string);
            if (applicationForPackage2 == null && applicationCollectionInternal.size() != 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= applicationCollectionInternal.size()) {
                        break;
                    }
                    if (NEW_SEC_SMS_PACKAGE_NAME.equals(((SmsApplicationData) applicationCollectionInternal.toArray()[i2]).mPackageName)) {
                        applicationForPackage2 = (SmsApplicationData) applicationCollectionInternal.toArray()[i2];
                        break;
                    }
                    i2++;
                }
                if (applicationForPackage2 == null) {
                    applicationForPackage2 = (SmsApplicationData) applicationCollectionInternal.toArray()[0];
                }
            }
            applicationForPackage = applicationForPackage2;
            if (applicationForPackage != null) {
                setDefaultApplicationInternal(applicationForPackage.mPackageName, context, i);
            }
        }
        if (applicationForPackage == null) {
            return applicationForPackage;
        }
        SmsApplicationData smsApplicationData = (!(z || applicationForPackage.mUid == Process.myUid()) || tryFixExclusiveSmsAppops(context, applicationForPackage, z)) ? applicationForPackage : null;
        if (smsApplicationData != null && z) {
            grantPermissionsToSystemApps(context);
        }
        return smsApplicationData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getDefaultSmsPackage(Context context, int i) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        if (roleManager == null) {
            return "";
        }
        return roleManager.getSmsRoleHolder(i);
    }

    public static void grantPermissionsToSystemApps(Context context) throws Resources.NotFoundException {
        PackageManager packageManager = context.getPackageManager();
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        String string = context.getResources().getString(17039427);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, "com.android.phone", true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, string, false);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, MMS_SERVICE_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, TELEPHONY_PROVIDER_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, CellBroadcastUtils.getDefaultCellBroadcastReceiverPackageName(context), false);
        for (String str : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            if (appOpsManager.unsafeCheckOp(str, 1001, "com.android.phone") != 0) {
                appOpsManager.setUidMode(str, 1001, 0);
            }
        }
    }

    private static boolean tryFixExclusiveSmsAppops(Context context, SmsApplicationData smsApplicationData, boolean z) {
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        for (String str : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            if (appOpsManager.unsafeCheckOp(str, smsApplicationData.mUid, smsApplicationData.mPackageName) != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(smsApplicationData.mPackageName);
                sb.append(" lost ");
                sb.append(str);
                sb.append(": ");
                sb.append(z ? " (fixing)" : " (no permission to fix)");
                Log.e(LOG_TAG, sb.toString());
                if (!z) {
                    return false;
                }
                appOpsManager.setUidMode(str, smsApplicationData.mUid, 0);
            }
        }
        return true;
    }

    public static void setDefaultApplication(String str, Context context) {
        setDefaultApplicationAsUser(str, context, getIncomingUserId());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    public static void setDefaultApplicationAsUser(String str, Context context, int i) {
        ?? Equals;
        if (context == null) {
            Rlog.e(LOG_TAG, "context in DefaultApplication is null");
            return;
        }
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.sec.knox.provider2/ApplicationPolicy"), null, SecContentProviderURI.APPLICATIONPOLICY_DEFAULTSMSAPP_METHOD, new String[]{str, Integer.toString(userId)}, null);
        if (cursorQuery != null) {
            try {
                cursorQuery.moveToFirst();
            } catch (Exception unused) {
            } finally {
                cursorQuery.close();
            }
        } else {
            Equals = -1;
        }
        if (Equals == 0) {
            Rlog.e(LOG_TAG, "Block setDefaultApplication by admin");
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        RoleManager roleManager = (RoleManager) context.getSystemService(Context.ROLE_SERVICE);
        if (telephonyManager.isSmsCapable() || (roleManager != null && roleManager.isRoleAvailable("android.app.role.SMS"))) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                setDefaultApplicationInternal(str, context, i);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    private static void setDefaultApplicationInternal(String str, Context context, int i) throws ExecutionException, InterruptedException, Resources.NotFoundException, TimeoutException {
        UserHandle userHandleOf = UserHandle.of(i);
        String defaultSmsPackage = getDefaultSmsPackage(context, i);
        if (str == null || defaultSmsPackage == null || !str.equals(defaultSmsPackage)) {
            PackageManager packageManager = context.createContextAsUser(userHandleOf, 0).getPackageManager();
            Collection<SmsApplicationData> applicationCollectionInternal = getApplicationCollectionInternal(context, i);
            if (defaultSmsPackage != null) {
                getApplicationForPackage(applicationCollectionInternal, defaultSmsPackage);
            }
            SmsApplicationData applicationForPackage = getApplicationForPackage(applicationCollectionInternal, str);
            if (applicationForPackage != null) {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (defaultSmsPackage != null) {
                    try {
                        setExclusiveAppops(defaultSmsPackage, appOpsManager, packageManager.getPackageInfo(defaultSmsPackage, 0).applicationInfo.uid, 3);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w(LOG_TAG, "Old SMS package not found: " + defaultSmsPackage);
                    }
                }
                Rlog.e(LOG_TAG, "update the default app to : " + applicationForPackage.mPackageName + " oldPackageName: " + defaultSmsPackage);
                sendBroadcast_SMS_BIG_DATA_INFO(context, defaultSmsPackage, applicationForPackage.mPackageName, null);
                final CompletableFuture completableFuture = new CompletableFuture();
                ((RoleManager) context.getSystemService(RoleManager.class)).addRoleHolderAsUser("android.app.role.SMS", applicationForPackage.mPackageName, 0, UserHandle.of(i), AsyncTask.THREAD_POOL_EXECUTOR, new Consumer() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SmsApplication.lambda$setDefaultApplicationInternal$0(completableFuture, (Boolean) obj);
                    }
                });
                try {
                    completableFuture.get(5L, TimeUnit.SECONDS);
                    grantPermissionsToSystemApps(context);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    Log.e(LOG_TAG, "Exception while adding sms role holder " + applicationForPackage, e);
                }
            }
        }
    }

    static /* synthetic */ void lambda$setDefaultApplicationInternal$0(CompletableFuture completableFuture, Boolean bool) {
        if (bool.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new RuntimeException());
        }
    }

    public static void sendBroadcast_SMS_BIG_DATA_INFO(Context context, String str, String str2, SemDMACdata semDMACdata) {
        try {
            Intent intent = new Intent("com.samsung.intent.action.SMS_BIG_DATA_INFO");
            intent.putExtra("feature", "sdac");
            intent.putExtra("nsda", str2);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("osda", str);
            }
            if (semDMACdata != null) {
                intent.putExtra("dmac", semDMACdata);
            }
            context.sendBroadcast(intent);
        } catch (IllegalStateException unused) {
            Log.w(LOG_TAG, "IllegalStateException : intent should be broadcast after boot completed");
        } catch (SecurityException unused2) {
            Log.w(LOG_TAG, "Permission Denial: com.samsung.intent.action.SMS_BIG_DATA_INFO");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void broadcastSmsAppChange(Context context, UserHandle userHandle, String str, String str2) {
        Collection<SmsApplicationData> applicationCollection = getApplicationCollection(context);
        broadcastSmsAppChange(context, userHandle, getApplicationForPackage(applicationCollection, str), getApplicationForPackage(applicationCollection, str2));
    }

    private static void broadcastSmsAppChange(Context context, UserHandle userHandle, SmsApplicationData smsApplicationData, SmsApplicationData smsApplicationData2) {
        if (smsApplicationData != null && smsApplicationData.mSmsAppChangedReceiverClass != null) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            intent.setComponent(new ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSmsAppChangedReceiverClass));
            intent.putExtra(Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, false);
            context.sendBroadcastAsUser(intent, userHandle);
        }
        if (smsApplicationData2 != null && smsApplicationData2.mSmsAppChangedReceiverClass != null) {
            Intent intent2 = new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            intent2.setComponent(new ComponentName(smsApplicationData2.mPackageName, smsApplicationData2.mSmsAppChangedReceiverClass));
            intent2.putExtra(Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, true);
            context.sendBroadcastAsUser(intent2, userHandle);
        }
        context.sendBroadcastAsUser(new Intent(ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL), userHandle, "android.permission.MONITOR_DEFAULT_SMS_PACKAGE");
    }

    private static void assignExclusiveSmsPermissionsToSystemApp(Context context, PackageManager packageManager, AppOpsManager appOpsManager, String str, boolean z) {
        if (str == null) {
            return;
        }
        if (z && packageManager.checkSignatures(context.getPackageName(), str) != 0) {
            Log.e(LOG_TAG, str + " does not have system signature");
            return;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (appOpsManager.unsafeCheckOp(AppOpsManager.OPSTR_WRITE_SMS, packageInfo.applicationInfo.uid, str) != 0) {
                Log.w(LOG_TAG, str + " does not have OP_WRITE_SMS:  (fixing)");
                setExclusiveAppops(str, appOpsManager, packageInfo.applicationInfo.uid, 0);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(LOG_TAG, "Package not found: " + str);
        }
    }

    private static void setExclusiveAppops(String str, AppOpsManager appOpsManager, int i, int i2) {
        for (String str2 : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            appOpsManager.setUidMode(str2, i, i2);
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
        public void onPackageModified(String str) {
            onPackageChanged();
        }

        private void onPackageChanged() {
            int identifier;
            try {
                identifier = getSendingUser().getIdentifier();
            } catch (NullPointerException unused) {
                identifier = UserHandle.SYSTEM.getIdentifier();
            }
            final Context contextCreatePackageContextAsUser = this.mContext;
            if (identifier != UserHandle.SYSTEM.getIdentifier()) {
                try {
                    Context context = this.mContext;
                    contextCreatePackageContextAsUser = context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(identifier));
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            threadPool.execute(new Runnable(this) { // from class: com.android.internal.telephony.SmsApplication.SmsPackageMonitor.1
                @Override // java.lang.Runnable
                public void run() {
                    Rlog.d(SmsApplication.LOG_TAG, "onPackageChanged: run");
                    PackageManager packageManager = contextCreatePackageContextAsUser.getPackageManager();
                    ComponentName defaultSendToApplication = SmsApplication.getDefaultSendToApplication(contextCreatePackageContextAsUser, true);
                    if (defaultSendToApplication != null) {
                        SmsApplication.configurePreferredActivity(packageManager, defaultSendToApplication);
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
            List<UserHandle> userHandles = ((UserManager) context.getSystemService(UserManager.class)).getUserHandles(true);
            int size = userHandles.size();
            for (int i = 0; i < size; i++) {
                UserHandle userHandle = userHandles.get(i);
                this.mSmsPackageNames.put(userHandle.getIdentifier(), getSmsPackageName(userHandle));
            }
            this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), this, UserHandle.ALL);
        }

        public void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if (Objects.equals(str, "android.app.role.SMS")) {
                int identifier = userHandle.getIdentifier();
                String smsPackageName = getSmsPackageName(userHandle);
                SmsApplication.broadcastSmsAppChange(this.mContext, userHandle, this.mSmsPackageNames.get(identifier), smsPackageName);
                this.mSmsPackageNames.put(identifier, smsPackageName);
            }
        }

        private String getSmsPackageName(UserHandle userHandle) {
            List roleHoldersAsUser = this.mRoleManager.getRoleHoldersAsUser("android.app.role.SMS", userHandle);
            if (roleHoldersAsUser.isEmpty()) {
                return null;
            }
            return (String) roleHoldersAsUser.get(0);
        }
    }

    public static void initSmsPackageMonitor(Context context) {
        SmsPackageMonitor smsPackageMonitor = new SmsPackageMonitor(context);
        sSmsPackageMonitor = smsPackageMonitor;
        smsPackageMonitor.register(context, context.getMainLooper(), UserHandle.ALL);
        sSmsRoleListener = new SmsRoleListener(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void configurePreferredActivity(PackageManager packageManager, ComponentName componentName) {
        replacePreferredActivity(packageManager, componentName, "sms");
        replacePreferredActivity(packageManager, componentName, SCHEME_SMSTO);
        replacePreferredActivity(packageManager, componentName, "mms");
        replacePreferredActivity(packageManager, componentName, SCHEME_MMSTO);
    }

    private static void replacePreferredActivity(PackageManager packageManager, ComponentName componentName, String str) {
        List<ComponentName> list = (List) packageManager.queryIntentActivities(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(str, "", null)), Sensor.SEM_TYPE_HALLIC).stream().map(new Function() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SmsApplication.lambda$replacePreferredActivity$1((ResolveInfo) obj);
            }
        }).collect(Collectors.toList());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SENDTO);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFilter.addDataScheme(str);
        packageManager.replacePreferredActivity(intentFilter, 2129920, list, componentName);
    }

    static /* synthetic */ ComponentName lambda$replacePreferredActivity$1(ResolveInfo resolveInfo) {
        return new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
    }

    public static SmsApplicationData getSmsApplicationData(String str, Context context) {
        return getApplicationForPackage(getApplicationCollection(context), str);
    }

    public static ComponentName getDefaultSmsApplication(Context context, boolean z) {
        return getDefaultSmsApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultSmsApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mSmsReceiverClass) : null;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static ComponentName getDefaultMmsApplication(Context context, boolean z) {
        return getDefaultMmsApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultMmsApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mMmsReceiverClass) : null;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static ComponentName getDefaultRespondViaMessageApplication(Context context, boolean z) {
        return getDefaultRespondViaMessageApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultRespondViaMessageApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mRespondViaMessageClass) : null;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static ComponentName getDefaultSendToApplication(Context context, boolean z) {
        int incomingUserId = getIncomingUserId();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, incomingUserId);
            return application != null ? new ComponentName(application.mPackageName, application.mSendToClass) : null;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplication(Context context, boolean z) {
        return getDefaultExternalTelephonyProviderChangedApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return (application == null || application.mProviderChangedReceiverClass == null) ? null : new ComponentName(application.mPackageName, application.mProviderChangedReceiverClass);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static ComponentName getDefaultSimFullApplication(Context context, boolean z) {
        return getDefaultSimFullApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultSimFullApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return (application == null || application.mSimFullReceiverClass == null) ? null : new ComponentName(application.mPackageName, application.mSimFullReceiverClass);
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public static boolean shouldWriteMessageForPackage(String str, Context context) {
        return !shouldWriteMessageForPackageAsUser(str, context, getIncomingUserHandle());
    }

    public static boolean shouldWriteMessageForPackageAsUser(String str, Context context, UserHandle userHandle) {
        return !isDefaultSmsApplicationAsUser(context, str, userHandle);
    }

    public static boolean isDefaultSmsApplication(Context context, String str) {
        return isDefaultSmsApplicationAsUser(context, str, getIncomingUserHandle());
    }

    public static boolean isDefaultSmsApplicationAsUser(Context context, String str, UserHandle userHandle) throws Resources.NotFoundException {
        if (str == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        ComponentName defaultSmsApplicationAsUser = getDefaultSmsApplicationAsUser(context, false, userHandle);
        String packageName = defaultSmsApplicationAsUser != null ? defaultSmsApplicationAsUser.getPackageName() : null;
        String string = context.getResources().getString(17039427);
        if ((packageName != null && packageName.equals(str)) || string.equals(str) || str.equals(NSRI_PACKAGE_NAME)) {
            return true;
        }
        if (getEnableSecSms(context) || !str.equals(COREAPPS_PACKAGE_NAME)) {
            return (packageName != null && packageName.equals(str)) || isShouldNotWriteMessage(context, str) || !(!SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableItsOn") || Binder.getCallingUid() != 4002);
        }
        Rlog.d(LOG_TAG, "shouldWriteMessageForPackage is true for none SECSMS app model.");
        return false;
    }

    public static boolean isDefaultMmsApplication(Context context, String str) {
        return isDefaultMmsApplicationAsUser(context, str, getIncomingUserHandle());
    }

    public static boolean isDefaultMmsApplicationAsUser(Context context, String str, UserHandle userHandle) {
        String packageName;
        if (str == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        ComponentName defaultMmsApplicationAsUser = getDefaultMmsApplicationAsUser(context, false, userHandle);
        if (defaultMmsApplicationAsUser == null || (packageName = defaultMmsApplicationAsUser.getPackageName()) == null) {
            return false;
        }
        return packageName.equals(str) || context.getResources().getString(17039427).equals(str);
    }

    private static void assignWriteSmsPermissionToSystemUid(AppOpsManager appOpsManager, int i) {
        appOpsManager.setUidMode(15, i, 0);
    }

    private static String getDefaultSmsApplicationPackageName(Context context) {
        ComponentName defaultSmsApplication = getDefaultSmsApplication(context, false);
        if (defaultSmsApplication != null) {
            return defaultSmsApplication.getPackageName();
        }
        return null;
    }

    public static boolean isShouldNotWriteMessage(Context context, String str) {
        boolean z;
        String str2 = SemSystemProperties.get("ro.csc.countryiso_code");
        String defaultSmsApplicationPackageName = getDefaultSmsApplicationPackageName(context);
        if ((context.getPackageManager().getApplicationInfo(GOOGLE_MESSAGE_PACKAGE, 0).flags & 1) != 0) {
            Rlog.i(LOG_TAG, "AM is preloaded");
            z = true;
        } else {
            z = false;
        }
        if (defaultSmsApplicationPackageName != null && !defaultSmsApplicationPackageName.equals(NEW_SEC_SMS_PACKAGE_NAME) && z && NEW_SEC_SMS_PACKAGE_NAME.equals(str) && !"KR".equalsIgnoreCase(str2) && !"KOREA".equalsIgnoreCase(str2)) {
            return false;
        }
        if (sPackageNamePattern == null) {
            sPackageNamePattern = context.getResources().getStringArray(R.array.shouldNotWriteMessage);
        }
        for (String str3 : sPackageNamePattern) {
            if (str.equals(str3)) {
                Rlog.d(LOG_TAG, str + " is matched");
                return true;
            }
        }
        if (isVzwAuthorizedApp(context, str)) {
            return true;
        }
        Rlog.d(LOG_TAG, "No PackageName Pattern : " + str);
        return false;
    }

    private static boolean isVzwAuthorizedApp(Context context, String str) {
        return VZWAVSLibrary.isPackageAuthorized(context, str, "VZWSMS");
    }

    public static void setPendingDeliveryIntent(PendingIntent pendingIntent) {
        mPendingDeliveryIntent = pendingIntent;
    }

    public static void initPendingDeliveryIntent() {
        mPendingDeliveryIntent = null;
    }

    public static PendingIntent getPendingDeliveryIntent() {
        return mPendingDeliveryIntent;
    }

    public static boolean getEnableSecSms(Context context) {
        boolean z;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.android.mms", 128);
            z = true;
        } catch (PackageManager.NameNotFoundException unused) {
            z = false;
        }
        try {
            packageManager.getPackageInfo(NEW_SEC_SMS_PACKAGE_NAME, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused2) {
            return z;
        }
    }

    private static void defaultMessageAppConfigInfoDump(Context context, String str) {
        Intent intent = new Intent(DEFAULT_MSG_APP_INFO_LOGGING);
        intent.putExtra("defaultMsgAppConfigInfo", str);
        intent.setPackage("com.android.phone");
        context.sendBroadcast(intent);
    }

    public static void setDefaultMessageAppConfig(Context context) throws IOException {
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
            boolean zIsTssDevice = isTssDevice();
            boolean z = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            Log.i(SmsApplication.LOG_TAG, "isOperatorFixed()- isSupportTrueSingleSKU : " + zIsTssDevice + " isTSSActivated : " + z);
            if (zIsTssDevice) {
                return z;
            }
            return true;
        }

        private String getActiveOperatorIdByCountryiso(String str) throws IOException {
            String str2 = "NONE";
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(SmsApplication.COUNTRYISO_OPENBUYER_CONFIG_XML));
                try {
                    try {
                        XmlPullParser parser = getParser(fileInputStream);
                        if (parser == null) {
                            Log.e(SmsApplication.LOG_TAG, "XmlPullParser is null");
                        } else {
                            String strTrim = "NONE";
                            boolean z = false;
                            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                                if (eventType != 2) {
                                    if (eventType == 4 && z) {
                                        try {
                                            strTrim = parser.getText().trim();
                                            z = false;
                                        } catch (IOException | XmlPullParserException e) {
                                            e = e;
                                            str2 = strTrim;
                                            Log.e(SmsApplication.LOG_TAG, "Error while parsing", e);
                                            closeFileInputStream(fileInputStream);
                                            return str2;
                                        } catch (Throwable unused) {
                                            str2 = strTrim;
                                            closeFileInputStream(fileInputStream);
                                            return str2;
                                        }
                                    }
                                } else if (str.equals(parser.getName())) {
                                    z = true;
                                }
                                if (!"NONE".equals(strTrim)) {
                                    break;
                                }
                            }
                            str2 = strTrim;
                        }
                        Log.d(SmsApplication.LOG_TAG, "xml parsing result- activeOperatorId: " + str2);
                        closeFileInputStream(fileInputStream);
                        return str2;
                    } catch (Throwable unused2) {
                    }
                } catch (IOException | XmlPullParserException e2) {
                    e = e2;
                }
            } catch (FileNotFoundException e3) {
                Log.e(SmsApplication.LOG_TAG, e3.getClass().getSimpleName() + "!! " + e3.getMessage());
                return "NONE";
            }
        }

        private boolean isWifiSkipCarrier() {
            String str = SemSystemProperties.get("ro.boot.carrierid", null);
            List listAsList = Arrays.asList("XSG", "MID", "ILO", "XFA", "AFR", "M10", "M06", "M05");
            if (TextUtils.isEmpty(str) || !listAsList.contains(str)) {
                return false;
            }
            Log.d(SmsApplication.LOG_TAG, "isWifiSkipCarrier return true");
            return true;
        }

        private String getActiveOperatorId() throws IOException {
            boolean zIsTssDevice = isTssDevice();
            boolean z = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            String str = "NONE";
            if (!zIsTssDevice) {
                str = SystemProperties.get("ro.csc.sales_code", "NONE");
            } else if (z) {
                String activeOperatorIdByCountryiso = SemSystemProperties.get("ro.boot.activatedid", "NONE");
                if ("EUX".equals(activeOperatorIdByCountryiso) || "EUY".equals(activeOperatorIdByCountryiso)) {
                    String str2 = SemSystemProperties.get("ro.csc.countryiso_code", "NONE");
                    Log.i(SmsApplication.LOG_TAG, "countryiso : " + str2);
                    if (!"NONE".equals(str2)) {
                        activeOperatorIdByCountryiso = getActiveOperatorIdByCountryiso(str2);
                        str = activeOperatorIdByCountryiso;
                    }
                } else {
                    str = activeOperatorIdByCountryiso;
                }
            } else if (isWifiSkipCarrier()) {
                str = SystemProperties.get("ro.csc.sales_code", "NONE");
            }
            SmsApplication.mLogStb.append(" isSupportTrueSingleSKU : ").append(zIsTssDevice).append(", isTSSActivated : ").append(z).append(", isWifiSkipCarrier : ").append(isWifiSkipCarrier()).append(", activeOperatorId : ").append(str);
            setDMACdataTssInfo(zIsTssDevice, z, str);
            return str;
        }

        void setDMACdataTssInfo(boolean z, boolean z2, String str) {
            if (!z) {
                SmsApplication.sDMACdata.setTssActivated("NotSupported");
            } else if (z2) {
                SmsApplication.sDMACdata.setTssActivated("Activated");
            } else {
                SmsApplication.sDMACdata.setTssActivated("Deactivated");
            }
            SmsApplication.sDMACdata.setCarrierActivatedId(str);
        }

        void setDMACdataConfigInfo(boolean z, String str) {
            if (z) {
                SmsApplication.sDMACdata.setIsUnLockedPhone("True");
            } else {
                SmsApplication.sDMACdata.setIsUnLockedPhone("False");
            }
            SmsApplication.sDMACdata.setMccmnc(str);
        }

        private int findLoadedSimSlot() {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            int phoneCount = telephonyManager != null ? telephonyManager.getPhoneCount() : 0;
            for (int i = 0; i < phoneCount; i++) {
                if (TelephonyManager.getSimStateForSlotIndex(i) == 10) {
                    return i;
                }
            }
            return -1;
        }

        private static XmlPullParser getParser(FileInputStream fileInputStream) throws XmlPullParserException, IOException {
            if (fileInputStream == null) {
                Log.d(SmsApplication.LOG_TAG, "no file");
                return null;
            }
            try {
                XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
                xmlPullParserFactoryNewInstance.setNamespaceAware(true);
                XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
                xmlPullParserNewPullParser.setInput(fileInputStream, null);
                return xmlPullParserNewPullParser;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                closeFileInputStream(fileInputStream);
                return null;
            }
        }

        private static void closeFileInputStream(FileInputStream fileInputStream) throws IOException {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean isAMInstalled() {
            try {
                this.mContext.getPackageManager().getApplicationInfo(SmsApplication.GOOGLE_MESSAGE_PACKAGE, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        private boolean isSMInstalled() {
            try {
                this.mContext.getPackageManager().getApplicationInfo(SmsApplication.NEW_SEC_SMS_PACKAGE_NAME, 0);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                return false;
            }
        }

        private boolean isPackageEnabled(Context context, String str) {
            try {
                int applicationEnabledSetting = context.getPackageManager().getApplicationEnabledSetting(str);
                return (2 == applicationEnabledSetting || 3 == applicationEnabledSetting) ? false : true;
            } catch (IllegalArgumentException unused) {
                return false;
            }
        }

        private boolean hasPackage(Context context, String str) {
            try {
                context.getPackageManager().getApplicationInfo(str, 128);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d(SmsApplication.LOG_TAG, "Package not found : " + str);
                return false;
            }
        }

        private String getMessagePackageName(Context context) {
            String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME");
            return !hasPackage(context, string) ? "com.android.mms" : string;
        }

        private String getSalesCode() {
            String str = "";
            try {
                str = SystemProperties.get("ro.csc.sales_code", "NONE");
                return TextUtils.isEmpty(str) ? SystemProperties.get("ril.sales_code", "NONE") : str;
            } catch (Exception unused) {
                Log.d(SmsApplication.LOG_TAG, "readSalesCode failed");
                return str;
            }
        }

        public void setDefaultMsgApp() throws IOException {
            String activeOperatorId = getActiveOperatorId();
            Log.i(SmsApplication.LOG_TAG, "setDefaultMsgAppFromConfig");
            SmsApplication.mLogStb.append("setDefaultMsgApp Config Info =");
            String defaultSmsPackage = SmsApplication.getDefaultSmsPackage(this.mContext, SmsApplication.getIncomingUserId());
            boolean z = "SBM".equals(activeOperatorId) || "DCM".equals(activeOperatorId) || "KDI".equals(activeOperatorId);
            if (!isAMInstalled()) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG);
                SmsApplication.sDMACdata.setPreInstalledMsgAppError("NoAM");
            } else if (!z && !isSMInstalled()) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, "AM");
                SmsApplication.sDMACdata.setPreInstalledMsgAppError("NoSM");
                Log.i(SmsApplication.LOG_TAG, "SM is not installed ");
            } else if ("NONE".equals(activeOperatorId) || z) {
                SemSystemProperties.set(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY, SmsApplication.SM_TAG);
            } else {
                boolean defaultMsgApp_File = setDefaultMsgApp_File(activeOperatorId);
                SmsApplication.mLogStb.append(", mChangeToAM : ").append(defaultMsgApp_File);
                if (defaultMsgApp_File) {
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
            SmsApplication.sendBroadcast_SMS_BIG_DATA_INFO(this.mContext, defaultSmsPackage, SemSystemProperties.get(SmsApplication.DEFAULT_MSGAPP_SYSTEMPROPERTY), SmsApplication.sDMACdata);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0061 A[PHI: r1
          0x0061: PHI (r1v9 java.lang.String) = (r1v8 java.lang.String), (r1v10 java.lang.String) binds: [B:27:0x006b, B:24:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void setSMorOperatorMessageApp() {
            String string;
            String messagePackageName;
            String str;
            String salesCode = getSalesCode();
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager == null || !telephonyManager.isSmsCapable()) {
                return;
            }
            string = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigDefSmsApp");
            messagePackageName = getMessagePackageName(this.mContext);
            salesCode.hashCode();
            switch (salesCode) {
                case "DCM":
                    if (isPackageEnabled(this.mContext, SmsApplication.DOCOMO_MESSAGES) && !string.contains("samsung")) {
                        messagePackageName = SmsApplication.DOCOMO_MESSAGES;
                        break;
                    }
                    break;
                case "KDI":
                    Context context = this.mContext;
                    str = SmsApplication.KDDI_MESSAGES;
                    if (isPackageEnabled(context, SmsApplication.KDDI_MESSAGES)) {
                        messagePackageName = str;
                        break;
                    }
                    break;
                case "SBM":
                    Context context2 = this.mContext;
                    str = SmsApplication.SOFTBANK_MESSAGES;
                    if (isPackageEnabled(context2, SmsApplication.SOFTBANK_MESSAGES)) {
                    }
                    break;
            }
            if (isPackageEnabled(this.mContext, messagePackageName)) {
                Log.i(SmsApplication.LOG_TAG, "setDefaultApplication messageAppName : " + messagePackageName);
                SmsApplication.setDefaultApplication(messagePackageName, this.mContext);
            }
        }

        private String updateChangeByOs(String str, String str2, boolean z) {
            if (!TextUtils.isEmpty(str)) {
                String[] strArrSplit = str.split("\\|");
                for (String str3 : strArrSplit) {
                    if (z) {
                        if (str3.contains(str2)) {
                            return str3.replace(str2 + Session.SESSION_SEPARATION_CHAR_CHILD, "");
                        }
                    } else if (str3.contains("SM_")) {
                        return str3.replace("SM_", "");
                    }
                }
            }
            return "";
        }

        private boolean isNeedToModifyFirstApi() {
            String str = SemSystemProperties.get("ro.product.model", LsConstants.TAG_UNKNOWN);
            if (!Arrays.asList("SM-A155F", "SM-A155M", "SM-G556B", "SM-A156E", "SM-A156B", "SM-A156M", "SM-A1560", "SM-A256B", "SM-A256E", "SM-A2560", "SM-A256U", "SM-A256U1", "SM-A256N", "SM-X306B", "SM-X300", "SM-X306N", "SM-X308U", "SM-X308B").contains(str)) {
                return false;
            }
            Log.d(SmsApplication.LOG_TAG, "isNeedToModifyFirstApi return true : " + str);
            return true;
        }

        private boolean setDefaultMsgApp_File(String str) throws IOException {
            String simOperatorNumericForPhone;
            String strTrim;
            String strTrim2;
            boolean z;
            int iFindLoadedSimSlot = findLoadedSimSlot();
            boolean z2 = true;
            boolean z3 = iFindLoadedSimSlot >= 0;
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            String str2 = "FIRST_API_LEVEL_" + SemSystemProperties.getInt("ro.product.first_api_level", 0);
            if ("SM-A136B".equals(SemSystemProperties.get("ro.product.model", LsConstants.TAG_UNKNOWN))) {
                Log.i(SmsApplication.LOG_TAG, "in case of SM-A136B, modify the first api level from 30 to 31");
                str2 = "FIRST_API_LEVEL_31";
            }
            if (isNeedToModifyFirstApi()) {
                Log.i(SmsApplication.LOG_TAG, "It is released before S24, so modify the first api level from 34 to 33");
                str2 = "FIRST_API_LEVEL_33";
            }
            String strTrim3 = "";
            if (!z3 || telephonyManager == null) {
                simOperatorNumericForPhone = "";
            } else {
                simOperatorNumericForPhone = telephonyManager.getSimOperatorNumericForPhone(iFindLoadedSimSlot);
            }
            Log.i(SmsApplication.LOG_TAG, "activeOperatorId: " + str + " phoneId: " + iFindLoadedSimSlot + " isSimLoaded: " + z3 + " mccmnc: " + simOperatorNumericForPhone + " firstApiLevel: " + str2);
            SmsApplication.mLogStb.append(", phoneId : ").append(iFindLoadedSimSlot).append(", isSimLoaded : ").append(z3).append(", mccmnc : ").append(simOperatorNumericForPhone);
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(SmsApplication.DEFAULT_MSG_CONFIG_XML));
                try {
                    try {
                        XmlPullParser parser = getParser(fileInputStream);
                        if (parser != null) {
                            boolean z4 = false;
                            boolean z5 = false;
                            boolean z6 = false;
                            strTrim = "";
                            int eventType = parser.getEventType();
                            strTrim2 = strTrim;
                            while (true) {
                                z = z2;
                                if (eventType == z2) {
                                    break;
                                }
                                if (eventType == 2) {
                                    if (str.equals(parser.getName())) {
                                        z4 = z;
                                    }
                                    if (SmsApplication.SM_TAG.equals(parser.getName())) {
                                        z5 = z;
                                    }
                                    if (str2.equals(parser.getName())) {
                                        z6 = z;
                                    }
                                } else if (eventType == 4) {
                                    if (z4) {
                                        strTrim3 = parser.getText().trim();
                                        z4 = false;
                                    }
                                    if (z5) {
                                        strTrim2 = parser.getText().trim();
                                        z5 = false;
                                    }
                                    if (z6) {
                                        strTrim = parser.getText().trim();
                                        z6 = false;
                                    }
                                }
                                eventType = parser.next();
                                z2 = z;
                            }
                        } else {
                            Log.e(SmsApplication.LOG_TAG, "XmlPullParser is null");
                            strTrim2 = "";
                            strTrim = strTrim2;
                            z = true;
                        }
                        Log.d(SmsApplication.LOG_TAG, "xml parsing result- smNetCodeOpen: " + strTrim3 + " smCarrierCsc: " + strTrim2 + " smChangeOs: " + strTrim);
                        closeFileInputStream(fileInputStream);
                        if (TextUtils.isEmpty(strTrim3)) {
                            setDMACdataConfigInfo(false, simOperatorNumericForPhone);
                            String strUpdateChangeByOs = updateChangeByOs(strTrim, str, false);
                            if (TextUtils.isEmpty(strUpdateChangeByOs)) {
                                Log.i(SmsApplication.LOG_TAG, "OS change is not shown");
                            } else {
                                Log.i(SmsApplication.LOG_TAG, "OS change: first api:" + str2 + " smCarrierCsc is change from " + strTrim2 + " to " + strUpdateChangeByOs);
                                strTrim2 = strUpdateChangeByOs;
                            }
                            if (TextUtils.isEmpty(strTrim2)) {
                                Log.i(SmsApplication.LOG_TAG, "SM tag is empty  - AM select!!");
                                return z;
                            }
                            if (strTrim2.contains(str)) {
                                Log.i(SmsApplication.LOG_TAG, "Carrier phone - SM select!!");
                                return false;
                            }
                            String str3 = SemSystemProperties.get("ro.csc.countryiso_code", "NONE");
                            Log.i(SmsApplication.LOG_TAG, "activeOperatorId: " + str + ", countryiso: " + str3 + ", firstApiLevel: " + str2);
                            if ("SUP".equals(str) && "US".equals(str3) && "FIRST_API_LEVEL_30".equals(str2)) {
                                Log.i(SmsApplication.LOG_TAG, "Carrier phone - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Carrier phone - AM select!!");
                            return z;
                        }
                        boolean z7 = z;
                        setDMACdataConfigInfo(z7, simOperatorNumericForPhone);
                        boolean zIsEmpty = TextUtils.isEmpty(simOperatorNumericForPhone);
                        String strUpdateChangeByOs2 = updateChangeByOs(strTrim, str, z7);
                        if (TextUtils.isEmpty(strUpdateChangeByOs2)) {
                            Log.i(SmsApplication.LOG_TAG, "OS change is not shown");
                        } else {
                            Log.i(SmsApplication.LOG_TAG, "OS change: first api:" + str2 + " smNetcodeOpen is change from " + strTrim3 + " to " + strUpdateChangeByOs2);
                            strTrim3 = strUpdateChangeByOs2;
                        }
                        strTrim3.hashCode();
                        if (strTrim3.equals("000000")) {
                            if (!zIsEmpty) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects AM. mccmnc: " + simOperatorNumericForPhone + " - AM select!!");
                                return true;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects AM. no sim! - AM select!!");
                            return true;
                        }
                        if (strTrim3.equals("111111")) {
                            if (!zIsEmpty) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects SM. mccmnc: " + simOperatorNumericForPhone + " - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects SM. no sim! - SM select!!");
                            return false;
                        }
                        if (!zIsEmpty) {
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, Some selects SM. mccmnc: " + simOperatorNumericForPhone);
                            if (strTrim3.contains(simOperatorNumericForPhone)) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone - contains mccmnc - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone - no matching mccmnc - AM select!!");
                            return true;
                        }
                        if (strTrim3.contains(SmsApplication.SM_TAG)) {
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, Some selects SM. no Sim! - SM select!!");
                            return false;
                        }
                        Log.i(SmsApplication.LOG_TAG, "Unlocked phone, Some selects SM. no Sim! - AM select!!");
                        return true;
                    } catch (IOException | XmlPullParserException e) {
                        Log.e(SmsApplication.LOG_TAG, "Error while parsing", e);
                        closeFileInputStream(fileInputStream);
                        return false;
                    }
                } catch (Throwable th) {
                    closeFileInputStream(fileInputStream);
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                Log.e(SmsApplication.LOG_TAG, e2.getClass().getSimpleName() + "!! " + e2.getMessage());
                return false;
            }
        }
    }
}
