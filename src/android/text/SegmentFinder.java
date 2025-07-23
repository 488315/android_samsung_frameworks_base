package android.text;

import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public abstract class SegmentFinder {
    public static final int DONE = -1;

    public abstract int nextEndBoundary(int i);

    public abstract int nextStartBoundary(int i);

    public abstract int previousEndBoundary(int i);

    public abstract int previousStartBoundary(int i);

    public static class PrescribedSegmentFinder extends SegmentFinder {
        private final int[] mSegments;

        public PrescribedSegmentFinder(int[] iArr) {
            checkSegmentsValid(iArr);
            this.mSegments = iArr;
        }

        @Override // android.text.SegmentFinder
        public int previousStartBoundary(int i) {
            return findPrevious(i, true);
        }

        @Override // android.text.SegmentFinder
        public int previousEndBoundary(int i) {
            return findPrevious(i, false);
        }

        @Override // android.text.SegmentFinder
        public int nextStartBoundary(int i) {
            return findNext(i, true);
        }

        @Override // android.text.SegmentFinder
        public int nextEndBoundary(int i) {
            return findNext(i, false);
        }

        private int findNext(int i, boolean z) {
            int i2;
            if (i < 0) {
                return -1;
            }
            int[] iArr = this.mSegments;
            if (iArr.length < 1 || i > iArr[iArr.length - 1]) {
                return -1;
            }
            int i3 = iArr[0];
            if (i < i3) {
                return z ? i3 : iArr[1];
            }
            int binarySearch = Arrays.binarySearch(iArr, i);
            if (binarySearch >= 0) {
                int i4 = binarySearch + 1;
                int[] iArr2 = this.mSegments;
                if (i4 < iArr2.length && iArr2[i4] == i) {
                    binarySearch = i4;
                }
                i2 = binarySearch + 1;
            } else {
                i2 = -(binarySearch + 1);
            }
            int[] iArr3 = this.mSegments;
            if (i2 >= iArr3.length) {
                return -1;
            }
            if (z != (i2 % 2 == 0)) {
                int i5 = i2 + 1;
                if (i5 < iArr3.length) {
                    return iArr3[i5];
                }
                return -1;
            }
            return iArr3[i2];
        }

        private int findPrevious(int i, boolean z) {
            int i2;
            int[] iArr = this.mSegments;
            if (iArr.length >= 1) {
                if (i >= iArr[0]) {
                    if (i > iArr[iArr.length - 1]) {
                        int length = iArr.length;
                        return z ? iArr[length - 2] : iArr[length - 1];
                    }
                    int binarySearch = Arrays.binarySearch(iArr, i);
                    if (binarySearch >= 0) {
                        if (binarySearch > 0) {
                            int i3 = binarySearch - 1;
                            if (this.mSegments[i3] == i) {
                                binarySearch = i3;
                            }
                        }
                        i2 = binarySearch - 1;
                    } else {
                        i2 = (-(binarySearch + 1)) - 1;
                    }
                    if (i2 < 0) {
                        return -1;
                    }
                    if (z == (i2 % 2 == 0)) {
                        return this.mSegments[i2];
                    }
                    if (i2 > 0) {
                        return this.mSegments[i2 - 1];
                    }
                    return -1;
                }
            }
            return -1;
        }

        private static void checkSegmentsValid(int[] iArr) {
            Objects.requireNonNull(iArr);
            Preconditions.checkArgument(iArr.length % 2 == 0, "the length of segments must be even");
            if (iArr.length == 0) {
                return;
            }
            int i = Integer.MIN_VALUE;
            for (int i2 = 0; i2 < iArr.length; i2 += 2) {
                int i3 = iArr[i2];
                if (i3 < i) {
                    throw new IllegalArgumentException("segments can't overlap");
                }
                i = iArr[i2 + 1];
                if (i3 >= i) {
                    throw new IllegalArgumentException("the segment range can't be empty");
                }
            }
        }
    }
}
