package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FlatteningSequence$iterator$1 implements Iterator, KMappedMarker {
    public Iterator itemIterator;
    public final Iterator iterator;
    public final /* synthetic */ FlatteningSequence this$0;

    public FlatteningSequence$iterator$1(FlatteningSequence flatteningSequence) {
        this.this$0 = flatteningSequence;
        this.iterator = flatteningSequence.sequence.iterator();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003f, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean ensureItemIterator() {
        Iterator it = this.itemIterator;
        if ((it == null || it.hasNext()) ? false : true) {
            this.itemIterator = null;
        }
        while (true) {
            if (this.itemIterator != null) {
                break;
            }
            if (!this.iterator.hasNext()) {
                return false;
            }
            Object next = this.iterator.next();
            FlatteningSequence flatteningSequence = this.this$0;
            Iterator it2 = (Iterator) flatteningSequence.iterator.invoke(flatteningSequence.transformer.invoke(next));
            if (it2.hasNext()) {
                this.itemIterator = it2;
                break;
            }
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return ensureItemIterator();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!ensureItemIterator()) {
            throw new NoSuchElementException();
        }
        Iterator it = this.itemIterator;
        Intrinsics.checkNotNull(it);
        return it.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
