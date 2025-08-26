package androidx.compose.ui;

import kotlin.coroutines.CoroutineContext;

/* loaded from: classes.dex */
public interface MotionDurationScale extends CoroutineContext.Element {
    public static final Key Key = Key.$$INSTANCE;

    public final class Key implements CoroutineContext.Key {
        public static final /* synthetic */ Key $$INSTANCE = new Key();

        private Key() {
        }
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    default CoroutineContext.Key getKey() {
        return Key;
    }

    float getScaleFactor();
}
