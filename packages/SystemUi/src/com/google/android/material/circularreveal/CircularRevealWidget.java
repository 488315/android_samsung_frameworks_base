package com.google.android.material.circularreveal;

import android.animation.TypeEvaluator;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.google.android.material.circularreveal.CircularRevealHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CircularRevealWidget extends CircularRevealHelper.Delegate {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CircularRevealEvaluator implements TypeEvaluator {
        public static final CircularRevealEvaluator CIRCULAR_REVEAL = new CircularRevealEvaluator();
        public final RevealInfo revealInfo = new RevealInfo();

        @Override // android.animation.TypeEvaluator
        public final Object evaluate(float f, Object obj, Object obj2) {
            RevealInfo revealInfo = (RevealInfo) obj;
            RevealInfo revealInfo2 = (RevealInfo) obj2;
            RevealInfo revealInfo3 = this.revealInfo;
            float f2 = revealInfo.centerX;
            float f3 = 1.0f - f;
            float f4 = (revealInfo2.centerX * f) + (f2 * f3);
            float f5 = revealInfo.centerY;
            float f6 = (revealInfo2.centerY * f) + (f5 * f3);
            float f7 = revealInfo.radius;
            float f8 = f * revealInfo2.radius;
            revealInfo3.centerX = f4;
            revealInfo3.centerY = f6;
            revealInfo3.radius = f8 + (f3 * f7);
            return revealInfo3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CircularRevealProperty extends Property {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CircularRevealScrimColorProperty extends Property {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RevealInfo {
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
