package com.android.server.chimera;

import android.os.SystemClock;
import android.util.Pair;

import com.android.internal.util.RingBuffer;
import com.android.server.clipboard.ClipboardService;

import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class HeavyLaunchCounter {
    private static final long DELAY = 10800000;
    private RingBuffer mBuffer;
    private int mBufferSize;
    private int mLaunchPackageLimit;
    private long mStartTime;
    int mLaunchCounter = 0;
    private boolean mIsHeavyLaunch = false;
    private long mLastStartedUpTime = 0;

    public HeavyLaunchCounter(int i, int i2) {
        this.mBufferSize = 80;
        this.mLaunchPackageLimit = 25;
        this.mBuffer = new RingBuffer(Pair.class, this.mBufferSize);
        this.mStartTime = 0L;
        this.mBufferSize = i;
        this.mLaunchPackageLimit = i2;
        this.mStartTime = SystemClock.uptimeMillis();
    }

    public void addLaunch(int i, long j) {
        this.mBuffer.append(new Pair(Integer.valueOf(i), Long.valueOf(j)));
        this.mLaunchCounter++;
        if (SystemClock.uptimeMillis() - this.mStartTime <= DELAY
                || this.mLaunchCounter % 10 != 0) {
            return;
        }
        Pair[] pairArr = (Pair[]) this.mBuffer.toArray();
        int length = pairArr.length;
        int i2 = this.mBufferSize;
        if (length == i2) {
            if (((Long) pairArr[i2 - 1].second).longValue() - ((Long) pairArr[0].second).longValue()
                    >= ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                this.mIsHeavyLaunch = false;
                return;
            }
            HashSet hashSet = new HashSet();
            for (int i3 = 0; i3 < this.mBufferSize; i3++) {
                hashSet.add((Integer) pairArr[i3].first);
            }
            if (hashSet.size() < this.mLaunchPackageLimit) {
                this.mIsHeavyLaunch = false;
            } else {
                this.mIsHeavyLaunch = true;
                this.mLastStartedUpTime = SystemClock.uptimeMillis() - this.mStartTime;
            }
        }
    }

    public long getLastStartedUpTime() {
        return this.mLastStartedUpTime;
    }

    public boolean isHeavyLaunch() {
        return this.mIsHeavyLaunch;
    }
}
