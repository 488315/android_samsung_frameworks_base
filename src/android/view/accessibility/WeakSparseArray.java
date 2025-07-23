package android.view.accessibility;

import android.util.SparseArray;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
final class WeakSparseArray<E> {
    private final ReferenceQueue<E> mRefQueue = new ReferenceQueue<>();
    private final SparseArray<WeakReferenceWithId<E>> mSparseArray = new SparseArray<>();

    WeakSparseArray() {
    }

    public void append(int i, E e) {
        removeUnreachableValues();
        this.mSparseArray.append(i, new WeakReferenceWithId<>(e, this.mRefQueue, i));
    }

    public void remove(int i) {
        removeUnreachableValues();
        this.mSparseArray.remove(i);
    }

    public E get(int i) {
        removeUnreachableValues();
        WeakReferenceWithId<E> weakReferenceWithId = this.mSparseArray.get(i);
        if (weakReferenceWithId != null) {
            return (E) weakReferenceWithId.get();
        }
        return null;
    }

    private void removeUnreachableValues() {
        while (true) {
            Reference<? extends E> poll = this.mRefQueue.poll();
            if (poll == null) {
                return;
            } else {
                this.mSparseArray.remove(((WeakReferenceWithId) poll).mId);
            }
        }
    }

    private static class WeakReferenceWithId<E> extends WeakReference<E> {
        final int mId;

        WeakReferenceWithId(E e, ReferenceQueue<? super E> referenceQueue, int i) {
            super(e, referenceQueue);
            this.mId = i;
        }
    }
}
