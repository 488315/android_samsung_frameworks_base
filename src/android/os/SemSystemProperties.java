package android.os;

/* loaded from: classes3.dex */
public class SemSystemProperties {
    private SemSystemProperties() {
    }

    public static String get(String str) {
        return SystemProperties.get(str);
    }

    public static String get(String str, String str2) {
        return SystemProperties.get(str, str2);
    }

    public static int getInt(String str, int i) {
        return SystemProperties.getInt(str, i);
    }

    public static long getLong(String str, long j) {
        return SystemProperties.getLong(str, j);
    }

    public static boolean getBoolean(String str, boolean z) {
        return SystemProperties.getBoolean(str, z);
    }

    public static void set(String str, String str2) {
        SystemProperties.set(str, str2);
    }

    @Deprecated
    public static String getDeviceSerialNumber() {
        return SystemProperties.get("ril.serialnumber", "00000000000");
    }

    public static String getSalesCode() {
        return SystemProperties.get("ro.csc.sales_code", "");
    }

    public static String getCountryCode() {
        return SystemProperties.get("ro.csc.country_code", "");
    }

    public static String getCountryIso() {
        return SystemProperties.get("ro.csc.countryiso_code", "");
    }
}
