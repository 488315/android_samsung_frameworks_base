package android.util;

public abstract class FloatProperty<T> extends Property<T, Float> {
    public abstract void setValue(T t, float f);

    @Override // android.util.Property
    public /* bridge */ /* synthetic */ void set(Object obj, Float f) {
        set2((FloatProperty<T>) obj, f);
    }

    public FloatProperty(String name) {
        super(Float.class, name);
    }

    /* renamed from: set, reason: avoid collision after fix types in other method */
    public final void set2(T object, Float value) {
        setValue(object, value.floatValue());
    }
}
