package com.google.protobuf;

/* loaded from: classes4.dex */
public final class CodedOutputStreamWriter {
    public final CodedOutputStream output;

    private CodedOutputStreamWriter(CodedOutputStream codedOutputStream) {
        Internal.checkNotNull(codedOutputStream, "output");
        this.output = codedOutputStream;
        codedOutputStream.wrapper = this;
    }

    public static CodedOutputStreamWriter forCodedOutput(CodedOutputStream codedOutputStream) {
        CodedOutputStreamWriter codedOutputStreamWriter = codedOutputStream.wrapper;
        return codedOutputStreamWriter != null ? codedOutputStreamWriter : new CodedOutputStreamWriter(codedOutputStream);
    }

    public final void writeBool(int i, boolean z) {
        this.output.writeBool(i, z);
    }

    public final void writeBytes(int i, ByteString byteString) {
        this.output.writeBytes(i, byteString);
    }

    public final void writeDouble(double d, int i) {
        CodedOutputStream codedOutputStream = this.output;
        codedOutputStream.getClass();
        codedOutputStream.writeFixed64(i, Double.doubleToRawLongBits(d));
    }

    public final void writeEnum(int i, int i2) {
        this.output.writeInt32(i, i2);
    }

    public final void writeFixed32(int i, int i2) {
        this.output.writeFixed32(i, i2);
    }

    public final void writeFixed64(int i, long j) {
        this.output.writeFixed64(i, j);
    }

    public final void writeFloat(float f, int i) {
        CodedOutputStream codedOutputStream = this.output;
        codedOutputStream.getClass();
        codedOutputStream.writeFixed32(i, Float.floatToRawIntBits(f));
    }

    public final void writeGroup(int i, Object obj, Schema schema) {
        CodedOutputStream codedOutputStream = this.output;
        codedOutputStream.writeTag(i, 3);
        schema.writeTo((MessageLite) obj, codedOutputStream.wrapper);
        codedOutputStream.writeTag(i, 4);
    }

    public final void writeInt32(int i, int i2) {
        this.output.writeInt32(i, i2);
    }

    public final void writeInt64(int i, long j) {
        this.output.writeUInt64(i, j);
    }

    public final void writeMessage(int i, Object obj, Schema schema) {
        this.output.writeMessage(i, (MessageLite) obj, schema);
    }

    public final void writeMessageSetItem(int i, Object obj) {
        boolean z = obj instanceof ByteString;
        CodedOutputStream codedOutputStream = this.output;
        if (z) {
            codedOutputStream.writeRawMessageSetExtension(i, (ByteString) obj);
        } else {
            codedOutputStream.writeMessageSetExtension(i, (MessageLite) obj);
        }
    }

    public final void writeSFixed32(int i, int i2) {
        this.output.writeFixed32(i, i2);
    }

    public final void writeSFixed64(int i, long j) {
        this.output.writeFixed64(i, j);
    }

    public final void writeSInt32(int i, int i2) {
        CodedOutputStream codedOutputStream = this.output;
        codedOutputStream.writeUInt32(i, (i2 >> 31) ^ (i2 << 1));
    }

    public final void writeSInt64(int i, long j) {
        CodedOutputStream codedOutputStream = this.output;
        codedOutputStream.writeUInt64(i, (j >> 63) ^ (j << 1));
    }

    public final void writeUInt32(int i, int i2) {
        this.output.writeUInt32(i, i2);
    }

    public final void writeUInt64(int i, long j) {
        this.output.writeUInt64(i, j);
    }
}
