package com.samsung.android.knox.analytics.service;

import android.content.Context;
import android.os.Build;
import android.os.SELinux;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.text.format.DateFormat;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver;
import com.samsung.android.knox.analytics.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class KnoxAnalyticsVersionCollector {
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
    public static final String TAG = "[KnoxAnalytics] " + KnoxAnalyticsVersionCollector.class.getSimpleName();

    public String generateVersioningBlob() {
        Log.d(TAG, "Call generateVersioningBlob()");
        JSONObject jSONObject = new JSONObject();
        try {
            try {
                jSONObject.put(BUILD_NUMBER, getBuildNumber());
                jSONObject.put(MODEL_NUMBER, getModelNumber());
                jSONObject.put(CSC_CODE, getCSCCode());
                jSONObject.put(CSC_COUNTRY, getCSCCountry());
                jSONObject.put(SE_ANDROID, getSEAndroidVersion());
                jSONObject.put(ANDROID_VERSION, getAndroidVersion());
                jSONObject.put(HARDWARE_VERSION, getHardwareVersion());
                jSONObject.put(ANDROID_SECURITY_PATCH_LEVEL, getAndroidSecurityPathLevel());
                jSONObject.put(SECURITY_SOFTWARE_VERSION, getSecuritySWVersion());
                jSONObject.put(KNOX_VERSION, getKnoxVersion());
                jSONObject.put(KERNEL_VERSION, getKernelVersion());
                jSONObject.put(KnoxAnalyticsVersion, KnoxAnalyticsSystemService.KNOX_ANALYTICS_SYSTEM_SERVICE_VERSION);
                return jSONObject.toString();
            } catch (JSONException unused) {
                Log.e(TAG, "generateVersioningBlob()");
                return jSONObject.toString();
            }
        } catch (Throwable unused2) {
            return jSONObject.toString();
        }
    }

    public static boolean checkVersioningBlob(Context context) {
        String str = TAG;
        Log.d(str, "checkVersioningBlob()");
        KnoxAnalyticsVersionCollector knoxAnalyticsVersionCollector = new KnoxAnalyticsVersionCollector();
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
        return KnoxAnalyticsQueryResolver.addVersioningBlob(context, intValue, KnoxAnalyticsDataConverter.generateVersioningBlobEvent(generateVersioningBlob, lastEventId, intValue), lastEventId) >= 0;
    }

    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getModelNumber() {
        return Build.MODEL;
    }

    public String getCSCCode() {
        return SemSystemProperties.getSalesCode();
    }

    public String getCSCCountry() {
        return SemSystemProperties.getCountryCode();
    }

    public String getHardwareVersion() {
        return SystemProperties.get("ro.revision", "Unknown");
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x001a, code lost:
    
        if ("none".equals(android.os.SystemProperties.get("ril.approved_modemver", "none")) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getBasebandVersion() {
        String str = SemCscFeature.getInstance().getBoolean("CscFeature_Setting_ChangeApprovedModemVersion") ? "ril.approved_modemver" : "gsm.version.baseband";
        return SystemProperties.get(str, "default");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x0036 -> B:12:0x0051). Please report as a decompilation issue!!! */
    public String getKernelVersion() {
        BufferedReader bufferedReader;
        String KernelVersion;
        String str = "";
        BufferedReader bufferedReader2 = null;
        BufferedReader bufferedReader3 = null;
        bufferedReader2 = null;
        try {
            try {
                try {
                    bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            Log.e(TAG, "reader.close(): Exception ", e2);
            bufferedReader2 = bufferedReader2;
        }
        try {
            String readLine = bufferedReader.readLine();
            boolean equals = "VZW".equals(SemSystemProperties.getSalesCode());
            if (equals != 0) {
                KernelVersion = KernelVersionVZW(readLine);
            } else {
                KernelVersion = KernelVersion(readLine);
            }
            str = KernelVersion;
            bufferedReader.close();
            bufferedReader.close();
            bufferedReader2 = equals;
        } catch (Exception e3) {
            e = e3;
            bufferedReader3 = bufferedReader;
            Log.e(TAG, "getKernelVersion(): Exception ", e);
            bufferedReader2 = bufferedReader3;
            if (bufferedReader3 != null) {
                bufferedReader3.close();
                bufferedReader2 = bufferedReader3;
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (Exception e4) {
                    Log.e(TAG, "reader.close(): Exception ", e4);
                }
            }
            throw th;
        }
        return str;
    }

    public final String KernelVersion(String str) {
        Matcher matcher = Pattern.compile("Linux version (\\S+) \\((\\S+)\\).*(#\\d+).*((?:Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(str);
        if (!matcher.matches()) {
            Log.e(TAG, "KernelVersion(): Regex did not match on /proc/version: " + str);
            return "Unavailable";
        }
        if (matcher.groupCount() < 4) {
            Log.e(TAG, "KernelVersion(): Regex match on /proc/version only returned " + matcher.groupCount() + " groups");
            return "Unavailable";
        }
        return matcher.group(1);
    }

    public final String KernelVersionVZW(String str) {
        Matcher matcher = Pattern.compile("Linux version (\\S+) \\((\\S+?)\\) (?:(\\(gcc.+? \\))|(\\(clang.+?\\))) (#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(str);
        if (!matcher.matches()) {
            Log.e(TAG, "KernelVersionVZW(): Regex did not match on /proc/version: " + str);
        } else if (matcher.groupCount() < 4) {
            Log.e(TAG, "KernelVersionVZW(): Regex match on /proc/version only returned " + matcher.groupCount() + " groups");
            return "Unavailable";
        }
        String[] split = matcher.group(1).split(PackageManagerShellCommandDataLoader.STDIN_PATH);
        if (split.length == 3) {
            split[0] = split[0] + PackageManagerShellCommandDataLoader.STDIN_PATH + split[2];
        }
        return split[0];
    }

    public String getBuildNumber() {
        if ("CHM".equals(SemSystemProperties.getSalesCode())) {
            StringBuilder sb = new StringBuilder(Build.DISPLAY);
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(SystemProperties.get("ro.build.date", "Unknown"), " ");
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
                    strArr[1] = "01";
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
                    strArr[1] = "11";
                } else {
                    strArr[1] = "12";
                }
                if (strArr[2].length() == 1) {
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    sb.append(strArr[5]);
                    sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                    sb.append(strArr[1]);
                    sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                    sb.append("0");
                    sb.append(strArr[2]);
                    return sb.toString();
                }
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                sb.append(strArr[5]);
                sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                sb.append(strArr[1]);
                sb.append(PackageManagerShellCommandDataLoader.STDIN_PATH);
                sb.append(strArr[2]);
                return sb.toString();
            } catch (NullPointerException unused) {
                Log.e(TAG, "getBuildNumber(): china version return Normal build number");
                return sb.toString();
            }
        }
        return Build.DISPLAY;
    }

    public String getSEAndroidVersion() {
        if (SELinux.isSELinuxEnabled()) {
            if (SELinux.isSELinuxEnforced()) {
                return "Enforcing";
            }
            return "Permissive";
        }
        return "Disabled";
    }

    public String getKnoxVersion() {
        return "Knox " + EnterpriseKnoxManager.getInstance().getVersion().getInternalVersion();
    }

    public String getSecuritySWVersion() {
        String str;
        String securityMDFVersion = getSecurityMDFVersion();
        if ("".equals(securityMDFVersion)) {
            str = "";
        } else {
            str = "MDF " + securityMDFVersion;
        }
        String securityWLANVersion = getSecurityWLANVersion();
        if (!"".equals(securityWLANVersion)) {
            if ("".equals(str)) {
                str = str + " ";
            }
            str = str + "WLAN " + securityWLANVersion;
        }
        String securityVPNVersion = getSecurityVPNVersion();
        if (!"".equals(securityVPNVersion)) {
            if ("".equals(str)) {
                str = str + " ";
            }
            str = str + "VPN " + securityVPNVersion;
        }
        String securityASKSVersion = getSecurityASKSVersion();
        if (!"".equals(securityASKSVersion)) {
            if ("".equals(str)) {
                str = str + " ";
            }
            str = str + "ASKS " + securityASKSVersion;
        }
        String securityFIPSVersion = getSecurityFIPSVersion();
        if (!"".equals(securityFIPSVersion)) {
            if ("".equals(str)) {
                str = str + " ";
            }
            str = str + securityFIPSVersion;
        }
        String securitySPLVersion = getSecuritySPLVersion();
        if ("".equals(securitySPLVersion)) {
            return str;
        }
        if ("".equals(str)) {
            str = str + " ";
        }
        return str + "SPL " + securitySPLVersion;
    }

    public final String getSecurityMDFVersion() {
        String str = "";
        if (!"Enabled".equals(SystemProperties.get("ro.security.mdf.ux"))) {
            return "";
        }
        String str2 = "v" + SystemProperties.get("ro.security.mdf.ver");
        String str3 = " Release " + SystemProperties.get("ro.security.mdf.release");
        String str4 = SystemProperties.get("security.mdf");
        if ("Disabled".equals(str4)) {
            str = " : " + str4;
        }
        return str2 + str3 + str;
    }

    public final String getSecurityWLANVersion() {
        if ("".equals(SystemProperties.get("ro.security.wlan.ver"))) {
            return "";
        }
        return ("v" + SystemProperties.get("ro.security.wlan.ver")) + (" Release " + SystemProperties.get("ro.security.wlan.release"));
    }

    public final String getSecurityVPNVersion() {
        if ("".equals(SystemProperties.get("ro.security.vpnpp.ver"))) {
            return "";
        }
        return ("v" + SystemProperties.get("ro.security.vpnpp.ver")) + (" Release " + SystemProperties.get("ro.security.vpnpp.release"));
    }

    public final String getSecurityASKSVersion() {
        return ("v" + SystemProperties.get("security.ASKS.version", "0") + " Release ") + SystemProperties.get("security.ASKS.policy_version", "000000");
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
                str = str + " ";
            }
            str = str + "FIPS SKC v" + SystemProperties.get("ro.security.fips_skc.ver");
        }
        if (!"".equals(SystemProperties.get("ro.security.fips_scrypto.ver"))) {
            if (!"".equals(str)) {
                str = str + " ";
            }
            str = str + "FIPS SCrypto v" + SystemProperties.get("ro.security.fips_scrypto.ver");
        }
        if ("".equals(SystemProperties.get("ro.security.fips_fmp.ver"))) {
            return str;
        }
        if (!"".equals(str)) {
            str = str + " ";
        }
        return str + "FIPS FMP v" + SystemProperties.get("ro.security.fips_fmp.ver");
    }

    public final String getSecuritySPLVersion() {
        String str;
        String str2 = Build.VERSION.SECURITY_PATCH;
        try {
            str = new SimpleDateFormat("MMM-yyyy", Locale.ENGLISH).format(new SimpleDateFormat("yyyy-MM-dd").parse(str2));
        } catch (Exception e) {
            Log.e(TAG, "getSecuritySPLVersion(): Exception ", e);
            str = "";
        }
        if ("".equals(str)) {
            return "";
        }
        return "SMR " + str + " Release " + SystemProperties.get("ro.build.version.security_index", "");
    }

    public String getAndroidSecurityPathLevel() {
        String str = Build.VERSION.SECURITY_PATCH;
        if ("".equals(str)) {
            return "";
        }
        try {
            return DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), "dMMMMyyyy"), new SimpleDateFormat("yyyy-MM-dd").parse(str)).toString();
        } catch (Exception e) {
            Log.e(TAG, "getAndroidSecurityPathLevel(): Exception ", e);
            return str;
        }
    }
}
