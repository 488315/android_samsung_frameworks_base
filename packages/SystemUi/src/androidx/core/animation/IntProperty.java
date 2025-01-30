package androidx.core.animation;

import android.util.Property;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class IntProperty extends Property {
    public IntProperty(String str) {
        super(Integer.class, str);
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        ((Integer) obj2).intValue();
        setValue();
    }

    public abstract void setValue();

    public IntProperty() {
        super(Integer.class, "");
    }
}
