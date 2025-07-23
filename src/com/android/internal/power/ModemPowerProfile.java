package com.android.internal.power;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseDoubleArray;
import com.android.internal.telephony.DctConstants;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ModemPowerProfile {
    private static final String ATTR_LEVEL = "level";
    private static final String ATTR_NR_FREQUENCY = "nrFrequency";
    private static final String ATTR_RAT = "rat";
    private static final int IGNORE = -1;
    public static final int MODEM_DRAIN_TYPE_IDLE = 268435456;
    private static final int MODEM_DRAIN_TYPE_MASK = -268435456;
    private static final SparseArray<String> MODEM_DRAIN_TYPE_NAMES;
    public static final int MODEM_DRAIN_TYPE_RX = 536870912;
    public static final int MODEM_DRAIN_TYPE_SLEEP = 0;
    public static final int MODEM_DRAIN_TYPE_TX = 805306368;
    public static final int MODEM_NR_FREQUENCY_RANGE_DEFAULT = 0;
    public static final int MODEM_NR_FREQUENCY_RANGE_HIGH = 196608;
    public static final int MODEM_NR_FREQUENCY_RANGE_LOW = 65536;
    private static final int MODEM_NR_FREQUENCY_RANGE_MASK = 983040;
    public static final int MODEM_NR_FREQUENCY_RANGE_MID = 131072;
    public static final int MODEM_NR_FREQUENCY_RANGE_MMWAVE = 262144;
    private static final SparseArray<String> MODEM_NR_FREQUENCY_RANGE_NAMES;
    public static final int MODEM_RAT_TYPE_DEFAULT = 0;
    public static final int MODEM_RAT_TYPE_LTE = 1048576;
    private static final int MODEM_RAT_TYPE_MASK = 15728640;
    private static final SparseArray<String> MODEM_RAT_TYPE_NAMES;
    public static final int MODEM_RAT_TYPE_NR = 2097152;
    public static final int MODEM_TX_LEVEL_0 = 0;
    public static final int MODEM_TX_LEVEL_1 = 16777216;
    public static final int MODEM_TX_LEVEL_2 = 33554432;
    public static final int MODEM_TX_LEVEL_3 = 50331648;
    public static final int MODEM_TX_LEVEL_4 = 67108864;
    private static final int MODEM_TX_LEVEL_COUNT = 5;
    private static final int[] MODEM_TX_LEVEL_MAP;
    private static final int MODEM_TX_LEVEL_MASK = 251658240;
    private static final SparseArray<String> MODEM_TX_LEVEL_NAMES;
    private static final String TAG = "ModemPowerProfile";
    private static final String TAG_ACTIVE = "active";
    private static final String TAG_IDLE = "idle";
    private static final String TAG_RECEIVE = "receive";
    private static final String TAG_SLEEP = "sleep";
    private static final String TAG_TRANSMIT = "transmit";
    private final SparseDoubleArray mPowerConstants = new SparseDoubleArray();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModemDrainType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModemNrFrequencyRange {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModemRatType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModemTxLevel {
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>(4);
        MODEM_DRAIN_TYPE_NAMES = sparseArray;
        sparseArray.put(0, "SLEEP");
        sparseArray.put(268435456, "IDLE");
        sparseArray.put(536870912, "RX");
        sparseArray.put(805306368, "TX");
        SparseArray<String> sparseArray2 = new SparseArray<>(5);
        MODEM_TX_LEVEL_NAMES = sparseArray2;
        sparseArray2.put(0, "0");
        sparseArray2.put(16777216, "1");
        sparseArray2.put(33554432, "2");
        sparseArray2.put(50331648, "3");
        sparseArray2.put(67108864, "4");
        MODEM_TX_LEVEL_MAP = new int[]{0, 16777216, 33554432, 50331648, 67108864};
        SparseArray<String> sparseArray3 = new SparseArray<>(3);
        MODEM_RAT_TYPE_NAMES = sparseArray3;
        sparseArray3.put(0, "DEFAULT");
        sparseArray3.put(1048576, DctConstants.RAT_NAME_LTE);
        sparseArray3.put(2097152, "NR");
        SparseArray<String> sparseArray4 = new SparseArray<>(5);
        MODEM_NR_FREQUENCY_RANGE_NAMES = sparseArray4;
        sparseArray4.put(0, "DEFAULT");
        sparseArray4.put(65536, "LOW");
        sparseArray4.put(131072, "MID");
        sparseArray4.put(196608, "HIGH");
        sparseArray4.put(262144, "MMWAVE");
    }

    public void parseFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String name;
        int depth = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            name = xmlPullParser.getName();
            name.hashCode();
            switch (name) {
                case "active":
                    parseActivePowerConstantsFromXml(xmlPullParser);
                    break;
                case "idle":
                    if (xmlPullParser.next() == 4) {
                        setPowerConstant(268435456, xmlPullParser.getText());
                        break;
                    } else {
                        break;
                    }
                case "sleep":
                    if (xmlPullParser.next() == 4) {
                        setPowerConstant(0, xmlPullParser.getText());
                        break;
                    } else {
                        break;
                    }
                default:
                    Slog.e(TAG, "Unexpected element parsed: " + name);
                    break;
            }
        }
    }

    private void parseActivePowerConstantsFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        try {
            int typeFromAttribute = getTypeFromAttribute(xmlPullParser, ATTR_RAT, MODEM_RAT_TYPE_NAMES);
            int typeFromAttribute2 = typeFromAttribute == 2097152 ? getTypeFromAttribute(xmlPullParser, ATTR_NR_FREQUENCY, MODEM_NR_FREQUENCY_RANGE_NAMES) : 0;
            int depth = xmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                String name = xmlPullParser.getName();
                name.hashCode();
                if (name.equals(TAG_RECEIVE)) {
                    if (xmlPullParser.next() == 4) {
                        setPowerConstant(536870912 | typeFromAttribute | typeFromAttribute2, xmlPullParser.getText());
                    }
                } else if (name.equals(TAG_TRANSMIT)) {
                    int readIntAttribute = XmlUtils.readIntAttribute(xmlPullParser, "level", -1);
                    if (xmlPullParser.next() == 4) {
                        String text = xmlPullParser.getText();
                        if (readIntAttribute < 0 || readIntAttribute >= 5) {
                            Slog.e(TAG, "Unexpected tx level: " + readIntAttribute + ". Must be between 0 and 4");
                        } else {
                            setPowerConstant(MODEM_TX_LEVEL_MAP[readIntAttribute] | 805306368 | typeFromAttribute | typeFromAttribute2, text);
                        }
                    }
                } else {
                    Slog.e(TAG, "Unexpected element parsed: " + name);
                }
            }
        } catch (IllegalArgumentException e) {
            Slog.e(TAG, "Failed parse to active modem power constants", e);
        }
    }

    private static int getTypeFromAttribute(XmlPullParser xmlPullParser, String str, SparseArray<String> sparseArray) {
        String readStringAttribute = XmlUtils.readStringAttribute(xmlPullParser, str);
        if (readStringAttribute == null) {
            return 0;
        }
        int size = sparseArray.size();
        int i = -1;
        for (int i2 = 0; i2 < size; i2++) {
            if (readStringAttribute.equals(sparseArray.valueAt(i2))) {
                i = i2;
            }
        }
        if (i < 0) {
            String[] strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = sparseArray.valueAt(i3);
            }
            throw new IllegalArgumentException("Unexpected " + str + " value : " + readStringAttribute + ". Acceptable values are " + Arrays.toString(strArr));
        }
        return sparseArray.keyAt(i);
    }

    public void setPowerConstant(int i, String str) {
        try {
            this.mPowerConstants.put(i, Double.valueOf(str).doubleValue());
        } catch (Exception e) {
            Slog.e(TAG, "Failed to set power constant 0x" + Integer.toHexString(i) + NavigationBarInflaterView.KEY_CODE_START + keyToString(i) + ") to " + str, e);
        }
    }

    public static long getAverageBatteryDrainKey(int i, int i2, int i3, int i4) {
        long j;
        long j2;
        long j3;
        long j4 = i != -1 ? 4294967296L | i : 4294967296L;
        if (i2 != -1 && i2 != 0) {
            if (i2 == 1) {
                j3 = 1048576;
            } else if (i2 != 2) {
                Log.w(TAG, "Unexpected RadioAccessTechnology : " + i2);
            } else {
                j3 = 2097152;
            }
            j4 |= j3;
        }
        if (i3 != -1 && i3 != 0) {
            if (i3 == 1) {
                j2 = 65536;
            } else if (i3 == 2) {
                j2 = 131072;
            } else if (i3 == 3) {
                j2 = 196608;
            } else if (i3 != 4) {
                Log.w(TAG, "Unexpected NR frequency range : " + i3);
            } else {
                j2 = 262144;
            }
            j4 |= j2;
        }
        if (i4 == -1 || i4 == 0) {
            return j4;
        }
        if (i4 == 1) {
            j = 16777216;
        } else if (i4 == 2) {
            j = 33554432;
        } else if (i4 == 3) {
            j = 50331648;
        } else {
            if (i4 != 4) {
                Log.w(TAG, "Unexpected transmission level : " + i4);
                return j4;
            }
            j = 67108864;
        }
        return j | j4;
    }

    public double getAverageBatteryDrainMa(int i) {
        int i2;
        double d = this.mPowerConstants.get(i, Double.NaN);
        if (!Double.isNaN(d)) {
            return d;
        }
        if ((983040 & i) != 0) {
            i2 = (-983041) & i;
            double d2 = this.mPowerConstants.get(i2, Double.NaN);
            if (!Double.isNaN(d2)) {
                return d2;
            }
        } else {
            i2 = i;
        }
        if ((15728640 & i2) != 0) {
            double d3 = this.mPowerConstants.get(i2 & (-15728641), Double.NaN);
            if (!Double.isNaN(d3)) {
                return d3;
            }
        }
        Slog.w(TAG, "getAverageBatteryDrainMaH called with unexpected key: 0x" + Integer.toHexString(i) + ", " + keyToString(i));
        return Double.NaN;
    }

    public static String keyToString(int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = (-268435456) & i;
        appendFieldToString(sb, "drain", MODEM_DRAIN_TYPE_NAMES, i2);
        sb.append(",");
        if (i2 == 805306368) {
            appendFieldToString(sb, "level", MODEM_TX_LEVEL_NAMES, MODEM_TX_LEVEL_MASK & i);
            sb.append(",");
        }
        int i3 = 15728640 & i;
        appendFieldToString(sb, "RAT", MODEM_RAT_TYPE_NAMES, i3);
        if (i3 == 2097152) {
            sb.append(",");
            appendFieldToString(sb, "nrFreq", MODEM_NR_FREQUENCY_RANGE_NAMES, i & 983040);
        }
        return sb.toString();
    }

    private static void appendFieldToString(StringBuilder sb, String str, SparseArray<String> sparseArray, int i) {
        sb.append(str);
        sb.append(":");
        String str2 = sparseArray.get(i, null);
        if (str2 == null) {
            sb.append("UNKNOWN(0x");
            sb.append(Integer.toHexString(i));
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        sb.append(str2);
    }

    public void clear() {
        this.mPowerConstants.clear();
    }

    public void dump(PrintWriter printWriter) {
        int size = this.mPowerConstants.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(keyToString(this.mPowerConstants.keyAt(i)));
            printWriter.print("=");
            printWriter.println(this.mPowerConstants.valueAt(i));
        }
    }
}
