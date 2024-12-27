package android.util;

public abstract class IntProperty<T> extends Property<T, Integer> {
    public abstract void setValue(T t, int i);

    @Override // android.util.Property
    public /* bridge */ /* synthetic */ void set(Object obj, Integer num) {
        set2((IntProperty<T>) obj, num);
    }

    public IntProperty(String name) {
        super(Integer.class, name);
    }

    /* renamed from: set, reason: avoid collision after fix types in other method */
    public final void set2(T object, Integer value) {
        setValue(object, value.intValue());
    }
}
