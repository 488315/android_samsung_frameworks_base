package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.StrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

/* loaded from: classes.dex */
public final class ShapeStroke implements ContentModel {
    private final LineCapType capType;
    private final AnimatableColorValue color;
    private final boolean hidden;
    private final LineJoinType joinType;
    private final List<AnimatableFloatValue> lineDashPattern;
    private final float miterLimit;
    private final String name;
    private final AnimatableFloatValue offset;
    private final AnimatableIntegerValue opacity;
    private final AnimatableFloatValue width;

    public enum LineCapType {
        /* JADX INFO: Fake field, exist only in values array */
        EF5,
        /* JADX INFO: Fake field, exist only in values array */
        EF13,
        /* JADX INFO: Fake field, exist only in values array */
        EF21;

        LineCapType() {
        }
    }

    public enum LineJoinType {
        /* JADX INFO: Fake field, exist only in values array */
        EF5,
        /* JADX INFO: Fake field, exist only in values array */
        EF13,
        /* JADX INFO: Fake field, exist only in values array */
        EF21;

        LineJoinType() {
        }
    }

    public ShapeStroke(String str, AnimatableFloatValue animatableFloatValue, List<AnimatableFloatValue> list, AnimatableColorValue animatableColorValue, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue2, LineCapType lineCapType, LineJoinType lineJoinType, float f, boolean z) {
        this.name = str;
        this.offset = animatableFloatValue;
        this.lineDashPattern = list;
        this.color = animatableColorValue;
        this.opacity = animatableIntegerValue;
        this.width = animatableFloatValue2;
        this.capType = lineCapType;
        this.joinType = lineJoinType;
        this.miterLimit = f;
        this.hidden = z;
    }

    public final LineCapType getCapType() {
        return this.capType;
    }

    public final AnimatableColorValue getColor() {
        return this.color;
    }

    public final AnimatableFloatValue getDashOffset() {
        return this.offset;
    }

    public final LineJoinType getJoinType() {
        return this.joinType;
    }

    public final List<AnimatableFloatValue> getLineDashPattern() {
        return this.lineDashPattern;
    }

    public final float getMiterLimit() {
        return this.miterLimit;
    }

    public final String getName() {
        return this.name;
    }

    public final AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public final AnimatableFloatValue getWidth() {
        return this.width;
    }

    public final boolean isHidden() {
        return this.hidden;
    }

    @Override // com.airbnb.lottie.model.content.ContentModel
    public final Content toContent(LottieDrawable lottieDrawable, BaseLayer baseLayer) {
        return new StrokeContent(lottieDrawable, baseLayer, this);
    }
}
