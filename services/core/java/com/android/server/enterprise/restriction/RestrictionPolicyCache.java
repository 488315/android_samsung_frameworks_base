package com.android.server.enterprise.restriction;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserManager;
import android.util.Log;

import com.android.server.enterprise.storage.EdmStorageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RestrictionPolicyCache {
    public static final Map MASK_AND_COLUMN_NAME =
            new HashMap() { // from class:
                            // com.android.server.enterprise.restriction.RestrictionPolicyCache.1
                {
                    put(1L, "allowAudioRecording");
                    put(2L, "allowVideoRecording");
                    put(4L, "microphoneEnabled");
                    put(8L, "allowSettingsChanges");
                    put(16L, "allowFastEncryption");
                    put(32L, "allowSVoice");
                    put(64L, "allowDeveloperMode");
                    put(128L, "allowAirplaneMode");
                    put(256L, "statusBarExpansionEnabled");
                    put(512L, "clipboardEnabled");
                    put(1024L, "allowSBeam");
                    put(2048L, "powerOffAllowed");
                    put(4096L, "allowStopSystemApp");
                    put(8192L, "allowWifiDirect");
                    put(16384L, "limitOfBackgroundProcess");
                    put(32768L, "allowKeepActivities");
                    put(65536L, "allowMobileDataLimit");
                    put(131072L, "allowClipboardShare");
                    put(262144L, "allowAndroidBeam");
                    put(524288L, "allowUsbHostStorage");
                    put(1048576L, "allowShareList");
                    put(2097152L, "useSecureKeypad");
                    put(4194304L, "blockNonTrustedApp");
                    put(8388608L, "lockScreenEnabled");
                    put(16777216L, "firmwarerecoveryallowed");
                    put(33554432L, "allowGoogleAccountsAutoSync");
                    put(67108864L, "allowFirmwareAutoUpdate");
                    put(134217728L, "allowActivationLock");
                    put(268435456L, "setHeadphoneState");
                    put(536870912L, "allowSDCardMove");
                    put(1073741824L, "setCCMode");
                    put(2147483648L, "ODETrustedBootVerification");
                    put(4294967296L, "preventAdminInstallation");
                    put(8589934592L, "preventAdminActivation");
                    put(17179869184L, "wallpaperEnabled");
                    put(34359738368L, "sdCardWriteEnabled");
                    put(68719476736L, "cameraEnabled");
                    put(137438953472L, "usbTetheringEnabled");
                    put(274877906944L, "wifiTetheringEnabled");
                    put(549755813888L, "bluetoothTetheringEnabled");
                    put(1099511627776L, "screenCaptureEnabled");
                    put(2199023255552L, "usbDebuggingEnabled");
                    put(4398046511104L, "massStorageEnabled");
                    put(8796093022208L, "mockLocationEnabled");
                    put(17592186044416L, "backupEnabled");
                    put(35184372088832L, "allowIntelligenceOnlineProcessing");
                    put(70368744177664L, "cellularDataEnabled");
                    put(140737488355328L, "sdCardEnabled");
                    put(281474976710656L, "allowNonMarketApp");
                    put(562949953421312L, "usbMediaPlayerEnabled");
                    put(1125899906842624L, "backgroundDataEnabled");
                    put(2251799813685248L, "factoryresetallowed");
                    put(4503599627370496L, "homeKeyEnabled");
                    put(9007199254740992L, "nativeVpnAllowed");
                    put(18014398509481984L, "OTAUpgradeEnabled");
                    put(36028797018963968L, "googleCrashReportEnabled");
                    put(72057594037927936L, "smartClipAllowed");
                    put(144115188075855872L, "screenPinningAllowed");
                    put(288230376151711744L, "iriscameraEnabled");
                    put(576460752303423488L, "allowDataSaving");
                    put(1152921504606846976L, "allowPowerSavingMode");
                    put(2305843009213693952L, "allowFaceRecognitionEvenCameraBlocked");
                    put(4611686018427387904L, "allowLocalContactStorage");
                    put(Long.MIN_VALUE, "knox_delegation");
                }
            };
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public final PackageManager mPackageManager;
    public UserManager mUserManager;
    public final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    public final HashMap mCameraDisabledAdmin = new HashMap();
    public final ApplyingAdmins mApplyingAdmins = new ApplyingAdmins();
    public final HashMap mCache = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ApplyingAdmins {
        public final Map admins = new HashMap();
        public final Map adminInfoMap = new HashMap();

        public ApplyingAdmins() {}

        public final String dump(int i, long j) {
            if (!((HashMap) this.admins).containsKey(Integer.valueOf(i))) {
                return "";
            }
            if (!((Map) ((HashMap) this.admins).get(Integer.valueOf(i)))
                    .containsKey(Long.valueOf(j))) {
                return "";
            }
            return ((Set)
                            ((Map) ((HashMap) this.admins).get(Integer.valueOf(i)))
                                    .get(Long.valueOf(j)))
                    .toString();
        }

        public final void loadAdminInfo(int i, String str) {
            if (((HashMap) this.adminInfoMap).containsKey(Integer.valueOf(i))
                    || str == null
                    || str.isEmpty()) {
                return;
            }
            ((HashMap) this.adminInfoMap).put(Integer.valueOf(i), str);
        }

        public final void put(int i, Long l, Integer num) {
            Set set;
            if (((Map) ((HashMap) this.admins).get(Integer.valueOf(i))).containsKey(l)) {
                set = (Set) ((Map) ((HashMap) this.admins).get(Integer.valueOf(i))).get(l);
            } else {
                HashSet hashSet = new HashSet();
                ((Map) ((HashMap) this.admins).get(Integer.valueOf(i))).put(l, hashSet);
                set = hashSet;
            }
            set.add(num);
        }

        public final void update(int i, int i2, boolean z, long j) {
            if (z) {
                put(i, Long.valueOf(j), Integer.valueOf(i2));
                return;
            }
            Integer valueOf = Integer.valueOf(i2);
            if (!((Map) ((HashMap) this.admins).get(Integer.valueOf(i)))
                    .containsKey(Long.valueOf(j))) {
                Log.d(
                        "RestrictionPolicy",
                        "No need to update admin cache for mask : "
                                + ((String)
                                        ((HashMap) RestrictionPolicyCache.MASK_AND_COLUMN_NAME)
                                                .get(Long.valueOf(j)))
                                + "("
                                + valueOf
                                + ")");
                return;
            }
            Set set =
                    (Set)
                            ((Map) ((HashMap) this.admins).get(Integer.valueOf(i)))
                                    .get(Long.valueOf(j));
            set.remove(valueOf);
            if (set.isEmpty()) {
                ((Map) ((HashMap) this.admins).get(Integer.valueOf(i))).remove(Long.valueOf(j));
            }
        }
    }

    public RestrictionPolicyCache(Context context, EdmStorageProvider edmStorageProvider) {
        this.mContext = context;
        this.mEdmStorageProvider = edmStorageProvider;
        this.mPackageManager = context.getPackageManager();
    }

    public final boolean extract(int i, long j, boolean z) {
        Long cachedPolicies = getCachedPolicies(i);
        return cachedPolicies != null ? (cachedPolicies.longValue() & j) == j : z;
    }

    public final Long getCachedPolicies(int i) {
        this.mLock.readLock().lock();
        try {
            return (Long) this.mCache.get(Integer.valueOf(i));
        } finally {
            this.mLock.readLock().unlock();
        }
    }

    public final void init(int i) {
        this.mLock.writeLock().lock();
        try {
            this.mCache.put(Integer.valueOf(i), 6917529011461554159L);
            ApplyingAdmins applyingAdmins = this.mApplyingAdmins;
            ((HashMap) applyingAdmins.admins).put(Integer.valueOf(i), new HashMap());
            ((HashMap) applyingAdmins.adminInfoMap)
                    .entrySet()
                    .removeIf(
                            new RestrictionPolicyCache$ApplyingAdmins$$ExternalSyntheticLambda0(
                                    i, 0));
            this.mCameraDisabledAdmin.put(Integer.valueOf(i), 0L);
        } finally {
            this.mLock.writeLock().unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0148 A[Catch: all -> 0x00b8, SQLException -> 0x00bb, TryCatch #0 {SQLException -> 0x00bb, blocks: (B:18:0x007f, B:19:0x0082, B:21:0x0088, B:23:0x0096, B:25:0x00ab, B:26:0x00c2, B:27:0x00cf, B:30:0x00d7, B:32:0x0106, B:34:0x0114, B:36:0x0144, B:38:0x0148, B:40:0x0159, B:45:0x016b, B:47:0x017c, B:52:0x0125, B:54:0x0133, B:57:0x0187, B:59:0x018f, B:61:0x0197, B:66:0x01c6, B:67:0x01cb, B:68:0x01d8, B:70:0x01ae, B:73:0x01bb, B:76:0x01c2, B:79:0x00be), top: B:17:0x007f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016b A[Catch: all -> 0x00b8, SQLException -> 0x00bb, TryCatch #0 {SQLException -> 0x00bb, blocks: (B:18:0x007f, B:19:0x0082, B:21:0x0088, B:23:0x0096, B:25:0x00ab, B:26:0x00c2, B:27:0x00cf, B:30:0x00d7, B:32:0x0106, B:34:0x0114, B:36:0x0144, B:38:0x0148, B:40:0x0159, B:45:0x016b, B:47:0x017c, B:52:0x0125, B:54:0x0133, B:57:0x0187, B:59:0x018f, B:61:0x0197, B:66:0x01c6, B:67:0x01cb, B:68:0x01d8, B:70:0x01ae, B:73:0x01bb, B:76:0x01c2, B:79:0x00be), top: B:17:0x007f, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0183 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void load(int r22) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.restriction.RestrictionPolicyCache.load(int):void");
    }

    public final void update(String str, long j, boolean z, int i, Integer num, Boolean bool) {
        boolean z2;
        String string;
        ApplyingAdmins applyingAdmins = this.mApplyingAdmins;
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        Iterator it = edmStorageProvider.getBooleanListAsUser(i, "RESTRICTION", str).iterator();
        while (true) {
            if (!it.hasNext()) {
                z2 = z;
                break;
            } else {
                z2 = ((Boolean) it.next()).booleanValue();
                if (z2 != z) {
                    break;
                }
            }
        }
        Long cachedPolicies = getCachedPolicies(i);
        if (cachedPolicies == null) {
            if (this.mUserManager == null) {
                this.mUserManager = (UserManager) this.mContext.getSystemService("user");
            }
            if (this.mUserManager.getUserInfo(i) != null) {
                init(i);
                cachedPolicies = getCachedPolicies(i);
            }
        }
        this.mLock.writeLock().lock();
        if (cachedPolicies != null) {
            try {
                this.mCache.put(
                        Integer.valueOf(i),
                        z2
                                ? Long.valueOf(cachedPolicies.longValue() | j)
                                : Long.valueOf(cachedPolicies.longValue() & (~j)));
                if (bool != null && num != null) {
                    this.mApplyingAdmins.update(i, num.intValue(), bool.booleanValue() != z, j);
                    Map map = applyingAdmins.adminInfoMap;
                    if (!((map == null || ((HashMap) map).isEmpty())
                            ? false
                            : ((HashMap) applyingAdmins.adminInfoMap).containsKey(num))) {
                        int intValue = num.intValue();
                        int intValue2 = num.intValue();
                        String str2 = null;
                        if (intValue2 != 1000
                                && (string =
                                                edmStorageProvider.getString(
                                                        intValue2, 0, "ADMIN_INFO", "adminName"))
                                        != null) {
                            ComponentName unflattenFromString =
                                    ComponentName.unflattenFromString(string);
                            str2 =
                                    unflattenFromString == null
                                            ? string
                                            : unflattenFromString.getPackageName();
                        }
                        applyingAdmins.loadAdminInfo(intValue, str2);
                    }
                }
            } catch (Throwable th) {
                this.mLock.writeLock().unlock();
                throw th;
            }
        }
        this.mLock.writeLock().unlock();
    }

    public final void updateCameraDisabledAdmin(int i) {
        this.mCameraDisabledAdmin.put(Integer.valueOf(i), 0L);
        ArrayList arrayList =
                (ArrayList)
                        this.mEdmStorageProvider.getValuesListAsUser(
                                0, i, "RESTRICTION", new String[] {"cameraEnabled", "adminUid"});
        if (arrayList.isEmpty()) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            if (contentValues != null && contentValues.size() > 0) {
                if (!(contentValues.getAsBoolean("cameraEnabled") == null
                        ? true
                        : contentValues.getAsBoolean("cameraEnabled").booleanValue())) {
                    if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i))).longValue()
                            == 0) {
                        this.mCameraDisabledAdmin.put(
                                Integer.valueOf(i), contentValues.getAsLong("adminUid"));
                    } else if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i)))
                                    .longValue()
                            > 0) {
                        this.mCameraDisabledAdmin.put(Integer.valueOf(i), -1L);
                    }
                }
            }
        }
    }

    public final void updateCameraDisabledAdmin(int i, int i2, Long l, long j) {
        boolean z = i > 0;
        if (l.longValue() != 68719476736L || z) {
            return;
        }
        if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i2))).longValue() == 0) {
            this.mCameraDisabledAdmin.put(Integer.valueOf(i2), Long.valueOf(j));
        } else if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i2))).longValue() > 0) {
            this.mCameraDisabledAdmin.put(Integer.valueOf(i2), -1L);
        }
    }
}
