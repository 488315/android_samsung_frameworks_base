package kotlin.collections;

import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SetsKt__SetsKt extends SetsKt__SetsJVMKt {
    public static final Set setOf(Object... objArr) {
        return objArr.length > 0 ? ArraysKt___ArraysKt.toSet(objArr) : EmptySet.INSTANCE;
    }
}
