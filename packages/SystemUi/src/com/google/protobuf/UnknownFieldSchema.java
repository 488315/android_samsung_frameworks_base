package com.google.protobuf;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class UnknownFieldSchema {
    public abstract void addFixed32(int i, int i2, Object obj);

    public abstract void addFixed64(int i, long j, Object obj);

    public abstract void addGroup(int i, Object obj, Object obj2);

    public abstract void addLengthDelimited(Object obj, int i, ByteString byteString);

    public abstract void addVarint(int i, long j, Object obj);

    public abstract UnknownFieldSetLite getBuilderFromMessage(Object obj);

    public abstract UnknownFieldSetLite getFromMessage(Object obj);

    public abstract int getSerializedSize(Object obj);

    public abstract int getSerializedSizeAsMessageSet(Object obj);

    public abstract void makeImmutable(Object obj);

    public abstract UnknownFieldSetLite merge(Object obj, Object obj2);

    public final boolean mergeOneFieldFrom(Object obj, Reader reader) {
        CodedInputStreamReader codedInputStreamReader = (CodedInputStreamReader) reader;
        int i = codedInputStreamReader.tag;
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            addVarint(i2, codedInputStreamReader.readInt64(), obj);
            return true;
        }
        if (i3 == 1) {
            addFixed64(i2, codedInputStreamReader.readFixed64(), obj);
            return true;
        }
        if (i3 == 2) {
            addLengthDelimited(obj, i2, codedInputStreamReader.readBytes());
            return true;
        }
        if (i3 != 3) {
            if (i3 == 4) {
                return false;
            }
            if (i3 != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            addFixed32(i2, codedInputStreamReader.readFixed32(), obj);
            return true;
        }
        UnknownFieldSetLite newBuilder = newBuilder();
        int i4 = (i2 << 3) | 4;
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE && mergeOneFieldFrom(newBuilder, codedInputStreamReader)) {
        }
        if (i4 != codedInputStreamReader.tag) {
            throw new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
        }
        addGroup(i2, obj, toImmutable(newBuilder));
        return true;
    }

    public abstract UnknownFieldSetLite newBuilder();

    public abstract void setBuilderToMessage(Object obj, Object obj2);

    public abstract void setToMessage(Object obj, Object obj2);

    public abstract void shouldDiscardUnknownFields();

    public abstract UnknownFieldSetLite toImmutable(Object obj);

    public abstract void writeAsMessageSetTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);

    public abstract void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);
}
