package androidx.compose.ui.res;

import androidx.collection.MutableIntObjectMap;
import kotlin.Unit;

/* loaded from: classes.dex */
public final class ResourceIdCache {
    public final MutableIntObjectMap resIdPathMap = new MutableIntObjectMap(0, 1, null);

    public final void clear() {
        synchronized (this) {
            this.resIdPathMap.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}
