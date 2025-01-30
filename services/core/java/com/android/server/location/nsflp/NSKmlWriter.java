package com.android.server.location.nsflp;

import android.util.Log;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes2.dex */
public class NSKmlWriter {
  public static double sThirtyRadian = Math.toRadians(30.0d);
  public static double sSixtyRadian = Math.toRadians(90.0d);
  public final List mNmeaKmlInfoList = new ArrayList();
  public final Map mPositionKmlInfoMap = new HashMap();
  public final List mDebugInfoList = new ArrayList();

  public final String getHeader() {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
               + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
               + "<Document>\n"
               + "<name>UtLogGenerator</name><open>1</open>\n"
               + "<Style id=\"lineStyleRed\"><LineStyle><color>ff0000ff</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleYellow\"><LineStyle><color>ff00fffd</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleGreen\"><LineStyle><color>ff008000</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStylePurple\"><LineStyle><color>ff800080</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleFuchsia\"><LineStyle><color>ffff00ff</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleNavy\"><LineStyle><color>ff000080</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleBlue\"><LineStyle><color>ff0000ff</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleTeal\"><LineStyle><color>ff008080</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style"
               + " id=\"lineStyleOlive\"><LineStyle><color>ff808000</color><width>1.5</width>\n"
               + "</LineStyle></Style>\n"
               + "<Style id=\"pointStyleRed\"><LabelStyle><color>00ffffff</color></LabelStyle>"
               + " <IconStyle><color>ff0000ff</color>\n"
               + "<scale>0.5</scale>\"<Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_square.png</href></Icon></IconStyle>\n"
               + "</Style>\n"
               + "<Style id=\"pointStyleYellow\"><LabelStyle><color>00ffffff</color></LabelStyle>"
               + " <IconStyle><color>ff00fffd</color>\n"
               + "<scale>0.5</scale>\"<Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_square.png</href></Icon></IconStyle>\n"
               + "</Style>\n"
               + "<Style id=\"pointStyleGreen\"><LabelStyle><color>00ffffff</color></LabelStyle>"
               + " <IconStyle><color>ff008000</color>\n"
               + "<scale>0.5</scale>\"<Icon><href>http://maps.google.com/mapfiles/kml/shapes/placemark_square.png</href></Icon></IconStyle>\n"
               + "</Style>\n"
               + "<Style"
               + " id=\"sn_wht-diamond\"><IconStyle><color>ffff0000</color><scale>1.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/paddle/wht-diamond.png</href></Icon></IconStyle></Style><Style"
               + " id=\"sn_forbidden\"><IconStyle><color>ffff0c00</color><scale>1.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/forbidden.png</href></Icon></IconStyle></Style><Style"
               + " id=\"sn_arrow\"><IconStyle><color>ffff0c00</color><scale>1.0</scale><Icon><href>http://maps.google.com/mapfiles/kml/shapes/arrow.png</href></Icon></IconStyle></Style>";
  }

  public final String getContents(int i, File file) {
    parseFile(i, file);
    StringBuilder sb = new StringBuilder();
    sb.append(getHeader());
    if (i == 1) {
      sb.append(getNmeaKmlBody());
      this.mNmeaKmlInfoList.clear();
    } else {
      Iterator it = this.mPositionKmlInfoMap.keySet().iterator();
      while (it.hasNext()) {
        sb.append(getPositionKmlBody((String) it.next()));
      }
      this.mPositionKmlInfoMap.clear();
      this.mDebugInfoList.clear();
    }
    sb.append("</Document></kml>");
    return sb.toString();
  }

