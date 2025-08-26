package kotlin.collections;

import java.util.List;
import kotlin.jvm.internal.markers.KMutableList;

/* loaded from: classes4.dex */
public abstract class AbstractMutableList extends java.util.AbstractList implements List, KMutableList {
    public abstract int getSize();

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ Object remove(int i) {
        return removeAt(i);
    }

    public abstract Object removeAt(int i);

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }
}
