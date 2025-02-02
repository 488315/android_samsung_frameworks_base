package kotlin.text;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class StringsKt extends StringsKt___StringsKt {
    public static String substringAfterLast$default(String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "<this>");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int lastIndexOf = missingDelimiterValue.lastIndexOf(46, missingDelimiterValue.length() - 1);
        if (lastIndexOf == -1) {
            return missingDelimiterValue;
        }
        String substring = missingDelimiterValue.substring(lastIndexOf + 1, missingDelimiterValue.length());
        Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
        return substring;
    }
}
