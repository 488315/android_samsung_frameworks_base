package dagger.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SetBuilder {
    public final List contributions;

    private SetBuilder(int i) {
        this.contributions = new ArrayList(i);
    }

    public static SetBuilder newSetBuilder(int i) {
        return new SetBuilder(i);
    }

    public final void add(Object obj) {
        List list = this.contributions;
        if (obj == null) {
            throw new NullPointerException("Set contributions cannot be null");
        }
        ((ArrayList) list).add(obj);
    }

    public final void addAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new NullPointerException("Set contributions cannot be null");
            }
        }
        ((ArrayList) this.contributions).addAll(collection);
    }

    public final Set build() {
        List list = this.contributions;
        return ((ArrayList) list).isEmpty() ? Collections.emptySet() : ((ArrayList) list).size() == 1 ? Collections.singleton(((ArrayList) list).get(0)) : Collections.unmodifiableSet(new HashSet(list));
    }
}
