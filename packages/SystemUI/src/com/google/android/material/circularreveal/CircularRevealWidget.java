package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.google.android.material.math.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface CircularRevealWidget extends CircularRevealHelper.Delegate {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CircularRevealEvaluator implements TypeEvaluator {
        public static final CircularRevealEvaluator CIRCULAR_REVEAL = new CircularRevealEvaluator();
        public final RevealInfo revealInfo = new RevealInfo();

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            RevealInfo revealInfo = (RevealInfo) obj;
            RevealInfo revealInfo2 = (RevealInfo) obj2;
            RevealInfo revealInfo3 = this.revealInfo;
            float lerp = MathUtils.lerp(revealInfo.centerX, revealInfo2.centerX, f);
            float lerp2 = MathUtils.lerp(revealInfo.centerY, revealInfo2.centerY, f);
            float lerp3 = MathUtils.lerp(revealInfo.radius, revealInfo2.radius, f);
            revealInfo3.centerX = lerp;
            revealInfo3.centerY = lerp2;
            revealInfo3.radius = lerp3;
            return this.revealInfo;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CircularRevealProperty extends Property {
        public static final CircularRevealProperty CIRCULAR_REVEAL = new CircularRevealProperty("circularReveal");

        private CircularRevealProperty(String str) {
            super(RevealInfo.class, str);
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return ((CircularRevealWidget) obj).getRevealInfo();
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ((CircularRevealWidget) obj).setRevealInfo((RevealInfo) obj2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CircularRevealScrimColorProperty extends Property {
        public static final CircularRevealScrimColorProperty CIRCULAR_REVEAL_SCRIM_COLOR = new CircularRevealScrimColorProperty("circularRevealScrimColor");

        private CircularRevealScrimColorProperty(String str) {
            super(Integer.class, str);
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return Integer.valueOf(((CircularRevealWidget) obj).getCircularRevealScrimColor());
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ((CircularRevealWidget) obj).setCircularRevealScrimColor(((Integer) obj2).intValue());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RevealInfo {
        public float centerX;
        public float centerY;
        public float radius;

        private RevealInfo() {
        }

        public RevealInfo(float f, float f2, float f3) {
            this.centerX = f;
            this.centerY = f2;
            this.radius = f3;
        }

        public RevealInfo(RevealInfo revealInfo) {
            this(revealInfo.centerX, revealInfo.centerY, revealInfo.radius);
        }
    }

    void buildCircularRevealCache();

    void destroyCircularRevealCache();

    int getCircularRevealScrimColor();

    RevealInfo getRevealInfo();

    void setCircularRevealOverlayDrawable(Drawable drawable);

    void setCircularRevealScrimColor(int i);

    void setRevealInfo(RevealInfo revealInfo);
}
