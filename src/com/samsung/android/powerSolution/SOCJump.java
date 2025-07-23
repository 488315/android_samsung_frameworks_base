package com.samsung.android.powerSolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import java.io.File;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SOCJump {
    protected static SOC mCurrentSoc = null;
    protected static File mFileObject = null;
    protected static final String mSocFilePath = "/data/log/eSOC.txt";
    Context mContext;
    IntentFilter mSocFilter;
    BroadcastReceiver mSocJumpReciver = new MyBroadcastReceiver();
    protected static SOC mPreviousSoc = new SOC(-1);
    private static SOCJump mSocjump = null;

    private static class SOC {
        int battery;

        SOC(int i) {
            this.battery = i;
        }

        public boolean socJumpcheck(SOC soc) {
            return soc != null && Math.abs(soc.battery - this.battery) > 1;
        }

        public void setBatteryLevel(int i) {
            this.battery = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dateFormat(long j) {
        return (String) DateFormat.format("yyyy-MM-dd kk:mm:ss", j);
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {
        private static final String TAG = "powerSolution_SOCJump_";

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                onEventRun(intent.getIntExtra("level", -1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:39:0x017a  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x017f  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0184  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x018b  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0190  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0195  */
        /* JADX WARN: Type inference failed for: r3v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void onEventRun(int r12) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 414
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.powerSolution.SOCJump.MyBroadcastReceiver.onEventRun(int):void");
        }
    }

    public static SOCJump getInstance(Context context) {
        if (mSocjump == null) {
            mSocjump = new SOCJump(context);
        }
        return mSocjump;
    }

    public SOCJump(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mSocFilter = intentFilter;
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.mContext.registerReceiver(this.mSocJumpReciver, this.mSocFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006a  */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dump(java.io.PrintWriter r3, java.lang.String[] r4) throws java.io.IOException {
        /*
            r2 = this;
            java.lang.String r2 = "[SOCJump]"
            r3.println(r2)
            r2 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L45
            java.lang.String r0 = "/data/log/eSOC.txt"
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L45
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            java.nio.charset.Charset r1 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r0.<init>(r4, r1)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L36
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L36
            java.lang.String r2 = r1.readLine()     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L5d
        L1d:
            if (r2 == 0) goto L27
            r3.println(r2)     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L5d
            java.lang.String r2 = r1.readLine()     // Catch: java.io.IOException -> L31 java.lang.Throwable -> L5d
            goto L1d
        L27:
            r1.close()
            r4.close()
            r0.close()
            return
        L31:
            r2 = move-exception
            goto L4a
        L33:
            r3 = move-exception
            r1 = r2
            goto L43
        L36:
            r3 = move-exception
            r1 = r2
            goto L49
        L39:
            r3 = move-exception
            r0 = r2
            goto L42
        L3c:
            r3 = move-exception
            r0 = r2
            goto L48
        L3f:
            r3 = move-exception
            r4 = r2
            r0 = r4
        L42:
            r1 = r0
        L43:
            r2 = r3
            goto L5e
        L45:
            r3 = move-exception
            r4 = r2
            r0 = r4
        L48:
            r1 = r0
        L49:
            r2 = r3
        L4a:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L5d
            if (r1 == 0) goto L52
            r1.close()
        L52:
            if (r4 == 0) goto L57
            r4.close()
        L57:
            if (r0 == 0) goto L5c
            r0.close()
        L5c:
            return
        L5d:
            r2 = move-exception
        L5e:
            if (r1 == 0) goto L63
            r1.close()
        L63:
            if (r4 == 0) goto L68
            r4.close()
        L68:
            if (r0 == 0) goto L6d
            r0.close()
        L6d:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.powerSolution.SOCJump.dump(java.io.PrintWriter, java.lang.String[]):void");
    }
}
