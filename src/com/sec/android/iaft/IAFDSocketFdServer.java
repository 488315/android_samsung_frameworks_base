package com.sec.android.iaft;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes6.dex */
public class IAFDSocketFdServer {
    private static final int CIPER_POS = 2;
    static final String DECRYPT_ARDB_NAME = "ardbhotfix_db.bin.enc.dec";
    static final String DECRYPT_HOTFIX_SUFFIX = ".dec";
    static final String DECRYPT_IAFDADDB_NAME = "iafdaddbhotfix_db.bin.enc.dec";
    static final String DECRYPT_IAFDDB_NAME = "iafddbhotfix_db.bin.enc.dec";
    static final String DECRYPT_IAFDHIGHBDB_NAME = "iafdiaftdbhotfix_db.bin.enc.dec";
    static final String DEXPATH_DEENCRYPT = "/iafd/dex/";
    static final String ENCRYPT_HOTFIX_DEX_SUFFIX = "_dex";
    static final String ENCRYPT_HOTFIX_SUFFIX = ".bin.enc";
    private static final String HOTFIX_END = "resourcesapybhotfixczfileend";
    private static final String HOTFIX_START = "resourcesapybhotfixczfilestart";
    static final String IAFDDBPATH_DEENCRYPT = "/iafd/db/";
    static final String IAFDPKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwaCLv6RvwU8gyFSbynkiPI1Yjb4O3PjCoTQOJadMly1MfePjpFFddlbHnEhyXZqK5znGPNCa/+grdCBV6bbdVf1DTjzcrleKeD6LwC5cioMMjtu91MqrZwDSyAvi6cpdiskEJ/ht+lDJGTdE5bpxJl5tQyy+HrXQk2wJFp3fTWwIDAQAB";
    static final String IAFD_ABSOLUTEPATH = "/data/user/0/com.sec.android.iaft";
    private static final int NAME_POS = 1;
    private static final String TAG = "IAFDGetHotfixDataService";
    private final String IAFDPATH = "/iafd/";
    private Context mContext;
    static final Uri mUriHotfixIAFDDB_TB = Uri.parse("content://com.sec.android.iaft/IAFDDB_TB");
    static final Uri mUriHotfixAR_TB = Uri.parse("content://com.sec.android.iaft/IAFDAD_TB");

    public IAFDSocketFdServer(Context context) {
        this.mContext = context;
    }

    public void getDataFromClient(final String str) {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        executorServiceNewSingleThreadExecutor.submit(new Runnable() { // from class: com.sec.android.iaft.IAFDSocketFdServer.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IAFDSocketFdServer.this.saveFile(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executorServiceNewSingleThreadExecutor.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009c A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:4:0x0014, B:6:0x001d, B:8:0x0026, B:10:0x003a, B:11:0x0051, B:26:0x009c, B:28:0x00af, B:30:0x00b6, B:32:0x00bd, B:14:0x0075, B:17:0x0080, B:20:0x008b), top: B:37:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00af A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:4:0x0014, B:6:0x001d, B:8:0x0026, B:10:0x003a, B:11:0x0051, B:26:0x009c, B:28:0x00af, B:30:0x00b6, B:32:0x00bd, B:14:0x0075, B:17:0x0080, B:20:0x008b), top: B:37:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b6 A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:4:0x0014, B:6:0x001d, B:8:0x0026, B:10:0x003a, B:11:0x0051, B:26:0x009c, B:28:0x00af, B:30:0x00b6, B:32:0x00bd, B:14:0x0075, B:17:0x0080, B:20:0x008b), top: B:37:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bd A[Catch: Exception -> 0x00c3, TRY_LEAVE, TryCatch #0 {Exception -> 0x00c3, blocks: (B:4:0x0014, B:6:0x001d, B:8:0x0026, B:10:0x003a, B:11:0x0051, B:26:0x009c, B:28:0x00af, B:30:0x00b6, B:32:0x00bd, B:14:0x0075, B:17:0x0080, B:20:0x008b), top: B:37:0x0014 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void saveFile(String str) {
        boolean z;
        boolean z2;
        boolean z3;
        Log.i(TAG, "saveFileing...");
        IAFDFileHexUtils iAFDFileHexUtils = new IAFDFileHexUtils();
        boolean z4 = false;
        if (str != null) {
            try {
                if (str.contains(HOTFIX_START) && str.contains(HOTFIX_END)) {
                    String[] strArrSplit = str.split(",");
                    z = true;
                    String str2 = strArrSplit[1];
                    String str3 = strArrSplit[2];
                    if (str2.contains(ENCRYPT_HOTFIX_DEX_SUFFIX)) {
                        iAFDFileHexUtils.makeHexStringToFile("/data/user/0/com.sec.android.iaft/iafd/dex/", str3, str2 + ENCRYPT_HOTFIX_SUFFIX);
                    } else {
                        IAFDRSAUtils.decryptBytesToFile(iAFDFileHexUtils.makeHexStringToBytes(str3), IAFDPKEY, "/data/user/0/com.sec.android.iaft/iafd/db/" + str2 + ".bin.enc.dec");
                        if (str2.contains("iafddbhotfix_db")) {
                            z2 = false;
                            z3 = false;
                            z4 = true;
                        } else {
                            if (str2.contains("ardbhotfix_db")) {
                                z2 = false;
                                z3 = false;
                            } else if (str2.contains("addbhotfix_db")) {
                                z3 = false;
                                z2 = true;
                            } else if (str2.contains("iaftdbhotfix_db")) {
                                z2 = false;
                                z3 = true;
                                z = false;
                            }
                            if (z4) {
                            }
                            if (z) {
                            }
                            if (z2) {
                            }
                            if (z3) {
                            }
                        }
                        z = z3;
                        if (z4) {
                        }
                        if (z) {
                        }
                        if (z2) {
                        }
                        if (z3) {
                        }
                    }
                    z2 = false;
                    z3 = false;
                    z = z3;
                    if (z4) {
                    }
                    if (z) {
                    }
                    if (z2) {
                    }
                    if (z3) {
                    }
                } else {
                    z2 = false;
                    z3 = false;
                    z = z3;
                    if (z4) {
                        Log.i(TAG, "IAFDDBHOTFIX_UPDATE");
                        this.mContext.getContentResolver().update(mUriHotfixIAFDDB_TB, null, null, null);
                    }
                    if (z) {
                        Log.i(TAG, "ARDBHOTFIX_UPDATE");
                    }
                    if (z2) {
                        Log.i(TAG, "ADDBHOTFIX_UPDATE");
                    }
                    if (z3) {
                        Log.i(TAG, "IAFTDBHOTFIX_UPDATE");
                    }
                }
            } catch (Exception unused) {
                Log.i(TAG, "ToFile fail");
            }
        }
        Log.i(TAG, "saveFile completed");
    }
}
