package com.android.server.notification.sec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class DisplayToast {
    public static DisplayToast sLogMsg = new DisplayToast();
    public Context mContext;
    public String mMessage;
    public String mPackageName;
    public int mUid;
    public ArrayList logList = new ArrayList();
    public StringBuffer outputContents = new StringBuffer();
    public SimpleDateFormat sdfNow = new SimpleDateFormat("yy-MM-dd_HH:mm:ss");

    public static void out(String str, String str2, int i, Context context) {
        sLogMsg.outFile(str, str2, i, context);
    }

    public final void outFile(String str, String str2, int i, Context context) {
        this.mPackageName = str;
        this.mMessage = str2;
        this.mUid = i;
        this.mContext = context;
        sendIntentForToastDumpLog(this.sdfNow.format(new Date(System.currentTimeMillis())));
    }

    public final void dump() {
        new Thread() { // from class: com.android.server.notification.sec.DisplayToast.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                File file = new File("/data/log/ToastLog.txt");
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8");
                    try {
                        synchronized (DisplayToast.this.logList) {
                            Iterator it = DisplayToast.this.logList.iterator();
                            while (it.hasNext()) {
                                outputStreamWriter.write((String) it.next());
                            }
                        }
                        file.setReadable(true, false);
                        outputStreamWriter.close();
                    } finally {
                    }
                } catch (Exception e) {
                    Log.w("ToastLog", e);
                }
            }
        }.start();
    }

    public class ToastDumpReceiver extends BroadcastReceiver {
        public final /* synthetic */ DisplayToast this$0;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "com.samsung.android.mobiledoctor.GETMOBILEDATAFILES" || intent.getAction() == "com.samsung.android.action.ACTION_REQUEST_INTERNET_LOG") {
                Log.d("ToastLog", "start dump");
                this.this$0.dump();
            }
        }
    }

    public final void sendIntentForToastDumpLog(String str) {
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.action.ACTION_TOAST_DUMP_LOG");
        intent.setPackage("android");
        intent.putExtra("sem_toast_date", str);
        intent.putExtra("sem_toast_caller_pkg", this.mPackageName);
        intent.putExtra("sem_toast_caller_uid", this.mUid);
        String str2 = this.mMessage;
        if (str2.length() > 100) {
            str2 = this.mMessage.substring(0, 99);
        }
        intent.putExtra("sem_toast_message", str2);
        try {
            this.mContext.sendBroadcast(intent);
        } catch (Exception e) {
            Log.w("ToastLog", "sendBroadcast fails!!", e);
        }
    }
}
