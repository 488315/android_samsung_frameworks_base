package androidx.datastore.preferences.core;

import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Preferences {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key {
        public final String name;

        public Key(String str) {
            this.name = str;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            return Intrinsics.areEqual(this.name, ((Key) obj).name);
        }

        public final int hashCode() {
            return this.name.hashCode();
        }

        public final String toString() {
            return this.name;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Pair {
        public final Key key;
        public final Object value;

        public Pair(Key key, Object obj) {
            this.key = key;
            this.value = obj;
        }
    }

    public abstract Map asMap();

    public abstract Object get(Key key);
}
