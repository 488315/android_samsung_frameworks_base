package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ShapeKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ShapeContent implements PathContent, BaseKeyframeAnimation.AnimationListener {
    public final boolean hidden;
    public boolean isPathValid;
    public final LottieDrawable lottieDrawable;
    public final ShapeKeyframeAnimation shapeAnimation;
    public final Path path = new Path();
    public final CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    public ShapeContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, ShapePath shapePath) {
        shapePath.getClass();
        this.hidden = shapePath.hidden;
        this.lottieDrawable = lottieDrawable;
        ShapeKeyframeAnimation shapeKeyframeAnimation = new ShapeKeyframeAnimation(shapePath.shapePath.keyframes);
        this.shapeAnimation = shapeKeyframeAnimation;
        baseLayer.addAnimation(shapeKeyframeAnimation);
        shapeKeyframeAnimation.addUpdateListener(this);
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        Path path = (Path) this.shapeAnimation.getValue();
        if (path == null) {
            return this.path;
        }
        this.path.set(path);
        this.path.setFillType(Path.FillType.EVEN_ODD);
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        ArrayList arrayList = null;
        int i = 0;
        while (true) {
            ArrayList arrayList2 = (ArrayList) list;
            if (i >= arrayList2.size()) {
                this.shapeAnimation.shapeModifiers = arrayList;
                return;
            }
            Content content = (Content) arrayList2.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.type == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    ((ArrayList) this.trimPaths.contents).add(trimPathContent);
                    trimPathContent.addListener(this);
                    i++;
                }
            }
            if (content instanceof RoundedCornersContent) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add((RoundedCornersContent) content);
            }
            i++;
        }
    }
}
