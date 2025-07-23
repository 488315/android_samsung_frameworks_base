package androidx.datastore.preferences.protobuf;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.AbstractMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class GeneratedMessageLite extends AbstractMessageLite {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Object, GeneratedMessageLite> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.DEFAULT_INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultInstanceBasedParser extends AbstractParser {
        public final GeneratedMessageLite defaultInstance;

        public DefaultInstanceBasedParser(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ExtendableMessage extends GeneratedMessageLite implements MessageLiteOrBuilder {
        protected FieldSet extensions = FieldSet.DEFAULT_INSTANCE;

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
        public final GeneratedMessageLite getDefaultInstanceForType$1() {
            return (GeneratedMessageLite) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLite
        public final Builder newBuilderForType() {
            return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLite
        public final Builder toBuilder() {
            Builder builder = (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
            builder.mergeFrom(this);
            return builder;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ExtensionDescriptor implements Comparable {
        public final boolean isPacked;
        public final boolean isRepeated;
        public final int number;
        public final WireFormat$FieldType type;

        public ExtensionDescriptor(Internal.EnumLiteMap enumLiteMap, int i, WireFormat$FieldType wireFormat$FieldType, boolean z, boolean z2) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class GeneratedExtension extends ExtensionLite {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (generatedMessageLite != null) {
            return generatedMessageLite;
        }
        GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) UnsafeUtil.allocateInstance(cls);
        generatedMessageLite2.getClass();
        GeneratedMessageLite generatedMessageLite3 = (GeneratedMessageLite) generatedMessageLite2.dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
        if (generatedMessageLite3 == null) {
            throw new IllegalStateException();
        }
        defaultInstanceMap.put(cls, generatedMessageLite3);
        return generatedMessageLite3;
    }

    public static Object invokeOrDie(Method method, MessageLite messageLite, Object... objArr) {
        try {
            return method.invoke(messageLite, objArr);
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

    public static GeneratedMessageLite parsePartialFrom(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) {
        GeneratedMessageLite newMutableInstance$1 = generatedMessageLite.newMutableInstance$1();
        try {
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            Schema schemaFor = protobuf.schemaFor(newMutableInstance$1.getClass());
            schemaFor.mergeFrom(newMutableInstance$1, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaFor.makeImmutable(newMutableInstance$1);
            return newMutableInstance$1;
        } catch (InvalidProtocolBufferException e) {
            e = e;
            if (e.getThrownFromInputStream()) {
                e = new InvalidProtocolBufferException((IOException) e);
            }
            e.setUnfinishedMessage(newMutableInstance$1);
            throw e;
        } catch (UninitializedMessageException e2) {
            InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(e2.getMessage());
            invalidProtocolBufferException.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException;
        } catch (IOException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            InvalidProtocolBufferException invalidProtocolBufferException2 = new InvalidProtocolBufferException(e3);
            invalidProtocolBufferException2.setUnfinishedMessage(newMutableInstance$1);
            throw invalidProtocolBufferException2;
        } catch (RuntimeException e4) {
            if (e4.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e4.getCause());
            }
            throw e4;
        }
    }

    public static void registerDefaultInstance(Class cls, GeneratedMessageLite generatedMessageLite) {
        generatedMessageLite.markImmutable();
        defaultInstanceMap.put(cls, generatedMessageLite);
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

    @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
    public GeneratedMessageLite getDefaultInstanceForType$1() {
        return (GeneratedMessageLite) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite
    public final int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite
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
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(serializedSize2, "serialized size must be non-negative, was "));
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

    @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return isInitialized(this, true);
    }

    public final boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    public final void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public Builder newBuilderForType() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    public final GeneratedMessageLite newMutableInstance$1() {
        return (GeneratedMessageLite) dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite
    public final void setMemoizedSerializedSize(int i) {
        if (i < 0) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "serialized size must be non-negative, was "));
        }
        this.memoizedSerializedSize = (i & Integer.MAX_VALUE) | (this.memoizedSerializedSize & Integer.MIN_VALUE);
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public Builder toBuilder() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Builder extends AbstractMessageLite.Builder {
        public final GeneratedMessageLite defaultInstance;
        public GeneratedMessageLite instance;

        public Builder(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
            if (generatedMessageLite.isMutable()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.instance = generatedMessageLite.newMutableInstance$1();
        }

        public static void mergeFromInstance(Object obj, Object obj2) {
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(obj.getClass()).mergeFrom(obj, obj2);
        }

        public final GeneratedMessageLite build() {
            GeneratedMessageLite buildPartial$1 = buildPartial$1();
            buildPartial$1.getClass();
            if (GeneratedMessageLite.isInitialized(buildPartial$1, true)) {
                return buildPartial$1;
            }
            throw new UninitializedMessageException(buildPartial$1);
        }

        public final GeneratedMessageLite buildPartial$1() {
            if (!this.instance.isMutable()) {
                return this.instance;
            }
            GeneratedMessageLite generatedMessageLite = this.instance;
            generatedMessageLite.getClass();
            Protobuf protobuf = Protobuf.INSTANCE;
            protobuf.getClass();
            protobuf.schemaFor(generatedMessageLite.getClass()).makeImmutable(generatedMessageLite);
            generatedMessageLite.markImmutable();
            return this.instance;
        }

        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone */
        public final Builder mo896clone() {
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            Builder builder = (Builder) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_BUILDER);
            builder.instance = buildPartial$1();
            return builder;
        }

        public final void copyOnWrite() {
            if (this.instance.isMutable()) {
                return;
            }
            GeneratedMessageLite newMutableInstance$1 = this.defaultInstance.newMutableInstance$1();
            mergeFromInstance(newMutableInstance$1, this.instance);
            this.instance = newMutableInstance$1;
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
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

        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone */
        public final Object mo896clone() {
            GeneratedMessageLite generatedMessageLite = this.defaultInstance;
            generatedMessageLite.getClass();
            Builder builder = (Builder) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_BUILDER);
            builder.instance = buildPartial$1();
            return builder;
        }
    }
}
