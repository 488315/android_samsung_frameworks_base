package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import java.util.Collection;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AbstractPersistentList$removeAll$1 extends Lambda implements Function1 {
    final /* synthetic */ Collection<Object> $elements;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractPersistentList$removeAll$1(Collection<Object> collection) {
        super(1);
        this.$elements = collection;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Boolean.valueOf(this.$elements.contains(obj));
    }
}
