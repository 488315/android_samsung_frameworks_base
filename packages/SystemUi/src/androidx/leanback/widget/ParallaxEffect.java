package androidx.leanback.widget;

import androidx.leanback.widget.Parallax;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ParallaxEffect {
    public final List mMarkerValues = new ArrayList(2);
    public final List mWeights = new ArrayList(2);
    public final List mTotalWeights = new ArrayList(2);
    public final List mTargets = new ArrayList(4);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IntEffect extends ParallaxEffect {
        @Override // androidx.leanback.widget.ParallaxEffect
        public final float calculateFraction(Parallax parallax) {
            float maxValue;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.mMarkerValues;
                if (i >= arrayList.size()) {
                    return 1.0f;
                }
                Parallax.IntPropertyMarkerValue intPropertyMarkerValue = (Parallax.IntPropertyMarkerValue) arrayList.get(i);
                int i5 = ((Parallax.IntProperty) intPropertyMarkerValue.mProperty).mIndex;
                int markerValue = intPropertyMarkerValue.getMarkerValue(parallax);
                int i6 = parallax.mValues[i5];
                if (i == 0) {
                    if (i6 >= markerValue) {
                        return 0.0f;
                    }
                } else {
                    if (i2 == i5 && i3 < markerValue) {
                        throw new IllegalStateException("marker value of same variable must be descendant order");
                    }
                    if (i6 == Integer.MAX_VALUE) {
                        return getFractionWithWeightAdjusted((i3 - i4) / parallax.getMaxValue(), i);
                    }
                    if (i6 >= markerValue) {
                        if (i2 == i5) {
                            maxValue = (i3 - i6) / (i3 - markerValue);
                        } else if (i4 != Integer.MIN_VALUE) {
                            int i7 = (i6 - i4) + i3;
                            maxValue = (i7 - i6) / (i7 - markerValue);
                        } else {
                            maxValue = 1.0f - ((i6 - markerValue) / parallax.getMaxValue());
                        }
                        return getFractionWithWeightAdjusted(maxValue, i);
                    }
                }
                i++;
                i3 = markerValue;
                i2 = i5;
                i4 = i6;
            }
        }
    }

    public abstract float calculateFraction(Parallax parallax);

    public final float getFractionWithWeightAdjusted(float f, int i) {
        List list = this.mMarkerValues;
        if (((ArrayList) list).size() < 3) {
            return f;
        }
        List list2 = this.mWeights;
        if (!(((ArrayList) list2).size() == ((ArrayList) list).size() - 1)) {
            float size = ((ArrayList) list).size() - 1;
            float f2 = f / size;
            return i >= 2 ? f2 + ((i - 1) / size) : f2;
        }
        List list3 = this.mTotalWeights;
        float floatValue = ((Float) ((ArrayList) list3).get(((ArrayList) list3).size() - 1)).floatValue();
        float floatValue2 = (((Float) ((ArrayList) list2).get(i - 1)).floatValue() * f) / floatValue;
        if (i >= 2) {
            return (((Float) ((ArrayList) list3).get(i - 2)).floatValue() / floatValue) + floatValue2;
        }
        return floatValue2;
    }
}
