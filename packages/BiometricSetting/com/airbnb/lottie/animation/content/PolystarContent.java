package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    private final boolean hidden;
    private final FloatKeyframeAnimation innerRadiusAnimation;
    private final FloatKeyframeAnimation innerRoundednessAnimation;
    private boolean isPathValid;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final FloatKeyframeAnimation outerRadiusAnimation;
    private final FloatKeyframeAnimation outerRoundednessAnimation;
    private final FloatKeyframeAnimation pointsAnimation;
    private final BaseKeyframeAnimation<?, PointF> positionAnimation;
    private final FloatKeyframeAnimation rotationAnimation;
    private final PolystarShape.Type type;
    private final Path path = new Path();
    private CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.getName();
        PolystarShape.Type type = polystarShape.getType();
        this.type = type;
        this.hidden = polystarShape.isHidden();
        BaseKeyframeAnimation<Float, Float> createAnimation = polystarShape.getPoints().createAnimation();
        this.pointsAnimation = (FloatKeyframeAnimation) createAnimation;
        BaseKeyframeAnimation<PointF, PointF> createAnimation2 = polystarShape.getPosition().createAnimation();
        this.positionAnimation = createAnimation2;
        BaseKeyframeAnimation<Float, Float> createAnimation3 = polystarShape.getRotation().createAnimation();
        this.rotationAnimation = (FloatKeyframeAnimation) createAnimation3;
        BaseKeyframeAnimation<Float, Float> createAnimation4 = polystarShape.getOuterRadius().createAnimation();
        this.outerRadiusAnimation = (FloatKeyframeAnimation) createAnimation4;
        BaseKeyframeAnimation<Float, Float> createAnimation5 = polystarShape.getOuterRoundedness().createAnimation();
        this.outerRoundednessAnimation = (FloatKeyframeAnimation) createAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = (FloatKeyframeAnimation) polystarShape.getInnerRadius().createAnimation();
            this.innerRoundednessAnimation = (FloatKeyframeAnimation) polystarShape.getInnerRoundedness().createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(createAnimation);
        baseLayer.addAnimation(createAnimation2);
        baseLayer.addAnimation(createAnimation3);
        baseLayer.addAnimation(createAnimation4);
        baseLayer.addAnimation(createAnimation5);
        if (type == type2) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        createAnimation.addUpdateListener(this);
        createAnimation2.addUpdateListener(this);
        createAnimation3.addUpdateListener(this);
        createAnimation4.addUpdateListener(this);
        createAnimation5.addUpdateListener(this);
        if (type == type2) {
            this.innerRadiusAnimation.addUpdateListener(this);
            this.innerRoundednessAnimation.addUpdateListener(this);
        }
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        FloatKeyframeAnimation floatKeyframeAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation2;
        if (obj == LottieProperty.POLYSTAR_POINTS) {
            this.pointsAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_ROTATION) {
            this.rotationAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POSITION) {
            this.positionAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_INNER_RADIUS && (floatKeyframeAnimation2 = this.innerRadiusAnimation) != null) {
            floatKeyframeAnimation2.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_OUTER_RADIUS) {
            this.outerRadiusAnimation.setValueCallback(lottieValueCallback);
            return;
        }
        if (obj == LottieProperty.POLYSTAR_INNER_ROUNDEDNESS && (floatKeyframeAnimation = this.innerRoundednessAnimation) != null) {
            floatKeyframeAnimation.setValueCallback(lottieValueCallback);
        } else if (obj == LottieProperty.POLYSTAR_OUTER_ROUNDEDNESS) {
            this.outerRoundednessAnimation.setValueCallback(lottieValueCallback);
        }
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        float f;
        float cos;
        float f2;
        double d;
        float f3;
        PolystarContent polystarContent;
        Path path;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        Path path2;
        float f9;
        float f10;
        float f11;
        int i;
        BaseKeyframeAnimation<?, PointF> baseKeyframeAnimation;
        double d2;
        double d3;
        float f12;
        double d4;
        boolean z = this.isPathValid;
        Path path3 = this.path;
        if (z) {
            return path3;
        }
        path3.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return path3;
        }
        int ordinal = this.type.ordinal();
        BaseKeyframeAnimation<?, PointF> baseKeyframeAnimation2 = this.positionAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation = this.outerRoundednessAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.outerRadiusAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation3 = this.rotationAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation4 = this.pointsAnimation;
        if (ordinal != 0) {
            if (ordinal != 1) {
                polystarContent = this;
            } else {
                int floor = (int) Math.floor(floatKeyframeAnimation4.getValue().floatValue());
                double radians = Math.toRadians((floatKeyframeAnimation3 != null ? floatKeyframeAnimation3.getValue().floatValue() : 0.0d) - 90.0d);
                double d5 = floor;
                float floatValue = floatKeyframeAnimation.getValue().floatValue() / 100.0f;
                float floatValue2 = floatKeyframeAnimation2.getValue().floatValue();
                double d6 = floatValue2;
                float cos2 = (float) (Math.cos(radians) * d6);
                float sin = (float) (Math.sin(radians) * d6);
                path3.moveTo(cos2, sin);
                double d7 = (float) (6.283185307179586d / d5);
                double d8 = radians + d7;
                double ceil = Math.ceil(d5);
                int i2 = 0;
                double d9 = d7;
                while (i2 < ceil) {
                    float cos3 = (float) (Math.cos(d8) * d6);
                    float sin2 = (float) (Math.sin(d8) * d6);
                    if (floatValue != 0.0f) {
                        double d10 = d6;
                        i = i2;
                        double atan2 = (float) (Math.atan2(sin, cos2) - 1.5707963267948966d);
                        float cos4 = (float) Math.cos(atan2);
                        float sin3 = (float) Math.sin(atan2);
                        baseKeyframeAnimation = baseKeyframeAnimation2;
                        d2 = d8;
                        double atan22 = (float) (Math.atan2(sin2, cos3) - 1.5707963267948966d);
                        float f13 = floatValue2 * floatValue * 0.25f;
                        d3 = d9;
                        f12 = sin2;
                        d4 = d10;
                        path3.cubicTo(cos2 - (cos4 * f13), sin - (sin3 * f13), (((float) Math.cos(atan22)) * f13) + cos3, (f13 * ((float) Math.sin(atan22))) + sin2, cos3, f12);
                    } else {
                        i = i2;
                        baseKeyframeAnimation = baseKeyframeAnimation2;
                        d2 = d8;
                        d3 = d9;
                        f12 = sin2;
                        d4 = d6;
                        path3.lineTo(cos3, f12);
                    }
                    double d11 = d2 + d3;
                    sin = f12;
                    d6 = d4;
                    d9 = d3;
                    baseKeyframeAnimation2 = baseKeyframeAnimation;
                    d8 = d11;
                    cos2 = cos3;
                    i2 = i + 1;
                }
                PointF value = baseKeyframeAnimation2.getValue();
                path3.offset(value.x, value.y);
                path3.close();
                polystarContent = this;
            }
            path = path3;
        } else {
            BaseKeyframeAnimation<?, PointF> baseKeyframeAnimation3 = baseKeyframeAnimation2;
            float floatValue3 = floatKeyframeAnimation4.getValue().floatValue();
            double radians2 = Math.toRadians((floatKeyframeAnimation3 != null ? floatKeyframeAnimation3.getValue().floatValue() : 0.0d) - 90.0d);
            double d12 = floatValue3;
            float f14 = (float) (6.283185307179586d / d12);
            float f15 = f14 / 2.0f;
            float f16 = floatValue3 - ((int) floatValue3);
            if (f16 != 0.0f) {
                radians2 += (1.0f - f16) * f15;
            }
            float floatValue4 = floatKeyframeAnimation2.getValue().floatValue();
            float floatValue5 = this.innerRadiusAnimation.getValue().floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation5 = this.innerRoundednessAnimation;
            float floatValue6 = floatKeyframeAnimation5 != null ? floatKeyframeAnimation5.getValue().floatValue() / 100.0f : 0.0f;
            float floatValue7 = floatKeyframeAnimation != null ? floatKeyframeAnimation.getValue().floatValue() / 100.0f : 0.0f;
            if (f16 != 0.0f) {
                float m3m = RunGroup$$ExternalSyntheticOutline0.m3m(floatValue4, floatValue5, f16, floatValue5);
                double d13 = m3m;
                f = floatValue5;
                cos = (float) (Math.cos(radians2) * d13);
                float sin4 = (float) (d13 * Math.sin(radians2));
                path3.moveTo(cos, sin4);
                f2 = sin4;
                d = radians2 + ((f14 * f16) / 2.0f);
                f3 = m3m;
            } else {
                f = floatValue5;
                double d14 = floatValue4;
                cos = (float) (Math.cos(radians2) * d14);
                float sin5 = (float) (d14 * Math.sin(radians2));
                path3.moveTo(cos, sin5);
                f2 = sin5;
                d = radians2 + f15;
                f3 = 0.0f;
            }
            double ceil2 = Math.ceil(d12) * 2.0d;
            int i3 = 0;
            double d15 = d;
            boolean z2 = false;
            while (true) {
                double d16 = i3;
                if (d16 >= ceil2) {
                    break;
                }
                float f17 = z2 ? floatValue4 : f;
                if (f3 == 0.0f || d16 != ceil2 - 2.0d) {
                    f4 = f3;
                    f5 = f15;
                } else {
                    f4 = f3;
                    f5 = (f14 * f16) / 2.0f;
                }
                if (f3 == 0.0f || d16 != ceil2 - 1.0d) {
                    f6 = f14;
                    f7 = f15;
                } else {
                    f6 = f14;
                    f7 = f15;
                    f17 = f4;
                }
                double d17 = f17;
                BaseKeyframeAnimation<?, PointF> baseKeyframeAnimation4 = baseKeyframeAnimation3;
                float cos5 = (float) (Math.cos(d15) * d17);
                float sin6 = (float) (d17 * Math.sin(d15));
                if (floatValue6 == 0.0f && floatValue7 == 0.0f) {
                    path3.lineTo(cos5, sin6);
                    path2 = path3;
                    f11 = f5;
                    f8 = sin6;
                    f9 = f;
                    f10 = floatValue4;
                } else {
                    float f18 = floatValue4;
                    float f19 = f2;
                    double atan23 = (float) (Math.atan2(f2, cos) - 1.5707963267948966d);
                    float cos6 = (float) Math.cos(atan23);
                    float sin7 = (float) Math.sin(atan23);
                    float f20 = f5;
                    f8 = sin6;
                    path2 = path3;
                    double atan24 = (float) (Math.atan2(sin6, cos5) - 1.5707963267948966d);
                    float cos7 = (float) Math.cos(atan24);
                    float sin8 = (float) Math.sin(atan24);
                    float f21 = z2 ? floatValue6 : floatValue7;
                    float f22 = z2 ? floatValue7 : floatValue6;
                    float f23 = (z2 ? f : f18) * f21 * 0.47829f;
                    float f24 = cos6 * f23;
                    float f25 = f23 * sin7;
                    float f26 = (z2 ? f18 : f) * f22 * 0.47829f;
                    float f27 = cos7 * f26;
                    float f28 = f26 * sin8;
                    if (f16 != 0.0f) {
                        if (i3 == 0) {
                            f24 *= f16;
                            f25 *= f16;
                        } else if (d16 == ceil2 - 1.0d) {
                            f27 *= f16;
                            f28 *= f16;
                        }
                    }
                    f9 = f;
                    f10 = f18;
                    path2.cubicTo(cos - f24, f19 - f25, cos5 + f27, f8 + f28, cos5, f8);
                    f11 = f20;
                }
                d15 += f11;
                z2 = !z2;
                i3++;
                cos = cos5;
                f = f9;
                floatValue4 = f10;
                f3 = f4;
                f14 = f6;
                f15 = f7;
                baseKeyframeAnimation3 = baseKeyframeAnimation4;
                f2 = f8;
                path3 = path2;
            }
            polystarContent = this;
            PointF value2 = baseKeyframeAnimation3.getValue();
            path = path3;
            path.offset(value2.x, value2.y);
            path.close();
        }
        path.close();
        polystarContent.trimPaths.apply(path);
        polystarContent.isPathValid = true;
        return path;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener
    public final void onValueChanged() {
        this.isPathValid = false;
        this.lottieDrawable.invalidateSelf();
    }

    @Override // com.airbnb.lottie.model.KeyPathElement
    public final void resolveKeyPath(KeyPath keyPath, int i, List<KeyPath> list, KeyPath keyPath2) {
        MiscUtils.resolveKeyPath(keyPath, i, list, keyPath2, this);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            Content content = (Content) arrayList.get(i);
            if (content instanceof TrimPathContent) {
                TrimPathContent trimPathContent = (TrimPathContent) content;
                if (trimPathContent.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.trimPaths.addTrimPath(trimPathContent);
                    trimPathContent.addListener(this);
                }
            }
            i++;
        }
    }
}
