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
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = polystarShape.points.createAnimation();
        this.pointsAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = polystarShape.position.createAnimation();
        this.positionAnimation = baseKeyframeAnimationCreateAnimation2;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = polystarShape.rotation.createAnimation();
        this.rotationAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation3;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = polystarShape.outerRadius.createAnimation();
        this.outerRadiusAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation4;
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation5 = polystarShape.outerRoundedness.createAnimation();
        this.outerRoundednessAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation5;
        PolystarShape.Type type2 = PolystarShape.Type.STAR;
        if (type == type2) {
            this.innerRadiusAnimation = (FloatKeyframeAnimation) polystarShape.innerRadius.createAnimation();
            this.innerRoundednessAnimation = (FloatKeyframeAnimation) polystarShape.innerRoundedness.createAnimation();
        } else {
            this.innerRadiusAnimation = null;
            this.innerRoundednessAnimation = null;
        }
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation2);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation3);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation4);
        baseLayer.addAnimation(baseKeyframeAnimationCreateAnimation5);
        if (type == type2) {
            baseLayer.addAnimation(this.innerRadiusAnimation);
            baseLayer.addAnimation(this.innerRoundednessAnimation);
        }
        baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        baseKeyframeAnimationCreateAnimation5.addUpdateListener(this);
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
        float fCos;
        float fSin;
        float f4;
        double d;
        float fM$1;
        float f5;
        double d2;
        float f6;
        BaseKeyframeAnimation baseKeyframeAnimation;
        double d3;
        float f7;
        float f8;
        int i;
        double d4;
        float f9;
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
            float fFloatValue = ((Float) floatKeyframeAnimation4.getValue()).floatValue();
            double radians = Math.toRadians((floatKeyframeAnimation3 != null ? ((Float) floatKeyframeAnimation3.getValue()).floatValue() : 0.0d) - 90.0d);
            double d5 = fFloatValue;
            float f10 = (float) (6.283185307179586d / d5);
            if (this.isReversed) {
                f10 *= -1.0f;
            }
            float f11 = f10 / 2.0f;
            float f12 = fFloatValue - ((int) fFloatValue);
            if (f12 != 0.0f) {
                radians += (1.0f - f12) * f11;
            }
            float fFloatValue2 = ((Float) floatKeyframeAnimation.getValue()).floatValue();
            float fFloatValue3 = ((Float) this.innerRadiusAnimation.getValue()).floatValue();
            FloatKeyframeAnimation floatKeyframeAnimation5 = this.innerRoundednessAnimation;
            float fFloatValue4 = floatKeyframeAnimation5 != null ? ((Float) floatKeyframeAnimation5.getValue()).floatValue() / 100.0f : 0.0f;
            float fFloatValue5 = floatKeyframeAnimation2 != null ? ((Float) floatKeyframeAnimation2.getValue()).floatValue() / 100.0f : 0.0f;
            if (f12 != 0.0f) {
                fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fFloatValue2, fFloatValue3, f12, fFloatValue3);
                f3 = 2.0f;
                double d6 = fM$1;
                f = f12;
                f2 = f10;
                float fCos2 = (float) (Math.cos(radians) * d6);
                fSin = (float) (d6 * Math.sin(radians));
                this.path.moveTo(fCos2, fSin);
                d = radians + ((f2 * f) / 2.0f);
                fCos = fCos2;
                f4 = f11;
            } else {
                f = f12;
                f2 = f10;
                f3 = 2.0f;
                double d7 = fFloatValue2;
                fCos = (float) (Math.cos(radians) * d7);
                fSin = (float) (Math.sin(radians) * d7);
                this.path.moveTo(fCos, fSin);
                f4 = f11;
                d = radians + f4;
                fM$1 = 0.0f;
            }
            double dCeil = Math.ceil(d5) * 2.0d;
            float f13 = fFloatValue5;
            int i3 = 0;
            boolean z = false;
            while (true) {
                double d8 = i3;
                if (d8 >= dCeil) {
                    break;
                }
                float f14 = z ? fFloatValue2 : fFloatValue3;
                if (fM$1 == 0.0f || d8 != dCeil - 2.0d) {
                    f5 = f4;
                } else {
                    f5 = f4;
                    f4 = (f2 * f) / f3;
                }
                if (fM$1 == 0.0f || d8 != dCeil - 1.0d) {
                    d2 = d8;
                    f6 = f14;
                } else {
                    d2 = d8;
                    f6 = fM$1;
                }
                double d9 = f6;
                float fCos3 = (float) (Math.cos(d) * d9);
                double d10 = d;
                float fSin2 = (float) (Math.sin(d) * d9);
                if (fFloatValue4 == 0.0f && f13 == 0.0f) {
                    this.path.lineTo(fCos3, fSin2);
                    f8 = fCos3;
                    f7 = fSin2;
                    baseKeyframeAnimation = baseKeyframeAnimation2;
                    d3 = dCeil;
                } else {
                    baseKeyframeAnimation = baseKeyframeAnimation2;
                    d3 = dCeil;
                    double dAtan2 = (float) (Math.atan2(fSin, fCos) - 1.5707963267948966d);
                    float fCos4 = (float) Math.cos(dAtan2);
                    float fSin3 = (float) Math.sin(dAtan2);
                    f7 = fSin2;
                    double dAtan22 = (float) (Math.atan2(fSin2, fCos3) - 1.5707963267948966d);
                    float fCos5 = (float) Math.cos(dAtan22);
                    float fSin4 = (float) Math.sin(dAtan22);
                    float f15 = z ? fFloatValue4 : f13;
                    float f16 = z ? f13 : fFloatValue4;
                    float f17 = (z ? fFloatValue3 : fFloatValue2) * f15 * 0.47829f;
                    float f18 = fCos4 * f17;
                    float f19 = f17 * fSin3;
                    float f20 = (z ? fFloatValue2 : fFloatValue3) * f16 * 0.47829f;
                    float f21 = fCos5 * f20;
                    float f22 = f20 * fSin4;
                    if (f12 != 0.0f) {
                        if (i3 == 0) {
                            f18 *= f;
                            f19 *= f;
                        } else if (d2 == d3 - 1.0d) {
                            f21 *= f;
                            f22 *= f;
                        }
                    }
                    f8 = fCos3;
                    this.path.cubicTo(fCos - f18, fSin - f19, fCos3 + f21, f7 + f22, f8, f7);
                }
                d = d10 + f4;
                z = !z;
                i3++;
                baseKeyframeAnimation2 = baseKeyframeAnimation;
                f4 = f5;
                dCeil = d3;
                fCos = f8;
                fSin = f7;
            }
            PointF pointF = (PointF) baseKeyframeAnimation2.getValue();
            this.path.offset(pointF.x, pointF.y);
            this.path.close();
        } else if (i2 == 2) {
            int iFloor = (int) Math.floor(((Float) floatKeyframeAnimation4.getValue()).floatValue());
            double radians2 = Math.toRadians((floatKeyframeAnimation3 != null ? ((Float) floatKeyframeAnimation3.getValue()).floatValue() : 0.0d) - 90.0d);
            double d11 = iFloor;
            float fFloatValue6 = ((Float) floatKeyframeAnimation2.getValue()).floatValue() / 100.0f;
            float fFloatValue7 = ((Float) floatKeyframeAnimation.getValue()).floatValue();
            double d12 = fFloatValue7;
            float fCos6 = (float) (Math.cos(radians2) * d12);
            float fSin5 = (float) (Math.sin(radians2) * d12);
            this.path.moveTo(fCos6, fSin5);
            double d13 = (float) (6.283185307179586d / d11);
            double d14 = radians2 + d13;
            double dCeil2 = Math.ceil(d11);
            int i4 = 0;
            while (i4 < dCeil2) {
                float fCos7 = (float) (Math.cos(d14) * d12);
                float fSin6 = (float) (Math.sin(d14) * d12);
                if (fFloatValue6 != 0.0f) {
                    d4 = d13;
                    i = i4;
                    f9 = fFloatValue6;
                    double dAtan23 = (float) (Math.atan2(fSin5, fCos6) - 1.5707963267948966d);
                    float fCos8 = (float) Math.cos(dAtan23);
                    float fSin7 = (float) Math.sin(dAtan23);
                    double dAtan24 = (float) (Math.atan2(fSin6, fCos7) - 1.5707963267948966d);
                    float f23 = fFloatValue7 * f9 * 0.25f;
                    this.path.cubicTo(fCos6 - (f23 * fCos8), fSin5 - (f23 * fSin7), fCos7 + (((float) Math.cos(dAtan24)) * f23), fSin6 + (f23 * ((float) Math.sin(dAtan24))), fCos7, fSin6);
                    fCos6 = fCos7;
                    fSin5 = fSin6;
                } else {
                    i = i4;
                    d4 = d13;
                    f9 = fFloatValue6;
                    fCos6 = fCos7;
                    fSin5 = fSin6;
                    this.path.lineTo(fCos6, fSin5);
                }
                d14 += d4;
                i4 = i + 1;
                fFloatValue6 = f9;
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
