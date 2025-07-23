package androidx.core.animation;

import android.util.Property;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
