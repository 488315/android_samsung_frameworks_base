package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.CodedInputStream;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AbstractParser implements Parser {
    static {
        ExtensionRegistryLite.getEmptyRegistry();
    }

    public final GeneratedMessageLite parseFrom(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        CodedInputStream.ArrayDecoder newCodedInput = byteString.newCodedInput();
        GeneratedMessageLite parsePartialFrom = GeneratedMessageLite.parsePartialFrom(((GeneratedMessageLite.DefaultInstanceBasedParser) this).defaultInstance, newCodedInput, extensionRegistryLite);
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
