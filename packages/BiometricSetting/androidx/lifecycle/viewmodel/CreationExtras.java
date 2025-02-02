package androidx.lifecycle.viewmodel;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: CreationExtras.kt */
/* loaded from: classes.dex */
public abstract class CreationExtras {
    private final Map<Key<?>, Object> map = new LinkedHashMap();

    /* compiled from: CreationExtras.kt */
    public static final class Empty extends CreationExtras {
        public static final Empty INSTANCE = new Empty();
    }

    /* compiled from: CreationExtras.kt */
    public interface Key<T> {
    }

    public final Map<Key<?>, Object> getMap$lifecycle_viewmodel_release() {
        return this.map;
    }
}
