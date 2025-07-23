package com.android.framework.protobuf;

import android.hardware.scontext.SContextConstants;
import java.io.IOException;

/* loaded from: classes5.dex */
public final class WireFormat {
    static final int FIXED32_SIZE = 4;
    static final int FIXED64_SIZE = 8;
    static final int MAX_VARINT32_SIZE = 5;
    static final int MAX_VARINT64_SIZE = 10;
    static final int MAX_VARINT_SIZE = 10;
    static final int MESSAGE_SET_ITEM = 1;
    static final int MESSAGE_SET_MESSAGE = 3;
    static final int MESSAGE_SET_TYPE_ID = 2;
    static final int TAG_TYPE_BITS = 3;
    static final int TAG_TYPE_MASK = 7;
    public static final int WIRETYPE_END_GROUP = 4;
    public static final int WIRETYPE_FIXED32 = 5;
    public static final int WIRETYPE_FIXED64 = 1;
    public static final int WIRETYPE_LENGTH_DELIMITED = 2;
    public static final int WIRETYPE_START_GROUP = 3;
    public static final int WIRETYPE_VARINT = 0;
    static final int MESSAGE_SET_ITEM_TAG = makeTag(1, 3);
    static final int MESSAGE_SET_ITEM_END_TAG = makeTag(1, 4);
    static final int MESSAGE_SET_TYPE_ID_TAG = makeTag(2, 0);
    static final int MESSAGE_SET_MESSAGE_TAG = makeTag(3, 2);

    enum Utf8Validation {
        LOOSE { // from class: com.android.framework.protobuf.WireFormat.Utf8Validation.1
            @Override // com.android.framework.protobuf.WireFormat.Utf8Validation
            Object readString(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readString();
            }
        },
        STRICT { // from class: com.android.framework.protobuf.WireFormat.Utf8Validation.2
            @Override // com.android.framework.protobuf.WireFormat.Utf8Validation
            Object readString(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readStringRequireUtf8();
            }
        },
        LAZY { // from class: com.android.framework.protobuf.WireFormat.Utf8Validation.3
            @Override // com.android.framework.protobuf.WireFormat.Utf8Validation
            Object readString(CodedInputStream codedInputStream) throws IOException {
                return codedInputStream.readBytes();
            }
        };

        abstract Object readString(CodedInputStream codedInputStream) throws IOException;
    }

    public static int getTagFieldNumber(int i) {
        return i >>> 3;
    }

    public static int getTagWireType(int i) {
        return i & 7;
    }

    static int makeTag(int i, int i2) {
        return (i << 3) | i2;
    }

    private WireFormat() {
    }

    public enum JavaType {
        INT(0),
        LONG(0L),
        FLOAT(Float.valueOf(0.0f)),
        DOUBLE(Double.valueOf(SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)),
        BOOLEAN(false),
        STRING(""),
        BYTE_STRING(ByteString.EMPTY),
        ENUM(null),
        MESSAGE(null);

        private final Object defaultDefault;

        JavaType(Object obj) {
            this.defaultDefault = obj;
        }

