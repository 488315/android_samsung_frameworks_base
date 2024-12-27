package com.android.server.am.mars.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.filter.AllowListFilter;

import java.util.ArrayList;

public final class MARsDBManager {
    public Context mContext;
    public Context mCurrentContext;
    public MARsDBHandler mMARsDBHandler;
    public SmartManagerFASObserver mSmartManagerDefaultAllowedListObserver;
    public SmartManagerFASObserver mSmartManagerFASObserver;
    public SmartManagerFASObserver mSmartManagerFreezeExcludeListObserver;
    public SmartManagerFASObserver mSmartManagerSettingsObserver;
    public boolean mRegisteredSmartManagerSettingsObserver = false;
    public boolean mRegisteredSmartManagerForcedAppStandbyObserver = false;
    public boolean mRegisteredSmartManagerDefaultAllowedListObserver = false;
    public boolean mRegisteredSmartManagerFreezeExcludeListObserver = false;
    public final ArrayList mAppStartUpList = new ArrayList();
    public String MARsLocalVersion = null;
    public String MARsDBVersion = null;
    public int mMaxFailCountForSCPM = 3;
    public boolean mIsBigDataRestrictionColumnExist = false;

    public abstract class MARsDBManagerHolder {
        public static final MARsDBManager INSTANCE = new MARsDBManager();
    }

    public final class SmartManagerFASObserver extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ MARsDBManager this$0;

