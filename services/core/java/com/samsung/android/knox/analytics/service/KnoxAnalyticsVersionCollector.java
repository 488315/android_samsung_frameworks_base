package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.SELinux;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.text.format.DateFormat;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;

import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxAnalyticsVersionCollector {
    public static final String ANDROID_SECURITY_PATCH_LEVEL = "aSPL";
    public static final String ANDROID_VERSION = "aV";
    public static final String BASEBAND_VERSION = "bbV";
    public static final String BUILD_NUMBER = "bN";
    public static final String CSC_CODE = "cssC";
    public static final String CSC_COUNTRY = "cscC";
    public static final String HARDWARE_VERSION = "hV";
    public static final String KERNEL_VERSION = "cV";
    public static final String KEY_VERSIONING_BLOB = "VERSIONING_BLOB";
    public static final String KEY_VERSIONING_ID = "VERSIONING_ID";
    public static final String KNOX_VERSION = "knoxV";
    public static final String KnoxAnalyticsVersion = "kaV";
    public static final String MODEL_NUMBER = "mN";
    public static final String SECURITY_SOFTWARE_VERSION = "sSV";
    public static final String SE_ANDROID = "seAS";
    public static final String TAG = "[KnoxAnalytics] KnoxAnalyticsVersionCollector";

    public static boolean checkVersioningBlob(Context context) {
        String str = TAG;
        Log.d(str, "checkVersioningBlob()");
        KnoxAnalyticsVersionCollector knoxAnalyticsVersionCollector =
                new KnoxAnalyticsVersionCollector();
        String[] versioningBlob = KnoxAnalyticsQueryResolver.getVersioningBlob(context);
        if (versioningBlob == null || versioningBlob.length <= 1) {
            return false;
        }
        String str2 = versioningBlob[1];
        String str3 = "";
        if (str2 != null && !str2.equals("")) {
            str3 = KnoxAnalyticsDataConverter.getVersioningBlobData(str2);
        }
        String generateVersioningBlob = knoxAnalyticsVersionCollector.generateVersioningBlob();
        if (KnoxAnalyticsDataConverter.isJSONEqual(str3, generateVersioningBlob)) {
            return false;
        }
        Log.d(str, "checkVersioningBlob(): versioning blob changed:");
        Log.d(str, "checkVersioningBlob(): old=" + str3);
        Log.d(str, "checkVersioningBlob(): new=" + generateVersioningBlob);
        int intValue = Integer.valueOf(versioningBlob[0]).intValue() + 1;
        long lastEventId = KnoxAnalyticsQueryResolver.getLastEventId(context) + 1;
        return KnoxAnalyticsQueryResolver.addVersioningBlob(
                        context,
                        intValue,
                        KnoxAnalyticsDataConverter.generateVersioningBlobEvent(
                                generateVersioningBlob, lastEventId, intValue),
                        lastEventId)
                >= 0;
    }

    public final String KernelVersion(String str) {
        Matcher matcher =
                Pattern.compile(
                                "Linux version (\\S+)"
                                    + " \\((\\S+)\\).*(#\\d+).*((?:Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)")
                        .matcher(str);
        if (!matcher.matches()) {
            Log.e(TAG, "KernelVersion(): Regex did not match on /proc/version: " + str);
            return "Unavailable";
        }
        if (matcher.groupCount() >= 4) {
            return matcher.group(1);
        }
        Log.e(
                TAG,
                "KernelVersion(): Regex match on /proc/version only returned "
                        + matcher.groupCount()
                        + " groups");
        return "Unavailable";
    }

    public final String KernelVersionVZW(String str) {
        Matcher matcher =
                Pattern.compile(
                                "Linux version (\\S+) \\((\\S+?)\\) (?:(\\(gcc.+?"
                                    + " \\))|(\\(clang.+?\\))) (#\\d+)"
                                    + " (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)")
                        .matcher(str);
        if (!matcher.matches()) {
            Log.e(TAG, "KernelVersionVZW(): Regex did not match on /proc/version: " + str);
        } else if (matcher.groupCount() < 4) {
            Log.e(
                    TAG,
                    "KernelVersionVZW(): Regex match on /proc/version only returned "
                            + matcher.groupCount()
                            + " groups");
            return "Unavailable";
        }
        String[] split = matcher.group(1).split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        if (split.length == 3) {
            split[0] = split[0] + PackageManagerShellCommandDataLoader.STDIN_PATH + split[2];
        }
        return split[0];
    }

    public final String generateVersioningBlob() {
        Log.d(TAG, "Call generateVersioningBlob()");
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                jSONObject.put(BUILD_NUMBER, getBuildNumber());
                jSONObject.put(MODEL_NUMBER, Build.MODEL);
                jSONObject.put(CSC_CODE, SemSystemProperties.getSalesCode());
                jSONObject.put(CSC_COUNTRY, SemSystemProperties.getCountryCode());
                jSONObject.put(SE_ANDROID, getSEAndroidVersion());
                jSONObject.put(ANDROID_VERSION, Build.VERSION.RELEASE);
                jSONObject.put(HARDWARE_VERSION, getHardwareVersion());
                jSONObject.put(ANDROID_SECURITY_PATCH_LEVEL, getAndroidSecurityPathLevel());
                jSONObject.put(SECURITY_SOFTWARE_VERSION, getSecuritySWVersion());
                jSONObject.put(KNOX_VERSION, getKnoxVersion());
                jSONObject.put(KERNEL_VERSION, getKernelVersion());
                jSONObject.put(
                        KnoxAnalyticsVersion,
                        KnoxAnalyticsSystemService.KNOX_ANALYTICS_SYSTEM_SERVICE_VERSION);
                return jSONObject.toString();
            } catch (JSONException unused) {
                Log.e(TAG, "generateVersioningBlob()");
                return jSONObject.toString();
            }
        } catch (Throwable unused2) {
            return jSONObject.toString();
        }
    }

    public final String getAndroidSecurityPathLevel() {
        String str = Build.VERSION.SECURITY_PATCH;
        if ("".equals(str)) {
            return "";
        }
        try {
            return DateFormat.format(
                            DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"),
                            new SimpleDateFormat("yyyy-MM-dd").parse(str))
                    .toString();
        } catch (Exception e) {
            Log.e(TAG, "getAndroidSecurityPathLevel(): Exception ", e);
            return str;
        }
    }

    public final String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public final String getBasebandVersion() {
        return SystemProperties.get(
                "none".equals(SystemProperties.get("ril.approved_modemver", "none"))
                        ? "gsm.version.baseband"
                        : "ril.approved_modemver",
                "default");
    }

    public final String getBuildNumber() {
        if (!"CHM".equals(SemSystemProperties.getSalesCode())) {
            return Build.DISPLAY;
        }
        StringBuilder sb = new StringBuilder(Build.DISPLAY);
        try {
            StringTokenizer stringTokenizer =
                    new StringTokenizer(SystemProperties.get("ro.build.date", "Unknown"), " ");
            String[] strArr = new String[stringTokenizer.countTokens()];
            int i = 0;
            while (stringTokenizer.hasMoreElements()) {
                strArr[i] = stringTokenizer.nextToken();
                i++;
            }
            if (i != 6) {
                return sb.toString();
            }
            if (strArr[1].compareToIgnoreCase("Jan") == 0) {
                strArr[1] = Constants.OTP_BIT_KG_ENABLED;
            } else if (strArr[1].compareToIgnoreCase("Feb") == 0) {
                strArr[1] = "02";
            } else if (strArr[1].compareToIgnoreCase("Mar") == 0) {
                strArr[1] = "03";
            } else if (strArr[1].compareToIgnoreCase("Apr") == 0) {
                strArr[1] = "04";
            } else if (strArr[1].compareToIgnoreCase("May") == 0) {
                strArr[1] = "05";
            } else if (strArr[1].compareToIgnoreCase("Jun") == 0) {
                strArr[1] = "06";
            } else if (strArr[1].compareToIgnoreCase("Jul") == 0) {
                strArr[1] = "07";
            } else if (strArr[1].compareToIgnoreCase("Aug") == 0) {
                strArr[1] = "08";
            } else if (strArr[1].compareToIgnoreCase("Sep") == 0) {
                strArr[1] = "09";
            } else if (strArr[1].compareToIgnoreCase("Oct") == 0) {
                strArr[1] = "10";
            } else if (strArr[1].compareToIgnoreCase("Nov") == 0) {
                strArr[1] = Constants.OTP_BIT_KG_COMPLETED;
            } else {
                strArr[1] = "12";
            }
            if (strArr[2].length() != 1) {
                sb.append("\n");
                sb.append(strArr[5]);
                sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                sb.append(strArr[1]);
                sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                sb.append(strArr[2]);
                return sb.toString();
            }
            sb.append("\n");
            sb.append(strArr[5]);
            sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
            sb.append(strArr[1]);
            sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
            sb.append("0");
            sb.append(strArr[2]);
            return sb.toString();
        } catch (NullPointerException unused) {
            Log.e(TAG, "getBuildNumber(): china version return Normal build number");
            return sb.toString();
        }
    }

    public final String getCSCCode() {
        return SemSystemProperties.getSalesCode();
    }

    public final String getCSCCountry() {
        return SemSystemProperties.getCountryCode();
    }

    public final String getHardwareVersion() {
        return SystemProperties.get("ro.revision", "Unknown");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x003d -> B:13:0x0052). Please report as a decompilation issue!!! */
    public final String getKernelVersion() {
        String str = "";
        BufferedReader bufferedReader = null;
        BufferedReader bufferedReader2 = null;
        bufferedReader = null;
        try {
        } catch (Exception e) {
            Log.e(TAG, "reader.close(): Exception ", e);
            bufferedReader = bufferedReader;
        }
        try {
            try {
                BufferedReader bufferedReader3 =
                        new BufferedReader(new FileReader("/proc/version"), 256);
                try {
                    String readLine = bufferedReader3.readLine();
                    boolean equals = "VZW".equals(SemSystemProperties.getSalesCode());
                    str = equals != 0 ? KernelVersionVZW(readLine) : KernelVersion(readLine);
                    bufferedReader3.close();
                    bufferedReader3.close();
                    bufferedReader = equals;
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader2 = bufferedReader3;
                    Log.e(TAG, "getKernelVersion(): Exception ", e);
                    bufferedReader = bufferedReader2;
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                        bufferedReader = bufferedReader2;
                    }
                    return str;
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader3;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e3) {
                            Log.e(TAG, "reader.close(): Exception ", e3);
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final String getKnoxVersion() {
        return "Knox " + EnterpriseKnoxManager.getInstance().getVersion().getInternalVersion();
    }

    public final String getModelNumber() {
        return Build.MODEL;
    }

    public final String getSEAndroidVersion() {
        return SELinux.isSELinuxEnabled()
                ? SELinux.isSELinuxEnforced() ? "Enforcing" : "Permissive"
                : "Disabled";
    }

    public final String getSecurityASKSVersion() {
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                "v" + SystemProperties.get("security.ASKS.version", "0") + " Release ",
                SystemProperties.get("security.ASKS.policy_version", "000000"));
    }

    public final String getSecurityFIPSVersion() {
        String str;
        if ("".equals(SystemProperties.get("ro.security.fips_bssl.ver"))) {
            str = "";
        } else {
            str = "FIPS BoringSSL v" + SystemProperties.get("ro.security.fips_bssl.ver");
        }
        if (!"".equals(SystemProperties.get("ro.security.fips_skc.ver"))) {
            if (!"".equals(str)) {
                str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " ");
            }
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "FIPS SKC v");
            m.append(SystemProperties.get("ro.security.fips_skc.ver"));
            str = m.toString();
        }
        if (!"".equals(SystemProperties.get("ro.security.fips_scrypto.ver"))) {
            if (!"".equals(str)) {
                str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " ");
            }
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, "FIPS SCrypto v");
            m2.append(SystemProperties.get("ro.security.fips_scrypto.ver"));
            str = m2.toString();
        }
        if ("".equals(SystemProperties.get("ro.security.fips_fmp.ver"))) {
            return str;
        }
        if (!"".equals(str)) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, " ");
        }
        StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, "FIPS FMP v");
        m3.append(SystemProperties.get("ro.security.fips_fmp.ver"));
        return m3.toString();
    }

    public final String getSecurityMDFVersion() {
        if (!"Enabled".equals(SystemProperties.get("ro.security.mdf.ux"))) {
            return "";
        }
        String str = "v" + SystemProperties.get("ro.security.mdf.ver");
        String str2 = " Release " + SystemProperties.get("ro.security.mdf.release");
        String str3 = SystemProperties.get("security.mdf");
        return AnyMotionDetector$$ExternalSyntheticOutline0.m(
                str,
                str2,
                "Disabled".equals(str3)
                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(" : ", str3)
                        : "");
    }

    public final String getSecuritySPLVersion() {
        String str;
        String str2 = Build.VERSION.SECURITY_PATCH;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            str =
                    new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH)
                            .format(simpleDateFormat.parse(str2));
        } catch (Exception e) {
            Log.e(TAG, "getSecuritySPLVersion(): Exception ", e);
            str = "";
        }
        if ("".equals(str)) {
            return "";
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("SMR ", str, " Release ");
        m.append(SystemProperties.get("ro.build.version.security_index", ""));
        return m.toString();
    }

    public final String getSecuritySWVersion() {
        String securityMDFVersion = getSecurityMDFVersion();
        String m =
                !"".equals(securityMDFVersion)
                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "MDF ", securityMDFVersion)
                        : "";
        String securityWLANVersion = getSecurityWLANVersion();
        if (!"".equals(securityWLANVersion)) {
            if ("".equals(m)) {
                m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " ");
            }
            m = AnyMotionDetector$$ExternalSyntheticOutline0.m(m, "WLAN ", securityWLANVersion);
        }
        String securityVPNVersion = getSecurityVPNVersion();
        if (!"".equals(securityVPNVersion)) {
            if ("".equals(m)) {
                m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " ");
            }
            m = AnyMotionDetector$$ExternalSyntheticOutline0.m(m, "VPN ", securityVPNVersion);
        }
        String securityASKSVersion = getSecurityASKSVersion();
        if (!"".equals(securityASKSVersion)) {
            if ("".equals(m)) {
                m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " ");
            }
            m = AnyMotionDetector$$ExternalSyntheticOutline0.m(m, "ASKS ", securityASKSVersion);
        }
        String securityFIPSVersion = getSecurityFIPSVersion();
        if (!"".equals(securityFIPSVersion)) {
            if ("".equals(m)) {
                m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " ");
            }
            m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, securityFIPSVersion);
        }
        String securitySPLVersion = getSecuritySPLVersion();
        if ("".equals(securitySPLVersion)) {
            return m;
        }
        if ("".equals(m)) {
            m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m, " ");
        }
        return AnyMotionDetector$$ExternalSyntheticOutline0.m(m, "SPL ", securitySPLVersion);
    }

    public final String getSecurityVPNVersion() {
        if ("".equals(SystemProperties.get("ro.security.vpnpp.ver"))) {
            return "";
        }
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                "v" + SystemProperties.get("ro.security.vpnpp.ver"),
                " Release " + SystemProperties.get("ro.security.vpnpp.release"));
    }

    public final String getSecurityWLANVersion() {
        if ("".equals(SystemProperties.get("ro.security.wlan.ver"))) {
            return "";
        }
        return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                "v" + SystemProperties.get("ro.security.wlan.ver"),
                " Release " + SystemProperties.get("ro.security.wlan.release"));
    }
}
