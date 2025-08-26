package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
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

    public final void writeBytes(int i, ByteString byteString) {
        this.output.writeBytes(i, byteString);
    }

    public final void writeFixed32(int i, int i2) {
        this.output.writeFixed32(i, i2);
    }

    public final void writeFixed64(int i, long j) {
        this.output.writeFixed64(i, j);
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
}
