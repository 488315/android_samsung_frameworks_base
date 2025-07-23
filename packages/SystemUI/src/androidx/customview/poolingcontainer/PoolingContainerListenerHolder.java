package androidx.customview.poolingcontainer;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PoolingContainerListenerHolder {
    private final ArrayList<PoolingContainerListener> listeners = new ArrayList<>();

    public final void addListener(PoolingContainerListener poolingContainerListener) {
        this.listeners.add(poolingContainerListener);
    }

    public final void onRelease() {
        for (int lastIndex = CollectionsKt__CollectionsKt.getLastIndex(this.listeners); -1 < lastIndex; lastIndex--) {
            this.listeners.get(lastIndex).onRelease();
        }
    }

    public final void removeListener(PoolingContainerListener poolingContainerListener) {
        this.listeners.remove(poolingContainerListener);
    }
}
