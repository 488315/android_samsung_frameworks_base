package androidx.leanback.widget;

import androidx.leanback.widget.Parallax;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ParallaxEffect {
    public final List mMarkerValues = new ArrayList(2);
    public final List mWeights = new ArrayList(2);
    public final List mTotalWeights = new ArrayList(2);
    public final List mTargets = new ArrayList(4);

    public final class IntEffect extends ParallaxEffect {
        @Override // androidx.leanback.widget.ParallaxEffect
        public final float calculateFraction(Parallax parallax) {
            float maxValue;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i < ((ArrayList) this.mMarkerValues).size()) {
                Parallax.IntPropertyMarkerValue intPropertyMarkerValue = (Parallax.IntPropertyMarkerValue) ((ArrayList) this.mMarkerValues).get(i);
                int i5 = ((Parallax.IntProperty) intPropertyMarkerValue.mProperty).mIndex;
                float f = intPropertyMarkerValue.mFactionOfMax;
                int iRound = intPropertyMarkerValue.mValue;
                if (f != 0.0f) {
                    iRound = Math.round(parallax.getMaxValue() * f) + iRound;
                }
                int i6 = parallax.mValues[i5];
                if (i == 0) {
                    if (i6 >= iRound) {
                        return 0.0f;
                    }
                } else {
                    if (i2 == i5 && i3 < iRound) {
                        throw new IllegalStateException("marker value of same variable must be descendant order");
                    }
                    if (i6 == Integer.MAX_VALUE) {
                        return getFractionWithWeightAdjusted((i3 - i4) / parallax.getMaxValue(), i);
                    }
                    if (i6 >= iRound) {
                        if (i2 == i5) {
                            maxValue = (i3 - i6) / (i3 - iRound);
                        } else if (i4 != Integer.MIN_VALUE) {
                            int i7 = (i6 - i4) + i3;
                            maxValue = (i7 - i6) / (i7 - iRound);
                        } else {
                            maxValue = 1.0f - ((i6 - iRound) / parallax.getMaxValue());
                        }
                        return getFractionWithWeightAdjusted(maxValue, i);
                    }
                }
                i++;
                i3 = iRound;
                i2 = i5;
                i4 = i6;
            }
            return 1.0f;
        }
    }

    public abstract float calculateFraction(Parallax parallax);

    public final float getFractionWithWeightAdjusted(float f, int i) {
        if (((ArrayList) this.mMarkerValues).size() >= 3) {
            if (((ArrayList) this.mWeights).size() == ((ArrayList) this.mMarkerValues).size() - 1) {
                float fFloatValue = ((Float) ((ArrayList) this.mTotalWeights).get(((ArrayList) r0).size() - 1)).floatValue();
                float fFloatValue2 = (((Float) ((ArrayList) this.mWeights).get(i - 1)).floatValue() * f) / fFloatValue;
                if (i < 2) {
                    return fFloatValue2;
                }
                return (((Float) ((ArrayList) this.mTotalWeights).get(i - 2)).floatValue() / fFloatValue) + fFloatValue2;
            }
            float size = ((ArrayList) this.mMarkerValues).size() - 1;
            f /= size;
            if (i >= 2) {
                return ((i - 1) / size) + f;
            }
        }
        return f;
    }
}
