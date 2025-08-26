package kotlin.text;

import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final /* synthetic */ class StringsKt__IndentKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = (String) obj;
        return StringsKt__StringsKt.isBlank(str) ? str.length() < 4 ? "    " : str : "    ".concat(str);
    }
}
