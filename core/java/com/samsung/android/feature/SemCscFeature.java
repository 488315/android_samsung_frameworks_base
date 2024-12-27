package com.samsung.android.feature;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.midi.MidiConstants;

import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;

public class SemCscFeature {
    private static final String ATTR_COUNTRYISO = "countryISO";
    private static final boolean DEBUG = isDebugEnabled();
    private static final String DEBUG_LEVEL_HIGH = "0x4948";
    private static final int SALT_LENGTH = 256;
    private static final String TAG = "SemCscFeature";
    private static final String TAG_COUNTRY = "Country";
    private static final String TAG_COUNTRYISO = "CountryISO";
    private static final String TAG_FEATURESET = "FeatureSet";
    private static final String XML_HEADER = "<?xml";
    private Hashtable<String, String> mFeatureList;
    private String mLastOmcUpdateVersion;
    private final byte[] salts;
    private final byte[] shifts;

    private byte[] _decode(byte[] source) {
        byte[] results = new byte[source.length];
        for (int i = 0; i < source.length; i++) {
            results[i] =
                    (byte)
                            (((source[i] & 255) << this.shifts[i % 256])
                                    | ((source[i] & 255) >>> (8 - this.shifts[i % 256])));
            results[i] = (byte) (results[i] ^ this.salts[i % 256]);
        }
        return results;
    }

