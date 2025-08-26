package com.google.protobuf;

/* loaded from: classes4.dex */
public final class ManifestSchemaFactory {
    public static final AnonymousClass1 EMPTY_FACTORY = new MessageInfoFactory() { // from class: com.google.protobuf.ManifestSchemaFactory.1
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

    public class CompositeMessageInfoFactory implements MessageInfoFactory {
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
        Internal.checkNotNull(messageInfoFactory, "messageInfoFactory");
        this.messageInfoFactory = messageInfoFactory;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ManifestSchemaFactory() {
        MessageInfoFactory messageInfoFactory;
        GeneratedMessageInfoFactory generatedMessageInfoFactory = GeneratedMessageInfoFactory.instance;
        try {
            Class[] clsArr = new Class[0];
            messageInfoFactory = (MessageInfoFactory) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception unused) {
            messageInfoFactory = EMPTY_FACTORY;
        }
        this(new CompositeMessageInfoFactory(generatedMessageInfoFactory, messageInfoFactory));
    }
}
