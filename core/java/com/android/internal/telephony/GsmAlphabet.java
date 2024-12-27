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
import java.util.List;

public class GsmAlphabet {
    public static final byte GSM_EXTENDED_ESCAPE = 27;
    private static final String TAG = "GSM";
    public static final int UDH_SEPTET_COST_CONCATENATED_MESSAGE = 6;
    public static final int UDH_SEPTET_COST_LENGTH = 1;
    public static final int UDH_SEPTET_COST_ONE_SHIFT_TABLE = 4;
    public static final int UDH_SEPTET_COST_TWO_SHIFT_TABLES = 7;
    private static final SparseIntArray[] sCharsToGsmTables;
    private static final SparseIntArray[] sCharsToShiftTables;
    private static int[] sEnabledLockingShiftTables;
    private static int[] sEnabledSingleShiftTables;
    private static int sHighestEnabledSingleShiftCode;
    private static boolean sDisableCountryEncodingCheck = false;
    private static boolean sEnableIgnoreSpecialChar = false;
    private static final String[] sLanguageTables = {
        "@£$¥èéùìòÇ\n"
            + "Øø\r"
            + "ÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffÆæßÉ"
            + " !\"#¤%&'()*+,-./0123456789:;<=>?¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿abcdefghijklmnopqrstuvwxyzäöñüà",
        "@£$¥€éùıòÇ\n"
            + "Ğğ\r"
            + "ÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffŞşßÉ"
            + " !\"#¤%&'()*+,-./0123456789:;<=>?İABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§çabcdefghijklmnopqrstuvwxyzäöñüà",
        "",
        "@£$¥êéúíóç\n"
            + "Ôô\r"
            + "ÁáΔ_ªÇÀ∞^\\€Ó|\uffffÂâÊÉ"
            + " !\"#º%&'()*+,-./0123456789:;<=>?ÍABCDEFGHIJKLMNOPQRSTUVWXYZÃÕÚÜ§~abcdefghijklmnopqrstuvwxyzãõ`üà",
        "ঁংঃঅআইঈউঊঋ\n"
            + "ঌ \r"
            + " এঐ  ওঔকখগঘঙচ\uffffছজঝঞ !টঠডঢণত)(থদ,ধ.ন0123456789:; পফ?বভমযর ল   শষসহ়ঽািীুূৃৄ  েৈ "
            + " োৌ্ৎabcdefghijklmnopqrstuvwxyzৗড়ঢ়ৰৱ",
        "ઁંઃઅઆઇઈઉઊઋ\n"
            + "ઌઍ\r"
            + " એઐઑ ઓઔકખગઘઙચ\uffffછજઝઞ !ટઠડઢણત)(થદ,ધ.ન0123456789:; પફ?બભમયર લળ વશષસહ઼ઽાિીુૂૃૄૅ ેૈૉ"
            + " ોૌ્ૐabcdefghijklmnopqrstuvwxyzૠૡૢૣ૱",
        "ँंःअआइईउऊऋ\n"
            + "ऌऍ\r"
            + "ऎएऐऑऒओऔकखगघङच\uffffछजझञ"
            + " !टठडढणत)(थद,ध.न0123456789:;ऩपफ?बभमयरऱलळऴवशषसह़ऽािीुूृॄॅॆेैॉॊोौ्ॐabcdefghijklmnopqrstuvwxyzॲॻॼॾॿ",
        " ಂಃಅಆಇಈಉಊಋ\n"
            + "ಌ \r"
            + "ಎಏಐ ಒಓಔಕಖಗಘಙಚ\uffffಛಜಝಞ !ಟಠಡಢಣತ)(ಥದ,ಧ.ನ0123456789:; ಪಫ?ಬಭಮಯರಱಲಳ ವಶಷಸಹ಼ಽಾಿೀುೂೃೄ ೆೇೈ"
            + " ೊೋೌ್ೕabcdefghijklmnopqrstuvwxyzೖೠೡೢೣ",
        " ംഃഅആഇഈഉഊഋ\n"
            + "ഌ \r"
            + "എഏഐ ഒഓഔകഖഗഘങച\uffffഛജഝഞ !ടഠഡഢണത)(ഥദ,ധ.ന0123456789:; പഫ?ബഭമയരറലളഴവശഷസഹ ഽാിീുൂൃൄ െേൈ"
            + " ൊോൌ്ൗabcdefghijklmnopqrstuvwxyzൠൡൢൣ൹",
        "ଁଂଃଅଆଇଈଉଊଋ\n"
            + "ଌ \r"
            + " ଏଐ  ଓଔକଖଗଘଙଚ\uffffଛଜଝଞ !ଟଠଡଢଣତ)(ଥଦ,ଧ.ନ0123456789:; ପଫ?ବଭମଯର ଲଳ ଵଶଷସହ଼ଽାିୀୁୂୃୄ  େୈ "
            + " ୋୌ୍ୖabcdefghijklmnopqrstuvwxyzୗୠୡୢୣ",
        "ਁਂਃਅਆਇਈਉਊ \n"
            + "  \r"
            + " ਏਐ  ਓਔਕਖਗਘਙਚ\uffffਛਜਝਞ !ਟਠਡਢਣਤ)(ਥਦ,ਧ.ਨ0123456789:; ਪਫ?ਬਭਮਯਰ ਲਲ਼ ਵਸ਼ ਸਹ਼ ਾਿੀੁੂ    ੇੈ "
            + " ੋੌ੍ੑabcdefghijklmnopqrstuvwxyzੰੱੲੳੴ",
        " ஂஃஅஆஇஈஉஊ \n"
            + "  \r"
            + "எஏஐ ஒஓஔக   ஙச\uffff ஜ ஞ !ட   ணத)(  , .ந0123456789:;னப ?  மயரறலளழவஶஷஸஹ  ாிீுூ   ெேை"
            + " ொோௌ்ௐabcdefghijklmnopqrstuvwxyzௗ௰௱௲௹",
        "ఁంఃఅఆఇఈఉఊఋ\n"
            + "ఌ \r"
            + "ఎఏఐ ఒఓఔకఖగఘఙచ\uffffఛజఝఞ !టఠడఢణత)(థద,ధ.న0123456789:; పఫ?బభమయరఱలళ వశషసహ ఽాిీుూృౄ ెేై"
            + " ొోౌ్ౕabcdefghijklmnopqrstuvwxyzౖౠౡౢౣ",
        "اآبٻڀپڦتۂٿ\n"
            + "ٹٽ\r"
            + "ٺټثجځڄڃڅچڇحخد\uffffڌڈډڊ"
            + " !ڏڍذرڑړ)(ڙز,ږ.ژ0123456789:;ښسش?صضطظعفقکڪګگڳڱلمنںڻڼوۄەہھءیېےٍُِٗٔabcdefghijklmnopqrstuvwxyzّٰٕٖٓ"
    };
    private static final String[] sLanguageShiftTables = {
        "          \f         ^                   {}     \\            [~] |                       "
            + "             €                          ",
        "          \f         ^                   {}     \\            [~] |      Ğ İ         Ş    "
            + "           ç € ğ ı         ş            ",
        "         ç\f         ^                   {}     \\            [~] |Á       Í     Ó     Ú  "
            + "         á   €   í     ó     ú          ",
        "     ê   ç\fÔô Áá  ΦΓ^ΩΠΨΣΘ     Ê        {}     \\            [~] |À       Í     Ó     Ú  "
            + "   ÃÕ    Â   €   í     ó     ú     ãõ  â",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*০১ ২৩৪৫৬৭৮৯য়ৠৡৢ{}ৣ৲৳৴৵\\৶৷৸৹৺       [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ૦૧૨૩૪૫૬૭૮૯  {}     \\            [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ०१२३४५६७८९॒॑{}॓॔क़ख़ग़\\ज़ड़ढ़फ़य़ॠॡॢॣ॰ॱ [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ೦೧೨೩೪೫೬೭೮೯ೞೱ{}ೲ    \\            [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ൦൧൨൩൪൫൬൭൮൯൰൱{}൲൳൴൵ൺ\\ൻർൽൾൿ       [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ୦୧୨୩୪୫୬୭୮୯ଡ଼ଢ଼{}ୟ୰ୱ  \\            [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ੦੧੨੩੪੫੬੭੮੯ਖ਼ਗ਼{}ਜ਼ੜਫ਼ੵ \\            [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ௦௧௨௩௪௫௬௭௮௯௳௴{}௵௶௷௸௺\\            [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*   ౦౧౨౩౪౫౬౭౮౯ౘౙ{}౸౹౺౻౼\\౽౾౿         [~]"
            + " |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ",
        "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*\u0600\u0601"
            + " ۰۱۲۳۴۵۶۷۸۹،؍{}؎؏ؐؑؒ\\ؓؔ؛؟ـْ٘٫٬ٲٳۍ[~]۔|ABCDEFGHIJKLMNOPQRSTUVWXYZ          €        "
            + "                  "
    };

    private GsmAlphabet() {}

    public static class TextEncodingDetails {
        public int codeUnitCount;
        public int codeUnitSize;
        public int codeUnitsRemaining;
        public int languageShiftTable;
        public int languageTable;
        public int msgCount;

        public String toString() {
            return "TextEncodingDetails { msgCount="
                    + this.msgCount
                    + ", codeUnitCount="
                    + this.codeUnitCount
                    + ", codeUnitsRemaining="
                    + this.codeUnitsRemaining
                    + ", codeUnitSize="
                    + this.codeUnitSize
                    + ", languageTable="
                    + this.languageTable
                    + ", languageShiftTable="
                    + this.languageShiftTable
                    + " }";
        }
    }

    public static int charToGsm(char c) {
        try {
            return charToGsm(c, false);
        } catch (EncodeException e) {
            return sCharsToGsmTables[0].get(32, 32);
        }
    }

    public static int charToGsm(char c, boolean throwException) throws EncodeException {
        int ret = sCharsToGsmTables[0].get(c, -1);
        if (ret == -1) {
            if (sCharsToShiftTables[0].get(c, -1) == -1) {
                if (throwException) {
                    throw new EncodeException(c);
                }
                return sCharsToGsmTables[0].get(32, 32);
            }
            return 27;
        }
        return ret;
    }

    public static int charToGsmExtended(char c) {
        int ret = sCharsToShiftTables[0].get(c, -1);
        if (ret == -1) {
            return sCharsToGsmTables[0].get(32, 32);
        }
        return ret;
    }

    public static char gsmToChar(int gsmChar) {
        if (gsmChar >= 0 && gsmChar < 128) {
            return sLanguageTables[0].charAt(gsmChar);
        }
        return ' ';
    }

    public static char gsmExtendedToChar(int gsmChar) {
        if (gsmChar == 27 || gsmChar < 0 || gsmChar >= 128) {
            return ' ';
        }
        char c = sLanguageShiftTables[0].charAt(gsmChar);
        if (c == ' ') {
            return sLanguageTables[0].charAt(gsmChar);
        }
        return c;
    }

    public static byte[] stringToGsm7BitPackedWithHeader(String data, byte[] header)
            throws EncodeException {
        return stringToGsm7BitPackedWithHeader(data, header, 0, 0);
    }

    public static byte[] stringToGsm7BitPackedWithHeader(
            String data, byte[] header, int languageTable, int languageShiftTable)
            throws EncodeException {
        if (header == null || header.length == 0) {
            return stringToGsm7BitPacked(data, languageTable, languageShiftTable);
        }
        int headerBits = (header.length + 1) * 8;
        int headerSeptets = (headerBits + 6) / 7;
        byte[] ret =
                stringToGsm7BitPacked(data, headerSeptets, true, languageTable, languageShiftTable);
        ret[1] = (byte) header.length;
        System.arraycopy(header, 0, ret, 2, header.length);
        return ret;
    }

    public static byte[] stringToGsm7BitPacked(String data) throws EncodeException {
        return stringToGsm7BitPacked(data, 0, true, 0, 0);
    }

    public static byte[] stringToGsm7BitPacked(
            String data, int languageTable, int languageShiftTable) throws EncodeException {
        return stringToGsm7BitPacked(data, 0, true, languageTable, languageShiftTable);
    }

    public static byte[] stringToGsm7BitPacked(
            String data,
            int startingSeptetOffset,
            boolean throwException,
            int languageTable,
            int languageShiftTable)
            throws EncodeException {
        int dataLen = data.length();
        int septetCount =
                countGsmSeptetsUsingTables(
                        data, !throwException, languageTable, languageShiftTable);
        int i = -1;
        if (septetCount == -1) {
            throw new EncodeException("countGsmSeptetsUsingTables(): unencodable char");
        }
        int septetCount2 = septetCount + startingSeptetOffset;
        if (septetCount2 > 255) {
            throw new EncodeException("Payload cannot exceed 255 septets", 1);
        }
        int byteCount = ((septetCount2 * 7) + 7) / 8;
        byte[] ret = new byte[byteCount + 1];
        SparseIntArray charToLanguageTable = sCharsToGsmTables[languageTable];
        SparseIntArray charToShiftTable = sCharsToShiftTables[languageShiftTable];
        int i2 = 0;
        int septets = startingSeptetOffset;
        int bitOffset = startingSeptetOffset * 7;
        while (i2 < dataLen && septets < septetCount2) {
            char c = data.charAt(i2);
            int v = charToLanguageTable.get(c, i);
            if (v == i) {
                v = charToShiftTable.get(c, i);
                if (v == i) {
                    if (throwException) {
                        throw new EncodeException("stringToGsm7BitPacked(): unencodable char");
                    }
                    v = charToLanguageTable.get(32, 32);
                } else {
                    packSmsChar(ret, bitOffset, 27);
                    bitOffset += 7;
                    septets++;
                }
            }
            packSmsChar(ret, bitOffset, v);
            septets++;
            i2++;
            bitOffset += 7;
            i = -1;
        }
        ret[0] = (byte) septetCount2;
        return ret;
    }

    private static void packSmsChar(byte[] packedChars, int bitOffset, int value) {
        int shift = bitOffset % 8;
        int byteOffset = (bitOffset / 8) + 1;
        packedChars[byteOffset] = (byte) (packedChars[byteOffset] | (value << shift));
        if (shift > 1) {
            packedChars[byteOffset + 1] = (byte) (value >> (8 - shift));
        }
    }

    public static String gsm7BitPackedToString(byte[] pdu, int offset, int lengthSeptets) {
        return gsm7BitPackedToString(pdu, offset, lengthSeptets, 0, 0, 0);
    }

    public static String gsm7BitPackedToString(
            byte[] pdu,
            int offset,
            int lengthSeptets,
            int numPaddingBits,
            int languageTable,
            int shiftTable) {
        int languageTable2;
        int shiftTable2 = shiftTable;
        StringBuilder ret = new StringBuilder(lengthSeptets);
        if (languageTable < 0 || languageTable > sLanguageTables.length) {
            Log.w(TAG, "unknown language table " + languageTable + ", using default");
            languageTable2 = 0;
        } else {
            languageTable2 = languageTable;
        }
        if (shiftTable2 < 0 || shiftTable2 > sLanguageShiftTables.length) {
            Log.w(TAG, "unknown single shift table " + shiftTable2 + ", using default");
            shiftTable2 = 0;
        }
        boolean prevCharWasEscape = false;
        try {
            String languageTableToChar = sLanguageTables[languageTable2];
            String shiftTableToChar = sLanguageShiftTables[shiftTable2];
            if (languageTableToChar.isEmpty()) {
                Log.w(TAG, "no language table for code " + languageTable2 + ", using default");
                languageTableToChar = sLanguageTables[0];
            }
            if (shiftTableToChar.isEmpty()) {
                Log.w(TAG, "no single shift table for code " + shiftTable2 + ", using default");
                shiftTableToChar = sLanguageShiftTables[0];
            }
            for (int i = 0; i < lengthSeptets; i++) {
                int bitOffset = (i * 7) + numPaddingBits;
                int byteOffset = bitOffset / 8;
                int shift = bitOffset % 8;
                int gsmVal = (pdu[offset + byteOffset] >> shift) & 127;
                if (shift > 1) {
                    gsmVal =
                            (gsmVal & (127 >> (shift - 1)))
                                    | (127 & (pdu[(offset + byteOffset) + 1] << (8 - shift)));
                }
                if (prevCharWasEscape) {
                    if (gsmVal == 27) {
                        ret.append(' ');
                    } else {
                        char c = shiftTableToChar.charAt(gsmVal);
                        if (c == ' ') {
                            ret.append(languageTableToChar.charAt(gsmVal));
                        } else {
                            ret.append(c);
                        }
                    }
                    prevCharWasEscape = false;
                } else if (gsmVal == 27) {
                    prevCharWasEscape = true;
                } else {
                    ret.append(languageTableToChar.charAt(gsmVal));
                }
            }
            return ret.toString();
        } catch (RuntimeException ex) {
            Log.e(TAG, "Error GSM 7 bit packed: ", ex);
            return null;
        }
    }

    public static String gsm8BitUnpackedToString(byte[] data, int offset, int length) {
        return gsm8BitUnpackedToString(data, offset, length, "");
    }

    public static String gsm8BitUnpackedToString(
            byte[] data, int offset, int length, String characterset) {
        int c;
        boolean isMbcs = false;
        Charset charset = null;
        ByteBuffer mbcsBuffer = null;
        if (!TextUtils.isEmpty(characterset)
                && !characterset.equalsIgnoreCase(CharacterSets.MIMENAME_US_ASCII)
                && Charset.isSupported(characterset)) {
            isMbcs = true;
            charset = Charset.forName(characterset);
            mbcsBuffer = ByteBuffer.allocate(2);
        }
        String languageTableToChar = sLanguageTables[0];
        String shiftTableToChar = sLanguageShiftTables[0];
        StringBuilder ret = new StringBuilder(length);
        boolean prevWasEscape = false;
        int i = offset;
        while (i < offset + length && (c = data[i] & 255) != 255) {
            if (c == 27) {
                if (prevWasEscape) {
                    ret.append(' ');
                    prevWasEscape = false;
                } else {
                    prevWasEscape = true;
                }
            } else {
                if (prevWasEscape) {
                    char shiftChar =
                            c < shiftTableToChar.length() ? shiftTableToChar.charAt(c) : ' ';
                    if (shiftChar == ' ') {
                        if (c < languageTableToChar.length()) {
                            ret.append(languageTableToChar.charAt(c));
                        } else {
                            ret.append(' ');
                        }
                    } else {
                        ret.append(shiftChar);
                    }
                } else if (!isMbcs || c < 128 || i + 1 >= offset + length) {
                    int i2 = languageTableToChar.length();
                    if (c < i2) {
                        ret.append(languageTableToChar.charAt(c));
                    } else {
                        ret.append(' ');
                    }
                } else {
                    mbcsBuffer.clear();
                    mbcsBuffer.put(data, i, 2);
                    mbcsBuffer.flip();
                    ret.append(charset.decode(mbcsBuffer).toString());
                    i++;
                }
                prevWasEscape = false;
            }
            i++;
        }
        return ret.toString();
    }

    public static byte[] stringToGsm8BitPacked(String s) {
        int septets = countGsmSeptetsUsingTables(s, true, 0, 0);
        byte[] ret = new byte[septets];
        stringToGsm8BitUnpackedField(s, ret, 0, ret.length);
        return ret;
    }

    /* JADX WARN: Incorrect condition in loop: B:20:0x0045 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void stringToGsm8BitUnpackedField(
            java.lang.String r9, byte[] r10, int r11, int r12) {
        /*
            r0 = r11
            android.util.SparseIntArray[] r1 = com.android.internal.telephony.GsmAlphabet.sCharsToGsmTables
            r2 = 0
            r1 = r1[r2]
            android.util.SparseIntArray[] r3 = com.android.internal.telephony.GsmAlphabet.sCharsToShiftTables
            r2 = r3[r2]
            r3 = 0
            int r4 = r9.length()
        Lf:
            r5 = -1
            if (r3 >= r4) goto L43
            int r6 = r0 - r11
            if (r6 >= r12) goto L43
            char r6 = r9.charAt(r3)
            int r7 = r1.get(r6, r5)
            if (r7 != r5) goto L3a
            int r7 = r2.get(r6, r5)
            if (r7 != r5) goto L2d
            r5 = 32
            int r7 = r1.get(r5, r5)
            goto L3a
        L2d:
            int r8 = r0 + 1
            int r8 = r8 - r11
            if (r8 < r12) goto L33
            goto L43
        L33:
            int r5 = r0 + 1
            r8 = 27
            r10[r0] = r8
            r0 = r5
        L3a:
            int r5 = r0 + 1
            byte r8 = (byte) r7
            r10[r0] = r8
            int r3 = r3 + 1
            r0 = r5
            goto Lf
        L43:
            int r3 = r0 - r11
            if (r3 >= r12) goto L4d
            int r3 = r0 + 1
            r10[r0] = r5
            r0 = r3
            goto L43
        L4d:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.telephony.GsmAlphabet.stringToGsm8BitUnpackedField(java.lang.String,"
                    + " byte[], int, int):void");
    }

    public static int countGsmSeptets(char c) {
        try {
            return countGsmSeptets(c, false);
        } catch (EncodeException e) {
            return 0;
        }
    }

    public static int countGsmSeptets(char c, boolean throwsException) throws EncodeException {
        if (sCharsToGsmTables[0].get(c, -1) != -1) {
            return 1;
        }
        if (sCharsToShiftTables[0].get(c, -1) != -1) {
            return 2;
        }
        if (throwsException) {
            throw new EncodeException(c);
        }
        return 1;
    }

    public static boolean isGsmSeptets(char c) {
        return (sCharsToGsmTables[0].get(c, -1) == -1 && sCharsToShiftTables[0].get(c, -1) == -1)
                ? false
                : true;
    }

    public static int countGsmSeptetsUsingTables(
            CharSequence s, boolean use7bitOnly, int languageTable, int languageShiftTable) {
        int count = 0;
        int sz = s.length();
        SparseIntArray charToLanguageTable = sCharsToGsmTables[languageTable];
        SparseIntArray charToShiftTable = sCharsToShiftTables[languageShiftTable];
        for (int i = 0; i < sz; i++) {
            char c = s.charAt(i);
            if (c == 27) {
                Log.w(TAG, "countGsmSeptets() string contains Escape character, skipping.");
            } else {
                if (charToLanguageTable.get(c, -1) == -1) {
                    if (charToShiftTable.get(c, -1) != -1) {
                        count += 2;
                    } else {
                        if (!use7bitOnly) {
                            return -1;
                        }
                        count++;
                    }
                } else {
                    count++;
                }
                if (sEnableIgnoreSpecialChar && (c == 165 || c == 163 || c == 8364)) {
                    return -1;
                }
            }
        }
        return count;
    }

    public static TextEncodingDetails countGsmSeptets(CharSequence s, boolean use7bitOnly) {
        int udhLength;
        int septetsPerMessage;
        int septetsRemaining;
        if (!sDisableCountryEncodingCheck) {
            enableCountrySpecificEncodings();
        }
        int i = 160;
        int i2 = -1;
        if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0) {
            TextEncodingDetails ted = new TextEncodingDetails();
            int septets = countGsmSeptetsUsingTables(s, use7bitOnly, 0, 0);
            if (septets == -1) {
                return null;
            }
            ted.codeUnitSize = 1;
            ted.codeUnitCount = septets;
            if (septets > 160) {
                ted.msgCount = (septets + 152) / 153;
                ted.codeUnitsRemaining = (ted.msgCount * 153) - septets;
            } else {
                ted.msgCount = 1;
                ted.codeUnitsRemaining = 160 - septets;
            }
            return ted;
        }
        int maxSingleShiftCode = sHighestEnabledSingleShiftCode;
        List<LanguagePairCount> lpcList = new ArrayList<>(sEnabledLockingShiftTables.length + 1);
        lpcList.add(new LanguagePairCount(0));
        for (int i3 : sEnabledLockingShiftTables) {
            if (i3 != 0 && !sLanguageTables[i3].isEmpty()) {
                lpcList.add(new LanguagePairCount(i3));
            }
        }
        int sz = s.length();
        for (int i4 = 0; i4 < sz && !lpcList.isEmpty(); i4++) {
            char c = s.charAt(i4);
            if (c == 27) {
                Log.w(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
            } else {
                for (LanguagePairCount lpc : lpcList) {
                    int tableIndex = sCharsToGsmTables[lpc.languageCode].get(c, -1);
                    if (tableIndex == -1) {
                        for (int table = 0; table <= maxSingleShiftCode; table++) {
                            if (lpc.septetCounts[table] != -1) {
                                int shiftTableIndex = sCharsToShiftTables[table].get(c, -1);
                                if (shiftTableIndex == -1) {
                                    if (use7bitOnly) {
                                        int[] iArr = lpc.septetCounts;
                                        iArr[table] = iArr[table] + 1;
                                        int[] iArr2 = lpc.unencodableCounts;
                                        iArr2[table] = iArr2[table] + 1;
                                    } else {
                                        lpc.septetCounts[table] = -1;
                                    }
                                } else {
                                    int[] iArr3 = lpc.septetCounts;
                                    iArr3[table] = iArr3[table] + 2;
                                }
                            }
                        }
                    } else {
                        for (int table2 = 0; table2 <= maxSingleShiftCode; table2++) {
                            if (lpc.septetCounts[table2] != -1) {
                                int[] iArr4 = lpc.septetCounts;
                                iArr4[table2] = iArr4[table2] + 1;
                            }
                        }
                    }
                }
            }
        }
        TextEncodingDetails ted2 = new TextEncodingDetails();
        ted2.msgCount = Integer.MAX_VALUE;
        ted2.codeUnitSize = 1;
        int minUnencodableCount = Integer.MAX_VALUE;
        for (LanguagePairCount lpc2 : lpcList) {
            int shiftTable = 0;
            while (shiftTable <= maxSingleShiftCode) {
                int septets2 = lpc2.septetCounts[shiftTable];
                if (septets2 != i2) {
                    if (lpc2.languageCode != 0 && shiftTable != 0) {
                        udhLength = 8;
                    } else {
                        int udhLength2 = lpc2.languageCode;
                        if (udhLength2 != 0 || shiftTable != 0) {
                            udhLength = 5;
                        } else {
                            udhLength = 0;
                        }
                    }
                    if (septets2 + udhLength > i) {
                        if (udhLength == 0) {
                            udhLength = 1;
                        }
                        int septetsPerMessage2 = 160 - (udhLength + 6);
                        int msgCount = ((septets2 + septetsPerMessage2) - 1) / septetsPerMessage2;
                        int septetsRemaining2 = (msgCount * septetsPerMessage2) - septets2;
                        septetsPerMessage = msgCount;
                        septetsRemaining = septetsRemaining2;
                    } else {
                        septetsPerMessage = 1;
                        septetsRemaining = (160 - udhLength) - septets2;
                    }
                    int unencodableCount = lpc2.unencodableCounts[shiftTable];
                    if ((!use7bitOnly || unencodableCount <= minUnencodableCount)
                            && ((use7bitOnly && unencodableCount < minUnencodableCount)
                                    || septetsPerMessage < ted2.msgCount
                                    || (septetsPerMessage == ted2.msgCount
                                            && septetsRemaining > ted2.codeUnitsRemaining))) {
                        minUnencodableCount = unencodableCount;
                        ted2.msgCount = septetsPerMessage;
                        ted2.codeUnitCount = septets2;
                        ted2.codeUnitsRemaining = septetsRemaining;
                        ted2.languageTable = lpc2.languageCode;
                        ted2.languageShiftTable = shiftTable;
                    }
                }
                shiftTable++;
                i = 160;
                i2 = -1;
            }
            i = 160;
            i2 = -1;
        }
        if (ted2.msgCount == Integer.MAX_VALUE) {
            return null;
        }
        return ted2;
    }

    public static int findGsmSeptetLimitIndex(
            String s, int start, int limit, int langTable, int langShiftTable) {
        int accumulator = 0;
        int size = s.length();
        SparseIntArray charToLangTable = sCharsToGsmTables[langTable];
        SparseIntArray charToLangShiftTable = sCharsToShiftTables[langShiftTable];
        for (int i = start; i < size; i++) {
            int encodedSeptet = charToLangTable.get(s.charAt(i), -1);
            if (encodedSeptet == -1) {
                int encodedSeptet2 = charToLangShiftTable.get(s.charAt(i), -1);
                if (encodedSeptet2 == -1) {
                    accumulator++;
                } else {
                    accumulator += 2;
                }
            } else {
                accumulator++;
            }
            if (accumulator > limit) {
                return i;
            }
        }
        return size;
    }

    public static synchronized void setEnabledSingleShiftTables(int[] tables) {
        synchronized (GsmAlphabet.class) {
            sEnabledSingleShiftTables = tables;
            sDisableCountryEncodingCheck = true;
            if (tables.length > 0) {
                sHighestEnabledSingleShiftCode = tables[tables.length - 1];
            } else {
                sHighestEnabledSingleShiftCode = 0;
            }
        }
    }

    public static synchronized void setEnabledLockingShiftTables(int[] tables) {
        synchronized (GsmAlphabet.class) {
            sEnabledLockingShiftTables = tables;
            sDisableCountryEncodingCheck = true;
        }
    }

    public static synchronized int[] getEnabledSingleShiftTables() {
        int[] iArr;
        synchronized (GsmAlphabet.class) {
            iArr = sEnabledSingleShiftTables;
        }
        return iArr;
    }

    public static synchronized int[] getEnabledLockingShiftTables() {
        int[] iArr;
        synchronized (GsmAlphabet.class) {
            iArr = sEnabledLockingShiftTables;
        }
        return iArr;
    }

    private static void enableCountrySpecificEncodings() {
        Resources r = Resources.getSystem();
        sEnabledSingleShiftTables = r.getIntArray(R.array.config_sms_enabled_single_shift_tables);
        sEnabledLockingShiftTables = r.getIntArray(R.array.config_sms_enabled_locking_shift_tables);
        if (sEnabledSingleShiftTables.length > 0) {
            sHighestEnabledSingleShiftCode =
                    sEnabledSingleShiftTables[sEnabledSingleShiftTables.length - 1];
        } else {
            sHighestEnabledSingleShiftCode = 0;
        }
    }

    static {
        enableCountrySpecificEncodings();
        int numTables = sLanguageTables.length;
        int numShiftTables = sLanguageShiftTables.length;
        if (numTables != numShiftTables) {
            Log.e(
                    TAG,
                    "Error: language tables array length "
                            + numTables
                            + " != shift tables array length "
                            + numShiftTables);
        }
        sCharsToGsmTables = new SparseIntArray[numTables];
        for (int i = 0; i < numTables; i++) {
            String table = sLanguageTables[i];
            int tableLen = table.length();
            if (tableLen != 0 && tableLen != 128) {
                Log.e(
                        TAG,
                        "Error: language tables index "
                                + i
                                + " length "
                                + tableLen
                                + " (expected 128 or 0)");
            }
            SparseIntArray charToGsmTable = new SparseIntArray(tableLen);
            sCharsToGsmTables[i] = charToGsmTable;
            for (int j = 0; j < tableLen; j++) {
                charToGsmTable.put(table.charAt(j), j);
            }
        }
        sCharsToShiftTables = new SparseIntArray[numShiftTables];
        for (int i2 = 0; i2 < numShiftTables; i2++) {
            String shiftTable = sLanguageShiftTables[i2];
            int shiftTableLen = shiftTable.length();
            if (shiftTableLen != 0 && shiftTableLen != 128) {
                Log.e(
                        TAG,
                        "Error: language shift tables index "
                                + i2
                                + " length "
                                + shiftTableLen
                                + " (expected 128 or 0)");
            }
            SparseIntArray charToShiftTable = new SparseIntArray(shiftTableLen);
            sCharsToShiftTables[i2] = charToShiftTable;
            for (int j2 = 0; j2 < shiftTableLen; j2++) {
                char c = shiftTable.charAt(j2);
                if (c != ' ') {
                    charToShiftTable.put(c, j2);
                }
            }
        }
    }

    private static class LanguagePairCount {
        final int languageCode;
        final int[] septetCounts;
        final int[] unencodableCounts;

        LanguagePairCount(int code) {
            this.languageCode = code;
            int maxSingleShiftCode = GsmAlphabet.sHighestEnabledSingleShiftCode;
            this.septetCounts = new int[maxSingleShiftCode + 1];
            this.unencodableCounts = new int[maxSingleShiftCode + 1];
            int tableOffset = 0;
            for (int i = 1; i <= maxSingleShiftCode; i++) {
                if (GsmAlphabet.sEnabledSingleShiftTables[tableOffset] == i) {
                    tableOffset++;
                } else {
                    this.septetCounts[i] = -1;
                }
            }
            if (code == 1 && maxSingleShiftCode >= 1) {
                this.septetCounts[1] = -1;
            } else if (code == 3 && maxSingleShiftCode >= 2) {
                this.septetCounts[2] = -1;
            }
        }
    }

    public static TextEncodingDetails countGsmSeptetsWithEmail(
            CharSequence s, boolean use7bitOnly, int maxEmailLen) {
        int udhLength;
        int septetsPerMessage;
        int septetsRemaining;
        Log.d(
                TAG,
                "sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0: "
                        + (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length
                                == 0));
        int i = -1;
        if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0) {
            TextEncodingDetails ted = new TextEncodingDetails();
            int septets = countGsmSeptetsUsingTables(s, use7bitOnly, 0, 0);
            if (septets == -1) {
                return null;
            }
            int maxLenPerSMS = maxEmailLen > 0 ? (160 - maxEmailLen) - 1 : 160;
            int maxLenPerSMSWithHeader = maxEmailLen > 0 ? (153 - maxEmailLen) - 1 : 153;
            if (septets != -1 && septets <= maxLenPerSMS) {
                ted.msgCount = 1;
                ted.codeUnitCount = septets;
                ted.codeUnitsRemaining = maxLenPerSMS - septets;
                ted.codeUnitSize = 1;
            } else if (septets != -1) {
                ted.codeUnitCount = septets;
                if (septets > maxLenPerSMS) {
                    ted.msgCount =
                            ((maxLenPerSMSWithHeader - 1) + septets) / maxLenPerSMSWithHeader;
                    if (septets % maxLenPerSMSWithHeader > 0) {
                        ted.codeUnitsRemaining =
                                maxLenPerSMSWithHeader - (septets % maxLenPerSMSWithHeader);
                    } else {
                        ted.codeUnitsRemaining = 0;
                    }
                } else {
                    ted.msgCount = 1;
                    ted.codeUnitsRemaining = maxLenPerSMS - septets;
                }
                ted.codeUnitSize = 1;
            }
            return ted;
        }
        int maxSingleShiftCode = sHighestEnabledSingleShiftCode;
        List<LanguagePairCount> lpcList = new ArrayList<>(sEnabledLockingShiftTables.length + 1);
        lpcList.add(new LanguagePairCount(0));
        for (int i2 : sEnabledLockingShiftTables) {
            if (i2 != 0 && !sLanguageTables[i2].isEmpty()) {
                lpcList.add(new LanguagePairCount(i2));
            }
        }
        int sz = s.length();
        int i3 = 0;
        while (i3 < sz && !lpcList.isEmpty()) {
            char c = s.charAt(i3);
            if (c == 27) {
                Log.d(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
            } else {
                for (LanguagePairCount lpc : lpcList) {
                    int tableIndex = sCharsToGsmTables[lpc.languageCode].get(c, i);
                    if (tableIndex == i) {
                        int table = 0;
                        while (table <= maxSingleShiftCode) {
                            if (lpc.septetCounts[table] != i) {
                                int shiftTableIndex = sCharsToShiftTables[table].get(c, i);
                                if (shiftTableIndex == i) {
                                    if (use7bitOnly) {
                                        int[] iArr = lpc.septetCounts;
                                        iArr[table] = iArr[table] + 1;
                                        int[] iArr2 = lpc.unencodableCounts;
                                        iArr2[table] = iArr2[table] + 1;
                                    } else {
                                        lpc.septetCounts[table] = -1;
                                    }
                                } else {
                                    int[] iArr3 = lpc.septetCounts;
                                    iArr3[table] = iArr3[table] + 2;
                                }
                            }
                            table++;
                            i = -1;
                        }
                    } else {
                        for (int table2 = 0; table2 <= maxSingleShiftCode; table2++) {
                            if (lpc.septetCounts[table2] != -1) {
                                int[] iArr4 = lpc.septetCounts;
                                iArr4[table2] = iArr4[table2] + 1;
                            }
                        }
                    }
                    i = -1;
                }
            }
            i3++;
            i = -1;
        }
        TextEncodingDetails ted2 = new TextEncodingDetails();
        ted2.msgCount = Integer.MAX_VALUE;
        ted2.codeUnitSize = 1;
        int minUnencodableCount = Integer.MAX_VALUE;
        for (LanguagePairCount lpc2 : lpcList) {
            for (int shiftTable = 0; shiftTable <= maxSingleShiftCode; shiftTable++) {
                int septets2 = lpc2.septetCounts[shiftTable];
                if (septets2 != -1) {
                    if (lpc2.languageCode != 0 && shiftTable != 0) {
                        udhLength = 8;
                    } else {
                        int udhLength2 = lpc2.languageCode;
                        if (udhLength2 != 0 || shiftTable != 0) {
                            udhLength = 5;
                        } else {
                            udhLength = 0;
                        }
                    }
                    if (septets2 + udhLength > 160) {
                        if (udhLength == 0) {
                            udhLength = 1;
                        }
                        int septetsPerMessage2 = 160 - (udhLength + 6);
                        int msgCount = ((septets2 + septetsPerMessage2) - 1) / septetsPerMessage2;
                        int septetsRemaining2 = (msgCount * septetsPerMessage2) - septets2;
                        septetsPerMessage = msgCount;
                        septetsRemaining = septetsRemaining2;
                    } else {
                        septetsPerMessage = 1;
                        septetsRemaining = (160 - udhLength) - septets2;
                    }
                    int unencodableCount = lpc2.unencodableCounts[shiftTable];
                    if ((!use7bitOnly || unencodableCount <= minUnencodableCount)
                            && ((use7bitOnly && unencodableCount < minUnencodableCount)
                                    || septetsPerMessage < ted2.msgCount
                                    || (septetsPerMessage == ted2.msgCount
                                            && septetsRemaining > ted2.codeUnitsRemaining))) {
                        minUnencodableCount = unencodableCount;
                        ted2.msgCount = septetsPerMessage;
                        ted2.codeUnitCount = septets2;
                        ted2.codeUnitsRemaining = septetsRemaining;
                        ted2.languageTable = lpc2.languageCode;
                        ted2.languageShiftTable = shiftTable;
                    }
                }
            }
        }
        if (ted2.msgCount == Integer.MAX_VALUE) {
            return null;
        }
        return ted2;
    }

    public static TextEncodingDetails countGsmSeptets(
            CharSequence s, boolean use7bitOnly, boolean ignoreSpecialChar) {
        sEnableIgnoreSpecialChar = ignoreSpecialChar;
        TextEncodingDetails ted = countGsmSeptets(s, use7bitOnly);
        sEnableIgnoreSpecialChar = false;
        return ted;
    }

    public static byte[] stringToGsm8BitPackedForAutoLogin(String msg) {
        int msgLen = msg.length();
        if (msgLen < 5) {
            return null;
        }
        byte[] ret = new byte[msgLen + 1];
        ret[1] = (byte) msg.charAt(0);
        ret[2] = (byte) msg.charAt(1);
        ret[3] = (byte) msg.charAt(2);
        ret[4] = (byte) msg.charAt(3);
        stringToGsm8BitUnpackedField(msg.substring(4), ret, 5, ret.length - 5);
        ret[0] = (byte) msgLen;
        return ret;
    }
}
