package kotlin.text;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class StringsKt__IndentKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String str = (String) obj;
        return StringsKt__StringsKt.isBlank(str) ? str.length() < 4 ? "    " : str : "    ".concat(str);
    }
}
