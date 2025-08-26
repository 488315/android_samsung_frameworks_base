package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes4.dex */
public interface CoroutineExceptionHandler extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    public final class Key implements CoroutineContext.Key {
        public static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    void handleException(Throwable th, CoroutineContext coroutineContext);
}
