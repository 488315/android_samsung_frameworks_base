package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GradientColorKeyframeAnimation extends KeyframeAnimation {
    public final GradientColor gradientColor;

    public GradientColorKeyframeAnimation(List<Keyframe> list) {
        super(list);
        GradientColor gradientColor = (GradientColor) list.get(0).startValue;
        int length = gradientColor != null ? gradientColor.colors.length : 0;
        this.gradientColor = new GradientColor(new float[length], new int[length]);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public final Object getValue(Keyframe keyframe, float f) {
        GradientColor gradientColor = (GradientColor) keyframe.startValue;
        GradientColor gradientColor2 = (GradientColor) keyframe.endValue;
        GradientColor gradientColor3 = this.gradientColor;
        gradientColor3.getClass();
        int[] iArr = gradientColor.colors;
        int length = iArr.length;
        int[] iArr2 = gradientColor2.colors;
        if (length != iArr2.length) {
            StringBuilder sb = new StringBuilder("Cannot interpolate between gradients. Lengths vary (");
            sb.append(iArr.length);
            sb.append(" vs ");
            throw new IllegalArgumentException(ReorderTile$$ExternalSyntheticOutline0.m(iArr2.length, ")", sb));
        }
        for (int i = 0; i < iArr.length; i++) {
            gradientColor3.positions[i] = MiscUtils.lerp(gradientColor.positions[i], gradientColor2.positions[i], f);
            gradientColor3.colors[i] = GammaEvaluator.evaluate(f, iArr[i], iArr2[i]);
        }
        return gradientColor3;
    }
}
