package com.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Base64;

import java.io.PrintWriter;
import java.util.function.BiConsumer;

public final class ContentDispatcher {
    public Context mContext;
    public BiConsumer mGrantUriPermission;
    public Handler mHandler;
    public ArrayMap mPdfInfos;

    public final class PdfInfo {
        public int mCallingUid;
        public Intent mIntent;
        public String mPackageName;
    }

    public final void clearPdfTask(int i) {
        synchronized (this.mPdfInfos) {
            this.mPdfInfos.remove(Integer.valueOf(i));
        }
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mPdfInfos) {
            try {
                printWriter.println("ACTIVITY MANAGER CONTENT_DISPATCHER");
                for (Integer num : this.mPdfInfos.keySet()) {
                    int intValue = num.intValue();
                    PdfInfo pdfInfo = (PdfInfo) this.mPdfInfos.get(num);
                    if (pdfInfo != null) {
                        printWriter.println("  TaskId: " + intValue);
                        printWriter.println("    viewer: " + pdfInfo.mPackageName);
                        printWriter.println(
                                "    content: "
                                        + Base64.encodeToString(
                                                pdfInfo.mIntent.getData().toString().getBytes(),
                                                2));
                    }
                }
                printWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
