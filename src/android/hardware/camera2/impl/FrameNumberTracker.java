package android.hardware.camera2.impl;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class FrameNumberTracker {
    private static final String TAG = "FrameNumberTracker";
    private long[] mCompletedFrameNumber = new long[3];
    private final LinkedList<Long>[] mPendingFrameNumbersWithOtherType = new LinkedList[3];
    private final LinkedList<Long>[] mPendingFrameNumbers = new LinkedList[3];
    private final TreeMap<Long, Integer> mFutureErrorMap = new TreeMap<>();
    private final HashMap<Long, List<CaptureResult>> mPartialResults = new HashMap<>();

    public FrameNumberTracker() {
        for (int i = 0; i < 3; i++) {
            this.mCompletedFrameNumber[i] = -1;
            this.mPendingFrameNumbersWithOtherType[i] = new LinkedList<>();
            this.mPendingFrameNumbers[i] = new LinkedList<>();
        }
    }

    private void update() {
        Iterator<Map.Entry<Long, Integer>> it = this.mFutureErrorMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Integer> next = it.next();
            Long key = next.getKey();
            long jLongValue = key.longValue();
            int iIntValue = next.getValue().intValue();
            Boolean bool = false;
            if (jLongValue == this.mCompletedFrameNumber[iIntValue] + 1) {
                bool = true;
            }
            if (this.mPendingFrameNumbers[iIntValue].isEmpty()) {
                int i = 1;
                while (true) {
                    if (i >= 3) {
                        break;
                    }
                    int i2 = (iIntValue + i) % 3;
                    if (!this.mPendingFrameNumbersWithOtherType[i2].isEmpty() && jLongValue == this.mPendingFrameNumbersWithOtherType[i2].element().longValue()) {
                        this.mPendingFrameNumbersWithOtherType[i2].remove();
                        bool = true;
                        break;
                    }
                    i++;
                }
                if (!bool.booleanValue()) {
                    int i3 = (iIntValue + 1) % 3;
                    int i4 = (iIntValue + 2) % 3;
                    if (this.mPendingFrameNumbersWithOtherType[i3].isEmpty() && this.mPendingFrameNumbersWithOtherType[i4].isEmpty()) {
                        long[] jArr = this.mCompletedFrameNumber;
                        long jMax = Math.max(jArr[i3], jArr[i4]) + 1;
                        if (jMax > this.mCompletedFrameNumber[iIntValue] + 1 && jMax == jLongValue) {
                            bool = true;
                        }
                    }
                }
            } else if (jLongValue == this.mPendingFrameNumbers[iIntValue].element().longValue()) {
                this.mPendingFrameNumbers[iIntValue].remove();
                bool = true;
            }
            if (bool.booleanValue()) {
                this.mCompletedFrameNumber[iIntValue] = jLongValue;
                this.mPartialResults.remove(key);
                it.remove();
            }
        }
    }

    public void updateTracker(long j, boolean z, int i) {
        if (z) {
            this.mFutureErrorMap.put(Long.valueOf(j), Integer.valueOf(i));
        } else {
            try {
                updateCompletedFrameNumber(j, i);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        update();
    }

    public void updateTracker(long j, CaptureResult captureResult, boolean z, int i) {
        if (!z) {
            updateTracker(j, false, i);
            return;
        }
        if (captureResult == null) {
            return;
        }
        List<CaptureResult> arrayList = this.mPartialResults.get(Long.valueOf(j));
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.mPartialResults.put(Long.valueOf(j), arrayList);
        }
        arrayList.add(captureResult);
    }

    public List<CaptureResult> popPartialResults(long j) {
        return this.mPartialResults.remove(Long.valueOf(j));
    }

    public long getCompletedFrameNumber() {
        return this.mCompletedFrameNumber[0];
    }

    public long getCompletedReprocessFrameNumber() {
        return this.mCompletedFrameNumber[1];
    }

    public long getCompletedZslStillFrameNumber() {
        return this.mCompletedFrameNumber[2];
    }

    private void updateCompletedFrameNumber(long j, int i) throws IllegalArgumentException {
        LinkedList<Long> linkedList;
        LinkedList<Long> linkedList2;
        long[] jArr = this.mCompletedFrameNumber;
        if (j <= jArr[i]) {
            throw new IllegalArgumentException("frame number " + j + " is a repeat");
        }
        int i2 = (i + 1) % 3;
        int i3 = (i + 2) % 3;
        long jMax = Math.max(jArr[i2], jArr[i3]);
        if (j < jMax) {
            if (!this.mPendingFrameNumbers[i].isEmpty()) {
                Long lElement = this.mPendingFrameNumbers[i].element();
                if (j != lElement.longValue()) {
                    if (j < lElement.longValue()) {
                        throw new IllegalArgumentException("frame number " + j + " is a repeat");
                    }
                    throw new IllegalArgumentException("frame number " + j + " comes out of order. Expecting " + lElement);
                }
                this.mPendingFrameNumbers[i].remove();
            } else {
                int iIndexOf = this.mPendingFrameNumbersWithOtherType[i2].indexOf(Long.valueOf(j));
                int iIndexOf2 = this.mPendingFrameNumbersWithOtherType[i3].indexOf(Long.valueOf(j));
                boolean z = iIndexOf != -1;
                if (!(z ^ (iIndexOf2 != -1))) {
                    throw new IllegalArgumentException("frame number " + j + " is a repeat or invalid");
                }
                if (z) {
                    linkedList = this.mPendingFrameNumbersWithOtherType[i2];
                    linkedList2 = this.mPendingFrameNumbers[i3];
                } else {
                    LinkedList<Long> linkedList3 = this.mPendingFrameNumbersWithOtherType[i3];
                    LinkedList<Long> linkedList4 = this.mPendingFrameNumbers[i2];
                    linkedList = linkedList3;
                    iIndexOf = iIndexOf2;
                    linkedList2 = linkedList4;
                }
                for (int i4 = 0; i4 < iIndexOf; i4++) {
                    linkedList2.add(linkedList.removeFirst());
                }
                linkedList.remove();
            }
        } else {
            long jMax2 = Math.max(jMax, this.mCompletedFrameNumber[i]);
            while (true) {
                jMax2++;
                if (jMax2 >= j) {
                    break;
                } else {
                    this.mPendingFrameNumbersWithOtherType[i].add(Long.valueOf(jMax2));
                }
            }
        }
        this.mCompletedFrameNumber[i] = j;
    }
}
