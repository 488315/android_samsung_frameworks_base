package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.CodedInputStream;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;

/* loaded from: classes.dex */
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
