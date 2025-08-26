package com.google.protobuf;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.GeneratedMessageLite;

/* loaded from: classes4.dex */
public abstract class AbstractParser implements Parser {
    static {
        ExtensionRegistryLite.getEmptyRegistry();
    }

    public final GeneratedMessageLite parseFrom(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) throws InvalidProtocolBufferException {
        CodedInputStream.ArrayDecoder arrayDecoderNewCodedInput = byteString.newCodedInput();
        GeneratedMessageLite partialFrom = GeneratedMessageLite.parsePartialFrom(((GeneratedMessageLite.DefaultInstanceBasedParser) this).defaultInstance, arrayDecoderNewCodedInput, extensionRegistryLite);
        try {
            arrayDecoderNewCodedInput.checkLastTagWas(0);
            if (GeneratedMessageLite.isInitialized(partialFrom, true)) {
                return partialFrom;
            }
            InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(new UninitializedMessageException(partialFrom).getMessage());
            invalidProtocolBufferException.setUnfinishedMessage(partialFrom);
            throw invalidProtocolBufferException;
        } catch (InvalidProtocolBufferException e) {
            e.setUnfinishedMessage(partialFrom);
            throw e;
        }
    }
}
