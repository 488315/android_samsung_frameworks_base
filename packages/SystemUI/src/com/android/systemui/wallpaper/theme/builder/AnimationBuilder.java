package com.android.systemui.wallpaper.theme.builder;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AnimationBuilder {
    public ImageView imageView;
    public float r = 0.0f;
    public float a = 0.0f;
    public float b = 0.0f;
    public float ra = 0.0f;
    public float rb = 0.0f;
    public float key = 0.0f;
    public float xOffSet = 0.0f;
    public float yOffSet = 0.0f;
    public float adjust = 0.0f;
    public int imageViewId = 0;
    public int length = 0;
    public int backgroundId = 0;
    public final ArrayList imageViewSetId = new ArrayList();
    public final ArrayList x = new ArrayList();
    public final ArrayList y = new ArrayList();
    public final ArrayList scale = new ArrayList();
    public final ArrayList startIndex = new ArrayList();
    public final ArrayList frameSize = new ArrayList();
    public int top = 0;
    public int minInterval = 0;
    public float from = 0.0f;
    public float to = 0.0f;
    public float dx = 0.0f;
    public float dy = 0.0f;
    public long duration = 0;
    public long delay = 0;
    public int repeatCount = 0;
    public int repeatMode = 0;
    public long startTime = 0;
    public long elementDuration = 0;
    public int preSequence = 0;
    public boolean isAnimationStarted = false;
    public TimeInterpolator interpolator = new AccelerateDecelerateInterpolator();

    public class ParabolaEvaluator implements TypeEvaluator {
        public final float key;
        public final float pX;
        public final float pY;

        public ParabolaEvaluator(float f, float f2, float f3) {
            this.key = f;
            this.pX = f2;
            this.pY = f3;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            float fFloatValue2 = ((((Number) obj2).floatValue() - fFloatValue) * f) + fFloatValue + this.pX;
            return Float.valueOf((this.key * fFloatValue2 * fFloatValue2) + this.pY);
        }
    }

    public class ParabolaEvaluatorReverse implements TypeEvaluator {
        public final float key;
        public final float pX;
        public final float pY;

        public ParabolaEvaluatorReverse(float f, float f2, float f3) {
            this.key = f;
            this.pX = f2;
            this.pY = f3;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            float fFloatValue2 = ((Number) obj2).floatValue();
            float f2 = (fFloatValue2 - ((fFloatValue2 - fFloatValue) * f)) + this.pX;
            return Float.valueOf((this.key * f2 * f2) + this.pY);
        }
    }

    public class SinXEvaluator implements TypeEvaluator {
        public final float adjust;
        public final float key;
        public final float pX;
        public final float pY;

        public SinXEvaluator(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.pX = f3;
            this.pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * (((((Number) obj2).floatValue() - fFloatValue) * f) + fFloatValue + this.pX)))) + this.pY);
        }
    }

    public class SinXEvaluatorReverse implements TypeEvaluator {
        public final float adjust;
        public final float key;
        public final float pX;
        public final float pY;

        public SinXEvaluatorReverse(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.pX = f3;
            this.pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            float fFloatValue2 = ((Number) obj2).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * ((fFloatValue2 - ((fFloatValue2 - fFloatValue) * f)) + this.pX)))) + this.pY);
        }
    }

    public class SinYEvaluator implements TypeEvaluator {
        public final float adjust;
        public final float key;
        public final float pX;
        public final float pY;

        public SinYEvaluator(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.pX = f3;
            this.pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * (((((Number) obj2).floatValue() - fFloatValue) * f) + fFloatValue + this.pX)))) + this.pY);
        }
    }

    public class SinYEvaluatorReverse implements TypeEvaluator {
        public final float adjust;
        public final float key;
        public final float pX;
        public final float pY;

        public SinYEvaluatorReverse(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.pX = f3;
            this.pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float fFloatValue = ((Number) obj).floatValue();
            float fFloatValue2 = ((Number) obj2).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * ((fFloatValue2 - ((fFloatValue2 - fFloatValue) * f)) + this.pX)))) + this.pY);
        }
    }
}
