package com.android.server.am;

import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.ContentProviderHolder;
import android.app.IApplicationThread;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.RescueParty;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ContentProviderRecord;
import com.android.server.am.ContentProviderRecord.ExternalProcessHandle;
import com.android.server.pm.UserManagerInternal;
import com.android.server.sdksandbox.SdkSandboxManagerLocal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.usage.UsageStatsService;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService.SettingObserver;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContentProviderHelper {
    public static final int[] PROCESS_STATE_STATS_FORMAT = {32, FrameworkStatsLog.PACKAGE_MANAGER_SNAPSHOT_REPORTED, 10272};
    public final ProviderMap mProviderMap;
    public final ActivityManagerService mService;
    public boolean mSystemProvidersInstalled;
    public final ArrayList mLaunchingProviders = new ArrayList();
    public final Map mCloneProfileAuthorityRedirectionCache = new HashMap();
    public final long[] mProcessStateStatsLongs = new long[1];

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DevelopmentSettingsObserver extends ContentObserver {
        public final ComponentName mBugreportStorageProvider;
        public final Uri mUri;

        public DevelopmentSettingsObserver() {
            super(ContentProviderHelper.this.mService.mHandler);
            Uri uriFor = Settings.Global.getUriFor("development_settings_enabled");
            this.mUri = uriFor;
            this.mBugreportStorageProvider = new ComponentName("com.android.shell", "com.android.shell.BugreportStorageProvider");
            ContentProviderHelper.this.mService.mContext.getContentResolver().registerContentObserver(uriFor, false, this, -1);
            onChange();
        }

        public final void onChange() {
            ContentProviderHelper.this.mService.mContext.getPackageManager().setComponentEnabledSetting(this.mBugreportStorageProvider, Settings.Global.getInt(ContentProviderHelper.this.mService.mContext.getContentResolver(), "development_settings_enabled", Build.IS_ENG ? 1 : 0) != 0 ? 1 : 0, 0);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            if (this.mUri.equals(uri)) {
                onChange();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StartActivityRunnable implements Runnable {
        public final Context mContext;
        public final Intent mIntent;
        public final UserHandle mUserHandle;

        public StartActivityRunnable(Context context, Intent intent, UserHandle userHandle) {
            this.mContext = context;
            this.mIntent = intent;
            this.mUserHandle = userHandle;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.mContext.startActivityAsUser(this.mIntent, this.mUserHandle);
        }
    }

    public ContentProviderHelper(ActivityManagerService activityManagerService, boolean z) {
        this.mService = activityManagerService;
        this.mProviderMap = z ? new ProviderMap(activityManagerService) : null;
    }

    public static void checkTime(long j, String str) {
        long uptimeMillis = SystemClock.uptimeMillis() - j;
        if (uptimeMillis > 50) {
            Slog.w("ContentProviderHelper", "Slow operation: " + uptimeMillis + "ms so far, now at " + str);
        }
    }

    public static boolean hasProviderConnectionLocked(ProcessRecord processRecord) {
        for (int size = processRecord.mProviders.mPubProviders.size() - 1; size >= 0; size--) {
            if (!((ContentProviderRecord) processRecord.mProviders.mPubProviders.valueAt(size)).connections.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final void checkAssociationAndPermissionLocked(final ProcessRecord processRecord, final ProviderInfo providerInfo, int i, int i2, boolean z, String str, long j) {
        String str2;
        if (processRecord == null) {
            str2 = this.mService.validateAssociationAllowedLocked(providerInfo.applicationInfo.uid, i, providerInfo.packageName, null) ? null : "<null>";
        } else {
            str2 = (String) processRecord.mPkgList.searchEachPackage(new Function() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    ContentProviderHelper contentProviderHelper = ContentProviderHelper.this;
                    ProcessRecord processRecord2 = processRecord;
                    ProviderInfo providerInfo2 = providerInfo;
                    contentProviderHelper.getClass();
                    int i3 = processRecord2.uid;
                    String str3 = providerInfo2.packageName;
                    int i4 = providerInfo2.applicationInfo.uid;
                    if (contentProviderHelper.mService.validateAssociationAllowedLocked(i3, i4, (String) obj, str3)) {
                        return null;
                    }
                    return providerInfo2.packageName;
                }
            });
        }
        if (str2 != null) {
            throw new SecurityException(BootReceiver$$ExternalSyntheticOutline0.m("Content provider lookup ", str, " failed: association not allowed with package ", str2));
        }
        checkTime(j, "getContentProviderImpl: before checkContentProviderPermission");
        String checkContentProviderPermission = checkContentProviderPermission(providerInfo, Binder.getCallingPid(), Binder.getCallingUid(), i2, z, processRecord != null ? processRecord.toString() : null);
        if (checkContentProviderPermission != null) {
            throw new SecurityException(checkContentProviderPermission);
        }
        checkTime(j, "getContentProviderImpl: after checkContentProviderPermission");
    }

    public final String checkContentProviderPermission(ProviderInfo providerInfo, int i, int i2, int i3, boolean z, String str) {
        boolean canAccessContentProviderFromSdkSandbox;
        int i4;
        String str2;
        boolean z2;
        if (Process.isSdkSandboxUid(i2)) {
            SdkSandboxManagerLocal sdkSandboxManagerLocal = (SdkSandboxManagerLocal) LocalManagerRegistry.getManager(SdkSandboxManagerLocal.class);
            if (sdkSandboxManagerLocal == null) {
                throw new IllegalStateException("SdkSandboxManagerLocal not found when checking whether SDK sandbox uid may access the contentprovider.");
            }
            canAccessContentProviderFromSdkSandbox = sdkSandboxManagerLocal.canAccessContentProviderFromSdkSandbox(providerInfo);
        } else {
            canAccessContentProviderFromSdkSandbox = true;
        }
        if (!canAccessContentProviderFromSdkSandbox) {
            return "ContentProvider access not allowed from sdk sandbox UID. ProviderInfo: " + providerInfo.toString();
        }
        boolean z3 = false;
        ActivityManagerService activityManagerService = this.mService;
        if (z) {
            UserController userController = activityManagerService.mUserController;
            userController.getClass();
            int currentUserId = (i3 == -2 || i3 == -3) ? userController.getCurrentUserId() : i3;
            if (currentUserId == UserHandle.getUserId(i2)) {
                z2 = false;
            } else {
                if (((UriGrantsManagerService.LocalService) activityManagerService.mUgmInternal).checkAuthorityGrants(i2, providerInfo, currentUserId, z)) {
                    return null;
                }
                z2 = true;
            }
            int handleIncomingUser = activityManagerService.mUserController.handleIncomingUser(i, i2, i3, false, 0, "checkContentProviderPermissionLocked " + providerInfo.authority, null);
            i4 = handleIncomingUser;
            if (handleIncomingUser == currentUserId) {
                z3 = z2;
            }
        } else {
            i4 = i3;
        }
        if (ActivityManagerService.checkComponentPermission(i, i2, providerInfo.readPermission, 0, providerInfo.applicationInfo.uid, providerInfo.exported) == 0 || ActivityManagerService.checkComponentPermission(i, i2, providerInfo.writePermission, 0, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
            return null;
        }
        PathPermission[] pathPermissionArr = providerInfo.pathPermissions;
        if (pathPermissionArr != null) {
            int length = pathPermissionArr.length;
            while (length > 0) {
                int i5 = length - 1;
                PathPermission pathPermission = pathPermissionArr[i5];
                String readPermission = pathPermission.getReadPermission();
                if (readPermission != null && ActivityManagerService.checkComponentPermission(i, i2, readPermission, 0, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
                    return null;
                }
                String writePermission = pathPermission.getWritePermission();
                if (writePermission != null && ActivityManagerService.checkComponentPermission(i, i2, writePermission, 0, providerInfo.applicationInfo.uid, providerInfo.exported) == 0) {
                    return null;
                }
                length = i5;
            }
        }
        if (!z3 && ((UriGrantsManagerService.LocalService) activityManagerService.mUgmInternal).checkAuthorityGrants(i2, providerInfo, i4, z)) {
            return null;
        }
        if (!providerInfo.exported) {
            str2 = " that is not exported from UID " + providerInfo.applicationInfo.uid;
        } else if ("android.permission.MANAGE_DOCUMENTS".equals(providerInfo.readPermission)) {
            str2 = " requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs";
        } else {
            str2 = " requires " + providerInfo.readPermission + " or " + providerInfo.writePermission;
        }
        StringBuilder sb = new StringBuilder("Permission Denial: opening provider ");
        sb.append(providerInfo.name);
        sb.append(" from ");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, str != null ? str : "(null)", " (pid=", ", uid=", sb);
        sb.append(i2);
        sb.append(")");
        sb.append(str2);
        String sb2 = sb.toString();
        Slog.w("ContentProviderHelper", sb2);
        return sb2;
    }

    public final boolean cleanupAppInLaunchingProvidersLocked(ProcessRecord processRecord, boolean z) {
        boolean z2 = false;
        for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
            ContentProviderRecord contentProviderRecord = (ContentProviderRecord) this.mLaunchingProviders.get(size);
            if (contentProviderRecord.launchingApp == processRecord) {
                int i = contentProviderRecord.mRestartCount + 1;
                contentProviderRecord.mRestartCount = i;
                if (i > 3) {
                    z = true;
                }
                if (z || processRecord.mErrorState.mBad || (contentProviderRecord.connections.isEmpty() && !contentProviderRecord.hasExternalProcessHandles())) {
                    removeDyingProviderLocked(processRecord, contentProviderRecord, true);
                } else {
                    z2 = true;
                }
            }
        }
        return z2;
    }

    public final boolean decProviderCountLocked(final ContentProviderConnection contentProviderConnection, ContentProviderRecord contentProviderRecord, IBinder iBinder, final boolean z, boolean z2, final boolean z3) {
        int i;
        if (contentProviderConnection == null) {
            contentProviderRecord.removeExternalProcessHandleLocked(iBinder);
            return false;
        }
        synchronized (contentProviderConnection.mLock) {
            i = contentProviderConnection.mStableCount + contentProviderConnection.mUnstableCount;
        }
        if (i > 1) {
            contentProviderConnection.decrementCount(z);
            return false;
        }
        if (z2) {
            BackgroundThread.getHandler().postDelayed(new Runnable() { // from class: com.android.server.am.ContentProviderHelper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ContentProviderHelper.this.handleProviderRemoval(contentProviderConnection, z, z3);
                }
            }, 5000L);
        } else {
            handleProviderRemoval(contentProviderConnection, z, z3);
        }
        return true;
    }

    public final void dumpProvidersLocked(PrintWriter printWriter, String[] strArr, int i, boolean z, String str) {
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        ArrayList arrayList3 = null;
        for (int i2 = i; i2 < strArr.length; i2++) {
            String str2 = strArr[i2];
            if ("--".equals(str2)) {
                break;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
            if (unflattenFromString != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(unflattenFromString);
            } else {
                try {
                    int parseInt = Integer.parseInt(str2, 16);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList();
                    }
                    arrayList2.add(Integer.valueOf(parseInt));
                } catch (RuntimeException unused) {
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList();
                    }
                    arrayList3.add(str2);
                }
            }
        }
        printWriter.println("ACTIVITY MANAGER CONTENT PROVIDERS (dumpsys activity providers)");
        ProviderMap providerMap = this.mProviderMap;
        boolean z2 = false;
        boolean dumpProvidersByClassLocked = providerMap.mSingletonByClass.size() > 0 ? ProviderMap.dumpProvidersByClassLocked(printWriter, z, str, "  Published single-user content providers (by class):", false, providerMap.mSingletonByClass) : false;
        for (int i3 = 0; i3 < providerMap.mProvidersByClassPerUser.size(); i3++) {
            dumpProvidersByClassLocked |= ProviderMap.dumpProvidersByClassLocked(printWriter, z, str, "  Published user " + providerMap.mProvidersByClassPerUser.keyAt(i3) + " content providers (by class):", dumpProvidersByClassLocked, (HashMap) providerMap.mProvidersByClassPerUser.valueAt(i3));
        }
        if (z) {
            dumpProvidersByClassLocked = ProviderMap.dumpProvidersByNameLocked(printWriter, str, "  Single-user authority to provider mappings:", dumpProvidersByClassLocked, providerMap.mSingletonByName) | dumpProvidersByClassLocked;
            for (int i4 = 0; i4 < providerMap.mProvidersByNamePerUser.size(); i4++) {
                dumpProvidersByClassLocked |= ProviderMap.dumpProvidersByNameLocked(printWriter, str, "  User " + providerMap.mProvidersByNamePerUser.keyAt(i4) + " authority to provider mappings:", dumpProvidersByClassLocked, (HashMap) providerMap.mProvidersByNamePerUser.valueAt(i4));
            }
        }
        if (this.mLaunchingProviders.size() > 0) {
            boolean z3 = dumpProvidersByClassLocked;
            for (int size = this.mLaunchingProviders.size() - 1; size >= 0; size--) {
                ContentProviderRecord contentProviderRecord = (ContentProviderRecord) this.mLaunchingProviders.get(size);
                if (str == null || str.equals(contentProviderRecord.name.getPackageName())) {
                    if (!z2) {
                        if (z3) {
                            printWriter.println();
                        }
                        printWriter.println("  Launching content providers:");
                        z3 = true;
                        z2 = true;
                        dumpProvidersByClassLocked = true;
                    }
                    printWriter.print("  Launching #");
                    printWriter.print(size);
                    printWriter.print(": ");
                    printWriter.println(contentProviderRecord);
                }
            }
        }
        if (dumpProvidersByClassLocked) {
            return;
        }
        printWriter.println("  (nothing)");
    }

    public final List generateApplicationProvidersLocked(ProcessRecord processRecord) {
        try {
            List list = AppGlobals.getPackageManager().queryContentProviders(processRecord.processName, processRecord.uid, 268438528L, (String) null).getList();
            if (list == null) {
                return null;
            }
            int size = list.size();
            ProcessProviderRecord processProviderRecord = processRecord.mProviders;
            processProviderRecord.mPubProviders.ensureCapacity(processProviderRecord.mPubProviders.size() + size);
            int i = 0;
            while (i < size) {
                ProviderInfo providerInfo = (ProviderInfo) list.get(i);
                ActivityManagerService activityManagerService = this.mService;
                String str = providerInfo.processName;
                ApplicationInfo applicationInfo = providerInfo.applicationInfo;
                String str2 = providerInfo.name;
                int i2 = providerInfo.flags;
                activityManagerService.getClass();
                boolean isSingleton = ActivityManagerService.isSingleton(str, applicationInfo, str2, i2);
                if (!isSingletonOrSystemUserOnly(providerInfo) || processRecord.userId == 0) {
                    boolean isInstantApp = providerInfo.applicationInfo.isInstantApp();
                    String str3 = providerInfo.splitName;
                    boolean z = str3 == null || ArrayUtils.contains(providerInfo.applicationInfo.splitNames, str3);
                    if (!isInstantApp || z) {
                        ComponentName componentName = new ComponentName(providerInfo.packageName, providerInfo.name);
                        ContentProviderRecord providerByClass = this.mProviderMap.getProviderByClass(processRecord.userId, componentName);
                        if (providerByClass == null) {
                            ContentProviderRecord contentProviderRecord = new ContentProviderRecord(this.mService, providerInfo, processRecord.info, componentName, isSingleton);
                            this.mProviderMap.putProviderByClass(componentName, contentProviderRecord);
                            providerByClass = contentProviderRecord;
                        }
                        processProviderRecord.mPubProviders.put(providerInfo.name, providerByClass);
                        if (!providerInfo.multiprocess || !"android".equals(providerInfo.packageName)) {
                            ApplicationInfo applicationInfo2 = providerInfo.applicationInfo;
                            processRecord.addPackage(applicationInfo2.packageName, applicationInfo2.longVersionCode, this.mService.mProcessStats);
                        }
                        this.mService.notifyPackageUse(providerInfo.applicationInfo.packageName, 4);
                        i++;
                    } else {
                        list.remove(i);
                    }
                } else {
                    list.remove(i);
                }
                size--;
                i--;
                i++;
            }
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (RemoteException unused) {
            return null;
        }
    }

    public final ContentProviderHolder getContentProvider(IApplicationThread iApplicationThread, String str, String str2, int i, boolean z) {
        ActivityManagerService activityManagerService = this.mService;
        activityManagerService.getClass();
        ActivityManagerService.enforceNotIsolatedCaller("getContentProvider");
        if (iApplicationThread != null) {
            int callingUid = Binder.getCallingUid();
            if (str == null || activityManagerService.mAppOpsService.checkPackage(callingUid, str) == 0) {
                return getContentProviderImpl(iApplicationThread, str2, null, callingUid, str, null, z, i, -1);
            }
            throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Given calling package ", str, " does not match caller's uid "));
        }
        String str3 = "null IApplicationThread when getting content provider " + str2;
        Slog.w("ContentProviderHelper", str3);
        throw new SecurityException(str3);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:240|(5:242|(1:244)|258|259|260)(1:382)|245|246|247|248|(1:250)|251|(1:253)|258|259|260) */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x03ea, code lost:
    
        if (r12 == 1000) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x071a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x071c, code lost:
    
        android.util.Slog.e("ContentProviderHelper", "Failed to schedule install provider " + r4.processName + ", " + r12.authority, r0);
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0410 A[Catch: all -> 0x023d, TRY_ENTER, TRY_LEAVE, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0457  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x049d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x05ad A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x063e A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x065c A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x066a A[Catch: all -> 0x023d, TRY_LEAVE, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:263:0x08d5 A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:266:0x08e5 A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0919 A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:273:0x092a A[Catch: all -> 0x023d, TRY_LEAVE, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x097f A[Catch: all -> 0x023d, TRY_LEAVE, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0a35 A[Catch: all -> 0x023d, TRY_LEAVE, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0ad6  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0ad7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:381:0x08eb A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:412:0x08bb  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0651  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0631  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0148 A[Catch: all -> 0x005b, TryCatch #23 {all -> 0x005b, blocks: (B:519:0x002c, B:10:0x0067, B:11:0x006b, B:19:0x0094, B:30:0x00da, B:32:0x00e3, B:36:0x00fe, B:39:0x0107, B:44:0x0148, B:46:0x014c, B:48:0x0152, B:50:0x0156, B:52:0x0183, B:58:0x0196, B:60:0x01a0, B:62:0x01a4, B:64:0x01c0, B:65:0x01c9, B:67:0x01db, B:68:0x01e4, B:70:0x01f4, B:485:0x0105, B:486:0x011c, B:489:0x0124, B:501:0x00b9, B:503:0x00c3, B:505:0x00c9, B:515:0x007c, B:522:0x0035, B:523:0x005a, B:14:0x006e, B:15:0x0076), top: B:518:0x002c, inners: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:450:0x0459 A[Catch: all -> 0x023d, TryCatch #11 {all -> 0x023d, blocks: (B:82:0x0217, B:83:0x0238, B:493:0x0b51, B:89:0x024c, B:92:0x0252, B:94:0x0258, B:98:0x0265, B:112:0x034a, B:113:0x034d, B:118:0x0390, B:122:0x03b9, B:125:0x03c7, B:126:0x03d6, B:128:0x03dc, B:134:0x0410, B:138:0x041a, B:140:0x042d, B:143:0x0437, B:148:0x044e, B:151:0x0461, B:153:0x0488, B:156:0x0494, B:157:0x049b, B:158:0x049c, B:163:0x04d6, B:165:0x04e3, B:166:0x051b, B:169:0x0520, B:171:0x0524, B:173:0x0530, B:175:0x0534, B:177:0x0550, B:178:0x0559, B:180:0x0579, B:183:0x0580, B:185:0x059f, B:191:0x05ad, B:193:0x05bb, B:203:0x05f5, B:204:0x05f8, B:208:0x061f, B:209:0x0636, B:211:0x063e, B:213:0x0646, B:214:0x064c, B:217:0x0653, B:219:0x065c, B:221:0x0665, B:225:0x066a, B:260:0x08b3, B:261:0x08cb, B:263:0x08d5, B:264:0x08dc, B:266:0x08e5, B:267:0x08fa, B:269:0x0919, B:270:0x091c, B:271:0x0924, B:273:0x092a, B:277:0x095f, B:279:0x097f, B:287:0x09d9, B:297:0x0a2a, B:298:0x0a2e, B:304:0x0a34, B:305:0x0a35, B:381:0x08eb, B:395:0x0875, B:396:0x0878, B:410:0x08b7, B:411:0x08ba, B:416:0x062d, B:422:0x0629, B:423:0x062c, B:443:0x0947, B:450:0x0459, B:452:0x0435, B:456:0x03f1, B:473:0x039a, B:474:0x039d, B:100:0x0296, B:102:0x02e9, B:103:0x02f7, B:105:0x02ff, B:108:0x0308, B:110:0x0319, B:117:0x0354, B:467:0x0361, B:197:0x05c0, B:200:0x05d4, B:202:0x05e0, B:207:0x0601, B:227:0x066e, B:229:0x067a, B:231:0x068c, B:232:0x06cc, B:234:0x06e2, B:236:0x06e6, B:240:0x06ee, B:242:0x06f8, B:245:0x0709, B:247:0x0716, B:248:0x073e, B:250:0x0744, B:251:0x0761, B:253:0x0765, B:257:0x071c, B:258:0x0784, B:259:0x08ac, B:385:0x07e9, B:388:0x07f5, B:392:0x0814, B:394:0x0840, B:400:0x0881, B:407:0x06aa, B:281:0x0980, B:283:0x0984, B:285:0x0988, B:286:0x09d8, B:292:0x09e5, B:294:0x0a10, B:295:0x0a13, B:296:0x0a29), top: B:3:0x001e, inners: #7, #13, #18, #25 }] */
    /* JADX WARN: Removed duplicated region for block: B:466:0x094a  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0183 A[Catch: all -> 0x005b, TRY_LEAVE, TryCatch #23 {all -> 0x005b, blocks: (B:519:0x002c, B:10:0x0067, B:11:0x006b, B:19:0x0094, B:30:0x00da, B:32:0x00e3, B:36:0x00fe, B:39:0x0107, B:44:0x0148, B:46:0x014c, B:48:0x0152, B:50:0x0156, B:52:0x0183, B:58:0x0196, B:60:0x01a0, B:62:0x01a4, B:64:0x01c0, B:65:0x01c9, B:67:0x01db, B:68:0x01e4, B:70:0x01f4, B:485:0x0105, B:486:0x011c, B:489:0x0124, B:501:0x00b9, B:503:0x00c3, B:505:0x00c9, B:515:0x007c, B:522:0x0035, B:523:0x005a, B:14:0x006e, B:15:0x0076), top: B:518:0x002c, inners: #9 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x018e A[Catch: all -> 0x0241, TRY_ENTER, TRY_LEAVE, TryCatch #14 {all -> 0x0241, blocks: (B:4:0x001e, B:17:0x0080, B:22:0x009d, B:55:0x018e, B:78:0x01fc, B:80:0x0202, B:498:0x00ab), top: B:3:0x001e }] */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25, types: [int] */
    /* JADX WARN: Type inference failed for: r4v68 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:434:0x0949 -> B:435:0x0945). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.ContentProviderHolder getContentProviderImpl(android.app.IApplicationThread r65, java.lang.String r66, android.os.IBinder r67, int r68, java.lang.String r69, java.lang.String r70, boolean r71, int r72, int r73) {
        /*
            Method dump skipped, instructions count: 2902
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ContentProviderHelper.getContentProviderImpl(android.app.IApplicationThread, java.lang.String, android.os.IBinder, int, java.lang.String, java.lang.String, boolean, int, int):android.app.ContentProviderHolder");
    }

    public final void handleProviderRemoval(ContentProviderConnection contentProviderConnection, boolean z, boolean z2) {
        ProcessRecord processRecord;
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            if (contentProviderConnection != null) {
                try {
                    if (contentProviderConnection.provider != null && contentProviderConnection.decrementCount(z) == 0) {
                        ContentProviderRecord contentProviderRecord = contentProviderConnection.provider;
                        if (contentProviderConnection.association != null) {
                            synchronized (contentProviderConnection.mProcStatsLock) {
                                contentProviderConnection.association.stop();
                            }
                            contentProviderConnection.association = null;
                        }
                        contentProviderRecord.connections.remove(contentProviderConnection);
                        ProcessRecord processRecord2 = contentProviderRecord.proc;
                        if (processRecord2 != null && !hasProviderConnectionLocked(processRecord2)) {
                            contentProviderRecord.proc.mProfile.clearHostingComponentType(64);
                        }
                        contentProviderConnection.client.mProviders.mConProviders.remove(contentProviderConnection);
                        if (contentProviderConnection.client.mState.mSetProcState < 15 && (processRecord = contentProviderRecord.proc) != null) {
                            processRecord.mProviders.mLastProviderTime = SystemClock.uptimeMillis();
                        }
                        ActivityManagerService activityManagerService2 = this.mService;
                        ProcessRecord processRecord3 = contentProviderConnection.client;
                        int i = processRecord3.uid;
                        String str = processRecord3.processName;
                        int i2 = contentProviderRecord.uid;
                        long j = contentProviderRecord.appInfo.longVersionCode;
                        ComponentName componentName = contentProviderRecord.name;
                        String str2 = contentProviderRecord.info.processName;
                        activityManagerService2.stopAssociationLocked(componentName, str, i, i2);
                        if (z2) {
                            Flags.serviceBindingOomAdjPolicy();
                            OomAdjuster oomAdjuster = this.mService.mOomAdjuster;
                            ProcessRecord processRecord4 = contentProviderConnection.client;
                            ProcessRecord processRecord5 = contentProviderRecord.proc;
                            oomAdjuster.getClass();
                            if (!OomAdjuster.evaluateConnectionPrelude(processRecord4, processRecord5)) {
                                ProcessStateRecord processStateRecord = processRecord5.mState;
                                int i3 = processStateRecord.mSetAdj;
                                ProcessStateRecord processStateRecord2 = processRecord4.mState;
                                if (i3 < processStateRecord2.mSetAdj && processStateRecord.mSetProcState < processStateRecord2.mSetProcState) {
                                }
                            }
                            this.mService.updateOomAdjLocked(8, contentProviderConnection.provider.proc);
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            ActivityManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final ContentProviderConnection incProviderCountLocked(ProcessRecord processRecord, ContentProviderRecord contentProviderRecord, IBinder iBinder, int i, String str, String str2, boolean z, boolean z2, long j, ProcessList processList, int i2) {
        if (processRecord == null) {
            if (iBinder == null) {
                contentProviderRecord.externalProcessNoHandleCount++;
            } else {
                if (contentProviderRecord.externalProcessTokenToHandle == null) {
                    contentProviderRecord.externalProcessTokenToHandle = new ArrayMap();
                }
                ContentProviderRecord.ExternalProcessHandle externalProcessHandle = (ContentProviderRecord.ExternalProcessHandle) contentProviderRecord.externalProcessTokenToHandle.get(iBinder);
                if (externalProcessHandle == null) {
                    externalProcessHandle = contentProviderRecord.new ExternalProcessHandle(iBinder, i, str2);
                    contentProviderRecord.externalProcessTokenToHandle.put(iBinder, externalProcessHandle);
                    externalProcessHandle.startAssociationIfNeeded(contentProviderRecord);
                }
                externalProcessHandle.mAcquisitionCount++;
            }
            return null;
        }
        ProcessProviderRecord processProviderRecord = processRecord.mProviders;
        int size = processProviderRecord.mConProviders.size();
        for (int i3 = 0; i3 < size; i3++) {
            ContentProviderConnection contentProviderConnection = (ContentProviderConnection) processProviderRecord.mConProviders.get(i3);
            if (contentProviderConnection.provider == contentProviderRecord) {
                synchronized (contentProviderConnection.mLock) {
                    try {
                        if (z) {
                            contentProviderConnection.mStableCount++;
                            contentProviderConnection.mNumStableIncs++;
                        } else {
                            contentProviderConnection.mUnstableCount++;
                            contentProviderConnection.mNumUnstableIncs++;
                        }
                    } finally {
                    }
                }
                return contentProviderConnection;
            }
        }
        ContentProviderConnection contentProviderConnection2 = new ContentProviderConnection(contentProviderRecord, processRecord, str, i2);
        contentProviderConnection2.startAssociationIfNeeded();
        synchronized (contentProviderConnection2.mLock) {
            try {
                if (z) {
                    contentProviderConnection2.mStableCount = 1;
                    contentProviderConnection2.mNumStableIncs = 1;
                    contentProviderConnection2.mUnstableCount = 0;
                    contentProviderConnection2.mNumUnstableIncs = 0;
                } else {
                    contentProviderConnection2.mStableCount = 0;
                    contentProviderConnection2.mNumStableIncs = 0;
                    contentProviderConnection2.mUnstableCount = 1;
                    contentProviderConnection2.mNumUnstableIncs = 1;
                }
            } finally {
            }
        }
        contentProviderRecord.connections.add(contentProviderConnection2);
        ProcessRecord processRecord2 = contentProviderRecord.proc;
        if (processRecord2 != null) {
            processRecord2.mProfile.addHostingComponentType(64);
        }
        processProviderRecord.mConProviders.add(contentProviderConnection2);
        ActivityManagerService activityManagerService = this.mService;
        int i4 = processRecord.uid;
        String str3 = processRecord.processName;
        int i5 = processRecord.mState.mCurProcState;
        int i6 = contentProviderRecord.uid;
        long j2 = contentProviderRecord.appInfo.longVersionCode;
        activityManagerService.startAssociationLocked(i4, str3, i5, i6, contentProviderRecord.name, contentProviderRecord.info.processName);
        if (z2 && contentProviderRecord.proc != null && processRecord.mState.mSetAdj <= 250) {
            checkTime(j, "getContentProviderImpl: before updateLruProcess");
            processList.updateLruProcessLocked(contentProviderRecord.proc, null, false);
            checkTime(j, "getContentProviderImpl: after updateLruProcess");
        }
        return contentProviderConnection2;
    }

    public final void installSystemProviders() {
        List generateApplicationProvidersLocked;
        String[] strArr;
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                generateApplicationProvidersLocked = generateApplicationProvidersLocked((ProcessRecord) this.mService.mProcessList.mProcessNames.get("system", 1000));
                if (generateApplicationProvidersLocked != null) {
                    for (int size = generateApplicationProvidersLocked.size() - 1; size >= 0; size--) {
                        ProviderInfo providerInfo = (ProviderInfo) generateApplicationProvidersLocked.get(size);
                        if ((providerInfo.applicationInfo.flags & 1) == 0) {
                            Slog.w("ContentProviderHelper", "Not installing system proc provider " + providerInfo.name + ": not system .apk");
                            generateApplicationProvidersLocked.remove(size);
                        }
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        if (generateApplicationProvidersLocked != null) {
            this.mService.mSystemThread.installSystemProviders(generateApplicationProvidersLocked);
        }
        synchronized (this) {
            this.mSystemProvidersInstalled = true;
        }
        ActivityManagerService activityManagerService2 = this.mService;
        ActivityManagerConstants activityManagerConstants = activityManagerService2.mConstants;
        ContentResolver contentResolver = activityManagerService2.mContext.getContentResolver();
        activityManagerConstants.mResolver = contentResolver;
        contentResolver.registerContentObserver(ActivityManagerConstants.ACTIVITY_MANAGER_CONSTANTS_URI, false, activityManagerConstants);
        activityManagerConstants.mResolver.registerContentObserver(ActivityManagerConstants.ACTIVITY_STARTS_LOGGING_ENABLED_URI, false, activityManagerConstants);
        activityManagerConstants.mResolver.registerContentObserver(ActivityManagerConstants.FOREGROUND_SERVICE_STARTS_LOGGING_ENABLED_URI, false, activityManagerConstants);
        if (activityManagerConstants.mSystemServerAutomaticHeapDumpEnabled) {
            activityManagerConstants.mResolver.registerContentObserver(ActivityManagerConstants.ENABLE_AUTOMATIC_SYSTEM_SERVER_HEAP_DUMPS_URI, false, activityManagerConstants);
        }
        activityManagerConstants.mResolver.registerContentObserver(ActivityManagerConstants.FORCE_ENABLE_PSS_PROFILING_URI, false, activityManagerConstants);
        activityManagerConstants.updateConstants();
        if (activityManagerConstants.mSystemServerAutomaticHeapDumpEnabled) {
            activityManagerConstants.updateEnableAutomaticSystemServerHeapDumps();
        }
        DeviceConfig.addOnPropertiesChangedListener("activity_manager", ActivityThread.currentApplication().getMainExecutor(), activityManagerConstants.mOnDeviceConfigChangedListener);
        DeviceConfig.addOnPropertiesChangedListener("activity_manager_ca", ActivityThread.currentApplication().getMainExecutor(), activityManagerConstants.mOnDeviceConfigChangedForComponentAliasListener);
        activityManagerConstants.mOnDeviceConfigChangedListener.onPropertiesChanged(DeviceConfig.getProperties("activity_manager", new String[0]));
        activityManagerConstants.mOnDeviceConfigChangedForComponentAliasListener.onPropertiesChanged(DeviceConfig.getProperties("activity_manager_ca", new String[0]));
        activityManagerConstants.mFlagActivityStartsLoggingEnabled = Settings.Global.getInt(activityManagerConstants.mResolver, "activity_starts_logging_enabled", 1) == 1;
        Settings.Global.getInt(activityManagerConstants.mResolver, "foreground_service_starts_logging_enabled", 1);
        activityManagerConstants.mForceEnablePssProfiling = Settings.Global.getInt(activityManagerConstants.mResolver, "force_enable_pss_profiling", 0) == 1;
        activityManagerConstants.mService.mDropboxRateLimiter.init();
        this.mService.mCoreSettingsObserver = new CoreSettingsObserver(this.mService);
        ActivityTaskManagerService activityTaskManagerService = this.mService.mActivityTaskManager;
        activityTaskManagerService.getClass();
        activityTaskManagerService.new SettingObserver();
        new DevelopmentSettingsObserver();
        new SettingsToPropertiesMapper(this.mService.mContext.getContentResolver(), SettingsToPropertiesMapper.sGlobalSettings, SettingsToPropertiesMapper.sDeviceConfigScopes, SettingsToPropertiesMapper.sDeviceConfigAconfigScopes).updatePropertiesFromSettings();
        final OomAdjuster oomAdjuster = this.mService.mOomAdjuster;
        CachedAppOptimizer cachedAppOptimizer = oomAdjuster.mCachedAppOptimizer;
        cachedAppOptimizer.getClass();
        DeviceConfig.addOnPropertiesChangedListener("activity_manager", ActivityThread.currentApplication().getMainExecutor(), cachedAppOptimizer.mOnFlagsChangedListener);
        DeviceConfig.addOnPropertiesChangedListener("activity_manager_native_boot", ActivityThread.currentApplication().getMainExecutor(), cachedAppOptimizer.mOnNativeBootFlagsChangedListener);
        cachedAppOptimizer.mAm.mContext.getContentResolver().registerContentObserver(CachedAppOptimizer.CACHED_APP_FREEZER_ENABLED_URI, false, cachedAppOptimizer.mSettingsObserver);
        synchronized (cachedAppOptimizer.mPhenotypeFlagLock) {
            cachedAppOptimizer.updateUseCompaction();
            cachedAppOptimizer.updateCompactionThrottles();
            cachedAppOptimizer.mCompactStatsdSampleRate = DeviceConfig.getFloat("activity_manager", "compact_statsd_sample_rate", 0.1f);
            cachedAppOptimizer.mCompactStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer.mCompactStatsdSampleRate));
            cachedAppOptimizer.mFreezerStatsdSampleRate = DeviceConfig.getFloat("activity_manager", "freeze_statsd_sample_rate", 0.1f);
            cachedAppOptimizer.mFreezerStatsdSampleRate = Math.min(1.0f, Math.max(FullScreenMagnificationGestureHandler.MAX_SCALE, cachedAppOptimizer.mFreezerStatsdSampleRate));
            cachedAppOptimizer.mFullAnonRssThrottleKb = DeviceConfig.getLong("activity_manager", "compact_full_rss_throttle_kb", 12000L);
            if (cachedAppOptimizer.mFullAnonRssThrottleKb < 0) {
                cachedAppOptimizer.mFullAnonRssThrottleKb = 12000L;
            }
            cachedAppOptimizer.mFullDeltaRssThrottleKb = DeviceConfig.getLong("activity_manager", "compact_full_delta_rss_throttle_kb", 8000L);
            if (cachedAppOptimizer.mFullDeltaRssThrottleKb < 0) {
                cachedAppOptimizer.mFullDeltaRssThrottleKb = 8000L;
            }
            cachedAppOptimizer.updateProcStateThrottle();
            cachedAppOptimizer.updateUseFreezer();
            cachedAppOptimizer.mCompactThrottleMinOomAdj = DeviceConfig.getLong("activity_manager", "compact_throttle_min_oom_adj", 850L);
            if (cachedAppOptimizer.mCompactThrottleMinOomAdj < 850) {
                cachedAppOptimizer.mCompactThrottleMinOomAdj = 850L;
            }
            cachedAppOptimizer.mCompactThrottleMaxOomAdj = DeviceConfig.getLong("activity_manager", "compact_throttle_max_oom_adj", 999L);
            if (cachedAppOptimizer.mCompactThrottleMaxOomAdj > 999) {
                cachedAppOptimizer.mCompactThrottleMaxOomAdj = 999L;
            }
        }
        CacheOomRanker cacheOomRanker = oomAdjuster.mCacheOomRanker;
        DeviceConfig.addOnPropertiesChangedListener("activity_manager", ActivityThread.currentApplication().getMainExecutor(), cacheOomRanker.mOnFlagsChangedListener);
        synchronized (cacheOomRanker.mPhenotypeFlagLock) {
            cacheOomRanker.mUseOomReRanking = DeviceConfig.getBoolean("activity_manager", "use_oom_re_ranking", false);
            cacheOomRanker.updateNumberToReRank();
            cacheOomRanker.mLruWeight = DeviceConfig.getFloat("activity_manager", "oom_re_ranking_lru_weight", 0.35f);
            cacheOomRanker.mUsesWeight = DeviceConfig.getFloat("activity_manager", "oom_re_ranking_uses_weight", 0.5f);
            cacheOomRanker.mRssWeight = DeviceConfig.getFloat("activity_manager", "oom_re_ranking_rss_weight", 0.15f);
        }
        if (oomAdjuster.mService.mConstants.KEEP_WARMING_SERVICES.size() > 0) {
            oomAdjuster.mService.mContext.registerReceiverForAllUsers(new BroadcastReceiver() { // from class: com.android.server.am.OomAdjuster.1
                public AnonymousClass1() {
                }

                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ActivityManagerService activityManagerService3 = OomAdjuster.this.mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService3) {
                        try {
                            OomAdjuster.this.handleUserSwitchedLocked();
                        } catch (Throwable th2) {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                            throw th2;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                }
            }, new IntentFilter("android.intent.action.USER_SWITCHED"), null, oomAdjuster.mService.mHandler);
        }
        Context context = this.mService.mContext;
        int i = RescueParty.LEVEL_ISRB_BOOT;
        if ("true".equals(SystemProperties.get("device_config.reset_performed"))) {
            if ("true".equals(SystemProperties.get("device_config.reset_performed"))) {
                String resetFlagsFileContent = SettingsToPropertiesMapper.getResetFlagsFileContent();
                if (TextUtils.isEmpty(resetFlagsFileContent)) {
                    strArr = new String[0];
                } else {
                    String[] split = resetFlagsFileContent.split(";");
                    HashSet hashSet = new HashSet();
                    for (String str : split) {
                        String[] split2 = str.split("\\.");
                        if (split2.length < 3) {
                            SettingsToPropertiesMapper.logErr("failed to extract category name from property ".concat(str));
                        } else {
                            hashSet.add(split2[2]);
                        }
                    }
                    strArr = (String[]) hashSet.toArray(new String[0]);
                }
            } else {
                strArr = new String[0];
            }
            for (int i2 = 0; i2 < strArr.length; i2++) {
                if (!"configuration".equals(strArr[i2])) {
                    DeviceConfig.resetToDefaults(4, strArr[i2]);
                }
            }
        }
        ContentResolver contentResolver2 = context.getContentResolver();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        RescueParty.RescuePartyMonitorCallback rescuePartyMonitorCallback = new RescueParty.RescuePartyMonitorCallback();
        rescuePartyMonitorCallback.mContext = context;
        DeviceConfig.setMonitorCallback(contentResolver2, newSingleThreadExecutor, rescuePartyMonitorCallback);
    }

    public final boolean isAuthorityRedirectedForCloneProfileCached(String str) {
        if (((HashMap) this.mCloneProfileAuthorityRedirectionCache).containsKey(str)) {
            Boolean bool = (Boolean) ((HashMap) this.mCloneProfileAuthorityRedirectionCache).get(str);
            if (bool == null) {
                return false;
            }
            return bool.booleanValue();
        }
        boolean isAuthorityRedirectedForCloneProfile = ContentProvider.isAuthorityRedirectedForCloneProfile(str);
        ((HashMap) this.mCloneProfileAuthorityRedirectionCache).put(str, Boolean.valueOf(isAuthorityRedirectedForCloneProfile));
        return isAuthorityRedirectedForCloneProfile;
    }

    public final boolean isHolderVisibleToCaller(ContentProviderHolder contentProviderHolder, int i, int i2) {
        ProviderInfo providerInfo;
        if (contentProviderHolder == null || (providerInfo = contentProviderHolder.info) == null) {
            return false;
        }
        boolean isAuthorityRedirectedForCloneProfileCached = isAuthorityRedirectedForCloneProfileCached(providerInfo.authority);
        ActivityManagerService activityManagerService = this.mService;
        if (isAuthorityRedirectedForCloneProfileCached) {
            UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            UserInfo userInfo = userManagerInternal.getUserInfo(i2);
            if (((userInfo == null || !userInfo.isCloneProfile()) ? i2 : userManagerInternal.getProfileParentId(i2)) != i2) {
                return !activityManagerService.getPackageManagerInternal().filterAppAccess(i, i2, contentProviderHolder.info.packageName, false);
            }
        }
        return !activityManagerService.getPackageManagerInternal().filterAppAccess(i, i2, contentProviderHolder.info.packageName, true);
    }

    public final boolean isProcessAliveLocked(ProcessRecord processRecord) {
        int i = processRecord.mPid;
        if (i <= 0) {
            return false;
        }
        String m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "/proc/", "/stat");
        long[] jArr = this.mProcessStateStatsLongs;
        jArr[0] = 0;
        if (!Process.readProcFile(m, PROCESS_STATE_STATS_FORMAT, null, jArr, null)) {
            return false;
        }
        long j = jArr[0];
        return (j == 90 || j == 88 || j == 120 || j == 75 || Process.getUidForPid(i) != processRecord.uid) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0016, code lost:
    
        if ((r0 & 536870912) != 0) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSingletonOrSystemUserOnly(android.content.pm.ProviderInfo r4) {
        /*
            r3 = this;
            boolean r0 = android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders()
            com.android.server.am.ActivityManagerService r3 = r3.mService
            if (r0 == 0) goto L19
            int r0 = r4.flags
            r3.getClass()
            boolean r1 = android.multiuser.Flags.enableSystemUserOnlyForServicesAndProviders()
            if (r1 == 0) goto L19
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r0 & r1
            if (r0 == 0) goto L19
            goto L2a
        L19:
            java.lang.String r0 = r4.processName
            android.content.pm.ApplicationInfo r1 = r4.applicationInfo
            java.lang.String r2 = r4.name
            int r4 = r4.flags
            r3.getClass()
            boolean r3 = com.android.server.am.ActivityManagerService.isSingleton(r0, r1, r2, r4)
            if (r3 == 0) goto L2c
        L2a:
            r3 = 1
            goto L2d
        L2c:
            r3 = 0
        L2d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ContentProviderHelper.isSingletonOrSystemUserOnly(android.content.pm.ProviderInfo):boolean");
    }

    public final void maybeUpdateProviderUsageStatsLocked(ProcessRecord processRecord, String str, String str2) {
        UserState startedUserState;
        if (processRecord == null || processRecord.mState.mCurProcState > 6 || (startedUserState = this.mService.mUserController.getStartedUserState(processRecord.userId)) == null) {
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Long l = (Long) startedUserState.mProviderLastReportedFg.get(str2);
        if (l == null || l.longValue() < elapsedRealtime - 60000) {
            if (this.mService.mSystemReady) {
                UsageStatsService.this.mAppStandby.postReportContentProviderUsage(str2, str, processRecord.userId);
            }
            startedUserState.mProviderLastReportedFg.put(str2, Long.valueOf(elapsedRealtime));
        }
    }

    public final void publishContentProviders(IApplicationThread iApplicationThread, List list) {
        ActivityManagerService activityManagerService;
        ProviderInfo providerInfo;
        ProviderInfo providerInfo2;
        if (list == null) {
            return;
        }
        this.mService.getClass();
        ActivityManagerService.enforceNotIsolatedCaller("publishContentProviders");
        if (Process.isSdkSandboxUid(Binder.getCallingUid())) {
            throw new SecurityException("SDK sandbox process not allowed to call publishContentProviders");
        }
        ActivityManagerService activityManagerService2 = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService2) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                ProcessRecord recordForAppLOSP = this.mService.getRecordForAppLOSP(iApplicationThread);
                if (recordForAppLOSP == null) {
                    throw new SecurityException("Unable to find app for caller " + iApplicationThread + " (pid=" + Binder.getCallingPid() + ") when publishing content providers");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                int size = list.size();
                int i = 0;
                boolean z = false;
                while (i < size) {
                    ContentProviderHolder contentProviderHolder = (ContentProviderHolder) list.get(i);
                    if (contentProviderHolder != null && (providerInfo2 = contentProviderHolder.info) != null && contentProviderHolder.provider != null) {
                        ContentProviderRecord contentProviderRecord = (ContentProviderRecord) recordForAppLOSP.mProviders.mPubProviders.get(providerInfo2.name);
                        if (contentProviderRecord != null) {
                            ProviderInfo providerInfo3 = contentProviderRecord.info;
                            this.mProviderMap.putProviderByClass(new ComponentName(providerInfo3.packageName, providerInfo3.name), contentProviderRecord);
                            for (String str : contentProviderRecord.info.authority.split(";")) {
                                ProviderMap providerMap = this.mProviderMap;
                                providerMap.getClass();
                                if (contentProviderRecord.singleton) {
                                    providerMap.mSingletonByName.put(str, contentProviderRecord);
                                } else {
                                    providerMap.getProvidersByName(UserHandle.getUserId(contentProviderRecord.appInfo.uid)).put(str, contentProviderRecord);
                                }
                            }
                            int size2 = this.mLaunchingProviders.size();
                            int i2 = 0;
                            boolean z2 = false;
                            while (i2 < size2) {
                                if (this.mLaunchingProviders.get(i2) == contentProviderRecord) {
                                    this.mLaunchingProviders.remove(i2);
                                    i2--;
                                    size2--;
                                    z2 = true;
                                }
                                i2++;
                            }
                            if (z2) {
                                this.mService.mHandler.removeMessages(73, contentProviderRecord);
                                this.mService.mHandler.removeMessages(57, recordForAppLOSP);
                            }
                            ApplicationInfo applicationInfo = contentProviderRecord.info.applicationInfo;
                            activityManagerService = activityManagerService2;
                            recordForAppLOSP.addPackage(applicationInfo.packageName, applicationInfo.longVersionCode, this.mService.mProcessStats);
                            synchronized (contentProviderRecord) {
                                contentProviderRecord.provider = contentProviderHolder.provider;
                                contentProviderRecord.setProcess(recordForAppLOSP);
                                contentProviderRecord.notifyAll();
                                contentProviderRecord.onProviderPublishStatusLocked(true);
                            }
                            contentProviderRecord.mRestartCount = 0;
                            if (hasProviderConnectionLocked(recordForAppLOSP)) {
                                recordForAppLOSP.mProfile.addHostingComponentType(64);
                            }
                            z = true;
                            i++;
                            activityManagerService2 = activityManagerService;
                        }
                    }
                    activityManagerService = activityManagerService2;
                    i++;
                    activityManagerService2 = activityManagerService;
                }
                ActivityManagerService activityManagerService3 = activityManagerService2;
                if (z) {
                    this.mService.updateOomAdjLocked(7, recordForAppLOSP);
                    int size3 = list.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        ContentProviderHolder contentProviderHolder2 = (ContentProviderHolder) list.get(i3);
                        if (contentProviderHolder2 != null && (providerInfo = contentProviderHolder2.info) != null && contentProviderHolder2.provider != null) {
                            maybeUpdateProviderUsageStatsLocked(recordForAppLOSP, providerInfo.packageName, providerInfo.authority);
                        }
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th2) {
                th = th2;
                activityManagerService = activityManagerService2;
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void removeContentProviderExternalUnchecked(int i, IBinder iBinder, String str) {
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                ContentProviderRecord providerByName = this.mProviderMap.getProviderByName(i, str);
                if (providerByName == null) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                ProviderInfo providerInfo = providerByName.info;
                ContentProviderRecord providerByClass = this.mProviderMap.getProviderByClass(i, new ComponentName(providerInfo.packageName, providerInfo.name));
                if (!providerByClass.hasExternalProcessHandles()) {
                    Slog.e("ContentProviderHelper", "Attempt to remove content provider: " + providerByClass + " with no external references.");
                } else if (providerByClass.removeExternalProcessHandleLocked(iBinder)) {
                    this.mService.updateOomAdjLocked(8, providerByClass.proc);
                } else {
                    Slog.e("ContentProviderHelper", "Attempt to remove content provider " + providerByClass + " with no external reference for token: " + iBinder + ".");
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean removeDyingProviderLocked(com.android.server.am.ProcessRecord r18, com.android.server.am.ContentProviderRecord r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.ContentProviderHelper.removeDyingProviderLocked(com.android.server.am.ProcessRecord, com.android.server.am.ContentProviderRecord, boolean):boolean");
    }

    public final boolean requestTargetProviderPermissionsReviewIfNeededLocked(ProviderInfo providerInfo, ProcessRecord processRecord, int i, Context context) {
        ActivityManagerService activityManagerService = this.mService;
        if (!activityManagerService.getPackageManagerInternal().isPermissionsReviewRequired(i, providerInfo.packageName)) {
            return true;
        }
        if (processRecord != null && processRecord.mState.mSetSchedGroup <= 0) {
            ProfileService$1$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m(i, "u", " Instantiating a provider in package "), providerInfo.packageName, " requires a permissions review", "ContentProviderHelper");
            return false;
        }
        Intent m = BatteryService$$ExternalSyntheticOutline0.m(276824064, "android.intent.action.REVIEW_PERMISSIONS");
        m.putExtra("android.intent.extra.PACKAGE_NAME", providerInfo.packageName);
        activityManagerService.mHandler.post(new StartActivityRunnable(context, m, new UserHandle(i)));
        return false;
    }
}
