package com.android.server.enterprise.auditlog;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.common.EnterprisePermissionChecker;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.activation.DevicePolicyListener;
import com.samsung.android.knox.deviceinfo.DeviceInventory;
import com.samsung.android.knox.log.AuditLogRulesInfo;
import com.samsung.android.knox.log.IAuditLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AuditLogService extends IAuditLog.Stub implements EnterpriseServiceCallback {
    public static final String[] swComponentWhitelist = {
        "KeyStore",
        "AndroidKeyStoreSpi",
        "PKIXRevocationChecker",
        "CertPathValidator",
        "ecryptfs",
        "fscrypt",
        "AndroidKeyStoreMaintenance"
    };
    public final AnonymousClass1 mBroadcastReceiver;
    public final ContentValues mContainerOwnerCache;
    public final Context mContext;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final Injector mInjector;
    public volatile boolean mIsBootCompleted;
    public final Map mLinkedHashMap;
    public final String mMessagePackage;
    public final Pattern mPattern;
    public final AnonymousClass1 mRemovableMediaBroadcastReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {
        public final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }
    }

    public AuditLogService(Context context) {
        this(new Injector(context));
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.enterprise.auditlog.AuditLogService$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.enterprise.auditlog.AuditLogService$1] */
    public AuditLogService(Injector injector) {
        final int i = 0;
        this.mRemovableMediaBroadcastReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.enterprise.auditlog.AuditLogService.1
                    public final /* synthetic */ AuditLogService this$0;

                    {
                        this.this$0 = this;
                    }

                    public void logRemovableMediaEvents(Intent intent, String str) {
                        StorageVolume storageVolume;
                        Bundle extras = intent.getExtras();
                        if (extras == null
                                || (storageVolume =
                                                (StorageVolume)
                                                        extras.getParcelable(
                                                                "android.os.storage.extra.STORAGE_VOLUME"))
                                        == null
                                || storageVolume.getUuid() == null) {
                            return;
                        }
                        for (VolumeInfo volumeInfo :
                                ((StorageManager)
                                                this.this$0.mInjector.mContext.getSystemService(
                                                        "storage"))
                                        .getVolumes()) {
                            if (volumeInfo.getDisk() != null
                                    && volumeInfo.getFsUuid() != null
                                    && volumeInfo.getFsUuid().equals(storageVolume.getUuid())) {
                                if (volumeInfo.getDisk().isSd()) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            "Removable Media Event: External SD Card ".concat(str),
                                            null);
                                }
                                if (volumeInfo.getDisk().isUsb()) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            "Removable Media Event: USB HOST MEMORY ".concat(str),
                                            null);
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int identifier;
                        switch (i) {
                            case 0:
                                if (intent.getAction()
                                        .equals("android.intent.action.MEDIA_MOUNTED")) {
                                    logRemovableMediaEvents(intent, "Mounted");
                                    return;
                                } else {
                                    if (intent.getAction()
                                            .equals("android.intent.action.MEDIA_UNMOUNTED")) {
                                        logRemovableMediaEvents(intent, "Unmounted");
                                        return;
                                    }
                                    return;
                                }
                            default:
                                if (intent.getAction()
                                                .equals("android.intent.action.ACTION_SHUTDOWN")
                                        || intent.getAction()
                                                .equals("android.intent.action.REBOOT")) {
                                    synchronized (this.this$0.mLinkedHashMap) {
                                        try {
                                            Iterator it =
                                                    this.this$0.mLinkedHashMap.values().iterator();
                                            while (it.hasNext()) {
                                                LogWritter logWritter =
                                                        ((Admin) it.next()).mLogWritter;
                                                LogWritter.LooperThread looperThread =
                                                        logWritter.mLooperThread;
                                                looperThread.mHandler.removeCallbacks(looperThread);
                                                logWritter.mCircularBuffer.closeFile();
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if (intent.getAction()
                                        .equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    Log.d("AuditLogService", "ACTION_LOCKED_BOOT_COMPLETED");
                                    this.this$0.mIsBootCompleted = true;
                                    synchronized (this.this$0.mLinkedHashMap) {
                                        try {
                                            Iterator it2 =
                                                    this.this$0.mLinkedHashMap.values().iterator();
                                            while (it2.hasNext()) {
                                                ((Admin) it2.next()).setBootCompleted(true);
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if (intent.getAction().equals("android.intent.action.TIME_SET")) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            String.format(
                                                    "The device time has been changed. Current Time"
                                                        + " = %d",
                                                    Long.valueOf(System.currentTimeMillis())),
                                            null);
                                    return;
                                }
                                if (intent.getAction()
                                        .equals(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED)) {
                                    UserHandle userHandle =
                                            (UserHandle)
                                                    intent.getExtra("android.intent.extra.USER");
                                    identifier =
                                            userHandle != null ? userHandle.getIdentifier() : 0;
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            identifier > 0
                                                    ? String.format(
                                                            "Managed Profile has been created"
                                                                + " successfully - user %d",
                                                            Integer.valueOf(identifier))
                                                    : "Managed Profile has been created"
                                                          + " successfully",
                                            null);
                                    return;
                                } else {
                                    if (intent.getAction()
                                            .equals(
                                                    DevicePolicyListener
                                                            .ACTION_PROFILE_OWNER_REMOVED)) {
                                        UserHandle userHandle2 =
                                                (UserHandle)
                                                        intent.getExtra(
                                                                "android.intent.extra.USER");
                                        identifier =
                                                userHandle2 != null
                                                        ? userHandle2.getIdentifier()
                                                        : 0;
                                        if (identifier > 0) {
                                            this.this$0.redactedAuditLogger(
                                                    null,
                                                    5,
                                                    2,
                                                    true,
                                                    Process.myPid(),
                                                    "AuditLogService",
                                                    String.format(
                                                            "Managed Profile has been removed -"
                                                                + " user %d",
                                                            Integer.valueOf(identifier)),
                                                    null);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                        }
                    }
                };
        final int i2 = 1;
        this.mBroadcastReceiver =
                new BroadcastReceiver(this) { // from class:
                    // com.android.server.enterprise.auditlog.AuditLogService.1
                    public final /* synthetic */ AuditLogService this$0;

                    {
                        this.this$0 = this;
                    }

                    public void logRemovableMediaEvents(Intent intent, String str) {
                        StorageVolume storageVolume;
                        Bundle extras = intent.getExtras();
                        if (extras == null
                                || (storageVolume =
                                                (StorageVolume)
                                                        extras.getParcelable(
                                                                "android.os.storage.extra.STORAGE_VOLUME"))
                                        == null
                                || storageVolume.getUuid() == null) {
                            return;
                        }
                        for (VolumeInfo volumeInfo :
                                ((StorageManager)
                                                this.this$0.mInjector.mContext.getSystemService(
                                                        "storage"))
                                        .getVolumes()) {
                            if (volumeInfo.getDisk() != null
                                    && volumeInfo.getFsUuid() != null
                                    && volumeInfo.getFsUuid().equals(storageVolume.getUuid())) {
                                if (volumeInfo.getDisk().isSd()) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            "Removable Media Event: External SD Card ".concat(str),
                                            null);
                                }
                                if (volumeInfo.getDisk().isUsb()) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            "Removable Media Event: USB HOST MEMORY ".concat(str),
                                            null);
                                }
                            }
                        }
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int identifier;
                        switch (i2) {
                            case 0:
                                if (intent.getAction()
                                        .equals("android.intent.action.MEDIA_MOUNTED")) {
                                    logRemovableMediaEvents(intent, "Mounted");
                                    return;
                                } else {
                                    if (intent.getAction()
                                            .equals("android.intent.action.MEDIA_UNMOUNTED")) {
                                        logRemovableMediaEvents(intent, "Unmounted");
                                        return;
                                    }
                                    return;
                                }
                            default:
                                if (intent.getAction()
                                                .equals("android.intent.action.ACTION_SHUTDOWN")
                                        || intent.getAction()
                                                .equals("android.intent.action.REBOOT")) {
                                    synchronized (this.this$0.mLinkedHashMap) {
                                        try {
                                            Iterator it =
                                                    this.this$0.mLinkedHashMap.values().iterator();
                                            while (it.hasNext()) {
                                                LogWritter logWritter =
                                                        ((Admin) it.next()).mLogWritter;
                                                LogWritter.LooperThread looperThread =
                                                        logWritter.mLooperThread;
                                                looperThread.mHandler.removeCallbacks(looperThread);
                                                logWritter.mCircularBuffer.closeFile();
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if (intent.getAction()
                                        .equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                                    Log.d("AuditLogService", "ACTION_LOCKED_BOOT_COMPLETED");
                                    this.this$0.mIsBootCompleted = true;
                                    synchronized (this.this$0.mLinkedHashMap) {
                                        try {
                                            Iterator it2 =
                                                    this.this$0.mLinkedHashMap.values().iterator();
                                            while (it2.hasNext()) {
                                                ((Admin) it2.next()).setBootCompleted(true);
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                if (intent.getAction().equals("android.intent.action.TIME_SET")) {
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            String.format(
                                                    "The device time has been changed. Current Time"
                                                        + " = %d",
                                                    Long.valueOf(System.currentTimeMillis())),
                                            null);
                                    return;
                                }
                                if (intent.getAction()
                                        .equals(DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED)) {
                                    UserHandle userHandle =
                                            (UserHandle)
                                                    intent.getExtra("android.intent.extra.USER");
                                    identifier =
                                            userHandle != null ? userHandle.getIdentifier() : 0;
                                    this.this$0.redactedAuditLogger(
                                            null,
                                            5,
                                            2,
                                            true,
                                            Process.myPid(),
                                            "AuditLogService",
                                            identifier > 0
                                                    ? String.format(
                                                            "Managed Profile has been created"
                                                                + " successfully - user %d",
                                                            Integer.valueOf(identifier))
                                                    : "Managed Profile has been created"
                                                          + " successfully",
                                            null);
                                    return;
                                } else {
                                    if (intent.getAction()
                                            .equals(
                                                    DevicePolicyListener
                                                            .ACTION_PROFILE_OWNER_REMOVED)) {
                                        UserHandle userHandle2 =
                                                (UserHandle)
                                                        intent.getExtra(
                                                                "android.intent.extra.USER");
                                        identifier =
                                                userHandle2 != null
                                                        ? userHandle2.getIdentifier()
                                                        : 0;
                                        if (identifier > 0) {
                                            this.this$0.redactedAuditLogger(
                                                    null,
                                                    5,
                                                    2,
                                                    true,
                                                    Process.myPid(),
                                                    "AuditLogService",
                                                    String.format(
                                                            "Managed Profile has been removed -"
                                                                + " user %d",
                                                            Integer.valueOf(identifier)),
                                                    null);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                        }
                    }
                };
        this.mInjector = injector;
        Context context = injector.mContext;
        Objects.requireNonNull(context);
        this.mContext = context;
        EdmStorageProvider edmStorageProvider = new EdmStorageProvider(context);
        this.mEdmStorageProvider = edmStorageProvider;
        this.mIsBootCompleted = false;
        this.mLinkedHashMap = Collections.synchronizedMap(new HashMap());
        this.mContainerOwnerCache = new ContentValues();
        Iterator it = ((ArrayList) edmStorageProvider.getValues("AUDITLOG", null, null)).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            Integer valueOf = Integer.valueOf(contentValues.getAsString("adminUid"));
            int intValue = valueOf.intValue();
            if (contentValues.get("auditLogEnabled").equals("true")) {
                Admin admin =
                        new Admin(this.mContext, getAdminPackageNameForUid(intValue), intValue);
                Integer asInteger = contentValues.getAsInteger("auditCriticalSize");
                if (asInteger != null) {
                    float intValue2 = asInteger.intValue();
                    CircularBuffer circularBuffer = admin.mLogWritter.mCircularBuffer;
                    circularBuffer.mAdminCriticalSize = intValue2;
                    circularBuffer.mCriticalIntent = false;
                }
                Integer asInteger2 = contentValues.getAsInteger("auditMaximumSize");
                if (asInteger2 != null) {
                    float intValue3 = asInteger2.intValue();
                    CircularBuffer circularBuffer2 = admin.mLogWritter.mCircularBuffer;
                    circularBuffer2.mAdminMaximumSize = intValue3;
                    circularBuffer2.mMaximumIntent = false;
                }
                contentValues.get("auditLogMDM").equals("true");
                Long asLong = contentValues.getAsLong("auditLogBufferSize");
                if (asLong != null) {
                    long longValue = asLong.longValue();
                    CircularBuffer circularBuffer3 = admin.mLogWritter.mCircularBuffer;
                    circularBuffer3.mBufferLimitSize = longValue;
                    circularBuffer3.createBubbleDir();
                }
                admin.mAuditLogRulesInfo = extractRulesFromCv(contentValues);
                synchronized (this.mLinkedHashMap) {
                    this.mLinkedHashMap.put(valueOf, admin);
                }
                admin.mDeviceInfo = getDeviceInfo();
            }
        }
        this.mPattern = Pattern.compile("(^Admin) ([0-9]+) (.*$)");
        InformFailure informFailure = InformFailure.getInstance();
        Context context2 = this.mContext;
        synchronized (informFailure) {
            informFailure.mContext = context2;
        }
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "android.intent.action.ACTION_SHUTDOWN",
                        "android.intent.action.LOCKED_BOOT_COMPLETED",
                        "android.intent.action.REBOOT",
                        "android.intent.action.TIME_SET",
                        DevicePolicyListener.ACTION_PROFILE_OWNER_ADDED);
        m.addAction(DevicePolicyListener.ACTION_PROFILE_OWNER_REMOVED);
        this.mContext.registerReceiver(this.mBroadcastReceiver, m, 2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addDataScheme("file");
        this.mContext.registerReceiverAsUser(
                this.mRemovableMediaBroadcastReceiver, UserHandle.SYSTEM, intentFilter, null, null);
        Context context3 = this.mContext;
        char[] cArr = Utils.HEX_DIGITS;
        int callingUid = Binder.getCallingUid();
        String str = "com.android.mms";
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_MESSAGE_CONFIG_PACKAGE_NAME",
                                "com.android.mms");
        if (!"com.android.mms".equals(string)) {
            try {
                PackageManagerAdapter packageManagerAdapter =
                        PackageManagerAdapter.getInstance(context3);
                int userId = UserHandle.getUserId(callingUid);
                packageManagerAdapter.getClass();
                PackageManagerAdapter.getPackageInfo(0, userId, string);
                str = string;
            } catch (Exception unused) {
            }
        }
        this.mMessagePackage = str;
    }

    public static boolean checkAuditPrivilegedAllowed(String str) {
        String[] strArr = swComponentWhitelist;
        for (int i = 0; i < 7; i++) {
            if (str.equals(strArr[i])) {
                return true;
            }
        }
        Log.e("AuditLogService", "Component " + str + " is not allowed to store audit messages!");
        return false;
    }

    public static AuditLogRulesInfo extractRulesFromCv(ContentValues contentValues) {
        ArrayList arrayList;
        AuditLogRulesInfo auditLogRulesInfo = new AuditLogRulesInfo();
        if (contentValues != null) {
            Integer asInteger = contentValues.getAsInteger("auditLogRuleSeverity");
            Integer asInteger2 = contentValues.getAsInteger("auditLogRuleOutcome");
            String asString = contentValues.getAsString("auditLogRuleGroups");
            ArrayList arrayList2 = null;
            if (TextUtils.isEmpty(asString)) {
                arrayList = null;
            } else {
                arrayList = new ArrayList();
                for (String str : asString.split(",")) {
                    arrayList.add(Integer.valueOf(str));
                }
            }
            String asString2 = contentValues.getAsString("auditLogRuleUsers");
            if (!TextUtils.isEmpty(asString2)) {
                arrayList2 = new ArrayList();
                for (String str2 : asString2.split(",")) {
                    arrayList2.add(Integer.valueOf(str2));
                }
            }
            if (asInteger != null) {
                auditLogRulesInfo.setSeverityRule(asInteger.intValue());
            }
            if (asInteger2 != null) {
                auditLogRulesInfo.setOutcomeRule(asInteger2.intValue());
            }
            auditLogRulesInfo.setGroupsRule(arrayList);
            auditLogRulesInfo.setUsersRule(arrayList2);
        }
        return auditLogRulesInfo;
    }

    public static boolean filterLoggingMessage(
            AuditLogRulesInfo auditLogRulesInfo, int i, boolean z, int i2, int i3) {
        if (i > auditLogRulesInfo.getSeverityRule()) {
            return false;
        }
        if (auditLogRulesInfo.getOutcomeRule() != 2
                && ((!z || auditLogRulesInfo.getOutcomeRule() != 1)
                        && (z || auditLogRulesInfo.getOutcomeRule() != 0))) {
            return false;
        }
        if (auditLogRulesInfo.getGroupsRule() == null
                || auditLogRulesInfo.getGroupsRule().isEmpty()
                || auditLogRulesInfo.getGroupsRule().contains(Integer.valueOf(i2))) {
            return auditLogRulesInfo.getUsersRule() == null
                    || auditLogRulesInfo.getUsersRule().isEmpty()
                    || auditLogRulesInfo.getUsersRule().contains(Integer.valueOf(i3))
                    || i3 == -1;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0082, code lost:

       if (r3 != null) goto L15;
    */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getProcessName$1(int r8) {
        /*
            java.lang.String r0 = "/proc/"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            r4.append(r8)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            java.lang.String r8 = "/cmdline"
            r4.append(r8)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            java.lang.String r8 = r4.toString()     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            r3.<init>(r8)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L59
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            java.lang.String r0 = "iso-8859-1"
            r8.<init>(r3, r0)     // Catch: java.lang.Throwable -> L4d java.io.IOException -> L50
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48
            r0.<init>(r8)     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48
        L2b:
            int r2 = r0.read()     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L3a
            if (r2 <= 0) goto L3c
            char r2 = (char) r2     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L3a
            r1.append(r2)     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L3a
            goto L2b
        L36:
            r1 = move-exception
            r2 = r0
            goto L8a
        L3a:
            r2 = move-exception
            goto L5e
        L3c:
            r0.close()
            r8.close()
        L42:
            r3.close()
            goto L85
        L46:
            r1 = move-exception
            goto L8a
        L48:
            r0 = move-exception
            r7 = r2
            r2 = r0
            r0 = r7
            goto L5e
        L4d:
            r1 = move-exception
            r8 = r2
            goto L8a
        L50:
            r8 = move-exception
            r0 = r2
            r2 = r8
            r8 = r0
            goto L5e
        L55:
            r1 = move-exception
            r8 = r2
            r3 = r8
            goto L8a
        L59:
            r8 = move-exception
            r0 = r2
            r3 = r0
            r2 = r8
            r8 = r3
        L5e:
            java.lang.String r4 = "AuditLogService"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L36
            r5.<init>()     // Catch: java.lang.Throwable -> L36
            java.lang.String r6 = "Exception: "
            r5.append(r6)     // Catch: java.lang.Throwable -> L36
            java.lang.String r2 = r2.getMessage()     // Catch: java.lang.Throwable -> L36
            r5.append(r2)     // Catch: java.lang.Throwable -> L36
            java.lang.String r2 = r5.toString()     // Catch: java.lang.Throwable -> L36
            android.util.Log.e(r4, r2)     // Catch: java.lang.Throwable -> L36
            if (r0 == 0) goto L7d
            r0.close()
        L7d:
            if (r8 == 0) goto L82
            r8.close()
        L82:
            if (r3 == 0) goto L85
            goto L42
        L85:
            java.lang.String r8 = r1.toString()
            return r8
        L8a:
            if (r2 == 0) goto L8f
            r2.close()
        L8f:
            if (r8 == 0) goto L94
            r8.close()
        L94:
            if (r3 == 0) goto L99
            r3.close()
        L99:
            throw r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.AuditLogService.getProcessName$1(int):java.lang.String");
    }

    public static boolean isBuildUserShip() {
        return SystemProperties.get("ro.build.type", "user").equals("user")
                && SystemProperties.get("ro.product_ship", "true").equals("true");
    }

    public final String appendLogMessageWithCallingUser(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
            String processName$1 = getProcessName$1(callingPid);
            sb.append(str);
            sb.append("\n[logged by: ");
            sb.append(processName$1);
            sb.append("/");
            sb.append(nameForUid);
            sb.append(", pid: ");
            sb.append(callingPid);
            sb.append("]");
        } catch (IOException e) {
            Log.e("AuditLogService", "Exception: " + e.getMessage());
        }
        return sb.toString();
    }

    public final void auditLogger(
            ContextInfo contextInfo, int i, int i2, boolean z, int i3, String str, String str2) {
        redactedAuditLogger(contextInfo, i, i2, z, i3, str, str2, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x009f, code lost:

       if (com.samsung.android.knox.SemPersonaManager.isKnoxId(r7) != false) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0074, code lost:

       if (r23 != null) goto L37;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void auditLoggerInternal(
            com.samsung.android.knox.ContextInfo r16,
            int r17,
            int r18,
            boolean r19,
            int r20,
            java.lang.String r21,
            java.lang.String r22,
            java.lang.String r23,
            int r24) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.AuditLogService.auditLoggerInternal(com.samsung.android.knox.ContextInfo,"
                    + " int, int, boolean, int, java.lang.String, java.lang.String,"
                    + " java.lang.String, int):void");
    }

    public final boolean checkAuditLogEnforce() {
        int callingUid = Binder.getCallingUid();
        String nameForUid = this.mContext.getPackageManager().getNameForUid(callingUid);
        if (!this.mMessagePackage.equals(nameForUid)
                && callingUid != 1999
                && this.mContext.checkCallingOrSelfPermission(
                                "com.samsung.android.knox.permission.KNOX_AUDIT_LOG")
                        != 0) {
            try {
                Context context = this.mContext;
                if (EnterprisePermissionChecker.sInstance == null) {
                    EnterprisePermissionChecker enterprisePermissionChecker =
                            new EnterprisePermissionChecker();
                    enterprisePermissionChecker.mContext = context;
                    EnterprisePermissionChecker.sInstance = enterprisePermissionChecker;
                }
                EnterprisePermissionChecker.sInstance.enforceAuthorization();
            } catch (SecurityException unused) {
                Log.w(
                        "AuditLogService",
                        AccountManagerService$$ExternalSyntheticOutline0.m(
                                callingUid,
                                "Caller (uid = ",
                                ", package = ",
                                nameForUid,
                                ") does not have Knox audit log permission"));
                return false;
            }
        }
        return true;
    }

    public final boolean checkOwnContainerOrSelf(int i, int i2) {
        Integer asInteger;
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (!SemPersonaManager.isKnoxId(i)) {
            return false;
        }
        if (this.mContainerOwnerCache.containsKey(String.valueOf(i))
                && (asInteger = this.mContainerOwnerCache.getAsInteger(String.valueOf(i)))
                        != null) {
            if (asInteger.intValue() == i2) {
                return true;
            }
            int appId = UserHandle.getAppId(asInteger.intValue());
            int appId2 = UserHandle.getAppId(i2);
            int userId = UserHandle.getUserId(i2);
            if (appId == appId2 && i == userId) {
                return true;
            }
        }
        int mUMContainerOwnerUid = this.mEdmStorageProvider.getMUMContainerOwnerUid(i);
        this.mContainerOwnerCache.put(String.valueOf(i), Integer.valueOf(mUMContainerOwnerUid));
        if (mUMContainerOwnerUid == i2) {
            return true;
        }
        return UserHandle.getAppId(mUMContainerOwnerUid) == UserHandle.getAppId(i2)
                && i == UserHandle.getUserId(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean disableAuditLog(com.samsung.android.knox.ContextInfo r12) {
        /*
            r11 = this;
            com.samsung.android.knox.ContextInfo r1 = r11.enforceAuditLogPermission(r12)
            int r12 = r1.mCallerUid
            java.util.Map r0 = r11.mLinkedHashMap
            java.lang.Integer r2 = java.lang.Integer.valueOf(r12)
            java.lang.Object r0 = r0.get(r2)
            com.android.server.enterprise.auditlog.Admin r0 = (com.android.server.enterprise.auditlog.Admin) r0
            r2 = 1
            if (r0 == 0) goto L67
            android.content.ContentValues r3 = new android.content.ContentValues
            r3.<init>()
            java.lang.String r4 = "auditLogEnabled"
            r5 = 0
            java.lang.String r6 = java.lang.String.valueOf(r5)
            r3.put(r4, r6)
            android.content.ContentValues r4 = new android.content.ContentValues
            r4.<init>()
            java.lang.String r6 = "adminUid"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r12)
            r4.put(r6, r7)
            com.android.server.enterprise.storage.EdmStorageProvider r6 = r11.mEdmStorageProvider
            java.lang.String r7 = "AUDITLOG"
            int r3 = r6.update(r7, r3, r4)
            if (r3 <= 0) goto L6c
            r0.deleteAllFiles()
            java.util.Map r0 = r11.mLinkedHashMap
            monitor-enter(r0)
            java.util.Map r3 = r11.mLinkedHashMap     // Catch: java.lang.Throwable -> L69
            java.lang.Integer r4 = java.lang.Integer.valueOf(r12)     // Catch: java.lang.Throwable -> L69
            r3.remove(r4)     // Catch: java.lang.Throwable -> L69
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L69
            android.content.Context r0 = r11.mContext
            java.lang.String r3 = "AuditLog/isAuditLogEnabled"
            int r4 = android.os.UserHandle.getUserId(r12)
            com.android.server.enterprise.utils.SecContentProviderUtil.notifyPolicyChangesAsUser(r0, r3, r4)
            boolean r0 = r11.isAuditServiceRunning()
            if (r0 != 0) goto L67
            java.lang.String r0 = "persist.sys.knox.auditlog"
            java.lang.String r3 = "false"
            com.android.server.enterprise.utils.Utils.setSystemProperty(r0, r3)
        L67:
            r10 = r2
            goto L6d
        L69:
            r11 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L69
            throw r11
        L6c:
            r10 = r5
        L6d:
            if (r10 == 0) goto L83
            int r5 = android.os.Process.myPid()
            java.lang.String r6 = "AuditLogService"
            java.lang.String r7 = "AuditLog status has changed to disable"
            int r9 = android.os.UserHandle.getUserId(r12)
            r8 = 0
            r2 = 5
            r3 = 2
            r4 = 1
            r0 = r11
            r0.redactedAuditLoggerAsUser(r1, r2, r3, r4, r5, r6, r7, r8, r9)
        L83:
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.AuditLogService.disableAuditLog(com.samsung.android.knox.ContextInfo):boolean");
    }

    public final synchronized boolean dumpLogFile(
            ContextInfo contextInfo,
            long j,
            long j2,
            String str,
            ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            if (parcelFileDescriptor.getFileDescriptor() != null
                    && parcelFileDescriptor.getFileDescriptor().valid()) {
                if (parcelFileDescriptor.canDetectErrors()) {
                    try {
                        Log.v("AuditLogService", "check error");
                        parcelFileDescriptor.checkError();
                    } catch (Exception unused) {
                        Log.v("AuditLogService", "error checking file descriptor");
                        return false;
                    }
                }
                try {
                    new SecurityManager().checkWrite(parcelFileDescriptor.getFileDescriptor());
                    Admin admin =
                            (Admin)
                                    this.mLinkedHashMap.get(
                                            Integer.valueOf(
                                                    enforceAuditLogPermission(contextInfo)
                                                            .mCallerUid));
                    if (admin == null || admin.mIsDumping) {
                        return false;
                    }
                    if (str != null) {
                        Filter filter = new Filter();
                        admin.mDumpFilter = filter;
                        try {
                            filter.mPattern = Pattern.compile(str);
                        } catch (PatternSyntaxException unused2) {
                            return false;
                        }
                    } else {
                        admin.mDumpFilter = null;
                    }
                    return admin.dump(parcelFileDescriptor, j, j2);
                } catch (SecurityException unused3) {
                    Log.w("AuditLogService", "can't write to file descriptor");
                    return false;
                }
            }
        }
        Log.e("AuditLogService", "invalid output file");
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x00bf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean enableAuditLog(com.samsung.android.knox.ContextInfo r13) {
        /*
            r12 = this;
            com.samsung.android.knox.ContextInfo r1 = r12.enforceAuditLogPermission(r13)
            int r13 = r1.mCallerUid
            r0 = 0
            java.util.Map r2 = r12.mLinkedHashMap     // Catch: java.lang.Exception -> La3
            java.lang.Integer r3 = java.lang.Integer.valueOf(r13)     // Catch: java.lang.Exception -> La3
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Exception -> La3
            r3 = 1
            if (r2 == 0) goto L15
            return r3
        L15:
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Exception -> La3
            r2.<init>()     // Catch: java.lang.Exception -> La3
            java.lang.String r4 = "auditLogEnabled"
            java.lang.String r5 = java.lang.String.valueOf(r3)     // Catch: java.lang.Exception -> La3
            r2.put(r4, r5)     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.storage.EdmStorageProvider r4 = r12.mEdmStorageProvider     // Catch: java.lang.Exception -> La3
            java.lang.String r5 = "AUDITLOG"
            boolean r2 = r4.putValues(r13, r0, r5, r2)     // Catch: java.lang.Exception -> La3
            long r4 = r12.setAvailableSize(r13)     // Catch: java.lang.Exception -> Laa
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 >= 0) goto L37
            goto L38
        L37:
            r0 = r2
        L38:
            if (r0 == 0) goto La8
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Exception -> La3
            r2.<init>()     // Catch: java.lang.Exception -> La3
            java.lang.String r6 = "adminUid"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r13)     // Catch: java.lang.Exception -> La3
            r2.put(r6, r7)     // Catch: java.lang.Exception -> La3
            r12.mIsBootCompleted = r3     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.Admin r2 = new com.android.server.enterprise.auditlog.Admin     // Catch: java.lang.Exception -> La3
            android.content.Context r3 = r12.mContext     // Catch: java.lang.Exception -> La3
            java.lang.String r6 = r12.getAdminPackageNameForUid(r13)     // Catch: java.lang.Exception -> La3
            r2.<init>(r3, r6, r13)     // Catch: java.lang.Exception -> La3
            java.util.List r3 = r12.getDeviceInfo()     // Catch: java.lang.Exception -> La3
            r2.mDeviceInfo = r3     // Catch: java.lang.Exception -> La3
            boolean r3 = r12.mIsBootCompleted     // Catch: java.lang.Exception -> La3
            r2.setBootCompleted(r3)     // Catch: java.lang.Exception -> La3
            com.samsung.android.knox.log.AuditLogRulesInfo r3 = r12.getRulesInfoFromDB(r13)     // Catch: java.lang.Exception -> La3
            r2.mAuditLogRulesInfo = r3     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.LogWritter r3 = r2.mLogWritter     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.CircularBuffer r3 = r3.mCircularBuffer     // Catch: java.lang.Exception -> La3
            r3.mBufferLimitSize = r4     // Catch: java.lang.Exception -> La3
            r3.createBubbleDir()     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.LogWritter r3 = r2.mLogWritter     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.CircularBuffer r3 = r3.mCircularBuffer     // Catch: java.lang.Exception -> La3
            r3.createBubbleDir()     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.LogWritter r3 = r2.mLogWritter     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.auditlog.CircularBuffer r3 = r3.mCircularBuffer     // Catch: java.lang.Exception -> La3
            long r4 = r3.getBufferLogSize()     // Catch: java.lang.Exception -> La3
            r3.resizeBubbleFile(r4)     // Catch: java.lang.Exception -> La3
            java.util.Map r3 = r12.mLinkedHashMap     // Catch: java.lang.Exception -> La3
            monitor-enter(r3)     // Catch: java.lang.Exception -> La3
            java.util.Map r4 = r12.mLinkedHashMap     // Catch: java.lang.Throwable -> La5
            java.lang.Integer r5 = java.lang.Integer.valueOf(r13)     // Catch: java.lang.Throwable -> La5
            r4.put(r5, r2)     // Catch: java.lang.Throwable -> La5
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La5
            java.lang.String r2 = "persist.sys.knox.auditlog"
            java.lang.String r3 = "true"
            com.android.server.enterprise.utils.Utils.setSystemProperty(r2, r3)     // Catch: java.lang.Exception -> La3
            android.content.Context r2 = r12.mContext     // Catch: java.lang.Exception -> La3
            java.lang.String r3 = "AuditLog/isAuditLogEnabled"
            int r4 = android.os.UserHandle.getUserId(r13)     // Catch: java.lang.Exception -> La3
            com.android.server.enterprise.utils.SecContentProviderUtil.notifyPolicyChangesAsUser(r2, r3, r4)     // Catch: java.lang.Exception -> La3
            goto La8
        La3:
            r2 = move-exception
            goto Lae
        La5:
            r2 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La5
            throw r2     // Catch: java.lang.Exception -> La3
        La8:
            r10 = r0
            goto Lbd
        Laa:
            r0 = move-exception
            r11 = r2
            r2 = r0
            r0 = r11
        Lae:
            r2.printStackTrace()
            com.android.server.enterprise.auditlog.InformFailure r3 = com.android.server.enterprise.auditlog.InformFailure.getInstance()
            java.lang.String r4 = r12.getAdminPackageNameForUid(r13)
            r3.broadcastFailure(r2, r4)
            goto La8
        Lbd:
            if (r10 == 0) goto Ld3
            int r5 = android.os.Process.myPid()
            java.lang.String r6 = "AuditLogService"
            java.lang.String r7 = "AuditLog status has changed to enable"
            int r9 = android.os.UserHandle.getUserId(r13)
            r3 = 2
            r4 = 1
            r2 = 5
            r8 = 0
            r0 = r12
            r0.redactedAuditLoggerAsUser(r1, r2, r3, r4, r5, r6, r7, r8, r9)
        Ld3:
            return r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.AuditLogService.enableAuditLog(com.samsung.android.knox.ContextInfo):boolean");
    }

    public final ContextInfo enforceAuditLogPermission(ContextInfo contextInfo) {
        return getEDM$1$1()
                .enforceActiveAdminPermissionByContext(
                        contextInfo,
                        new ArrayList(
                                Arrays.asList(
                                        "com.samsung.android.knox.permission.KNOX_AUDIT_LOG")));
    }

    public final String getAdminPackageNameForUid(int i) {
        int appId = UserHandle.getAppId(i);
        if (i != 1000 && (appId < 10000 || appId > 19999)) {
            return "com.sec.enterprise.knox.cloudmdm.smdms";
        }
        String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
        return packageNameForUid == null
                ? this.mContext.getPackageManager().getNameForUid(i)
                : packageNameForUid;
    }

    public final AuditLogRulesInfo getAuditLogRules(ContextInfo contextInfo) {
        int i = enforceAuditLogPermission(contextInfo).mCallerUid;
        Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
        return admin != null ? admin.mAuditLogRulesInfo : getRulesInfoFromDB(i);
    }

    public final int getCriticalLogSize(ContextInfo contextInfo) {
        Admin admin =
                (Admin)
                        this.mLinkedHashMap.get(
                                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
        if (admin != null) {
            return (int) admin.mLogWritter.mCircularBuffer.mAdminCriticalSize;
        }
        return 0;
    }

    public final int getCurrentLogFileSize(ContextInfo contextInfo) {
        Admin admin =
                (Admin)
                        this.mLinkedHashMap.get(
                                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
        if (admin == null) {
            return 0;
        }
        CircularBuffer circularBuffer = admin.mLogWritter.mCircularBuffer;
        int i =
                (int)
                        ((circularBuffer.mCircularBufferSize * 100)
                                / circularBuffer.mBufferLimitSize);
        if (i > 100) {
            return 100;
        }
        return i;
    }

    public final List getDeviceInfo() {
        String str;
        DeviceInventory deviceInventory = getEDM$1$1().getDeviceInventory();
        ArrayList m =
                PortStatus_1_1$$ExternalSyntheticOutline0.m(
                        " -----------------------------------------\n");
        m.add("OEM: " + Build.MANUFACTURER);
        m.add("DEVICE: " + Build.MODEL);
        m.add("PLATFORM: " + Build.VERSION.RELEASE);
        m.add("OS: " + deviceInventory.getDeviceOS());
        m.add("OS VERSION: " + deviceInventory.getDeviceOSVersion());
        String radioVersion = Build.getRadioVersion();
        if (radioVersion != null) {
            m.add("BASEBAND: ".concat(radioVersion));
        }
        StringBuilder sb = new StringBuilder("DEVICE ID: ");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        String str2 = null;
        try {
            try {
                str = SystemProperties.get("ril.serialnumber", "");
            } catch (Exception unused) {
                Log.w("AuditLogService", "could not get property");
            }
            if (!TextUtils.isEmpty(str) && !str.equals("00000000000")) {
                str2 = str;
                sb.append(str2);
                m.add(sb.toString());
                m.add(" -----------------------------------------\n");
                return m;
            }
            String str3 = SystemProperties.get("ro.boot.serialno", "");
            if (!TextUtils.isEmpty(str3)) {
                str2 = str3;
            }
            sb.append(str2);
            m.add(sb.toString());
            m.add(" -----------------------------------------\n");
            return m;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final EnterpriseDeviceManager getEDM$1$1() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final int getMaximumLogSize(ContextInfo contextInfo) {
        Admin admin =
                (Admin)
                        this.mLinkedHashMap.get(
                                Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid));
        if (admin != null) {
            return (int) admin.mLogWritter.mCircularBuffer.mAdminMaximumSize;
        }
        return 0;
    }

    public final AuditLogRulesInfo getRulesInfoFromDB(int i) {
        AuditLogRulesInfo auditLogRulesInfo = new AuditLogRulesInfo();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        ArrayList arrayList =
                (ArrayList) this.mEdmStorageProvider.getValues("AUDITLOG", null, contentValues);
        return !arrayList.isEmpty()
                ? extractRulesFromCv((ContentValues) arrayList.get(0))
                : auditLogRulesInfo;
    }

    public final boolean isAuditLogEnabled(ContextInfo contextInfo) {
        return this.mLinkedHashMap.get(
                        Integer.valueOf(enforceAuditLogPermission(contextInfo).mCallerUid))
                != null;
    }

    public final boolean isAuditLogEnabledAsUser(int i) {
        Iterator it = this.mLinkedHashMap.keySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            int userId = UserHandle.getUserId(intValue);
            if (i == -1) {
                return true;
            }
            ((PersonaManagerAdapter)
                            ((IPersonaManagerAdapter)
                                    AdapterRegistry.mAdapterHandles.get(
                                            IPersonaManagerAdapter.class)))
                    .getClass();
            if (SemPersonaManager.isKnoxId(i)) {
                if (checkOwnContainerOrSelf(i, intValue)) {
                    return true;
                }
            } else if (userId == 0 || userId == i) {
                return true;
            }
        }
        return false;
    }

    public final boolean isAuditServiceRunning() {
        return !this.mLinkedHashMap.isEmpty();
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void logEventAsUser(
            java.lang.String r20, int r21, int r22, int r23, java.util.List r24) {
        /*
            Method dump skipped, instructions count: 874
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.auditlog.AuditLogService.logEventAsUser(java.lang.String,"
                    + " int, int, int, java.util.List):void");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
        if (admin != null) {
            synchronized (this.mLinkedHashMap) {
                this.mLinkedHashMap.remove(Integer.valueOf(i));
            }
            LogWritter logWritter = admin.mLogWritter;
            LogWritter.LooperThread looperThread = logWritter.mLooperThread;
            looperThread.mHandler.removeCallbacks(looperThread);
            logWritter.mCircularBuffer.closeFile();
            admin.deleteAllFiles();
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
        if (admin != null) {
            synchronized (this.mLinkedHashMap) {
                this.mLinkedHashMap.remove(Integer.valueOf(i));
            }
            LogWritter logWritter = admin.mLogWritter;
            LogWritter.LooperThread looperThread = logWritter.mLooperThread;
            looperThread.mHandler.removeCallbacks(looperThread);
            logWritter.mCircularBuffer.closeFile();
            admin.deleteAllFiles();
            if (isAuditServiceRunning()) {
                return;
            }
            Utils.setSystemProperty("persist.sys.knox.auditlog", "false");
        }
    }

    public final void redactedAuditLogger(
            ContextInfo contextInfo,
            int i,
            int i2,
            boolean z,
            int i3,
            String str,
            String str2,
            String str3) {
        if (checkAuditLogEnforce()) {
            auditLoggerInternal(contextInfo, i, i2, z, i3, str, str2, str3, -1);
        }
    }

    public final boolean redactedAuditLoggerAsUser(
            ContextInfo contextInfo,
            int i,
            int i2,
            boolean z,
            int i3,
            String str,
            String str2,
            String str3,
            int i4) {
        String str4;
        if (!checkAuditLogEnforce()) {
            return false;
        }
        Matcher matcher = this.mPattern.matcher(str2);
        if (matcher.find()) {
            str4 =
                    matcher.replaceFirst(
                            "$1 "
                                    + this.mContext
                                            .getPackageManager()
                                            .getNameForUid(
                                                    UserHandle.getAppId(
                                                            Integer.parseInt(matcher.group(2))))
                                    + " $3");
        } else {
            str4 = str2;
        }
        auditLoggerInternal(contextInfo, i, i2, z, i3, str, str4, str3, i4);
        return true;
    }

    public final boolean setAuditLogRules(
            ContextInfo contextInfo, AuditLogRulesInfo auditLogRulesInfo) {
        ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
        int i = enforceAuditLogPermission.mCallerUid;
        if (auditLogRulesInfo == null
                || auditLogRulesInfo.getSeverityRule() > 5
                || auditLogRulesInfo.getSeverityRule() < 1
                || auditLogRulesInfo.getOutcomeRule() < 0
                || auditLogRulesInfo.getOutcomeRule() > 2) {
            return false;
        }
        Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i));
        ContentValues contentValues = new ContentValues();
        if (admin != null) {
            contentValues.put("auditLogEnabled", String.valueOf(true));
        }
        StringBuilder sb = new StringBuilder();
        if (auditLogRulesInfo.getGroupsRule() != null
                && !auditLogRulesInfo.getGroupsRule().isEmpty()) {
            Iterator it = auditLogRulesInfo.getGroupsRule().iterator();
            while (it.hasNext()) {
                sb.append(((Integer) it.next()).toString() + ",");
            }
        }
        StringBuilder sb2 = new StringBuilder();
        if (auditLogRulesInfo.getUsersRule() != null
                && !auditLogRulesInfo.getUsersRule().isEmpty()) {
            Iterator it2 = auditLogRulesInfo.getUsersRule().iterator();
            while (it2.hasNext()) {
                sb2.append(((Integer) it2.next()).toString() + ",");
            }
        }
        contentValues.put(
                "auditLogRuleOutcome", Integer.valueOf(auditLogRulesInfo.getOutcomeRule()));
        contentValues.put(
                "auditLogRuleSeverity", Integer.valueOf(auditLogRulesInfo.getSeverityRule()));
        contentValues.put("auditLogRuleGroups", sb.toString());
        contentValues.put("auditLogRuleUsers", sb2.toString());
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i));
        boolean put = this.mEdmStorageProvider.put("AUDITLOG", contentValues, contentValues2);
        if (!put) {
            InformFailure.getInstance()
                    .broadcastFailure(
                            "Cannot set filter on Database",
                            admin != null ? admin.mPackageName : "");
        }
        if (admin != null) {
            admin.mAuditLogRulesInfo = auditLogRulesInfo;
        }
        if (put) {
            redactedAuditLoggerAsUser(
                    enforceAuditLogPermission,
                    5,
                    2,
                    true,
                    Process.myPid(),
                    "AuditLogService",
                    "AuditLog filter rules has changed.",
                    null,
                    UserHandle.getUserId(i));
        }
        return put;
    }

    public final long setAvailableSize(int i) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long availableBlocks = ((statFs.getAvailableBlocks() * statFs.getBlockSize()) * 5) / 100;
        if (availableBlocks < 10485760 || availableBlocks > 52428800) {
            if (availableBlocks >= 52428800
                    && this.mEdmStorageProvider.putLong(
                            i, "AUDITLOG", 52428800L, "auditLogBufferSize")) {
                return 52428800L;
            }
        } else if (this.mEdmStorageProvider.putLong(
                i, "AUDITLOG", availableBlocks, "auditLogBufferSize")) {
            return availableBlocks;
        }
        return -1L;
    }

    public final boolean setCriticalLogSize(ContextInfo contextInfo, int i) {
        boolean z = false;
        if (i >= 1 && i <= 99) {
            ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
            int i2 = enforceAuditLogPermission.mCallerUid;
            Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i2));
            if (admin != null) {
                boolean putInt =
                        this.mEdmStorageProvider.putInt(i2, 0, i, "AUDITLOG", "auditCriticalSize");
                if (!putInt) {
                    InformFailure.getInstance()
                            .broadcastFailure(
                                    "Cannot set critcal log size on Databank", admin.mPackageName);
                }
                CircularBuffer circularBuffer = admin.mLogWritter.mCircularBuffer;
                circularBuffer.mAdminCriticalSize = i;
                circularBuffer.mCriticalIntent = false;
                z = putInt;
            }
            if (z) {
                redactedAuditLoggerAsUser(
                        enforceAuditLogPermission,
                        5,
                        2,
                        true,
                        Process.myPid(),
                        "AuditLogService",
                        String.format(
                                "AuditLog critical size has changed to %d", Integer.valueOf(i)),
                        null,
                        UserHandle.getUserId(i2));
            }
        }
        return z;
    }

    public final boolean setMaximumLogSize(ContextInfo contextInfo, int i) {
        boolean z = false;
        if (i >= 1 && i <= 99) {
            ContextInfo enforceAuditLogPermission = enforceAuditLogPermission(contextInfo);
            int i2 = enforceAuditLogPermission.mCallerUid;
            Admin admin = (Admin) this.mLinkedHashMap.get(Integer.valueOf(i2));
            if (admin != null) {
                boolean putInt =
                        this.mEdmStorageProvider.putInt(i2, 0, i, "AUDITLOG", "auditMaximumSize");
                if (!putInt) {
                    InformFailure.getInstance()
                            .broadcastFailure(
                                    "Cannot set maximum log size on databank", admin.mPackageName);
                }
                CircularBuffer circularBuffer = admin.mLogWritter.mCircularBuffer;
                circularBuffer.mAdminMaximumSize = i;
                circularBuffer.mMaximumIntent = false;
                z = putInt;
            }
            if (z) {
                redactedAuditLoggerAsUser(
                        enforceAuditLogPermission,
                        5,
                        2,
                        true,
                        Process.myPid(),
                        "AuditLogService",
                        String.format(
                                "AuditLog maximum size has changed to %d", Integer.valueOf(i)),
                        null,
                        UserHandle.getUserId(i2));
            }
        }
        return z;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
