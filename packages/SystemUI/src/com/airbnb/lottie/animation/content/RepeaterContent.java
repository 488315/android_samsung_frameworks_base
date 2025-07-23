package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Repeater;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class RepeaterContent implements DrawingContent, PathContent, GreedyContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public ContentGroup contentGroup;
    public final FloatKeyframeAnimation copies;
    public final boolean hidden;
    public final BaseLayer layer;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final FloatKeyframeAnimation offset;
    public final TransformKeyframeAnimation transform;
    public final Matrix matrix = new Matrix();
    public final Path path = new Path();

    public RepeaterContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, Repeater repeater) {
        this.lottieDrawable = lottieDrawable;
        this.layer = baseLayer;
        this.name = repeater.name;
        this.hidden = repeater.hidden;
        BaseKeyframeAnimation createAnimation = repeater.copies.createAnimation();
        this.copies = (FloatKeyframeAnimation) createAnimation;
        baseLayer.addAnimation(createAnimation);
        createAnimation.addUpdateListener(this);
        BaseKeyframeAnimation createAnimation2 = repeater.offset.createAnimation();
        this.offset = (FloatKeyframeAnimation) createAnimation2;
        baseLayer.addAnimation(createAnimation2);
        createAnimation2.addUpdateListener(this);
        AnimatableTransform animatableTransform = repeater.transform;
        animatableTransform.getClass();
        TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
        this.transform = transformKeyframeAnimation;
        transformKeyframeAnimation.addAnimationsToLayer(baseLayer);
        transformKeyframeAnimation.addListener(this);
    }

    @Override // com.airbnb.lottie.animation.content.GreedyContent
    public final void absorbContent(ListIterator listIterator) {
        if (this.contentGroup != null) {
            return;
        }
        while (listIterator.hasPrevious() && listIterator.previous() != this) {
        }
        ArrayList arrayList = new ArrayList();
        while (listIterator.hasPrevious()) {
            arrayList.add((Content) listIterator.previous());
            listIterator.remove();
        }
        Collections.reverse(arrayList);
        this.contentGroup = new ContentGroup(this.lottieDrawable, this.layer, "Repeater", this.hidden, arrayList, null);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        if (this.transform.applyValueCallback(lottieValueCallback, obj)) {
            return;
        }
        if (obj == LottieProperty.REPEATER_COPIES) {
            this.copies.setValueCallback(lottieValueCallback);
        } else if (obj == LottieProperty.REPEATER_OFFSET) {
            this.offset.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        float floatValue = ((Float) this.copies.getValue()).floatValue();
        float floatValue2 = ((Float) this.offset.getValue()).floatValue();
        TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
        float floatValue3 = ((Float) transformKeyframeAnimation.startOpacity.getValue()).floatValue() / 100.0f;
        float floatValue4 = ((Float) transformKeyframeAnimation.endOpacity.getValue()).floatValue() / 100.0f;
        for (int i2 = ((int) floatValue) - 1; i2 >= 0; i2--) {
            this.matrix.set(matrix);
            float f = i2;
            this.matrix.preConcat(transformKeyframeAnimation.getMatrixForRepeater(f + floatValue2));
            this.contentGroup.draw(canvas, this.matrix, (int) (MiscUtils.lerp(floatValue3, floatValue4, f / floatValue) * i));
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        this.contentGroup.getBounds(rectF, matrix, z);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        Path path = this.contentGroup.getPath();
        this.path.reset();
        float floatValue = ((Float) this.copies.getValue()).floatValue();
        float floatValue2 = ((Float) this.offset.getValue()).floatValue();
        for (int i = ((int) floatValue) - 1; i >= 0; i--) {
            this.matrix.set(this.transform.getMatrixForRepeater(i + floatValue2));
            this.path.addPath(path, this.matrix);
        }
        return this.path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
        for (int i2 = 0; i2 < this.contentGroup.contents.size(); i2++) {
            Content content = (Content) this.contentGroup.contents.get(i2);
            if (content instanceof KeyPathElementContent) {
                MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, (KeyPathElementContent) content);
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        this.contentGroup.setContents(list, list2);
    }
}
