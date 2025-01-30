package com.android.server.chimera;

import android.os.SystemClock;
import android.util.Pair;
import com.android.internal.util.RingBuffer;
import com.android.server.clipboard.ClipboardService;
import java.util.HashSet;

/* loaded from: classes.dex */
public class HeavyLaunchCounter {
  public RingBuffer mBuffer;
  public int mBufferSize;
  public int mLaunchPackageLimit;
  public long mStartTime;
  public int mLaunchCounter = 0;
  public boolean mIsHeavyLaunch = false;
  public long mLastStartedUpTime = 0;

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
    if (SystemClock.uptimeMillis() - this.mStartTime <= 10800000 || this.mLaunchCounter % 10 != 0) {
      return;
    }
    Pair[] pairArr = (Pair[]) this.mBuffer.toArray();
    int length = pairArr.length;
    int i2 = this.mBufferSize;
    if (length == i2) {
      if (((Long) pairArr[i2 - 1].second).longValue() - ((Long) pairArr[0].second).longValue()
          < ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
        HashSet hashSet = new HashSet();
        for (int i3 = 0; i3 < this.mBufferSize; i3++) {
          hashSet.add((Integer) pairArr[i3].first);
        }
        if (hashSet.size() >= this.mLaunchPackageLimit) {
          this.mIsHeavyLaunch = true;
          this.mLastStartedUpTime = SystemClock.uptimeMillis() - this.mStartTime;
          return;
        } else {
          this.mIsHeavyLaunch = false;
          return;
        }
      }
      this.mIsHeavyLaunch = false;
    }
  }

  public boolean isHeavyLaunch() {
    return this.mIsHeavyLaunch;
  }

  public long getLastStartedUpTime() {
    return this.mLastStartedUpTime;
  }
}
