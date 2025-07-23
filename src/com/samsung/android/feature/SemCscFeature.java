package com.samsung.android.feature;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes6.dex */
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

    private byte[] _decode(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i % 256;
            byte b2 = this.shifts[i2];
            byte b3 = (byte) (((b & 255) >>> (8 - b2)) | ((b & 255) << b2));
            bArr2[i] = b3;
            bArr2[i] = (byte) (b3 ^ this.salts[i2]);
        }
        return bArr2;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x007b A[Catch: IOException -> 0x0077, TRY_LEAVE, TryCatch #0 {IOException -> 0x0077, blocks: (B:52:0x0073, B:45:0x007b), top: B:51:0x0073 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0073 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] _decompressGzip(byte[] r7) {
        /*
            r6 = this;
            r6 = 1024(0x400, float:1.435E-42)
            r0 = 0
            byte[] r6 = new byte[r6]     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            r2.<init>(r7)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L57
            java.util.zip.GZIPInputStream r7 = new java.util.zip.GZIPInputStream     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            r7.<init>(r1)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L51
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r2.<init>()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
        L19:
            int r3 = r7.available()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            if (r3 <= 0) goto L34
            java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r3.<init>(r2)     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
        L24:
            int r4 = r7.read(r6)     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r5 = -1
            if (r4 == r5) goto L30
            r5 = 0
            r3.write(r6, r5, r4)     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            goto L24
        L30:
            r3.close()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            goto L19
        L34:
            byte[] r6 = r2.toByteArray()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r2.close()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r7.close()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r1.close()     // Catch: java.lang.Exception -> L4d java.lang.Throwable -> L6f
            r7.close()     // Catch: java.io.IOException -> L48
            r1.close()     // Catch: java.io.IOException -> L48
            return r6
        L48:
            r7 = move-exception
            r7.printStackTrace()
            return r6
        L4d:
            r6 = move-exception
            goto L5a
        L4f:
            r6 = move-exception
            goto L71
        L51:
            r6 = move-exception
            r7 = r0
            goto L5a
        L54:
            r6 = move-exception
            r1 = r0
            goto L71
        L57:
            r6 = move-exception
            r7 = r0
            r1 = r7
        L5a:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            if (r7 == 0) goto L65
            r7.close()     // Catch: java.io.IOException -> L63
            goto L65
        L63:
            r6 = move-exception
            goto L6b
        L65:
            if (r1 == 0) goto L6e
            r1.close()     // Catch: java.io.IOException -> L63
            goto L6e
        L6b:
            r6.printStackTrace()
        L6e:
            return r0
        L6f:
            r6 = move-exception
            r0 = r7
        L71:
            if (r0 == 0) goto L79
            r0.close()     // Catch: java.io.IOException -> L77
            goto L79
        L77:
            r7 = move-exception
            goto L7f
        L79:
            if (r1 == 0) goto L82
            r1.close()     // Catch: java.io.IOException -> L77
            goto L82
        L7f:
            r7.printStackTrace()
        L82:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.feature.SemCscFeature._decompressGzip(byte[]):byte[]");
    }

    private boolean isXmlEncoded(File file) {
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                try {
                    String readLine = bufferedReader2.readLine();
                    bufferedReader2.close();
                    if (readLine != null && !readLine.contains(XML_HEADER)) {
                        Log.d(TAG, "Encoded");
                        try {
                            bufferedReader2.close();
                            return true;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return true;
                        }
                    }
                    try {
                        bufferedReader2.close();
                        return false;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return false;
                    }
                } catch (Exception unused) {
                    bufferedReader = bufferedReader2;
                    Log.e(TAG, "Exception : isXmlEncoded");
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused2) {
        }
    }

    private byte[] decode(byte[] bArr) {
        return _decompressGzip(_decode(bArr));
    }

    private SemCscFeature() {
        this.mFeatureList = new Hashtable<>();
        this.mLastOmcUpdateVersion = null;
        this.salts = new byte[]{65, -59, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -34, 107, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -107, 55, 78, 17, -81, 6, MidiConstants.STATUS_CONTROL_CHANGE, -121, -35, -23, 72, 122, -63, -43, 68, 119, -78, -111, -60, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 60, 57, 92, -88, -100, -69, -106, 91, 69, 93, 110, 23, 93, 53, -44, -51, 64, MidiConstants.STATUS_CONTROL_CHANGE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 2, -4, 12, -45, 80, -44, -35, -111, -28, -66, -116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 2, -27, -45, -52, 125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 66, -90, 63, -105, -67, 84, -57, -4, -4, 101, -90, 81, 10, -33, 1, 67, -57, -71, 18, -74, 102, SprAttributeBase.TYPE_DURATION, -89, 64, -17, 54, -94, -84, -66, 14, 119, 121, 2, -78, -79, 89, 63, 93, 109, -78, -51, 66, -36, 32, 86, 3, -58, MidiConstants.STATUS_MIDI_TIME_CODE, 92, 58, 2, -89, MidiConstants.STATUS_CONTROL_CHANGE, MidiConstants.STATUS_SONG_SELECT, -1, 122, -4, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 63, -44, 59, 100, -42, -45, 59, -7, -17, -54, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -54, 71, MidiConstants.STATUS_PROGRAM_CHANGE, -26, -87, MidiConstants.STATUS_CONTROL_CHANGE, -17, -44, -38, MidiConstants.STATUS_NOTE_ON, 70, 10, -106, 95, -24, -4, -118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -85, MidiConstants.STATUS_SONG_SELECT, 85, 25, -102, -119, 13, -37, 116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -69, 59, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -90, -38, -105, 101, -119, -36, SprAttributeBase.TYPE_ANIMATOR_SET, -3, -62, -91, -97, -125, 17, 14, 106, -72, -119, 99, 111, 20, 18, -27, 113, 64, -24, 74, -60, -100, 26, 56, -44, -70, 12, -51, -100, MidiConstants.STATUS_PITCH_BEND, -11, 26, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -117, 98, -93, 51, -25, -79, -31, SprAttributeBase.TYPE_ANIMATOR_SET, 87, -105, MidiConstants.STATUS_PROGRAM_CHANGE, 7, MidiConstants.STATUS_SONG_SELECT, -101, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -122, 5, -104, 89, -44, -117, 63, MidiConstants.STATUS_CONTROL_CHANGE, -6, -71, -110, -29, -105, 116, 107, -93, 91, -41, MidiConstants.STATUS_SONG_SELECT, 20, -115, -78, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 79, -122, 6, 102, MidiConstants.STATUS_PITCH_BEND, 52, -118, -51, 72, -104, 41, -38, 124, 72, -126, -35};
        this.shifts = new byte[]{1, 1, 0, 2, 2, 4, 5, 0, 4, 7, 1, 6, 5, 3, 3, 1, 2, 5, 0, 6, 2, 2, 4, 2, 2, 3, 0, 2, 1, 2, 4, 3, 4, 0, 0, 0, 3, 5, 3, 1, 6, 5, 6, 1, 1, 1, 0, 0, 3, 2, 7, 7, 5, 6, 7, 3, 5, 1, 0, 7, 6, 3, 6, 5, 4, 5, 3, 5, 1, 3, 3, 1, 5, 4, 1, 0, 0, 2, 6, 6, 6, 6, 4, 0, 1, 1, 0, 5, 5, 4, 2, 4, 6, 1, 7, 1, 2, 1, 1, 6, 5, 4, 7, 6, 5, 1, 6, 7, 0, 2, 6, 3, 1, 7, 1, 1, 7, 4, 0, 4, 2, 5, 3, 1, 1, 5, 6, 0, 3, 5, 3, 6, 5, 7, 2, 5, 6, 6, 2, 2, 3, 6, 0, 4, 3, 2, 0, 2, 2, 3, 5, 3, 3, 2, 5, 5, 5, 1, 3, 1, 1, 1, 4, 5, 1, 6, 2, 4, 7, 1, 4, 6, 0, 6, 4, 3, 2, 6, 1, 6, 3, 2, 1, 6, 7, 3, 2, 1, 1, 5, 6, 7, 2, 2, 2, 7, 4, 6, 7, 5, 3, 1, 4, 2, 7, 1, 6, 2, 4, 1, 5, 6, 5, 4, 5, 0, 1, 1, 6, 3, 7, 2, 0, 2, 5, 0, 1, 3, 3, 2, 6, 7, 7, 2, 5, 6, 0, 4, 1, 2, 5, 3, 7, 6, 5, 2, 5, 2, 0, 1, 3, 1, 4, 3, 4, 2};
        loadFeatureFile();
    }

    private static boolean isDebugEnabled() {
        return DEBUG_LEVEL_HIGH.equals(SystemProperties.get("ro.boot.debug_level", "")) && !SystemProperties.getBoolean("ro.product_ship", true);
    }

    private synchronized String get(String str) {
        if (isFeatureChanged()) {
            Log.d(TAG, "CscFeature file is changed");
            loadFeatureFile();
        }
        return this.mFeatureList.get(str);
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

    public Hashtable<String, String> tracer(int i) {
        if (i == 0) {
            Log.d(TAG, "mFeatureList");
            return this.mFeatureList;
        }
        if (i != 1) {
            Log.d(TAG, "Invalid feature table number");
            return null;
        }
        return this.mFeatureList;
    }

    private static class SemCscFeatureHolder {
        private static final SemCscFeature INSTANCE = new SemCscFeature();

        private SemCscFeatureHolder() {
        }
    }

    public static SemCscFeature getInstance() {
        return SemCscFeatureHolder.INSTANCE;
    }

    public boolean getBoolean(String str) {
        try {
            String str2 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getBoolean] tag : " + str + "  result : " + str2);
            }
            if (str2 != null) {
                return Boolean.parseBoolean(str2);
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public boolean getBoolean(String str, boolean z) {
        try {
            String str2 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getBoolean] tag : " + str + "  defaultValue : " + z + "  result : " + str2);
            }
            if (str2 != null) {
                return Boolean.parseBoolean(str2);
            }
        } catch (Exception unused) {
        }
        return z;
    }

    public String getString(String str) {
        String str2;
        try {
            str2 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getString] tag : " + str + "  result : " + str2);
            }
        } catch (Exception unused) {
        }
        return str2 != null ? str2 : "";
    }

    public String getString(String str, String str2) {
        String str3;
        try {
            str3 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getString] tag : " + str + "  defaultValue : " + str2 + "  result : " + str3);
            }
        } catch (Exception unused) {
        }
        return str3 != null ? str3 : str2;
    }

    public int getInteger(String str) {
        return getInt(str);
    }

    public int getInt(String str) {
        try {
            String str2 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getInt] tag : " + str + "  result : " + str2);
            }
            if (str2 != null) {
                return Integer.parseInt(str2);
            }
        } catch (Exception unused) {
        }
        return -1;
    }

    public int getInteger(String str, int i) {
        return getInt(str, i);
    }

    public int getInt(String str, int i) {
        try {
            String str2 = get(str);
            if (DEBUG) {
                Log.d(TAG, "[getInt] tag : " + str + "  defaultValue : " + i + "  result : " + str2);
            }
            if (str2 != null) {
                return Integer.parseInt(str2);
            }
        } catch (Exception unused) {
        }
        return i;
    }

    public boolean getBoolean(int i, String str) {
        return getBoolean(str);
    }

    public boolean getBoolean(int i, String str, boolean z) {
        return getBoolean(str, z);
    }

    public String getString(int i, String str) {
        return getString(str);
    }

    public String getString(int i, String str, String str2) {
        return getString(str, str2);
    }

    public int getInteger(int i, String str) {
        return getInt(i, str);
    }

    public int getInt(int i, String str) {
        return getInt(str);
    }

    public int getInteger(int i, String str, int i2) {
        return getInt(i, str, i2);
    }

    public int getInt(int i, String str, int i2) {
        return getInt(str, i2);
    }

    private void loadFeatureFile() {
        String str = SystemProperties.get("mdc.system.path", PerfettoProtoLogImpl.NULL_STRING);
        String str2 = SystemProperties.get("mdc.unified", "false");
        this.mLastOmcUpdateVersion = getOmcUpdateVersion();
        loadFeatureFile(str, "");
        if ("true".equalsIgnoreCase(str2)) {
            loadFeatureFile(str, SystemProperties.get("ro.csc.countryiso_code", ""));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x015e A[Catch: IOException -> 0x015a, TRY_LEAVE, TryCatch #2 {IOException -> 0x015a, blocks: (B:107:0x0156, B:100:0x015e), top: B:106:0x0156 }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x012b A[Catch: IOException -> 0x0143, TRY_ENTER, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0130 A[Catch: IOException -> 0x0143, TRY_LEAVE, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0117 A[Catch: IOException -> 0x0143, TRY_ENTER, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x011c A[Catch: IOException -> 0x0143, TRY_LEAVE, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x013f A[Catch: IOException -> 0x0143, TRY_ENTER, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0147 A[Catch: IOException -> 0x0143, TRY_LEAVE, TryCatch #13 {IOException -> 0x0143, blocks: (B:16:0x0062, B:67:0x00d6, B:69:0x00db, B:86:0x0117, B:88:0x011c, B:76:0x012b, B:78:0x0130, B:93:0x013f, B:95:0x0147), top: B:2:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadFeatureFile(java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.feature.SemCscFeature.loadFeatureFile(java.lang.String, java.lang.String):void");
    }

    private boolean isElementWithCountryISO(String str) {
        return TAG_FEATURESET.equals(str) || TAG_COUNTRY.equals(str) || TAG_COUNTRYISO.equals(str);
    }

    private boolean needToSkipElement(XmlPullParser xmlPullParser, String str, boolean z) {
        String name = xmlPullParser.getName();
        String attributeValue = xmlPullParser.getAttributeValue(null, ATTR_COUNTRYISO);
        if (!isElementWithCountryISO(name)) {
            return false;
        }
        if (z || !TextUtils.isEmpty(attributeValue)) {
            return (z && str.equals(attributeValue)) ? false : true;
        }
        return false;
    }
}
