package androidx.window.embedding;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class EmbeddingBackend$Companion$decorator$1 extends Lambda implements Function1 {
    public static final EmbeddingBackend$Companion$decorator$1 INSTANCE = new EmbeddingBackend$Companion$decorator$1();

    public EmbeddingBackend$Companion$decorator$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return (EmbeddingBackend) obj;
    }
}
