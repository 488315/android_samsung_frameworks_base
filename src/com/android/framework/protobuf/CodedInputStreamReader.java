package com.android.framework.protobuf;

import com.android.framework.protobuf.WireFormat;
import java.io.IOException;
import java.util.List;

@CheckReturnValue
/* loaded from: classes5.dex */
final class CodedInputStreamReader implements Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;
    private static final int NEXT_TAG_UNSET = 0;
    private int endGroupTag;
    private final CodedInputStream input;
    private int nextTag = 0;
    private int tag;

    public static CodedInputStreamReader forCodedInput(CodedInputStream codedInputStream) {
        if (codedInputStream.wrapper != null) {
            return codedInputStream.wrapper;
        }
        return new CodedInputStreamReader(codedInputStream);
    }

    private CodedInputStreamReader(CodedInputStream codedInputStream) {
        CodedInputStream codedInputStream2 = (CodedInputStream) Internal.checkNotNull(codedInputStream, "input");
        this.input = codedInputStream2;
        codedInputStream2.wrapper = this;
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean shouldDiscardUnknownFields() {
        return this.input.shouldDiscardUnknownFields();
    }

    @Override // com.android.framework.protobuf.Reader
    public int getFieldNumber() throws IOException {
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
        return WireFormat.getTagFieldNumber(i2);
    }

    @Override // com.android.framework.protobuf.Reader
    public int getTag() {
        return this.tag;
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean skipField() throws IOException {
        int i;
        if (this.input.isAtEnd() || (i = this.tag) == this.endGroupTag) {
            return false;
        }
        return this.input.skipField(i);
    }

    private void requireWireType(int i) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != i) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    @Override // com.android.framework.protobuf.Reader
    public double readDouble() throws IOException {
        requireWireType(1);
        return this.input.readDouble();
    }

    @Override // com.android.framework.protobuf.Reader
    public float readFloat() throws IOException {
        requireWireType(5);
        return this.input.readFloat();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readUInt64() throws IOException {
        requireWireType(0);
        return this.input.readUInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readInt64() throws IOException {
        requireWireType(0);
        return this.input.readInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readInt32() throws IOException {
        requireWireType(0);
        return this.input.readInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readFixed64() throws IOException {
        requireWireType(1);
        return this.input.readFixed64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readFixed32() throws IOException {
        requireWireType(5);
        return this.input.readFixed32();
    }

    @Override // com.android.framework.protobuf.Reader
    public boolean readBool() throws IOException {
        requireWireType(0);
        return this.input.readBool();
    }

    @Override // com.android.framework.protobuf.Reader
    public String readString() throws IOException {
        requireWireType(2);
        return this.input.readString();
    }

    @Override // com.android.framework.protobuf.Reader
    public String readStringRequireUtf8() throws IOException {
        requireWireType(2);
        return this.input.readStringRequireUtf8();
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> T readMessage(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(2);
        return (T) readMessage(Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(2);
        return (T) readMessage(schema, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Reader
    @Deprecated
    public <T> T readGroup(Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(3);
        return (T) readGroup(Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Reader
    @Deprecated
    public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(3);
        return (T) readGroup(schema, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> void mergeMessageField(T t, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(2);
        mergeMessageFieldInternal(t, schema, extensionRegistryLite);
    }

    private <T> void mergeMessageFieldInternal(T t, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readUInt32 = this.input.readUInt32();
        if (this.input.recursionDepth >= this.input.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int pushLimit = this.input.pushLimit(readUInt32);
        this.input.recursionDepth++;
        schema.mergeFrom(t, this, extensionRegistryLite);
        this.input.checkLastTagWas(0);
        CodedInputStream codedInputStream = this.input;
        codedInputStream.recursionDepth--;
        this.input.popLimit(pushLimit);
    }

    private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        T newInstance = schema.newInstance();
        mergeMessageFieldInternal(newInstance, schema, extensionRegistryLite);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> void mergeGroupField(T t, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        requireWireType(3);
        mergeGroupFieldInternal(t, schema, extensionRegistryLite);
    }

    private <T> void mergeGroupFieldInternal(T t, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int i = this.endGroupTag;
        this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
        try {
            schema.mergeFrom(t, this, extensionRegistryLite);
            if (this.tag == this.endGroupTag) {
            } else {
                throw InvalidProtocolBufferException.parseFailure();
            }
        } finally {
            this.endGroupTag = i;
        }
    }

    private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        T newInstance = schema.newInstance();
        mergeGroupFieldInternal(newInstance, schema, extensionRegistryLite);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    @Override // com.android.framework.protobuf.Reader
    public ByteString readBytes() throws IOException {
        requireWireType(2);
        return this.input.readBytes();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readUInt32() throws IOException {
        requireWireType(0);
        return this.input.readUInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readEnum() throws IOException {
        requireWireType(0);
        return this.input.readEnum();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readSFixed32() throws IOException {
        requireWireType(5);
        return this.input.readSFixed32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readSFixed64() throws IOException {
        requireWireType(1);
        return this.input.readSFixed64();
    }

    @Override // com.android.framework.protobuf.Reader
    public int readSInt32() throws IOException {
        requireWireType(0);
        return this.input.readSInt32();
    }

    @Override // com.android.framework.protobuf.Reader
    public long readSInt64() throws IOException {
        requireWireType(0);
        return this.input.readSInt64();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readDoubleList(List<Double> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof DoubleArrayList) {
            DoubleArrayList doubleArrayList = (DoubleArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 1) {
                do {
                    doubleArrayList.addDouble(this.input.readDouble());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    doubleArrayList.addDouble(this.input.readDouble());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 1) {
            do {
                list.add(Double.valueOf(this.input.readDouble()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed64Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Double.valueOf(this.input.readDouble()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFloatList(List<Float> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof FloatArrayList) {
            FloatArrayList floatArrayList = (FloatArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    floatArrayList.addFloat(this.input.readFloat());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (tagWireType == 5) {
                do {
                    floatArrayList.addFloat(this.input.readFloat());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Float.valueOf(this.input.readFloat()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (tagWireType2 == 5) {
            do {
                list.add(Float.valueOf(this.input.readFloat()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readUInt64List(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    longArrayList.addLong(this.input.readUInt64());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    longArrayList.addLong(this.input.readUInt64());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Long.valueOf(this.input.readUInt64()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Long.valueOf(this.input.readUInt64()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readInt64List(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    longArrayList.addLong(this.input.readInt64());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    longArrayList.addLong(this.input.readInt64());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Long.valueOf(this.input.readInt64()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Long.valueOf(this.input.readInt64()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readInt32List(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    intArrayList.addInt(this.input.readInt32());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    intArrayList.addInt(this.input.readInt32());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Integer.valueOf(this.input.readInt32()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Integer.valueOf(this.input.readInt32()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFixed64List(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 1) {
                do {
                    longArrayList.addLong(this.input.readFixed64());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    longArrayList.addLong(this.input.readFixed64());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 1) {
            do {
                list.add(Long.valueOf(this.input.readFixed64()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed64Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Long.valueOf(this.input.readFixed64()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readFixed32List(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    intArrayList.addInt(this.input.readFixed32());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (tagWireType == 5) {
                do {
                    intArrayList.addInt(this.input.readFixed32());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Integer.valueOf(this.input.readFixed32()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (tagWireType2 == 5) {
            do {
                list.add(Integer.valueOf(this.input.readFixed32()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readBoolList(List<Boolean> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof BooleanArrayList) {
            BooleanArrayList booleanArrayList = (BooleanArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    booleanArrayList.addBoolean(this.input.readBool());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    booleanArrayList.addBoolean(this.input.readBool());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Boolean.valueOf(this.input.readBool()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Boolean.valueOf(this.input.readBool()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readStringList(List<String> list) throws IOException {
        readStringListInternal(list, false);
    }

    @Override // com.android.framework.protobuf.Reader
    public void readStringListRequireUtf8(List<String> list) throws IOException {
        readStringListInternal(list, true);
    }

    public void readStringListInternal(List<String> list, boolean z) throws IOException {
        int readTag;
        int readTag2;
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        if ((list instanceof LazyStringList) && !z) {
            LazyStringList lazyStringList = (LazyStringList) list;
            do {
                lazyStringList.add(readBytes());
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag2 = this.input.readTag();
                }
            } while (readTag2 == this.tag);
            this.nextTag = readTag2;
            return;
        }
        do {
            list.add(z ? readStringRequireUtf8() : readString());
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public <T> void readMessageList(List<T> list, Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        readMessageList(list, Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Reader
    public <T> void readMessageList(List<T> list, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int i = this.tag;
        do {
            list.add(readMessage(schema, extensionRegistryLite));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == i);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    @Deprecated
    public <T> void readGroupList(List<T> list, Class<T> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        readGroupList(list, Protobuf.getInstance().schemaFor((Class) cls), extensionRegistryLite);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Reader
    @Deprecated
    public <T> void readGroupList(List<T> list, Schema<T> schema, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.tag) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int i = this.tag;
        do {
            list.add(readGroup(schema, extensionRegistryLite));
            if (this.input.isAtEnd() || this.nextTag != 0) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == i);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readBytesList(List<ByteString> list) throws IOException {
        int readTag;
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            list.add(readBytes());
            if (this.input.isAtEnd()) {
                return;
            } else {
                readTag = this.input.readTag();
            }
        } while (readTag == this.tag);
        this.nextTag = readTag;
    }

    @Override // com.android.framework.protobuf.Reader
    public void readUInt32List(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    intArrayList.addInt(this.input.readUInt32());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    intArrayList.addInt(this.input.readUInt32());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Integer.valueOf(this.input.readUInt32()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Integer.valueOf(this.input.readUInt32()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readEnumList(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    intArrayList.addInt(this.input.readEnum());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    intArrayList.addInt(this.input.readEnum());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Integer.valueOf(this.input.readEnum()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Integer.valueOf(this.input.readEnum()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSFixed32List(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed32Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    intArrayList.addInt(this.input.readSFixed32());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            if (tagWireType == 5) {
                do {
                    intArrayList.addInt(this.input.readSFixed32());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed32Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Integer.valueOf(this.input.readSFixed32()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        if (tagWireType2 == 5) {
            do {
                list.add(Integer.valueOf(this.input.readSFixed32()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSFixed64List(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 1) {
                do {
                    longArrayList.addLong(this.input.readSFixed64());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int readUInt32 = this.input.readUInt32();
                verifyPackedFixed64Length(readUInt32);
                int totalBytesRead = this.input.getTotalBytesRead() + readUInt32;
                do {
                    longArrayList.addLong(this.input.readSFixed64());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 1) {
            do {
                list.add(Long.valueOf(this.input.readSFixed64()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int readUInt322 = this.input.readUInt32();
            verifyPackedFixed64Length(readUInt322);
            int totalBytesRead2 = this.input.getTotalBytesRead() + readUInt322;
            do {
                list.add(Long.valueOf(this.input.readSFixed64()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSInt32List(List<Integer> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    intArrayList.addInt(this.input.readSInt32());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    intArrayList.addInt(this.input.readSInt32());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Integer.valueOf(this.input.readSInt32()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Integer.valueOf(this.input.readSInt32()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Override // com.android.framework.protobuf.Reader
    public void readSInt64List(List<Long> list) throws IOException {
        int readTag;
        int readTag2;
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            int tagWireType = WireFormat.getTagWireType(this.tag);
            if (tagWireType == 0) {
                do {
                    longArrayList.addLong(this.input.readSInt64());
                    if (this.input.isAtEnd()) {
                        return;
                    } else {
                        readTag2 = this.input.readTag();
                    }
                } while (readTag2 == this.tag);
                this.nextTag = readTag2;
                return;
            }
            if (tagWireType == 2) {
                int totalBytesRead = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    longArrayList.addLong(this.input.readSInt64());
                } while (this.input.getTotalBytesRead() < totalBytesRead);
                requirePosition(totalBytesRead);
                return;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }
        int tagWireType2 = WireFormat.getTagWireType(this.tag);
        if (tagWireType2 == 0) {
            do {
                list.add(Long.valueOf(this.input.readSInt64()));
                if (this.input.isAtEnd()) {
                    return;
                } else {
                    readTag = this.input.readTag();
                }
            } while (readTag == this.tag);
            this.nextTag = readTag;
            return;
        }
        if (tagWireType2 == 2) {
            int totalBytesRead2 = this.input.getTotalBytesRead() + this.input.readUInt32();
            do {
                list.add(Long.valueOf(this.input.readSInt64()));
            } while (this.input.getTotalBytesRead() < totalBytesRead2);
            requirePosition(totalBytesRead2);
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    private void verifyPackedFixed64Length(int i) throws IOException {
        if ((i & 7) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x005c, code lost:
    
        r8.put(r2, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0064, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <K, V> void readMap(java.util.Map<K, V> r8, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> r9, com.android.framework.protobuf.ExtensionRegistryLite r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 2
            r7.requireWireType(r0)
            com.android.framework.protobuf.CodedInputStream r1 = r7.input
            int r1 = r1.readUInt32()
            com.android.framework.protobuf.CodedInputStream r2 = r7.input
            int r1 = r2.pushLimit(r1)
            K r2 = r9.defaultKey
            V r3 = r9.defaultValue
        L14:
            int r4 = r7.getFieldNumber()     // Catch: java.lang.Throwable -> L65
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L5c
            com.android.framework.protobuf.CodedInputStream r5 = r7.input     // Catch: java.lang.Throwable -> L65
            boolean r5 = r5.isAtEnd()     // Catch: java.lang.Throwable -> L65
            if (r5 == 0) goto L26
            goto L5c
        L26:
            r5 = 1
            java.lang.String r6 = "Unable to parse map entry."
            if (r4 == r5) goto L47
            if (r4 == r0) goto L3a
            boolean r4 = r7.skipField()     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            if (r4 == 0) goto L34
            goto L14
        L34:
            com.android.framework.protobuf.InvalidProtocolBufferException r4 = new com.android.framework.protobuf.InvalidProtocolBufferException     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            r4.<init>(r6)     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            throw r4     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
        L3a:
            com.android.framework.protobuf.WireFormat$FieldType r4 = r9.valueType     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            V r5 = r9.defaultValue     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            java.lang.Class r5 = r5.getClass()     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            java.lang.Object r3 = r7.readField(r4, r5, r10)     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            goto L14
        L47:
            com.android.framework.protobuf.WireFormat$FieldType r4 = r9.keyType     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            r5 = 0
            java.lang.Object r2 = r7.readField(r4, r5, r5)     // Catch: com.android.framework.protobuf.InvalidProtocolBufferException.InvalidWireTypeException -> L4f java.lang.Throwable -> L65
            goto L14
        L4f:
            boolean r4 = r7.skipField()     // Catch: java.lang.Throwable -> L65
            if (r4 == 0) goto L56
            goto L14
        L56:
            com.android.framework.protobuf.InvalidProtocolBufferException r8 = new com.android.framework.protobuf.InvalidProtocolBufferException     // Catch: java.lang.Throwable -> L65
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L65
            throw r8     // Catch: java.lang.Throwable -> L65
        L5c:
            r8.put(r2, r3)     // Catch: java.lang.Throwable -> L65
            com.android.framework.protobuf.CodedInputStream r7 = r7.input
            r7.popLimit(r1)
            return
        L65:
            r8 = move-exception
            com.android.framework.protobuf.CodedInputStream r7 = r7.input
            r7.popLimit(r1)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.CodedInputStreamReader.readMap(java.util.Map, com.android.framework.protobuf.MapEntryLite$Metadata, com.android.framework.protobuf.ExtensionRegistryLite):void");
    }

    /* renamed from: com.android.framework.protobuf.CodedInputStreamReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.ENUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FLOAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT32.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.MESSAGE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT32.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT64.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.STRING.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT32.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private Object readField(WireFormat.FieldType fieldType, Class<?> cls, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                return Boolean.valueOf(readBool());
            case 2:
                return readBytes();
            case 3:
                return Double.valueOf(readDouble());
            case 4:
                return Integer.valueOf(readEnum());
            case 5:
                return Integer.valueOf(readFixed32());
            case 6:
                return Long.valueOf(readFixed64());
            case 7:
                return Float.valueOf(readFloat());
            case 8:
                return Integer.valueOf(readInt32());
            case 9:
                return Long.valueOf(readInt64());
            case 10:
                return readMessage(cls, extensionRegistryLite);
            case 11:
                return Integer.valueOf(readSFixed32());
            case 12:
                return Long.valueOf(readSFixed64());
            case 13:
                return Integer.valueOf(readSInt32());
            case 14:
                return Long.valueOf(readSInt64());
            case 15:
                return readStringRequireUtf8();
            case 16:
                return Integer.valueOf(readUInt32());
            case 17:
                return Long.valueOf(readUInt64());
            default:
                throw new IllegalArgumentException("unsupported field type.");
        }
    }

    private void verifyPackedFixed32Length(int i) throws IOException {
        if ((i & 3) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    private void requirePosition(int i) throws IOException {
        if (this.input.getTotalBytesRead() != i) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }
}
