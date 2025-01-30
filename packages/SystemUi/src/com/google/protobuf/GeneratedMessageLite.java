package com.google.protobuf;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.Internal;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class GeneratedMessageLite extends AbstractMessageLite {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Object, GeneratedMessageLite> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.DEFAULT_INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultInstanceBasedParser extends AbstractParser {
        public final GeneratedMessageLite defaultInstance;

        public DefaultInstanceBasedParser(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
        }

        public final GeneratedMessageLite parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) {
            int i = GeneratedMessageLite.UNINITIALIZED_SERIALIZED_SIZE;
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            try {
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                Schema schemaFor = protobuf.schemaFor(generatedMessageLite2.getClass());
                schemaFor.mergeFrom(generatedMessageLite2, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
                schemaFor.makeImmutable(generatedMessageLite2);
                return generatedMessageLite2;
            } catch (InvalidProtocolBufferException e) {
                e = e;
                if (e.getThrownFromInputStream()) {
                    e = new InvalidProtocolBufferException((IOException) e);
                }
                e.setUnfinishedMessage(generatedMessageLite2);
                throw e;
            } catch (UninitializedMessageException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(e2.getMessage());
                invalidProtocolBufferException.setUnfinishedMessage(generatedMessageLite2);
                throw invalidProtocolBufferException;
            } catch (IOException e3) {
                if (e3.getCause() instanceof InvalidProtocolBufferException) {
                    throw ((InvalidProtocolBufferException) e3.getCause());
                }
                InvalidProtocolBufferException invalidProtocolBufferException2 = new InvalidProtocolBufferException(e3);
                invalidProtocolBufferException2.setUnfinishedMessage(generatedMessageLite2);
                throw invalidProtocolBufferException2;
            } catch (RuntimeException e4) {
                if (e4.getCause() instanceof InvalidProtocolBufferException) {
                    throw ((InvalidProtocolBufferException) e4.getCause());
                }
                throw e4;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ExtendableMessage extends GeneratedMessageLite implements MessageLiteOrBuilder {
        protected FieldSet extensions = FieldSet.DEFAULT_INSTANCE;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExtensionDescriptor implements Comparable {
        public final Internal.EnumLiteMap enumTypeMap;
        public final boolean isPacked;
        public final boolean isRepeated;
        public final int number;
        public final WireFormat$FieldType type;

        public ExtensionDescriptor(Internal.EnumLiteMap enumLiteMap, int i, WireFormat$FieldType wireFormat$FieldType, boolean z, boolean z2) {
            this.enumTypeMap = enumLiteMap;
            this.number = i;
            this.type = wireFormat$FieldType;
            this.isRepeated = z;
            this.isPacked = z2;
        }

        @Override // java.lang.Comparable
        public final int compareTo(Object obj) {
            return this.number - ((ExtensionDescriptor) obj).number;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GeneratedExtension extends ExtensionLite {
        public final Object defaultValue;
        public final ExtensionDescriptor descriptor;
        public final MessageLite messageDefaultInstance;

        public GeneratedExtension(MessageLite messageLite, Object obj, MessageLite messageLite2, ExtensionDescriptor extensionDescriptor, Class cls) {
            if (messageLite == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (extensionDescriptor.type == WireFormat$FieldType.MESSAGE && messageLite2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.defaultValue = obj;
            this.messageDefaultInstance = messageLite2;
            this.descriptor = extensionDescriptor;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    public static GeneratedMessageLite getDefaultInstance(Class cls) {
        GeneratedMessageLite generatedMessageLite = defaultInstanceMap.get(cls);
        if (generatedMessageLite == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                generatedMessageLite = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (generatedMessageLite == null) {
            GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) UnsafeUtil.allocateInstance(cls);
            generatedMessageLite2.getClass();
            generatedMessageLite = (GeneratedMessageLite) generatedMessageLite2.dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
            if (generatedMessageLite == null) {
                throw new IllegalStateException();
            }
            defaultInstanceMap.put(cls, generatedMessageLite);
        }
        return generatedMessageLite;
    }

    public static Object invokeOrDie(Object obj, Method method, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static Internal.ProtobufList mutableCopy(Internal.ProtobufList protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    public static void registerDefaultInstance(Class cls, GeneratedMessageLite generatedMessageLite) {
        defaultInstanceMap.put(cls, generatedMessageLite);
        generatedMessageLite.makeImmutable();
    }

    public final Builder createBuilder() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    public abstract Object dynamicMethod(MethodToInvoke methodToInvoke);

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        return protobuf.schemaFor(getClass()).equals(this, (GeneratedMessageLite) obj);
    }

    @Override // com.google.protobuf.AbstractMessageLite
    public final int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // com.google.protobuf.AbstractMessageLite
    public final int getSerializedSize(Schema schema) {
        int serializedSize;
        int serializedSize2;
        if (isMutable()) {
            if (schema == null) {
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                serializedSize2 = protobuf.schemaFor(getClass()).getSerializedSize(this);
            } else {
                serializedSize2 = schema.getSerializedSize(this);
            }
            if (serializedSize2 >= 0) {
                return serializedSize2;
            }
            throw new IllegalStateException(AbstractC0000x2c234b15.m0m("serialized size must be non-negative, was ", serializedSize2));
        }
        if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
            return getMemoizedSerializedSize();
        }
        if (schema == null) {
            Protobuf protobuf2 = Protobuf.INSTANCE;
            protobuf2.getClass();
            serializedSize = protobuf2.schemaFor(getClass()).getSerializedSize(this);
        } else {
            serializedSize = schema.getSerializedSize(this);
        }
        setMemoizedSerializedSize(serializedSize);
        return serializedSize;
    }

    public final int hashCode() {
        if (isMutable()) {
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            return protobuf.schemaFor(getClass()).hashCode(this);
        }
        if (this.memoizedHashCode == 0) {
            Protobuf protobuf2 = Protobuf.INSTANCE;
            protobuf2.getClass();
            this.memoizedHashCode = protobuf2.schemaFor(getClass()).hashCode(this);
        }
        return this.memoizedHashCode;
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return isInitialized(this, true);
    }

    public final boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    public final void makeImmutable() {
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        protobuf.schemaFor(getClass()).makeImmutable(this);
        markImmutable();
    }

    public final void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    @Override // com.google.protobuf.AbstractMessageLite
    public final void setMemoizedSerializedSize(int i) {
        if (i < 0) {
            throw new IllegalStateException(AbstractC0000x2c234b15.m0m("serialized size must be non-negative, was ", i));
        }
        this.memoizedSerializedSize = (i & Integer.MAX_VALUE) | (this.memoizedSerializedSize & Integer.MIN_VALUE);
    }

    public final Builder toBuilder() {
        Builder builder = (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
        builder.mergeFrom(this);
        return builder;
    }

    public final String toString() {
        String obj = super.toString();
        char[] cArr = MessageLiteToString.INDENT_BUFFER;
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(obj);
        MessageLiteToString.reflectivePrintWithIndent(this, sb, 0);
        return sb.toString();
    }

    public final void writeTo(CodedOutputStream codedOutputStream) {
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        protobuf.schemaFor(getClass()).writeTo(this, CodedOutputStreamWriter.forCodedOutput(codedOutputStream));
    }

    public static final boolean isInitialized(GeneratedMessageLite generatedMessageLite, boolean z) {
        byte byteValue = ((Byte) generatedMessageLite.dynamicMethod(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        Protobuf protobuf = Protobuf.INSTANCE;
        protobuf.getClass();
        boolean isInitialized = protobuf.schemaFor(generatedMessageLite.getClass()).isInitialized(generatedMessageLite);
        if (z) {
            generatedMessageLite.dynamicMethod(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED);
        }
        return isInitialized;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Builder extends AbstractMessageLite.Builder {
        public final GeneratedMessageLite defaultInstance;
        public GeneratedMessageLite instance;

        public Builder(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
            if (generatedMessageLite.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            generatedMessageLite.getClass();
            this.instance = (GeneratedMessageLite) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        }

        public static void mergeFromInstance(GeneratedMessageLite generatedMessageLite, Object obj) {
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass()).mergeFrom(generatedMessageLite, obj);
        }

        public final GeneratedMessageLite build() {
            GeneratedMessageLite buildPartial = buildPartial();
            buildPartial.getClass();
            if (GeneratedMessageLite.isInitialized(buildPartial, true)) {
                return buildPartial;
            }
            throw new UninitializedMessageException(buildPartial);
        }

        public final GeneratedMessageLite buildPartial() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            this.instance.makeImmutable();
            return this.instance;
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone */
        public final Builder mo2769clone() {
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            Builder builder = (Builder) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_BUILDER);
            builder.instance = buildPartial();
            return builder;
        }

        public final void copyOnWrite() {
            if (this.instance.isMutable()) {
                return;
            }
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            mergeFromInstance(generatedMessageLite2, this.instance);
            this.instance = generatedMessageLite2;
        }

        public final Builder internalMergeFrom(AbstractMessageLite abstractMessageLite) {
            mergeFrom((GeneratedMessageLite) abstractMessageLite);
            return this;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        public final void mergeFrom(GeneratedMessageLite generatedMessageLite) {
            if (this.defaultInstance.equals(generatedMessageLite)) {
                return;
            }
            copyOnWrite();
            mergeFromInstance(this.instance, generatedMessageLite);
        }

        public final void mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) {
            copyOnWrite();
            try {
                Protobuf protobuf = Protobuf.INSTANCE;
                GeneratedMessageLite generatedMessageLite = this.instance;
                protobuf.getClass();
                protobuf.schemaFor(generatedMessageLite.getClass()).mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }

        @Override // com.google.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone */
        public final Object mo2769clone() {
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            Builder builder = (Builder) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_BUILDER);
            builder.instance = buildPartial();
            return builder;
        }
    }

    public final int getSerializedSize() {
        return getSerializedSize(null);
    }
}