  public boolean createKmzFile(int i, File file) {
    String contents = getContents(i, file);
    String replace = file.toString().replace(".txt", ".kml");
    File file2 = new File(replace);
    NSUtLogger.makeFile(file2);
    try {
      if (!NSUtLogger.writeFile(file2, contents.getBytes(StandardCharsets.UTF_8), false)) {
        return false;
      }
      try {
        createZipFile(file2, new File(replace.replace(".kml", ".kmz")));
        NSUtLogger.deleteFile(file2);
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
    } catch (StringIndexOutOfBoundsException e2) {
      e2.printStackTrace();
      return false;
    }
  }

  public final String getCourseArrow(KmlInfo kmlInfo) {
    if (kmlInfo.getSpeed() <= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return "";
    }
    double radians = Math.toRadians(kmlInfo.getCourse());
    double speed = (kmlInfo.getSpeed() / 2.0d) / 100000.0d;
    double longitude = kmlInfo.getLongitude() + (Math.sin(radians) * speed);
    double latitude = kmlInfo.getLatitude() + (Math.cos(radians) * speed);
    double d = speed / 5.0d;
    double d2 = radians + 1.0d;
    String str =
        kmlInfo.getLongitude()
            + ","
            + kmlInfo.getLatitude()
            + ",0.0 "
            + longitude
            + ","
            + latitude
            + ",0.0 "
            + (longitude - (Math.sin(d2 - sThirtyRadian) * d))
            + ","
            + (latitude - (Math.cos(d2 - sThirtyRadian) * d))
            + ",0.0 "
            + (longitude - (Math.sin(d2 - sSixtyRadian) * d))
            + ","
            + (latitude - (d * Math.cos(d2 - sSixtyRadian)))
            + ",0.0 "
            + longitude
            + ","
            + latitude
            + ",0.0";
    StringBuilder sb = new StringBuilder();
    sb.append("<LineString><coordinates>\n");
    sb.append(str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    sb.append("</coordinates></LineString>\n");
    return sb.toString();
  }

  public final String getNmeaKmlBody() {
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    sb2.append("<Folder id=\"Data\">\n<name>NMEA</name>\n");
    sb2.append("<Placemark>\n<name>Line</name><styleUrl>#lineStyleRed</styleUrl>");
    sb2.append("<LineString>\n");
    sb2.append("<extrude>1</extrude>\n");
    sb2.append("<tessellate>1</tessellate>");
    sb2.append("<coordinates>\n");
    int i = 0;
    for (KmlInfo kmlInfo : this.mNmeaKmlInfoList) {
      sb2.append(kmlInfo.getLongitude());
      sb2.append(',');
      sb2.append(kmlInfo.getLatitude());
      sb2.append('\n');
      i++;
      sb.append("<Placemark>\n<name>P");
      sb.append(i);
      sb.append(" - ");
      sb.append(getHeadTime(kmlInfo.getTime()));
      sb.append("</name>");
      sb.append("<styleUrl>#pointStyleRed</styleUrl>");
      sb.append("<TimeStamp><when>");
      sb.append(getKmlTime(kmlInfo.getTime()));
      sb.append("</when></TimeStamp>");
      sb.append("<description>latitude=");
      sb.append(kmlInfo.getLatitude());
      sb.append(" longitude=");
      sb.append(kmlInfo.getLongitude());
      sb.append(" speed=");
      sb.append(kmlInfo.getSpeed());
      sb.append(" bearing=");
      sb.append(kmlInfo.getCourse());
      sb.append("</description>\n");
      sb.append("<MultiGeometry>\n");
      sb.append("<Point><coordinates>");
      sb.append(kmlInfo.getLongitude());
      sb.append(",");
      sb.append(kmlInfo.getLatitude());
      sb.append(",0 \n");
      sb.append("</coordinates></Point>\n");
      sb.append(getCourseArrow(kmlInfo));
      sb.append("</MultiGeometry>\n");
      sb.append("</Placemark>\n");
    }
    sb.append("</Folder>");
    sb2.append("</coordinates>\n</LineString>\n</Placemark>");
    sb2.append((CharSequence) sb);
    return sb2.toString();
  }

  public final String getPositionKmlBody(String str) {
    String str2;
    String str3;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    StringBuilder sb3 = new StringBuilder();
    StringBuilder sb4 = new StringBuilder();
    String style = getStyle(str, true);
    int i = 0;
    String style2 = getStyle(str, false);
    sb2.append("<Folder id=\"Data\">\n<name>");
    sb2.append(str);
    String str4 = "</name>";
    sb2.append("</name>");
    sb2.append("<Placemark>\n<name>Line</name><styleUrl>");
    sb2.append(style);
    String str5 = "</styleUrl>";
    sb2.append("</styleUrl>");
    sb2.append("<LineString>\n");
    sb2.append("<extrude>1</extrude>\n");
    sb2.append("<tessellate>1</tessellate>");
    sb2.append("<coordinates>\n");
    HashMap hashMap = new HashMap();
    List<KmlInfo> list = (List) this.mPositionKmlInfoMap.get(str);
    Objects.requireNonNull(list);
    for (KmlInfo kmlInfo : list) {
      String str6 = str5;
      sb2.append(kmlInfo.getLongitude());
      sb2.append(',');
      sb2.append(kmlInfo.getLatitude());
      sb2.append('\n');
      int i2 = i + 1;
      sb.append("<Placemark>\n<name>P");
      sb.append(i2);
      sb.append(" - ");
      sb.append(kmlInfo.getTime());
      sb.append(str4);
      sb.append("<styleUrl>");
      sb.append(style2);
      sb.append(str6);
      sb.append("<TimeStamp><when>");
      sb.append(getKmlTimeFromPosLog(kmlInfo.getTime()));
      sb.append("</when></TimeStamp>");
      sb.append("<description>latitude=");
      sb.append(kmlInfo.getLatitude());
      sb.append(" longitude=");
      sb.append(kmlInfo.getLongitude());
      sb.append(" speed=");
      sb.append(kmlInfo.getSpeed());
      sb.append(" bearing=");
      sb.append(kmlInfo.getCourse());
      sb.append("</description>\n");
      sb.append("<MultiGeometry>\n");
      sb.append("<Point><coordinates>");
      sb.append(kmlInfo.getLongitude());
      sb.append(",");
      sb.append(kmlInfo.getLatitude());
      sb.append(",0 \n");
      sb.append("</coordinates></Point>\n");
      sb.append(getCourseArrow(kmlInfo));
      sb.append("</MultiGeometry>\n");
      sb.append("</Placemark>\n");
      if ("gps".equals(str)) {
        SATELLITE_STATE satelliteState = kmlInfo.getSatelliteState();
        StringBuilder sb5 = new StringBuilder();
        str2 = style2;
        str3 = str4;
        sb5.append(kmlInfo.getLongitude());
        sb5.append(",");
        sb5.append(kmlInfo.getLatitude());
        ((List)
                hashMap.computeIfAbsent(
                    satelliteState,
                    new Function() { // from class:
                                     // com.android.server.location.nsflp.NSKmlWriter$$ExternalSyntheticLambda0
                      @Override // java.util.function.Function
                      public final Object apply(Object obj) {
                        List lambda$getPositionKmlBody$0;
                        lambda$getPositionKmlBody$0 =
                            NSKmlWriter.lambda$getPositionKmlBody$0(
                                (NSKmlWriter.SATELLITE_STATE) obj);
                        return lambda$getPositionKmlBody$0;
                      }
                    }))
            .add(sb5.toString());
      } else {
        str2 = style2;
        str3 = str4;
      }
      str5 = str6;
      i = i2;
      style2 = str2;
      str4 = str3;
    }
    sb.append("</Folder>");
    sb2.append("</coordinates>\n</LineString>\n</Placemark>");
    if ("gps".equals(str)) {
      if (!this.mDebugInfoList.isEmpty()) {
        updateDebugBody(sb4);
      }
      if (!hashMap.isEmpty()) {
        updateSatelliteBody(hashMap, sb3);
      }
    }
    sb2.append((CharSequence) sb);
    sb2.append((CharSequence) sb3);
    sb2.append((CharSequence) sb4);
    return sb2.toString();
  }

  public static /* synthetic */ List lambda$getPositionKmlBody$0(SATELLITE_STATE satellite_state) {
    return new LinkedList();
  }

  public final String getStyle(String str, boolean z) {
    return "gps".equals(str)
        ? z ? "#lineStyleRed" : "#pointStyleRed"
        : "network".equals(str)
            ? z ? "#lineStyleYellow" : "#pointStyleYellow"
            : z ? "#lineStyleGreen" : "#pointStyleGreen";
  }

  public final String getSignalLineStyle(SATELLITE_STATE satellite_state) {
    return SATELLITE_STATE.OUTDOOR.equals(satellite_state)
        ? "#lineStylePurple"
        : SATELLITE_STATE.MILD_INDOOR.equals(satellite_state)
            ? "#lineStyleFuchsia"
            : SATELLITE_STATE.DEEP_INDOOR.equals(satellite_state)
                ? "#lineStyleNavy"
                : SATELLITE_STATE.SHADOW.equals(satellite_state)
                    ? "#lineStyleBlue"
                    : SATELLITE_STATE.NO_SATELLITE.equals(satellite_state)
                        ? "#lineStyleTeal"
                        : "#lineStyleOlive";
  }

  public final void parseFile(int i, File file) {
    KmlInfo nmeaKmlInfo;
    try {
      BufferedReader bufferedReader =
          new BufferedReader(
              new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            bufferedReader.close();
            return;
          }
          if (i == 1) {
            if (isRMC(readLine) && (nmeaKmlInfo = getNmeaKmlInfo(readLine.split("[,*]"))) != null) {
              this.mNmeaKmlInfoList.add(nmeaKmlInfo);
            }
          } else {
            String[] split = readLine.split("[,*]");
            if (split.length >= 2) {
              String str = split[1];
              if (!"FIRST_FIX".equals(str) && !"ENGINE_OFF".equals(str)) {
                KmlInfo positionKmlInfo = getPositionKmlInfo(split);
                if (positionKmlInfo != null) {
                  ((List)
                          this.mPositionKmlInfoMap.computeIfAbsent(
                              positionKmlInfo.getProvider(),
                              new Function() { // from class:
                                               // com.android.server.location.nsflp.NSKmlWriter$$ExternalSyntheticLambda1
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                  List lambda$parseFile$1;
                                  lambda$parseFile$1 = NSKmlWriter.lambda$parseFile$1((String) obj);
                                  return lambda$parseFile$1;
                                }
                              }))
                      .add(positionKmlInfo);
                }
              }
              DebugInfo debugKmlInfo = getDebugKmlInfo(split);
              if (debugKmlInfo != null) {
                this.mDebugInfoList.add(debugKmlInfo);
              }
            }
          }
        } finally {
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static /* synthetic */ List lambda$parseFile$1(String str) {
    return new ArrayList();
  }

  public final KmlInfo getPositionKmlInfo(String[] strArr) {
    int parseInt;
    int length = strArr.length;
    SATELLITE_STATE satellite_state = null;
    if (length < 8) {
      Log.e("NSKmlWriter", "getPositionKmlInfo, wrong data length : " + length);
      return null;
    }
    String str = strArr[0];
    String str2 = strArr[1];
    double parseDouble = Double.parseDouble(strArr[2]);
    double parseDouble2 = Double.parseDouble(strArr[3]);
    float parseFloat = Float.parseFloat(strArr[5]);
    float parseFloat2 = Float.parseFloat(strArr[7]);
    if ("gps".equals(str2)
        && length > 8
        && (parseInt = Integer.parseInt(strArr[8])) >= 0
        && parseInt < SATELLITE_STATE.values().length) {
      satellite_state = SATELLITE_STATE.values()[parseInt];
    }
    return new KmlInfo(
        str2, parseDouble, parseDouble2, parseFloat, parseFloat2, str, satellite_state);
  }

  public final DebugInfo getDebugKmlInfo(String[] strArr) {
    String str;
    int length = strArr.length;
    if (length < 8) {
      Log.e("NSKmlWriter", "getDebugKmlInfo, wrong data length : " + length);
      return null;
    }
    String str2 = strArr[0];
    String str3 = strArr[1];
    long parseLong = Long.parseLong(strArr[2]);
    String str4 = strArr[3];
    String str5 = strArr[4];
    String str6 = strArr[5];
    String str7 = strArr[6];
    String str8 = strArr[8];
    if (!"ENGINE_OFF".equals(str3) || length <= 10) {
      str = str8;
    } else {
      str = str8 + "/" + strArr[10];
    }
    return new DebugInfo(str2, str3, parseLong, str4, str5, str6, str7, str);
  }

  public final void createZipFile(File file, File file2) {
    FileOutputStream fileOutputStream = new FileOutputStream(file2);
    try {
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
      try {
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        try {
          FileInputStream fileInputStream = new FileInputStream(file);
          try {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            byte[] bArr = new byte[1024];
            while (true) {
              int read = fileInputStream.read(bArr);
              if (read > 0) {
                zipOutputStream.write(bArr, 0, read);
              } else {
                zipOutputStream.closeEntry();
                zipOutputStream.finish();
                fileInputStream.close();
                zipOutputStream.close();
                bufferedOutputStream.close();
                fileOutputStream.close();
                return;
              }
            }
          } finally {
          }
        } finally {
        }
      } finally {
      }
    } catch (Throwable th) {
      try {
        fileOutputStream.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  public final KmlInfo getNmeaKmlInfo(String[] strArr) {
    if (strArr.length < 9 || strArr[1].length() < 8 || !"A".equals(strArr[2].toUpperCase())) {
      return null;
    }
    double parseDouble = Double.parseDouble(strArr[3]);
    String upperCase = strArr[4].toUpperCase();
    double parseDouble2 = Double.parseDouble(strArr[5]);
    String upperCase2 = strArr[6].toUpperCase();
    boolean isEmpty = strArr[7].isEmpty();
    float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    float parseFloat = isEmpty ? 0.0f : Float.parseFloat(strArr[7]) * 1.852f;
    if (!strArr[8].isEmpty()) {
      f = Float.parseFloat(strArr[8]);
    }
    float f2 = f;
    String str = strArr[9];
    String substring = strArr[1].substring(0, 8);
    if (substring.contains(".")) {
      substring = roundsUtcTime(substring);
    }
    double d = parseDouble * 0.01d;
    double d2 = (int) d;
    double d3 = parseDouble2 * 0.01d;
    double d4 = (int) d3;
    return new KmlInfo(
        "gps",
        ("S".equals(upperCase) ? -1.0d : 1.0d) * (((d - d2) * 1.6666666666666667d) + d2),
        (((d3 - d4) * 1.6666666666666667d) + d4) * ("W".equals(upperCase2) ? -1.0d : 1.0d),
        parseFloat,
        f2,
        substring + str,
        null);
  }

  public final boolean isRMC(String str) {
    if (str == null || str.length() == 0) {
      return false;
    }
    return str.startsWith("RMC", 3);
  }

  public final String roundsUtcTime(String str) {
    try {
      Calendar calendar = Calendar.getInstance();
      Date parse = new SimpleDateFormat("HHmmss", Locale.getDefault()).parse(str);
      Objects.requireNonNull(parse);
      Date date = parse;
      calendar.setTime(parse);
      if (Integer.parseInt(str.substring(7, 8)) >= 5) {
        calendar.add(13, 1);
      }
      return String.format(
          Locale.getDefault(),
          "%02d%02d%02d",
          Integer.valueOf(calendar.get(11)),
          Integer.valueOf(calendar.get(12)),
          Integer.valueOf(calendar.get(13)));
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  public final String getHeadTime(String str) {
    try {
      Calendar calendar = Calendar.getInstance();
      Date parse = new SimpleDateFormat("HHmmssddMMyy", Locale.getDefault()).parse(str);
      Objects.requireNonNull(parse);
      Date date = parse;
      calendar.setTime(parse);
      return String.format(
          Locale.getDefault(),
          "UTC %04d/%02d/%02d, %02d:%02d:%02d",
          Integer.valueOf(calendar.get(1)),
          Integer.valueOf(calendar.get(2) + 1),
          Integer.valueOf(calendar.get(5)),
          Integer.valueOf(calendar.get(11)),
          Integer.valueOf(calendar.get(12)),
          Integer.valueOf(calendar.get(13)));
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  public final String getKmlTime(String str) {
    try {
      Calendar calendar = Calendar.getInstance();
      Date parse = new SimpleDateFormat("HHmmssddMMyy", Locale.getDefault()).parse(str);
      Objects.requireNonNull(parse);
      Date date = parse;
      calendar.setTime(parse);
      return String.format(
          Locale.getDefault(),
          "%04d-%02d-%02dT%02d:%02d:%02d",
          Integer.valueOf(calendar.get(1)),
          Integer.valueOf(calendar.get(2) + 1),
          Integer.valueOf(calendar.get(5)),
          Integer.valueOf(calendar.get(11)),
          Integer.valueOf(calendar.get(12)),
          Integer.valueOf(calendar.get(13)));
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  public final String getKmlTimeFromPosLog(String str) {
    try {
      Calendar calendar = Calendar.getInstance();
      int i = calendar.get(1);
      Date parse = new SimpleDateFormat("MM-dd.HH:mm:ss.SSS", Locale.getDefault()).parse(str);
      Objects.requireNonNull(parse);
      Date date = parse;
      calendar.setTime(parse);
      calendar.set(1, i);
      return String.format(
          Locale.getDefault(),
          "%04d-%02d-%02dT%02d:%02d:%02d",
          Integer.valueOf(calendar.get(1)),
          Integer.valueOf(calendar.get(2) + 1),
          Integer.valueOf(calendar.get(5)),
          Integer.valueOf(calendar.get(11)),
          Integer.valueOf(calendar.get(12)),
          Integer.valueOf(calendar.get(13)));
    } catch (ParseException e) {
      e.printStackTrace();
      return "";
    }
  }

  public final void updateSatelliteBody(Map map, StringBuilder sb) {
    sb.append("<Folder>\n<name>Signal</name>");
    for (Map.Entry entry : map.entrySet()) {
      List list = (List) entry.getValue();
      if (list != null) {
        SATELLITE_STATE satellite_state = (SATELLITE_STATE) entry.getKey();
        sb.append("<Placemark>\n<name>");
        sb.append(satellite_state);
        sb.append("</name>");
        sb.append("<visibility>0</visibility>");
        sb.append("<styleUrl>");
        sb.append(getSignalLineStyle(satellite_state));
        sb.append("</styleUrl>");
        sb.append("<LineString>\n");
        sb.append("<extrude>1</extrude>\n");
        sb.append("<tessellate>1</tessellate>");
        sb.append("<coordinates>\n");
        sb.append(list.toString().replace("[", "").replace("]", ""));
        sb.append("</coordinates>\n</LineString></Placemark>");
      }
    }
    sb.append("</Folder>");
  }

  public final void updateDebugBody(StringBuilder sb) {
    sb.append("<Folder>\n<name>Debug</name>");
    Iterator it = this.mDebugInfoList.iterator();
    while (it.hasNext()) {
      DebugInfo debugInfo = (DebugInfo) it.next();
      Iterator it2 = it;
      if ("FIRST_FIX".equals(debugInfo.type)) {
        sb.append("<Placemark>\n<name>");
        sb.append('#');
        sb.append(debugInfo.sessionNumber);
        sb.append(' ');
        sb.append(debugInfo.type);
        sb.append(" - ");
        sb.append(debugInfo.receivedTime);
        sb.append("</name>");
        sb.append("<description>\n");
        sb.append("<![CDATA[<div style=\"white-space: nowrap;\">\n");
        sb.append("<p><b>* Session start time : ");
        sb.append(debugInfo.time);
        sb.append("</b></p>\n");
        sb.append("<p><b>** Location history around 1sec based on engine on</b></p>\n");
        for (String str : debugInfo.message.split("\\|")) {
          sb.append("<p>");
          sb.append(str);
          sb.append("</p>");
        }
        sb.append("</div>]]></description>");
        sb.append("<styleUrl>");
        sb.append("#sn_wht-diamond");
        sb.append("</styleUrl>");
        sb.append("<Point><gx:drawOrder>1</gx:drawOrder>");
        sb.append("<coordinates>");
        sb.append(debugInfo.longitude);
        sb.append(',');
        sb.append(debugInfo.latitude);
        sb.append('0');
        sb.append("</coordinates></Point>");
        sb.append("</Placemark>");
      } else if ("ENGINE_OFF".equals(debugInfo.type)) {
        String[] split = debugInfo.message.split("/");
        if (split.length >= 2) {
          String str2 = split[0];
          String str3 = split[1];
          sb.append("<Placemark>\n<name>");
          sb.append('#');
          sb.append(debugInfo.sessionNumber);
          sb.append(' ');
          sb.append(debugInfo.type);
          sb.append(" - ");
          sb.append(debugInfo.receivedTime);
          sb.append("</name>");
          sb.append("<description>\n");
          sb.append("<![CDATA[<div style=\"white-space: nowrap;\">\n");
          sb.append("<p><b>");
          sb.append("* Last fix time : ");
          sb.append(debugInfo.time);
          sb.append(", Last interval=");
          sb.append(debugInfo.lastInterval);
          sb.append("</b></p>\n");
          sb.append("<p><b>** Location history around 1sec based on engine off</b></p>\n");
          String[] split2 = str3.split("\\|");
          int length = split2.length;
          int i = 0;
          while (i < length) {
            String[] split3 = split2[i].split(KnoxVpnFirewallHelper.DELIMITER);
            int length2 = split3.length;
            String[] strArr = split2;
            int i2 = 0;
            while (i2 < length2) {
              int i3 = length;
              String str4 = split3[i2];
              sb.append("<p>");
              sb.append(str4);
              sb.append("</p>");
              i2++;
              length = i3;
            }
            i++;
            split2 = strArr;
          }
          sb.append("</div>]]></description>");
          sb.append("<styleUrl>");
          sb.append("#sn_forbidden");
          sb.append("</styleUrl>");
          sb.append("<Point><gx:drawOrder>1</gx:drawOrder>");
          sb.append("<coordinates>");
          sb.append(debugInfo.longitude);
          sb.append(',');
          sb.append(debugInfo.latitude);
          sb.append('0');
          sb.append("</coordinates></Point>");
          sb.append("</Placemark>");
          sb.append("<Placemark>\n<name>");
          sb.append('#');
          sb.append(debugInfo.sessionNumber);
          sb.append(' ');
          sb.append("SUPL_HISTORY");
          sb.append("</name>");
          sb.append("<visibility>0</visibility>");
          sb.append("<description>\n");
          sb.append("<![CDATA[<div style=\"white-space: nowrap;\">\n");
          for (String str5 : str2.split("\\|")) {
            sb.append("<p>");
            sb.append(str5);
            sb.append("</p>");
          }
          sb.append("</div>]]></description>");
          sb.append("<styleUrl>");
          sb.append("#sn_arrow");
          sb.append("</styleUrl>");
          sb.append("<Point><gx:drawOrder>1</gx:drawOrder>");
          sb.append("<coordinates>");
          sb.append(debugInfo.longitude);
          sb.append(',');
          sb.append(debugInfo.latitude);
          sb.append('0');
          sb.append("</coordinates></Point>");
          sb.append("</Placemark>");
        }
      }
      it = it2;
    }
    sb.append("</Folder>");
  }

  public class KmlInfo {
    public final float course;
    public final double latitude;
    public final double longitude;
    public final String provider;
    public final SATELLITE_STATE satelliteState;
    public final float speed;
    public final String time;

    public KmlInfo(
        String str,
        double d,
        double d2,
        float f,
        float f2,
        String str2,
        SATELLITE_STATE satellite_state) {
      this.provider = str;
      this.latitude = d;
      this.longitude = d2;
      this.speed = f;
      this.course = f2;
      this.time = str2;
      this.satelliteState = satellite_state;
    }

    public String getProvider() {
      return this.provider;
    }

    public double getLatitude() {
      return this.latitude;
    }

    public double getLongitude() {
      return this.longitude;
    }

    public float getSpeed() {
      return this.speed;
    }

    public float getCourse() {
      return this.course;
    }

    public String getTime() {
      return this.time;
    }

    public SATELLITE_STATE getSatelliteState() {
      return this.satelliteState;
    }
  }

  public class DebugInfo {
    public final String lastInterval;
    public final String latitude;
    public final String longitude;
    public final String message;
    public final String receivedTime;
    public final long sessionNumber;
    public final String time;
    public final String type;

    public DebugInfo(
        String str,
        String str2,
        long j,
        String str3,
        String str4,
        String str5,
        String str6,
        String str7) {
      this.receivedTime = str;
      this.type = str2;
      this.sessionNumber = j;
      this.latitude = str3;
      this.longitude = str4;
      this.time = str5;
      this.lastInterval = str6;
      this.message = str7;
    }
  }

  enum SATELLITE_STATE {
    INIT("in"),
    NO_SATELLITE("no"),
    SHADOW("sh"),
    DEEP_INDOOR("di"),
    MILD_INDOOR("mi"),
    OUTDOOR("od");

    private String simpleKeyword;

    SATELLITE_STATE(String str) {
      this.simpleKeyword = str;
    }
  }
}
