package androidx.compose.ui.platform;

import androidx.compose.runtime.collection.MutableVector;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WeakCache<T> {
    public final MutableVector values = new MutableVector(new Reference[16], 0);
    public final ReferenceQueue referenceQueue = new ReferenceQueue();
}
