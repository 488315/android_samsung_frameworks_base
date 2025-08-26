package com.android.internal.telephony;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.R;
import com.google.android.mms.pdu.CharacterSets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class GsmAlphabet {
    public static final byte GSM_EXTENDED_ESCAPE = 27;
    private static final String TAG = "GSM";
    public static final int UDH_SEPTET_COST_CONCATENATED_MESSAGE = 6;
    public static final int UDH_SEPTET_COST_LENGTH = 1;
    public static final int UDH_SEPTET_COST_ONE_SHIFT_TABLE = 4;
    public static final int UDH_SEPTET_COST_TWO_SHIFT_TABLES = 7;
    private static final SparseIntArray[] sCharsToGsmTables;
    private static final SparseIntArray[] sCharsToShiftTables;
    private static boolean sDisableCountryEncodingCheck = false;
    private static boolean sEnableIgnoreSpecialChar = false;
    private static int[] sEnabledLockingShiftTables;
    private static int[] sEnabledSingleShiftTables;
    private static int sHighestEnabledSingleShiftCode;
    private static final String[] sLanguageShiftTables;
    private static final String[] sLanguageTables;

    private GsmAlphabet() {
    }

    public static class TextEncodingDetails {
        public int codeUnitCount;
        public int codeUnitSize;
        public int codeUnitsRemaining;
        public int languageShiftTable;
        public int languageTable;
        public int msgCount;

        public String toString() {
            return "TextEncodingDetails { msgCount=" + this.msgCount + ", codeUnitCount=" + this.codeUnitCount + ", codeUnitsRemaining=" + this.codeUnitsRemaining + ", codeUnitSize=" + this.codeUnitSize + ", languageTable=" + this.languageTable + ", languageShiftTable=" + this.languageShiftTable + " }";
        }
    }

    public static int charToGsm(char c) {
        try {
            return charToGsm(c, false);
        } catch (EncodeException unused) {
            return sCharsToGsmTables[0].get(32, 32);
        }
    }

    public static int charToGsm(char c, boolean z) throws EncodeException {
        SparseIntArray[] sparseIntArrayArr = sCharsToGsmTables;
        int i = sparseIntArrayArr[0].get(c, -1);
        if (i != -1) {
            return i;
        }
        if (sCharsToShiftTables[0].get(c, -1) != -1) {
            return 27;
        }
        if (z) {
            throw new EncodeException(c);
        }
        return sparseIntArrayArr[0].get(32, 32);
    }

    public static int charToGsmExtended(char c) {
        int i = sCharsToShiftTables[0].get(c, -1);
        return i == -1 ? sCharsToGsmTables[0].get(32, 32) : i;
    }

    public static char gsmToChar(int i) {
        if (i < 0 || i >= 128) {
            return ' ';
        }
        return sLanguageTables[0].charAt(i);
    }

    public static char gsmExtendedToChar(int i) {
        if (i == 27 || i < 0 || i >= 128) {
            return ' ';
        }
        char cCharAt = sLanguageShiftTables[0].charAt(i);
        return cCharAt == ' ' ? sLanguageTables[0].charAt(i) : cCharAt;
    }

    public static byte[] stringToGsm7BitPackedWithHeader(String str, byte[] bArr) throws EncodeException {
        return stringToGsm7BitPackedWithHeader(str, bArr, 0, 0);
    }

    public static byte[] stringToGsm7BitPackedWithHeader(String str, byte[] bArr, int i, int i2) throws EncodeException {
        if (bArr == null || bArr.length == 0) {
            return stringToGsm7BitPacked(str, i, i2);
        }
        byte[] bArrStringToGsm7BitPacked = stringToGsm7BitPacked(str, (((bArr.length + 1) * 8) + 6) / 7, true, i, i2);
        bArrStringToGsm7BitPacked[1] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArrStringToGsm7BitPacked, 2, bArr.length);
        return bArrStringToGsm7BitPacked;
    }

    public static byte[] stringToGsm7BitPacked(String str) throws EncodeException {
        return stringToGsm7BitPacked(str, 0, true, 0, 0);
    }

    public static byte[] stringToGsm7BitPacked(String str, int i, int i2) throws EncodeException {
        return stringToGsm7BitPacked(str, 0, true, i, i2);
    }

    public static byte[] stringToGsm7BitPacked(String str, int i, boolean z, int i2, int i3) throws EncodeException {
        int length = str.length();
        int iCountGsmSeptetsUsingTables = countGsmSeptetsUsingTables(str, !z, i2, i3);
        if (iCountGsmSeptetsUsingTables == -1) {
            throw new EncodeException("countGsmSeptetsUsingTables(): unencodable char");
        }
        int i4 = iCountGsmSeptetsUsingTables + i;
        if (i4 > 255) {
            throw new EncodeException("Payload cannot exceed 255 septets", 1);
        }
        byte[] bArr = new byte[(((i4 * 7) + 7) / 8) + 1];
        SparseIntArray sparseIntArray = sCharsToGsmTables[i2];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[i3];
        int i5 = i * 7;
        int i6 = 0;
        while (i6 < length && i < i4) {
            char cCharAt = str.charAt(i6);
            int i7 = sparseIntArray.get(cCharAt, -1);
            if (i7 == -1) {
                i7 = sparseIntArray2.get(cCharAt, -1);
                if (i7 != -1) {
                    packSmsChar(bArr, i5, 27);
                    i5 += 7;
                    i++;
                } else {
                    if (z) {
                        throw new EncodeException("stringToGsm7BitPacked(): unencodable char");
                    }
                    i7 = sparseIntArray.get(32, 32);
                }
            }
            packSmsChar(bArr, i5, i7);
            i++;
            i6++;
            i5 += 7;
        }
        bArr[0] = (byte) i4;
        return bArr;
    }

    private static void packSmsChar(byte[] bArr, int i, int i2) {
        int i3 = i / 8;
        int i4 = i % 8;
        int i5 = i3 + 1;
        bArr[i5] = (byte) (bArr[i5] | (i2 << i4));
        if (i4 > 1) {
            bArr[i3 + 2] = (byte) (i2 >> (8 - i4));
        }
    }

    public static String gsm7BitPackedToString(byte[] bArr, int i, int i2) {
        return gsm7BitPackedToString(bArr, i, i2, 0, 0, 0);
    }

    public static String gsm7BitPackedToString(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        int i6 = i4;
        int i7 = i5;
        StringBuilder sb = new StringBuilder(i2);
        if (i6 < 0 || i6 > sLanguageTables.length) {
            Log.w(TAG, "unknown language table " + i6 + ", using default");
            i6 = 0;
        }
        if (i7 < 0 || i7 > sLanguageShiftTables.length) {
            Log.w(TAG, "unknown single shift table " + i7 + ", using default");
            i7 = 0;
        }
        try {
            String[] strArr = sLanguageTables;
            String str = strArr[i6];
            String[] strArr2 = sLanguageShiftTables;
            String str2 = strArr2[i7];
            if (str.isEmpty()) {
                Log.w(TAG, "no language table for code " + i6 + ", using default");
                str = strArr[0];
            }
            if (str2.isEmpty()) {
                Log.w(TAG, "no single shift table for code " + i7 + ", using default");
                str2 = strArr2[0];
            }
            boolean z = false;
            for (int i8 = 0; i8 < i2; i8++) {
                int i9 = (i8 * 7) + i3;
                int i10 = i9 / 8;
                int i11 = i9 % 8;
                int i12 = i10 + i;
                int i13 = (bArr[i12] >> i11) & 127;
                if (i11 > 1) {
                    i13 = (i13 & (127 >> (i11 - 1))) | ((bArr[i12 + 1] << (8 - i11)) & 127);
                }
                if (z) {
                    if (i13 == 27) {
                        sb.append(' ');
                    } else {
                        char cCharAt = str2.charAt(i13);
                        if (cCharAt == ' ') {
                            sb.append(str.charAt(i13));
                        } else {
                            sb.append(cCharAt);
                        }
                    }
                    z = false;
                } else if (i13 == 27) {
                    z = true;
                } else {
                    sb.append(str.charAt(i13));
                }
            }
            return sb.toString();
        } catch (RuntimeException e) {
            Log.e(TAG, "Error GSM 7 bit packed: ", e);
            return null;
        }
    }

    public static String gsm8BitUnpackedToString(byte[] bArr, int i, int i2) {
        return gsm8BitUnpackedToString(bArr, i, i2, "");
    }

    public static String gsm8BitUnpackedToString(byte[] bArr, int i, int i2, String str) {
        Charset charsetForName;
        ByteBuffer byteBufferAllocate;
        boolean z;
        int i3;
        int i4;
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase(CharacterSets.MIMENAME_US_ASCII) || !Charset.isSupported(str)) {
            charsetForName = null;
            byteBufferAllocate = null;
            z = false;
        } else {
            charsetForName = Charset.forName(str);
            byteBufferAllocate = ByteBuffer.allocate(2);
            z = true;
        }
        String str2 = sLanguageTables[0];
        String str3 = sLanguageShiftTables[0];
        StringBuilder sb = new StringBuilder(i2);
        int i5 = i;
        boolean z2 = false;
        while (true) {
            int i6 = i + i2;
            if (i5 >= i6 || (i3 = bArr[i5] & 255) == 255) {
                break;
            }
            if (i3 == 27) {
                if (z2) {
                    sb.append(' ');
                } else {
                    z2 = true;
                    i5++;
                }
            } else if (z2) {
                char cCharAt = i3 < str3.length() ? str3.charAt(i3) : ' ';
                if (cCharAt == ' ') {
                    if (i3 < str2.length()) {
                        sb.append(str2.charAt(i3));
                    } else {
                        sb.append(' ');
                    }
                } else {
                    sb.append(cCharAt);
                }
            } else if (!z || i3 < 128 || (i4 = i5 + 1) >= i6) {
                if (i3 < str2.length()) {
                    sb.append(str2.charAt(i3));
                } else {
                    sb.append(' ');
                }
            } else {
                byteBufferAllocate.clear();
                byteBufferAllocate.put(bArr, i5, 2);
                byteBufferAllocate.flip();
                sb.append(charsetForName.decode(byteBufferAllocate).toString());
                i5 = i4;
            }
            z2 = false;
            i5++;
        }
        return sb.toString();
    }

    public static byte[] stringToGsm8BitPacked(String str) {
        int iCountGsmSeptetsUsingTables = countGsmSeptetsUsingTables(str, true, 0, 0);
        byte[] bArr = new byte[iCountGsmSeptetsUsingTables];
        stringToGsm8BitUnpackedField(str, bArr, 0, iCountGsmSeptetsUsingTables);
        return bArr;
    }

    public static void stringToGsm8BitUnpackedField(String str, byte[] bArr, int i, int i2) {
        int i3 = 0;
        SparseIntArray sparseIntArray = sCharsToGsmTables[0];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[0];
        int length = str.length();
        int i4 = i;
        while (i3 < length && i4 - i < i2) {
            char cCharAt = str.charAt(i3);
            int i5 = sparseIntArray.get(cCharAt, -1);
            if (i5 == -1) {
                i5 = sparseIntArray2.get(cCharAt, -1);
                if (i5 == -1) {
                    i5 = sparseIntArray.get(32, 32);
                } else {
                    int i6 = i4 + 1;
                    if (i6 - i >= i2) {
                        break;
                    }
                    bArr[i4] = 27;
                    i4 = i6;
                }
            }
            bArr[i4] = (byte) i5;
            i3++;
            i4++;
        }
        while (i4 - i < i2) {
            bArr[i4] = -1;
            i4++;
        }
    }

    public static int countGsmSeptets(char c) {
        try {
            return countGsmSeptets(c, false);
        } catch (EncodeException unused) {
            return 0;
        }
    }

    public static int countGsmSeptets(char c, boolean z) throws EncodeException {
        if (sCharsToGsmTables[0].get(c, -1) != -1) {
            return 1;
        }
        if (sCharsToShiftTables[0].get(c, -1) != -1) {
            return 2;
        }
        if (z) {
            throw new EncodeException(c);
        }
        return 1;
    }

    public static boolean isGsmSeptets(char c) {
        return (sCharsToGsmTables[0].get(c, -1) == -1 && sCharsToShiftTables[0].get(c, -1) == -1) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0047 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int countGsmSeptetsUsingTables(CharSequence charSequence, boolean z, int i, int i2) {
        int length = charSequence.length();
        SparseIntArray sparseIntArray = sCharsToGsmTables[i];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char cCharAt = charSequence.charAt(i4);
            if (cCharAt == 27) {
                Log.w(TAG, "countGsmSeptets() string contains Escape character, skipping.");
            } else if (sparseIntArray.get(cCharAt, -1) == -1) {
                if (sparseIntArray2.get(cCharAt, -1) != -1) {
                    i3 += 2;
                    if (!sEnableIgnoreSpecialChar && (cCharAt == 165 || cCharAt == 163 || cCharAt == 8364)) {
                        return -1;
                    }
                } else {
                    if (!z) {
                        return -1;
                    }
                    i3++;
                    if (!sEnableIgnoreSpecialChar) {
                        continue;
                    }
                }
            } else {
                i3++;
                if (!sEnableIgnoreSpecialChar) {
                }
            }
        }
        return i3;
    }

    public static TextEncodingDetails countGsmSeptets(CharSequence charSequence, boolean z) {
        int i;
        int i2;
        int i3;
        TextEncodingDetails textEncodingDetails;
        if (!sDisableCountryEncodingCheck) {
            enableCountrySpecificEncodings();
        }
        TextEncodingDetails textEncodingDetails2 = null;
        int i4 = 160;
        if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0) {
            TextEncodingDetails textEncodingDetails3 = new TextEncodingDetails();
            int iCountGsmSeptetsUsingTables = countGsmSeptetsUsingTables(charSequence, z, 0, 0);
            if (iCountGsmSeptetsUsingTables == -1) {
                return null;
            }
            textEncodingDetails3.codeUnitSize = 1;
            textEncodingDetails3.codeUnitCount = iCountGsmSeptetsUsingTables;
            if (iCountGsmSeptetsUsingTables > 160) {
                textEncodingDetails3.msgCount = (iCountGsmSeptetsUsingTables + 152) / 153;
                textEncodingDetails3.codeUnitsRemaining = (textEncodingDetails3.msgCount * 153) - iCountGsmSeptetsUsingTables;
                return textEncodingDetails3;
            }
            textEncodingDetails3.msgCount = 1;
            textEncodingDetails3.codeUnitsRemaining = 160 - iCountGsmSeptetsUsingTables;
            return textEncodingDetails3;
        }
        int i5 = sHighestEnabledSingleShiftCode;
        ArrayList<LanguagePairCount> arrayList = new ArrayList(sEnabledLockingShiftTables.length + 1);
        arrayList.add(new LanguagePairCount(0));
        for (int i6 : sEnabledLockingShiftTables) {
            if (i6 != 0 && !sLanguageTables[i6].isEmpty()) {
                arrayList.add(new LanguagePairCount(i6));
            }
        }
        int length = charSequence.length();
        for (int i7 = 0; i7 < length && !arrayList.isEmpty(); i7++) {
            char cCharAt = charSequence.charAt(i7);
            if (cCharAt == 27) {
                Log.w(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
            } else {
                for (LanguagePairCount languagePairCount : arrayList) {
                    if (sCharsToGsmTables[languagePairCount.languageCode].get(cCharAt, -1) == -1) {
                        for (int i8 = 0; i8 <= i5; i8++) {
                            if (languagePairCount.septetCounts[i8] != -1) {
                                if (sCharsToShiftTables[i8].get(cCharAt, -1) != -1) {
                                    int[] iArr = languagePairCount.septetCounts;
                                    iArr[i8] = iArr[i8] + 2;
                                } else if (z) {
                                    int[] iArr2 = languagePairCount.septetCounts;
                                    iArr2[i8] = iArr2[i8] + 1;
                                    int[] iArr3 = languagePairCount.unencodableCounts;
                                    iArr3[i8] = iArr3[i8] + 1;
                                } else {
                                    languagePairCount.septetCounts[i8] = -1;
                                }
                            }
                        }
                    } else {
                        for (int i9 = 0; i9 <= i5; i9++) {
                            if (languagePairCount.septetCounts[i9] != -1) {
                                int[] iArr4 = languagePairCount.septetCounts;
                                iArr4[i9] = iArr4[i9] + 1;
                            }
                        }
                    }
                }
            }
        }
        TextEncodingDetails textEncodingDetails4 = new TextEncodingDetails();
        textEncodingDetails4.msgCount = Integer.MAX_VALUE;
        textEncodingDetails4.codeUnitSize = 1;
        int i10 = Integer.MAX_VALUE;
        for (LanguagePairCount languagePairCount2 : arrayList) {
            int i11 = 0;
            while (i11 <= i5) {
                int i12 = languagePairCount2.septetCounts[i11];
                if (i12 == -1) {
                    textEncodingDetails = textEncodingDetails2;
                } else {
                    if (languagePairCount2.languageCode == 0 || i11 == 0) {
                        i = (languagePairCount2.languageCode == 0 && i11 == 0) ? 0 : 5;
                    } else {
                        i = 8;
                    }
                    if (i12 + i > i4) {
                        if (i == 0) {
                            i = 1;
                        }
                        int i13 = 160 - (i + 6);
                        i3 = ((i12 + i13) - 1) / i13;
                        i2 = (i13 * i3) - i12;
                    } else {
                        i2 = (160 - i) - i12;
                        i3 = 1;
                    }
                    textEncodingDetails = textEncodingDetails2;
                    int i14 = languagePairCount2.unencodableCounts[i11];
                    if ((!z || i14 <= i10) && ((z && i14 < i10) || i3 < textEncodingDetails4.msgCount || (i3 == textEncodingDetails4.msgCount && i2 > textEncodingDetails4.codeUnitsRemaining))) {
                        textEncodingDetails4.msgCount = i3;
                        textEncodingDetails4.codeUnitCount = i12;
                        textEncodingDetails4.codeUnitsRemaining = i2;
                        textEncodingDetails4.languageTable = languagePairCount2.languageCode;
                        textEncodingDetails4.languageShiftTable = i11;
                        i10 = i14;
                    }
                }
                i11++;
                textEncodingDetails2 = textEncodingDetails;
                i4 = 160;
            }
        }
        return textEncodingDetails4.msgCount == Integer.MAX_VALUE ? textEncodingDetails2 : textEncodingDetails4;
    }

    public static int findGsmSeptetLimitIndex(String str, int i, int i2, int i3, int i4) {
        int length = str.length();
        SparseIntArray sparseIntArray = sCharsToGsmTables[i3];
        SparseIntArray sparseIntArray2 = sCharsToShiftTables[i4];
        int i5 = 0;
        while (i < length) {
            i5 = (sparseIntArray.get(str.charAt(i), -1) != -1 || sparseIntArray2.get(str.charAt(i), -1) == -1) ? i5 + 1 : i5 + 2;
            if (i5 > i2) {
                return i;
            }
            i++;
        }
        return length;
    }

    public static synchronized void setEnabledSingleShiftTables(int[] iArr) {
        sEnabledSingleShiftTables = iArr;
        sDisableCountryEncodingCheck = true;
        if (iArr.length > 0) {
            sHighestEnabledSingleShiftCode = iArr[iArr.length - 1];
        } else {
            sHighestEnabledSingleShiftCode = 0;
        }
    }

    public static synchronized void setEnabledLockingShiftTables(int[] iArr) {
        sEnabledLockingShiftTables = iArr;
        sDisableCountryEncodingCheck = true;
    }

    public static synchronized int[] getEnabledSingleShiftTables() {
        return sEnabledSingleShiftTables;
    }

    public static synchronized int[] getEnabledLockingShiftTables() {
        return sEnabledLockingShiftTables;
    }

    private static void enableCountrySpecificEncodings() {
        Resources system = Resources.getSystem();
        sEnabledSingleShiftTables = system.getIntArray(R.array.config_sms_enabled_single_shift_tables);
        sEnabledLockingShiftTables = system.getIntArray(R.array.config_sms_enabled_locking_shift_tables);
        int[] iArr = sEnabledSingleShiftTables;
        if (iArr.length > 0) {
            sHighestEnabledSingleShiftCode = iArr[iArr.length - 1];
        } else {
            sHighestEnabledSingleShiftCode = 0;
        }
    }

    private static class LanguagePairCount {
        final int languageCode;
        final int[] septetCounts;
        final int[] unencodableCounts;

        LanguagePairCount(int i) {
            this.languageCode = i;
            int i2 = GsmAlphabet.sHighestEnabledSingleShiftCode;
            int i3 = i2 + 1;
            this.septetCounts = new int[i3];
            this.unencodableCounts = new int[i3];
            int i4 = 0;
            for (int i5 = 1; i5 <= i2; i5++) {
                if (GsmAlphabet.sEnabledSingleShiftTables[i4] == i5) {
                    i4++;
                } else {
                    this.septetCounts[i5] = -1;
                }
            }
            if (i == 1 && i2 >= 1) {
                this.septetCounts[1] = -1;
            } else {
                if (i != 3 || i2 < 2) {
                    return;
                }
                this.septetCounts[2] = -1;
            }
        }
    }

    static {
        String[] strArr = {"@£$¥èéùìòÇ\nØø\rÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffÆæßÉ !\"#¤%&'()*+,-./0123456789:;<=>?¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿abcdefghijklmnopqrstuvwxyzäöñüà", "@£$¥€éùıòÇ\nĞğ\rÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffŞşßÉ !\"#¤%&'()*+,-./0123456789:;<=>?İABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§çabcdefghijklmnopqrstuvwxyzäöñüà", "", "@£$¥êéúíóç\nÔô\rÁáΔ_ªÇÀ∞^\\€Ó|\uffffÂâÊÉ !\"#º%&'()*+,-./0123456789:;<=>?ÍABCDEFGHIJKLMNOPQRSTUVWXYZÃÕÚÜ§~abcdefghijklmnopqrstuvwxyzãõ`üà", "ঁংঃঅআইঈউঊঋ\nঌ \r এঐ  ওঔকখগঘঙচ\uffffছজঝঞ !টঠডঢণত)(থদ,ধ.ন0123456789:; পফ?বভমযর ল   শষসহ়ঽািীুূৃৄ  েৈ  োৌ্ৎabcdefghijklmnopqrstuvwxyzৗড়ঢ়ৰৱ", "ઁંઃઅઆઇઈઉઊઋ\nઌઍ\r એઐઑ ઓઔકખગઘઙચ\uffffછજઝઞ !ટઠડઢણત)(થદ,ધ.ન0123456789:; પફ?બભમયર લળ વશષસહ઼ઽાિીુૂૃૄૅ ેૈૉ ોૌ્ૐabcdefghijklmnopqrstuvwxyzૠૡૢૣ૱", "ँंःअआइईउऊऋ\nऌऍ\rऎएऐऑऒओऔकखगघङच\uffffछजझञ !टठडढणत)(थद,ध.न0123456789:;ऩपफ?बभमयरऱलळऴवशषसह़ऽािीुूृॄॅॆेैॉॊोौ्ॐabcdefghijklmnopqrstuvwxyzॲॻॼॾॿ", " ಂಃಅಆಇಈಉಊಋ\nಌ \rಎಏಐ ಒಓಔಕಖಗಘಙಚ\uffffಛಜಝಞ !ಟಠಡಢಣತ)(ಥದ,ಧ.ನ0123456789:; ಪಫ?ಬಭಮಯರಱಲಳ ವಶಷಸಹ಼ಽಾಿೀುೂೃೄ ೆೇೈ ೊೋೌ್ೕabcdefghijklmnopqrstuvwxyzೖೠೡೢೣ", " ംഃഅആഇഈഉഊഋ\nഌ \rഎഏഐ ഒഓഔകഖഗഘങച\uffffഛജഝഞ !ടഠഡഢണത)(ഥദ,ധ.ന0123456789:; പഫ?ബഭമയരറലളഴവശഷസഹ ഽാിീുൂൃൄ െേൈ ൊോൌ്ൗabcdefghijklmnopqrstuvwxyzൠൡൢൣ൹", "ଁଂଃଅଆଇଈଉଊଋ\nଌ \r ଏଐ  ଓଔକଖଗଘଙଚ\uffffଛଜଝଞ !ଟଠଡଢଣତ)(ଥଦ,ଧ.ନ0123456789:; ପଫ?ବଭମଯର ଲଳ ଵଶଷସହ଼ଽାିୀୁୂୃୄ  େୈ  ୋୌ୍ୖabcdefghijklmnopqrstuvwxyzୗୠୡୢୣ", "ਁਂਃਅਆਇਈਉਊ \n  \r ਏਐ  ਓਔਕਖਗਘਙਚ\uffffਛਜਝਞ !ਟਠਡਢਣਤ)(ਥਦ,ਧ.ਨ0123456789:; ਪਫ?ਬਭਮਯਰ ਲਲ਼ ਵਸ਼ ਸਹ਼ ਾਿੀੁੂ    ੇੈ  ੋੌ੍ੑabcdefghijklmnopqrstuvwxyzੰੱੲੳੴ", " ஂஃஅஆஇஈஉஊ \n  \rஎஏஐ ஒஓஔக   ஙச\uffff ஜ ஞ !ட   ணத)(  , .ந0123456789:;னப ?  மயரறலளழவஶஷஸஹ  ாிீுூ   ெேை ொோௌ்ௐabcdefghijklmnopqrstuvwxyzௗ௰௱௲௹", "ఁంఃఅఆఇఈఉఊఋ\nఌ \rఎఏఐ ఒఓఔకఖగఘఙచ\uffffఛజఝఞ !టఠడఢణత)(థద,ధ.న0123456789:; పఫ?బభమయరఱలళ వశషసహ ఽాిీుూృౄ ెేై ొోౌ్ౕabcdefghijklmnopqrstuvwxyzౖౠౡౢౣ", "اآبٻڀپڦتۂٿ\nٹٽ\rٺټثجځڄڃڅچڇحخد\uffffڌڈډڊ !ڏڍذرڑړ)(ڙز,ږ.ژ0123456789:;ښسش?صضطظعفقکڪګگڳڱلمنںڻڼوۄەہھءیېےٍُِٗٔabcdefghijklmnopqrstuvwxyzّٰٕٖٓ"};
        sLanguageTables = strArr;
        String[] strArr2 = {"          \f         ^                   {}     \\            [~] |                                    €                          ", "          \f         ^                   {}     \\            [~] |      Ğ İ         Ş               ç € ğ ı         ş            ", "         ç\f         ^                   {}     \\            [~] |Á       Í     Ó     Ú           á   €   í     ó     ú          ", "     ê   ç\fÔô Áá  ΦΓ^ΩΠΨΣΘ     Ê        {}     \\            [~] |À       Í     Ó     Ú     ÃÕ    Â   €   í     ó     ú     ãõ  â", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*০১ ২৩৪৫৬৭৮৯য়ৠৡৢ{}ৣ৲৳৴৵\\৶৷৸৹৺       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ૦૧૨૩૪૫૬૭૮૯  {}     \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ०१२३४५६७८९॒॑{}॓॔क़ख़ग़\\ज़ड़ढ़फ़य़ॠॡॢॣ॰ॱ [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ೦೧೨೩೪೫೬೭೮೯ೞೱ{}ೲ    \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ൦൧൨൩൪൫൬൭൮൯൰൱{}൲൳൴൵ൺ\\ൻർൽൾൿ       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ୦୧୨୩୪୫୬୭୮୯ଡ଼ଢ଼{}ୟ୰ୱ  \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ੦੧੨੩੪੫੬੭੮੯ਖ਼ਗ਼{}ਜ਼ੜਫ਼ੵ \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ௦௧௨௩௪௫௬௭௮௯௳௴{}௵௶௷௸௺\\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*   ౦౧౨౩౪౫౬౭౮౯ౘౙ{}౸౹౺౻౼\\౽౾౿         [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*\u0600\u0601 ۰۱۲۳۴۵۶۷۸۹،؍{}؎؏ؐؑؒ\\ؓؔ؛؟ـْ٘٫٬ٲٳۍ[~]۔|ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          "};
        sLanguageShiftTables = strArr2;
        enableCountrySpecificEncodings();
        int length = strArr.length;
        int length2 = strArr2.length;
        if (length != length2) {
            Log.e(TAG, "Error: language tables array length " + length + " != shift tables array length " + length2);
        }
        sCharsToGsmTables = new SparseIntArray[length];
        for (int i = 0; i < length; i++) {
            String str = sLanguageTables[i];
            int length3 = str.length();
            if (length3 != 0 && length3 != 128) {
                Log.e(TAG, "Error: language tables index " + i + " length " + length3 + " (expected 128 or 0)");
            }
            SparseIntArray sparseIntArray = new SparseIntArray(length3);
            sCharsToGsmTables[i] = sparseIntArray;
            for (int i2 = 0; i2 < length3; i2++) {
                sparseIntArray.put(str.charAt(i2), i2);
            }
        }
        sCharsToShiftTables = new SparseIntArray[length2];
        for (int i3 = 0; i3 < length2; i3++) {
            String str2 = sLanguageShiftTables[i3];
            int length4 = str2.length();
            if (length4 != 0 && length4 != 128) {
                Log.e(TAG, "Error: language shift tables index " + i3 + " length " + length4 + " (expected 128 or 0)");
            }
            SparseIntArray sparseIntArray2 = new SparseIntArray(length4);
            sCharsToShiftTables[i3] = sparseIntArray2;
            for (int i4 = 0; i4 < length4; i4++) {
                char cCharAt = str2.charAt(i4);
                if (cCharAt != ' ') {
                    sparseIntArray2.put(cCharAt, i4);
                }
            }
        }
    }

    public static TextEncodingDetails countGsmSeptetsWithEmail(CharSequence charSequence, boolean z, int i) {
        int i2;
        int i3;
        int i4;
        StringBuilder sb = new StringBuilder("sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0: ");
        int i5 = 1;
        sb.append(sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0);
        Log.d(TAG, sb.toString());
        if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0) {
            TextEncodingDetails textEncodingDetails = new TextEncodingDetails();
            int iCountGsmSeptetsUsingTables = countGsmSeptetsUsingTables(charSequence, z, 0, 0);
            if (iCountGsmSeptetsUsingTables == -1) {
                return null;
            }
            int i6 = i > 0 ? 159 - i : 160;
            int i7 = i > 0 ? 152 - i : 153;
            if (iCountGsmSeptetsUsingTables != -1 && iCountGsmSeptetsUsingTables <= i6) {
                textEncodingDetails.msgCount = 1;
                textEncodingDetails.codeUnitCount = iCountGsmSeptetsUsingTables;
                textEncodingDetails.codeUnitsRemaining = i6 - iCountGsmSeptetsUsingTables;
                textEncodingDetails.codeUnitSize = 1;
                return textEncodingDetails;
            }
            if (iCountGsmSeptetsUsingTables != -1) {
                textEncodingDetails.codeUnitCount = iCountGsmSeptetsUsingTables;
                if (iCountGsmSeptetsUsingTables > i6) {
                    textEncodingDetails.msgCount = ((i7 - 1) + iCountGsmSeptetsUsingTables) / i7;
                    int i8 = iCountGsmSeptetsUsingTables % i7;
                    if (i8 > 0) {
                        textEncodingDetails.codeUnitsRemaining = i7 - i8;
                    } else {
                        textEncodingDetails.codeUnitsRemaining = 0;
                    }
                } else {
                    textEncodingDetails.msgCount = 1;
                    textEncodingDetails.codeUnitsRemaining = i6 - iCountGsmSeptetsUsingTables;
                }
                textEncodingDetails.codeUnitSize = 1;
            }
            return textEncodingDetails;
        }
        int i9 = sHighestEnabledSingleShiftCode;
        ArrayList<LanguagePairCount> arrayList = new ArrayList(sEnabledLockingShiftTables.length + 1);
        arrayList.add(new LanguagePairCount(0));
        for (int i10 : sEnabledLockingShiftTables) {
            if (i10 != 0 && !sLanguageTables[i10].isEmpty()) {
                arrayList.add(new LanguagePairCount(i10));
            }
        }
        int length = charSequence.length();
        for (int i11 = 0; i11 < length && !arrayList.isEmpty(); i11++) {
            char cCharAt = charSequence.charAt(i11);
            if (cCharAt == 27) {
                Log.d(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
            } else {
                for (LanguagePairCount languagePairCount : arrayList) {
                    if (sCharsToGsmTables[languagePairCount.languageCode].get(cCharAt, -1) == -1) {
                        for (int i12 = 0; i12 <= i9; i12++) {
                            if (languagePairCount.septetCounts[i12] != -1) {
                                if (sCharsToShiftTables[i12].get(cCharAt, -1) != -1) {
                                    int[] iArr = languagePairCount.septetCounts;
                                    iArr[i12] = iArr[i12] + 2;
                                } else if (z) {
                                    int[] iArr2 = languagePairCount.septetCounts;
                                    iArr2[i12] = iArr2[i12] + 1;
                                    int[] iArr3 = languagePairCount.unencodableCounts;
                                    iArr3[i12] = iArr3[i12] + 1;
                                } else {
                                    languagePairCount.septetCounts[i12] = -1;
                                }
                            }
                        }
                    } else {
                        for (int i13 = 0; i13 <= i9; i13++) {
                            if (languagePairCount.septetCounts[i13] != -1) {
                                int[] iArr4 = languagePairCount.septetCounts;
                                iArr4[i13] = iArr4[i13] + 1;
                            }
                        }
                    }
                }
            }
        }
        TextEncodingDetails textEncodingDetails2 = new TextEncodingDetails();
        textEncodingDetails2.msgCount = Integer.MAX_VALUE;
        textEncodingDetails2.codeUnitSize = 1;
        int i14 = Integer.MAX_VALUE;
        for (LanguagePairCount languagePairCount2 : arrayList) {
            int i15 = 0;
            while (i15 <= i9) {
                int i16 = languagePairCount2.septetCounts[i15];
                if (i16 != -1) {
                    if (languagePairCount2.languageCode == 0 || i15 == 0) {
                        i2 = (languagePairCount2.languageCode == 0 && i15 == 0) ? 0 : 5;
                    } else {
                        i2 = 8;
                    }
                    if (i16 + i2 > 160) {
                        if (i2 == 0) {
                            i2 = i5;
                        }
                        int i17 = 160 - (i2 + 6);
                        i4 = ((i16 + i17) - i5) / i17;
                        i3 = (i17 * i4) - i16;
                    } else {
                        i3 = (160 - i2) - i16;
                        i4 = i5;
                    }
                    int i18 = languagePairCount2.unencodableCounts[i15];
                    if ((!z || i18 <= i14) && ((z && i18 < i14) || i4 < textEncodingDetails2.msgCount || (i4 == textEncodingDetails2.msgCount && i3 > textEncodingDetails2.codeUnitsRemaining))) {
                        textEncodingDetails2.msgCount = i4;
                        textEncodingDetails2.codeUnitCount = i16;
                        textEncodingDetails2.codeUnitsRemaining = i3;
                        textEncodingDetails2.languageTable = languagePairCount2.languageCode;
                        textEncodingDetails2.languageShiftTable = i15;
                        i14 = i18;
                    }
                }
                i15++;
                i5 = 1;
            }
        }
        if (textEncodingDetails2.msgCount == Integer.MAX_VALUE) {
            return null;
        }
        return textEncodingDetails2;
    }

    public static TextEncodingDetails countGsmSeptets(CharSequence charSequence, boolean z, boolean z2) {
        sEnableIgnoreSpecialChar = z2;
        TextEncodingDetails textEncodingDetailsCountGsmSeptets = countGsmSeptets(charSequence, z);
        sEnableIgnoreSpecialChar = false;
        return textEncodingDetailsCountGsmSeptets;
    }

    public static byte[] stringToGsm8BitPackedForAutoLogin(String str) {
        int length = str.length();
        if (length < 5) {
            return null;
        }
        byte[] bArr = new byte[length + 1];
        bArr[1] = (byte) str.charAt(0);
        bArr[2] = (byte) str.charAt(1);
        bArr[3] = (byte) str.charAt(2);
        bArr[4] = (byte) str.charAt(3);
        stringToGsm8BitUnpackedField(str.substring(4), bArr, 5, length - 4);
        bArr[0] = (byte) length;
        return bArr;
    }
}
