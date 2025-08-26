package kotlin.collections.builders;

import java.util.Map;
import kotlin.collections.AbstractMutableSet;

/* loaded from: classes4.dex */
public abstract class AbstractMapBuilderEntrySet extends AbstractMutableSet {
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return containsEntry((Map.Entry) obj);
        }
        return false;
    }

    public abstract boolean containsEntry(Map.Entry entry);

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof Map.Entry) {
            return remove((Map.Entry) obj);
        }
        return false;
    }

    public /* bridge */ boolean remove(Map.Entry entry) {
        return super.remove((Object) entry);
    }
}
