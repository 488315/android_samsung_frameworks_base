package com.google.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessageLite;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AbstractParser implements Parser {
    static {
        ExtensionRegistryLite.getEmptyRegistry();
    }

    public final MessageLite parseFrom(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        CodedInputStream.ArrayDecoder newCodedInput = byteString.newCodedInput();
        GeneratedMessageLite parsePartialFrom = ((GeneratedMessageLite.DefaultInstanceBasedParser) this).parsePartialFrom(newCodedInput, extensionRegistryLite);
        try {
            newCodedInput.checkLastTagWas(0);
            if (GeneratedMessageLite.isInitialized(parsePartialFrom, true)) {
                return parsePartialFrom;
            }
            InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(parsePartialFrom).getMessage());
            invalidProtocolBufferException.setUnfinishedMessage(parsePartialFrom);
            throw invalidProtocolBufferException;
        } catch (InvalidProtocolBufferException e) {
            e.setUnfinishedMessage(parsePartialFrom);
            throw e;
        }
    }
}
