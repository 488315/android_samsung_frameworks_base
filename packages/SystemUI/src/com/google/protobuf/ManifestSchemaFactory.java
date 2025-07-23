package com.google.protobuf;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ManifestSchemaFactory() {
        /*
            r5 = this;
            com.google.protobuf.ManifestSchemaFactory$CompositeMessageInfoFactory r0 = new com.google.protobuf.ManifestSchemaFactory$CompositeMessageInfoFactory
            com.google.protobuf.GeneratedMessageInfoFactory r1 = com.google.protobuf.GeneratedMessageInfoFactory.instance
            java.lang.String r2 = "com.google.protobuf.DescriptorMessageInfoFactory"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch: java.lang.Exception -> L1b
            java.lang.String r3 = "getInstance"
            r4 = 0
            java.lang.Class[] r4 = new java.lang.Class[r4]     // Catch: java.lang.Exception -> L1b
            r4 = 0
            java.lang.reflect.Method r2 = r2.getDeclaredMethod(r3, r4)     // Catch: java.lang.Exception -> L1b
            java.lang.Object r2 = r2.invoke(r4, r4)     // Catch: java.lang.Exception -> L1b
            com.google.protobuf.MessageInfoFactory r2 = (com.google.protobuf.MessageInfoFactory) r2     // Catch: java.lang.Exception -> L1b
            goto L1d
        L1b:
            com.google.protobuf.ManifestSchemaFactory$1 r2 = com.google.protobuf.ManifestSchemaFactory.EMPTY_FACTORY
        L1d:
            com.google.protobuf.MessageInfoFactory[] r1 = new com.google.protobuf.MessageInfoFactory[]{r1, r2}
            r0.<init>(r1)
            r5.<init>(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.ManifestSchemaFactory.<init>():void");
    }
}
