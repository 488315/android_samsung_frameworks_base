package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AnnotatedStringKt$substringWithoutParagraphStyles$1 extends Lambda implements Function1 {
    public static final AnnotatedStringKt$substringWithoutParagraphStyles$1 INSTANCE = new AnnotatedStringKt$substringWithoutParagraphStyles$1();

    public AnnotatedStringKt$substringWithoutParagraphStyles$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Boolean.valueOf(!(((AnnotatedString.Annotation) obj) instanceof ParagraphStyle));
    }
}
