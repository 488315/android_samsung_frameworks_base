package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ObjectListKt {
    public static final Object[] EmptyArray = new Object[0];
    public static final MutableObjectList EmptyObjectList = new MutableObjectList(0);

    public static final void access$checkIndex(int i, List list) {
        int size = list.size();
        if (i < 0 || i >= size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index " + i + " is out of bounds. The list has " + size + " elements.");
            throw null;
        }
    }

    public static final void access$checkSubIndex(List list, int i, int i2) {
        int size = list.size();
        if (i > i2) {
            RuntimeHelpersKt.throwIllegalArgumentException("Indices are out of order. fromIndex (" + i + ") is greater than toIndex (" + i2 + ").");
            throw null;
        }
        if (i < 0) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("fromIndex (" + i + ") is less than 0.");
            throw null;
        }
        if (i2 <= size) {
            return;
        }
        RuntimeHelpersKt.throwIndexOutOfBoundsException("toIndex (" + i2 + ") is more than than the list size (" + size + ')');
        throw null;
    }
}
