package androidx.compose.ui.res;

import androidx.collection.MutableIntObjectMap;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
