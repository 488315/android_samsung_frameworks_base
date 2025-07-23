package androidx.dynamicanimation.animation;

import android.util.FloatProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FloatPropertyCompat {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.dynamicanimation.animation.FloatPropertyCompat$1, reason: invalid class name */
    public class AnonymousClass1 extends FloatPropertyCompat {
        public final /* synthetic */ FloatProperty val$property;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, FloatProperty floatProperty) {
            super(str);
            this.val$property = floatProperty;
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final float getValue(Object obj) {
            return ((Float) this.val$property.get(obj)).floatValue();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public final void setValue(Object obj, float f) {
            this.val$property.setValue(obj, f);
        }
    }

    public FloatPropertyCompat(String str) {
    }

    public abstract float getValue(Object obj);

    public abstract void setValue(Object obj, float f);
}
