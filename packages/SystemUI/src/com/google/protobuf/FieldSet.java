package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.LazyField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FieldSet {
    public static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
    public final SmallSortedMap fields;
    public boolean hasLazyField;
    public boolean isImmutable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.google.protobuf.FieldSet$1, reason: invalid class name */
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
        int computeTagSize = CodedOutputStream.computeTagSize(i);
        if (wireFormat$FieldType == WireFormat$FieldType.GROUP) {
            computeTagSize *= 2;
        }
        return computeElementSizeNoTag(wireFormat$FieldType, obj) + computeTagSize;
    }

    public static int computeElementSizeNoTag(WireFormat$FieldType wireFormat$FieldType, Object obj) {
        int serializedSize;
        int computeUInt32SizeNoTag;
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
                return CodedOutputStream.computeInt32SizeNoTag(((Integer) obj).intValue());
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
                    serializedSize = ((GeneratedMessageLite) ((MessageLite) obj)).getSerializedSize();
                    computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(serializedSize);
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
                    computeUInt32SizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(serializedSize);
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
        return computeUInt32SizeNoTag + serializedSize;
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
        int serializedSize = ((GeneratedMessageLite) ((MessageLite) value)).getSerializedSize();
        return FieldSet$$ExternalSyntheticOutline0.m(serializedSize, serializedSize, computeTagSize, computeUInt32Size);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        if ((r4 instanceof com.google.protobuf.LazyField) == false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0029, code lost:
    
        if ((r4 instanceof com.google.protobuf.Internal.EnumLite) == false) goto L4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0032, code lost:
    
        if ((r4 instanceof byte[]) == false) goto L4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void verifyType(com.google.protobuf.GeneratedMessageLite.ExtensionDescriptor r3, java.lang.Object r4) {
        /*
            com.google.protobuf.WireFormat$FieldType r0 = r3.type
            java.nio.charset.Charset r1 = com.google.protobuf.Internal.UTF_8
            r4.getClass()
            int[] r1 = com.google.protobuf.FieldSet.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$JavaType
            com.google.protobuf.WireFormat$JavaType r0 = r0.getJavaType()
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            r2 = 0
            switch(r0) {
                case 1: goto L44;
                case 2: goto L41;
                case 3: goto L3e;
                case 4: goto L3b;
                case 5: goto L38;
                case 6: goto L35;
                case 7: goto L2c;
                case 8: goto L23;
                case 9: goto L1a;
                default: goto L18;
            }
        L18:
            r1 = r2
            goto L46
        L1a:
            boolean r0 = r4 instanceof com.google.protobuf.MessageLite
            if (r0 != 0) goto L46
            boolean r0 = r4 instanceof com.google.protobuf.LazyField
            if (r0 == 0) goto L18
            goto L46
        L23:
            boolean r0 = r4 instanceof java.lang.Integer
            if (r0 != 0) goto L46
            boolean r0 = r4 instanceof com.google.protobuf.Internal.EnumLite
            if (r0 == 0) goto L18
            goto L46
        L2c:
            boolean r0 = r4 instanceof com.google.protobuf.ByteString
            if (r0 != 0) goto L46
            boolean r0 = r4 instanceof byte[]
            if (r0 == 0) goto L18
            goto L46
        L35:
            boolean r1 = r4 instanceof java.lang.String
            goto L46
        L38:
            boolean r1 = r4 instanceof java.lang.Boolean
            goto L46
        L3b:
            boolean r1 = r4 instanceof java.lang.Double
            goto L46
        L3e:
            boolean r1 = r4 instanceof java.lang.Float
            goto L46
        L41:
            boolean r1 = r4 instanceof java.lang.Long
            goto L46
        L44:
            boolean r1 = r4 instanceof java.lang.Integer
        L46:
            if (r1 == 0) goto L49
            return
        L49:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            int r1 = r3.number
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            com.google.protobuf.WireFormat$FieldType r3 = r3.type
            com.google.protobuf.WireFormat$JavaType r3 = r3.getJavaType()
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getName()
            java.lang.Object[] r3 = new java.lang.Object[]{r1, r3, r4}
            java.lang.String r4 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r3 = java.lang.String.format(r4, r3)
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.FieldSet.verifyType(com.google.protobuf.GeneratedMessageLite$ExtensionDescriptor, java.lang.Object):void");
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
        int i = 0;
        while (true) {
            SmallSortedMap smallSortedMap = this.fields;
            if (i >= smallSortedMap.entryList.size()) {
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
            if (i >= smallSortedMap.entryList.size()) {
                smallSortedMap.makeImmutable();
                this.isImmutable = true;
                return;
            }
            Map.Entry arrayEntryAt = smallSortedMap.getArrayEntryAt(i);
            if (arrayEntryAt.getValue() instanceof GeneratedMessageLite) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) arrayEntryAt.getValue();
                generatedMessageLite.getClass();
                Protobuf protobuf = Protobuf.INSTANCE;
                protobuf.getClass();
                protobuf.schemaFor((Class) generatedMessageLite.getClass()).makeImmutable(generatedMessageLite);
                generatedMessageLite.markImmutable();
            }
            i++;
        }
    }

    public final void mergeFromField(Map.Entry entry) {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            LazyField lazyField = (LazyField) value;
            value = lazyField.getValue(lazyField.defaultInstance);
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
        final int i2 = 16;
        this.fields = new SmallSortedMap(i2) { // from class: com.google.protobuf.SmallSortedMap.1
            public AnonymousClass1(final int i22) {
                super(i22);
            }

            @Override // com.google.protobuf.SmallSortedMap
            public final void makeImmutable() {
                if (!this.isImmutable) {
                    for (int i3 = 0; i3 < this.entryList.size(); i3++) {
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
    public final FieldSet m3270clone() {
        SmallSortedMap smallSortedMap;
        FieldSet fieldSet = new FieldSet();
        int i = 0;
        while (true) {
            smallSortedMap = this.fields;
            if (i >= smallSortedMap.entryList.size()) {
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private FieldSet(boolean r2) {
        /*
            r1 = this;
            int r2 = com.google.protobuf.SmallSortedMap.$r8$clinit
            com.google.protobuf.SmallSortedMap$1 r2 = new com.google.protobuf.SmallSortedMap$1
            r0 = 0
            r2.<init>(r0)
            r1.<init>(r2)
            r1.makeImmutable()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.FieldSet.<init>(boolean):void");
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

    private FieldSet(SmallSortedMap smallSortedMap) {
        this.fields = smallSortedMap;
        makeImmutable();
    }
}
