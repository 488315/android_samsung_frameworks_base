package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface Job extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class DefaultImpls {
        public static /* synthetic */ DisposableHandle invokeOnCompletion$default(Job job, boolean z, JobNode jobNode, int i) {
            if ((i & 1) != 0) {
                z = false;
            }
            return ((JobSupport) job).invokeOnCompletion(z, (i & 2) != 0, jobNode);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Key implements CoroutineContext.Key {
        public static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    void cancel(CancellationException cancellationException);

    boolean isActive();
}
