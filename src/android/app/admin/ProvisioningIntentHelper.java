package android.app.admin;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaMetrics;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/* loaded from: classes.dex */
final class ProvisioningIntentHelper {
    private static final Map<String, Class> EXTRAS_TO_CLASS_MAP = createExtrasToClassMap();
    private static final String TAG = "ProvisioningIntentHelper";

    private ProvisioningIntentHelper() {
    }

    public static Intent createProvisioningIntentFromNfcIntent(Intent intent) {
        Objects.requireNonNull(intent);
        if (!"android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction())) {
            Log.e(TAG, "Wrong Nfc action: " + intent.getAction());
            return null;
        }
        NdefRecord firstNdefRecord = getFirstNdefRecord(intent);
        if (firstNdefRecord != null) {
            return createProvisioningIntentFromNdefRecord(firstNdefRecord);
        }
        return null;
    }

    private static Intent createProvisioningIntentFromNdefRecord(NdefRecord ndefRecord) {
        Objects.requireNonNull(ndefRecord);
        Properties loadPropertiesFromPayload = loadPropertiesFromPayload(ndefRecord.getPayload());
        if (loadPropertiesFromPayload == null) {
            Log.e(TAG, "Failed to load NdefRecord properties.");
            return null;
        }
        Bundle createBundleFromProperties = createBundleFromProperties(loadPropertiesFromPayload);
        if (!containsRequiredProvisioningExtras(createBundleFromProperties)) {
            Log.e(TAG, "Bundle does not contain the required provisioning extras.");
            return null;
        }
        return createProvisioningIntentFromBundle(createBundleFromProperties);
    }

