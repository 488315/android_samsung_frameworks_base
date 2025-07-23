package android.text;

import android.graphics.Paint;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class Highlights {
    private final List<Pair<Paint, int[]>> mHighlights;

    private Highlights(List<Pair<Paint, int[]>> list) {
        this.mHighlights = list;
    }

    public int getSize() {
        return this.mHighlights.size();
    }

    public Paint getPaint(int i) {
        return this.mHighlights.get(i).first;
    }

    public int[] getRanges(int i) {
        return this.mHighlights.get(i).second;
    }

    public static final class Builder {
        private final List<Pair<Paint, int[]>> mHighlights = new ArrayList();

        public Builder addRange(Paint paint, int i, int i2) {
            if (i > i2) {
                throw new IllegalArgumentException("start must not be larger than end: " + i + ", " + i2);
            }
            Objects.requireNonNull(paint);
            this.mHighlights.add(new Pair<>(paint, new int[]{i, i2}));
            return this;
        }

        public Builder addRanges(Paint paint, int... iArr) {
            if (iArr.length % 2 == 1) {
                throw new IllegalArgumentException("Flatten ranges must have even numbered elements");
            }
            for (int i = 0; i < iArr.length / 2; i++) {
                int i2 = i * 2;
                if (iArr[i2] > iArr[i2 + 1]) {
                    throw new IllegalArgumentException("Reverse range found in the flatten range: " + Arrays.toString(iArr));
                }
            }
            Objects.requireNonNull(paint);
            this.mHighlights.add(new Pair<>(paint, iArr));
            return this;
        }

        public Highlights build() {
            return new Highlights(this.mHighlights);
        }
    }
}
