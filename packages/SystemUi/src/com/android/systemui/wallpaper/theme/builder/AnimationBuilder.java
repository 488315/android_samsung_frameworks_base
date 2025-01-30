package com.android.systemui.wallpaper.theme.builder;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AnimationBuilder {
    public ImageView imageView;

    /* renamed from: r */
    public float f407r = 0.0f;

    /* renamed from: a */
    public float f403a = 0.0f;

    /* renamed from: b */
    public float f404b = 0.0f;

    /* renamed from: ra */
    public float f408ra = 0.0f;

    /* renamed from: rb */
    public float f409rb = 0.0f;
    public float key = 0.0f;
    public float xOffSet = 0.0f;
    public float yOffSet = 0.0f;
    public float adjust = 0.0f;
    public int imageViewId = 0;
    public int length = 0;
    public int backgroundId = 0;
    public final ArrayList imageViewSetId = new ArrayList();

    /* renamed from: x */
    public final ArrayList f411x = new ArrayList();

    /* renamed from: y */
    public final ArrayList f412y = new ArrayList();
    public final ArrayList scale = new ArrayList();
    public final ArrayList startIndex = new ArrayList();
    public final ArrayList frameSize = new ArrayList();
    public int top = 0;
    public int minInterval = 0;
    public float from = 0.0f;

    /* renamed from: to */
    public float f410to = 0.0f;

    /* renamed from: dx */
    public float f405dx = 0.0f;

    /* renamed from: dy */
    public float f406dy = 0.0f;
    public long duration = 0;
    public long delay = 0;
    public int repeatCount = 0;
    public int repeatMode = 0;
    public long startTime = 0;
    public long elementDuration = 0;
    public int preSequence = 0;
    public boolean isAnimationStarted = false;
    public TimeInterpolator interpolator = new AccelerateDecelerateInterpolator();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ParabolaEvaluator implements TypeEvaluator {
        public final float key;

        /* renamed from: pX */
        public final float f413pX;

        /* renamed from: pY */
        public final float f414pY;

        public ParabolaEvaluator(float f, float f2, float f3) {
            this.key = f;
            this.f413pX = f2;
            this.f414pY = f3;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            float floatValue2 = ((((Number) obj2).floatValue() - floatValue) * f) + floatValue + this.f413pX;
            return Float.valueOf((this.key * floatValue2 * floatValue2) + this.f414pY);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ParabolaEvaluatorReverse implements TypeEvaluator {
        public final float key;

        /* renamed from: pX */
        public final float f415pX;

        /* renamed from: pY */
        public final float f416pY;

        public ParabolaEvaluatorReverse(float f, float f2, float f3) {
            this.key = f;
            this.f415pX = f2;
            this.f416pY = f3;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            float floatValue2 = ((Number) obj2).floatValue();
            float f2 = (floatValue2 - ((floatValue2 - floatValue) * f)) + this.f415pX;
            return Float.valueOf((this.key * f2 * f2) + this.f416pY);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SinXEvaluator implements TypeEvaluator {
        public final float adjust;
        public final float key;

        /* renamed from: pX */
        public final float f417pX;

        /* renamed from: pY */
        public final float f418pY;

        public SinXEvaluator(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.f417pX = f3;
            this.f418pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * (((((Number) obj2).floatValue() - floatValue) * f) + floatValue + this.f417pX)))) + this.f418pY);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SinXEvaluatorReverse implements TypeEvaluator {
        public final float adjust;
        public final float key;

        /* renamed from: pX */
        public final float f419pX;

        /* renamed from: pY */
        public final float f420pY;

        public SinXEvaluatorReverse(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.f419pX = f3;
            this.f420pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            float floatValue2 = ((Number) obj2).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * ((floatValue2 - ((floatValue2 - floatValue) * f)) + this.f419pX)))) + this.f420pY);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SinYEvaluator implements TypeEvaluator {
        public final float adjust;
        public final float key;

        /* renamed from: pX */
        public final float f421pX;

        /* renamed from: pY */
        public final float f422pY;

        public SinYEvaluator(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.f421pX = f3;
            this.f422pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * (((((Number) obj2).floatValue() - floatValue) * f) + floatValue + this.f421pX)))) + this.f422pY);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SinYEvaluatorReverse implements TypeEvaluator {
        public final float adjust;
        public final float key;

        /* renamed from: pX */
        public final float f423pX;

        /* renamed from: pY */
        public final float f424pY;

        public SinYEvaluatorReverse(float f, float f2, float f3, float f4) {
            this.key = f;
            this.adjust = f2;
            this.f423pX = f3;
            this.f424pY = f4;
        }

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            float floatValue = ((Number) obj).floatValue();
            float floatValue2 = ((Number) obj2).floatValue();
            return Float.valueOf((this.key * ((float) Math.sin(this.adjust * ((floatValue2 - ((floatValue2 - floatValue) * f)) + this.f423pX)))) + this.f424pY);
        }
    }
}
