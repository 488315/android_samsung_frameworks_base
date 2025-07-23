package com.android.framework.protobuf;

import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.WireFormat;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.io.IOException;
import java.util.List;

@CheckReturnValue
/* loaded from: classes5.dex */
final class ArrayDecoders {
    private ArrayDecoders() {
    }

    static final class Registers {
        public final ExtensionRegistryLite extensionRegistry;
        public int int1;
        public long long1;
        public Object object1;

        Registers() {
            this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
        }

        Registers(ExtensionRegistryLite extensionRegistryLite) {
            extensionRegistryLite.getClass();
            this.extensionRegistry = extensionRegistryLite;
        }
    }

    static int decodeVarint32(byte[] bArr, int i, Registers registers) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            registers.int1 = b;
            return i2;
        }
        return decodeVarint32(b, bArr, i2, registers);
    }

    static int decodeVarint32(int i, byte[] bArr, int i2, Registers registers) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            registers.int1 = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Byte.MAX_VALUE) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            registers.int1 = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & Byte.MAX_VALUE) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            registers.int1 = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & Byte.MAX_VALUE) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            registers.int1 = i9 | (b4 << SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN);
            return i10;
        }
        int i11 = i9 | ((b4 & Byte.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                registers.int1 = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int decodeVarint64(byte[] bArr, int i, Registers registers) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            registers.long1 = j;
            return i2;
        }
        return decodeVarint64(j, bArr, i2, registers);
    }

    static int decodeVarint64(long j, byte[] bArr, int i, Registers registers) {
        int i2 = i + 1;
        byte b = bArr[i];
        long j2 = (j & 127) | ((b & Byte.MAX_VALUE) << 7);
        int i3 = 7;
        while (b < 0) {
            int i4 = i2 + 1;
            byte b2 = bArr[i2];
            i3 += 7;
            j2 |= (b2 & Byte.MAX_VALUE) << i3;
            i2 = i4;
            b = b2;
        }
        registers.long1 = j2;
        return i2;
    }

    static int decodeFixed32(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static long decodeFixed64(byte[] bArr, int i) {
        return ((bArr[i + 7] & 255) << 56) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 3] & 255) << 24) | ((bArr[i + 4] & 255) << 32) | ((bArr[i + 5] & 255) << 40) | ((bArr[i + 6] & 255) << 48);
    }

    static double decodeDouble(byte[] bArr, int i) {
        return Double.longBitsToDouble(decodeFixed64(bArr, i));
    }

    static float decodeFloat(byte[] bArr, int i) {
        return Float.intBitsToFloat(decodeFixed32(bArr, i));
    }

    static int decodeString(byte[] bArr, int i, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = "";
            return decodeVarint32;
        }
        registers.object1 = new String(bArr, decodeVarint32, i2, Internal.UTF_8);
        return decodeVarint32 + i2;
    }

    static int decodeStringRequireUtf8(byte[] bArr, int i, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 == 0) {
            registers.object1 = "";
            return decodeVarint32;
        }
        registers.object1 = Utf8.decodeUtf8(bArr, decodeVarint32, i2);
        return decodeVarint32 + i2;
    }

    static int decodeBytes(byte[] bArr, int i, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1;
        if (i2 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i2 > bArr.length - decodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (i2 == 0) {
            registers.object1 = ByteString.EMPTY;
            return decodeVarint32;
        }
        registers.object1 = ByteString.copyFrom(bArr, decodeVarint32, i2);
        return decodeVarint32 + i2;
    }

    static int decodeMessageField(Schema schema, byte[] bArr, int i, int i2, Registers registers) throws IOException {
        Object newInstance = schema.newInstance();
        int mergeMessageField = mergeMessageField(newInstance, schema, bArr, i, i2, registers);
        schema.makeImmutable(newInstance);
        registers.object1 = newInstance;
        return mergeMessageField;
    }

    static int decodeGroupField(Schema schema, byte[] bArr, int i, int i2, int i3, Registers registers) throws IOException {
        Object newInstance = schema.newInstance();
        int mergeGroupField = mergeGroupField(newInstance, schema, bArr, i, i2, i3, registers);
        schema.makeImmutable(newInstance);
        registers.object1 = newInstance;
        return mergeGroupField;
    }

    static int mergeMessageField(Object obj, Schema schema, byte[] bArr, int i, int i2, Registers registers) throws IOException {
        int i3 = i + 1;
        int i4 = bArr[i];
        if (i4 < 0) {
            i3 = decodeVarint32(i4, bArr, i3, registers);
            i4 = registers.int1;
        }
        int i5 = i3;
        if (i4 < 0 || i4 > i2 - i5) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i6 = i5 + i4;
        schema.mergeFrom(obj, bArr, i5, i6, registers);
        registers.object1 = obj;
        return i6;
    }

    static int mergeGroupField(Object obj, Schema schema, byte[] bArr, int i, int i2, int i3, Registers registers) throws IOException {
        int parseProto2Message = ((MessageSchema) schema).parseProto2Message(obj, bArr, i, i2, i3, registers);
        registers.object1 = obj;
        return parseProto2Message;
    }

    static int decodeVarint32List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        intArrayList.addInt(registers.int1);
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            intArrayList.addInt(registers.int1);
        }
        return decodeVarint32;
    }

    static int decodeVarint64List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        longArrayList.addLong(registers.long1);
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(registers.long1);
        }
        return decodeVarint64;
    }

    static int decodeFixed32List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        intArrayList.addInt(decodeFixed32(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            intArrayList.addInt(decodeFixed32(bArr, decodeVarint32));
            i4 = decodeVarint32 + 4;
        }
        return i4;
    }

    static int decodeFixed64List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        longArrayList.addLong(decodeFixed64(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            longArrayList.addLong(decodeFixed64(bArr, decodeVarint32));
            i4 = decodeVarint32 + 8;
        }
        return i4;
    }

    static int decodeFloatList(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        FloatArrayList floatArrayList = (FloatArrayList) protobufList;
        floatArrayList.addFloat(decodeFloat(bArr, i2));
        int i4 = i2 + 4;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            floatArrayList.addFloat(decodeFloat(bArr, decodeVarint32));
            i4 = decodeVarint32 + 4;
        }
        return i4;
    }

    static int decodeDoubleList(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
        doubleArrayList.addDouble(decodeDouble(bArr, i2));
        int i4 = i2 + 8;
        while (i4 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, i4, registers);
            if (i != registers.int1) {
                break;
            }
            doubleArrayList.addDouble(decodeDouble(bArr, decodeVarint32));
            i4 = decodeVarint32 + 8;
        }
        return i4;
    }

    static int decodeBoolList(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        booleanArrayList.addBoolean(registers.long1 != 0);
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        return decodeVarint64;
    }

    static int decodeSInt32List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        return decodeVarint32;
    }

    static int decodeSInt64List(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int decodeVarint64 = decodeVarint64(bArr, i2, registers);
        longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        while (decodeVarint64 < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeVarint64, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint64 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        return decodeVarint64;
    }

    static int decodePackedVarint32List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint32(bArr, decodeVarint32, registers);
            intArrayList.addInt(registers.int1);
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedVarint64List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(registers.long1);
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed32List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            intArrayList.addInt(decodeFixed32(bArr, decodeVarint32));
            decodeVarint32 += 4;
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed64List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            longArrayList.addLong(decodeFixed64(bArr, decodeVarint32));
            decodeVarint32 += 8;
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFloatList(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        FloatArrayList floatArrayList = (FloatArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            floatArrayList.addFloat(decodeFloat(bArr, decodeVarint32));
            decodeVarint32 += 4;
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedDoubleList(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        DoubleArrayList doubleArrayList = (DoubleArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            doubleArrayList.addDouble(decodeDouble(bArr, decodeVarint32));
            decodeVarint32 += 8;
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedBoolList(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        BooleanArrayList booleanArrayList = (BooleanArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            booleanArrayList.addBoolean(registers.long1 != 0);
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt32List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        IntArrayList intArrayList = (IntArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint32(bArr, decodeVarint32, registers);
            intArrayList.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt64List(byte[] bArr, int i, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        LongArrayList longArrayList = (LongArrayList) protobufList;
        int decodeVarint32 = decodeVarint32(bArr, i, registers);
        int i2 = registers.int1 + decodeVarint32;
        while (decodeVarint32 < i2) {
            decodeVarint32 = decodeVarint64(bArr, decodeVarint32, registers);
            longArrayList.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (decodeVarint32 == i2) {
            return decodeVarint32;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodeStringList(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i4 == 0) {
            protobufList.add("");
        } else {
            protobufList.add(new String(bArr, decodeVarint32, i4, Internal.UTF_8));
            decodeVarint32 += i4;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i5 == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new String(bArr, decodeVarint32, i5, Internal.UTF_8));
                decodeVarint32 += i5;
            }
        }
        return decodeVarint32;
    }

    static int decodeStringListRequireUtf8(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i4 == 0) {
            protobufList.add("");
        } else {
            int i5 = decodeVarint32 + i4;
            if (!Utf8.isValidUtf8(bArr, decodeVarint32, i5)) {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            protobufList.add(new String(bArr, decodeVarint32, i4, Internal.UTF_8));
            decodeVarint32 = i5;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i6 = registers.int1;
            if (i6 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i6 == 0) {
                protobufList.add("");
            } else {
                int i7 = decodeVarint32 + i6;
                if (!Utf8.isValidUtf8(bArr, decodeVarint32, i7)) {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
                protobufList.add(new String(bArr, decodeVarint32, i6, Internal.UTF_8));
                decodeVarint32 = i7;
            }
        }
        return decodeVarint32;
    }

    static int decodeBytesList(int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) throws InvalidProtocolBufferException {
        int decodeVarint32 = decodeVarint32(bArr, i2, registers);
        int i4 = registers.int1;
        if (i4 < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        if (i4 > bArr.length - decodeVarint32) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        if (i4 == 0) {
            protobufList.add(ByteString.EMPTY);
        } else {
            protobufList.add(ByteString.copyFrom(bArr, decodeVarint32, i4));
            decodeVarint32 += i4;
        }
        while (decodeVarint32 < i3) {
            int decodeVarint322 = decodeVarint32(bArr, decodeVarint32, registers);
            if (i != registers.int1) {
                break;
            }
            decodeVarint32 = decodeVarint32(bArr, decodeVarint322, registers);
            int i5 = registers.int1;
            if (i5 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i5 > bArr.length - decodeVarint32) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i5 == 0) {
                protobufList.add(ByteString.EMPTY);
            } else {
                protobufList.add(ByteString.copyFrom(bArr, decodeVarint32, i5));
                decodeVarint32 += i5;
            }
        }
        return decodeVarint32;
    }

    static int decodeMessageList(Schema<?> schema, int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        int decodeMessageField = decodeMessageField(schema, bArr, i2, i3, registers);
        protobufList.add(registers.object1);
        while (decodeMessageField < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeMessageField, registers);
            if (i != registers.int1) {
                break;
            }
            decodeMessageField = decodeMessageField(schema, bArr, decodeVarint32, i3, registers);
            protobufList.add(registers.object1);
        }
        return decodeMessageField;
    }

    static int decodeGroupList(Schema schema, int i, byte[] bArr, int i2, int i3, Internal.ProtobufList<?> protobufList, Registers registers) throws IOException {
        int i4 = (i & (-8)) | 4;
        int decodeGroupField = decodeGroupField(schema, bArr, i2, i3, i4, registers);
        protobufList.add(registers.object1);
        while (decodeGroupField < i3) {
            int decodeVarint32 = decodeVarint32(bArr, decodeGroupField, registers);
            if (i != registers.int1) {
                break;
            }
            decodeGroupField = decodeGroupField(schema, bArr, decodeVarint32, i3, i4, registers);
            protobufList.add(registers.object1);
        }
        return decodeGroupField;
    }

    static int decodeExtensionOrUnknownField(int i, byte[] bArr, int i2, int i3, Object obj, MessageLite messageLite, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        GeneratedMessageLite.GeneratedExtension findLiteExtensionByNumber = registers.extensionRegistry.findLiteExtensionByNumber(messageLite, i >>> 3);
        if (findLiteExtensionByNumber == null) {
            return decodeUnknownField(i, bArr, i2, i3, MessageSchema.getMutableUnknownFields(obj), registers);
        }
        GeneratedMessageLite.ExtendableMessage extendableMessage = (GeneratedMessageLite.ExtendableMessage) obj;
        extendableMessage.ensureExtensionsAreMutable();
        return decodeExtension(i, bArr, i2, i3, extendableMessage, findLiteExtensionByNumber, unknownFieldSchema, registers);
    }

    static int decodeExtension(int i, byte[] bArr, int i2, int i3, GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage, GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        int i4;
        int i5;
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> fieldSet = extendableMessage.extensions;
        int i6 = i >>> 3;
        if (generatedExtension.descriptor.isRepeated() && generatedExtension.descriptor.isPacked()) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    DoubleArrayList doubleArrayList = new DoubleArrayList();
                    int decodePackedDoubleList = decodePackedDoubleList(bArr, i2, doubleArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, doubleArrayList);
                    return decodePackedDoubleList;
                case 2:
                    FloatArrayList floatArrayList = new FloatArrayList();
                    int decodePackedFloatList = decodePackedFloatList(bArr, i2, floatArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, floatArrayList);
                    return decodePackedFloatList;
                case 3:
                case 4:
                    LongArrayList longArrayList = new LongArrayList();
                    int decodePackedVarint64List = decodePackedVarint64List(bArr, i2, longArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList);
                    return decodePackedVarint64List;
                case 5:
                case 6:
                    IntArrayList intArrayList = new IntArrayList();
                    int decodePackedVarint32List = decodePackedVarint32List(bArr, i2, intArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList);
                    return decodePackedVarint32List;
                case 7:
                case 8:
                    LongArrayList longArrayList2 = new LongArrayList();
                    int decodePackedFixed64List = decodePackedFixed64List(bArr, i2, longArrayList2, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList2);
                    return decodePackedFixed64List;
                case 9:
                case 10:
                    IntArrayList intArrayList2 = new IntArrayList();
                    int decodePackedFixed32List = decodePackedFixed32List(bArr, i2, intArrayList2, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList2);
                    return decodePackedFixed32List;
                case 11:
                    BooleanArrayList booleanArrayList = new BooleanArrayList();
                    int decodePackedBoolList = decodePackedBoolList(bArr, i2, booleanArrayList, registers);
                    fieldSet.setField(generatedExtension.descriptor, booleanArrayList);
                    return decodePackedBoolList;
                case 12:
                    IntArrayList intArrayList3 = new IntArrayList();
                    int decodePackedSInt32List = decodePackedSInt32List(bArr, i2, intArrayList3, registers);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList3);
                    return decodePackedSInt32List;
                case 13:
                    LongArrayList longArrayList3 = new LongArrayList();
                    int decodePackedSInt64List = decodePackedSInt64List(bArr, i2, longArrayList3, registers);
                    fieldSet.setField(generatedExtension.descriptor, longArrayList3);
                    return decodePackedSInt64List;
                case 14:
                    IntArrayList intArrayList4 = new IntArrayList();
                    int decodePackedVarint32List2 = decodePackedVarint32List(bArr, i2, intArrayList4, registers);
                    SchemaUtil.filterUnknownEnumList((Object) extendableMessage, i6, (List<Integer>) intArrayList4, generatedExtension.descriptor.getEnumType(), (Object) null, (UnknownFieldSchema<UT, Object>) unknownFieldSchema);
                    fieldSet.setField(generatedExtension.descriptor, intArrayList4);
                    return decodePackedVarint32List2;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + generatedExtension.descriptor.getLiteType());
            }
        }
        Object obj = null;
        if (generatedExtension.getLiteType() == WireFormat.FieldType.ENUM) {
            i2 = decodeVarint32(bArr, i2, registers);
            if (generatedExtension.descriptor.getEnumType().findValueByNumber(registers.int1) == null) {
                SchemaUtil.storeUnknownEnum(extendableMessage, i6, registers.int1, null, unknownFieldSchema);
                return i2;
            }
            obj = Integer.valueOf(registers.int1);
        } else {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    i4 = i2;
                    obj = Double.valueOf(decodeDouble(bArr, i4));
                    i2 = i4 + 8;
                    break;
                case 2:
                    i5 = i2;
                    obj = Float.valueOf(decodeFloat(bArr, i5));
                    i2 = i5 + 4;
                    break;
                case 3:
                case 4:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj = Long.valueOf(registers.long1);
                    break;
                case 5:
                case 6:
                    i2 = decodeVarint32(bArr, i2, registers);
                    obj = Integer.valueOf(registers.int1);
                    break;
                case 7:
                case 8:
                    i4 = i2;
                    obj = Long.valueOf(decodeFixed64(bArr, i4));
                    i2 = i4 + 8;
                    break;
                case 9:
                case 10:
                    i5 = i2;
                    obj = Integer.valueOf(decodeFixed32(bArr, i5));
                    i2 = i5 + 4;
                    break;
                case 11:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj = Boolean.valueOf(registers.long1 != 0);
                    break;
                case 12:
                    i2 = decodeVarint32(bArr, i2, registers);
                    obj = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                    break;
                case 13:
                    i2 = decodeVarint64(bArr, i2, registers);
                    obj = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                    break;
                case 14:
                    throw new IllegalStateException("Shouldn't reach here.");
                case 15:
                    i2 = decodeBytes(bArr, i2, registers);
                    obj = registers.object1;
                    break;
                case 16:
                    i2 = decodeString(bArr, i2, registers);
                    obj = registers.object1;
                    break;
                case 17:
                    int i7 = (i6 << 3) | 4;
                    Schema schemaFor = Protobuf.getInstance().schemaFor((Class) generatedExtension.getMessageDefaultInstance().getClass());
                    if (generatedExtension.isRepeated()) {
                        int decodeGroupField = decodeGroupField(schemaFor, bArr, i2, i3, i7, registers);
                        fieldSet.addRepeatedField(generatedExtension.descriptor, registers.object1);
                        return decodeGroupField;
                    }
                    Object field = fieldSet.getField(generatedExtension.descriptor);
                    if (field == null) {
                        field = schemaFor.newInstance();
                        fieldSet.setField(generatedExtension.descriptor, field);
                    }
                    return mergeGroupField(field, schemaFor, bArr, i2, i3, i7, registers);
                case 18:
                    Schema schemaFor2 = Protobuf.getInstance().schemaFor((Class) generatedExtension.getMessageDefaultInstance().getClass());
                    if (generatedExtension.isRepeated()) {
                        int decodeMessageField = decodeMessageField(schemaFor2, bArr, i2, i3, registers);
                        fieldSet.addRepeatedField(generatedExtension.descriptor, registers.object1);
                        return decodeMessageField;
                    }
                    Object field2 = fieldSet.getField(generatedExtension.descriptor);
                    if (field2 == null) {
                        field2 = schemaFor2.newInstance();
                        fieldSet.setField(generatedExtension.descriptor, field2);
                    }
                    return mergeMessageField(field2, schemaFor2, bArr, i2, i3, registers);
            }
        }
        if (generatedExtension.isRepeated()) {
            fieldSet.addRepeatedField(generatedExtension.descriptor, obj);
            return i2;
        }
        fieldSet.setField(generatedExtension.descriptor, obj);
        return i2;
    }

    /* renamed from: com.android.framework.protobuf.ArrayDecoders$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT32.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED64.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BOOL.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    static int decodeUnknownField(int i, byte[] bArr, int i2, int i3, UnknownFieldSetLite unknownFieldSetLite, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        int tagWireType = WireFormat.getTagWireType(i);
        if (tagWireType == 0) {
            int decodeVarint64 = decodeVarint64(bArr, i2, registers);
            unknownFieldSetLite.storeField(i, Long.valueOf(registers.long1));
            return decodeVarint64;
        }
        if (tagWireType == 1) {
            unknownFieldSetLite.storeField(i, Long.valueOf(decodeFixed64(bArr, i2)));
            return i2 + 8;
        }
        if (tagWireType == 2) {
            int decodeVarint32 = decodeVarint32(bArr, i2, registers);
            int i4 = registers.int1;
            if (i4 < 0) {
                throw InvalidProtocolBufferException.negativeSize();
            }
            if (i4 > bArr.length - decodeVarint32) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            if (i4 == 0) {
                unknownFieldSetLite.storeField(i, ByteString.EMPTY);
            } else {
                unknownFieldSetLite.storeField(i, ByteString.copyFrom(bArr, decodeVarint32, i4));
            }
            return decodeVarint32 + i4;
        }
        if (tagWireType != 3) {
            if (tagWireType == 5) {
                unknownFieldSetLite.storeField(i, Integer.valueOf(decodeFixed32(bArr, i2)));
                return i2 + 4;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }
        UnknownFieldSetLite newInstance = UnknownFieldSetLite.newInstance();
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int decodeVarint322 = decodeVarint32(bArr, i2, registers);
            i6 = registers.int1;
            if (i6 == i5) {
                i2 = decodeVarint322;
                break;
            }
            i2 = decodeUnknownField(i6, bArr, decodeVarint322, i3, newInstance, registers);
        }
        if (i2 > i3 || i6 != i5) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        unknownFieldSetLite.storeField(i, newInstance);
        return i2;
    }

    static int skipField(int i, byte[] bArr, int i2, int i3, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(i) == 0) {
            throw InvalidProtocolBufferException.invalidTag();
        }
        int tagWireType = WireFormat.getTagWireType(i);
        if (tagWireType == 0) {
            return decodeVarint64(bArr, i2, registers);
        }
        if (tagWireType == 1) {
            return i2 + 8;
        }
        if (tagWireType == 2) {
            return decodeVarint32(bArr, i2, registers) + registers.int1;
        }
        if (tagWireType != 3) {
            if (tagWireType == 5) {
                return i2 + 4;
            }
            throw InvalidProtocolBufferException.invalidTag();
        }
        int i4 = (i & (-8)) | 4;
        int i5 = 0;
        while (i2 < i3) {
            i2 = decodeVarint32(bArr, i2, registers);
            i5 = registers.int1;
            if (i5 == i4) {
                break;
            }
            i2 = skipField(i5, bArr, i2, i3, registers);
        }
        if (i2 > i3 || i5 != i4) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return i2;
    }
}
