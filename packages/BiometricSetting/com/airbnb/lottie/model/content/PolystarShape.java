package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.PolystarContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;

/* loaded from: classes.dex */
public final class PolystarShape implements ContentModel {
    private final boolean hidden;
    private final AnimatableFloatValue innerRadius;
    private final AnimatableFloatValue innerRoundedness;
    private final String name;
    private final AnimatableFloatValue outerRadius;
    private final AnimatableFloatValue outerRoundedness;
    private final AnimatableFloatValue points;
    private final AnimatableValue<PointF, PointF> position;
    private final AnimatableFloatValue rotation;
    private final Type type;

    public enum Type {
        STAR("STAR"),
        /* JADX INFO: Fake field, exist only in values array */
        EF16("POLYGON");

        private final int value;

        Type(String str) {
            this.value = r2;
        }

        public static Type forValue(int i) {
            for (Type type : values()) {
                if (type.value == i) {
                    return type;
                }
            }
            return null;
        }
    }

    public PolystarShape(String str, Type type, AnimatableFloatValue animatableFloatValue, AnimatableValue<PointF, PointF> animatableValue, AnimatableFloatValue animatableFloatValue2, AnimatableFloatValue animatableFloatValue3, AnimatableFloatValue animatableFloatValue4, AnimatableFloatValue animatableFloatValue5, AnimatableFloatValue animatableFloatValue6, boolean z) {
        this.name = str;
        this.type = type;
        this.points = animatableFloatValue;
        this.position = animatableValue;
        this.rotation = animatableFloatValue2;
        this.innerRadius = animatableFloatValue3;
        this.outerRadius = animatableFloatValue4;
        this.innerRoundedness = animatableFloatValue5;
        this.outerRoundedness = animatableFloatValue6;
        this.hidden = z;
    }

    public final AnimatableFloatValue getInnerRadius() {
        return this.innerRadius;
    }

    public final AnimatableFloatValue getInnerRoundedness() {
        return this.innerRoundedness;
    }

    public final String getName() {
        return this.name;
    }

    public final AnimatableFloatValue getOuterRadius() {
        return this.outerRadius;
    }

    public final AnimatableFloatValue getOuterRoundedness() {
        return this.outerRoundedness;
    }

    public final AnimatableFloatValue getPoints() {
        return this.points;
    }

    public final AnimatableValue<PointF, PointF> getPosition() {
        return this.position;
    }

    public final AnimatableFloatValue getRotation() {
        return this.rotation;
    }

    public final Type getType() {
        return this.type;
    }

    public final boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return new PolystarContent(lottieDrawable, baseLayer, this);
    }
}
