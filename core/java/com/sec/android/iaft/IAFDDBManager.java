package com.sec.android.iaft;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Slog;
import com.sec.android.iaft.IAFDDiagnosis;
import java.io.File;

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
    private static final int MAX_DBINIT_RETRY_CNT = 100;
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
        if (mCurDBIndex < 0) {
            return null;
        }
        return this.mIfadDBData[mCurDBIndex];
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
            this.mIAFDDBManagerThread = new IAFDDBManagerThread("IAFDDBManagerThread", 0);
            this.mIAFDDBManagerThread.start();
        } else {
            this.mIAFDDBManagerHandler.obtainMessage(255).sendToTarget();
        }
    }

    public void deInit() {
        try {
            if (this.mRegisteredSmartManagerIAFDObserver) {
                this.mContext.getContentResolver().unregisterContentObserver(this.mIAFDDBObserver);
                this.mRegisteredSmartManagerIAFDObserver = false;
                this.mRegisteredHotfixDBObserver = false;
            }
        } catch (Exception e) {
            Slog.d(TAG, "exception occurred in unregisterContentObserver()");
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.sec.android.iaft.IAFDDiagnosis.IAFD_DATA initDBByURIOrFile(boolean r27, android.net.Uri r28, java.lang.String r29) {
        /*
            Method dump skipped, instructions count: 716
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.iaft.IAFDDBManager.initDBByURIOrFile(boolean, android.net.Uri, java.lang.String):com.sec.android.iaft.IAFDDiagnosis$IAFD_DATA");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncDBType() {
        int curDBVer = -1;
        mCurDBIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (this.mIfadDBData[i] != null && this.mIfadDBData[i].controlInfo.getDBVersion() >= curDBVer) {
                curDBVer = this.mIfadDBData[i].controlInfo.getDBVersion();
                mCurDBIndex = i;
            }
        }
        Slog.d(TAG, "syncDBType(): mCurDBIndex=" + mCurDBIndex + ", curDBVer=" + curDBVer);
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
            Slog.d(TAG, "happened Exception : get TB fail!");
        }
        syncDBType();
        isDBIniting = false;
    }

    private class IAFDDBManagerHandler extends Handler {
        public IAFDDBManagerHandler() {
        }

        @Override // android.os.Handler
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
                            if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mSMDBInitReTryCnt < 100) {
                                Slog.i(IAFDDBManager.TAG, "in update,  mSMDBInitReTryCnt=" + IAFDDBManager.mSMDBInitReTryCnt);
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
                            IAFDDBManager.this.mIAFDDBObserver = IAFDDBManager.this.new IAFDDBObserver(IAFDDBManager.this.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDDBManager.DB_IAFD_TB_URI_SM, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = true;
                        if (!IAFDDBManager.this.mRegisteredHotfixDBObserver) {
                            IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(250), 1000L);
                        }
                    } catch (Exception e2) {
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = false;
                        IAFDDBManager.mSMDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mSMDBInitReTryCnt < 100) {
                            Slog.i(IAFDDBManager.TAG, "mSMDBInitReTryCnt=" + IAFDDBManager.mSMDBInitReTryCnt);
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
                            IAFDDBManager.this.mIAFDDBObserver = IAFDDBManager.this.new IAFDDBObserver(IAFDDBManager.this.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDSocketFdServer.mUriHotfixIAFDDB_TB, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = true;
                    } catch (Exception e3) {
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = false;
                        IAFDDBManager.mHotfixDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler != null && IAFDDBManager.mHotfixDBInitReTryCnt < 100) {
                            Slog.i(IAFDDBManager.TAG, "mHotfixDBInitReTryCnt=" + IAFDDBManager.mHotfixDBInitReTryCnt);
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
                Slog.i(IAFDDBManager.TAG, "DB onChange: DB_IAFD_TB_URI_SM");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(253).sendToTarget();
            } else if (uri.equals(IAFDSocketFdServer.mUriHotfixIAFDDB_TB)) {
                Slog.i(IAFDDBManager.TAG, "DB onChange: HotfixIAFDDB_TB");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(252).sendToTarget();
            } else if (uri.equals(IAFDSocketFdServer.mUriHotfixAR_TB)) {
                Slog.i(IAFDDBManager.TAG, "DB onChange: HotfixARDB_TB");
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(251).sendToTarget();
            }
        }
    }
}
