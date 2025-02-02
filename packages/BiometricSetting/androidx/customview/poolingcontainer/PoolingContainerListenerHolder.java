package androidx.customview.poolingcontainer;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;

/* compiled from: PoolingContainer.kt */
/* loaded from: classes.dex */
final class PoolingContainerListenerHolder {
    private final ArrayList<PoolingContainerListener> listeners = new ArrayList<>();

    public final void onRelease() {
        ArrayList<PoolingContainerListener> arrayList = this.listeners;
        for (int lastIndex = CollectionsKt.getLastIndex(arrayList); -1 < lastIndex; lastIndex--) {
            arrayList.get(lastIndex).onRelease();
        }
    }
}
