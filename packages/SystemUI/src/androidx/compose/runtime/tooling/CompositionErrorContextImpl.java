package androidx.compose.runtime.tooling;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.changelist.OperationErrorContext;
import java.util.List;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CompositionErrorContextImpl implements CompositionErrorContext, OperationErrorContext, CoroutineContext.Element {
    public static final Key Key = new Key(null);
    public final ComposerImpl composer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key implements CoroutineContext.Key {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String toString() {
            return "CompositionErrorContext";
        }

        private Key() {
        }
    }

    public CompositionErrorContextImpl(ComposerImpl composerImpl) {
        this.composer = composerImpl;
    }

    public final boolean attachComposeStackTrace(final Object obj, Throwable th) {
        return ComposeStackTraceKt.tryAttachComposeStackTrace(th, new Function0() { // from class: androidx.compose.runtime.tooling.CompositionErrorContextImpl$attachComposeStackTrace$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CompositionErrorContextImpl.this.composer.stackTraceForValue$runtime_release(obj);
            }
        });
    }

    @Override // androidx.compose.runtime.changelist.OperationErrorContext
    public final List buildStackTrace(Integer num) {
        return this.composer.parentStackTrace();
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return Key;
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }
}
