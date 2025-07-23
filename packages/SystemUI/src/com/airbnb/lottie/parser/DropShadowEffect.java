package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DropShadowEffect {
    public final AnimatableColorValue color;
    public final AnimatableFloatValue direction;
    public final AnimatableFloatValue distance;
    public final AnimatableFloatValue opacity;
    public final AnimatableFloatValue radius;

    public DropShadowEffect(AnimatableColorValue animatableColorValue, AnimatableFloatValue animatableFloatValue, AnimatableFloatValue animatableFloatValue2, AnimatableFloatValue animatableFloatValue3, AnimatableFloatValue animatableFloatValue4) {
        this.color = animatableColorValue;
        this.opacity = animatableFloatValue;
        this.direction = animatableFloatValue2;
        this.distance = animatableFloatValue3;
        this.radius = animatableFloatValue4;
    }
}
