package android.net.wifi;

import android.net.MacAddress;
import android.os.Environment;
import android.util.Log;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0095: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:46:0x0095 */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.net.wifi.SoftApConfiguration loadFromLegacyFile(java.io.InputStream r9) {
        /*
            java.lang.String r0 = "Error closing hotspot configuration during read"
            java.lang.String r1 = "SoftApConfToXmlMigrationUtil"
            r2 = 0
            android.net.wifi.SoftApConfiguration$Builder r3 = new android.net.wifi.SoftApConfiguration$Builder     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            r3.<init>()     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            java.io.DataInputStream r4 = new java.io.DataInputStream     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            r5.<init>(r9)     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L78 java.lang.IllegalArgumentException -> L7a java.io.IOException -> L87
            int r9 = r4.readInt()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r5 = 1
            if (r9 < r5) goto L66
            r6 = 3
            if (r9 <= r6) goto L1f
            goto L66
        L1f:
            java.lang.String r7 = r4.readUTF()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r3.setSsid(r7)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r7 = 2
            if (r9 < r7) goto L42
            int r7 = r4.readInt()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            int r8 = r4.readInt()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            if (r8 != 0) goto L3b
            int r7 = convertWifiConfigBandToSoftApConfigBand(r7)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r3.setBand(r7)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            goto L42
        L3b:
            int r7 = convertWifiConfigBandToSoftApConfigBand(r7)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r3.setChannel(r8, r7)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
        L42:
            if (r9 < r6) goto L4b
            boolean r9 = r4.readBoolean()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r3.setHiddenSsid(r9)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
        L4b:
            int r9 = r4.readInt()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r6 = 4
            if (r9 != r6) goto L59
            java.lang.String r9 = r4.readUTF()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r3.setPassphrase(r9, r5)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
        L59:
            android.net.wifi.SoftApConfiguration r2 = r3.build()     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r4.close()     // Catch: java.io.IOException -> L61
            goto L93
        L61:
            r9 = move-exception
            android.util.Log.e(r1, r0, r9)
            goto L93
        L66:
            java.lang.String r9 = "Bad version on hotspot configuration file"
            android.util.Log.e(r1, r9)     // Catch: java.lang.IllegalArgumentException -> L74 java.io.IOException -> L76 java.lang.Throwable -> L94
            r4.close()     // Catch: java.io.IOException -> L6f
            return r2
        L6f:
            r9 = move-exception
            android.util.Log.e(r1, r0, r9)
            return r2
        L74:
            r9 = move-exception
            goto L7c
        L76:
            r9 = move-exception
            goto L89
        L78:
            r9 = move-exception
            goto L96
        L7a:
            r9 = move-exception
            r4 = r2
        L7c:
            java.lang.String r3 = "Invalid hotspot configuration "
            android.util.Log.e(r1, r3, r9)     // Catch: java.lang.Throwable -> L94
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L61
            goto L93
        L87:
            r9 = move-exception
            r4 = r2
        L89:
            java.lang.String r3 = "Error reading hotspot configuration "
            android.util.Log.e(r1, r3, r9)     // Catch: java.lang.Throwable -> L94
            if (r4 == 0) goto L93
            r4.close()     // Catch: java.io.IOException -> L61
        L93:
            return r2
        L94:
            r9 = move-exception
            r2 = r4
        L96:
            if (r2 == 0) goto La0
            r2.close()     // Catch: java.io.IOException -> L9c
            goto La0
        L9c:
            r2 = move-exception
            android.util.Log.e(r1, r0, r2)
        La0:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.SoftApConfToXmlMigrationUtil.loadFromLegacyFile(java.io.InputStream):android.net.wifi.SoftApConfiguration");
    }

    private static byte[] convertConfToXml(SoftApConfiguration softApConfiguration) {
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

    public static InputStream convert(InputStream inputStream) {
        byte[] convertConfToXml;
        SoftApConfiguration loadFromLegacyFile = loadFromLegacyFile(inputStream);
        if (loadFromLegacyFile == null || (convertConfToXml = convertConfToXml(loadFromLegacyFile)) == null) {
            return null;
        }
        return new ByteArrayInputStream(convertConfToXml);
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
