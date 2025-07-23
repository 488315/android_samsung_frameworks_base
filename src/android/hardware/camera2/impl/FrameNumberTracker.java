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
            long longValue = key.longValue();
            int intValue = next.getValue().intValue();
            Boolean bool = false;
            if (longValue == this.mCompletedFrameNumber[intValue] + 1) {
                bool = true;
            }
            if (this.mPendingFrameNumbers[intValue].isEmpty()) {
                int i = 1;
                while (true) {
                    if (i >= 3) {
                        break;
                    }
                    int i2 = (intValue + i) % 3;
                    if (!this.mPendingFrameNumbersWithOtherType[i2].isEmpty() && longValue == this.mPendingFrameNumbersWithOtherType[i2].element().longValue()) {
                        this.mPendingFrameNumbersWithOtherType[i2].remove();
                        bool = true;
                        break;
                    }
                    i++;
                }
                if (!bool.booleanValue()) {
                    int i3 = (intValue + 1) % 3;
                    int i4 = (intValue + 2) % 3;
                    if (this.mPendingFrameNumbersWithOtherType[i3].isEmpty() && this.mPendingFrameNumbersWithOtherType[i4].isEmpty()) {
                        long[] jArr = this.mCompletedFrameNumber;
                        long max = Math.max(jArr[i3], jArr[i4]) + 1;
                        if (max > this.mCompletedFrameNumber[intValue] + 1 && max == longValue) {
                            bool = true;
                        }
                    }
                }
            } else if (longValue == this.mPendingFrameNumbers[intValue].element().longValue()) {
                this.mPendingFrameNumbers[intValue].remove();
                bool = true;
            }
            if (bool.booleanValue()) {
                this.mCompletedFrameNumber[intValue] = longValue;
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
        List<CaptureResult> list = this.mPartialResults.get(Long.valueOf(j));
        if (list == null) {
            list = new ArrayList<>();
            this.mPartialResults.put(Long.valueOf(j), list);
        }
        list.add(captureResult);
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
        long max = Math.max(jArr[i2], jArr[i3]);
        if (j < max) {
            if (!this.mPendingFrameNumbers[i].isEmpty()) {
                Long element = this.mPendingFrameNumbers[i].element();
                if (j != element.longValue()) {
                    if (j < element.longValue()) {
                        throw new IllegalArgumentException("frame number " + j + " is a repeat");
                    }
                    throw new IllegalArgumentException("frame number " + j + " comes out of order. Expecting " + element);
                }
                this.mPendingFrameNumbers[i].remove();
            } else {
                int indexOf = this.mPendingFrameNumbersWithOtherType[i2].indexOf(Long.valueOf(j));
                int indexOf2 = this.mPendingFrameNumbersWithOtherType[i3].indexOf(Long.valueOf(j));
                boolean z = indexOf != -1;
                if (!(z ^ (indexOf2 != -1))) {
                    throw new IllegalArgumentException("frame number " + j + " is a repeat or invalid");
                }
                if (z) {
                    linkedList = this.mPendingFrameNumbersWithOtherType[i2];
                    linkedList2 = this.mPendingFrameNumbers[i3];
                } else {
                    LinkedList<Long> linkedList3 = this.mPendingFrameNumbersWithOtherType[i3];
                    LinkedList<Long> linkedList4 = this.mPendingFrameNumbers[i2];
                    linkedList = linkedList3;
                    indexOf = indexOf2;
                    linkedList2 = linkedList4;
                }
                for (int i4 = 0; i4 < indexOf; i4++) {
                    linkedList2.add(linkedList.removeFirst());
                }
                linkedList.remove();
            }
        } else {
            long max2 = Math.max(max, this.mCompletedFrameNumber[i]);
            while (true) {
                max2++;
                if (max2 >= j) {
                    break;
                } else {
                    this.mPendingFrameNumbersWithOtherType[i].add(Long.valueOf(max2));
                }
            }
        }
        this.mCompletedFrameNumber[i] = j;
    }
}
