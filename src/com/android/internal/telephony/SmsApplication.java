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
        int myUserId = UserHandle.myUserId();
        int callingUid = Binder.getCallingUid();
        return UserHandle.getAppId(callingUid) < 10000 ? myUserId : UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    }

    private static UserHandle getIncomingUserHandle() {
        return UserHandle.of(getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollection(Context context) {
        return getApplicationCollectionAsUser(context, getIncomingUserId());
    }

    public static Collection<SmsApplicationData> getApplicationCollectionAsUser(Context context, int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getApplicationCollectionInternal(context, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
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
        UserHandle of = UserHandle.of(i);
        List<ResolveInfo> queryBroadcastReceiversAsUser = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.SMS_DELIVER_ACTION), 786432, of);
        HashMap hashMap = new HashMap();
        Iterator<ResolveInfo> it = queryBroadcastReceiversAsUser.iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && Manifest.permission.BROADCAST_SMS.equals(activityInfo.permission)) {
                String str = activityInfo.packageName;
                if (!hashMap.containsKey(str)) {
                    try {
                        SmsApplicationData smsApplicationData7 = new SmsApplicationData(str, activityInfo.applicationInfo.uid);
                        smsApplicationData7.mSmsReceiverClass = activityInfo.name;
                        hashMap.put(str, smsApplicationData7);
                    } catch (Exception unused) {
                        Rlog.e(LOG_TAG, "Error getting applicationName");
                    }
                }
            }
        }
        Intent intent = new Intent(Telephony.Sms.Intents.WAP_PUSH_DELIVER_ACTION);
        intent.setDataAndType(null, ContentType.MMS_MESSAGE);
        Iterator<ResolveInfo> it2 = packageManager.queryBroadcastReceiversAsUser(intent, 786432, of).iterator();
        while (it2.hasNext()) {
            ActivityInfo activityInfo2 = it2.next().activityInfo;
            if (activityInfo2 != null && Manifest.permission.BROADCAST_WAP_PUSH.equals(activityInfo2.permission) && (smsApplicationData6 = (SmsApplicationData) hashMap.get(activityInfo2.packageName)) != null) {
                smsApplicationData6.mMmsReceiverClass = activityInfo2.name;
            }
        }
        Iterator<ResolveInfo> it3 = packageManager.queryIntentServicesAsUser(new Intent(TelephonyManager.ACTION_RESPOND_VIA_MESSAGE, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, UserHandle.of(i)).iterator();
        while (it3.hasNext()) {
            ServiceInfo serviceInfo = it3.next().serviceInfo;
            if (serviceInfo != null && Manifest.permission.SEND_RESPOND_VIA_MESSAGE.equals(serviceInfo.permission) && (smsApplicationData5 = (SmsApplicationData) hashMap.get(serviceInfo.packageName)) != null) {
                smsApplicationData5.mRespondViaMessageClass = serviceInfo.name;
            }
        }
        Iterator<ResolveInfo> it4 = packageManager.queryIntentActivitiesAsUser(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, of).iterator();
        while (it4.hasNext()) {
            ActivityInfo activityInfo3 = it4.next().activityInfo;
            if (activityInfo3 != null && (smsApplicationData4 = (SmsApplicationData) hashMap.get(activityInfo3.packageName)) != null) {
                smsApplicationData4.mSendToClass = activityInfo3.name;
            }
        }
        Iterator<ResolveInfo> it5 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED), 786432, of).iterator();
        while (it5.hasNext()) {
            ActivityInfo activityInfo4 = it5.next().activityInfo;
            if (activityInfo4 != null && (smsApplicationData3 = (SmsApplicationData) hashMap.get(activityInfo4.packageName)) != null) {
                smsApplicationData3.mSmsAppChangedReceiverClass = activityInfo4.name;
            }
        }
        Iterator<ResolveInfo> it6 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.ACTION_EXTERNAL_PROVIDER_CHANGE), 786432, of).iterator();
        while (it6.hasNext()) {
            ActivityInfo activityInfo5 = it6.next().activityInfo;
            if (activityInfo5 != null && (smsApplicationData2 = (SmsApplicationData) hashMap.get(activityInfo5.packageName)) != null) {
                smsApplicationData2.mProviderChangedReceiverClass = activityInfo5.name;
            }
        }
        Iterator<ResolveInfo> it7 = packageManager.queryBroadcastReceiversAsUser(new Intent(Telephony.Sms.Intents.SIM_FULL_ACTION), 786432, of).iterator();
        while (it7.hasNext()) {
            ActivityInfo activityInfo6 = it7.next().activityInfo;
            if (activityInfo6 != null && (smsApplicationData = (SmsApplicationData) hashMap.get(activityInfo6.packageName)) != null) {
                smsApplicationData.mSimFullReceiverClass = activityInfo6.name;
            }
        }
        Iterator<ResolveInfo> it8 = queryBroadcastReceiversAsUser.iterator();
        while (it8.hasNext()) {
            ActivityInfo activityInfo7 = it8.next().activityInfo;
            if (activityInfo7 != null) {
                String str2 = activityInfo7.packageName;
                SmsApplicationData smsApplicationData8 = (SmsApplicationData) hashMap.get(str2);
                if (smsApplicationData8 != null && !smsApplicationData8.isComplete()) {
                    hashMap.remove(str2);
                }
            }
        }
        return hashMap.values();
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

    private static SmsApplicationData getApplication(Context context, boolean z, int i) {
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

    public static void grantPermissionsToSystemApps(Context context) {
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
    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void setDefaultApplicationAsUser(java.lang.String r9, android.content.Context r10, int r11) {
        /*
            java.lang.String r0 = "SmsApplication"
            if (r10 != 0) goto La
            java.lang.String r9 = "context in DefaultApplication is null"
            android.telephony.Rlog.e(r0, r9)
            return
        La:
            int r1 = android.os.Binder.getCallingUid()
            int r1 = android.os.UserHandle.getUserId(r1)
            java.lang.String r2 = "content://com.sec.knox.provider2/ApplicationPolicy"
            android.net.Uri r4 = android.net.Uri.parse(r2)
            java.lang.String r1 = java.lang.Integer.toString(r1)
            java.lang.String[] r7 = new java.lang.String[]{r9, r1}
            android.content.ContentResolver r3 = r10.getContentResolver()
            java.lang.String r6 = "isChangeSmsDefaultAppAllowed"
            r8 = 0
            r5 = 0
            android.database.Cursor r1 = r3.query(r4, r5, r6, r7, r8)
            if (r1 == 0) goto L4f
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4c
            java.lang.String r2 = "isChangeSmsDefaultAppAllowed"
            int r2 = r1.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4c
            java.lang.String r2 = r1.getString(r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4c
            java.lang.String r3 = "true"
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4c
            r1.close()
            goto L50
        L46:
            r0 = move-exception
            r9 = r0
            r1.close()
            throw r9
        L4c:
            r1.close()
        L4f:
            r2 = -1
        L50:
            if (r2 != 0) goto L58
            java.lang.String r9 = "Block setDefaultApplication by admin"
            android.telephony.Rlog.e(r0, r9)
            return
        L58:
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r10.getSystemService(r0)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            java.lang.String r1 = "role"
            java.lang.Object r1 = r10.getSystemService(r1)
            android.app.role.RoleManager r1 = (android.app.role.RoleManager) r1
            boolean r0 = r0.isSmsCapable()
            if (r0 != 0) goto L7b
            if (r1 == 0) goto L85
            java.lang.String r0 = "android.app.role.SMS"
            boolean r0 = r1.isRoleAvailable(r0)
            if (r0 != 0) goto L7b
            goto L85
        L7b:
            long r1 = android.os.Binder.clearCallingIdentity()
            setDefaultApplicationInternal(r9, r10, r11)     // Catch: java.lang.Throwable -> L86
            android.os.Binder.restoreCallingIdentity(r1)
        L85:
            return
        L86:
            r0 = move-exception
            r9 = r0
            android.os.Binder.restoreCallingIdentity(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsApplication.setDefaultApplicationAsUser(java.lang.String, android.content.Context, int):void");
    }

    private static void setDefaultApplicationInternal(String str, Context context, int i) {
        UserHandle of = UserHandle.of(i);
        String defaultSmsPackage = getDefaultSmsPackage(context, i);
        if (str == null || defaultSmsPackage == null || !str.equals(defaultSmsPackage)) {
            PackageManager packageManager = context.createContextAsUser(of, 0).getPackageManager();
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
            final Context context = this.mContext;
            if (identifier != UserHandle.SYSTEM.getIdentifier()) {
                try {
                    Context context2 = this.mContext;
                    context = context2.createPackageContextAsUser(context2.getPackageName(), 0, UserHandle.of(identifier));
                } catch (PackageManager.NameNotFoundException unused2) {
                }
            }
            threadPool.execute(new Runnable(this) { // from class: com.android.internal.telephony.SmsApplication.SmsPackageMonitor.1
                @Override // java.lang.Runnable
                public void run() {
                    Rlog.d(SmsApplication.LOG_TAG, "onPackageChanged: run");
                    PackageManager packageManager = context.getPackageManager();
                    ComponentName defaultSendToApplication = SmsApplication.getDefaultSendToApplication(context, true);
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
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mSmsReceiverClass) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ComponentName getDefaultMmsApplication(Context context, boolean z) {
        return getDefaultMmsApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultMmsApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mMmsReceiverClass) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ComponentName getDefaultRespondViaMessageApplication(Context context, boolean z) {
        return getDefaultRespondViaMessageApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultRespondViaMessageApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return application != null ? new ComponentName(application.mPackageName, application.mRespondViaMessageClass) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ComponentName getDefaultSendToApplication(Context context, boolean z) {
        int incomingUserId = getIncomingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, incomingUserId);
            return application != null ? new ComponentName(application.mPackageName, application.mSendToClass) : null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplication(Context context, boolean z) {
        return getDefaultExternalTelephonyProviderChangedApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultExternalTelephonyProviderChangedApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return (application == null || application.mProviderChangedReceiverClass == null) ? null : new ComponentName(application.mPackageName, application.mProviderChangedReceiverClass);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ComponentName getDefaultSimFullApplication(Context context, boolean z) {
        return getDefaultSimFullApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static ComponentName getDefaultSimFullApplicationAsUser(Context context, boolean z, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            return (application == null || application.mSimFullReceiverClass == null) ? null : new ComponentName(application.mPackageName, application.mSimFullReceiverClass);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
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

    public static boolean isDefaultSmsApplicationAsUser(Context context, String str, UserHandle userHandle) {
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0086 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isShouldNotWriteMessage(android.content.Context r7, java.lang.String r8) {
        /*
            java.lang.String r0 = "SmsApplication"
            java.lang.String r1 = "ro.csc.countryiso_code"
            java.lang.String r1 = android.os.SemSystemProperties.get(r1)
            java.lang.String r2 = getDefaultSmsApplicationPackageName(r7)
            android.content.pm.PackageManager r3 = r7.getPackageManager()
            r4 = 1
            r5 = 0
            java.lang.String r6 = "com.google.android.apps.messaging"
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo(r6, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L25
            int r3 = r3.flags     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L25
            r3 = r3 & r4
            if (r3 == 0) goto L25
            java.lang.String r3 = "AM is preloaded"
            android.telephony.Rlog.i(r0, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L25
            r3 = r4
            goto L26
        L25:
            r3 = r5
        L26:
            if (r2 == 0) goto L49
            java.lang.String r6 = "com.samsung.android.messaging"
            boolean r2 = r2.equals(r6)
            if (r2 != 0) goto L49
            if (r3 == 0) goto L49
            boolean r2 = r6.equals(r8)
            if (r2 == 0) goto L49
            java.lang.String r2 = "KR"
            boolean r2 = r2.equalsIgnoreCase(r1)
            if (r2 != 0) goto L49
            java.lang.String r2 = "KOREA"
            boolean r1 = r2.equalsIgnoreCase(r1)
            if (r1 != 0) goto L49
            return r5
        L49:
            java.lang.String[] r1 = com.android.internal.telephony.SmsApplication.sPackageNamePattern
            if (r1 != 0) goto L5a
            android.content.res.Resources r1 = r7.getResources()
            r2 = 17236488(0x1070208, float:2.479704E-38)
            java.lang.String[] r1 = r1.getStringArray(r2)
            com.android.internal.telephony.SmsApplication.sPackageNamePattern = r1
        L5a:
            java.lang.String[] r1 = com.android.internal.telephony.SmsApplication.sPackageNamePattern
            int r2 = r1.length
            r3 = r5
        L5e:
            if (r3 >= r2) goto L80
            r6 = r1[r3]
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L7d
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r7.append(r8)
            java.lang.String r8 = " is matched"
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.telephony.Rlog.d(r0, r7)
            return r4
        L7d:
            int r3 = r3 + 1
            goto L5e
        L80:
            boolean r7 = isVzwAuthorizedApp(r7, r8)
            if (r7 == 0) goto L87
            return r4
        L87:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r1 = "No PackageName Pattern : "
            r7.<init>(r1)
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.telephony.Rlog.d(r0, r7)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsApplication.isShouldNotWriteMessage(android.content.Context, java.lang.String):boolean");
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
            boolean isTssDevice = isTssDevice();
            boolean z = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            Log.i(SmsApplication.LOG_TAG, "isOperatorFixed()- isSupportTrueSingleSKU : " + isTssDevice + " isTSSActivated : " + z);
            if (isTssDevice) {
                return z;
            }
            return true;
        }

        private String getActiveOperatorIdByCountryiso(String str) {
            String str2 = "NONE";
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(SmsApplication.COUNTRYISO_OPENBUYER_CONFIG_XML));
                try {
                    try {
                        XmlPullParser parser = getParser(fileInputStream);
                        if (parser == null) {
                            Log.e(SmsApplication.LOG_TAG, "XmlPullParser is null");
                        } else {
                            String str3 = "NONE";
                            boolean z = false;
                            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                                if (eventType != 2) {
                                    if (eventType == 4 && z) {
                                        try {
                                            str3 = parser.getText().trim();
                                            z = false;
                                        } catch (IOException | XmlPullParserException e) {
                                            e = e;
                                            str2 = str3;
                                            Log.e(SmsApplication.LOG_TAG, "Error while parsing", e);
                                            closeFileInputStream(fileInputStream);
                                            return str2;
                                        } catch (Throwable unused) {
                                            str2 = str3;
                                            closeFileInputStream(fileInputStream);
                                            return str2;
                                        }
                                    }
                                } else if (str.equals(parser.getName())) {
                                    z = true;
                                }
                                if (!"NONE".equals(str3)) {
                                    break;
                                }
                            }
                            str2 = str3;
                        }
                        Log.d(SmsApplication.LOG_TAG, "xml parsing result- activeOperatorId: " + str2);
                        closeFileInputStream(fileInputStream);
                        return str2;
                    } catch (IOException | XmlPullParserException e2) {
                        e = e2;
                    }
                } catch (Throwable unused2) {
                }
            } catch (FileNotFoundException e3) {
                Log.e(SmsApplication.LOG_TAG, e3.getClass().getSimpleName() + "!! " + e3.getMessage());
                return "NONE";
            }
        }

        private boolean isWifiSkipCarrier() {
            String str = SemSystemProperties.get("ro.boot.carrierid", null);
            List asList = Arrays.asList("XSG", "MID", "ILO", "XFA", "AFR", "M10", "M06", "M05");
            if (TextUtils.isEmpty(str) || !asList.contains(str)) {
                return false;
            }
            Log.d(SmsApplication.LOG_TAG, "isWifiSkipCarrier return true");
            return true;
        }

        private String getActiveOperatorId() {
            boolean isTssDevice = isTssDevice();
            boolean z = SemSystemProperties.getBoolean("mdc.singlesku.activated", false);
            String str = "NONE";
            if (!isTssDevice) {
                str = SystemProperties.get("ro.csc.sales_code", "NONE");
            } else if (z) {
                String str2 = SemSystemProperties.get("ro.boot.activatedid", "NONE");
                if ("EUX".equals(str2) || "EUY".equals(str2)) {
                    String str3 = SemSystemProperties.get("ro.csc.countryiso_code", "NONE");
                    Log.i(SmsApplication.LOG_TAG, "countryiso : " + str3);
                    if (!"NONE".equals(str3)) {
                        str2 = getActiveOperatorIdByCountryiso(str3);
                    }
                }
                str = str2;
            } else if (isWifiSkipCarrier()) {
                str = SystemProperties.get("ro.csc.sales_code", "NONE");
            }
            SmsApplication.mLogStb.append(" isSupportTrueSingleSKU : ").append(isTssDevice).append(", isTSSActivated : ").append(z).append(", isWifiSkipCarrier : ").append(isWifiSkipCarrier()).append(", activeOperatorId : ").append(str);
            setDMACdataTssInfo(isTssDevice, z, str);
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

        private static XmlPullParser getParser(FileInputStream fileInputStream) {
            if (fileInputStream == null) {
                Log.d(SmsApplication.LOG_TAG, "no file");
                return null;
            }
            try {
                XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
                newInstance.setNamespaceAware(true);
                XmlPullParser newPullParser = newInstance.newPullParser();
                newPullParser.setInput(fileInputStream, null);
                return newPullParser;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                closeFileInputStream(fileInputStream);
                return null;
            }
        }

        private static void closeFileInputStream(FileInputStream fileInputStream) {
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

        public void setDefaultMsgApp() {
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

        /* JADX WARN: Code restructure failed: missing block: B:18:0x005f, code lost:
        
            if (isPackageEnabled(r0, com.android.internal.telephony.SmsApplication.SOFTBANK_MESSAGES) != false) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0061, code lost:
        
            r2 = r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x006b, code lost:
        
            if (isPackageEnabled(r0, com.android.internal.telephony.SmsApplication.KDDI_MESSAGES) != false) goto L25;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void setSMorOperatorMessageApp() {
            /*
                r5 = this;
                java.lang.String r0 = r5.getSalesCode()
                android.content.Context r1 = r5.mContext
                java.lang.String r2 = "phone"
                java.lang.Object r1 = r1.getSystemService(r2)
                android.telephony.TelephonyManager r1 = (android.telephony.TelephonyManager) r1
                if (r1 == 0) goto La3
                boolean r1 = r1.isSmsCapable()
                if (r1 == 0) goto La3
                com.samsung.android.feature.SemCscFeature r1 = com.samsung.android.feature.SemCscFeature.getInstance()
                java.lang.String r2 = "CscFeature_Setting_ConfigDefSmsApp"
                java.lang.String r1 = r1.getString(r2)
                android.content.Context r2 = r5.mContext
                java.lang.String r2 = r5.getMessagePackageName(r2)
                r0.hashCode()
                int r3 = r0.hashCode()
                r4 = -1
                switch(r3) {
                    case 67502: goto L49;
                    case 74256: goto L3e;
                    case 81886: goto L33;
                    default: goto L32;
                }
            L32:
                goto L53
            L33:
                java.lang.String r3 = "SBM"
                boolean r0 = r0.equals(r3)
                if (r0 != 0) goto L3c
                goto L53
            L3c:
                r4 = 2
                goto L53
            L3e:
                java.lang.String r3 = "KDI"
                boolean r0 = r0.equals(r3)
                if (r0 != 0) goto L47
                goto L53
            L47:
                r4 = 1
                goto L53
            L49:
                java.lang.String r3 = "DCM"
                boolean r0 = r0.equals(r3)
                if (r0 != 0) goto L52
                goto L53
            L52:
                r4 = 0
            L53:
                switch(r4) {
                    case 0: goto L6e;
                    case 1: goto L63;
                    case 2: goto L57;
                    default: goto L56;
                }
            L56:
                goto L82
            L57:
                android.content.Context r0 = r5.mContext
                java.lang.String r1 = "jp.softbank.mb.mail"
                boolean r0 = r5.isPackageEnabled(r0, r1)
                if (r0 == 0) goto L82
            L61:
                r2 = r1
                goto L82
            L63:
                android.content.Context r0 = r5.mContext
                java.lang.String r1 = "com.kddi.android.cmail"
                boolean r0 = r5.isPackageEnabled(r0, r1)
                if (r0 == 0) goto L82
                goto L61
            L6e:
                android.content.Context r0 = r5.mContext
                java.lang.String r3 = "com.nttdocomo.android.msg"
                boolean r0 = r5.isPackageEnabled(r0, r3)
                if (r0 == 0) goto L82
                java.lang.String r0 = "samsung"
                boolean r0 = r1.contains(r0)
                if (r0 != 0) goto L82
                r2 = r3
            L82:
                android.content.Context r0 = r5.mContext
                boolean r0 = r5.isPackageEnabled(r0, r2)
                if (r0 == 0) goto La3
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "setDefaultApplication messageAppName : "
                r0.<init>(r1)
                r0.append(r2)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "SmsApplication"
                android.util.Log.i(r1, r0)
                android.content.Context r5 = r5.mContext
                com.android.internal.telephony.SmsApplication.setDefaultApplication(r2, r5)
            La3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.SmsApplication.DefaultMessageAppConfig.setSMorOperatorMessageApp():void");
        }

        private String updateChangeByOs(String str, String str2, boolean z) {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("\\|");
                for (String str3 : split) {
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

        private boolean setDefaultMsgApp_File(String str) {
            String str2;
            String str3;
            String str4;
            boolean z;
            int findLoadedSimSlot = findLoadedSimSlot();
            boolean z2 = true;
            boolean z3 = findLoadedSimSlot >= 0;
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            String str5 = "FIRST_API_LEVEL_" + SemSystemProperties.getInt("ro.product.first_api_level", 0);
            if ("SM-A136B".equals(SemSystemProperties.get("ro.product.model", LsConstants.TAG_UNKNOWN))) {
                Log.i(SmsApplication.LOG_TAG, "in case of SM-A136B, modify the first api level from 30 to 31");
                str5 = "FIRST_API_LEVEL_31";
            }
            if (isNeedToModifyFirstApi()) {
                Log.i(SmsApplication.LOG_TAG, "It is released before S24, so modify the first api level from 34 to 33");
                str5 = "FIRST_API_LEVEL_33";
            }
            String str6 = "";
            if (!z3 || telephonyManager == null) {
                str2 = "";
            } else {
                str2 = telephonyManager.getSimOperatorNumericForPhone(findLoadedSimSlot);
            }
            Log.i(SmsApplication.LOG_TAG, "activeOperatorId: " + str + " phoneId: " + findLoadedSimSlot + " isSimLoaded: " + z3 + " mccmnc: " + str2 + " firstApiLevel: " + str5);
            SmsApplication.mLogStb.append(", phoneId : ").append(findLoadedSimSlot).append(", isSimLoaded : ").append(z3).append(", mccmnc : ").append(str2);
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(SmsApplication.DEFAULT_MSG_CONFIG_XML));
                try {
                    try {
                        XmlPullParser parser = getParser(fileInputStream);
                        if (parser != null) {
                            boolean z4 = false;
                            boolean z5 = false;
                            boolean z6 = false;
                            str3 = "";
                            int eventType = parser.getEventType();
                            str4 = str3;
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
                                    if (str5.equals(parser.getName())) {
                                        z6 = z;
                                    }
                                } else if (eventType == 4) {
                                    if (z4) {
                                        str6 = parser.getText().trim();
                                        z4 = false;
                                    }
                                    if (z5) {
                                        str4 = parser.getText().trim();
                                        z5 = false;
                                    }
                                    if (z6) {
                                        str3 = parser.getText().trim();
                                        z6 = false;
                                    }
                                }
                                eventType = parser.next();
                                z2 = z;
                            }
                        } else {
                            Log.e(SmsApplication.LOG_TAG, "XmlPullParser is null");
                            str4 = "";
                            str3 = str4;
                            z = true;
                        }
                        Log.d(SmsApplication.LOG_TAG, "xml parsing result- smNetCodeOpen: " + str6 + " smCarrierCsc: " + str4 + " smChangeOs: " + str3);
                        closeFileInputStream(fileInputStream);
                        if (TextUtils.isEmpty(str6)) {
                            setDMACdataConfigInfo(false, str2);
                            String updateChangeByOs = updateChangeByOs(str3, str, false);
                            if (TextUtils.isEmpty(updateChangeByOs)) {
                                Log.i(SmsApplication.LOG_TAG, "OS change is not shown");
                            } else {
                                Log.i(SmsApplication.LOG_TAG, "OS change: first api:" + str5 + " smCarrierCsc is change from " + str4 + " to " + updateChangeByOs);
                                str4 = updateChangeByOs;
                            }
                            if (TextUtils.isEmpty(str4)) {
                                Log.i(SmsApplication.LOG_TAG, "SM tag is empty  - AM select!!");
                                return z;
                            }
                            if (str4.contains(str)) {
                                Log.i(SmsApplication.LOG_TAG, "Carrier phone - SM select!!");
                                return false;
                            }
                            String str7 = SemSystemProperties.get("ro.csc.countryiso_code", "NONE");
                            Log.i(SmsApplication.LOG_TAG, "activeOperatorId: " + str + ", countryiso: " + str7 + ", firstApiLevel: " + str5);
                            if ("SUP".equals(str) && "US".equals(str7) && "FIRST_API_LEVEL_30".equals(str5)) {
                                Log.i(SmsApplication.LOG_TAG, "Carrier phone - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Carrier phone - AM select!!");
                            return z;
                        }
                        boolean z7 = z;
                        setDMACdataConfigInfo(z7, str2);
                        boolean isEmpty = TextUtils.isEmpty(str2);
                        String updateChangeByOs2 = updateChangeByOs(str3, str, z7);
                        if (TextUtils.isEmpty(updateChangeByOs2)) {
                            Log.i(SmsApplication.LOG_TAG, "OS change is not shown");
                        } else {
                            Log.i(SmsApplication.LOG_TAG, "OS change: first api:" + str5 + " smNetcodeOpen is change from " + str6 + " to " + updateChangeByOs2);
                            str6 = updateChangeByOs2;
                        }
                        str6.hashCode();
                        if (str6.equals("000000")) {
                            if (!isEmpty) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects AM. mccmnc: " + str2 + " - AM select!!");
                                return true;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects AM. no sim! - AM select!!");
                            return true;
                        }
                        if (str6.equals("111111")) {
                            if (!isEmpty) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects SM. mccmnc: " + str2 + " - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, All selects SM. no sim! - SM select!!");
                            return false;
                        }
                        if (!isEmpty) {
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone, Some selects SM. mccmnc: " + str2);
                            if (str6.contains(str2)) {
                                Log.i(SmsApplication.LOG_TAG, "Unlocked phone - contains mccmnc - SM select!!");
                                return false;
                            }
                            Log.i(SmsApplication.LOG_TAG, "Unlocked phone - no matching mccmnc - AM select!!");
                            return true;
                        }
                        if (str6.contains(SmsApplication.SM_TAG)) {
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
