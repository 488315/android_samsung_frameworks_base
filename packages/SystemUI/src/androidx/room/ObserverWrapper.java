package androidx.room;

import androidx.room.InvalidationTracker;
import java.util.Collections;
import java.util.Set;
import kotlin.collections.EmptySet;

/* loaded from: classes.dex */
public final class ObserverWrapper {
    public final InvalidationTracker.Observer observer;
    public final Set singleTableSet;
    public final int[] tableIds;
    public final String[] tableNames;

    public ObserverWrapper(InvalidationTracker.Observer observer, int[] iArr, String[] strArr) {
        this.observer = observer;
        this.tableIds = iArr;
        this.tableNames = strArr;
        if (iArr.length != strArr.length) {
            throw new IllegalStateException("Check failed.");
        }
        this.singleTableSet = !(strArr.length == 0) ? Collections.singleton(strArr[0]) : EmptySet.INSTANCE;
    }
}
