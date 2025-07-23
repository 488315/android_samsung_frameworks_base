package dagger.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
        obj.getClass();
        ((ArrayList) list).add(obj);
    }

    public final void addAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            it.next().getClass();
        }
        ((ArrayList) this.contributions).addAll(collection);
    }

    public final Set build() {
        return ((ArrayList) this.contributions).isEmpty() ? Collections.EMPTY_SET : ((ArrayList) this.contributions).size() == 1 ? Collections.singleton(((ArrayList) this.contributions).get(0)) : Collections.unmodifiableSet(new HashSet(this.contributions));
    }
}
