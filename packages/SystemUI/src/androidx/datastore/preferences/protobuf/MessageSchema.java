package androidx.datastore.preferences.protobuf;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.UnsafeUtil;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MessageSchema implements Schema {
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final Unsafe UNSAFE;
    public final int[] buffer;
    public final int checkInitializedCount;
    public final MessageLite defaultInstance;
    public final ExtensionSchema extensionSchema;
    public final boolean hasExtensions;
    public final int[] intArray;
    public final ListFieldSchema listFieldSchema;
    public final boolean lite;
    public final MapFieldSchema mapFieldSchema;
    public final int maxFieldNumber;
    public final int minFieldNumber;
    public final NewInstanceSchema newInstanceSchema;
    public final Object[] objects;
    public final int repeatedFieldOffsetStart;
    public final UnknownFieldSchema unknownFieldSchema;
    public final boolean useCachedSizeField;

    static {
        Unsafe unsafe;
        try {
            unsafe = (Unsafe) AccessController.doPrivileged(new UnsafeUtil.AnonymousClass1());
        } catch (Throwable unused) {
            unsafe = null;
        }
        UNSAFE = unsafe;
    }

    private MessageSchema(int[] iArr, Object[] objArr, int i, int i2, MessageLite messageLite, ProtoSyntax protoSyntax, boolean z, int[] iArr2, int i3, int i4, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema unknownFieldSchema, ExtensionSchema extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        this.lite = messageLite instanceof GeneratedMessageLite;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(messageLite);
        this.useCachedSizeField = z;
        this.intArray = iArr2;
        this.checkInitializedCount = i3;
        this.repeatedFieldOffsetStart = i4;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema;
    }

    public static boolean isMutable(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x03df  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0595  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x05b9  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x05c8  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x05d0  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x05a8  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x054d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.datastore.preferences.protobuf.MessageSchema newSchema(androidx.datastore.preferences.protobuf.MessageInfo r40, androidx.datastore.preferences.protobuf.NewInstanceSchema r41, androidx.datastore.preferences.protobuf.ListFieldSchema r42, androidx.datastore.preferences.protobuf.UnknownFieldSchema r43, androidx.datastore.preferences.protobuf.ExtensionSchema r44, androidx.datastore.preferences.protobuf.MapFieldSchema r45) {
        /*
            Method dump skipped, instructions count: 1609
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.newSchema(androidx.datastore.preferences.protobuf.MessageInfo, androidx.datastore.preferences.protobuf.NewInstanceSchema, androidx.datastore.preferences.protobuf.ListFieldSchema, androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, androidx.datastore.preferences.protobuf.MapFieldSchema):androidx.datastore.preferences.protobuf.MessageSchema");
    }

    public static long offset(int i) {
        return i & 1048575;
    }

    public static int oneofIntAt(long j, Object obj) {
        return ((Integer) UnsafeUtil.getObject(j, obj)).intValue();
    }

    public static long oneofLongAt(long j, Object obj) {
        return ((Long) UnsafeUtil.getObject(j, obj)).longValue();
    }

    public static Field reflectField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Field ", str, " for ");
            m.append(cls.getName());
            m.append(" not found. Known fields are ");
            m.append(Arrays.toString(declaredFields));
            throw new RuntimeException(m.toString());
        }
    }

    public static int type(int i) {
        return (i & 267386880) >>> 20;
    }

    public static void writeString(int i, Object obj, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (!(obj instanceof String)) {
            codedOutputStreamWriter.writeBytes(i, (ByteString) obj);
        } else {
            codedOutputStreamWriter.output.writeString(i, (String) obj);
        }
    }

    public final boolean arePresentForEquals(GeneratedMessageLite generatedMessageLite, GeneratedMessageLite generatedMessageLite2, int i) {
        return isFieldPresent(i, generatedMessageLite) == isFieldPresent(i, generatedMessageLite2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
    
        if (androidx.datastore.preferences.protobuf.SchemaUtil.safeEquals(androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r12), androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0080, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0092, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a6, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b8, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ca, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00dc, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f2, code lost:
    
        if (androidx.datastore.preferences.protobuf.SchemaUtil.safeEquals(androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r12), androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0108, code lost:
    
        if (androidx.datastore.preferences.protobuf.SchemaUtil.safeEquals(androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r12), androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x011e, code lost:
    
        if (androidx.datastore.preferences.protobuf.SchemaUtil.safeEquals(androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r12), androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0132, code lost:
    
        if (r5.getBoolean(r7, r12) == r5.getBoolean(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0144, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0158, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016a, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getInt(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x017d, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0190, code lost:
    
        if (androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r12) == androidx.datastore.preferences.protobuf.UnsafeUtil.getLong(r7, r13)) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ab, code lost:
    
        if (java.lang.Float.floatToIntBits(r5.getFloat(r7, r12)) == java.lang.Float.floatToIntBits(r5.getFloat(r7, r13))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c8, code lost:
    
        if (java.lang.Double.doubleToLongBits(r5.getDouble(r7, r12)) == java.lang.Double.doubleToLongBits(r5.getDouble(r7, r13))) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
    
        if (androidx.datastore.preferences.protobuf.SchemaUtil.safeEquals(androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r12), androidx.datastore.preferences.protobuf.UnsafeUtil.getObject(r7, r13)) != false) goto L105;
     */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(androidx.datastore.preferences.protobuf.GeneratedMessageLite r12, androidx.datastore.preferences.protobuf.GeneratedMessageLite r13) {
        /*
            Method dump skipped, instructions count: 644
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.equals(androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.GeneratedMessageLite):boolean");
    }

    public final void filterMapUnknownEnumValues(int i, Object obj, Object obj2) {
        int i2 = this.buffer[i];
        if (UnsafeUtil.getObject(typeAndOffsetAt(i) & 1048575, obj) == null) {
            return;
        }
        getEnumFieldVerifier(i);
    }

    public final void getEnumFieldVerifier(int i) {
        if (this.objects[((i / 3) * 2) + 1] != null) {
            throw new ClassCastException();
        }
    }

    public final Schema getMessageFieldSchema(int i) {
        int i2 = (i / 3) * 2;
        Object[] objArr = this.objects;
        Schema schema = (Schema) objArr[i2];
        if (schema != null) {
            return schema;
        }
        Schema schemaFor = Protobuf.INSTANCE.schemaFor((Class) objArr[i2 + 1]);
        objArr[i2] = schemaFor;
        return schemaFor;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.datastore.preferences.protobuf.Schema
    public final int getSerializedSize(AbstractMessageLite abstractMessageLite) {
        int i;
        int i2;
        int computeTagSize;
        int computeTagSize2;
        int computeTagSize3;
        int computeUInt64SizeNoTag;
        int computeTagSize4;
        int computeUInt64SizeNoTag2;
        int computeTagSize5;
        int computeTagSize6;
        int computeBytesSize;
        int computeTagSize7;
        int size;
        int computeSizeUInt64ListNoTag;
        int computeTagSize8;
        int computeTagSize9;
        int size2;
        int computeTagSize10;
        int computeUInt32SizeNoTag;
        int i3;
        int computeTagSize11;
        int computeTagSize12;
        int computeTagSize13;
        int computeUInt64SizeNoTag3;
        int computeTagSize14;
        int computeUInt64SizeNoTag4;
        int computeTagSize15;
        MessageSchema messageSchema = this;
        AbstractMessageLite abstractMessageLite2 = abstractMessageLite;
        int i4 = 1;
        Unsafe unsafe = UNSAFE;
        int i5 = 1048575;
        int i6 = 1048575;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            int[] iArr = messageSchema.buffer;
            if (i7 >= iArr.length) {
                UnknownFieldSchema unknownFieldSchema = messageSchema.unknownFieldSchema;
                int serializedSize = unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(abstractMessageLite2)) + i9;
                if (!messageSchema.hasExtensions) {
                    return serializedSize;
                }
                SmallSortedMap smallSortedMap = messageSchema.extensionSchema.getExtensions(abstractMessageLite2).fields;
                int size3 = smallSortedMap.entryList.size();
                int i10 = 0;
                for (int i11 = 0; i11 < size3; i11++) {
                    Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i11);
                    i10 += FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue());
                }
                for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
                    i10 += FieldSet.computeFieldSize((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue());
                }
                return serializedSize + i10;
            }
            int typeAndOffsetAt = messageSchema.typeAndOffsetAt(i7);
            int type = type(typeAndOffsetAt);
            int i12 = iArr[i7];
            int i13 = iArr[i7 + 2];
            int i14 = i13 & i5;
            if (type <= 17) {
                if (i14 != i6) {
                    i8 = i14 == i5 ? 0 : unsafe.getInt(abstractMessageLite2, i14);
                    i6 = i14;
                }
                i = i4 << (i13 >>> 20);
            } else {
                i = 0;
            }
            long j = typeAndOffsetAt & i5;
            if (type < FieldType.DOUBLE_LIST_PACKED.id() || type > FieldType.SINT64_LIST_PACKED.id()) {
                i14 = 0;
            }
            boolean z = messageSchema.useCachedSizeField;
            switch (type) {
                case 0:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize = CodedOutputStream.computeTagSize(i12) + 8;
                        i9 += computeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 1:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize2 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize6 = computeTagSize2 + 4;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 2:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        long j2 = unsafe.getLong(abstractMessageLite2, j);
                        computeTagSize3 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j2);
                        i9 += computeUInt64SizeNoTag + computeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 3:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        long j3 = unsafe.getLong(abstractMessageLite2, j);
                        computeTagSize3 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag = CodedOutputStream.computeUInt64SizeNoTag(j3);
                        i9 += computeUInt64SizeNoTag + computeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 4:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        int i15 = unsafe.getInt(abstractMessageLite2, j);
                        computeTagSize4 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(i15);
                        computeBytesSize = computeUInt64SizeNoTag2 + computeTagSize4;
                        i9 += computeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 5:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize5 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize6 = computeTagSize5 + 8;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 6:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize2 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize6 = computeTagSize2 + 4;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 7:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize6 = CodedOutputStream.computeTagSize(i12) + 1;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 8:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        Object object = unsafe.getObject(abstractMessageLite2, j);
                        i9 = (object instanceof ByteString ? CodedOutputStream.computeBytesSize(i12, (ByteString) object) : CodedOutputStream.computeStringSizeNoTag((String) object) + CodedOutputStream.computeTagSize(i12)) + i9;
                    }
                    messageSchema = this;
                    break;
                case 9:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        Object object2 = unsafe.getObject(abstractMessageLite2, j);
                        Schema messageFieldSchema = messageSchema.getMessageFieldSchema(i7);
                        Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        if (object2 instanceof LazyFieldLite) {
                            computeTagSize = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) object2) + CodedOutputStream.computeTagSize(i12);
                        } else {
                            int computeTagSize16 = CodedOutputStream.computeTagSize(i12);
                            int serializedSize2 = ((AbstractMessageLite) ((MessageLite) object2)).getSerializedSize(messageFieldSchema);
                            computeTagSize = CodedOutputStream.computeUInt32SizeNoTag(serializedSize2) + serializedSize2 + computeTagSize16;
                        }
                        i9 += computeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 10:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeBytesSize = CodedOutputStream.computeBytesSize(i12, (ByteString) unsafe.getObject(abstractMessageLite2, j));
                        i9 += computeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 11:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        int i16 = unsafe.getInt(abstractMessageLite2, j);
                        computeTagSize4 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(i16);
                        computeBytesSize = computeUInt64SizeNoTag2 + computeTagSize4;
                        i9 += computeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 12:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        int i17 = unsafe.getInt(abstractMessageLite2, j);
                        computeTagSize4 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeUInt64SizeNoTag(i17);
                        computeBytesSize = computeUInt64SizeNoTag2 + computeTagSize4;
                        i9 += computeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 13:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize2 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize6 = computeTagSize2 + 4;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 14:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize5 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize6 = computeTagSize5 + 8;
                        i9 += computeTagSize6;
                    }
                    messageSchema = this;
                    abstractMessageLite2 = abstractMessageLite;
                    break;
                case 15:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        int i18 = unsafe.getInt(abstractMessageLite2, j);
                        computeTagSize4 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag2 = CodedOutputStream.computeSInt32SizeNoTag(i18);
                        computeBytesSize = computeUInt64SizeNoTag2 + computeTagSize4;
                        i9 += computeBytesSize;
                    }
                    messageSchema = this;
                    break;
                case 16:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        long j4 = unsafe.getLong(abstractMessageLite2, j);
                        computeTagSize3 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag = CodedOutputStream.computeSInt64SizeNoTag(j4);
                        i9 += computeUInt64SizeNoTag + computeTagSize3;
                    }
                    messageSchema = this;
                    break;
                case 17:
                    i2 = i4;
                    if (messageSchema.isFieldPresent(i7, abstractMessageLite2, i6, i8, i)) {
                        computeTagSize = ((AbstractMessageLite) ((MessageLite) unsafe.getObject(abstractMessageLite2, j))).getSerializedSize(messageSchema.getMessageFieldSchema(i7)) + (CodedOutputStream.computeTagSize(i12) * 2);
                        i9 += computeTagSize;
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed64List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 19:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed32List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 20:
                    i2 = i4;
                    List list = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    if (list.size() != 0) {
                        computeTagSize7 = (CodedOutputStream.computeTagSize(i12) * list.size()) + SchemaUtil.computeSizeInt64ListNoTag(list);
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 21:
                    i2 = i4;
                    List list2 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls3 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list2.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt64ListNoTag(list2);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 22:
                    i2 = i4;
                    List list3 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls4 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list3.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeInt32ListNoTag(list3);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 23:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed64List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 24:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed32List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 25:
                    i2 = i4;
                    List list4 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls5 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size4 = list4.size();
                    i9 += size4 == 0 ? 0 : (CodedOutputStream.computeTagSize(i12) + 1) * size4;
                    break;
                case 26:
                    i2 = i4;
                    List list5 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls6 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size5 = list5.size();
                    if (size5 != 0) {
                        computeTagSize7 = CodedOutputStream.computeTagSize(i12) * size5;
                        if (list5 instanceof LazyStringList) {
                            LazyStringList lazyStringList = (LazyStringList) list5;
                            for (int i19 = 0; i19 < size5; i19++) {
                                Object raw = lazyStringList.getRaw();
                                computeTagSize7 = (raw instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) raw) : CodedOutputStream.computeStringSizeNoTag((String) raw)) + computeTagSize7;
                            }
                        } else {
                            for (int i20 = 0; i20 < size5; i20++) {
                                Object obj = list5.get(i20);
                                computeTagSize7 = (obj instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) obj) : CodedOutputStream.computeStringSizeNoTag((String) obj)) + computeTagSize7;
                            }
                        }
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 27:
                    i2 = i4;
                    List list6 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Schema messageFieldSchema2 = messageSchema.getMessageFieldSchema(i7);
                    Class cls7 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size6 = list6.size();
                    if (size6 == 0) {
                        computeTagSize9 = 0;
                    } else {
                        computeTagSize9 = CodedOutputStream.computeTagSize(i12) * size6;
                        for (int i21 = 0; i21 < size6; i21++) {
                            Object obj2 = list6.get(i21);
                            if (obj2 instanceof LazyFieldLite) {
                                computeTagSize9 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj2) + computeTagSize9;
                            } else {
                                int serializedSize3 = ((AbstractMessageLite) ((MessageLite) obj2)).getSerializedSize(messageFieldSchema2);
                                computeTagSize9 = CodedOutputStream.computeUInt32SizeNoTag(serializedSize3) + serializedSize3 + computeTagSize9;
                            }
                        }
                    }
                    i9 += computeTagSize9;
                    break;
                case 28:
                    i2 = i4;
                    List list7 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls8 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size7 = list7.size();
                    if (size7 != 0) {
                        computeTagSize7 = CodedOutputStream.computeTagSize(i12) * size7;
                        for (int i22 = 0; i22 < list7.size(); i22++) {
                            computeTagSize7 += CodedOutputStream.computeBytesSizeNoTag((ByteString) list7.get(i22));
                        }
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 29:
                    i2 = i4;
                    List list8 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls9 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list8.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeUInt32ListNoTag(list8);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 30:
                    i2 = i4;
                    List list9 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls10 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list9.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeEnumListNoTag(list9);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 31:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed32List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 32:
                    i2 = i4;
                    computeTagSize = SchemaUtil.computeSizeFixed64List(i12, (List) unsafe.getObject(abstractMessageLite2, j));
                    i9 += computeTagSize;
                    break;
                case 33:
                    i2 = i4;
                    List list10 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls11 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list10.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeSInt32ListNoTag(list10);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 34:
                    i2 = i4;
                    List list11 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls12 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size = list11.size();
                    if (size != 0) {
                        computeSizeUInt64ListNoTag = SchemaUtil.computeSizeSInt64ListNoTag(list11);
                        computeTagSize8 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize7 = (computeTagSize8 * size) + computeSizeUInt64ListNoTag;
                        i9 += computeTagSize7;
                        break;
                    }
                    computeTagSize7 = 0;
                    i9 += computeTagSize7;
                case 35:
                    i2 = i4;
                    List list12 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls13 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list12.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 36:
                    i2 = i4;
                    List list13 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls14 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list13.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 37:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 38:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeUInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 39:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 40:
                    i2 = i4;
                    List list14 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls15 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list14.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 41:
                    i2 = i4;
                    List list15 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls16 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list15.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 42:
                    i2 = i4;
                    List list16 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls17 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list16.size();
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 43:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeUInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 44:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeEnumListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 45:
                    i2 = i4;
                    List list17 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls18 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list17.size() * 4;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 46:
                    i2 = i4;
                    List list18 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Class cls19 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    size2 = list18.size() * 8;
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 47:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeSInt32ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 48:
                    i2 = i4;
                    size2 = SchemaUtil.computeSizeSInt64ListNoTag((List) unsafe.getObject(abstractMessageLite2, j));
                    if (size2 <= 0) {
                        break;
                    } else {
                        if (z) {
                            unsafe.putInt(abstractMessageLite2, i14, size2);
                        }
                        computeTagSize10 = CodedOutputStream.computeTagSize(i12);
                        computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size2);
                        i9 += computeUInt32SizeNoTag + computeTagSize10 + size2;
                        break;
                    }
                case 49:
                    i2 = i4;
                    List list19 = (List) unsafe.getObject(abstractMessageLite2, j);
                    Schema messageFieldSchema3 = messageSchema.getMessageFieldSchema(i7);
                    Class cls20 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    int size8 = list19.size();
                    if (size8 == 0) {
                        i3 = 0;
                    } else {
                        i3 = 0;
                        for (int i23 = 0; i23 < size8; i23++) {
                            i3 += ((AbstractMessageLite) ((MessageLite) list19.get(i23))).getSerializedSize(messageFieldSchema3) + (CodedOutputStream.computeTagSize(i12) * 2);
                        }
                    }
                    i9 += i3;
                    break;
                case 50:
                    Object object3 = unsafe.getObject(abstractMessageLite2, j);
                    Object obj3 = messageSchema.objects[(i7 / 3) * 2];
                    ((MapFieldSchemaLite) messageSchema.mapFieldSchema).getClass();
                    MapFieldLite mapFieldLite = (MapFieldLite) object3;
                    MapEntryLite mapEntryLite = (MapEntryLite) obj3;
                    if (mapFieldLite.isEmpty()) {
                        computeTagSize9 = 0;
                    } else {
                        computeTagSize9 = 0;
                        for (Map.Entry entry2 : mapFieldLite.entrySet()) {
                            Object key = entry2.getKey();
                            Object value = entry2.getValue();
                            mapEntryLite.getClass();
                            int computeTagSize17 = CodedOutputStream.computeTagSize(i12);
                            int i24 = i4;
                            int computeSerializedSize = MapEntryLite.computeSerializedSize(mapEntryLite.metadata, key, value);
                            computeTagSize9 += CodedOutputStream.computeUInt32SizeNoTag(computeSerializedSize) + computeSerializedSize + computeTagSize17;
                            i4 = i24;
                        }
                    }
                    i2 = i4;
                    i9 += computeTagSize9;
                    break;
                case 51:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize11 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize11 + 8;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 52:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize12 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize12 + 4;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 53:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        long oneofLongAt = oneofLongAt(j, abstractMessageLite2);
                        computeTagSize13 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt);
                        i9 += computeUInt64SizeNoTag3 + computeTagSize13;
                    }
                    i2 = i4;
                    break;
                case 54:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        long oneofLongAt2 = oneofLongAt(j, abstractMessageLite2);
                        computeTagSize13 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag3 = CodedOutputStream.computeUInt64SizeNoTag(oneofLongAt2);
                        i9 += computeUInt64SizeNoTag3 + computeTagSize13;
                    }
                    i2 = i4;
                    break;
                case 55:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        int oneofIntAt = oneofIntAt(j, abstractMessageLite2);
                        computeTagSize14 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag4 = CodedOutputStream.computeUInt64SizeNoTag(oneofIntAt);
                        computeTagSize15 = computeUInt64SizeNoTag4 + computeTagSize14;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 56:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize11 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize11 + 8;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 57:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize12 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize12 + 4;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 58:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize15 = CodedOutputStream.computeTagSize(i12) + i4;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 59:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        Object object4 = unsafe.getObject(abstractMessageLite2, j);
                        i9 = (object4 instanceof ByteString ? CodedOutputStream.computeBytesSize(i12, (ByteString) object4) : CodedOutputStream.computeStringSizeNoTag((String) object4) + CodedOutputStream.computeTagSize(i12)) + i9;
                    }
                    i2 = i4;
                    break;
                case 60:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        Object object5 = unsafe.getObject(abstractMessageLite2, j);
                        Schema messageFieldSchema4 = messageSchema.getMessageFieldSchema(i7);
                        Class cls21 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                        if (object5 instanceof LazyFieldLite) {
                            computeTagSize14 = CodedOutputStream.computeTagSize(i12);
                            computeUInt64SizeNoTag4 = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) object5);
                            computeTagSize15 = computeUInt64SizeNoTag4 + computeTagSize14;
                            i9 += computeTagSize15;
                        } else {
                            int computeTagSize18 = CodedOutputStream.computeTagSize(i12);
                            int serializedSize4 = ((AbstractMessageLite) ((MessageLite) object5)).getSerializedSize(messageFieldSchema4);
                            computeTagSize15 = CodedOutputStream.computeUInt32SizeNoTag(serializedSize4) + serializedSize4 + computeTagSize18;
                            i9 += computeTagSize15;
                        }
                    }
                    i2 = i4;
                    break;
                case 61:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize15 = CodedOutputStream.computeBytesSize(i12, (ByteString) unsafe.getObject(abstractMessageLite2, j));
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 62:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        int oneofIntAt2 = oneofIntAt(j, abstractMessageLite2);
                        computeTagSize14 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag4 = CodedOutputStream.computeUInt32SizeNoTag(oneofIntAt2);
                        computeTagSize15 = computeUInt64SizeNoTag4 + computeTagSize14;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 63:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        int oneofIntAt3 = oneofIntAt(j, abstractMessageLite2);
                        computeTagSize14 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag4 = CodedOutputStream.computeUInt64SizeNoTag(oneofIntAt3);
                        computeTagSize15 = computeUInt64SizeNoTag4 + computeTagSize14;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 64:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize12 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize12 + 4;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 65:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize11 = CodedOutputStream.computeTagSize(i12);
                        computeTagSize15 = computeTagSize11 + 8;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 66:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        int oneofIntAt4 = oneofIntAt(j, abstractMessageLite2);
                        computeTagSize14 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag4 = CodedOutputStream.computeSInt32SizeNoTag(oneofIntAt4);
                        computeTagSize15 = computeUInt64SizeNoTag4 + computeTagSize14;
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                case 67:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        long oneofLongAt3 = oneofLongAt(j, abstractMessageLite2);
                        computeTagSize13 = CodedOutputStream.computeTagSize(i12);
                        computeUInt64SizeNoTag3 = CodedOutputStream.computeSInt64SizeNoTag(oneofLongAt3);
                        i9 += computeUInt64SizeNoTag3 + computeTagSize13;
                    }
                    i2 = i4;
                    break;
                case 68:
                    if (messageSchema.isOneofPresent(i12, i7, abstractMessageLite2)) {
                        computeTagSize15 = ((AbstractMessageLite) ((MessageLite) unsafe.getObject(abstractMessageLite2, j))).getSerializedSize(messageSchema.getMessageFieldSchema(i7)) + (CodedOutputStream.computeTagSize(i12) * 2);
                        i9 += computeTagSize15;
                    }
                    i2 = i4;
                    break;
                default:
                    i2 = i4;
                    break;
            }
            i7 += 3;
            i4 = i2;
            i5 = 1048575;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x01f0, code lost:
    
        if (r4 != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00d5, code lost:
    
        if (r4 != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00d7, code lost:
    
        r8 = 1231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00d8, code lost:
    
        r3 = r8 + r3;
     */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int hashCode(androidx.datastore.preferences.protobuf.GeneratedMessageLite r12) {
        /*
            Method dump skipped, instructions count: 764
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.hashCode(androidx.datastore.preferences.protobuf.GeneratedMessageLite):int");
    }

    public final boolean isFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = i2 & 1048575;
        if (j == 1048575) {
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long j2 = typeAndOffsetAt & 1048575;
            switch (type(typeAndOffsetAt)) {
                case 0:
                    if (Double.doubleToRawLongBits(UnsafeUtil.MEMORY_ACCESSOR.getDouble(j2, obj)) == 0) {
                        return false;
                    }
                    break;
                case 1:
                    if (Float.floatToRawIntBits(UnsafeUtil.MEMORY_ACCESSOR.getFloat(j2, obj)) == 0) {
                        return false;
                    }
                    break;
                case 2:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 3:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 4:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 5:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 6:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 7:
                    return UnsafeUtil.MEMORY_ACCESSOR.getBoolean(j2, obj);
                case 8:
                    Object object = UnsafeUtil.getObject(j2, obj);
                    if (object instanceof String) {
                        return !((String) object).isEmpty();
                    }
                    if (object instanceof ByteString) {
                        return !ByteString.EMPTY.equals(object);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    if (UnsafeUtil.getObject(j2, obj) == null) {
                        return false;
                    }
                    break;
                case 10:
                    return !ByteString.EMPTY.equals(UnsafeUtil.getObject(j2, obj));
                case 11:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 12:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 13:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 14:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 15:
                    if (UnsafeUtil.getInt(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 16:
                    if (UnsafeUtil.getLong(j2, obj) == 0) {
                        return false;
                    }
                    break;
                case 17:
                    if (UnsafeUtil.getObject(j2, obj) == null) {
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } else if (((1 << (i2 >>> 20)) & UnsafeUtil.getInt(j, obj)) == 0) {
            return false;
        }
        return true;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final boolean isInitialized(Object obj) {
        int i;
        int i2;
        int i3;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        loop0: while (true) {
            if (i6 < this.checkInitializedCount) {
                int i7 = this.intArray[i6];
                int[] iArr = this.buffer;
                int i8 = iArr[i7];
                int typeAndOffsetAt = typeAndOffsetAt(i7);
                int i9 = iArr[i7 + 2];
                int i10 = i9 & 1048575;
                int i11 = 1 << (i9 >>> 20);
                if (i10 != i4) {
                    if (i10 != 1048575) {
                        i5 = UNSAFE.getInt(obj, i10);
                    }
                    i2 = i7;
                    i3 = i5;
                    i = i10;
                } else {
                    int i12 = i5;
                    i = i4;
                    i2 = i7;
                    i3 = i12;
                }
                if ((268435456 & typeAndOffsetAt) != 0 && !isFieldPresent(i2, obj, i, i3, i11)) {
                    break;
                }
                int type = type(typeAndOffsetAt);
                if (type == 9 || type == 17) {
                    if (isFieldPresent(i2, obj, i, i3, i11) && !getMessageFieldSchema(i2).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                        break;
                    }
                    i6++;
                    i4 = i;
                    i5 = i3;
                } else {
                    if (type != 27) {
                        if (type == 60 || type == 68) {
                            if (isOneofPresent(i8, i2, obj) && !getMessageFieldSchema(i2).isInitialized(UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj))) {
                                break;
                            }
                            i6++;
                            i4 = i;
                            i5 = i3;
                        } else if (type != 49) {
                            if (type == 50) {
                                Object object = UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                                ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                MapFieldLite mapFieldLite = (MapFieldLite) object;
                                if (!mapFieldLite.isEmpty()) {
                                    if (((MapEntryLite) this.objects[(i2 / 3) * 2]).metadata.valueType.getJavaType() == WireFormat$JavaType.MESSAGE) {
                                        Schema schema = null;
                                        for (Object obj2 : mapFieldLite.values()) {
                                            if (schema == null) {
                                                schema = Protobuf.INSTANCE.schemaFor(obj2.getClass());
                                            }
                                            if (!schema.isInitialized(obj2)) {
                                                break loop0;
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                            i6++;
                            i4 = i;
                            i5 = i3;
                        }
                    }
                    List list = (List) UnsafeUtil.getObject(typeAndOffsetAt & 1048575, obj);
                    if (list.isEmpty()) {
                        continue;
                    } else {
                        Schema messageFieldSchema = getMessageFieldSchema(i2);
                        for (int i13 = 0; i13 < list.size(); i13++) {
                            if (!messageFieldSchema.isInitialized(list.get(i13))) {
                                break loop0;
                            }
                        }
                    }
                    i6++;
                    i4 = i;
                    i5 = i3;
                }
            } else if (!this.hasExtensions || this.extensionSchema.getExtensions(obj).isInitialized()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isOneofPresent(int i, int i2, Object obj) {
        return UnsafeUtil.getInt((long) (this.buffer[i2 + 2] & 1048575), obj) == i;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void makeImmutable(Object obj) {
        if (isMutable(obj)) {
            if (obj instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) obj;
                generatedMessageLite.setMemoizedSerializedSize(Integer.MAX_VALUE);
                generatedMessageLite.memoizedHashCode = 0;
                generatedMessageLite.markImmutable();
            }
            int[] iArr = this.buffer;
            int length = iArr.length;
            for (int i = 0; i < length; i += 3) {
                int typeAndOffsetAt = typeAndOffsetAt(i);
                long j = 1048575 & typeAndOffsetAt;
                int type = type(typeAndOffsetAt);
                if (type != 9) {
                    if (type != 60 && type != 68) {
                        switch (type) {
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                                ((ListFieldSchemaLite) this.listFieldSchema).getClass();
                                AbstractProtobufList abstractProtobufList = (AbstractProtobufList) ((Internal.ProtobufList) UnsafeUtil.getObject(j, obj));
                                if (abstractProtobufList.isMutable) {
                                    abstractProtobufList.isMutable = false;
                                    break;
                                } else {
                                    break;
                                }
                            case 50:
                                Unsafe unsafe = UNSAFE;
                                Object object = unsafe.getObject(obj, j);
                                if (object != null) {
                                    ((MapFieldSchemaLite) this.mapFieldSchema).getClass();
                                    ((MapFieldLite) object).makeImmutable();
                                    unsafe.putObject(obj, j, object);
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } else if (isOneofPresent(iArr[i], i, obj)) {
                        getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                    }
                }
                if (isFieldPresent(i, obj)) {
                    getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(obj, j));
                }
            }
            this.unknownFieldSchema.makeImmutable(obj);
            if (this.hasExtensions) {
                this.extensionSchema.makeImmutable(obj);
            }
        }
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void mergeFrom(Object obj, CodedInputStreamReader codedInputStreamReader, ExtensionRegistryLite extensionRegistryLite) {
        extensionRegistryLite.getClass();
        if (isMutable(obj)) {
            mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, obj, codedInputStreamReader, extensionRegistryLite);
        } else {
            throw new IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public final void mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema r22, androidx.datastore.preferences.protobuf.ExtensionSchema r23, java.lang.Object r24, androidx.datastore.preferences.protobuf.CodedInputStreamReader r25, androidx.datastore.preferences.protobuf.ExtensionRegistryLite r26) {
        /*
            Method dump skipped, instructions count: 2230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.mergeFromHelper(androidx.datastore.preferences.protobuf.UnknownFieldSchema, androidx.datastore.preferences.protobuf.ExtensionSchema, java.lang.Object, androidx.datastore.preferences.protobuf.CodedInputStreamReader, androidx.datastore.preferences.protobuf.ExtensionRegistryLite):void");
    }

    public final void mergeMap(Object obj, int i, Object obj2, ExtensionRegistryLite extensionRegistryLite, CodedInputStreamReader codedInputStreamReader) {
        long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        Object object = UnsafeUtil.getObject(typeAndOffsetAt, obj);
        MapFieldSchema mapFieldSchema = this.mapFieldSchema;
        if (object == null) {
            ((MapFieldSchemaLite) mapFieldSchema).getClass();
            object = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
            UnsafeUtil.putObject(typeAndOffsetAt, obj, object);
        } else {
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) mapFieldSchema;
            mapFieldSchemaLite.getClass();
            if (!((MapFieldLite) object).isMutable()) {
                mapFieldSchemaLite.getClass();
                MapFieldLite mutableCopy = MapFieldLite.EMPTY_MAP_FIELD.mutableCopy();
                mapFieldSchemaLite.mergeFrom(mutableCopy, object);
                UnsafeUtil.putObject(typeAndOffsetAt, obj, mutableCopy);
                object = mutableCopy;
            }
        }
        MapFieldSchemaLite mapFieldSchemaLite2 = (MapFieldSchemaLite) mapFieldSchema;
        mapFieldSchemaLite2.getClass();
        MapFieldLite mapFieldLite = (MapFieldLite) object;
        mapFieldSchemaLite2.getClass();
        MapEntryLite.Metadata metadata = ((MapEntryLite) obj2).metadata;
        codedInputStreamReader.requireWireType(2);
        CodedInputStream codedInputStream = codedInputStreamReader.input;
        int pushLimit = codedInputStream.pushLimit(codedInputStream.readUInt32());
        Object obj3 = metadata.defaultKey;
        Object obj4 = metadata.defaultValue;
        Object obj5 = obj4;
        while (true) {
            try {
                int fieldNumber = codedInputStreamReader.getFieldNumber();
                if (fieldNumber == Integer.MAX_VALUE || codedInputStream.isAtEnd()) {
                    break;
                }
                if (fieldNumber == 1) {
                    obj3 = codedInputStreamReader.readField(metadata.keyType, null, null);
                } else if (fieldNumber != 2) {
                    try {
                        if (!codedInputStreamReader.skipField()) {
                            throw new InvalidProtocolBufferException("Unable to parse map entry.");
                        }
                    } catch (InvalidProtocolBufferException.InvalidWireTypeException unused) {
                        if (!codedInputStreamReader.skipField()) {
                            throw new InvalidProtocolBufferException("Unable to parse map entry.");
                        }
                    }
                } else {
                    obj5 = codedInputStreamReader.readField(metadata.valueType, obj4.getClass(), extensionRegistryLite);
                }
            } catch (Throwable th) {
                codedInputStream.popLimit(pushLimit);
                throw th;
            }
        }
        mapFieldLite.put(obj3, obj5);
        codedInputStream.popLimit(pushLimit);
    }

    public final void mergeMessage(int i, Object obj, Object obj2) {
        if (isFieldPresent(i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.buffer[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isFieldPresent(i, obj)) {
                if (isMutable(object)) {
                    Object newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                setFieldPresent(i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                Object newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final void mergeOneofMessage(int i, Object obj, Object obj2) {
        int[] iArr = this.buffer;
        int i2 = iArr[i];
        if (isOneofPresent(i2, i, obj2)) {
            long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(obj2, typeAndOffsetAt);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + iArr[i] + " is present but null: " + obj2);
            }
            Schema messageFieldSchema = getMessageFieldSchema(i);
            if (!isOneofPresent(i2, i, obj)) {
                if (isMutable(object)) {
                    Object newInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(newInstance, object);
                    unsafe.putObject(obj, typeAndOffsetAt, newInstance);
                } else {
                    unsafe.putObject(obj, typeAndOffsetAt, object);
                }
                setOneofPresent(i2, i, obj);
                return;
            }
            Object object2 = unsafe.getObject(obj, typeAndOffsetAt);
            if (!isMutable(object2)) {
                Object newInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance2, object2);
                unsafe.putObject(obj, typeAndOffsetAt, newInstance2);
                object2 = newInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    public final Object mutableMessageFieldForMerge(int i, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i);
        long typeAndOffsetAt = typeAndOffsetAt(i) & 1048575;
        if (!isFieldPresent(i, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt);
        if (isMutable(object)) {
            return object;
        }
        Object newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    public final Object mutableOneofMessageFieldForMerge(int i, int i2, Object obj) {
        Schema messageFieldSchema = getMessageFieldSchema(i2);
        if (!isOneofPresent(i, i2, obj)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(obj, typeAndOffsetAt(i2) & 1048575);
        if (isMutable(object)) {
            return object;
        }
        Object newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    @Override // androidx.datastore.preferences.protobuf.Schema
    public final Object newInstance() {
        ((NewInstanceSchemaLite) this.newInstanceSchema).getClass();
        return ((GeneratedMessageLite) this.defaultInstance).newMutableInstance$1();
    }

    public final int positionForFieldNumber(int i) {
        if (i >= this.minFieldNumber && i <= this.maxFieldNumber) {
            int[] iArr = this.buffer;
            int length = (iArr.length / 3) - 1;
            int i2 = 0;
            while (i2 <= length) {
                int i3 = (length + i2) >>> 1;
                int i4 = i3 * 3;
                int i5 = iArr[i4];
                if (i == i5) {
                    return i4;
                }
                if (i < i5) {
                    length = i3 - 1;
                } else {
                    i2 = i3 + 1;
                }
            }
        }
        return -1;
    }

    public final void readGroupList(Object obj, long j, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        Internal.ProtobufList mutableListAt = ((ListFieldSchemaLite) this.listFieldSchema).mutableListAt(j, obj);
        int i = codedInputStreamReader.tag;
        if ((i & 7) != 3) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            Object newInstance = schema.newInstance();
            codedInputStreamReader.mergeGroupFieldInternal(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            mutableListAt.add(newInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == i);
        codedInputStreamReader.nextTag = readTag;
    }

    public final void readMessageList(Object obj, int i, CodedInputStreamReader codedInputStreamReader, Schema schema, ExtensionRegistryLite extensionRegistryLite) {
        int readTag;
        Internal.ProtobufList mutableListAt = ((ListFieldSchemaLite) this.listFieldSchema).mutableListAt(i & 1048575, obj);
        int i2 = codedInputStreamReader.tag;
        if ((i2 & 7) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            Object newInstance = schema.newInstance();
            codedInputStreamReader.mergeMessageFieldInternal(newInstance, schema, extensionRegistryLite);
            schema.makeImmutable(newInstance);
            mutableListAt.add(newInstance);
            CodedInputStream codedInputStream = codedInputStreamReader.input;
            if (codedInputStream.isAtEnd() || codedInputStreamReader.nextTag != 0) {
                return;
            } else {
                readTag = codedInputStream.readTag();
            }
        } while (readTag == i2);
        codedInputStreamReader.nextTag = readTag;
    }

    public final void readString(int i, CodedInputStreamReader codedInputStreamReader, Object obj) {
        if ((536870912 & i) != 0) {
            codedInputStreamReader.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.input.readStringRequireUtf8());
        } else if (!this.lite) {
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.readBytes());
        } else {
            codedInputStreamReader.requireWireType(2);
            UnsafeUtil.putObject(i & 1048575, obj, codedInputStreamReader.input.readString());
        }
    }

    public final void readStringList(int i, CodedInputStreamReader codedInputStreamReader, Object obj) {
        boolean z = (536870912 & i) != 0;
        ListFieldSchema listFieldSchema = this.listFieldSchema;
        if (z) {
            codedInputStreamReader.readStringListInternal(((ListFieldSchemaLite) listFieldSchema).mutableListAt(i & 1048575, obj), true);
        } else {
            codedInputStreamReader.readStringListInternal(((ListFieldSchemaLite) listFieldSchema).mutableListAt(i & 1048575, obj), false);
        }
    }

    public final void setFieldPresent(int i, Object obj) {
        int i2 = this.buffer[i + 2];
        long j = 1048575 & i2;
        if (j == 1048575) {
            return;
        }
        UnsafeUtil.putInt((1 << (i2 >>> 20)) | UnsafeUtil.getInt(j, obj), j, obj);
    }

    public final void setOneofPresent(int i, int i2, Object obj) {
        UnsafeUtil.putInt(i, this.buffer[i2 + 2] & 1048575, obj);
    }

    public final int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    public final void writeMapHelper(CodedOutputStreamWriter codedOutputStreamWriter, int i, Object obj, int i2) {
        if (obj != null) {
            Object obj2 = this.objects[(i2 / 3) * 2];
            MapFieldSchemaLite mapFieldSchemaLite = (MapFieldSchemaLite) this.mapFieldSchema;
            mapFieldSchemaLite.getClass();
            MapEntryLite.Metadata metadata = ((MapEntryLite) obj2).metadata;
            mapFieldSchemaLite.getClass();
            CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
            codedOutputStream.getClass();
            for (Map.Entry entry : ((MapFieldLite) obj).entrySet()) {
                codedOutputStream.writeTag(i, 2);
                codedOutputStream.writeUInt32NoTag(MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue()));
                Object key = entry.getKey();
                Object value = entry.getValue();
                FieldSet.writeElement(codedOutputStream, metadata.keyType, 1, key);
                FieldSet.writeElement(codedOutputStream, metadata.valueType, 2, value);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0647  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0c0b  */
    @Override // androidx.datastore.preferences.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void writeTo(java.lang.Object r26, androidx.datastore.preferences.protobuf.CodedOutputStreamWriter r27) {
        /*
            Method dump skipped, instructions count: 3394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.MessageSchema.writeTo(java.lang.Object, androidx.datastore.preferences.protobuf.CodedOutputStreamWriter):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // androidx.datastore.preferences.protobuf.Schema
    public final void mergeFrom(Object obj, Object obj2) {
        Object obj3;
        if (isMutable(obj)) {
            obj2.getClass();
            int i = 0;
            while (true) {
                int[] iArr = this.buffer;
                if (i < iArr.length) {
                    int typeAndOffsetAt = typeAndOffsetAt(i);
                    long j = 1048575 & typeAndOffsetAt;
                    int i2 = iArr[i];
                    switch (type(typeAndOffsetAt)) {
                        case 0:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.MemoryAccessor memoryAccessor = UnsafeUtil.MEMORY_ACCESSOR;
                                obj3 = obj;
                                memoryAccessor.putDouble(obj3, j, memoryAccessor.getDouble(j, obj2));
                                setFieldPresent(i, obj3);
                                break;
                            }
                            obj3 = obj;
                            break;
                        case 1:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.MemoryAccessor memoryAccessor2 = UnsafeUtil.MEMORY_ACCESSOR;
                                memoryAccessor2.putFloat(obj, j, memoryAccessor2.getFloat(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 2:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 3:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 4:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 5:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 6:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 7:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.MemoryAccessor memoryAccessor3 = UnsafeUtil.MEMORY_ACCESSOR;
                                memoryAccessor3.putBoolean(obj, j, memoryAccessor3.getBoolean(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 8:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 9:
                            mergeMessage(i, obj, obj2);
                            obj3 = obj;
                            break;
                        case 10:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 11:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 12:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 13:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 14:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 15:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putInt(UnsafeUtil.getInt(j, obj2), j, obj);
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 16:
                            if (isFieldPresent(i, obj2)) {
                                UnsafeUtil.putLong(obj, j, UnsafeUtil.getLong(j, obj2));
                                setFieldPresent(i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 17:
                            mergeMessage(i, obj, obj2);
                            obj3 = obj;
                            break;
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                            ((ListFieldSchemaLite) this.listFieldSchema).getClass();
                            Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
                            Internal.ProtobufList protobufList2 = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj2);
                            int size = protobufList.size();
                            int size2 = protobufList2.size();
                            if (size > 0 && size2 > 0) {
                                if (!((AbstractProtobufList) protobufList).isMutable) {
                                    protobufList = protobufList.mutableCopyWithCapacity(size2 + size);
                                }
                                protobufList.addAll(protobufList2);
                            }
                            if (size > 0) {
                                protobufList2 = protobufList;
                            }
                            UnsafeUtil.putObject(j, obj, protobufList2);
                            obj3 = obj;
                            break;
                        case 50:
                            Class cls = SchemaUtil.GENERATED_MESSAGE_CLASS;
                            UnsafeUtil.putObject(j, obj, ((MapFieldSchemaLite) this.mapFieldSchema).mergeFrom(UnsafeUtil.getObject(j, obj), UnsafeUtil.getObject(j, obj2)));
                            obj3 = obj;
                            break;
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                        case 58:
                        case 59:
                            if (isOneofPresent(i2, i, obj2)) {
                                UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                                setOneofPresent(i2, i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 60:
                            mergeOneofMessage(i, obj, obj2);
                            obj3 = obj;
                            break;
                        case 61:
                        case 62:
                        case 63:
                        case 64:
                        case 65:
                        case 66:
                        case 67:
                            if (isOneofPresent(i2, i, obj2)) {
                                UnsafeUtil.putObject(j, obj, UnsafeUtil.getObject(j, obj2));
                                setOneofPresent(i2, i, obj);
                            }
                            obj3 = obj;
                            break;
                        case 68:
                            mergeOneofMessage(i, obj, obj2);
                            obj3 = obj;
                            break;
                        default:
                            obj3 = obj;
                            break;
                    }
                    i += 3;
                    obj = obj3;
                } else {
                    Object obj4 = obj;
                    Class cls2 = SchemaUtil.GENERATED_MESSAGE_CLASS;
                    UnknownFieldSchema unknownFieldSchema = this.unknownFieldSchema;
                    unknownFieldSchema.setToMessage(obj4, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(obj4), unknownFieldSchema.getFromMessage(obj2)));
                    if (this.hasExtensions) {
                        SchemaUtil.mergeExtensions(this.extensionSchema, obj4, obj2);
                        return;
                    }
                    return;
                }
            }
        } else {
            throw new IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    public final boolean isFieldPresent(int i, Object obj, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return isFieldPresent(i, obj);
        }
        return (i3 & i4) != 0;
    }
}
