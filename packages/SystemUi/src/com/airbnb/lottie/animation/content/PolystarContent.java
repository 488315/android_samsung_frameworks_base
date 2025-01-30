package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
    public final boolean hidden;
    public final FloatKeyframeAnimation innerRadiusAnimation;
    public final FloatKeyframeAnimation innerRoundednessAnimation;
    public boolean isPathValid;
    public final boolean isReversed;
    public final LottieDrawable lottieDrawable;
    public final String name;
    public final FloatKeyframeAnimation outerRadiusAnimation;
    public final FloatKeyframeAnimation outerRoundednessAnimation;
    public final FloatKeyframeAnimation pointsAnimation;
    public final BaseKeyframeAnimation positionAnimation;
    public final FloatKeyframeAnimation rotationAnimation;
    public final PolystarShape.Type type;
    public final Path path = new Path();
    public final CompoundTrimPathContent trimPaths = new CompoundTrimPathContent();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.animation.content.PolystarContent$1 */
    public abstract /* synthetic */ class AbstractC05931 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type;

        static {
            int[] iArr = new int[PolystarShape.Type.values().length];
            $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type = iArr;
            try {
                iArr[PolystarShape.Type.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[PolystarShape.Type.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public PolystarContent(LottieDrawable lottieDrawable, BaseLayer baseLayer, PolystarShape polystarShape) {
        this.lottieDrawable = lottieDrawable;
        this.name = polystarShape.name;
        PolystarShape.Type type = polystarShape.type;
        this.type = type;
        this.hidden = polystarShape.hidden;
        this.isReversed = polystarShape.isReversed;
        BaseKeyframeAnimation createAnimation = polystarShape.points.createAnimation();
        this.pointsAnimation = (FloatKeyframeAnimation) createAnimation;
        BaseKeyframeAnimation createAnimation2 = polystarShape.position.createAnimation();
        this.positionAnimation = createAnimation2;
        BaseKeyframeAnimation createAnimation3 = polystarShape.rotation.createAnimation();
        this.rotationAnimation = (FloatKeyframeAnimation) createAnimation3;
        BaseKeyframeAnimation createAnimation4 = polystarShape.outerRadius.createAnimation();
        this.outerRadiusAnimation = (FloatKeyframeAnimation) createAnimation4;
        BaseKeyframeAnimation createAnimation5 = polystarShape.outerRoundedness.createAnimation();
        this.outerRoundednessAnimation = (FloatKeyframeAnimation) createAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = (FloatKeyframeAnimation) polystarShape.innerRadius.createAnimation();
            this.innerRoundednessAnimation = (FloatKeyframeAnimation) polystarShape.innerRoundedness.createAnimation();
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
        Path path;
        float f4;
        float f5;
        float f6;
        float f7;
        Path path2;
        float f8;
        float f9;
        float f10;
        float f11;
        int i;
        BaseKeyframeAnimation baseKeyframeAnimation;
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
        int i2 = AbstractC05931.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.positionAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation = this.outerRoundednessAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.outerRadiusAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation3 = this.rotationAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation4 = this.pointsAnimation;
        if (i2 != 1) {
            if (i2 == 2) {
                int floor = (int) Math.floor(((Float) floatKeyframeAnimation4.getValue()).floatValue());
                double radians = Math.toRadians((floatKeyframeAnimation3 == null ? 0.0d : ((Float) floatKeyframeAnimation3.getValue()).floatValue()) - 90.0d);
                double d5 = floor;
                float floatValue = ((Float) floatKeyframeAnimation.getValue()).floatValue() / 100.0f;
                float floatValue2 = ((Float) floatKeyframeAnimation2.getValue()).floatValue();
                double d6 = floatValue2;
                float cos2 = (float) (Math.cos(radians) * d6);
                float sin = (float) (Math.sin(radians) * d6);
                path3.moveTo(cos2, sin);
                double d7 = (float) (6.283185307179586d / d5);
                double d8 = radians + d7;
                double ceil = Math.ceil(d5);
                int i3 = 0;
                double d9 = d7;
                while (i3 < ceil) {
                    float cos3 = (float) (Math.cos(d8) * d6);
                    float sin2 = (float) (Math.sin(d8) * d6);
                    if (floatValue != 0.0f) {
                        double d10 = d6;
                        i = i3;
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
                        i = i3;
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
                    i3 = i + 1;
                }
                PointF pointF = (PointF) baseKeyframeAnimation2.getValue();
                path3.offset(pointF.x, pointF.y);
                path3.close();
            }
            path = path3;
        } else {
            BaseKeyframeAnimation baseKeyframeAnimation3 = baseKeyframeAnimation2;
            float floatValue3 = ((Float) floatKeyframeAnimation4.getValue()).floatValue();
            double radians2 = Math.toRadians((floatKeyframeAnimation3 == null ? 0.0d : ((Float) floatKeyframeAnimation3.getValue()).floatValue()) - 90.0d);
            double d12 = floatValue3;
            float f14 = (float) (6.283185307179586d / d12);
            if (this.isReversed) {
                f14 *= -1.0f;
            }
            float f15 = f14;
            float f16 = f15 / 2.0f;
            float f17 = floatValue3 - ((int) floatValue3);
            if (f17 != 0.0f) {
                radians2 += (1.0f - f17) * f16;
            }
            float floatValue4 = ((Float) floatKeyframeAnimation2.getValue()).floatValue();
            float floatValue5 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation5 = this.innerRoundednessAnimation;
            float floatValue6 = floatKeyframeAnimation5 != null ? ((Float) floatKeyframeAnimation5.getValue()).floatValue() / 100.0f : 0.0f;
            float floatValue7 = floatKeyframeAnimation != null ? ((Float) floatKeyframeAnimation.getValue()).floatValue() / 100.0f : 0.0f;
            if (f17 != 0.0f) {
                float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(floatValue4, floatValue5, f17, floatValue5);
                double d13 = m20m;
                f = floatValue5;
                cos = (float) (Math.cos(radians2) * d13);
                float sin4 = (float) (d13 * Math.sin(radians2));
                path3.moveTo(cos, sin4);
                f2 = sin4;
                d = radians2 + ((f15 * f17) / 2.0f);
                f3 = m20m;
            } else {
                f = floatValue5;
                double d14 = floatValue4;
                cos = (float) (Math.cos(radians2) * d14);
                float sin5 = (float) (d14 * Math.sin(radians2));
                path3.moveTo(cos, sin5);
                f2 = sin5;
                d = radians2 + f16;
                f3 = 0.0f;
            }
            double ceil2 = Math.ceil(d12) * 2.0d;
            int i4 = 0;
            double d15 = 2.0d;
            double d16 = d;
            boolean z2 = false;
            float f18 = floatValue4;
            while (true) {
                double d17 = i4;
                if (d17 >= ceil2) {
                    break;
                }
                float f19 = z2 ? f18 : f;
                if (f3 == 0.0f || d17 != ceil2 - d15) {
                    f4 = f3;
                    f5 = f16;
                } else {
                    f4 = f3;
                    f5 = (f15 * f17) / 2.0f;
                }
                if (f3 == 0.0f || d17 != ceil2 - 1.0d) {
                    f6 = f15;
                } else {
                    f6 = f15;
                    f19 = f4;
                }
                double d18 = f19;
                BaseKeyframeAnimation baseKeyframeAnimation4 = baseKeyframeAnimation3;
                float cos5 = (float) (Math.cos(d16) * d18);
                float sin6 = (float) (d18 * Math.sin(d16));
                if (floatValue6 == 0.0f && floatValue7 == 0.0f) {
                    path3.lineTo(cos5, sin6);
                    f10 = f16;
                    path2 = path3;
                    f11 = f5;
                    f7 = sin6;
                    f9 = f18;
                    f8 = f;
                } else {
                    float f20 = f16;
                    float f21 = f2;
                    double atan23 = (float) (Math.atan2(f2, cos) - 1.5707963267948966d);
                    float cos6 = (float) Math.cos(atan23);
                    float sin7 = (float) Math.sin(atan23);
                    float f22 = f5;
                    f7 = sin6;
                    path2 = path3;
                    double atan24 = (float) (Math.atan2(sin6, cos5) - 1.5707963267948966d);
                    float cos7 = (float) Math.cos(atan24);
                    float sin8 = (float) Math.sin(atan24);
                    float f23 = z2 ? floatValue6 : floatValue7;
                    float f24 = z2 ? floatValue7 : floatValue6;
                    float f25 = (z2 ? f : f18) * f23 * 0.47829f;
                    float f26 = cos6 * f25;
                    float f27 = f25 * sin7;
                    float f28 = (z2 ? f18 : f) * f24 * 0.47829f;
                    float f29 = cos7 * f28;
                    float f30 = f28 * sin8;
                    if (f17 != 0.0f) {
                        if (i4 == 0) {
                            f26 *= f17;
                            f27 *= f17;
                        } else if (d17 == ceil2 - 1.0d) {
                            f29 *= f17;
                            f30 *= f17;
                        }
                    }
                    f8 = f;
                    f9 = f18;
                    f10 = f20;
                    path2.cubicTo(cos - f26, f21 - f27, cos5 + f29, f7 + f30, cos5, f7);
                    f11 = f22;
                }
                d16 += f11;
                z2 = !z2;
                i4++;
                cos = cos5;
                f = f8;
                f18 = f9;
                f16 = f10;
                f3 = f4;
                f15 = f6;
                baseKeyframeAnimation3 = baseKeyframeAnimation4;
                path3 = path2;
                d15 = 2.0d;
                f2 = f7;
            }
            PointF pointF2 = (PointF) baseKeyframeAnimation3.getValue();
            path = path3;
            path.offset(pointF2.x, pointF2.y);
            path.close();
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
                }
            }
            i++;
        }
    }
}
