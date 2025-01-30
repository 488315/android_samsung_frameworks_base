package com.samsung.android.feature;

import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import com.samsung.android.ims.options.SemCapabilities;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes5.dex */
public class SemCscFeature {
  private static final String ATTR_COUNTRYISO = "countryISO";
  private static final String TAG = "SemCscFeature";
  private static final String TAG_COUNTRY = "Country";
  private static final String TAG_COUNTRYISO = "CountryISO";
  private static final String TAG_FEATURESET = "FeatureSet";
  private static SemCscFeature sInstance = null;
  private static boolean bLoadFeaure = true;
  private Hashtable<String, String> mFeatureList = new Hashtable<>();
  private final String XML_HEADER = "<?xml";
  private final int SALT_LENGTH = 256;
  private final byte[] salts = {
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
    SprAnimatorBase.INTERPOLATOR_TYPE_ELASTICEASEOUT,
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
    SprAnimatorBase.INTERPOLATOR_TYPE_CIRCEASEINOUT,
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
    SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEIN,
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
    SprAnimatorBase.INTERPOLATOR_TYPE_CIRCEASEINOUT,
    -27,
    113,
    64,
    -24,
    74,
    -60,
    -100,
    SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEOUT,
    56,
    -44,
    -70,
    12,
    -51,
    -100,
    MidiConstants.STATUS_PITCH_BEND,
    -11,
    SprAnimatorBase.INTERPOLATOR_TYPE_EXPOEASEOUT,
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
  private final byte[] shifts = {
    1, 1, 0, 2, 2, 4, 5, 0, 4, 7, 1, 6, 5, 3, 3, 1, 2, 5, 0, 6, 2, 2, 4, 2, 2, 3, 0, 2, 1, 2, 4, 3,
    4, 0, 0, 0, 3, 5, 3, 1, 6, 5, 6, 1, 1, 1, 0, 0, 3, 2, 7, 7, 5, 6, 7, 3, 5, 1, 0, 7, 6, 3, 6, 5,
    4, 5, 3, 5, 1, 3, 3, 1, 5, 4, 1, 0, 0, 2, 6, 6, 6, 6, 4, 0, 1, 1, 0, 5, 5, 4, 2, 4, 6, 1, 7, 1,
    2, 1, 1, 6, 5, 4, 7, 6, 5, 1, 6, 7, 0, 2, 6, 3, 1, 7, 1, 1, 7, 4, 0, 4, 2, 5, 3, 1, 1, 5, 6, 0,
    3, 5, 3, 6, 5, 7, 2, 5, 6, 6, 2, 2, 3, 6, 0, 4, 3, 2, 0, 2, 2, 3, 5, 3, 3, 2, 5, 5, 5, 1, 3, 1,
    1, 1, 4, 5, 1, 6, 2, 4, 7, 1, 4, 6, 0, 6, 4, 3, 2, 6, 1, 6, 3, 2, 1, 6, 7, 3, 2, 1, 1, 5, 6, 7,
    2, 2, 2, 7, 4, 6, 7, 5, 3, 1, 4, 2, 7, 1, 6, 2, 4, 1, 5, 6, 5, 4, 5, 0, 1, 1, 6, 3, 7, 2, 0, 2,
    5, 0, 1, 3, 3, 2, 6, 7, 7, 2, 5, 6, 0, 4, 1, 2, 5, 3, 7, 6, 5, 2, 5, 2, 0, 1, 3, 1, 4, 3, 4, 2
  };

  private byte[] _decode(byte[] source) {
    byte[] results = new byte[source.length];
    for (int i = 0; i < source.length; i++) {
      int i2 = source[i] & 255;
      byte[] bArr = this.shifts;
      results[i] = (byte) ((i2 << bArr[i % 256]) | ((source[i] & 255) >>> (8 - bArr[i % 256])));
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
        if (headerStr != null && !headerStr.contains("<?xml")) {
          Log.m94d(TAG, "Encoded");
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
      } catch (Throwable th) {
        if (ptrRead != null) {
          try {
            ptrRead.close();
          } catch (IOException e3) {
            e3.printStackTrace();
          }
        }
        throw th;
      }
    } catch (Exception e4) {
      Log.m96e(TAG, "Exception : isXmlEncoded");
      if (ptrRead != null) {
        try {
          ptrRead.close();
        } catch (IOException e5) {
          e5.printStackTrace();
        }
      }
      return false;
    }
  }

  private byte[] decode(byte[] source) {
    byte[] decodedGzip = _decode(source);
    return _decompressGzip(decodedGzip);
  }

  private SemCscFeature() {
    try {
      String omcPath = SystemProperties.get("mdc.system.path", SemCapabilities.FEATURE_TAG_NULL);
      loadFeatureFile(true, omcPath, this.mFeatureList, false);
    } catch (Exception e) {
      Log.m102w(TAG, e.toString());
    }
  }

  public Hashtable<String, String> tracer(int tableNumber) {
    if (tableNumber == 0) {
      Log.m94d(TAG, "mFeatureList");
      return this.mFeatureList;
    }
    if (tableNumber != 1) {
      Log.m94d(TAG, "Invalid feature table number");
      return null;
    }
    return this.mFeatureList;
  }

  public static SemCscFeature getInstance() {
    SemCscFeature semCscFeature = sInstance;
    if (semCscFeature == null || !bLoadFeaure) {
      if (semCscFeature != null && !bLoadFeaure) {
        sInstance = null;
      }
      sInstance = new SemCscFeature();
    }
    return sInstance;
  }

  public boolean getBoolean(String tag) {
    try {
      String value = this.mFeatureList.get(tag);
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
      String value = this.mFeatureList.get(tag);
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
      String value = this.mFeatureList.get(tag);
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
      String value = this.mFeatureList.get(tag);
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
      String value = this.mFeatureList.get(tag);
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
      String value = this.mFeatureList.get(tag);
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

  private boolean loadFeatureFile(
      boolean isOmc,
      String targetPath,
      Map<String, String> featureList,
      boolean isFeatureForSlot2) {
    String unifiedSalesCode = SystemProperties.get("mdc.unified", "false");
    if ("true".equalsIgnoreCase(unifiedSalesCode)) {
      boolean ret = loadFeatureFile(isOmc, targetPath, featureList, isFeatureForSlot2, "");
      String deviceCountryISO = SystemProperties.get("ro.csc.countryiso_code", "");
      boolean retCountryISO =
          loadFeatureFile(isOmc, targetPath, featureList, isFeatureForSlot2, deviceCountryISO);
      return ret | retCountryISO;
    }
    return loadFeatureFile(isOmc, targetPath, featureList, isFeatureForSlot2, "");
  }

  /* JADX WARN: Can't wrap try/catch for region: R(25:0|1|(1:3)(1:261)|(1:5)(1:260)|6|7|(2:245|246)|(1:10)(1:244)|11|(3:12|13|14)|(13:(1:20)|21|22|(5:176|177|178|179|180)(3:24|25|26)|27|28|(2:29|(8:31|(2:(4:34|35|36|(1:38))(1:106)|(2:40|41)(1:96))(3:107|(1:(6:112|113|114|(2:116|117)|118|(1:140)(4:122|123|124|125))(1:110))(2:150|(1:155))|111)|42|43|45|46|47|48)(1:156))|157|158|159|(2:161|162)|164|65)|200|(6:206|(2:208|(1:210))(1:235)|(3:212|213|214)|(2:233|234)|(2:226|227)|232)(1:205)|22|(0)(0)|27|28|(3:29|(0)(0)|48)|157|158|159|(0)|164|65|(2:(0)|(1:131))) */
  /* JADX WARN: Code restructure failed: missing block: B:165:0x029c, code lost:

     r0 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:166:0x029d, code lost:

     android.util.Log.m102w(com.samsung.android.feature.SemCscFeature.TAG, r0.toString());
  */
  /* JADX WARN: Removed duplicated region for block: B:156:0x0289 A[EDGE_INSN: B:156:0x0289->B:157:0x0289 BREAK  A[LOOP:0: B:29:0x016f->B:48:0x016f], SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:161:0x0294 A[Catch: IOException -> 0x029c, TRY_LEAVE, TryCatch #46 {IOException -> 0x029c, blocks: (B:159:0x028e, B:161:0x0294), top: B:158:0x028e }] */
  /* JADX WARN: Removed duplicated region for block: B:176:0x00f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:24:0x0160  */
  /* JADX WARN: Removed duplicated region for block: B:31:0x0174  */
  /* JADX WARN: Removed duplicated region for block: B:60:0x0347 A[Catch: IOException -> 0x0385, TRY_ENTER, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:62:0x034e A[Catch: IOException -> 0x0385, TRY_LEAVE, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:71:0x0363 A[Catch: IOException -> 0x0385, TRY_ENTER, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:73:0x036a A[Catch: IOException -> 0x0385, TRY_LEAVE, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:78:0x037f A[Catch: IOException -> 0x0385, TRY_ENTER, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0389 A[Catch: IOException -> 0x0385, TRY_LEAVE, TryCatch #16 {IOException -> 0x0385, blocks: (B:60:0x0347, B:62:0x034e, B:71:0x0363, B:73:0x036a, B:78:0x037f, B:80:0x0389), top: B:7:0x0021 }] */
  /* JADX WARN: Removed duplicated region for block: B:85:0x03ab A[Catch: IOException -> 0x03a7, TRY_LEAVE, TryCatch #35 {IOException -> 0x03a7, blocks: (B:95:0x03a2, B:85:0x03ab), top: B:94:0x03a2 }] */
  /* JADX WARN: Removed duplicated region for block: B:93:? A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:94:0x03a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Unreachable blocks removed: 2, instructions: 5 */
  /* JADX WARN: Unreachable blocks removed: 2, instructions: 6 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private boolean loadFeatureFile(
      boolean isOmc,
      String targetPath,
      Map<String, String> featureList,
      boolean isFeatureForSlot2,
      String deviceCountryISO) {
    Throwable th;
    String featurePath;
    File featureXmlFile;
    File featureXmlFile2;
    int eventType;
    int eventType2;
    int eventType3;
    String TagName;
    Throwable th2;
    SemCscFeature semCscFeature = this;
    InputStream fi = null;
    InputStream is = null;
    int eventType4 = -1;
    String TagName2 = null;
    String TagName3 = null;
    boolean ret = false;
    boolean appendFeatureSet = !TextUtils.isEmpty(deviceCountryISO);
    String str = isFeatureForSlot2 ? "slot2 " : "slot1 ";
    try {
      try {
        if (!appendFeatureSet) {
          try {
            featureList.clear();
          } catch (FileNotFoundException e) {
            e = e;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (IOException e2) {
            e = e2;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (XmlPullParserException e3) {
            e = e3;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (Throwable th3) {
            th = th3;
            if (fi != null) {}
            if (is == null) {}
          }
        }
        featurePath = isOmc ? targetPath : "/system/csc";
        try {
          featureXmlFile = new File(featurePath + "/cscfeature.xml");
        } catch (FileNotFoundException e4) {
          e = e4;
        } catch (IOException e5) {
          e = e5;
        } catch (XmlPullParserException e6) {
          e = e6;
        } catch (Throwable th4) {
          th = th4;
        }
      } catch (IOException e7) {
        Log.m102w(TAG, e7.toString());
      }
      if (featureXmlFile.exists() && featureXmlFile.length() > 0) {
        if (!isFeatureForSlot2) {
          bLoadFeaure = true;
        }
        ret = true;
        featureXmlFile2 = featureXmlFile;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();
        fi = new FileInputStream(featureXmlFile2);
        if (semCscFeature.isXmlEncoded(featureXmlFile2)) {
          eventType2 = -1;
          try {
            parser.setInput(fi, null);
          } catch (FileNotFoundException e8) {
            e = e8;
            eventType4 = eventType2;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (IOException e9) {
            e = e9;
            eventType4 = eventType2;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (XmlPullParserException e10) {
            e = e10;
            eventType4 = eventType2;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (Throwable th5) {
            th = th5;
            if (fi != null) {}
            if (is == null) {}
          }
        } else {
          try {
            int size = fi.available();
            byte[] source = new byte[size];
            fi.read(source);
            fi.close();
            byte[] result = semCscFeature.decode(source);
            eventType2 = -1;
            try {
              is = new ByteArrayInputStream(result);
              parser.setInput(is, null);
            } catch (FileNotFoundException e11) {
              e = e11;
              eventType4 = -1;
              Log.m102w(TAG, e.toString());
              if (fi != null) {}
              if (is != null) {}
              return ret;
            } catch (IOException e12) {
              e = e12;
              eventType4 = -1;
              Log.m102w(TAG, e.toString());
              if (fi != null) {}
              if (is != null) {}
              return ret;
            } catch (XmlPullParserException e13) {
              e = e13;
              eventType4 = -1;
              Log.m102w(TAG, e.toString());
              if (fi != null) {}
              if (is != null) {}
              return ret;
            } catch (Throwable th6) {
              th = th6;
              if (fi != null) {}
              if (is == null) {}
            }
          } catch (FileNotFoundException e14) {
            e = e14;
          } catch (IOException e15) {
            e = e15;
          } catch (XmlPullParserException e16) {
            e = e16;
          } catch (Throwable th7) {
            th = th7;
          }
        }
        String TagValue = null;
        String TagName4 = null;
        eventType3 = parser.getEventType();
        boolean skipElement = false;
        while (true) {
          String featurePath2 = featurePath;
          if (eventType3 != 1) {
            break;
          }
          if (eventType3 == 2) {
            if (!skipElement) {
              try {
                if (semCscFeature.needToSkipElement(parser, deviceCountryISO, appendFeatureSet)) {
                  skipElement = true;
                  TagName4 = parser.getName();
                }
              } catch (FileNotFoundException e17) {
                e = e17;
                eventType4 = eventType3;
                TagName2 = TagName4;
                TagName3 = TagValue;
                Log.m102w(TAG, e.toString());
                if (fi != null) {}
                if (is != null) {}
                return ret;
              } catch (IOException e18) {
                e = e18;
                eventType4 = eventType3;
                TagName2 = TagName4;
                TagName3 = TagValue;
                Log.m102w(TAG, e.toString());
                if (fi != null) {}
                if (is != null) {}
                return ret;
              } catch (XmlPullParserException e19) {
                e = e19;
                eventType4 = eventType3;
                TagName2 = TagName4;
                TagName3 = TagValue;
                Log.m102w(TAG, e.toString());
                if (fi != null) {}
                if (is != null) {}
                return ret;
              } catch (Throwable th8) {
                th2 = th8;
                th = th2;
                if (fi != null) {}
                if (is == null) {}
              }
            }
            if (!skipElement) {
              TagName4 = parser.getName();
            }
          } else {
            if (eventType3 != 4) {
              TagName = TagName4;
              if (eventType3 == 3
                  && skipElement
                  && semCscFeature.isElementWithCountryISO(parser.getName())) {
                skipElement = false;
                TagName4 = TagName;
              }
            } else if (skipElement) {
              TagName = TagName4;
            } else {
              try {
                TagValue = parser.getText();
                if (TagValue != null) {
                  TagValue = TagValue.trim();
                }
                if (TextUtils.isEmpty(TagName4) || TextUtils.isEmpty(TagValue)) {
                  TagName = TagName4;
                } else {
                  try {
                    featureList.put(TagName4, TagValue);
                    TagName = TagName4;
                  } catch (Exception ex) {
                    TagName = TagName4;
                    try {
                      Log.m102w(TAG, ex.toString());
                    } catch (FileNotFoundException e20) {
                      e = e20;
                      eventType4 = eventType3;
                      TagName3 = TagValue;
                      TagName2 = TagName;
                      Log.m102w(TAG, e.toString());
                      if (fi != null) {}
                      if (is != null) {}
                      return ret;
                    } catch (IOException e21) {
                      e = e21;
                      eventType4 = eventType3;
                      TagName3 = TagValue;
                      TagName2 = TagName;
                      Log.m102w(TAG, e.toString());
                      if (fi != null) {}
                      if (is != null) {}
                      return ret;
                    } catch (XmlPullParserException e22) {
                      e = e22;
                      eventType4 = eventType3;
                      TagName3 = TagValue;
                      TagName2 = TagName;
                      Log.m102w(TAG, e.toString());
                      if (fi != null) {}
                      if (is != null) {}
                      return ret;
                    } catch (Throwable th9) {
                      th = th9;
                      if (fi != null) {}
                      if (is == null) {}
                    }
                  }
                }
              } catch (FileNotFoundException e23) {
                e = e23;
                String str2 = TagName4;
                eventType4 = eventType3;
                TagName3 = TagValue;
                TagName2 = str2;
              } catch (IOException e24) {
                e = e24;
                String str3 = TagName4;
                eventType4 = eventType3;
                TagName3 = TagValue;
                TagName2 = str3;
              } catch (XmlPullParserException e25) {
                e = e25;
                String str4 = TagName4;
                eventType4 = eventType3;
                TagName3 = TagValue;
                TagName2 = str4;
              } catch (Throwable th10) {
                th = th10;
              }
            }
            TagName4 = TagName;
          }
          try {
            try {
              eventType3 = parser.next();
              featurePath = featurePath2;
            } catch (IOException e26) {
              try {
                Log.m102w(TAG, e26.toString());
                semCscFeature = this;
                featurePath = featurePath2;
              } catch (IOException e27) {
                e = e27;
                eventType4 = eventType3;
                TagName2 = TagName4;
                TagName3 = TagValue;
                Log.m102w(TAG, e.toString());
                if (fi != null) {
                  fi.close();
                }
                if (is != null) {
                  is.close();
                }
                return ret;
              }
            }
          } catch (FileNotFoundException e28) {
            e = e28;
            eventType4 = eventType3;
            TagName2 = TagName4;
            TagName3 = TagValue;
            Log.m102w(TAG, e.toString());
            if (fi != null) {
              fi.close();
            }
            if (is != null) {
              is.close();
            }
            return ret;
          } catch (XmlPullParserException e29) {
            e = e29;
            eventType4 = eventType3;
            TagName2 = TagName4;
            TagName3 = TagValue;
            Log.m102w(TAG, e.toString());
            if (fi != null) {
              fi.close();
            }
            if (is != null) {
              is.close();
            }
            return ret;
          } catch (Throwable th11) {
            th2 = th11;
            th = th2;
            if (fi != null) {
              try {
                fi.close();
              } catch (IOException e30) {
                Log.m102w(TAG, e30.toString());
                throw th;
              }
            }
            if (is == null) {
              throw th;
            }
            is.close();
            throw th;
          }
        }
        fi.close();
        if (is != null) {
          is.close();
        }
        return ret;
      }
      File featureXmlFile3 = new File(featurePath + "/feature.xml");
      if (!featureXmlFile3.exists() || featureXmlFile3.length() <= 0) {
        featureXmlFile2 = new File(featurePath + "/others.xml");
        if (!featureXmlFile2.exists()) {
          eventType = -1;
        } else if (featureXmlFile2.length() <= 0) {
          eventType = -1;
        }
        if (!isFeatureForSlot2) {
          try {
            bLoadFeaure = false;
          } catch (FileNotFoundException e31) {
            e = e31;
            eventType4 = eventType;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (IOException e32) {
            e = e32;
            eventType4 = eventType;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (XmlPullParserException e33) {
            e = e33;
            eventType4 = eventType;
            Log.m102w(TAG, e.toString());
            if (fi != null) {}
            if (is != null) {}
            return ret;
          } catch (Throwable th12) {
            th = th12;
            if (fi != null) {}
            if (is == null) {}
          }
        }
        if (0 != 0) {
          try {
            fi.close();
          } catch (IOException e34) {
            Log.m102w(TAG, e34.toString());
          }
        }
        if (0 != 0) {
          is.close();
        }
        return false;
      }
      featureXmlFile2 = featureXmlFile3;
      XmlPullParserFactory factory2 = XmlPullParserFactory.newInstance();
      factory2.setNamespaceAware(true);
      XmlPullParser parser2 = factory2.newPullParser();
      fi = new FileInputStream(featureXmlFile2);
      if (semCscFeature.isXmlEncoded(featureXmlFile2)) {}
      String TagValue2 = null;
      String TagName42 = null;
      eventType3 = parser2.getEventType();
      boolean skipElement2 = false;
      while (true) {
        String featurePath22 = featurePath;
        if (eventType3 != 1) {}
      }
      fi.close();
      if (is != null) {}
      return ret;
    } catch (Throwable th13) {
      th = th13;
    }
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
