package kotlin.text;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class StringsKt__AppendableKt {
    public static final void appendElement(Appendable appendable, Object obj, Function1 function1) {
        if (function1 != null) {
            ((StringBuilder) appendable).append((CharSequence) function1.invoke(obj));
            return;
        }
        if (obj == null ? true : obj instanceof CharSequence) {
            ((StringBuilder) appendable).append((CharSequence) obj);
        } else if (obj instanceof Character) {
            ((StringBuilder) appendable).append(((Character) obj).charValue());
        } else {
            ((StringBuilder) appendable).append((CharSequence) String.valueOf(obj));
        }
    }
}
