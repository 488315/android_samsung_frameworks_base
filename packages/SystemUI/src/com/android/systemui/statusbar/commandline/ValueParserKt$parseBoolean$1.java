package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ValueParserKt$parseBoolean$1 implements ValueParser {
    public static final ValueParserKt$parseBoolean$1 INSTANCE = new ValueParserKt$parseBoolean$1();

    @Override // com.android.systemui.statusbar.commandline.ValueParser
    /* renamed from: parseValue-IoAF18A */
    public final Object mo1930parseValueIoAF18A(String str) {
        Boolean booleanStrictOrNull = StringsKt__StringsKt.toBooleanStrictOrNull(str);
        if (booleanStrictOrNull != null) {
            int i = Result.$r8$clinit;
            return booleanStrictOrNull;
        }
        int i2 = Result.$r8$clinit;
        return new Result.Failure(new ArgParseError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Failed to parse ", str, " as a boolean")));
    }
}
