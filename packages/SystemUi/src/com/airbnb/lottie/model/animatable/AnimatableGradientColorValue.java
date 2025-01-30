package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.GradientColorKeyframeAnimation;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.value.Keyframe;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimatableGradientColorValue extends BaseAnimatableValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimatableGradientColorValue(List<Keyframe> list) {
        super(list);
        for (int i = 0; i < list.size(); i++) {
            Keyframe keyframe = list.get(i);
            GradientColor gradientColor = (GradientColor) keyframe.startValue;
            GradientColor gradientColor2 = (GradientColor) keyframe.endValue;
            if (gradientColor != null && gradientColor2 != null) {
                float[] fArr = gradientColor.positions;
                int length = fArr.length;
                float[] fArr2 = gradientColor2.positions;
                if (length != fArr2.length) {
                    int length2 = fArr.length + fArr2.length;
                    float[] fArr3 = new float[length2];
                    System.arraycopy(fArr, 0, fArr3, 0, fArr.length);
                    System.arraycopy(fArr2, 0, fArr3, fArr.length, fArr2.length);
                    Arrays.sort(fArr3);
                    float f = Float.NaN;
                    int i2 = 0;
                    for (int i3 = 0; i3 < length2; i3++) {
                        float f2 = fArr3[i3];
                        if (f2 != f) {
                            fArr3[i2] = f2;
                            i2++;
                            f = fArr3[i3];
                        }
                    }
                    float[] copyOfRange = Arrays.copyOfRange(fArr3, 0, i2);
                    keyframe = Keyframe.copyWith(gradientColor.copyWithPositions(copyOfRange), gradientColor2.copyWithPositions(copyOfRange));
                }
            }
            list.set(i, keyframe);
        }
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final BaseKeyframeAnimation createAnimation() {
        return new GradientColorKeyframeAnimation(this.keyframes);
    }
}
