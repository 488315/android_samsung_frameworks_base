package androidx.room.util;

import java.util.Collection;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.text.StringsKt__IndentKt$$ExternalSyntheticLambda0;
import kotlin.text.StringsKt__StringsKt;
import kotlin.text.StringsKt__StringsKt$lineSequence$$inlined$Sequence$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TableInfoKt {
    public static final boolean defaultValueEqualsCommon(String str, String str2) {
        if (Intrinsics.areEqual(str, str2)) {
            return true;
        }
        if (str.length() != 0) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i < str.length()) {
                    char charAt = str.charAt(i);
                    int i4 = i3 + 1;
                    if (i3 == 0 && charAt != '(') {
                        break;
                    }
                    if (charAt == '(') {
                        i2++;
                    } else if (charAt == ')' && i2 - 1 == 0 && i3 != str.length() - 1) {
                        break;
                    }
                    i++;
                    i3 = i4;
                } else if (i2 == 0) {
                    return Intrinsics.areEqual(StringsKt__StringsKt.trim(str.substring(1, str.length() - 1)).toString(), str2);
                }
            }
        }
        return false;
    }

    public static final String formatString(Collection collection) {
        String joinToString$default;
        if (collection.isEmpty()) {
            return " }";
        }
        StringBuilder sb = new StringBuilder();
        joinToString$default = SequencesKt___SequencesKt.joinToString$default(new TransformingSequence(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(CollectionsKt___CollectionsKt.joinToString$default(collection, ",\n", "\n", "\n", null, 56)), new StringsKt__IndentKt$$ExternalSyntheticLambda0()), "\n", null, 62);
        sb.append(joinToString$default);
        sb.append("},");
        return sb.toString();
    }
}
