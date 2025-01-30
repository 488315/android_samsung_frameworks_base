package kotlinx.coroutines.internal;

import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InlineList {
    public final Object holder;

    /* renamed from: plus-FjFbRPM, reason: not valid java name */
    public static final Object m2876plusFjFbRPM(Object obj, LockFreeLinkedListNode lockFreeLinkedListNode) {
        if (obj == null) {
            return lockFreeLinkedListNode;
        }
        if (obj instanceof ArrayList) {
            ((ArrayList) obj).add(lockFreeLinkedListNode);
            return obj;
        }
        ArrayList arrayList = new ArrayList(4);
        arrayList.add(obj);
        arrayList.add(lockFreeLinkedListNode);
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
