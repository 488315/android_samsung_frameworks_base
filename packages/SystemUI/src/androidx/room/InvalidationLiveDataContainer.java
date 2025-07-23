package androidx.room;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InvalidationLiveDataContainer {
    public final Set liveDataSet = Collections.newSetFromMap(new IdentityHashMap());

    public InvalidationLiveDataContainer(RoomDatabase roomDatabase) {
    }
}
