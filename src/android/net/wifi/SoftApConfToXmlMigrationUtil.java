package android.net.wifi;

import android.net.MacAddress;
import android.net.wifi.SoftApConfiguration;
import android.os.Environment;
import android.util.Log;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public final class SoftApConfToXmlMigrationUtil {
    private static final int CONFIG_STORE_DATA_VERSION = 3;
    private static final String LEGACY_AP_CONFIG_FILE = "softap.conf";
    private static final String LEGACY_WIFI_STORE_DIRECTORY_NAME = "wifi";
    private static final String TAG = "SoftApConfToXmlMigrationUtil";
    private static final int WIFICONFIG_AP_BAND_2GHZ = 0;
    private static final int WIFICONFIG_AP_BAND_5GHZ = 1;
    private static final int WIFICONFIG_AP_BAND_ANY = -1;
    private static final String XML_TAG_ALLOWED_CLIENT_LIST = "AllowedClientList";
    private static final String XML_TAG_AP_BAND = "ApBand";
    private static final String XML_TAG_AUTO_SHUTDOWN_ENABLED = "AutoShutdownEnabled";
    private static final String XML_TAG_BLOCKED_CLIENT_LIST = "BlockedClientList";
    private static final String XML_TAG_BSSID = "Bssid";
    private static final String XML_TAG_CHANNEL = "Channel";
    private static final String XML_TAG_CLIENT_CONTROL_BY_USER = "ClientControlByUser";
    public static final String XML_TAG_CLIENT_MACADDRESS = "ClientMacAddress";
    private static final String XML_TAG_DOCUMENT_HEADER = "WifiConfigStoreData";
    private static final String XML_TAG_HIDDEN_SSID = "HiddenSSID";
    private static final String XML_TAG_MAX_NUMBER_OF_CLIENTS = "MaxNumberOfClients";
    private static final String XML_TAG_PASSPHRASE = "Passphrase";
    private static final String XML_TAG_SECTION_HEADER_SOFTAP = "SoftAp";
    private static final String XML_TAG_SECURITY_TYPE = "SecurityType";
    private static final String XML_TAG_SHUTDOWN_TIMEOUT_MILLIS = "ShutdownTimeoutMillis";
    private static final String XML_TAG_SSID = "SSID";
    private static final String XML_TAG_VERSION = "Version";

    public static int convertWifiConfigBandToSoftApConfigBand(int i) {
        if (i != -1) {
            return i != 1 ? 1 : 2;
        }
        return 3;
    }

    private static File getLegacyWifiSharedDirectory() {
        return new File(Environment.getDataMiscDirectory(), "wifi");
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0095: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:50:0x0095 */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static SoftApConfiguration loadFromLegacyFile(InputStream inputStream) throws Throwable {
        DataInputStream dataInputStream;
        DataInputStream dataInputStream2;
        SoftApConfiguration.Builder builder;
        int i;
        SoftApConfiguration softApConfigurationBuild = null;
        softApConfigurationBuild = null;
        softApConfigurationBuild = null;
        softApConfigurationBuild = null;
        softApConfigurationBuild = null;
        DataInputStream dataInputStream3 = null;
        try {
            try {
                try {
                    builder = new SoftApConfiguration.Builder();
                    dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                    try {
                        i = dataInputStream.readInt();
                    } catch (IOException e) {
                        e = e;
                        Log.e(TAG, "Error reading hotspot configuration ", e);
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        return softApConfigurationBuild;
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        Log.e(TAG, "Invalid hotspot configuration ", e);
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        return softApConfigurationBuild;
                    }
                } catch (Throwable th) {
                    th = th;
                    dataInputStream3 = dataInputStream2;
                    if (dataInputStream3 != null) {
                        try {
                            dataInputStream3.close();
                        } catch (IOException e3) {
                            Log.e(TAG, "Error closing hotspot configuration during read", e3);
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                dataInputStream = null;
            } catch (IllegalArgumentException e5) {
                e = e5;
                dataInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                if (dataInputStream3 != null) {
                }
                throw th;
            }
        } catch (IOException e6) {
            Log.e(TAG, "Error closing hotspot configuration during read", e6);
        }
        if (i >= 1 && i <= 3) {
            builder.setSsid(dataInputStream.readUTF());
            if (i >= 2) {
                int i2 = dataInputStream.readInt();
                int i3 = dataInputStream.readInt();
                if (i3 == 0) {
                    builder.setBand(convertWifiConfigBandToSoftApConfigBand(i2));
                } else {
                    builder.setChannel(i3, convertWifiConfigBandToSoftApConfigBand(i2));
                }
            }
            if (i >= 3) {
                builder.setHiddenSsid(dataInputStream.readBoolean());
            }
            if (dataInputStream.readInt() == 4) {
                builder.setPassphrase(dataInputStream.readUTF(), 1);
            }
            softApConfigurationBuild = builder.build();
            dataInputStream.close();
            return softApConfigurationBuild;
        }
        Log.e(TAG, "Bad version on hotspot configuration file");
        try {
            dataInputStream.close();
            return null;
        } catch (IOException e7) {
            Log.e(TAG, "Error closing hotspot configuration during read", e7);
            return null;
        }
    }

    private static byte[] convertConfToXml(SoftApConfiguration softApConfiguration) throws IllegalStateException, IOException, IllegalArgumentException {
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            fastXmlSerializer.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument(null, true);
            fastXmlSerializer.startTag(null, XML_TAG_DOCUMENT_HEADER);
            XmlUtils.writeValueXml((Object) 3, XML_TAG_VERSION, (XmlSerializer) fastXmlSerializer);
            fastXmlSerializer.startTag(null, XML_TAG_SECTION_HEADER_SOFTAP);
            XmlUtils.writeValueXml(softApConfiguration.getSsid(), XML_TAG_SSID, fastXmlSerializer);
            if (softApConfiguration.getBssid() != null) {
                XmlUtils.writeValueXml(softApConfiguration.getBssid().toString(), XML_TAG_BSSID, fastXmlSerializer);
            }
            XmlUtils.writeValueXml(Integer.valueOf(softApConfiguration.getBand()), XML_TAG_AP_BAND, fastXmlSerializer);
            XmlUtils.writeValueXml(Integer.valueOf(softApConfiguration.getChannel()), XML_TAG_CHANNEL, fastXmlSerializer);
            XmlUtils.writeValueXml(Boolean.valueOf(softApConfiguration.isHiddenSsid()), XML_TAG_HIDDEN_SSID, fastXmlSerializer);
            XmlUtils.writeValueXml(Integer.valueOf(softApConfiguration.getSecurityType()), XML_TAG_SECURITY_TYPE, fastXmlSerializer);
            if (softApConfiguration.getSecurityType() != 0) {
                XmlUtils.writeValueXml(softApConfiguration.getPassphrase(), XML_TAG_PASSPHRASE, fastXmlSerializer);
            }
            XmlUtils.writeValueXml(Integer.valueOf(softApConfiguration.getMaxNumberOfClients()), XML_TAG_MAX_NUMBER_OF_CLIENTS, fastXmlSerializer);
            XmlUtils.writeValueXml(Boolean.valueOf(softApConfiguration.isClientControlByUserEnabled()), XML_TAG_CLIENT_CONTROL_BY_USER, fastXmlSerializer);
            XmlUtils.writeValueXml(Boolean.valueOf(softApConfiguration.isAutoShutdownEnabled()), XML_TAG_AUTO_SHUTDOWN_ENABLED, fastXmlSerializer);
            XmlUtils.writeValueXml(Long.valueOf(softApConfiguration.getShutdownTimeoutMillis()), XML_TAG_SHUTDOWN_TIMEOUT_MILLIS, fastXmlSerializer);
            fastXmlSerializer.startTag(null, XML_TAG_BLOCKED_CLIENT_LIST);
            Iterator it = softApConfiguration.getBlockedClientList().iterator();
            while (it.hasNext()) {
                XmlUtils.writeValueXml(((MacAddress) it.next()).toString(), XML_TAG_CLIENT_MACADDRESS, fastXmlSerializer);
            }
            fastXmlSerializer.endTag(null, XML_TAG_BLOCKED_CLIENT_LIST);
            fastXmlSerializer.startTag(null, XML_TAG_ALLOWED_CLIENT_LIST);
            Iterator it2 = softApConfiguration.getAllowedClientList().iterator();
            while (it2.hasNext()) {
                XmlUtils.writeValueXml(((MacAddress) it2.next()).toString(), XML_TAG_CLIENT_MACADDRESS, fastXmlSerializer);
            }
            fastXmlSerializer.endTag(null, XML_TAG_ALLOWED_CLIENT_LIST);
            fastXmlSerializer.endTag(null, XML_TAG_SECTION_HEADER_SOFTAP);
            fastXmlSerializer.endTag(null, XML_TAG_DOCUMENT_HEADER);
            fastXmlSerializer.endDocument();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Failed to convert softap conf to XML", e);
            return null;
        }
    }

    private SoftApConfToXmlMigrationUtil() {
    }

    public static InputStream convert(InputStream inputStream) throws Throwable {
        byte[] bArrConvertConfToXml;
        SoftApConfiguration softApConfigurationLoadFromLegacyFile = loadFromLegacyFile(inputStream);
        if (softApConfigurationLoadFromLegacyFile == null || (bArrConvertConfToXml = convertConfToXml(softApConfigurationLoadFromLegacyFile)) == null) {
            return null;
        }
        return new ByteArrayInputStream(bArrConvertConfToXml);
    }

    public static InputStream convert() {
        try {
            return convert(new FileInputStream(new File(getLegacyWifiSharedDirectory(), LEGACY_AP_CONFIG_FILE)));
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    public static void remove() {
        new File(getLegacyWifiSharedDirectory(), LEGACY_AP_CONFIG_FILE).delete();
    }
}
