package androidx.compose.runtime.snapshots;

import androidx.collection.MutableLongList;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.SequencesKt__SequenceBuilderKt;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SnapshotIdSet implements Iterable<Long>, KMappedMarker {
    public static final Companion Companion = new Companion(null);
    public static final SnapshotIdSet EMPTY = new SnapshotIdSet(0, 0, 0, null);
    public final long[] belowBound;
    public final long lowerBound;
    public final long lowerSet;
    public final long upperSet;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private SnapshotIdSet(long j, long j2, long j3, long[] jArr) {
        this.upperSet = j;
        this.lowerSet = j2;
        this.lowerBound = j3;
        this.belowBound = jArr;
    }

    public final SnapshotIdSet andNot(SnapshotIdSet snapshotIdSet) {
        SnapshotIdSet snapshotIdSet2 = EMPTY;
        if (snapshotIdSet == snapshotIdSet2) {
            return this;
        }
        if (this == snapshotIdSet2) {
            return snapshotIdSet2;
        }
        long j = snapshotIdSet.lowerBound;
        long j2 = this.lowerBound;
        if (j == j2) {
            long[] jArr = snapshotIdSet.belowBound;
            long[] jArr2 = this.belowBound;
            if (jArr == jArr2) {
                return new SnapshotIdSet((~snapshotIdSet.upperSet) & this.upperSet, this.lowerSet & (~snapshotIdSet.lowerSet), j2, jArr2);
            }
        }
        long[] jArr3 = snapshotIdSet.belowBound;
        if (jArr3 != null) {
            for (long j3 : jArr3) {
                this = this.clear(j3);
            }
        }
        if (snapshotIdSet.lowerSet != 0) {
            for (int i = 0; i < 64; i++) {
                if ((snapshotIdSet.lowerSet & (1 << i)) != 0) {
                    this = this.clear(snapshotIdSet.lowerBound + i);
                }
            }
        }
        if (snapshotIdSet.upperSet != 0) {
            for (int i2 = 0; i2 < 64; i2++) {
                if ((snapshotIdSet.upperSet & (1 << i2)) != 0) {
                    this = this.clear(snapshotIdSet.lowerBound + i2 + 64);
                }
            }
        }
        return this;
    }

    public final SnapshotIdSet clear(long j) {
        long[] jArr;
        int binarySearch;
        long[] jArr2;
        long j2 = this.lowerBound;
        long j3 = j - j2;
        if (j3 >= 0 && j3 < 64) {
            long j4 = 1 << ((int) j3);
            long j5 = this.lowerSet;
            if ((j5 & j4) != 0) {
                return new SnapshotIdSet(this.upperSet, j5 & (~j4), j2, this.belowBound);
            }
        } else if (j3 >= 64 && j3 < 128) {
            long j6 = 1 << (((int) j3) - 64);
            long j7 = this.upperSet;
            if ((j7 & j6) != 0) {
                return new SnapshotIdSet(j7 & (~j6), this.lowerSet, j2, this.belowBound);
            }
        } else if (j3 < 0 && (jArr = this.belowBound) != null && (binarySearch = SnapshotId_jvmKt.binarySearch(jArr, j)) >= 0) {
            long j8 = this.upperSet;
            long j9 = this.lowerSet;
            long j10 = this.lowerBound;
            int length = jArr.length;
            int i = length - 1;
            if (i == 0) {
                jArr2 = null;
            } else {
                jArr2 = new long[i];
                if (binarySearch > 0) {
                    ArraysKt___ArraysJvmKt.copyInto(jArr, jArr2, 0, 0, binarySearch);
                }
                if (binarySearch < i) {
                    ArraysKt___ArraysJvmKt.copyInto(jArr, jArr2, binarySearch, binarySearch + 1, length);
                }
            }
            return new SnapshotIdSet(j8, j9, j10, jArr2);
        }
        return this;
    }

    public final boolean get(long j) {
        long[] jArr;
        long j2 = j - this.lowerBound;
        return (j2 < 0 || j2 >= 64) ? (j2 < 64 || j2 >= 128) ? j2 <= 0 && (jArr = this.belowBound) != null && SnapshotId_jvmKt.binarySearch(jArr, j) >= 0 : ((1 << (((int) j2) + (-64))) & this.upperSet) != 0 : ((1 << ((int) j2)) & this.lowerSet) != 0;
    }

    @Override // java.lang.Iterable
    public final Iterator<Long> iterator() {
        return SequencesKt__SequenceBuilderKt.iterator(new SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1(new SnapshotIdSet$iterator$1(this, null)).$block$inlined);
    }

    public final SnapshotIdSet or(SnapshotIdSet snapshotIdSet) {
        SnapshotIdSet snapshotIdSet2 = EMPTY;
        if (snapshotIdSet == snapshotIdSet2) {
            return this;
        }
        if (this == snapshotIdSet2) {
            return snapshotIdSet;
        }
        long j = snapshotIdSet.lowerBound;
        long j2 = this.lowerBound;
        if (j == j2) {
            long[] jArr = snapshotIdSet.belowBound;
            long[] jArr2 = this.belowBound;
            if (jArr == jArr2) {
                return new SnapshotIdSet(snapshotIdSet.upperSet | this.upperSet, this.lowerSet | snapshotIdSet.lowerSet, j2, jArr2);
            }
        }
        long[] jArr3 = this.belowBound;
        int i = 0;
        if (jArr3 == null) {
            if (jArr3 != null) {
                for (long j3 : jArr3) {
                    snapshotIdSet = snapshotIdSet.set(j3);
                }
            }
            if (this.lowerSet != 0) {
                for (int i2 = 0; i2 < 64; i2++) {
                    if ((this.lowerSet & (1 << i2)) != 0) {
                        snapshotIdSet = snapshotIdSet.set(this.lowerBound + i2);
                    }
                }
            }
            if (this.upperSet != 0) {
                while (i < 64) {
                    if ((this.upperSet & (1 << i)) != 0) {
                        snapshotIdSet = snapshotIdSet.set(this.lowerBound + i + 64);
                    }
                    i++;
                }
            }
            return snapshotIdSet;
        }
        long[] jArr4 = snapshotIdSet.belowBound;
        if (jArr4 != null) {
            for (long j4 : jArr4) {
                this = this.set(j4);
            }
        }
        if (snapshotIdSet.lowerSet != 0) {
            for (int i3 = 0; i3 < 64; i3++) {
                if ((snapshotIdSet.lowerSet & (1 << i3)) != 0) {
                    this = this.set(snapshotIdSet.lowerBound + i3);
                }
            }
        }
        if (snapshotIdSet.upperSet != 0) {
            while (i < 64) {
                if ((snapshotIdSet.upperSet & (1 << i)) != 0) {
                    this = this.set(snapshotIdSet.lowerBound + i + 64);
                }
                i++;
            }
        }
        return this;
    }

    public final SnapshotIdSet set(long j) {
        long j2;
        long j3;
        long[] jArr;
        long j4 = this.lowerBound;
        long j5 = j - j4;
        if (j5 < 0 || j5 >= 64) {
            int i = 64;
            if (j5 >= 64 && j5 < 128) {
                long j6 = 1 << (((int) j5) - 64);
                long j7 = this.upperSet;
                if ((j7 & j6) == 0) {
                    return new SnapshotIdSet(j7 | j6, this.lowerSet, j4, this.belowBound);
                }
            } else if (j5 < 128) {
                long[] jArr2 = this.belowBound;
                if (jArr2 == null) {
                    return new SnapshotIdSet(this.upperSet, this.lowerSet, j4, new long[]{j});
                }
                int binarySearch = SnapshotId_jvmKt.binarySearch(jArr2, j);
                if (binarySearch < 0) {
                    int i2 = -(binarySearch + 1);
                    int length = jArr2.length;
                    long[] jArr3 = new long[length + 1];
                    ArraysKt___ArraysJvmKt.copyInto(jArr2, jArr3, 0, 0, i2);
                    ArraysKt___ArraysJvmKt.copyInto(jArr2, jArr3, 1 + i2, i2, length);
                    jArr3[i2] = j;
                    return new SnapshotIdSet(this.upperSet, this.lowerSet, this.lowerBound, jArr3);
                }
            } else if (!get(j)) {
                long j8 = this.upperSet;
                long j9 = this.lowerSet;
                long j10 = this.lowerBound;
                long j11 = 64;
                long j12 = ((j + 1) / j11) * j11;
                if (j12 < 0) {
                    j12 = 9223372036854775680L;
                }
                long[] jArr4 = null;
                long j13 = j8;
                SnapshotIdArrayBuilder snapshotIdArrayBuilder = null;
                while (true) {
                    if (j10 >= j12) {
                        j2 = j9;
                        j3 = j10;
                        break;
                    }
                    if (j9 != 0) {
                        if (snapshotIdArrayBuilder == null) {
                            snapshotIdArrayBuilder = new SnapshotIdArrayBuilder(this.belowBound);
                        }
                        int i3 = 0;
                        while (i3 < i) {
                            long j14 = j9;
                            if ((j9 & (1 << i3)) != 0) {
                                snapshotIdArrayBuilder.list.add(i3 + j10);
                            }
                            i3++;
                            j9 = j14;
                            i = 64;
                        }
                    }
                    if (j13 == 0) {
                        j3 = j12;
                        j2 = 0;
                        break;
                    }
                    j10 += j11;
                    j9 = j13;
                    i = 64;
                    j13 = 0;
                }
                if (snapshotIdArrayBuilder != null) {
                    MutableLongList mutableLongList = snapshotIdArrayBuilder.list;
                    int i4 = mutableLongList._size;
                    if (i4 != 0) {
                        long[] jArr5 = new long[i4];
                        long[] jArr6 = mutableLongList.content;
                        for (int i5 = 0; i5 < i4; i5++) {
                            jArr5[i5] = jArr6[i5];
                        }
                        jArr4 = jArr5;
                    }
                    if (jArr4 != null) {
                        jArr = jArr4;
                        return new SnapshotIdSet(j13, j2, j3, jArr).set(j);
                    }
                }
                jArr = this.belowBound;
                return new SnapshotIdSet(j13, j2, j3, jArr).set(j);
            }
        } else {
            long j15 = 1 << ((int) j5);
            long j16 = this.lowerSet;
            if ((j16 & j15) == 0) {
                return new SnapshotIdSet(this.upperSet, j16 | j15, j4, this.belowBound);
            }
        }
        return this;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [");
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(this, 10));
        Iterator<Long> it = iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next().longValue()));
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) "");
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = arrayList.get(i2);
            i++;
            if (i > 1) {
                sb2.append((CharSequence) ", ");
            }
            if (obj != null ? obj instanceof CharSequence : true) {
                sb2.append((CharSequence) obj);
            } else if (obj instanceof Character) {
                sb2.append(((Character) obj).charValue());
            } else {
                sb2.append((CharSequence) String.valueOf(obj));
            }
        }
        sb2.append((CharSequence) "");
        sb.append(sb2.toString());
        sb.append(']');
        return sb.toString();
    }
}
