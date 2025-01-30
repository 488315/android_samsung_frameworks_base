package com.android.server.location.gnss;

import android.content.Context;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.feature.SemCarrierFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import libcore.io.IoUtils;

/* loaded from: classes2.dex */
public class GnssConfiguration {
    public static final boolean DEBUG = Log.isLoggable("GnssConfiguration", 3);
    public final Context mContext;
    public int mEsExtensionSec = 0;
    public final Properties mProperties = new Properties();

    private static native HalInterfaceVersion native_get_gnss_configuration_version();

    private static native boolean native_set_es_extension_sec(int i);

    private static native boolean native_set_satellite_blocklist(int[] iArr, int[] iArr2);

    public class HalInterfaceVersion {
        public static final int AIDL_INTERFACE = 3;
        public final int mMajor;
        public final int mMinor;

        public HalInterfaceVersion(int i, int i2) {
            this.mMajor = i;
            this.mMinor = i2;
        }
    }

    public GnssConfiguration(Context context) {
        this.mContext = context;
    }

    public Properties getProperties() {
        return this.mProperties;
    }

    public int getEsExtensionSec() {
        return this.mEsExtensionSec;
    }

    public int getSuplMode(int i) {
        return getIntConfig("SUPL_MODE", i);
    }

    public int getSuplEs(int i) {
        return getIntConfig("SUPL_ES", i);
    }

    public String getLppProfile() {
        return this.mProperties.getProperty("LPP_PROFILE");
    }

