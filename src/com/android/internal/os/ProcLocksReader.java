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
            long nextLong = this.mReader.nextLong(true);
            if (nextLong == j) {
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                this.mReader.nextIgnored();
                int nextInt = this.mReader.nextInt();
                if (nextInt > 0) {
                    this.mPids.add(nextInt);
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
                int nextInt2 = this.mReader.nextInt();
                if (nextInt2 > 0) {
                    if (this.mPids.size() == 0) {
                        this.mPids.add(nextInt2);
                    } else {
                        this.mPids.set(0, nextInt2);
                    }
                }
                this.mReader.finishLine();
                j = nextLong;
            }
        }
        if (this.mPids.size() > 1) {
            procLocksReaderCallback.onBlockingFileLock(this.mPids);
        }
    }
}
