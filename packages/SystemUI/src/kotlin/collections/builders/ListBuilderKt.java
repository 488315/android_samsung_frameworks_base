package kotlin.collections.builders;

import java.util.List;
import kotlin.collections.AbstractMutableList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ListBuilderKt {
    public static final boolean access$subarrayContentEquals(Object[] objArr, int i, int i2, List list) {
        if (i2 == list.size()) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (Intrinsics.areEqual(objArr[i + i3], list.get(i3))) {
                }
            }
            return true;
        }
        return false;
    }

    public static final String access$subarrayContentToString(Object[] objArr, int i, int i2, AbstractMutableList abstractMutableList) {
        StringBuilder sb = new StringBuilder((i2 * 3) + 2);
        sb.append("[");
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            Object obj = objArr[i + i3];
            if (obj == abstractMutableList) {
                sb.append("(this Collection)");
            } else {
                sb.append(obj);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static final void resetRange(int i, int i2, Object[] objArr) {
        while (i < i2) {
            objArr[i] = null;
            i++;
        }
    }
}
