package androidx.customview.poolingcontainer;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
