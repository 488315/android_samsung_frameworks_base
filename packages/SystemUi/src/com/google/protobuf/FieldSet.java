package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.LazyField;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FieldSet {
    public static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
    public final SmallSortedMap fields;
    public boolean hasLazyField;
    public boolean isImmutable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.google.protobuf.FieldSet$1 */
    public abstract /* synthetic */ class AbstractC45301 {
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

    public /* synthetic */ FieldSet(SmallSortedMap smallSortedMap, AbstractC45301 abstractC45301) {
        this(smallSortedMap);
    }

    public static int computeElementSize(WireFormat$FieldType wireFormat$FieldType, int i, Object obj) {
        int computeTagSize = CodedOutputStream.computeTagSize(i);
        if (wireFormat$FieldType == WireFormat$FieldType.GROUP) {
            computeTagSize *= 2;
        }
        return computeElementSizeNoTag(wireFormat$FieldType, obj) + computeTagSize;
    }

    public static int computeElementSizeNoTag(WireFormat$FieldType wireFormat$FieldType, Object obj) {
        switch (AbstractC45301.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
            case 1:
                ((Double) obj).doubleValue();
                Logger logger = CodedOutputStream.logger;
                return 8;
            case 2:
                ((Float) obj).floatValue();
                Logger logger2 = CodedOutputStream.logger;
                return 4;
            case 3:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) obj).longValue());
            case 4:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) obj).longValue());
            case 5:
                return CodedOutputStream.computeInt32SizeNoTag(((Integer) obj).intValue());
            case 6:
                ((Long) obj).longValue();
                Logger logger3 = CodedOutputStream.logger;
                return 8;
            case 7:
                ((Integer) obj).intValue();
                Logger logger4 = CodedOutputStream.logger;
                return 4;
            case 8:
                ((Boolean) obj).booleanValue();
                Logger logger5 = CodedOutputStream.logger;
                return 1;
            case 9:
                Logger logger6 = CodedOutputStream.logger;
                return ((GeneratedMessageLite) ((MessageLite) obj)).getSerializedSize(null);
            case 10:
                if (obj instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) obj);
                }
                Logger logger7 = CodedOutputStream.logger;
                int serializedSize = ((GeneratedMessageLite) ((MessageLite) obj)).getSerializedSize(null);
                return CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize;
            case 11:
                if (!(obj instanceof ByteString)) {
                    return CodedOutputStream.computeStringSizeNoTag((String) obj);
                }
                Logger logger8 = CodedOutputStream.logger;
                int size = ((ByteString) obj).size();
                return CodedOutputStream.computeUInt32SizeNoTag(size) + size;
            case 12:
                if (obj instanceof ByteString) {
                    Logger logger9 = CodedOutputStream.logger;
                    int size2 = ((ByteString) obj).size();
                    return CodedOutputStream.computeUInt32SizeNoTag(size2) + size2;
                }
                Logger logger10 = CodedOutputStream.logger;
                int length = ((byte[]) obj).length;
                return CodedOutputStream.computeUInt32SizeNoTag(length) + length;
            case 13:
                return CodedOutputStream.computeUInt32SizeNoTag(((Integer) obj).intValue());
            case 14:
                ((Integer) obj).intValue();
                Logger logger11 = CodedOutputStream.logger;
                return 4;
            case 15:
                ((Long) obj).longValue();
                Logger logger12 = CodedOutputStream.logger;
                return 8;
            case 16:
                int intValue = ((Integer) obj).intValue();
                return CodedOutputStream.computeUInt32SizeNoTag((intValue >> 31) ^ (intValue << 1));
            case 17:
                long longValue = ((Long) obj).longValue();
                return CodedOutputStream.computeUInt64SizeNoTag((longValue >> 63) ^ (longValue << 1));
            case 18:
                return obj instanceof Internal.EnumLite ? CodedOutputStream.computeInt32SizeNoTag(((Internal.EnumLite) obj).getNumber()) : CodedOutputStream.computeInt32SizeNoTag(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int computeFieldSize(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        int i = extensionDescriptor.number;
        if (!extensionDescriptor.isRepeated) {
            return computeElementSize(wireFormat$FieldType, i, obj);
        }
        int i2 = 0;
        if (!extensionDescriptor.isPacked) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                i2 += computeElementSize(wireFormat$FieldType, i, it.next());
            }
            return i2;
        }
        Iterator it2 = ((List) obj).iterator();
        while (it2.hasNext()) {
            i2 += computeElementSizeNoTag(wireFormat$FieldType, it2.next());
        }
        return CodedOutputStream.computeUInt32SizeNoTag(i2) + CodedOutputStream.computeTagSize(i) + i2;
    }

    public static int getMessageSetSerializedSize(Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        Object value = entry.getValue();
        if (extensionDescriptor.type.getJavaType() != WireFormat$JavaType.MESSAGE || extensionDescriptor.isRepeated || extensionDescriptor.isPacked) {
            return computeFieldSize(extensionDescriptor, value);
        }
        if (value instanceof LazyField) {
            int i = ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
            return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) value) + CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeUInt32Size(2, i) + (CodedOutputStream.computeTagSize(1) * 2);
        }
        int i2 = ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).number;
        int computeUInt32Size = CodedOutputStream.computeUInt32Size(2, i2) + (CodedOutputStream.computeTagSize(1) * 2);
        int computeTagSize = CodedOutputStream.computeTagSize(3);
        int serializedSize = ((GeneratedMessageLite) ((MessageLite) value)).getSerializedSize(null);
        return CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize + computeTagSize + computeUInt32Size;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
    
        if ((r3 instanceof com.google.protobuf.Internal.EnumLite) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:
    
        if ((r3 instanceof byte[]) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001e, code lost:
    
        if ((r3 instanceof com.google.protobuf.LazyField) == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void verifyType(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        WireFormat$FieldType wireFormat$FieldType = extensionDescriptor.type;
        Charset charset = Internal.UTF_8;
        obj.getClass();
        boolean z = false;
        switch (AbstractC45301.$SwitchMap$com$google$protobuf$WireFormat$JavaType[wireFormat$FieldType.getJavaType().ordinal()]) {
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
                if (!(obj instanceof ByteString)) {
                    break;
                }
                z = true;
                break;
            case 8:
                if (!(obj instanceof Integer)) {
                    break;
                }
                z = true;
                break;
            case 9:
                if (!(obj instanceof MessageLite)) {
                    break;
                }
                z = true;
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
            return;
        }
        codedOutputStream.writeTag(i, wireFormat$FieldType.getWireType());
        switch (AbstractC45301.$SwitchMap$com$google$protobuf$WireFormat$FieldType[wireFormat$FieldType.ordinal()]) {
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
                    codedOutputStream.writeByteArrayNoTag(bArr, bArr.length);
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
                int intValue = ((Integer) obj).intValue();
                codedOutputStream.writeUInt32NoTag((intValue >> 31) ^ (intValue << 1));
                break;
            case 17:
                long longValue = ((Long) obj).longValue();
                codedOutputStream.writeUInt64NoTag((longValue >> 63) ^ (longValue << 1));
                break;
            case 18:
                if (!(obj instanceof Internal.EnumLite)) {
                    codedOutputStream.writeInt32NoTag(((Integer) obj).intValue());
                    break;
                } else {
                    codedOutputStream.writeInt32NoTag(((Internal.EnumLite) obj).getNumber());
                    break;
                }
        }
    }

    public final void addRepeatedField(GeneratedMessageLite.ExtensionDescriptor extensionDescriptor, Object obj) {
        List list;
        if (!extensionDescriptor.isRepeated) {
            throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }
        verifyType(extensionDescriptor, obj);
        Object field = getField(extensionDescriptor);
        if (field == null) {
            list = new ArrayList();
            this.fields.put((Comparable) extensionDescriptor, (Object) list);
        } else {
            list = (List) field;
        }
        list.add(obj);
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
        return obj instanceof LazyField ? ((LazyField) obj).getValue() : obj;
    }

    public final int hashCode() {
        return this.fields.hashCode();
    }

    public final boolean isInitialized() {
        int i = 0;
        while (true) {
            SmallSortedMap smallSortedMap = this.fields;
            if (i >= smallSortedMap.getNumArrayEntries()) {
                Iterator it = smallSortedMap.getOverflowEntries().iterator();
                while (it.hasNext()) {
                    if (!isInitialized((Map.Entry) it.next())) {
                        return false;
                    }
                }
                return true;
            }
            if (!isInitialized(smallSortedMap.getArrayEntryAt(i))) {
                return false;
            }
            i++;
        }
    }

    public final Iterator iterator() {
        boolean z = this.hasLazyField;
        SmallSortedMap smallSortedMap = this.fields;
        return z ? new LazyField.LazyIterator(smallSortedMap.entrySet().iterator()) : smallSortedMap.entrySet().iterator();
    }

    public final void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        int i = 0;
        while (true) {
            SmallSortedMap smallSortedMap = this.fields;
            if (i >= smallSortedMap.getNumArrayEntries()) {
                smallSortedMap.makeImmutable();
                this.isImmutable = true;
                return;
            } else {
                Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i);
                if (arrayEntryAt.getValue() instanceof GeneratedMessageLite) {
                    ((GeneratedMessageLite) arrayEntryAt.getValue()).makeImmutable();
                }
                i++;
            }
        }
    }

    public final void mergeFromField(Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            value = ((LazyField) value).getValue();
        }
        boolean z = extensionDescriptor.isRepeated;
        SmallSortedMap smallSortedMap = this.fields;
        if (z) {
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
            GeneratedMessageLite.Builder builder = ((GeneratedMessageLite) ((MessageLite) field2)).toBuilder();
            builder.mergeFrom((GeneratedMessageLite) ((MessageLite) value));
            smallSortedMap.put((Comparable) extensionDescriptor, (Object) builder.build());
        } else {
            if (value instanceof byte[]) {
                byte[] bArr5 = (byte[]) value;
                byte[] bArr6 = new byte[bArr5.length];
                System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
                value = bArr6;
            }
            smallSortedMap.put((Comparable) extensionDescriptor, value);
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                verifyType(extensionDescriptor, it.next());
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
        final int i2 = 16;
        this.fields = new SmallSortedMap(i2) { // from class: com.google.protobuf.SmallSortedMap.1
            public C45361(final int i22) {
                super(i22);
            }

            @Override // com.google.protobuf.SmallSortedMap
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i3 = 0; i3 < getNumArrayEntries(); i3++) {
                        Map.Entry arrayEntryAt = getArrayEntryAt(i3);
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
        };
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final FieldSet m2770clone() {
        SmallSortedMap smallSortedMap;
        FieldSet fieldSet = new FieldSet();
        int i = 0;
        while (true) {
            smallSortedMap = this.fields;
            if (i >= smallSortedMap.getNumArrayEntries()) {
                break;
            }
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i);
            fieldSet.setField((GeneratedMessageLite.ExtensionDescriptor) arrayEntryAt.getKey(), arrayEntryAt.getValue());
            i++;
        }
        for (Map.Entry entry : smallSortedMap.getOverflowEntries()) {
            fieldSet.setField((GeneratedMessageLite.ExtensionDescriptor) entry.getKey(), entry.getValue());
        }
        fieldSet.hasLazyField = this.hasLazyField;
        return fieldSet;
    }

    public static boolean isInitialized(Map.Entry entry) {
        boolean z;
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (extensionDescriptor.type.getJavaType() == WireFormat$JavaType.MESSAGE) {
            if (extensionDescriptor.isRepeated) {
                for (Object obj : (List) entry.getValue()) {
                    if (obj instanceof MessageLiteOrBuilder) {
                        z = ((MessageLiteOrBuilder) obj).isInitialized();
                    } else {
                        if (!(obj instanceof LazyField)) {
                            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                        }
                        z = true;
                    }
                    if (!z) {
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private FieldSet(boolean z) {
        this(new SmallSortedMap(r0) { // from class: com.google.protobuf.SmallSortedMap.1
            public C45361(final int i) {
                super(i);
            }

            @Override // com.google.protobuf.SmallSortedMap
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i3 = 0; i3 < getNumArrayEntries(); i3++) {
                        Map.Entry arrayEntryAt = getArrayEntryAt(i3);
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
        });
        int i = SmallSortedMap.$r8$clinit;
        final int i2 = 0;
        makeImmutable();
    }

    private FieldSet(SmallSortedMap smallSortedMap) {
        this.fields = smallSortedMap;
        makeImmutable();
    }
}
