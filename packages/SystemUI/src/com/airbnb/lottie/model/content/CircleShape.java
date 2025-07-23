package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.EllipseContent;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CircleShape implements ContentModel {
    public final boolean hidden;
    public final boolean isReversed;
    public final String name;
    public final AnimatableValue position;
    public final AnimatablePointValue size;

    public CircleShape(String str, AnimatableValue animatableValue, AnimatablePointValue animatablePointValue, boolean z, boolean z2) {
        this.name = str;
        this.position = animatableValue;
        this.size = animatablePointValue;
        this.isReversed = z;
        this.hidden = z2;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, LottieComposition lottieComposition, BaseLayer baseLayer) {
        return new EllipseContent(lottieDrawable, baseLayer, this);
    }
}
