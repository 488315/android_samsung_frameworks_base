package com.android.server.wm;

import android.provider.DeviceConfig;
import android.util.ArraySet;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class LetterboxConfigurationDeviceConfig implements DeviceConfig.OnPropertiesChangedListener {
    static final Map sKeyToDefaultValueMap;
    public boolean mIsCameraCompatTreatmentEnabled = true;
    public boolean mIsDisplayRotationImmersiveAppCompatPolicyEnabled = true;
    public boolean mIsAllowIgnoreOrientationRequest = true;
    public boolean mIsCompatFakeFocusAllowed = true;
    public boolean mIsTranslucentLetterboxingAllowed = true;
    public final ArraySet mActiveDeviceConfigsSet = new ArraySet();

    static {
        Boolean bool = Boolean.TRUE;
        sKeyToDefaultValueMap = Map.of("enable_compat_camera_treatment", bool, "enable_display_rotation_immersive_app_compat_policy", bool, "allow_ignore_orientation_request", bool, "enable_compat_fake_focus", bool, "enable_letterbox_translucent_activity", bool);
    }

    public LetterboxConfigurationDeviceConfig(Executor executor) {
        DeviceConfig.addOnPropertiesChangedListener("window_manager", executor, this);
    }

    public void onPropertiesChanged(DeviceConfig.Properties properties) {
        for (int size = this.mActiveDeviceConfigsSet.size() - 1; size >= 0; size--) {
            String str = (String) this.mActiveDeviceConfigsSet.valueAt(size);
            if (properties.getKeyset().contains(str)) {
                readAndSaveValueFromDeviceConfig(str);
            }
        }
    }

    public void updateFlagActiveStatus(boolean z, String str) {
        if (z) {
            this.mActiveDeviceConfigsSet.add(str);
            readAndSaveValueFromDeviceConfig(str);
        }
    }

    public boolean getFlag(String str) {
        str.hashCode();
        switch (str) {
            case "enable_compat_fake_focus":
                return this.mIsCompatFakeFocusAllowed;
            case "enable_display_rotation_immersive_app_compat_policy":
                return this.mIsDisplayRotationImmersiveAppCompatPolicyEnabled;
            case "enable_letterbox_translucent_activity":
                return this.mIsTranslucentLetterboxingAllowed;
            case "allow_ignore_orientation_request":
                return this.mIsAllowIgnoreOrientationRequest;
            case "enable_compat_camera_treatment":
                return this.mIsCameraCompatTreatmentEnabled;
            default:
                throw new AssertionError("Unexpected flag name: " + str);
        }
    }

    public final void readAndSaveValueFromDeviceConfig(String str) {
        Boolean bool = (Boolean) sKeyToDefaultValueMap.get(str);
        if (bool == null) {
            throw new AssertionError("Haven't found default value for flag: " + str);
        }
        str.hashCode();
        switch (str) {
            case "enable_compat_fake_focus":
                this.mIsCompatFakeFocusAllowed = getDeviceConfig(str, bool.booleanValue());
                return;
            case "enable_display_rotation_immersive_app_compat_policy":
                this.mIsDisplayRotationImmersiveAppCompatPolicyEnabled = getDeviceConfig(str, bool.booleanValue());
                return;
            case "enable_letterbox_translucent_activity":
                this.mIsTranslucentLetterboxingAllowed = getDeviceConfig(str, bool.booleanValue());
                return;
            case "allow_ignore_orientation_request":
                this.mIsAllowIgnoreOrientationRequest = getDeviceConfig(str, bool.booleanValue());
                return;
            case "enable_compat_camera_treatment":
                this.mIsCameraCompatTreatmentEnabled = getDeviceConfig(str, bool.booleanValue());
                return;
            default:
                throw new AssertionError("Unexpected flag name: " + str);
        }
    }

    public final boolean getDeviceConfig(String str, boolean z) {
        return DeviceConfig.getBoolean("window_manager", str, z);
    }
}
