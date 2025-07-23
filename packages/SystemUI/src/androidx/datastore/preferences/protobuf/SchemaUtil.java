package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            i3 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return i3;
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
            int i2 = 0;
            while (i < size) {
                i2 += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            i3 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return i3;
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
            i3 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return i3;
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
                i2 += CodedOutputStream.computeSInt32SizeNoTag(((Integer) list.get(i)).intValue());
                i++;
            }
            return i2;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        int i3 = 0;
        while (i < size) {
            i3 += CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return i3;
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
                i2 += CodedOutputStream.computeSInt64SizeNoTag(((Long) list.get(i)).longValue());
                i++;
            }
            return i2;
        }
        LongArrayList longArrayList = (LongArrayList) list;
        int i3 = 0;
        while (i < size) {
            i3 += CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return i3;
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
            i3 += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i));
            i++;
        }
        return i3;
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
            i3 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i));
            i++;
        }
        return i3;
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
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i4)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
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
        int i5 = 0;
        for (int i6 = 0; i6 < intArrayList.size; i6++) {
            i5 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
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
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt64SizeNoTag(((Integer) list.get(i4)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
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
        int i5 = 0;
        for (int i6 = 0; i6 < intArrayList.size; i6++) {
            i5 += CodedOutputStream.computeUInt64SizeNoTag(intArrayList.getInt(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
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
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i4)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
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
        int i5 = 0;
        for (int i6 = 0; i6 < longArrayList.size; i6++) {
            i5 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
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
                    int intValue = ((Integer) list.get(i2)).intValue();
                    codedOutputStream.writeUInt32(i, (intValue >> 31) ^ (intValue << 1));
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSInt32SizeNoTag(((Integer) list.get(i4)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                int intValue2 = ((Integer) list.get(i2)).intValue();
                codedOutputStream.writeUInt32NoTag((intValue2 >> 31) ^ (intValue2 << 1));
                i2++;
            }
            return;
        }
        IntArrayList intArrayList = (IntArrayList) list;
        if (!z) {
            while (i2 < intArrayList.size) {
                int i5 = intArrayList.getInt(i2);
                codedOutputStream.writeUInt32(i, (i5 >> 31) ^ (i5 << 1));
                i2++;
            }
            return;
        }
        codedOutputStream.writeTag(i, 2);
        int i6 = 0;
        for (int i7 = 0; i7 < intArrayList.size; i7++) {
            i6 += CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i7));
        }
        codedOutputStream.writeUInt32NoTag(i6);
        while (i2 < intArrayList.size) {
            int i8 = intArrayList.getInt(i2);
            codedOutputStream.writeUInt32NoTag((i8 >> 31) ^ (i8 << 1));
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
                    long longValue = ((Long) list.get(i2)).longValue();
                    codedOutputStream.writeUInt64(i, (longValue >> 63) ^ (longValue << 1));
                    i2++;
                }
                return;
            }
            codedOutputStream.writeTag(i, 2);
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeSInt64SizeNoTag(((Long) list.get(i4)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
            while (i2 < list.size()) {
                long longValue2 = ((Long) list.get(i2)).longValue();
                codedOutputStream.writeUInt64NoTag((longValue2 >> 63) ^ (longValue2 << 1));
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
        int i5 = 0;
        for (int i6 = 0; i6 < longArrayList.size; i6++) {
            i5 += CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
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
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i4)).intValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
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
        int i5 = 0;
        for (int i6 = 0; i6 < intArrayList.size; i6++) {
            i5 += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
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
            int i3 = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                i3 += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i4)).longValue());
            }
            codedOutputStream.writeUInt32NoTag(i3);
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
        int i5 = 0;
        for (int i6 = 0; i6 < longArrayList.size; i6++) {
            i5 += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i6));
        }
        codedOutputStream.writeUInt32NoTag(i5);
        while (i2 < longArrayList.size) {
            codedOutputStream.writeUInt64NoTag(longArrayList.getLong(i2));
            i2++;
        }
    }

    public static Object filterUnknownEnumList(Object obj, int i, Internal.ProtobufList protobufList, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        return obj2;
    }
}
