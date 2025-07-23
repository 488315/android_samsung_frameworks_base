package androidx.compose.foundation.text.modifiers;

import com.sec.ims.settings.ImsProfile;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class MinLinesConstrainerKt {
    public static final String EmptyTextReplacement;
    public static final String TwoLineTextReplacement;

    static {
        String repeat = StringsKt__StringsJVMKt.repeat(10, ImsProfile.TIMER_NAME_H);
        EmptyTextReplacement = repeat;
        TwoLineTextReplacement = repeat + '\n' + repeat;
    }
}
