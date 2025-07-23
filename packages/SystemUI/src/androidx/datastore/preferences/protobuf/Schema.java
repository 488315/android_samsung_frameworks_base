package androidx.datastore.preferences.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Schema {
    boolean equals(GeneratedMessageLite generatedMessageLite, GeneratedMessageLite generatedMessageLite2);

    int getSerializedSize(AbstractMessageLite abstractMessageLite);

    int hashCode(GeneratedMessageLite generatedMessageLite);

    boolean isInitialized(Object obj);

    void makeImmutable(Object obj);

    void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite);

    void mergeFrom(Object obj, Object obj2);

    Object newInstance();

    void writeTo(Object obj, CodedOutputStreamWriter codedOutputStreamWriter);
}