    private byte[] _decompressGzip(byte[] sourceGz) {
        BufferedInputStream bis = null;
        GZIPInputStream gzIs = null;
        try {
            try {
                byte[] buffer = new byte[1024];
                bis = new BufferedInputStream(new ByteArrayInputStream(sourceGz));
                gzIs = new GZIPInputStream(bis);
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                while (gzIs.available() > 0) {
                    BufferedOutputStream bos = new BufferedOutputStream(resultStream);
                    while (true) {
                        int len = gzIs.read(buffer);
                        if (len != -1) {
                            bos.write(buffer, 0, len);
                        }
                    }
                    bos.close();
                }
                byte[] result = resultStream.toByteArray();
                resultStream.close();
                gzIs.close();
                bis.close();
                try {
                    gzIs.close();
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (gzIs != null) {
                    try {
                        gzIs.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return null;
                    }
                }
                if (bis == null) {
                    return null;
                }
                bis.close();
                return null;
            }
        } catch (Throwable th) {
            if (gzIs != null) {
                try {
                    gzIs.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    throw th;
                }
            }
            if (bis != null) {
                bis.close();
            }
            throw th;
        }
    }

    private boolean isXmlEncoded(File featureXmlFile) {
        BufferedReader ptrRead = null;
        try {
            try {
                ptrRead = new BufferedReader(new FileReader(featureXmlFile));
                String headerStr = ptrRead.readLine();
                ptrRead.close();
                if (headerStr != null && !headerStr.contains(XML_HEADER)) {
                    Log.d(TAG, "Encoded");
                    try {
                        ptrRead.close();
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return true;
                    }
                }
                try {
                    ptrRead.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                return false;
            } catch (Exception e3) {
                Log.e(TAG, "Exception : isXmlEncoded");
                if (ptrRead != null) {
                    try {
                        ptrRead.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (ptrRead != null) {
                try {
                    ptrRead.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
    }

    private byte[] decode(byte[] source) {
        byte[] decodedGzip = _decode(source);
        return _decompressGzip(decodedGzip);
    }

    private SemCscFeature() {
        this.mFeatureList = new Hashtable<>();
        this.mLastOmcUpdateVersion = null;
        this.salts =
                new byte[] {
                    65,
                    -59,
                    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
                    -34,
                    107,
                    SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN,
                    -107,
                    55,
                    78,
                    17,
                    -81,
                    6,
                    MidiConstants.STATUS_CONTROL_CHANGE,
                    -121,
                    -35,
                    -23,
                    72,
                    122,
                    -63,
                    -43,
                    68,
                    119,
                    -78,
                    -111,
                    -60,
                    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN,
                    60,
                    57,
                    92,
                    -88,
                    -100,
                    -69,
                    -106,
                    91,
                    69,
                    93,
                    110,
                    23,
                    93,
                    53,
                    -44,
                    -51,
                    64,
                    MidiConstants.STATUS_CONTROL_CHANGE,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70,
                    2,
                    -4,
                    12,
                    -45,
                    80,
                    -44,
                    -35,
                    -111,
                    -28,
                    -66,
                    -116,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT,
                    2,
                    -27,
                    -45,
                    -52,
                    125,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT,
                    66,
                    -90,
                    63,
                    -105,
                    -67,
                    84,
                    -57,
                    -4,
                    -4,
                    101,
                    -90,
                    81,
                    10,
                    -33,
                    1,
                    67,
                    -57,
                    -71,
                    18,
                    -74,
                    102,
                    SprAttributeBase.TYPE_DURATION,
                    -89,
                    64,
                    -17,
                    54,
                    -94,
                    -84,
                    -66,
                    14,
                    119,
                    121,
                    2,
                    -78,
                    -79,
                    89,
                    63,
                    93,
                    109,
                    -78,
                    -51,
                    66,
                    -36,
                    32,
                    86,
                    3,
                    -58,
                    MidiConstants.STATUS_MIDI_TIME_CODE,
                    92,
                    58,
                    2,
                    -89,
                    MidiConstants.STATUS_CONTROL_CHANGE,
                    MidiConstants.STATUS_SONG_SELECT,
                    -1,
                    122,
                    -4,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
                    63,
                    -44,
                    59,
                    100,
                    -42,
                    -45,
                    59,
                    -7,
                    -17,
                    -54,
                    SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN,
                    -54,
                    71,
                    MidiConstants.STATUS_PROGRAM_CHANGE,
                    -26,
                    -87,
                    MidiConstants.STATUS_CONTROL_CHANGE,
                    -17,
                    -44,
                    -38,
                    MidiConstants.STATUS_NOTE_ON,
                    70,
                    10,
                    -106,
                    95,
                    -24,
                    -4,
                    -118,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
                    -85,
                    MidiConstants.STATUS_SONG_SELECT,
                    85,
                    25,
                    -102,
                    -119,
                    13,
                    -37,
                    116,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70,
                    -69,
                    59,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33,
                    -90,
                    -38,
                    -105,
                    101,
                    -119,
                    -36,
                    SprAttributeBase.TYPE_ANIMATOR_SET,
                    -3,
                    -62,
                    -91,
                    -97,
                    -125,
                    17,
                    14,
                    106,
                    -72,
                    -119,
                    99,
                    111,
                    20,
                    18,
                    -27,
                    113,
                    64,
                    -24,
                    74,
                    -60,
                    -100,
                    26,
                    56,
                    -44,
                    -70,
                    12,
                    -51,
                    -100,
                    MidiConstants.STATUS_PITCH_BEND,
                    -11,
                    26,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90,
                    -117,
                    98,
                    -93,
                    51,
                    -25,
                    -79,
                    -31,
                    SprAttributeBase.TYPE_ANIMATOR_SET,
                    87,
                    -105,
                    MidiConstants.STATUS_PROGRAM_CHANGE,
                    7,
                    MidiConstants.STATUS_SONG_SELECT,
                    -101,
                    SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT,
                    -122,
                    5,
                    -104,
                    89,
                    -44,
                    -117,
                    63,
                    MidiConstants.STATUS_CONTROL_CHANGE,
                    -6,
                    -71,
                    -110,
                    -29,
                    -105,
                    116,
                    107,
                    -93,
                    91,
                    -41,
                    MidiConstants.STATUS_SONG_SELECT,
                    20,
                    -115,
                    -78,
                    SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33,
                    79,
                    -122,
                    6,
                    102,
                    MidiConstants.STATUS_PITCH_BEND,
                    52,
                    -118,
                    -51,
                    72,
                    -104,
                    41,
                    -38,
                    124,
                    72,
                    -126,
                    -35
                };
        this.shifts =
                new byte[] {
                    1, 1, 0, 2, 2, 4, 5, 0, 4, 7, 1, 6, 5, 3, 3, 1, 2, 5, 0, 6, 2, 2, 4, 2, 2, 3, 0,
                    2, 1, 2, 4, 3, 4, 0, 0, 0, 3, 5, 3, 1, 6, 5, 6, 1, 1, 1, 0, 0, 3, 2, 7, 7, 5, 6,
                    7, 3, 5, 1, 0, 7, 6, 3, 6, 5, 4, 5, 3, 5, 1, 3, 3, 1, 5, 4, 1, 0, 0, 2, 6, 6, 6,
                    6, 4, 0, 1, 1, 0, 5, 5, 4, 2, 4, 6, 1, 7, 1, 2, 1, 1, 6, 5, 4, 7, 6, 5, 1, 6, 7,
                    0, 2, 6, 3, 1, 7, 1, 1, 7, 4, 0, 4, 2, 5, 3, 1, 1, 5, 6, 0, 3, 5, 3, 6, 5, 7, 2,
                    5, 6, 6, 2, 2, 3, 6, 0, 4, 3, 2, 0, 2, 2, 3, 5, 3, 3, 2, 5, 5, 5, 1, 3, 1, 1, 1,
                    4, 5, 1, 6, 2, 4, 7, 1, 4, 6, 0, 6, 4, 3, 2, 6, 1, 6, 3, 2, 1, 6, 7, 3, 2, 1, 1,
                    5, 6, 7, 2, 2, 2, 7, 4, 6, 7, 5, 3, 1, 4, 2, 7, 1, 6, 2, 4, 1, 5, 6, 5, 4, 5, 0,
                    1, 1, 6, 3, 7, 2, 0, 2, 5, 0, 1, 3, 3, 2, 6, 7, 7, 2, 5, 6, 0, 4, 1, 2, 5, 3, 7,
                    6, 5, 2, 5, 2, 0, 1, 3, 1, 4, 3, 4, 2
                };
        loadFeatureFile();
    }

    private static boolean isDebugEnabled() {
        String debugLevel = SystemProperties.get("ro.boot.debug_level", "");
        boolean isShipBuild = SystemProperties.getBoolean("ro.product_ship", true);
        return DEBUG_LEVEL_HIGH.equals(debugLevel) && !isShipBuild;
    }

    private synchronized String get(String key) {
        if (isFeatureChanged()) {
            Log.d(TAG, "CscFeature file is changed");
            loadFeatureFile();
        }
        return this.mFeatureList.get(key);
    }

    private boolean isFeatureChanged() {
        return isOmcUpdateVersionChanged();
    }

    private boolean isOmcUpdateVersionChanged() {
        return !TextUtils.equals(this.mLastOmcUpdateVersion, getOmcUpdateVersion());
    }

    private String getOmcUpdateVersion() {
        return SystemProperties.get("mdc.omc.update_version", null);
    }

    public Hashtable<String, String> tracer(int tableNumber) {
        if (tableNumber == 0) {
            Log.d(TAG, "mFeatureList");
            return this.mFeatureList;
        }
        if (tableNumber != 1) {
            Log.d(TAG, "Invalid feature table number");
            return null;
        }
        return this.mFeatureList;
    }

    private static class SemCscFeatureHolder {
        private static final SemCscFeature INSTANCE = new SemCscFeature();

        private SemCscFeatureHolder() {}
    }

    public static SemCscFeature getInstance() {
        return SemCscFeatureHolder.INSTANCE;
    }

    public boolean getBoolean(String tag) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(TAG, "[getBoolean] tag : " + tag + "  result : " + value);
            }
            if (value == null) {
                return false;
            }
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getBoolean(String tag, boolean defaultValue) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(
                        TAG,
                        "[getBoolean] tag : "
                                + tag
                                + "  defaultValue : "
                                + defaultValue
                                + "  result : "
                                + value);
            }
            if (value != null) {
                return Boolean.parseBoolean(value);
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String getString(String tag) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(TAG, "[getString] tag : " + tag + "  result : " + value);
            }
            if (value == null) {
                return "";
            }
            return value;
        } catch (Exception e) {
            return "";
        }
    }

    public String getString(String tag, String defaultValue) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(
                        TAG,
                        "[getString] tag : "
                                + tag
                                + "  defaultValue : "
                                + defaultValue
                                + "  result : "
                                + value);
            }
            if (value != null) {
                return value;
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public int getInteger(String tag) {
        return getInt(tag);
    }

    public int getInt(String tag) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(TAG, "[getInt] tag : " + tag + "  result : " + value);
            }
            if (value == null) {
                return -1;
            }
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getInteger(String tag, int defaultValue) {
        return getInt(tag, defaultValue);
    }

    public int getInt(String tag, int defaultValue) {
        try {
            String value = get(tag);
            if (DEBUG) {
                Log.d(
                        TAG,
                        "[getInt] tag : "
                                + tag
                                + "  defaultValue : "
                                + defaultValue
                                + "  result : "
                                + value);
            }
            if (value != null) {
                return Integer.parseInt(value);
            }
            return defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(int slotId, String tag) {
        return getBoolean(tag);
    }

    public boolean getBoolean(int slotId, String tag, boolean defaultValue) {
        return getBoolean(tag, defaultValue);
    }

    public String getString(int slotId, String tag) {
        return getString(tag);
    }

    public String getString(int slotId, String tag, String defaultValue) {
        return getString(tag, defaultValue);
    }

    public int getInteger(int slotId, String tag) {
        return getInt(slotId, tag);
    }

    public int getInt(int slotId, String tag) {
        return getInt(tag);
    }

    public int getInteger(int slotId, String tag, int defaultValue) {
        return getInt(slotId, tag, defaultValue);
    }

    public int getInt(int slotId, String tag, int defaultValue) {
        return getInt(tag, defaultValue);
    }

    private void loadFeatureFile() {
        String featurePath = SystemProperties.get("mdc.system.path", "null");
        String unifiedSalesCode = SystemProperties.get("mdc.unified", "false");
        this.mLastOmcUpdateVersion = getOmcUpdateVersion();
        loadFeatureFile(featurePath, "");
        if ("true".equalsIgnoreCase(unifiedSalesCode)) {
            String deviceCountryISO = SystemProperties.get("ro.csc.countryiso_code", "");
            loadFeatureFile(featurePath, deviceCountryISO);
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadFeatureFile(java.lang.String r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.feature.SemCscFeature.loadFeatureFile(java.lang.String,"
                    + " java.lang.String):void");
    }

    private boolean isElementWithCountryISO(String tag) {
        return TAG_FEATURESET.equals(tag) || TAG_COUNTRY.equals(tag) || TAG_COUNTRYISO.equals(tag);
    }

    private boolean needToSkipElement(
            XmlPullParser parser, String deviceCountryISO, boolean needCheckCountryIso) {
        String tag = parser.getName();
        String countryISO = parser.getAttributeValue(null, ATTR_COUNTRYISO);
        if (isElementWithCountryISO(tag)) {
            if (needCheckCountryIso || !TextUtils.isEmpty(countryISO)) {
                if (!needCheckCountryIso || !deviceCountryISO.equals(countryISO)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