    public List getProxyApps() {
        String property = this.mProperties.getProperty("NFW_PROXY_APPS");
        if (TextUtils.isEmpty(property)) {
            return Collections.emptyList();
        }
        String[] split = property.trim().split("\\s+");
        if (split.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.asList(split);
    }

    public boolean isPsdsPeriodicDownloadEnabled() {
        return getBooleanConfig("ENABLE_PSDS_PERIODIC_DOWNLOAD", false);
    }

    public boolean isActiveSimEmergencySuplEnabled() {
        return getBooleanConfig("ENABLE_ACTIVE_SIM_EMERGENCY_SUPL", false);
    }

    public boolean isLongTermPsdsServerConfigured() {
        return (this.mProperties.getProperty("LONGTERM_PSDS_SERVER_1") == null && this.mProperties.getProperty("LONGTERM_PSDS_SERVER_2") == null && this.mProperties.getProperty("LONGTERM_PSDS_SERVER_3") == null) ? false : true;
    }

    public void setSatelliteBlocklist(int[] iArr, int[] iArr2) {
        native_set_satellite_blocklist(iArr, iArr2);
    }

    public HalInterfaceVersion getHalInterfaceVersion() {
        return native_get_gnss_configuration_version();
    }

    public void reloadGpsProperties() {
        reloadGpsProperties(false, -1);
    }

    public void reloadGpsProperties(boolean z, int i) {
        if (DEBUG) {
            Log.d("GnssConfiguration", "Reset GPS properties, previous size = " + this.mProperties.size() + ", inEmergency:" + z + ", activeSubId=" + i);
        }
        loadPropertiesFromCarrierConfig(z, i);
        if (isSimAbsent(this.mContext)) {
            String str = SystemProperties.get("persist.sys.gps.lpp");
            if (!TextUtils.isEmpty(str)) {
                this.mProperties.setProperty("LPP_PROFILE", str);
            }
        }
        loadPropertiesFromGpsDebugConfig(this.mProperties, "/vendor/etc/gps_debug.conf");
        loadPropertiesFromGpsDebugConfig(this.mProperties, "/etc/gps_debug.conf");
        this.mEsExtensionSec = getRangeCheckedConfigEsExtensionSec();
    }

    public void loadPropertiesFromCarrierConfig(boolean z, int i) {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (carrierConfigManager == null) {
            return;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (!z || i < 0) {
            i = defaultDataSubscriptionId;
        }
        PersistableBundle configForSubId = SubscriptionManager.isValidSubscriptionId(i) ? carrierConfigManager.getConfigForSubId(i) : carrierConfigManager.getConfig();
        if (configForSubId == null) {
            if (DEBUG) {
                Log.d("GnssConfiguration", "SIM not ready, use default carrier config.");
            }
            configForSubId = CarrierConfigManager.getDefaultConfig();
        }
        for (String str : configForSubId.keySet()) {
            if (str.startsWith("gps.")) {
                String upperCase = str.substring(4).toUpperCase();
                Object obj = configForSubId.get(str);
                if (DEBUG) {
                    Log.d("GnssConfiguration", "Gps config: " + upperCase + " = " + obj);
                }
                if (obj instanceof String) {
                    this.mProperties.setProperty(upperCase, (String) obj);
                } else if (obj != null) {
                    this.mProperties.setProperty(upperCase, obj.toString());
                }
            }
        }
    }

    public final void loadPropertiesFromGpsDebugConfig(Properties properties, String str) {
        try {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(new File(str));
                try {
                    properties.load(fileInputStream2);
                    IoUtils.closeQuietly(fileInputStream2);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    IoUtils.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException unused) {
            if (DEBUG) {
                Log.d("GnssConfiguration", "Could not open GPS configuration file " + str);
            }
        }
    }

    public final int getRangeCheckedConfigEsExtensionSec() {
        int intConfig = getIntConfig("ES_EXTENSION_SEC", 0);
        if (intConfig > 300) {
            Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + intConfig + " too high, reset to 300");
            return 300;
        }
        if (intConfig >= 0) {
            return intConfig;
        }
        Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + intConfig + " is negative, reset to zero.");
        return 0;
    }

    public int getEsExtensionSecCSC() {
        int i;
        try {
            i = Integer.parseInt(SystemProperties.get("persist.sys.gps.dds.subId", "0"));
        } catch (NumberFormatException unused) {
            Log.w("GnssConfiguration", "Sim slot property has wrong value, set 0");
            i = 0;
        }
        int i2 = SemCarrierFeature.getInstance().getInt(i, "CarrierFeature_GPS_ConfigEsExtensionSec", 0, false);
        if (i2 > 300) {
            Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + i2 + " too high, reset to 300");
            return 300;
        }
        if (i2 >= 0) {
            return i2;
        }
        Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + i2 + " is negative, reset to zero.");
        return 0;
    }

    public void setEsExtensionSec() {
        int esExtensionSecCSC = getEsExtensionSecCSC();
        if (esExtensionSecCSC <= 0) {
            return;
        }
        Log.d("GnssConfiguration", "Set ES extension seconds : " + esExtensionSecCSC);
        HalInterfaceVersion native_get_gnss_configuration_version = native_get_gnss_configuration_version();
        if (native_get_gnss_configuration_version == null || !isConfigEsExtensionSecSupported(native_get_gnss_configuration_version) || native_set_es_extension_sec(esExtensionSecCSC)) {
            return;
        }
        Log.e("GnssConfiguration", "Unable to set ES_EXTENSION_SEC: " + esExtensionSecCSC);
    }

    public final int getIntConfig(String str, int i) {
        String property = this.mProperties.getProperty(str);
        if (TextUtils.isEmpty(property)) {
            return i;
        }
        try {
            return Integer.decode(property).intValue();
        } catch (NumberFormatException unused) {
            Log.e("GnssConfiguration", "Unable to parse config parameter " + str + " value: " + property + ". Using default value: " + i);
            return i;
        }
    }

    public final boolean getBooleanConfig(String str, boolean z) {
        String property = this.mProperties.getProperty(str);
        return TextUtils.isEmpty(property) ? z : Boolean.parseBoolean(property);
    }

    public static boolean isConfigEsExtensionSecSupported(HalInterfaceVersion halInterfaceVersion) {
        return halInterfaceVersion.mMajor >= 2;
    }

    public static boolean isSimAbsent(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimState() == 1;
    }

    public boolean isWifiOnlyModel() {
        if (((TelephonyManager) this.mContext.getSystemService("phone")) != null) {
            return !r1.isDataCapable();
        }
        return false;
    }
}
