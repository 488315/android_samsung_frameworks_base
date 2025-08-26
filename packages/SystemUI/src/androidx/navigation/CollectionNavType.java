package androidx.navigation;

import java.util.List;

/* loaded from: classes.dex */
public abstract class CollectionNavType extends NavType {
    public CollectionNavType(boolean z) {
        super(z);
    }

    public abstract Object emptyCollection();

    public abstract List serializeAsValues(Object obj);
}
