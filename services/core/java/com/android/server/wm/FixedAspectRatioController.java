package com.android.server.wm;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class FixedAspectRatioController extends PackagesChange implements BoundsCompatController {
    public final SystemChange mSystemChange;
    public final PackageFeatureUserChange mUserChange;

    public FixedAspectRatioController(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        this.mSystemChange = new SystemChange();
        PackageFeatureUserChange packageFeatureUserChange = new PackageFeatureUserChange(8, PackageFeatureUserChangePersister.CONVENTIONAL_MODE_DIRECTORY, "FixedAspectRatioPackageMap");
        this.mUserChange = packageFeatureUserChange;
        setUserChanges(packageFeatureUserChange);
    }

    public float getFixedAspectRatio(ActivityRecord activityRecord) {
        if (activityRecord.info.packageName == null) {
            return -1.0f;
        }
        Task task = activityRecord.getTask();
        if (activityRecord.mCompatRecord.mWaitForChangingPinnedMode || !(task == null || task.inMultiWindowMode())) {
            return getMergedChange(task.mUserId, activityRecord.info.packageName);
        }
        return -1.0f;
    }

    public void computeConfigResourceOverridesIfNeeded(ActivityRecord activityRecord, Configuration configuration, Rect rect, Configuration configuration2) {
        if (configuration.smallestScreenWidthDp == 0 || activityRecord.isFixedRotationTransforming()) {
            float f = configuration.densityDpi;
            if (f == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                f = configuration2.densityDpi;
            }
            configuration.smallestScreenWidthDp = (int) (Math.min(rect.width(), rect.height()) / (f * 0.00625f));
        }
    }

    @Override // com.android.server.wm.BoundsCompatController
    public void adjustBounds(ActivityRecord activityRecord, Configuration configuration) {
        getBoundsCompatUtils().adjustBoundsAsMinAspectRatio(activityRecord, configuration);
    }

    public float getUserChange(int i, String str) {
        Float userChangeWithLegacy = getUserChangeWithLegacy(i, str);
        if (userChangeWithLegacy != null) {
            return userChangeWithLegacy.floatValue();
        }
        return -1.0f;
    }

    public final Float getUserChangeWithLegacy(int i, String str) {
        boolean isMinAspectRatioOverrideDisallowed = isMinAspectRatioOverrideDisallowed(str, i);
        Float valueOf = Float.valueOf(-1.0f);
        if (isMinAspectRatioOverrideDisallowed) {
            return valueOf;
        }
        Float f = (Float) this.mUserChange.getValue(i, str);
        return (f == null || f.floatValue() != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) ? f : valueOf;
    }

    public float getSystemChange(String str) {
        return this.mSystemChange.getAspectRatio(str);
    }

    public float getMergedChange(int i, String str) {
        Float userChangeWithLegacy = getUserChangeWithLegacy(i, str);
        return userChangeWithLegacy != null ? userChangeWithLegacy.floatValue() : getSystemChange(str);
    }

    public ConcurrentHashMap getChangeValuesAsUser(int i) {
        return this.mUserChange.getChangeValuesAsUser(i);
    }

    public void requestToSave(int i) {
        this.mUserChange.requestToSave(i);
    }

    class SystemChange extends ConcurrentHashMap implements PackageFeatureCallback {
        public static final float DEFAULT_RATIO;

        static {
            DEFAULT_RATIO = CoreRune.FW_TABLET_MIN_ASPECT_RATIO_MODE ? 2.0555556f : -1.0f;
        }

        private SystemChange() {
            PackageFeature.FIXED_ASPECT_RATIO.registerCallback(this);
        }

        public float getAspectRatio(String str) {
            float floatValue;
            synchronized (this) {
                Float f = (Float) get(str);
                floatValue = f != null ? f.floatValue() : -1.0f;
            }
            return floatValue;
        }

        @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
        public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
            synchronized (this) {
                clear();
                for (Map.Entry entry : packageFeatureData.entrySet()) {
                    String str = (String) entry.getKey();
                    String str2 = (String) entry.getValue();
                    if (PackageFeatureData.EMPTY_STRING.equals(str2)) {
                        put(str, Float.valueOf(DEFAULT_RATIO));
                    } else {
                        try {
                            String[] split = str2.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                            put(str, Float.valueOf(Float.parseFloat(split[0]) / Float.parseFloat(split[1])));
                        } catch (Exception e) {
                            Slog.w("PackageSettingsManager", "Fail to fixed aspect ratio, packageName=" + str + ", value=" + str2, e);
                            put(str, Float.valueOf(DEFAULT_RATIO));
                        }
                    }
                }
            }
        }
    }
}
