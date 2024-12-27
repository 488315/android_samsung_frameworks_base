package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.text.StringsKt__StringNumberConversionsKt;

public final class ValueParserKt$parseInt$1 implements ValueParser {
    public static final ValueParserKt$parseInt$1 INSTANCE = new ValueParserKt$parseInt$1();

    @Override // com.android.systemui.statusbar.commandline.ValueParser
    /* renamed from: parseValue-IoAF18A */
    public final Object mo1930parseValueIoAF18A(String str) {
        Integer intOrNull = StringsKt__StringNumberConversionsKt.toIntOrNull(10, str);
        if (intOrNull == null) {
            int i = Result.$r8$clinit;
            return new Result.Failure(new ArgParseError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Failed to parse ", str, " as an int")));
        }
        int intValue = intOrNull.intValue();
        int i2 = Result.$r8$clinit;
        return Integer.valueOf(intValue);
    }
}
