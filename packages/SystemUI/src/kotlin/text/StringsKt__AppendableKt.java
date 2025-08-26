package kotlin.text;

import java.io.IOException;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public class StringsKt__AppendableKt {
    public static void appendElement(Appendable appendable, Object obj, Function1 function1) throws IOException {
        if (function1 != null) {
            appendable.append((CharSequence) function1.mo781invoke(obj));
            return;
        }
        if (obj == null ? true : obj instanceof CharSequence) {
            appendable.append((CharSequence) obj);
        } else if (obj instanceof Character) {
            appendable.append(((Character) obj).charValue());
        } else {
            appendable.append(obj.toString());
        }
    }
}
