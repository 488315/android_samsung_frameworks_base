package android.text;

import com.android.internal.util.Preconditions;

import java.util.Arrays;
import java.util.Objects;

public abstract class SegmentFinder {
    public static final int DONE = -1;

    public abstract int nextEndBoundary(int i);

    public abstract int nextStartBoundary(int i);

    public abstract int previousEndBoundary(int i);

    public abstract int previousStartBoundary(int i);

    public static class PrescribedSegmentFinder extends SegmentFinder {
        private final int[] mSegments;

        public PrescribedSegmentFinder(int[] segments) {
            checkSegmentsValid(segments);
            this.mSegments = segments;
        }

        @Override // android.text.SegmentFinder
        public int previousStartBoundary(int offset) {
            return findPrevious(offset, true);
        }

        @Override // android.text.SegmentFinder
        public int previousEndBoundary(int offset) {
            return findPrevious(offset, false);
        }

        @Override // android.text.SegmentFinder
        public int nextStartBoundary(int offset) {
            return findNext(offset, true);
        }

        @Override // android.text.SegmentFinder
        public int nextEndBoundary(int offset) {
            return findNext(offset, false);
        }

        private int findNext(int offset, boolean isStart) {
            int index;
            if (offset < 0) {
                return -1;
            }
            if (this.mSegments.length < 1 || offset > this.mSegments[this.mSegments.length - 1]) {
                return -1;
            }
            if (offset < this.mSegments[0]) {
                int[] iArr = this.mSegments;
                return isStart ? iArr[0] : iArr[1];
            }
            int index2 = Arrays.binarySearch(this.mSegments, offset);
            if (index2 >= 0) {
                if (index2 + 1 < this.mSegments.length && this.mSegments[index2 + 1] == offset) {
                    index2++;
                }
                index = index2 + 1;
            } else {
                index = -(index2 + 1);
            }
            if (index >= this.mSegments.length) {
                return -1;
            }
            boolean indexIsStart = index % 2 == 0;
            if (isStart != indexIsStart) {
                if (index + 1 < this.mSegments.length) {
                    return this.mSegments[index + 1];
                }
                return -1;
            }
            return this.mSegments[index];
        }

        private int findPrevious(int offset, boolean isStart) {
            int index;
            if (this.mSegments.length < 1 || offset < this.mSegments[0]) {
                return -1;
            }
            if (offset > this.mSegments[this.mSegments.length - 1]) {
                int[] iArr = this.mSegments;
                return isStart ? iArr[r1.length - 2] : iArr[this.mSegments.length - 1];
            }
            int index2 = Arrays.binarySearch(this.mSegments, offset);
            if (index2 >= 0) {
                if (index2 > 0 && this.mSegments[index2 - 1] == offset) {
                    index2--;
                }
                index = index2 - 1;
            } else {
                index = (-(index2 + 1)) - 1;
            }
            if (index < 0) {
                return -1;
            }
            boolean indexIsStart = index % 2 == 0;
            if (isStart != indexIsStart) {
                if (index > 0) {
                    return this.mSegments[index - 1];
                }
                return -1;
            }
            return this.mSegments[index];
        }

        private static void checkSegmentsValid(int[] segments) {
            Objects.requireNonNull(segments);
            Preconditions.checkArgument(
                    segments.length % 2 == 0, "the length of segments must be even");
            if (segments.length == 0) {
                return;
            }
            int lastSegmentEnd = Integer.MIN_VALUE;
            for (int index = 0; index < segments.length; index += 2) {
                if (segments[index] < lastSegmentEnd) {
                    throw new IllegalArgumentException("segments can't overlap");
                }
                if (segments[index] >= segments[index + 1]) {
                    throw new IllegalArgumentException("the segment range can't be empty");
                }
                lastSegmentEnd = segments[index + 1];
            }
        }
    }
}
