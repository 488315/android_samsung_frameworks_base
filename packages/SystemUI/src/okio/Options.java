package okio;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Options extends AbstractList implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    public final ByteString[] byteStrings;
    public final int[] trie;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static void buildTrieRecursive(long j, Buffer buffer, int i, List list, int i2, int i3, List list2) {
            int i4;
            int i5;
            int i6;
            int i7 = i;
            if (i2 >= i3) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            for (int i8 = i2; i8 < i3; i8++) {
                if (((ByteString) ((ArrayList) list).get(i8)).getSize$external__okio__android_common__okio_lib() < i7) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
            }
            ArrayList arrayList = (ArrayList) list;
            ByteString byteString = (ByteString) arrayList.get(i2);
            ByteString byteString2 = (ByteString) arrayList.get(i3 - 1);
            if (i7 == byteString.getSize$external__okio__android_common__okio_lib()) {
                int intValue = ((Number) ((ArrayList) list2).get(i2)).intValue();
                int i9 = i2 + 1;
                ByteString byteString3 = (ByteString) arrayList.get(i9);
                i4 = i9;
                i5 = intValue;
                byteString = byteString3;
            } else {
                i4 = i2;
                i5 = -1;
            }
            if (byteString.internalGet$external__okio__android_common__okio_lib(i7) == byteString2.internalGet$external__okio__android_common__okio_lib(i7)) {
                int min = Math.min(byteString.getSize$external__okio__android_common__okio_lib(), byteString2.getSize$external__okio__android_common__okio_lib());
                int i10 = 0;
                for (int i11 = i7; i11 < min && byteString.internalGet$external__okio__android_common__okio_lib(i11) == byteString2.internalGet$external__okio__android_common__okio_lib(i11); i11++) {
                    i10++;
                }
                long j2 = 4;
                long j3 = (buffer.size / j2) + j + 2 + i10 + 1;
                buffer.writeInt(-i10);
                buffer.writeInt(i5);
                int i12 = i7 + i10;
                while (i7 < i12) {
                    buffer.writeInt(byteString.internalGet$external__okio__android_common__okio_lib(i7) & 255);
                    i7++;
                }
                if (i4 + 1 == i3) {
                    if (i12 != ((ByteString) arrayList.get(i4)).getSize$external__okio__android_common__okio_lib()) {
                        throw new IllegalStateException("Check failed.");
                    }
                    buffer.writeInt(((Number) ((ArrayList) list2).get(i4)).intValue());
                    return;
                } else {
                    Buffer buffer2 = new Buffer();
                    buffer.writeInt(((int) ((buffer2.size / j2) + j3)) * (-1));
                    buildTrieRecursive(j3, buffer2, i12, list, i4, i3, list2);
                    buffer.writeAll(buffer2);
                    return;
                }
            }
            int i13 = 1;
            for (int i14 = i4 + 1; i14 < i3; i14++) {
                if (((ByteString) arrayList.get(i14 - 1)).internalGet$external__okio__android_common__okio_lib(i7) != ((ByteString) arrayList.get(i14)).internalGet$external__okio__android_common__okio_lib(i7)) {
                    i13++;
                }
            }
            long j4 = 4;
            long j5 = (buffer.size / j4) + j + 2 + (i13 * 2);
            buffer.writeInt(i13);
            buffer.writeInt(i5);
            for (int i15 = i4; i15 < i3; i15++) {
                byte internalGet$external__okio__android_common__okio_lib = ((ByteString) arrayList.get(i15)).internalGet$external__okio__android_common__okio_lib(i7);
                if (i15 == i4 || internalGet$external__okio__android_common__okio_lib != ((ByteString) arrayList.get(i15 - 1)).internalGet$external__okio__android_common__okio_lib(i7)) {
                    buffer.writeInt(internalGet$external__okio__android_common__okio_lib & 255);
                }
            }
            Buffer buffer3 = new Buffer();
            while (i4 < i3) {
                byte internalGet$external__okio__android_common__okio_lib2 = ((ByteString) arrayList.get(i4)).internalGet$external__okio__android_common__okio_lib(i7);
                int i16 = i4 + 1;
                int i17 = i16;
                while (true) {
                    if (i17 >= i3) {
                        i17 = i3;
                        break;
                    } else if (internalGet$external__okio__android_common__okio_lib2 != ((ByteString) arrayList.get(i17)).internalGet$external__okio__android_common__okio_lib(i7)) {
                        break;
                    } else {
                        i17++;
                    }
                }
                if (i16 == i17 && i7 + 1 == ((ByteString) arrayList.get(i4)).getSize$external__okio__android_common__okio_lib()) {
                    buffer.writeInt(((Number) ((ArrayList) list2).get(i4)).intValue());
                    i6 = i17;
                } else {
                    buffer.writeInt(((int) ((buffer3.size / j4) + j5)) * (-1));
                    i6 = i17;
                    buildTrieRecursive(j5, buffer3, i7 + 1, list, i4, i6, list2);
                }
                i4 = i6;
            }
            buffer.writeAll(buffer3);
        }

        private Companion() {
        }
    }

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00cc, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final okio.Options of(okio.ByteString... r12) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Options.of(okio.ByteString[]):okio.Options");
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
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
