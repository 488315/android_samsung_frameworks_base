package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RectangleContent implements BaseKeyframeAnimation.AnimationListener, KeyPathElementContent, PathContent {
    public final FloatKeyframeAnimation cornerRadiusAnimation;
    public final boolean hidden;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final BaseKeyframeAnimation positionAnimation;
    public final BaseKeyframeAnimation sizeAnimation;
    public final Path path = new Path();
    public final RectF rect = new RectF();
    public final CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();
    public BaseKeyframeAnimation roundedCornersAnimation = null;

    public RectangleContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, RectangleShape rectangleShape) {
        this.name = rectangleShape.name;
        this.hidden = rectangleShape.hidden;
        this.lottieDrawable = lottieDrawable;
        BaseKeyframeAnimation createAnimation = rectangleShape.position.createAnimation();
        this.positionAnimation = createAnimation;
        BaseKeyframeAnimation createAnimation2 = rectangleShape.size.createAnimation();
        this.sizeAnimation = createAnimation2;
        BaseKeyframeAnimation createAnimation3 = rectangleShape.cornerRadius.createAnimation();
        this.cornerRadiusAnimation = (FloatKeyframeAnimation) createAnimation3;
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        if (obj == LottieProperty.RECTANGLE_SIZE) {
            this.sizeAnimation.setValueCallback(lottieValueCallback);
        } else if (obj == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
        } else if (obj == LottieProperty.CORNER_RADIUS) {
            this.cornerRadiusAnimation.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        BaseKeyframeAnimation baseKeyframeAnimation;
        boolean z = this.isPathValid;
        Path path = this.path;
        if (z) {
            return path;
        }
        path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return path;
        }
        PointF pointF = (PointF) this.sizeAnimation.getValue();
        float f = pointF.x / 2.0f;
        float f2 = pointF.y / 2.0f;
        FloatKeyframeAnimation floatKeyframeAnimation = this.cornerRadiusAnimation;
        float floatValue = floatKeyframeAnimation == null ? 0.0f : floatKeyframeAnimation.getFloatValue();
        if (floatValue == 0.0f && (baseKeyframeAnimation = this.roundedCornersAnimation) != null) {
            floatValue = Math.min(((Float) baseKeyframeAnimation.getValue()).floatValue(), Math.min(f, f2));
        }
        float min = Math.min(f, f2);
        if (floatValue > min) {
            floatValue = min;
        }
        PointF pointF2 = (PointF) this.positionAnimation.getValue();
        path.moveTo(pointF2.x + f, (pointF2.y - f2) + floatValue);
        path.lineTo(pointF2.x + f, (pointF2.y + f2) - floatValue);
        RectF rectF = this.rect;
        if (floatValue > 0.0f) {
            float f3 = pointF2.x + f;
            float f4 = floatValue * 2.0f;
            float f5 = pointF2.y + f2;
            rectF.set(f3 - f4, f5 - f4, f3, f5);
            path.arcTo(rectF, 0.0f, 90.0f, false);
        }
        path.lineTo((pointF2.x - f) + floatValue, pointF2.y + f2);
        if (floatValue > 0.0f) {
            float f6 = pointF2.x - f;
            float f7 = pointF2.y + f2;
            float f8 = floatValue * 2.0f;
            rectF.set(f6, f7 - f8, f8 + f6, f7);
            path.arcTo(rectF, 90.0f, 90.0f, false);
        }
        path.lineTo(pointF2.x - f, (pointF2.y - f2) + floatValue);
        if (floatValue > 0.0f) {
            float f9 = pointF2.x - f;
            float f10 = pointF2.y - f2;
            float f11 = floatValue * 2.0f;
            rectF.set(f9, f10, f9 + f11, f11 + f10);
            path.arcTo(rectF, 180.0f, 90.0f, false);
        }
        path.lineTo((pointF2.x + f) - floatValue, pointF2.y - f2);
        if (floatValue > 0.0f) {
            float f12 = pointF2.x + f;
            float f13 = floatValue * 2.0f;
            float f14 = pointF2.y - f2;
            rectF.set(f12 - f13, f14, f12, f13 + f14);
            path.arcTo(rectF, 270.0f, 90.0f, false);
        }
        path.close();
        this.trimPaths.apply(path);
        this.isPathValid = true;
        return path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            Content content = (Content) arrayList.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.type == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    ((ArrayList) this.trimPaths.contents).add(trimPathContent);
                    trimPathContent.addListener(this);
                    i++;
                }
            }
            if (content instanceof RoundedCornersContent) {
                this.roundedCornersAnimation = ((RoundedCornersContent) content).roundedCorners;
            }
            i++;
        }
    }
}
