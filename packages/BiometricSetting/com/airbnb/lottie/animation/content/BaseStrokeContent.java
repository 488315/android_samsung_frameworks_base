package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import com.airbnb.lottie.C0179L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.LPaint;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.IntegerKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseStrokeContent implements BaseKeyframeAnimation.AnimationListener, KeyPathElementContent, DrawingContent {
    private ValueCallbackKeyframeAnimation colorFilterAnimation;
    private final List<BaseKeyframeAnimation<?, Float>> dashPatternAnimations;
    private final FloatKeyframeAnimation dashPatternOffsetAnimation;
    private final float[] dashPatternValues;
    protected final BaseLayer layer;
    private final LottieDrawable lottieDrawable;
    private final IntegerKeyframeAnimation opacityAnimation;
    final LPaint paint;
    private final FloatKeyframeAnimation widthAnimation;

    /* renamed from: pm */
    private final PathMeasure f19pm = new PathMeasure();
    private final Path path = new Path();
    private final Path trimPathPath = new Path();
    private final RectF rect = new RectF();
    private final List<PathGroup> pathGroups = new ArrayList();

    private static final class PathGroup {
        private final List<PathContent> paths = new ArrayList();
        private final TrimPathContent trimPath;

        PathGroup(TrimPathContent trimPathContent) {
            this.trimPath = trimPathContent;
        }
    }

    BaseStrokeContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, Paint.Cap cap, Paint.Join join, float f, AnimatableIntegerValue animatableIntegerValue, AnimatableFloatValue animatableFloatValue, List<AnimatableFloatValue> list, AnimatableFloatValue animatableFloatValue2) {
        LPaint lPaint = new LPaint(1);
        this.paint = lPaint;
        this.lottieDrawable = lottieDrawable;
        this.layer = baseLayer;
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeCap(cap);
        lPaint.setStrokeJoin(join);
        lPaint.setStrokeMiter(f);
        this.opacityAnimation = (IntegerKeyframeAnimation) animatableIntegerValue.createAnimation();
        this.widthAnimation = (FloatKeyframeAnimation) animatableFloatValue.createAnimation();
        if (animatableFloatValue2 == null) {
            this.dashPatternOffsetAnimation = null;
        } else {
            this.dashPatternOffsetAnimation = (FloatKeyframeAnimation) animatableFloatValue2.createAnimation();
        }
        this.dashPatternAnimations = new ArrayList(list.size());
        this.dashPatternValues = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ((ArrayList) this.dashPatternAnimations).add(list.get(i).createAnimation());
        }
        baseLayer.addAnimation(this.opacityAnimation);
        baseLayer.addAnimation(this.widthAnimation);
        for (int i2 = 0; i2 < ((ArrayList) this.dashPatternAnimations).size(); i2++) {
            baseLayer.addAnimation((BaseKeyframeAnimation) ((ArrayList) this.dashPatternAnimations).get(i2));
        }
        FloatKeyframeAnimation floatKeyframeAnimation = this.dashPatternOffsetAnimation;
        if (floatKeyframeAnimation != null) {
            baseLayer.addAnimation(floatKeyframeAnimation);
        }
        this.opacityAnimation.addUpdateListener(this);
        this.widthAnimation.addUpdateListener(this);
        for (int i3 = 0; i3 < list.size(); i3++) {
            ((BaseKeyframeAnimation) ((ArrayList) this.dashPatternAnimations).get(i3)).addUpdateListener(this);
        }
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.dashPatternOffsetAnimation;
        if (floatKeyframeAnimation2 != null) {
            floatKeyframeAnimation2.addUpdateListener(this);
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        if (obj == LottieProperty.OPACITY) {
            this.opacityAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.STROKE_WIDTH) {
            this.widthAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.COLOR_FILTER) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorFilterAnimation;
            BaseLayer baseLayer = this.layer;
            if (valueCallbackKeyframeAnimation != null) {
                baseLayer.removeAnimation(valueCallbackKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorFilterAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.colorFilterAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            baseLayer.addAnimation(this.colorFilterAnimation);
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public void draw(Canvas canvas, Matrix matrix, int i) {
        float[] fArr;
        float f;
        float f2;
        BaseStrokeContent baseStrokeContent = this;
        if (Utils.hasZeroScaleAxis(matrix)) {
            C0179L.endSection();
            return;
        }
        float f3 = 100.0f;
        int i2 = MiscUtils.$r8$clinit;
        boolean z = false;
        int max = Math.max(0, Math.min(255, (int) ((((i / 255.0f) * baseStrokeContent.opacityAnimation.getIntValue()) / 100.0f) * 255.0f)));
        LPaint lPaint = baseStrokeContent.paint;
        lPaint.setAlpha(max);
        lPaint.setStrokeWidth(Utils.getScale(matrix) * baseStrokeContent.widthAnimation.getFloatValue());
        float f4 = 0.0f;
        if (lPaint.getStrokeWidth() <= 0.0f) {
            C0179L.endSection();
            return;
        }
        ArrayList arrayList = (ArrayList) baseStrokeContent.dashPatternAnimations;
        float f5 = 1.0f;
        if (arrayList.isEmpty()) {
            C0179L.endSection();
        } else {
            float scale = Utils.getScale(matrix);
            int i3 = 0;
            while (true) {
                int size = arrayList.size();
                fArr = baseStrokeContent.dashPatternValues;
                if (i3 >= size) {
                    break;
                }
                float floatValue = ((Float) ((BaseKeyframeAnimation) arrayList.get(i3)).getValue()).floatValue();
                fArr[i3] = floatValue;
                if (i3 % 2 == 0) {
                    if (floatValue < 1.0f) {
                        fArr[i3] = 1.0f;
                    }
                } else if (floatValue < 0.1f) {
                    fArr[i3] = 0.1f;
                }
                fArr[i3] = fArr[i3] * scale;
                i3++;
            }
            FloatKeyframeAnimation floatKeyframeAnimation = baseStrokeContent.dashPatternOffsetAnimation;
            lPaint.setPathEffect(new DashPathEffect(fArr, floatKeyframeAnimation == null ? 0.0f : floatKeyframeAnimation.getValue().floatValue() * scale));
            C0179L.endSection();
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = baseStrokeContent.colorFilterAnimation;
        if (valueCallbackKeyframeAnimation != null) {
            lPaint.setColorFilter((ColorFilter) valueCallbackKeyframeAnimation.getValue());
        }
        int i4 = 0;
        while (true) {
            ArrayList arrayList2 = (ArrayList) baseStrokeContent.pathGroups;
            if (i4 >= arrayList2.size()) {
                C0179L.endSection();
                return;
            }
            PathGroup pathGroup = (PathGroup) arrayList2.get(i4);
            TrimPathContent trimPathContent = pathGroup.trimPath;
            Path path = baseStrokeContent.path;
            if (trimPathContent == null) {
                f = f4;
                path.reset();
                int size2 = ((ArrayList) pathGroup.paths).size();
                while (true) {
                    size2--;
                    if (size2 < 0) {
                        break;
                    } else {
                        path.addPath(((PathContent) ((ArrayList) pathGroup.paths).get(size2)).getPath(), matrix);
                    }
                }
                C0179L.endSection();
                canvas.drawPath(path, lPaint);
                C0179L.endSection();
            } else if (pathGroup.trimPath == null) {
                C0179L.endSection();
                f = f4;
            } else {
                path.reset();
                int size3 = ((ArrayList) pathGroup.paths).size();
                while (true) {
                    size3--;
                    if (size3 < 0) {
                        break;
                    } else {
                        path.addPath(((PathContent) ((ArrayList) pathGroup.paths).get(size3)).getPath(), matrix);
                    }
                }
                PathMeasure pathMeasure = baseStrokeContent.f19pm;
                pathMeasure.setPath(path, z);
                float length = pathMeasure.getLength();
                while (pathMeasure.nextContour()) {
                    length += pathMeasure.getLength();
                }
                float floatValue2 = (pathGroup.trimPath.getOffset().getValue().floatValue() * length) / 360.0f;
                float floatValue3 = ((pathGroup.trimPath.getStart().getValue().floatValue() * length) / f3) + floatValue2;
                float floatValue4 = ((pathGroup.trimPath.getEnd().getValue().floatValue() * length) / f3) + floatValue2;
                int size4 = ((ArrayList) pathGroup.paths).size() - 1;
                float f6 = f4;
                while (size4 >= 0) {
                    Path path2 = baseStrokeContent.trimPathPath;
                    path2.set(((PathContent) ((ArrayList) pathGroup.paths).get(size4)).getPath());
                    path2.transform(matrix);
                    pathMeasure.setPath(path2, z);
                    float length2 = pathMeasure.getLength();
                    if (floatValue4 > length) {
                        float f7 = floatValue4 - length;
                        if (f7 < f6 + length2 && f6 < f7) {
                            Utils.applyTrimPathIfNeeded(path2, floatValue3 > length ? (floatValue3 - length) / length2 : 0.0f, Math.min(f7 / length2, f5), 0.0f);
                            canvas.drawPath(path2, lPaint);
                            f2 = 0.0f;
                            f6 += length2;
                            size4--;
                            baseStrokeContent = this;
                            f4 = f2;
                            z = false;
                            f5 = 1.0f;
                        }
                    }
                    float f8 = f6 + length2;
                    if (f8 >= floatValue3 && f6 <= floatValue4) {
                        if (f8 > floatValue4 || floatValue3 >= f6) {
                            f2 = 0.0f;
                            Utils.applyTrimPathIfNeeded(path2, floatValue3 < f6 ? 0.0f : (floatValue3 - f6) / length2, floatValue4 > f8 ? 1.0f : (floatValue4 - f6) / length2, 0.0f);
                            canvas.drawPath(path2, lPaint);
                            f6 += length2;
                            size4--;
                            baseStrokeContent = this;
                            f4 = f2;
                            z = false;
                            f5 = 1.0f;
                        } else {
                            canvas.drawPath(path2, lPaint);
                        }
                    }
                    f2 = 0.0f;
                    f6 += length2;
                    size4--;
                    baseStrokeContent = this;
                    f4 = f2;
                    z = false;
                    f5 = 1.0f;
                }
                f = f4;
                C0179L.endSection();
            }
            i4++;
            baseStrokeContent = this;
            f4 = f;
            z = false;
            f3 = 100.0f;
            f5 = 1.0f;
        }
    }

    @Override // com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        Path path = this.path;
        path.reset();
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.pathGroups;
            if (i >= arrayList.size()) {
                RectF rectF2 = this.rect;
                path.computeBounds(rectF2, false);
                float floatValue = this.widthAnimation.getFloatValue() / 2.0f;
                rectF2.set(rectF2.left - floatValue, rectF2.top - floatValue, rectF2.right + floatValue, rectF2.bottom + floatValue);
                rectF.set(rectF2);
                rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
                C0179L.endSection();
                return;
            }
            PathGroup pathGroup = (PathGroup) arrayList.get(i);
            for (int i2 = 0; i2 < ((ArrayList) pathGroup.paths).size(); i2++) {
                path.addPath(((PathContent) ((ArrayList) pathGroup.paths).get(i2)).getPath(), matrix);
            }
            i++;
        }
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
        ShapeTrimPath.Type type;
        List<PathGroup> list3;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size() - 1;
        PathGroup pathGroup = null;
        TrimPathContent trimPathContent = null;
        while (true) {
            type = ShapeTrimPath.Type.INDIVIDUALLY;
            if (size < 0) {
                break;
            }
            Content content = (Content) arrayList.get(size);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent2 = (TrimPathContent) content;
                if (trimPathContent2.getType() == type) {
                    trimPathContent = trimPathContent2;
                }
            }
            size--;
        }
        if (trimPathContent != null) {
            trimPathContent.addListener(this);
        }
        int size2 = list2.size();
        while (true) {
            size2--;
            list3 = this.pathGroups;
            if (size2 < 0) {
                break;
            }
            Content content2 = list2.get(size2);
            if (content2 instanceof TrimPathContent) {
                TrimPathContent trimPathContent3 = (TrimPathContent) content2;
                if (trimPathContent3.getType() == type) {
                    if (pathGroup != null) {
                        ((ArrayList) list3).add(pathGroup);
                    }
                    PathGroup pathGroup2 = new PathGroup(trimPathContent3);
                    trimPathContent3.addListener(this);
                    pathGroup = pathGroup2;
                }
            }
            if (content2 instanceof PathContent) {
                if (pathGroup == null) {
                    pathGroup = new PathGroup(trimPathContent);
                }
                ((ArrayList) pathGroup.paths).add((PathContent) content2);
            }
        }
        if (pathGroup != null) {
            ((ArrayList) list3).add(pathGroup);
        }
    }
}
