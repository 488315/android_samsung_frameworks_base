package com.android.internal.os;

import android.os.Process;
import android.util.IntArray;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/* loaded from: classes5.dex */
public class ProcTimeInStateReader {
    private static final String TAG = "ProcTimeInStateReader";
    private long[] mFrequenciesKhz;
    private int[] mTimeInStateTimeFormat;
    private static final int[] TIME_IN_STATE_LINE_FREQUENCY_FORMAT = {8224, 10};
    private static final int[] TIME_IN_STATE_LINE_TIME_FORMAT = {32, 8202};
    private static final int[] TIME_IN_STATE_HEADER_LINE_FORMAT = {10};

    public ProcTimeInStateReader(Path path) throws IOException {
        initializeTimeInStateFormat(path);
    }

    public long[] getUsageTimesMillis(Path path) {
        int length = this.mFrequenciesKhz.length;
        long[] jArr = new long[length];
        if (!Process.readProcFile(path.toString(), this.mTimeInStateTimeFormat, null, jArr, null)) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            jArr[i] = jArr[i] * 10;
        }
        return jArr;
    }

    public long[] getFrequenciesKhz() {
        return this.mFrequenciesKhz;
    }

    private void initializeTimeInStateFormat(Path path) throws IOException {
        byte[] readAllBytes = Files.readAllBytes(path);
        IntArray intArray = new IntArray();
        IntArray intArray2 = new IntArray();
        int i = 0;
        int i2 = 0;
        while (i < readAllBytes.length) {
            if (!Character.isDigit(readAllBytes[i])) {
                int[] iArr = TIME_IN_STATE_HEADER_LINE_FORMAT;
                intArray.addAll(iArr);
                intArray2.addAll(iArr);
            } else {
                intArray.addAll(TIME_IN_STATE_LINE_FREQUENCY_FORMAT);
                intArray2.addAll(TIME_IN_STATE_LINE_TIME_FORMAT);
                i2++;
            }
            while (i < readAllBytes.length && readAllBytes[i] != 10) {
                i++;
            }
            i++;
        }
        if (i2 == 0) {
            throw new IOException("Empty time_in_state file");
        }
        long[] jArr = new long[i2];
        if (!Process.parseProcLine(readAllBytes, 0, readAllBytes.length, intArray.toArray(), null, jArr, null)) {
            throw new IOException("Failed to parse time_in_state file");
        }
        this.mTimeInStateTimeFormat = intArray2.toArray();
        this.mFrequenciesKhz = jArr;
    }
}
