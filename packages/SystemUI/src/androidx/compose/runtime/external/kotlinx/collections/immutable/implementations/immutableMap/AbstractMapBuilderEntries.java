package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Map;
import java.util.Map.Entry;
import kotlin.collections.AbstractMutableSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AbstractMapBuilderEntries<E extends Map.Entry<? extends K, ? extends V>, K, V> extends AbstractMutableSet {
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if ((entry != null ? entry : null) == null) {
            return false;
        }
        return containsEntry(entry);
    }

    public abstract boolean containsEntry(Map.Entry entry);

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if ((entry != null ? entry : null) == null) {
            return false;
        }
        return removeEntry(entry);
    }

    public abstract boolean removeEntry(Map.Entry entry);
}
