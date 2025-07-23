package android.telephony;

import android.app.settings.SettingsEnums;
import android.telephony.AccessNetworkConstants;
import android.view.KeyEvent;
import com.android.internal.logging.nano.MetricsProto;
import com.samsung.android.media.SemExtendedFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes4.dex */
public class AccessNetworkUtils {
    private static final int FREQUENCY_KHZ = 1000;
    private static final int FREQUENCY_RANGE_HIGH_KHZ = 6000000;
    private static final int FREQUENCY_RANGE_LOW_KHZ = 1000000;
    private static final int FREQUENCY_RANGE_MID_KHZ = 3000000;
    public static final int INVALID_BAND = -1;
    public static final int INVALID_FREQUENCY = -1;
    private static final String JAPAN_ISO_COUNTRY_CODE = "jp";
    private static final String TAG = "AccessNetworkUtils";
    private static final Set<Integer> UARFCN_NOT_GENERAL_BAND;

    private static int convertArfcnToFrequency(int i, int i2, int i3) {
        return i2 + ((i - i3) * 200);
    }

    private static int convertEarfcnToFrequency(int i, int i2, int i3) {
        return i + ((i2 - i3) * 100);
    }

    private static int convertUarfcnTddToFrequency(int i, int i2) {
        return i != 104 ? i2 * 5000 : ((i2 * 1000) - 2150100) * 5;
    }

    private static int convertUarfcnToFrequency(int i, int i2) {
        return i + (i2 * 200);
    }

    public static int getDuplexModeForEutranBand(int i) {
        if (i == -1 || i > 88) {
            return 0;
        }
        if (i >= 65) {
            return 1;
        }
        if (i >= 33) {
            return 2;
        }
        return i >= 1 ? 1 : 0;
    }

    public static int getFrequencyRangeFromArfcn(int i) {
        if (i < 1000000) {
            return 1;
        }
        if (i >= 3000000 || i < 1000000) {
            return (i >= FREQUENCY_RANGE_HIGH_KHZ || i < 3000000) ? 4 : 3;
        }
        return 2;
    }

    public static int getFrequencyRangeGroupFromEutranBand(int i) {
        if (i == 30) {
            return 2;
        }
        if (i == 31) {
            return 1;
        }
        if (i == 65 || i == 66) {
            return 2;
        }
        if (i == 68 || i == 85 || i == 87 || i == 88) {
            return 1;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 9:
            case 10:
            case 11:
                return 2;
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 14:
                return 1;
            default:
                switch (i) {
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 26:
                    case 27:
                    case 28:
                        return 1;
                    case 21:
                    case 23:
                    case 24:
                    case 25:
                        return 2;
                    case 22:
                        return 3;
                    default:
                        switch (i) {
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 45:
                            case 53:
                                return 2;
                            case 42:
                            case 43:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 52:
                                return 3;
                            case 44:
                            case 50:
                            case 51:
                                return 1;
                            default:
                                switch (i) {
                                    case 70:
                                    case 74:
                                        return 2;
                                    case 71:
                                    case 72:
                                    case 73:
                                        return 1;
                                    default:
                                        return 0;
                                }
                        }
                }
        }
    }

