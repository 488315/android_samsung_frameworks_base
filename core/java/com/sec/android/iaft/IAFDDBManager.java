package com.sec.android.iaft;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.Process;
import android.util.Slog;
import com.sec.android.iaft.IAFDDiagnosis;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class IAFDDBManager {
    static final int CONTROLINFOTB_code = 1;
    static final String DB_IAFD_TB = "IAFD_TB";
    static final int EXP_32BITAPP = 30;
    static final int EXP_AllFilesAccess = 27;
    static final int EXP_FeatureControl = 38;
    static final int EXP_NoEnoughSpace = 34;
    static final int EXP_NoSettingsProvidersForDual = 35;
    static final int EXP_OOM = 25;
    static final int EXP_REMOVABLEAPP = 31;
    static final int EXP_RepairLinks = 37;
    static final int EXP_RepairOnlyShowList = 39;
    static final int EXP_SUPPORT_AppWhiteLIST = 36;
    static final int EXP_SUPPORT_CSC = 33;
    static final int EXP_WEBVIEWREMOVABLEAPP = 32;
    static final int EXP_WebView = 19;
    static final int HandleDB_HotfixARDB_Update = 251;
    static final int HandleDB_HotfixDB_TryInit = 250;
    static final int HandleDB_HotfixIAFDDB_Update = 252;
    static final int HandleDB_SMDCDB_TryInit = 254;
    static final int HandleDB_SMDCDB_Update = 253;
    static final int HandleDB_allDB_init = 255;
    private static final int IAFDDBTYPE_HC = 0;
    private static final int IAFDDBTYPE_HOTFIX = 2;
    private static final int IAFDDBTYPE_SMDC = 1;
    static final String IAFD_AUTOHORITY_SM = "com.samsung.android.sm";
    static final int IAFD_FW_Version = 5;
    static final int JE_CALLSTACKTB_code = 4;
    static final int JE_CLASSNAMETB_code = 2;
    static final int JE_DETAILMSGTB_code = 3;
    private static final int MAX_DBINIT_RETRY_CNT = 25;
    static final int NE_CALLSTACKTB_code = 5;
    static final int NE_HEADERINFOTB_code = 6;
    private static final String TAG = "IAFDDBManager";
    private static final long mReTryInterval = 5000;
    private boolean isCHNModel;
    private Context mContext;
    private IAFDDBManagerHandler mIAFDDBManagerHandler;
    private IAFDDBManagerThread mIAFDDBManagerThread;
    private IAFDDBObserver mIAFDDBObserver;
    private IAFDDiagnosis.IAFD_DATA[] mIfadDBData;
    private boolean mRegisteredHotfixDBObserver;
    private boolean mRegisteredSmartManagerIAFDObserver;
    private String mSalesCode;
    static int mSMDBInitReTryCnt = 0;
    static int mHotfixDBInitReTryCnt = 0;
    static boolean isDBIniting = false;
    static int DBversion = 1;
    static final String[] columnsSMTB = {"tbID", "expID", "enable", "keyWord", "rule", "suggestion"};
    static final Uri DB_IAFD_TB_URI_SM = Uri.parse("content://com.samsung.android.sm/IAFD_TB");
    static int mCurDBIndex = -1;

    private IAFDDBManager() {
        this.mIfadDBData = new IAFDDiagnosis.IAFD_DATA[]{null, null, null};
        this.mRegisteredSmartManagerIAFDObserver = false;
        this.mRegisteredHotfixDBObserver = false;
        this.mSalesCode = null;
        this.isCHNModel = false;
        this.mIAFDDBManagerThread = null;
        mCurDBIndex = -1;
    }

    private static class IAFDDBManagerHolder {
        private static final IAFDDBManager INSTANCE = new IAFDDBManager();

        private IAFDDBManagerHolder() {
        }
    }

    public static IAFDDBManager getInstance() {
        return IAFDDBManagerHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public IAFDDiagnosis.IAFD_DATA getData() {
        int i = mCurDBIndex;
        if (i < 0) {
            return null;
        }
        return this.mIfadDBData[i];
    }

    public void updateHotfixDB_IAFDDB() {
        this.mIAFDDBManagerHandler.obtainMessage(252).sendToTarget();
    }

    public void updateHotfixDB_ARDB() {
    }

    public void init(Context context, String salesCode, boolean isCHN) {
        setContext(context);
        this.mSalesCode = salesCode;
        this.isCHNModel = isCHN;
        if (this.mIfadDBData[0] == null) {
            IAFDHCDatabase.getInstance().init(this.mContext, this.mSalesCode, this.isCHNModel);
            this.mIfadDBData[0] = IAFDHCDatabase.getInstance().getData();
            syncDBType();
        }
        if (this.mIAFDDBManagerThread == null) {
            IAFDDBManagerThread iAFDDBManagerThread = new IAFDDBManagerThread("IAFDDBManagerThread", 0);
            this.mIAFDDBManagerThread = iAFDDBManagerThread;
            iAFDDBManagerThread.start();
            return;
        }
        this.mIAFDDBManagerHandler.obtainMessage(255).sendToTarget();
    }

    public void deInit() {
        try {
            if (this.mRegisteredSmartManagerIAFDObserver) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mIAFDDBObserver);
                this.mRegisteredSmartManagerIAFDObserver = false;
                this.mRegisteredHotfixDBObserver = false;
            }
        } catch (Exception e) {
            Slog.m113d(TAG, "exception occurred in unregisterContentObserver()");
        }
    }

    private class IAFDDBManagerThread extends Thread {
        int mPriority;

        public IAFDDBManagerThread(String name) {
            super(name);
            this.mPriority = 0;
        }

        public IAFDDBManagerThread(String name, int priority) {
            super(name);
            this.mPriority = priority;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(this.mPriority);
            Looper.prepare();
            IAFDDBManager.this.mIAFDDBManagerHandler = IAFDDBManager.this.new IAFDDBManagerHandler();
            IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(255).sendToTarget();
            Looper.loop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public IAFDDiagnosis.IAFD_DATA initDBByURIOrFile(boolean isURI, Uri db_uri, String dbPath) {
        Cursor cursor;
        boolean z;
        IAFDDiagnosis.IAFD_DATA iafd_data = null;
        Cursor cursor2 = null;
        int i = 1;
        if (isURI) {
            try {
                cursor = this.mContext.getContentResolver().query(DB_IAFD_TB_URI_SM, columnsSMTB, null, null, null);
            } catch (Exception e) {
                e = e;
                e.printStackTrace();
                if (cursor2 == null) {
                }
            }
        } else {
            try {
                SQLiteDatabase mdb = SQLiteDatabase.openDatabase(dbPath, (SQLiteDatabase.CursorFactory) null, 1);
                try {
                    cursor = mdb.query(DB_IAFD_TB, columnsSMTB, null, null, null, null, null);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    if (cursor2 == null) {
                        cursor2.close();
                        return null;
                    }
                    return null;
                }
            } catch (Exception e3) {
                e = e3;
            }
        }
        if (cursor != null) {
            iafd_data = new IAFDDiagnosis.IAFD_DATA();
            int i2 = 0;
            int i3 = 2;
            if (cursor.moveToNext()) {
                iafd_data.controlInfo = new IAFDDiagnosis.IAFD_CONTROLINFO();
                String tmpStr = cursor.getString(3);
                String[] strArray = tmpStr.split(">,<");
                iafd_data.controlInfo.setEnable("1".equals(strArray[0]));
                iafd_data.controlInfo.setJE_cstack_maxSize(Integer.parseInt(strArray[1]));
                iafd_data.controlInfo.setJE_cstack_start(strArray[2]);
                iafd_data.controlInfo.setNE_cstack_maxSize(Integer.parseInt(strArray[3]));
                iafd_data.controlInfo.setNE_cHeader_maxSize(Integer.parseInt(strArray[4]));
                iafd_data.controlInfo.setNE_cstack_start(strArray[5]);
                iafd_data.controlInfo.setReason_maxSize(256);
                iafd_data.controlInfo.setCallstack_maxSize(512);
                iafd_data.controlInfo.setenableDetectAll32bitApp(false, null);
                DBversion = cursor.getInt(2);
                iafd_data.controlInfo.setDBVersion(DBversion);
                if (DBversion > 1) {
                    iafd_data.controlInfo.setReason_maxSize(Integer.parseInt(strArray[6]));
                    iafd_data.controlInfo.setCallstack_maxSize(Integer.parseInt(strArray[7]));
                }
                if (DBversion < 3) {
                    iafd_data.controlInfo.setCSCFilter(null, null, null);
                }
                iafd_data.controlInfo.setWhiteList("1", "android.app.stubs>,<com.android.cts>,<com.android.test>,<com.android.app1>,<com.android.app2>,<com.android.app3");
                iafd_data.controlInfo.setSupportRepair(false);
            }
            int[] arrayCnt = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            HashMap<String, Integer> hashMapCN = new HashMap<>();
            IAFDDiagnosis.IAFD_ENTITY[] tmpTB = new IAFDDiagnosis.IAFD_ENTITY[cursor.getCount() - 1];
            int i4 = 0;
            while (cursor.moveToNext()) {
                int iTB = cursor.getInt(i2);
                int curDBVersion = cursor.getInt(i3);
                int i5 = curDBVersion <= 5 ? i : i2;
                if (curDBVersion != 0) {
                    z = i5;
                } else {
                    z = 0;
                }
                if (iTB == i3) {
                    switch (cursor.getInt(i)) {
                        case 30:
                            iafd_data.controlInfo.setenableDetectAll32bitApp(Boolean.valueOf(z), cursor.getString(5));
                            break;
                        case 31:
                            if (z != 0) {
                                iafd_data.controlInfo.setreMovableAppPaths(cursor.getString(4));
                                break;
                            }
                            break;
                        case 32:
                            if (z != 0) {
                                iafd_data.controlInfo.setwebView_pkgName(cursor.getString(4));
                                break;
                            }
                            break;
                        case 33:
                            if (z != 0) {
                                iafd_data.controlInfo.setCSCFilter(cursor.getString(4), cursor.getString(5), this.mSalesCode);
                                break;
                            }
                            break;
                        case 34:
                        case 35:
                        default:
                            tmpTB[i4] = new IAFDDiagnosis.IAFD_ENTITY(iTB, cursor.getInt(1), Boolean.valueOf(z), cursor.getString(3), cursor.getString(4), cursor.getString(5), i4, hashMapCN);
                            arrayCnt[iTB] = arrayCnt[iTB] + 1;
                            i4++;
                            i = 1;
                            i2 = 0;
                            i3 = 2;
                            continue;
                        case 36:
                            if (z != 0) {
                                iafd_data.controlInfo.setWhiteList(cursor.getString(4), cursor.getString(5));
                                break;
                            }
                            break;
                        case 37:
                            if (z != 0) {
                                iafd_data.controlInfo.sethashMapOfLinkForVocApp(cursor.getString(4));
                                break;
                            }
                            break;
                        case 38:
                            if (z != 0) {
                                iafd_data.controlInfo.setIAFDDBControlFeature(cursor.getString(4), cursor.getString(5), this.isCHNModel);
                                break;
                            }
                            break;
                        case 39:
                            if (z != 0) {
                                iafd_data.controlInfo.sethashMapOfLinkForVocAppOnlyShow(cursor.getString(4));
                                break;
                            }
                            break;
                    }
                    i = 1;
                    i2 = 0;
                    i3 = 2;
                } else {
                    tmpTB[i4] = new IAFDDiagnosis.IAFD_ENTITY(iTB, cursor.getInt(1), Boolean.valueOf(z), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                    arrayCnt[iTB] = arrayCnt[iTB] + 1;
                    i4++;
                    i = 1;
                    i2 = 0;
                    i3 = 2;
                }
            }
            cursor.close();
            cursor2 = null;
            int iStart = 0;
            iafd_data.hashMapJE_ClassNameTB = hashMapCN;
            iafd_data.JE_ClassNameTB = new IAFDDiagnosis.IAFD_ENTITY[arrayCnt[2]];
            int iEnd = 0 + arrayCnt[2];
            int i6 = 0;
            while (iStart < iEnd) {
                iafd_data.JE_ClassNameTB[i6] = tmpTB[iStart];
                i6++;
                iStart++;
            }
            iafd_data.JE_DetailMsgTB = new IAFDDiagnosis.IAFD_ENTITY[arrayCnt[3]];
            int iEnd2 = iEnd + arrayCnt[3];
            int i7 = 0;
            while (iStart < iEnd2) {
                iafd_data.JE_DetailMsgTB[i7] = tmpTB[iStart];
                i7++;
                iStart++;
            }
            iafd_data.JE_CallStackTB = new IAFDDiagnosis.IAFD_ENTITY[arrayCnt[4]];
            int iEnd3 = iEnd2 + arrayCnt[4];
            int i8 = 0;
            while (iStart < iEnd3) {
                iafd_data.JE_CallStackTB[i8] = tmpTB[iStart];
                i8++;
                iStart++;
            }
            iafd_data.NE_CallStackTB = new IAFDDiagnosis.IAFD_ENTITY[arrayCnt[5]];
            int iEnd4 = iEnd3 + arrayCnt[5];
            int i9 = 0;
            while (iStart < iEnd4) {
                iafd_data.NE_CallStackTB[i9] = tmpTB[iStart];
                i9++;
                iStart++;
            }
            iafd_data.NE_HeaderInfoTB = new IAFDDiagnosis.IAFD_ENTITY[arrayCnt[6]];
            int iEnd5 = iEnd4 + arrayCnt[6];
            int i10 = 0;
            while (iStart < iEnd5) {
                iafd_data.NE_HeaderInfoTB[i10] = tmpTB[iStart];
                i10++;
                iStart++;
            }
        }
        return iafd_data;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncDBType() {
        int curDBVer = -1;
        mCurDBIndex = -1;
        for (int i = 0; i < 3; i++) {
            IAFDDiagnosis.IAFD_DATA iafd_data = this.mIfadDBData[i];
            if (iafd_data != null && iafd_data.controlInfo.getDBVersion() >= curDBVer) {
                curDBVer = this.mIfadDBData[i].controlInfo.getDBVersion();
                mCurDBIndex = i;
            }
        }
        Slog.m113d(TAG, "syncDBType(): mCurDBIndex=" + mCurDBIndex + ", curDBVer=" + curDBVer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initIAFDDBHotfix() {
        IAFDDiagnosis.IAFD_DATA iafddataTmp;
        try {
            File filepath = new File("/data/user/0/com.sec.android.iaft/iafd/db/", "iafddbhotfix_db.bin.enc.dec");
            if (filepath.exists()) {
                IAFDDiagnosis.IAFD_DATA iafddataTmp2 = initDBByURIOrFile(false, null, filepath.toString());
                if (iafddataTmp2 != null) {
                    this.mIfadDBData[2] = iafddataTmp2;
                }
            } else {
                File filepath2 = new File("/data/user/0/com.sec.android.iaft/iafd/db/", "iafddbhotfix_db.bin.enc.dec");
                if (filepath2.exists() && (iafddataTmp = initDBByURIOrFile(false, null, filepath2.toString())) != null) {
                    this.mIfadDBData[2] = iafddataTmp;
                }
            }
        } catch (Exception e) {
        }
    }

    private void initARDBHotfix() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTBs() {
        if (this.mContext == null || isDBIniting) {
            return;
        }
        isDBIniting = true;
        try {
            if (this.mIfadDBData[0] == null) {
                IAFDHCDatabase.getInstance().init(this.mContext, this.mSalesCode, this.isCHNModel);
                this.mIfadDBData[0] = getInstance().getData();
            }
            if (this.mIfadDBData[1] == null) {
                this.mIAFDDBManagerHandler.obtainMessage(254).sendToTarget();
            }
            if (this.mIfadDBData[2] == null) {
                this.mIAFDDBManagerHandler.obtainMessage(250).sendToTarget();
            }
        } catch (Exception e) {
            Slog.m113d(TAG, "happened Exception : get TB fail!");
        }
        syncDBType();
        isDBIniting = false;
    }

    private class IAFDDBManagerHandler extends Handler {
        public IAFDDBManagerHandler() {
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 255) {
                IAFDDBManager.this.initTBs();
                return;
            }
            if (msg.what == 252 || msg.what == 253) {
                if (IAFDDBManager.isDBIniting) {
                    return;
                }
                IAFDDBManager.isDBIniting = true;
                try {
                    if (msg.what == 252) {
                        IAFDDBManager.this.initIAFDDBHotfix();
                    } else if (msg.what == 253) {
                        IAFDDiagnosis.IAFD_DATA iafddataTmp = IAFDDBManager.this.initDBByURIOrFile(true, IAFDDBManager.DB_IAFD_TB_URI_SM, null);
                        if (iafddataTmp != null) {
                            IAFDDBManager.this.mIfadDBData[1] = iafddataTmp;
                        } else {
                            IAFDDBManager.mSMDBInitReTryCnt++;
                            if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mSMDBInitReTryCnt < 25) {
                                Slog.m117i(IAFDDBManager.TAG, "in update,  mSMDBInitReTryCnt=" + IAFDDBManager.mSMDBInitReTryCnt);
                                IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(253), 5000L);
                            }
                        }
                    }
                    IAFDDBManager.this.syncDBType();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                IAFDDBManager.isDBIniting = false;
                return;
            }
            if (msg.what == 254) {
                if (!IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver) {
                    try {
                        if (IAFDDBManager.this.mIAFDDBObserver == null) {
                            IAFDDBManager iAFDDBManager = IAFDDBManager.this;
                            iAFDDBManager.mIAFDDBObserver = iAFDDBManager.new IAFDDBObserver(iAFDDBManager.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDDBManager.DB_IAFD_TB_URI_SM, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = true;
                        if (!IAFDDBManager.this.mRegisteredHotfixDBObserver) {
                            IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(250), 1000L);
                        }
                    } catch (Exception e2) {
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = false;
                        IAFDDBManager.mSMDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mSMDBInitReTryCnt < 25) {
                            Slog.m117i(IAFDDBManager.TAG, "mSMDBInitReTryCnt=" + IAFDDBManager.mSMDBInitReTryCnt);
                            IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(254), 5000L);
                            return;
                        }
                        return;
                    }
                }
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(253).sendToTarget();
                return;
            }
            if (msg.what == 250) {
                if (!IAFDDBManager.this.mRegisteredHotfixDBObserver) {
                    try {
                        if (IAFDDBManager.this.mIAFDDBObserver == null) {
                            IAFDDBManager iAFDDBManager2 = IAFDDBManager.this;
                            iAFDDBManager2.mIAFDDBObserver = iAFDDBManager2.new IAFDDBObserver(iAFDDBManager2.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDSocketFdServer.mUriHotfixIAFDDB_TB, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = true;
                    } catch (Exception e3) {
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = false;
                        IAFDDBManager.mHotfixDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mHotfixDBInitReTryCnt < 25) {
                            Slog.m117i(IAFDDBManager.TAG, "mHotfixDBInitReTryCnt=" + IAFDDBManager.mHotfixDBInitReTryCnt);
                            IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(250), 5000L);
                            return;
                        }
                        return;
                    }
                }
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(252).sendToTarget();
            }
        }
    }

    private class IAFDDBObserver extends ContentObserver {
        public IAFDDBObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            if (uri.equals(IAFDDBManager.DB_IAFD_TB_URI_SM)) {
                Slog.m117i(IAFDDBManager.TAG, "DB onChange: DB_IAFD_TB_URI_SM");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(253).sendToTarget();
            } else if (uri.equals(IAFDSocketFdServer.mUriHotfixIAFDDB_TB)) {
                Slog.m117i(IAFDDBManager.TAG, "DB onChange: HotfixIAFDDB_TB");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(252).sendToTarget();
            } else if (uri.equals(IAFDSocketFdServer.mUriHotfixAR_TB)) {
                Slog.m117i(IAFDDBManager.TAG, "DB onChange: HotfixARDB_TB");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(251).sendToTarget();
            }
        }
    }
}
