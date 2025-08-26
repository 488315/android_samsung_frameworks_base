package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telephony.CbGeoUtils;
import android.text.TextUtils;
import android.util.NtpTrustedTime;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SystemApi
/* loaded from: classes4.dex */
public class CbGeoUtils {
    private static final String CIRCLE_SYMBOL = "circle";
    public static final int EARTH_RADIUS_METER = 6371000;
    public static final double EPS = 1.0E-7d;
    public static final int GEOMETRY_TYPE_CIRCLE = 3;
    public static final int GEOMETRY_TYPE_POLYGON = 2;
    public static final int GEO_FENCING_MAXIMUM_WAIT_TIME = 1;
    private static final String POLYGON_SYMBOL = "polygon";
    private static final String TAG = "CbGeoUtils";

    public interface Geometry {
        boolean contains(LatLng latLng);
    }

    public static int sign(double d) {
        if (d > 1.0E-7d) {
            return 1;
        }
        return d < -1.0E-7d ? -1 : 0;
    }

    private CbGeoUtils() {
    }

    public static class LatLng {
        public final double lat;
        public final double lng;

        public LatLng(double d, double d2) {
            this.lat = d;
            this.lng = d2;
        }

        public LatLng subtract(LatLng latLng) {
            return new LatLng(this.lat - latLng.lat, this.lng - latLng.lng);
        }

        public double distance(LatLng latLng) {
            double dSin = Math.sin(Math.toRadians(this.lat - latLng.lat) * 0.5d);
            double dSin2 = Math.sin(Math.toRadians(this.lng - latLng.lng) * 0.5d);
            double dCos = (dSin * dSin) + (dSin2 * dSin2 * Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(latLng.lat)));
            return Math.atan2(Math.sqrt(dCos), Math.sqrt(1.0d - dCos)) * 2.0d * 6371000.0d;
        }

