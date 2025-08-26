package androidx.core.animation;

import android.util.Property;

/* loaded from: classes.dex */
public abstract class IntProperty extends Property {
    public IntProperty(String str) {
        super(Integer.class, str);
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        setValue(obj, ((Integer) obj2).intValue());
    }

    public abstract void setValue(Object obj, int i);

    public IntProperty() {
        super(Integer.class, "");
    }
}
