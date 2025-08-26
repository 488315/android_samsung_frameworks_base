package com.android.internal.os;

import android.util.IntArray;
import com.android.internal.util.ProcFileReader;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class ProcLocksReader {
    private final String mPath;
    private IntArray mPids;
    private ProcFileReader mReader;

    public interface ProcLocksReaderCallback {
        void onBlockingFileLock(IntArray intArray);
    }

    public ProcLocksReader() {
        this.mReader = null;
        this.mPids = new IntArray();
        this.mPath = "/proc/locks";
    }

    public ProcLocksReader(String str) {
        this.mReader = null;
        this.mPids = new IntArray();
        this.mPath = str;
    }

    public void handleBlockingFileLocks(ProcLocksReaderCallback procLocksReaderCallback) throws IOException {
        ProcFileReader procFileReader = this.mReader;
        if (procFileReader == null) {
            this.mReader = new ProcFileReader(new FileInputStream(this.mPath));
        } else {
            procFileReader.rewind();
        }
        this.mPids.clear();
        long j = -1;
        while (this.mReader.hasMoreData()) {
            long jNextLong = this.mReader.nextLong(true);
            if (jNextLong == j) {
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int iNextInt = this.mReader.nextInt();
                if (iNextInt > 0) {
                    this.mPids.add(iNextInt);
                }
                this.mReader.finishLine();
            } else {
                if (this.mPids.size() > 1) {
                    procLocksReaderCallback.onBlockingFileLock(this.mPids);
                    this.mPids.clear();
                }
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int iNextInt2 = this.mReader.nextInt();
                if (iNextInt2 > 0) {
                    if (this.mPids.size() == 0) {
                        this.mPids.add(iNextInt2);
                    } else {
                        this.mPids.set(0, iNextInt2);
                    }
                }
                this.mReader.finishLine();
                j = jNextLong;
            }
        }
        if (this.mPids.size() > 1) {
            procLocksReaderCallback.onBlockingFileLock(this.mPids);
        }
    }
}
