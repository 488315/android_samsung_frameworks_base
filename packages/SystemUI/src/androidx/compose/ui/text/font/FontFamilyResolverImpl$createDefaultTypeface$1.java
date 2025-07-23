package androidx.compose.ui.text.font;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FontFamilyResolverImpl$createDefaultTypeface$1 extends Lambda implements Function1 {
    final /* synthetic */ FontFamilyResolverImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontFamilyResolverImpl$createDefaultTypeface$1(FontFamilyResolverImpl fontFamilyResolverImpl) {
        super(1);
        this.this$0 = fontFamilyResolverImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        TypefaceRequest typefaceRequest = (TypefaceRequest) obj;
        return this.this$0.resolve(new TypefaceRequest(null, typefaceRequest.fontWeight, typefaceRequest.fontStyle, typefaceRequest.fontSynthesis, typefaceRequest.resourceLoaderCacheKey, null)).getValue();
    }
}
