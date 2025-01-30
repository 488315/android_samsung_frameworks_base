package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContentGroup implements DrawingContent, PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElement {
    public final List contents;
    public final boolean hidden;
    public final LottieDrawable lottieDrawable;
    public final Matrix matrix;
    public final String name;
    public final LPaint offScreenPaint;
    public final Path path;
    public List pathContents;
    public final RectF rect;
    public final TransformKeyframeAnimation transformAnimation;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ContentGroup(LottieDrawable lottieDrawable, BaseLayer baseLayer, ShapeGroup shapeGroup, LottieComposition lottieComposition) {
        this(lottieDrawable, baseLayer, r3, r4, r5, r6);
        AnimatableTransform animatableTransform;
        String str = shapeGroup.name;
        boolean z = shapeGroup.hidden;
        List list = shapeGroup.items;
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Content content = ((ContentModel) list.get(i2)).toContent(lottieDrawable, lottieComposition, baseLayer);
            if (content != null) {
                arrayList.add(content);
            }
        }
        while (true) {
            if (i >= list.size()) {
                animatableTransform = null;
                break;
            }
            ContentModel contentModel = (ContentModel) list.get(i);
            if (contentModel instanceof AnimatableTransform) {
                animatableTransform = (AnimatableTransform) contentModel;
                break;
            }
            i++;
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            transformKeyframeAnimation.applyValueCallback(lottieValueCallback, obj);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void draw(Canvas canvas, Matrix matrix, int i) {
        if (this.hidden) {
            return;
        }
        Matrix matrix2 = this.matrix;
        matrix2.set(matrix);
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix2.preConcat(transformKeyframeAnimation.getMatrix());
            i = (int) (((((transformKeyframeAnimation.opacity == null ? 100 : ((Integer) r5.getValue()).intValue()) / 100.0f) * i) / 255.0f) * 255.0f);
        }
        this.lottieDrawable.getClass();
        List list = this.contents;
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            Object obj = list.get(size);
            if (obj instanceof DrawingContent) {
                ((DrawingContent) obj).draw(canvas, matrix2, i);
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        Matrix matrix2 = this.matrix;
        matrix2.set(matrix);
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix2.preConcat(transformKeyframeAnimation.getMatrix());
        }
        RectF rectF2 = this.rect;
        rectF2.set(0.0f, 0.0f, 0.0f, 0.0f);
        List list = this.contents;
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            Content content = (Content) list.get(size);
            if (content instanceof DrawingContent) {
                ((DrawingContent) content).getBounds(rectF2, matrix2, z);
                rectF.union(rectF2);
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        Matrix matrix = this.matrix;
        matrix.reset();
        TransformKeyframeAnimation transformKeyframeAnimation = this.transformAnimation;
        if (transformKeyframeAnimation != null) {
            matrix.set(transformKeyframeAnimation.getMatrix());
        }
        Path path = this.path;
        path.reset();
        if (this.hidden) {
            return path;
        }
        List list = this.contents;
        for (int size = list.size() - 1; size >= 0; size--) {
            Content content = (Content) list.get(size);
            if (content instanceof PathContent) {
                path.addPath(((PathContent) content).getPath(), matrix);
            }
        }
        return path;
    }

    public final List getPathList() {
        if (this.pathContents == null) {
            this.pathContents = new ArrayList();
            int i = 0;
            while (true) {
                List list = this.contents;
                if (i >= list.size()) {
                    break;
                }
                Content content = (Content) list.get(i);
                if (content instanceof PathContent) {
                    this.pathContents.add((PathContent) content);
                }
                i++;
            }
        }
        return this.pathContents;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2) {
        String str = this.name;
        if (!keyPath.matches(i, str) && !"__container".equals(str)) {
            return;
        }
        if (!"__container".equals(str)) {
            keyPath2 = keyPath2.addKey(str);
            if (keyPath.fullyResolvesTo(i, str)) {
                ((ArrayList) list).add(keyPath2.resolve(this));
            }
        }
        if (!keyPath.propagateToChildren(i, str)) {
            return;
        }
        int incrementDepthBy = keyPath.incrementDepthBy(i, str) + i;
        int i2 = 0;
        while (true) {
            List list2 = this.contents;
            if (i2 >= list2.size()) {
                return;
            }
            Content content = (Content) list2.get(i2);
            if (content instanceof KeyPathElement) {
                ((KeyPathElement) content).resolveKeyPath(keyPath, incrementDepthBy, list, keyPath2);
            }
            i2++;
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        int size = list.size();
        List list3 = this.contents;
        ArrayList arrayList = new ArrayList(list3.size() + size);
        arrayList.addAll(list);
        for (int size2 = list3.size() - 1; size2 >= 0; size2--) {
            Content content = (Content) list3.get(size2);
            content.setContents(arrayList, list3.subList(0, size2));
            arrayList.add(content);
        }
    }

    public ContentGroup(LottieDrawable lottieDrawable, BaseLayer baseLayer, String str, boolean z, List<Content> list, AnimatableTransform animatableTransform) {
        this.offScreenPaint = new LPaint();
        new RectF();
        this.matrix = new Matrix();
        this.path = new Path();
        this.rect = new RectF();
        this.name = str;
        this.lottieDrawable = lottieDrawable;
        this.hidden = z;
        this.contents = list;
        if (animatableTransform != null) {
            TransformKeyframeAnimation transformKeyframeAnimation = new TransformKeyframeAnimation(animatableTransform);
            this.transformAnimation = transformKeyframeAnimation;
            transformKeyframeAnimation.addAnimationsToLayer(baseLayer);
            transformKeyframeAnimation.addListener(this);
        }
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            Content content = list.get(size);
            if (content instanceof GreedyContent) {
                arrayList.add((GreedyContent) content);
            }
        }
        int size2 = arrayList.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                return;
            } else {
                ((GreedyContent) arrayList.get(size2)).absorbContent(list.listIterator(list.size()));
            }
        }
    }
}
