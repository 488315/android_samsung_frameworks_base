package com.android.internal.telephony;

import android.content.res.Resources;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.C4337R;
import com.android.internal.logging.nano.MetricsProto;
import com.google.android.mms.pdu.CharacterSets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class GsmAlphabet {
  public static final byte GSM_EXTENDED_ESCAPE = 27;
  private static final String TAG = "GSM";
  public static final int UDH_SEPTET_COST_CONCATENATED_MESSAGE = 6;
  public static final int UDH_SEPTET_COST_LENGTH = 1;
  public static final int UDH_SEPTET_COST_ONE_SHIFT_TABLE = 4;
  public static final int UDH_SEPTET_COST_TWO_SHIFT_TABLES = 7;
  private static final SparseIntArray charToGsm;
  private static final SparseIntArray charToGsmExtended;
  private static final SparseIntArray gsmExtendedToChar;
  private static final SparseIntArray gsmToChar;
  private static final SparseIntArray[] sCharsToGsmTables;
  private static final SparseIntArray[] sCharsToShiftTables;
  private static boolean sDisableCountryEncodingCheck = false;
  private static boolean sEnableIgnoreSpecialChar = false;
  private static int[] sEnabledLockingShiftTables;
  private static int[] sEnabledSingleShiftTables;
  private static int sGsmSpaceChar;
  private static int sHighestEnabledSingleShiftCode;
  private static final String[] sLanguageShiftTables;
  private static final String[] sLanguageTables;

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
    SparseIntArray[] sparseIntArrayArr = sCharsToGsmTables;
    int ret = sparseIntArrayArr[0].get(c, -1);
    if (ret == -1) {
      if (sCharsToShiftTables[0].get(c, -1) == -1) {
        if (throwException) {
          throw new EncodeException(c);
        }
        return sparseIntArrayArr[0].get(32, 32);
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

  public static byte[] stringToGsm7BitPacked(String data, int languageTable, int languageShiftTable)
      throws EncodeException {
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
        countGsmSeptetsUsingTables(data, !throwException, languageTable, languageShiftTable);
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
      Log.m102w(TAG, "unknown language table " + languageTable + ", using default");
      languageTable2 = 0;
    } else {
      languageTable2 = languageTable;
    }
    if (shiftTable2 < 0 || shiftTable2 > sLanguageShiftTables.length) {
      Log.m102w(TAG, "unknown single shift table " + shiftTable2 + ", using default");
      shiftTable2 = 0;
    }
    boolean prevCharWasEscape = false;
    try {
      String[] strArr = sLanguageTables;
      String languageTableToChar = strArr[languageTable2];
      String[] strArr2 = sLanguageShiftTables;
      String shiftTableToChar = strArr2[shiftTable2];
      if (languageTableToChar.isEmpty()) {
        Log.m102w(TAG, "no language table for code " + languageTable2 + ", using default");
        languageTableToChar = strArr[0];
      }
      if (shiftTableToChar.isEmpty()) {
        Log.m102w(TAG, "no single shift table for code " + shiftTable2 + ", using default");
        shiftTableToChar = strArr2[0];
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
      Log.m97e(TAG, "Error GSM 7 bit packed: ", ex);
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
          char shiftChar = c < shiftTableToChar.length() ? shiftTableToChar.charAt(c) : ' ';
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
  */
  public static void stringToGsm8BitUnpackedField(String s, byte[] dest, int offset, int length) {
    int outByteIndex = offset;
    SparseIntArray charToLanguageTable = sCharsToGsmTables[0];
    SparseIntArray charToShiftTable = sCharsToShiftTables[0];
    int i = 0;
    int sz = s.length();
    while (i < sz && outByteIndex - offset < length) {
      char c = s.charAt(i);
      int v = charToLanguageTable.get(c, -1);
      if (v == -1) {
        v = charToShiftTable.get(c, -1);
        if (v == -1) {
          v = charToLanguageTable.get(32, 32);
        } else {
          if ((outByteIndex + 1) - offset >= length) {
            break;
          }
          dest[outByteIndex] = 27;
          outByteIndex++;
        }
      }
      dest[outByteIndex] = (byte) v;
      i++;
      outByteIndex++;
    }
    while (i < length) {
      dest[outByteIndex] = -1;
      outByteIndex++;
    }
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
        Log.m102w(TAG, "countGsmSeptets() string contains Escape character, skipping.");
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
        Log.m102w(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
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
    sEnabledSingleShiftTables = r.getIntArray(C4337R.array.config_sms_enabled_single_shift_tables);
    sEnabledLockingShiftTables =
        r.getIntArray(C4337R.array.config_sms_enabled_locking_shift_tables);
    int[] iArr = sEnabledSingleShiftTables;
    if (iArr.length > 0) {
      sHighestEnabledSingleShiftCode = iArr[iArr.length - 1];
    } else {
      sHighestEnabledSingleShiftCode = 0;
    }
  }

  static {
    String[] strArr = {
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
    sLanguageTables = strArr;
    String[] strArr2 = {
      "          \f         ^                   {}     \\            [~] |                         "
          + "           €                          ",
      "          \f         ^                   {}     \\            [~] |      Ğ İ         Ş      "
          + "         ç € ğ ı         ş            ",
      "         ç\f         ^                   {}     \\            [~] |Á       Í     Ó     Ú    "
          + "       á   €   í     ó     ú          ",
      "     ê   ç\fÔô Áá  ΦΓ^ΩΠΨΣΘ     Ê        {}     \\            [~] |À       Í     Ó     Ú    "
          + " ÃÕ    Â   €   í     ó     ú     ãõ  â",
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
          + " ۰۱۲۳۴۵۶۷۸۹،؍{}؎؏ؐؑؒ\\ؓؔ؛؟ـْ٘٫٬ٲٳۍ[~]۔|ABCDEFGHIJKLMNOPQRSTUVWXYZ          €          "
          + "                "
    };
    sLanguageShiftTables = strArr2;
    enableCountrySpecificEncodings();
    int numTables = strArr.length;
    int numShiftTables = strArr2.length;
    if (numTables != numShiftTables) {
      Log.m96e(
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
        Log.m96e(
            TAG,
            "Error: language tables index " + i + " length " + tableLen + " (expected 128 or 0)");
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
        Log.m96e(
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
    SparseIntArray sparseIntArray = new SparseIntArray();
    charToGsm = sparseIntArray;
    gsmToChar = new SparseIntArray();
    SparseIntArray sparseIntArray2 = new SparseIntArray();
    charToGsmExtended = sparseIntArray2;
    gsmExtendedToChar = new SparseIntArray();
    int i3 = 0 + 1;
    sparseIntArray.put(64, 0);
    int i4 = i3 + 1;
    sparseIntArray.put(163, i3);
    int i5 = i4 + 1;
    sparseIntArray.put(36, i4);
    int i6 = i5 + 1;
    sparseIntArray.put(165, i5);
    int i7 = i6 + 1;
    sparseIntArray.put(232, i6);
    int i8 = i7 + 1;
    sparseIntArray.put(233, i7);
    int i9 = i8 + 1;
    sparseIntArray.put(249, i8);
    int i10 = i9 + 1;
    sparseIntArray.put(236, i9);
    int i11 = i10 + 1;
    sparseIntArray.put(242, i10);
    int i12 = i11 + 1;
    sparseIntArray.put(199, i11);
    int i13 = i12 + 1;
    sparseIntArray.put(10, i12);
    int i14 = i13 + 1;
    sparseIntArray.put(216, i13);
    int i15 = i14 + 1;
    sparseIntArray.put(248, i14);
    int i16 = i15 + 1;
    sparseIntArray.put(13, i15);
    int i17 = i16 + 1;
    sparseIntArray.put(197, i16);
    int i18 = i17 + 1;
    sparseIntArray.put(229, i17);
    int i19 = i18 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.AUTOFILL_SAVE_UI, i18);
    int i20 = i19 + 1;
    sparseIntArray.put(95, i19);
    int i21 = i20 + 1;
    sparseIntArray.put(934, i20);
    int i22 = i21 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, i21);
    int i23 = i22 + 1;
    sparseIntArray.put(923, i22);
    int i24 = i23 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.ACTION_TEXT_SELECTION_MENU_ITEM_ASSIST, i23);
    int i25 = i24 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.FIELD_QS_VALUE, i24);
    int i26 = i25 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.TEXT_SELECTION_MENU_ITEM_ASSIST, i25);
    int i27 = i26 + 1;
    sparseIntArray.put(931, i26);
    int i28 = i27 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.METRICS_CHECKPOINT, i27);
    int i29 = i28 + 1;
    sparseIntArray.put(MetricsProto.MetricsEvent.ACTION_QS_SECONDARY_CLICK, i28);
    int i30 = i29 + 1;
    sparseIntArray.put(65535, i29);
    int i31 = i30 + 1;
    sparseIntArray.put(198, i30);
    int i32 = i31 + 1;
    sparseIntArray.put(230, i31);
    int i33 = i32 + 1;
    sparseIntArray.put(223, i32);
    int i34 = i33 + 1;
    sparseIntArray.put(201, i33);
    int i35 = i34 + 1;
    sparseIntArray.put(32, i34);
    int i36 = i35 + 1;
    sparseIntArray.put(33, i35);
    int i37 = i36 + 1;
    sparseIntArray.put(34, i36);
    int i38 = i37 + 1;
    sparseIntArray.put(35, i37);
    int i39 = i38 + 1;
    sparseIntArray.put(164, i38);
    int i40 = i39 + 1;
    sparseIntArray.put(37, i39);
    int i41 = i40 + 1;
    sparseIntArray.put(38, i40);
    int i42 = i41 + 1;
    sparseIntArray.put(39, i41);
    int i43 = i42 + 1;
    sparseIntArray.put(40, i42);
    int i44 = i43 + 1;
    sparseIntArray.put(41, i43);
    int i45 = i44 + 1;
    sparseIntArray.put(42, i44);
    int i46 = i45 + 1;
    sparseIntArray.put(43, i45);
    int i47 = i46 + 1;
    sparseIntArray.put(44, i46);
    int i48 = i47 + 1;
    sparseIntArray.put(45, i47);
    int i49 = i48 + 1;
    sparseIntArray.put(46, i48);
    int i50 = i49 + 1;
    sparseIntArray.put(47, i49);
    int i51 = i50 + 1;
    sparseIntArray.put(48, i50);
    int i52 = i51 + 1;
    sparseIntArray.put(49, i51);
    int i53 = i52 + 1;
    sparseIntArray.put(50, i52);
    int i54 = i53 + 1;
    sparseIntArray.put(51, i53);
    int i55 = i54 + 1;
    sparseIntArray.put(52, i54);
    int i56 = i55 + 1;
    sparseIntArray.put(53, i55);
    int i57 = i56 + 1;
    sparseIntArray.put(54, i56);
    int i58 = i57 + 1;
    sparseIntArray.put(55, i57);
    int i59 = i58 + 1;
    sparseIntArray.put(56, i58);
    int i60 = i59 + 1;
    sparseIntArray.put(57, i59);
    int i61 = i60 + 1;
    sparseIntArray.put(58, i60);
    int i62 = i61 + 1;
    sparseIntArray.put(59, i61);
    int i63 = i62 + 1;
    sparseIntArray.put(60, i62);
    int i64 = i63 + 1;
    sparseIntArray.put(61, i63);
    int i65 = i64 + 1;
    sparseIntArray.put(62, i64);
    int i66 = i65 + 1;
    sparseIntArray.put(63, i65);
    int i67 = i66 + 1;
    sparseIntArray.put(161, i66);
    int i68 = i67 + 1;
    sparseIntArray.put(65, i67);
    int i69 = i68 + 1;
    sparseIntArray.put(66, i68);
    int i70 = i69 + 1;
    sparseIntArray.put(67, i69);
    int i71 = i70 + 1;
    sparseIntArray.put(68, i70);
    int i72 = i71 + 1;
    sparseIntArray.put(69, i71);
    int i73 = i72 + 1;
    sparseIntArray.put(70, i72);
    int i74 = i73 + 1;
    sparseIntArray.put(71, i73);
    int i75 = i74 + 1;
    sparseIntArray.put(72, i74);
    int i76 = i75 + 1;
    sparseIntArray.put(73, i75);
    int i77 = i76 + 1;
    sparseIntArray.put(74, i76);
    int i78 = i77 + 1;
    sparseIntArray.put(75, i77);
    int i79 = i78 + 1;
    sparseIntArray.put(76, i78);
    int i80 = i79 + 1;
    sparseIntArray.put(77, i79);
    int i81 = i80 + 1;
    sparseIntArray.put(78, i80);
    int i82 = i81 + 1;
    sparseIntArray.put(79, i81);
    int i83 = i82 + 1;
    sparseIntArray.put(80, i82);
    int i84 = i83 + 1;
    sparseIntArray.put(81, i83);
    int i85 = i84 + 1;
    sparseIntArray.put(82, i84);
    int i86 = i85 + 1;
    sparseIntArray.put(83, i85);
    int i87 = i86 + 1;
    sparseIntArray.put(84, i86);
    int i88 = i87 + 1;
    sparseIntArray.put(85, i87);
    int i89 = i88 + 1;
    sparseIntArray.put(86, i88);
    int i90 = i89 + 1;
    sparseIntArray.put(87, i89);
    int i91 = i90 + 1;
    sparseIntArray.put(88, i90);
    int i92 = i91 + 1;
    sparseIntArray.put(89, i91);
    int i93 = i92 + 1;
    sparseIntArray.put(90, i92);
    int i94 = i93 + 1;
    sparseIntArray.put(196, i93);
    int i95 = i94 + 1;
    sparseIntArray.put(214, i94);
    int i96 = i95 + 1;
    sparseIntArray.put(209, i95);
    int i97 = i96 + 1;
    sparseIntArray.put(220, i96);
    int i98 = i97 + 1;
    sparseIntArray.put(167, i97);
    int i99 = i98 + 1;
    sparseIntArray.put(191, i98);
    int i100 = i99 + 1;
    sparseIntArray.put(97, i99);
    int i101 = i100 + 1;
    sparseIntArray.put(98, i100);
    int i102 = i101 + 1;
    sparseIntArray.put(99, i101);
    int i103 = i102 + 1;
    sparseIntArray.put(100, i102);
    int i104 = i103 + 1;
    sparseIntArray.put(101, i103);
    int i105 = i104 + 1;
    sparseIntArray.put(102, i104);
    int i106 = i105 + 1;
    sparseIntArray.put(103, i105);
    int i107 = i106 + 1;
    sparseIntArray.put(104, i106);
    int i108 = i107 + 1;
    sparseIntArray.put(105, i107);
    int i109 = i108 + 1;
    sparseIntArray.put(106, i108);
    int i110 = i109 + 1;
    sparseIntArray.put(107, i109);
    int i111 = i110 + 1;
    sparseIntArray.put(108, i110);
    int i112 = i111 + 1;
    sparseIntArray.put(109, i111);
    int i113 = i112 + 1;
    sparseIntArray.put(110, i112);
    int i114 = i113 + 1;
    sparseIntArray.put(111, i113);
    int i115 = i114 + 1;
    sparseIntArray.put(112, i114);
    int i116 = i115 + 1;
    sparseIntArray.put(113, i115);
    int i117 = i116 + 1;
    sparseIntArray.put(114, i116);
    int i118 = i117 + 1;
    sparseIntArray.put(115, i117);
    int i119 = i118 + 1;
    sparseIntArray.put(116, i118);
    int i120 = i119 + 1;
    sparseIntArray.put(117, i119);
    int i121 = i120 + 1;
    sparseIntArray.put(118, i120);
    int i122 = i121 + 1;
    sparseIntArray.put(119, i121);
    int i123 = i122 + 1;
    sparseIntArray.put(120, i122);
    int i124 = i123 + 1;
    sparseIntArray.put(121, i123);
    int i125 = i124 + 1;
    sparseIntArray.put(122, i124);
    int i126 = i125 + 1;
    sparseIntArray.put(228, i125);
    int i127 = i126 + 1;
    sparseIntArray.put(246, i126);
    int i128 = i127 + 1;
    sparseIntArray.put(241, i127);
    int i129 = i128 + 1;
    sparseIntArray.put(252, i128);
    int i130 = i129 + 1;
    sparseIntArray.put(224, i129);
    sparseIntArray2.put(12, 10);
    sparseIntArray2.put(94, 20);
    sparseIntArray2.put(123, 40);
    sparseIntArray2.put(125, 41);
    sparseIntArray2.put(92, 47);
    sparseIntArray2.put(91, 60);
    sparseIntArray2.put(126, 61);
    sparseIntArray2.put(93, 62);
    sparseIntArray2.put(124, 64);
    sparseIntArray2.put(8364, 101);
    int size = sparseIntArray.size();
    for (int j3 = 0; j3 < size; j3++) {
      SparseIntArray sparseIntArray3 = gsmToChar;
      SparseIntArray sparseIntArray4 = charToGsm;
      sparseIntArray3.put(sparseIntArray4.valueAt(j3), sparseIntArray4.keyAt(j3));
    }
    int size2 = charToGsmExtended.size();
    for (int j4 = 0; j4 < size2; j4++) {
      SparseIntArray sparseIntArray5 = gsmExtendedToChar;
      SparseIntArray sparseIntArray6 = charToGsmExtended;
      sparseIntArray5.put(sparseIntArray6.valueAt(j4), sparseIntArray6.keyAt(j4));
    }
    sGsmSpaceChar = charToGsm.get(32);
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

  public static char convertEachCharacter(char c) {
    if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length != 0) {
      return c;
    }
    if (charToGsm.get(c, -1) != -1) {
      return c;
    }
    if (charToGsmExtended.get(c, -1) != -1) {
      return c;
    }
    char ret = convertNonGSMCharacter(c);
    return ret;
  }

  private static char convertNonGSMCharacter(char c) {
    char temp = c;
    System.out.println("temp char :" + ((int) c));
    switch (temp) {
      case 192:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 193:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 194:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 195:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 200:
        temp = DateFormat.DAY;
        break;
      case 202:
        temp = DateFormat.DAY;
        break;
      case 203:
        temp = DateFormat.DAY;
        break;
      case 204:
        temp = 'I';
        break;
      case 205:
        temp = 'I';
        break;
      case 206:
        temp = 'I';
        break;
      case 207:
        temp = 'I';
        break;
      case 210:
        temp = 'O';
        break;
      case 211:
        temp = 'O';
        break;
      case 212:
        temp = 'O';
        break;
      case 213:
        temp = 'O';
        break;
      case 217:
        temp = 'U';
        break;
      case 218:
        temp = 'U';
        break;
      case 219:
        temp = 'U';
        break;
      case 221:
        temp = 'Y';
        break;
      case 225:
        temp = DateFormat.AM_PM;
        break;
      case 226:
        temp = DateFormat.AM_PM;
        break;
      case 227:
        temp = DateFormat.AM_PM;
        break;
      case 231:
        temp = 'c';
        break;
      case 233:
        temp = 'e';
        break;
      case 234:
        temp = 'e';
        break;
      case 235:
        temp = 'e';
        break;
      case 237:
        temp = 'i';
        break;
      case 238:
        temp = 'i';
        break;
      case 239:
        temp = 'i';
        break;
      case 243:
        temp = 'o';
        break;
      case 244:
        temp = 'o';
        break;
      case 245:
        temp = 'o';
        break;
      case 246:
        temp = 'o';
        break;
      case 250:
        temp = 'u';
        break;
      case 251:
        temp = 'u';
        break;
      case 252:
        temp = 'u';
        break;
      case 253:
        temp = 'y';
        break;
      case 255:
        temp = 'y';
        break;
      case 256:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 257:
        temp = DateFormat.AM_PM;
        break;
      case 260:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case 261:
        temp = DateFormat.AM_PM;
        break;
      case 262:
        temp = 'C';
        break;
      case 263:
        temp = 'c';
        break;
      case 268:
        temp = 'C';
        break;
      case 269:
        temp = 'c';
        break;
      case 270:
        temp = 'D';
        break;
      case 271:
        temp = DateFormat.DATE;
        break;
      case 274:
        temp = DateFormat.DAY;
        break;
      case 275:
        temp = 'e';
        break;
      case 280:
        temp = DateFormat.DAY;
        break;
      case 281:
        temp = 'e';
        break;
      case 282:
        temp = DateFormat.DAY;
        break;
      case 283:
        temp = 'e';
        break;
      case 286:
        temp = 'G';
        break;
      case 287:
        temp = 'g';
        break;
      case 298:
        temp = 'I';
        break;
      case 299:
        temp = 'i';
        break;
      case 304:
        temp = 'I';
        break;
      case 305:
        temp = 'i';
        break;
      case 313:
        temp = DateFormat.STANDALONE_MONTH;
        break;
      case 314:
        temp = 'l';
        break;
      case 317:
        temp = DateFormat.STANDALONE_MONTH;
        break;
      case 318:
        temp = 'l';
        break;
      case 321:
        temp = DateFormat.STANDALONE_MONTH;
        break;
      case 322:
        temp = 'l';
        break;
      case 323:
        temp = PhoneNumberUtils.WILD;
        break;
      case 324:
        temp = 'n';
        break;
      case 327:
        temp = PhoneNumberUtils.WILD;
        break;
      case 328:
        temp = 'n';
        break;
      case 332:
        temp = 'O';
        break;
      case 333:
        temp = 'o';
        break;
      case 336:
        temp = 'O';
        break;
      case 337:
        temp = 'o';
        break;
      case 338:
        temp = 'O';
        break;
      case 339:
        temp = 'o';
        break;
      case 340:
        temp = 'R';
        break;
      case 341:
        temp = 'r';
        break;
      case 344:
        temp = 'R';
        break;
      case 345:
        temp = 'r';
        break;
      case 346:
        temp = 'S';
        break;
      case 347:
        temp = 's';
        break;
      case 350:
        temp = 'S';
        break;
      case 351:
        temp = 's';
        break;
      case 352:
        temp = 'S';
        break;
      case 353:
        temp = 's';
        break;
      case 356:
        temp = 'T';
        break;
      case 357:
        temp = 't';
        break;
      case 362:
        temp = 'U';
        break;
      case 363:
        temp = 'u';
        break;
      case 366:
        temp = 'U';
        break;
      case 367:
        temp = 'u';
        break;
      case 368:
        temp = 'U';
        break;
      case 369:
        temp = 'u';
        break;
      case 376:
        temp = 'Y';
        break;
      case 377:
        temp = 'Z';
        break;
      case 378:
        temp = DateFormat.TIME_ZONE;
        break;
      case 379:
        temp = 'Z';
        break;
      case 380:
        temp = DateFormat.TIME_ZONE;
        break;
      case 381:
        temp = 'Z';
        break;
      case 382:
        temp = DateFormat.TIME_ZONE;
        break;
      case MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED /* 913 */:
        temp = DateFormat.CAPITAL_AM_PM;
        break;
      case MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES /* 914 */:
        temp = 'B';
        break;
      case MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_IDS /* 917 */:
        temp = DateFormat.DAY;
        break;
      case MetricsProto.MetricsEvent.AUTOFILL_DATA_SAVE_REQUEST /* 918 */:
        temp = 'Z';
        break;
      case MetricsProto.MetricsEvent.AUTOFILL_SESSION_FINISHED /* 919 */:
        temp = 'H';
        break;
      case 921:
        temp = 'I';
        break;
      case 922:
        temp = 'K';
        break;
      case 924:
        temp = DateFormat.MONTH;
        break;
      case MetricsProto.MetricsEvent.ACTION_QS_CLICK /* 925 */:
        temp = PhoneNumberUtils.WILD;
        break;
      case MetricsProto.MetricsEvent.FIELD_QS_POSITION /* 927 */:
        temp = 'O';
        break;
      case MetricsProto.MetricsEvent.ACTION_QS_MORE_SETTINGS /* 929 */:
        temp = 'P';
        break;
      case MetricsProto.MetricsEvent.FIELD_FLAGS /* 932 */:
        temp = 'T';
        break;
      case MetricsProto.MetricsEvent.FIELD_NAV_ACTION /* 933 */:
        temp = 'Y';
        break;
      case 935:
        temp = 'X';
        break;
      default:
        if (temp > 127 || temp == '`') {
          if (temp == 128) {
            temp = ' ';
            break;
          } else {
            temp = 65279;
            break;
          }
        }
        break;
    }
    System.out.println("temp char :" + temp);
    return temp;
  }

  public static TextEncodingDetails countGsmSeptetsWithEmail(
      CharSequence s, boolean use7bitOnly, int maxEmailLen) {
    int udhLength;
    int septetsPerMessage;
    int septetsRemaining;
    Log.m94d(
        TAG,
        "sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0: "
            + (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0));
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
          ted.msgCount = ((maxLenPerSMSWithHeader - 1) + septets) / maxLenPerSMSWithHeader;
          if (septets % maxLenPerSMSWithHeader > 0) {
            ted.codeUnitsRemaining = maxLenPerSMSWithHeader - (septets % maxLenPerSMSWithHeader);
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
        Log.m94d(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
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
