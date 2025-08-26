package androidx.compose.ui.platform;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes.dex */
public interface InfiniteAnimationPolicy extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    public final class Key implements CoroutineContext.Key {
        public static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }
}
