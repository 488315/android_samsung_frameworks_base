package com.google.protobuf;

import java.nio.charset.Charset;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CodedInputStreamReader implements Reader {
    public int endGroupTag;
    public final CodedInputStream input;
    public int nextTag = 0;
    public int tag;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.CodedInputStreamReader$1 */
    public abstract /* synthetic */ class AbstractC45261 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat$FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat$FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.ENUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FLOAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT32.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.MESSAGE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT32.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT64.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.STRING.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT32.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private CodedInputStreamReader(CodedInputStream codedInputStream) {
        Charset charset = Internal.UTF_8;
        if (codedInputStream == null) {
            throw new NullPointerException("input");
        }
        this.input = codedInputStream;
        codedInputStream.wrapper = this;
    }

    public static CodedInputStreamReader forCodedInput(CodedInputStream codedInputStream) {
        CodedInputStreamReader codedInputStreamReader = codedInputStream.wrapper;
        return codedInputStreamReader != null ? codedInputStreamReader : new CodedInputStreamReader(codedInputStream);
    }

    public static void verifyPackedFixed32Length(int i) {
        if ((i & 3) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    public static void verifyPackedFixed64Length(int i) {
        if ((i & 7) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    public final int getFieldNumber() {
        int i = this.nextTag;
        if (i != 0) {
            this.tag = i;
            this.nextTag = 0;
        } else {
            this.tag = this.input.readTag();
        }
        int i2 = this.tag;
        if (i2 == 0 || i2 == this.endGroupTag) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final void mergeGroupFieldInternal(Object obj, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int i = this.endGroupTag;
        this.endGroupTag = ((this.tag >>> 3) << 3) | 4;
        try {
            schema.mergeFrom(obj, this, extensionRegistryLite);
            if (this.tag == this.endGroupTag) {
            } else {
                throw InvalidProtocolBufferException.parseFailure();
            }
        } finally {
            this.endGroupTag = i;
        }
    }

    public final void mergeMessageFieldInternal(Object obj, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        CodedInputStream codedInputStream = this.input;
        int readUInt32 = codedInputStream.readUInt32();
        if (codedInputStream.recursionDepth >= codedInputStream.recursionLimit) {
            throw new InvalidProtocolBufferException("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
        }
        int pushLimit = codedInputStream.pushLimit(readUInt32);
        codedInputStream.recursionDepth++;
        schema.mergeFrom(obj, this, extensionRegistryLite);
        codedInputStream.checkLastTagWas(0);
        codedInputStream.recursionDepth--;
        codedInputStream.popLimit(pushLimit);
    }

    public final void readBoolList(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof BooleanArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Boolean.valueOf(codedInputStream.readBool()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Boolean.valueOf(codedInputStream.readBool()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        BooleanArrayList booleanArrayList = (BooleanArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                booleanArrayList.addBoolean(codedInputStream.readBool());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            booleanArrayList.addBoolean(codedInputStream.readBool());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final ByteString readBytes() {
        requireWireType(2);
        return this.input.readBytes();
    }

    public final void readBytesList(List list) {
        int readTag;
        if ((this.tag & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(readBytes());
            CodedInputStream codedInputStream = this.input;
            if (codedInputStream.isAtEnd()) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    public final void readDoubleList(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof DoubleArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    list.add(Double.valueOf(codedInputStream.readDouble()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int readUInt32 = codedInputStream.readUInt32();
            verifyPackedFixed64Length(readUInt32);
            int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
            do {
                list.add(Double.valueOf(codedInputStream.readDouble()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            return;
        }
        DoubleArrayList doubleArrayList = (DoubleArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                doubleArrayList.addDouble(codedInputStream.readDouble());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int readUInt322 = codedInputStream.readUInt32();
        verifyPackedFixed64Length(readUInt322);
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
        do {
            doubleArrayList.addDouble(codedInputStream.readDouble());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
    }

    public final void readEnumList(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(codedInputStream.readEnum()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Integer.valueOf(codedInputStream.readEnum()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                intArrayList.addInt(codedInputStream.readEnum());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            intArrayList.addInt(codedInputStream.readEnum());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final Object readField(WireFormat$FieldType wireFormat$FieldType, Class cls, ExtensionRegistryLite extensionRegistryLite) {
        int i = AbstractC45261.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()];
        CodedInputStream codedInputStream = this.input;
        switch (i) {
            case 1:
                requireWireType(0);
                return Boolean.valueOf(codedInputStream.readBool());
            case 2:
                return readBytes();
            case 3:
                requireWireType(1);
                return Double.valueOf(codedInputStream.readDouble());
            case 4:
                requireWireType(0);
                return Integer.valueOf(codedInputStream.readEnum());
            case 5:
                return Integer.valueOf(readFixed32());
            case 6:
                return Long.valueOf(readFixed64());
            case 7:
                requireWireType(5);
                return Float.valueOf(codedInputStream.readFloat());
            case 8:
                return Integer.valueOf(readInt32());
            case 9:
                return Long.valueOf(readInt64());
            case 10:
                requireWireType(2);
                Schema schemaFor = Protobuf.INSTANCE.schemaFor(cls);
                GeneratedMessageLite newInstance = schemaFor.newInstance();
                mergeMessageFieldInternal(newInstance, schemaFor, extensionRegistryLite);
                schemaFor.makeImmutable(newInstance);
                return newInstance;
            case 11:
                requireWireType(5);
                return Integer.valueOf(codedInputStream.readSFixed32());
            case 12:
                requireWireType(1);
                return Long.valueOf(codedInputStream.readSFixed64());
            case 13:
                requireWireType(0);
                return Integer.valueOf(codedInputStream.readSInt32());
            case 14:
                requireWireType(0);
                return Long.valueOf(codedInputStream.readSInt64());
            case 15:
                requireWireType(2);
                return codedInputStream.readStringRequireUtf8();
            case 16:
                return Integer.valueOf(readUInt32());
            case 17:
                requireWireType(0);
                return Long.valueOf(codedInputStream.readUInt64());
            default:
                throw new IllegalArgumentException("unsupported field type.");
        }
    }

    public final int readFixed32() {
        requireWireType(5);
        return this.input.readFixed32();
    }

    public final void readFixed32List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 2) {
                int readUInt32 = codedInputStream.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
                do {
                    list.add(Integer.valueOf(codedInputStream.readFixed32()));
                } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (i != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(Integer.valueOf(codedInputStream.readFixed32()));
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag = codedInputStream.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int readUInt322 = codedInputStream.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
            do {
                intArrayList.addInt(codedInputStream.readFixed32());
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (i2 != 5) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            intArrayList.addInt(codedInputStream.readFixed32());
            if (codedInputStream.isAtEnd()) {
                return;
            } else {
                readTag2 = codedInputStream.readTag();
            }
        } while (readTag2 == this.tag);
        this.nextTag = readTag2;
    }

    public final long readFixed64() {
        requireWireType(1);
        return this.input.readFixed64();
    }

    public final void readFixed64List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof LongArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(codedInputStream.readFixed64()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int readUInt32 = codedInputStream.readUInt32();
            verifyPackedFixed64Length(readUInt32);
            int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
            do {
                list.add(Long.valueOf(codedInputStream.readFixed64()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                longArrayList.addLong(codedInputStream.readFixed64());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int readUInt322 = codedInputStream.readUInt32();
        verifyPackedFixed64Length(readUInt322);
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
        do {
            longArrayList.addLong(codedInputStream.readFixed64());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
    }

    public final void readFloatList(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof FloatArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 2) {
                int readUInt32 = codedInputStream.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
                do {
                    list.add(Float.valueOf(codedInputStream.readFloat()));
                } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (i != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(Float.valueOf(codedInputStream.readFloat()));
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag = codedInputStream.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        FloatArrayList floatArrayList = (FloatArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int readUInt322 = codedInputStream.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
            do {
                floatArrayList.addFloat(codedInputStream.readFloat());
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (i2 != 5) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            floatArrayList.addFloat(codedInputStream.readFloat());
            if (codedInputStream.isAtEnd()) {
                return;
            } else {
                readTag2 = codedInputStream.readTag();
            }
        } while (readTag2 == this.tag);
        this.nextTag = readTag2;
    }

    public final int readInt32() {
        requireWireType(0);
        return this.input.readInt32();
    }

    public final void readInt32List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(codedInputStream.readInt32()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Integer.valueOf(codedInputStream.readInt32()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                intArrayList.addInt(codedInputStream.readInt32());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            intArrayList.addInt(codedInputStream.readInt32());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final long readInt64() {
        requireWireType(0);
        return this.input.readInt64();
    }

    public final void readInt64List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof LongArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(codedInputStream.readInt64()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Long.valueOf(codedInputStream.readInt64()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                longArrayList.addLong(codedInputStream.readInt64());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            longArrayList.addLong(codedInputStream.readInt64());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final void readSFixed32List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 2) {
                int readUInt32 = codedInputStream.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
                do {
                    list.add(Integer.valueOf(codedInputStream.readSFixed32()));
                } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (i != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                list.add(Integer.valueOf(codedInputStream.readSFixed32()));
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag = codedInputStream.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 2) {
            int readUInt322 = codedInputStream.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
            do {
                intArrayList.addInt(codedInputStream.readSFixed32());
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (i2 != 5) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            intArrayList.addInt(codedInputStream.readSFixed32());
            if (codedInputStream.isAtEnd()) {
                return;
            } else {
                readTag2 = codedInputStream.readTag();
            }
        } while (readTag2 == this.tag);
        this.nextTag = readTag2;
    }

    public final void readSFixed64List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof LongArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 1) {
                do {
                    list.add(Long.valueOf(codedInputStream.readSFixed64()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int readUInt32 = codedInputStream.readUInt32();
            verifyPackedFixed64Length(readUInt32);
            int totalBytesRead = codedInputStream.getTotalBytesRead() + readUInt32;
            do {
                list.add(Long.valueOf(codedInputStream.readSFixed64()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 1) {
            do {
                longArrayList.addLong(codedInputStream.readSFixed64());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int readUInt322 = codedInputStream.readUInt32();
        verifyPackedFixed64Length(readUInt322);
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + readUInt322;
        do {
            longArrayList.addLong(codedInputStream.readSFixed64());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
    }

    public final void readSInt32List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(codedInputStream.readSInt32()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Integer.valueOf(codedInputStream.readSInt32()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                intArrayList.addInt(codedInputStream.readSInt32());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            intArrayList.addInt(codedInputStream.readSInt32());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final void readSInt64List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof LongArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(codedInputStream.readSInt64()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Long.valueOf(codedInputStream.readSInt64()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                longArrayList.addLong(codedInputStream.readSInt64());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            longArrayList.addLong(codedInputStream.readSInt64());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final void readStringListInternal(List list, boolean z) {
        String readString;
        int readTag;
        int readTag2;
        if ((this.tag & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        boolean z2 = list instanceof LazyStringList;
        CodedInputStream codedInputStream = this.input;
        if (z2 && !z) {
            LazyStringList lazyStringList = (LazyStringList) list;
            do {
                lazyStringList.add(readBytes());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        do {
            if (z) {
                requireWireType(2);
                readString = codedInputStream.readStringRequireUtf8();
            } else {
                requireWireType(2);
                readString = codedInputStream.readString();
            }
            list.add(readString);
            if (codedInputStream.isAtEnd()) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    public final int readUInt32() {
        requireWireType(0);
        return this.input.readUInt32();
    }

    public final void readUInt32List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof IntArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Integer.valueOf(codedInputStream.readUInt32()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Integer.valueOf(codedInputStream.readUInt32()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                intArrayList.addInt(codedInputStream.readUInt32());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            intArrayList.addInt(codedInputStream.readUInt32());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final void readUInt64List(List list) {
        int readTag;
        int readTag2;
        boolean z = list instanceof LongArrayList;
        CodedInputStream codedInputStream = this.input;
        if (!z) {
            int i = this.tag & 7;
            if (i == 0) {
                do {
                    list.add(Long.valueOf(codedInputStream.readUInt64()));
                    if (codedInputStream.isAtEnd()) {
                        return;
                    } else {
                        readTag = codedInputStream.readTag();
                    }
                } while (readTag == this.tag);
                this.nextTag = readTag;
                return;
            }
            if (i != 2) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            int totalBytesRead = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
            do {
                list.add(Long.valueOf(codedInputStream.readUInt64()));
            } while (codedInputStream.getTotalBytesRead() < totalBytesRead);
            requirePosition(totalBytesRead);
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i2 = this.tag & 7;
        if (i2 == 0) {
            do {
                longArrayList.addLong(codedInputStream.readUInt64());
                if (codedInputStream.isAtEnd()) {
                    return;
                } else {
                    readTag2 = codedInputStream.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        if (i2 != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int totalBytesRead2 = codedInputStream.getTotalBytesRead() + codedInputStream.readUInt32();
        do {
            longArrayList.addLong(codedInputStream.readUInt64());
        } while (codedInputStream.getTotalBytesRead() < totalBytesRead2);
        requirePosition(totalBytesRead2);
    }

    public final void requirePosition(int i) {
        if (this.input.getTotalBytesRead() != i) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    public final void requireWireType(int i) {
        if ((this.tag & 7) != i) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public final boolean skipField() {
        int i;
        CodedInputStream codedInputStream = this.input;
        if (codedInputStream.isAtEnd() || (i = this.tag) == this.endGroupTag) {
            return false;
        }
        return codedInputStream.skipField(i);
    }
}
