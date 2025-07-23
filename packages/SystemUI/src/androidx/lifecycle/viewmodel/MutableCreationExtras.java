package androidx.lifecycle.viewmodel;

import androidx.lifecycle.viewmodel.CreationExtras;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableCreationExtras extends CreationExtras {
    public MutableCreationExtras() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.lifecycle.viewmodel.CreationExtras
    public final Object get(CreationExtras.Key key) {
        return ((LinkedHashMap) this.map).get(key);
    }

    public final void set(CreationExtras.Key key, Object obj) {
        this.map.put(key, obj);
    }

    public MutableCreationExtras(CreationExtras creationExtras) {
        this.map.putAll(creationExtras.map);
    }

    public /* synthetic */ MutableCreationExtras(CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }
}
