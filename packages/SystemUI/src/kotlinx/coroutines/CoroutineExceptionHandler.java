package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface CoroutineExceptionHandler extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key implements CoroutineContext.Key {
        public static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    void handleException(Throwable th, CoroutineContext coroutineContext);
}
