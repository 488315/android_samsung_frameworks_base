package androidx.profileinstaller;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

/* loaded from: classes.dex */
public class ProfileTranscoder {
    public static final byte[] MAGIC_PROF = {112, 114, 111, 0};
    public static final byte[] MAGIC_PROFM = {112, 114, 109, 0};

    private ProfileTranscoder() {
    }

    public static byte[] createCompressibleBody(DexProfileData[] dexProfileDataArr, byte[] bArr) throws IOException {
        int i = 0;
        int length = 0;
        for (DexProfileData dexProfileData : dexProfileDataArr) {
            length += ((((dexProfileData.numMethodIds * 2) + 7) & (-8)) / 8) + (dexProfileData.classSetSize * 2) + generateDexKey(dexProfileData.apkName, dexProfileData.dexName, bArr).getBytes(StandardCharsets.UTF_8).length + 16 + dexProfileData.hotMethodRegionSize;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(length);
        if (Arrays.equals(bArr, ProfileVersion.V009_O_MR1)) {
            int length2 = dexProfileDataArr.length;
            while (i < length2) {
                DexProfileData dexProfileData2 = dexProfileDataArr[i];
                writeLineHeader(byteArrayOutputStream, dexProfileData2, generateDexKey(dexProfileData2.apkName, dexProfileData2.dexName, bArr));
                writeLineData(byteArrayOutputStream, dexProfileData2);
                i++;
            }
        } else {
            for (DexProfileData dexProfileData3 : dexProfileDataArr) {
                writeLineHeader(byteArrayOutputStream, dexProfileData3, generateDexKey(dexProfileData3.apkName, dexProfileData3.dexName, bArr));
            }
            int length3 = dexProfileDataArr.length;
            while (i < length3) {
                writeLineData(byteArrayOutputStream, dexProfileDataArr[i]);
                i++;
            }
        }
        if (byteArrayOutputStream.size() == length) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new IllegalStateException("The bytes saved do not match expectation. actual=" + byteArrayOutputStream.size() + " expected=" + length);
    }

    public static String generateDexKey(String str, String str2, byte[] bArr) {
        byte[] bArr2 = ProfileVersion.V001_N;
        boolean zEquals = Arrays.equals(bArr, bArr2);
        byte[] bArr3 = ProfileVersion.V005_O;
        String str3 = (zEquals || Arrays.equals(bArr, bArr3)) ? ":" : "!";
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
        int uInt = 0;
        for (int i2 = 0; i2 < i; i2++) {
            uInt += (int) Encoding.readUInt(inputStream, 2);
            iArr[i2] = uInt;
        }
        return iArr;
    }

    public static DexProfileData[] readMeta(InputStream inputStream, byte[] bArr, byte[] bArr2, DexProfileData[] dexProfileDataArr) throws IOException {
        byte[] bArr3 = ProfileVersion.METADATA_V001_N;
        if (!Arrays.equals(bArr, bArr3)) {
            if (!Arrays.equals(bArr, ProfileVersion.METADATA_V002)) {
                throw new IllegalStateException("Unsupported meta version");
            }
            int uInt = (int) Encoding.readUInt(inputStream, 2);
            byte[] compressed = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
            if (inputStream.read() > 0) {
                throw new IllegalStateException("Content found after the end of file");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed);
            try {
                DexProfileData[] metadataV002Body = readMetadataV002Body(byteArrayInputStream, bArr2, uInt, dexProfileDataArr);
                byteArrayInputStream.close();
                return metadataV002Body;
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
        int uInt2 = (int) Encoding.readUInt(inputStream, 1);
        byte[] compressed2 = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
        if (inputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(compressed2);
        try {
            DexProfileData[] metadataForNBody = readMetadataForNBody(byteArrayInputStream2, uInt2, dexProfileDataArr);
            byteArrayInputStream2.close();
            return metadataForNBody;
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
            int uInt = (int) Encoding.readUInt(inputStream, 2);
            iArr[i2] = (int) Encoding.readUInt(inputStream, 2);
            strArr[i2] = new String(Encoding.read(inputStream, uInt), StandardCharsets.UTF_8);
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

    public static DexProfileData[] readMetadataV002Body(InputStream inputStream, byte[] bArr, int i, DexProfileData[] dexProfileDataArr) throws IOException {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        if (i != dexProfileDataArr.length) {
            throw new IllegalStateException("Mismatched number of dex files found in metadata");
        }
        for (int i2 = 0; i2 < i; i2++) {
            Encoding.readUInt(inputStream, 2);
            String str = new String(Encoding.read(inputStream, (int) Encoding.readUInt(inputStream, 2)), StandardCharsets.UTF_8);
            long uInt = Encoding.readUInt(inputStream, 4);
            int uInt2 = (int) Encoding.readUInt(inputStream, 2);
            DexProfileData dexProfileData = null;
            if (dexProfileDataArr.length > 0) {
                int iIndexOf = str.indexOf("!");
                if (iIndexOf < 0) {
                    iIndexOf = str.indexOf(":");
                }
                String strSubstring = iIndexOf > 0 ? str.substring(iIndexOf + 1) : str;
                int i3 = 0;
                while (true) {
                    if (i3 >= dexProfileDataArr.length) {
                        break;
                    }
                    if (dexProfileDataArr[i3].dexName.equals(strSubstring)) {
                        dexProfileData = dexProfileDataArr[i3];
                        break;
                    }
                    i3++;
                }
            }
            if (dexProfileData == null) {
                throw new IllegalStateException("Missing profile key: ".concat(str));
            }
            dexProfileData.mTypeIdCount = uInt;
            int[] classes = readClasses(inputStream, uInt2);
            if (Arrays.equals(bArr, ProfileVersion.V001_N)) {
                dexProfileData.classSetSize = uInt2;
                dexProfileData.classes = classes;
            }
        }
        return dexProfileDataArr;
    }

    public static DexProfileData[] readProfile(InputStream inputStream, byte[] bArr, String str) throws IOException {
        if (!Arrays.equals(bArr, ProfileVersion.V010_P)) {
            throw new IllegalStateException("Unsupported version");
        }
        int uInt = (int) Encoding.readUInt(inputStream, 1);
        byte[] compressed = Encoding.readCompressed(inputStream, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4));
        if (inputStream.read() > 0) {
            throw new IllegalStateException("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed);
        try {
            DexProfileData[] uncompressedBody = readUncompressedBody(byteArrayInputStream, str, uInt);
            byteArrayInputStream.close();
            return uncompressedBody;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static DexProfileData[] readUncompressedBody(InputStream inputStream, String str, int i) throws IOException {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        DexProfileData[] dexProfileDataArr = new DexProfileData[i];
        for (int i2 = 0; i2 < i; i2++) {
            int uInt = (int) Encoding.readUInt(inputStream, 2);
            int uInt2 = (int) Encoding.readUInt(inputStream, 2);
            dexProfileDataArr[i2] = new DexProfileData(str, new String(Encoding.read(inputStream, uInt), StandardCharsets.UTF_8), Encoding.readUInt(inputStream, 4), 0L, uInt2, (int) Encoding.readUInt(inputStream, 4), (int) Encoding.readUInt(inputStream, 4), new int[uInt2], new TreeMap());
        }
        for (int i3 = 0; i3 < i; i3++) {
            DexProfileData dexProfileData = dexProfileDataArr[i3];
            int iAvailable = inputStream.available() - dexProfileData.hotMethodRegionSize;
            int uInt3 = 0;
            while (inputStream.available() > iAvailable) {
                uInt3 += (int) Encoding.readUInt(inputStream, 2);
                dexProfileData.methods.put(Integer.valueOf(uInt3), 1);
                for (int uInt4 = (int) Encoding.readUInt(inputStream, 2); uInt4 > 0; uInt4--) {
                    Encoding.readUInt(inputStream, 2);
                    int uInt5 = (int) Encoding.readUInt(inputStream, 1);
                    if (uInt5 != 6 && uInt5 != 7) {
                        while (uInt5 > 0) {
                            Encoding.readUInt(inputStream, 1);
                            for (int uInt6 = (int) Encoding.readUInt(inputStream, 1); uInt6 > 0; uInt6--) {
                                Encoding.readUInt(inputStream, 2);
                            }
                            uInt5--;
                        }
                    }
                }
            }
            if (inputStream.available() != iAvailable) {
                throw new IllegalStateException("Read too much data during profile line parse");
            }
            dexProfileData.classes = readClasses(inputStream, dexProfileData.classSetSize);
            int i4 = dexProfileData.numMethodIds;
            BitSet bitSetValueOf = BitSet.valueOf(Encoding.read(inputStream, (((i4 * 2) + 7) & (-8)) / 8));
            for (int i5 = 0; i5 < i4; i5++) {
                int i6 = bitSetValueOf.get(i5) ? 2 : 0;
                if (bitSetValueOf.get(i5 + i4)) {
                    i6 |= 4;
                }
                if (i6 != 0) {
                    Integer num = (Integer) dexProfileData.methods.get(Integer.valueOf(i5));
                    if (num == null) {
                        num = 0;
                    }
                    dexProfileData.methods.put(Integer.valueOf(i5), Integer.valueOf(i6 | num.intValue()));
                }
            }
        }
        return dexProfileDataArr;
    }

    /* JADX WARN: Finally extract failed */
    public static boolean transcodeAndWriteBody(OutputStream outputStream, byte[] bArr, DexProfileData[] dexProfileDataArr) throws IOException {
        ArrayList arrayList;
        int length;
        byte[] bArr2 = ProfileVersion.V015_S;
        int i = 0;
        if (!Arrays.equals(bArr, bArr2)) {
            byte[] bArr3 = ProfileVersion.V010_P;
            if (Arrays.equals(bArr, bArr3)) {
                byte[] bArrCreateCompressibleBody = createCompressibleBody(dexProfileDataArr, bArr3);
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                Encoding.writeUInt(outputStream, bArrCreateCompressibleBody.length, 4);
                byte[] bArrCompress = Encoding.compress(bArrCreateCompressibleBody);
                Encoding.writeUInt(outputStream, bArrCompress.length, 4);
                outputStream.write(bArrCompress);
                return true;
            }
            byte[] bArr4 = ProfileVersion.V005_O;
            if (Arrays.equals(bArr, bArr4)) {
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                for (DexProfileData dexProfileData : dexProfileDataArr) {
                    int size = dexProfileData.methods.size() * 4;
                    String strGenerateDexKey = generateDexKey(dexProfileData.apkName, dexProfileData.dexName, bArr4);
                    Charset charset = StandardCharsets.UTF_8;
                    Encoding.writeUInt16(outputStream, strGenerateDexKey.getBytes(charset).length);
                    Encoding.writeUInt16(outputStream, dexProfileData.classes.length);
                    Encoding.writeUInt(outputStream, size, 4);
                    Encoding.writeUInt(outputStream, dexProfileData.dexChecksum, 4);
                    outputStream.write(strGenerateDexKey.getBytes(charset));
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
                byte[] bArrCreateCompressibleBody2 = createCompressibleBody(dexProfileDataArr, bArr5);
                Encoding.writeUInt(outputStream, dexProfileDataArr.length, 1);
                Encoding.writeUInt(outputStream, bArrCreateCompressibleBody2.length, 4);
                byte[] bArrCompress2 = Encoding.compress(bArrCreateCompressibleBody2);
                Encoding.writeUInt(outputStream, bArrCompress2.length, 4);
                outputStream.write(bArrCompress2);
                return true;
            }
            byte[] bArr6 = ProfileVersion.V001_N;
            if (!Arrays.equals(bArr, bArr6)) {
                return false;
            }
            Encoding.writeUInt16(outputStream, dexProfileDataArr.length);
            for (DexProfileData dexProfileData2 : dexProfileDataArr) {
                String strGenerateDexKey2 = generateDexKey(dexProfileData2.apkName, dexProfileData2.dexName, bArr6);
                Charset charset2 = StandardCharsets.UTF_8;
                Encoding.writeUInt16(outputStream, strGenerateDexKey2.getBytes(charset2).length);
                Encoding.writeUInt16(outputStream, dexProfileData2.methods.size());
                Encoding.writeUInt16(outputStream, dexProfileData2.classes.length);
                Encoding.writeUInt(outputStream, dexProfileData2.dexChecksum, 4);
                outputStream.write(strGenerateDexKey2.getBytes(charset2));
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
                String strGenerateDexKey3 = generateDexKey(dexProfileData3.apkName, dexProfileData3.dexName, bArr2);
                Charset charset3 = StandardCharsets.UTF_8;
                int length2 = strGenerateDexKey3.getBytes(charset3).length;
                Encoding.writeUInt16(byteArrayOutputStream, length2);
                i5 = i5 + 14 + length2;
                byteArrayOutputStream.write(strGenerateDexKey3.getBytes(charset3));
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
                    int iIntValue = i;
                    while (it3.hasNext()) {
                        iIntValue |= ((Integer) ((Map.Entry) it3.next()).getValue()).intValue();
                    }
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    try {
                        writeMethodBitmapForS(byteArrayOutputStream3, iIntValue, dexProfileData5);
                        byte[] byteArray3 = byteArrayOutputStream3.toByteArray();
                        byteArrayOutputStream3.close();
                        byteArrayOutputStream3 = new ByteArrayOutputStream();
                        try {
                            writeMethodsWithInlineCaches(byteArrayOutputStream3, dexProfileData5);
                            byte[] byteArray4 = byteArrayOutputStream3.toByteArray();
                            byteArrayOutputStream3.close();
                            Encoding.writeUInt16(byteArrayOutputStream2, i11);
                            int length4 = byteArray3.length + 2 + byteArray4.length;
                            int i13 = i12 + 6;
                            ArrayList arrayList4 = arrayList3;
                            Encoding.writeUInt(byteArrayOutputStream2, length4, 4);
                            Encoding.writeUInt16(byteArrayOutputStream2, iIntValue);
                            byteArrayOutputStream2.write(byteArray3);
                            byteArrayOutputStream2.write(byteArray4);
                            i12 = i13 + length4;
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
            int i14 = 0;
            while (i14 < arrayList2.size()) {
                WritableFileSection writableFileSection4 = (WritableFileSection) arrayList2.get(i14);
                Encoding.writeUInt(outputStream, writableFileSection4.mType.getValue(), 4);
                Encoding.writeUInt(outputStream, size2, 4);
                boolean z = writableFileSection4.mNeedsCompression;
                byte[] bArr7 = writableFileSection4.mContents;
                if (z) {
                    long length5 = bArr7.length;
                    byte[] bArrCompress3 = Encoding.compress(bArr7);
                    arrayList = arrayList5;
                    arrayList.add(bArrCompress3);
                    Encoding.writeUInt(outputStream, bArrCompress3.length, 4);
                    Encoding.writeUInt(outputStream, length5, 4);
                    length = bArrCompress3.length;
                } else {
                    arrayList = arrayList5;
                    arrayList.add(bArr7);
                    Encoding.writeUInt(outputStream, bArr7.length, 4);
                    Encoding.writeUInt(outputStream, 0L, 4);
                    length = bArr7.length;
                }
                size2 += length;
                i14++;
                arrayList5 = arrayList;
            }
            ArrayList arrayList6 = arrayList5;
            for (int i15 = 0; i15 < arrayList6.size(); i15++) {
                outputStream.write((byte[]) arrayList6.get(i15));
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

    public static void writeLineData(OutputStream outputStream, DexProfileData dexProfileData) throws IOException {
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
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            if ((iIntValue2 & 2) != 0) {
                int i5 = iIntValue / 8;
                bArr[i5] = (byte) (bArr[i5] | (1 << (iIntValue % 8)));
            }
            if ((iIntValue2 & 4) != 0) {
                int i6 = iIntValue + i4;
                int i7 = i6 / 8;
                bArr[i7] = (byte) ((1 << (i6 % 8)) | bArr[i7]);
            }
        }
        outputStream.write(bArr);
    }

    public static void writeLineHeader(OutputStream outputStream, DexProfileData dexProfileData, String str) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        Encoding.writeUInt16(outputStream, str.getBytes(charset).length);
        Encoding.writeUInt16(outputStream, dexProfileData.classSetSize);
        Encoding.writeUInt(outputStream, dexProfileData.hotMethodRegionSize, 4);
        Encoding.writeUInt(outputStream, dexProfileData.dexChecksum, 4);
        Encoding.writeUInt(outputStream, dexProfileData.numMethodIds, 4);
        outputStream.write(str.getBytes(charset));
    }

    public static void writeMethodBitmapForS(OutputStream outputStream, int i, DexProfileData dexProfileData) throws IOException {
        int iBitCount = Integer.bitCount(i & (-2));
        int i2 = dexProfileData.numMethodIds;
        byte[] bArr = new byte[(((iBitCount * i2) + 7) & (-8)) / 8];
        for (Map.Entry entry : dexProfileData.methods.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            int i3 = 0;
            for (int i4 = 1; i4 <= 4; i4 <<= 1) {
                if (i4 != 1 && (i4 & i) != 0) {
                    if ((i4 & iIntValue2) == i4) {
                        int i5 = (i3 * i2) + iIntValue;
                        int i6 = i5 / 8;
                        bArr[i6] = (byte) ((1 << (i5 % 8)) | bArr[i6]);
                    }
                    i3++;
                }
            }
        }
        outputStream.write(bArr);
    }

    public static void writeMethodsWithInlineCaches(OutputStream outputStream, DexProfileData dexProfileData) throws IOException {
        int i = 0;
        for (Map.Entry entry : dexProfileData.methods.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            if ((((Integer) entry.getValue()).intValue() & 1) != 0) {
                Encoding.writeUInt16(outputStream, iIntValue - i);
                Encoding.writeUInt16(outputStream, 0);
                i = iIntValue;
            }
        }
    }
}
