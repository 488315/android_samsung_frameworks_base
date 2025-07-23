package androidx.compose.material3.tokens;

import androidx.compose.material3.internal.DefaultPlatformTextStyle_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.LineHeightStyle;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TypographyTokensKt {
    public static final TextStyle DefaultTextStyle;

    static {
        LineHeightStyle.Alignment.Companion.getClass();
        float f = LineHeightStyle.Alignment.Center;
        LineHeightStyle.Trim.Companion.getClass();
        LineHeightStyle lineHeightStyle = new LineHeightStyle(f, 0, (DefaultConstructorMarker) null);
        TextStyle.Companion.getClass();
        DefaultTextStyle = TextStyle.m754copyp1EtxEg$default(TextStyle.Default, 0L, 0L, null, null, 0L, 0, 0L, DefaultPlatformTextStyle_androidKt.DefaultPlatformTextStyle, lineHeightStyle, 0, 15204351);
    }
}
