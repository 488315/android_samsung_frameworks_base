package com.android.server.usb;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.sysfwutil.Slog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class UsbMonitorImpl {
    public final UsbStatsHandler mHandler;

    public UsbMonitorImpl(Looper looper) {
        this.mHandler = new UsbStatsHandler(looper);
    }

    public void systemReady() {
        this.mHandler.sendEmptyMessage(0);
    }

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

        public void sendMessageDelayed(int i, long j) {
            removeMessages(i);
            sendMessageDelayed(Message.obtain(this, i), j);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                finishBoot();
                return;
            }
            if (i == 1) {
                updateUsbStats();
                return;
            }
            Slog.e("UsbStatsMonitor", "Unexpected message " + message.what);
        }

        public final void finishBoot() {
            Slog.d("UsbStatsMonitor", "finishBoot");
            if (!this.mSysFs.exists()) {
                Slog.e("UsbStatsMonitor", "No sysfs node");
                return;
            }
            if (this.mLogFile.exists()) {
                UsbMonitorImpl usbMonitorImpl = UsbMonitorImpl.this;
                usbMonitorImpl.stringToFile(this.mSysFs, usbMonitorImpl.readFileAsStringOrNull(this.mLogFile));
            } else {
                UsbMonitorImpl.this.stringToFile(this.mSysFs, "0");
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
            String readFileAsStringOrNull = UsbMonitorImpl.this.readFileAsStringOrNull(this.mSysFs);
            if (readFileAsStringOrNull != null && !readFileAsStringOrNull.equals(this.mCurrentUsbStats)) {
                this.mCurrentUsbStats = readFileAsStringOrNull;
                UsbMonitorImpl.this.stringToFile(this.mLogFile, readFileAsStringOrNull);
            }
            Slog.d("UsbStatsMonitor", this.mCurrentUsbStats);
            sendMessageDelayed(1, 60000L);
        }

        public void dump(PrintWriter printWriter) {
            printWriter.println("Current USB Stats: " + this.mCurrentUsbStats);
        }
    }

    public final String readFileAsStringOrNull(File file) {
        try {
            return IoUtils.readFileAsString(file.getAbsolutePath()).trim();
        } catch (IOException e) {
            Slog.e("UsbStatsMonitor", "Couldn't read " + file.getAbsolutePath(), e);
            return null;
        }
    }

    public final void stringToFile(File file, String str) {
        FileWriter fileWriter;
        if (!file.exists() || str == null) {
            return;
        }
        FileWriter fileWriter2 = null;
        try {
            try {
                try {
                    fileWriter = new FileWriter(file);
                } catch (IOException unused) {
                    Slog.e("UsbStatsMonitor", "Couldn't close stream");
                }
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileWriter.write(str);
            fileWriter.close();
        } catch (IOException e2) {
            e = e2;
            fileWriter2 = fileWriter;
            Slog.e("UsbStatsMonitor", "Couldn't write " + file.getAbsolutePath(), e);
            if (fileWriter2 != null) {
                fileWriter2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            fileWriter2 = fileWriter;
            if (fileWriter2 != null) {
                try {
                    fileWriter2.close();
                } catch (IOException unused2) {
                    Slog.e("UsbStatsMonitor", "Couldn't close stream");
                }
            }
            throw th;
        }
    }

    public void dump(PrintWriter printWriter) {
        UsbStatsHandler usbStatsHandler = this.mHandler;
        if (usbStatsHandler != null) {
            usbStatsHandler.dump(printWriter);
        }
    }
}
