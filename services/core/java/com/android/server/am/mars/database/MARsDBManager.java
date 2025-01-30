package com.android.server.am.mars.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.am.mars.database.MARsVersionManager.AdjustRestriction;
import com.android.server.am.mars.database.MARsVersionManager.AdjustTargetCurrentImportant;
import com.android.server.am.mars.database.MARsVersionManager.AdjustTargetExcludePackage;
import com.android.server.am.mars.database.MARsVersionManager.MARsSettingsInfo;
import com.android.server.am.mars.database.MARsVersionManager.PolicyInfo;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes.dex */
public class MARsDBManager {
    public String MARsDBVersion;
    public String MARsLocalVersion;
    public final String TAG;
    public ArrayList mAppStartUpList;
    public Context mContext;
    public Context mCurrentContext;
    public boolean mDBCreate;
    public boolean mDBUpdated;
    public Runnable mFASDBupdateRunnable;
    public boolean mIsBigDataRestrictionColumnExist;
    public MARsDBManagerHandler mMARsDBManagerHandler;
    public MARsDBManagerThread mMARsDBManagerThread;
    public int mMaxFailCountForSCPM;
    public boolean mRegisteredSmartManagerDefaultAllowedListObserver;
    public boolean mRegisteredSmartManagerForcedAppStandbyObserver;
    public boolean mRegisteredSmartManagerFreezeExcludeListObserver;
    public boolean mRegisteredSmartManagerSettingsObserver;
    public SmartManagerDefaultAllowedListObserver mSmartManagerDefaultAllowedListObserver;
    public SmartManagerFASObserver mSmartManagerFASObserver;
    public SmartManagerFreezeExcludeListObserver mSmartManagerFreezeExcludeListObserver;
    public SmartManagerSettingsObserver mSmartManagerSettingsObserver;

    public abstract class MARsDBManagerHolder {
        public static final MARsDBManager INSTANCE = new MARsDBManager();
    }

    public MARsDBManager() {
        this.TAG = MARsDBManager.class.getSimpleName();
        this.mRegisteredSmartManagerSettingsObserver = false;
        this.mRegisteredSmartManagerForcedAppStandbyObserver = false;
        this.mRegisteredSmartManagerDefaultAllowedListObserver = false;
        this.mRegisteredSmartManagerFreezeExcludeListObserver = false;
        this.mDBUpdated = false;
        this.mDBCreate = false;
        this.mAppStartUpList = new ArrayList();
        this.MARsLocalVersion = null;
        this.MARsDBVersion = null;
        this.mMaxFailCountForSCPM = 3;
        this.mFASDBupdateRunnable = new Runnable() { // from class: com.android.server.am.mars.database.MARsDBManager.1
            @Override // java.lang.Runnable
            public void run() {
                ArrayList fASDataFromDB = FASDataManager.getInstance().getFASDataFromDB();
                if (fASDataFromDB != null && fASDataFromDB.size() > 0) {
                    MARsPolicyManager.getInstance().updateMARsTargetPackages(fASDataFromDB);
                } else {
                    Slog.e(MARsDBManager.this.TAG, "Packages database not exist, and not created!!");
                }
            }
        };
        this.mIsBigDataRestrictionColumnExist = false;
    }

