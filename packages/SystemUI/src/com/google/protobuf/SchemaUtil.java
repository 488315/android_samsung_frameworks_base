package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.logging.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SchemaUtil {
    public static final Class GENERATED_MESSAGE_CLASS;
    public static final UnknownFieldSchema PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    public static final UnknownFieldSchema PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    public static final UnknownFieldSetLiteSchema UNKNOWN_FIELD_SET_LITE_SCHEMA;

    static {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessageV3");
        } catch (Throwable unused) {
            cls = null;
        }
        GENERATED_MESSAGE_CLASS = cls;
        PROTO2_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(false);
        PROTO3_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(true);
        UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();
    }

    private SchemaUtil() {
    }

    public static int computeSizeByteStringList(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        for (int i2 = 0; i2 < list.size(); i2++) {
            computeTagSize += CodedOutputStream.computeBytesSizeNoTag((ByteString) list.get(i2));
        }
        return computeTagSize;
    }

    public static int computeSizeEnumList(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeEnumListNoTag(list);
    }

    public static int computeSizeEnumListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            intArrayList.ensureIndexInRange$3(i);
            i3 += CodedOutputStream.computeInt32SizeNoTag(intArrayList.array[i]);
            i++;
        }
        return i3;
    }

    public static int computeSizeFixed32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream.computeFixed32Size(i) * size;
    }

    public static int computeSizeFixed32ListNoTag(List list) {
        return list.size() * 4;
    }

    public static int computeSizeFixed64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream.computeFixed64Size(i) * size;
    }

    public static int computeSizeFixed64ListNoTag(List list) {
        return list.size() * 8;
    }

    public static int computeSizeInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeInt32ListNoTag(list);
    }

    public static int computeSizeInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            intArrayList.ensureIndexInRange$3(i);
            i3 += CodedOutputStream.computeInt32SizeNoTag(intArrayList.array[i]);
            i++;
        }
        return i3;
    }

    public static int computeSizeInt64List(int i, List list) {
        if (list.size() == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * list.size()) + computeSizeInt64ListNoTag(list);
    }

    public static int computeSizeInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return i2;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i3 = 0;
        while (i < size) {
            longArrayList.ensureIndexInRange$4(i);
            i3 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.array[i]);
            i++;
        }
        return i3;
    }

    public static int computeSizeMessage(int i, Object obj, Schema schema) {
        if (obj instanceof LazyFieldLite) {
            return CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj) + CodedOutputStream.computeTagSize(i);
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i);
        int serializedSize = ((AbstractMessageLite) ((MessageLite) obj)).getSerializedSize(schema);
        return CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize + computeTagSize;
    }

    public static int computeSizeMessageList(int i, List list, Schema schema) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof LazyFieldLite) {
                computeTagSize = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj) + computeTagSize;
            } else {
                int serializedSize = ((AbstractMessageLite) ((MessageLite) obj)).getSerializedSize(schema);
                computeTagSize = CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize + computeTagSize;
            }
        }
        return computeTagSize;
    }

    public static int computeSizeSInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeSInt32ListNoTag(list);
    }

    public static int computeSizeSInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int i2 = 0;
            while (i < size) {
                int intValue = ((Integer) list.get(i)).intValue();
                i2 += CodedOutputStream.computeUInt32SizeNoTag((intValue >> 31) ^ (intValue << 1));
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            intArrayList.ensureIndexInRange$3(i);
            int i4 = intArrayList.array[i];
            i3 += CodedOutputStream.computeUInt32SizeNoTag((i4 >> 31) ^ (i4 << 1));
            i++;
        }
        return i3;
    }

    public static int computeSizeSInt64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeSInt64ListNoTag(list);
    }

    public static int computeSizeSInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int i2 = 0;
            while (i < size) {
                long longValue = ((Long) list.get(i)).longValue();
                i2 += CodedOutputStream.computeUInt64SizeNoTag((longValue >> 63) ^ (longValue << 1));
                i++;
            }
            return i2;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i3 = 0;
        while (i < size) {
            longArrayList.ensureIndexInRange$4(i);
            long j = longArrayList.array[i];
            i3 += CodedOutputStream.computeUInt64SizeNoTag((j >> 63) ^ (j << 1));
            i++;
        }
        return i3;
    }

    public static int computeSizeStringList(int i, List list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        if (!(list instanceof LazyStringList)) {
            while (i2 < size) {
                Object obj = list.get(i2);
                computeTagSize = (obj instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) obj) : CodedOutputStream.computeStringSizeNoTag((String) obj)) + computeTagSize;
                i2++;
            }
            return computeTagSize;
        }
        LazyStringList lazyStringList = (LazyStringList) list;
        while (i2 < size) {
            Object raw = lazyStringList.getRaw(i2);
            computeTagSize = (raw instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) raw) : CodedOutputStream.computeStringSizeNoTag((String) raw)) + computeTagSize;
            i2++;
        }
        return computeTagSize;
    }

    public static int computeSizeUInt32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeUInt32ListNoTag(list);
    }

    public static int computeSizeUInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            intArrayList.ensureIndexInRange$3(i);
            i3 += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.array[i]);
            i++;
        }
        return i3;
    }

    public static int computeSizeUInt64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeUInt64ListNoTag(list);
    }

    public static int computeSizeUInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return i2;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i3 = 0;
        while (i < size) {
            longArrayList.ensureIndexInRange$4(i);
            i3 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.array[i]);
            i++;
        }
        return i3;
    }

    public static Object filterUnknownEnumList(Object obj, int i, List list, Internal.EnumLiteMap enumLiteMap, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (enumLiteMap == null) {
            return obj2;
        }
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Integer num = (Integer) list.get(i3);
            int intValue = num.intValue();
            if (enumLiteMap.findValueByNumber(intValue) != null) {
                if (i3 != i2) {
                    list.set(i2, num);
                }
                i2++;
            } else {
                obj2 = storeUnknownEnum(obj, i, intValue, obj2, unknownFieldSchema);
            }
        }
        if (i2 != size) {
            list.subList(i2, size).clear();
        }
        return obj2;
    }

    public static UnknownFieldSchema getUnknownFieldSetSchema(boolean z) {
        Class<?> cls;
        try {
            cls = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            cls = null;
        }
        if (cls != null) {
            try {
                return (UnknownFieldSchema) cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
            } catch (Throwable unused2) {
            }
        }
        return null;
    }

    public static void mergeExtensions(ExtensionSchema extensionSchema, Object obj, Object obj2) {
        SmallSortedMap smallSortedMap;
        FieldSet extensions = extensionSchema.getExtensions(obj2);
        if (extensions.fields.isEmpty()) {
            return;
        }
        FieldSet mutableExtensions = extensionSchema.getMutableExtensions(obj);
        mutableExtensions.getClass();
        int i = 0;
        while (true) {
            smallSortedMap = extensions.fields;
            if (i >= smallSortedMap.entryList.size()) {
                break;
            }
            mutableExtensions.mergeFromField(smallSortedMap.getArrayEntryAt(i));
            i++;
        }
        Iterator it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            mutableExtensions.mergeFromField((Map.Entry) it.next());
        }
    }

    public static boolean safeEquals(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static Object storeUnknownEnum(Object obj, int i, int i2, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (obj2 == null) {
            obj2 = unknownFieldSchema.getBuilderFromMessage(obj);
        }
        unknownFieldSchema.addVarint(i, i2, obj2);
        return obj2;
    }

    public static void writeBoolList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeBool(i, ((Boolean) list.get(i2)).booleanValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Boolean) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3++;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.write(((Boolean) list.get(i2)).booleanValue() ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    public static void writeBytesList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            codedOutputStreamWriter.output.writeBytes(i, (ByteString) list.get(i2));
        }
    }

    public static void writeDoubleList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                double doubleValue = ((Double) list.get(i2)).doubleValue();
                codedOutputStream.getClass();
                codedOutputStream.writeFixed64(i, Double.doubleToRawLongBits(doubleValue));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Double) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed64NoTag(Double.doubleToRawLongBits(((Double) list.get(i2)).doubleValue()));
            i2++;
        }
    }

    public static void writeEnumList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeInt32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i4)).intValue());
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeInt32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeFixed32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeFixed32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeFixed64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeFixed64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeFloatList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                float floatValue = ((Float) list.get(i2)).floatValue();
                codedOutputStream.getClass();
                codedOutputStream.writeFixed32(i, Float.floatToRawIntBits(floatValue));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Float) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed32NoTag(Float.floatToRawIntBits(((Float) list.get(i2)).floatValue()));
            i2++;
        }
    }

    public static void writeGroupList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, Schema schema) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            codedOutputStreamWriter.writeGroup(i, list.get(i2), schema);
        }
    }

    public static void writeInt32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeInt32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i4)).intValue());
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeInt32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeUInt64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i4)).longValue());
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeUInt64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeMessageList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, Schema schema) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        for (int i2 = 0; i2 < list.size(); i2++) {
            codedOutputStreamWriter.writeMessage(i, list.get(i2), schema);
        }
    }

    public static void writeSFixed32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeFixed32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Integer) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeSFixed64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeFixed64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            ((Long) list.get(i4)).getClass();
            Logger logger = CodedOutputStream.logger;
            i3 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeFixed64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static void writeSInt32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                int intValue = ((Integer) list.get(i2)).intValue();
                codedOutputStream.writeUInt32(i, (intValue >> 31) ^ (intValue << 1));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            int intValue2 = ((Integer) list.get(i4)).intValue();
            i3 += CodedOutputStream.computeUInt32SizeNoTag((intValue2 >> 31) ^ (intValue2 << 1));
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            int intValue3 = ((Integer) list.get(i2)).intValue();
            codedOutputStream.writeUInt32NoTag((intValue3 >> 31) ^ (intValue3 << 1));
            i2++;
        }
    }

    public static void writeSInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                long longValue = ((Long) list.get(i2)).longValue();
                codedOutputStream.writeUInt64(i, (longValue >> 63) ^ (longValue << 1));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            long longValue2 = ((Long) list.get(i4)).longValue();
            i3 += CodedOutputStream.computeUInt64SizeNoTag((longValue2 >> 63) ^ (longValue2 << 1));
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            long longValue3 = ((Long) list.get(i2)).longValue();
            codedOutputStream.writeUInt64NoTag((longValue3 >> 63) ^ (longValue3 << 1));
            i2++;
        }
    }

    public static void writeStringList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z = list instanceof LazyStringList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeString(i, (String) list.get(i2));
                i2++;
            }
            return;
        }
        LazyStringList lazyStringList = (LazyStringList) list;
        while (i2 < list.size()) {
            Object raw = lazyStringList.getRaw(i2);
            if (raw instanceof String) {
                codedOutputStream.writeString(i, (String) raw);
            } else {
                codedOutputStream.writeBytes(i, (ByteString) raw);
            }
            i2++;
        }
    }

    public static void writeUInt32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeUInt32(i, ((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i4)).intValue());
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeUInt32NoTag(((Integer) list.get(i2)).intValue());
            i2++;
        }
    }

    public static void writeUInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                codedOutputStream.writeUInt64(i, ((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            i3 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i4)).longValue());
        }
        codedOutputStream.writeUInt32NoTag(i3);
        while (i2 < list.size()) {
            codedOutputStream.writeUInt64NoTag(((Long) list.get(i2)).longValue());
            i2++;
        }
    }

    public static Object filterUnknownEnumList(Object obj, int i, List list, Internal.EnumVerifier enumVerifier, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (enumVerifier == null) {
            return obj2;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                Integer num = (Integer) list.get(i3);
                int intValue = num.intValue();
                if (enumVerifier.isInRange(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, num);
                    }
                    i2++;
                } else {
                    obj2 = storeUnknownEnum(obj, i, intValue, obj2, unknownFieldSchema);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
            return obj2;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            int intValue2 = ((Integer) it.next()).intValue();
            if (!enumVerifier.isInRange(intValue2)) {
                obj2 = storeUnknownEnum(obj, i, intValue2, obj2, unknownFieldSchema);
                it.remove();
            }
        }
        return obj2;
    }
}