        public /* synthetic */ SmartManagerFASObserver(
                MARsDBManager mARsDBManager, MARsDBHandler.MainHandler mainHandler, int i) {
            super(mainHandler);
            this.$r8$classId = i;
            this.this$0 = mARsDBManager;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 1:
                    if (MARsDebugConfig.DEBUG_ENG) {
                        this.this$0.getClass();
                        Slog.d(
                                "MARsDBManager",
                                "onChange - mSmartManagerDefaultAllowedListObserver!");
                    }
                    this.this$0.getDefaultAllowedListDBValues();
                    break;
                case 2:
                    if (MARsDebugConfig.DEBUG_ENG) {
                        this.this$0.getClass();
                        Slog.d(
                                "MARsDBManager",
                                "onChange - mSmartManagerFreezeExcludeListObserver!");
                    }
                    this.this$0.getFreezeExcludeListFromDB();
                    break;
                case 3:
                    if (MARsDebugConfig.DEBUG_ENG) {
                        this.this$0.getClass();
                        Slog.d("MARsDBManager", "onChange - mSmartManagerSettingsObserver!");
                    }
                    this.this$0.getSettingsValueFromDB(false);
                    break;
                default:
                    super.onChange(z);
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (uri != null) {
                        if (MARsDebugConfig.DEBUG_ENG) {
                            this.this$0.getClass();
                            Slog.d(
                                    "MARsDBManager",
                                    "onChange - mSmartManagerFASObserver! Uri = " + uri);
                        }
                        if (!uri.getBooleanQueryParameter("MARs-self", false)) {
                            MARsDBHandler mARsDBHandler = this.this$0.mMARsDBHandler;
                            mARsDBHandler.mMainHandler.post(mARsDBHandler.mFASDBupdateRunnable);
                            break;
                        }
                    }
                    break;
                default:
                    super.onChange(z, uri, i);
                    break;
            }
        }
    }

    public static void getMARsSettingsInfoForNotificationTime(Context context) {
        if (context == null) {
            Slog.e("MARsDBManager", "getMARsSettingsInfoForNotificationTime() context is null!");
            return;
        }
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    MARsTableContract.MARS_SETTINGS_URI,
                                    new String[] {"key", "value"},
                                    null,
                                    null,
                                    null);
            if (query == null) {
                Slog.e(
                        "MARsDBManager",
                        "getMARsSettingsInfoForNotificationTime error, no database!!");
                return;
            }
            while (query.moveToNext()) {
                if (query.getString(0) != null) {
                    String string = query.getString(0);
                    String string2 = query.getString(1);
                    try {
                        if ("deep_sleep_notification_time".equals(string)) {
                            long parseLong = Long.parseLong(string2);
                            boolean z = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                            boolean z2 = MARsPolicyManager.MARs_ENABLE;
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                            .mLastNotiSentTimeForDisabled =
                                    parseLong;
                        }
                    } catch (Exception e) {
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                e,
                                new StringBuilder("Exception with parseLong : "),
                                "MARsDBManager");
                    }
                }
            }
            query.close();
        } catch (Exception e2) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    e2, "getMARsSettingsInfoForNotificationTime : Exception : ", "MARsDBManager");
        }
    }

    public final void doUpdateCompHistory(boolean z) {
        if (this.mAppStartUpList.isEmpty()) {
            return;
        }
        try {
            ContentValues[] contentValuesArr = new ContentValues[this.mAppStartUpList.size()];
            this.mAppStartUpList.toArray(contentValuesArr);
            this.mCurrentContext
                    .getContentResolver()
                    .bulkInsert(FASTableContract.SMART_MGR_APP_STARTUP_URI, contentValuesArr);
            if (z) {
                return;
            }
            this.mAppStartUpList.clear();
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Exception on handling DB : "), "MARsDBManager");
        }
    }

    public final void doUpdatePkgToSMDB(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9,
            String str10,
            String str11,
            String str12,
            String str13) {
        if (str == null || str2 == null) {
            Slog.e("MARsDBManager", "parameter is error!");
        }
        ContentValues contentValues = new ContentValues();
        if (str != null) {
            contentValues.put("package_name", str);
        }
        if (str2 != null) {
            contentValues.put("uid", str2);
        }
        if (str3 != null) {
            contentValues.put("mode", str3);
        }
        if (str4 != null) {
            contentValues.put("new", str4);
        }
        if (str5 != null) {
            contentValues.put("reason", str5);
        }
        if (str6 != null) {
            contentValues.put("extras", str6);
        }
        if (str7 != null) {
            contentValues.put("resetTime", str7);
        }
        if (str8 != null) {
            contentValues.put("packageType", str8);
        }
        if (str9 != null) {
            contentValues.put("level", str9);
        }
        if (str10 != null) {
            contentValues.put("disableType", str10);
        }
        if (str11 != null) {
            contentValues.put("disableResetTime", str11);
        }
        if (str12 != null) {
            contentValues.put("prevCurrent", str12);
        }
        if (str13 != null) {
            contentValues.put("disableReason", str13);
        }
        try {
            this.mContext
                    .getContentResolver()
                    .update(
                            FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI
                                    .buildUpon()
                                    .appendQueryParameter("MARs-self", "true")
                                    .build(),
                            contentValues,
                            "package_name=? AND uid=?",
                            new String[] {str, str2});
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception with update() : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public final void getDefaultAllowedListDBValues() {
        ArrayList arrayList = new ArrayList();
        try {
            Cursor query =
                    this.mCurrentContext
                            .getContentResolver()
                            .query(
                                    FASTableContract.SMART_MGR_DEFAULT_ALLOWED_LIST_URI,
                                    new String[] {"package_name"},
                                    "type=?",
                                    new String[] {MARsUtils.isChinaPolicyEnabled() ? "1" : "0"},
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        if (query.getString(0) != null) {
                            arrayList.add(query.getString(0));
                        }
                    } finally {
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
        }
        AllowListFilter allowListFilter = AllowListFilter.AllowListFilterHolder.INSTANCE;
        synchronized (allowListFilter.mDefaultAllowList) {
            allowListFilter.mDefaultAllowList.clear();
            allowListFilter.mDefaultAllowList.addAll(arrayList);
        }
    }

    public final void getFreezeExcludeListFromDB() {
        ArrayList arrayList = new ArrayList();
        try {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    FASTableContract.SMART_MGR_FREEZE_EXCLUDE_LIST_URI,
                                    new String[] {"package_name"},
                                    null,
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        String string = query.getString(0);
                        if (string != null) {
                            if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d("MARsDBManager", "pkgname = " + string);
                            }
                            arrayList.add(string);
                        }
                    } finally {
                    }
                }
                MARsUtils.setFreezeExcludeList(arrayList);
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getSCPMList() {
        /*
            Method dump skipped, instructions count: 727
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.mars.database.MARsDBManager.getSCPMList():void");
    }

    public final void getSettingsValueFromDB(boolean z) {
        try {
            Cursor query =
                    this.mCurrentContext
                            .getContentResolver()
                            .query(
                                    FASTableContract.SMART_MGR_SETTINGS_URI,
                                    new String[] {"key", "value"},
                                    null,
                                    null,
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        String string = query.getString(0);
                        try {
                            if ("spcm_locking_time".equals(string)) {
                                MARsUtils.setPackagesUnusedLockingTime(
                                        Integer.parseInt(query.getString(1)));
                            }
                            if ("spcm_switch".equals(string)) {
                                MARsUtils.setAllPoliciesOnOffState(
                                        Integer.parseInt(query.getString(1)), z);
                            }
                            if ("ConfigCompTracing".equals(string)) {
                                MARsComponentTracker.MARsComponentTrackerHolder.INSTANCE
                                                .isEnabledCT =
                                        Integer.parseInt(query.getString(1)) > 0;
                            }
                        } catch (Exception e) {
                            Slog.e("MARsDBManager", "Exception with parseInt : " + e.getMessage());
                        }
                    } finally {
                    }
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e2) {
            Slog.e("MARsDBManager", "Exception with contentResolver : " + e2.getMessage());
            e2.printStackTrace();
        }
    }

    public final boolean isCompareDBVersion() {
        Cursor cursor;
        String str;
        String str2;
        String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
        MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
        String[][] strArr2 = MARsVersionManager.mMARsSettingsInfoDefault;
        String str3 = null;
        String str4 = null;
        for (int i = 0; i < 6; i++) {
            String[] strArr3 = strArr2[i];
            if ("marsversion".equals(strArr3[0])) {
                str4 = strArr3[1];
            }
        }
        this.MARsLocalVersion = str4;
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.v("MARsDBManager", "getSMDBVersionFromDB!");
        }
        try {
            cursor =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    MARsTableContract.MARS_SETTINGS_URI,
                                    new String[] {"value"},
                                    "key=?",
                                    new String[] {"marsversion"},
                                    null);
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            str = null;
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    str = cursor.getString(0);
                }
            }
            cursor.close();
        } else {
            Slog.e("MARsDBManager", "getSMDBVersionFromDB error, no database!!");
            str = null;
        }
        this.MARsDBVersion = str;
        getMARsSettingsInfoForNotificationTime(this.mContext);
        if (this.MARsDBVersion != null) {
            try {
                str2 = this.MARsLocalVersion.substring(0, 2);
            } catch (StringIndexOutOfBoundsException unused) {
                Slog.e("MARsDBManager", "isCompareDBVersion(), IndexOutOfBoundsException");
                str2 = null;
            }
            try {
                str3 = this.MARsDBVersion.substring(0, 2);
            } catch (StringIndexOutOfBoundsException unused2) {
                Slog.e("MARsDBManager", "isCompareDBVersion(), IndexOutOfBoundsException");
            }
            if (str3 != null
                    && str2 != null
                    && str2.equals(str3)
                    && Integer.parseInt(this.MARsDBVersion)
                            >= Integer.parseInt(this.MARsLocalVersion)) {
                return true;
            }
        }
        return false;
    }

    public final void registerContentObservers(Context context) {
        MARsDBHandler.MainHandler mainHandler;
        MARsDBHandler.MainHandler mainHandler2;
        MARsDBHandler.MainHandler mainHandler3;
        MARsDBHandler.MainHandler mainHandler4;
        try {
            if (!this.mRegisteredSmartManagerSettingsObserver) {
                MARsDBHandler mARsDBHandler = this.mMARsDBHandler;
                synchronized (mARsDBHandler) {
                    mainHandler4 = mARsDBHandler.mMainHandler;
                }
                this.mSmartManagerSettingsObserver =
                        new SmartManagerFASObserver(this, mainHandler4, 3);
                context.getContentResolver()
                        .registerContentObserver(
                                FASTableContract.SMART_MGR_SETTINGS_URI,
                                false,
                                this.mSmartManagerSettingsObserver,
                                context.getUserId());
                this.mRegisteredSmartManagerSettingsObserver = true;
            }
            if (!this.mRegisteredSmartManagerDefaultAllowedListObserver) {
                MARsDBHandler mARsDBHandler2 = this.mMARsDBHandler;
                synchronized (mARsDBHandler2) {
                    mainHandler3 = mARsDBHandler2.mMainHandler;
                }
                this.mSmartManagerDefaultAllowedListObserver =
                        new SmartManagerFASObserver(this, mainHandler3, 1);
                context.getContentResolver()
                        .registerContentObserver(
                                FASTableContract.SMART_MGR_DEFAULT_ALLOWED_LIST_URI,
                                false,
                                this.mSmartManagerDefaultAllowedListObserver,
                                context.getUserId());
                this.mRegisteredSmartManagerDefaultAllowedListObserver = true;
            }
            if (!this.mRegisteredSmartManagerForcedAppStandbyObserver) {
                MARsDBHandler mARsDBHandler3 = this.mMARsDBHandler;
                synchronized (mARsDBHandler3) {
                    mainHandler2 = mARsDBHandler3.mMainHandler;
                }
                this.mSmartManagerFASObserver = new SmartManagerFASObserver(this, mainHandler2, 0);
                context.getContentResolver()
                        .registerContentObserver(
                                FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI,
                                true,
                                this.mSmartManagerFASObserver,
                                context.getUserId());
                this.mRegisteredSmartManagerForcedAppStandbyObserver = true;
            }
            if (this.mRegisteredSmartManagerFreezeExcludeListObserver
                    || !MARsUtils.isChinaPolicyEnabled()) {
                return;
            }
            MARsDBHandler mARsDBHandler4 = this.mMARsDBHandler;
            synchronized (mARsDBHandler4) {
                mainHandler = mARsDBHandler4.mMainHandler;
            }
            this.mSmartManagerFreezeExcludeListObserver =
                    new SmartManagerFASObserver(this, mainHandler, 2);
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            FASTableContract.SMART_MGR_FREEZE_EXCLUDE_LIST_URI,
                            false,
                            this.mSmartManagerFreezeExcludeListObserver,
                            this.mContext.getUserId());
            this.mRegisteredSmartManagerFreezeExcludeListObserver = true;
        } catch (Exception e) {
            KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(e, "Exception : ", "MARsDBManager");
        }
    }

    public final void updatePkgToSMDB(FASEntity fASEntity) {
        if (fASEntity == null) {
            return;
        }
        try {
            doUpdatePkgToSMDB(
                    fASEntity.getStrPkgName() != null ? fASEntity.getStrPkgName() : null,
                    fASEntity.getStrUid() != null ? fASEntity.getStrUid() : null,
                    fASEntity.getStrMode() != null ? fASEntity.getStrMode() : null,
                    fASEntity.getStrNew() != null ? fASEntity.getStrNew() : null,
                    fASEntity.getStrFasReason() != null ? fASEntity.getStrFasReason() : null,
                    fASEntity.getStrExtras() != null ? fASEntity.getStrExtras() : null,
                    fASEntity.getStrResetTime() != null ? fASEntity.getStrResetTime() : null,
                    fASEntity.getStrPackageType() != null ? fASEntity.getStrPackageType() : null,
                    fASEntity.getStrLevel() != null ? fASEntity.getStrLevel() : null,
                    fASEntity.getStrDisableType() != null ? fASEntity.getStrDisableType() : null,
                    fASEntity.getStrDisableResetTime() != null
                            ? fASEntity.getStrDisableResetTime()
                            : null,
                    fASEntity.getStrPreBatteryUsage() != null
                            ? fASEntity.getStrPreBatteryUsage()
                            : null,
                    fASEntity.getStrDisableReason() != null
                            ? fASEntity.getStrDisableReason()
                            : null);
        } catch (Exception e) {
            Slog.e("MARsDBManager", "Exception on handling DB : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
