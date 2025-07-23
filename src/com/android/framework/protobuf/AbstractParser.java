package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.MessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes5.dex */
public abstract class AbstractParser<MessageType extends MessageLite> implements Parser<MessageType> {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

    private UninitializedMessageException newUninitializedMessageException(MessageType messagetype) {
        if (messagetype instanceof AbstractMessageLite) {
            return ((AbstractMessageLite) messagetype).newUninitializedMessageException();
        }
        return new UninitializedMessageException(messagetype);
    }

    private MessageType checkMessageInitialized(MessageType messagetype) throws InvalidProtocolBufferException {
        if (messagetype == null || messagetype.isInitialized()) {
            return messagetype;
        }
        throw newUninitializedMessageException(messagetype).asInvalidProtocolBufferException().setUnfinishedMessage(messagetype);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(codedInputStream, EMPTY_REGISTRY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized((MessageLite) parsePartialFrom(codedInputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return parseFrom(codedInputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream newCodedInput = byteString.newCodedInput();
        MessageType messagetype = (MessageType) parsePartialFrom(newCodedInput, extensionRegistryLite);
        try {
            newCodedInput.checkLastTagWas(0);
            return messagetype;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(messagetype);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return parsePartialFrom(byteString, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(byteString, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return parseFrom(byteString, EMPTY_REGISTRY);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream newInstance = CodedInputStream.newInstance(byteBuffer);
        MessageLite messageLite = (MessageLite) parsePartialFrom(newInstance, extensionRegistryLite);
        try {
            newInstance.checkLastTagWas(0);
            return (MessageType) checkMessageInitialized(messageLite);
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(messageLite);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return parseFrom(byteBuffer, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream newInstance = CodedInputStream.newInstance(bArr, i, i2);
        MessageType messagetype = (MessageType) parsePartialFrom(newInstance, extensionRegistryLite);
        try {
            newInstance.checkLastTagWas(0);
            return messagetype;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(messagetype);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
        return parsePartialFrom(bArr, i, i2, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return parsePartialFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return parsePartialFrom(bArr, 0, bArr.length, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(bArr, i, i2, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
        return parseFrom(bArr, i, i2, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return parseFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return parseFrom(bArr, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream newInstance = CodedInputStream.newInstance(inputStream);
        MessageType messagetype = (MessageType) parsePartialFrom(newInstance, extensionRegistryLite);
        try {
            newInstance.checkLastTagWas(0);
            return messagetype;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(messagetype);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return parsePartialFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(inputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return parseFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        try {
            int read = inputStream.read();
            if (read == -1) {
                return null;
            }
            return parsePartialFrom((InputStream) new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(read, inputStream)), extensionRegistryLite);
        } catch (IOException e) {
            throw new InvalidProtocolBufferException(e);
        }
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parsePartialDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return parsePartialDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(inputStream, extensionRegistryLite));
    }

    @Override // com.android.framework.protobuf.Parser
    public MessageType parseDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return parseDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }
}
