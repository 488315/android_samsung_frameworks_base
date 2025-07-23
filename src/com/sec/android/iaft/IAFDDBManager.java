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
    static int DBversion = 1;
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
    static boolean isDBIniting = false;
    static int mHotfixDBInitReTryCnt = 0;
    private static final long mReTryInterval = 5000;
    static int mSMDBInitReTryCnt;
    private boolean isCHNModel;
    private Context mContext;
    private IAFDDBManagerHandler mIAFDDBManagerHandler;
    private IAFDDBManagerThread mIAFDDBManagerThread;
    private IAFDDBObserver mIAFDDBObserver;
    private IAFDDiagnosis.IAFD_DATA[] mIfadDBData;
    private boolean mRegisteredHotfixDBObserver;
    private boolean mRegisteredSmartManagerIAFDObserver;
    private String mSalesCode;
    static final String[] columnsSMTB = {"tbID", "expID", "enable", "keyWord", "rule", "suggestion"};
    static final Uri DB_IAFD_TB_URI_SM = Uri.parse("content://com.samsung.android.sm/IAFD_TB");
    static int mCurDBIndex = -1;

    private void initARDBHotfix() {
    }

    public void updateHotfixDB_ARDB() {
    }

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

    public void init(Context context, String str, boolean z) {
        setContext(context);
        this.mSalesCode = str;
        this.isCHNModel = z;
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
        } catch (Exception unused) {
            Slog.d(TAG, "exception occurred in unregisterContentObserver()");
        }
    }

    private class IAFDDBManagerThread extends Thread {
        int mPriority;

        public IAFDDBManagerThread(String str) {
            super(str);
            this.mPriority = 0;
        }

        public IAFDDBManagerThread(String str, int i) {
            super(str);
            this.mPriority = i;
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
    /* JADX WARN: Removed duplicated region for block: B:74:0x0290  */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v5, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.sec.android.iaft.IAFDDiagnosis.IAFD_DATA initDBByURIOrFile(boolean r24, android.net.Uri r25, java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 684
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.iaft.IAFDDBManager.initDBByURIOrFile(boolean, android.net.Uri, java.lang.String):com.sec.android.iaft.IAFDDiagnosis$IAFD_DATA");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncDBType() {
        int i = -1;
        mCurDBIndex = -1;
        for (int i2 = 0; i2 < 3; i2++) {
            IAFDDiagnosis.IAFD_DATA iafd_data = this.mIfadDBData[i2];
            if (iafd_data != null && iafd_data.controlInfo.getDBVersion() >= i) {
                i = this.mIfadDBData[i2].controlInfo.getDBVersion();
                mCurDBIndex = i2;
            }
        }
        Slog.d(TAG, "syncDBType(): mCurDBIndex=" + mCurDBIndex + ", curDBVer=" + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initIAFDDBHotfix() {
        IAFDDiagnosis.IAFD_DATA initDBByURIOrFile;
        try {
            File file = new File("/data/user/0/com.sec.android.iaft/iafd/db/", "iafddbhotfix_db.bin.enc.dec");
            if (file.exists()) {
                IAFDDiagnosis.IAFD_DATA initDBByURIOrFile2 = initDBByURIOrFile(false, null, file.toString());
                if (initDBByURIOrFile2 != null) {
                    this.mIfadDBData[2] = initDBByURIOrFile2;
                    return;
                }
                return;
            }
            File file2 = new File("/data/user/0/com.sec.android.iaft/iafd/db/", "iafddbhotfix_db.bin.enc.dec");
            if (!file2.exists() || (initDBByURIOrFile = initDBByURIOrFile(false, null, file2.toString())) == null) {
                return;
            }
            this.mIfadDBData[2] = initDBByURIOrFile;
        } catch (Exception unused) {
        }
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
        } catch (Exception unused) {
            Slog.d(TAG, "happened Exception : get TB fail!");
        }
        syncDBType();
        isDBIniting = false;
    }

    private class IAFDDBManagerHandler extends Handler {
        public IAFDDBManagerHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 255) {
                IAFDDBManager.this.initTBs();
                return;
            }
            if (message.what == 252 || message.what == 253) {
                if (IAFDDBManager.isDBIniting) {
                    return;
                }
                IAFDDBManager.isDBIniting = true;
                try {
                    if (message.what == 252) {
                        IAFDDBManager.this.initIAFDDBHotfix();
                    } else if (message.what == 253) {
                        IAFDDiagnosis.IAFD_DATA initDBByURIOrFile = IAFDDBManager.this.initDBByURIOrFile(true, IAFDDBManager.DB_IAFD_TB_URI_SM, null);
                        if (initDBByURIOrFile != null) {
                            IAFDDBManager.this.mIfadDBData[1] = initDBByURIOrFile;
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
            if (message.what == 254) {
                if (!IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver) {
                    try {
                        if (IAFDDBManager.this.mIAFDDBObserver == null) {
                            IAFDDBManager iAFDDBManager = IAFDDBManager.this;
                            IAFDDBManager iAFDDBManager2 = IAFDDBManager.this;
                            iAFDDBManager.mIAFDDBObserver = iAFDDBManager2.new IAFDDBObserver(iAFDDBManager2.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDDBManager.DB_IAFD_TB_URI_SM, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = true;
                        if (!IAFDDBManager.this.mRegisteredHotfixDBObserver) {
                            IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(250), 1000L);
                        }
                    } catch (Exception unused) {
                        IAFDDBManager.this.mRegisteredSmartManagerIAFDObserver = false;
                        IAFDDBManager.mSMDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler == null || IAFDDBManager.mSMDBInitReTryCnt >= 100) {
                            return;
                        }
                        Slog.i(IAFDDBManager.TAG, "mSMDBInitReTryCnt=" + IAFDDBManager.mSMDBInitReTryCnt);
                        IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(254), 5000L);
                        return;
                    }
                }
                IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(253).sendToTarget();
                return;
            }
            if (message.what == 250) {
                if (!IAFDDBManager.this.mRegisteredHotfixDBObserver) {
                    try {
                        if (IAFDDBManager.this.mIAFDDBObserver == null) {
                            IAFDDBManager iAFDDBManager3 = IAFDDBManager.this;
                            IAFDDBManager iAFDDBManager4 = IAFDDBManager.this;
                            iAFDDBManager3.mIAFDDBObserver = iAFDDBManager4.new IAFDDBObserver(iAFDDBManager4.mIAFDDBManagerHandler);
                        }
                        IAFDDBManager.this.mContext.getContentResolver().registerContentObserver(IAFDSocketFdServer.mUriHotfixIAFDDB_TB, true, IAFDDBManager.this.mIAFDDBObserver);
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = true;
                    } catch (Exception unused2) {
                        IAFDDBManager.this.mRegisteredHotfixDBObserver = false;
                        IAFDDBManager.mHotfixDBInitReTryCnt++;
                        if (IAFDDBManager.this.mIAFDDBManagerHandler == null || IAFDDBManager.mHotfixDBInitReTryCnt >= 100) {
                            return;
                        }
                        Slog.i(IAFDDBManager.TAG, "mHotfixDBInitReTryCnt=" + IAFDDBManager.mHotfixDBInitReTryCnt);
                        IAFDDBManager.this.mIAFDDBManagerHandler.sendMessageDelayed(IAFDDBManager.this.mIAFDDBManagerHandler.obtainMessage(250), 5000L);
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
        public void onChange(boolean z, Uri uri) {
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
