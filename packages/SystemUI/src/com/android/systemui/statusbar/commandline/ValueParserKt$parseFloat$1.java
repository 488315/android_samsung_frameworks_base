package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ValueParserKt$parseFloat$1 implements ValueParser {
    public static final ValueParserKt$parseFloat$1 INSTANCE = new ValueParserKt$parseFloat$1();

    @Override // com.android.systemui.statusbar.commandline.ValueParser
    /* renamed from: parseValue-IoAF18A */
    public final Object mo2548parseValueIoAF18A(String str) {
        Float floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(str);
        if (floatOrNull == null) {
            int i = Result.$r8$clinit;
            return new Result.Failure(new ArgParseError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Failed to parse ", str, " as a float")));
        }
        float floatValue = floatOrNull.floatValue();
        int i2 = Result.$r8$clinit;
        return Float.valueOf(floatValue);
    }
}
