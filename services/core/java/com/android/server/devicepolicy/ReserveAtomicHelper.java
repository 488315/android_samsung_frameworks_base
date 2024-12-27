package com.android.server.devicepolicy;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.FileUtils;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class ReserveAtomicHelper {
    public final String LOG_TAG;
    public boolean failFlag;
    public final File mReserveFile;
    public final File orignalFile;

    public ReserveAtomicHelper(File file) {
        this.mReserveFile = null;
        this.failFlag = false;
        String name = file.getName();
        this.orignalFile = file;
        String m$1 =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(name, ".reservecopy");
        this.LOG_TAG = "ReserveAtomic.owners";
        File file2 = new File(file.getParent(), m$1);
        this.mReserveFile = file2;
        if (!file2.exists() && file.exists()) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "reserve not exists copying orignal to reserve. status : ",
                    "ReserveAtomic.owners",
                    writeReserve());
        }
        this.failFlag = false;
    }

    public final boolean writeReserve() {
        this.failFlag = false;
        if (this.mReserveFile.exists()) {
            this.mReserveFile.delete();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(this.orignalFile);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.mReserveFile);
                try {
                    FileUtils.copy(fileInputStream, fileOutputStream);
                    fileOutputStream.getFD().sync();
                    fileOutputStream.close();
                    fileInputStream.close();
                    return true;
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.e(this.LOG_TAG, "Failed to write reserve copy: " + this.mReserveFile, e);
            if (this.mReserveFile.exists()) {
                this.mReserveFile.delete();
            }
            return false;
        }
    }
}
