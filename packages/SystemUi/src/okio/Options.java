package okio;

import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Options extends AbstractList implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    public final ByteString[] byteStrings;
    public final int[] trie;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void buildTrieRecursive(long j, Buffer buffer, int i, List list, int i2, int i3, List list2) {
            int i4;
            int i5;
            int i6;
            int i7;
            Buffer buffer2;
            long j2;
            int i8 = i;
            if (!(i2 < i3)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i9 = i2; i9 < i3; i9++) {
                if (!(((ByteString) ((ArrayList) list).get(i9)).getSize$okio() >= i8)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ArrayList arrayList = (ArrayList) list;
            ByteString byteString = (ByteString) arrayList.get(i2);
            ByteString byteString2 = (ByteString) arrayList.get(i3 - 1);
            int i10 = -1;
            if (i8 == byteString.getSize$okio()) {
                int intValue = ((Number) list2.get(i2)).intValue();
                int i11 = i2 + 1;
                ByteString byteString3 = (ByteString) arrayList.get(i11);
                i4 = i11;
                i5 = intValue;
                byteString = byteString3;
            } else {
                i4 = i2;
                i5 = -1;
            }
            if (byteString.internalGet$okio(i8) == byteString2.internalGet$okio(i8)) {
                int min = Math.min(byteString.getSize$okio(), byteString2.getSize$okio());
                int i12 = 0;
                for (int i13 = i8; i13 < min && byteString.internalGet$okio(i13) == byteString2.internalGet$okio(i13); i13++) {
                    i12++;
                }
                long j3 = 4;
                long j4 = (buffer.size / j3) + j + 2 + i12 + 1;
                buffer.writeInt(-i12);
                buffer.writeInt(i5);
                int i14 = i8 + i12;
                while (i8 < i14) {
                    buffer.writeInt(byteString.internalGet$okio(i8) & 255);
                    i8++;
                }
                if (i4 + 1 == i3) {
                    if (!(i14 == ((ByteString) arrayList.get(i4)).getSize$okio())) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    buffer.writeInt(((Number) list2.get(i4)).intValue());
                    return;
                } else {
                    Buffer buffer3 = new Buffer();
                    buffer.writeInt(((int) ((buffer3.size / j3) + j4)) * (-1));
                    buildTrieRecursive(j4, buffer3, i14, list, i4, i3, list2);
                    while (buffer3.read(buffer, 8192) != -1) {
                    }
                    return;
                }
            }
            int i15 = 1;
            for (int i16 = i4 + 1; i16 < i3; i16++) {
                if (((ByteString) arrayList.get(i16 - 1)).internalGet$okio(i8) != ((ByteString) arrayList.get(i16)).internalGet$okio(i8)) {
                    i15++;
                }
            }
            long j5 = 4;
            long j6 = (i15 * 2) + (buffer.size / j5) + j + 2;
            buffer.writeInt(i15);
            buffer.writeInt(i5);
            for (int i17 = i4; i17 < i3; i17++) {
                byte internalGet$okio = ((ByteString) arrayList.get(i17)).internalGet$okio(i8);
                if (i17 == i4 || internalGet$okio != ((ByteString) arrayList.get(i17 - 1)).internalGet$okio(i8)) {
                    buffer.writeInt(internalGet$okio & 255);
                }
            }
            Buffer buffer4 = new Buffer();
            while (i4 < i3) {
                byte internalGet$okio2 = ((ByteString) arrayList.get(i4)).internalGet$okio(i8);
                int i18 = i4 + 1;
                int i19 = i18;
                while (true) {
                    if (i19 >= i3) {
                        i6 = i3;
                        break;
                    } else {
                        if (internalGet$okio2 != ((ByteString) arrayList.get(i19)).internalGet$okio(i8)) {
                            i6 = i19;
                            break;
                        }
                        i19++;
                    }
                }
                if (i18 == i6 && i8 + 1 == ((ByteString) arrayList.get(i4)).getSize$okio()) {
                    buffer.writeInt(((Number) list2.get(i4)).intValue());
                    i7 = i6;
                    buffer2 = buffer4;
                    j2 = j5;
                } else {
                    buffer.writeInt(((int) ((buffer4.size / j5) + j6)) * i10);
                    i7 = i6;
                    buffer2 = buffer4;
                    j2 = j5;
                    buildTrieRecursive(j6, buffer4, i8 + 1, list, i4, i7, list2);
                }
                buffer4 = buffer2;
                i4 = i7;
                j5 = j2;
                i10 = -1;
            }
            while (buffer4.read(buffer, 8192) != -1) {
            }
        }
    }

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:76:0x0138, code lost:
    
        continue;
     */
    /* renamed from: of */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Options m299of(ByteString... byteStringArr) {
        int i;
        int i2;
        Companion.getClass();
        boolean z = false;
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (byteStringArr.length == 0) {
            return new Options(new ByteString[0], new int[]{0, -1}, defaultConstructorMarker);
        }
        List mutableList = ArraysKt___ArraysKt.toMutableList(byteStringArr);
        ArrayList arrayList = (ArrayList) mutableList;
        if (arrayList.size() > 1) {
            Collections.sort(mutableList);
        }
        ArrayList arrayList2 = new ArrayList(byteStringArr.length);
        for (ByteString byteString : byteStringArr) {
            arrayList2.add(-1);
        }
        Object[] array = arrayList2.toArray(new Integer[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        Integer[] numArr = (Integer[]) array;
        List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf((Integer[]) Arrays.copyOf(numArr, numArr.length));
        int length = byteStringArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            ByteString byteString2 = byteStringArr[i3];
            int i5 = i4 + 1;
            int size = arrayList.size();
            int size2 = arrayList.size();
            if (size < 0) {
                throw new IllegalArgumentException(LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("fromIndex (0) is greater than toIndex (", size, ")."));
            }
            if (size > size2) {
                throw new IndexOutOfBoundsException(SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("toIndex (", size, ") is greater than size (", size2, ")."));
            }
            int i6 = size - 1;
            int i7 = 0;
            while (true) {
                if (i7 > i6) {
                    i2 = -(i7 + 1);
                    break;
                }
                i2 = (i7 + i6) >>> 1;
                int compareValues = ComparisonsKt__ComparisonsKt.compareValues((Comparable) arrayList.get(i2), byteString2);
                if (compareValues < 0) {
                    i7 = i2 + 1;
                } else if (compareValues > 0) {
                    i6 = i2 - 1;
                }
            }
            mutableListOf.set(i2, Integer.valueOf(i4));
            i3++;
            i4 = i5;
        }
        if (!(((ByteString) arrayList.get(0)).getSize$okio() > 0)) {
            throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
        }
        int i8 = 0;
        while (i8 < arrayList.size()) {
            ByteString byteString3 = (ByteString) arrayList.get(i8);
            int i9 = i8 + 1;
            int i10 = i9;
            while (i10 < arrayList.size()) {
                ByteString byteString4 = (ByteString) arrayList.get(i10);
                byteString4.getClass();
                if (!byteString4.rangeEquals(byteString3, byteString3.getSize$okio())) {
                    break;
                }
                if (!(byteString4.getSize$okio() != byteString3.getSize$okio())) {
                    throw new IllegalArgumentException(("duplicate option: " + byteString4).toString());
                }
                if (((Number) mutableListOf.get(i10)).intValue() > ((Number) mutableListOf.get(i8)).intValue()) {
                    arrayList.remove(i10);
                    mutableListOf.remove(i10);
                } else {
                    i10++;
                }
            }
            i8 = i9;
        }
        Buffer buffer = new Buffer();
        Companion.buildTrieRecursive(0L, buffer, 0, mutableList, 0, arrayList.size(), mutableListOf);
        int[] iArr = new int[(int) (buffer.size / 4)];
        boolean z2 = true;
        int i11 = 0;
        while (true) {
            long j = buffer.size;
            if (j == 0) {
                z = z2;
            }
            if (z) {
                return new Options((ByteString[]) Arrays.copyOf(byteStringArr, byteStringArr.length), iArr, defaultConstructorMarker);
            }
            int i12 = i11 + 1;
            if (j < 4) {
                throw new EOFException();
            }
            Segment segment = buffer.head;
            if (segment == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            int i13 = segment.pos;
            int i14 = segment.limit;
            if (i14 - i13 < 4) {
                i = ((buffer.readByte() & 255) << 24) | ((buffer.readByte() & 255) << 16) | ((buffer.readByte() & 255) << 8) | (buffer.readByte() & 255);
            } else {
                int i15 = i13 + 1;
                byte[] bArr = segment.data;
                int i16 = i15 + 1;
                int i17 = ((bArr[i13] & 255) << 24) | ((bArr[i15] & 255) << 16);
                int i18 = i16 + 1;
                int i19 = i17 | ((bArr[i16] & 255) << 8);
                int i20 = i18 + 1;
                int i21 = i19 | (bArr[i18] & 255);
                buffer.size = j - 4;
                if (i20 == i14) {
                    buffer.head = segment.pop();
                    SegmentPool.INSTANCE.recycle(segment);
                } else {
                    segment.pos = i20;
                }
                i = i21;
            }
            iArr[i11] = i;
            z2 = true;
            i11 = i12;
            z = false;
        }
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return super.contains((ByteString) obj);
        }
        return false;
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.byteStrings[i];
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return super.indexOf((ByteString) obj);
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return super.lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }
}
