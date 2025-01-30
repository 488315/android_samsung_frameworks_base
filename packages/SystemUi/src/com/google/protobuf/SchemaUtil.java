package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.logging.Logger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
            int size2 = ((ByteString) list.get(i2)).size();
            computeTagSize += CodedOutputStream.computeUInt32SizeNoTag(size2) + size2;
        }
        return computeTagSize;
    }

    public static int computeSizeEnumList(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeEnumListNoTag(list);
    }

    public static int computeSizeEnumListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange(i2);
                i += CodedOutputStream.computeInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeFixed32List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream.computeFixed32Size(i) * size;
    }

    public static int computeSizeFixed32ListNoTag(List list) {
        return list.size() * 4;
    }

    public static int computeSizeFixed64List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return CodedOutputStream.computeFixed64Size(i) * size;
    }

    public static int computeSizeFixed64ListNoTag(List list) {
        return list.size() * 8;
    }

    public static int computeSizeInt32List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeInt32ListNoTag(list);
    }

    public static int computeSizeInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange(i2);
                i += CodedOutputStream.computeInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeInt64List(List list, int i) {
        if (list.size() == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * list.size()) + computeSizeInt64ListNoTag(list);
    }

    public static int computeSizeInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange(i2);
                i += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeMessage(int i, Schema schema, Object obj) {
        if (obj instanceof LazyFieldLite) {
            return CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj) + CodedOutputStream.computeTagSize(i);
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i);
        int serializedSize = ((AbstractMessageLite) ((MessageLite) obj)).getSerializedSize(schema);
        return CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize + computeTagSize;
    }

    public static int computeSizeMessageList(int i, List list, Schema schema) {
        int computeUInt32SizeNoTag;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            Object obj = list.get(i2);
            if (obj instanceof LazyFieldLite) {
                computeUInt32SizeNoTag = CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj);
            } else {
                int serializedSize = ((AbstractMessageLite) ((MessageLite) obj)).getSerializedSize(schema);
                computeUInt32SizeNoTag = serializedSize + CodedOutputStream.computeUInt32SizeNoTag(serializedSize);
            }
            computeTagSize += computeUInt32SizeNoTag;
        }
        return computeTagSize;
    }

    public static int computeSizeSInt32List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeSInt32ListNoTag(list);
    }

    public static int computeSizeSInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange(i2);
                int i3 = intArrayList.array[i2];
                i += CodedOutputStream.computeUInt32SizeNoTag((i3 >> 31) ^ (i3 << 1));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                int intValue = ((Integer) list.get(i2)).intValue();
                i += CodedOutputStream.computeUInt32SizeNoTag((intValue >> 31) ^ (intValue << 1));
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeSInt64List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeSInt64ListNoTag(list);
    }

    public static int computeSizeSInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange(i2);
                long j = longArrayList.array[i2];
                i += CodedOutputStream.computeUInt64SizeNoTag((j >> 63) ^ (j << 1));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                long longValue = ((Long) list.get(i2)).longValue();
                i += CodedOutputStream.computeUInt64SizeNoTag((longValue >> 63) ^ (longValue << 1));
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeStringList(int i, List list) {
        int computeStringSizeNoTag;
        int computeStringSizeNoTag2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int computeTagSize = CodedOutputStream.computeTagSize(i) * size;
        if (list instanceof LazyStringList) {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i2 < size) {
                Object raw = lazyStringList.getRaw(i2);
                if (raw instanceof ByteString) {
                    int size2 = ((ByteString) raw).size();
                    computeStringSizeNoTag2 = CodedOutputStream.computeUInt32SizeNoTag(size2) + size2;
                } else {
                    computeStringSizeNoTag2 = CodedOutputStream.computeStringSizeNoTag((String) raw);
                }
                computeTagSize += computeStringSizeNoTag2;
                i2++;
            }
        } else {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof ByteString) {
                    int size3 = ((ByteString) obj).size();
                    computeStringSizeNoTag = CodedOutputStream.computeUInt32SizeNoTag(size3) + size3;
                } else {
                    computeStringSizeNoTag = CodedOutputStream.computeStringSizeNoTag((String) obj);
                }
                computeTagSize += computeStringSizeNoTag;
                i2++;
            }
        }
        return computeTagSize;
    }

    public static int computeSizeUInt32List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeUInt32ListNoTag(list);
    }

    public static int computeSizeUInt32ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            i = 0;
            while (i2 < size) {
                intArrayList.ensureIndexInRange(i2);
                i += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return i;
    }

    public static int computeSizeUInt64List(List list, int i) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) * size) + computeSizeUInt64ListNoTag(list);
    }

    public static int computeSizeUInt64ListNoTag(List list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            i = 0;
            while (i2 < size) {
                longArrayList.ensureIndexInRange(i2);
                i += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.array[i2]);
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return i;
    }

    public static Object filterUnknownEnumList(Object obj, int i, List list, Internal.EnumLiteMap enumLiteMap, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (enumLiteMap == null) {
            return obj2;
        }
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            int intValue = ((Integer) list.get(i3)).intValue();
            if (enumLiteMap.findValueByNumber(intValue) != null) {
                if (i3 != i2) {
                    list.set(i2, Integer.valueOf(intValue));
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
        if (cls == null) {
            return null;
        }
        try {
            return (UnknownFieldSchema) cls.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z));
        } catch (Throwable unused2) {
            return null;
        }
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
            if (i >= smallSortedMap.getNumArrayEntries()) {
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
        return obj == obj2 || (obj != null && obj.equals(obj2));
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
            ((Boolean) list.get(i4)).booleanValue();
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
            ((Double) list.get(i4)).doubleValue();
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
            ((Integer) list.get(i4)).intValue();
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
            ((Long) list.get(i4)).longValue();
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
            ((Float) list.get(i4)).floatValue();
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
            codedOutputStreamWriter.writeGroup(i, schema, list.get(i2));
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
            codedOutputStreamWriter.writeMessage(i, schema, list.get(i2));
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
            ((Integer) list.get(i4)).intValue();
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
            ((Long) list.get(i4)).longValue();
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
                int intValue = ((Integer) list.get(i3)).intValue();
                if (enumVerifier.isInRange(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    obj2 = storeUnknownEnum(obj, i, intValue, obj2, unknownFieldSchema);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = ((Integer) it.next()).intValue();
                if (!enumVerifier.isInRange(intValue2)) {
                    obj2 = storeUnknownEnum(obj, i, intValue2, obj2, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return obj2;
    }
}
