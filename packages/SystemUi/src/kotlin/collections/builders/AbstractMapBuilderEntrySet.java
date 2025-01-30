package kotlin.collections.builders;

import java.util.Map;
import kotlin.collections.AbstractMutableSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
