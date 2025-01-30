package com.android.server.enterprise.restriction;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Binder;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.enterprise.restriction.RestrictionPolicyCache;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class RestrictionPolicyCache {
    public static final Map MASK_AND_COLUMN_NAME = new HashMap() { // from class: com.android.server.enterprise.restriction.RestrictionPolicyCache.1
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
    public ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    public HashMap mCameraDisabledAdmin = new HashMap();
    public ApplyingAdmins mApplyingAdmins = new ApplyingAdmins();
    public final HashMap mCache = new HashMap();

    public RestrictionPolicyCache(Context context, EdmStorageProvider edmStorageProvider) {
        this.mContext = context;
        this.mEdmStorageProvider = edmStorageProvider;
        this.mPackageManager = context.getPackageManager();
    }

    public final void init(int i) {
        this.mLock.writeLock().lock();
        try {
            this.mCache.put(Integer.valueOf(i), 6917529011461554159L);
            this.mApplyingAdmins.init(i);
            this.mCameraDisabledAdmin.put(Integer.valueOf(i), 0L);
        } finally {
            this.mLock.writeLock().unlock();
        }
    }

    public void clearCache(int i) {
        this.mLock.writeLock().lock();
        try {
            this.mCache.remove(Integer.valueOf(i));
            this.mApplyingAdmins.remove(i);
            this.mCameraDisabledAdmin.remove(Integer.valueOf(i));
        } finally {
            this.mLock.writeLock().unlock();
        }
    }

    public void load(int i) {
        ContentValues contentValues;
        String str;
        long j;
        Long l;
        Long valueOf;
        if (i == -1) {
            Iterator it = getUserManager().getUsers().iterator();
            while (it.hasNext()) {
                init(((UserInfo) it.next()).getUserHandle().getIdentifier());
            }
        } else {
            init(i);
        }
        if (i == -1) {
            contentValues = null;
        } else {
            contentValues = new ContentValues();
            contentValues.put(EdmStorageProviderBase.getAdminLUIDWhereIn(0, i), "#SelectClause#");
        }
        Collection values = MASK_AND_COLUMN_NAME.values();
        String[] strArr = (String[]) values.toArray(new String[values.size() + 1]);
        String str2 = "adminUid";
        strArr[strArr.length - 1] = "adminUid";
        Cursor cursor = this.mEdmStorageProvider.getCursor("RESTRICTION", strArr, contentValues, null);
        if (cursor != null) {
            this.mLock.writeLock().lock();
            try {
                try {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        long j2 = cursor.getLong(cursor.getColumnIndex(str2));
                        int i2 = (int) j2;
                        if (j2 == i2) {
                            int userId = UserHandle.getUserId(i2);
                            if (this.mCache.get(Integer.valueOf(userId)) != null) {
                                j = (Long) this.mCache.get(Integer.valueOf(userId));
                            } else {
                                j = 6917529011461554159L;
                            }
                            Long l2 = j;
                            for (Map.Entry entry : MASK_AND_COLUMN_NAME.entrySet()) {
                                int i3 = cursor.getInt(cursor.getColumnIndex((String) entry.getValue()));
                                Long l3 = (Long) entry.getKey();
                                String str3 = str2;
                                updateCameraDisabledAdmin(i3, userId, l3, j2);
                                if (i3 == 1) {
                                    l = l3;
                                    if (isDefaultValueZero(l)) {
                                        valueOf = Long.valueOf(l2.longValue() | l.longValue());
                                        l2 = valueOf;
                                        this.mApplyingAdmins.load(i2, userId, l, i3);
                                        str2 = str3;
                                    }
                                } else {
                                    l = l3;
                                }
                                if (i3 == 0 && isDefaultValueOne(l)) {
                                    valueOf = Long.valueOf(l2.longValue() & (~l.longValue()));
                                    l2 = valueOf;
                                }
                                this.mApplyingAdmins.load(i2, userId, l, i3);
                                str2 = str3;
                            }
                            str = str2;
                            if (!this.mApplyingAdmins.isAdminInfoPresent(i2)) {
                                this.mApplyingAdmins.loadAdminInfo(i2, getPackageNameForUid(i2));
                            }
                            this.mCache.put(Integer.valueOf(userId), l2);
                        } else {
                            str = str2;
                        }
                        cursor.moveToNext();
                        str2 = str;
                    }
                } catch (SQLException e) {
                    Log.e("RestrictionPolicy", "Exception occurred accessing Enterprise db " + e.getMessage());
                }
            } finally {
                this.mLock.writeLock().unlock();
                cursor.close();
            }
        }
    }

    public final boolean isDefaultValueOne(Long l) {
        return getDefaultMaskedValue(l) == l.longValue();
    }

    public final boolean isDefaultValueZero(Long l) {
        return getDefaultMaskedValue(l) != l.longValue();
    }

    public final long getDefaultMaskedValue(Long l) {
        return l.longValue() & 6917529011461554159L;
    }

    public final UserManager getUserManager() {
        if (this.mUserManager == null) {
            this.mUserManager = (UserManager) this.mContext.getSystemService("user");
        }
        return this.mUserManager;
    }

    public void update(String str, long j, boolean z, int i, Integer num, Boolean bool) {
        boolean z2;
        Long valueOf;
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(0, "RESTRICTION", str, i).iterator();
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
        if (cachedPolicies == null && getUserManager().getUserInfo(i) != null) {
            init(i);
            cachedPolicies = getCachedPolicies(i);
        }
        this.mLock.writeLock().lock();
        if (cachedPolicies != null) {
            try {
                if (z2) {
                    valueOf = Long.valueOf(cachedPolicies.longValue() | j);
                } else {
                    valueOf = Long.valueOf(cachedPolicies.longValue() & (~j));
                }
                this.mCache.put(Integer.valueOf(i), valueOf);
                if (bool != null && num != null) {
                    this.mApplyingAdmins.update(bool.booleanValue() != z, i, j, num.intValue());
                    if (!this.mApplyingAdmins.isAdminInfoPresent(num.intValue())) {
                        this.mApplyingAdmins.loadAdminInfo(num.intValue(), getPackageNameForUid(num.intValue()));
                    }
                }
            } finally {
                this.mLock.writeLock().unlock();
            }
        }
    }

    public final Long getCachedPolicies(int i) {
        this.mLock.readLock().lock();
        try {
            return (Long) this.mCache.get(Integer.valueOf(i));
        } finally {
            this.mLock.readLock().unlock();
        }
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        this.mLock.readLock().lock();
        try {
            sb.append("[Admin Info : ");
            sb.append(this.mApplyingAdmins.getAdminInfo());
            sb.append("]");
            sb.append(System.lineSeparator());
            Iterator it = this.mCache.keySet().iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                sb.append("[Restrictions applied for user : ");
                sb.append(intValue);
                sb.append("]");
                sb.append(System.lineSeparator());
                long longValue = getCachedPolicies(intValue).longValue();
                for (Map.Entry entry : MASK_AND_COLUMN_NAME.entrySet()) {
                    long longValue2 = ((Long) entry.getKey()).longValue();
                    sb.append("   ");
                    sb.append((String) entry.getValue());
                    sb.append(": ");
                    long j = longValue & longValue2;
                    sb.append(j == longValue2);
                    if (j != getDefaultMaskedValue(Long.valueOf(longValue2))) {
                        sb.append("(Enforced) ");
                    }
                    sb.append(this.mApplyingAdmins.dump(intValue, longValue2));
                    sb.append(System.lineSeparator());
                }
            }
            this.mLock.readLock().unlock();
            return sb.toString();
        } catch (Throwable th) {
            this.mLock.readLock().unlock();
            throw th;
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

    public void updateCameraDisabledAdmin(String str, long j, boolean z, int i) {
        this.mCameraDisabledAdmin.put(Integer.valueOf(i), 0L);
        List<ContentValues> valuesListAsUser = this.mEdmStorageProvider.getValuesListAsUser("RESTRICTION", new String[]{str, "adminUid"}, i);
        if (valuesListAsUser == null || valuesListAsUser.isEmpty()) {
            return;
        }
        for (ContentValues contentValues : valuesListAsUser) {
            if (contentValues != null && contentValues.size() > 0) {
                if ((contentValues.getAsBoolean(str) == null ? z : contentValues.getAsBoolean(str).booleanValue()) != z) {
                    if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i))).longValue() == 0) {
                        this.mCameraDisabledAdmin.put(Integer.valueOf(i), contentValues.getAsLong("adminUid"));
                    } else if (((Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i))).longValue() > 0) {
                        this.mCameraDisabledAdmin.put(Integer.valueOf(i), -1L);
                    }
                }
            }
        }
    }

    public Long getCameraDisabledAdmin(int i) {
        return (Long) this.mCameraDisabledAdmin.get(Integer.valueOf(i));
    }

    public boolean extract(long j, boolean z, int i) {
        Long cachedPolicies = getCachedPolicies(i);
        return cachedPolicies != null ? (cachedPolicies.longValue() & j) == j : z;
    }

    public List getAdminAppLabelListAsUserForMask(long j, int i) {
        ArrayList arrayList = new ArrayList();
        if (this.mApplyingAdmins.admins.get(Integer.valueOf(i)) == null) {
            Log.d("RestrictionPolicy", "no admin data present for userId " + i);
            return Collections.emptyList();
        }
        for (Integer num : (Set) ((Map) this.mApplyingAdmins.admins.get(Integer.valueOf(i))).getOrDefault(Long.valueOf(j), null)) {
            String str = (String) this.mApplyingAdmins.adminInfoMap.get(num);
            if (str != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    try {
                        String charSequence = this.mPackageManager.getApplicationLabel(this.mPackageManager.getApplicationInfoAsUser(str, 0, UserHandle.getUserId(num.intValue()))).toString();
                        if (charSequence != null && !charSequence.isEmpty()) {
                            arrayList.add(charSequence);
                        }
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.e("RestrictionPolicy", String.format("Package(%s) name not found for user %d", str, Integer.valueOf(i)));
                    } catch (Exception unused2) {
                        Log.e("RestrictionPolicy", String.format("Admin(%s) app label not found for user %d", str, Integer.valueOf(i)));
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        return arrayList;
    }

    public final String getPackageNameForUid(int i) {
        String string;
        if (i == 1000 || (string = this.mEdmStorageProvider.getString(i, "ADMIN_INFO", "adminName")) == null) {
            return null;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
        return unflattenFromString == null ? string : unflattenFromString.getPackageName();
    }

    public class ApplyingAdmins {
        public Map adminInfoMap;
        public Map admins;

        public ApplyingAdmins() {
            this.admins = new HashMap();
            this.adminInfoMap = new HashMap();
        }

        public void init(final int i) {
            this.admins.put(Integer.valueOf(i), new HashMap());
            this.adminInfoMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.enterprise.restriction.RestrictionPolicyCache$ApplyingAdmins$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$init$0;
                    lambda$init$0 = RestrictionPolicyCache.ApplyingAdmins.lambda$init$0(i, (Map.Entry) obj);
                    return lambda$init$0;
                }
            });
        }

        public static /* synthetic */ boolean lambda$init$0(int i, Map.Entry entry) {
            return ((Integer) entry.getKey()).intValue() / 100000 == i;
        }

        public void remove(final int i) {
            this.admins.remove(Integer.valueOf(i));
            this.adminInfoMap.entrySet().removeIf(new Predicate() { // from class: com.android.server.enterprise.restriction.RestrictionPolicyCache$ApplyingAdmins$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$remove$1;
                    lambda$remove$1 = RestrictionPolicyCache.ApplyingAdmins.lambda$remove$1(i, (Map.Entry) obj);
                    return lambda$remove$1;
                }
            });
        }

        public static /* synthetic */ boolean lambda$remove$1(int i, Map.Entry entry) {
            return ((Integer) entry.getKey()).intValue() / 100000 == i;
        }

        public void load(int i, int i2, Long l, int i3) {
            if (i3 == 1 && RestrictionPolicyCache.this.isDefaultValueZero(l)) {
                put(i2, l, Integer.valueOf(i));
            } else if (i3 == 0 && RestrictionPolicyCache.this.isDefaultValueOne(l)) {
                put(i2, l, Integer.valueOf(i));
            }
        }

        public void update(boolean z, int i, long j, int i2) {
            if (z) {
                put(i, Long.valueOf(j), Integer.valueOf(i2));
            } else {
                remove(i, j, Integer.valueOf(i2));
            }
        }

        public void loadAdminInfo(int i, String str) {
            if (this.adminInfoMap.containsKey(Integer.valueOf(i)) || str == null || str.isEmpty()) {
                return;
            }
            this.adminInfoMap.put(Integer.valueOf(i), str);
        }

        public boolean isAdminInfoPresent(int i) {
            Map map = this.adminInfoMap;
            if (map == null || map.isEmpty()) {
                return false;
            }
            return this.adminInfoMap.containsKey(Integer.valueOf(i));
        }

        public final void put(int i, Long l, Integer num) {
            Set set;
            if (((Map) this.admins.get(Integer.valueOf(i))).containsKey(l)) {
                set = (Set) ((Map) this.admins.get(Integer.valueOf(i))).get(l);
            } else {
                HashSet hashSet = new HashSet();
                ((Map) this.admins.get(Integer.valueOf(i))).put(l, hashSet);
                set = hashSet;
            }
            set.add(num);
        }

        public final void remove(int i, long j, Integer num) {
            if (!((Map) this.admins.get(Integer.valueOf(i))).containsKey(Long.valueOf(j))) {
                Log.d("RestrictionPolicy", "No need to update admin cache for mask : " + ((String) RestrictionPolicyCache.MASK_AND_COLUMN_NAME.get(Long.valueOf(j))) + "(" + num + ")");
                return;
            }
            Set set = (Set) ((Map) this.admins.get(Integer.valueOf(i))).get(Long.valueOf(j));
            set.remove(num);
            if (set.isEmpty()) {
                ((Map) this.admins.get(Integer.valueOf(i))).remove(Long.valueOf(j));
            }
        }

        public String dump(int i, long j) {
            return (this.admins.containsKey(Integer.valueOf(i)) && ((Map) this.admins.get(Integer.valueOf(i))).containsKey(Long.valueOf(j))) ? ((Set) ((Map) this.admins.get(Integer.valueOf(i))).get(Long.valueOf(j))).toString() : "";
        }

        public String getAdminInfo() {
            StringBuilder sb = new StringBuilder();
            Map map = this.adminInfoMap;
            if (map != null && !map.isEmpty()) {
                sb.append(this.adminInfoMap.toString());
            }
            return sb.toString();
        }
    }
}
