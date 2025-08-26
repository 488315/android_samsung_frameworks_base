package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ExtensionSchema {
    public abstract int extensionNumber(Map.Entry entry);

    public abstract GeneratedMessageLite.GeneratedExtension findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i);

    public abstract FieldSet getExtensions(Object obj);

    public abstract FieldSet getMutableExtensions(Object obj);

    public abstract boolean hasExtensions(MessageLite messageLite);

    public abstract void makeImmutable(Object obj);

    public abstract Object parseExtension(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj2);

    public abstract void parseLengthPrefixedMessageSetItem(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    public abstract void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    public abstract void serializeExtension(CodedOutputStreamWriter codedOutputStreamWriter, Map.Entry entry);
}
