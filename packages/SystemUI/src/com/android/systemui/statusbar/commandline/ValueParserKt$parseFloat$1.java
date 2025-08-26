package com.android.systemui.statusbar.commandline;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* loaded from: classes3.dex */
public final class ValueParserKt$parseFloat$1 implements ValueParser {
    public static final ValueParserKt$parseFloat$1 INSTANCE = new ValueParserKt$parseFloat$1();

    @Override // com.android.systemui.statusbar.commandline.ValueParser
    /* renamed from: parseValue-IoAF18A */
    public final Object mo2565parseValueIoAF18A(String str) {
        Float floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(str);
        if (floatOrNull == null) {
            int i = Result.$r8$clinit;
            return new Result.Failure(new ArgParseError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Failed to parse ", str, " as a float")));
        }
        float fFloatValue = floatOrNull.floatValue();
        int i2 = Result.$r8$clinit;
        return Float.valueOf(fFloatValue);
    }
}
