package androidx.profileinstaller;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ProfileTranscoder {
    public static final byte[] MAGIC_PROF = {112, 114, 111, 0};
    public static final byte[] MAGIC_PROFM = {112, 114, 109, 0};

    private ProfileTranscoder() {
    }

    public static byte[] createCompressibleBody(DexProfileData[] dexProfileDataArr, byte[] bArr) {
        int i = 0;
        int i2 = 0;
        for (DexProfileData dexProfileData : dexProfileDataArr) {
            i2 += ((((dexProfileData.numMethodIds * 2) + 7) & (-8)) / 8) + (dexProfileData.classSetSize * 2) + generateDexKey(dexProfileData.apkName, dexProfileData.dexName, bArr).getBytes(StandardCharsets.UTF_8).length + 16 + dexProfileData.hotMethodRegionSize;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
        if (Arrays.equals(bArr, ProfileVersion.V009_O_MR1)) {
            int length = dexProfileDataArr.length;
            while (i < length) {
                DexProfileData dexProfileData2 = dexProfileDataArr[i];
                writeLineHeader(byteArrayOutputStream, dexProfileData2, generateDexKey(dexProfileData2.apkName, dexProfileData2.dexName, bArr));
                writeLineData(byteArrayOutputStream, dexProfileData2);
                i++;
            }
        } else {
            for (DexProfileData dexProfileData3 : dexProfileDataArr) {
                writeLineHeader(byteArrayOutputStream, dexProfileData3, generateDexKey(dexProfileData3.apkName, dexProfileData3.dexName, bArr));
            }
            int length2 = dexProfileDataArr.length;
            while (i < length2) {
                writeLineData(byteArrayOutputStream, dexProfileDataArr[i]);
                i++;
            }
        }
        if (byteArrayOutputStream.size() == i2) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new IllegalStateException("The bytes saved do not match expectation. actual=" + byteArrayOutputStream.size() + " expected=" + i2);
    }

    public static String generateDexKey(String str, String str2, byte[] bArr) {
        byte[] bArr2 = ProfileVersion.V001_N;
        boolean equals = Arrays.equals(bArr, bArr2);
        byte[] bArr3 = ProfileVersion.V005_O;
        String str3 = (equals || Arrays.equals(bArr, bArr3)) ? ":" : "!";
        if (str.length() <= 0) {
            return "!".equals(str3) ? str2.replace(":", "!") : ":".equals(str3) ? str2.replace("!", ":") : str2;
        }
        if (str2.equals("classes.dex")) {
            return str;
        }
        if (str2.contains("!") || str2.contains(":")) {
            if ("!".equals(str3)) {
                return str2.replace(":", "!");
            }
            if (":".equals(str3)) {
                return str2.replace("!", ":");
            }
        } else if (!str2.endsWith(".apk")) {
            return TransitionKt$$ExternalSyntheticOutline0.m(PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str), (Arrays.equals(bArr, bArr2) || Arrays.equals(bArr, bArr3)) ? ":" : "!", str2);
        }
        return str2;
    }

    public static int[] readClasses(InputStream inputStream, int i) {
        int[] iArr = new int[i];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            i2 += (int) Encoding.readUInt(inputStream, 2);
            iArr[i3] = i2;
        }
        return iArr;
    }

    public static DexProfileData[] readMeta(InputStream inputStream, byte[] bArr, byte[] bArr2, DexProfileData[] dexProfileDataArr) {
        byte[] bArr3 = ProfileVersion.METADATA_V001_N;
        if (!Arrays.equals(bArr, bArr3)) {
            if (!Arrays.equals(bArr, ProfileVersion.METADATA_V002)) {
                throw new IllegalStateException("Unsupported meta version");
            }
            int readUInt = (int) Encoding.readUInt(inputStream, 2);
            byte[] readCompressed = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
            if (inputStream.read() > 0) {
                throw new IllegalStateException("Content found after the end of file");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readCompressed);
            try {
                DexProfileData[] readMetadataV002Body = readMetadataV002Body(byteArrayInputStream, bArr2, readUInt, dexProfileDataArr);
                byteArrayInputStream.close();
                return readMetadataV002Body;
            } catch (Throwable th) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (Arrays.equals(ProfileVersion.V015_S, bArr2)) {
            throw new IllegalStateException("Requires new Baseline Profile Metadata. Please rebuild the APK with Android Gradle Plugin 7.2 Canary 7 or higher");
        }
        if (!Arrays.equals(bArr, bArr3)) {
            throw new IllegalStateException("Unsupported meta version");
        }
        int readUInt2 = (int) Encoding.readUInt(inputStream, 1);
        byte[] readCompressed2 = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
        if (inputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(readCompressed2);
        try {
            DexProfileData[] readMetadataForNBody = readMetadataForNBody(byteArrayInputStream2, readUInt2, dexProfileDataArr);
            byteArrayInputStream2.close();
            return readMetadataForNBody;
        } catch (Throwable th3) {
            try {
                byteArrayInputStream2.close();
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
            }
            throw th3;
        }
    }

    public static DexProfileData[] readMetadataForNBody(InputStream inputStream, int i, DexProfileData[] dexProfileDataArr) {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        if (i != dexProfileDataArr.length) {
            throw new IllegalStateException("Mismatched number of dex files found in metadata");
        }
        String[] strArr = new String[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int readUInt = (int) Encoding.readUInt(inputStream, 2);
            iArr[i2] = (int) Encoding.readUInt(inputStream, 2);
            strArr[i2] = new String(Encoding.read(inputStream, readUInt), StandardCharsets.UTF_8);
        }
        for (int i3 = 0; i3 < i; i3++) {
            DexProfileData dexProfileData = dexProfileDataArr[i3];
            if (!dexProfileData.dexName.equals(strArr[i3])) {
                throw new IllegalStateException("Order of dexfiles in metadata did not match baseline");
            }
            int i4 = iArr[i3];
            dexProfileData.classSetSize = i4;
            dexProfileData.classes = readClasses(inputStream, i4);
        }
        return dexProfileDataArr;
    }

    public static DexProfileData[] readMetadataV002Body(InputStream inputStream, byte[] bArr, int i, DexProfileData[] dexProfileDataArr) {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        if (i != dexProfileDataArr.length) {
            throw new IllegalStateException("Mismatched number of dex files found in metadata");
        }
        for (int i2 = 0; i2 < i; i2++) {
            Encoding.readUInt(inputStream, 2);
            String str = new String(Encoding.read(inputStream, (int) Encoding.readUInt(inputStream, 2)), StandardCharsets.UTF_8);
            long readUInt = Encoding.readUInt(inputStream, 4);
            int readUInt2 = (int) Encoding.readUInt(inputStream, 2);
            DexProfileData dexProfileData = null;
            if (dexProfileDataArr.length > 0) {
                int indexOf = str.indexOf("!");
                if (indexOf < 0) {
                    indexOf = str.indexOf(":");
                }
                String substring = indexOf > 0 ? str.substring(indexOf + 1) : str;
                int i3 = 0;
                while (true) {
                    if (i3 >= dexProfileDataArr.length) {
                        break;
                    }
                    if (dexProfileDataArr[i3].dexName.equals(substring)) {
                        dexProfileData = dexProfileDataArr[i3];
                        break;
                    }
                    i3++;
                }
            }
            if (dexProfileData == null) {
                throw new IllegalStateException("Missing profile key: ".concat(str));
            }
            dexProfileData.mTypeIdCount = readUInt;
            int[] readClasses = readClasses(inputStream, readUInt2);
            if (Arrays.equals(bArr, ProfileVersion.V001_N)) {
                dexProfileData.classSetSize = readUInt2;
                dexProfileData.classes = readClasses;
            }
        }
        return dexProfileDataArr;
    }

    public static DexProfileData[] readProfile(InputStream inputStream, byte[] bArr, String str) {
        if (!Arrays.equals(bArr, ProfileVersion.V010_P)) {
            throw new IllegalStateException("Unsupported version");
        }
        int readUInt = (int) Encoding.readUInt(inputStream, 1);
        byte[] readCompressed = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
        if (inputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readCompressed);
        try {
            DexProfileData[] readUncompressedBody = readUncompressedBody(byteArrayInputStream, str, readUInt);
            byteArrayInputStream.close();
            return readUncompressedBody;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static DexProfileData[] readUncompressedBody(InputStream inputStream, String str, int i) {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        DexProfileData[] dexProfileDataArr = new DexProfileData[i];
        for (int i2 = 0; i2 < i; i2++) {
            int readUInt = (int) Encoding.readUInt(inputStream, 2);
            int readUInt2 = (int) Encoding.readUInt(inputStream, 2);
            dexProfileDataArr[i2] = new DexProfileData(str, new String(Encoding.read(inputStream, readUInt), StandardCharsets.UTF_8), Encoding.readUInt(inputStream, 4), 0L, readUInt2, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4), new int[readUInt2], new TreeMap());
        }
        for (int i3 = 0; i3 < i; i3++) {
            DexProfileData dexProfileData = dexProfileDataArr[i3];
            int available = inputStream.available() - dexProfileData.hotMethodRegionSize;
            int i4 = 0;
            while (inputStream.available() > available) {
                i4 += (int) Encoding.readUInt(inputStream, 2);
                dexProfileData.methods.put(Integer.valueOf(i4), 1);
                for (int readUInt3 = (int) Encoding.readUInt(inputStream, 2); readUInt3 > 0; readUInt3--) {
                    Encoding.readUInt(inputStream, 2);
                    int readUInt4 = (int) Encoding.readUInt(inputStream, 1);
                    if (readUInt4 != 6 && readUInt4 != 7) {
                        while (readUInt4 > 0) {
                            Encoding.readUInt(inputStream, 1);
                            for (int readUInt5 = (int) Encoding.readUInt(inputStream, 1); readUInt5 > 0; readUInt5--) {
                                Encoding.readUInt(inputStream, 2);
                            }
                            readUInt4--;
                        }
                    }
                }
            }
            if (inputStream.available() != available) {
                throw new IllegalStateException("Read too much data during profile line parse");
            }
            dexProfileData.classes = readClasses(inputStream, dexProfileData.classSetSize);
            int i5 = dexProfileData.numMethodIds;
            BitSet valueOf = BitSet.valueOf(Encoding.read(inputStream, (((i5 * 2) + 7) & (-8)) / 8));
            for (int i6 = 0; i6 < i5; i6++) {
                int i7 = valueOf.get(i6) ? 2 : 0;
                if (valueOf.get(i6 + i5)) {
                    i7 |= 4;
                }
                if (i7 != 0) {
                    Integer num = (Integer) dexProfileData.methods.get(Integer.valueOf(i6));
                    if (num == null) {
                        num = 0;
                    }
                    dexProfileData.methods.put(Integer.valueOf(i6), Integer.valueOf(i7 | num.intValue()));
                }
            }
        }
        return dexProfileDataArr;
    }

    /* JADX WARN: Finally extract failed */
    public static boolean transcodeAndWriteBody(OutputStream outputStream, byte[] bArr, DexProfileData[] dexProfileDataArr) {
        ArrayList arrayList;
        int length;
        byte[] bArr2 = ProfileVersion.V015_S;
        int i = 0;
        if (!Arrays.equals(bArr, bArr2)) {
            byte[] bArr3 = ProfileVersion.V010_P;
            if (Arrays.equals(bArr, bArr3)) {
                byte[] createCompressibleBody = createCompressibleBody(dexProfileDataArr, bArr3);
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                Encoding.writeUInt(outputStream, createCompressibleBody.length, 4);
                byte[] compress = Encoding.compress(createCompressibleBody);
                Encoding.writeUInt(outputStream, compress.length, 4);
                outputStream.write(compress);
                return true;
            }
            byte[] bArr4 = ProfileVersion.V005_O;
            if (Arrays.equals(bArr, bArr4)) {
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                for (DexProfileData dexProfileData : dexProfileDataArr) {
                    int size = dexProfileData.methods.size() * 4;
                    String generateDexKey = generateDexKey(dexProfileData.apkName, dexProfileData.dexName, bArr4);
                    Charset charset = StandardCharsets.UTF_8;
                    Encoding.writeUInt16(outputStream, generateDexKey.getBytes(charset).length);
                    Encoding.writeUInt16(outputStream, dexProfileData.classes.length);
                    Encoding.writeUInt(outputStream, size, 4);
                    Encoding.writeUInt(outputStream, dexProfileData.dexChecksum, 4);
                    outputStream.write(generateDexKey.getBytes(charset));
                    Iterator it = dexProfileData.methods.keySet().iterator();
                    while (it.hasNext()) {
                        Encoding.writeUInt16(outputStream, ((Integer) it.next()).intValue());
                        Encoding.writeUInt16(outputStream, 0);
                    }
                    for (int i2 : dexProfileData.classes) {
                        Encoding.writeUInt16(outputStream, i2);
                    }
                }
                return true;
            }
            byte[] bArr5 = ProfileVersion.V009_O_MR1;
            if (Arrays.equals(bArr, bArr5)) {
                byte[] createCompressibleBody2 = createCompressibleBody(dexProfileDataArr, bArr5);
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                Encoding.writeUInt(outputStream, createCompressibleBody2.length, 4);
                byte[] compress2 = Encoding.compress(createCompressibleBody2);
                Encoding.writeUInt(outputStream, compress2.length, 4);
                outputStream.write(compress2);
                return true;
            }
            byte[] bArr6 = ProfileVersion.V001_N;
            if (!Arrays.equals(bArr, bArr6)) {
                return false;
            }
            Encoding.writeUInt16(outputStream, dexProfileDataArr.length);
            for (DexProfileData dexProfileData2 : dexProfileDataArr) {
                String generateDexKey2 = generateDexKey(dexProfileData2.apkName, dexProfileData2.dexName, bArr6);
                Charset charset2 = StandardCharsets.UTF_8;
                Encoding.writeUInt16(outputStream, generateDexKey2.getBytes(charset2).length);
                Encoding.writeUInt16(outputStream, dexProfileData2.methods.size());
                Encoding.writeUInt16(outputStream, dexProfileData2.classes.length);
                Encoding.writeUInt(outputStream, dexProfileData2.dexChecksum, 4);
                outputStream.write(generateDexKey2.getBytes(charset2));
                Iterator it2 = dexProfileData2.methods.keySet().iterator();
                while (it2.hasNext()) {
                    Encoding.writeUInt16(outputStream, ((Integer) it2.next()).intValue());
                }
                for (int i3 : dexProfileData2.classes) {
                    Encoding.writeUInt16(outputStream, i3);
                }
            }
            return true;
        }
        ArrayList arrayList2 = new ArrayList(3);
        ArrayList arrayList3 = new ArrayList(3);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Encoding.writeUInt16(byteArrayOutputStream, dexProfileDataArr.length);
            int i4 = 2;
            int i5 = 2;
            for (DexProfileData dexProfileData3 : dexProfileDataArr) {
                Encoding.writeUInt(byteArrayOutputStream, dexProfileData3.dexChecksum, 4);
                Encoding.writeUInt(byteArrayOutputStream, dexProfileData3.mTypeIdCount, 4);
                Encoding.writeUInt(byteArrayOutputStream, dexProfileData3.numMethodIds, 4);
                String generateDexKey3 = generateDexKey(dexProfileData3.apkName, dexProfileData3.dexName, bArr2);
                Charset charset3 = StandardCharsets.UTF_8;
                int length2 = generateDexKey3.getBytes(charset3).length;
                Encoding.writeUInt16(byteArrayOutputStream, length2);
                i5 = i5 + 14 + length2;
                byteArrayOutputStream.write(generateDexKey3.getBytes(charset3));
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (i5 != byteArray.length) {
                throw new IllegalStateException("Expected size " + i5 + ", does not match actual size " + byteArray.length);
            }
            WritableFileSection writableFileSection = new WritableFileSection(FileSectionType.DEX_FILES, i5, byteArray, false);
            byteArrayOutputStream.close();
            arrayList2.add(writableFileSection);
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            int i6 = 0;
            for (int i7 = 0; i7 < dexProfileDataArr.length; i7++) {
                try {
                    DexProfileData dexProfileData4 = dexProfileDataArr[i7];
                    Encoding.writeUInt16(byteArrayOutputStream2, i7);
                    Encoding.writeUInt16(byteArrayOutputStream2, dexProfileData4.classSetSize);
                    i6 = i6 + 4 + (dexProfileData4.classSetSize * i4);
                    int[] iArr = dexProfileData4.classes;
                    int length3 = iArr.length;
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 < length3) {
                        int i10 = iArr[i8];
                        Encoding.writeUInt16(byteArrayOutputStream2, i10 - i9);
                        i8++;
                        i4 = i4;
                        i9 = i10;
                    }
                } catch (Throwable th) {
                }
            }
            byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
            if (i6 != byteArray2.length) {
                throw new IllegalStateException("Expected size " + i6 + ", does not match actual size " + byteArray2.length);
            }
            WritableFileSection writableFileSection2 = new WritableFileSection(FileSectionType.CLASSES, i6, byteArray2, true);
            byteArrayOutputStream2.close();
            arrayList2.add(writableFileSection2);
            byteArrayOutputStream2 = new ByteArrayOutputStream();
            int i11 = 0;
            int i12 = 0;
            while (i11 < dexProfileDataArr.length) {
                try {
                    DexProfileData dexProfileData5 = dexProfileDataArr[i11];
                    Iterator it3 = dexProfileData5.methods.entrySet().iterator();
                    int i13 = i;
                    while (it3.hasNext()) {
                        i13 |= ((Integer) ((Map.Entry) it3.next()).getValue()).intValue();
                    }
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    try {
                        writeMethodBitmapForS(byteArrayOutputStream3, i13, dexProfileData5);
                        byte[] byteArray3 = byteArrayOutputStream3.toByteArray();
                        byteArrayOutputStream3.close();
                        byteArrayOutputStream3 = new ByteArrayOutputStream();
                        try {
                            writeMethodsWithInlineCaches(byteArrayOutputStream3, dexProfileData5);
                            byte[] byteArray4 = byteArrayOutputStream3.toByteArray();
                            byteArrayOutputStream3.close();
                            Encoding.writeUInt16(byteArrayOutputStream2, i11);
                            int length4 = byteArray3.length + 2 + byteArray4.length;
                            int i14 = i12 + 6;
                            ArrayList arrayList4 = arrayList3;
                            Encoding.writeUInt(byteArrayOutputStream2, length4, 4);
                            Encoding.writeUInt16(byteArrayOutputStream2, i13);
                            byteArrayOutputStream2.write(byteArray3);
                            byteArrayOutputStream2.write(byteArray4);
                            i12 = i14 + length4;
                            i11++;
                            arrayList3 = arrayList4;
                            i = 0;
                        } finally {
                        }
                    } finally {
                    }
                } finally {
                    try {
                        byteArrayOutputStream2.close();
                        throw th;
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
            }
            ArrayList arrayList5 = arrayList3;
            byte[] byteArray5 = byteArrayOutputStream2.toByteArray();
            if (i12 != byteArray5.length) {
                throw new IllegalStateException("Expected size " + i12 + ", does not match actual size " + byteArray5.length);
            }
            WritableFileSection writableFileSection3 = new WritableFileSection(FileSectionType.METHODS, i12, byteArray5, true);
            byteArrayOutputStream2.close();
            arrayList2.add(writableFileSection3);
            long j = 4;
            long size2 = j + j + 4 + (arrayList2.size() * 16);
            Encoding.writeUInt(outputStream, arrayList2.size(), 4);
            int i15 = 0;
            while (i15 < arrayList2.size()) {
                WritableFileSection writableFileSection4 = (WritableFileSection) arrayList2.get(i15);
                Encoding.writeUInt(outputStream, writableFileSection4.mType.getValue(), 4);
                Encoding.writeUInt(outputStream, size2, 4);
                boolean z = writableFileSection4.mNeedsCompression;
                byte[] bArr7 = writableFileSection4.mContents;
                if (z) {
                    long length5 = bArr7.length;
                    byte[] compress3 = Encoding.compress(bArr7);
                    arrayList = arrayList5;
                    arrayList.add(compress3);
                    Encoding.writeUInt(outputStream, compress3.length, 4);
                    Encoding.writeUInt(outputStream, length5, 4);
                    length = compress3.length;
                } else {
                    arrayList = arrayList5;
                    arrayList.add(bArr7);
                    Encoding.writeUInt(outputStream, bArr7.length, 4);
                    Encoding.writeUInt(outputStream, 0L, 4);
                    length = bArr7.length;
                }
                size2 += length;
                i15++;
                arrayList5 = arrayList;
            }
            ArrayList arrayList6 = arrayList5;
            for (int i16 = 0; i16 < arrayList6.size(); i16++) {
                outputStream.write((byte[]) arrayList6.get(i16));
            }
            return true;
        } catch (Throwable th3) {
            try {
                byteArrayOutputStream.close();
                throw th3;
            } catch (Throwable th4) {
                th3.addSuppressed(th4);
                throw th3;
            }
        }
    }

    public static void writeLineData(OutputStream outputStream, DexProfileData dexProfileData) {
        writeMethodsWithInlineCaches(outputStream, dexProfileData);
        int[] iArr = dexProfileData.classes;
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = iArr[i];
            Encoding.writeUInt16(outputStream, i3 - i2);
            i++;
            i2 = i3;
        }
        int i4 = dexProfileData.numMethodIds;
        byte[] bArr = new byte[(((i4 * 2) + 7) & (-8)) / 8];
        for (Map.Entry entry : dexProfileData.methods.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            if ((intValue2 & 2) != 0) {
                int i5 = intValue / 8;
                bArr[i5] = (byte) (bArr[i5] | (1 << (intValue % 8)));
            }
            if ((intValue2 & 4) != 0) {
                int i6 = intValue + i4;
                int i7 = i6 / 8;
                bArr[i7] = (byte) ((1 << (i6 % 8)) | bArr[i7]);
            }
        }
        outputStream.write(bArr);
    }

    public static void writeLineHeader(OutputStream outputStream, DexProfileData dexProfileData, String str) {
        Charset charset = StandardCharsets.UTF_8;
        Encoding.writeUInt16(outputStream, str.getBytes(charset).length);
        Encoding.writeUInt16(outputStream, dexProfileData.classSetSize);
        Encoding.writeUInt(outputStream, dexProfileData.hotMethodRegionSize, 4);
        Encoding.writeUInt(outputStream, dexProfileData.dexChecksum, 4);
        Encoding.writeUInt(outputStream, dexProfileData.numMethodIds, 4);
        outputStream.write(str.getBytes(charset));
    }

    public static void writeMethodBitmapForS(OutputStream outputStream, int i, DexProfileData dexProfileData) {
        int bitCount = Integer.bitCount(i & (-2));
        int i2 = dexProfileData.numMethodIds;
        byte[] bArr = new byte[(((bitCount * i2) + 7) & (-8)) / 8];
        for (Map.Entry entry : dexProfileData.methods.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            int intValue2 = ((Integer) entry.getValue()).intValue();
            int i3 = 0;
            for (int i4 = 1; i4 <= 4; i4 <<= 1) {
                if (i4 != 1 && (i4 & i) != 0) {
                    if ((i4 & intValue2) == i4) {
                        int i5 = (i3 * i2) + intValue;
                        int i6 = i5 / 8;
                        bArr[i6] = (byte) ((1 << (i5 % 8)) | bArr[i6]);
                    }
                    i3++;
                }
            }
        }
        outputStream.write(bArr);
    }

    public static void writeMethodsWithInlineCaches(OutputStream outputStream, DexProfileData dexProfileData) {
        int i = 0;
        for (Map.Entry entry : dexProfileData.methods.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            if ((((Integer) entry.getValue()).intValue() & 1) != 0) {
                Encoding.writeUInt16(outputStream, intValue - i);
                Encoding.writeUInt16(outputStream, 0);
                i = intValue;
            }
        }
    }
}
