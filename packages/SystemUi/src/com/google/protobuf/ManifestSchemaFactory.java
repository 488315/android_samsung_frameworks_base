package com.google.protobuf;

import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ManifestSchemaFactory {
    public static final C45341 EMPTY_FACTORY = new MessageInfoFactory() { // from class: com.google.protobuf.ManifestSchemaFactory.1
        @Override // com.google.protobuf.MessageInfoFactory
        public final boolean isSupported(Class cls) {
            return false;
        }

        @Override // com.google.protobuf.MessageInfoFactory
        public final MessageInfo messageInfoFor(Class cls) {
            throw new IllegalStateException("This should never be called.");
        }
    };
    public final MessageInfoFactory messageInfoFactory;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CompositeMessageInfoFactory implements MessageInfoFactory {
        public final MessageInfoFactory[] factories;

        public CompositeMessageInfoFactory(MessageInfoFactory... messageInfoFactoryArr) {
            this.factories = messageInfoFactoryArr;
        }

        @Override // com.google.protobuf.MessageInfoFactory
        public final boolean isSupported(Class cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.google.protobuf.MessageInfoFactory
        public final MessageInfo messageInfoFor(Class cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return messageInfoFactory.messageInfoFor(cls);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: ".concat(cls.getName()));
        }
    }

    private ManifestSchemaFactory(MessageInfoFactory messageInfoFactory) {
        Charset charset = Internal.UTF_8;
        if (messageInfoFactory == null) {
            throw new NullPointerException("messageInfoFactory");
        }
        this.messageInfoFactory = messageInfoFactory;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ManifestSchemaFactory() {
        this(new CompositeMessageInfoFactory(r1));
        MessageInfoFactory messageInfoFactory;
        MessageInfoFactory[] messageInfoFactoryArr = new MessageInfoFactory[2];
        messageInfoFactoryArr[0] = GeneratedMessageInfoFactory.instance;
        try {
            messageInfoFactory = (MessageInfoFactory) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            messageInfoFactory = EMPTY_FACTORY;
        }
        messageInfoFactoryArr[1] = messageInfoFactory;
    }
}
