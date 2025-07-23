package android.telephony;

import android.annotation.SystemApi;
import android.app.settings.SettingsEnums;
import android.mtp.MtpConstants;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Process;
import android.util.BoostFramework;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.telephony.gsm.SmsCbConstants;
import com.samsung.android.media.SemMediaPlayer;
import com.samsung.android.smartface.SmartFaceManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class AccessNetworkConstants {
    private static final String TAG = "AccessNetworkConstants";

    @SystemApi
    public static final int TRANSPORT_TYPE_INVALID = -1;
    public static final int TRANSPORT_TYPE_WLAN = 2;
    public static final int TRANSPORT_TYPE_WWAN = 1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RadioAccessNetworkType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransportType {
    }

    public static String transportTypeToString(int i) {
        if (i == -1) {
            return "INVALID";
        }
        if (i == 1) {
            return "WWAN";
        }
        if (i == 2) {
            return "WLAN";
        }
        return Integer.toString(i);
    }

    public static final class AccessNetworkType {
        public static final int CDMA2000 = 4;
        public static final int EUTRAN = 3;
        public static final int GERAN = 1;
        public static final int IWLAN = 5;
        public static final int NGRAN = 6;
        public static final int UNKNOWN = 0;
        public static final int UTRAN = 2;

        private AccessNetworkType() {
        }

        public static String toString(int i) {
            switch (i) {
                case 0:
                    return "UNKNOWN";
                case 1:
                    return "GERAN";
                case 2:
                    return "UTRAN";
                case 3:
                    return "EUTRAN";
                case 4:
                    return "CDMA2000";
                case 5:
                    return "IWLAN";
                case 6:
                    return "NGRAN";
                default:
                    return Integer.toString(i);
            }
        }

        public static int fromString(String str) {
            String upperCase = str.toUpperCase(Locale.ROOT);
            upperCase.hashCode();
            switch (upperCase) {
                case "CDMA2000":
                    return 4;
                case "GERAN":
                    return 1;
                case "IWLAN":
                    return 5;
                case "NGRAN":
                    return 6;
                case "UTRAN":
                    return 2;
                case "UNKNOWN":
                    return 0;
                case "EUTRAN":
                    return 3;
                default:
                    throw new IllegalArgumentException("Invalid access network type " + str);
            }
        }
    }

    public static final class GeranBand {
        public static final int BAND_450 = 3;
        public static final int BAND_480 = 4;
        public static final int BAND_710 = 5;
        public static final int BAND_750 = 6;
        public static final int BAND_850 = 8;
        public static final int BAND_DCS1800 = 12;
        public static final int BAND_E900 = 10;
        public static final int BAND_ER900 = 14;
        public static final int BAND_P900 = 9;
        public static final int BAND_PCS1900 = 13;
        public static final int BAND_R900 = 11;
        public static final int BAND_T380 = 1;
        public static final int BAND_T410 = 2;
        public static final int BAND_T810 = 7;

        @Retention(RetentionPolicy.SOURCE)
        public @interface GeranBands {
        }

        private GeranBand() {
        }
    }

    enum GeranBandArfcnFrequency {
        GERAN_ARFCN_FREQUENCY_BAND_450(3, 450600, 259, 259, 293, 10),
        GERAN_ARFCN_FREQUENCY_BAND_480(4, 479000, 306, 306, 340, 10),
        GERAN_ARFCN_FREQUENCY_BAND_850(8, 824200, 128, 128, 251, 45),
        GERAN_ARFCN_FREQUENCY_BAND_DCS1800(12, 1710200, 512, 512, MetricsProto.MetricsEvent.ACTION_APPOP_DENIED_ACCESS_NOTIFICATIONS, 95),
        GERAN_ARFCN_FREQUENCY_BAND_PCS1900(13, 1850200, 512, 512, MetricsProto.MetricsEvent.PROVISIONING_TERMS_COUNT, 80),
        GERAN_ARFCN_FREQUENCY_BAND_E900_1(10, 890000, 0, 0, 124, 45),
        GERAN_ARFCN_FREQUENCY_BAND_E900_2(10, 890000, 1024, 975, 1023, 45),
        GERAN_ARFCN_FREQUENCY_BAND_R900_1(11, 890000, 0, 0, 124, 45),
        GERAN_ARFCN_FREQUENCY_BAND_R900_2(11, 890000, 1024, 955, 1023, 45),
        GERAN_ARFCN_FREQUENCY_BAND_P900(9, 890000, 0, 1, 124, 45),
        GERAN_ARFCN_FREQUENCY_BAND_ER900_1(14, 890000, 0, 0, 124, 45),
        GERAN_ARFCN_FREQUENCY_BAND_ER900_2(14, 890000, 1024, 940, 1023, 1024);

        int arfcnOffset;
        int arfcnRangeFirst;
        int arfcnRangeLast;
        int band;
        int downlinkOffset;
        int uplinkFrequencyFirst;

        GeranBandArfcnFrequency(int i, int i2, int i3, int i4, int i5, int i6) {
            this.band = i;
            this.uplinkFrequencyFirst = i2;
            this.arfcnOffset = i3;
            this.arfcnRangeFirst = i4;
            this.arfcnRangeLast = i5;
            this.downlinkOffset = i6;
        }
    }

    public static final class UtranBand {
        public static final int BAND_1 = 1;
        public static final int BAND_10 = 10;
        public static final int BAND_11 = 11;
        public static final int BAND_12 = 12;
        public static final int BAND_13 = 13;
        public static final int BAND_14 = 14;
        public static final int BAND_19 = 19;
        public static final int BAND_2 = 2;
        public static final int BAND_20 = 20;
        public static final int BAND_21 = 21;
        public static final int BAND_22 = 22;
        public static final int BAND_25 = 25;
        public static final int BAND_26 = 26;
        public static final int BAND_3 = 3;
        public static final int BAND_4 = 4;
        public static final int BAND_5 = 5;
        public static final int BAND_6 = 6;
        public static final int BAND_7 = 7;
        public static final int BAND_8 = 8;
        public static final int BAND_9 = 9;
        public static final int BAND_A = 101;
        public static final int BAND_B = 102;
        public static final int BAND_C = 103;
        public static final int BAND_D = 104;
        public static final int BAND_E = 105;
        public static final int BAND_F = 106;

        @Retention(RetentionPolicy.SOURCE)
        public @interface UtranBands {
        }

        private UtranBand() {
        }
    }

    enum UtranBandArfcnFrequency {
        UTRAN_ARFCN_FREQUENCY_BAND_1(1, 0, 10562, 10838, 0, 9612, 9888),
        UTRAN_ARFCN_FREQUENCY_BAND_2(2, 0, 9662, 9938, 0, 9262, 9538),
        UTRAN_ARFCN_FREQUENCY_BAND_3(3, 1575000, 1162, 1513, 1525000, MetricsProto.MetricsEvent.ACTION_TEXT_SELECTION_MENU_ITEM_ASSIST, MetricsProto.MetricsEvent.ROTATION_SUGGESTION_SHOWN),
        UTRAN_ARFCN_FREQUENCY_BAND_4(4, 1805000, 1537, 1738, 1450000, 1312, 1513),
        UTRAN_ARFCN_FREQUENCY_BAND_5(5, 0, 4357, 4458, 0, 4132, BoostFramework.VENDOR_HINT_ROTATION_LATENCY_BOOST),
        UTRAN_ARFCN_FREQUENCY_BAND_6(6, 0, SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE, 4413, 0, BoostFramework.VENDOR_HINT_FIRST_DRAW, 4188),
        UTRAN_ARFCN_FREQUENCY_BAND_7(7, 2175000, 2237, 2563, Build.VERSION_CODES_FULL.LOLLIPOP, 2012, 2338),
        UTRAN_ARFCN_FREQUENCY_BAND_8(8, 340000, 2937, 3088, 340000, 2712, 2863),
        UTRAN_ARFCN_FREQUENCY_BAND_9(9, 0, 9327, 9837, 0, 8762, 8912),
        UTRAN_ARFCN_FREQUENCY_BAND_10(10, 1490000, 3112, 3388, 1135000, 2887, 3163),
        UTRAN_ARFCN_FREQUENCY_BAND_11(11, 736000, 3712, 3787, 733000, 3487, 3562),
        UTRAN_ARFCN_FREQUENCY_BAND_12(12, -37000, 3842, 3903, -22000, 3617, 3678),
        UTRAN_ARFCN_FREQUENCY_BAND_13(13, -55000, 4017, 4043, 21000, 3792, 3818),
        UTRAN_ARFCN_FREQUENCY_BAND_14(14, -63000, MtpConstants.OPERATION_GET_DEVICE_PROP_VALUE, 4143, 12000, 3892, 3918),
        UTRAN_ARFCN_FREQUENCY_BAND_19(19, 735000, MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_SMS, MetricsProto.MetricsEvent.ACTION_CLICK_SETTINGS_SEARCH_RESULT, 770000, 312, 363),
        UTRAN_ARFCN_FREQUENCY_BAND_20(20, -109000, 4512, 4638, -23000, 4287, 4413),
        UTRAN_ARFCN_FREQUENCY_BAND_21(21, 1326000, 862, 912, 1358000, 462, 512),
        UTRAN_ARFCN_FREQUENCY_BAND_22(22, 2580000, 4662, 5038, 2525000, 4437, 4813),
        UTRAN_ARFCN_FREQUENCY_BAND_25(25, 910000, 5112, 5413, 875000, 4887, 5188),
        UTRAN_ARFCN_FREQUENCY_BAND_A(101, 0, 10054, 10121, 0, 9504, 9596),
        UTRAN_ARFCN_FREQUENCY_BAND_B(102, 0, 9654, 9946, 0, 9254, 9546),
        UTRAN_ARFCN_FREQUENCY_BAND_C(103, 0, 0, 0, 0, 9554, 9646),
        UTRAN_ARFCN_FREQUENCY_BAND_D(104, 0, 0, 0, 0, 12854, 13096),
        UTRAN_ARFCN_FREQUENCY_BAND_E(105, 0, 0, 0, 0, 11504, 11996),
        UTRAN_ARFCN_FREQUENCY_BAND_F(106, 0, 0, 0, 0, 9404, 9596);

        int band;
        int downlinkOffset;
        int downlinkRangeFirst;
        int downlinkRangeLast;
        int uplinkOffset;
        int uplinkRangeFirst;
        int uplinkRangeLast;

        UtranBandArfcnFrequency(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.band = i;
            this.downlinkOffset = i2;
            this.downlinkRangeFirst = i3;
            this.downlinkRangeLast = i4;
            this.uplinkOffset = i5;
            this.uplinkRangeFirst = i6;
            this.uplinkRangeLast = i7;
        }
    }

    public static final class EutranBand {
        public static final int BAND_1 = 1;
        public static final int BAND_10 = 10;
        public static final int BAND_11 = 11;
        public static final int BAND_12 = 12;
        public static final int BAND_13 = 13;
        public static final int BAND_14 = 14;
        public static final int BAND_17 = 17;
        public static final int BAND_18 = 18;
        public static final int BAND_19 = 19;
        public static final int BAND_2 = 2;
        public static final int BAND_20 = 20;
        public static final int BAND_21 = 21;
        public static final int BAND_22 = 22;
        public static final int BAND_23 = 23;
        public static final int BAND_24 = 24;
        public static final int BAND_25 = 25;
        public static final int BAND_26 = 26;
        public static final int BAND_27 = 27;
        public static final int BAND_28 = 28;
        public static final int BAND_3 = 3;
        public static final int BAND_30 = 30;
        public static final int BAND_31 = 31;
        public static final int BAND_33 = 33;
        public static final int BAND_34 = 34;
        public static final int BAND_35 = 35;
        public static final int BAND_36 = 36;
        public static final int BAND_37 = 37;
        public static final int BAND_38 = 38;
        public static final int BAND_39 = 39;
        public static final int BAND_4 = 4;
        public static final int BAND_40 = 40;
        public static final int BAND_41 = 41;
        public static final int BAND_42 = 42;
        public static final int BAND_43 = 43;
        public static final int BAND_44 = 44;
        public static final int BAND_45 = 45;
        public static final int BAND_46 = 46;
        public static final int BAND_47 = 47;
        public static final int BAND_48 = 48;
        public static final int BAND_49 = 49;
        public static final int BAND_5 = 5;
        public static final int BAND_50 = 50;
        public static final int BAND_51 = 51;
        public static final int BAND_52 = 52;
        public static final int BAND_53 = 53;
        public static final int BAND_6 = 6;
        public static final int BAND_65 = 65;
        public static final int BAND_66 = 66;
        public static final int BAND_68 = 68;
        public static final int BAND_7 = 7;
        public static final int BAND_70 = 70;
        public static final int BAND_71 = 71;
        public static final int BAND_72 = 72;
        public static final int BAND_73 = 73;
        public static final int BAND_74 = 74;
        public static final int BAND_8 = 8;
        public static final int BAND_85 = 85;
        public static final int BAND_87 = 87;
        public static final int BAND_88 = 88;
        public static final int BAND_9 = 9;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EutranBands {
        }

        private EutranBand() {
        }
    }

    enum EutranBandArfcnFrequency {
        EUTRAN_ARFCN_FREQUENCY_BAND_1(1, 2110000, 0, 599, 1920000, 18800, 18599),
        EUTRAN_ARFCN_FREQUENCY_BAND_2(2, 1930000, 600, 1199, 1850000, 18600, 19199),
        EUTRAN_ARFCN_FREQUENCY_BAND_3(3, 1805000, 1200, SettingsEnums.ACTION_AUDIO_STREAM_CONFIRM_LAUNCH_MAIN_BUTTON_CLICK, 1710000, 19200, 19949),
        EUTRAN_ARFCN_FREQUENCY_BAND_4(4, 2110000, 1950, 2399, 1710000, 19950, 20399),
        EUTRAN_ARFCN_FREQUENCY_BAND_5(5, 869000, 2400, 2649, 824000, 20400, 20649),
        EUTRAN_ARFCN_FREQUENCY_BAND_6(6, 875000, 2650, 2749, 830000, 20650, 20749),
        EUTRAN_ARFCN_FREQUENCY_BAND_7(7, 2620000, SmartFaceManager.SMART_STAY_DELAY, 3449, Build.VERSION_CODES_FULL.N_MR1, 20750, 21449),
        EUTRAN_ARFCN_FREQUENCY_BAND_8(8, 925000, 3450, 3799, 880000, 21450, 21799),
        EUTRAN_ARFCN_FREQUENCY_BAND_9(9, 1844900, 3800, 4149, 1749900, 21800, 22149),
        EUTRAN_ARFCN_FREQUENCY_BAND_10(10, 2110000, 4150, 4749, 1710000, 22150, 22749),
        EUTRAN_ARFCN_FREQUENCY_BAND_11(11, 1475900, 4750, 4949, 1427900, 22750, 22949),
        EUTRAN_ARFCN_FREQUENCY_BAND_12(12, 729000, Process.INTELLIGENCE_SERVICE_UID, 5179, 699000, 23010, 23179),
        EUTRAN_ARFCN_FREQUENCY_BAND_13(13, 746000, 5180, Process.SPAY_UID, 777000, 23180, 23279),
        EUTRAN_ARFCN_FREQUENCY_BAND_14(14, 758000, 5280, 5379, 788000, 23230, 23379),
        EUTRAN_ARFCN_FREQUENCY_BAND_17(17, 734000, 5730, 5849, 704000, 23730, 23849),
        EUTRAN_ARFCN_FREQUENCY_BAND_18(18, 860000, 5850, 5999, 815000, 23850, 23999),
        EUTRAN_ARFCN_FREQUENCY_BAND_19(19, 875000, 6000, 6149, 830000, 24000, 24149),
        EUTRAN_ARFCN_FREQUENCY_BAND_20(20, 791000, 6150, 6449, 832000, 24150, 24449),
        EUTRAN_ARFCN_FREQUENCY_BAND_21(21, 1495900, 6450, 6599, 1447900, 24450, 24599),
        EUTRAN_ARFCN_FREQUENCY_BAND_22(22, 3510000, 6600, 7399, 3410000, 24600, 25399),
        EUTRAN_ARFCN_FREQUENCY_BAND_23(23, 2180000, 7500, 7699, 2000000, 25500, 25699),
        EUTRAN_ARFCN_FREQUENCY_BAND_24(24, 1525000, 7700, 8039, 1626500, 25700, 26039),
        EUTRAN_ARFCN_FREQUENCY_BAND_25(25, 1930000, 8040, 8689, 1850000, 26040, 26689),
        EUTRAN_ARFCN_FREQUENCY_BAND_26(26, 859000, 8690, 9039, 814000, 26690, 27039),
        EUTRAN_ARFCN_FREQUENCY_BAND_27(27, 852000, 9040, 9209, 807000, 27040, 27209),
        EUTRAN_ARFCN_FREQUENCY_BAND_28(28, 758000, 9210, 9659, 703000, 27210, 27659),
        EUTRAN_ARFCN_FREQUENCY_BAND_30(30, 2350000, 9770, 9869, 2305000, 27660, 27759),
        EUTRAN_ARFCN_FREQUENCY_BAND_31(31, 462500, 9870, 9919, 452500, 27760, 27809),
        EUTRAN_ARFCN_FREQUENCY_BAND_33(33, Build.VERSION_CODES_FULL.KITKAT, SemMediaPlayer.KEY_PARAMETER_ENABLE_ALL_SUPER_SLOW_REGION, GLES11Ext.GL_TEXTURE_BINDING_EXTERNAL_OES, Build.VERSION_CODES_FULL.KITKAT, SemMediaPlayer.KEY_PARAMETER_ENABLE_ALL_SUPER_SLOW_REGION, GLES11Ext.GL_TEXTURE_BINDING_EXTERNAL_OES),
        EUTRAN_ARFCN_FREQUENCY_BAND_34(34, WindowManagerPolicyConstants.SCREEN_FREEZE_LAYER_BASE, GLES11Ext.GL_REQUIRED_TEXTURE_IMAGE_UNITS_OES, GLES20.GL_MAX_FRAGMENT_UNIFORM_VECTORS, WindowManagerPolicyConstants.SCREEN_FREEZE_LAYER_BASE, GLES11Ext.GL_REQUIRED_TEXTURE_IMAGE_UNITS_OES, GLES20.GL_MAX_FRAGMENT_UNIFORM_VECTORS),
        EUTRAN_ARFCN_FREQUENCY_BAND_35(35, 1850000, 36350, 36949, 1850000, 36350, 36949),
        EUTRAN_ARFCN_FREQUENCY_BAND_36(36, 1930000, 36950, 37549, 1930000, 36950, 37549),
        EUTRAN_ARFCN_FREQUENCY_BAND_37(37, 1910000, 37550, 37749, 1910000, 37550, 37749),
        EUTRAN_ARFCN_FREQUENCY_BAND_38(38, 2570000, 37750, 38249, 2570000, 37750, 38249),
        EUTRAN_ARFCN_FREQUENCY_BAND_39(39, 1880000, 38250, 38649, 1880000, 38250, 38649),
        EUTRAN_ARFCN_FREQUENCY_BAND_40(40, Build.VERSION_CODES_FULL.M, 38650, 39649, Build.VERSION_CODES_FULL.M, 38650, 39649),
        EUTRAN_ARFCN_FREQUENCY_BAND_41(41, 2496000, 39650, 41589, 2496000, 39650, 41589),
        EUTRAN_ARFCN_FREQUENCY_BAND_42(42, Build.VERSION_CODES_FULL.UPSIDE_DOWN_CAKE, 41590, 43589, Build.VERSION_CODES_FULL.UPSIDE_DOWN_CAKE, 41590, 43589),
        EUTRAN_ARFCN_FREQUENCY_BAND_43(43, Build.VERSION_CODES_FULL.BAKLAVA, 43590, 45589, Build.VERSION_CODES_FULL.BAKLAVA, 43590, 45589),
        EUTRAN_ARFCN_FREQUENCY_BAND_44(44, 703000, 45590, 46589, 703000, 45590, 46589),
        EUTRAN_ARFCN_FREQUENCY_BAND_45(45, 1447000, 46590, 46789, 1447000, 46590, 46789),
        EUTRAN_ARFCN_FREQUENCY_BAND_46(46, 5150000, 46790, 54539, 5150000, 46790, 54539),
        EUTRAN_ARFCN_FREQUENCY_BAND_47(47, 5855000, 54540, 55239, 5855000, 54540, 55239),
        EUTRAN_ARFCN_FREQUENCY_BAND_48(48, 3550000, 55240, 56739, 3550000, 55240, 56739),
        EUTRAN_ARFCN_FREQUENCY_BAND_49(49, 3550000, 56740, 58239, 3550000, 56740, 58239),
        EUTRAN_ARFCN_FREQUENCY_BAND_50(50, 1432000, 58240, 59089, 1432000, 58240, 59089),
        EUTRAN_ARFCN_FREQUENCY_BAND_51(51, 1427000, 59090, 59139, 1427000, 59090, 59139),
        EUTRAN_ARFCN_FREQUENCY_BAND_52(52, Build.VERSION_CODES_FULL.TIRAMISU, 59140, 60139, Build.VERSION_CODES_FULL.TIRAMISU, 59140, 60139),
        EUTRAN_ARFCN_FREQUENCY_BAND_53(53, 2483500, 60140, 60254, 2483500, 60140, 60254),
        EUTRAN_ARFCN_FREQUENCY_BAND_65(65, 2110000, 65536, 66435, 1920000, 131072, 131971),
        EUTRAN_ARFCN_FREQUENCY_BAND_66(66, 2110000, 66436, 67335, 1710000, 131972, 132671),
        EUTRAN_ARFCN_FREQUENCY_BAND_68(68, 753000, 67536, 67835, 698000, 132672, 132971),
        EUTRAN_ARFCN_FREQUENCY_BAND_70(70, 1995000, 68336, 68585, 1695000, 132972, 133121),
        EUTRAN_ARFCN_FREQUENCY_BAND_71(71, 617000, 68586, 68935, 663000, 133122, 133471),
        EUTRAN_ARFCN_FREQUENCY_BAND_72(72, 461000, 68936, 68985, 451000, 133472, 133521),
        EUTRAN_ARFCN_FREQUENCY_BAND_73(73, 460000, 68986, 69035, 450000, 133522, 133571),
        EUTRAN_ARFCN_FREQUENCY_BAND_74(74, 1475000, 69036, 69465, 1427000, 133572, 134001),
        EUTRAN_ARFCN_FREQUENCY_BAND_85(85, 728000, 70366, 70545, 698000, 134002, 134181),
        EUTRAN_ARFCN_FREQUENCY_BAND_87(87, 420000, 70546, 70595, 410000, 134182, 134231),
        EUTRAN_ARFCN_FREQUENCY_BAND_88(88, 422000, 70596, 70645, 412000, 134231, 134280);

        int band;
        int downlinkLowKhz;
        int downlinkOffset;
        int downlinkRange;
        int uplinkLowKhz;
        int uplinkOffset;
        int uplinkRange;

        EutranBandArfcnFrequency(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.band = i;
            this.downlinkLowKhz = i2;
            this.downlinkOffset = i3;
            this.downlinkRange = i4;
            this.uplinkLowKhz = i5;
            this.uplinkOffset = i6;
            this.uplinkRange = i7;
        }
    }

    public static final class CdmaBands {
        public static final int BAND_0 = 1;
        public static final int BAND_1 = 2;
        public static final int BAND_10 = 11;
        public static final int BAND_11 = 12;
        public static final int BAND_12 = 13;
        public static final int BAND_13 = 14;
        public static final int BAND_14 = 15;
        public static final int BAND_15 = 16;
        public static final int BAND_16 = 17;
        public static final int BAND_17 = 18;
        public static final int BAND_18 = 19;
        public static final int BAND_19 = 20;
        public static final int BAND_2 = 3;
        public static final int BAND_20 = 21;
        public static final int BAND_21 = 22;
        public static final int BAND_3 = 4;
        public static final int BAND_4 = 5;
        public static final int BAND_5 = 6;
        public static final int BAND_6 = 7;
        public static final int BAND_7 = 8;
        public static final int BAND_8 = 9;
        public static final int BAND_9 = 10;

        private CdmaBands() {
        }
    }

    public static final class NgranBands {
        public static final int BAND_1 = 1;
        public static final int BAND_12 = 12;
        public static final int BAND_14 = 14;
        public static final int BAND_18 = 18;
        public static final int BAND_2 = 2;
        public static final int BAND_20 = 20;
        public static final int BAND_25 = 25;
        public static final int BAND_257 = 257;
        public static final int BAND_258 = 258;
        public static final int BAND_26 = 26;
        public static final int BAND_260 = 260;
        public static final int BAND_261 = 261;
        public static final int BAND_28 = 28;
        public static final int BAND_29 = 29;
        public static final int BAND_3 = 3;
        public static final int BAND_30 = 30;
        public static final int BAND_34 = 34;
        public static final int BAND_38 = 38;
        public static final int BAND_39 = 39;
        public static final int BAND_40 = 40;
        public static final int BAND_41 = 41;
        public static final int BAND_46 = 46;
        public static final int BAND_48 = 48;
        public static final int BAND_5 = 5;
        public static final int BAND_50 = 50;
        public static final int BAND_51 = 51;
        public static final int BAND_53 = 53;
        public static final int BAND_65 = 65;
        public static final int BAND_66 = 66;
        public static final int BAND_7 = 7;
        public static final int BAND_70 = 70;
        public static final int BAND_71 = 71;
        public static final int BAND_74 = 74;
        public static final int BAND_75 = 75;
        public static final int BAND_76 = 76;
        public static final int BAND_77 = 77;
        public static final int BAND_78 = 78;
        public static final int BAND_79 = 79;
        public static final int BAND_8 = 8;
        public static final int BAND_80 = 80;
        public static final int BAND_81 = 81;
        public static final int BAND_82 = 82;
        public static final int BAND_83 = 83;
        public static final int BAND_84 = 84;
        public static final int BAND_86 = 86;
        public static final int BAND_89 = 89;
        public static final int BAND_90 = 90;
        public static final int BAND_91 = 91;
        public static final int BAND_92 = 92;
        public static final int BAND_93 = 93;
        public static final int BAND_94 = 94;
        public static final int BAND_95 = 95;
        public static final int BAND_96 = 96;

        @SystemApi
        public static final int FREQUENCY_RANGE_GROUP_1 = 1;

        @SystemApi
        public static final int FREQUENCY_RANGE_GROUP_2 = 2;

        @SystemApi
        public static final int FREQUENCY_RANGE_GROUP_UNKNOWN = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface FrequencyRangeGroup {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface NgranBand {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:34:0x003f A[FALL_THROUGH, RETURN] */
        @android.annotation.SystemApi
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getFrequencyRangeGroup(int r3) {
            /*
                r0 = 1
                if (r3 == r0) goto L3f
                r1 = 2
                if (r3 == r1) goto L3f
                r2 = 3
                if (r3 == r2) goto L3f
                r2 = 7
                if (r3 == r2) goto L3f
                r2 = 8
                if (r3 == r2) goto L3f
                r2 = 25
                if (r3 == r2) goto L3f
                r2 = 26
                if (r3 == r2) goto L3f
                r2 = 50
                if (r3 == r2) goto L3f
                r2 = 51
                if (r3 == r2) goto L3f
                r2 = 65
                if (r3 == r2) goto L3f
                r2 = 66
                if (r3 == r2) goto L3f
                r2 = 70
                if (r3 == r2) goto L3f
                r2 = 71
                if (r3 == r2) goto L3f
                switch(r3) {
                    case 5: goto L3f;
                    case 12: goto L3f;
                    case 14: goto L3f;
                    case 18: goto L3f;
                    case 20: goto L3f;
                    case 34: goto L3f;
                    case 46: goto L3f;
                    case 48: goto L3f;
                    case 53: goto L3f;
                    case 74: goto L3f;
                    case 75: goto L3f;
                    case 76: goto L3f;
                    case 77: goto L3f;
                    case 78: goto L3f;
                    case 79: goto L3f;
                    case 80: goto L3f;
                    case 81: goto L3f;
                    case 82: goto L3f;
                    case 83: goto L3f;
                    case 84: goto L3f;
                    case 86: goto L3f;
                    case 257: goto L3e;
                    case 258: goto L3e;
                    case 260: goto L3e;
                    case 261: goto L3e;
                    default: goto L33;
                }
            L33:
                switch(r3) {
                    case 28: goto L3f;
                    case 29: goto L3f;
                    case 30: goto L3f;
                    default: goto L36;
                }
            L36:
                switch(r3) {
                    case 38: goto L3f;
                    case 39: goto L3f;
                    case 40: goto L3f;
                    case 41: goto L3f;
                    default: goto L39;
                }
            L39:
                switch(r3) {
                    case 89: goto L3f;
                    case 90: goto L3f;
                    case 91: goto L3f;
                    case 92: goto L3f;
                    case 93: goto L3f;
                    case 94: goto L3f;
                    case 95: goto L3f;
                    case 96: goto L3f;
                    default: goto L3c;
                }
            L3c:
                r3 = 0
                return r3
            L3e:
                return r1
            L3f:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.AccessNetworkConstants.NgranBands.getFrequencyRangeGroup(int):int");
        }

        private NgranBands() {
        }
    }

    enum NgranArfcnFrequency {
        NGRAN_ARFCN_FREQUENCY_RANGE_1(5, 0, 0, 0, 599999),
        NGRAN_ARFCN_FREQUENCY_RANGE_2(15, Build.VERSION_CODES_FULL.R, Build.VERSION_CODES_FULL.ECLAIR_0_1, Build.VERSION_CODES_FULL.ECLAIR_0_1, 2016666),
        NGRAN_ARFCN_FREQUENCY_RANGE_3(60, 24250080, 2016667, 2016667, 3279165);

        int arfcnOffset;
        int globalKhz;
        int rangeFirst;
        int rangeLast;
        int rangeOffset;

        NgranArfcnFrequency(int i, int i2, int i3, int i4, int i5) {
            this.globalKhz = i;
            this.rangeOffset = i2;
            this.arfcnOffset = i3;
            this.rangeFirst = i4;
            this.rangeLast = i5;
        }
    }

    private AccessNetworkConstants() {
    }
}
