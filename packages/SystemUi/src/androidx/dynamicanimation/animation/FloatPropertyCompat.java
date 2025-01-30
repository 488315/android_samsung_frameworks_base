package androidx.dynamicanimation.animation;

import android.util.FloatProperty;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FloatPropertyCompat {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.dynamicanimation.animation.FloatPropertyCompat$1 */
    public final class C01991 extends FloatPropertyCompat {
        public final /* synthetic */ FloatProperty val$property;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01991(String str, FloatProperty floatProperty) {
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
