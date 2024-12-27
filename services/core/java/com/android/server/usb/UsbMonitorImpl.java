package com.android.server.usb;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.sysfwutil.Slog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbMonitorImpl {
    public final UsbStatsHandler mHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsbStatsHandler extends Handler {
        public String mCurrentUsbStats;
        public final File mLogFile;
        public final File mSysFs;

        public UsbStatsHandler(Looper looper) {
            super(looper);
            this.mSysFs = new File("/sys/class/usb_notify/usb_control/usb_hw_param");
            this.mLogFile = new File("/efs/usb_hw_param/usb_hw_param.log");
            this.mCurrentUsbStats = "none";
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 0) {
                if (i == 1) {
                    updateUsbStats();
                    return;
                }
                Slog.e("UsbStatsMonitor", "Unexpected message " + message.what);
                return;
            }
            Slog.d("UsbStatsMonitor", "finishBoot");
            if (!this.mSysFs.exists()) {
                Slog.e("UsbStatsMonitor", "No sysfs node");
                return;
            }
            boolean exists = this.mLogFile.exists();
            UsbMonitorImpl usbMonitorImpl = UsbMonitorImpl.this;
            if (exists) {
                UsbMonitorImpl.m1024$$Nest$mstringToFile(usbMonitorImpl, this.mSysFs, UsbMonitorImpl.m1023$$Nest$mreadFileAsStringOrNull(usbMonitorImpl, this.mLogFile));
            } else {
                UsbMonitorImpl.m1024$$Nest$mstringToFile(usbMonitorImpl, this.mSysFs, "0");
                try {
                    if (this.mLogFile.getParentFile() != null) {
                        this.mLogFile.getParentFile().mkdirs();
                    }
                    this.mLogFile.createNewFile();
                } catch (IOException e) {
                    Slog.e("UsbStatsMonitor", "Couldn't create log file", e);
                    return;
                }
            }
            updateUsbStats();
        }

        public final void updateUsbStats() {
            File file = this.mSysFs;
            UsbMonitorImpl usbMonitorImpl = UsbMonitorImpl.this;
            String m1023$$Nest$mreadFileAsStringOrNull = UsbMonitorImpl.m1023$$Nest$mreadFileAsStringOrNull(usbMonitorImpl, file);
            if (m1023$$Nest$mreadFileAsStringOrNull != null && !m1023$$Nest$mreadFileAsStringOrNull.equals(this.mCurrentUsbStats)) {
                this.mCurrentUsbStats = m1023$$Nest$mreadFileAsStringOrNull;
                UsbMonitorImpl.m1024$$Nest$mstringToFile(usbMonitorImpl, this.mLogFile, m1023$$Nest$mreadFileAsStringOrNull);
            }
            Slog.d("UsbStatsMonitor", this.mCurrentUsbStats);
            removeMessages(1);
            sendMessageDelayed(Message.obtain(this, 1), 60000L);
        }
    }

    /* renamed from: -$$Nest$mreadFileAsStringOrNull, reason: not valid java name */
    public static String m1023$$Nest$mreadFileAsStringOrNull(UsbMonitorImpl usbMonitorImpl, File file) {
        usbMonitorImpl.getClass();
        try {
            return IoUtils.readFileAsString(file.getAbsolutePath()).trim();
        } catch (IOException e) {
            Slog.e("UsbStatsMonitor", "Couldn't read " + file.getAbsolutePath(), e);
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:19:0x001f
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* renamed from: -$$Nest$mstringToFile, reason: not valid java name */
    public static void m1024$$Nest$mstringToFile(com.android.server.usb.UsbMonitorImpl r4, java.io.File r5, java.lang.String r6) {
        /*
            r4.getClass()
            java.lang.String r4 = "Couldn't close stream"
            java.lang.String r0 = "UsbStatsMonitor"
            java.lang.String r1 = "Couldn't write "
            boolean r2 = r5.exists()
            if (r2 == 0) goto L4f
            if (r6 != 0) goto L12
            goto L4f
        L12:
            r2 = 0
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r3.<init>(r5)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2b
            r3.write(r6)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L26
            r3.close()     // Catch: java.io.IOException -> L1f
            goto L4f
        L1f:
            android.util.sysfwutil.Slog.e(r0, r4)
            goto L4f
        L23:
            r5 = move-exception
            r2 = r3
            goto L45
        L26:
            r6 = move-exception
            r2 = r3
            goto L2c
        L29:
            r5 = move-exception
            goto L45
        L2b:
            r6 = move-exception
        L2c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L29
            r3.<init>(r1)     // Catch: java.lang.Throwable -> L29
            java.lang.String r5 = r5.getAbsolutePath()     // Catch: java.lang.Throwable -> L29
            r3.append(r5)     // Catch: java.lang.Throwable -> L29
            java.lang.String r5 = r3.toString()     // Catch: java.lang.Throwable -> L29
            android.util.sysfwutil.Slog.e(r0, r5, r6)     // Catch: java.lang.Throwable -> L29
            if (r2 == 0) goto L4f
            r2.close()     // Catch: java.io.IOException -> L1f
            goto L4f
        L45:
            if (r2 == 0) goto L4e
            r2.close()     // Catch: java.io.IOException -> L4b
            goto L4e
        L4b:
            android.util.sysfwutil.Slog.e(r0, r4)
        L4e:
            throw r5
        L4f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbMonitorImpl.m1024$$Nest$mstringToFile(com.android.server.usb.UsbMonitorImpl, java.io.File, java.lang.String):void");
    }

    public UsbMonitorImpl(Looper looper) {
        this.mHandler = new UsbStatsHandler(looper);
    }

    public final void dump(PrintWriter printWriter) {
        UsbStatsHandler usbStatsHandler = this.mHandler;
        if (usbStatsHandler != null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Current USB Stats: "), usbStatsHandler.mCurrentUsbStats, printWriter);
        }
    }
}
