package com.android.server.permission.jarjar.kotlin.text;

import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;

public abstract class StringsKt__AppendableKt {
    public static void appendElement(Appendable appendable, Object obj, Function1 function1) {
        if (function1 != null) {
            function1.invoke(obj);
            throw null;
        }
        if (obj == null ? true : obj instanceof CharSequence) {
            appendable.append((CharSequence) obj);
        } else if (obj instanceof Character) {
            appendable.append(((Character) obj).charValue());
        } else {
            appendable.append(String.valueOf(obj));
        }
    }
}
