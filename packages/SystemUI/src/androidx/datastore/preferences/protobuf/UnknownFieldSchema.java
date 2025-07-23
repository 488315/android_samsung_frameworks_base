package androidx.datastore.preferences.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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

    public final boolean mergeOneFieldFrom(int i, CodedInputStreamReader codedInputStreamReader, Object obj) {
        int i2 = codedInputStreamReader.tag;
        int i3 = i2 >>> 3;
        int i4 = i2 & 7;
        CodedInputStream codedInputStream = codedInputStreamReader.input;
        if (i4 == 0) {
            codedInputStreamReader.requireWireType(0);
            addVarint(i3, codedInputStream.readInt64(), obj);
            return true;
        }
        if (i4 == 1) {
            codedInputStreamReader.requireWireType(1);
            addFixed64(i3, codedInputStream.readFixed64(), obj);
            return true;
        }
        if (i4 == 2) {
            addLengthDelimited(obj, i3, codedInputStreamReader.readBytes());
            return true;
        }
        if (i4 != 3) {
            if (i4 == 4) {
                return false;
            }
            if (i4 != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            codedInputStreamReader.requireWireType(5);
            addFixed32(i3, codedInputStream.readFixed32(), obj);
            return true;
        }
        UnknownFieldSetLite newBuilder = newBuilder();
        int i5 = (i3 << 3) | 4;
        int i6 = i + 1;
        if (i6 >= 100) {
            throw new InvalidProtocolBufferException("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
        }
        while (codedInputStreamReader.getFieldNumber() != Integer.MAX_VALUE && mergeOneFieldFrom(i6, codedInputStreamReader, newBuilder)) {
        }
        if (i5 != codedInputStreamReader.tag) {
            throw new InvalidProtocolBufferException("Protocol message end-group tag did not match expected tag.");
        }
        addGroup(i3, obj, toImmutable(newBuilder));
        return true;
    }

    public abstract UnknownFieldSetLite newBuilder();

    public abstract void setBuilderToMessage(Object obj, Object obj2);

    public abstract void setToMessage(Object obj, Object obj2);

    public abstract UnknownFieldSetLite toImmutable(Object obj);

    public abstract void writeAsMessageSetTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);

    public abstract void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);
}