        public String toString() {
            return NavigationBarInflaterView.KEY_CODE_START + this.lat + "," + this.lng + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LatLng)) {
                return false;
            }
            LatLng latLng = (LatLng) obj;
            return this.lat == latLng.lat && this.lng == latLng.lng;
        }
    }

    public static class Polygon implements Geometry {
        private static final double SCALE = 1000.0d;
        private final LatLng mOrigin;
        private final List<Point> mScaledVertices;
        private final List<LatLng> mVertices;

        public Polygon(List<LatLng> list) {
            this.mVertices = list;
            int i = 0;
            for (int i2 = 1; i2 < list.size(); i2++) {
                if (list.get(i2).lng < list.get(i).lng) {
                    i = i2;
                }
            }
            this.mOrigin = list.get(i);
            this.mScaledVertices = (List) list.stream().map(new Function() { // from class: android.telephony.CbGeoUtils$Polygon$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.lambda$new$0((CbGeoUtils.LatLng) obj);
                }
            }).collect(Collectors.toList());
        }

        public List<LatLng> getVertices() {
            return this.mVertices;
        }

        @Override // android.telephony.CbGeoUtils.Geometry
        public boolean contains(LatLng latLng) {
            Point pointLambda$new$0 = lambda$new$0(latLng);
            int size = this.mScaledVertices.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                Point point = this.mScaledVertices.get(i);
                i++;
                Point point2 = this.mScaledVertices.get(i % size);
                int iSign = CbGeoUtils.sign(crossProduct(point2.subtract(point), pointLambda$new$0.subtract(point)));
                if (iSign == 0) {
                    if (Math.min(point.x, point2.x) <= pointLambda$new$0.x && pointLambda$new$0.x <= Math.max(point.x, point2.x) && Math.min(point.y, point2.y) <= pointLambda$new$0.y && pointLambda$new$0.y <= Math.max(point.y, point2.y)) {
                        return true;
                    }
                } else if (CbGeoUtils.sign(point.y - pointLambda$new$0.y) <= 0) {
                    if (iSign > 0 && CbGeoUtils.sign(point2.y - pointLambda$new$0.y) > 0) {
                        i2++;
                    }
                } else if (iSign < 0 && CbGeoUtils.sign(point2.y - pointLambda$new$0.y) <= 0) {
                    i2--;
                }
            }
            return i2 != 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: convertAndScaleLatLng, reason: merged with bridge method [inline-methods] */
        public Point lambda$new$0(LatLng latLng) {
            double d = latLng.lat - this.mOrigin.lat;
            double dSign = latLng.lng - this.mOrigin.lng;
            if (CbGeoUtils.sign(this.mOrigin.lng) != 0 && CbGeoUtils.sign(this.mOrigin.lng) != CbGeoUtils.sign(latLng.lng)) {
                double dAbs = Math.abs(this.mOrigin.lng) + Math.abs(latLng.lng);
                if (CbGeoUtils.sign((2.0d * dAbs) - 360.0d) > 0) {
                    dSign = CbGeoUtils.sign(this.mOrigin.lng) * (360.0d - dAbs);
                }
            }
            return new Point(d * SCALE, dSign * SCALE);
        }

        private static double crossProduct(Point point, Point point2) {
            return (point.x * point2.y) - (point.y * point2.x);
        }

        static final class Point {
            public final double x;
            public final double y;

            Point(double d, double d2) {
                this.x = d;
                this.y = d2;
            }

            public Point subtract(Point point) {
                return new Point(this.x - point.x, this.y - point.y);
            }
        }

        public String toString() {
            if (!TelephonyUtils.IS_DEBUGGABLE) {
                return "Polygon: ";
            }
            return "Polygon: " + this.mVertices;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Polygon)) {
                return false;
            }
            Polygon polygon = (Polygon) obj;
            if (this.mVertices.size() != polygon.mVertices.size()) {
                return false;
            }
            for (int i = 0; i < this.mVertices.size(); i++) {
                if (!this.mVertices.get(i).equals(polygon.mVertices.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class Circle implements Geometry {
        private final LatLng mCenter;
        private final double mRadiusMeter;

        public Circle(LatLng latLng, double d) {
            this.mCenter = latLng;
            this.mRadiusMeter = d;
        }

        public LatLng getCenter() {
            return this.mCenter;
        }

        public double getRadius() {
            return this.mRadiusMeter;
        }

        @Override // android.telephony.CbGeoUtils.Geometry
        public boolean contains(LatLng latLng) {
            return this.mCenter.distance(latLng) <= this.mRadiusMeter;
        }

        public String toString() {
            if (!TelephonyUtils.IS_DEBUGGABLE) {
                return "Circle: ";
            }
            return "Circle: " + this.mCenter + ", radius = " + this.mRadiusMeter;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Circle)) {
                return false;
            }
            Circle circle = (Circle) obj;
            return this.mCenter.equals(circle.mCenter) && Double.compare(this.mRadiusMeter, circle.mRadiusMeter) == 0;
        }
    }

    public static List<Geometry> parseGeometriesFromString(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split("\\s*;\\s*")) {
            String[] strArrSplit = str2.split("\\s*\\|\\s*");
            String str3 = strArrSplit[0];
            str3.hashCode();
            if (str3.equals(CIRCLE_SYMBOL)) {
                arrayList.add(new Circle(parseLatLngFromString(strArrSplit[1]), Double.parseDouble(strArrSplit[2])));
            } else if (str3.equals(POLYGON_SYMBOL)) {
                ArrayList arrayList2 = new ArrayList(strArrSplit.length - 1);
                for (int i = 1; i < strArrSplit.length; i++) {
                    arrayList2.add(parseLatLngFromString(strArrSplit[i]));
                }
                arrayList.add(new Polygon(arrayList2));
            } else {
                com.android.telephony.Rlog.e(TAG, "Invalid geometry format " + str2);
            }
        }
        return arrayList;
    }

    public static String encodeGeometriesToString(List<Geometry> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return (String) list.stream().map(new Function() { // from class: android.telephony.CbGeoUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CbGeoUtils.encodeGeometryToString((CbGeoUtils.Geometry) obj);
            }
        }).filter(new Predicate() { // from class: android.telephony.CbGeoUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CbGeoUtils.lambda$encodeGeometriesToString$1((String) obj);
            }
        }).collect(Collectors.joining(NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    static /* synthetic */ boolean lambda$encodeGeometriesToString$1(String str) {
        return !TextUtils.isEmpty(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String encodeGeometryToString(Geometry geometry) {
        StringBuilder sb = new StringBuilder();
        if (geometry instanceof Polygon) {
            sb.append(POLYGON_SYMBOL);
            for (LatLng latLng : ((Polygon) geometry).getVertices()) {
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
                sb.append(latLng.lat);
                sb.append(",");
                sb.append(latLng.lng);
            }
        } else {
            if (!(geometry instanceof Circle)) {
                com.android.telephony.Rlog.e(TAG, "Unsupported geometry object " + geometry);
                return null;
            }
            sb.append("circle|");
            Circle circle = (Circle) geometry;
            sb.append(circle.getCenter().lat);
            sb.append(",");
            sb.append(circle.getCenter().lng);
            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append(circle.getRadius());
        }
        return sb.toString();
    }

    public static LatLng parseLatLngFromString(String str) {
        String[] strArrSplit = str.split("\\s*,\\s*");
        return new LatLng(Double.parseDouble(strArrSplit[0]), Double.parseDouble(strArrSplit[1]));
    }
}