    public static MARsDBManager getInstance() {
        return MARsDBManagerHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.mCurrentContext = context;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void init(Context context) {
        setContext(context);
        MARsDBManagerThread mARsDBManagerThread = new MARsDBManagerThread("MARsDBManagerThread", 0);
        this.mMARsDBManagerThread = mARsDBManagerThread;
        mARsDBManagerThread.start();
        FASDataManager.getInstance().init(getContext());
    }

    public void switchUser(Context context) {
        unregisterContentObservers(this.mCurrentContext, true);
        this.mCurrentContext = context;
        registerContentObservers(context);
    }

    public class MARsDBManagerThread extends Thread {
        public int mPriority;

        public MARsDBManagerThread(String str, int i) {
            super(str);
            this.mPriority = i;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(this.mPriority);
            Looper.prepare();
            MARsDBManager.this.mMARsDBManagerHandler = MARsDBManager.this.new MARsDBManagerHandler();
            Looper.loop();
        }
    }

    public class MARsDBManagerHandler extends Handler {
        public Bundle extras = null;

        public MARsDBManagerHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ArrayList arrayList;
            FASEntity fASEntity;
            switch (message.what) {
                case 1:
                    MARsDBManager.this.getSettingsValueFromDB(true);
                    MARsDBManager.this.getDefaultAllowedListDBValues();
                    if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                        MARsDBManager.this.getFreezeExcludeListFromDB();
                        break;
                    }
                    break;
                case 2:
                    Bundle data = message.getData();
                    if (data != null && (arrayList = (ArrayList) data.getSerializable("values")) != null) {
                        MARsDBManager.this.updatePkgsToSMDB(arrayList);
                        break;
                    }
                    break;
                case 3:
                    Bundle data2 = message.getData();
                    if (data2 != null && (fASEntity = (FASEntity) data2.getSerializable("value")) != null) {
                        MARsDBManager.this.updatePkgToSMDB(fASEntity);
                        break;
                    }
                    break;
                case 4:
                    Bundle data3 = message.getData();
                    if (data3 != null) {
                        MARsDBManager.this.updateCompHistory(data3.getString("callee", null), data3.getString("caller", null), data3.getBoolean("isblock", true), data3.getLong("requesttime", System.currentTimeMillis()));
                        break;
                    }
                    break;
                case 5:
                    Bundle data4 = message.getData();
                    if (data4 != null) {
                        MARsDBManager.this.getPolicyDefaultInfoFromSMToMARs(data4.getBoolean("boot", false));
                        break;
                    }
                    break;
                case 6:
                    Bundle data5 = message.getData();
                    if (data5 != null) {
                        MARsDBManager.this.mDBCreate = data5.getBoolean("onCreate", false);
                        boolean z = data5.getBoolean("onUpgrade", false);
                        boolean z2 = data5.getBoolean("onInit", false);
                        if (MARsDebugConfig.DEBUG_MARs) {
                            Slog.d(MARsDBManager.this.TAG, "Received MARS_DB_SM_CHANGED_MSG, --mDBCreate = " + MARsDBManager.this.mDBCreate + " --onUpgrade = " + z + " --onInit = " + z2 + " --mDBUpdated = " + MARsDBManager.this.mDBUpdated);
                        }
                        if (z2) {
                            MARsPolicyManager.getInstance().cancelAllPolicy();
                            MARsDBManager.this.getSettingsValueFromDB(false);
                        }
                        if (z || z2) {
                            MARsPolicyManager.getInstance().clearAllPackages();
                        }
                        if (MARsDBManager.this.mDBCreate || z || z2) {
                            if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                                MARsPolicyManager.getInstance().setFirstTimeUpdatePkgsState(true);
                            }
                            MARsDBManager.this.getDefaultAllowedListDBValues();
                            MARsDBManager.this.initManagedPackagesInternal();
                        }
                        if (z2) {
                            MARsDBManager.this.sendMigrateMsgToDBHandler();
                            break;
                        }
                    }
                    break;
                case 7:
                    Bundle data6 = message.getData();
                    this.extras = data6;
                    if (data6 != null) {
                        Context contextForUser = MARsDBManager.this.getContextForUser(new UserHandle(data6.getInt("userId", -1)));
                        Slog.d(MARsDBManager.this.TAG, "mContext.id = " + contextForUser.getUserId());
                        MARsDBManager.this.getMARsSettingsInfoForNotificationTime(contextForUser);
                        break;
                    }
                    break;
                case 8:
                    MARsDBManager.this.doMigration();
                    break;
                case 9:
                    MARsDBManager.this.getRestrictionFlagFromSMToMARs();
                    break;
                case 10:
                    MARsDBManager.this.getSCPMList();
                    break;
                case 11:
                    MARsDBManager.this.getSCPMPolicyForFreecess();
                    break;
                case 12:
                    MARsPolicyManager.getInstance().updateResetTime();
                    MARsDBManager.this.sendUpdateDisableResetTimeToDBHandler(true);
                    break;
            }
        }
    }

    public final Context getContextForUser(UserHandle userHandle) {
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            return this.mContext;
        }
    }

    public void sendInitSettingMsgToDBHandler() {
        this.mMARsDBManagerHandler.sendMessage(this.mMARsDBManagerHandler.obtainMessage(1));
        this.mMARsDBManagerHandler.post(this.mFASDBupdateRunnable);
        getSCPMList();
        sendGetSCPMPolicyMsgToDBHandler();
    }

    public void sendSdhmsDBCompleteMsgToDBHandler() {
        Slog.d(this.TAG, "sendSdhmsDBCompleteMsgToDBHandler");
        this.mMARsDBManagerHandler.post(this.mFASDBupdateRunnable);
    }

    public void sendUpdateResetTimeMsgToDBHandler(ArrayList arrayList) {
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(2);
        Bundle bundle = new Bundle();
        bundle.putSerializable("values", arrayList);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendUpdateDisableResetTimeToDBHandler(boolean z) {
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(12);
        if (z) {
            this.mMARsDBManagerHandler.sendMessageDelayed(obtainMessage, 43200000L);
        } else {
            this.mMARsDBManagerHandler.sendMessage(obtainMessage);
        }
    }

    public void sendUpdateResetTimeSpecificMsgToDBHandler(FASEntity fASEntity) {
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(3);
        Bundle bundle = new Bundle();
        bundle.putSerializable("value", fASEntity);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendUpdateAppStartUpInfoMsgToDBHandler(String str, String str2, boolean z, long j) {
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString("callee", str);
        bundle.putString("caller", str2);
        bundle.putBoolean("isblock", z);
        bundle.putLong("requesttime", j);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendGetMARsPolicyConditionMsgToDBHandler(boolean z) {
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(5);
        Bundle bundle = new Bundle();
        bundle.putBoolean("boot", z);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendSMDBChangedMsgToDBHandler(boolean z, boolean z2, boolean z3) {
        this.mMARsDBManagerHandler.removeMessages(6);
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(6);
        Bundle bundle = new Bundle();
        bundle.putBoolean("onCreate", z);
        bundle.putBoolean("onUpgrade", z2);
        bundle.putBoolean("onInit", z3);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendUpdateNotiTimeMsgToMainHandler(int i) {
        if (this.mMARsDBManagerHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("userId", i);
        Message obtainMessage = this.mMARsDBManagerHandler.obtainMessage(7);
        obtainMessage.setData(bundle);
        this.mMARsDBManagerHandler.sendMessage(obtainMessage);
    }

    public void sendMigrateMsgToDBHandler() {
        MARsDBManagerHandler mARsDBManagerHandler = this.mMARsDBManagerHandler;
        if (mARsDBManagerHandler == null) {
            return;
        }
        if (mARsDBManagerHandler.hasMessages(8)) {
            this.mMARsDBManagerHandler.removeMessages(8);
        }
        this.mMARsDBManagerHandler.sendMessageDelayed(this.mMARsDBManagerHandler.obtainMessage(8), 1000L);
    }

    public final void doMigration() {
        Slog.i(this.TAG, "doMigration send MARS_REQUEST_MIGRATE");
        try {
            if (this.mContext.getContentResolver() != null) {
                this.mContext.getContentResolver().call(FASTableContract.DC_AUTHORITY_FAS_URI, "FasDataMigration", (String) null, (Bundle) null);
            }
        } catch (Exception e) {
            Slog.e(this.TAG, "Error on migrateFasData e = " + e.toString());
        }
    }

    public void sendGetRestrictionFlagMsgToMainHandler() {
        MARsDBManagerHandler mARsDBManagerHandler = this.mMARsDBManagerHandler;
        if (mARsDBManagerHandler == null) {
            return;
        }
        this.mMARsDBManagerHandler.sendMessage(mARsDBManagerHandler.obtainMessage(9));
    }

    public void sendGetSCPMListMsgToMainHandler(long j) {
        MARsDBManagerHandler mARsDBManagerHandler = this.mMARsDBManagerHandler;
        if (mARsDBManagerHandler == null) {
            return;
        }
        mARsDBManagerHandler.removeMessages(10);
        this.mMARsDBManagerHandler.sendMessageDelayed(this.mMARsDBManagerHandler.obtainMessage(10), j);
        Slog.d(this.TAG, "sendGetSCPMListMsgToMainHandler");
    }

    public void sendGetSCPMPolicyMsgToDBHandler() {
        MARsDBManagerHandler mARsDBManagerHandler = this.mMARsDBManagerHandler;
        if (mARsDBManagerHandler == null) {
            return;
        }
        mARsDBManagerHandler.removeMessages(11);
        this.mMARsDBManagerHandler.sendMessageDelayed(this.mMARsDBManagerHandler.obtainMessage(11), 1000L);
        Slog.d(this.TAG, "sendGetSCPMPolicyMsgToDBHandler");
    }

    public final void updatePkgsToSMDB(ArrayList arrayList) {
        try {
            Slog.d(this.TAG, "updatePkgsToSMDB : begin --size " + arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                updatePkgToSMDB((FASEntity) arrayList.get(i));
            }
            Slog.d(this.TAG, "updatePkgsToSMDB : end");
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception on handling DB : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePkgToSMDB(FASEntity fASEntity) {
        if (fASEntity == null) {
            return;
        }
        try {
            doUpdatePkgToSMDB(fASEntity.getStrPkgName() != null ? fASEntity.getStrPkgName() : null, fASEntity.getStrUid() != null ? fASEntity.getStrUid() : null, fASEntity.getStrMode() != null ? fASEntity.getStrMode() : null, fASEntity.getStrNew() != null ? fASEntity.getStrNew() : null, fASEntity.getStrFasReason() != null ? fASEntity.getStrFasReason() : null, fASEntity.getStrExtras() != null ? fASEntity.getStrExtras() : null, fASEntity.getStrResetTime() != null ? fASEntity.getStrResetTime() : null, fASEntity.getStrPackageType() != null ? fASEntity.getStrPackageType() : null, fASEntity.getStrLevel() != null ? fASEntity.getStrLevel() : null, fASEntity.getStrDisableType() != null ? fASEntity.getStrDisableType() : null, fASEntity.getStrDisableResetTime() != null ? fASEntity.getStrDisableResetTime() : null, fASEntity.getStrPreBatteryUsage() != null ? fASEntity.getStrPreBatteryUsage() : null, fASEntity.getStrDisableReason() != null ? fASEntity.getStrDisableReason() : null);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception on handling DB : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public final void doUpdatePkgToSMDB(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13) {
        if (str == null || str2 == null) {
            Slog.e(this.TAG, "parameter is error!");
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
            this.mContext.getContentResolver().update(FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI.buildUpon().appendQueryParameter("MARs-self", "true").build(), contentValues, "package_name=? AND uid=?", new String[]{str, str2});
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception with update() : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public final void updateCompHistory(String str, String str2, boolean z, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("calleepackage", str);
        contentValues.put("callerpackage", str2);
        contentValues.put("isblocked", Boolean.toString(z));
        contentValues.put("requestTime", Long.toString(j));
        synchronized (this.mAppStartUpList) {
            this.mAppStartUpList.add(contentValues);
            if (this.mAppStartUpList.size() > 99) {
                doUpdateCompHistory(false);
            }
        }
    }

    public void doUpdateCompHistory(boolean z) {
        if (this.mAppStartUpList.size() == 0) {
            return;
        }
        try {
            ContentValues[] contentValuesArr = new ContentValues[this.mAppStartUpList.size()];
            this.mAppStartUpList.toArray(contentValuesArr);
            this.mCurrentContext.getContentResolver().bulkInsert(FASTableContract.SMART_MGR_APP_STARTUP_URI, contentValuesArr);
            if (z) {
                return;
            }
            this.mAppStartUpList.clear();
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception on handling DB : " + e.getMessage());
        }
    }

    public final void getMARsSettingsInfoForNotificationTime(Context context) {
        if (context == null) {
            Slog.e(this.TAG, "getMARsSettingsInfoForNotificationTime() context is null!");
            return;
        }
        try {
            Cursor query = context.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, new String[]{"key", "value"}, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    if (query.getString(0) != null) {
                        String string = query.getString(0);
                        String string2 = query.getString(1);
                        try {
                            if ("deep_sleep_notification_time".equals(string)) {
                                MARsPolicyManager.getInstance().setLastNotiSentTimeForDisabled(Long.parseLong(string2));
                            }
                        } catch (Exception e) {
                            Slog.e(this.TAG, "Exception with parseLong : " + e.getMessage());
                        }
                    }
                }
                query.close();
                return;
            }
            Slog.e(this.TAG, "getMARsSettingsInfoForNotificationTime error, no database!!");
        } catch (Exception e2) {
            Slog.e(this.TAG, "getMARsSettingsInfoForNotificationTime : Exception : " + e2);
        }
    }

    public final void getPolicyDefaultInfoFromSMToMARs(boolean z) {
        if (isCompareDBVersion()) {
            ArrayList initMARsSettingsDefinitionInternal = initMARsSettingsDefinitionInternal();
            if (initMARsSettingsDefinitionInternal != null && initMARsSettingsDefinitionInternal.size() > 0) {
                MARsVersionManager.getInstance().setMARsSettingsInfoList(initMARsSettingsDefinitionInternal);
            } else {
                MARsVersionManager.getInstance().getMARsSettingsInfoFromDefaultValue();
            }
            ArrayList initPolicyDefinitionInternal = initPolicyDefinitionInternal();
            if (initPolicyDefinitionInternal != null && initPolicyDefinitionInternal.size() > 0) {
                MARsVersionManager.getInstance().setPolicy(initPolicyDefinitionInternal);
            } else {
                MARsVersionManager.getInstance().getPolicyFromDefaultValue();
            }
            ArrayList initAdjustTargetExcludePackageInternal = initAdjustTargetExcludePackageInternal();
            if (initAdjustTargetExcludePackageInternal != null && initAdjustTargetExcludePackageInternal.size() > 0) {
                MARsVersionManager.getInstance().setAdjustTargetExcludePackage(initAdjustTargetExcludePackageInternal);
            } else {
                MARsVersionManager.getInstance().getExcludeTargetFromDefaultValue();
            }
            ArrayList initAdjustTargetIsCurrentImportantInternal = initAdjustTargetIsCurrentImportantInternal();
            if (initAdjustTargetIsCurrentImportantInternal != null && initAdjustTargetIsCurrentImportantInternal.size() > 0) {
                MARsVersionManager.getInstance().setAdjustTargetCurrentImportant(initAdjustTargetIsCurrentImportantInternal);
            } else {
                MARsVersionManager.getInstance().getIsCurrentImportantFromDefaultValue();
            }
            ArrayList initAdjustRestrictionInternal = initAdjustRestrictionInternal();
            if (initAdjustRestrictionInternal != null && initAdjustRestrictionInternal.size() > 0) {
                MARsVersionManager.getInstance().setAdjustRestriction(initAdjustRestrictionInternal);
            } else {
                MARsVersionManager.getInstance().getAdjustRestrictionFromDefaultValue();
            }
        } else {
            MARsVersionManager.getInstance().getMARsSettingsInfoFromDefaultValue();
            MARsVersionManager.getInstance().getPolicyFromDefaultValue();
            MARsVersionManager.getInstance().getExcludeTargetFromDefaultValue();
            MARsVersionManager.getInstance().getIsCurrentImportantFromDefaultValue();
            MARsVersionManager.getInstance().getAdjustRestrictionFromDefaultValue();
        }
        MARsPolicyManager.getInstance().setFakeTopActivityList(MARsVersionManager.getInstance().mRestrictionList);
        MARsPolicyManager.getInstance().createPolicies();
        MARsPolicyManager.getInstance().initInternal(z);
        if (z) {
            return;
        }
        MARsPolicyManager.getInstance().cancelAllPolicy();
    }

    public final ArrayList initMARsSettingsDefinitionInternal() {
        Cursor cursor;
        String[] strArr = {"key", "value"};
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "initMARsSettingsDefinitionInternal : Exception : " + e);
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    String string = cursor.getString(0);
                    String string2 = cursor.getString(1);
                    MARsVersionManager mARsVersionManager = MARsVersionManager.getInstance();
                    Objects.requireNonNull(mARsVersionManager);
                    arrayList.add(mARsVersionManager.new MARsSettingsInfo(string, string2));
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "initMARsSettingsDefinitionInternal error, no database!!");
        }
        return arrayList;
    }

    public final ArrayList initPolicyDefinitionInternal() {
        String[] strArr;
        Cursor cursor;
        checkBigDataRestrictionColumnExist();
        if (this.mIsBigDataRestrictionColumnExist) {
            strArr = new String[]{"policyNum", "isPolicyEnabled", "targetCategory", "restriction", "killType", "firstTriggerTime", "repeatTriggerTime", "bigdataRestriction"};
        } else {
            strArr = new String[]{"policyNum", "isPolicyEnabled", "targetCategory", "restriction", "killType", "firstTriggerTime", "repeatTriggerTime"};
        }
        String[] strArr2 = strArr;
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_POLICY_DEFINITION, strArr2, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "initPolicyDefinitionInternal: Exception : " + e);
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    int parseInt = Integer.parseInt(cursor.getString(0));
                    int parseInt2 = Integer.parseInt(cursor.getString(1));
                    int parseInt3 = Integer.parseInt(cursor.getString(2));
                    int parseInt4 = Integer.parseInt(cursor.getString(3));
                    int parseInt5 = Integer.parseInt(cursor.getString(4));
                    long parseLong = Long.parseLong(cursor.getString(5));
                    long parseLong2 = Long.parseLong(cursor.getString(6));
                    String convertPolicyNumToName = MARsVersionManager.getInstance().convertPolicyNumToName(parseInt);
                    MARsVersionManager mARsVersionManager = MARsVersionManager.getInstance();
                    Objects.requireNonNull(mARsVersionManager);
                    arrayList.add(mARsVersionManager.new PolicyInfo(convertPolicyNumToName, parseInt, parseInt2, parseInt3, parseInt4, parseInt5, parseLong, parseLong2, 0));
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "initPolicyDefinitionInternal error, no database!!");
        }
        return arrayList;
    }

    public final ArrayList initAdjustTargetExcludePackageInternal() {
        Cursor cursor;
        String[] strArr = {"policyNum", "condition", "matchType", "packageName"};
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_TARGET_EXCLUDE_PACKAGE, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "initAdjustTargetExcludePackageInternal: Exception : " + e);
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    int parseInt = Integer.parseInt(cursor.getString(0));
                    int parseInt2 = Integer.parseInt(cursor.getString(1));
                    String string = cursor.getString(2);
                    String string2 = cursor.getString(3);
                    MARsVersionManager mARsVersionManager = MARsVersionManager.getInstance();
                    Objects.requireNonNull(mARsVersionManager);
                    arrayList.add(mARsVersionManager.new AdjustTargetExcludePackage(parseInt, parseInt2, string, string2));
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "initAdjustTargetExcludePackageInternal error, no database!!");
        }
        return arrayList;
    }

    public final ArrayList initAdjustTargetIsCurrentImportantInternal() {
        Cursor cursor;
        String[] strArr = {"policyNum", "currentImportantValue"};
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_TARGET_CURRENT_IMPORTANT, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "initAdjustTargetIsCurrentImportantInternal: Exception : " + e);
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    int parseInt = Integer.parseInt(cursor.getString(0));
                    int parseInt2 = Integer.parseInt(cursor.getString(1));
                    MARsVersionManager mARsVersionManager = MARsVersionManager.getInstance();
                    Objects.requireNonNull(mARsVersionManager);
                    arrayList.add(mARsVersionManager.new AdjustTargetCurrentImportant(parseInt, parseInt2));
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "initAdjustTargetIsCurrentImportantInternal error, no database!!");
        }
        return arrayList;
    }

    public final ArrayList initAdjustRestrictionInternal() {
        Cursor cursor;
        String[] strArr = {"restrictionType", "isAllowed", "callee", "caller", "matchType", "action"};
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_RESTRICTION, strArr, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "initAdjustRestrictionInternal: Exception : " + e);
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    int parseInt = Integer.parseInt(cursor.getString(0));
                    String string = cursor.getString(1);
                    String normalText = MARsVersionManager.toNormalText(cursor.getString(2));
                    String normalText2 = MARsVersionManager.toNormalText(cursor.getString(3));
                    String string2 = cursor.getString(4);
                    String normalText3 = MARsVersionManager.toNormalText(cursor.getString(5));
                    if (parseInt == 12) {
                        FreecessController.getInstance().setPendingIntentList(string, normalText3);
                    } else if (parseInt == 13) {
                        FreecessController.getInstance().setPendingBlocklistForGPS(normalText);
                    } else if (parseInt == 16) {
                        FreecessController.getInstance().setOLAFBlockList(normalText);
                    } else if (parseInt == 18) {
                        FreecessController.getInstance().setProcessAllowList(normalText, string2);
                    } else {
                        MARsVersionManager mARsVersionManager = MARsVersionManager.getInstance();
                        Objects.requireNonNull(mARsVersionManager);
                        arrayList.add(mARsVersionManager.new AdjustRestriction(parseInt, string, normalText, normalText2, string2, normalText3));
                    }
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "initAdjustRestrictionInternal error, no database!!");
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void getSCPMList() {
        boolean z;
        int i;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        try {
            Bundle call = this.mContext.getContentResolver().call(MARsTableContract.SCPM_AUTHORITY_URI, "dc_scpm_get_deep_sleep_specific_list", (String) null, new Bundle());
            z = call.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT);
            try {
                if (z) {
                    int i3 = call.getInt("result_id");
                    Slog.d(this.TAG, "getScpmData : isSuccess : " + i3);
                    IDeviceIdleController asInterface = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
                    ArrayList<String> stringArrayList = call.getStringArrayList("package_list");
                    int i4 = 0;
                    while (i2 < stringArrayList.size()) {
                        try {
                            String str = stringArrayList.get(i2);
                            arrayList.add(str);
                            if (asInterface.isPowerSaveWhitelistApp(str)) {
                                try {
                                    asInterface.removePowerSaveWhitelistApp(str);
                                    i4 = 1;
                                } catch (Exception e) {
                                    e = e;
                                    i2 = 1;
                                    Slog.e(this.TAG, "SCPM not available : " + e.toString());
                                    if (z) {
                                    }
                                    if (!arrayList.isEmpty()) {
                                    }
                                    sendGetSCPMListMsgToMainHandler(43200000L);
                                }
                            }
                            i2++;
                        } catch (Exception e2) {
                            e = e2;
                            i2 = i4;
                        }
                    }
                    i2 = i4;
                } else {
                    int i5 = call.getInt("error_id");
                    Slog.d(this.TAG, "getScpmData : fail : " + i5);
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
            z = false;
        }
        if (z && (i = this.mMaxFailCountForSCPM) > 0) {
            this.mMaxFailCountForSCPM = i - 1;
            sendGetSCPMListMsgToMainHandler(60000L);
            return;
        }
        if (!arrayList.isEmpty()) {
            if (i2 == 0) {
                MARsPolicyManager.getInstance().setSCPMList(arrayList);
            } else {
                sendGetSCPMListMsgToMainHandler(60000L);
                return;
            }
        }
        sendGetSCPMListMsgToMainHandler(43200000L);
    }

    public void getSCPMPolicyForFreecess() {
        try {
            Bundle call = this.mContext.getContentResolver().call(MARsTableContract.SCPM_AUTHORITY_URI, "dc_scpm_mars_app_freeze_settings", (String) null, new Bundle());
            if (call.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT)) {
                FreecessController.getInstance().setFreecessPolicyFromSCPM(call.getString("app_freeze_settings_json"));
            }
        } catch (Exception e) {
            Slog.e(this.TAG, "getSCPMPolicyForFreecess not available : " + e.toString());
        }
    }

    public String getSMDBVersionFromDB() {
        Cursor cursor;
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.v(this.TAG, "getSMDBVersionFromDB!");
        }
        String str = null;
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, new String[]{"value"}, "key=?", new String[]{"marsversion"}, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    str = cursor.getString(0);
                }
            }
            cursor.close();
        } else {
            Slog.e(this.TAG, "getSMDBVersionFromDB error, no database!!");
        }
        return str;
    }

    public void getRestrictionFlagFromSMToMARs() {
        int restrictionFlag;
        boolean isCompareDBVersion = isCompareDBVersion();
        if (this.MARsDBVersion == null) {
            return;
        }
        if (isCompareDBVersion) {
            restrictionFlag = getRestrictionFlagFromDB();
        } else {
            restrictionFlag = MARsVersionManager.getInstance().getRestrictionFlag();
        }
        FreecessController.getInstance().setRestrictionFlagFromDC(restrictionFlag);
    }

    public int getRestrictionFlagFromDB() {
        Cursor cursor;
        try {
            cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, new String[]{"value"}, "key=?", new String[]{"restriction_flag"}, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    i = Integer.parseInt(cursor.getString(0));
                }
            }
            return i;
        }
        int restrictionFlag = MARsVersionManager.getInstance().getRestrictionFlag();
        Slog.e(this.TAG, "getRestrictionFlagFromDB error, no database!!");
        return restrictionFlag;
    }

    public final boolean isCompareDBVersion() {
        String str;
        this.MARsLocalVersion = MARsVersionManager.getInstance().getMARsLocalVersionFromDefaultValue();
        this.MARsDBVersion = getSMDBVersionFromDB();
        getMARsSettingsInfoForNotificationTime(this.mContext);
        if (this.MARsDBVersion == null) {
            return false;
        }
        String str2 = null;
        try {
            str = this.MARsLocalVersion.substring(0, 2);
        } catch (StringIndexOutOfBoundsException unused) {
            Slog.e(this.TAG, "isCompareDBVersion(), IndexOutOfBoundsException");
            str = null;
        }
        try {
            str2 = this.MARsDBVersion.substring(0, 2);
        } catch (StringIndexOutOfBoundsException unused2) {
            Slog.e(this.TAG, "isCompareDBVersion(), IndexOutOfBoundsException");
        }
        return str2 != null && str != null && str.equals(str2) && Integer.parseInt(this.MARsDBVersion) >= Integer.parseInt(this.MARsLocalVersion);
    }

    public final void initManagedPackagesInternal() {
        ArrayList fASDataFromDB = FASDataManager.getInstance().getFASDataFromDB();
        if (fASDataFromDB != null && fASDataFromDB.size() > 0) {
            MARsPolicyManager.getInstance().updateMARsTargetPackages(fASDataFromDB);
        } else {
            Slog.e(this.TAG, "Packages database not exist, and not created!!");
        }
        if (this.mRegisteredSmartManagerForcedAppStandbyObserver) {
            return;
        }
        this.mSmartManagerFASObserver = new SmartManagerFASObserver(this.mMARsDBManagerHandler);
        this.mContext.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI, true, this.mSmartManagerFASObserver, this.mContext.getUserId());
        this.mRegisteredSmartManagerForcedAppStandbyObserver = true;
    }

    public void checkBigDataRestrictionColumnExist() {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mContext.getContentResolver().query(MARsTableContract.MARS_POLICY_DEFINITION, new String[]{"bigdataRestriction"}, null, null, null);
                this.mIsBigDataRestrictionColumnExist = true;
                if (cursor == null) {
                    return;
                }
            } catch (SQLiteException unused) {
                Slog.v(this.TAG, "checkBigDataRestrictionColumnExist-sql, catch no column exception!");
                this.mIsBigDataRestrictionColumnExist = false;
                if (cursor == null) {
                    return;
                }
            } catch (IllegalArgumentException unused2) {
                Slog.v(this.TAG, "checkBigDataRestrictionColumnExist, catch no column exception!");
                this.mIsBigDataRestrictionColumnExist = false;
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public boolean isSMProviderExist() {
        try {
            Cursor query = this.mCurrentContext.getContentResolver().query(FASTableContract.SMART_MGR_SETTINGS_URI, null, null, null, null);
            if (query == null) {
                return false;
            }
            query.close();
            return true;
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception occurred in isSMProviderExist : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public final void getDefaultAllowedListDBValues() {
        Cursor cursor;
        ArrayList arrayList = new ArrayList();
        String[] strArr = new String[1];
        strArr[0] = MARsPolicyManager.getInstance().isChinaPolicyEnabled() ? "1" : "0";
        try {
            cursor = this.mCurrentContext.getContentResolver().query(FASTableContract.SMART_MGR_DEFAULT_ALLOWED_LIST_URI, new String[]{"package_name"}, "type=?", strArr, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(0) != null) {
                    arrayList.add(cursor.getString(0));
                }
            }
            cursor.close();
        }
        AllowListFilter.getInstance().setDefaultAllowList(arrayList);
    }

    public final void getSettingsValueFromDB(boolean z) {
        Cursor cursor;
        try {
            cursor = this.mCurrentContext.getContentResolver().query(FASTableContract.SMART_MGR_SETTINGS_URI, new String[]{"key", "value"}, null, null, null);
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String string = cursor.getString(0);
                try {
                    if ("spcm_locking_time".equals(string)) {
                        MARsPolicyManager.getInstance().setPackagesUnusedLockingTime(Integer.parseInt(cursor.getString(1)));
                    }
                    if ("spcm_switch".equals(string)) {
                        MARsPolicyManager.getInstance().setAllPoliciesOnOffState(Integer.parseInt(cursor.getString(1)), z);
                    }
                    if ("ConfigCompTracing".equals(string)) {
                        MARsComponentTracker.getInstance().setEnabled(Integer.parseInt(cursor.getString(1)) > 0);
                    }
                } catch (Exception e2) {
                    Slog.e(this.TAG, "Exception with parseInt : " + e2.getMessage());
                }
            }
            cursor.close();
        }
    }

    public final void getFreezeExcludeListFromDB() {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = this.mContext.getContentResolver().query(FASTableContract.SMART_MGR_FREEZE_EXCLUDE_LIST_URI, new String[]{"package_name"}, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(0);
                        if (string != null) {
                            if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d(this.TAG, "pkgname = " + string);
                            }
                            arrayList.add(string);
                        }
                    }
                    FreecessController.getInstance().setFreezeExcludeList(arrayList);
                }
                if (cursor == null) {
                    return;
                }
            } catch (Exception e) {
                Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
                e.printStackTrace();
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void registerContentObservers(Context context) {
        try {
            if (!this.mRegisteredSmartManagerSettingsObserver) {
                this.mSmartManagerSettingsObserver = new SmartManagerSettingsObserver(this.mMARsDBManagerHandler);
                context.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_SETTINGS_URI, false, this.mSmartManagerSettingsObserver, context.getUserId());
                this.mRegisteredSmartManagerSettingsObserver = true;
            }
            if (!this.mRegisteredSmartManagerDefaultAllowedListObserver) {
                this.mSmartManagerDefaultAllowedListObserver = new SmartManagerDefaultAllowedListObserver(this.mMARsDBManagerHandler);
                context.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_DEFAULT_ALLOWED_LIST_URI, false, this.mSmartManagerDefaultAllowedListObserver, context.getUserId());
                this.mRegisteredSmartManagerDefaultAllowedListObserver = true;
            }
            if (!this.mRegisteredSmartManagerForcedAppStandbyObserver) {
                this.mSmartManagerFASObserver = new SmartManagerFASObserver(this.mMARsDBManagerHandler);
                context.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI, true, this.mSmartManagerFASObserver, context.getUserId());
                this.mRegisteredSmartManagerForcedAppStandbyObserver = true;
            }
            if (this.mRegisteredSmartManagerFreezeExcludeListObserver || !MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                return;
            }
            this.mSmartManagerFreezeExcludeListObserver = new SmartManagerFreezeExcludeListObserver(this.mMARsDBManagerHandler);
            this.mContext.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_FREEZE_EXCLUDE_LIST_URI, false, this.mSmartManagerFreezeExcludeListObserver, this.mContext.getUserId());
            this.mRegisteredSmartManagerFreezeExcludeListObserver = true;
        } catch (Exception e) {
            Slog.d(this.TAG, "Exception : " + e);
        }
    }

    public void unregisterContentObservers(Context context, boolean z) {
        try {
            if (this.mRegisteredSmartManagerSettingsObserver) {
                context.getContentResolver().unregisterContentObserver(this.mSmartManagerSettingsObserver);
                this.mRegisteredSmartManagerSettingsObserver = false;
            }
            if (!z && this.mRegisteredSmartManagerForcedAppStandbyObserver) {
                context.getContentResolver().unregisterContentObserver(this.mSmartManagerFASObserver);
                this.mRegisteredSmartManagerForcedAppStandbyObserver = false;
            }
            if (this.mRegisteredSmartManagerFreezeExcludeListObserver) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mSmartManagerFreezeExcludeListObserver);
                this.mRegisteredSmartManagerFreezeExcludeListObserver = false;
            }
            if (this.mRegisteredSmartManagerDefaultAllowedListObserver) {
                context.getContentResolver().unregisterContentObserver(this.mSmartManagerDefaultAllowedListObserver);
                this.mRegisteredSmartManagerDefaultAllowedListObserver = false;
            }
        } catch (IllegalArgumentException unused) {
            Slog.e(this.TAG, "IllegalArgumentException occurred in unregisterContentObserver()");
        }
    }

    public class SmartManagerDefaultAllowedListObserver extends ContentObserver {
        public SmartManagerDefaultAllowedListObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(MARsDBManager.this.TAG, "onChange - mSmartManagerDefaultAllowedListObserver!");
            }
            MARsDBManager.this.getDefaultAllowedListDBValues();
        }
    }

    public class SmartManagerSettingsObserver extends ContentObserver {
        public SmartManagerSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(MARsDBManager.this.TAG, "onChange - mSmartManagerSettingsObserver!");
            }
            MARsDBManager.this.getSettingsValueFromDB(false);
        }
    }

    public class SmartManagerFASObserver extends ContentObserver {
        public SmartManagerFASObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            if (uri == null) {
                return;
            }
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(MARsDBManager.this.TAG, "onChange - mSmartManagerFASObserver! Uri = " + uri);
            }
            if (uri.getBooleanQueryParameter("MARs-self", false)) {
                return;
            }
            MARsDBManager.this.mMARsDBManagerHandler.post(MARsDBManager.this.mFASDBupdateRunnable);
        }
    }

    public class SmartManagerFreezeExcludeListObserver extends ContentObserver {
        public SmartManagerFreezeExcludeListObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (MARsDebugConfig.DEBUG_ENG) {
                Slog.d(MARsDBManager.this.TAG, "onChange - mSmartManagerFreezeExcludeListObserver!");
            }
            MARsDBManager.this.getFreezeExcludeListFromDB();
        }
    }
}
