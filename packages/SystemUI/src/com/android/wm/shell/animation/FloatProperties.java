package com.android.wm.shell.animation;

import android.graphics.Rect;
import android.graphics.RectF;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FloatProperties {
    public static final FloatProperties$Companion$RECT_HEIGHT$1 RECT_HEIGHT;
    public static final FloatProperties$Companion$RECT_WIDTH$1 RECT_WIDTH;
    public static final FloatProperties$Companion$RECT_X$1 RECT_X;
    public static final FloatProperties$Companion$RECT_Y$1 RECT_Y;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.animation.FloatProperties$Companion$RECT_Y$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.wm.shell.animation.FloatProperties$Companion$RECT_HEIGHT$1] */
    static {
        new Companion(null);
        RECT_X = new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                if (((Rect) obj) != null) {
                    return r1.left;
                }
                return -3.4028235E38f;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                Rect rect = (Rect) obj;
                if (rect != null) {
                    rect.offsetTo((int) f, rect.top);
                }
            }
        };
        RECT_Y = new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECT_Y$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                if (((Rect) obj) != null) {
                    return r1.top;
                }
                return -3.4028235E38f;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                Rect rect = (Rect) obj;
                if (rect != null) {
                    rect.offsetTo(rect.left, (int) f);
                }
            }
        };
        RECT_WIDTH = new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((Rect) obj).width();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                Rect rect = (Rect) obj;
                rect.right = rect.left + ((int) f);
            }
        };
        RECT_HEIGHT = new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECT_HEIGHT$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                return ((Rect) obj).height();
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                Rect rect = (Rect) obj;
                rect.bottom = rect.top + ((int) f);
            }
        };
        new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECTF_X$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                RectF rectF = (RectF) obj;
                if (rectF != null) {
                    return rectF.left;
                }
                return -3.4028235E38f;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                RectF rectF = (RectF) obj;
                if (rectF != null) {
                    rectF.offsetTo(f, rectF.top);
                }
            }
        };
        new FloatPropertyCompat() { // from class: com.android.wm.shell.animation.FloatProperties$Companion$RECTF_Y$1
            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final float getValue(Object obj) {
                RectF rectF = (RectF) obj;
                if (rectF != null) {
                    return rectF.top;
                }
                return -3.4028235E38f;
            }

            @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
            public final void setValue(Object obj, float f) {
                RectF rectF = (RectF) obj;
                if (rectF != null) {
                    rectF.offsetTo(rectF.left, f);
                }
            }
        };
    }
}
