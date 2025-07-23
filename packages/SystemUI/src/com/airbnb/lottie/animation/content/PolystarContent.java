package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class PolystarContent implements PathContent, BaseKeyframeAnimation.AnimationListener, KeyPathElementContent {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.airbnb.lottie.animation.content.PolystarContent$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
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
        float f2;
        float f3;
        float cos;
        float sin;
        float f4;
        double d;
        float f5;
        float f6;
        double d2;
        float f7;
        BaseKeyframeAnimation baseKeyframeAnimation;
        double d3;
        float f8;
        float f9;
        int i;
        double d4;
        float f10;
        if (this.isPathValid) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.isPathValid = true;
            return this.path;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$content$PolystarShape$Type[this.type.ordinal()];
        FloatKeyframeAnimation floatKeyframeAnimation = this.outerRadiusAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.outerRoundednessAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation3 = this.rotationAnimation;
        FloatKeyframeAnimation floatKeyframeAnimation4 = this.pointsAnimation;
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.positionAnimation;
        if (i2 == 1) {
            float floatValue = ((Float) floatKeyframeAnimation4.getValue()).floatValue();
            double radians = Math.toRadians((floatKeyframeAnimation3 != null ? ((Float) floatKeyframeAnimation3.getValue()).floatValue() : 0.0d) - 90.0d);
            double d5 = floatValue;
            float f11 = (float) (6.283185307179586d / d5);
            if (this.isReversed) {
                f11 *= -1.0f;
            }
            float f12 = f11 / 2.0f;
            float f13 = floatValue - ((int) floatValue);
            if (f13 != 0.0f) {
                radians += (1.0f - f13) * f12;
            }
            float floatValue2 = ((Float) floatKeyframeAnimation.getValue()).floatValue();
            float floatValue3 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation5 = this.innerRoundednessAnimation;
            float floatValue4 = floatKeyframeAnimation5 != null ? ((Float) floatKeyframeAnimation5.getValue()).floatValue() / 100.0f : 0.0f;
            float floatValue5 = floatKeyframeAnimation2 != null ? ((Float) floatKeyframeAnimation2.getValue()).floatValue() / 100.0f : 0.0f;
            if (f13 != 0.0f) {
                f5 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(floatValue2, floatValue3, f13, floatValue3);
                f3 = 2.0f;
                double d6 = f5;
                f = f13;
                f2 = f11;
                float cos2 = (float) (Math.cos(radians) * d6);
                sin = (float) (d6 * Math.sin(radians));
                this.path.moveTo(cos2, sin);
                d = radians + ((f2 * f) / 2.0f);
                cos = cos2;
                f4 = f12;
            } else {
                f = f13;
                f2 = f11;
                f3 = 2.0f;
                double d7 = floatValue2;
                cos = (float) (Math.cos(radians) * d7);
                sin = (float) (Math.sin(radians) * d7);
                this.path.moveTo(cos, sin);
                f4 = f12;
                d = radians + f4;
                f5 = 0.0f;
            }
            double ceil = Math.ceil(d5) * 2.0d;
            float f14 = floatValue5;
            int i3 = 0;
            boolean z = false;
            while (true) {
                double d8 = i3;
                if (d8 >= ceil) {
                    break;
                }
                float f15 = z ? floatValue2 : floatValue3;
                if (f5 == 0.0f || d8 != ceil - 2.0d) {
                    f6 = f4;
                } else {
                    f6 = f4;
                    f4 = (f2 * f) / f3;
                }
                if (f5 == 0.0f || d8 != ceil - 1.0d) {
                    d2 = d8;
                    f7 = f15;
                } else {
                    d2 = d8;
                    f7 = f5;
                }
                double d9 = f7;
                float cos3 = (float) (Math.cos(d) * d9);
                double d10 = d;
                float sin2 = (float) (Math.sin(d) * d9);
                if (floatValue4 == 0.0f && f14 == 0.0f) {
                    this.path.lineTo(cos3, sin2);
                    f9 = cos3;
                    f8 = sin2;
                    baseKeyframeAnimation = baseKeyframeAnimation2;
                    d3 = ceil;
                } else {
                    baseKeyframeAnimation = baseKeyframeAnimation2;
                    d3 = ceil;
                    double atan2 = (float) (Math.atan2(sin, cos) - 1.5707963267948966d);
                    float cos4 = (float) Math.cos(atan2);
                    float sin3 = (float) Math.sin(atan2);
                    f8 = sin2;
                    double atan22 = (float) (Math.atan2(sin2, cos3) - 1.5707963267948966d);
                    float cos5 = (float) Math.cos(atan22);
                    float sin4 = (float) Math.sin(atan22);
                    float f16 = z ? floatValue4 : f14;
                    float f17 = z ? f14 : floatValue4;
                    float f18 = (z ? floatValue3 : floatValue2) * f16 * 0.47829f;
                    float f19 = cos4 * f18;
                    float f20 = f18 * sin3;
                    float f21 = (z ? floatValue2 : floatValue3) * f17 * 0.47829f;
                    float f22 = cos5 * f21;
                    float f23 = f21 * sin4;
                    if (f13 != 0.0f) {
                        if (i3 == 0) {
                            f19 *= f;
                            f20 *= f;
                        } else if (d2 == d3 - 1.0d) {
                            f22 *= f;
                            f23 *= f;
                        }
                    }
                    f9 = cos3;
                    this.path.cubicTo(cos - f19, sin - f20, cos3 + f22, f8 + f23, f9, f8);
                }
                d = d10 + f4;
                z = !z;
                i3++;
                baseKeyframeAnimation2 = baseKeyframeAnimation;
                f4 = f6;
                ceil = d3;
                cos = f9;
                sin = f8;
            }
            PointF pointF = (PointF) baseKeyframeAnimation2.getValue();
            this.path.offset(pointF.x, pointF.y);
            this.path.close();
        } else if (i2 == 2) {
            int floor = (int) Math.floor(((Float) floatKeyframeAnimation4.getValue()).floatValue());
            double radians2 = Math.toRadians((floatKeyframeAnimation3 != null ? ((Float) floatKeyframeAnimation3.getValue()).floatValue() : 0.0d) - 90.0d);
            double d11 = floor;
            float floatValue6 = ((Float) floatKeyframeAnimation2.getValue()).floatValue() / 100.0f;
            float floatValue7 = ((Float) floatKeyframeAnimation.getValue()).floatValue();
            double d12 = floatValue7;
            float cos6 = (float) (Math.cos(radians2) * d12);
            float sin5 = (float) (Math.sin(radians2) * d12);
            this.path.moveTo(cos6, sin5);
            double d13 = (float) (6.283185307179586d / d11);
            double d14 = radians2 + d13;
            double ceil2 = Math.ceil(d11);
            int i4 = 0;
            while (i4 < ceil2) {
                float cos7 = (float) (Math.cos(d14) * d12);
                float sin6 = (float) (Math.sin(d14) * d12);
                if (floatValue6 != 0.0f) {
                    d4 = d13;
                    i = i4;
                    f10 = floatValue6;
                    double atan23 = (float) (Math.atan2(sin5, cos6) - 1.5707963267948966d);
                    float cos8 = (float) Math.cos(atan23);
                    float sin7 = (float) Math.sin(atan23);
                    double atan24 = (float) (Math.atan2(sin6, cos7) - 1.5707963267948966d);
                    float f24 = floatValue7 * f10 * 0.25f;
                    this.path.cubicTo(cos6 - (f24 * cos8), sin5 - (f24 * sin7), cos7 + (((float) Math.cos(atan24)) * f24), sin6 + (f24 * ((float) Math.sin(atan24))), cos7, sin6);
                    cos6 = cos7;
                    sin5 = sin6;
                } else {
                    i = i4;
                    d4 = d13;
                    f10 = floatValue6;
                    cos6 = cos7;
                    sin5 = sin6;
                    this.path.lineTo(cos6, sin5);
                }
                d14 += d4;
                i4 = i + 1;
                floatValue6 = f10;
                d13 = d4;
            }
            PointF pointF2 = (PointF) baseKeyframeAnimation2.getValue();
            this.path.offset(pointF2.x, pointF2.y);
            this.path.close();
        }
        this.path.close();
        this.trimPaths.apply(this.path);
        this.isPathValid = true;
        return this.path;
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
