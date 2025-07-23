package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ExtensionSchema {
    public abstract int extensionNumber(Map.Entry entry);

    public abstract GeneratedMessageLite.GeneratedExtension findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i);

    public abstract FieldSet getExtensions(Object obj);

    public abstract FieldSet getMutableExtensions(Object obj);

    public abstract boolean hasExtensions(MessageLite messageLite);

    public abstract void makeImmutable(Object obj);

    public abstract Object parseExtension(Object obj, CodedInputStreamReader codedInputStreamReader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj3, UnknownFieldSchema unknownFieldSchema);

    public abstract void parseLengthPrefixedMessageSetItem(CodedInputStreamReader codedInputStreamReader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    public abstract void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    public abstract void serializeExtension(CodedOutputStreamWriter codedOutputStreamWriter, Map.Entry entry);
}
