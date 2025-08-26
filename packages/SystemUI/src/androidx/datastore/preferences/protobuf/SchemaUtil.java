package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public final class SchemaUtil {
    public static final Class GENERATED_MESSAGE_CLASS;
    public static final UnknownFieldSchema UNKNOWN_FIELD_SET_FULL_SCHEMA;
    public static final UnknownFieldSetLiteSchema UNKNOWN_FIELD_SET_LITE_SCHEMA;

    static {
        Class<?> cls;
        Class<?> cls2;
        Protobuf protobuf = Protobuf.INSTANCE;
        UnknownFieldSchema unknownFieldSchema = null;
        try {
            cls = Class.forName("androidx.datastore.preferences.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        GENERATED_MESSAGE_CLASS = cls;
        try {
            Protobuf protobuf2 = Protobuf.INSTANCE;
            try {
                cls2 = Class.forName("androidx.datastore.preferences.protobuf.UnknownFieldSetSchema");
            } catch (Throwable unused2) {
                cls2 = null;
            }
            if (cls2 != null) {
                Class[] clsArr = new Class[0];
                unknownFieldSchema = (UnknownFieldSchema) cls2.getConstructor(null).newInstance(null);
            }
        } catch (Throwable unused3) {
        }
        UNKNOWN_FIELD_SET_FULL_SCHEMA = unknownFieldSchema;
        UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();
    }

    private SchemaUtil() {
    }

    public static int computeSizeEnumListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int iComputeUInt64SizeNoTag = 0;
            while (i < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return iComputeUInt64SizeNoTag;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int iComputeUInt64SizeNoTag2 = 0;
        while (i < size) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return iComputeUInt64SizeNoTag2;
    }

    public static int computeSizeFixed32List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) + 4) * size;
    }

    public static int computeSizeFixed64List(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return (CodedOutputStream.computeTagSize(i) + 8) * size;
    }

    public static int computeSizeInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int iComputeUInt64SizeNoTag = 0;
            while (i < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return iComputeUInt64SizeNoTag;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int iComputeUInt64SizeNoTag2 = 0;
        while (i < size) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return iComputeUInt64SizeNoTag2;
    }

    public static int computeSizeInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int iComputeUInt64SizeNoTag = 0;
            while (i < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return iComputeUInt64SizeNoTag;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int iComputeUInt64SizeNoTag2 = 0;
        while (i < size) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return iComputeUInt64SizeNoTag2;
    }

    public static int computeSizeSInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int iComputeSInt32SizeNoTag = 0;
            while (i < size) {
                iComputeSInt32SizeNoTag += CodedOutputStream.computeSInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return iComputeSInt32SizeNoTag;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int iComputeSInt32SizeNoTag2 = 0;
        while (i < size) {
            iComputeSInt32SizeNoTag2 += CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return iComputeSInt32SizeNoTag2;
    }

    public static int computeSizeSInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int iComputeSInt64SizeNoTag = 0;
            while (i < size) {
                iComputeSInt64SizeNoTag += CodedOutputStream.computeSInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return iComputeSInt64SizeNoTag;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int iComputeSInt64SizeNoTag2 = 0;
        while (i < size) {
            iComputeSInt64SizeNoTag2 += CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return iComputeSInt64SizeNoTag2;
    }

    public static int computeSizeUInt32ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof IntArrayList)) {
            int iComputeUInt32SizeNoTag = 0;
            while (i < size) {
                iComputeUInt32SizeNoTag += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return iComputeUInt32SizeNoTag;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int iComputeUInt32SizeNoTag2 = 0;
        while (i < size) {
            iComputeUInt32SizeNoTag2 += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return iComputeUInt32SizeNoTag2;
    }

    public static int computeSizeUInt64ListNoTag(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof LongArrayList)) {
            int iComputeUInt64SizeNoTag = 0;
            while (i < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return iComputeUInt64SizeNoTag;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int iComputeUInt64SizeNoTag2 = 0;
        while (i < size) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return iComputeUInt64SizeNoTag2;
    }

    public static void mergeExtensions(ExtensionSchema extensionSchema, Object obj, Object obj2) {
        FieldSet extensions = extensionSchema.getExtensions(obj2);
        if (extensions.fields.isEmpty()) {
            return;
        }
        FieldSet mutableExtensions = extensionSchema.getMutableExtensions(obj);
        mutableExtensions.getClass();
        SmallSortedMap smallSortedMap = extensions.fields;
        int size = smallSortedMap.entryList.size();
        for (int i = 0; i < size; i++) {
            mutableExtensions.mergeFromField(smallSortedMap.getArrayEntryAt(i));
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

    public static void writeBoolList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof BooleanArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
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
            return;
        }
        BooleanArrayList booleanArrayList = (BooleanArrayList) list;
        if (!z) {
            while (i2 < booleanArrayList.size) {
                booleanArrayList.ensureIndexInRange(i2);
                codedOutputStream.writeBool(i, booleanArrayList.array[i2]);
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < booleanArrayList.size; i6++) {
            booleanArrayList.ensureIndexInRange(i6);
            boolean z3 = booleanArrayList.array[i6];
            Logger logger2 = CodedOutputStream.logger;
            i5++;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < booleanArrayList.size) {
            booleanArrayList.ensureIndexInRange(i2);
            codedOutputStream.write(booleanArrayList.array[i2] ? (byte) 1 : (byte) 0);
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
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof DoubleArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    double dDoubleValue = ((Double) list.get(i2)).doubleValue();
                    codedOutputStream.getClass();
                    codedOutputStream.writeFixed64(i, Double.doubleToRawLongBits(dDoubleValue));
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
            return;
        }
        DoubleArrayList doubleArrayList = (DoubleArrayList) list;
        if (!z) {
            while (i2 < doubleArrayList.size) {
                doubleArrayList.ensureIndexInRange$1(i2);
                double d = doubleArrayList.array[i2];
                codedOutputStream.getClass();
                codedOutputStream.writeFixed64(i, Double.doubleToRawLongBits(d));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < doubleArrayList.size; i6++) {
            doubleArrayList.ensureIndexInRange$1(i6);
            double d2 = doubleArrayList.array[i6];
            Logger logger2 = CodedOutputStream.logger;
            i5 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < doubleArrayList.size) {
            doubleArrayList.ensureIndexInRange$1(i2);
            codedOutputStream.writeFixed64NoTag(Double.doubleToRawLongBits(doubleArrayList.array[i2]));
            i2++;
        }
    }

    public static void writeEnumList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    codedOutputStream.writeInt32(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeUInt64SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i3)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag);
            while (i2 < list.size()) {
                codedOutputStream.writeInt32NoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                codedOutputStream.writeInt32(i, intArrayList.getInt(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeUInt64SizeNoTag2 = 0;
        for (int i4 = 0; i4 < intArrayList.size; i4++) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag2);
        while (i2 < intArrayList.size) {
            codedOutputStream.writeInt32NoTag(intArrayList.getInt(i2));
            i2++;
        }
    }

    public static void writeFixed32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
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
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                codedOutputStream.writeFixed32(i, intArrayList.getInt(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < intArrayList.size; i6++) {
            intArrayList.getInt(i6);
            Logger logger2 = CodedOutputStream.logger;
            i5 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < intArrayList.size) {
            codedOutputStream.writeFixed32NoTag(intArrayList.getInt(i2));
            i2++;
        }
    }

    public static void writeFixed64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof LongArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
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
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        if (!z) {
            while (i2 < longArrayList.size) {
                codedOutputStream.writeFixed64(i, longArrayList.getLong(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < longArrayList.size; i6++) {
            longArrayList.getLong(i6);
            Logger logger2 = CodedOutputStream.logger;
            i5 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < longArrayList.size) {
            codedOutputStream.writeFixed64NoTag(longArrayList.getLong(i2));
            i2++;
        }
    }

    public static void writeFloatList(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof FloatArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    float fFloatValue = ((Float) list.get(i2)).floatValue();
                    codedOutputStream.getClass();
                    codedOutputStream.writeFixed32(i, Float.floatToRawIntBits(fFloatValue));
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
            return;
        }
        FloatArrayList floatArrayList = (FloatArrayList) list;
        if (!z) {
            while (i2 < floatArrayList.size) {
                floatArrayList.ensureIndexInRange$2(i2);
                float f = floatArrayList.array[i2];
                codedOutputStream.getClass();
                codedOutputStream.writeFixed32(i, Float.floatToRawIntBits(f));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < floatArrayList.size; i6++) {
            floatArrayList.ensureIndexInRange$2(i6);
            float f2 = floatArrayList.array[i6];
            Logger logger2 = CodedOutputStream.logger;
            i5 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < floatArrayList.size) {
            floatArrayList.ensureIndexInRange$2(i2);
            codedOutputStream.writeFixed32NoTag(Float.floatToRawIntBits(floatArrayList.array[i2]));
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
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    codedOutputStream.writeInt32(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeUInt64SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i3)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag);
            while (i2 < list.size()) {
                codedOutputStream.writeInt32NoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                codedOutputStream.writeInt32(i, intArrayList.getInt(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeUInt64SizeNoTag2 = 0;
        for (int i4 = 0; i4 < intArrayList.size; i4++) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag2);
        while (i2 < intArrayList.size) {
            codedOutputStream.writeInt32NoTag(intArrayList.getInt(i2));
            i2++;
        }
    }

    public static void writeInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof LongArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    codedOutputStream.writeUInt64(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeUInt64SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i3)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag);
            while (i2 < list.size()) {
                codedOutputStream.writeUInt64NoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        if (!z) {
            while (i2 < longArrayList.size) {
                codedOutputStream.writeUInt64(i, longArrayList.getLong(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeUInt64SizeNoTag2 = 0;
        for (int i4 = 0; i4 < longArrayList.size; i4++) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag2);
        while (i2 < longArrayList.size) {
            codedOutputStream.writeUInt64NoTag(longArrayList.getLong(i2));
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
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
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
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                codedOutputStream.writeFixed32(i, intArrayList.getInt(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < intArrayList.size; i6++) {
            intArrayList.getInt(i6);
            Logger logger2 = CodedOutputStream.logger;
            i5 += 4;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < intArrayList.size) {
            codedOutputStream.writeFixed32NoTag(intArrayList.getInt(i2));
            i2++;
        }
    }

    public static void writeSFixed64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof LongArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
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
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        if (!z) {
            while (i2 < longArrayList.size) {
                codedOutputStream.writeFixed64(i, longArrayList.getLong(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i5 = 0;
        for (int i6 = 0; i6 < longArrayList.size; i6++) {
            longArrayList.getLong(i6);
            Logger logger2 = CodedOutputStream.logger;
            i5 += 8;
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < longArrayList.size) {
            codedOutputStream.writeFixed64NoTag(longArrayList.getLong(i2));
            i2++;
        }
    }

    public static void writeSInt32List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    int iIntValue = ((Integer) list.get(i2)).intValue();
                    codedOutputStream.writeUInt32(i, (iIntValue >> 31) ^ (iIntValue << 1));
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeSInt32SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeSInt32SizeNoTag += CodedOutputStream.computeSInt32SizeNoTag(((Integer) list.get(i3)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeSInt32SizeNoTag);
            while (i2 < list.size()) {
                int iIntValue2 = ((Integer) list.get(i2)).intValue();
                codedOutputStream.writeUInt32NoTag((iIntValue2 >> 31) ^ (iIntValue2 << 1));
                i2++;
            }
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                int i4 = intArrayList.getInt(i2);
                codedOutputStream.writeUInt32(i, (i4 >> 31) ^ (i4 << 1));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeSInt32SizeNoTag2 = 0;
        for (int i5 = 0; i5 < intArrayList.size; i5++) {
            iComputeSInt32SizeNoTag2 += CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i5));
        }
        codedOutputStream.writeUInt32NoTag(iComputeSInt32SizeNoTag2);
        while (i2 < intArrayList.size) {
            int i6 = intArrayList.getInt(i2);
            codedOutputStream.writeUInt32NoTag((i6 >> 31) ^ (i6 << 1));
            i2++;
        }
    }

    public static void writeSInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof LongArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    long jLongValue = ((Long) list.get(i2)).longValue();
                    codedOutputStream.writeUInt64(i, (jLongValue >> 63) ^ (jLongValue << 1));
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeSInt64SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeSInt64SizeNoTag += CodedOutputStream.computeSInt64SizeNoTag(((Long) list.get(i3)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeSInt64SizeNoTag);
            while (i2 < list.size()) {
                long jLongValue2 = ((Long) list.get(i2)).longValue();
                codedOutputStream.writeUInt64NoTag((jLongValue2 >> 63) ^ (jLongValue2 << 1));
                i2++;
            }
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        if (!z) {
            while (i2 < longArrayList.size) {
                long j = longArrayList.getLong(i2);
                codedOutputStream.writeUInt64(i, (j >> 63) ^ (j << 1));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeSInt64SizeNoTag2 = 0;
        for (int i4 = 0; i4 < longArrayList.size; i4++) {
            iComputeSInt64SizeNoTag2 += CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeSInt64SizeNoTag2);
        while (i2 < longArrayList.size) {
            long j2 = longArrayList.getLong(i2);
            codedOutputStream.writeUInt64NoTag((j2 >> 63) ^ (j2 << 1));
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
            Object raw = lazyStringList.getRaw();
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
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof IntArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    codedOutputStream.writeUInt32(i, ((Integer) list.get(i2)).intValue());
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeUInt32SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeUInt32SizeNoTag += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i3)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeUInt32SizeNoTag);
            while (i2 < list.size()) {
                codedOutputStream.writeUInt32NoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                codedOutputStream.writeUInt32(i, intArrayList.getInt(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeUInt32SizeNoTag2 = 0;
        for (int i4 = 0; i4 < intArrayList.size; i4++) {
            iComputeUInt32SizeNoTag2 += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeUInt32SizeNoTag2);
        while (i2 < intArrayList.size) {
            codedOutputStream.writeUInt32NoTag(intArrayList.getInt(i2));
            i2++;
        }
    }

    public static void writeUInt64List(int i, List list, CodedOutputStreamWriter codedOutputStreamWriter, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        codedOutputStreamWriter.getClass();
        boolean z2 = list instanceof LongArrayList;
        CodedOutputStream codedOutputStream = codedOutputStreamWriter.output;
        int i2 = 0;
        if (!z2) {
            if (!z) {
                while (i2 < list.size()) {
                    codedOutputStream.writeUInt64(i, ((Long) list.get(i2)).longValue());
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int iComputeUInt64SizeNoTag = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i3)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag);
            while (i2 < list.size()) {
                codedOutputStream.writeUInt64NoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
            return;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        if (!z) {
            while (i2 < longArrayList.size) {
                codedOutputStream.writeUInt64(i, longArrayList.getLong(i2));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int iComputeUInt64SizeNoTag2 = 0;
        for (int i4 = 0; i4 < longArrayList.size; i4++) {
            iComputeUInt64SizeNoTag2 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i4));
        }
        codedOutputStream.writeUInt32NoTag(iComputeUInt64SizeNoTag2);
        while (i2 < longArrayList.size) {
            codedOutputStream.writeUInt64NoTag(longArrayList.getLong(i2));
            i2++;
        }
    }

    public static Object filterUnknownEnumList(Object obj, int i, Internal.ProtobufList protobufList, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        return obj2;
    }
}
