package androidx.collection;

/* loaded from: classes.dex */
public abstract class ObjectIntMapKt {
    public static final MutableObjectIntMap EmptyObjectIntMap = new MutableObjectIntMap(0);

    public static final MutableObjectIntMap mutableObjectIntMapOf() {
        return new MutableObjectIntMap(0, 1, null);
    }
}
