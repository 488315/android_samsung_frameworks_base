package com.android.server.enterprise.auditlog;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LogWritter {
    public CircularBuffer mCircularBuffer;
    public LooperThread mLooperThread;
    public Admin mObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LooperThread extends Thread {
        public SaveLogHandler mHandler;

        public LooperThread() {}

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            this.mHandler = LogWritter.this.new SaveLogHandler();
            Looper.loop();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SaveLogHandler extends Handler {
        public Bundle data;

        public SaveLogHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList arrayList;
            ArrayList arrayList2;
            Bundle data = message.getData();
            this.data = data;
            if (data.getString("swap") == null) {
                if (this.data.getString("log") != null) {
                    LogWritter.this.mCircularBuffer.write(this.data.getString("log"));
                    return;
                } else {
                    LogWritter.this.mCircularBuffer.closeFile();
                    return;
                }
            }
            PartialFileNode partialFileNode = null;
            LogWritter.this.mCircularBuffer.write(null);
            Admin admin = LogWritter.this.mObserver;
            CircularBuffer circularBuffer = admin.mLogWritter.mCircularBuffer;
            synchronized (circularBuffer.mDumpList) {
                arrayList = new ArrayList(circularBuffer.mDumpList);
            }
            if (((PartialFileNode) arrayList.get(0)).mFile != null) {
                long j = admin.mBegin;
                long j2 = admin.mEnd;
                ParcelFileDescriptor parcelFileDescriptor = admin.mAdminOutputFile;
                Dumper dumper = new Dumper();
                dumper.mPackageName = null;
                dumper.mHeader = null;
                dumper.mTemporaryDirectory = null;
                dumper.mPfd = parcelFileDescriptor;
                dumper.mDumpFilesList = arrayList;
                dumper.mObserver = admin;
                dumper.mFilter = null;
                dumper.mBegin = j;
                dumper.mEnd = j2;
                dumper.mDumpResult = true;
                dumper.mIsFullDump = false;
                dumper.mTemporaryPath =
                        ((PartialFileNode) arrayList.get(0)).mFile.getParent() + "/temp/";
                Filter filter = admin.mDumpFilter;
                if (filter != null) {
                    dumper.mFilter = filter;
                }
                dumper.mDeviceInfo = admin.mDeviceInfo;
                dumper.mPackageName = admin.mPackageName;
                dumper.start();
                LogWritter logWritter = admin.mLogWritter;
                CircularBuffer circularBuffer2 = logWritter.mCircularBuffer;
                synchronized (circularBuffer2.mDumpList) {
                    arrayList2 = new ArrayList(circularBuffer2.mDumpList);
                }
                CircularBuffer circularBuffer3 = logWritter.mCircularBuffer;
                circularBuffer3.getClass();
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    partialFileNode = (PartialFileNode) it.next();
                }
                if (partialFileNode != null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(
                            "auditLogLastTimestamp",
                            Long.valueOf(Long.parseLong(partialFileNode.mFile.getName())));
                    circularBuffer3.mEdmStorageProvider.putValues(
                            circularBuffer3.mUid, 0, "AUDITLOG", contentValues);
                }
            }
        }
    }

    public final void setIsDumping(boolean z, boolean z2) {
        CircularBuffer circularBuffer = this.mCircularBuffer;
        synchronized (circularBuffer) {
            circularBuffer.mIsDumping = z;
            if (!z && z2 && circularBuffer.mTypeOfDump) {
                circularBuffer.mTypeOfDump = false;
                circularBuffer.markDeprecatedFiles();
            }
        }
    }
}
