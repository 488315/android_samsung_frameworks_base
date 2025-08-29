package com.android.systemui.statusbar.commandline;

import android.graphics.Color;
import kotlin.Result;

/* loaded from: classes3.dex */
public abstract class ValueParserKt {
    public static final ValueParserKt$parseColor$1 parseColor = null;
    public static final ValueParserKt$parseString$1 parseString = ValueParserKt$parseString$1.INSTANCE;
    public static final ValueParserKt$parseBoolean$1 parseBoolean = ValueParserKt$parseBoolean$1.INSTANCE;
    public static final ValueParserKt$parseInt$1 parseInt = ValueParserKt$parseInt$1.INSTANCE;
    public static final ValueParserKt$parseFloat$1 parseFloat = ValueParserKt$parseFloat$1.INSTANCE;

    static {
        ValueParserKt$parseColor$1 valueParserKt$parseColor$1 = new ValueParser() { // from class: com.android.systemui.statusbar.commandline.ValueParserKt$parseColor$1
            @Override // com.android.systemui.statusbar.commandline.ValueParser
            /* renamed from: parseValue-IoAF18A */
            public final Object mo2563parseValueIoAF18A(String str) {
                try {
                    int i = Result.$r8$clinit;
                    return Integer.valueOf(Color.parseColor(str));
                } catch (IllegalArgumentException e) {
                    int i2 = Result.$r8$clinit;
                    return new Result.Failure(new ArgParseError("Failed to parse " + str + " as a color: " + e));
                }
            }
        };
    }
}