    private static Properties loadPropertiesFromPayload(byte[] bArr) {
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(new String(bArr, StandardCharsets.UTF_8)));
            return properties;
        } catch (IOException unused) {
            Log.e(TAG, "NFC Intent properties loading failed.");
            return null;
        }
    }

    private static Bundle createBundleFromProperties(Properties properties) {
        Enumeration<?> propertyNames = properties.propertyNames();
        Bundle bundle = new Bundle();
        while (propertyNames.hasMoreElements()) {
            addPropertyToBundle((String) propertyNames.nextElement(), properties, bundle);
        }
        return bundle;
    }

    private static void addPropertyToBundle(String str, Properties properties, Bundle bundle) {
        Map<String, Class> map = EXTRAS_TO_CLASS_MAP;
        if (map.get(str) == ComponentName.class) {
            bundle.putParcelable(str, ComponentName.unflattenFromString(properties.getProperty(str)));
            return;
        }
        if (map.get(str) == PersistableBundle.class) {
            try {
                bundle.putParcelable(str, deserializeExtrasBundle(properties, str));
                return;
            } catch (IOException e) {
                Log.e(TAG, "Failed to parse " + str + MediaMetrics.SEPARATOR, e);
                return;
            }
        }
        if (map.get(str) == Boolean.class) {
            bundle.putBoolean(str, Boolean.parseBoolean(properties.getProperty(str)));
            return;
        }
        if (map.get(str) == Long.class) {
            bundle.putLong(str, Long.parseLong(properties.getProperty(str)));
        } else if (map.get(str) == Integer.class) {
            bundle.putInt(str, Integer.parseInt(properties.getProperty(str)));
        } else {
            bundle.putString(str, properties.getProperty(str));
        }
    }

    private static PersistableBundle deserializeExtrasBundle(Properties properties, String str) throws IOException {
        String property = properties.getProperty(str);
        if (property == null) {
            return null;
        }
        Properties properties2 = new Properties();
        properties2.load(new StringReader(property));
        PersistableBundle persistableBundle = new PersistableBundle(properties2.size());
        for (String str2 : properties2.stringPropertyNames()) {
            persistableBundle.putString(str2, properties2.getProperty(str2));
        }
        return persistableBundle;
    }

    private static Intent createProvisioningIntentFromBundle(Bundle bundle) {
        Objects.requireNonNull(bundle);
        Intent intent = new Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE);
        intent.putExtras(bundle);
        intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_TRIGGER, 5);
        return intent;
    }

    private static boolean containsRequiredProvisioningExtras(Bundle bundle) {
        return bundle.containsKey(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME) || bundle.containsKey(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME);
    }

    private static NdefRecord getFirstNdefRecord(Intent intent) {
        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
        if (parcelableArrayExtra == null) {
            Log.i(TAG, "No EXTRA_NDEF_MESSAGES from nfcIntent");
            return null;
        }
        for (Parcelable parcelable : parcelableArrayExtra) {
            NdefRecord[] records = ((NdefMessage) parcelable).getRecords();
            if (records.length > 0) {
                NdefRecord ndefRecord = records[0];
                if (DevicePolicyManager.MIME_TYPE_PROVISIONING_NFC.equals(new String(ndefRecord.getType(), StandardCharsets.UTF_8))) {
                    return ndefRecord;
                }
            }
        }
        Log.i(TAG, "No compatible records found on nfcIntent");
        return null;
    }

    private static Map<String, Class> createExtrasToClassMap() {
        HashMap hashMap = new HashMap();
        Iterator<String> it = getBooleanExtras().iterator();
        while (it.hasNext()) {
            hashMap.put(it.next(), Boolean.class);
        }
        Iterator<String> it2 = getLongExtras().iterator();
        while (it2.hasNext()) {
            hashMap.put(it2.next(), Long.class);
        }
        Iterator<String> it3 = getIntExtras().iterator();
        while (it3.hasNext()) {
            hashMap.put(it3.next(), Integer.class);
        }
        Iterator<String> it4 = getComponentNameExtras().iterator();
        while (it4.hasNext()) {
            hashMap.put(it4.next(), ComponentName.class);
        }
        Iterator<String> it5 = getPersistableBundleExtras().iterator();
        while (it5.hasNext()) {
            hashMap.put(it5.next(), PersistableBundle.class);
        }
        return hashMap;
    }

    private static Set<String> getPersistableBundleExtras() {
        return Set.of(DevicePolicyManager.EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE, DevicePolicyManager.EXTRA_PROVISIONING_ROLE_HOLDER_EXTRAS_BUNDLE);
    }

    private static Set<String> getComponentNameExtras() {
        return Set.of(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME);
    }

    private static Set<String> getIntExtras() {
        return Set.of(DevicePolicyManager.EXTRA_PROVISIONING_WIFI_PROXY_PORT, DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE, DevicePolicyManager.EXTRA_PROVISIONING_SUPPORTED_MODES);
    }

    private static Set<String> getLongExtras() {
        return Set.of(DevicePolicyManager.EXTRA_PROVISIONING_LOCAL_TIME);
    }

    private static Set<String> getBooleanExtras() {
        return Set.of((Object[]) new String[]{DevicePolicyManager.EXTRA_PROVISIONING_ALLOW_OFFLINE, DevicePolicyManager.EXTRA_PROVISIONING_SHOULD_LAUNCH_RESULT_INTENT, DevicePolicyManager.EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION, DevicePolicyManager.EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED, DevicePolicyManager.EXTRA_PROVISIONING_WIFI_HIDDEN, DevicePolicyManager.EXTRA_PROVISIONING_SENSORS_PERMISSION_GRANT_OPT_OUT, DevicePolicyManager.EXTRA_PROVISIONING_SKIP_ENCRYPTION, DevicePolicyManager.EXTRA_PROVISIONING_SKIP_EDUCATION_SCREENS, DevicePolicyManager.EXTRA_PROVISIONING_USE_MOBILE_DATA, DevicePolicyManager.EXTRA_PROVISIONING_SKIP_OWNERSHIP_DISCLAIMER, DevicePolicyManager.EXTRA_PROVISIONING_RETURN_BEFORE_POLICY_COMPLIANCE, DevicePolicyManager.EXTRA_PROVISIONING_KEEP_SCREEN_ON});
    }
}
