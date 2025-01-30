package com.android.server.permission.jarjar.kotlin.text;

import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: Appendable.kt */
/* loaded from: classes2.dex */
public abstract class StringsKt__AppendableKt {
  public static final void appendElement(Appendable appendable, Object obj, Function1 function1) {
    Intrinsics.checkNotNullParameter(appendable, "<this>");
    if (function1 != null) {
      appendable.append((CharSequence) function1.invoke(obj));
      return;
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
