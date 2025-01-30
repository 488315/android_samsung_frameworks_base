package com.android.server.display;

import android.hardware.display.BrightnessChangeEvent;
import android.os.SystemClock;
import android.os.UserManager;
import android.util.Slog;
import android.util.Spline;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.power.PowerManagerUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class AdaptiveBrightnessStatsTracker {
  static final int MAX_DAYS_TO_TRACK = 7;
  public final AdaptiveBrightnessStats mAdaptiveBrightnessStats;
  public float[] mBucketBoundariesForStats;
  public float mCurrentAmbientLux;
  public float mCurrentScreenBrightness;
  public Spline mCurrentScreenBrightnessSpline;
  public int mCurrentUserId;
  public final Injector mInjector;
  public final Timer mTimer;
  public final UserManager mUserManager;

  interface Clock {
    long elapsedTimeMillis();
  }

  public AdaptiveBrightnessStatsTracker(
      UserManager userManager,
      Injector injector,
      BrightnessMappingStrategy brightnessMappingStrategy) {
    this.mUserManager = userManager;
    if (injector != null) {
      this.mInjector = injector;
    } else {
      this.mInjector = new Injector();
    }
    this.mAdaptiveBrightnessStats = new AdaptiveBrightnessStats(brightnessMappingStrategy);
    this.mTimer =
        new Timer(
            new Clock() { // from class:
                          // com.android.server.display.AdaptiveBrightnessStatsTracker$$ExternalSyntheticLambda0
              @Override // com.android.server.display.AdaptiveBrightnessStatsTracker.Clock
              public final long elapsedTimeMillis() {
                long lambda$new$0;
                lambda$new$0 = AdaptiveBrightnessStatsTracker.this.lambda$new$0();
                return lambda$new$0;
              }
            });
    this.mCurrentAmbientLux = -1.0f;
    initBucketBoundariesForStats();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ long lambda$new$0() {
    return this.mInjector.elapsedRealtimeMillis();
  }

  public synchronized void start(boolean z) {
    this.mTimer.reset();
    if (z) {
      this.mTimer.start();
    }
  }

  public synchronized void stop() {
    if (this.mTimer.isRunning()) {
      this.mAdaptiveBrightnessStats.log(
          this.mCurrentUserId,
          this.mInjector.getLocalDate(),
          this.mCurrentAmbientLux,
          this.mCurrentScreenBrightness,
          this.mTimer.totalDurationSec(),
          this.mCurrentScreenBrightnessSpline,
          null,
          null,
          false);
    }
    this.mTimer.reset();
    this.mCurrentAmbientLux = -1.0f;
  }

  public synchronized void resume() {
    if (!this.mTimer.isRunning()) {
      this.mTimer.start();
    } else if (this.mTimer.isPaused()) {
      this.mTimer.resume();
    }
  }

  public synchronized void pause() {
    this.mTimer.pause();
  }

  public synchronized void add(
      int i,
      float f,
      float f2,
      Spline spline,
      BrightnessChangeEvent brightnessChangeEvent,
      boolean z) {
    if (this.mTimer.isRunning()) {
      int i2 = this.mCurrentUserId;
      if (i == i2) {
        this.mAdaptiveBrightnessStats.log(
            i2,
            this.mInjector.getLocalDate(),
            this.mCurrentAmbientLux,
            this.mCurrentScreenBrightness,
            this.mTimer.totalDurationSec(),
            this.mCurrentScreenBrightnessSpline,
            brightnessChangeEvent,
            spline,
            z);
      } else {
        this.mCurrentUserId = i;
      }
      boolean z2 = !this.mTimer.isPaused();
      this.mTimer.reset();
      if (z2) {
        this.mTimer.start();
      }
    }
    this.mCurrentAmbientLux = f;
    this.mCurrentScreenBrightness = f2;
    this.mCurrentScreenBrightnessSpline = spline;
  }

  public synchronized void writeStats(OutputStream outputStream) {
    this.mAdaptiveBrightnessStats.writeToXML(outputStream);
  }

  public synchronized void readStats(InputStream inputStream) {
    this.mAdaptiveBrightnessStats.readFromXML(inputStream);
  }

  public synchronized AdaptiveBrightnessWeightStats getUserStats(int i) {
    return this.mAdaptiveBrightnessStats.getUserStats(i);
  }

  public synchronized void setMaxWeight(int i) {
    this.mAdaptiveBrightnessStats.setMaxWeight(i);
  }

  public synchronized void summarizeStats(int i) {
    this.mAdaptiveBrightnessStats.summarizeStats(i, this.mInjector.getLocalDate());
  }

  public synchronized void dump(PrintWriter printWriter) {
    printWriter.println("AdaptiveBrightnessStats:");
    printWriter.print(this.mAdaptiveBrightnessStats);
  }

  public final void initBucketBoundariesForStats() {
    int i;
    float[] fArr = {
      DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
      1.0f,
      2.0f,
      3.0f,
      4.0f,
      5.0f,
      6.0f,
      7.0f,
      9.0f,
      10.0f,
      12.0f,
      15.0f,
      18.0f,
      21.0f,
      26.0f,
      31.0f,
      37.0f,
      45.0f,
      53.0f,
      64.0f,
      77.0f,
      92.0f,
      111.0f,
      133.0f,
      160.0f,
      192.0f,
      230.0f,
      276.0f,
      331.0f,
      397.0f,
      477.0f,
      572.0f,
      687.0f,
      824.0f,
      989.0f,
      1187.0f,
      1424.0f,
      1709.0f,
      2051.0f,
      2461.0f,
      2953.0f,
      3544.0f,
      4253.0f,
      5103.0f,
      6124.0f,
      7349.0f,
      8819.0f,
      10582.0f,
      12699.0f,
      15239.0f,
      18286.0f,
      21944.0f,
      26332.0f,
      31599.0f,
      37918.0f,
      45502.0f
    };
    if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM) {
      i = 0;
      while (i < 56 && fArr[i] <= PowerManagerUtil.HBM_LUX) {
        i++;
      }
    } else {
      i = 55;
    }
    this.mBucketBoundariesForStats = Arrays.copyOf(fArr, i);
  }

  public synchronized void addDirectly(int i, float f, float f2, float f3, Spline spline) {
    int i2 = this.mCurrentUserId;
    if (i == i2) {
      this.mAdaptiveBrightnessStats.log(
          i2, this.mInjector.getLocalDate(), f, f2, f3, spline, null, null, false);
    }
  }

  public class AdaptiveBrightnessStats {
    public BrightnessMappingStrategy mBrightnessMapper;
    public Map mStats = new HashMap();

    public AdaptiveBrightnessStats(BrightnessMappingStrategy brightnessMappingStrategy) {
      this.mBrightnessMapper = brightnessMappingStrategy;
    }

    public void log(
        int i,
        LocalDate localDate,
        float f,
        float f2,
        float f3,
        Spline spline,
        BrightnessChangeEvent brightnessChangeEvent,
        Spline spline2,
        boolean z) {
      getOrCreateUserStats(this.mStats, i)
          .log(f, f2, f3, spline, brightnessChangeEvent, spline2, z);
    }

    public AdaptiveBrightnessWeightStats getUserStats(int i) {
      if (this.mStats.containsKey(Integer.valueOf(i))) {
        return (AdaptiveBrightnessWeightStats) this.mStats.get(Integer.valueOf(i));
      }
      return null;
    }

    public void setMaxWeight(int i) {
      if (getUserStats(i) == null) {
        getOrCreateUserStats(this.mStats, i).setMaxWeight();
      } else {
        Slog.e("AdaptiveBrightnessStatsTracker", "setMaxWeight: shoudn't be here");
      }
    }

    public void summarizeStats(int i, LocalDate localDate) {
      getOrCreateUserStats(this.mStats, i).summarizeStats();
    }

    public void writeToXML(OutputStream outputStream) {
      FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
      fastXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
      fastXmlSerializer.startDocument(null, Boolean.TRUE);
      fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
      Slog.d("AdaptiveBrightnessStatsTracker", "writeToXML start");
      fastXmlSerializer.startTag(null, "SBS-stats");
      Iterator it = this.mStats.entrySet().iterator();
      while (it.hasNext()) {
        int userSerialNumber =
            AdaptiveBrightnessStatsTracker.this.mInjector.getUserSerialNumber(
                AdaptiveBrightnessStatsTracker.this.mUserManager,
                ((Integer) ((Map.Entry) it.next()).getKey()).intValue());
        Slog.d("AdaptiveBrightnessStatsTracker", "userSerialNumber = " + userSerialNumber);
        if (userSerialNumber != -1) {
          AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats =
              (AdaptiveBrightnessWeightStats) this.mStats.get(Integer.valueOf(userSerialNumber));
          fastXmlSerializer.startTag(null, "fixed-lux-zone");
          fastXmlSerializer.attribute(null, "user", Integer.toString(userSerialNumber));
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < adaptiveBrightnessWeightStats.getBucketBoundaries().length; i++) {
            if (i > 0) {
              sb.append(",");
            }
            sb.append(adaptiveBrightnessWeightStats.getBucketBoundaries()[i]);
          }
          fastXmlSerializer.attribute(null, "bucket-boundaries", sb.toString());
          fastXmlSerializer.endTag(null, "fixed-lux-zone");
          for (int i2 = 0; i2 < adaptiveBrightnessWeightStats.getStats().length; i2++) {
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            StringBuilder sb5 = new StringBuilder();
            fastXmlSerializer.startTag(null, "adaptive-brightness-weight-stats");
            sb2.append(adaptiveBrightnessWeightStats.getStats()[i2].getLux());
            sb3.append(adaptiveBrightnessWeightStats.getStats()[i2].getBrightness());
            sb4.append(adaptiveBrightnessWeightStats.getStats()[i2].getWeight());
            sb5.append(adaptiveBrightnessWeightStats.getStats()[i2].getLastUserBrightnessTime());
            fastXmlSerializer.attribute(null, "lux", sb2.toString());
            fastXmlSerializer.attribute(null, "brightness-learned", sb3.toString());
            fastXmlSerializer.attribute(null, "weight", sb4.toString());
            fastXmlSerializer.attribute(null, "last-user-brighntess-time", sb5.toString());
            Slog.d(
                "AdaptiveBrightnessStatsTracker",
                "writeToXML: ["
                    + i2
                    + "] l="
                    + sb2.toString()
                    + ", br="
                    + sb3.toString()
                    + ", w="
                    + sb4.toString()
                    + ", t="
                    + sb5.toString());
            fastXmlSerializer.endTag(null, "adaptive-brightness-weight-stats");
          }
          fastXmlSerializer.startTag(null, "END");
          fastXmlSerializer.endTag(null, "END");
        }
      }
      fastXmlSerializer.endTag(null, "SBS-stats");
      fastXmlSerializer.endDocument();
      Slog.d("AdaptiveBrightnessStatsTracker", "writeToXML done");
      outputStream.flush();
    }

    public void readFromXML(InputStream inputStream) {
      int next;
      int i;
      String str;
      XmlPullParser xmlPullParser;
      int i2;
      AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr;
      String str2 = "][";
      try {
        HashMap hashMap = new HashMap();
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        do {
          next = newPullParser.next();
          i = 1;
          if (next == 1) {
            break;
          }
        } while (next != 2);
        String name = newPullParser.getName();
        if (!"SBS-stats".equals(name)) {
          throw new XmlPullParserException(
              "Ambient brightness stats not found in tracker file " + name);
        }
        int depth = newPullParser.getDepth();
        newPullParser.next();
        int i3 = -1;
        String str3 = null;
        int i4 = -1;
        AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr2 = null;
        int i5 = 0;
        int i6 = 0;
        while (true) {
          int next2 = newPullParser.next();
          if (next2 == i || (next2 == 3 && newPullParser.getDepth() <= depth)) {
            break;
          }
          if (next2 != 3 && next2 != 4) {
            String name2 = newPullParser.getName();
            if ("fixed-lux-zone".equals(name2)) {
              String attributeValue = newPullParser.getAttributeValue(str3, "user");
              String[] split =
                  newPullParser.getAttributeValue(str3, "bucket-boundaries").split(",");
              if (split != null) {
                int length = split.length;
                int userId =
                    AdaptiveBrightnessStatsTracker.this.mInjector.getUserId(
                        AdaptiveBrightnessStatsTracker.this.mUserManager,
                        Integer.parseInt(attributeValue));
                Slog.d(
                    "AdaptiveBrightnessStatsTracker",
                    "TAG_LUX_STATS ["
                        + userId
                        + str2
                        + length
                        + str2
                        + AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats.length
                        + "]");
                if (length == 0
                    || userId == i3
                    || length
                        != AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats.length) {
                  i6 = length;
                  i4 = userId;
                } else {
                  brightnessWeightsArr2 =
                      new AdaptiveBrightnessWeightStats.BrightnessWeights[length];
                  i6 = length;
                  i4 = userId;
                  i5 = 0;
                }
              }
            }
            if ("adaptive-brightness-weight-stats".equals(name2)) {
              String attributeValue2 = newPullParser.getAttributeValue(null, "lux");
              String attributeValue3 = newPullParser.getAttributeValue(null, "brightness-learned");
              String attributeValue4 = newPullParser.getAttributeValue(null, "weight");
              str = str2;
              String attributeValue5 =
                  newPullParser.getAttributeValue(null, "last-user-brighntess-time");
              float parseFloat = Float.parseFloat(attributeValue2);
              float parseFloat2 = Float.parseFloat(attributeValue3);
              float parseFloat3 = Float.parseFloat(attributeValue4);
              brightnessWeightsArr = brightnessWeightsArr2;
              long parseLong = Long.parseLong(attributeValue5);
              StringBuilder sb = new StringBuilder();
              xmlPullParser = newPullParser;
              sb.append("readFromXML: [");
              sb.append(i5);
              sb.append("] l = ");
              sb.append(parseFloat);
              sb.append(", br = ");
              sb.append(parseFloat2);
              sb.append(", w = ");
              sb.append(parseFloat3);
              sb.append(", t = ");
              sb.append(parseLong);
              Slog.d("AdaptiveBrightnessStatsTracker", sb.toString());
              if (brightnessWeightsArr != null && i5 < i6) {
                brightnessWeightsArr[i5] =
                    new AdaptiveBrightnessWeightStats.BrightnessWeights(
                        parseFloat, parseFloat2, parseFloat3, parseLong);
                i5++;
              }
            } else {
              str = str2;
              xmlPullParser = newPullParser;
              brightnessWeightsArr = brightnessWeightsArr2;
            }
            if ("END".equals(name2)) {
              i2 = -1;
              if (i4 == -1 || brightnessWeightsArr == null) {
                brightnessWeightsArr2 = brightnessWeightsArr;
              } else {
                Slog.d("AdaptiveBrightnessStatsTracker", "getOrCreateUserStats from xml");
                brightnessWeightsArr2 = brightnessWeightsArr;
                getOrCreateUserStats(hashMap, brightnessWeightsArr2, i4);
              }
            } else {
              brightnessWeightsArr2 = brightnessWeightsArr;
              i2 = -1;
            }
            i3 = i2;
            str2 = str;
            newPullParser = xmlPullParser;
            i = 1;
            str3 = null;
          }
          str = str2;
          xmlPullParser = newPullParser;
          i2 = i3;
          i3 = i2;
          str2 = str;
          newPullParser = xmlPullParser;
          i = 1;
          str3 = null;
        }
        this.mStats = hashMap;
      } catch (IOException
          | NullPointerException
          | NumberFormatException
          | DateTimeParseException
          | XmlPullParserException e) {
        throw new IOException("Failed to parse brightness stats file.", e);
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (Map.Entry entry : this.mStats.entrySet()) {
        AdaptiveBrightnessWeightStats adaptiveBrightnessWeightStats =
            (AdaptiveBrightnessWeightStats) entry.getValue();
        sb.append("  ");
        sb.append(entry.getKey());
        sb.append(" ");
        sb.append(adaptiveBrightnessWeightStats);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      }
      return sb.toString();
    }

    public final AdaptiveBrightnessWeightStats getOrCreateUserStats(Map map, int i) {
      if (!map.containsKey(Integer.valueOf(i))) {
        map.put(
            Integer.valueOf(i),
            new AdaptiveBrightnessWeightStats(
                AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats,
                this.mBrightnessMapper));
      }
      return (AdaptiveBrightnessWeightStats) map.get(Integer.valueOf(i));
    }

    public final AdaptiveBrightnessWeightStats getOrCreateUserStats(
        Map map, AdaptiveBrightnessWeightStats.BrightnessWeights[] brightnessWeightsArr, int i) {
      if (!map.containsKey(Integer.valueOf(i))) {
        map.put(
            Integer.valueOf(i),
            new AdaptiveBrightnessWeightStats(
                AdaptiveBrightnessStatsTracker.this.mBucketBoundariesForStats,
                brightnessWeightsArr,
                this.mBrightnessMapper));
      }
      return (AdaptiveBrightnessWeightStats) map.get(Integer.valueOf(i));
    }
  }

  class Timer {
    public final Clock clock;
    public boolean paused;
    public long startTimeMillis;
    public boolean started;
    public float totalDurationSec;

    public Timer(Clock clock) {
      this.clock = clock;
    }

    public void reset() {
      this.started = false;
      this.paused = false;
      this.totalDurationSec = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    }

    public void start() {
      if (this.started) {
        return;
      }
      this.startTimeMillis = this.clock.elapsedTimeMillis();
      this.started = true;
    }

    public void pause() {
      if (!this.started || this.paused) {
        return;
      }
      this.totalDurationSec +=
          (float) ((this.clock.elapsedTimeMillis() - this.startTimeMillis) / 1000.0d);
      this.paused = true;
    }

    public void resume() {
      if (this.started && this.paused) {
        this.startTimeMillis = this.clock.elapsedTimeMillis();
        this.paused = false;
      }
    }

    public boolean isRunning() {
      return this.started;
    }

    public boolean isPaused() {
      return this.paused;
    }

    public float totalDurationSec() {
      if (!this.started) {
        return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      }
      if (!this.paused) {
        this.totalDurationSec +=
            (float) ((this.clock.elapsedTimeMillis() - this.startTimeMillis) / 1000.0d);
        this.startTimeMillis = this.clock.elapsedTimeMillis();
      }
      return this.totalDurationSec;
    }
  }

  class Injector {
    public long elapsedRealtimeMillis() {
      return SystemClock.elapsedRealtime();
    }

    public int getUserSerialNumber(UserManager userManager, int i) {
      return userManager.getUserSerialNumber(i);
    }

    public int getUserId(UserManager userManager, int i) {
      return userManager.getUserHandle(i);
    }

    public LocalDate getLocalDate() {
      return LocalDate.now();
    }
  }
}
