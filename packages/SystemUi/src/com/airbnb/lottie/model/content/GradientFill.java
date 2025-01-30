package com.airbnb.lottie.model.content;

import android.graphics.Path;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.GradientFillContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.layer.BaseLayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GradientFill implements ContentModel {
    public final AnimatablePointValue endPoint;
    public final Path.FillType fillType;
    public final AnimatableGradientColorValue gradientColor;
    public final GradientType gradientType;
    public final boolean hidden;
    public final String name;
    public final AnimatableIntegerValue opacity;
    public final AnimatablePointValue startPoint;

    public GradientFill(String str, GradientType gradientType, Path.FillType fillType, AnimatableGradientColorValue animatableGradientColorValue, AnimatableIntegerValue animatableIntegerValue, AnimatablePointValue animatablePointValue, AnimatablePointValue animatablePointValue2, AnimatableFloatValue animatableFloatValue, AnimatableFloatValue animatableFloatValue2, boolean z) {
        this.gradientType = gradientType;
        this.fillType = fillType;
        this.gradientColor = animatableGradientColorValue;
        this.opacity = animatableIntegerValue;
        this.startPoint = animatablePointValue;
        this.endPoint = animatablePointValue2;
        this.name = str;
        this.hidden = z;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer) {
        return new GradientFillContent(lottieDrawable, lottieComposition, baseLayer, this);
    }
}
