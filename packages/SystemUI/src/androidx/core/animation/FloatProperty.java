package androidx.core.animation;

import android.util.Property;

/* loaded from: classes.dex */
public abstract class FloatProperty extends Property {
    public FloatProperty(String str) {
        super(Float.class, str);
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        ((Float) obj2).getClass();
        setValue();
    }

    public abstract void setValue();

    public FloatProperty() {
        super(Float.class, "");
    }
}
