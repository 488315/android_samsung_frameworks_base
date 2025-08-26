package androidx.compose.ui.platform;

import androidx.compose.runtime.collection.MutableVector;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/* loaded from: classes.dex */
public final class WeakCache<T> {
    public final MutableVector values = new MutableVector(new Reference[16], 0);
    public final ReferenceQueue referenceQueue = new ReferenceQueue();
}
