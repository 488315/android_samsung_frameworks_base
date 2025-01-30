package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.GradientStrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GradientStroke implements ContentModel {
    public final ShapeStroke.LineCapType capType;
    public final AnimatableFloatValue dashOffset;
    public final AnimatablePointValue endPoint;
    public final AnimatableGradientColorValue gradientColor;
    public final GradientType gradientType;
    public final boolean hidden;
    public final ShapeStroke.LineJoinType joinType;
    public final List lineDashPattern;
    public final float miterLimit;
    public final String name;
    public final AnimatableIntegerValue opacity;
    public final AnimatablePointValue startPoint;
    public final AnimatableFloatValue width;

    public GradientStroke(String str, GradientType gradientType, AnimatableGradientColorValue animatableGradientColorValue, AnimatableIntegerValue animatableIntegerValue, AnimatablePointValue animatablePointValue, AnimatablePointValue animatablePointValue2, AnimatableFloatValue animatableFloatValue, ShapeStroke.LineCapType lineCapType, ShapeStroke.LineJoinType lineJoinType, float f, List<AnimatableFloatValue> list, AnimatableFloatValue animatableFloatValue2, boolean z) {
        this.name = str;
        this.gradientType = gradientType;
        this.gradientColor = animatableGradientColorValue;
        this.opacity = animatableIntegerValue;
        this.startPoint = animatablePointValue;
        this.endPoint = animatablePointValue2;
        this.width = animatableFloatValue;
        this.capType = lineCapType;
        this.joinType = lineJoinType;
        this.miterLimit = f;
        this.lineDashPattern = list;
        this.dashOffset = animatableFloatValue2;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer) {
        return new GradientStrokeContent(lottieDrawable, baseLayer, this);
    }
}
