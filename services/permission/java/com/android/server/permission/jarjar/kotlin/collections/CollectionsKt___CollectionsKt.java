package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.text.StringsKt__AppendableKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: _Collections.kt */
/* loaded from: classes2.dex */
public abstract class CollectionsKt___CollectionsKt extends CollectionsKt___CollectionsJvmKt {
  public static final Collection toCollection(Iterable iterable, Collection collection) {
    Intrinsics.checkNotNullParameter(iterable, "<this>");
    Intrinsics.checkNotNullParameter(collection, "destination");
    Iterator it = iterable.iterator();
    while (it.hasNext()) {
      collection.add(it.next());
    }
    return collection;
  }

  public static final List toList(Iterable iterable) {
    Intrinsics.checkNotNullParameter(iterable, "<this>");
    if (iterable instanceof Collection) {
      Collection collection = (Collection) iterable;
      int size = collection.size();
      if (size == 0) {
        return CollectionsKt__CollectionsKt.emptyList();
      }
      if (size != 1) {
        return toMutableList(collection);
      }
      return CollectionsKt__CollectionsJVMKt.listOf(
          iterable instanceof List ? ((List) iterable).get(0) : iterable.iterator().next());
    }
    return CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(iterable));
  }

  public static final List toMutableList(Iterable iterable) {
    Intrinsics.checkNotNullParameter(iterable, "<this>");
    if (iterable instanceof Collection) {
      return toMutableList((Collection) iterable);
    }
    return (List) toCollection(iterable, new ArrayList());
  }

  public static final List toMutableList(Collection collection) {
    Intrinsics.checkNotNullParameter(collection, "<this>");
    return new ArrayList(collection);
  }

  public static final Appendable joinTo(
      Iterable iterable,
      Appendable appendable,
      CharSequence charSequence,
      CharSequence charSequence2,
      CharSequence charSequence3,
      int i,
      CharSequence charSequence4,
      Function1 function1) {
    Intrinsics.checkNotNullParameter(iterable, "<this>");
    Intrinsics.checkNotNullParameter(appendable, "buffer");
    Intrinsics.checkNotNullParameter(charSequence, "separator");
    Intrinsics.checkNotNullParameter(charSequence2, "prefix");
    Intrinsics.checkNotNullParameter(charSequence3, "postfix");
    Intrinsics.checkNotNullParameter(charSequence4, "truncated");
    appendable.append(charSequence2);
    int i2 = 0;
    for (Object obj : iterable) {
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

  public static /* synthetic */ String joinToString$default(
      Iterable iterable,
      CharSequence charSequence,
      CharSequence charSequence2,
      CharSequence charSequence3,
      int i,
      CharSequence charSequence4,
      Function1 function1,
      int i2,
      Object obj) {
    if ((i2 & 1) != 0) {
      charSequence = ", ";
    }
    CharSequence charSequence5 = (i2 & 2) != 0 ? "" : charSequence2;
    CharSequence charSequence6 = (i2 & 4) == 0 ? charSequence3 : "";
    if ((i2 & 8) != 0) {
      i = -1;
    }
    int i3 = i;
    if ((i2 & 16) != 0) {
      charSequence4 = "...";
    }
    CharSequence charSequence7 = charSequence4;
    if ((i2 & 32) != 0) {
      function1 = null;
    }
    return joinToString(
        iterable, charSequence, charSequence5, charSequence6, i3, charSequence7, function1);
  }

  public static final String joinToString(
      Iterable iterable,
      CharSequence charSequence,
      CharSequence charSequence2,
      CharSequence charSequence3,
      int i,
      CharSequence charSequence4,
      Function1 function1) {
    Intrinsics.checkNotNullParameter(iterable, "<this>");
    Intrinsics.checkNotNullParameter(charSequence, "separator");
    Intrinsics.checkNotNullParameter(charSequence2, "prefix");
    Intrinsics.checkNotNullParameter(charSequence3, "postfix");
    Intrinsics.checkNotNullParameter(charSequence4, "truncated");
    String sb =
        ((StringBuilder)
                joinTo(
                    iterable,
                    new StringBuilder(),
                    charSequence,
                    charSequence2,
                    charSequence3,
                    i,
                    charSequence4,
                    function1))
            .toString();
    Intrinsics.checkNotNullExpressionValue(
        sb, "joinTo(StringBuilder(), …ed, transform).toString()");
    return sb;
  }
}
