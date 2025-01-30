package kotlinx.coroutines;

import java.io.Closeable;
import java.util.concurrent.Executor;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ExecutorCoroutineDispatcher extends CoroutineDispatcher implements Closeable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Key extends AbstractCoroutineContextKey {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
            super(CoroutineDispatcher.Key, new Function1() { // from class: kotlinx.coroutines.ExecutorCoroutineDispatcher.Key.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    CoroutineContext.Element element = (CoroutineContext.Element) obj;
                    if (element instanceof ExecutorCoroutineDispatcher) {
                        return (ExecutorCoroutineDispatcher) element;
                    }
                    return null;
                }
            });
        }
    }

    static {
        new Key(null);
    }

    public abstract Executor getExecutor();
}
