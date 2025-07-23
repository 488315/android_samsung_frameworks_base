package com.sec.android.iaft;

import android.content.Context;
import android.net.Uri;
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
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        newSingleThreadExecutor.submit(new Runnable() { // from class: com.sec.android.iaft.IAFDSocketFdServer.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    IAFDSocketFdServer.this.saveFile(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        newSingleThreadExecutor.shutdown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x00b6 A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:19:0x0014, B:21:0x001d, B:23:0x0026, B:25:0x003a, B:26:0x0051, B:6:0x009c, B:8:0x00af, B:10:0x00b6, B:12:0x00bd, B:29:0x0075, B:32:0x0080, B:35:0x008b), top: B:18:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00bd A[Catch: Exception -> 0x00c3, TRY_LEAVE, TryCatch #0 {Exception -> 0x00c3, blocks: (B:19:0x0014, B:21:0x001d, B:23:0x0026, B:25:0x003a, B:26:0x0051, B:6:0x009c, B:8:0x00af, B:10:0x00b6, B:12:0x00bd, B:29:0x0075, B:32:0x0080, B:35:0x008b), top: B:18:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x009c A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:19:0x0014, B:21:0x001d, B:23:0x0026, B:25:0x003a, B:26:0x0051, B:6:0x009c, B:8:0x00af, B:10:0x00b6, B:12:0x00bd, B:29:0x0075, B:32:0x0080, B:35:0x008b), top: B:18:0x0014 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00af A[Catch: Exception -> 0x00c3, TryCatch #0 {Exception -> 0x00c3, blocks: (B:19:0x0014, B:21:0x001d, B:23:0x0026, B:25:0x003a, B:26:0x0051, B:6:0x009c, B:8:0x00af, B:10:0x00b6, B:12:0x00bd, B:29:0x0075, B:32:0x0080, B:35:0x008b), top: B:18:0x0014 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void saveFile(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = ".bin.enc.dec"
            java.lang.String r1 = "/data/user/0/com.sec.android.iaft/iafd/db/"
            java.lang.String r2 = "saveFileing..."
            java.lang.String r3 = "IAFDGetHotfixDataService"
            android.util.Log.i(r3, r2)
            com.sec.android.iaft.IAFDFileHexUtils r2 = new com.sec.android.iaft.IAFDFileHexUtils
            r2.<init>()
            r4 = 0
            if (r9 == 0) goto L97
            java.lang.String r5 = "resourcesapybhotfixczfilestart"
            boolean r5 = r9.contains(r5)     // Catch: java.lang.Exception -> Lc3
            if (r5 == 0) goto L97
            java.lang.String r5 = "resourcesapybhotfixczfileend"
            boolean r5 = r9.contains(r5)     // Catch: java.lang.Exception -> Lc3
            if (r5 == 0) goto L97
            java.lang.String r5 = ","
            java.lang.String[] r9 = r9.split(r5)     // Catch: java.lang.Exception -> Lc3
            r5 = 1
            r6 = r9[r5]     // Catch: java.lang.Exception -> Lc3
            r7 = 2
            r9 = r9[r7]     // Catch: java.lang.Exception -> Lc3
            java.lang.String r7 = "_dex"
            boolean r7 = r6.contains(r7)     // Catch: java.lang.Exception -> Lc3
            if (r7 == 0) goto L51
            java.lang.String r0 = "/data/user/0/com.sec.android.iaft/iafd/dex/"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc3
            r1.<init>()     // Catch: java.lang.Exception -> Lc3
            r1.append(r6)     // Catch: java.lang.Exception -> Lc3
            java.lang.String r5 = ".bin.enc"
            r1.append(r5)     // Catch: java.lang.Exception -> Lc3
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> Lc3
            r2.makeHexStringToFile(r0, r9, r1)     // Catch: java.lang.Exception -> Lc3
            goto L97
        L51:
            byte[] r9 = r2.makeHexStringToBytes(r9)     // Catch: java.lang.Exception -> Lc3
            java.lang.String r2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwaCLv6RvwU8gyFSbynkiPI1Yjb4O3PjCoTQOJadMly1MfePjpFFddlbHnEhyXZqK5znGPNCa/+grdCBV6bbdVf1DTjzcrleKeD6LwC5cioMMjtu91MqrZwDSyAvi6cpdiskEJ/ht+lDJGTdE5bpxJl5tQyy+HrXQk2wJFp3fTWwIDAQAB"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lc3
            r7.<init>(r1)     // Catch: java.lang.Exception -> Lc3
            r7.append(r6)     // Catch: java.lang.Exception -> Lc3
            r7.append(r0)     // Catch: java.lang.Exception -> Lc3
            java.lang.String r0 = r7.toString()     // Catch: java.lang.Exception -> Lc3
            com.sec.android.iaft.IAFDRSAUtils.decryptBytesToFile(r9, r2, r0)     // Catch: java.lang.Exception -> Lc3
            java.lang.String r9 = "iafddbhotfix_db"
            boolean r9 = r6.contains(r9)     // Catch: java.lang.Exception -> Lc3
            if (r9 == 0) goto L75
            r9 = r4
            r0 = r9
            r4 = r5
            goto L99
        L75:
            java.lang.String r9 = "ardbhotfix_db"
            boolean r9 = r6.contains(r9)     // Catch: java.lang.Exception -> Lc3
            if (r9 == 0) goto L80
            r9 = r4
            r0 = r9
            goto L9a
        L80:
            java.lang.String r9 = "addbhotfix_db"
            boolean r9 = r6.contains(r9)     // Catch: java.lang.Exception -> Lc3
            if (r9 == 0) goto L8b
            r0 = r4
            r9 = r5
            goto L99
        L8b:
            java.lang.String r9 = "iaftdbhotfix_db"
            boolean r9 = r6.contains(r9)     // Catch: java.lang.Exception -> Lc3
            if (r9 == 0) goto L97
            r9 = r4
            r0 = r5
            r5 = r9
            goto L9a
        L97:
            r9 = r4
            r0 = r9
        L99:
            r5 = r0
        L9a:
            if (r4 == 0) goto Lad
            java.lang.String r1 = "IAFDDBHOTFIX_UPDATE"
            android.util.Log.i(r3, r1)     // Catch: java.lang.Exception -> Lc3
            android.content.Context r8 = r8.mContext     // Catch: java.lang.Exception -> Lc3
            android.content.ContentResolver r8 = r8.getContentResolver()     // Catch: java.lang.Exception -> Lc3
            android.net.Uri r1 = com.sec.android.iaft.IAFDSocketFdServer.mUriHotfixIAFDDB_TB     // Catch: java.lang.Exception -> Lc3
            r2 = 0
            r8.update(r1, r2, r2, r2)     // Catch: java.lang.Exception -> Lc3
        Lad:
            if (r5 == 0) goto Lb4
            java.lang.String r8 = "ARDBHOTFIX_UPDATE"
            android.util.Log.i(r3, r8)     // Catch: java.lang.Exception -> Lc3
        Lb4:
            if (r9 == 0) goto Lbb
            java.lang.String r8 = "ADDBHOTFIX_UPDATE"
            android.util.Log.i(r3, r8)     // Catch: java.lang.Exception -> Lc3
        Lbb:
            if (r0 == 0) goto Lc8
            java.lang.String r8 = "IAFTDBHOTFIX_UPDATE"
            android.util.Log.i(r3, r8)     // Catch: java.lang.Exception -> Lc3
            goto Lc8
        Lc3:
            java.lang.String r8 = "ToFile fail"
            android.util.Log.i(r3, r8)
        Lc8:
            java.lang.String r8 = "saveFile completed"
            android.util.Log.i(r3, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.android.iaft.IAFDSocketFdServer.saveFile(java.lang.String):void");
    }
}
