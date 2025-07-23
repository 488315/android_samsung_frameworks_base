package com.android.framework.protobuf;

import com.android.framework.protobuf.Internal;
import com.samsung.android.widget.SemOneTouchApi;
import java.lang.reflect.Field;

@CheckReturnValue
/* loaded from: classes5.dex */
final class FieldInfo implements Comparable<FieldInfo> {
    private final Field cachedSizeField;
    private final boolean enforceUtf8;
    private final Internal.EnumVerifier enumVerifier;
    private final Field field;
    private final int fieldNumber;
    private final Object mapDefaultEntry;
    private final Class<?> messageClass;
    private final OneofInfo oneof;
    private final Class<?> oneofStoredType;
    private final Field presenceField;
    private final int presenceMask;
    private final boolean required;
    private final FieldType type;

    private static boolean isExactlyOneBitSet(int i) {
        return i != 0 && (i & (i + (-1))) == 0;
    }

    public static FieldInfo forField(Field field, int i, FieldType fieldType, boolean z) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
            throw new IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new FieldInfo(field, i, fieldType, null, null, 0, false, z, null, null, null, null, null);
    }

    public static FieldInfo forPackedField(Field field, int i, FieldType fieldType, Field field2) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == FieldType.MESSAGE_LIST || fieldType == FieldType.GROUP_LIST) {
            throw new IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, null, field2);
    }

    public static FieldInfo forRepeatedMessageField(Field field, int i, FieldType fieldType, Class<?> cls) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(cls, "messageClass");
        return new FieldInfo(field, i, fieldType, cls, null, 0, false, false, null, null, null, null, null);
    }

    public static FieldInfo forFieldWithEnumVerifier(Field field, int i, FieldType fieldType, Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, null);
    }

    public static FieldInfo forPackedFieldWithEnumVerifier(Field field, int i, FieldType fieldType, Internal.EnumVerifier enumVerifier, Field field2) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        return new FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, field2);
    }

    public static FieldInfo forProto2OptionalField(Field field, int i, FieldType fieldType, Field field2, int i2, boolean z, Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(field2, "presenceField");
        if (field2 != null && !isExactlyOneBitSet(i2)) {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
        }
        return new FieldInfo(field, i, fieldType, null, field2, i2, false, z, null, null, null, enumVerifier, null);
    }

    public static FieldInfo forOneofMemberField(int i, FieldType fieldType, OneofInfo oneofInfo, Class<?> cls, boolean z, Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(oneofInfo, "oneof");
        Internal.checkNotNull(cls, "oneofStoredType");
        if (!fieldType.isScalar()) {
            throw new IllegalArgumentException("Oneof is only supported for scalar fields. Field " + i + " is of type " + fieldType);
        }
        return new FieldInfo(null, i, fieldType, null, null, 0, false, z, oneofInfo, cls, null, enumVerifier, null);
    }

    private static void checkFieldNumber(int i) {
        if (i > 0) {
            return;
        }
        throw new IllegalArgumentException("fieldNumber must be positive: " + i);
    }

    public static FieldInfo forProto2RequiredField(Field field, int i, FieldType fieldType, Field field2, int i2, boolean z, Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        Internal.checkNotNull(fieldType, "fieldType");
        Internal.checkNotNull(field2, "presenceField");
        if (field2 != null && !isExactlyOneBitSet(i2)) {
            throw new IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
        }
        return new FieldInfo(field, i, fieldType, null, field2, i2, true, z, null, null, null, enumVerifier, null);
    }

    public static FieldInfo forMapField(Field field, int i, Object obj, Internal.EnumVerifier enumVerifier) {
        Internal.checkNotNull(obj, "mapDefaultEntry");
        checkFieldNumber(i);
        Internal.checkNotNull(field, SemOneTouchApi.COLUMN_FIELD_NAME);
        return new FieldInfo(field, i, FieldType.MAP, null, null, 0, false, true, null, null, obj, enumVerifier, null);
    }

    private FieldInfo(Field field, int i, FieldType fieldType, Class<?> cls, Field field2, int i2, boolean z, boolean z2, OneofInfo oneofInfo, Class<?> cls2, Object obj, Internal.EnumVerifier enumVerifier, Field field3) {
        this.field = field;
        this.type = fieldType;
        this.messageClass = cls;
        this.fieldNumber = i;
        this.presenceField = field2;
        this.presenceMask = i2;
        this.required = z;
        this.enforceUtf8 = z2;
        this.oneof = oneofInfo;
        this.oneofStoredType = cls2;
        this.mapDefaultEntry = obj;
        this.enumVerifier = enumVerifier;
        this.cachedSizeField = field3;
    }

    public int getFieldNumber() {
        return this.fieldNumber;
    }

    public Field getField() {
        return this.field;
    }

    public FieldType getType() {
        return this.type;
    }

    public OneofInfo getOneof() {
        return this.oneof;
    }

    public Class<?> getOneofStoredType() {
        return this.oneofStoredType;
    }

    public Internal.EnumVerifier getEnumVerifier() {
        return this.enumVerifier;
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldInfo fieldInfo) {
        return this.fieldNumber - fieldInfo.fieldNumber;
    }

    public Class<?> getListElementType() {
        return this.messageClass;
    }

    public Field getPresenceField() {
        return this.presenceField;
    }

    public Object getMapDefaultEntry() {
        return this.mapDefaultEntry;
    }

    public int getPresenceMask() {
        return this.presenceMask;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isEnforceUtf8() {
        return this.enforceUtf8;
    }

    public Field getCachedSizeField() {
        return this.cachedSizeField;
    }

    /* renamed from: com.android.framework.protobuf.FieldInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$FieldType;

        static {
            int[] iArr = new int[FieldType.values().length];
            $SwitchMap$com$google$protobuf$FieldType = iArr;
            try {
                iArr[FieldType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType[FieldType.GROUP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType[FieldType.MESSAGE_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$FieldType[FieldType.GROUP_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public Class<?> getMessageFieldClass() {
        int i = AnonymousClass1.$SwitchMap$com$google$protobuf$FieldType[this.type.ordinal()];
        if (i == 1 || i == 2) {
            Field field = this.field;
            return field != null ? field.getType() : this.oneofStoredType;
        }
        if (i == 3 || i == 4) {
            return this.messageClass;
        }
        return null;
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public static final class Builder {
        private Field cachedSizeField;
        private boolean enforceUtf8;
        private Internal.EnumVerifier enumVerifier;
        private Field field;
        private int fieldNumber;
        private Object mapDefaultEntry;
        private OneofInfo oneof;
        private Class<?> oneofStoredType;
        private Field presenceField;
        private int presenceMask;
        private boolean required;
        private FieldType type;

        /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
        }

        public Builder withField(Field field) {
            if (this.oneof != null) {
                throw new IllegalStateException("Cannot set field when building a oneof.");
            }
            this.field = field;
            return this;
        }

        public Builder withType(FieldType fieldType) {
            this.type = fieldType;
            return this;
        }

        public Builder withFieldNumber(int i) {
            this.fieldNumber = i;
            return this;
        }

        public Builder withPresence(Field field, int i) {
            this.presenceField = (Field) Internal.checkNotNull(field, "presenceField");
            this.presenceMask = i;
            return this;
        }

        public Builder withOneof(OneofInfo oneofInfo, Class<?> cls) {
            if (this.field != null || this.presenceField != null) {
                throw new IllegalStateException("Cannot set oneof when field or presenceField have been provided");
            }
            this.oneof = oneofInfo;
            this.oneofStoredType = cls;
            return this;
        }

        public Builder withRequired(boolean z) {
            this.required = z;
            return this;
        }

        public Builder withMapDefaultEntry(Object obj) {
            this.mapDefaultEntry = obj;
            return this;
        }

        public Builder withEnforceUtf8(boolean z) {
            this.enforceUtf8 = z;
            return this;
        }

        public Builder withEnumVerifier(Internal.EnumVerifier enumVerifier) {
            this.enumVerifier = enumVerifier;
            return this;
        }

        public Builder withCachedSizeField(Field field) {
            this.cachedSizeField = field;
            return this;
        }

        public FieldInfo build() {
            OneofInfo oneofInfo = this.oneof;
            if (oneofInfo != null) {
                return FieldInfo.forOneofMemberField(this.fieldNumber, this.type, oneofInfo, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
            }
            Object obj = this.mapDefaultEntry;
            if (obj != null) {
                return FieldInfo.forMapField(this.field, this.fieldNumber, obj, this.enumVerifier);
            }
            Field field = this.presenceField;
            if (field != null) {
                if (this.required) {
                    return FieldInfo.forProto2RequiredField(this.field, this.fieldNumber, this.type, field, this.presenceMask, this.enforceUtf8, this.enumVerifier);
                }
                return FieldInfo.forProto2OptionalField(this.field, this.fieldNumber, this.type, field, this.presenceMask, this.enforceUtf8, this.enumVerifier);
            }
            Internal.EnumVerifier enumVerifier = this.enumVerifier;
            if (enumVerifier != null) {
                Field field2 = this.cachedSizeField;
                if (field2 == null) {
                    return FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, enumVerifier);
                }
                return FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, enumVerifier, field2);
            }
            Field field3 = this.cachedSizeField;
            if (field3 == null) {
                return FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8);
            }
            return FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, field3);
        }
    }
}
