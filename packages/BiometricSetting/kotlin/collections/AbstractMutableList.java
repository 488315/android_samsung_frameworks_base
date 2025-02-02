package kotlin.collections;

import java.util.AbstractList;
import java.util.List;

/* compiled from: AbstractMutableList.kt */
/* loaded from: classes.dex */
public abstract class AbstractMutableList<E> extends AbstractList<E> implements List<E> {
    protected AbstractMutableList() {
    }

    public abstract int getSize();

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ E remove(int i) {
        return removeAt(i);
    }

    public abstract E removeAt(int i);

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }
}
