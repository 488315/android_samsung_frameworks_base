package com.google.protobuf;

import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnknownFieldSetLite {
    public static final UnknownFieldSetLite DEFAULT_INSTANCE = new UnknownFieldSetLite(0, new int[0], new Object[0], false);
    public int count;
    public boolean isMutable;
    public int memoizedSerializedSize;
    public Object[] objects;
    public int[] tags;

    private UnknownFieldSetLite() {
        this(0, new int[8], new Object[8], true);
    }

    public static UnknownFieldSetLite mutableCopyOf(UnknownFieldSetLite unknownFieldSetLite, UnknownFieldSetLite unknownFieldSetLite2) {
        int i = unknownFieldSetLite.count + unknownFieldSetLite2.count;
        int[] copyOf = Arrays.copyOf(unknownFieldSetLite.tags, i);
        System.arraycopy(unknownFieldSetLite2.tags, 0, copyOf, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        Object[] copyOf2 = Arrays.copyOf(unknownFieldSetLite.objects, i);
        System.arraycopy(unknownFieldSetLite2.objects, 0, copyOf2, unknownFieldSetLite.count, unknownFieldSetLite2.count);
        return new UnknownFieldSetLite(i, copyOf, copyOf2, true);
    }

    public static UnknownFieldSetLite newInstance() {
        return new UnknownFieldSetLite();
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.tags;
        if (i > iArr.length) {
            int i2 = this.count;
            int i3 = (i2 / 2) + i2;
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.tags = Arrays.copyOf(iArr, i);
            this.objects = Arrays.copyOf(this.objects, i);
        }
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnknownFieldSetLite)) {
            return false;
        }
        UnknownFieldSetLite unknownFieldSetLite = (UnknownFieldSetLite) obj;
        int i = this.count;
        if (i == unknownFieldSetLite.count) {
            int[] iArr = this.tags;
            int[] iArr2 = unknownFieldSetLite.tags;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    z = true;
                    break;
                }
                if (iArr[i2] != iArr2[i2]) {
                    z = false;
                    break;
                }
                i2++;
            }
            if (z) {
                Object[] objArr = this.objects;
                Object[] objArr2 = unknownFieldSetLite.objects;
                int i3 = this.count;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    }
                    if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    }
                    i4++;
                }
                if (z2) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int getSerializedSize() {
        int computeTagSize;
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.count; i3++) {
            int i4 = this.tags[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                computeTagSize = CodedOutputStream.computeTagSize(i5) + CodedOutputStream.computeUInt64SizeNoTag(((Long) this.objects[i3]).longValue());
            } else if (i6 == 1) {
                ((Long) this.objects[i3]).longValue();
                computeTagSize = CodedOutputStream.computeFixed64Size(i5);
            } else if (i6 == 2) {
                computeTagSize = CodedOutputStream.computeBytesSize(i5, (ByteString) this.objects[i3]);
            } else if (i6 == 3) {
                i2 = ((UnknownFieldSetLite) this.objects[i3]).getSerializedSize() + (CodedOutputStream.computeTagSize(i5) * 2) + i2;
            } else {
                if (i6 != 5) {
                    throw new IllegalStateException(InvalidProtocolBufferException.invalidWireType());
                }
                ((Integer) this.objects[i3]).intValue();
                computeTagSize = CodedOutputStream.computeFixed32Size(i5);
            }
            i2 = computeTagSize + i2;
        }
        this.memoizedSerializedSize = i2;
        return i2;
    }

    public final int hashCode() {
        int i = this.count;
        int i2 = (527 + i) * 31;
        int[] iArr = this.tags;
        int i3 = 17;
        int i4 = 17;
        for (int i5 = 0; i5 < i; i5++) {
            i4 = (i4 * 31) + iArr[i5];
        }
        int i6 = (i2 + i4) * 31;
        Object[] objArr = this.objects;
        int i7 = this.count;
        for (int i8 = 0; i8 < i7; i8++) {
            i3 = (i3 * 31) + objArr[i8].hashCode();
        }
        return i6 + i3;
    }

    public final void storeField(int i, Object obj) {
        if (!this.isMutable) {
            throw new UnsupportedOperationException();
        }
        ensureCapacity(this.count + 1);
        int[] iArr = this.tags;
        int i2 = this.count;
        iArr[i2] = i;
        this.objects[i2] = obj;
        this.count = i2 + 1;
    }

    public final void writeTo(CodedOutputStreamWriter codedOutputStreamWriter) {
        if (this.count == 0) {
            return;
        }
        codedOutputStreamWriter.getClass();
        Writer$FieldOrder writer$FieldOrder = Writer$FieldOrder.ASCENDING;
        for (int i = 0; i < this.count; i++) {
            int i2 = this.tags[i];
            Object obj = this.objects[i];
            int i3 = i2 >>> 3;
            int i4 = i2 & 7;
            if (i4 == 0) {
                codedOutputStreamWriter.writeInt64(i3, ((Long) obj).longValue());
            } else if (i4 == 1) {
                codedOutputStreamWriter.writeFixed64(i3, ((Long) obj).longValue());
            } else if (i4 == 2) {
                codedOutputStreamWriter.writeBytes(i3, (ByteString) obj);
            } else if (i4 == 3) {
                Writer$FieldOrder writer$FieldOrder2 = Writer$FieldOrder.ASCENDING;
                codedOutputStreamWriter.writeStartGroup(i3);
                ((UnknownFieldSetLite) obj).writeTo(codedOutputStreamWriter);
                codedOutputStreamWriter.writeEndGroup(i3);
            } else {
                if (i4 != 5) {
                    throw new RuntimeException(InvalidProtocolBufferException.invalidWireType());
                }
                codedOutputStreamWriter.writeFixed32(i3, ((Integer) obj).intValue());
            }
        }
    }

    private UnknownFieldSetLite(int i, int[] iArr, Object[] objArr, boolean z) {
        this.memoizedSerializedSize = -1;
        this.count = i;
        this.tags = iArr;
        this.objects = objArr;
        this.isMutable = z;
    }
}
