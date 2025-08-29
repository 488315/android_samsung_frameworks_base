package kotlinx.coroutines.internal;

import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class InlineList {
    public final Object holder;

    /* renamed from: plus-FjFbRPM, reason: not valid java name */
    public static final Object m3483plusFjFbRPM(Object obj, Object obj2) {
        if (obj == null) {
            return obj2;
        }
        if (obj instanceof ArrayList) {
            ((ArrayList) obj).add(obj2);
            return obj;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(obj2);
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof InlineList) {
            return Intrinsics.areEqual(this.holder, ((InlineList) obj).holder);
        }
        return false;
    }

    public final int hashCode() {
        Object obj = this.holder;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public final String toString() {
        return "InlineList(holder=" + this.holder + ")";
    }
}
