package androidx.compose.runtime.external.kotlinx.collections.immutable;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import java.util.Collection;
import java.util.List;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ImmutableList<E> extends List<E>, Collection, KMappedMarker {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SubList<E> extends AbstractList implements ImmutableList<E> {
        public final int _size;
        public final int fromIndex;
        public final ImmutableList source;

        public SubList(ImmutableList<? extends E> immutableList, int i, int i2) {
            this.source = immutableList;
            this.fromIndex = i;
            ListImplementation.checkRangeIndexes$runtime_release(i, i2, immutableList.size());
            this._size = i2 - i;
        }

        @Override // java.util.List
        public final Object get(int i) {
            ListImplementation.checkElementIndex$runtime_release(i, this._size);
            return this.source.get(this.fromIndex + i);
        }

        @Override // kotlin.collections.AbstractCollection
        public final int getSize() {
            return this._size;
        }

        @Override // kotlin.collections.AbstractList, java.util.List
        public final List subList(int i, int i2) {
            ListImplementation.checkRangeIndexes$runtime_release(i, i2, this._size);
            ImmutableList immutableList = this.source;
            int i3 = this.fromIndex;
            return new SubList(immutableList, i + i3, i3 + i2);
        }
    }

    @Override // java.util.List
    default ImmutableList subList(int i, int i2) {
        return new SubList(this, i, i2);
    }
}