    public static int getFrequencyRangeGroupFromGeranBand(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
                return 1;
            case 12:
            case 13:
                return 2;
            default:
                return 0;
        }
    }

    public static int getFrequencyRangeGroupFromNrBand(int i) {
        if (i != 1 && i != 2 && i != 3 && i != 7) {
            if (i != 8) {
                if (i != 25) {
                    if (i != 26) {
                        if (i != 50 && i != 51 && i != 65 && i != 66 && i != 70) {
                            if (i != 71) {
                                switch (i) {
                                    case 5:
                                    case 12:
                                    case 14:
                                    case 18:
                                    case 20:
                                    case 81:
                                    case 82:
                                    case 83:
                                        break;
                                    case 34:
                                    case 53:
                                    case 74:
                                    case 75:
                                    case 76:
                                    case 80:
                                    case 84:
                                    case 86:
                                        break;
                                    case 46:
                                    case 48:
                                    case 77:
                                    case 78:
                                    case 79:
                                        return 3;
                                    case 257:
                                    case 258:
                                    case 260:
                                    case 261:
                                        return 4;
                                    default:
                                        switch (i) {
                                            case 28:
                                            case 29:
                                                break;
                                            case 30:
                                                break;
                                            default:
                                                switch (i) {
                                                    default:
                                                        switch (i) {
                                                            case 89:
                                                                break;
                                                            case 90:
                                                            case 91:
                                                            case 92:
                                                            case 93:
                                                            case 94:
                                                            case 95:
                                                                break;
                                                            case 96:
                                                                return 4;
                                                            default:
                                                                return 0;
                                                        }
                                                    case 38:
                                                    case 39:
                                                    case 40:
                                                    case 41:
                                                        return 2;
                                                }
                                        }
                                }
                            }
                        }
                    }
                }
            }
            return 1;
        }
        return 2;
    }

    public static int getFrequencyRangeGroupFromUtranBand(int i) {
        if (i == 25) {
            return 2;
        }
        if (i == 26) {
            return 1;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 9:
            case 10:
            case 11:
                return 2;
            case 5:
            case 6:
            case 8:
            case 12:
            case 13:
            case 14:
                return 1;
            default:
                switch (i) {
                    case 19:
                    case 20:
                        return 1;
                    case 21:
                        return 2;
                    case 22:
                        return 3;
                    default:
                        switch (i) {
                            case 101:
                            case 102:
                            case 103:
                            case 104:
                            case 105:
                            case 106:
                                return 2;
                            default:
                                return 0;
                        }
                }
        }
    }

    public static int getOperatingBandForArfcn(int i) {
        if (i >= 0 && i <= 124) {
            return 10;
        }
        if (i >= 128 && i <= 251) {
            return 8;
        }
        if (i >= 259 && i <= 293) {
            return 3;
        }
        if (i >= 306 && i <= 340) {
            return 4;
        }
        if (i >= 438 && i <= 511) {
            return 6;
        }
        if (i >= 512 && i <= 885) {
            return 12;
        }
        if (i < 940 || i > 974) {
            return (i < 975 || i > 1023) ? -1 : 10;
        }
        return 14;
    }

    public static int getOperatingBandForEarfcn(int i) {
        if (i > 70645) {
            return -1;
        }
        if (i >= 70596) {
            return 88;
        }
        if (i >= 70546) {
            return 87;
        }
        if (i >= 70366) {
            return 85;
        }
        if (i > 69465) {
            return -1;
        }
        if (i >= 69036) {
            return 74;
        }
        if (i >= 68986) {
            return 73;
        }
        if (i >= 68936) {
            return 72;
        }
        if (i >= 68586) {
            return 71;
        }
        if (i >= 68336) {
            return 70;
        }
        if (i > 67835) {
            return -1;
        }
        if (i >= 67536) {
            return 68;
        }
        if (i >= 67366) {
            return -1;
        }
        if (i >= 66436) {
            return 66;
        }
        if (i >= 65536) {
            return 65;
        }
        if (i > 60254) {
            return -1;
        }
        if (i >= 60140) {
            return 53;
        }
        if (i >= 59140) {
            return 52;
        }
        if (i >= 59090) {
            return 51;
        }
        if (i >= 58240) {
            return 50;
        }
        if (i >= 56740) {
            return 49;
        }
        if (i >= 55240) {
            return 48;
        }
        if (i >= 54540) {
            return 47;
        }
        if (i >= 46790) {
            return 46;
        }
        if (i >= 46590) {
            return 45;
        }
        if (i >= 45590) {
            return 44;
        }
        if (i >= 43590) {
            return 43;
        }
        if (i >= 41590) {
            return 42;
        }
        if (i >= 39650) {
            return 41;
        }
        if (i >= 38650) {
            return 40;
        }
        if (i >= 38250) {
            return 39;
        }
        if (i >= 37750) {
            return 38;
        }
        if (i >= 37550) {
            return 37;
        }
        if (i >= 36950) {
            return 36;
        }
        if (i >= 36350) {
            return 35;
        }
        if (i >= 36200) {
            return 34;
        }
        if (i >= 36000) {
            return 33;
        }
        if (i > 10359 || i >= 9920) {
            return -1;
        }
        if (i >= 9870) {
            return 31;
        }
        if (i >= 9770) {
            return 30;
        }
        if (i >= 9660) {
            return -1;
        }
        if (i >= 9210) {
            return 28;
        }
        if (i >= 9040) {
            return 27;
        }
        if (i >= 8690) {
            return 26;
        }
        if (i >= 8040) {
            return 25;
        }
        if (i >= 7700) {
            return 24;
        }
        if (i >= 7500) {
            return 23;
        }
        if (i >= 6600) {
            return 22;
        }
        if (i >= 6450) {
            return 21;
        }
        if (i >= 6150) {
            return 20;
        }
        if (i >= 6000) {
            return 19;
        }
        if (i >= 5850) {
            return 18;
        }
        if (i >= 5730) {
            return 17;
        }
        if (i > 5379) {
            return -1;
        }
        if (i >= 5280) {
            return 14;
        }
        if (i >= 5180) {
            return 13;
        }
        if (i >= 5010) {
            return 12;
        }
        if (i >= 4750) {
            return 11;
        }
        if (i >= 4150) {
            return 10;
        }
        if (i >= 3800) {
            return 9;
        }
        if (i >= 3450) {
            return 8;
        }
        if (i >= 2750) {
            return 7;
        }
        if (i >= 2650) {
            return 6;
        }
        if (i >= 2400) {
            return 5;
        }
        if (i >= 1950) {
            return 4;
        }
        if (i >= 1200) {
            return 3;
        }
        if (i >= 600) {
            return 2;
        }
        return i >= 0 ? 1 : -1;
    }

    public static int getOperatingBandForNrarfcn(int i) {
        if (i >= 422000 && i <= 434000) {
            return 1;
        }
        if (i >= 386000 && i <= 398000) {
            return 2;
        }
        if (i >= 361000 && i <= 376000) {
            return 3;
        }
        if (i >= 173800 && i <= 178800) {
            return 5;
        }
        if (i >= 524000 && i <= 538000) {
            return 7;
        }
        if (i >= 185000 && i <= 192000) {
            return 8;
        }
        if (i >= 145800 && i <= 149200) {
            return 12;
        }
        if (i >= 151600 && i <= 153600) {
            return 14;
        }
        if (i >= 172000 && i <= 175000) {
            return 18;
        }
        if (i >= 158200 && i <= 164200) {
            return 20;
        }
        if (i >= 386000 && i <= 399000) {
            return 25;
        }
        if (i >= 171800 && i <= 178800) {
            return 26;
        }
        if (i >= 151600 && i <= 160600) {
            return 28;
        }
        if (i >= 143400 && i <= 145600) {
            return 29;
        }
        if (i >= 470000 && i <= 472000) {
            return 30;
        }
        if (i >= 402000 && i <= 405000) {
            return 34;
        }
        if (i >= 514000 && i <= 524000) {
            return 38;
        }
        if (i >= 376000 && i <= 384000) {
            return 39;
        }
        if (i >= 460000 && i <= 480000) {
            return 40;
        }
        if (i >= 499200 && i <= 537999) {
            return 41;
        }
        if (i >= 743334 && i <= 795000) {
            return 46;
        }
        if (i >= 636667 && i <= 646666) {
            return 48;
        }
        if (i >= 286400 && i <= 303400) {
            return 50;
        }
        if (i >= 285400 && i <= 286400) {
            return 51;
        }
        if (i >= 496700 && i <= 499000) {
            return 53;
        }
        if (i >= 422000 && i <= 440000) {
            return 65;
        }
        if (i >= 399000 && i <= 404000) {
            return 70;
        }
        if (i >= 123400 && i <= 130400) {
            return 71;
        }
        if (i >= 295000 && i <= 303600) {
            return 74;
        }
        if (i >= 286400 && i <= 303400) {
            return 75;
        }
        if (i >= 285400 && i <= 286400) {
            return 76;
        }
        if (i >= 620000 && i <= 680000) {
            return 77;
        }
        if (i >= 620000 && i <= 653333) {
            return 78;
        }
        if (i >= 693334 && i <= 733333) {
            return 79;
        }
        if (i >= 499200 && i <= 538000) {
            return 90;
        }
        if (i >= 285400 && i <= 286400) {
            return 91;
        }
        if (i >= 286400 && i <= 303400) {
            return 92;
        }
        if (i >= 285400 && i <= 286400) {
            return 93;
        }
        if (i >= 286400 && i <= 303400) {
            return 94;
        }
        if (i >= 795000 && i <= 875000) {
            return 96;
        }
        if (i >= 2054166 && i <= 2104165) {
            return 257;
        }
        if (i >= 2016667 && i <= 2070832) {
            return 258;
        }
        if (i < 2229166 || i > 2279165) {
            return (i < 2070833 || i > 2084999) ? -1 : 261;
        }
        return 260;
    }

    private AccessNetworkUtils() {
    }

    static {
        HashSet hashSet = new HashSet();
        UARFCN_NOT_GENERAL_BAND = hashSet;
        hashSet.add(101);
        hashSet.add(102);
        hashSet.add(103);
        hashSet.add(104);
        hashSet.add(105);
        hashSet.add(106);
    }

    public static int getOperatingBandForUarfcn(int i) {
        int[] iArr = {412, 437, 462, 487, 512, MetricsProto.MetricsEvent.DIALOG_NO_HOME, 562, 587, MetricsProto.MetricsEvent.PROVISIONING_EXTRA, MetricsProto.MetricsEvent.ACTION_PERMISSION_REVOKE_READ_CALENDAR, MetricsProto.MetricsEvent.ACTION_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION, MetricsProto.MetricsEvent.ACTION_PERMISSION_GRANT_ADD_VOICEMAIL};
        int[] iArr2 = {1887, 1912, SettingsEnums.ACTION_AUDIO_SHARING_LEAVE_FAILED, SettingsEnums.ACTION_ROBUST_OPEN_CLOSE_DETECTION_CHANGED, 1987, 2012, 2037, 2062, 2087};
        int[] iArr3 = {1007, 1012, 1032, 1037, KeyEvent.KEYCODE_VOICE_WAKEUP, 1087};
        int[] iArr4 = {1037, KeyEvent.KEYCODE_VOICE_WAKEUP};
        int[] iArr5 = {2587, 2612, 2637, 2662, 2687, 2712, SemExtendedFormat.DataType.DUAL_SHOT_DEPTHMAP, 2762, 2787, 2812, 2837, 2862, 2887, 2912};
        int[] iArr6 = {3412, 3437, 3462, 3487, 3512, 3537, 3562, 3587, 3612, 3637, 3662, 3687};
        int[] iArr7 = {3932, 3957, 3962, 3987, 3992};
        int[] iArr8 = {4067, 4092};
        int[] iArr9 = {4167, 4192};
        int[] iArr10 = {787, 812, 837};
        int[] iArr11 = {6292, 6317, 6342, 6367, ServiceState.RIL_RADIO_CDMA_TECHNOLOGY_BITMASK, 6417, 6442, 6467, 6492, 6517, 6542, 6567, 6592};
        int[] iArr12 = {5937, 5962, 5987, 5992, 6012, 6017, 6037, 6042, 6062, 6067, 6087};
        if (i >= 10562 && i <= 10838) {
            return 1;
        }
        if ((i >= 9662 && i <= 9938) || Arrays.binarySearch(iArr, i) >= 0) {
            return 2;
        }
        if (i >= 1162 && i <= 1513) {
            return 3;
        }
        if ((i >= 1537 && i <= 1738) || Arrays.binarySearch(iArr2, i) >= 0) {
            return 4;
        }
        if (i >= 4387 && i <= 4413) {
            return JAPAN_ISO_COUNTRY_CODE.compareToIgnoreCase(TelephonyManager.getDefault().getNetworkCountryIso()) == 0 ? 6 : 5;
        }
        if ((i >= 4357 && i <= 4458) || Arrays.binarySearch(iArr3, i) >= 0) {
            return 5;
        }
        if (Arrays.binarySearch(iArr4, i) >= 0) {
            return 6;
        }
        if ((i >= 2237 && i <= 2563) || Arrays.binarySearch(iArr5, i) >= 0) {
            return 7;
        }
        if (i >= 2937 && i <= 3088) {
            return 8;
        }
        if (i >= 9237 && i <= 9387) {
            return 9;
        }
        if ((i >= 3112 && i <= 3388) || Arrays.binarySearch(iArr6, i) >= 0) {
            return 10;
        }
        if (i >= 3712 && i <= 3787) {
            return 11;
        }
        if ((i >= 3842 && i <= 3903) || Arrays.binarySearch(iArr7, i) >= 0) {
            return 12;
        }
        if ((i >= 4017 && i <= 4043) || Arrays.binarySearch(iArr8, i) >= 0) {
            return 13;
        }
        if ((i >= 4117 && i <= 4143) || Arrays.binarySearch(iArr9, i) >= 0) {
            return 14;
        }
        if ((i >= 712 && i <= 763) || Arrays.binarySearch(iArr10, i) >= 0) {
            return 19;
        }
        if (i >= 4512 && i <= 4638) {
            return 20;
        }
        if (i >= 862 && i <= 912) {
            return 21;
        }
        if (i >= 4662 && i <= 5038) {
            return 22;
        }
        if ((i < 5112 || i > 5413) && Arrays.binarySearch(iArr11, i) < 0) {
            return ((i < 5762 || i > 5913) && Arrays.binarySearch(iArr12, i) < 0) ? -1 : 26;
        }
        return 25;
    }

    public static int getFrequencyFromNrArfcn(int i) {
        int i2;
        int i3;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        AccessNetworkConstants.NgranArfcnFrequency[] values = AccessNetworkConstants.NgranArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i2 = 0;
                i3 = 0;
                break;
            }
            AccessNetworkConstants.NgranArfcnFrequency ngranArfcnFrequency = values[i5];
            if (i >= ngranArfcnFrequency.rangeFirst && i <= ngranArfcnFrequency.rangeLast) {
                int i6 = ngranArfcnFrequency.globalKhz;
                int i7 = ngranArfcnFrequency.rangeOffset;
                i3 = ngranArfcnFrequency.arfcnOffset;
                i4 = i7;
                i2 = i6;
                break;
            }
            i5++;
        }
        return i4 + (i2 * (i - i3));
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0052, code lost:
    
        return convertEarfcnToFrequency(r2, r7, r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getFrequencyFromEarfcn(int r6, int r7, boolean r8) {
        /*
            android.telephony.AccessNetworkConstants$EutranBandArfcnFrequency[] r0 = android.telephony.AccessNetworkConstants.EutranBandArfcnFrequency.values()
            int r1 = r0.length
            r2 = 0
            r3 = r2
        L7:
            if (r3 >= r1) goto L4d
            r4 = r0[r3]
            int r5 = r4.band
            if (r6 != r5) goto L4a
            boolean r0 = isInEarfcnRange(r7, r4, r8)
            if (r0 == 0) goto L25
            if (r8 == 0) goto L1a
            int r6 = r4.uplinkLowKhz
            goto L1c
        L1a:
            int r6 = r4.downlinkLowKhz
        L1c:
            r2 = r6
            if (r8 == 0) goto L22
            int r6 = r4.uplinkOffset
            goto L4e
        L22:
            int r6 = r4.downlinkOffset
            goto L4e
        L25:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Band and the range of EARFCN are not consistent: band = "
            r0.<init>(r1)
            r0.append(r6)
            java.lang.String r6 = " ,earfcn = "
            r0.append(r6)
            r0.append(r7)
            java.lang.String r6 = " ,isUplink = "
            r0.append(r6)
            r0.append(r8)
            java.lang.String r6 = r0.toString()
            java.lang.String r7 = "AccessNetworkUtils"
            android.telephony.Rlog.w(r7, r6)
            r6 = -1
            return r6
        L4a:
            int r3 = r3 + 1
            goto L7
        L4d:
            r6 = r2
        L4e:
            int r6 = convertEarfcnToFrequency(r2, r7, r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.AccessNetworkUtils.getFrequencyFromEarfcn(int, int, boolean):int");
    }

    private static boolean isInEarfcnRange(int i, AccessNetworkConstants.EutranBandArfcnFrequency eutranBandArfcnFrequency, boolean z) {
        return z ? i >= eutranBandArfcnFrequency.uplinkOffset && i <= eutranBandArfcnFrequency.uplinkRange : i >= eutranBandArfcnFrequency.downlinkOffset && i <= eutranBandArfcnFrequency.downlinkRange;
    }

    public static int getFrequencyFromUarfcn(int i, int i2, boolean z) {
        int i3;
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        AccessNetworkConstants.UtranBandArfcnFrequency[] values = AccessNetworkConstants.UtranBandArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                break;
            }
            AccessNetworkConstants.UtranBandArfcnFrequency utranBandArfcnFrequency = values[i5];
            if (i != utranBandArfcnFrequency.band) {
                i5++;
            } else {
                if (!isInUarfcnRange(i2, utranBandArfcnFrequency, z)) {
                    Rlog.w(TAG, "Band and the range of UARFCN are not consistent: band = " + i + " ,uarfcn = " + i2 + " ,isUplink = " + z);
                    return -1;
                }
                if (z) {
                    i3 = utranBandArfcnFrequency.uplinkOffset;
                } else {
                    i3 = utranBandArfcnFrequency.downlinkOffset;
                }
                i4 = i3;
            }
        }
        if (!UARFCN_NOT_GENERAL_BAND.contains(Integer.valueOf(i))) {
            return convertUarfcnToFrequency(i4, i2);
        }
        return convertUarfcnTddToFrequency(i, i2);
    }

    private static boolean isInUarfcnRange(int i, AccessNetworkConstants.UtranBandArfcnFrequency utranBandArfcnFrequency, boolean z) {
        if (z) {
            return i >= utranBandArfcnFrequency.uplinkRangeFirst && i <= utranBandArfcnFrequency.uplinkRangeLast;
        }
        if (utranBandArfcnFrequency.downlinkRangeFirst == 0 || utranBandArfcnFrequency.downlinkRangeLast == 0) {
            return true;
        }
        return i >= utranBandArfcnFrequency.downlinkRangeFirst && i <= utranBandArfcnFrequency.downlinkRangeLast;
    }

    public static int getFrequencyFromArfcn(int i, int i2, boolean z) {
        int i3;
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        AccessNetworkConstants.GeranBandArfcnFrequency[] values = AccessNetworkConstants.GeranBandArfcnFrequency.values();
        int length = values.length;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i5 >= length) {
                i3 = 0;
                break;
            }
            AccessNetworkConstants.GeranBandArfcnFrequency geranBandArfcnFrequency = values[i5];
            if (i != geranBandArfcnFrequency.band) {
                i5++;
            } else if (i2 >= geranBandArfcnFrequency.arfcnRangeFirst && i2 <= geranBandArfcnFrequency.arfcnRangeLast) {
                int i6 = geranBandArfcnFrequency.uplinkFrequencyFirst;
                int i7 = geranBandArfcnFrequency.downlinkOffset;
                i4 = convertArfcnToFrequency(i2, i6, geranBandArfcnFrequency.arfcnOffset);
                i3 = i7;
            } else {
                Rlog.w(TAG, "Band and the range of ARFCN are not consistent: band = " + i + " ,arfcn = " + i2 + " ,isUplink = " + z);
                return -1;
            }
        }
        return z ? i4 : i4 + i3;
    }
}
