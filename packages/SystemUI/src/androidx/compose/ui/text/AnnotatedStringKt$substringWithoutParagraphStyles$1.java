package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class AnnotatedStringKt$substringWithoutParagraphStyles$1 extends Lambda implements Function1 {
    public static final AnnotatedStringKt$substringWithoutParagraphStyles$1 INSTANCE = new AnnotatedStringKt$substringWithoutParagraphStyles$1();

    public AnnotatedStringKt$substringWithoutParagraphStyles$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(!(((AnnotatedString.Annotation) obj) instanceof ParagraphStyle));
    }
}
