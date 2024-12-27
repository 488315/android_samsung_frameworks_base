package com.android.server.location.gnss;

import android.R;
import android.content.Context;
import android.location.flags.Flags;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemCarrierFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class GnssConfiguration {
    public static final boolean DEBUG = Log.isLoggable("GnssConfiguration", 3);
    public final Context mContext;
    public int mEsExtensionSec = 0;
    public final Properties mProperties = new Properties();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HalInterfaceVersion {
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

    public static int getEsExtensionSecCSC() {
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

    public static HalInterfaceVersion getHalInterfaceVersion() {
        return native_get_gnss_configuration_version();
    }

    public static void loadPropertiesFromGpsDebugConfig(Properties properties, String str) {
        FileInputStream fileInputStream;
        try {
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(new File(str));
            } catch (Throwable th) {
                th = th;
            }
            try {
                properties.load(fileInputStream);
                IoUtils.closeQuietly(fileInputStream);
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                IoUtils.closeQuietly(fileInputStream2);
                throw th;
            }
        } catch (IOException unused) {
            if (DEBUG) {
                Log.d("GnssConfiguration", "Could not open GPS configuration file ".concat(str));
            }
        }
    }

    private static native HalInterfaceVersion native_get_gnss_configuration_version();

    private static native boolean native_set_es_extension_sec(int i);

    private static native boolean native_set_satellite_blocklist(int[] iArr, int[] iArr2);

    public static void setEsExtensionSec() {
        int esExtensionSecCSC = getEsExtensionSecCSC();
        if (esExtensionSecCSC <= 0) {
            return;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(esExtensionSecCSC, "Set ES extension seconds : ", "GnssConfiguration");
        HalInterfaceVersion native_get_gnss_configuration_version = native_get_gnss_configuration_version();
        if (native_get_gnss_configuration_version == null || native_get_gnss_configuration_version.mMajor < 2 || native_set_es_extension_sec(esExtensionSecCSC)) {
            return;
        }
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(esExtensionSecCSC, "Unable to set ES_EXTENSION_SEC: ", "GnssConfiguration");
    }

    public static void setSatelliteBlocklist(int[] iArr, int[] iArr2) {
        native_set_satellite_blocklist(iArr, iArr2);
    }

    public final int getIntConfig(String str) {
        String property = this.mProperties.getProperty(str);
        if (TextUtils.isEmpty(property)) {
            return 0;
        }
        try {
            return Integer.decode(property).intValue();
        } catch (NumberFormatException unused) {
            Log.e("GnssConfiguration", XmlUtils$$ExternalSyntheticOutline0.m("Unable to parse config parameter ", str, " value: ", property, ". Using default value: 0"));
            return 0;
        }
    }

    public final boolean isWifiOnlyModel() {
        if (((TelephonyManager) this.mContext.getSystemService("phone")) != null) {
            return !r1.isDataCapable();
        }
        return false;
    }

    public final void loadPropertiesFromCarrierConfig(int i, boolean z) {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (carrierConfigManager == null) {
            return;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (!z || i < 0) {
            i = defaultDataSubscriptionId;
        }
        PersistableBundle configForSubId = SubscriptionManager.isValidSubscriptionId(i) ? carrierConfigManager.getConfigForSubId(i) : carrierConfigManager.getConfig();
        boolean z2 = DEBUG;
        if (configForSubId == null) {
            if (z2) {
                Log.d("GnssConfiguration", "SIM not ready, use default carrier config.");
            }
            configForSubId = CarrierConfigManager.getDefaultConfig();
        }
        for (String str : configForSubId.keySet()) {
            if (str != null && str.startsWith("gps.")) {
                String upperCase = str.substring(4).toUpperCase(Locale.ROOT);
                Object obj = configForSubId.get(str);
                if (z2) {
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

    public final void reloadGpsProperties(int i, boolean z) {
        int i2;
        boolean z2 = DEBUG;
        if (z2) {
            StringBuilder sb = new StringBuilder("Reset GPS properties, previous size = ");
            sb.append(this.mProperties.size());
            sb.append(", inEmergency:");
            sb.append(z);
            sb.append(", activeSubId=");
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, i, "GnssConfiguration");
        }
        loadPropertiesFromCarrierConfig(i, z);
        int i3 = 0;
        if (Flags.gnssConfigurationFromResource()) {
            Context context = this.mContext;
            Properties properties = this.mProperties;
            for (String str : context.getResources().getStringArray(R.array.resolver_target_actions_unpin)) {
                if (z2) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m("GnssParamsResource: ", str, "GnssConfiguration");
                }
                int indexOf = str.indexOf("=");
                if (indexOf <= 0 || (i2 = indexOf + 1) >= str.length()) {
                    Log.w("GnssConfiguration", "malformed contents: ".concat(str));
                } else {
                    properties.setProperty(str.substring(0, indexOf).trim().toUpperCase(Locale.ROOT), str.substring(i2));
                }
            }
        }
        if (((TelephonyManager) this.mContext.getSystemService("phone")).getSimState() == 1) {
            String str2 = SystemProperties.get("persist.sys.gps.lpp");
            if (!TextUtils.isEmpty(str2)) {
                this.mProperties.setProperty("LPP_PROFILE", str2);
            }
        }
        loadPropertiesFromGpsDebugConfig(this.mProperties, "/vendor/etc/gps_debug.conf");
        loadPropertiesFromGpsDebugConfig(this.mProperties, "/etc/gps_debug.conf");
        int intConfig = getIntConfig("ES_EXTENSION_SEC");
        if (intConfig > 300) {
            Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + intConfig + " too high, reset to 300");
            i3 = 300;
        } else if (intConfig < 0) {
            Log.w("GnssConfiguration", "ES_EXTENSION_SEC: " + intConfig + " is negative, reset to zero.");
        } else {
            i3 = intConfig;
        }
        this.mEsExtensionSec = i3;
    }
}
