package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.LazyField;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public final class FieldSet {
    public static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
    public final SmallSortedMap fields;
    public boolean hasLazyField;
    public boolean isImmutable;

    /* renamed from: androidx.datastore.preferences.protobuf.FieldSet$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;
        public static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$JavaType;

        static {
            int[] iArr = new int[WireFormat$FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat$FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.GROUP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.MESSAGE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.STRING.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.BYTES.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.UINT32.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED32.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SFIXED64.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT32.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.SINT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat$FieldType.ENUM.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr2 = new int[WireFormat$JavaType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$JavaType = iArr2;
            try {
                iArr2[WireFormat$JavaType.INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.FLOAT.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.DOUBLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.STRING.ordinal()] = 6;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.BYTE_STRING.ordinal()] = 7;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.ENUM.ordinal()] = 8;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat$JavaType.MESSAGE.ordinal()] = 9;
            } catch (NoSuchFieldError unused27) {
            }
        }
    }

    public /* synthetic */ FieldSet(SmallSortedMap smallSortedMap, AnonymousClass1 anonymousClass1) {
        this(smallSortedMap);
    }

    public static int computeElementSize(WireFormat$FieldType wireFormat$FieldType, int i, Object obj) {
        int iComputeTagSize = CodedOutputStream.computeTagSize(i);
        if (wireFormat$FieldType == WireFormat$FieldType.GROUP) {
            iComputeTagSize *= 2;
        }
        return computeElementSizeNoTag(wireFormat$FieldType, obj) + iComputeTagSize;
    }

    public static int computeElementSizeNoTag(WireFormat$FieldType wireFormat$FieldType, Object obj) {
        int serializedSize;
        int iComputeUInt32SizeNoTag;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
            case 1:
                ((Double) obj).getClass();
                Logger logger = CodedOutputStream.logger;
                return 8;
            case 2:
                ((Float) obj).getClass();
                Logger logger2 = CodedOutputStream.logger;
                return 4;
            case 3:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) obj).longValue());
            case 4:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) obj).longValue());
            case 5:
                return CodedOutputStream.computeUInt64SizeNoTag(((Integer) obj).intValue());
            case 6:
                ((Long) obj).getClass();
                Logger logger3 = CodedOutputStream.logger;
                return 8;
            case 7:
                ((Integer) obj).getClass();
                Logger logger4 = CodedOutputStream.logger;
                return 4;
            case 8:
                ((Boolean) obj).getClass();
                Logger logger5 = CodedOutputStream.logger;
                return 1;
            case 9:
                Logger logger6 = CodedOutputStream.logger;
                return ((GeneratedMessageLite) ((MessageLite) obj)).getSerializedSize(null);
            case 10:
                if (!(obj instanceof LazyField)) {
                    Logger logger7 = CodedOutputStream.logger;
                    serializedSize = ((GeneratedMessageLite) ((MessageLite) obj)).getSerializedSize(null);
                    iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(serializedSize);
                    break;
                } else {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) obj);
                }
            case 11:
                return obj instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) obj) : CodedOutputStream.computeStringSizeNoTag((String) obj);
            case 12:
                if (!(obj instanceof ByteString)) {
                    Logger logger8 = CodedOutputStream.logger;
                    serializedSize = ((byte[]) obj).length;
                    iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(serializedSize);
                    break;
                } else {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
                }
            case 13:
                return CodedOutputStream.computeUInt32SizeNoTag(((Integer) obj).intValue());
            case 14:
                ((Integer) obj).getClass();
                Logger logger9 = CodedOutputStream.logger;
                return 4;
            case 15:
                ((Long) obj).getClass();
                Logger logger10 = CodedOutputStream.logger;
                return 8;
            case 16:
                return CodedOutputStream.computeSInt32SizeNoTag(((Integer) obj).intValue());
            case 17:
                return CodedOutputStream.computeSInt64SizeNoTag(((Long) obj).longValue());
            case 18:
                return CodedOutputStream.computeUInt64SizeNoTag(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
        return iComputeUInt32SizeNoTag + serializedSize;
    }

    public static int computeFieldSize(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        int i = extensionDescriptor.number;
        if (!extensionDescriptor.isRepeated) {
            return computeElementSize(wireFormat$FieldType, i, obj);
        }
        List list = (List) obj;
        int size = list.size();
        int i2 = 0;
        if (!extensionDescriptor.isPacked) {
            int iComputeElementSize = 0;
            while (i2 < size) {
                iComputeElementSize += computeElementSize(wireFormat$FieldType, i, list.get(i2));
                i2++;
            }
            return iComputeElementSize;
        }
        if (list.isEmpty()) {
            return 0;
        }
        int iComputeElementSizeNoTag = 0;
        while (i2 < size) {
            iComputeElementSizeNoTag += computeElementSizeNoTag(wireFormat$FieldType, list.get(i2));
            i2++;
        }
        return CodedOutputStream.computeUInt32SizeNoTag(iComputeElementSizeNoTag) + CodedOutputStream.computeTagSize(i) + iComputeElementSizeNoTag;
    }

    public static int getMessageSetSerializedSize(Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        Object value = entry.getValue();
        if (extensionDescriptor.type.getJavaType() != WireFormat$JavaType.MESSAGE || extensionDescriptor.isRepeated || extensionDescriptor.isPacked) {
            return computeFieldSize(extensionDescriptor, value);
        }
        if (value instanceof LazyField) {
            int i = ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
            return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) value) + CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeUInt32SizeNoTag(i) + CodedOutputStream.computeTagSize(2) + (CodedOutputStream.computeTagSize(1) * 2);
        }
        int i2 = ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
        int iComputeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(i2) + CodedOutputStream.computeTagSize(2) + (CodedOutputStream.computeTagSize(1) * 2);
        int iComputeTagSize = CodedOutputStream.computeTagSize(3);
        int serializedSize = ((GeneratedMessageLite) ((MessageLite) value)).getSerializedSize(null);
        return CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize + iComputeTagSize + iComputeUInt32SizeNoTag;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void verifyType(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        Charset charset = Internal.UTF_8;
        obj.getClass();
        boolean z = true;
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$JavaType[wireFormat$FieldType.getJavaType().ordinal()]) {
            case 1:
                z = obj instanceof Integer;
                break;
            case 2:
                z = obj instanceof Long;
                break;
            case 3:
                z = obj instanceof Float;
                break;
            case 4:
                z = obj instanceof Double;
                break;
            case 5:
                z = obj instanceof Boolean;
                break;
            case 6:
                z = obj instanceof String;
                break;
            case 7:
                if (!(obj instanceof ByteString) && !(obj instanceof byte[])) {
                    z = false;
                    break;
                }
                break;
            case 8:
                if (!(obj instanceof Integer)) {
                }
                break;
            case 9:
                if (!(obj instanceof MessageLite) && !(obj instanceof LazyField)) {
                }
                break;
        }
        if (!z) {
            throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(extensionDescriptor.number), extensionDescriptor.type.getJavaType(), obj.getClass().getName()));
        }
    }

    public static void writeElement(CodedOutputStream codedOutputStream, WireFormat$FieldType wireFormat$FieldType, int i, Object obj) {
        if (wireFormat$FieldType == WireFormat$FieldType.GROUP) {
            codedOutputStream.writeTag(i, 3);
            ((GeneratedMessageLite) ((MessageLite) obj)).writeTo(codedOutputStream);
            codedOutputStream.writeTag(i, 4);
        }
        codedOutputStream.writeTag(i, wireFormat$FieldType.getWireType());
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
            case 1:
                codedOutputStream.writeFixed64NoTag(Double.doubleToRawLongBits(((Double) obj).doubleValue()));
                break;
            case 2:
                codedOutputStream.writeFixed32NoTag(Float.floatToRawIntBits(((Float) obj).floatValue()));
                break;
            case 3:
                codedOutputStream.writeUInt64NoTag(((Long) obj).longValue());
                break;
            case 4:
                codedOutputStream.writeUInt64NoTag(((Long) obj).longValue());
                break;
            case 5:
                codedOutputStream.writeInt32NoTag(((Integer) obj).intValue());
                break;
            case 6:
                codedOutputStream.writeFixed64NoTag(((Long) obj).longValue());
                break;
            case 7:
                codedOutputStream.writeFixed32NoTag(((Integer) obj).intValue());
                break;
            case 8:
                codedOutputStream.write(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
                break;
            case 9:
                ((GeneratedMessageLite) ((MessageLite) obj)).writeTo(codedOutputStream);
                break;
            case 10:
                codedOutputStream.writeMessageNoTag((MessageLite) obj);
                break;
            case 11:
                if (!(obj instanceof ByteString)) {
                    codedOutputStream.writeStringNoTag((String) obj);
                    break;
                } else {
                    codedOutputStream.writeBytesNoTag((ByteString) obj);
                    break;
                }
            case 12:
                if (!(obj instanceof ByteString)) {
                    byte[] bArr = (byte[]) obj;
                    codedOutputStream.writeByteArrayNoTag(bArr.length, bArr);
                    break;
                } else {
                    codedOutputStream.writeBytesNoTag((ByteString) obj);
                    break;
                }
            case 13:
                codedOutputStream.writeUInt32NoTag(((Integer) obj).intValue());
                break;
            case 14:
                codedOutputStream.writeFixed32NoTag(((Integer) obj).intValue());
                break;
            case 15:
                codedOutputStream.writeFixed64NoTag(((Long) obj).longValue());
                break;
            case 16:
                int iIntValue = ((Integer) obj).intValue();
                codedOutputStream.writeUInt32NoTag((iIntValue >> 31) ^ (iIntValue << 1));
                break;
            case 17:
                long jLongValue = ((Long) obj).longValue();
                codedOutputStream.writeUInt64NoTag((jLongValue >> 63) ^ (jLongValue << 1));
                break;
            case 18:
                codedOutputStream.writeInt32NoTag(((Integer) obj).intValue());
                break;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FieldSet) {
            return this.fields.equals(((FieldSet) obj).fields);
        }
        return false;
    }

    public final Object getField(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor) {
        Object obj = this.fields.get(extensionDescriptor);
        if (!(obj instanceof LazyField)) {
            return obj;
        }
        LazyField lazyField = (LazyField) obj;
        return lazyField.getValue(lazyField.defaultInstance);
    }

    public final int hashCode() {
        return this.fields.hashCode();
    }

    public final boolean isInitialized() {
        SmallSortedMap smallSortedMap = this.fields;
        int size = smallSortedMap.entryList.size();
        for (int i = 0; i < size; i++) {
            if (!isInitialized(smallSortedMap.getArrayEntryAt(i))) {
                return false;
            }
        }
        Iterator it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            if (!isInitialized((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    public final Iterator iterator() {
        SmallSortedMap smallSortedMap = this.fields;
        return smallSortedMap.isEmpty() ? Collections.emptyIterator() : this.hasLazyField ? new LazyField.LazyIterator(smallSortedMap.entrySet().iterator()) : smallSortedMap.entrySet().iterator();
    }

    public final void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        SmallSortedMap smallSortedMap = this.fields;
        int size = smallSortedMap.entryList.size();
        for (int i = 0; i < size; i++) {
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i);
            if (arrayEntryAt.getValue() instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) arrayEntryAt.getValue();
                generatedMessageLite.getClass();
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                protobuf.schemaFor(generatedMessageLite.getClass()).makeImmutable(generatedMessageLite);
                generatedMessageLite.markImmutable();
            }
        }
        smallSortedMap.makeImmutable();
        this.isImmutable = true;
    }

    public final void mergeFromField(Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        Object value = entry.getValue();
        boolean z = value instanceof LazyField;
        boolean z2 = extensionDescriptor.isRepeated;
        SmallSortedMap smallSortedMap = this.fields;
        if (z2) {
            if (z) {
                throw new IllegalStateException("Lazy fields can not be repeated");
            }
            Object field = getField(extensionDescriptor);
            if (field == null) {
                field = new ArrayList();
            }
            for (Object obj : (List) value) {
                List list = (List) field;
                if (obj instanceof byte[]) {
                    byte[] bArr = (byte[]) obj;
                    byte[] bArr2 = new byte[bArr.length];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    obj = bArr2;
                }
                list.add(obj);
            }
            smallSortedMap.put((Comparable) extensionDescriptor, field);
            return;
        }
        if (extensionDescriptor.type.getJavaType() != WireFormat$JavaType.MESSAGE) {
            if (z) {
                throw new IllegalStateException("Lazy fields must be message-valued");
            }
            if (value instanceof byte[]) {
                byte[] bArr3 = (byte[]) value;
                byte[] bArr4 = new byte[bArr3.length];
                System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                value = bArr4;
            }
            smallSortedMap.put((Comparable) extensionDescriptor, value);
            return;
        }
        Object field2 = getField(extensionDescriptor);
        if (field2 != null) {
            if (z) {
                LazyField lazyField = (LazyField) value;
                value = lazyField.getValue(lazyField.defaultInstance);
            }
            GeneratedMessageLite.Builder builder = ((MessageLite) field2).toBuilder();
            builder.mergeFrom((GeneratedMessageLite) ((MessageLite) value));
            smallSortedMap.put((Comparable) extensionDescriptor, (Object) builder.build());
            return;
        }
        if (value instanceof byte[]) {
            byte[] bArr5 = (byte[]) value;
            byte[] bArr6 = new byte[bArr5.length];
            System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
            value = bArr6;
        }
        smallSortedMap.put((Comparable) extensionDescriptor, value);
        if (z) {
            this.hasLazyField = true;
        }
    }

    public final void setField(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        if (!extensionDescriptor.isRepeated) {
            verifyType(extensionDescriptor, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                verifyType(extensionDescriptor, obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put((Comparable) extensionDescriptor, obj);
    }

    private FieldSet() {
        int i = SmallSortedMap.$r8$clinit;
        this.fields = new SmallSortedMap() { // from class: androidx.datastore.preferences.protobuf.SmallSortedMap.1
            @Override // androidx.datastore.preferences.protobuf.SmallSortedMap
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i2 = 0; i2 < this.entryList.size(); i2++) {
                        Map.Entry arrayEntryAt = getArrayEntryAt(i2);
                        if (((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey()).isRepeated) {
                            arrayEntryAt.setValue(Collections.unmodifiableList((List) arrayEntryAt.getValue()));
                        }
                    }
                    for (Map.Entry entry : getOverflowEntries()) {
                        if (((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).isRepeated) {
                            entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                        }
                    }
                }
                super.makeImmutable();
            }

            @Override // androidx.datastore.preferences.protobuf.SmallSortedMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
                return put((Comparable) obj, obj2);
            }
        };
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final FieldSet m899clone() {
        FieldSet fieldSet = new FieldSet();
        SmallSortedMap smallSortedMap = this.fields;
        int size = smallSortedMap.entryList.size();
        for (int i = 0; i < size; i++) {
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i);
            fieldSet.setField((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue());
        }
        for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
            fieldSet.setField((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue());
        }
        fieldSet.hasLazyField = this.hasLazyField;
        return fieldSet;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private FieldSet(boolean z) {
        this(new SmallSortedMap() { // from class: androidx.datastore.preferences.protobuf.SmallSortedMap.1
            @Override // androidx.datastore.preferences.protobuf.SmallSortedMap
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i2 = 0; i2 < this.entryList.size(); i2++) {
                        Map.Entry arrayEntryAt = getArrayEntryAt(i2);
                        if (((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey()).isRepeated) {
                            arrayEntryAt.setValue(Collections.unmodifiableList((List) arrayEntryAt.getValue()));
                        }
                    }
                    for (Map.Entry entry : getOverflowEntries()) {
                        if (((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).isRepeated) {
                            entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                        }
                    }
                }
                super.makeImmutable();
            }

            @Override // androidx.datastore.preferences.protobuf.SmallSortedMap, java.util.AbstractMap, java.util.Map
            public final /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
                return put((Comparable) obj, obj2);
            }
        });
        int i = SmallSortedMap.$r8$clinit;
        makeImmutable();
    }

    public static boolean isInitialized(Map.Entry entry) {
        boolean zIsInitialized;
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.type.getJavaType() == WireFormat$JavaType.MESSAGE) {
            if (extensionDescriptor.isRepeated) {
                List list = (List) entry.getValue();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Object obj = list.get(i);
                    if (obj instanceof MessageLiteOrBuilder) {
                        zIsInitialized = ((MessageLiteOrBuilder) obj).isInitialized();
                    } else {
                        if (!(obj instanceof LazyField)) {
                            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                        }
                        zIsInitialized = true;
                    }
                    if (!zIsInitialized) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof MessageLiteOrBuilder) {
                    return ((MessageLiteOrBuilder) value).isInitialized();
                }
                if (value instanceof LazyField) {
                    return true;
                }
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    private FieldSet(SmallSortedMap smallSortedMap) {
        this.fields = smallSortedMap;
        makeImmutable();
    }
}
