package androidx.collection;

/* loaded from: classes.dex */
public abstract class IntObjectMapKt {
    public static final MutableIntObjectMap EmptyIntObjectMap = new MutableIntObjectMap(0);

    public static final MutableIntObjectMap mutableIntObjectMapOf() {
        return new MutableIntObjectMap(0, 1, null);
    }
}
