package com.android.framework.protobuf;

import java.io.IOException;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes3.dex */
abstract class ExtensionSchema<T extends FieldSet.FieldDescriptorLite<T>> {
    abstract int extensionNumber(Map.Entry<?, ?> entry);

    abstract Object findExtensionByNumber(
            ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i);

    abstract FieldSet<T> getExtensions(Object obj);

    abstract FieldSet<T> getMutableExtensions(Object obj);

    abstract boolean hasExtensions(MessageLite messageLite);

    abstract void makeImmutable(Object obj);

    abstract <UT, UB> UB parseExtension(
            Object obj,
            Reader reader,
            Object obj2,
            ExtensionRegistryLite extensionRegistryLite,
            FieldSet<T> fieldSet,
            UB ub,
            UnknownFieldSchema<UT, UB> unknownFieldSchema)
            throws IOException;

    abstract void parseLengthPrefixedMessageSetItem(
            Reader reader,
            Object obj,
            ExtensionRegistryLite extensionRegistryLite,
            FieldSet<T> fieldSet)
            throws IOException;

    abstract void parseMessageSetItem(
            ByteString byteString,
            Object obj,
            ExtensionRegistryLite extensionRegistryLite,
            FieldSet<T> fieldSet)
            throws IOException;

    abstract void serializeExtension(Writer writer, Map.Entry<?, ?> entry) throws IOException;

    abstract void setExtensions(Object obj, FieldSet<T> fieldSet);

    ExtensionSchema() {}
}
