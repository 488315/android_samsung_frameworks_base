package androidx.navigation;

import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class CollectionNavType extends NavType {
    public CollectionNavType(boolean z) {
        super(z);
    }

    public abstract Object emptyCollection();

    public abstract List serializeAsValues(Object obj);
}
