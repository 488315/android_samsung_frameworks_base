package com.android.server.location.nsflp;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class NSKmlWriter {
    public static final double sThirtyRadian = Math.toRadians(30.0d);
    public static final double sSixtyRadian = Math.toRadians(90.0d);
    public final List mNmeaKmlInfoList = new ArrayList();
    public final Map mPositionKmlInfoMap = new HashMap();
    public final List mDebugInfoList = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DebugInfo {
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KmlInfo {
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum SATELLITE_STATE {
        /* JADX INFO: Fake field, exist only in values array */
        EF0("in"),
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

    public static void createZipFile(File file, File file2) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
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
                            if (read <= 0) {
                                zipOutputStream.closeEntry();
                                zipOutputStream.finish();
                                fileInputStream.close();
                                zipOutputStream.close();
                                bufferedOutputStream.close();
                                fileOutputStream.close();
                                return;
                            }
                            zipOutputStream.write(bArr, 0, read);
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

    public static String getCourseArrow(KmlInfo kmlInfo) {
        float f = kmlInfo.speed;
        if (f <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return "";
        }
        double radians = Math.toRadians(kmlInfo.course);
        double d = (f / 2.0d) / 100000.0d;
        double sin = Math.sin(radians) * d;
        double d2 = kmlInfo.longitude;
        double d3 = sin + d2;
        double cos = Math.cos(radians) * d;
        double d4 = kmlInfo.latitude;
        double d5 = cos + d4;
        double d6 = d / 5.0d;
        double d7 = radians + 1.0d;
        double d8 = d7 - sThirtyRadian;
        double sin2 = d3 - (Math.sin(d8) * d6);
        double cos2 = d5 - (Math.cos(d8) * d6);
        double d9 = d7 - sSixtyRadian;
        String str =
                d2
                        + ","
                        + d4
                        + ",0.0 "
                        + d3
                        + ","
                        + d5
                        + ",0.0 "
                        + sin2
                        + ","
                        + cos2
                        + ",0.0 "
                        + (d3 - (Math.sin(d9) * d6))
                        + ","
                        + (d5 - (Math.cos(d9) * d6))
                        + ",0.0 "
                        + d3
                        + ","
                        + d5
                        + ",0.0";
        StringBuilder sb = new StringBuilder("<LineString><coordinates>\n");
        sb.append(str + "\n");
        sb.append("</coordinates></LineString>\n");
        return sb.toString();
    }

    public static DebugInfo getDebugKmlInfo(String[] strArr) {
        int length = strArr.length;
        if (length < 8) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    length, "getDebugKmlInfo, wrong data length : ", "NSKmlWriter");
            return null;
        }
        String str = strArr[0];
        String str2 = strArr[1];
        long parseLong = Long.parseLong(strArr[2]);
        String str3 = strArr[3];
        String str4 = strArr[4];
        String str5 = strArr[5];
        String str6 = strArr[6];
        String str7 = strArr[8];
        return new DebugInfo(
                str,
                str2,
                parseLong,
                str3,
                str4,
                str5,
                str6,
                (!"ENGINE_OFF".equals(str2) || length <= 10)
                        ? str7
                        : AnyMotionDetector$$ExternalSyntheticOutline0.m(str7, "/", strArr[10]));
    }

    public static KmlInfo getNmeaKmlInfo(String[] strArr) {
        if (strArr.length < 9 || strArr[1].length() < 8 || !"A".equals(strArr[2].toUpperCase())) {
            return null;
        }
        double parseDouble = Double.parseDouble(strArr[3]);
        String upperCase = strArr[4].toUpperCase();
        double parseDouble2 = Double.parseDouble(strArr[5]);
        String upperCase2 = strArr[6].toUpperCase();
        boolean isEmpty = strArr[7].isEmpty();
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float parseFloat = isEmpty ? 0.0f : Float.parseFloat(strArr[7]) * 1.852f;
        if (!strArr[8].isEmpty()) {
            f = Float.parseFloat(strArr[8]);
        }
        float f2 = f;
        String str = strArr[9];
        String substring = strArr[1].substring(0, 8);
        if (substring.contains(".")) {
            try {
                Calendar calendar = Calendar.getInstance();
                Date parse = new SimpleDateFormat("HHmmss", Locale.getDefault()).parse(substring);
                Objects.requireNonNull(parse);
                calendar.setTime(parse);
                if (Integer.parseInt(substring.substring(7, 8)) >= 5) {
                    calendar.add(13, 1);
                }
                substring =
                        String.format(
                                Locale.getDefault(),
                                "%02d%02d%02d",
                                Integer.valueOf(calendar.get(11)),
                                Integer.valueOf(calendar.get(12)),
                                Integer.valueOf(calendar.get(13)));
            } catch (ParseException e) {
                e.printStackTrace();
                substring = "";
            }
        }
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(substring, str);
        double d = parseDouble * 0.01d;
        double d2 = (int) d;
        double d3 = parseDouble2 * 0.01d;
        double d4 = (int) d3;
        return new KmlInfo(
                "gps",
                (((d - d2) * 1.6666666666666667d) + d2) * ("S".equals(upperCase) ? -1.0d : 1.0d),
                (((d3 - d4) * 1.6666666666666667d) + d4) * ("W".equals(upperCase2) ? -1.0d : 1.0d),
                parseFloat,
                f2,
                m$1,
                null);
    }

    public static KmlInfo getPositionKmlInfo(String[] strArr) {
        int parseInt;
        int length = strArr.length;
        SATELLITE_STATE satellite_state = null;
        if (length < 8) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    length, "getPositionKmlInfo, wrong data length : ", "NSKmlWriter");
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

    public static String getStyle(String str, boolean z) {
        return "gps".equals(str)
                ? z ? "#lineStyleRed" : "#pointStyleRed"
                : "network".equals(str)
                        ? z ? "#lineStyleYellow" : "#pointStyleYellow"
                        : z ? "#lineStyleGreen" : "#pointStyleGreen";
    }
}
