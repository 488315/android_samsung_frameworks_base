package android.content.pm;

import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ConstrainDisplayApisConfig {
    private static final String FLAG_ALWAYS_CONSTRAIN_DISPLAY_APIS = "always_constrain_display_apis";
    private static final String FLAG_NEVER_CONSTRAIN_DISPLAY_APIS = "never_constrain_display_apis";
    private static final String FLAG_NEVER_CONSTRAIN_DISPLAY_APIS_ALL_PACKAGES = "never_constrain_display_apis_all_packages";
    private static final String TAG = "ConstrainDisplayApisConfig";
    private ArrayMap<String, Pair<Long, Long>> mAlwaysConstrainConfigMap;
    private ArrayMap<String, Pair<Long, Long>> mNeverConstrainConfigMap;
    private boolean mNeverConstrainDisplayApisAllPackages;

    public ConstrainDisplayApisConfig() {
        updateCache();
        DeviceConfig.addOnPropertiesChangedListener("constrain_display_apis", BackgroundThread.getExecutor(), new DeviceConfig.OnPropertiesChangedListener() { // from class: android.content.pm.ConstrainDisplayApisConfig$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                this.f$0.lambda$new$0(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
        updateCache();
    }

    public boolean getNeverConstrainDisplayApis(ApplicationInfo applicationInfo) {
        if (this.mNeverConstrainDisplayApisAllPackages) {
            return true;
        }
        return flagHasMatchingPackageEntry(this.mNeverConstrainConfigMap, applicationInfo);
    }

    public boolean getAlwaysConstrainDisplayApis(ApplicationInfo applicationInfo) {
        return flagHasMatchingPackageEntry(this.mAlwaysConstrainConfigMap, applicationInfo);
    }

    private void updateCache() {
        this.mNeverConstrainDisplayApisAllPackages = DeviceConfig.getBoolean("constrain_display_apis", FLAG_NEVER_CONSTRAIN_DISPLAY_APIS_ALL_PACKAGES, false);
        this.mNeverConstrainConfigMap = buildConfigMap(DeviceConfig.getString("constrain_display_apis", FLAG_NEVER_CONSTRAIN_DISPLAY_APIS, ""));
        this.mAlwaysConstrainConfigMap = buildConfigMap(DeviceConfig.getString("constrain_display_apis", FLAG_ALWAYS_CONSTRAIN_DISPLAY_APIS, ""));
    }

    private static ArrayMap<String, Pair<Long, Long>> buildConfigMap(String str) {
        ArrayMap<String, Pair<Long, Long>> arrayMap = new ArrayMap<>();
        if (!str.isEmpty()) {
            for (String str2 : str.split(",")) {
                List listAsList = Arrays.asList(str2.split(":", 3));
                if (listAsList.size() != 3) {
                    Slog.w(TAG, "Invalid package entry in flag 'never/always_constrain_display_apis': " + str2);
                } else {
                    String str3 = (String) listAsList.get(0);
                    String str4 = (String) listAsList.get(1);
                    String str5 = (String) listAsList.get(2);
                    try {
                        arrayMap.put(str3, new Pair<>(Long.valueOf(str4.isEmpty() ? Long.MIN_VALUE : Long.parseLong(str4)), Long.valueOf(str5.isEmpty() ? Long.MAX_VALUE : Long.parseLong(str5))));
                    } catch (NumberFormatException unused) {
                        Slog.w(TAG, "Invalid APK version code in package entry: " + str2);
                    }
                }
            }
        }
        return arrayMap;
    }

    private static boolean flagHasMatchingPackageEntry(Map<String, Pair<Long, Long>> map, ApplicationInfo applicationInfo) {
        if (!map.isEmpty() && map.containsKey(applicationInfo.packageName)) {
            return matchesApplicationInfo(map.get(applicationInfo.packageName), applicationInfo);
        }
        return false;
    }

    private static boolean matchesApplicationInfo(Pair<Long, Long> pair, ApplicationInfo applicationInfo) {
        return applicationInfo.longVersionCode >= pair.first.longValue() && applicationInfo.longVersionCode <= pair.second.longValue();
    }
}
