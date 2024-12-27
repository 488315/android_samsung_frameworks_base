package com.android.server.am;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class LmkdStatsReporter {
    public static void logKillOccurred(DataInputStream dataInputStream, int i, int i2) {
        int i3;
        try {
            long readLong = dataInputStream.readLong();
            long readLong2 = dataInputStream.readLong();
            long readLong3 = dataInputStream.readLong();
            long readLong4 = dataInputStream.readLong();
            long readLong5 = dataInputStream.readLong();
            long readLong6 = dataInputStream.readLong();
            int readInt = dataInputStream.readInt();
            int readInt2 = dataInputStream.readInt();
            int readInt3 = dataInputStream.readInt();
            int readInt4 = dataInputStream.readInt();
            int readInt5 = dataInputStream.readInt();
            int readInt6 = dataInputStream.readInt();
            int readInt7 = dataInputStream.readInt();
            int readInt8 = dataInputStream.readInt();
            int readInt9 = dataInputStream.readInt();
            String readUTF = dataInputStream.readUTF();
            switch (readInt6) {
                case 0:
                    i3 = 1;
                    break;
                case 1:
                    i3 = 2;
                    break;
                case 2:
                    i3 = 3;
                    break;
                case 3:
                    i3 = 4;
                    break;
                case 4:
                    i3 = 5;
                    break;
                case 5:
                    i3 = 6;
                    break;
                case 6:
                    i3 = 7;
                    break;
                case 7:
                    i3 = 8;
                    break;
                case 8:
                    i3 = 9;
                    break;
                case 9:
                    i3 = 10;
                    break;
                default:
                    i3 = 0;
                    break;
            }
            FrameworkStatsLog.write(
                    51, readInt, readUTF, readInt2, readLong, readLong2, readLong3, readLong4,
                    readLong5, readLong6, readInt3, readInt4, readInt5, i3, readInt7, readInt8, i,
                    i2);
            SecLmkdStats.noteLmkKillOccurred(readInt2, readInt9, readInt6);
        } catch (IOException unused) {
            Slog.e("ActivityManager", "Invalid buffer data. Failed to log LMK_KILL_OCCURRED");
        }
    }
}
