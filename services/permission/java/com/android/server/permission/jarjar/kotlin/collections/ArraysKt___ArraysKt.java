package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.text.StringsKt__AppendableKt;
import java.util.NoSuchElementException;

/* compiled from: _Arrays.kt */
/* loaded from: classes2.dex */
public abstract class ArraysKt___ArraysKt extends ArraysKt___ArraysJvmKt {
    public static final boolean contains(Object[] objArr, Object obj) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        return indexOf(objArr, obj) >= 0;
    }

    public static final Object first(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        if (objArr.length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        return objArr[0];
    }

    public static final int indexOf(Object[] objArr, Object obj) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        int i = 0;
        if (obj == null) {
            int length = objArr.length;
            while (i < length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        int length2 = objArr.length;
        while (i < length2) {
            if (Intrinsics.areEqual(obj, objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static final Object singleOrNull(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        if (objArr.length == 1) {
            return objArr[0];
        }
        return null;
    }

    public static final Appendable joinTo(Object[] objArr, Appendable appendable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, Function1 function1) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Intrinsics.checkNotNullParameter(appendable, "buffer");
        Intrinsics.checkNotNullParameter(charSequence, "separator");
        Intrinsics.checkNotNullParameter(charSequence2, "prefix");
        Intrinsics.checkNotNullParameter(charSequence3, "postfix");
        Intrinsics.checkNotNullParameter(charSequence4, "truncated");
        appendable.append(charSequence2);
        int i2 = 0;
        for (Object obj : objArr) {
            i2++;
            if (i2 > 1) {
                appendable.append(charSequence);
            }
            if (i >= 0 && i2 > i) {
                break;
            }
            StringsKt__AppendableKt.appendElement(appendable, obj, function1);
        }
        if (i >= 0 && i2 > i) {
            appendable.append(charSequence4);
        }
        appendable.append(charSequence3);
        return appendable;
    }
}
