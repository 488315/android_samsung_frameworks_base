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

    public void getDataFromClient(final String hotFixData) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() { // from class: com.sec.android.iaft.IAFDSocketFdServer.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IAFDSocketFdServer.this.saveFile(hotFixData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveFile(String inData) {
        Log.i(TAG, "saveFileing...");
        IAFDFileHexUtils f2h = new IAFDFileHexUtils();
        boolean isIAFDDBHOTFIX_UPDATE = false;
        boolean isARDBHOTFIX_UPDATE = false;
        boolean isADDBHOTFIX_UPDATE = false;
        boolean isIAFTDBHOTFIX_UPDATE = false;
        if (inData != null) {
            try {
                if (inData.contains(HOTFIX_START) && inData.contains(HOTFIX_END)) {
                    String[] item = inData.split(",");
                    String fileName = item[1];
                    String ciphertext = item[2];
                    if (fileName.contains(ENCRYPT_HOTFIX_DEX_SUFFIX)) {
                        f2h.makeHexStringToFile(IAFD_ABSOLUTEPATH + DEXPATH_DEENCRYPT, ciphertext, fileName + ENCRYPT_HOTFIX_SUFFIX);
                    } else {
                        byte[] bs = f2h.makeHexStringToBytes(ciphertext);
                        IAFDRSAUtils.decryptBytesToFile(bs, IAFDPKEY, IAFD_ABSOLUTEPATH + IAFDDBPATH_DEENCRYPT + fileName + ENCRYPT_HOTFIX_SUFFIX + DECRYPT_HOTFIX_SUFFIX);
                        if (fileName.contains("iafddbhotfix_db")) {
                            isIAFDDBHOTFIX_UPDATE = true;
                        } else if (fileName.contains("ardbhotfix_db")) {
                            isARDBHOTFIX_UPDATE = true;
                        } else if (fileName.contains("addbhotfix_db")) {
                            isADDBHOTFIX_UPDATE = true;
                        } else if (fileName.contains("iaftdbhotfix_db")) {
                            isIAFTDBHOTFIX_UPDATE = true;
                        }
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "ToFile fail");
                Log.i(TAG, "saveFile completed");
            }
        }
        if (isIAFDDBHOTFIX_UPDATE) {
            Log.i(TAG, "IAFDDBHOTFIX_UPDATE");
            try {
                this.mContext.getContentResolver().update(mUriHotfixIAFDDB_TB, null, null, null);
            } catch (Exception e2) {
                Log.i(TAG, "ToFile fail");
                Log.i(TAG, "saveFile completed");
            }
        }
        if (isARDBHOTFIX_UPDATE) {
            Log.i(TAG, "ARDBHOTFIX_UPDATE");
        }
        if (isADDBHOTFIX_UPDATE) {
            Log.i(TAG, "ADDBHOTFIX_UPDATE");
        }
        if (isIAFTDBHOTFIX_UPDATE) {
            Log.i(TAG, "IAFTDBHOTFIX_UPDATE");
        }
        Log.i(TAG, "saveFile completed");
    }
}