        Object getDefaultDefault() {
            return this.defaultDefault;
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'STRING' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static class FieldType {
        public static final FieldType BYTES;
        public static final FieldType GROUP = new FieldType("GROUP", 9, JavaType.MESSAGE, 3) { // from class: com.android.framework.protobuf.WireFormat.FieldType.2
            @Override // com.android.framework.protobuf.WireFormat.FieldType
            public boolean isPackable() {
                return false;
            }
        };
        public static final FieldType MESSAGE;
        public static final FieldType STRING;
        private final JavaType javaType;
        private final int wireType;
        public static final FieldType DOUBLE = new FieldType("DOUBLE", 0, JavaType.DOUBLE, 1);
        public static final FieldType FLOAT = new FieldType("FLOAT", 1, JavaType.FLOAT, 5);
        public static final FieldType INT64 = new FieldType("INT64", 2, JavaType.LONG, 0);
        public static final FieldType UINT64 = new FieldType("UINT64", 3, JavaType.LONG, 0);
        public static final FieldType INT32 = new FieldType("INT32", 4, JavaType.INT, 0);
        public static final FieldType FIXED64 = new FieldType("FIXED64", 5, JavaType.LONG, 1);
        public static final FieldType FIXED32 = new FieldType("FIXED32", 6, JavaType.INT, 5);
        public static final FieldType BOOL = new FieldType("BOOL", 7, JavaType.BOOLEAN, 0);
        public static final FieldType UINT32 = new FieldType("UINT32", 12, JavaType.INT, 0);
        public static final FieldType ENUM = new FieldType("ENUM", 13, JavaType.ENUM, 0);
        public static final FieldType SFIXED32 = new FieldType("SFIXED32", 14, JavaType.INT, 5);
        public static final FieldType SFIXED64 = new FieldType("SFIXED64", 15, JavaType.LONG, 1);
        public static final FieldType SINT32 = new FieldType("SINT32", 16, JavaType.INT, 0);
        public static final FieldType SINT64 = new FieldType("SINT64", 17, JavaType.LONG, 0);
        private static final /* synthetic */ FieldType[] $VALUES = $values();

        public boolean isPackable() {
            return true;
        }

        private static /* synthetic */ FieldType[] $values() {
            return new FieldType[]{DOUBLE, FLOAT, INT64, UINT64, INT32, FIXED64, FIXED32, BOOL, STRING, GROUP, MESSAGE, BYTES, UINT32, ENUM, SFIXED32, SFIXED64, SINT32, SINT64};
        }

        public static FieldType valueOf(String str) {
            return (FieldType) Enum.valueOf(FieldType.class, str);
        }

        public static FieldType[] values() {
            return (FieldType[]) $VALUES.clone();
        }

        static {
            int i = 2;
            STRING = new FieldType("STRING", 8, JavaType.STRING, i) { // from class: com.android.framework.protobuf.WireFormat.FieldType.1
                @Override // com.android.framework.protobuf.WireFormat.FieldType
                public boolean isPackable() {
                    return false;
                }
            };
            MESSAGE = new FieldType("MESSAGE", 10, JavaType.MESSAGE, i) { // from class: com.android.framework.protobuf.WireFormat.FieldType.3
                @Override // com.android.framework.protobuf.WireFormat.FieldType
                public boolean isPackable() {
                    return false;
                }
            };
            BYTES = new FieldType("BYTES", 11, JavaType.BYTE_STRING, i) { // from class: com.android.framework.protobuf.WireFormat.FieldType.4
                @Override // com.android.framework.protobuf.WireFormat.FieldType
                public boolean isPackable() {
                    return false;
                }
            };
        }

        private FieldType(String str, int i, JavaType javaType, int i2) {
            this.javaType = javaType;
            this.wireType = i2;
        }

        public JavaType getJavaType() {
            return this.javaType;
        }

        public int getWireType() {
            return this.wireType;
        }
    }

    static Object readPrimitiveField(CodedInputStream codedInputStream, FieldType fieldType, Utf8Validation utf8Validation) throws IOException {
        switch (fieldType.ordinal()) {
            case 0:
                return Double.valueOf(codedInputStream.readDouble());
            case 1:
                return Float.valueOf(codedInputStream.readFloat());
            case 2:
                return Long.valueOf(codedInputStream.readInt64());
            case 3:
                return Long.valueOf(codedInputStream.readUInt64());
            case 4:
                return Integer.valueOf(codedInputStream.readInt32());
            case 5:
                return Long.valueOf(codedInputStream.readFixed64());
            case 6:
                return Integer.valueOf(codedInputStream.readFixed32());
            case 7:
                return Boolean.valueOf(codedInputStream.readBool());
            case 8:
                return utf8Validation.readString(codedInputStream);
            case 9:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 10:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 11:
                return codedInputStream.readBytes();
            case 12:
                return Integer.valueOf(codedInputStream.readUInt32());
            case 13:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            case 14:
                return Integer.valueOf(codedInputStream.readSFixed32());
            case 15:
                return Long.valueOf(codedInputStream.readSFixed64());
            case 16:
                return Integer.valueOf(codedInputStream.readSInt32());
            case 17:
                return Long.valueOf(codedInputStream.readSInt64());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }
}
