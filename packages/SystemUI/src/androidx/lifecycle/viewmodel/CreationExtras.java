package androidx.lifecycle.viewmodel;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class CreationExtras {
    public final Map map = new LinkedHashMap();

    public final class Empty extends CreationExtras {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // androidx.lifecycle.viewmodel.CreationExtras
        public final Object get(Key key) {
            return null;
        }
    }

    public interface Key {
    }

    public abstract Object get(Key key);
}
