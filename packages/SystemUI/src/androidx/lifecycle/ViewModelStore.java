package androidx.lifecycle;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ViewModelStore {
    public final Map map = new LinkedHashMap();

    public final void clear() {
        Iterator it = ((LinkedHashMap) this.map).values().iterator();
        while (it.hasNext()) {
            ((ViewModel) it.next()).clear$lifecycle_viewmodel_release();
        }
        ((LinkedHashMap) this.map).clear();
    }
}
